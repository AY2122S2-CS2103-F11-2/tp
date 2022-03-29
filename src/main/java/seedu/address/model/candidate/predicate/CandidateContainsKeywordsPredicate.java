package seedu.address.model.candidate.predicate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.candidate.Candidate;

/**
 * Tests that a {@code Candidate} matches any of the keywords given.
 */
public class CandidateContainsKeywordsPredicate extends ContainsKeywordsPredicate implements Predicate<Candidate> {
    public static final String[] DAYS_IN_FULL = { "", "MON", "TUE", "WED", "THU", "FRI" };

    /**
     * Creates a new {@link CandidateContainsKeywordsPredicate} object with the
     * {@link CandidateContainsKeywordsPredicate#keywords} initialised.
     * @param keywords contain keyword(s) to find in {@code Candidate}.
     */
    public CandidateContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    /**
     * Tests if any part of {@code Candidate} matches any of the specified
     * {@link CandidateContainsKeywordsPredicate#keywords}.
     * @param candidate object to retrieve the description.
     * @return true if a match is found, and false otherwise.
     */
    @Override
    public boolean test(Candidate candidate) {
        String availability = candidate.getAvailability().toString();
        int[] availArr = Arrays.stream(availability.split(",")).mapToInt((Integer::parseInt)).toArray();
        HashMap<String, Integer> availMap = new HashMap<>();

        for (Integer i: availArr) {
            availMap.put(DAYS_IN_FULL[i], i);
        }

        return keywords.stream().anyMatch(keyword ->
                StringUtil.containsStringIgnoreCase(candidate.getApplicationStatus().toString(), keyword)
                        || StringUtil.containsStringIgnoreCase(candidate.getCourse().toString(), keyword)
                        || StringUtil.containsStringIgnoreCase(candidate.getEmail().toString(), keyword)
                        || StringUtil.containsStringIgnoreCase(candidate.getInterviewStatus().toString(), keyword)
                        || StringUtil.containsStringIgnoreCase(candidate.getName().toString(), keyword)
                        || StringUtil.containsStringIgnoreCase(candidate.getPhone().toString(), keyword)
                        || StringUtil.containsStringIgnoreCase(candidate.getSeniority().toSearchString(), keyword)
                        || StringUtil.containsStringIgnoreCase(candidate.getStudentId().toString(), keyword)
                        || availMap.containsKey(keyword.toUpperCase()));
    }

    /**
     * Checks if another object are instances of the same class and contains the same
     * {@link CandidateContainsKeywordsPredicate#keywords}.
     * @param other object to compare against.
     * @return true if both objects are instances of {@link CandidateContainsKeywordsPredicate} with the same
     * {@link CandidateContainsKeywordsPredicate#keywords}.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CandidateContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((CandidateContainsKeywordsPredicate) other).keywords)); // state check
    }

}
