package seedu.malitio.model.history;

import seedu.malitio.logic.commands.MarkCommand;
//@@author A0129595N
public class InputUnmarkHistory extends InputHistory {
    
    Object taskToUnmark;

    public InputUnmarkHistory(Object taskToUnmark) {
        this.commandForUndo = MarkCommand.COMMAND_WORD;
        this.taskToUnmark = taskToUnmark;
    }

    public Object getTask() {
        return taskToUnmark;
    }
}
