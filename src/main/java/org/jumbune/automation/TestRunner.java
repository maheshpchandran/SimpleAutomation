/*
 * 
 */
package org.jumbune.automation;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;


// TODO: Auto-generated Javadoc
/**
 * The Class TestRunner.
 */
public class TestRunner {

    /** The test runner logger. */
    private static Logger testRunnerLogger = Logger.getLogger(TestRunner.class);
    
    /** The prop. */
    static Properties prop = new Properties();
	
	/** The input config. */
	static InputStream inputConfig = null;
	
	/**
	 * Run test.
	 * 
	 * @param wDriver
	 *            the w driver
	 * @param sKeyword
	 *            the s keyword
	 * @param sObject
	 *            the s object
	 * @param sValue
	 *            the s value
	 * @param sTestName
	 *            the s test name
	 * @param sPropType
	 *            the s prop type
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	public static boolean RunTest(WebDriver wDriver, String sKeyword,String sObject, String sValue, String sTestName, String sPropType)
			throws Exception {
		testRunnerLogger.setLevel(Level.DEBUG);
		inputConfig = new FileInputStream(System.getenv(AutomationConstants.AUTOMATION_HOME)+AutomationConstants.FILE_SEPARATOR+AutomationConstants.PROPERTY_FILE_NAME);
		prop.load(inputConfig);
		try {
			
			if (sKeyword.equalsIgnoreCase("URL")) {
				if (sValue != null && sValue.trim() != "")
				{	wDriver.get(sValue);
				if("Certificate Error: Navigation Blocked".equals(wDriver.getTitle())) {
					wDriver.navigate().to("javascript:document.getElementById('overridelink').click()");
					}}
				else if (sObject != null && sObject.trim() != "")
					{wDriver.get(sObject);
					if("Certificate Error: Navigation Blocked".equals(wDriver.getTitle())) {
						wDriver.navigate().to("javascript:document.getElementById('overridelink').click()");
						}
					}
				else
				{	wDriver.get((String) prop.get(AutomationConstants.URL));
				if("Certificate Error: Navigation Blocked".equals(wDriver.getTitle())) {
					wDriver.navigate().to("javascript:document.getElementById('overridelink').click()");
					}
				}

			} else if (sKeyword.startsWith("Click")) {
				Driver.getWebElement(wDriver, sObject, sPropType).click();

			} else if (sKeyword.startsWith("Clear")) {
				Driver.getWebElement(wDriver, sObject, sPropType).clear();
				
			} else if (sKeyword.startsWith("dropdown")) {
				Driver.getWebElement(wDriver, sObject, sPropType);

			}
			
			else if (sKeyword.equalsIgnoreCase("Close")) {
				
				
				wDriver.close();
				wDriver.quit();
			}
	 		else if (sKeyword.equalsIgnoreCase("EnterText")) {
				Driver.getWebElement(wDriver, sObject, sPropType).sendKeys(
						sValue);
			} else if (sKeyword.equalsIgnoreCase("VerifyLink")) {	
				//logger here
				testRunnerLogger.debug(Driver.getWebElement(wDriver, sObject, sPropType).isEnabled());	
				//System.out.println(Driver.getWebElement(wDriver, sObject, sPropType).isEnabled());
			}else if (sKeyword.equalsIgnoreCase("VerifyText")) {	
				//logger here
				testRunnerLogger.debug(Driver.getWebElement(wDriver, sObject, sPropType).getText().equals(sValue));	
				//System.out.println(Driver.getWebElement(wDriver, sObject, sPropType).isEnabled());
			}
			
			
			else if (sKeyword.equalsIgnoreCase("VerifyEnabled")) {	
				//logger here
				testRunnerLogger.debug(Driver.getWebElement(wDriver, sObject, sPropType).isDisplayed());	
				//System.out.println(Driver.getWebElement(wDriver, sObject, sPropType).isEnabled());
			}
			else if (sKeyword.equalsIgnoreCase("sleep")) {
				testRunnerLogger.debug("long sValue:" + Long.parseLong(sValue));
			//	System.out.println("long sValue:" + Long.parseLong(sValue)); //logger here
				if (sValue != null && sValue.trim() != "")
					Thread.sleep((Long.parseLong(sValue)) * 1000L);
				else
					Thread.sleep(10000L);
			}

			else if (sKeyword.equalsIgnoreCase("CaptureScreenShot")) {
				Screenshoter.captureScreenShot(wDriver, sValue, sTestName);
			} 
			else if(sKeyword.equalsIgnoreCase("FileUpload"))
			{
				Runtime.getRuntime().exec(System.getenv(AutomationConstants.AUTOMATION_HOME)+AutomationConstants.FILE_SEPARATOR+"fileupload.exe");
				
			}
			
			
			else {
				testRunnerLogger.debug("Enter a valid Keyword ");
				//System.out.println("Enter a valid Keyword "); //logger here
				return false;
			}

			return true;
		} catch (Exception e) {
			testRunnerLogger.error(e);
			wDriver.close();
			return false;
			
		}
	
	}

}
