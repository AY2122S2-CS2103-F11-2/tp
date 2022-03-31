package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORTKEY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.model.candidate.Candidate;

public class SortCommandParserTest {

    private static final String SORT_EMPTY = " " + PREFIX_SORTKEY;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingParts_failure() {
        // no sort key specified
        assertParseFailure(parser, SORT_EMPTY, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.INVALID_ATTRIBUTE_FIELD));
    }

    @Test
    public void parse_invalidFields_failure() {
        // invalid field specified
        assertParseFailure(parser, " " + PREFIX_SORTKEY + "named", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.INVALID_ATTRIBUTE_FIELD));

        // multiple fields specified
        assertParseFailure(parser, " " + PREFIX_SORTKEY + "name course", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.INVALID_ATTRIBUTE_FIELD));

        // no whitespace before prefix
        assertParseFailure(parser, PREFIX_SORTKEY + "name", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Comparator<Candidate> sortComparatorName = Comparator.comparing(l -> l.getName().toString().toLowerCase());
        Comparator<Candidate> sortComparatorCourse = Comparator.comparing(l -> l.getName().toString().toLowerCase());
        Comparator<Candidate> sortComparatorStudentId = Comparator.comparing(l -> l.getName().toString().toLowerCase());
        Comparator<Candidate> sortComparatorInterviewStatus =
                Comparator.comparing(l -> l.getInterviewStatus().toString().toLowerCase());
        Comparator<Candidate> sortComparatorApplicationStatus =
                Comparator.comparing(l -> l.getApplicationStatus().toString().toLowerCase());
        Comparator<Candidate> sortComparatorSeniority =
                Comparator.comparing(l -> l.getSeniority().toString().toLowerCase());

        SortCommand expectedCommandName = new SortCommand(sortComparatorName, "name");
        SortCommand expectedCommandCourse = new SortCommand(sortComparatorCourse, "course");
        SortCommand expectedCommandStudentId = new SortCommand(sortComparatorStudentId, "studentid");
        SortCommand expectedCommandApplicationStatus = new SortCommand(sortComparatorApplicationStatus,
                "appstatus");
        SortCommand expectedCommandInterviewStatus = new SortCommand(sortComparatorInterviewStatus, "intstatus");
        SortCommand expectedCommandSeniority = new SortCommand(sortComparatorSeniority, "seniority");

        assertParseSuccess(parser, SORT_EMPTY + "name", expectedCommandName);
        assertParseSuccess(parser, " " + PREFIX_SORTKEY + "name", expectedCommandName);
        assertParseSuccess(parser, SORT_EMPTY + "course", expectedCommandCourse);
        assertParseSuccess(parser, SORT_EMPTY + "studentid", expectedCommandStudentId);
        assertParseSuccess(parser, SORT_EMPTY + "appstatus", expectedCommandApplicationStatus);
        assertParseSuccess(parser, SORT_EMPTY + "intstatus", expectedCommandInterviewStatus);
        assertParseSuccess(parser, SORT_EMPTY + "seniority", expectedCommandSeniority);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        String userInput = SORT_EMPTY + "name" + SORT_EMPTY + "course" + SORT_EMPTY + "studentid";

        Comparator<Candidate> sortComparator = Comparator.comparing(l -> l.getStudentId().toString().toLowerCase());
        SortCommand expectedCommand = new SortCommand(sortComparator, "studentid");

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
