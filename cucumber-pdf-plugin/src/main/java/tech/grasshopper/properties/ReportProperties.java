package tech.grasshopper.properties;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.inject.Inject;
import javax.inject.Singleton;

import tech.grasshopper.logging.CucumberPDFReportLogger;

@Singleton
public class ReportProperties {

	private boolean strictCucumber6Behavior;

	private String reportDirectory;

	private static final String DEFAULT_SCREENSHOTS_LOCATION = "embedded";
	public static final String REPORT_DIRECTORY = "report";
	public static final String REPORT_DIRECTORY_TIMESTAMP = "dd MM yyyy HH mm ss";

	private CucumberPDFReportLogger logger;

	@Inject
	public ReportProperties(CucumberPDFReportLogger logger) {
		this.logger = logger;
	}

	public String getReportScreenshotLocation() {
		return DEFAULT_SCREENSHOTS_LOCATION;
	}

	public boolean isStrictCucumber6Behavior() {
		return strictCucumber6Behavior;
	}

	public String getReportDirectory() {
		return reportDirectory;
	}

	public void setStrictCucumber6Behavior(String strictCucumber6Behavior) {
		this.strictCucumber6Behavior = Boolean.parseBoolean(strictCucumber6Behavior);
	}

	public void setReportDirectory(String reportDirectory, String reportDirectoryTimeStamp) {
		if (reportDirectoryTimeStamp == null || "null".equals(reportDirectoryTimeStamp)) {
			this.reportDirectory = reportDirectory;
		}
		else {
			DateTimeFormatter timeStampFormat = null;
			String timeStampStr = "";

			try {
				timeStampFormat = DateTimeFormatter.ofPattern(reportDirectoryTimeStamp);
			} catch (Exception e) {
				logger.info(
						"Unable to process supplied date time format pattern. Creating report with default directory timestamp settings.");

				timeStampFormat = DateTimeFormatter.ofPattern(REPORT_DIRECTORY_TIMESTAMP);
			} finally {
				timeStampStr = timeStampFormat.format(LocalDateTime.now());
			}

			this.reportDirectory = reportDirectory + " " + timeStampStr;
		}
	}
}
