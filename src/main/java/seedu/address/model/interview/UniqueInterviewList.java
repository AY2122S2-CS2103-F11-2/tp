package seedu.address.model.interview;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.interview.exceptions.ConflictingInterviewException;
import seedu.address.model.interview.exceptions.DuplicateInterviewException;
import seedu.address.model.interview.exceptions.InterviewNotFoundException;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class UniqueInterviewList implements Iterable<Interview> {
    private final ObservableList<Interview> internalList = FXCollections.observableArrayList();
    private final ObservableList<Interview> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Interview toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameInterview);
    }

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean containsConflictingInterview(Interview toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isConflictingInterview);
    }
    /**
     * Adds an interview to the list.
     * The person must not already exist in the list.
     */
    public void add(Interview toAdd) {
        requireNonNull(toAdd);
        if (containsConflictingInterview(toAdd)) {
            throw new DuplicateInterviewException();
        }
        internalList.add(toAdd);
    }
    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setInterview(Interview target, Interview editedInterview) {
        requireAllNonNull(target, editedInterview);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new InterviewNotFoundException();
        }

        if (!target.isSameInterview(editedInterview)) {
            throw new DuplicateInterviewException();
        }

        if (containsConflictingInterview(editedInterview)) {
            throw new ConflictingInterviewException();
        }

        internalList.set(index, editedInterview);
    }

    /**
     * Removes the equivalent interview from the list.
     * The interview must exist in the list.
     */
    public void remove(Interview toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new InterviewNotFoundException();
        }
    }

    /*public void setInterviews(UniqueInterviewList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }*/

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setInterviews(List<Interview> interviews) {
        requireAllNonNull(interviews);
        if (!interviewsAreUnique(interviews)) {
            throw new DuplicateInterviewException();
        }

        internalList.setAll(interviews);
    }
    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Interview> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueInterviewList // instanceof handles nulls
                && internalList.equals(((UniqueInterviewList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public Iterator<Interview> iterator() {
        return internalList.iterator();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean interviewsAreUnique(List<Interview> interviews) {
        for (int i = 0; i < interviews.size() - 1; i++) {
            for (int j = i + 1; j < interviews.size(); j++) {
                if (interviews.get(i).isConflictingInterview(interviews.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
