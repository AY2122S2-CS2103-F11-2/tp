package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.interview.Interview;
import seedu.address.model.interview.UniqueInterviewList;

public class InterviewSchedule implements ReadOnlyInterviewSchedule {
    private final UniqueInterviewList interviews;

    {
        interviews = new UniqueInterviewList();
    }

    public InterviewSchedule() {}

    /**
     * Creates an InterviewSchedule using the Interviews in the {@code toBeCopied}
     */
    public InterviewSchedule(ReadOnlyInterviewSchedule toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the interview list with {@code interviews}.
     * {@code interviews} must not contain duplicate interviews.
     */
    public void setInterviews(List<Interview> interviews) {
        this.interviews.setInterviews(interviews);
    }

    /**
     * Resets the existing data of this {@code InterviewSchedule} with {@code newData}.
     */
    public void resetData(ReadOnlyInterviewSchedule newData) {
        requireNonNull(newData);

        setInterviews(newData.getInterviewList());
    }

    /**
     * Returns true if an interview with the same candidate as {@code interview} exists in the interview schedule.
     */
    public boolean hasCandidate(Interview interview) {
        requireNonNull(interview);
        return interviews.containsSameCandidate(interview);
    }
    /**
     * Returns true if an interview with the same date and time slot as {@code interview}
     * exists in the interview schedule.
     */
    public boolean hasConflictingInterview(Interview interview) {
        requireNonNull(interview);
        return interviews.containsConflictingInterview(interview);
    }

    /**
     * Adds an interview to the interview schedule.
     * The interview must not already exist in the interview schedule.
     */
    public void addInterview(Interview interview) {
        interviews.add(interview);
    }

    // Edit function not implemented yet, code commented out to reduce test coverage.
    /* public void setInterview(Interview target, Interview editedInterview) {
        requireNonNull(editedInterview);

        interviews.setInterview(target, editedInterview);
    }*/

    // Delete function not implemented yet, code commented out to reduce test coverage.
    /* public void removeInterview(Interview key) {
        interviews.remove(key);
    }*/

    @Override
    public String toString() {
        return interviews.asUnmodifiableObservableList().size() + " interviews";
        // TODO: refine later
    }

    @Override
    public ObservableList<Interview> getInterviewList() {
        return interviews.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InterviewSchedule // instanceof handles nulls
                && interviews.equals(((InterviewSchedule) other).interviews));
    }

    @Override
    public int hashCode() {
        return interviews.hashCode();
    }
}