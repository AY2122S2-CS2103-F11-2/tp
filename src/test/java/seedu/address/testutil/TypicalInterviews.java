package seedu.address.testutil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.InterviewSchedule;
import seedu.address.model.interview.Interview;


/**
 * A utility class containing a list of {@code Candidate} objects to be used in tests.
 */
public class TypicalInterviews {
    public static final LocalDateTime TYPICAL_INTERVIEW_DATE_TIME = LocalDateTime.of(2022, Month.DECEMBER, 23, 10, 00);
    public static final LocalDateTime THURSDAY_INTERVIEW_DATE_TIME = LocalDateTime.of(2080, Month.APRIL, 25, 10, 00);
    public static final LocalDateTime TUESDAY_INTERVIEW_DATE_TIME = LocalDateTime.of(2080, Month.APRIL, 23, 10, 00);
    public static final LocalDateTime AFTER_OFFICE_HOURS = LocalDateTime.of(2080, Month.APRIL, 23, 17, 31);
    public static final LocalDateTime BEFORE_OFFICE_HOURS = LocalDateTime.of(2080, Month.APRIL, 23, 7, 59);
    public static final LocalDateTime ON_WEEKEND = LocalDateTime.of(2080, Month.APRIL, 20, 8, 30);

    public static final LocalDateTime VALID_ALICE_INTERVIEW_DATE_TIME =
            LocalDateTime.of(2023, Month.FEBRUARY, 23, 15, 00);
    public static final LocalDateTime VALID_BENSON_INTERVIEW_DATE_TIME =
            LocalDateTime.of(2023, Month.DECEMBER, 01, 15, 00);
    public static final LocalDateTime VALID_CARL_INTERVIEW_DATE_TIME =
            VALID_ALICE_INTERVIEW_DATE_TIME.plusMinutes(15);
    public static final LocalDateTime VALID_NO_CONFLICT_INTERVIEW_DATE_TIME =
            LocalDateTime.of(2030, Month.FEBRUARY, 23, 15, 00);

    public static final Interview INTERVIEW_ALICE = new InterviewBuilder().withCandidate(TypicalCandidates.ALICE)
            .withInterviewDateTime(VALID_ALICE_INTERVIEW_DATE_TIME).build();
    public static final Interview INTERVIEW_BENSON = new InterviewBuilder().withCandidate(TypicalCandidates.BENSON)
            .withInterviewDateTime(VALID_BENSON_INTERVIEW_DATE_TIME).build();
    public static final Interview INTERVIEW_CARL = new InterviewBuilder().withCandidate(TypicalCandidates.CARL)
            .withInterviewDateTime(VALID_CARL_INTERVIEW_DATE_TIME).build();

    // Manually added - Candidate's details found in {@code CommandTestUtil}
    public static final Interview INTERVIEW_AMY_TYPICAL = new InterviewBuilder().withCandidate(TypicalCandidates.AMY)
            .withInterviewDateTime(TYPICAL_INTERVIEW_DATE_TIME).build();
    public static final Interview INTERVIEW_BOB_TYPICAL = new InterviewBuilder().withCandidate(TypicalCandidates.BOB)
            .withInterviewDateTime(TYPICAL_INTERVIEW_DATE_TIME).build();


    private TypicalInterviews() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical candidates.
     */
    public static InterviewSchedule getTypicalInterviewSchedule() {
        InterviewSchedule interviewSchedule = new InterviewSchedule();
        for (Interview interview : getTypicalInterviews()) {
            interviewSchedule.addInterview(interview);
        }
        return interviewSchedule;
    }

    public static List<Interview> getTypicalInterviews() {
        return new ArrayList<>(Arrays.asList(INTERVIEW_ALICE, INTERVIEW_BENSON, INTERVIEW_AMY_TYPICAL));
    }
}
