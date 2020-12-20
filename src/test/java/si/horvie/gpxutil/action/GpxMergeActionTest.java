package si.horvie.gpxutil.action;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GpxMergeActionTest {

	@Test
	void testMerge() {
		List<String> responses = new GpxMergeAction("src/test/resources/input", "target/output.gpx").merge();
		System.out.println(responses);
		Assertions.assertTrue(responses.isEmpty());
	}
}
