import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.List;

public class EventApp extends Application {
    private EventManager eventManager;
    private TableView<Event> eventTableView; // Changed to TableView
    private TextField titleField, startTimeField, endTimeField, locationField;
    private TextArea descriptionArea;
    private DatePicker datePicker;
    private int selectedEventId = -1;

    @Override
    public void start(Stage primaryStage) {
        eventManager = new EventManager();

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        eventTableView = new TableView<>();
        eventTableView.setPrefWidth(300);
        eventTableView.setPrefHeight(550);

        TableColumn<Event, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Event, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Event, LocalDate> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));

        TableColumn<Event, String> timeColumn = new TableColumn<>("Time");
        timeColumn.setCellValueFactory(cellData -> {
            Event event = cellData.getValue();
            return new SimpleStringProperty(event.getStartTime() + " - " + event.getEndTime());
        });

        eventTableView.getColumns().addAll(idColumn, titleColumn, dateColumn, timeColumn);
        refreshEventTable();

        eventTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedEventId = newSelection.getId();
                titleField.setText(newSelection.getTitle());
                datePicker.setValue(newSelection.getEventDate());
                startTimeField.setText(newSelection.getStartTime());
                endTimeField.setText(newSelection.getEndTime());
                locationField.setText(newSelection.getLocation());
                descriptionArea.setText(newSelection.getDescription());
            }
        });

        root.setLeft(eventTableView);

        VBox formBox = new VBox(10);
        formBox.setPadding(new Insets(10));
        formBox.setPrefWidth(450);

        titleField = new TextField();
        titleField.setPromptText("Event Title");

        datePicker = new DatePicker(LocalDate.now());
        datePicker.setPromptText("Select Date");
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.isBefore(today));
            }
        });

        startTimeField = new TextField();
        startTimeField.setPromptText("Start Time (HH:MM)");
        endTimeField = new TextField();
        endTimeField.setPromptText("End Time (HH:MM)");
        locationField = new TextField();
        locationField.setPromptText("Location");
        descriptionArea = new TextArea();
        descriptionArea.setPromptText("Description");
        descriptionArea.setPrefRowCount(5);

        Button addButton = new Button("Add Event");
        addButton.setOnAction(e -> {
            try {
                Event newEvent = new Event(0, titleField.getText(), datePicker.getValue(),
                        startTimeField.getText(), endTimeField.getText(),
                        locationField.getText(), descriptionArea.getText());
                if (eventManager.addEvent(newEvent)) {
                    refreshEventTable();
                    clearForm();
                } else {
                    showAlert("Invalid input! Check time format (HH:MM), ensure end time is after start time, and select a date on or after today.");
                }
            } catch (Exception ex) {
                showAlert("Error adding event: " + ex.getMessage());
            }
        });

        Button updateButton = new Button("Update Event");
        updateButton.setOnAction(e -> {
            try {
                if (selectedEventId != -1) {
                    Event updatedEvent = new Event(selectedEventId, titleField.getText(),
                            datePicker.getValue(), startTimeField.getText(), endTimeField.getText(),
                            locationField.getText(), descriptionArea.getText());
                    if (eventManager.updateEvent(updatedEvent)) {
                        refreshEventTable();
                        clearForm();
                    } else {
                        showAlert("Invalid input! Check time format (HH:MM), ensure end time is after start time, and select a date on or after today.");
                    }
                } else {
                    showAlert("Please select an event to update!");
                }
            } catch (Exception ex) {
                showAlert("Error updating event: " + ex.getMessage());
            }
        });

        Button deleteButton = new Button("Delete Event");
        deleteButton.setOnAction(e -> {
            try {
                if (selectedEventId != -1) {
                    eventManager.deleteEvent(selectedEventId);
                    refreshEventTable();
                    clearForm();
                } else {
                    showAlert("Please select an event to delete!");
                }
            } catch (Exception ex) {
                showAlert("Error deleting event: " + ex.getMessage());
            }
        });

        Button reportButton = new Button("Generate Report");
        reportButton.setOnAction(e -> {
            try {
                String report = eventManager.generateReport();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Report");
                alert.setHeaderText(null);
                alert.setContentText(report);
                alert.showAndWait();
            } catch (Exception ex) {
                showAlert("Error generating report: " + ex.getMessage());
            }
        });

        Button exportButton = new Button("Export Data");
        exportButton.setOnAction(e -> {
            try {
                List<Event> events = eventManager.getAllEvents();
                FileExporter.exportToCSV(events, "events.csv");
                FileExporter.exportToJSON(events, "events.json");
                showAlert("Data exported to events.csv and events.json successfully!");
            } catch (Exception ex) {
                showAlert("Error exporting data: " + ex.getMessage());
            }
        });

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(addButton, updateButton, deleteButton, reportButton, exportButton);

        formBox.getChildren().addAll(
                new Label("Title:"), titleField,
                new Label("Date:"), datePicker,
                new Label("Start Time:"), startTimeField,
                new Label("End Time:"), endTimeField,
                new Label("Location:"), locationField,
                new Label("Description:"), descriptionArea,
                buttonBox
        );
        root.setCenter(formBox);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Conference Schedule Organizer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void refreshEventTable() {
        try {
            eventTableView.getItems().clear();
            List<Event> events = eventManager.getAllEvents();
            eventTableView.getItems().addAll(events);
            selectedEventId = -1;
        } catch (Exception e) {
            showAlert("Error refreshing event table: " + e.getMessage());
        }
    }

    private void clearForm() {
        titleField.clear();
        datePicker.setValue(LocalDate.now());
        startTimeField.clear();
        endTimeField.clear();
        locationField.clear();
        descriptionArea.clear();
        selectedEventId = -1;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}