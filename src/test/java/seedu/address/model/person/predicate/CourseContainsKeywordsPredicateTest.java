package seedu.address.model.person.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

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

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_courseContainsKeywords_returnsTrue() {
        // One keyword
        CourseContainsKeywordsPredicate predicate = new CourseContainsKeywordsPredicate(Collections.singletonList(
                "computer"));
        assertTrue(predicate.test(new PersonBuilder().withCourse("Computer Science").build()));

        // Multiple keywords
        predicate = new CourseContainsKeywordsPredicate(Arrays.asList("Information", "Systems"));
        assertTrue(predicate.test(new PersonBuilder().withCourse("Information Systems").build()));

        // Only one matching keyword
        predicate = new CourseContainsKeywordsPredicate(Arrays.asList("Business", "Computer"));
        assertTrue(predicate.test(new PersonBuilder().withCourse("Business Analytics").build()));

        // Mixed-case keywords
        predicate = new CourseContainsKeywordsPredicate(Arrays.asList("AnALyTICs", "BusINESS"));
        assertTrue(predicate.test(new PersonBuilder().withCourse("Business Analytics").build()));
    }

    @Test
    public void test_courseDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        CourseContainsKeywordsPredicate predicate = new CourseContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Computer Science").build()));

        // Non-matching keyword
        predicate = new CourseContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Computer Science").build()));

        // Keywords match other fields, but does/do not match course
        predicate = new CourseContainsKeywordsPredicate(Arrays.asList("87654321", "alice@email.com", "E0324444",
                "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("87654321")
                .withEmail("alice@email.com").withCourse("Business Analytics").withStudentID("E0324444").build()));
    }
}