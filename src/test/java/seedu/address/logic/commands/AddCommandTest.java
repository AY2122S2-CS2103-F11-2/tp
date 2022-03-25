package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyInterviewSchedule;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.candidate.Candidate;
import seedu.address.model.interview.Interview;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.CandidateBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullCandidate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_candidateAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingCandidateAdded modelStub = new ModelStubAcceptingCandidateAdded();
        Candidate validCandidate = new CandidateBuilder().build();

        CommandResult commandResult = new AddCommand(validCandidate).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validCandidate), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validCandidate), modelStub.candidatesAdded);
    }

    @Test
    public void execute_duplicateCandidate_throwsCommandException() {
        Candidate validCandidate = new CandidateBuilder().build();
        AddCommand addCommand = new AddCommand(validCandidate);
        ModelStub modelStub = new ModelStubWithCandidate(validCandidate);

        assertThrows(
                CommandException.class, AddCommand.MESSAGE_DUPLICATE_CANDIDATE, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Candidate[] candidates = new SampleDataUtil().getSampleCandidates();
        Candidate alice = new CandidateBuilder().withName("Alice").build();
        Candidate bob = new CandidateBuilder().withName("Bob").build();
        Candidate charlotte = new CandidateBuilder().withName("Charlotte Oliveiro").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different candidate -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));

        assertTrue(charlotte.getName().equals(candidates[2].getName()));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getInterviewScheduleFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setInterviewScheduleFilePath(Path interviewListFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCandidate(Candidate candidate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setInterviewSchedule(ReadOnlyInterviewSchedule interviewList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyInterviewSchedule getInterviewSchedule() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCandidate(Candidate candidate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCandidate(Candidate target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCandidate(Candidate target, Candidate editedCandidate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasInterviewCandidate(Interview interview) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasConflictingInterview(Interview interview) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addInterview(Interview interview) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Candidate> getFilteredCandidateList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCandidateList(Predicate<Candidate> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSortedCandidateList(Comparator<Candidate> sortKey) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single candidate.
     */
    private class ModelStubWithCandidate extends ModelStub {
        private final Candidate candidate;

        ModelStubWithCandidate(Candidate candidate) {
            requireNonNull(candidate);
            this.candidate = candidate;
        }

        @Override
        public boolean hasCandidate(Candidate candidate) {
            requireNonNull(candidate);
            return this.candidate.isSameCandidate(candidate);
        }
    }

    /**
     * A Model stub that always accept the candidate being added.
     */
    private class ModelStubAcceptingCandidateAdded extends ModelStub {
        final ArrayList<Candidate> candidatesAdded = new ArrayList<>();

        @Override
        public boolean hasCandidate(Candidate candidate) {
            requireNonNull(candidate);
            return candidatesAdded.stream().anyMatch(candidate::isSameCandidate);
        }

        @Override
        public void addCandidate(Candidate candidate) {
            requireNonNull(candidate);
            candidatesAdded.add(candidate);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
