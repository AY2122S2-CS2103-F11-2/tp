package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyInterviewSchedule;

/**
 * A class to access InterviewSchedule data stored as a json file on the hard disk.
 */
public class JsonInterviewScheduleStorage implements InterviewScheduleStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonInterviewScheduleStorage.class);

    private Path filePath;

    public JsonInterviewScheduleStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getInterviewScheduleFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyInterviewSchedule> readInterviewSchedule() throws DataConversionException {
        return readInterviewSchedule(filePath);
    }

    /**
     * Similar to {@link #readInterviewSchedule()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyInterviewSchedule> readInterviewSchedule(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableInterviewSchedule> jsonInterviewSchedule = JsonUtil.readJsonFile(
                filePath, JsonSerializableInterviewSchedule.class);
        if (!jsonInterviewSchedule.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonInterviewSchedule.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveInterviewSchedule(ReadOnlyInterviewSchedule interviewSchedule) throws IOException {
        saveInterviewSchedule(interviewSchedule, filePath);
    }

    /**
     * Similar to {@link #saveInterviewSchedule(ReadOnlyInterviewSchedule)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveInterviewSchedule(ReadOnlyInterviewSchedule interviewList, Path filePath) throws IOException {
        requireNonNull(interviewList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableInterviewSchedule(interviewList), filePath);
    }
}
