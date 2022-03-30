package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCandidateAtIndex;
import static seedu.address.testutil.TypicalCandidates.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CANDIDATE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CANDIDATE;
import static seedu.address.testutil.TypicalInterviews.getTypicalInterviewSchedule;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.candidate.Candidate;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalInterviewSchedule(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Candidate candidateToDelete = model.getFilteredCandidateList().get(INDEX_FIRST_CANDIDATE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_CANDIDATE);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_CANDIDATE_SUCCESS, candidateToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(),
                model.getInterviewSchedule(), new UserPrefs());

        expectedModel.deleteCandidate(candidateToDelete);
        expectedModel.deleteInterviewForCandidate(candidateToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCandidateList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_CANDIDATE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showCandidateAtIndex(model, INDEX_FIRST_CANDIDATE);

        Candidate candidateToDelete = model.getFilteredCandidateList().get(INDEX_FIRST_CANDIDATE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_CANDIDATE);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_CANDIDATE_SUCCESS, candidateToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getInterviewSchedule(), new UserPrefs());

        expectedModel.deleteInterviewForCandidate(candidateToDelete);
        expectedModel.deleteCandidate(candidateToDelete);
        showNoCandidate(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showCandidateAtIndex(model, INDEX_FIRST_CANDIDATE);

        Index outOfBoundIndex = INDEX_SECOND_CANDIDATE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getCandidateList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_CANDIDATE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_emptyList_throwsCommandException() {
        Index outOfBoundIndex = INDEX_FIRST_CANDIDATE;
        model.updateFilteredCandidateList(Model.PREDICATE_SHOW_EMPTY_LIST);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_NO_CANDIDATES_IN_SYSTEM);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_CANDIDATE);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_CANDIDATE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_CANDIDATE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different candidate -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoCandidate(Model model) {
        model.updateFilteredCandidateList(p -> false);

        assertTrue(model.getFilteredCandidateList().isEmpty());
    }
}
