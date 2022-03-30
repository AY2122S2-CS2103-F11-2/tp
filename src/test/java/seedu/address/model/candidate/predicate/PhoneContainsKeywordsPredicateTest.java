package seedu.address.model.candidate.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.CandidateBuilder;

public class PhoneContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PhoneContainsKeywordsPredicate firstPredicate = new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        PhoneContainsKeywordsPredicate secondPredicate = new PhoneContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneContainsKeywordsPredicate firstPredicateCopy =
                new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different candidate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        // One keyword
        PhoneContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate(Collections.singletonList(
                "9235"));
        assertTrue(predicate.test(new CandidateBuilder().withPhone("92352677").build()));

        // Multiple keywords
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("9235", "2677"));
        assertTrue(predicate.test(new CandidateBuilder().withPhone("92352677").build()));

        // Only one matching keyword
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("9235", "11111"));
        assertTrue(predicate.test(new CandidateBuilder().withPhone("92352677").build()));
    }

    @Test
    public void test_phoneDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PhoneContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new CandidateBuilder().withPhone("92352677").build()));

        // Non-matching keyword
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("92352678"));
        assertFalse(predicate.test(new CandidateBuilder().withPhone("92352677").build()));

        // Spaces in keywords
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("9235 2677"));
        assertFalse(predicate.test(new CandidateBuilder().withPhone("92352677").build()));

        // Keywords match other fields, but does/do not match phone
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("Business Analytics", "alice@email.com", "Main",
                "Street"));
        assertFalse(predicate.test(new CandidateBuilder().withName("Alice").withPhone("87654321")
                .withEmail("E0123456@u.nus.edu").withCourse("Business Analytics").withStudentId("A0324444B").build()));
    }
}
