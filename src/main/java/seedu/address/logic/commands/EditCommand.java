package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPLICATION_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVAILABILITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SENIORITY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CANDIDATES;

import java.util.List;
import java.util.Optional;

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
import seedu.address.model.candidate.Remark;
import seedu.address.model.candidate.Seniority;
import seedu.address.model.candidate.StudentId;
import seedu.address.model.interview.Interview;

/**
 * Edits the details of an existing candidate in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String REFRESH_MESSAGE = "Note: To view updated Candidate's profile, refresh "
            + "the candidate's information by using the Focus Command.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the candidate identified "
            + "by the index number used in the displayed candidate list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX "
            + "[" + PREFIX_ID + "ID] "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_COURSE + "COURSE] "
            + "[" + PREFIX_SENIORITY + "SENIORITY] "
            + "[" + PREFIX_APPLICATION_STATUS + "APPLICATION_STATUS] "
            + "[" + PREFIX_AVAILABILITY + "AVAILABILITY]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "E0123456@u.nus.edu\n"
            + "Note: Validity checks will need to be met for certain fields. See the user guide for full details.";

    public static final String MESSAGE_EDIT_CANDIDATE_SUCCESS = "Edited Candidate: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided";
    public static final String MESSAGE_DUPLICATE_CANDIDATE = "This candidate already exists in the system";
    public static final String MESSAGE_CONFLICTED_AVAILABILITY =
            "This candidate already has an interview scheduled on his/her available day that was previously added"
            + "\nPlease consider re-scheduling the interview before making this change";

    private final Index index;
    private final EditCandidateDescriptor editCandidateDescriptor;

    /**
     * @param index of the candidate in the filtered candidate list to edit
     * @param editCandidateDescriptor details to edit the candidate with
     */
    public EditCommand(Index index, EditCandidateDescriptor editCandidateDescriptor) {
        requireNonNull(index);
        requireNonNull(editCandidateDescriptor);

        this.index = index;
        this.editCandidateDescriptor = new EditCandidateDescriptor(editCandidateDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Candidate> lastShownList = model.getFilteredCandidateList();
        List<Interview> interviewSchedule = model.getFilteredInterviewSchedule();
        if (lastShownList.isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_NO_CANDIDATES_DISPLAYED));
        }
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CANDIDATE_DISPLAYED_INDEX);
        }
        Candidate candidateToEdit = lastShownList.get(index.getZeroBased());
        Candidate editedCandidate = createEditedCandidate(candidateToEdit, editCandidateDescriptor);

        if (!candidateToEdit.isSameCandidate(editedCandidate) && model.hasCandidate(editedCandidate)) {
            throw new CommandException(MESSAGE_DUPLICATE_CANDIDATE);
        }

        if (model.hasInterview(editedCandidate)) {
            throw new CommandException(MESSAGE_CONFLICTED_AVAILABILITY);
        }

        model.setCandidate(candidateToEdit, editedCandidate);

        for (int i = 0; i < interviewSchedule.size(); i++) {
            if (candidateToEdit.equals(interviewSchedule.get(i).getCandidate())) {
                Interview interviewToUpdate = interviewSchedule.get(i);
                Interview updatedInterview = new Interview(editedCandidate, interviewToUpdate.getInterviewDateTime());
                model.updateInterviewCandidate(interviewToUpdate, updatedInterview);
            }
        }

        model.updateFilteredCandidateList(PREDICATE_SHOW_ALL_CANDIDATES);
        return new CommandResult(String.format(MESSAGE_EDIT_CANDIDATE_SUCCESS,
                editedCandidate + "\n" + REFRESH_MESSAGE));
    }

    /**
     * Creates and returns a {@code Candidate} with the details of {@code candidateToEdit}
     * edited with {@code editCandidateDescriptor}.
     */
    private static Candidate createEditedCandidate(
            Candidate candidateToEdit, EditCandidateDescriptor editCandidateDescriptor) {
        assert candidateToEdit != null;

        StudentId updatedID = editCandidateDescriptor.getStudentId().orElse(candidateToEdit.getStudentId());
        Name updatedName = editCandidateDescriptor.getName().orElse(candidateToEdit.getName());
        Phone updatedPhone = editCandidateDescriptor.getPhone().orElse(candidateToEdit.getPhone());
        Email updatedEmail = editCandidateDescriptor.getEmail().orElse(candidateToEdit.getEmail());
        Course updatedCourse = editCandidateDescriptor.getCourse().orElse(candidateToEdit.getCourse());
        Seniority updatedSeniority = editCandidateDescriptor.getSeniority().orElse(candidateToEdit.getSeniority());
        ApplicationStatus applicationStatus = editCandidateDescriptor.getApplicationStatus()
                .orElse(candidateToEdit.getApplicationStatus());
        InterviewStatus interviewStatus = editCandidateDescriptor.getInterviewStatus()
                .orElse(candidateToEdit.getInterviewStatus());
        Availability updatedAvailability = editCandidateDescriptor.getAvailability()
                .orElse(candidateToEdit.getAvailability());
        Remark updatedRemark = candidateToEdit.getRemark(); // edit command does not allow editing remarks

        return new Candidate(updatedID, updatedName, updatedPhone, updatedEmail,
                updatedCourse, updatedSeniority, applicationStatus, interviewStatus,
                updatedAvailability, updatedRemark);
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
                && editCandidateDescriptor.equals(e.editCandidateDescriptor);
    }

    /**
     * Stores the details to edit the candidate with. Each non-empty field value will replace the
     * corresponding field value of the candidate.
     */
    public static class EditCandidateDescriptor {
        private StudentId studentId;
        private Name name;
        private Phone phone;
        private Email email;
        private Course course;
        private Seniority seniority;
        private ApplicationStatus applicationStatus;
        private InterviewStatus interviewStatus;
        private Availability availability;

        public EditCandidateDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditCandidateDescriptor(EditCandidateDescriptor toCopy) {
            setStudentId(toCopy.studentId);
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setCourse(toCopy.course);
            setSeniority(toCopy.seniority);
            setApplicationStatus(toCopy.applicationStatus);
            setInterviewStatus(toCopy.interviewStatus);
            setAvailability(toCopy.availability);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(studentId, name, phone, email, course, seniority,
                    applicationStatus, availability);
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

        public void setSeniority(Seniority seniority) {
            this.seniority = seniority;
        }

        public Optional<Seniority> getSeniority() {
            return Optional.ofNullable(seniority);
        }

        public void setApplicationStatus(ApplicationStatus applicationStatus) {
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

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCandidateDescriptor)) {
                return false;
            }

            // state check
            EditCandidateDescriptor e = (EditCandidateDescriptor) other;

            return getStudentId().equals(e.getStudentId())
                    && getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getCourse().equals(e.getCourse())
                    && getSeniority().equals(e.getSeniority())
                    && getApplicationStatus().equals(e.getApplicationStatus())
                    && getAvailability().equals(e.getAvailability());
        }

    }
}
