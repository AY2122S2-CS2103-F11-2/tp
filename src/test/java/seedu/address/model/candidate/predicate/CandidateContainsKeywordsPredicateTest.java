package seedu.address.model.candidate.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.candidate.Candidate;
import seedu.address.testutil.CandidateBuilder;

public class CandidateContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        CandidateContainsKeywordsPredicate firstPredicate =
                new CandidateContainsKeywordsPredicate(firstPredicateKeywordList);
        CandidateContainsKeywordsPredicate secondPredicate =
                new CandidateContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CandidateContainsKeywordsPredicate firstPredicateCopy =
                new CandidateContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different candidate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_candidateContainsKeywords_returnsTrue() {
        Candidate candidate = new CandidateBuilder().withName("Alice").withPhone("87654321")
                .withEmail("E0123456@u.nus.edu").withCourse("Business Analytics")
                .withStudentId("A0324444B").withAvailability("1,2,3").withSeniority("1").build();

        // One keyword
        CandidateContainsKeywordsPredicate predicate =
                new CandidateContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(candidate));

        // Multiple keywords
        predicate = new CandidateContainsKeywordsPredicate(Arrays.asList("Alice", "Business Analytics"));
        assertTrue(predicate.test(candidate));

        // Only one matching keyword
        predicate = new CandidateContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(candidate));

        // Mixed-case keywords
        predicate = new CandidateContainsKeywordsPredicate(Arrays.asList("aLIce", "e0324"));
        assertTrue(predicate.test(candidate));

        // Availability: One keyword
        predicate = new CandidateContainsKeywordsPredicate(Collections.singletonList(
                "Mon"));
        assertTrue(predicate.test(candidate));

        // Availability: Multiple keywords
        predicate = new CandidateContainsKeywordsPredicate(Arrays.asList("Mon", "Tue", "Wed"));
        assertTrue(predicate.test(candidate));

        // Mixed-case keywords
        predicate = new CandidateContainsKeywordsPredicate(Arrays.asList("moN", "TuE", "WED"));
        assertTrue(predicate.test(candidate));

        // Seniority
        predicate = new CandidateContainsKeywordsPredicate(Arrays.asList("com1"));
        assertTrue(predicate.test(candidate));

        // Phone
        predicate = new CandidateContainsKeywordsPredicate(Arrays.asList("76543"));
        assertTrue(predicate.test(candidate));

        // Email
        predicate = new CandidateContainsKeywordsPredicate(Arrays.asList("E0123456@u."));
        assertTrue(predicate.test(candidate));

        // Course
        predicate = new CandidateContainsKeywordsPredicate(Arrays.asList("business"));
        assertTrue(predicate.test(candidate));

        // Student ID
        predicate = new CandidateContainsKeywordsPredicate(Arrays.asList("444"));
        assertTrue(predicate.test(candidate));
    }

    @Test
    public void test_candidateDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        CandidateContainsKeywordsPredicate predicate = new CandidateContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new CandidateBuilder().withName("Alice").withPhone("87654321")
                .withEmail("E0123456@u.nus.edu").withCourse("Business Analytics").withStudentId("A0324444B").build()));

        // Non-matching keyword
        predicate = new CandidateContainsKeywordsPredicate(Arrays.asList("Carol Alice"));
        assertFalse(predicate.test(new CandidateBuilder().withName("Alice").withPhone("87654321")
                .withEmail("E0123456@u.nus.edu").withCourse("Business Analytics").withStudentId("A0324444B").build()));
    }
}
