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
import tech.grasshopper.pdf.config.ParameterConfig;
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

	@Parameter(property = "pdfreport.title")
	private String title;

	@Parameter(property = "pdfreport.titleColor")
	private String titleColor;

	@Parameter(property = "pdfreport.passColor")
	private String passColor;

	@Parameter(property = "pdfreport.failColor")
	private String failColor;

	@Parameter(property = "pdfreport.skipColor")
	private String skipColor;

	@Parameter(property = "pdfreport.displayFeature")
	private String displayFeature;

	@Parameter(property = "pdfreport.displayScenario")
	private String displayScenario;

	@Parameter(property = "pdfreport.displayDetailed")
	private String displayDetailed;

	@Parameter(property = "pdfreport.displayExpanded")
	private String displayExpanded;

	@Parameter(property = "pdfreport.displayAttached")
	private String displayAttached;

	@Parameter(property = "pdfreport.skipHooks")
	private String skipHooks;

	@Parameter(property = "pdfreport.skipScenarioHooks")
	private String skipScenarioHooks;

	@Parameter(property = "pdfreport.skipStepHooks")
	private String skipStepHooks;

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

			ParameterConfig parameterConfig = ParameterConfig.builder().title(title).titleColor(titleColor)
					.passColor(passColor).failColor(failColor).skipColor(skipColor).displayFeature(displayFeature)
					.displayScenario(displayScenario).displayDetailed(displayDetailed).displayExpanded(displayExpanded)
					.displayAttached(displayAttached).skipHooks(skipHooks).skipScenarioHooks(skipScenarioHooks)
					.skipStepHooks(skipStepHooks).build();

			PDFCucumberReport pdfCucumberReport = new PDFCucumberReport(reportData,
					reportProperties.getReportDirectory(),
					MediaCleanupOption.builder().cleanUpType(CleanupType.ALL).build());
			pdfCucumberReport.setParameterConfig(parameterConfig);
			pdfCucumberReport.createReport();

			logger.info("FINISHED CUCUMBER PDF REPORT GENERATION PLUGIN");
		} catch (Throwable t) {
			// Report will not result in build failure.
			t.printStackTrace();
			logger.error(String.format("STOPPING CUCUMBER PDF REPORT GENERATION - %s", t.getMessage()));
		}
	}
}
