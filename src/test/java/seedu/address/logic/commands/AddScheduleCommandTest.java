package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCandidateAtIndex;
import static seedu.address.logic.commands.schedule.AddScheduleCommand.MESSAGE_CANDIDATE_NOT_AVAILABLE;
import static seedu.address.logic.commands.schedule.AddScheduleCommand.MESSAGE_CONFLICTING_INTERVIEW;
import static seedu.address.logic.commands.schedule.AddScheduleCommand.MESSAGE_DUPLICATE_CANDIDATE_INTERVIEW;
import static seedu.address.logic.commands.schedule.AddScheduleCommand.MESSAGE_NOT_OFFICE_HOUR;
import static seedu.address.testutil.TypicalCandidates.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CANDIDATE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_CANDIDATE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CANDIDATE;
import static seedu.address.testutil.TypicalInterviews.AFTER_OFFICE_HOURS;
import static seedu.address.testutil.TypicalInterviews.BEFORE_OFFICE_HOURS;
import static seedu.address.testutil.TypicalInterviews.INTERVIEW_AMY_TYPICAL;
import static seedu.address.testutil.TypicalInterviews.ON_WEEKEND;
import static seedu.address.testutil.TypicalInterviews.THURSDAY_INTERVIEW_DATE_TIME;
import static seedu.address.testutil.TypicalInterviews.TUESDAY_INTERVIEW_DATE_TIME;
import static seedu.address.testutil.TypicalInterviews.TYPICAL_INTERVIEW_DATE_TIME;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.schedule.AddScheduleCommand;
import seedu.address.model.InterviewSchedule;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.candidate.Candidate;
import seedu.address.model.interview.Interview;
import seedu.address.testutil.InterviewBuilder;


