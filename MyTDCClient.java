//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// Student name: Lance Baker 
// Course: SENG6400 (Network & Distributed Computing)
// Student number: c3128034
// Assignment title: SENG6400 Assignment 2 
// File name: MyTDCClient.java
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

import localhost.axis.tdc.MyTDCServer_jws.*;

public class MyTDCClient {
	public enum Method {currentOffset, listLocations, difference}; // The MyTDCServer webservice methods.
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
	// The method receives a MyTDCServer stub instance, the service method (Converted to a Enumeration type), 
	// and the String[] args inputted from the CLI.
	// It returns a String value, which is the output from the webservice requests. 
	// Throws a Exception in events where a communication error has occurred (required by the MyTDCServer stub).
	public static String sendRequest(MyTDCServer server, Method method, String[] args) throws Exception {
		switch(method) { // The desired service method.
			// Ensures that the correct arguments have been inputted.
			// First element being the method, and the second being the location.
			case currentOffset: 
				if (args.length == 2) {
					// Returns a String representation of the Double value received 
					// from the webservices currentOffset method (based on the location).
					return Double.toString(server.currentOffset(args[1]));
				}
				break;
			case listLocations:
				// Invokes the listLocations webservice method.
				// Outputs a list of the available locations.
				return server.listLocations();
			case difference:
				// Ensures that the correct arguments have been inputted.
				// First element being the method, the second being the first location, and the third being the second location.
				if (args.length == 3) {
					// Calculates and returns the offset difference between two locations.
					return server.difference(args[1].toUpperCase(), args[2].toUpperCase());
				}
				break;
		}
		return DEFAULT_ERROR;
	}
	
	public static void main(String[] args) {
		try {
			// If the received arguments have at least the method specified, and the first args element
			// is not null - then it converts the first element into a Method Enumeration type (which is
			// the desired webservice method that you want invoked).
			if ((args.length >= 1) && (args[0] != null)) {
				// The ServiceLocator, which essentially connects to the remote webservice, and provides
				// the ability to get a stub instance of the web service.
				MyTDCServerService service = new MyTDCServerServiceLocator();
				// Calls the sendRequest method, which receives a instantiated stub of the webservice, the method desired to be invoked,
				// and the arguments received from the CLI.
				System.out.println(sendRequest(service.getMyTDCServer(), Method.valueOf(args[0]), args));
			} else {
				// Invalid starting arguments
				System.out.println(DEFAULT_ERROR);
			}
		} catch (Exception ex) {
			System.out.println(DEFAULT_ERROR);
		}
	}
}