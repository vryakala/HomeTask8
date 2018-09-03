package homeTask8;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import homeTask8.HTML;

public class Log4jClass {

	static {
		init();
	}

	private final static Logger logger = Logger.getLogger(Log4jClass.class);
	static File file = new File("D:\\DirInfo.html");
	static File dir = new File("D:\\Bills");

	public static void main(String[] args) {

		logger.debug("My Debug Log");
		logger.info("My Info Log");
		logger.warn("My Warn Log");
		logger.error("My error log");
		logger.fatal("My fatal log");

		try {
			HTML.FileCreation(file, dir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * method to init log4j configurations
	 */
	private static void init() {
		DOMConfigurator.configure("log4j.xml");
	}

}
