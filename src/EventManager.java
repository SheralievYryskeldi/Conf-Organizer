import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private DatabaseConnection dbConnection;
    private List<Event> eventLog;

    public EventManager() {
        dbConnection = new DatabaseConnection();
        eventLog = new ArrayList<>();
        loadEvents();
    }

    private void loadEvents() {
        eventLog.addAll(dbConnection.getAllEvents());
    }

    public boolean addEvent(Event event) {
        if (!event.isValidInput()) {
            System.err.println("Invalid input! Please fill all fields correctly.");
            return false;
        }
        dbConnection.addEvent(event);
        eventLog.add(event);
        System.out.println("Added event: " + event);
        return true;
    }

    public List<Event> getAllEvents() {
        return new ArrayList<>(dbConnection.getAllEvents());
    }

    public boolean updateEvent(Event event) {
        if (!event.isValidInput()) {
            System.err.println("Invalid input! Please fill all fields correctly.");
            return false;
        }
        dbConnection.updateEvent(event);
        for (int i = 0; i < eventLog.size(); i++) {
            if (eventLog.get(i).getId() == event.getId()) {
                eventLog.set(i, event);
                break;
            }
        }
        System.out.println("Updated event: " + event);
        return true;
    }

    public void deleteEvent(int id) {
        dbConnection.deleteEvent(id);
        eventLog.removeIf(event -> event.getId() == id);
        System.out.println("Deleted event with ID: " + id);
    }

    public String generateReport() {
        int totalEvents = eventLog.size();
        StringBuilder report = new StringBuilder();
        report.append("Conference Schedule Report - ").append(LocalDate.now()).append("\n");
        report.append("Total Events: ").append(totalEvents).append("\n");
        if (!eventLog.isEmpty()) {
            report.append("Most Recent Event: ").append(eventLog.get(eventLog.size() - 1)).append("\n");
        }
        report.append("User Activity Log:\n");
        for (Event event : eventLog) {
            report.append("- ").append(event).append("\n");
        }
        return report.toString();
    }
}