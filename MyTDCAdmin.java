//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// Student name: Lance Baker 
// Course: SENG6400 (Network & Distributed Computing)
// Student number: c3128034
// Assignment title: SENG6400 Assignment 2 
// File name: MyTDCAdmin.java
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

import localhost.axis.tdc.MyTDCAdmin_jws.*;

public class MyTDCAdmin {
	public enum Method {tdcLogin, addLocation, setOffset, callCount, tdcLogout}; // The MyTDCAdmin webservice methods.
	private static String DEFAULT_ERROR = "ERROR: Invalid arguments received";
	
	// Checks whether the received String value is a double.
	// Returns a boolean value indicating success.
	private static boolean isDouble(String input) {
		try {
			Double.parseDouble(input);
			return true;
		} catch (Exception ex) {}
		return false;
	}
	
	// The sendRequest method is used to perform the actions corresponding to the desired service method.
	// The method receives a MyTDCAdmin stub instance, the service method (Converted to a Enumeration type), 
	// and the String[] args inputted from the CLI.
	// It returns a String value, which is the output from the webservice requests. 
	// Throws a Exception in events where a communication error has occurred (required by the MyTDCAdmin stub).
	public static String sendRequest(localhost.axis.tdc.MyTDCAdmin_jws.MyTDCAdmin server, Method method, String[] args) throws Exception {
		switch(method) { // The desired service method.
			// The tdcLogin webservice method requires; the username (args[1]) and password (args[2]) as arguments.
			// It will return the sessionID that will be used to access the other webservice methods.
			case tdcLogin:
				if (args.length == 3) { 
					return server.tdcLogin(args[1], args[2]);
				}
				break;
			// The addCurr webservice method requires; the sessionID (args[1]), the location code (args[2]),
			// and the offset (args[3]) - as arguments.	
			case addLocation: 
				if ((args.length == 4) && (isDouble(args[3]))) { 
					return new Boolean(server.addLocation(args[1], args[2], Double.parseDouble(args[3]))).toString();
				}
				break;
			// The setOffset webservice method requires the same arguments as the addLocation method.
			case setOffset:
				if ((args.length == 4) && (isDouble(args[3]))) {
					return new Boolean(server.setOffset(args[1], args[2], Double.parseDouble(args[3]))).toString();
				}
				break;
				
			// The callCount webservice method requires the sessionID (args[1]) for invocation.
			case callCount:
				if (args.length == 2) {
					return Integer.toString(server.callCount(args[1]));
				}
				break;
				
			// The tdcLogout webservice method requires the sessionID (args[1]) for invocation.
			// It will clear the session, which will logout the user.
			case tdcLogout:
				if (args.length == 2) {
					server.tdcLogout(args[1]);
				}
				break;
		}
		return DEFAULT_ERROR;
	}
	
	public static void main(String[] args) {
		try {
			// Checks whether the received arguments are correct length; having at least the webservice method.
			if (args[0] != null) {
				// The ServiceLocator, which essentially connects to the remote webservice, and provides
				// the ability to get a stub instance of the web service.
				MyTDCAdminService service = new MyTDCAdminServiceLocator();
				// Converts the first element into a Method Enumeration type (which is the desired webservice method that you want invoked).
				// Calls the sendRequest method, which receives a instantiated stub of the webservice, the method desired to be invoked,
				// and the arguments received from the CLI. Prints the output of the sendRequest method to the console.
				System.out.println(sendRequest(service.getMyTDCAdmin(), Method.valueOf(args[0]), args));
			} else {
				// Invalid starting arguments
				System.out.println(DEFAULT_ERROR);
			}
		} catch (Exception ex) {
			System.out.println(DEFAULT_ERROR);
		}
	}
}