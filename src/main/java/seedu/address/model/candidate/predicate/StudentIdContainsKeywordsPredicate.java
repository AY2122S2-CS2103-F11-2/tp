package seedu.address.model.candidate.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.candidate.Candidate;

/**
 * Tests that a {@code Candidate}'s {@code StudentId} matches any of the keywords given.
 */
public class StudentIdContainsKeywordsPredicate extends ContainsKeywordsPredicate implements Predicate<Candidate> {

    /**
     * Creates a new {@link StudentIdContainsKeywordsPredicate} object with the
     * {@link StudentIdContainsKeywordsPredicate#keywords} initialised.
     * @param keywords contain keyword(s) to find in {@code Candidate}'s {@code StudentId}.
     */
    public StudentIdContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    /**
     * Tests if any part of {@code Candidate}'s {@code StudentId} matches any of the specified
     * {@link StudentIdContainsKeywordsPredicate#keywords}.
     * @param candidate object to retrieve the {@code StudentID}.
     * @return true if a match is found, and false otherwise.
     */
    @Override
    public boolean test(Candidate candidate) {
        return keywords.stream()
                .anyMatch(keyword -> candidate.getStudentId().toString().toLowerCase().contains(keyword.toLowerCase()));
    }

    /**
     * Checks if another object are instances of the same class and contains the same
     * {@link StudentIdContainsKeywordsPredicate#keywords}.
     * @param other object to compare against.
     * @return true if both objects are instances of {@link StudentIdContainsKeywordsPredicate} with the same
     * {@link StudentIdContainsKeywordsPredicate#keywords}.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StudentIdContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((StudentIdContainsKeywordsPredicate) other).keywords)); // state check
    }

}
