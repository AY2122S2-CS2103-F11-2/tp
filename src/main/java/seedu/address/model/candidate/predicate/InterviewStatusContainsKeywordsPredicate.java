package seedu.address.model.candidate.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.candidate.Candidate;

/**
 * Tests that a {@code Candidate}'s {@code InterviewStatus} matches any of the keywords given.
 */
public class InterviewStatusContainsKeywordsPredicate extends ContainsKeywordsPredicate
        implements Predicate<Candidate> {

    /**
     * Creates a new {@link InterviewStatusContainsKeywordsPredicate} object with the
     * {@link InterviewStatusContainsKeywordsPredicate#keywords} initialised.
     * @param keywords contain keyword(s) to find in {@code Candidate}'s {@code InterviewStatus}.
     */
    public InterviewStatusContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    /**
     * Tests if any part of {@code Candidate}'s {@code InterviewStatus} matches any of the specified
     * {@link InterviewStatusContainsKeywordsPredicate#keywords}.
     * @param candidate object to retrieve the {@code InterviewStatus}.
     * @return true if a match is found, and false otherwise.
     */
    @Override
    public boolean test(Candidate candidate) {
        return keywords.stream().anyMatch(keyword ->
                candidate.getInterviewStatus().toString().toLowerCase().contains(keyword.toLowerCase()));
    }

    /**
     * Checks if another object are instances of the same class and contains the same
     * {@link InterviewStatusContainsKeywordsPredicate#keywords}.
     * @param other object to compare against.
     * @return true if both objects are instances of {@link InterviewStatusContainsKeywordsPredicate} with the same
     * {@link InterviewStatusContainsKeywordsPredicate#keywords}.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InterviewStatusContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((InterviewStatusContainsKeywordsPredicate) other).keywords)); // state check
    }

}
