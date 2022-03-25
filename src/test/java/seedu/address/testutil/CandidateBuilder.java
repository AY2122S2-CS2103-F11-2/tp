package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.candidate.ApplicationStatus;
import seedu.address.model.candidate.Availability;
import seedu.address.model.candidate.Candidate;
import seedu.address.model.candidate.Course;
import seedu.address.model.candidate.Email;
import seedu.address.model.candidate.InterviewStatus;
import seedu.address.model.candidate.Name;
import seedu.address.model.candidate.Phone;
import seedu.address.model.candidate.StudentId;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class CandidateBuilder {

    public static final String DEFAULT_STUDENT_ID = "E0123456";
    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "E0123456@u.nus.edu";
    public static final String DEFAULT_COURSE = "Computer Science";
    public static final String DEFAULT_APPLICATION_STATUS = "Pending";
    public static final String DEFAULT_INTERVIEW_STATUS = " Not Scheduled";
    public static final String DEFAULT_AVAILABILITY = "1,2,3,4,5";

    private StudentId studentId;
    private Name name;
    private Phone phone;
    private Email email;
    private Course course;
    private Set<Tag> tags;
    private ApplicationStatus applicationStatus;
    private InterviewStatus interviewStatus;
    private Availability availability;

    /**
     * Creates a {@code CandidateBuilder} with the default details.
     */
    public CandidateBuilder() {
        studentId = new StudentId(DEFAULT_STUDENT_ID);
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        course = new Course(DEFAULT_COURSE);
        tags = new HashSet<>();
        applicationStatus = new ApplicationStatus(DEFAULT_APPLICATION_STATUS);
        interviewStatus = new InterviewStatus(DEFAULT_INTERVIEW_STATUS);
        availability = new Availability(DEFAULT_AVAILABILITY);
    }

    /**
     * Initializes the CandidateBuilder with the data of {@code candidateToCopy}.
     */
    public CandidateBuilder(Candidate candidateToCopy) {
        studentId = candidateToCopy.getStudentId();
        name = candidateToCopy.getName();
        phone = candidateToCopy.getPhone();
        email = candidateToCopy.getEmail();
        course = candidateToCopy.getCourse();
        tags = new HashSet<>(candidateToCopy.getTags());
        applicationStatus = candidateToCopy.getApplicationStatus();
        interviewStatus = candidateToCopy.getInterviewStatus();
        availability = candidateToCopy.getAvailability();
    }

    /**
     * Sets the {@code StudentId} of the {@code Candidate} that we are building.
     */
    public CandidateBuilder withStudentId(String id) {
        this.studentId = new StudentId(id);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public CandidateBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public CandidateBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public CandidateBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public CandidateBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Course} of the {@code Candidate} that we are building.
     */
    public CandidateBuilder withCourse(String course) {
        this.course = new Course(course);
        return this;
    }

    /**
     * Sets the {@code Application Status} of the {@code Candidate} that we are building.
     */
    public CandidateBuilder withApplicationStatus(String applicationStatus) {
        this.applicationStatus = new ApplicationStatus(applicationStatus);
        return this;
    }

    /**
     * Sets the {@code Interview Status} of the {@code Candidate} that we are building.
     */
    public CandidateBuilder withInterviewStatus(String interviewStatus) {
        this.interviewStatus = new InterviewStatus(interviewStatus);
        return this;
    }

    /**
     * Sets the {@code Availability} of the {@code Person} that we are building.
     */
    public CandidateBuilder withAvailability(String availability) {
        this.availability = new Availability(availability);
        return this;
    }

    /**
     * Returns a new Person with specific fields.
     * @return a new Person.
     */
    public Candidate build() {
        return new Candidate(studentId, name, phone, email, course, tags,
                applicationStatus, interviewStatus, availability);
    }

}
