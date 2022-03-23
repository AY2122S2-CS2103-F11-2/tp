package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPLICATION_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVAILABILITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVIEW_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
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
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_ID + "ID] "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_COURSE + "COURSE] "
            + "[" + PREFIX_APPLICATION_STATUS + "APPLICATION STATUS] "
            + "[" + PREFIX_INTERVIEW_STATUS + "INTERVIEW STATUS] "
            + "[" + PREFIX_AVAILABILITY + "AVAILABILITY]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "E0123456@u.nus.edu";

    public static final String MESSAGE_EDIT_CANDIDATE_SUCCESS = "Edited Candidate: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CANDIDATE = "This candidate already exists in the system";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Candidate> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Candidate candidateToEdit = lastShownList.get(index.getZeroBased());
        Candidate editedCandidate = createEditedPerson(candidateToEdit, editPersonDescriptor);

        if (!candidateToEdit.isSamePerson(editedCandidate) && model.hasPerson(editedCandidate)) {
            throw new CommandException(MESSAGE_DUPLICATE_CANDIDATE);
        }

        model.setPerson(candidateToEdit, editedCandidate);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_CANDIDATE_SUCCESS, editedCandidate));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code candidateToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Candidate createEditedPerson(Candidate candidateToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert candidateToEdit != null;

        StudentId updatedID = editPersonDescriptor.getStudentId().orElse(candidateToEdit.getStudentId());
        Name updatedName = editPersonDescriptor.getName().orElse(candidateToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(candidateToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(candidateToEdit.getEmail());
        Course updatedCourse = editPersonDescriptor.getCourse().orElse(candidateToEdit.getCourse());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(candidateToEdit.getTags());
        ApplicationStatus applicationStatus = editPersonDescriptor.getApplicationStatus()
                .orElse(candidateToEdit.getApplicationStatus());
        InterviewStatus interviewStatus = editPersonDescriptor.getInterviewStatus()
                .orElse(candidateToEdit.getInterviewStatus());
        Availability updatedAvailability = editPersonDescriptor.getAvailability()
                .orElse(candidateToEdit.getAvailability());

        return new Candidate(updatedID, updatedName, updatedPhone, updatedEmail,
                updatedCourse, updatedTags, applicationStatus, interviewStatus, updatedAvailability);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {

        private static final String ACCEPTED_APPLICATION = "Accepted";
        private static final String REJECTED_APPLICATION = "Rejected";
        private static final String COMPLETED_INTERVIEW = "Completed";

        private StudentId studentId;
        private Name name;
        private Phone phone;
        private Email email;
        private Course course;

        private Set<Tag> tags;
        private ApplicationStatus applicationStatus;
        private InterviewStatus interviewStatus;
        private Availability availability;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setStudentId(toCopy.studentId);
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setCourse(toCopy.course);
            setTags(toCopy.tags);
            setApplicationStatus(toCopy.applicationStatus);
            setInterviewStatus(toCopy.interviewStatus);
            setAvailability(toCopy.availability);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(studentId, name, phone, email, course, tags,
                    applicationStatus, interviewStatus, availability);
        }

        public void setStudentId(StudentId id) {
            this.studentId = id;
        }

        public Optional<StudentId> getStudentId() {
            return Optional.ofNullable(studentId);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setCourse(Course course) {
            this.course = course;
        }

        public Optional<Course> getCourse() {
            return Optional.ofNullable(course);
        }

        public void setApplicationStatus(ApplicationStatus applicationStatus) {
            triggerInterviewStatus(applicationStatus);
            this.applicationStatus = applicationStatus;
        }

        public Optional<ApplicationStatus> getApplicationStatus() {
            return Optional.ofNullable(applicationStatus);
        }

        public void setInterviewStatus(InterviewStatus interviewStatus) {
            this.interviewStatus = interviewStatus;
        }

        public Optional<InterviewStatus> getInterviewStatus() {
            return Optional.ofNullable(interviewStatus);
        }

        public void setAvailability(Availability availability) {
            this.availability = availability;
        }

        public Optional<Availability> getAvailability() {
            return Optional.ofNullable(availability);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getStudentId().equals(e.getStudentId())
                    && getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getCourse().equals(e.getCourse())
                    && getTags().equals(e.getTags())
                    && getApplicationStatus().equals(e.getApplicationStatus())
                    && getInterviewStatus().equals(e.getInterviewStatus())
                    && getAvailability().equals(e.getAvailability());
        }

        /**
         * This method will trigger the {@code InterviewStatus} to be completed if
         * {@code ApplicationStatus} is Accepted or Rejected.
         */
        public void triggerInterviewStatus(ApplicationStatus applicationStatus) {
            if (getInterviewStatus().isPresent()) {
                if (applicationStatus.toString().equals(ACCEPTED_APPLICATION)
                        || applicationStatus.toString().equals(REJECTED_APPLICATION)) {
                    this.setInterviewStatus(new InterviewStatus(COMPLETED_INTERVIEW));
                }
            }
        }
    }
}
