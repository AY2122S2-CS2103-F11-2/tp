package seedu.address.model.candidate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERVIEW_NOT_SCHEDULED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERVIEW_SCHEDULED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_ID_BOB;
import static seedu.address.testutil.TypicalCandidates.ALICE;
import static seedu.address.testutil.TypicalCandidates.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.CandidateBuilder;

public class CandidateTest {

    @Test
    public void isSameCandidate() {
        // same object -> returns true
        assertTrue(ALICE.isSameCandidate(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameCandidate(null));

        // same name, all other attributes different -> returns true
        Candidate editedAlice = new CandidateBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withCourse(VALID_COURSE_BOB).build();
        assertTrue(ALICE.isSameCandidate(editedAlice));

        // different studentId, all other attributes same, incl. email -> returns true
        editedAlice = new CandidateBuilder(ALICE).withStudentId(VALID_STUDENT_ID_BOB).build();
        assertTrue(ALICE.isSameCandidate(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Candidate editedBob = new CandidateBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSameCandidate(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new CandidateBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSameCandidate(editedBob));

        // different studentId, all other attributes same, incl. email -> returns true
        editedBob = new CandidateBuilder(BOB).withStudentId(VALID_STUDENT_ID_AMY).build();
        assertTrue(BOB.isSameCandidate(editedBob));

        // totally different candidates
        assertFalse(ALICE.isSameCandidate(BOB));
        assertFalse(BOB.isSameCandidate(ALICE));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Candidate aliceCopy = new CandidateBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different candidate -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Candidate editedAlice = new CandidateBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new CandidateBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new CandidateBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new CandidateBuilder(ALICE).withCourse(VALID_COURSE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void trigger_interviewStatus_success() {
        Candidate candidate = new CandidateBuilder().withInterviewStatus(VALID_INTERVIEW_NOT_SCHEDULED).build();
        assertEquals(candidate.triggerInterviewStatusScheduled().getInterviewStatus(),
                new InterviewStatus(VALID_INTERVIEW_SCHEDULED));
    }

}
