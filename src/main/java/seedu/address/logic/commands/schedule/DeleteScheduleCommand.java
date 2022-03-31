package seedu.address.logic.commands.schedule;

import static java.util.Objects.requireNonNull;

import java.util.List;

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
public class DeleteScheduleCommand extends ScheduleCommand {

    public static final String COMMAND_WORD = ScheduleCommand.COMMAND_WORD + " delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the interview identified by the index number in the displayed interview schedule.\n"
            + "Parameters: INTERVIEW_INDEX\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Note: The interview index number must be a valid non zero positive integer.";

    public static final String MESSAGE_DELETE_INTERVIEW_SUCCESS =
            "Successfully deleted interview for %1$s";

    private final Index targetIndex;

    /**
     * Creates a AddScheduleCommand to schedule the candidate at specified index for an
     * interview on {@code LocalDateTime}
     */
    public DeleteScheduleCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Interview> lastShownList = model.getFilteredInterviewSchedule();
        if (lastShownList.isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_NO_INTERVIEWS_DISPLAYED));
        }
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INTERVIEW_DISPLAYED_INDEX);
        }
        Interview interviewToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteInterview(interviewToDelete);
        model.setCandidate(interviewToDelete.getCandidate(),
                interviewToDelete.getCandidate().triggerInterviewStatusNotScheduled());
        int candidateIndex = model.getFilteredCandidateList()
                .indexOf(interviewToDelete.getCandidate()
                        .triggerInterviewStatusNotScheduled());
        return new CommandResult(String.format(MESSAGE_DELETE_INTERVIEW_SUCCESS, interviewToDelete),
                false, false, false, -1, true, candidateIndex);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteScheduleCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteScheduleCommand) other).targetIndex)); // state check
    }
}
