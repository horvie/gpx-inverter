package si.horvie.gpxinverter;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class GpxInverter {
  private static final String CONTENT_PATH = "src/main/resources";

  public static void main(String[] args) throws Exception {
    String filename = "invert.gpx";

    Document doc =
        DocumentHelper.parseText(
            new String(Files.readAllBytes(Paths.get(CONTENT_PATH + "/" + filename))));

    Element trkseg = doc.getRootElement().element("trk").element("trkseg");
    List<Element> newEls = new ArrayList<>();
    List<Element> els = trkseg.elements();
    els.forEach(el -> newEls.add((Element) el.detach()));

    Collections.reverse(newEls);
    newEls.forEach(el -> trkseg.add(el));

    System.out.println(doc.asXML());
  }
}
