package tech.grasshopper;

import java.nio.file.Path;
import java.util.List;

import javax.inject.Inject;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import tech.grasshopper.data.PDFCucumberReportDataGenerator;
import tech.grasshopper.json.JsonFileConverter;
import tech.grasshopper.json.JsonPathCollector;
import tech.grasshopper.logging.CucumberPDFReportLogger;
import tech.grasshopper.pdf.PDFCucumberReport;
import tech.grasshopper.pdf.data.ReportData;
import tech.grasshopper.pdf.section.details.executable.MediaCleanup.CleanupType;
import tech.grasshopper.pdf.section.details.executable.MediaCleanup.MediaCleanupOption;
import tech.grasshopper.pojo.Feature;
import tech.grasshopper.processor.HierarchyProcessor;
import tech.grasshopper.properties.ReportProperties;

@Mojo(name = "pdfreport")
public class CucumberPDFReportPlugin extends AbstractMojo {

	@Parameter(property = "pdfreport.cucumberJsonReportDirectory", required = true)
	private String cucumberJsonReportDirectory;

	@Parameter(property = "pdfreport.cucumberPdfReportDirectory", /* required = true, */defaultValue = ReportProperties.REPORT_DIRECTORY)
	private String cucumberPdfReportDirectory;

	@Parameter(property = "pdfreport.reportDirectoryTimeStamp", defaultValue = ReportProperties.REPORT_DIRECTORY_TIMESTAMP)
	private String cucumberPdfReportDirectoryTimeStamp;

	@Parameter(property = "pdfreport.strictCucumber6Behavior", defaultValue = "true")
	private String strictCucumber6Behavior;

	private JsonPathCollector jsonPathCollector;
	private JsonFileConverter jsonFileConverter;
	private ReportProperties reportProperties;
	private HierarchyProcessor hierarchyProcessor;
	private CucumberPDFReportLogger logger;

	@Inject
	public CucumberPDFReportPlugin(JsonPathCollector jsonPathCollector, JsonFileConverter jsonFileConverter,
			ReportProperties reportProperties, HierarchyProcessor hierarchyProcessor, CucumberPDFReportLogger logger) {
		this.jsonPathCollector = jsonPathCollector;
		this.jsonFileConverter = jsonFileConverter;
		this.reportProperties = reportProperties;
		this.hierarchyProcessor = hierarchyProcessor;
		this.logger = logger;
	}

	public void execute() {
		try {
			logger.initializeLogger(getLog());
			logger.info("STARTED CUCUMBER PDF REPORT GENERATION PLUGIN");

			reportProperties.setStrictCucumber6Behavior(strictCucumber6Behavior);
			reportProperties.setReportDirectory(cucumberPdfReportDirectory, cucumberPdfReportDirectoryTimeStamp);

			List<Path> jsonFilePaths = jsonPathCollector.retrieveFilePaths(cucumberJsonReportDirectory);
			List<Feature> features = jsonFileConverter.retrieveFeaturesFromReport(jsonFilePaths);
			hierarchyProcessor.process(features);

			PDFCucumberReportDataGenerator generator = new PDFCucumberReportDataGenerator();
			ReportData reportData = generator.generateReportData(features);

			PDFCucumberReport pdfCucumberReport = new PDFCucumberReport(reportData,
					reportProperties.getReportDirectory(),
					MediaCleanupOption.builder().cleanUpType(CleanupType.ALL).build());
			pdfCucumberReport.createReport();

			logger.info("FINISHED CUCUMBER PDF REPORT GENERATION PLUGIN");
		} catch (Throwable t) {
			// Report will not result in build failure.
			t.printStackTrace();
			logger.error(String.format("STOPPING CUCUMBER PDF REPORT GENERATION - %s", t.getMessage()));
		}
	}
}
