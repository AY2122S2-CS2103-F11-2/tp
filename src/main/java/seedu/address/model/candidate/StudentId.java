package seedu.address.model.candidate;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Candidate's Student ID in TAlent Assistant™.
 * Guarantees: immutable; is valid as declared in {@link #isValidId(String)}
 */
public class StudentId {
    public static final String MESSAGE_CONSTRAINTS =
            "IDs should only contain A, 7 digits/numbers and an alphabet at the end"
            + " e.g. A0123456X, and it should not be blank";

    /*
     * The format of Student ID should be E followed by 7 digits without spacing.
     */
    public static final String VALIDATION_REGEX = "A[\\p{Digit}]{7}[\\p{Alpha}]{1}";

    public final String studentId;

    /**
     * Constructs a {@code StudentId}.
     *
     * @param id A valid student ID.
     */
    public StudentId(String id) {
        requireNonNull(id);
        checkArgument(isValidId(id), MESSAGE_CONSTRAINTS);
        studentId = id;
    }

    /**
     * Returns true if a given string is a valid student ID.
     */
    public static boolean isValidId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return studentId;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StudentId // instanceof handles nulls
                && studentId.equals(((StudentId) other).studentId)); // state check
    }

    @Override
    public int hashCode() {
        return studentId.hashCode();
    }
}
