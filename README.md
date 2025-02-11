Project Structure Review:
.github/workflows/ci-automation.yml

This file is responsible for GitHub Actions CI/CD pipeline automation.
Ensure the YAML script correctly sets up dependencies and triggers builds/tests.
Logs & Reports:

logs/application.log: Logging system in place.
reports/extent-report.html: Using Extent Reports for test reporting.
Main Source Code (src/main/java)

pages/: Contains CenterLocatorPage, HomePage, and SearchResultsPage, indicating a Page Object Model (POM) implementation.
utilities/: Includes ExtentReportUtil for reporting and TestDataReader for data handling.
Resources (src/main/resources)

config/logback.xml: Logback configuration file.
Test Cases (test/java/tests)

BrightHorizonsSearchTest: Test class, likely validating search functionality.
CI/CD Integration

azure-pipelines.yml: Suggests Azure DevOps pipeline integration.
Miscellaneous

.gitignore: Manages ignored files.
pom.xml: Maven build configuration.
