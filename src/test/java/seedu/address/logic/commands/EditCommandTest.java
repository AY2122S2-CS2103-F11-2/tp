package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPLICATION_ACCEPTED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPLICATION_PENDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERVIEW_COMPLETED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERVIEW_NOT_SCHEDULED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
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
import seedu.address.logic.commands.EditCommand.EditCandidateDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.InterviewSchedule;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.candidate.Candidate;
import seedu.address.testutil.CandidateBuilder;
import seedu.address.testutil.EditCandidateDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalInterviewSchedule(), new UserPrefs());
    private Model deletedListModel =
            new ModelManager(getTypicalAddressBook(), new InterviewSchedule(), new UserPrefs(), true);

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Candidate editedCandidate = new CandidateBuilder().build();
        EditCandidateDescriptor descriptor = new EditCandidateDescriptorBuilder(editedCandidate).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CANDIDATE, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CANDIDATE_SUCCESS, editedCandidate);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new InterviewSchedule(model.getInterviewSchedule()), new UserPrefs());
        expectedModel.setCandidate(model.getFilteredCandidateList().get(0), editedCandidate);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredListDeletedListModel_success() {
        Candidate editedCandidate = new CandidateBuilder().build();
        EditCandidateDescriptor descriptor = new EditCandidateDescriptorBuilder(editedCandidate).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CANDIDATE, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CANDIDATE_SUCCESS, editedCandidate);

        Model expectedModel = new ModelManager(new AddressBook(deletedListModel.getAddressBook()),
                new InterviewSchedule(deletedListModel.getInterviewSchedule()), new UserPrefs(), true);
        expectedModel.setCandidate(deletedListModel.getFilteredCandidateList().get(0), editedCandidate);

        assertCommandSuccess(editCommand, deletedListModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastCandidate = Index.fromOneBased(model.getFilteredCandidateList().size());
        Candidate lastCandidate = model.getFilteredCandidateList().get(indexLastCandidate.getZeroBased());

        CandidateBuilder candidateInList = new CandidateBuilder(lastCandidate);
        Candidate editedCandidate = candidateInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();

        EditCandidateDescriptor descriptor = new EditCandidateDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).build();
        EditCommand editCommand = new EditCommand(indexLastCandidate, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CANDIDATE_SUCCESS, editedCandidate);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new InterviewSchedule(model.getInterviewSchedule()), new UserPrefs());
        expectedModel.setCandidate(lastCandidate, editedCandidate);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredListDeletedListModel_success() {
        Index indexLastCandidate = Index.fromOneBased(deletedListModel.getFilteredCandidateList().size());
        Candidate lastCandidate = deletedListModel.getFilteredCandidateList().get(indexLastCandidate.getZeroBased());

        CandidateBuilder candidateInList = new CandidateBuilder(lastCandidate);
        Candidate editedCandidate = candidateInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();

        EditCandidateDescriptor descriptor = new EditCandidateDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).build();
        EditCommand editCommand = new EditCommand(indexLastCandidate, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CANDIDATE_SUCCESS, editedCandidate);

        Model expectedModel = new ModelManager(new AddressBook(deletedListModel.getAddressBook()),
                new InterviewSchedule(deletedListModel.getInterviewSchedule()), new UserPrefs(), true);
        expectedModel.setCandidate(lastCandidate, editedCandidate);

        assertCommandSuccess(editCommand, deletedListModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CANDIDATE, new EditCandidateDescriptor());
        Candidate editedCandidate = model.getFilteredCandidateList().get(INDEX_FIRST_CANDIDATE.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CANDIDATE_SUCCESS, editedCandidate);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new InterviewSchedule(model.getInterviewSchedule()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showCandidateAtIndex(model, INDEX_FIRST_CANDIDATE);

        Candidate candidateInFilteredList = model.getFilteredCandidateList().get(INDEX_FIRST_CANDIDATE.getZeroBased());
        Candidate editedCandidate = new CandidateBuilder(candidateInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CANDIDATE,
                new EditCandidateDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CANDIDATE_SUCCESS, editedCandidate);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new InterviewSchedule(model.getInterviewSchedule()), new UserPrefs());
        expectedModel.setCandidate(model.getFilteredCandidateList().get(0), editedCandidate);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateCandidateUnfilteredList_failure() {
        Candidate firstCandidate = model.getFilteredCandidateList().get(INDEX_FIRST_CANDIDATE.getZeroBased());
        EditCandidateDescriptor descriptor = new EditCandidateDescriptorBuilder(firstCandidate).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_CANDIDATE, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_CANDIDATE);
    }

    @Test
    public void execute_duplicateCandidateFilteredList_failure() {
        showCandidateAtIndex(model, INDEX_FIRST_CANDIDATE);

        // edit candidate in filtered list into a duplicate in address book
        Candidate candidateInList = model.getAddressBook().getCandidateList()
                .get(INDEX_SECOND_CANDIDATE.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CANDIDATE,
                new EditCandidateDescriptorBuilder(candidateInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_CANDIDATE);
    }

    @Test
    public void execute_invalidCandidateIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCandidateList().size() + 1);
        EditCandidateDescriptor descriptor = new EditCandidateDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_CANDIDATE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidCandidateIndexFilteredList_failure() {
        showCandidateAtIndex(model, INDEX_FIRST_CANDIDATE);
        Index outOfBoundIndex = INDEX_SECOND_CANDIDATE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getCandidateList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditCandidateDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_CANDIDATE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_CANDIDATE, DESC_AMY);

        // same values -> returns true
        EditCandidateDescriptor copyDescriptor = new EditCandidateDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_CANDIDATE, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_CANDIDATE, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_CANDIDATE, DESC_BOB)));

        EditCandidateDescriptor differentApplicationStatusPending = new EditCandidateDescriptorBuilder()
                .withInterviewStatus(VALID_APPLICATION_PENDING)
                .build();

        EditCandidateDescriptor differentApplicationStatusAccepted = new EditCandidateDescriptorBuilder()
                .withInterviewStatus(VALID_APPLICATION_ACCEPTED)
                .build();

        assertFalse(differentApplicationStatusPending.equals(new EditCommand(INDEX_FIRST_CANDIDATE,
                differentApplicationStatusAccepted)));

        EditCandidateDescriptor differentInterviewStatusNotScheduled = new EditCandidateDescriptorBuilder()
                .withInterviewStatus(VALID_INTERVIEW_NOT_SCHEDULED)
                .build();

        EditCandidateDescriptor differentInterviewStatusCompleted = new EditCandidateDescriptorBuilder()
                .withInterviewStatus(VALID_INTERVIEW_COMPLETED)
                .build();

        assertFalse(differentInterviewStatusNotScheduled.equals(new EditCommand(INDEX_FIRST_CANDIDATE,
                differentInterviewStatusCompleted)));

        EditCandidateDescriptor differentSenorityOne = new EditCandidateDescriptorBuilder()
                .withSeniority("2")
                .build();

        EditCandidateDescriptor differentSeniorityTwo = new EditCandidateDescriptorBuilder()
                .withSeniority("1")
                .build();

        assertFalse(differentSenorityOne.equals(new EditCommand(INDEX_FIRST_CANDIDATE,
                differentSeniorityTwo)));

    }

}
