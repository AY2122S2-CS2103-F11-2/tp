package seedu.address.logic.commands.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INTERVIEWS;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.interview.Interview;

/**
 * Schedules a candidate identified using it's displayed index from the address book for an interview
 * on a specified time slot.
 */
public class EditScheduleCommand extends ScheduleCommand {

    public static final String COMMAND_WORD = ScheduleCommand.COMMAND_WORD + " edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the interview identified by the index number in the interview schedule "
            + "to the proposed date and time.\n"
            + "Parameters: INTERVIEW_INDEX at/DATE_TIME (in dd-MM-yyyy HH:mm format)\n"
            + "Example: " + COMMAND_WORD + " 3 at/20-03-2023 10:00\n"
            + "Note: The interview index number must be a valid non zero positive integer.";

    public static final String MESSAGE_EDIT_INTERVIEW_SUCCESS =
            "Successfully edited interview for %1$s";
    public static final String MESSAGE_NOT_EDITED = "Interview date and time must be provided!";
    public static final String MESSAGE_EXPIRED_INTERVIEW = "The interview you are trying to edit has expired!!";

    private final Index index;
    private final LocalDateTime newDateTime;

    /**
     * Creates a AddScheduleCommand to schedule the candidate at specified index for an
     * interview on {@code LocalDateTime}
     */
    public EditScheduleCommand(Index index, LocalDateTime newDateTime) {
        requireNonNull(index);
        requireNonNull(newDateTime);
        this.index = index;
        this.newDateTime = newDateTime;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Interview> lastShownList = model.getFilteredInterviewSchedule();
        if (lastShownList.isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_NO_INTERVIEWS_DISPLAYED));
        }
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INTERVIEW_DISPLAYED_INDEX);
        }

        Interview interviewToEdit = lastShownList.get(index.getZeroBased());

        if (interviewToEdit.isExpired()) {
            throw new CommandException(MESSAGE_EXPIRED_INTERVIEW);
        }
        Interview editedInterview = createEditedInterview(interviewToEdit, newDateTime);

        if (!editedInterview.isDuringOfficeHour()) {
            throw new CommandException(MESSAGE_NOT_OFFICE_HOUR);
        }
        if (!editedInterview.hasMatchingAvailability()) {
            throw new CommandException(MESSAGE_CANDIDATE_NOT_AVAILABLE);
        }
        if (model.hasConflictingInterview(editedInterview)) {
            throw new CommandException(MESSAGE_CONFLICTING_INTERVIEW);
        }
        model.setInterview(interviewToEdit, editedInterview);
        model.updateFilteredInterviewSchedule(PREDICATE_SHOW_ALL_INTERVIEWS);

        int indexCandidate = model.getFilteredCandidateList().indexOf(editedInterview.getCandidate());
        Logger.getLogger(EditScheduleCommand.class.getName()).log(Level.INFO, String.valueOf(indexCandidate));
        return new CommandResult(String.format(MESSAGE_EDIT_INTERVIEW_SUCCESS, interviewToEdit
                + " to " + editedInterview.getInterviewDate() + " " + editedInterview.getInterviewStartTime()),
                false, false, false, -1, true, indexCandidate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditScheduleCommand // instanceof handles nulls
                && index.equals(((EditScheduleCommand) other).index)); // state check
    }

    /**
     * Creates and returns a {@code Candidate} with the details of {@code candidateToEdit}
     * edited with {@code editCandidateDescriptor}.
     */
    private static Interview createEditedInterview(Interview interviewToEdit, LocalDateTime newDateTime) {
        assert interviewToEdit != null;
        assert newDateTime != null;

        return new Interview(interviewToEdit.getCandidate(), newDateTime);
    }
}
