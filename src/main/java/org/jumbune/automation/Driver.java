/*
 * 
 */
package org.jumbune.automation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.HSSFColor.AUTOMATIC;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

// TODO: Auto-generated Javadoc
/**
 * The Class Driver.
 */
public class Driver {

	/** The driver logger. */
	private static Logger driverLogger = Logger.getLogger(Driver.class);

	/**
	 * Gets the web element.
	 * 
	 * @param wDriver
	 *            the w driver
	 * @param sObject
	 *            the s object
	 * @param sPropType
	 *            the s prop type
	 * @return the web element
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	static WebElement getWebElement(WebDriver wDriver, String sObject,String sPropType) throws IOException {
		Properties prop = new Properties();
		InputStream inputConfig = null;
		inputConfig = new FileInputStream(System.getenv(AutomationConstants.AUTOMATION_HOME)+AutomationConstants.FILE_SEPARATOR+AutomationConstants.PROPERTY_FILE_NAME);
		prop.load(inputConfig);
		driverLogger.setLevel(Level.DEBUG);

		driverLogger.debug("In Function getWebElement, sPropType is "+ sPropType );
		if (sPropType.equalsIgnoreCase("CSS"))
			return wDriver.findElement(By.cssSelector(sObject));
		else if (sPropType.equalsIgnoreCase("XPATH"))
			return wDriver.findElement(By.xpath(sObject));
		else if (sPropType.equalsIgnoreCase("Name"))
			return wDriver.findElement(By.name(sObject));
		else if (sPropType.equalsIgnoreCase("ID"))
			return wDriver.findElement(By.id(sObject));
		else if (sPropType.equalsIgnoreCase("Link"))
			return wDriver.findElement(By.linkText(sObject));
		else
			return wDriver.findElement(By.xpath(sObject));
	}

	/**
	 * Gets the driver.
	 * 
	 * @param sBrowserName
	 *            the s browser name
	 * @return the driver
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static WebDriver getDriver(String sBrowserName) throws IOException {
		FirefoxProfile profile;
		Properties prop = new Properties();
		InputStream inputConfig = new FileInputStream(System.getenv(AutomationConstants.AUTOMATION_HOME)+AutomationConstants.FILE_SEPARATOR+AutomationConstants.PROPERTY_FILE_NAME);
		prop.load(inputConfig);
		WebDriver driver;
		DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
		ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		if (sBrowserName.equalsIgnoreCase("Firefox"))
		{
		profile = new FirefoxProfile();
		profile.setAcceptUntrustedCertificates(true);
		FirefoxBinary binary = new FirefoxBinary(new File(System.getenv(AutomationConstants.FIREFOX_HOME)+AutomationConstants.FIREFOX_EXE));

		driver = new FirefoxDriver(binary, profile);
			}
		else if (sBrowserName.equalsIgnoreCase("IE"))
		{
		System.setProperty("webdriver.ie.driver",System.getProperty(AutomationConstants.USER_DIR)+prop.getProperty(AutomationConstants.IEDRIVER));
		driver = new InternetExplorerDriver(ieCapabilities);
		}
		else if (sBrowserName.equalsIgnoreCase("Chrome"))
		{
			//System.setProperty("webdriver.chrome.logfile", "D:\\chrome\\chromedriver.log");
			System.setProperty(AutomationConstants.WEBDRIVER_CHROME_DRIVER,System.getenv(AutomationConstants.AUTOMATION_HOME)+prop.getProperty(AutomationConstants.CHROME_DRIVER));
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		else
		{	
		System.setProperty(AutomationConstants.WEBDRIVER_CHROME_DRIVER,System.getenv(AutomationConstants.AUTOMATION_HOME)+prop.getProperty(AutomationConstants.CHROME_DRIVER));
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		}
		return driver;
	}

}
