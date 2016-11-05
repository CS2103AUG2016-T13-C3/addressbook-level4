package guitests;


import seedu.malitio.commons.core.Messages;
import seedu.malitio.logic.commands.MarkCommand;

import org.junit.Test;
import static seedu.malitio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

//@@author A0153006W
public class MarkCommandTest extends MalitioGuiTest {

    @Test
    public void markFloatingtask() {

        // mark floating task
        commandBox.runCommand("mark f1");
        assertResultMessage(String.format(MarkCommand.MESSAGE_MARK_SUCCESS));
        
        commandBox.runCommand("mark f2");
        assertResultMessage(String.format(MarkCommand.MESSAGE_MARK_SUCCESS));
        
        // mark already marked deadline
        commandBox.runCommand("mark f1");
        assertResultMessage(String.format(MarkCommand.MESSAGE_MARK_AGAIN));
        
        // mark error command
        commandBox.runCommand("mark");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        
        commandBox.runCommand("mark do");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));

        // mark with an invalid index
        commandBox.runCommand("mark f200");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX));
    }

    @Test
    public void markDeadline() {

        // mark deadline
        commandBox.runCommand("mark d1");
        assertResultMessage(String.format(MarkCommand.MESSAGE_MARK_SUCCESS));
        
        commandBox.runCommand("mark d4");
        assertResultMessage(String.format(MarkCommand.MESSAGE_MARK_SUCCESS));
        
        // mark already marked deadline
        commandBox.runCommand("mark d1");
        assertResultMessage(String.format(MarkCommand.MESSAGE_MARK_AGAIN));

        // mark with an invalid index
        commandBox.runCommand("mark d200");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX));
    }
    
    @Test
    public void markEvent() {

        // mark deadline
        commandBox.runCommand("mark e1");
        assertResultMessage(String.format(MarkCommand.MESSAGE_MARK_SUCCESS));
        
        commandBox.runCommand("mark e5");
        assertResultMessage(String.format(MarkCommand.MESSAGE_MARK_SUCCESS));
        
        // mark already marked deadline
        commandBox.runCommand("mark e1");
        assertResultMessage(String.format(MarkCommand.MESSAGE_MARK_AGAIN));

        // mark with an invalid index
        commandBox.runCommand("mark e200");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX));
    }

}