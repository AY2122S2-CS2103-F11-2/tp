package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.candidate.Candidate;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class CandidateUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Candidate candidate) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(candidate);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Candidate candidate) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_ID + candidate.getStudentID().studentID + " ");
        sb.append(PREFIX_NAME + candidate.getName().fullName + " ");
        sb.append(PREFIX_PHONE + candidate.getPhone().value + " ");
        sb.append(PREFIX_COURSE + candidate.getCourse().course + " ");
        candidate.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getStudentID().ifPresent(id -> sb.append(PREFIX_ID).append(id.studentID).append(" "));
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getCourse().ifPresent(course -> sb.append(PREFIX_COURSE).append(course.course).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
