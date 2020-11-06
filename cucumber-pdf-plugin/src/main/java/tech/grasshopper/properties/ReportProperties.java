package tech.grasshopper.properties;

import javax.inject.Singleton;

@Singleton
public class ReportProperties {

	private boolean strictCucumber6Behavior;

	private static final String DEFAULT_SCREENSHOTS_LOCATION = "embedded";

	public String getReportScreenshotLocation() {
		return DEFAULT_SCREENSHOTS_LOCATION;
	}

	public boolean isStrictCucumber6Behavior() {
		return strictCucumber6Behavior;
	}

	public void setStrictCucumber6Behavior(String strictCucumber6Behavior) {
		this.strictCucumber6Behavior = Boolean.parseBoolean(strictCucumber6Behavior);
	}
}
