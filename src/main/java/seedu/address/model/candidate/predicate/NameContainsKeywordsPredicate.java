package seedu.address.model.candidate.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.candidate.Candidate;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate extends ContainsKeywordsPredicate implements Predicate<Candidate> {
    private final List<String> keywords;

    /**
     * Creates a new {@link NameContainsKeywordsPredicate} object with the
     * {@link NameContainsKeywordsPredicate#keywords} initialised.
     * @param keywords contain keyword(s) to find in {@code Person}'s {@code Name}.
     */
    public NameContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
        this.keywords = keywords;
    }

    /**
     * Tests if any part of {@code Person}'s {@code Name} matches any of the specified
     * {@link NameContainsKeywordsPredicate#keywords}.
     * @param candidate object to retrieve the {@code Name}.
     * @return true if a match is found, and false otherwise.
     */
    @Override
    public boolean test(Candidate candidate) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsStringIgnoreCase(candidate.getName().fullName, keyword));
    }

    /**
     * Checks if another object are instances of the same class and contains the same
     * {@link NameContainsKeywordsPredicate#keywords}.
     * @param other object to compare against.
     * @return true if both objects are instances of {@link NameContainsKeywordsPredicate} with the same
     * {@link NameContainsKeywordsPredicate#keywords}.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}