package seedu.address.model.candidate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERVIEW_COMPLETED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERVIEW_NOT_SCHEDULED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERVIEW_SCHEDULED;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class InterviewStatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new InterviewStatus(null));
    }

    @Test
    public void constructor_test_success() {
        InterviewStatus notScheduled = new InterviewStatus(VALID_INTERVIEW_NOT_SCHEDULED);
        InterviewStatus scheduled = new InterviewStatus(VALID_INTERVIEW_SCHEDULED);
        InterviewStatus completed = new InterviewStatus(VALID_INTERVIEW_COMPLETED);

        assertEquals(new InterviewStatus(VALID_INTERVIEW_NOT_SCHEDULED), notScheduled);
        assertEquals(new InterviewStatus(VALID_INTERVIEW_SCHEDULED), scheduled);
        assertEquals(new InterviewStatus(VALID_INTERVIEW_COMPLETED), completed);
    }

    @Test
    public void isValidStatus() {
        assertThrows(NullPointerException.class, () -> InterviewStatus.isValidStatus(null));

        assertFalse(InterviewStatus.isValidStatus(""));
        assertFalse(InterviewStatus.isValidStatus(" "));
        assertFalse(InterviewStatus.isValidStatus("interviews"));
        assertFalse(InterviewStatus.isValidStatus("interviewe"));
        assertFalse(InterviewStatus.isValidStatus("not interview"));
        assertFalse(InterviewStatus.isValidStatus("pend"));
        assertFalse(InterviewStatus.isValidStatus("completes"));
        assertFalse(InterviewStatus.isValidStatus("schedules"));
        assertFalse(InterviewStatus.isValidStatus("schedule"));



        assertTrue(InterviewStatus.isValidStatus("not scheduled"));
        assertTrue(InterviewStatus.isValidStatus("notscheduled"));
        assertTrue(InterviewStatus.isValidStatus("Not Scheduled"));
        assertTrue(InterviewStatus.isValidStatus("notScheduled"));
        assertTrue(InterviewStatus.isValidStatus("Notscheduled"));
        assertTrue(InterviewStatus.isValidStatus("Scheduled"));
        assertTrue(InterviewStatus.isValidStatus("scheduled"));
        assertTrue(InterviewStatus.isValidStatus("Completed"));
        assertTrue(InterviewStatus.isValidStatus("completed"));
    }
}
