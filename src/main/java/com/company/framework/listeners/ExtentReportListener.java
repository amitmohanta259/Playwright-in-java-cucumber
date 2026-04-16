package com.company.framework.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.company.framework.config.FrameworkConstants;
import com.company.framework.utils.CommonUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExtentReportListener implements ITestListener {
    private ExtentReports extentReports;
    private final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private final Map<String, ExtentTest> testNodes = new ConcurrentHashMap<>();

    @Override
    public void onStart(ITestContext context) {
        String reportPath = FrameworkConstants.REPORT_DIR + "extent-" + CommonUtils.timestamp() + ".html";
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
    }

    @Override
    public void onTestStart(ITestResult result) {
        String scenarioName = extractScenarioName(result);
        String testKey = result.getMethod().getMethodName() + "_" + scenarioName;
        ExtentTest test = testNodes.computeIfAbsent(testKey, key -> extentReports.createTest(scenarioName));
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTest test = extentTest.get();
        if (test != null) {
            test.pass("Test passed");
        }
        extentTest.remove();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = extentTest.get();
        if (test != null) {
            test.fail(result.getThrowable());
        }
        extentTest.remove();
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extentReports != null) {
            extentReports.flush();
        }
    }

    private String extractScenarioName(ITestResult result) {
        Object[] parameters = result.getParameters();
        if (parameters != null && parameters.length > 0 && parameters[0] != null) {
            return parameters[0].toString();
        }
        return result.getMethod().getMethodName();
    }
}
