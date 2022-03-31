package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FocusCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.schedule.AddScheduleCommand;
import seedu.address.logic.commands.schedule.ClearScheduleCommand;
import seedu.address.logic.commands.schedule.DeleteScheduleCommand;
import seedu.address.logic.commands.schedule.EditScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.candidate.Candidate;
import seedu.address.model.interview.Interview;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final int EDIT_COMMAND_INDEX = 5;
    private static final int DELETE_COMMAND_INDEX = 7;
    private static final int ADD_SCHEDULE_COMMAND_INDEX = 23;

    private final Logger logger = LogsCenter.getLogger(getClass());


    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private CandidateListPanel candidateListPanel;
    private InterviewListPanel interviewListPanel;
    private FocusCard focusListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane candidateListPanelPlaceholder;

    @FXML
    private StackPane interviewListPanelPlaceholder;

    @FXML
    private StackPane focusListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        candidateListPanel = new CandidateListPanel(logic.getFilteredCandidateList());
        candidateListPanelPlaceholder.getChildren().add(candidateListPanel.getRoot());

        interviewListPanel = new InterviewListPanel(logic.getFilteredInterviewSchedule());
        interviewListPanelPlaceholder.getChildren().add(interviewListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    private void handleFocus(CommandResult commandResult) {
        if (!focusListPanelPlaceholder.getChildren().isEmpty()) {
            focusListPanelPlaceholder.getChildren().remove(0);
        }
        Candidate candidate = logic.getFilteredCandidateList().get(commandResult.getIndexFocus());
        Interview interview = null;
        for (Interview i: logic.getFilteredInterviewSchedule()) {
            if (i.getCandidate().isSameCandidate(candidate)) {
                interview = i;
                break;
            }
        }
        focusListPanel = new FocusCard(candidate, interview);
        focusListPanelPlaceholder.getChildren().add(focusListPanel.getRoot());
    }

    private void handleDelete(String commandResult) throws CommandException {
        int index;
        try {
            index = Integer.parseInt(commandResult.substring(DELETE_COMMAND_INDEX));
            if (focusListPanel.getCandidate().equals(logic.getFilteredCandidateList().get(index - 1))) {
                clearFocusCard();
            }
        } catch (Exception e) {
            return;
        }
    }

    /**
     * Clear Focus Card
     */
    public void clearFocusCard() {
        if (!focusListPanelPlaceholder.getChildren().isEmpty()) {
            focusListPanelPlaceholder.getChildren().remove(0);
        }
        candidateListPanel = new CandidateListPanel(logic.getFilteredCandidateList());
        candidateListPanelPlaceholder.getChildren().add(candidateListPanel.getRoot());
    }

    /**
     * This method will be used when a user tries to key in an Edit Command. It will check if the index
     * being edited corresponds to the Candidate being shown.
     *
     * @return if the Candidate being edited is the one on Focus Panel, return the index of the Candidate. Else
     * it will return the value of -1.
     */
    public int handleEdit(String commandText, int commandIndex) throws CommandException, ParseException {
        logger.info(commandText);

        try {
            int displayedIndex = logic.getFilteredCandidateList().indexOf(focusListPanel.getCandidate());
            String string = commandText.substring(commandIndex);
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < string.length(); i++) {
                char c = string.charAt(i);
                if (Character.isDigit(c)) {
                    builder.append(c);
                } else {
                    break;
                }
            }

            int editIndex = Integer.parseInt(builder.toString());
            if (editIndex - 1 == displayedIndex) {
                return editIndex;
            } else {
                return -1;
            }
        } catch (Exception e) {
            return -1;
        }
    }

    public CandidateListPanel getCandidateListPanel() {
        return candidateListPanel;
    }

    public InterviewListPanel getInterviewListPanel() {
        return interviewListPanel;
    }

    public FocusCard getFocusCard() {
        return focusListPanel;
    }


    /**
     * Methods where we need to execute the command to get the Index of the Candidate to refresh the panel
     */
    public CommandResult executeCommandThenRefresh(String commandText) throws CommandException, ParseException {
        CommandResult commandResult = logic.execute(commandText);

        if (focusListPanel.getCandidate().looseEqual(logic
                .getFilteredCandidateList()
                .get(commandResult.getEditIndex()))) {
            executeCommand(FocusCommand.COMMAND_WORD + " "
                    + String.valueOf(commandResult.getEditIndex() + 1));
        }

        resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
        //to manually clear the commandBox
        commandBoxPlaceholder.getChildren().remove(0);
        commandBoxPlaceholder.getChildren().add(new CommandBox(this::executeCommand).getRoot());
        return commandResult;
    }


    /**
     * Refreshes the FocusCard if all schedules are cleared so that the displayed Candidate has the latest information.
     */
    public CommandResult refreshWhenClearAllSchedule(String commandText) throws CommandException, ParseException {
        int displayedIndex = logic.getFilteredCandidateList().indexOf(focusListPanel.getCandidate());
        CommandResult commandResult = logic.execute(commandText);
        if (!focusListPanelPlaceholder.getChildren().isEmpty()) {
            logger.info(String.valueOf(logic.getFilteredCandidateList().contains(focusListPanel.getCandidate())));
            executeCommand(FocusCommand.COMMAND_WORD + " " + String.valueOf(displayedIndex + 1));
        }

        resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
        //to manually clear the commandBox
        commandBoxPlaceholder.getChildren().remove(0);
        commandBoxPlaceholder.getChildren().add(new CommandBox(this::executeCommand).getRoot());
        return commandResult;
    }


    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            int editFlag = -1;

            if (commandText.contains(DeleteScheduleCommand.COMMAND_WORD)
                    || commandText.contains(RemarkCommand.COMMAND_WORD)
                    || (commandText.contains(EditScheduleCommand.COMMAND_WORD))) {
                return executeCommandThenRefresh(commandText);
            } else if (commandText.contains(DeleteCommand.COMMAND_WORD)) {
                handleDelete(commandText);
            } else if (commandText.contains(EditCommand.COMMAND_WORD)) {
                //do nothing (for future implementation)
            } else if (commandText.contains(AddScheduleCommand.COMMAND_WORD)) {
                editFlag = handleEdit(commandText, ADD_SCHEDULE_COMMAND_INDEX);
            } else if (commandText.contains(ClearScheduleCommand.COMMAND_WORD)) {
                return refreshWhenClearAllSchedule(commandText);
            } else if (commandText.split(" ")[0].equals(ClearCommand.COMMAND_WORD)) {
                clearFocusCard();
            }

            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isShowFocus()) {
                handleFocus(commandResult);
            }

            if (editFlag != -1) {
                executeCommand(FocusCommand.COMMAND_WORD + " " + editFlag);
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
