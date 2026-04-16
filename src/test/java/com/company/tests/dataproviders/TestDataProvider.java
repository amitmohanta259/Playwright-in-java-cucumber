package com.company.tests.dataproviders;

import com.company.framework.config.FrameworkConstants;
import com.company.framework.utils.ExcelUtils;
import com.company.framework.utils.JsonUtils;
import org.testng.annotations.DataProvider;

import java.util.List;
import java.util.Map;

public class TestDataProvider {
    private final ExcelUtils excelUtils = new ExcelUtils();
    private final JsonUtils jsonUtils = new JsonUtils();

    @DataProvider(name = "loginExcelData")
    public Object[][] loginExcelData() {
        return excelUtils.readSheetAsDataProvider(FrameworkConstants.EXCEL_DATA_PATH, "Login");
    }

    @DataProvider(name = "loginJsonData")
    public Object[][] loginJsonData() {
        List<Map<String, String>> data = jsonUtils.readJsonAsListOfMaps(FrameworkConstants.JSON_DATA_PATH);
        Object[][] result = new Object[data.size()][1];
        for (int i = 0; i < data.size(); i++) {
            result[i][0] = data.get(i);
        }
        return result;
    }
}
