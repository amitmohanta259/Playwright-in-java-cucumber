package com.company.framework.utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelUtils {
    public Object[][] readSheetAsDataProvider(String path, String sheetName) {
        try (FileInputStream stream = new FileInputStream(path);
             Workbook workbook = new XSSFWorkbook(stream)) {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null || sheet.getPhysicalNumberOfRows() <= 1) {
                return new Object[0][0];
            }

            List<Object[]> rows = new ArrayList<>();
            Iterator<Row> iterator = sheet.iterator();
            iterator.next();
            while (iterator.hasNext()) {
                Row row = iterator.next();
                rows.add(new Object[]{
                        row.getCell(0).getStringCellValue(),
                        row.getCell(1).getStringCellValue()
                });
            }
            return rows.toArray(new Object[0][]);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read Excel data from: " + path, exception);
        }
    }
}
