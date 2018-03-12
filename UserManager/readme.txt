STEPS ON HOW TO RUN THE APPLICATION
=====================================

1. You need MYSQL installed and then create a database called kinetic.
   Username: root
   Password: password
   
2. Run the following code in the query window to create a users table:

			CREATE TABLE `users` (
			`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
			`username` VARCHAR(50) NOT NULL,
			`password` VARCHAR(50) NOT NULL,
			`phone` VARCHAR(50) NULL DEFAULT NULL,
			`token` VARCHAR(50) NULL DEFAULT NULL,
			`last_login_time` DATETIME NULL DEFAULT NULL,
			`status` TINYINT(4) NULL DEFAULT NULL,
			`loggedIn` VARCHAR(1) NULL DEFAULT NULL,
			PRIMARY KEY (`id`),
			UNIQUE INDEX `username` (`username`)
		)
		COLLATE='latin1_swedish_ci'
		ENGINE=InnoDB
		AUTO_INCREMENT=4
		;

3. Go to either of these web pages to register or log in if you are have already registered:

	http://localhost:8080/Kinetic/login.jsp
	http://localhost:8080/Kinetic/register.jsp
	
4. On successful login, you will have an option to log out or to filter users according to the options in the dropdown.


NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE
==========================================================================================
Please be aware that this is my first try and work on a Restful webservice application. I only have experience with SOAP web services throughout my career.

Points 6 and 7 are not complete. The application can filter out users who logged in the past 5 minutes, but I did not show the users updated dynamically in the front-end when a user's session expires. 
But in the back-end the feature works fine.

I could also have cleaned out my code if I had enough time.
I have included a war file to be deployed in order to run this application.
Under normal circumstances, I would not have kept an executable file as part of the code on Git.

Please run this appication using Tomcat.

When the user's session expires, for some reason redirect does not work in the frontend. This leads to a not found page if the user clicks any link after the expiry of the token.



