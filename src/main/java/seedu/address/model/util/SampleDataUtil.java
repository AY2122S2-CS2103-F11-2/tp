package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.InterviewSchedule;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyInterviewSchedule;
import seedu.address.model.candidate.ApplicationStatus;
import seedu.address.model.candidate.Availability;
import seedu.address.model.candidate.Candidate;
import seedu.address.model.candidate.Course;
import seedu.address.model.candidate.Email;
import seedu.address.model.candidate.InterviewStatus;
import seedu.address.model.candidate.Name;
import seedu.address.model.candidate.Phone;
import seedu.address.model.candidate.Seniority;
import seedu.address.model.candidate.StudentId;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    private static final ApplicationStatus PENDING = new ApplicationStatus("pending");
    private static final InterviewStatus INTERVIEW_NOT_SCHEDULED = new InterviewStatus("Not Scheduled");

    public static Candidate[] getSampleCandidates() {
        return new Candidate[] {
            new Candidate(new StudentId("A0123456B"), new Name("Alex Yeoh"), new Phone("87438807"),
                    new Email("E0123456@u.nus.edu"), new Course("Business Analytics"), new Seniority("1"),
                    PENDING, INTERVIEW_NOT_SCHEDULED, new Availability("1,2,3,4,5")),
            new Candidate(new StudentId("A0234567B"), new Name("Bernice Yu"), new Phone("99272758"),
                    new Email("E0234567@u.nus.edu"), new Course("Computer Engineering"), new Seniority("2"),
                    PENDING, INTERVIEW_NOT_SCHEDULED, new Availability("1,2,3,4,5")),
            new Candidate(new StudentId("A0345678B"), new Name("Charlotte Oliveiro"), new Phone("93210283"),
                    new Email("E0345678@u.nus.edu"), new Course("Computer Science"), new Seniority("3"),
                    PENDING, INTERVIEW_NOT_SCHEDULED, new Availability("1,2,3")),
            new Candidate(new StudentId("A0456789B"), new Name("David Li"), new Phone("91031282"),
                    new Email("E0456789@u.nus.edu"), new Course("Information Security"), new Seniority("4"),
                    PENDING, INTERVIEW_NOT_SCHEDULED, new Availability("3,4,5")),
            new Candidate(new StudentId("A0567890B"), new Name("Irfan Ibrahim"), new Phone("92492021"),
                    new Email("E0567890@u.nus.edu"), new Course("Information Systems"), new Seniority("1"),
                    PENDING, INTERVIEW_NOT_SCHEDULED, new Availability("4,5")),
            new Candidate(new StudentId("A0678901B"), new Name("Roy Balakrishnan"), new Phone("92624417"),
                    new Email("E0678901@u.nus.edu"), new Course("Computer Science"), new Seniority("2"),
                    PENDING, INTERVIEW_NOT_SCHEDULED, new Availability("3,5"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Candidate sampleCandidate : getSampleCandidates()) {
            sampleAb.addCandidate(sampleCandidate);
        }
        return sampleAb;
    }

    public static ReadOnlyInterviewSchedule getEmptyInterviewList() {
        InterviewSchedule interviewSchedule = new InterviewSchedule();
        return interviewSchedule;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    public static ApplicationStatus getPending() {
        return PENDING;
    }

}
