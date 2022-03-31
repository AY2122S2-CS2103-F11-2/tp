package seedu.address.model.candidate.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.candidate.Candidate;

/**
 * Tests that a {@code Candidate}'s {@code Seniority} matches any of the keywords given.
 */
public class SeniorityContainsKeywordsPredicate extends ContainsKeywordsPredicate
        implements Predicate<Candidate> {

    /**
     * Creates a new {@link SeniorityContainsKeywordsPredicate} object with the
     * {@link SeniorityContainsKeywordsPredicate#keywords} initialised.
     * @param keywords contain keyword(s) to find in {@code Candidate}'s {@code Seniority}.
     */
    public SeniorityContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    /**
     * Tests if any part of {@code Candidate}'s {@code Seniority} matches any of the specified
     * {@link SeniorityContainsKeywordsPredicate#keywords}.
     * @param candidate object to retrieve the {@code Seniority}.
     * @return true if a match is found, and false otherwise.
     */
    @Override
    public boolean test(Candidate candidate) {
        return keywords.stream().anyMatch(keyword ->
                candidate.getSeniority().toSearchString().toLowerCase().contains(keyword.toLowerCase()));
    }

    /**
     * Checks if another object are instances of the same class and contains the same
     * {@link SeniorityContainsKeywordsPredicate#keywords}.
     * @param other object to compare against.
     * @return true if both objects are instances of {@link SeniorityContainsKeywordsPredicate} with the same
     * {@link SeniorityContainsKeywordsPredicate#keywords}.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SeniorityContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((SeniorityContainsKeywordsPredicate) other).keywords)); // state check
    }

}
