package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIELD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KEYWORD;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.candidate.predicate.ContainsKeywordsPredicate;

/**
 * Finds and lists all candidates in TAlent Assistant™ whose description (in one of all of the fields)
 * contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String INVALID_ATTRIBUTE_FIELD = "The provided attribute field to search by is invalid! \n"
            + "Note: Searchable attribute fields include `appstatus`, `avail`, `all`, `course`, `email`, "
            + "`intstatus`, `name`, `phone`, `remark`, `seniority`, `studentid`.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all candidates containing any of "
            + "the specified keywords (case-insensitive) in the specified attribute field.\n"
            + "Parameters: " + PREFIX_KEYWORD + "KEYWORD [" + PREFIX_KEYWORD + "MORE_KEYWORDS]... ["
            + PREFIX_FIELD + "ATTRIBUTE_FIELD]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_KEYWORD + "alice " + PREFIX_KEYWORD + "charlie "
            + PREFIX_FIELD + "name\n"
            + "Note: Searchable attribute fields include `appstatus`, `avail`, `all`, `course`, `email`, "
            + "`intstatus`, `name`, `phone`, `remark`, `seniority`, `studentid`.";

    private final ContainsKeywordsPredicate predicate;

    public FindCommand(ContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredCandidateList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_CANDIDATES_LISTED_OVERVIEW, model.getFilteredCandidateList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