public class AddScheduleCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new InterviewSchedule(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Candidate candidateToInterview = model.getFilteredCandidateList().get(INDEX_FIRST_CANDIDATE.getZeroBased());
        LocalDateTime interviewDateTime = TUESDAY_INTERVIEW_DATE_TIME;
        AddScheduleCommand addScheduleCommand = new AddScheduleCommand(INDEX_FIRST_CANDIDATE, interviewDateTime);
        model.setCandidate(candidateToInterview, candidateToInterview.triggerInterviewStatusScheduled());
        candidateToInterview = candidateToInterview.triggerInterviewStatusScheduled();
        Interview toAdd = new Interview(candidateToInterview, interviewDateTime);

        String expectedMessage = String.format(AddScheduleCommand.MESSAGE_SCHEDULED_CANDIDATE_SUCCESS,
                toAdd.getCandidate().getName(), toAdd.getCandidate().getStudentId(),
                toAdd.getInterviewDate(), toAdd.getInterviewStartTime());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(),
                model.getInterviewSchedule(), new UserPrefs());

        expectedModel.addInterview(toAdd);

        assertCommandSuccess(addScheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCandidateList().size() + 1);
        AddScheduleCommand addScheduleCommand = new AddScheduleCommand(outOfBoundIndex, TYPICAL_INTERVIEW_DATE_TIME);

        assertCommandFailure(addScheduleCommand, model, Messages.MESSAGE_INVALID_CANDIDATE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showCandidateAtIndex(model, INDEX_FIRST_CANDIDATE);
        AddScheduleCommand addScheduleCommand =
                new AddScheduleCommand(INDEX_FIRST_CANDIDATE, TUESDAY_INTERVIEW_DATE_TIME);

        Candidate candidateToInterview = model.getFilteredCandidateList().get(INDEX_FIRST_CANDIDATE.getZeroBased());
        LocalDateTime interviewDateTime = TUESDAY_INTERVIEW_DATE_TIME;
        model.setCandidate(candidateToInterview, candidateToInterview.triggerInterviewStatusScheduled());

        String expectedMessage = String.format(AddScheduleCommand.MESSAGE_SCHEDULED_CANDIDATE_SUCCESS,
                candidateToInterview.getName(), candidateToInterview.getStudentId(),
                interviewDateTime.toLocalDate(), interviewDateTime.toLocalTime());

        Interview toAdd = new Interview(candidateToInterview, interviewDateTime);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(),
                new InterviewSchedule(), new UserPrefs());

        showCandidateAtIndex(expectedModel, INDEX_FIRST_CANDIDATE);
        expectedModel.addInterview(toAdd);
        assertCommandSuccess(addScheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showCandidateAtIndex(model, INDEX_FIRST_CANDIDATE);
        Index outOfBoundIndex = INDEX_SECOND_CANDIDATE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getCandidateList().size());
        AddScheduleCommand addScheduleCommand =
                new AddScheduleCommand(INDEX_SECOND_CANDIDATE, TYPICAL_INTERVIEW_DATE_TIME);
        assertCommandFailure(addScheduleCommand, model, Messages.MESSAGE_INVALID_CANDIDATE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_emptyList_throwsCommandException() {
        Index outOfBoundIndex = INDEX_FIRST_CANDIDATE;
        model.updateFilteredCandidateList(Model.PREDICATE_SHOW_EMPTY_LIST);
        AddScheduleCommand addScheduleCommand = new AddScheduleCommand(outOfBoundIndex, TYPICAL_INTERVIEW_DATE_TIME);

        assertCommandFailure(addScheduleCommand, model, Messages.MESSAGE_NO_CANDIDATES_IN_SYSTEM);
    }

    @Test
    public void execute_hasSameCandidate_throwsCommandException() {
        Candidate candidateToInterview = model.getFilteredCandidateList().get(INDEX_FIRST_CANDIDATE.getZeroBased());
        model.addInterview(new InterviewBuilder().withCandidate(candidateToInterview)
                .withInterviewDateTime(TYPICAL_INTERVIEW_DATE_TIME).build());
        AddScheduleCommand addScheduleCommand =
                new AddScheduleCommand(INDEX_FIRST_CANDIDATE, TYPICAL_INTERVIEW_DATE_TIME);

        assertCommandFailure(addScheduleCommand, model, MESSAGE_DUPLICATE_CANDIDATE_INTERVIEW);
    }

    @Test
    public void execute_hasConflictingInterview_throwsCommandException() {
        model.addInterview(INTERVIEW_AMY_TYPICAL);
        LocalDateTime interviewDateTime = TYPICAL_INTERVIEW_DATE_TIME;
        AddScheduleCommand addScheduleCommand = new AddScheduleCommand(INDEX_SECOND_CANDIDATE, interviewDateTime);

        assertCommandFailure(addScheduleCommand, model, MESSAGE_CONFLICTING_INTERVIEW);
    }

    @Test
    public void execute_hasNoMatchingAvailability_throwsCommandException() {
        LocalDateTime interviewDateTime = THURSDAY_INTERVIEW_DATE_TIME;
        AddScheduleCommand addScheduleCommand = new AddScheduleCommand(INDEX_SECOND_CANDIDATE, interviewDateTime);

        assertCommandFailure(addScheduleCommand, model, MESSAGE_CANDIDATE_NOT_AVAILABLE);
    }

    @Test
    public void execute_beforeOfficeHours_throwsCommandException() {
        LocalDateTime interviewDateTime = BEFORE_OFFICE_HOURS;
        AddScheduleCommand addScheduleCommand = new AddScheduleCommand(INDEX_FIRST_CANDIDATE, interviewDateTime);

        assertCommandFailure(addScheduleCommand, model, MESSAGE_NOT_OFFICE_HOUR);
    }

    @Test
    public void execute_afterOfficeHours_throwsCommandException() {
        LocalDateTime interviewDateTime = AFTER_OFFICE_HOURS;
        AddScheduleCommand addScheduleCommand = new AddScheduleCommand(INDEX_FIRST_CANDIDATE, interviewDateTime);

        assertCommandFailure(addScheduleCommand, model, MESSAGE_NOT_OFFICE_HOUR);
    }

    @Test
    public void execute_interviewDuringWeekend_throwsCommandException() {
        LocalDateTime interviewDateTime = ON_WEEKEND;
        AddScheduleCommand addScheduleCommand = new AddScheduleCommand(INDEX_FOURTH_CANDIDATE, interviewDateTime);

        assertCommandFailure(addScheduleCommand, model, MESSAGE_CANDIDATE_NOT_AVAILABLE);
    }

    @Test
    public void equals() {
        AddScheduleCommand scheduleFirstCommand =
                new AddScheduleCommand(INDEX_FIRST_CANDIDATE, TYPICAL_INTERVIEW_DATE_TIME);
        AddScheduleCommand scheduleSecondCommand =
                new AddScheduleCommand(INDEX_SECOND_CANDIDATE, TYPICAL_INTERVIEW_DATE_TIME);

        // same object -> returns true
        assertTrue(scheduleFirstCommand.equals(scheduleFirstCommand));

        // same values -> returns true
        AddScheduleCommand scheduleFirstCommandcopy =
                new AddScheduleCommand(INDEX_FIRST_CANDIDATE, TYPICAL_INTERVIEW_DATE_TIME);
        assertTrue(scheduleFirstCommand.equals(scheduleFirstCommandcopy));

        // different types -> returns false
        assertFalse(scheduleFirstCommand.equals(1));

        // null -> returns false
        assertFalse(scheduleFirstCommand.equals(null));

        // different candidate -> returns false
        assertFalse(scheduleFirstCommand.equals(scheduleSecondCommand));
    }
}
