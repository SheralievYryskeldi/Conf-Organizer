import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    private Connection connection;
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "vn9282g++2";

    public DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database!");
            resetSequence();
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
    }

    private void resetSequence() {
        String sql = "ALTER SEQUENCE confs_id_seq RESTART WITH 1";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error resetting sequence: " + e.getMessage());
        }
    }

    public void addEvent(Event event) {
        if (!event.isValidInput()) {
            System.err.println("Invalid event data!");
            return;
        }
        String sql = "INSERT INTO confs (title, event_date, start_time, end_time, location, description) VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, event.getTitle());
            stmt.setObject(2, event.getEventDate());
            stmt.setString(3, event.getStartTime());
            stmt.setString(4, event.getEndTime());
            stmt.setString(5, event.getLocation());
            stmt.setString(6, event.getDescription());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                event.setId(rs.getInt("id"));
            }
            System.out.println("Event added successfully with ID: " + event.getId());
        } catch (SQLException e) {
            System.err.println("Error adding event: " + e.getMessage());
        }
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM confs ORDER BY id ASC";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Event event = new Event(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getObject("event_date", LocalDate.class),
                        rs.getString("start_time"),
                        rs.getString("end_time"),
                        rs.getString("location"),
                        rs.getString("description")
                );
                events.add(event);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching events: " + e.getMessage());
        }
        return events;
    }

    public void updateEvent(Event event) {
        if (!event.isValidInput()) {
            System.err.println("Invalid event data!");
            return;
        }
        String sql = "UPDATE confs SET title = ?, event_date = ?, start_time = ?, end_time = ?, location = ?, description = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, event.getTitle());
            stmt.setObject(2, event.getEventDate());
            stmt.setString(3, event.getStartTime());
            stmt.setString(4, event.getEndTime());
            stmt.setString(5, event.getLocation());
            stmt.setString(6, event.getDescription());
            stmt.setInt(7, event.getId());
            stmt.executeUpdate();
            System.out.println("Event updated successfully!");
        } catch (SQLException e) {
            System.err.println("Error updating event: " + e.getMessage());
        }
    }

    public void deleteEvent(int id) {
        String sql = "DELETE FROM confs WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Event deleted successfully!");
        } catch (SQLException e) {
            System.err.println("Error deleting event: " + e.getMessage());
        }
    }
}