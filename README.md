# Mergen - Socialease
This is a project developed for the Software Engineering Lab in the spring semester of 2021. Developed by a team of 4 people.
## Our Video: [Socialease - Final Presentation Video](https://www.youtube.com/watch?v=y8nfwa5V0Xw)
##### Team Members
Ahmet Hakan YILDIZ (Frontend Developer - Backend Developer)<br/>
Onur ZEREN (Backend Developer)<br/>
Ahmet Fatih SOLAK (Backend Developer)<br/>
Bayram YILDIZ (Backend Developer)<br/>

I was responsible for project management. I worked as both frontend developer and backend developer. I also took care of the security side.

##### Used Technologies
React<br/>
Spring Boot<br/>
MySQL<br/>
##### About Commits
The first commit to this repository is the school submitted version. In the next commits, I plan to refactor the project, improve it in terms of security, and add new functionality (INDIVIDUALLY).

##### Warning
Lombok is a code generation library that can be integrated into the IDE while developing a Java project. Lombok integration is required in the developer version of this project! Otherwise, it will give an error.

### The changes that came with the second commit and the decision I made
There were some issues with the deleteClub and updateClub functions. Deleting a club/subclub was causing some data not to be deleted and bloating the database. These two functions have been updated.</br>
Added deleteUser function.</br>
The evaluateReport function was able to ban the reported user. I changed it to delete instead of ban. So when a user is reported and banned, his account is deleted.</br>
Some visual improvements have been made.</br></br>
Initially, I was planning to make this project better by updating it from time to time. But I realized that this would take a lot of time. Instead of refactoring this project, I decided to reengineer it. I will reengineer this project using MEAN stack technology. If we look at the final state of the project in general:</br>
All functions work smoothly.</br>
Satisfactory in terms of user interface.</br>
It contains some bugs.</br>
The project structure is somewhat complex. The reason of this is that it is a school project and it is tried to be done in a very short time. Also, it's the complexity of the project that prompted me to reengineer it.


