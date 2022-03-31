package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORTKEY;

import java.util.Comparator;
import java.util.stream.Stream;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.candidate.Candidate;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SORTKEY);

        // throws exception if no sort attribute field is specified
        if (!arePrefixesPresent(argMultimap, PREFIX_SORTKEY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        String sortKey = argMultimap.getValue(PREFIX_SORTKEY).get().toLowerCase();
        Comparator<Candidate> sortComparator;

        switch (sortKey) {
        case "appstatus":
            sortComparator = Comparator.comparing(l -> l.getApplicationStatus().toString().toLowerCase());
            break;
        case "course":
            sortComparator = Comparator.comparing(l -> l.getCourse().toString().toLowerCase());
            break;
        case "intstatus":
            sortComparator = Comparator.comparing(l -> l.getInterviewStatus().toString().toLowerCase());
            break;
        case "name":
            sortComparator = Comparator.comparing(l -> l.getName().toString().toLowerCase());
            break;
        case "seniority":
            sortComparator = Comparator.comparing(l -> l.getSeniority().toString().toLowerCase());
            break;
        case "studentid":
            sortComparator = Comparator.comparing(l -> l.getStudentId().toString().toLowerCase());
            break;
        default:
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.INVALID_ATTRIBUTE_FIELD));
        }

        return new SortCommand(sortComparator, sortKey);

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
