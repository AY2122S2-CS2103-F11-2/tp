package seedu.address.model.interview;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import seedu.address.model.candidate.Candidate;

/**
 * Represents an Interview in the interview schedule.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Interview {
    private static final int INTERVIEW_DURATION_IN_MINUTES = 30;

    private final Candidate candidate;
    private final LocalDateTime interviewDateTime;
    private final LocalDateTime interviewEndDateTime;
    private final int interviewDay;

    /**
     * Every field must be present and not null.
     */
    public Interview(Candidate candidate, LocalDateTime interviewDateTime) {
        requireAllNonNull(candidate, interviewDateTime);
        this.interviewDateTime = interviewDateTime;
        this.interviewEndDateTime = interviewDateTime.plusMinutes(INTERVIEW_DURATION_IN_MINUTES);
        this.interviewDay = interviewDateTime.getDayOfWeek().getValue();
        Candidate candidateWithStatus = candidate.triggerInterviewStatusScheduled();
        this.candidate = candidateWithStatus;
    }

    /**
     * Returns true if both interviews have the same candidates.
     */
    public boolean isSameInterviewCandidate(Interview otherInterview) {
        if (otherInterview == this) {
            return true;
        }
        return otherInterview != null
                && otherInterview.getCandidate().equals(getCandidate());
    }

    /**
     * Returns true if both interviews have the same interview date and time.
     */
    public boolean isConflictingInterview(Interview otherInterview) {
        if (otherInterview == this) {
            return true;
        }
        return otherInterview != null
                && (!otherInterview.getInterviewDateTime().isAfter(getInterviewDateTime())
                && otherInterview.getInterviewEndDateTime().isAfter(getInterviewDateTime()))
                || (otherInterview.getInterviewDateTime().isBefore(getInterviewEndDateTime())
                && otherInterview.getInterviewEndDateTime().isAfter(getInterviewEndDateTime()));
    }

    /**
     * Returns true if the candidate is available on the propsoed interview day
     */
    public boolean hasMatchingAvailability() {
        String candidateAvailabilities = this.candidate.getAvailability().toString();
        String interviewDayString = Integer.toString(this.interviewDay);
        return candidateAvailabilities.contains(interviewDayString);
    }

    /**
     * Returns true if the candidate is available on the proposed interview day
     */
    public boolean isDuringOfficeHour() {
        int interviewHour = this.interviewDateTime.getHour();
        int interviewMin = this.interviewDateTime.getMinute();

        if (this.interviewDay > 5) {
            return false;
        }

        if (interviewHour < 8) {
            return false;
        } else if (interviewHour >= 17 && interviewMin > 30) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * Returns true if this interview's date and time has expired.
     */
    public boolean isExpired() {
        return this.interviewDateTime.isBefore(LocalDateTime.now());
    }

    /**
     * Returns true if the given date and time is not in the past.
     */
    public static boolean isValidDateTime(LocalDateTime localDateTime) {
        return LocalDateTime.now().isBefore(localDateTime);
    }

    public Candidate getCandidate() {
        return this.candidate;
    }

    public LocalDateTime getInterviewDateTime() {
        return this.interviewDateTime;
    }

    public int getInterviewDay() {
        return this.interviewDay;
    }

    public LocalTime getInterviewStartTime() {
        return this.interviewDateTime.toLocalTime();
    }
    public LocalDateTime getInterviewEndDateTime() {
        return this.interviewEndDateTime;
    }

    public LocalDate getInterviewDate() {
        return this.interviewDateTime.toLocalDate();
    }


    /**
     * Returns true if both candidates have the same identity and data fields.
     * This defines a stronger notion of equality between two candidates.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Interview)) {
            return false;
        }

        Interview otherInterview = (Interview) other;
        return otherInterview.getCandidate().equals(getCandidate())
                && otherInterview.getInterviewDateTime().equals(getInterviewDateTime());
    }

    @Override
    public String toString() {
        return this.candidate.getName() + " " + this.candidate.getStudentId() + " "
                + this.getInterviewDate() + " " + this.getInterviewStartTime();
    }
}
