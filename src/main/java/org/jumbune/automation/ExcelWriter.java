/*
 * 
 */
package org.jumbune.automation;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFColor.AUTOMATIC;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.sun.jna.platform.win32.WinBase.SYSTEMTIME;

// TODO: Auto-generated Javadoc
/**
 * The Class ExcelWriter.
 */
@SuppressWarnings("unused")
public class ExcelWriter {

	/** The excel writer logger. */
	private static Logger excelWriterLogger = Logger.getLogger(ExcelWriter.class);
	
	/** The s result file name. */
	private String sResultFileName;
	
	/** The hwb. */
	private HSSFWorkbook hwb;
	
	/** The sheet. */
	private HSSFSheet sheet;
	
	/** The row cnt. */
	private int rowCnt;
	
	/** The testcase cnt. */
	private int testCaseCnt;
	
	/** The date format. */
	DateFormat dateFormat = new SimpleDateFormat(AutomationConstants.DATE_FORMAT_EXCEL);
	
	/** The date. */
	Date date = new Date();
	
	/**
	 * Instantiates a new excel writer.
	 * 
	 * @param sModuleName
	 *            the s module name
	 */
	ExcelWriter(String sModuleName) {
		String[] cutname=sModuleName.split(AutomationConstants.DOT_XLS);
		excelWriterLogger.setLevel(Level.DEBUG);
		SimpleDateFormat dateFormatFile = new SimpleDateFormat ("E_dd_MM_yy_hh_mm");
	
		String sFileName =cutname[0]+dateFormatFile.format(date)+AutomationConstants.DOT_XLS;
		
		this.sResultFileName = System.getenv(AutomationConstants.AUTOMATION_HOME)+AutomationConstants.FILE_SEPARATOR+AutomationConstants.RESULTS+AutomationConstants.FILE_SEPARATOR+sFileName;
		System.out.println(this.sResultFileName);
		this.hwb = new HSSFWorkbook();
		this.sheet = hwb.createSheet("Results");
		this.rowCnt = AutomationConstants.ZERO;
		this.testCaseCnt = AutomationConstants.ONE;
	}

	/**
	 * Creates the sheet.
	 */
	public void createSheet() {
		try {
			HSSFRow rowhead = sheet.createRow((short) this.rowCnt);
			HSSFCellStyle cellStyle = this.hwb.createCellStyle();
			cellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
			HSSFCell cellA0 = rowhead.createCell(AutomationConstants.ZERO);
			HSSFCell cellA1 = rowhead.createCell(AutomationConstants.ONE);
			HSSFCell cellA2 = rowhead.createCell(AutomationConstants.TWO);
			HSSFCell cellA3 = rowhead.createCell(AutomationConstants.THREE);
			HSSFCell cellA4 = rowhead.createCell(AutomationConstants.FOUR);
			HSSFCell cellA5 = rowhead.createCell(AutomationConstants.FIVE);
			HSSFCell cellA6 = rowhead.createCell(AutomationConstants.SIX);
			HSSFCell cellA7 = rowhead.createCell(AutomationConstants.SEVEN);
			HSSFCell cellA8 = rowhead.createCell(AutomationConstants.EIGHT);

			cellA0.setCellStyle(cellStyle);
			cellA1.setCellStyle(cellStyle);
			cellA2.setCellStyle(cellStyle);
			cellA3.setCellStyle(cellStyle);
			cellA4.setCellStyle(cellStyle);
			cellA5.setCellStyle(cellStyle);
			cellA6.setCellStyle(cellStyle);
			cellA7.setCellStyle(cellStyle);
			cellA8.setCellStyle(cellStyle);

			cellA0.setCellValue(new HSSFRichTextString("#"));
			cellA1.setCellValue(new HSSFRichTextString("Test Case"));
			cellA2.setCellValue(new HSSFRichTextString("Test Step"));
			cellA3.setCellValue(new HSSFRichTextString("Date & Time"));
			cellA4.setCellValue(new HSSFRichTextString("Description"));
			cellA5.setCellValue(new HSSFRichTextString("Action"));
			cellA6.setCellValue(new HSSFRichTextString("Value"));
			cellA7.setCellValue(new HSSFRichTextString("Pass/Fail"));
		

			FileOutputStream fileOut = new FileOutputStream(this.sResultFileName);
			
			this.hwb.write(fileOut);
			fileOut.close();
			excelWriterLogger.debug("Excel file has been generated and Header is written" + this.sResultFileName.toString());

		} catch (Exception ex) {
			excelWriterLogger.debug(ex);
		}
	}

	/**
	 * Insert row.
	 * 
	 * @param sTestCase
	 *            the s test case
	 * @param sTestStep
	 *            the s test step
	 * @param sDescription
	 *            the s description
	 * @param sAction
	 *            the s action
	 * @param sValue
	 *            the s value
	 * @param bPass
	 *            the b pass
	 * @return the int
	 */
	public int InsertRow(String sTestCase, String sTestStep,
			String sDescription, String sAction, String sValue, boolean bPass) {
		try {
			this.rowCnt++;
			HSSFRow row = sheet.createRow((short) this.rowCnt);

			if (sTestCase == null) {
				row.createCell(2).setCellValue(
						new HSSFRichTextString(sTestStep));
				excelWriterLogger.debug(dateFormat.format(date));
				//System.out.println(dateFormat.format(date));
				row.createCell(3).setCellValue(
						new HSSFRichTextString(dateFormat.format(date)));
				row.createCell(4).setCellValue(
						new HSSFRichTextString(sDescription));
				row.createCell(5).setCellValue(new HSSFRichTextString(sAction));
				row.createCell(6).setCellValue(new HSSFRichTextString(sValue));

				HSSFCellStyle cellStyle = this.hwb.createCellStyle();
				HSSFCell cellA1 = row.createCell(7);

				if (bPass) {
					cellA1.setCellValue(new HSSFRichTextString(AutomationConstants.PASS));
				} else {
					cellA1.setCellValue(new HSSFRichTextString(AutomationConstants.FAIL));
				}

			} else {
				HSSFCellStyle cellStyle = this.hwb.createCellStyle();
				HSSFCell cellA0 = row.createCell(AutomationConstants.ZERO);
				HSSFCell cellA1 = row.createCell(AutomationConstants.ONE);
				HSSFCell cellA2 = row.createCell(AutomationConstants.TWO);

				cellA0.setCellStyle(cellStyle);
				cellA0.setCellValue(this.testCaseCnt);
				cellA1.setCellStyle(cellStyle);
				cellA1.setCellValue(new HSSFRichTextString(sTestCase));
				cellA2.setCellStyle(cellStyle);
				cellA2.setCellValue(new HSSFRichTextString(sDescription));
				this.testCaseCnt++;
			}
			FileOutputStream fileOut = new FileOutputStream(
					this.sResultFileName);
			this.hwb.write(fileOut);
			fileOut.flush();
			fileOut.close();
			excelWriterLogger.debug("A Row has been inserted");
			return this.rowCnt;
		} catch (Exception ex) {
			excelWriterLogger.error(ex);
			return this.rowCnt;
		}
	}
}
