package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.ScheduleCommand.MESSAGE_INVALID_FORMAT_DATETIME;
import static seedu.address.logic.commands.ScheduleCommand.MESSAGE_INVALID_PAST_DATETIME;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.candidate.Address;
import seedu.address.model.candidate.ApplicationStatus;
import seedu.address.model.candidate.Availability;
import seedu.address.model.candidate.Course;
import seedu.address.model.candidate.Email;
import seedu.address.model.candidate.InterviewStatus;
import seedu.address.model.candidate.Name;
import seedu.address.model.candidate.Phone;
import seedu.address.model.candidate.StudentId;
import seedu.address.model.interview.Interview;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String id} into a {@code StudentId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code id} is invalid.
     */
    public static StudentId parseStudentId(String id) throws ParseException {
        requireNonNull(id);
        String updatedId = id.trim().toUpperCase();
        if (!StudentId.isValidId(updatedId)) {
            throw new ParseException(StudentId.MESSAGE_CONSTRAINTS);
        }
        return new StudentId(updatedId);
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String course} into a {@code Course}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code course} is invalid.
     */
    public static Course parseCourse(String course) throws ParseException {
        requireNonNull(course);
        String trimmedCourse = course.trim();
        if (!Course.isValidCourse(trimmedCourse)) {
            throw new ParseException(Course.MESSAGE_CONSTRAINTS);
        }
        return new Course(trimmedCourse);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String applicationStatus} into an {@code ApplicationStatus}.
     *
     * @param applicationStatus the application status
     * @return the ApplicationStatus of the string
     * @throws ParseException if the given {@code applicationStatus} is invalid.
     */
    public static ApplicationStatus parseApplicationStatus (String applicationStatus) throws ParseException {
        requireNonNull(applicationStatus);
        String trimmedStatus = applicationStatus.trim();
        if (!ApplicationStatus.isValidStatus(trimmedStatus)) {
            throw new ParseException(ApplicationStatus.MESSAGE_CONSTRAINTS);
        }
        return new ApplicationStatus(trimmedStatus);
    }

    /**
     * Parses a {@code String interviewStatus} into an {@code InterviewStatus}.
     *
     * @param interviewStatus the application status
     * @return the InterviewStatus of the string
     * @throws ParseException if the given {@code interviewStatus} is invalid.
     */
    public static InterviewStatus parseInterviewStatus (String interviewStatus) throws ParseException {
        requireNonNull(interviewStatus);
        String trimmedStatus = interviewStatus.trim();
        if (!InterviewStatus.isValidStatus(trimmedStatus)) {
            throw new ParseException(InterviewStatus.MESSAGE_CONSTRAINTS);
        }
        return new InterviewStatus(trimmedStatus);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Availability parseAvailability(String availability) throws ParseException {
        requireNonNull(availability);
        String trimmedAvailability = availability.trim();
        if (!Availability.isValidDay(trimmedAvailability)) {
            throw new ParseException(Availability.MESSAGE_CONSTRAINTS);
        }
        return new Availability(trimmedAvailability);
    }

    /**
     * Parses a {@code String dateTime} into a {@code LocalDateTime}.
     *
     * @param dateTime The given interview date and time.
     * @return the LocalDateTime of the string.
     * @throws ParseException if the given {@code dateTime} is in the past or has an invalid format.
     */
    public static LocalDateTime parseDateTime(String dateTime) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime formattedDateTime;
        try {
            formattedDateTime = LocalDateTime.parse(dateTime, formatter);
        } catch (DateTimeParseException e) {
            throw new ParseException(MESSAGE_INVALID_FORMAT_DATETIME);
        }
        if (!Interview.isValidDateTime(formattedDateTime)) {
            throw new ParseException(MESSAGE_INVALID_PAST_DATETIME);
        }
        return formattedDateTime;
    }
}
