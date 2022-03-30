package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CANDIDATES;
import static seedu.address.model.candidate.InterviewStatus.NOT_SCHEDULED;
import static seedu.address.model.candidate.InterviewStatus.SCHEDULED;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCandidates.ALICE;
import static seedu.address.testutil.TypicalCandidates.BENSON;
import static seedu.address.testutil.TypicalCandidates.getTypicalAddressBook;
import static seedu.address.testutil.TypicalInterviews.INTERVIEW_ALICE;
import static seedu.address.testutil.TypicalInterviews.INTERVIEW_BENSON;
import static seedu.address.testutil.TypicalInterviews.getTypicalInterviewSchedule;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.candidate.Candidate;
import seedu.address.model.candidate.predicate.NameContainsKeywordsPredicate;
import seedu.address.model.interview.Interview;
import seedu.address.model.interview.predicate.AllWithinTimePeriodPredicate;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.CandidateBuilder;
import seedu.address.testutil.InterviewScheduleBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
        assertEquals(new InterviewSchedule(), new InterviewSchedule(modelManager.getInterviewSchedule()));
    }

    @Test
    public void constructorIsDeletedInterviewList_notEmptyInterviewSchedule_throwsAssertionError() {
        assertThrows(AssertionError.class, () ->
                new ModelManager(getTypicalAddressBook(), getTypicalInterviewSchedule(), new UserPrefs(), true));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasCandidate_nullCandidate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasCandidate(null));
    }

    @Test
    public void hasCandidate_candidateNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasCandidate(ALICE));
    }

    @Test
    public void hasCandidate_candidateInAddressBook_returnsTrue() {
        modelManager.addCandidate(ALICE);
        assertTrue(modelManager.hasCandidate(ALICE));
    }

    @Test
    public void getFilteredCandidateList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredCandidateList().remove(0));
    }

    @Test
    public void setInterviewScheduleFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setInterviewScheduleFilePath(null));
    }

    @Test
    public void setInterviewScheduleFilePath_validPath_setsInterviewScheduleFilePath() {
        Path path = Paths.get("interview/schedule/file/path");
        modelManager.setInterviewScheduleFilePath(path);
        assertEquals(path, modelManager.getInterviewScheduleFilePath());
    }

    @Test
    public void hasInterviewCandidate_nullinterview_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasInterviewCandidate(null));
    }

    @Test
    public void hasConflictingInterview_nullinterview_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasConflictingInterview(null));
    }

    @Test
    public void hasInterviewCandidate_candidateNotInInterviewSchedule_returnsFalse() {
        assertFalse(modelManager.hasInterviewCandidate(INTERVIEW_ALICE));
    }

    @Test
    public void hasConflictingInterview_interviewNotInInterviewSchedule_returnsFalse() {
        assertFalse(modelManager.hasConflictingInterview(INTERVIEW_ALICE));
    }

    @Test
    public void hasInterviewCandidate_interviewInInterviewSchedule_returnsTrue() {
        modelManager.addInterview(INTERVIEW_ALICE);
        assertTrue(modelManager.hasInterviewCandidate(INTERVIEW_ALICE));
    }

    @Test
    public void hasConflictingInterview_interviewInInterviewSchedule_returnsTrue() {
        modelManager.addInterview(INTERVIEW_ALICE);
        assertTrue(modelManager.hasConflictingInterview(INTERVIEW_ALICE));
    }

    @Test
    public void getFilteredInterviewSchedule_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredInterviewSchedule().remove(0));
    }

    @Test
    public void getFilteredInterviewSchedule_alwaysSorted_returnsEqual() {
        AllWithinTimePeriodPredicate predicate = new AllWithinTimePeriodPredicate(LocalDateTime.now());
        modelManager.addInterview(INTERVIEW_BENSON);
        modelManager.addInterview(INTERVIEW_ALICE);
        ObservableList<Interview> notSorted = modelManager.getFilteredInterviewSchedule();
        modelManager.updateFilteredInterviewSchedule(predicate);
        ObservableList<Interview> sorted = modelManager.getFilteredInterviewSchedule();
        assertEquals(notSorted, sorted);
    }

    @Test
    public void deleteInterviewSuccess() {
        AddressBook addressBook = new AddressBookBuilder().withCandidate(ALICE).build();
        InterviewSchedule interviewSchedule = new InterviewScheduleBuilder().withInterview(INTERVIEW_ALICE).build();
        InterviewSchedule emptyInterviewSchedule = new InterviewScheduleBuilder().build();
        UserPrefs userPrefs = new UserPrefs();

        modelManager = new ModelManager(addressBook, interviewSchedule, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, emptyInterviewSchedule, userPrefs);
        modelManager.deleteInterviewForCandidate(ALICE);

        assertEquals(modelManager, modelManagerCopy);
    }

    @Test
    public void deleteCandidate_withNoInterviewScheduled() {
        AddressBook addressBook = new AddressBookBuilder().withCandidate(ALICE).withCandidate(BENSON).build();
        InterviewSchedule emptyInterviewSchedule = new InterviewScheduleBuilder().build();
        UserPrefs userPrefs = new UserPrefs();

        modelManager = new ModelManager(addressBook, emptyInterviewSchedule, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, emptyInterviewSchedule, userPrefs);
        modelManager.deleteInterviewForCandidate(ALICE);

        assertEquals(modelManager, modelManagerCopy);

    }

    @Test
    public void deleteCandidate_withNoInterviewScheduled_isDeletedInterviewList() {
        AddressBook addressBook = new AddressBookBuilder().withCandidate(ALICE).withCandidate(BENSON).build();
        InterviewSchedule emptyInterviewSchedule = new InterviewScheduleBuilder().build();
        UserPrefs userPrefs = new UserPrefs();

        modelManager = new ModelManager(addressBook, emptyInterviewSchedule, userPrefs, true);
        ModelManager modelManagerCopy = new ModelManager(addressBook, emptyInterviewSchedule, userPrefs, true);
        modelManager.deleteInterviewForCandidate(ALICE);

        assertEquals(modelManager, modelManagerCopy);

    }

    @Test
    public void candidatesNotScheduled_onStart_isDeletedInterviewList() {
        Candidate aliceScheduled = new CandidateBuilder(ALICE).withInterviewStatus(SCHEDULED).build();
        Candidate aliceNotScheduled = new CandidateBuilder(ALICE).withInterviewStatus(NOT_SCHEDULED).build();
        AddressBook addressBook1 = new AddressBookBuilder().withCandidate(aliceScheduled).build();
        AddressBook addressBook2 = new AddressBookBuilder().withCandidate(aliceNotScheduled).build();
        InterviewSchedule emptyInterviewSchedule = new InterviewScheduleBuilder().build();
        UserPrefs userPrefs = new UserPrefs();

        modelManager = new ModelManager(addressBook1, emptyInterviewSchedule, userPrefs, true);
        ModelManager modelManagerCopy = new ModelManager(addressBook2, emptyInterviewSchedule, userPrefs);

        assertEquals(modelManager, modelManagerCopy);

    }

    @Test
    public void deletePastInterviews_noPastInterviews() {
        AddressBook addressBook = new AddressBook();
        InterviewSchedule interviewSchedule = new InterviewScheduleBuilder().withInterview(INTERVIEW_ALICE).build();
        UserPrefs userPrefs = new UserPrefs();

        modelManager = new ModelManager(addressBook, interviewSchedule, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, interviewSchedule, userPrefs);
        LocalDateTime localDateTime = LocalDateTime.now();
        modelManager.deletePastInterviewsForInterviewList(localDateTime);

        assertEquals(modelManager, modelManagerCopy);
    }

    @Test
    public void deletePastInterviews_hasPastInterviews() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Interview pastInterview = new Interview(ALICE, currentDateTime);

        InterviewSchedule interviewSchedule = new InterviewScheduleBuilder().withInterview(pastInterview).build();
        InterviewSchedule emptyInterviewSchedule = new InterviewScheduleBuilder().build();
        UserPrefs userPrefs = new UserPrefs();

        modelManager = new ModelManager(getTypicalAddressBook(), interviewSchedule, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(getTypicalAddressBook(), emptyInterviewSchedule, userPrefs);
        LocalDateTime dateTimeThirtyOneMinutesIntoFuture = currentDateTime.plusMinutes(31);
        modelManager.deletePastInterviewsForInterviewList(dateTimeThirtyOneMinutesIntoFuture);
        modelManagerCopy.setCandidate(pastInterview.getCandidate(),
                pastInterview.getCandidate().triggerInterviewStatusCompleted());

        assertEquals(modelManager, modelManagerCopy);
    }

    @Test
    public void deletePastInterviews_hasBothPastAndFutureInterviews() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Interview pastInterview = new Interview(ALICE, currentDateTime);

        InterviewSchedule interviewSchedule = new InterviewScheduleBuilder()
                .withInterview(pastInterview).withInterview(INTERVIEW_BENSON).build();
        InterviewSchedule emptyInterviewSchedule = new InterviewScheduleBuilder()
                .withInterview(INTERVIEW_BENSON).build();
        UserPrefs userPrefs = new UserPrefs();

        modelManager = new ModelManager(getTypicalAddressBook(), interviewSchedule, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(getTypicalAddressBook(), emptyInterviewSchedule, userPrefs);
        LocalDateTime dateTimeThirtyOneMinutesIntoFuture = currentDateTime.plusMinutes(31);

        modelManager.deletePastInterviewsForInterviewList(dateTimeThirtyOneMinutesIntoFuture);
        modelManagerCopy.setCandidate(pastInterview.getCandidate(),
                pastInterview.getCandidate().triggerInterviewStatusCompleted());

        assertEquals(modelManager, modelManagerCopy);
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withCandidate(ALICE).withCandidate(BENSON).build();
        InterviewSchedule interviewSchedule = new InterviewScheduleBuilder().withInterview(INTERVIEW_ALICE)
                .withInterview(INTERVIEW_BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        InterviewSchedule differentInterviewSchedule = new InterviewSchedule();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, interviewSchedule, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, interviewSchedule, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook,
                differentInterviewSchedule, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredCandidateList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, interviewSchedule, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredCandidateList(PREDICATE_SHOW_ALL_CANDIDATES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, interviewSchedule, differentUserPrefs)));
    }
}
