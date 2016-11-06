package seedu.malitio.logic.commands;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import seedu.malitio.commons.core.Config;
import seedu.malitio.commons.core.EventsCenter;
import seedu.malitio.commons.core.LogsCenter;
import seedu.malitio.commons.events.storage.DataStorageFileChangedEvent;
import seedu.malitio.commons.util.ConfigUtil;
import seedu.malitio.commons.util.FileUtil;
import seedu.malitio.commons.util.StringUtil;

//@@author a0126633j
/**
 * Allows the user to change the directory of save file. Old file in old directory will be deleted.
 * The new directory will be remembered next time the App starts.
 */
public class SaveCommand extends Command {
    
    private static final Logger logger = LogsCenter.getLogger(SaveCommand.class);

    public static final String COMMAND_WORD = "save";
    
    public static final String MESSAGE_DIRECTORY_EXAMPLE = "C://Users/User PC/Downloads/";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes data file location of Malitio.\n"
            + "Parameters: File Directory\n"
            + "Example: " + COMMAND_WORD
            + " " + MESSAGE_DIRECTORY_EXAMPLE;

    public static final String MESSAGE_SAVE_SUCCESSFUL = "Malitio data will be saved in %s from now onwards.";
    
    public static final String MESSAGE_INVALID_DIRECTORY = "The directory is invalid! Valid file paths must end with '/' or '\\'\nExample: ";
    
    public static final String[] FILE_PATH_IDENTIFIER = {"\\", "/" };
    
    private final String dataFilePath;
    
    /**
     * Initialises dataFilePath to the input if the input ends with '/' or '\', else set dataFilePath to null
     * 
     */
    public SaveCommand(String dataFilePath) {
        if(dataFilePath.endsWith(FILE_PATH_IDENTIFIER[0]) || dataFilePath.endsWith(FILE_PATH_IDENTIFIER[1])) {
            this.dataFilePath = dataFilePath + Config.DEFAULT_FILE_NAME;
        } else {
            this.dataFilePath = null;
        }
    }
    
    @Override
    public CommandResult execute() {
        if(!isValidFilePath()) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(MESSAGE_INVALID_DIRECTORY + MESSAGE_DIRECTORY_EXAMPLE);
        }
        EventsCenter.getInstance().post(new DataStorageFileChangedEvent(dataFilePath));
        ConfigUtil.changeMalitioSaveDirectory(dataFilePath);
        model.handleDataFilePathChanged();


        return new CommandResult(String.format(MESSAGE_SAVE_SUCCESSFUL, dataFilePath));
    }
    
    /**
     * Checks if the input by user is a valid file path. Valid file paths must end with '/' and cannot contain '\'
     */
    private boolean isValidFilePath() {
        if(dataFilePath == null) {
            return false;
        }
 
        File file = new File(dataFilePath);
        try {
        if(!FileUtil.createFile(file)) {
            logger.warning("File already exists");
        }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
