package server.configuration;

public class Configuration {
	public static String host = "0.0.0.0";
	public static Integer port = 8080;
	public static String baseUri = "http://localhost:8080/";
	public static Boolean secure = false;
	public static Boolean logRequests = false;
	public static Integer stopPort = 8099;
	public static Boolean useSsl = false;
	public static String sslKeyStorePath = "";
	public static String sslKeyStorePassword = "";

	public static String databaseUrl = "jdbc:hsqldb:mem:unit-testing-jpa;sql.syntax_mys=true";
	public static String databaseUser = "";
	public static String databasePassword = "";

	public static String smtpHost = "";
	public static String smtpUsername = "";
	public static String smtpPassword = "";

	public static String filePath = "/tmp/server/files";

	public static String resizedImagePath = "/tmp/server/images";
	public static String imageMagickPath = "/usr/local/bin";

	public static String logInfoPath = "/tmp/server/logs/info.log";
	public static String logDebugPath = null;
	public static String logAccessPath = "/tmp/server/logs/access.log";
	public static String logEmailRecipients = "";
}
