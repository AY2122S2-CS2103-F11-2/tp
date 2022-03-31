package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.candidate.Candidate;



public class FocusCommand extends Command {

    public static final String MESSAGE_FOCUS_CANDIDATE = "Details of candidate shown";
    public static final String MESSAGE_USAGE = "focus: Brings up a candidate's information into the middle panel.\n"
            + "Parameters: INDEX\n"
            + "Example: focus 1\n"
            + "Note: The candidate index number must be a valid non zero positive integer.";
    public static final String COMMAND_WORD = "focus";
    private final Index targetIndex;

    /**
     * Constructor for creating a Focus Command.
     */
    public FocusCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    /**
     * Executes the focus command to a particular index.
     *
     * @throws CommandException when the command is invalid or index is wrong.
     */
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Candidate> lastShownList = model.getFilteredCandidateList();
        if (lastShownList.isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_NO_CANDIDATES_IN_SYSTEM));
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CANDIDATE_DISPLAYED_INDEX);
        }

        Candidate candidateToShow = lastShownList.get(targetIndex.getZeroBased());
        return new CommandResult(String.format(MESSAGE_FOCUS_CANDIDATE),
                false, false, true, targetIndex.getZeroBased(), false, -1);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        } else if (other instanceof FocusCommand) {
            if (((FocusCommand) other).targetIndex.equals(this.targetIndex)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Index getTargetIndex() {
        return targetIndex;
    }
}
