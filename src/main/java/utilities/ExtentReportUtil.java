package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportUtil {
    private static ExtentReports extent;
    private static ExtentTest test;


    // Initialize ExtentReports
    public static void initialize(String reportPath) {
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    // Start a test
    public static void startTest(String testName) {
        test = extent.createTest(testName);
    }

    // Log Info
    public static void logInfo(String message) {
        if (test != null) {
            test.info(message);
        }
    }

    // Log Pass
    public static void logPass(String message) {
        if (test != null) {
            test.pass(message);
        }
    }

    // Log Fail
    public static void logFail(String message) {
        if (test != null) {
            test.fail(message);
        }
    }

      // End test (optional)
    public static void endTest() {
        if (extent != null) {
            extent.flush();
        }
    }

    // Ensure final flush
    public static void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}
