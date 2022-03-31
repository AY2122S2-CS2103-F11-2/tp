package seedu.address.model.candidate.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.candidate.Candidate;

/**
 * Tests that a {@code Candidate}'s {@code Course} matches any of the keywords given.
 */
public class CourseContainsKeywordsPredicate extends ContainsKeywordsPredicate implements Predicate<Candidate> {

    /**
     * Creates a new {@link CourseContainsKeywordsPredicate} object with the
     * {@link CourseContainsKeywordsPredicate#keywords} initialised.
     * @param keywords contain keyword(s) to find in {@code Candidate}'s {@code Course}.
     */
    public CourseContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    /**
     * Tests if any part of {@code Candidate}'s {@code Course} matches any of the specified
     * {@link CourseContainsKeywordsPredicate#keywords}.
     * @param candidate object to retrieve the {@code Course}.
     * @return true if a match is found, and false otherwise.
     */
    @Override
    public boolean test(Candidate candidate) {
        return keywords.stream()
                .anyMatch(keyword -> candidate.getCourse().toString().toLowerCase().contains(keyword.toLowerCase()));
    }

    /**
     * Checks if another object are instances of the same class and contains the same
     * {@link CourseContainsKeywordsPredicate#keywords}.
     * @param other object to compare against.
     * @return true if both objects are instances of {@link CourseContainsKeywordsPredicate} with the same
     * {@link CourseContainsKeywordsPredicate#keywords}.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CourseContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((CourseContainsKeywordsPredicate) other).keywords)); // state check
    }

}
