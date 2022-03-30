package seedu.address.model.candidate;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.candidate.exceptions.CandidateNotFoundException;
import seedu.address.model.candidate.exceptions.DuplicateCandidateException;

/**
 * A list of candidates that enforces uniqueness between its elements and does not allow nulls.
 * A candidate is considered unique by comparing using {@code Candidate#isSameCandidate(Candidate)}. As such, adding
 * and updating of candidates uses Candidate#isSameCandidate(Candidate) for equality so as to ensure that the
 * candidate being added or updated is unique in terms of identity in the UniqueCandidateList. However, the removal
 * of a candidate uses Candidate#equals(Object) so as to ensure that the candidate with exactly the same fields will
 * be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Candidate#isSameCandidate(Candidate)
 */
public class UniqueCandidateList implements Iterable<Candidate> {

    private final ObservableList<Candidate> internalList = FXCollections.observableArrayList();
    private final ObservableList<Candidate> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent candidate as the given argument.
     */
    public boolean contains(Candidate toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameCandidate);
    }

    /**
     * Adds a candidate to the list.
     * The candidate must not already exist in the list.
     */
    public void add(Candidate toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCandidateException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the candidate {@code target} in the list with {@code editedCandidate}.
     * {@code target} must exist in the list.
     * The candidate identity of {@code editedCandidate} must not be the same as another existing candidate in the list.
     */
    public void setCandidate(Candidate target, Candidate editedCandidate) {
        requireAllNonNull(target, editedCandidate);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new CandidateNotFoundException();
        }

        if (!target.isSameCandidate(editedCandidate) && contains(editedCandidate)) {
            throw new DuplicateCandidateException();
        }

        internalList.set(index, editedCandidate);
    }

    /**
     * Removes the equivalent candidate from the list.
     * The candidate must exist in the list.
     */
    public void remove(Candidate toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new CandidateNotFoundException();
        }
    }

    public void setCandidates(UniqueCandidateList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code candidates}.
     * {@code candidates} must not contain duplicate candidates.
     */
    public void setCandidates(List<Candidate> candidates) {
        requireAllNonNull(candidates);
        if (!candidatesAreUnique(candidates)) {
            throw new DuplicateCandidateException();
        }

        internalList.setAll(candidates);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Candidate> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Candidate> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueCandidateList // instanceof handles nulls
                        && internalList.equals(((UniqueCandidateList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code candidates} contains only unique candidates.
     */
    private boolean candidatesAreUnique(List<Candidate> candidates) {
        for (int i = 0; i < candidates.size() - 1; i++) {
            for (int j = i + 1; j < candidates.size(); j++) {
                if (candidates.get(i).isSameCandidate(candidates.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * Resets the interviewStatus of all candidates to not scheduled.
     */
    public void resetAllInterviewStatus() {
        for (Candidate c : internalList) {
            setCandidate(c, c.setInterviewStatusNotScheduled());
        }
    }
}
