package seedu.address.ui;

import static seedu.address.model.candidate.ApplicationStatus.ACCEPTED_STATUS;
import static seedu.address.model.candidate.ApplicationStatus.PENDING_STATUS;
import static seedu.address.model.candidate.ApplicationStatus.REJECTED_STATUS;
import static seedu.address.model.candidate.InterviewStatus.COMPLETED;
import static seedu.address.model.candidate.InterviewStatus.NOT_SCHEDULED;
import static seedu.address.model.candidate.InterviewStatus.SCHEDULED;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.candidate.ApplicationStatus;
import seedu.address.model.candidate.Availability;
import seedu.address.model.candidate.Candidate;
import seedu.address.model.candidate.InterviewStatus;

/**
 * An UI component that displays information of a {@code Candidate}.
 */
public class CandidateCard extends UiPart<Region> {

    // Misc
    private static final String FXML = "CandidateListCard.fxml";

    // UI Text
    private static final String APPLICATION_STATUS_MSG = "Application Status : ";
    private static final String INTERVIEW_STATUS_MSG = "Interview Status : ";
    private static final String AVAILABILITY_MSG = "Availability: ";
    private static final String SENIORITY_VALUE = "COM";

    // CSS
    private static final String RED = "#800000";
    private static final String GREEN = "#006100";
    private static final String YELLOW = "#CBA92B";
    private static final String GREY = "#808080";
    private static final String BRIGHT_GREEN = "#4BB11F";
    private static final String CHANGE_COLOUR = "-fx-background-color: ";

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
    private FlowPane statusPane;
    @FXML
    private FlowPane availableDays;
    /**
     * Creates a {@code CandidateCode} with the given {@code Candidate} and index to display.
     */
    public CandidateCard(Candidate candidate, int displayedIndex) {
        super(FXML);
        this.candidate = candidate;
        id.setText(displayedIndex + ". ");
        name.setText(candidate.getName().fullName + ", " + candidate.getStudentId().studentId);
        phone.setText(candidate.getPhone().value);
        course.setText(candidate.getCourse().course + ", " + SENIORITY_VALUE + candidate.getSeniority().seniority);
        email.setText(candidate.getEmail().value);
        applicationStatus.setText(APPLICATION_STATUS_MSG + candidate.getApplicationStatus().toString());
        interviewStatus.setText(INTERVIEW_STATUS_MSG + candidate.getInterviewStatus().toString());
        availability.setText(AVAILABILITY_MSG);

        setAvailableDays(candidate.getAvailability());
        setApplicationStatus(candidate.getApplicationStatus());
        setInterviewStatus(candidate.getInterviewStatus());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CandidateCard)) {
            return false;
        }

        // state check
        CandidateCard card = (CandidateCard) other;
        return id.getText().equals(card.id.getText())
                && candidate.equals(card.candidate);
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        String applicationString = applicationStatus.toString();
        Label applicationLabel = new Label(applicationString);

        if (applicationString.equals(REJECTED_STATUS)) {
            applicationLabel.setStyle(CHANGE_COLOUR + RED);
            statusPane.getChildren().add(applicationLabel);
        } else if (applicationString.equals(ACCEPTED_STATUS)) {
            applicationLabel.setStyle(CHANGE_COLOUR + GREEN);
            statusPane.getChildren().add(applicationLabel);
        } else if (applicationString.equals(PENDING_STATUS)) {
            applicationLabel.setStyle(CHANGE_COLOUR + YELLOW);
            statusPane.getChildren().add(applicationLabel);
        }
    }

    public void setInterviewStatus(InterviewStatus interviewStatus) {
        String interviewString = interviewStatus.toString();
        Label interviewLabel = new Label(interviewString);

        if (interviewString.equals(NOT_SCHEDULED)) {
            interviewLabel.setStyle(CHANGE_COLOUR + RED);
            statusPane.getChildren().add(interviewLabel);
        } else if (interviewString.equals(COMPLETED)) {
            interviewLabel.setStyle(CHANGE_COLOUR + GREEN);
            statusPane.getChildren().add(interviewLabel);
        } else if (interviewString.equals(SCHEDULED)) {
            interviewLabel.setStyle(CHANGE_COLOUR + YELLOW);
            statusPane.getChildren().add(interviewLabel);
        }
    }

    public void setAvailableDays(Availability availability) {
        String[] week = Availability.WEEK;
        boolean[] isAvail = availability.getAvailableListAsBoolean();

        for (int i = 0; i < week.length; i++) {
            Label label = new Label(week[i]);
            if (isAvail[i]) {
                label.setStyle(CHANGE_COLOUR + BRIGHT_GREEN);
            } else {
                label.setStyle(CHANGE_COLOUR + GREY);
            }
            availableDays.getChildren().add(label);
        }
    }
}
