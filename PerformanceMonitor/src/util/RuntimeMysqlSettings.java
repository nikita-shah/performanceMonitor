package util;


public class RuntimeMysqlSettings {

	public static final String APPLICATION_NAME = "Performance_Monitor";
	public static final String VERSION_ID = " version 0.1";
	public static final String SERVER_IP = "localhost";
	static String databaseName = "performance_monitor";
	static String dbUserID = "root";
	
	static String dbPassword = "root"; 
	public static String RUN_MODE = "Test"; // "Production"; //
	public static boolean IS_IN_DEBUG_MODE = true; // false; //
	static int portNo = 3306;// 5432; //
		
	
}