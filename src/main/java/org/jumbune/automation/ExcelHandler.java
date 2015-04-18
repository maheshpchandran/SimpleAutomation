/*
 * 
 */
package org.jumbune.automation;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

// TODO: Auto-generated Javadoc
/**
 * The Class ExcelHandler.
 */
@SuppressWarnings("unused")
public class ExcelHandler {
	
	/** The sheet. */
	Sheet sheet;
	
	/** The book. */
	Workbook book =null;
	
	/** The dict. */
	@SuppressWarnings("rawtypes")
	Hashtable dict= new Hashtable();
	
	/**
	 * Instantiates a new excel handler.
	 * 
	 * @param ExcelSheetPath
	 *            the excel sheet path
	 * @param TableName
	 *            the table name
	 * @throws BiffException
	 *             the biff exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public ExcelHandler(String ExcelSheetPath, String TableName) throws BiffException, IOException
	{
		
		book = Workbook.getWorkbook(new File(ExcelSheetPath));
		sheet = book.getSheet(TableName);
	}
	
	//Returns the Number of Rows
	/**
	 * Row count.
	 * 
	 * @return the int
	 */
	public int RowCount()
	{
		return sheet.getRows();	
	}

	//Returns the Number of Columns
	/**
	 * Column count.
	 * 
	 * @return the int
	 */
	public int ColumnCount()
	{
		return sheet.getColumns();
	}
	
	//Returns the Cell value by taking row and Column values as argument
	/**
	 * Read cell.
	 * 
	 * @param column
	 *            the column
	 * @param row
	 *            the row
	 * @return the string
	 */
	public String ReadCell(int column,int row)
	{
		return sheet.getCell(column,row).getContents();
	}
	
	//Create Column Dictionary to hold all the Column Names
	/**
	 * Column data.
	 */
	@SuppressWarnings("unchecked")
	public void ColumnData()
	{
		//Iterate through all the columns in the Excel sheet and store the value in Hashtable
		for(int col=0;col < sheet.getColumns();col++)
		{
			dict.put(ReadCell(col,0), col);
		}
	}
	
	//Create Column Dictionary to hold all the Column Names
	/**
	 * Row data.
	 */
	@SuppressWarnings("unchecked")
	public void RowData()
	{
		//Iterate through all the columns in the Excel sheet and store the value in Hashtable
		for(int row=0; row < sheet.getRows(); row++)
		{
			dict.put(ReadCell(0, row), row);
		}
	}
	
	//Read Column Names
	/**
	 * Gets the cell.
	 * 
	 * @param colName
	 *            the col name
	 * @return the int
	 */
	public int GetCell(String colName)
	{
		try {
			int value;
			value = ((Integer) dict.get(colName)).intValue();
			return value;
		} catch (NullPointerException e) {
			return (0);

		}
	}
}


