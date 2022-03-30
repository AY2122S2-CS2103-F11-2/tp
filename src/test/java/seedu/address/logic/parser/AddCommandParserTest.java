package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AVAILABILITY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.COURSE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AVAILABILITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COURSE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SENIORITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STUDENT_ID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SENIORITY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_ID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPLICATION_PENDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERVIEW_NOT_SCHEDULED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_ID_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalCandidates.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.candidate.Availability;
import seedu.address.model.candidate.Candidate;
import seedu.address.model.candidate.Course;
import seedu.address.model.candidate.Email;
import seedu.address.model.candidate.Name;
import seedu.address.model.candidate.Phone;
import seedu.address.model.candidate.Seniority;
import seedu.address.model.candidate.StudentId;
import seedu.address.testutil.CandidateBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Candidate expectedCandidate = new CandidateBuilder(BOB)
                .withApplicationStatus(VALID_APPLICATION_PENDING)
                .withInterviewStatus(VALID_INTERVIEW_NOT_SCHEDULED).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + STUDENT_ID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + COURSE_DESC_BOB + SENIORITY_DESC_BOB + AVAILABILITY_DESC_BOB,
                new AddCommand(expectedCandidate));

        // multiple names - last name accepted
        assertParseSuccess(parser, STUDENT_ID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + COURSE_DESC_BOB + SENIORITY_DESC_BOB + AVAILABILITY_DESC_BOB,
                new AddCommand(expectedCandidate));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, STUDENT_ID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + COURSE_DESC_BOB + SENIORITY_DESC_BOB + AVAILABILITY_DESC_BOB,
                new AddCommand(expectedCandidate));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing student id prefix
        assertParseFailure(parser, VALID_STUDENT_ID_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + COURSE_DESC_BOB + SENIORITY_DESC_BOB, expectedMessage);

        // missing name prefix
        assertParseFailure(parser, STUDENT_ID_DESC_BOB + VALID_NAME_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + COURSE_DESC_BOB + SENIORITY_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, STUDENT_ID_DESC_BOB + NAME_DESC_BOB + VALID_PHONE_BOB
                + EMAIL_DESC_BOB + COURSE_DESC_BOB + SENIORITY_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, STUDENT_ID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + COURSE_DESC_BOB + SENIORITY_DESC_BOB, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, STUDENT_ID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + VALID_COURSE_BOB + SENIORITY_DESC_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, STUDENT_ID_DESC_BOB + VALID_NAME_BOB + VALID_PHONE_BOB
                + EMAIL_DESC_BOB + VALID_COURSE_BOB + SENIORITY_DESC_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid studentId
        assertParseFailure(parser, INVALID_STUDENT_ID_DESC + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + COURSE_DESC_BOB + SENIORITY_DESC_BOB + AVAILABILITY_DESC_BOB,
                StudentId.MESSAGE_CONSTRAINTS);

        // invalid name
        assertParseFailure(parser, STUDENT_ID_DESC_BOB + INVALID_NAME_DESC + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + COURSE_DESC_BOB + SENIORITY_DESC_BOB + AVAILABILITY_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, STUDENT_ID_DESC_BOB + NAME_DESC_BOB + INVALID_PHONE_DESC
                + EMAIL_DESC_BOB + COURSE_DESC_BOB + SENIORITY_DESC_BOB + AVAILABILITY_DESC_BOB,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, STUDENT_ID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + INVALID_EMAIL_DESC + COURSE_DESC_BOB + SENIORITY_DESC_BOB + AVAILABILITY_DESC_BOB,
                Email.MESSAGE_CONSTRAINTS);

        // invalid course
        assertParseFailure(parser, STUDENT_ID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + INVALID_COURSE_DESC + SENIORITY_DESC_BOB
                + AVAILABILITY_DESC_BOB, Course.MESSAGE_CONSTRAINTS);

        // invalid seniority
        assertParseFailure(parser, STUDENT_ID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + COURSE_DESC_BOB + INVALID_SENIORITY_DESC
                + AVAILABILITY_DESC_BOB, Seniority.MESSAGE_CONSTRAINTS);

        // invalid availability
        assertParseFailure(parser, STUDENT_ID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + COURSE_DESC_BOB + SENIORITY_DESC_BOB + INVALID_AVAILABILITY_DESC,
                Availability.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, STUDENT_ID_DESC_BOB + INVALID_NAME_DESC + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + INVALID_COURSE_DESC + SENIORITY_DESC_BOB + AVAILABILITY_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + STUDENT_ID_DESC_BOB + NAME_DESC_BOB
                + EMAIL_DESC_BOB + COURSE_DESC_BOB + SENIORITY_DESC_BOB + AVAILABILITY_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
