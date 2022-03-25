package seedu.address.storage;

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
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Candidate}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String studentId;
    private final String name;
    private final String phone;
    private final String email;
    private final String course;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final String applicationStatus;
    private final String interviewStatus;
    private final String availability;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("studentId") String studentId, @JsonProperty("name") String name,
            @JsonProperty("phone") String phone, @JsonProperty("email") String email,
            @JsonProperty("course") String course, @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
            @JsonProperty("applicationStatus") String applicationStatus,
            @JsonProperty("interviewStatus") String interviewStatus,
            @JsonProperty("availability") String availability) {
        this.studentId = studentId;
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
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Candidate source) {
        studentId = source.getStudentId().studentId;
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        course = source.getCourse().course;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        applicationStatus = source.getApplicationStatus().toString();
        interviewStatus = source.getInterviewStatus().toString();
        availability = source.getAvailability().availability;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Candidate toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (studentId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StudentId.class.getSimpleName()));
        }
        if (!StudentId.isValidId(studentId)) {
            throw new IllegalValueException(StudentId.MESSAGE_CONSTRAINTS);
        }
        final StudentId modelId = new StudentId(studentId);

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
        return new Candidate(modelId, modelName, modelPhone, modelEmail, modelCourse, modelTags,
                new ApplicationStatus(applicationStatus), new InterviewStatus(interviewStatus), modelAvailability);
    }
}
