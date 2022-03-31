package seedu.address.model.candidate.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.candidate.Candidate;

/**
 * Tests that a {@code Candidate}'s {@code ApplicationStatus} matches any of the keywords given.
 */
public class ApplicationStatusContainsKeywordsPredicate extends ContainsKeywordsPredicate
        implements Predicate<Candidate> {

    /**
     * Creates a new {@link ApplicationStatusContainsKeywordsPredicate} object with the
     * {@link ApplicationStatusContainsKeywordsPredicate#keywords} initialised.
     * @param keywords contain keyword(s) to find in {@code Candidate}'s {@code ApplicationStatus}.
     */
    public ApplicationStatusContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    /**
     * Tests if any part of {@code Candidate}'s {@code ApplicationStatus} matches any of the specified
     * {@link ApplicationStatusContainsKeywordsPredicate#keywords}.
     * @param candidate object to retrieve the {@code ApplicationStatus}.
     * @return true if a match is found, and false otherwise.
     */
    @Override
    public boolean test(Candidate candidate) {
        return keywords.stream().anyMatch(keyword ->
                candidate.getApplicationStatus().toString().toLowerCase().contains(keyword.toLowerCase()));
    }

    /**
     * Checks if another object are instances of the same class and contains the same
     * {@link ApplicationStatusContainsKeywordsPredicate#keywords}.
     * @param other object to compare against.
     * @return true if both objects are instances of {@link ApplicationStatusContainsKeywordsPredicate} with the same
     * {@link ApplicationStatusContainsKeywordsPredicate#keywords}.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ApplicationStatusContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ApplicationStatusContainsKeywordsPredicate) other).keywords)); // state check
    }

}
