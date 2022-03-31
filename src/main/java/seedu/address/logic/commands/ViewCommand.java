package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.interview.predicate.AllWithinTimePeriodPredicate;
import seedu.address.model.interview.predicate.WithinTimePeriodPredicate;

/**
 * Lists all candidates in the address book to the user.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays all interviews in the system"
            + " scheduled within the specified time period.\n"
            + "Parameters: TIME_PERIOD\n"
            + "Example: " + COMMAND_WORD + " today\n"
            + "Note: Allowable time periods include `all`, `today`, `week`, `month`.";

    private final WithinTimePeriodPredicate predicate;

    public ViewCommand(WithinTimePeriodPredicate predicate) {
        this.predicate = predicate;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (predicate instanceof AllWithinTimePeriodPredicate
                && model.getInterviewSchedule().getInterviewList().size() == 0) {
            throw new CommandException(String.format(Messages.MESSAGE_NO_INTERVIEWS_IN_SYSTEM));
        }

        model.updateFilteredInterviewSchedule(predicate);
        return new CommandResult(String
                .format(Messages.MESSAGE_INTERVIEWS_LISTED_OVERVIEW, model.getFilteredInterviewSchedule().size()));
    }
}
