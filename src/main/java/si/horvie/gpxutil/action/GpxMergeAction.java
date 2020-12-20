package si.horvie.gpxutil.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class GpxMergeAction {
	private static final String STUB = "<gpx><trk><trkseg></trkseg></trk></gpx>";
	private static final String REVERSE_MARK = "r.";

	private final String inputFolder;
	private final String outputFile;

	public GpxMergeAction(String inputFolder, String outputFile) {
		this.inputFolder = inputFolder;
		this.outputFile = outputFile;
	}

	public List<String> merge() {
		Path inputPath = Paths.get(inputFolder);
		File inputFile = inputPath.toFile();

		if (!inputFile.exists()) {
			return List.of("Input folder doesn't exist: " + inputFolder);
		}
		if (!inputFile.isDirectory()) {
			return List.of("Input folder isn't a directory: " + inputFolder);
		}

		List<String> responses = new ArrayList<>();

		List<String> fileList = extractFiles(inputFile);
		Document doc;
		try {
			doc = DocumentHelper.parseText(STUB);
		} catch (DocumentException e) {
			// this should not happen (stub is correct)
			throw new IllegalArgumentException(e.getMessage(), e);
		}

		fileList.sort(String.CASE_INSENSITIVE_ORDER);
		fileList.forEach(f -> {
			try {
				this.add(f, doc);
			} catch (Exception e) {
				responses.add("Problem adding '" + f + "': " + e.getMessage());
			}
		});

		try {
			store(doc);
		} catch (IOException e) {
			responses.add("Problem storig merged file '" + outputFile + "': " + e.getMessage());
		}

		return responses;
	}

	private List<String> extractFiles(File inputFile) {
		List<String> fileList;
		String[] inputFileList = inputFile.list();
		if (inputFileList != null) {
			fileList = Arrays.asList(inputFileList);
			fileList.removeIf(f -> new File(f).isDirectory());
			fileList = fileList.stream().map(f -> inputFolder + File.separator + f).collect(Collectors.toList());
		} else {
			fileList = Collections.emptyList();
		}

		return fileList;
	}

	private void add(String gpxFile, Document doc) throws Exception {
		try (FileInputStream content = new FileInputStream(gpxFile)) {
			Document gpx = new SAXReader().read(content);

			List<Element> trkpts = gpx.getRootElement().element("trk").element("trkseg").elements();

			if (gpxFile.contains(REVERSE_MARK)) {
				Collections.reverse(trkpts);
			}

			trkpts.forEach(el -> doc.add((Element) el.detach()));
		}
	}

	private void store(Document doc) throws IOException {
		Path path = Paths.get(outputFile);
		byte[] outputBytes = doc.asXML().getBytes(StandardCharsets.UTF_8);
		Files.write(path, outputBytes);
	}
}
