package si.horvie.gpxutil.command;

import java.util.List;

import picocli.CommandLine;
import si.horvie.gpxutil.action.GpxMergeAction;

@CommandLine.Command(name = "gpx-merge", version = "Version 1.0.0", mixinStandardHelpOptions = true, requiredOptionMarker = '*', showDefaultValues = true, sortOptions = false)
public class GpxMergeCommand implements Runnable {

	@CommandLine.Option(names = { "-i",
			"--input" }, description = "Location of data file or folder", required = true, order = 1)
	String inputFolder;

	@CommandLine.Option(names = { "-o",
			"--output" }, description = "Location of output folder", defaultValue = "merged.gpx", order = 2)
	String outputFile;

	@Override
	public void run() {
		List<String> responses = new GpxMergeAction(inputFolder, outputFile).merge();
		if (responses.isEmpty()) {
			System.out.println("Files successfully merged to '" + outputFile + "'");
		} else {
			System.out.println(
					"Files partially merged to '" + outputFile + "'. Problems:\n" + String.join("\n", responses));
		}
	}

}
