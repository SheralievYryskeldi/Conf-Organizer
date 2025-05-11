import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class FileExporter {
    public static void exportToCSV(List<Event> events, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("ID,Title,Date,Start Time,End Time,Location,Description\n");
            for (Event event : events) {
                writer.append(String.format("%d,%s,%s,%s,%s,%s,%s\n",
                        event.getId(),
                        event.getTitle().replace(",", ""),
                        event.getEventDate().toString(),
                        event.getStartTime(),
                        event.getEndTime(),
                        event.getLocation().replace(",", ""),
                        event.getDescription().replace(",", "")
                ));
            }
            System.out.println("CSV file exported successfully to " + filePath);
        } catch (IOException e) {
            System.err.println("Error exporting to CSV: " + e.getMessage());
        }
    }

    public static void exportToJSON(List<Event> events, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            JSONArray jsonArray = new JSONArray();
            for (Event event : events) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", event.getId());
                jsonObject.put("title", event.getTitle());
                jsonObject.put("date", event.getEventDate().toString());
                jsonObject.put("startTime", event.getStartTime());
                jsonObject.put("endTime", event.getEndTime());
                jsonObject.put("location", event.getLocation());
                jsonObject.put("description", event.getDescription());
                jsonArray.put(jsonObject);
            }
            writer.write(jsonArray.toString(4));
            System.out.println("JSON file exported successfully to " + filePath);
        } catch (IOException e) {
            System.err.println("Error exporting to JSON: " + e.getMessage());
        }
    }
}