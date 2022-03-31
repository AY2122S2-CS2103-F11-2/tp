package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIELD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KEYWORD;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.candidate.predicate.ApplicationStatusContainsKeywordsPredicate;
import seedu.address.model.candidate.predicate.AvailabilityContainsKeywordsPredicate;
import seedu.address.model.candidate.predicate.CandidateContainsKeywordsPredicate;
import seedu.address.model.candidate.predicate.ContainsKeywordsPredicate;
import seedu.address.model.candidate.predicate.CourseContainsKeywordsPredicate;
import seedu.address.model.candidate.predicate.EmailContainsKeywordsPredicate;
import seedu.address.model.candidate.predicate.InterviewStatusContainsKeywordsPredicate;
import seedu.address.model.candidate.predicate.NameContainsKeywordsPredicate;
import seedu.address.model.candidate.predicate.PhoneContainsKeywordsPredicate;
import seedu.address.model.candidate.predicate.RemarkContainsKeywordsPredicate;
import seedu.address.model.candidate.predicate.SeniorityContainsKeywordsPredicate;
import seedu.address.model.candidate.predicate.StudentIdContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_KEYWORD, PREFIX_FIELD);

        // throws exception if no keywords are specified
        if (!arePrefixesPresent(argMultimap, PREFIX_KEYWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        List<String> keywords = argMultimap.getAllValues(PREFIX_KEYWORD);
        Optional<String> field = argMultimap.getValue(PREFIX_FIELD);
        String fieldString;

        if (field.isEmpty()) {
            fieldString = "";
        } else {
            fieldString = field.get().toLowerCase();
        }

        ContainsKeywordsPredicate predicate;

        switch (fieldString) {
        case "appstatus":
            predicate = new ApplicationStatusContainsKeywordsPredicate(keywords);
            break;
        case "avail":
            predicate = new AvailabilityContainsKeywordsPredicate(keywords);
            break;
        case "all":
        case "":
            predicate = new CandidateContainsKeywordsPredicate(keywords);
            break;
        case "course":
            predicate = new CourseContainsKeywordsPredicate(keywords);
            break;
        case "email":
            predicate = new EmailContainsKeywordsPredicate(keywords);
            break;
        case "intstatus":
            predicate = new InterviewStatusContainsKeywordsPredicate(keywords);
            break;
        case "name":
            predicate = new NameContainsKeywordsPredicate(keywords);
            break;
        case "phone":
            predicate = new PhoneContainsKeywordsPredicate(keywords);
            break;
        case "remark":
            predicate = new RemarkContainsKeywordsPredicate(keywords);
            break;
        case "seniority":
            predicate = new SeniorityContainsKeywordsPredicate(keywords);
            break;
        case "studentid":
            predicate = new StudentIdContainsKeywordsPredicate(keywords);
            break;
        default:
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.INVALID_ATTRIBUTE_FIELD));
        }

        return new FindCommand(predicate);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
