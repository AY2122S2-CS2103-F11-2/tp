package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVAILABILITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SENIORITY;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.candidate.Candidate;

/**
 * Adds a candidate to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a candidate to the address book. "
            + "Parameters: "
            + PREFIX_ID + "ID "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_COURSE + "COURSE "
            + PREFIX_SENIORITY + "SENIORITY "
            + PREFIX_AVAILABILITY + "AVAILABILITY \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "A0123456B "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "E0123456@u.nus.edu "
            + PREFIX_COURSE + "Computer Science "
            + PREFIX_SENIORITY + "2 "
            + PREFIX_AVAILABILITY + "1,2,3,4,5";

    public static final String MESSAGE_SUCCESS = "New candidate added: %1$s";
    public static final String MESSAGE_DUPLICATE_CANDIDATE = "This candidate already exists in the system";

    private final Candidate toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Candidate}
     */
    public AddCommand(Candidate candidate) {
        requireNonNull(candidate);
        toAdd = candidate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasCandidate(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CANDIDATE);
        }

        model.addCandidate(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || ((other instanceof AddCommand) // instanceof handles nulls
                    && toAdd.equals(((AddCommand) other).toAdd));
    }
}
