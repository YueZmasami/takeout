[Project Information]
This project implements basic backend restaurant management and front-end ordering functionality for a takeaway platform. However, it does not include subsequent delivery functionality.

The project is written in Java with JDK 8, utilizing technologies such as Spring Boot and MyBatis Plus. The database used is MySQL, and session caching is implemented for queries. Learning materials are derived from https://www.bilibili.com/video/BV13a411q753/
In comparison to the original tutorial, I've performed code optimizations and improvements in formatting. Regarding SMS verification, I've modified it to use email verification. Due to the unavailability of email verification functionality, I simplified it to output the verification code in the backend logs.

[Technologies Used]
Spring, Spring Boot, MySQL, MyBatis-Plus, Vue.js, Element UI

[Environments]
JDK 8
Maven

[Implemented Features]
 User registration
 User login
 User address management
 User order management
 User shopping cart
 Employee management
 Dish management
 Dish category management
 Set meal management
 Employee order management

[Run the program]
Run ReggieApplication.java under the src/main/java/com/yueZhai/reggie directory.
The default login username for the backend is admin, and the password is 123456.
The front-end user interface is optimized for mobile devices. If opened directly on a computer, the layout may be distorted. Therefore, please use a mobile browser or simulate a mobile browser using developer tools.
Default link for the backend management system: http://localhost:8080/backend/index.html
Default link for the front-end management system: http://localhost:8080/front/index.html
