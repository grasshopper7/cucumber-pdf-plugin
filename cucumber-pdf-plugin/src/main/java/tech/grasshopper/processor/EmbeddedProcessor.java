package tech.grasshopper.processor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.SneakyThrows;
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
				Files.write(path, Base64.getDecoder().decode(embedded.getData()));
			} catch (IOException e) {
				logger.warn("Displaying 'no image file' for location - " + path.toString()
						+ ", due to error in creating file.");

				path = createNoImageFoundFileStructure();
				return;
			} finally {
				// No need anymore
				embedded.setFilePath(path.toString());
				embedded.setData("");
			}
		} else {
			logger.warn("Mime type '" + mimeType + "' not supported.");
		}
	}

	@SneakyThrows
	private Path createNoImageFoundFileStructure() {
		String embedDirPath = reportProperties.getReportScreenshotLocation();
		Path path = Paths.get(embedDirPath, "not-found-image.png");

		if (path.toFile().exists())
			return path;

		File dir = new File(embedDirPath);
		// Create directory if not existing
		if (!dir.exists())
			dir.mkdirs();

		Files.write(path, Base64.getDecoder().decode(NoImageFile.BASE64_STR));
		return path;
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
