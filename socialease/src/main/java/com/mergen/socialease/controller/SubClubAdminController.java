package com.mergen.socialease.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mergen.socialease.model.SubClub;
import com.mergen.socialease.model.User;
import com.mergen.socialease.model.Report;
import com.mergen.socialease.repository.SubClubRepository;
import com.mergen.socialease.repository.ReportRepository;
import com.mergen.socialease.repository.UserRepository;
import com.mergen.socialease.shared.CurrentUser;
import com.mergen.socialease.shared.GenericResponse;

@RestController
public class SubClubAdminController {

	@Autowired
	private SubClubRepository subClubRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ReportRepository reportRepository;

	@PostMapping("/report")
	public GenericResponse report(@RequestBody Report r, @CurrentUser User user) {
		if (r.getReportType() != 1) {
			return new GenericResponse("Error: Type is wrong!");
		}
		if (r.getReported() == null || r.getReported().equals("") || r.getReporter() == null
				|| r.getReporter().equals("")) {
			return new GenericResponse("Error: Something is wrong!");
		}
		if (r.getExplanation() == null || r.getExplanation() == "") {
			return new GenericResponse("Error: invalid-explanation");
		}
		try {
			User u = userRepository.findByUsername(r.getReported());
			String sCString = u.getSubClubList();
			String[] scList = sCString.split(",");
			Long SCID = r.getSubClubid();
			boolean check1 = true;
			for (int i = 0; i < scList.length; i++) {
				String x = scList[i].split("-")[0];
				x=x.substring(1, x.length());
				if (SCID == Long.parseLong(x)) {
					check1 = false;
					break;
				}
			}
			if (check1) {
				return new GenericResponse("Error: This user is not member of this subclub!");
			}
		} catch (Exception e) {
			System.out.println(e);
			return new GenericResponse("Error: Something is wrong!");
		}
		reportRepository.save(r);
		return new GenericResponse("Report is sent!");
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/viewreport")
	public ArrayList viewReport(@RequestBody JSONObject reportTypee, @CurrentUser User user) {

		Long reportType = Long.valueOf((Integer) reportTypee.get("reportType"));
		if (user.getIsSubClubAdmin() == -1 || user.getIsSubClubAdmin() == -2 || user.getIsSubClubAdmin() == -3) {
			ArrayList<String> list = new ArrayList<String>();
			list.add("You are not a subclub admin");
			return list;
		} else
			return reportRepository.findAllByReportTypeAndSubClubid(reportType, user.getIsSubClubAdmin());

	}
	
	
	@PostMapping("/evaluatereport")
	public GenericResponse evalueateReport(@RequestBody JSONObject json, @CurrentUser User user) {
		if(user.getIsSubClubAdmin()>=0) {
			Long reportid = Long.valueOf((Integer) json.get("reportid"));
			boolean ban = (boolean) json.get("ban");
			Report r = reportRepository.findByreportid(reportid);
			String x = r.getReported();
			User reportedUser = userRepository.findByUsername(x);
			SubClub subclub = subClubRepository.findBysubClubid(user.getIsSubClubAdmin());
			String[] list1=subclub.getUserList().split(",");
			List<String> targetList = Arrays.asList(list1);
			if(targetList.contains(Long.toString(reportedUser.getUserid()))) {
				if (ban == false) {
					reportRepository.deleteById(reportid);
					return new GenericResponse("Report Deleted");
				} else {
					reportRepository.deleteById(reportid);
					//USER İLE İLGİLİ DİĞER HER ŞEY DE SİLİNMELİ!
					userRepository.delete(reportedUser);
					return new GenericResponse("User banned");
				}
			}
			else {
				return new GenericResponse("ERROR");
			}
		}
		else {
			return new GenericResponse("ERROR");
		}
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/getnameofthesubclub")
	public JSONObject getNameOfTheSubClub(@RequestParam Long id, @CurrentUser User user) {
		JSONObject json = new JSONObject();
		try {
			json.put("nameOfSubClub", subClubRepository.findBysubClubid(id).getName());
		}
		catch(Exception e) {}
		return json;
	}
}
