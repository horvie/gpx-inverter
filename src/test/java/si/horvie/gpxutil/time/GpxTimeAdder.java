package si.horvie.gpxutil.time;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class GpxTimeAdder {
  private static final String CONTENT_PATH = "src/main/resources";
  private static final DateTimeFormatter DF =
      DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

  public static void main(String[] args) throws Exception {
    String filename = "time.gpx";

    Document doc =
        DocumentHelper.parseText(
            new String(Files.readAllBytes(Paths.get(CONTENT_PATH + "/" + filename))));

    Element trkseg = doc.getRootElement().element("trk").element("trkseg");
    List<Element> trkpts = trkseg.elements();
    LocalDateTime time = LocalDateTime.of(2016, 8, 15, 12, 0);
    for (Element trkpt : trkpts) {
      trkpt.addElement("time").setText(DF.format(time));
      time = time.plusMinutes(1);
    }

    System.out.println(doc.asXML());
  }
}
