package com.company.framework.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.company.framework.config.FrameworkConstants;
import com.company.framework.utils.CommonUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.HashMap;
import java.util.Map;

public class ExtentReportListener implements ITestListener {
    private ExtentReports extentReports;
    private final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private final Map<String, ExtentTest> testNodes = new HashMap<>();

    @Override
    public void onStart(ITestContext context) {
        String reportPath = FrameworkConstants.REPORT_DIR + "extent-" + CommonUtils.timestamp() + ".html";
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = testNodes.computeIfAbsent(result.getMethod().getMethodName(), extentReports::createTest);
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().pass("Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().fail(result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extentReports != null) {
            extentReports.flush();
        }
    }
}
