package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.candidate.Candidate;
import seedu.address.model.candidate.Remark;
import seedu.address.model.interview.Interview;

/**
 * Changes the remark of an existing candidate in the address book.
 */
public class RemarkCommand extends Command {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the remark of the candidate identified "
            + "by the index number used in the displayed candidates list. "
            + "Parameters: INDEX " + PREFIX_REMARK + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_REMARK + "Is a good candidate.\n"
            + "Note: The candidate index number must be a valid non zero positive integer. Candidate's existing "
            + "remark will be overwritten.\n";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Candidate: \n %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed remark from Candidate: \n %1$s";

    private final Index index;
    private final Remark remark;

    /**
     * @param index of the candidate in the filtered candidate list to edit the remark
     * @param remark of the candidate to be updated to
     */
    public RemarkCommand(Index index, Remark remark) {
        requireAllNonNull(index, remark);

        this.index = index;
        this.remark = remark;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Candidate> lastShownList = model.getFilteredCandidateList();
        List<Interview> interviewSchedule = model.getFilteredInterviewSchedule();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CANDIDATE_DISPLAYED_INDEX);
        }

        Candidate candidateToEdit = lastShownList.get(index.getZeroBased());

        Candidate editedCandidate = new Candidate(
                candidateToEdit.getStudentId(),
                candidateToEdit.getName(),
                candidateToEdit.getPhone(),
                candidateToEdit.getEmail(),
                candidateToEdit.getCourse(),
                candidateToEdit.getSeniority(),
                candidateToEdit.getApplicationStatus(),
                candidateToEdit.getInterviewStatus(),
                candidateToEdit.getAvailability(),
                remark);

        model.setCandidate(candidateToEdit, editedCandidate);

        for (int i = 0; i < interviewSchedule.size(); i++) {
            if (candidateToEdit.equals(interviewSchedule.get(i).getCandidate())) {
                Interview interviewToUpdate = interviewSchedule.get(i);
                Interview updatedInterview = new Interview(editedCandidate, interviewToUpdate.getInterviewDateTime());
                model.updateInterviewCandidate(interviewToUpdate, updatedInterview);
            }
        }

        return new CommandResult(generateSuccessMessage(editedCandidate),
                false, false, false, -1, true, index.getZeroBased());
    }

    /**
     * Generates a command execution success message based on whether the remark is added to or removed from
     * {@code candidateToEdit}.
     */
    private String generateSuccessMessage(Candidate candidateToEdit) {
        String message = !remark.value.isEmpty() ? MESSAGE_ADD_REMARK_SUCCESS : MESSAGE_DELETE_REMARK_SUCCESS;
        return String.format(message, candidateToEdit);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        // state check
        RemarkCommand e = (RemarkCommand) other;
        return index.equals(e.index)
                && remark.equals(e.remark);
    }
}
