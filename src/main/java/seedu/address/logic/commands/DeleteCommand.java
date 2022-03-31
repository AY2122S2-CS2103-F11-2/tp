package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.candidate.Candidate;

/**
 * Deletes a candidate identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the candidate identified by the index number used in the displayed candidates list.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Note: The candidate index number must be a valid non zero positive integer.";

    public static final String MESSAGE_DELETE_CANDIDATE_SUCCESS = "Deleted candidate: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Candidate> lastShownList = model.getFilteredCandidateList();
        if (lastShownList.isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_NO_CANDIDATES_DISPLAYED));
        }
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CANDIDATE_DISPLAYED_INDEX);
        }

        Candidate candidateToDelete = lastShownList.get(targetIndex.getZeroBased());

        model.deleteInterviewForCandidate(candidateToDelete);
        model.deleteCandidate(candidateToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_CANDIDATE_SUCCESS, candidateToDelete), false,
                false, false, -1, true, targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
