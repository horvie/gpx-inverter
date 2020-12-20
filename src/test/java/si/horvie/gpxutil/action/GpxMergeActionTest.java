package si.horvie.gpxutil.action;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GpxMergeActionTest {

	@Test
	void testMerge() throws Exception {
		String outputFile = "target/output.gpx";

		List<String> responses = new GpxMergeAction("src/test/resources/input", outputFile).merge();
		Assertions.assertTrue(responses.isEmpty());

	}
}
