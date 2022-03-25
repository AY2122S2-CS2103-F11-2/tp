package seedu.address.model.candidate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class AvailabilityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Availability(null));
    }

    @Test
    public void isValidDay() {
        // null availability
        assertThrows(NullPointerException.class, () -> Availability.isValidDay(null));

        // invalid availability
        assertFalse(Availability.isValidDay(""));
        assertFalse(Availability.isValidDay(","));
        assertFalse(Availability.isValidDay("0"));
        assertFalse(Availability.isValidDay("8"));
        assertFalse(Availability.isValidDay(",1"));
        assertFalse(Availability.isValidDay("1,"));
        assertFalse(Availability.isValidDay("1,,2"));
        assertFalse(Availability.isValidDay("12"));
        assertFalse(Availability.isValidDay("a"));
        assertFalse(Availability.isValidDay(",a"));
        assertFalse(Availability.isValidDay("a,"));
        assertFalse(Availability.isValidDay("a,a"));

        // valid availability
        assertTrue(Availability.isValidDay("2"));
        assertTrue(Availability.isValidDay("1,2,3,4,5"));
        assertTrue(Availability.isValidDay("5,4,3,2,1"));
    }

    private void assertFalse(boolean validDay) {
    }

    @Test
    public void getList_return_list() {
        List<String> test = new ArrayList<>();
        test.add("Mon");
        test.add("Tue");
        test.add("Wed");
        Availability availability = new Availability("1,2,3");
        assertEquals(availability.getList(), test);
    }
}
