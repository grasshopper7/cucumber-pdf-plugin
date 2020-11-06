package tech.grasshopper.exception;

public class CucumberPDFReportPluginException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CucumberPDFReportPluginException(String message) {
		super(message);
	}

	public CucumberPDFReportPluginException(String message, Exception exception) {
		super(message, exception);
	}
}
