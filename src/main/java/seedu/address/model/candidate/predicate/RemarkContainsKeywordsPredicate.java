package seedu.address.model.candidate.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.candidate.Candidate;

/**
 * Tests that a {@code Candidate}'s {@code Remark} matches any of the keywords given.
 */
public class RemarkContainsKeywordsPredicate extends ContainsKeywordsPredicate implements Predicate<Candidate> {

    /**
     * Creates a new {@link RemarkContainsKeywordsPredicate} object with the
     * {@link RemarkContainsKeywordsPredicate#keywords} initialised.
     * @param keywords contain keyword(s) to find in {@code Candidate}'s {@code Remark}.
     */
    public RemarkContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    /**
     * Tests if any part of {@code Candidate}'s {@code Remark} matches any of the specified
     * {@link RemarkContainsKeywordsPredicate#keywords}.
     * @param candidate object to retrieve the {@code Remark}.
     * @return true if a match is found, and false otherwise.
     */
    @Override
    public boolean test(Candidate candidate) {
        return keywords.stream()
                .anyMatch(keyword -> candidate.getRemark().toString().toLowerCase().contains(keyword.toLowerCase()));
    }

    /**
     * Checks if another object are instances of the same class and contains the same
     * {@link RemarkContainsKeywordsPredicate#keywords}.
     * @param other object to compare against.
     * @return true if both objects are instances of {@link RemarkContainsKeywordsPredicate} with the same
     * {@link RemarkContainsKeywordsPredicate#keywords}.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemarkContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((RemarkContainsKeywordsPredicate) other).keywords)); // state check
    }

}
