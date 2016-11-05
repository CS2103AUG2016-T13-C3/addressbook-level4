package guitests;


import seedu.malitio.commons.core.Messages;
import seedu.malitio.logic.commands.UnmarkCommand;

import org.junit.Test;
import static seedu.malitio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

//@@author A0153006W
public class UnmarkCommandTest extends MalitioGuiTest {

    @Test
    public void ununmarkFloatingtask() {

        // unmark floating task
        commandBox.runCommand("mark f1");
        commandBox.runCommand("unmark f1");
        assertResultMessage(String.format(UnmarkCommand.MESSAGE_UNMARK_SUCCESS));
        
        commandBox.runCommand("mark f3");
        commandBox.runCommand("unmark f3");
        assertResultMessage(String.format(UnmarkCommand.MESSAGE_UNMARK_SUCCESS));
        
        // unmark already unmarked deadline
        commandBox.runCommand("unmark f1");
        assertResultMessage(String.format(UnmarkCommand.MESSAGE_UNMARK_AGAIN));
        
        // unmark error command
        commandBox.runCommand("unmark");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
        
        commandBox.runCommand("unmark do");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));

        // unmark with an invalid index
        commandBox.runCommand("unmark f200");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX));
    }

    @Test
    public void unmarkDeadline() {

        // unmark deadline
        commandBox.runCommand("mark d1");
        commandBox.runCommand("unmark d1");
        assertResultMessage(String.format(UnmarkCommand.MESSAGE_UNMARK_SUCCESS));
        
        commandBox.runCommand("mark d5");
        commandBox.runCommand("unmark d5");
        assertResultMessage(String.format(UnmarkCommand.MESSAGE_UNMARK_SUCCESS));
        
        // unmark already unmarked deadline
        commandBox.runCommand("unmark d1");
        assertResultMessage(String.format(UnmarkCommand.MESSAGE_UNMARK_AGAIN));

        // unmark with an invalid index
        commandBox.runCommand("unmark d200");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX));
    }
    
    @Test
    public void unmarkEvent() {

        // unmark deadline
        commandBox.runCommand("mark e1");
        commandBox.runCommand("unmark e1");
        assertResultMessage(String.format(UnmarkCommand.MESSAGE_UNMARK_SUCCESS));
        
        commandBox.runCommand("mark e4");
        commandBox.runCommand("unmark e4");
        assertResultMessage(String.format(UnmarkCommand.MESSAGE_UNMARK_SUCCESS));
        
        // unmark already unmarked deadline
        commandBox.runCommand("unmark e1");
        assertResultMessage(String.format(UnmarkCommand.MESSAGE_UNMARK_AGAIN));

        // unmark with an invalid index
        commandBox.runCommand("unmark e200");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX));
    }

}