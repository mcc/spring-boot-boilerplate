package mcc.springbootboilerplate.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExcelUtils {

    public static SXSSFWorkbook toExcel(String sheetName, String[] fieldNames, List<Map<String, Object>> records) {
        SXSSFWorkbook workbook = new SXSSFWorkbook(1000);
        SXSSFSheet sheet = workbook.createSheet(sheetName);
        sheet.setRandomAccessWindowSize(100);
        int rowNum = 0;
        Row row = sheet.createRow(rowNum);
        Cell cell;
        CreationHelper createHelper = workbook.getCreationHelper();
        CellStyle dateFormatStyle = workbook.createCellStyle();
        dateFormatStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy"));
        CellStyle dpOneStyle = workbook.createCellStyle();
        dpOneStyle.setDataFormat(createHelper.createDataFormat().getFormat("0.0"));
        CellStyle dpTwoStyle = workbook.createCellStyle();
        dpTwoStyle.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
        CellStyle integerStyle = workbook.createCellStyle();
        integerStyle.setDataFormat(createHelper.createDataFormat().getFormat("0"));

        int cellNum = 0;
        for (String name : fieldNames) {
            cell = row.createCell(cellNum);
            cell.setCellValue(name);
            cellNum++;
        }
        int rows = 1;
        for (Map<String, Object> record : records) {
            row = sheet.createRow(rows);
            int col = 0;
            for (String key : fieldNames) {
                Object o = record.get(key);
                Cell rowCell = row.createCell(col);
                if (o instanceof BigDecimal || o instanceof Double) {
                    BigDecimal bd = null;
                    if (o instanceof Double) {
                        bd = new BigDecimal((Double) o);
                    } else {
                        bd = (BigDecimal) o;
                    }
                    bd = bd.setScale(2);
                    rowCell.setCellStyle(dpTwoStyle);
                    rowCell.setCellValue(bd.doubleValue());
                } else if (o instanceof Date) {
                    Date date = (Date) o;
                    Date truncate = DateUtils.truncate(date, Calendar.DATE);
                    rowCell.setCellValue(truncate);
                    rowCell.setCellStyle(dateFormatStyle);
                    double d = (rowCell.getNumericCellValue()); //get double value f
                    rowCell.setCellValue((int) d);  //get int value of the double
                } else {
                    if (o instanceof String) {
                        rowCell.setCellValue((String) o);
                    } else if (o instanceof Double) {
                        rowCell.setCellValue((Double) o);
                    } else if (o instanceof Long) {
                        rowCell.setCellValue((Long) o);
                    } else if (o instanceof Integer) {
                        rowCell.setCellValue((Integer) o);
                    } else if (o instanceof BigDecimal) {
                        rowCell.setCellValue((Double) ((BigDecimal) o).doubleValue());
                    } else if (o != null) {
                        rowCell.setCellValue(o.toString());
                    }
                }
                col++;
            }
            rows++;
        }
        return workbook;
    }
}
