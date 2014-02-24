-----------------------------------------------------------------------------------------
Installing
-----------------------------------------------------------------------------------------
Please note
-----------------------------------------------------------------------------------------
The SERVER_SERVICE constant located in the MyTDCAdmin.jws may need to be modified to contain 
the web accessible path to the MyTDCServer.jws service.

	Example
	private static final String SERVER_SERVICE = "http://localhost:8080/axis/tdc/MyTDCServer.jws";

-----------------------------------------------------------------------------------------

1) Copy the MyTDCServer.jws, and the MyTDCAdmin.jws files into a new folder called "tdc" (within the axis directory).
   Therefore, you should be able to access the webservices using the following URLs:
   http://localhost:8080/axis/tdc/MyTDCServer.jws
   http://localhost:8080/axis/tdc/MyTDCAdmin.jws

   If the above addresses are not valid; either your not using the localhost, port, or changed the folder name - then you
   will need to update the constant (as outlined above) and redownload the webservices XML.

2) The XML files for the webservices are included, and also the generated stub code. In order to create the stub code based
   on the provided XML; then you will need to do the following:
 
   java org.apache.axis.wsdl.WSDL2Java MyTDCServer.xml
   java org.apache.axis.wsdl.WSDL2Java MyTDCAdmin.xml

   The above should generate the stub code in a 'localhost' directory, which is imported in both client applications:
   import localhost.axis.tdc.MyTDCAdmin_jws.*;
   import localhost.axis.tdc.MyTDCServer_jws.*;

3) Compile both client applications:

   javac MyTDCAdmin.java
   javac MyTDCClient.java

-----------------------------------------------------------------------------------------
Runtime formats
-----------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------
Please note
-----------------------------------------------------------------------------------------

The session_id value is the returned 5 character combination from tdcLogin


MyUserClient.java:

	java MyTDCClient currentOffset <location>
	
	java MyTDCClient listLocations

	java MyTDCClient difference <location1> <location2>

	
MyUserAdmin.java:

	java MyTDCAdmin tdcLogin admin difference

	java MyTDCAdmin addLocation <session_id> <location> <offset>

	java MyTDCAdmin setOffset <session_id> <location> <offset>

	java MyTDCAdmin callCount <session_id>

	java MyTDCAdmin tdcLogout <session_id>
