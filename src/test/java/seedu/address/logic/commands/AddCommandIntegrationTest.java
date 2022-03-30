package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCandidates.getTypicalAddressBook;
import static seedu.address.testutil.TypicalInterviews.getTypicalInterviewSchedule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.candidate.Candidate;
import seedu.address.testutil.CandidateBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalInterviewSchedule(), new UserPrefs());
    }

    @Test
    public void execute_newCandidate_success() {
        Candidate validCandidate = new CandidateBuilder()
                .withName("Valid Candidate")
                .withStudentId("A0563827B").build();

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getInterviewSchedule(), new UserPrefs());
        expectedModel.addCandidate(validCandidate);

        assertCommandSuccess(new AddCommand(validCandidate), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validCandidate), expectedModel);
    }

    @Test
    public void execute_duplicateCandidate_throwsCommandException() {
        Candidate candidateInList = model.getAddressBook().getCandidateList().get(0);
        assertCommandFailure(new AddCommand(candidateInList), model, AddCommand.MESSAGE_DUPLICATE_CANDIDATE);
    }

}
