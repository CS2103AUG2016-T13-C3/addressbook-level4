package seedu.malitio.logic.commands;

import seedu.malitio.commons.core.Messages;
import seedu.malitio.commons.core.UnmodifiableObservableList;
import seedu.malitio.model.task.ReadOnlyDeadline;
import seedu.malitio.model.task.ReadOnlyEvent;
import seedu.malitio.model.task.ReadOnlyFloatingTask;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineMarkedException;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineNotFoundException;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineUnmarkedException;
import seedu.malitio.model.task.UniqueEventList.EventMarkedException;
import seedu.malitio.model.task.UniqueEventList.EventNotFoundException;
import seedu.malitio.model.task.UniqueEventList.EventUnmarkedException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskMarkedException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskNotFoundException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskUnmarkedException;

/**
 * Unmarks a specified task or deadline as a priority in Malitio to the user.
 * @@author A0153006W
 */
public class UnmarkCommand extends Command {

    public static final String COMMAND_WORD = "unmark";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unmarks specified task or deadline as priority in Malitio\n" +
            "Parameters: INDEX\n" + "Example: " + COMMAND_WORD + " f1";
    
    public static final String MESSAGE_UNMARK_SUCCESS = "Task has been unmarked as priority";
    
    private final int targetIndex;
    private final char taskType;
    
    private Object taskToUnmark;

    public UnmarkCommand(char taskType, int targetIndex) {
        this.taskType = taskType;
        this.targetIndex = targetIndex;
    }
            
    @Override
    public CommandResult execute() {
        if (!(taskType == 'f' || taskType == 'd' || taskType == 'e')) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        UnmodifiableObservableList lastShownList;
        lastShownList = getCorrectList();        
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        taskToUnmark = lastShownList.get(targetIndex - 1);

        try {
            assert model != null;
            model.unmarkTask(taskToUnmark);
        } catch (FloatingTaskNotFoundException e) {
            assert false : "The target floating task cannot be missing";
        } catch (FloatingTaskUnmarkedException e) {
            return new CommandResult(MESSAGE_UNMARK_SUCCESS);
        } catch (DeadlineNotFoundException e) {
            assert false : "The target deadline cannot be missing";
        } catch (DeadlineUnmarkedException e) {
            return new CommandResult(MESSAGE_UNMARK_SUCCESS);
        } catch (EventNotFoundException e) {
            assert false : "The target event cannot be missing";
        } catch (EventUnmarkedException e) {
            return new CommandResult(MESSAGE_UNMARK_SUCCESS);
        }
        return new CommandResult(MESSAGE_UNMARK_SUCCESS);
    }

    /**
     * @return UnmodifiableObservableList of the correct task type
     */
    private UnmodifiableObservableList getCorrectList() {
        UnmodifiableObservableList lastShownList;
        if (taskType == 'f') {
            lastShownList = model.getFilteredFloatingTaskList();
        } else if (taskType == 'd') {
            lastShownList = model.getFilteredDeadlineList(); 
        } else {
            lastShownList = model.getFilteredEventList();
        }
        return lastShownList;
    }
}