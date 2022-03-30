package seedu.address.model.candidate;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.candidate.InterviewStatus.COMPLETED;
import static seedu.address.model.candidate.InterviewStatus.NOT_SCHEDULED;
import static seedu.address.model.candidate.InterviewStatus.SCHEDULED;

import java.util.Objects;

/**
 * Represents a Candidate in TAlent Assistant™.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Candidate {

    // Identity fields
    private final StudentId studentId;
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Course course;
    private final Seniority seniority;
    private final ApplicationStatus applicationStatus;
    private InterviewStatus interviewStatus;
    private final Availability availability;

    /**
     * Every field must be present and not null.
     */
    public Candidate(StudentId studentId, Name name, Phone phone, Email email, Course course, Seniority seniority,
            ApplicationStatus applicationStatus, InterviewStatus interviewStatus,
            Availability availability) {
        requireAllNonNull(studentId, name, phone, email, course, seniority, availability);
        this.studentId = studentId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.course = course;
        this.seniority = seniority;
        this.applicationStatus = applicationStatus;
        this.interviewStatus = interviewStatus;
        this.availability = availability;
    }

    public StudentId getStudentId() {
        return studentId;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Course getCourse() {
        return course;
    }

    public Seniority getSeniority() {
        return seniority;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public InterviewStatus getInterviewStatus() {
        return interviewStatus;
    }

    public Availability getAvailability() {
        return availability;
    }

    /**
     * Returns true if both candidates have the same name.
     * This defines a weaker notion of equality between two candidates.
     */
    public boolean isSameCandidate(Candidate otherCandidate) {
        if (otherCandidate == this) {
            return true;
        }

        return otherCandidate != null
                && otherCandidate.getStudentId().equals(getStudentId());
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

        if (!(other instanceof Candidate)) {
            return false;
        }

        Candidate otherCandidate = (Candidate) other;
        return otherCandidate.getStudentId().equals(getStudentId())
                && otherCandidate.getName().equals(getName())
                && otherCandidate.getPhone().equals(getPhone())
                && otherCandidate.getEmail().equals(getEmail())
                && otherCandidate.getCourse().equals(getCourse())
                && otherCandidate.getSeniority().equals(getSeniority())
                && otherCandidate.getApplicationStatus().equals(getApplicationStatus())
                && otherCandidate.getInterviewStatus().equals(getInterviewStatus())
                && otherCandidate.getAvailability().equals(getAvailability());
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, name, phone, email, course, seniority,
                applicationStatus, interviewStatus, availability);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Student ID: ").append(getStudentId())
                .append("; Name: ")
                .append(getName())
                .append("; Phone: ")
                .append(getPhone())
                .append("; Email: ")
                .append(getEmail())
                .append("; Course: ")
                .append(getCourse())
                .append("; Seniority: ")
                .append(getSeniority())
                .append("; Application Status: ")
                .append(getApplicationStatus())
                .append("; Interview Status: ")
                .append(getInterviewStatus())
                .append("; Availability: ")
                .append(getAvailability());
        return builder.toString();
    }

    /**
     * Trigger the Candidate status to be a `Scheduled`.
     *
     * @return a New Candidate with the same attributes, but updated InterviewStatus.
     */
    public Candidate triggerInterviewStatusScheduled() {
        requireAllNonNull(name, phone, email, course, seniority,
                applicationStatus, interviewStatus, availability);
        return new Candidate(this.getStudentId(),
                this.getName(),
                this.getPhone(),
                this.getEmail(),
                this.getCourse(),
                this.getSeniority(),
                this.getApplicationStatus(),
                new InterviewStatus(SCHEDULED),
                this.getAvailability()
        );
    }

    /**
     * Trigger the Candidate status to be a `Not Scheduled`.
     *
     * @return a New Candidate with the same attributes, but updated InterviewStatus.
     */
    public Candidate triggerInterviewStatusNotScheduled() {
        requireAllNonNull(name, phone, email, course, seniority,
                applicationStatus, interviewStatus, availability);
        return new Candidate(this.getStudentId(),
                this.getName(),
                this.getPhone(),
                this.getEmail(),
                this.getCourse(),
                this.getSeniority(),
                this.getApplicationStatus(),
                new InterviewStatus(NOT_SCHEDULED),
                this.getAvailability()
        );
    }

    /**
     * Trigger the Candidate status to be a `Completed`.
     *
     * @return a New Candidate with the same attributes, but updated InterviewStatus.
     */
    public Candidate triggerInterviewStatusCompleted() {
        requireAllNonNull(name, phone, email, course, seniority,
                applicationStatus, interviewStatus, availability);
        return new Candidate(this.getStudentId(),
                this.getName(),
                this.getPhone(),
                this.getEmail(),
                this.getCourse(),
                this.getSeniority(),
                this.getApplicationStatus(),
                new InterviewStatus(COMPLETED),
                this.getAvailability()
        );
    }

    public boolean isCompleted() {
        return interviewStatus.equals(new InterviewStatus(COMPLETED));
    }

    public Candidate setInterviewStatusNotScheduled() {
        requireAllNonNull(name, phone, email, course, seniority,
                applicationStatus, interviewStatus, availability);
        return new Candidate(this.getStudentId(),
                this.getName(),
                this.getPhone(),
                this.getEmail(),
                this.getCourse(),
                this.getSeniority(),
                this.getApplicationStatus(),
                new InterviewStatus(NOT_SCHEDULED),
                this.getAvailability()
        );
    }
}
