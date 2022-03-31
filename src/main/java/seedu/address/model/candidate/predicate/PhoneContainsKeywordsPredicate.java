package seedu.address.model.candidate.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.candidate.Candidate;

/**
 * Tests that a {@code Candidate}'s {@code Phone} matches any of the keywords given.
 */
public class PhoneContainsKeywordsPredicate extends ContainsKeywordsPredicate implements Predicate<Candidate> {

    /**
     * Creates a new {@link PhoneContainsKeywordsPredicate} object with the
     * {@link PhoneContainsKeywordsPredicate#keywords} initialised.
     * @param keywords contain keyword(s) to find in {@code Candidate}'s {@code Phone}.
     */
    public PhoneContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    /**
     * Tests if any part of {@code Candidate}'s {@code Phone} matches any of the specified
     * {@link PhoneContainsKeywordsPredicate#keywords}.
     * @param candidate object to retrieve the {@code Phone}.
     * @return true if a match is found, and false otherwise.
     */
    @Override
    public boolean test(Candidate candidate) {
        return keywords.stream()
                .anyMatch(keyword -> candidate.getPhone().toString().toLowerCase().contains(keyword.toLowerCase()));
    }

    /**
     * Checks if another object are instances of the same class and contains the same
     * {@link PhoneContainsKeywordsPredicate#keywords}.
     * @param other object to compare against.
     * @return true if both objects are instances of {@link PhoneContainsKeywordsPredicate} with the same
     * {@link PhoneContainsKeywordsPredicate#keywords}.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((PhoneContainsKeywordsPredicate) other).keywords)); // state check
    }

}
