package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_APPLICATION_PENDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVAILABILITY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVAILABILITY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERVIEW_NOT_SCHEDULED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.candidate.Candidate;

/**
 * A utility class containing a list of {@code Candidate} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Candidate ALICE = new CandidateBuilder()
            .withStudentId("E0123450")
            .withName("Alice Pauline")
            .withPhone("94351253")
            .withEmail("E0123456@u.nus.edu")
            .withCourse("Business Analytics")
            .withTags("friends")
            .withApplicationStatus(VALID_APPLICATION_PENDING)
            .withInterviewStatus(VALID_INTERVIEW_NOT_SCHEDULED)
            .withAvailability("1,2,3")
            .build();
    public static final Candidate BENSON = new CandidateBuilder()
            .withStudentId("E0234567")
            .withName("Benson Meier")
            .withPhone("98765432")
            .withEmail("E0234567@u.nus.edu")
            .withCourse("Computer Engineering")
            .withTags("owesMoney", "friends")
            .withApplicationStatus(VALID_APPLICATION_PENDING)
            .withInterviewStatus(VALID_INTERVIEW_NOT_SCHEDULED)
            .withAvailability("2,3,4")
            .build();
    public static final Candidate CARL = new CandidateBuilder()
            .withStudentId("E0345678")
            .withName("Carl Kurz")
            .withPhone("95352563")
            .withEmail("E0345678@u.nus.edu")
            .withCourse("Computer Science")
            .withApplicationStatus(VALID_APPLICATION_PENDING)
            .withInterviewStatus(VALID_INTERVIEW_NOT_SCHEDULED)
            .withAvailability("3,4,5")
            .build();
    public static final Candidate DANIEL = new CandidateBuilder()
            .withStudentId("E0456789")
            .withName("Daniel Meier")
            .withPhone("87652533")
            .withEmail("E0456789@u.nus.edu")
            .withCourse("Information Security")
            .withTags("friends")
            .withApplicationStatus(VALID_APPLICATION_PENDING)
            .withInterviewStatus(VALID_INTERVIEW_NOT_SCHEDULED)
            .withAvailability("5,6,7")
            .build();
    public static final Candidate ELLE = new CandidateBuilder()
            .withStudentId("E0567890")
            .withName("Elle Meyer")
            .withPhone("94822240")
            .withEmail("E0567890@u.nus.edu")
            .withCourse("Information Systems")
            .withApplicationStatus(VALID_APPLICATION_PENDING)
            .withInterviewStatus(VALID_INTERVIEW_NOT_SCHEDULED)
            .withAvailability("1,3,5,7")
            .build();
    public static final Candidate FIONA = new CandidateBuilder()
            .withStudentId("E0678901")
            .withName("Fiona Kunz")
            .withPhone("94824270")
            .withEmail("E0678901@u.nus.edu")
            .withCourse("Business Analytics")
            .withApplicationStatus(VALID_APPLICATION_PENDING)
            .withInterviewStatus(VALID_INTERVIEW_NOT_SCHEDULED)
            .withAvailability("2,4,6")
            .build();
    public static final Candidate GEORGE = new CandidateBuilder()
            .withStudentId("E0789012")
            .withName("George Best")
            .withPhone("94824420")
            .withEmail("E0789012@u.nus.edu")
            .withCourse("Computer Engineering")
            .withApplicationStatus(VALID_APPLICATION_PENDING)
            .withInterviewStatus(VALID_INTERVIEW_NOT_SCHEDULED)
            .withAvailability("2,3,4,5,6")
            .build();

    // Manually added
    public static final Candidate HOON = new CandidateBuilder()
            .withStudentId("E0890123")
            .withName("Hoon Meier")
            .withPhone("84824240")
            .withEmail("E0890123@u.nus.edu")
            .withCourse("Computer Science")
            .withApplicationStatus(VALID_APPLICATION_PENDING)
            .withInterviewStatus(VALID_INTERVIEW_NOT_SCHEDULED)
            .withAvailability("1,4,7")
            .build();
    public static final Candidate IDA = new CandidateBuilder()
            .withStudentId("E0901234")
            .withName("Ida Mueller")
            .withPhone("84821310")
            .withEmail("E0901234@u.nus.edu")
            .withCourse("Information Security")
            .withApplicationStatus(VALID_APPLICATION_PENDING)
            .withInterviewStatus(VALID_INTERVIEW_NOT_SCHEDULED)
            .withAvailability("3,4,5,6,7")
            .build();

    // Manually added - Candidate's details found in {@code CommandTestUtil}
    public static final Candidate AMY = new CandidateBuilder()
            .withStudentId(VALID_STUDENT_ID_AMY)
            .withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withCourse(VALID_COURSE_AMY)
            .withTags(VALID_TAG_FRIEND)
            .withApplicationStatus(VALID_APPLICATION_PENDING)
            .withInterviewStatus(VALID_INTERVIEW_NOT_SCHEDULED)
            .withAvailability(VALID_AVAILABILITY_AMY)
            .build();
    public static final Candidate BOB = new CandidateBuilder()
            .withStudentId(VALID_STUDENT_ID_BOB)
            .withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withCourse(VALID_COURSE_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withApplicationStatus(VALID_APPLICATION_PENDING)
            .withInterviewStatus(VALID_INTERVIEW_NOT_SCHEDULED)
            .withAvailability(VALID_AVAILABILITY_BOB)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Candidate candidate : getTypicalCandidates()) {
            ab.addPerson(candidate);
        }
        return ab;
    }

    public static List<Candidate> getTypicalCandidates() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
