package seedu.address.model.candidate.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.CandidateBuilder;

public class StudentIdContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        StudentIdContainsKeywordsPredicate firstPredicate =
                new StudentIdContainsKeywordsPredicate(firstPredicateKeywordList);
        StudentIdContainsKeywordsPredicate secondPredicate =
                new StudentIdContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        StudentIdContainsKeywordsPredicate firstPredicateCopy =
                new StudentIdContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different candidate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_studentIdContainsKeywords_returnsTrue() {
        // One keyword
        StudentIdContainsKeywordsPredicate predicate = new StudentIdContainsKeywordsPredicate(Collections.singletonList(
                "A0234"));
        assertTrue(predicate.test(new CandidateBuilder().withStudentId("A0234149B").build()));

        // Multiple keywords
        predicate = new StudentIdContainsKeywordsPredicate(Arrays.asList("A0234", "414"));
        assertTrue(predicate.test(new CandidateBuilder().withStudentId("A0234149B").build()));

        // Only one matching keyword
        predicate = new StudentIdContainsKeywordsPredicate(Arrays.asList("A0234", "414"));
        assertTrue(predicate.test(new CandidateBuilder().withStudentId("A0234119B").build()));

        // Mixed-case keywords
        predicate = new StudentIdContainsKeywordsPredicate(Arrays.asList("a0234", "A0234"));
        assertTrue(predicate.test(new CandidateBuilder().withStudentId("A0234119B").build()));
    }

    @Test
    public void test_studentIdDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        StudentIdContainsKeywordsPredicate predicate = new StudentIdContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new CandidateBuilder().withStudentId("A0234119B").build()));

        // Non-matching keyword
        predicate = new StudentIdContainsKeywordsPredicate(Arrays.asList("E0234"));
        assertFalse(predicate.test(new CandidateBuilder().withStudentId("A0234119B").build()));

        // Keywords match other fields, but does/do not match student id
        predicate = new StudentIdContainsKeywordsPredicate(Arrays.asList("87654321", "alice@email.com", "Main",
                "Street"));
        assertFalse(predicate.test(new CandidateBuilder().withName("Alice").withPhone("87654321")
                .withEmail("E0123456@u.nus.edu").withCourse("Business Analytics").withStudentId("A0324444B").build()));
    }
}
