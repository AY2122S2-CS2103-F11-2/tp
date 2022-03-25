package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.candidate.Candidate;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final String APPLICATION_STATUS_MSG = "Application Status : ";
    private static final String INTERVIEW_STATUS_MSG = "Interview Status : ";
    private static final String AVAILABILITY_MSG = "Availability: ";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Candidate candidate;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label course;
    @FXML
    private Label email;
    @FXML
    private Label applicationStatus;
    @FXML
    private Label interviewStatus;
    @FXML
    private Label availability;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane availableDays;
    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Candidate candidate, int displayedIndex) {
        super(FXML);
        this.candidate = candidate;
        id.setText(displayedIndex + ". ");
        name.setText(candidate.getName().fullName + ", " + candidate.getStudentId().studentId);
        phone.setText(candidate.getPhone().value);
        course.setText(candidate.getCourse().course);
        email.setText(candidate.getEmail().value);
        candidate.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        applicationStatus.setText(APPLICATION_STATUS_MSG + candidate.getApplicationStatus().toString());
        interviewStatus.setText(INTERVIEW_STATUS_MSG + candidate.getInterviewStatus().toString());
        availability.setText(AVAILABILITY_MSG);
        candidate.getAvailability().getList()
                .forEach(availability -> availableDays.getChildren().add(new Label(availability)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && candidate.equals(card.candidate);
    }
}
