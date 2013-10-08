DatabasesProject
================



Project for Databases!
Application Name:
Basket

To successfully run the project you need to:

1) Import from Eclipse an existing project.
	-Here you will search in the Basket folder for the project.
	
2) After this, from Eclipse you shall import existing android code.  
	-You will search in the google-play-services-lib folder.  This 
	 is a library necessary for push notifications.  
	 
3) In eclipse perform a project clean on all projects. 

4)Turn on the local server in the basket server folder. This is done with
 node app.js start from a terminal in where the basket binary is in the path variable
 and the current directory in the terminal is the Basket Server folder.
Optional
	-If there are still build errors in the project,
	 make sure that in the Basket project properties, under
	 Android, in library, it is able to find the google-play-services
	 library. If not then remove the faulty entry and press the add button. 
	 There you shall search for the google-play-services-lib project you
	 imported on the previous step
	 
	-To communicate with the external server,
	 Make sure that it is turned on and that in the BasketConstant class, in 
	 the general package, the amazon address is uncommented and the local one is commented
	-If you wish to communicate with the local server just uncomment the local address mentioned 
	 in the previous step and comment the amazon one.
	 
Troubleshooting:
1) Clean project
2)Make sure the server is turned on whether local or remote.
