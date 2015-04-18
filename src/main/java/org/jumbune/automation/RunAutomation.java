/*
 * 
 */
package org.jumbune.automation;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;


/**
 * The Class RunAutomation.
 */
public class RunAutomation {

	/** The driver. */
	private static WebDriver driver;

	/** The test cases logger. */
	private static Logger testCasesLogger = Logger.getLogger(RunAutomation.class);


	
	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		boolean testPass = true;
		String brwoserName = null;
//		reading from the property file
		Properties prop = new Properties();
		InputStream inputConfig = new FileInputStream(System.getenv(AutomationConstants.AUTOMATION_HOME)+AutomationConstants.FILE_SEPARATOR+AutomationConstants.PROPERTY_FILE_NAME);
		Boolean sanityEnable=false;
		Boolean regressionEnable=false;
		long totalTestCaseTime=0;
		long totalSuitTestTime=0;
		int totalTestCaseCount=0;
		int totalFailedTests=0;
		String sTestScenarioName;
		Boolean totalTestSuitPass = true;
		HTMLReport reportLevelThree = new HTMLReport ();
		HTMLReport reportLevelTwo = new HTMLReport(); 
		HTMLReport	reportLevelOne = new HTMLReport();
		try {
			prop.load(inputConfig);
			ExcelHandler testSuite;
			ExcelHandler testCases;
			ExcelHandler objectRepository;
			int oR = AutomationConstants.ONE;
			int oRType = AutomationConstants.TWO;
			String sAllTests=prop.get(AutomationConstants.TEST_FILE_NAME).toString();
			String sAllBrowsers=prop.getProperty(AutomationConstants.BROWSER);
			String [] allBrowsers=sAllBrowsers.split(AutomationConstants.SPLIT_STRING);
			String [] allModules=sAllTests.split(AutomationConstants.SPLIT_STRING);
			reportLevelOne.generateLevelOneHeader(); 
			reportLevelTwo.generateLevelTwoHeader();
//			iterate through the browsers
			for (String currentBrowser : allBrowsers){
//				iterate through all the  module test files 
				for (String currentModule : allModules) {
					brwoserName = currentBrowser;
//					access the testcase file
//					access the testsuit sheet
					testSuite = new ExcelHandler(System.getenv(AutomationConstants.AUTOMATION_HOME)+AutomationConstants.FILE_SEPARATOR+prop.getProperty(AutomationConstants.TEST_FOLDER_NAME)+AutomationConstants.FILE_SEPARATOR+currentModule, AutomationConstants.SUITE);
					testSuite.ColumnData();
//					access the OR
					objectRepository = new ExcelHandler(System.getenv(AutomationConstants.AUTOMATION_HOME)+AutomationConstants.FILE_SEPARATOR+AutomationConstants.FILE_SEPARATOR+prop.getProperty(AutomationConstants.OBJECT_REPOSITORY_FOLDER_NAME)+prop.getProperty(AutomationConstants.OBJECT_REPOSITORY_FILE_NAME), AutomationConstants.OBJECT_REPOSITORY_SHEET_NAME);
					objectRepository.RowData();
					String sTestName;
//					Making the the result excel 
					ExcelWriter wExcelResults = new ExcelWriter(currentModule);
					wExcelResults.createSheet();
					reportLevelThree.generateLevelThreeHeader();
//					iterate through all the test cases
				
					for (int rowSuiteCnt = 1; rowSuiteCnt < testSuite.RowCount(); rowSuiteCnt++) {
						totalTestCaseTime=0;
						testCasesLogger.debug("rowCnt = " + rowSuiteCnt);
//						access respective columns
						
						sTestName = testSuite.ReadCell(
								testSuite.GetCell(AutomationConstants.TESTCASES), rowSuiteCnt);
						String sDescription = testSuite.ReadCell(
								testSuite.GetCell(AutomationConstants.DESCRIPTION), rowSuiteCnt);
						String sSanity = testSuite.ReadCell(
								testSuite.GetCell(AutomationConstants.SANITY), rowSuiteCnt);
						String sRegression = testSuite.ReadCell(
								testSuite.GetCell(AutomationConstants.REGRESSION), rowSuiteCnt);
						if(prop.get(AutomationConstants.SANITY).equals(AutomationConstants.YES))
							sanityEnable=true;
						if(prop.get(AutomationConstants.REGRESSION).equals(AutomationConstants.YES))
							regressionEnable=true;
						if(sSanity.equalsIgnoreCase(AutomationConstants.NO))
							sanityEnable=false;
						if(sRegression.equalsIgnoreCase(AutomationConstants.NO))
							regressionEnable=false;
						testCasesLogger.debug("strTestName = " + sTestName);
						String sDescriptionOld=sDescription;
						
						testCases = new ExcelHandler(System.getenv(AutomationConstants.AUTOMATION_HOME)+AutomationConstants.FILE_SEPARATOR+prop.getProperty(AutomationConstants.TEST_FOLDER_NAME)+AutomationConstants.FILE_SEPARATOR+currentModule, sTestName);
						testCases.ColumnData();
						testCasesLogger.debug("xlsTest Rows = " + testCases.RowCount());
						testCasesLogger.debug("xlsTest Columns = "+ testCases.ColumnCount());
						sTestScenarioName=sTestName;
						try {
//						pick if sanity or regresson if enabled
							if (regressionEnable||sanityEnable) {
								totalTestSuitPass=true;
								totalTestCaseCount++;
								long startTime;
								driver = Driver.getDriver(currentBrowser);
//								writing the results to the excel sheet
								wExcelResults.InsertRow(sTestName, null, sDescription, null, null, true);
//								writing the report to the HTML sheet
								reportLevelThree.generateLevelThree(currentModule,sTestName," ", sDescription," "," ",null, " ",currentBrowser);
								for (int rowTestCnt = 1; rowTestCnt < testCases.RowCount(); rowTestCnt++) {
									startTime = System.currentTimeMillis();
									String sTestStep = testCases.ReadCell(testCases.GetCell(AutomationConstants.STEPS), rowTestCnt);
									sDescription = testCases.ReadCell(
											testCases.GetCell(AutomationConstants.DESCRIPTION), rowTestCnt);
									String sKeyword = testCases.ReadCell(
											testCases.GetCell(AutomationConstants.KEYWORD), rowTestCnt);
									String sObjName = testCases.ReadCell(
											testCases.GetCell(AutomationConstants.OBJECT), rowTestCnt);
									String sValue = testCases.ReadCell(
											testCases.GetCell(AutomationConstants.VALUE), rowTestCnt);
									String sObjProperty = objectRepository.ReadCell(oR,
											objectRepository.GetCell(sObjName));
									String sObjPropertyType = objectRepository.ReadCell(
											oRType, objectRepository.GetCell(sObjName));
									testCasesLogger.debug("Driver = " + sKeyword);
									testCasesLogger.debug("The Name of the Object = "+ sObjName);
									testCasesLogger.debug("Property = "+ sObjProperty);
									testCasesLogger.debug("Property type = "+ sObjPropertyType);
									testCasesLogger.debug("Value = " + sValue);
									testPass = TestRunner.RunTest(driver, sKeyword,sObjProperty, sValue, sTestName,sObjPropertyType);
									regressionEnable=false;
									sanityEnable=false;
									wExcelResults.InsertRow(null, sTestStep, sDescription,sKeyword, sValue, testPass);
									long lTime=(System.currentTimeMillis()-startTime);
									totalTestCaseTime=totalTestCaseTime+lTime ;
									if(!testPass)
									{
										//	Screenshoter.captureScreenShot(driver, "Exception",sTestName);
										totalFailedTests++;
										totalTestSuitPass=false;
										reportLevelThree.generateLevelThree("","",sTestStep, sDescription,sKeyword, sValue, testPass,"Unable to" +sKeyword+" on " +sObjName, " ");
										reportLevelTwo.generateLevelTwo(currentModule,sTestScenarioName, sDescriptionOld,totalTestCaseTime, totalTestSuitPass,currentBrowser);
										driver.close();
										driver.quit();
										continue;
									}
									reportLevelThree.generateLevelThree("","",sTestStep, sDescription,sKeyword, sValue, testPass," ", " ");	
								}
								reportLevelTwo.generateLevelTwo(currentModule,sTestScenarioName, sDescriptionOld,totalTestCaseTime, totalTestSuitPass,currentBrowser);
								}
							
							
						}
						catch (Exception e) {
							String sName=AutomationConstants.EXCEPTION_SS+sTestName;
							testCasesLogger.error(e);
							continue;
						} finally {
							testCasesLogger.info("Completed");
							totalSuitTestTime=totalSuitTestTime+totalTestCaseTime;
						}
					}
					
					
					
				}
				
			}
		} catch (Exception e) {
			testCasesLogger.error(e.toString());
		}
		finally {
			reportLevelOne.generateLevelOne(totalTestCaseCount, totalSuitTestTime ,totalFailedTests);
			driver=null;
			String documentLevelThree = reportLevelThree.getReport();
			String documentLevelTwo = reportLevelTwo.getReport();
			String documentLevelOne=reportLevelOne.getReportOne();
			
			String sLevelTwoMailer=prop.get(AutomationConstants.LEVEL_TWO).toString();
			String[] sLevelTwoAddress=sLevelTwoMailer.split(",");
			Mailer.mailme(sLevelTwoAddress,documentLevelTwo);
			String sLevelThreeMailer=prop.get(AutomationConstants.LEVEL_THREE).toString();
			String[] sLevelThreeAddress=sLevelThreeMailer.split(",");
			Mailer.mailme(sLevelThreeAddress,documentLevelThree);
			try {
				File output = new File(System.getenv(AutomationConstants.AUTOMATION_HOME)+AutomationConstants.FILE_SEPARATOR+AutomationConstants.HTML_REPORTS+AutomationConstants.FILE_SEPARATOR+AutomationConstants.REPORT_LEVEL_THREE);
				PrintWriter out = new PrintWriter(new FileOutputStream(output));
				out.println(documentLevelThree);
				out.close();
				File output2 = new File(System.getenv(AutomationConstants.AUTOMATION_HOME)+AutomationConstants.FILE_SEPARATOR+AutomationConstants.HTML_REPORTS+AutomationConstants.FILE_SEPARATOR+AutomationConstants.REPORT_LEVEL_TWO);
				PrintWriter out2 = new PrintWriter(new FileOutputStream(output2));
				out2.println(documentLevelTwo);
				out2.close();
				File output3 = new File(System.getenv(AutomationConstants.AUTOMATION_HOME)+AutomationConstants.FILE_SEPARATOR+AutomationConstants.HTML_REPORTS+AutomationConstants.FILE_SEPARATOR+AutomationConstants.REPORT_LEVEL_ONE);
				PrintWriter out3 = new PrintWriter(new FileOutputStream(output3));
				out3.println(documentLevelOne);
				out3.close();
				FileUtils.copyFile(output3, new File(prop.get(AutomationConstants.JENKINS).toString()));
			
			} catch (FileNotFoundException e) {
				testCasesLogger.error(e.toString());
			}
			System.exit(0);


		}
	}

}


