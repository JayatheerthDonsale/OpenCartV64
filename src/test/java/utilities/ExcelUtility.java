package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ExcelUtility class provides basic operations for reading and writing Excel (.xlsx) files
 * using Apache POI.
 *
 * Features:
 * - Get row count
 * - Get cell count
 * - Read cell data
 * - Write cell data
 * - Fill a cell with green or red background colour (useful for marking pass/fail)
 *
 * Usage:
 *   ExcelUtility excel = new ExcelUtility("TestData.xlsx");
 *   int rows = excel.getRowCount("Sheet1");
 *   String value = excel.getCellData("Sheet1", 1, 0);
 *   excel.setCellData("Sheet1", 1, 2, "Pass");
 *   excel.fillGreenColour("Sheet1", 1, 2);
 *   excel.close();
 */
public class ExcelUtility {

    private String filePath;          // Path to the Excel file
    private FileInputStream fis;      // Input stream for reading
    private FileOutputStream fos;     // Output stream for writing
    private XSSFWorkbook workbook;    // Excel workbook object
    private XSSFSheet sheet;          // Current sheet object
    private Row row;                  // Current row object
    private Cell cell;                // Current cell object

    /**
     * Constructor: Opens the given Excel file.
     * @param filePath Path to the Excel file (.xlsx)
     */
    public ExcelUtility(String filePath) throws IOException {
        this.filePath = filePath;
        fis = new FileInputStream(filePath);
        workbook = new XSSFWorkbook(fis);
        fis.close();
    }

    /**
     * Get total number of rows in the given sheet.
     * @param sheetName Name of the sheet
     * @return Number of rows
     */
    public int getRowCount(String sheetName) {
        sheet = workbook.getSheet(sheetName);
        return sheet.getLastRowNum(); // last row index (0-based)
    }

    /**
     * Get total number of cells (columns) in a particular row.
     * @param sheetName Name of the sheet
     * @param rowIndex Row index (0-based)
     * @return Number of cells in the row
     */
    public int getCellCount(String sheetName, int rowIndex) {
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rowIndex);
        return row.getLastCellNum(); // last cell number (1-based)
    }

    /**
     * Get the data of a cell as a String, handling different data types.
     * @param sheetName Name of the sheet
     * @param rowIndex Row index (0-based)
     * @param colIndex Column index (0-based)
     * @return Cell value as String
     */
    public String getCellData(String sheetName, int rowIndex, int colIndex) {
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rowIndex);
        cell = row.getCell(colIndex);

        DataFormatter formatter = new DataFormatter();
        String data;
        try {
            data = formatter.formatCellValue(cell); // handles numeric, string, boolean
        } catch (Exception e) {
            data = ""; // return empty if error/null
        }
        return data;
    }

    /**
     * Write data to a cell. Creates the row/cell if not already present.
     * @param sheetName Name of the sheet
     * @param rowIndex Row index (0-based)
     * @param colIndex Column index (0-based)
     * @param data Value to write
     */
    public void setCellData(String sheetName, int rowIndex, int colIndex, String data) throws IOException {
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rowIndex);
        if (row == null) row = sheet.createRow(rowIndex);
        cell = row.getCell(colIndex);
        if (cell == null) cell = row.createCell(colIndex);
        cell.setCellValue(data);

        fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
    }

    /**
     * Fill a cell with green colour (commonly used to mark Pass).
     * @param sheetName Name of the sheet
     * @param rowIndex Row index (0-based)
     * @param colIndex Column index (0-based)
     */
    public void fillGreenColour(String sheetName, int rowIndex, int colIndex) throws IOException {
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rowIndex);
        cell = row.getCell(colIndex);

        XSSFCellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(new XSSFColor(Color.GREEN, null));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell.setCellStyle(style);

        fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
    }

    /**
     * Fill a cell with red colour (commonly used to mark Fail).
     * @param sheetName Name of the sheet
     * @param rowIndex Row index (0-based)
     * @param colIndex Column index (0-based)
     */
    public void fillRedColour(String sheetName, int rowIndex, int colIndex) throws IOException {
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rowIndex);
        cell = row.getCell(colIndex);

        XSSFCellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(new XSSFColor(Color.RED, null));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell.setCellStyle(style);

        fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
    }

    /**
     * Close the workbook and release resources.
     */
    public void close() throws IOException {
        workbook.close();
    }
}
