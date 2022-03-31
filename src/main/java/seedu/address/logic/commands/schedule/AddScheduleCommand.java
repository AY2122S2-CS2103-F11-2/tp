package seedu.address.logic.commands.schedule;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.candidate.Candidate;
import seedu.address.model.interview.Interview;

/**
 * Schedules a candidate identified using it's displayed index from the address book for an interview
 * on a specified time slot.
 */
public class AddScheduleCommand extends ScheduleCommand {

    public static final String COMMAND_WORD = ScheduleCommand.COMMAND_WORD + " add";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Schedules the candidate identified by the index number for an interview on given date and time.\n"
            + "Parameters: candidate/CANDIDATE_INDEX at/DATE_TIME (in dd-MM-yyyy HH:mm format)\n"
            + "Example: " + COMMAND_WORD + " candidate/1 at/23-09-2022 13:30\n"
            + "Note: The candidate's index number must be a valid non zero positive integer.";

    public static final String MESSAGE_SCHEDULED_CANDIDATE_SUCCESS =
            "Successfully scheduled %1$s %2$s for interview on %3$s %4$s";

    public static final String MESSAGE_CANDIDATE_COMPLETED =
            "Candidate has already completed his/her interview.";

    private final Index targetIndex;
    private final LocalDateTime interviewDateTime;

    /**
     * Creates a AddScheduleCommand to schedule the candidate at specified index for an
     * interview on {@code LocalDateTime}
     */
    public AddScheduleCommand(Index targetIndex, LocalDateTime interviewDateTime) {
        this.targetIndex = targetIndex;
        this.interviewDateTime = interviewDateTime;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Candidate> lastShownList = model.getFilteredCandidateList();
        if (lastShownList.isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_NO_CANDIDATES_IN_SYSTEM));
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CANDIDATE_DISPLAYED_INDEX);
        }

        Candidate candidateToInterview = lastShownList.get(targetIndex.getZeroBased());
        Candidate editedCandidate = candidateToInterview.triggerInterviewStatusScheduled();
        Interview toAdd = new Interview(candidateToInterview, interviewDateTime);

        if (candidateToInterview.isCompleted()) {
            throw new CommandException(MESSAGE_CANDIDATE_COMPLETED);
        }
        if (model.hasInterviewCandidate(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CANDIDATE_INTERVIEW);
        }
        if (!toAdd.isDuringOfficeHour()) {
            throw new CommandException(MESSAGE_NOT_OFFICE_HOUR);
        }
        if (!toAdd.hasMatchingAvailability()) {
            throw new CommandException(MESSAGE_CANDIDATE_NOT_AVAILABLE);
        }
        if (model.hasConflictingInterview(toAdd)) {
            throw new CommandException(MESSAGE_CONFLICTING_INTERVIEW);
        }
        model.setCandidate(candidateToInterview, editedCandidate);
        model.addInterview(toAdd);
        return new CommandResult(String.format(MESSAGE_SCHEDULED_CANDIDATE_SUCCESS, toAdd.getCandidate().getName(),
                toAdd.getCandidate().getStudentId(), toAdd.getInterviewDate(), toAdd.getInterviewStartTime()),
                false, false, false, -1, true, targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddScheduleCommand // instanceof handles nulls
                && targetIndex.equals(((AddScheduleCommand) other).targetIndex)
                && interviewDateTime.equals(((AddScheduleCommand) other).interviewDateTime)); // state check
    }
}
