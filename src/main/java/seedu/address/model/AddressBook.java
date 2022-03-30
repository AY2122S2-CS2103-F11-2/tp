package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.candidate.Candidate;
import seedu.address.model.candidate.UniqueCandidateList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameCandidate comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueCandidateList candidates;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        candidates = new UniqueCandidateList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Candidates in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the candidate list with {@code candidates}.
     * {@code candidates} must not contain duplicate candidates.
     */
    public void setCandidates(List<Candidate> candidates) {
        this.candidates.setCandidates(candidates);
    }

    /**
     * Reorders the contents of the candidate list by comparator on {@code sortKey}.
     * {@code candidates} must not contain duplicate candidates.
     */
    public void sortCandidates(Comparator<Candidate> sortComparator) {
        requireNonNull(sortComparator);
        List<Candidate> candidatesCopy = new ArrayList<Candidate>(this.getCandidateList());
        candidatesCopy.sort(sortComparator);
        this.candidates.setCandidates(candidatesCopy);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setCandidates(newData.getCandidateList());
    }

    //// candidate-level operations

    /**
     * Returns true if a candidate with the same identity as {@code candidate} exists in the address book.
     */
    public boolean hasCandidate(Candidate candidate) {
        requireNonNull(candidate);
        return candidates.contains(candidate);
    }

    /**
     * Adds a candidate to the address book.
     * The candidate must not already exist in the address book.
     */
    public void addCandidate(Candidate p) {
        candidates.add(p);
    }

    /**
     * Replaces the given candidate {@code target} in the list with {@code editedCandidate}.
     * {@code target} must exist in the address book.
     * The candidate identity of {@code editedCandidate} must not be the same as another existing candidate in the
     * address book.
     */
    public void setCandidate(Candidate target, Candidate editedCandidate) {
        requireNonNull(editedCandidate);

        candidates.setCandidate(target, editedCandidate);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeCandidate(Candidate key) {
        candidates.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return candidates.asUnmodifiableObservableList().size() + " candidates";
        // TODO: refine later
    }

    @Override
    public ObservableList<Candidate> getCandidateList() {
        return candidates.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && candidates.equals(((AddressBook) other).candidates));
    }

    @Override
    public int hashCode() {
        return candidates.hashCode();
    }

    public void resetAllInterviewStatus() {
        candidates.resetAllInterviewStatus();
    }
}
