/*
 * 
 */
package org.jumbune.automation;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

// TODO: Auto-generated Javadoc
/**
 * The Class Screenshoter.
 */
public class Screenshoter {

	/** The last screen shot. */
	@SuppressWarnings("unused")
	private static String lastScreenShot;
	
	/** The screen shot count. */
	private static int screenShotCount = 0;
	
	/** The prop. */
	static Properties prop = new Properties();
	
	/** The input config. */
	static InputStream inputConfig = null;

	/**
	 * Capture screen shot.
	 * 
	 * @param wDriver
	 *            the w driver
	 * @param sScreenShotName
	 *            the s screen shot name
	 * @param strTestName
	 *            the str test name
	 * @throws Exception
	 *             the exception
	 */
	public static void captureScreenShot(WebDriver wDriver,
			String sScreenShotName, String strTestName) throws Exception {
		screenShotCount++;
		inputConfig = new FileInputStream(System.getenv(AutomationConstants.AUTOMATION_HOME)+AutomationConstants.FILE_SEPARATOR+AutomationConstants.PROPERTY_FILE_NAME);
		prop.load(inputConfig);
		if (sScreenShotName != null && sScreenShotName.trim() != "")
			sScreenShotName = String.valueOf(screenShotCount) + "_"
					+ sScreenShotName + strTestName + ".png";

		else
			sScreenShotName = String.valueOf(screenShotCount) + "_"
					+ strTestName + ".png";
		File scrFile = ((TakesScreenshot) wDriver).getScreenshotAs(OutputType.FILE);
		createDatewiseDirectory(String.valueOf(System.getenv(AutomationConstants.AUTOMATION_HOME)+AutomationConstants.FILE_SEPARATOR+ prop.get(AutomationConstants.SCREENSHOT_FOLDER)));
		FileUtils.copyFile(scrFile,	new File(System.getenv(AutomationConstants.AUTOMATION_HOME)+AutomationConstants.FILE_SEPARATOR+prop.get(AutomationConstants.SCREENSHOT_FOLDER)+getDateString() + "\\" + sScreenShotName));
		
	}

	/**
	 * Creates the datewise directory.
	 * 
	 * @param parentPath
	 *            the parent path
	 */
	private static void  createDatewiseDirectory(String parentPath) {
		File dateDir=new File(parentPath+AutomationConstants.FILE_SEPARATOR+getDateString());
		if(!dateDir.exists())
		{
			dateDir.mkdir();
		}
	}
	
	/**
	 * Gets the date string.
	 * 
	 * @return the date string
	 */
	private static String getDateString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());
	}

	
}
