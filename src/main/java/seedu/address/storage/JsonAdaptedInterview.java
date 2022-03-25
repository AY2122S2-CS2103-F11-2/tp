package seedu.address.storage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.candidate.Address;
import seedu.address.model.candidate.ApplicationStatus;
import seedu.address.model.candidate.Availability;
import seedu.address.model.candidate.Candidate;
import seedu.address.model.candidate.Course;
import seedu.address.model.candidate.Email;
import seedu.address.model.candidate.InterviewStatus;
import seedu.address.model.candidate.Name;
import seedu.address.model.candidate.Phone;
import seedu.address.model.candidate.StudentId;
import seedu.address.model.interview.Interview;
import seedu.address.model.tag.Tag;


/**
 * Jackson-friendly version of {@link Interview}.
 */
class JsonAdaptedInterview {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Interview's %s field is missing!";

    private final String studentID;
    private final String name;
    private final String phone;
    private final String email;
    private final String course;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final String applicationStatus;
    private final String interviewStatus;
    private final String availability;
    private final String interviewDateTime;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedInterview(@JsonProperty("studentID") String studentID, @JsonProperty("name") String name,
                                @JsonProperty("phone") String phone, @JsonProperty("email") String email,
                                @JsonProperty("course") String course,
                                @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                                @JsonProperty("applicationStatus") String applicationStatus,
                                @JsonProperty("interviewStatus") String interviewStatus,
                                @JsonProperty("availability") String availability,
                                @JsonProperty("interviewDateTime") String interviewDateTime) {
        this.studentID = studentID;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.course = course;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        this.applicationStatus = applicationStatus;
        this.interviewStatus = interviewStatus;
        this.availability = availability;
        this.interviewDateTime = interviewDateTime;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedInterview(Interview source) {
        studentID = source.getCandidate().getStudentId().studentId;
        name = source.getCandidate().getName().fullName;
        phone = source.getCandidate().getPhone().value;
        email = source.getCandidate().getEmail().value;
        course = source.getCandidate().getCourse().course;
        tagged.addAll(source.getCandidate().getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        applicationStatus = source.getCandidate().getApplicationStatus().toString();
        interviewStatus = source.getCandidate().getInterviewStatus().toString();
        availability = source.getCandidate().getAvailability().availability;
        interviewDateTime = source.getInterviewDateTime().toString();
    }

    /**
     * Converts this Jackson-friendly adapted interview object into the model's {@code Interview} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Interview toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (studentID == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StudentId.class.getSimpleName()));
        }
        if (!StudentId.isValidId(studentID)) {
            throw new IllegalValueException(StudentId.MESSAGE_CONSTRAINTS);
        }
        final StudentId modelId = new StudentId(studentID);

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (course == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Course.class.getSimpleName()));
        }
        if (!Course.isValidCourse(course)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Course modelCourse = new Course(course);

        if (availability == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Availability.class.getSimpleName()));
        }
        if (!Availability.isValidDay(availability)) {
            throw new IllegalValueException(Availability.MESSAGE_CONSTRAINTS);
        }
        final Availability modelAvailability = new Availability(availability);

        final Set<Tag> modelTags = new HashSet<>(personTags);

        Candidate candidate = new Candidate(modelId, modelName, modelPhone, modelEmail, modelCourse, modelTags,
                new ApplicationStatus(applicationStatus), new InterviewStatus(interviewStatus), modelAvailability);

        if (interviewDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "test"));
        }
        //watch this
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        final LocalDateTime modelInterviewDateTime = LocalDateTime.parse(interviewDateTime);
        return new Interview(candidate, modelInterviewDateTime);
    }

}
