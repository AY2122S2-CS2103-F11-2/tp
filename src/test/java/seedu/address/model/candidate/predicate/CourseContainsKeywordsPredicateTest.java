package seedu.address.model.candidate.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.CandidateBuilder;

public class CourseContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        CourseContainsKeywordsPredicate firstPredicate = new CourseContainsKeywordsPredicate(firstPredicateKeywordList);
        CourseContainsKeywordsPredicate secondPredicate =
                new CourseContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CourseContainsKeywordsPredicate firstPredicateCopy =
                new CourseContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different candidate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_courseContainsKeywords_returnsTrue() {
        // One keyword
        CourseContainsKeywordsPredicate predicate = new CourseContainsKeywordsPredicate(Collections.singletonList(
                "computer"));
        assertTrue(predicate.test(new CandidateBuilder().withCourse("Computer Science").build()));

        // Multiple keywords
        predicate = new CourseContainsKeywordsPredicate(Arrays.asList("Information", "Systems"));
        assertTrue(predicate.test(new CandidateBuilder().withCourse("Information Systems").build()));

        // Only one matching keyword
        predicate = new CourseContainsKeywordsPredicate(Arrays.asList("Business", "Computer"));
        assertTrue(predicate.test(new CandidateBuilder().withCourse("Business Analytics").build()));

        // Mixed-case keywords
        predicate = new CourseContainsKeywordsPredicate(Arrays.asList("AnALyTICs", "BusINESS"));
        assertTrue(predicate.test(new CandidateBuilder().withCourse("Business Analytics").build()));
    }

    @Test
    public void test_courseDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        CourseContainsKeywordsPredicate predicate = new CourseContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new CandidateBuilder().withName("Computer Science").build()));

        // Non-matching keyword
        predicate = new CourseContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new CandidateBuilder().withName("Computer Science").build()));

        // Keywords match other fields, but does/do not match course
        predicate = new CourseContainsKeywordsPredicate(Arrays.asList("87654321", "E0123456@u.nus.edu", "A0324444B",
                "Street"));
        assertFalse(predicate.test(new CandidateBuilder().withName("Alice").withPhone("87654321")
                .withEmail("E0123456@u.nus.edu").withCourse("Business Analytics").withStudentId("A0324444B").build()));
    }
}
