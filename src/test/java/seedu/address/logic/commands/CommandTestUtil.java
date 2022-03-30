package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPLICATION_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVAILABILITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVIEW_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SENIORITY;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.candidate.Candidate;
import seedu.address.model.candidate.predicate.NameContainsKeywordsPredicate;
import seedu.address.model.interview.Interview;
import seedu.address.model.interview.predicate.SameInterviewPredicate;
import seedu.address.testutil.EditCandidateDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_STUDENT_ID_AMY = "A0123456B";
    public static final String VALID_STUDENT_ID_BOB = "A0234567B";
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "91111111";
    public static final String VALID_PHONE_BOB = "92222222";
    public static final String VALID_EMAIL_AMY = "E0123456@u.nus.edu";
    public static final String VALID_EMAIL_BOB = "E0234567@u.nus.edu";
    public static final String VALID_COURSE_AMY = "Business Analytics";
    public static final String VALID_COURSE_BOB = "Computer Engineering";
    public static final String VALID_SENIORITY_AMY = "2";
    public static final String VALID_SENIORITY_BOB = "2";

    public static final String VALID_APPLICATION_PENDING = "Pending";
    public static final String VALID_APPLICATION_REJECTED = "Rejected";
    public static final String VALID_APPLICATION_ACCEPTED = "Accepted";
    public static final String VALID_INTERVIEW_SCHEDULED = "Scheduled";
    public static final String VALID_INTERVIEW_COMPLETED = "Completed";
    public static final String VALID_INTERVIEW_NOT_SCHEDULED = "Not Scheduled";
    public static final String VALID_AVAILABILITY_AMY = "1,2,3,4,5";
    public static final String VALID_AVAILABILITY_BOB = "1,2";

    public static final String VALID_INTERVIEW_DATE_TIME = " " + PREFIX_DATETIME + "23-12-2022 10:00";

    public static final String STUDENT_ID_DESC_AMY = " " + PREFIX_ID + VALID_STUDENT_ID_AMY;
    public static final String STUDENT_ID_DESC_BOB = " " + PREFIX_ID + VALID_STUDENT_ID_BOB;
    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String COURSE_DESC_AMY = " " + PREFIX_COURSE + VALID_COURSE_AMY;
    public static final String COURSE_DESC_BOB = " " + PREFIX_COURSE + VALID_COURSE_BOB;
    public static final String SENIORITY_DESC_AMY = " " + PREFIX_SENIORITY + VALID_SENIORITY_AMY;
    public static final String SENIORITY_DESC_BOB = " " + PREFIX_SENIORITY + VALID_SENIORITY_BOB;
    public static final String APPLICATION_STATUS_PENDING = " " + PREFIX_APPLICATION_STATUS + VALID_APPLICATION_PENDING;
    public static final String INTERVIEW_STATUS_PENDING = " " + PREFIX_INTERVIEW_STATUS + VALID_INTERVIEW_NOT_SCHEDULED;
    public static final String AVAILABILITY_DESC_AMY = " " + PREFIX_AVAILABILITY + VALID_AVAILABILITY_AMY;
    public static final String AVAILABILITY_DESC_BOB = " " + PREFIX_AVAILABILITY + VALID_AVAILABILITY_BOB;

    public static final String INVALID_STUDENT_ID_DESC = " " + PREFIX_ID + "A0123456"; // Must begin with 'E'
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_COURSE_DESC = " " + PREFIX_COURSE; // empty string disallowed for courses
    public static final String INVALID_SENIORITY_DESC = " " + PREFIX_SENIORITY; // empty string disallowed for seniority
    public static final String INVALID_AVAILABILITY_DESC = " "
            + PREFIX_AVAILABILITY + "1,,1"; // double commas ',' not allowed in availability

    public static final LocalDateTime INVALID_INTERVIEW_DATE_TIME =
            LocalDateTime.of(2020, Month.FEBRUARY, 23, 15, 00); //Past date and time


    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditCandidateDescriptor DESC_AMY;
    public static final EditCommand.EditCandidateDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditCandidateDescriptorBuilder().withStudentId(VALID_STUDENT_ID_AMY).withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withCourse(VALID_COURSE_AMY)
                .withSeniority(VALID_SENIORITY_AMY)
                .withApplicationStatus(APPLICATION_STATUS_PENDING)
                .withAvailability(VALID_AVAILABILITY_AMY).build();
        DESC_BOB = new EditCandidateDescriptorBuilder().withStudentId(VALID_STUDENT_ID_BOB).withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withCourse(VALID_COURSE_BOB)
                .withSeniority(VALID_SENIORITY_BOB)
                .withApplicationStatus(APPLICATION_STATUS_PENDING)
                .withAvailability(VALID_AVAILABILITY_BOB).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            //assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered candidate list and selected candidate in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Candidate> expectedFilteredList = new ArrayList<>(actualModel.getFilteredCandidateList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredCandidateList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the candidate at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showCandidateAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredCandidateList().size());

        Candidate candidate = model.getFilteredCandidateList().get(targetIndex.getZeroBased());
        final String[] splitName = candidate.getName().fullName.split("\\s+");
        model.updateFilteredCandidateList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredCandidateList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the candidate at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showInterviewAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredInterviewSchedule().size());

        Interview interview = model.getFilteredInterviewSchedule().get(targetIndex.getZeroBased());
        model.updateFilteredInterviewSchedule(new SameInterviewPredicate(interview));;

        assertEquals(1, model.getFilteredInterviewSchedule().size());
    }
}
