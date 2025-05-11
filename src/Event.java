import java.time.LocalDate;

public class Event {
    private int id;
    private String title;
    private LocalDate eventDate;
    private String startTime;
    private String endTime;
    private String location;
    private String description;

    public Event(int id, String title, LocalDate eventDate, String startTime, String endTime, String location, String description) {
        this.id = id;
        this.title = title;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.description = description;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public LocalDate getEventDate() { return eventDate; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }
    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isValidTime() {
        String timePattern = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$";
        if (startTime == null || endTime == null || !startTime.matches(timePattern) || !endTime.matches(timePattern)) {
            return false;
        }
        String[] startParts = startTime.split(":");
        String[] endParts = endTime.split(":");
        try {
            int startHours = Integer.parseInt(startParts[0]);
            int startMinutes = Integer.parseInt(startParts[1]);
            int endHours = Integer.parseInt(endParts[0]);
            int endMinutes = Integer.parseInt(endParts[1]);
            int startTotalMinutes = startHours * 60 + startMinutes;
            int endTotalMinutes = endHours * 60 + endMinutes;
            return endTotalMinutes > startTotalMinutes;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isValidInput() {
        LocalDate today = LocalDate.now();
        return title != null && !title.trim().isEmpty() &&
                location != null && !location.trim().isEmpty() &&
                description != null && !description.trim().isEmpty() &&
                eventDate != null && !eventDate.isBefore(today) &&
                isValidTime();
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Title: " + title + ", Date: " + eventDate +
                ", Time: " + startTime + " - " + endTime;
    }
}