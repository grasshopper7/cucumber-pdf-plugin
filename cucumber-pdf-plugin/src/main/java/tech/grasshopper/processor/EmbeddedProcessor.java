package tech.grasshopper.processor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.iharder.Base64;
import tech.grasshopper.logging.CucumberPDFReportLogger;
import tech.grasshopper.pojo.Embedded;
import tech.grasshopper.properties.ReportProperties;

@Singleton
public class EmbeddedProcessor {

	private static final AtomicInteger EMBEDDED_INT = new AtomicInteger(0);

	@SuppressWarnings("serial")
	private static final Map<String, String> MIME_TYPES_EXTENSIONS = new HashMap<String, String>() {
		{
			put("image/bmp", "bmp");
			put("image/gif", "gif");
			put("image/jpeg", "jpg");
			put("image/png", "png");
			put("image/svg+xml", "svg");
			put("video/ogg", "ogg");
		}
	};

	private ReportProperties reportProperties;
	private CucumberPDFReportLogger logger;

	@Inject
	public EmbeddedProcessor(ReportProperties reportProperties, CucumberPDFReportLogger logger) {
		this.reportProperties = reportProperties;
		this.logger = logger;
	}

	public void processEmbedding(Embedded embedded) {
		String mimeType = embedded.getMimeType();
		String extension = MIME_TYPES_EXTENSIONS.get(mimeType);

		if (extension != null) {
			Path path = createEmbeddedFileStructure(extension);
			try {
				Files.write(path, Base64.decode(embedded.getData()));
			} catch (IOException e) {
				logger.warn("Skipping embedded file creation at location - " + path.toString()
						+ ", due to error in creating file.");
				return;
			} finally {
				// No need anymore
				embedded.setData("");
			}
			embedded.setFilePath(Paths
					.get(reportProperties.getReportScreenshotLocation(), path.getFileName().toString()).toString());
		} else {
			logger.warn("Mime type '" + mimeType + "' not supported.");
		}
	}

	private Path createEmbeddedFileStructure(String extension) {
		StringBuilder fileName = new StringBuilder("embedded").append(EMBEDDED_INT.incrementAndGet()).append(".")
				.append(extension);
		String embedDirPath = reportProperties.getReportScreenshotLocation();

		File dir = new File(embedDirPath);
		// Create directory if not existing
		if (!dir.exists())
			dir.mkdirs();

		Path path = Paths.get(embedDirPath, fileName.toString());
		return path;
	}
}