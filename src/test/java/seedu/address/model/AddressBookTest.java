package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPLICATION_PENDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVAILABILITY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERVIEW_NOT_SCHEDULED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.candidate.Candidate;
import seedu.address.model.candidate.exceptions.DuplicatePersonException;
import seedu.address.testutil.CandidateBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Candidate editedAlice = new CandidateBuilder(ALICE).withCourse(VALID_COURSE_BOB).withTags(VALID_TAG_HUSBAND)
                .withApplicationStatus(VALID_APPLICATION_PENDING)
                .withInterviewStatus(VALID_INTERVIEW_NOT_SCHEDULED)
                .withAvailability(VALID_AVAILABILITY_BOB)
                .build();
        List<Candidate> newCandidates = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newCandidates);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void sortPersons_basedOnName_returnsSortedList() {
        ObservableList<Candidate> newData = addressBook.getPersonList();
        List<Candidate> personsCopy = new ArrayList<Candidate>(newData);
        Comparator<Candidate> sortComparator = Comparator.comparing(l -> l.getName().toString().toLowerCase());
        personsCopy.sort(sortComparator);
        addressBook.setPersons(newData);
        addressBook.sortPersons(newData, sortComparator);

        assertEquals(personsCopy, addressBook.getPersonList());
    }


    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Candidate editedAlice = new CandidateBuilder(ALICE).withCourse(VALID_COURSE_BOB).withTags(VALID_TAG_HUSBAND)
                .withAvailability(VALID_AVAILABILITY_BOB).build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Candidate> candidates = FXCollections.observableArrayList();

        AddressBookStub(Collection<Candidate> candidates) {
            this.candidates.setAll(candidates);
        }

        @Override
        public ObservableList<Candidate> getPersonList() {
            return candidates;
        }
    }

}
