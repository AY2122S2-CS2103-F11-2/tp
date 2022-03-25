package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.candidate.Candidate;

/**
 * An UI component that displays information of a {@code Candidate}.
 */
public class FocusCard extends UiPart<Region> {

    private static final String FXML = "FocusListCard.fxml";

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
    private Label candidateStatus;
    @FXML
    private Label availability;
    @FXML
    private FlowPane tags;
    /**
     * Creates a {@code CandidateCode} with the given {@code Candidate} and index to display.
     */
    public FocusCard(Candidate candidate, int displayedIndex) {
        super(FXML);
        this.candidate = candidate;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FocusCard)) {
            return false;
        }

        // state check
        FocusCard card = (FocusCard) other;
        return id.getText().equals(card.id.getText())
                && candidate.equals(card.candidate);
    }
}
