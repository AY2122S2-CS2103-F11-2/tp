package seedu.address.logic.commands.schedule;

import seedu.address.logic.commands.Command;

public abstract class ScheduleCommand extends Command {
    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = "The following formats are supported for " + COMMAND_WORD + ":\n"
            + COMMAND_WORD + " add candidate/INDEX at/DATE_TIME \n"
            + COMMAND_WORD + " edit SCHEDULE_INDEX at/DATE_TIME \n"
            + COMMAND_WORD + " delete SCHEDULE_INDEX";

    public static final String MESSAGE_DUPLICATE_CANDIDATE_INTERVIEW =
            "Interview for this candidate already exists!";

    public static final String MESSAGE_CONFLICTING_INTERVIEW =
            "Interview for another candidate clashes with the proposed time slot!";

    public static final String MESSAGE_CANDIDATE_NOT_AVAILABLE =
            "Candidate is not available on the proposed interview day!";

    public static final String MESSAGE_NOT_OFFICE_HOUR =
            "You are trying to schedule the interview outside of your office hours!\n"
                    + "Your office hours are Mon-Fri, 8am - 6pm. The latest interview for the day "
                    + "is to be scheduled at 5.30PM";

    public static final String MESSAGE_INVALID_FORMAT_DATETIME =
            "Date and/or Time is not in the following format: dd-MM-yyyy HH:mm";

    public static final String MESSAGE_INVALID_PAST_DATETIME =
            "Date and/or Time must not be in the past!";
}
