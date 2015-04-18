/*
 * 
 */
package org.jumbune.automation;


// TODO: Auto-generated Javadoc
/**
 * The Class HTMLReport.
 */
public class HTMLReport {

	/** The t html. */
	StringBuilder tHtml;

	/**
	 * Instantiates a new HTML report.
	 */
	HTMLReport() {
		tHtml = new StringBuilder();
		tHtml.append("<DOCTYPE html>" + "\n");
		tHtml.append("<html lang='en-US'>" + "\n");
		tHtml.append("<head>" + "\n");
		tHtml.append("<meta charset=utf-8>" + "\n");
		tHtml.append("<title>Automation Report</title>" + "\n");
		tHtml.append("</head>" + "\n");
		tHtml.append("<body>" + "\n");
		tHtml.append("<style type=\"text/css\">tg{border-collapse:collapse;border-spacing:0;border-color:#aabcfe;}.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#aabcfe;color:#669;background-color:#e8edff;}.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#aabcfe;color:#039;background-color:#b9c9fe;}.tg .tg-0ord{text-align:right}.tg .tg-ifyx{background-color:#D2E4FC;text-align:right}.tg .tg-s6z2{text-align:center}.tg .tg-vn4c{background-color:#D2E4FC}tg .tg-sju8{background-color:#ce6301;color:#fe0000;text-align:right}</style>");
		tHtml.append("<table class=\"tg\"");

	}

	/**
	 * Generate level three header.
	 */
	public void generateLevelThreeHeader() {

		tHtml.append("<tr><th class=\"tg-s6z2\">Module </th><th> Test Number </th><th> Step Number </th><th> Description </th><th>Keyword Used</th><th>Value</th><th>Pass/Fail</th><th>Comments</th><th>Browser</th></tr>");
	}

	/**
	 * Generate level two header.
	 */
	public void generateLevelTwoHeader() {

		tHtml.append("<tr><th class=\"tg-s6z2\"> Module </th><th>Test case name </th><th>Descriptio </th><th>Time taken </th><th>Pass/Fail</th><th>Browser</th></tr>");
	}

	/**
	 * Generate level one header.
	 */
	public void generateLevelOneHeader() {

		tHtml.append("<tr><th class=\"tg-s6z2\">Total Number of test cases</th><th>Total Time taken</th><th>Total Passed</th><th>Total Failures</th></tr>");
	}

	/**
	 * Generate level three.
	 * 
	 * @param sModuleName
	 *            the s module name
	 * @param sTestName
	 *            the s test name
	 * @param sTestStep
	 *            the s test step
	 * @param sDescription
	 *            the s description
	 * @param sKeyword
	 *            the s keyword
	 * @param sValue
	 *            the s value
	 * @param bPass
	 *            the b pass
	 * @param sException
	 *            the s exception
	 * @param sBrowser
	 *            the s browser
	 */
	public void generateLevelThree(String sModuleName, String sTestName,String sTestStep, String sDescription, String sKeyword,	String sValue, Boolean bPass, String sException, String sBrowser) {
		tHtml.append("<tr>");
		tHtml.append("<td>" + sModuleName + "</td>");
		tHtml.append("<td>" + sTestName + "</td>");
	
		tHtml.append("<td>" + sTestStep + "</td>");
		tHtml.append("<td>" + sDescription + "</td>");
		tHtml.append("<td>" + sKeyword + "</td>");
		tHtml.append("<td>" + sValue + "</td>");
	
		if(bPass!=null)
		{
		if (bPass == false)
			{tHtml.append("<td bgcolor=\"#FF0000\">" + "FAIL" + "</td>");}
		else 
			{tHtml.append("<td>" + "PASS" + "</td>");}
		}
		else
		{tHtml.append("<td>" + " " + "</td>");}
		tHtml.append("<td>" + sException + "</td>");
		tHtml.append("<td>" + sBrowser + "</td>");
		tHtml.append("</tr>");
	

	}

	/**
	 * Generate level two.
	 * 
	 * @param sModuleName
	 *            the s module name
	 * @param sTestName
	 *            the s test name
	 * @param sDescription
	 *            the s description
	 * @param lTime
	 *            the l time
	 * @param bPass
	 *            the b pass
	 * @param sBrowser
	 *            the s browser
	 */
	public void generateLevelTwo(String sModuleName, String sTestName,
			String sDescription, Long lTime, Boolean bPass, String sBrowser) {

		tHtml.append("<tr>");
		tHtml.append("<td>" + sModuleName + "</td>");
		tHtml.append("<td>" + sTestName + "</td>");
		tHtml.append("<td>" + sDescription + "</td>");
		tHtml.append("<td>" + lTime / 1000 + "secs" + "</td>");
		if (!bPass) {
			tHtml.append("<td bgcolor=\"#FF0000\">" + "FAIL" + "</td>");
		} else {
			tHtml.append("<td>" + "PASS" + "</td>");
		}
		tHtml.append("<td>" + sBrowser + "</td>");
		tHtml.append("</tr>");

	}

/**
 * Generate level one.
 * 
 * @param iTotalNumber
 *            the i total number
 * @param lTime
 *            the l time
 * @param iFail
 *            the i fail
 */
public void generateLevelOne(int iTotalNumber, Long lTime,int iFail) 
{
	int iPass =iTotalNumber-iFail;
	tHtml.append("<tr>");
	tHtml.append("<td>" + iTotalNumber + "</td>");
	tHtml.append("<td>" + lTime / 1000 + "secs" + "</td>");
	tHtml.append("<td>" + iPass + "</td>");
	tHtml.append("<td>" + iFail + "</td>");
	tHtml.append("</tr>");
	
	
}
public String getReportOne() {

	
	tHtml.append("</body>" + "\n");
	tHtml.append("</html>" + "\n");
	return tHtml.toString();

}
	/**
	 * Gets the report.
	 * 
	 * @return the report
	 */
	public String getReport() {

	/*	tHtml.append("<a href=reportLevelThree.html> Detailed Report</a><br>" + "\n");
		tHtml.append("<a href=reportLevelTwo.html> Overview </a><br>" + "\n");
	*/	tHtml.append("</body>" + "\n");
		tHtml.append("</html>" + "\n");
		return tHtml.toString();

	}

}
