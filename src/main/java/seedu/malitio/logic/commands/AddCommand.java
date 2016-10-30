package seedu.malitio.logic.commands;

import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.model.tag.Tag;
import seedu.malitio.model.tag.UniqueTagList;
import seedu.malitio.model.task.*;
import seedu.malitio.model.task.UniqueDeadlineList.DuplicateDeadlineException;
import seedu.malitio.model.task.UniqueEventList.DuplicateEventException;
import seedu.malitio.model.task.UniqueFloatingTaskList.DuplicateFloatingTaskException;

import java.util.HashSet;
import java.util.Set;
//@@author A0129595N
/**
 * Adds a task to Malitio.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": adds a task to Malitio. Task name cannot contain \'/\'. \n"
            + "Parameters: NAME [by DEADLINE] [start STARTTIME end ENDTIME] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " Pay John $100 by Oct 11 2359 t/oweMoney";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This floating task already exists in Malitio";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in Malitio";
    public static final String MESSAGE_DUPLICATE_DEADLINE ="This deadline already exists in Malitio";

    private Object toAddTask;

    /**
     * Convenience constructor for floating tasks using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAddTask = new FloatingTask(
                new Name(name),
                new UniqueTagList(tagSet)
        );
    }
    
    /**
     * Convenience constructor for deadlines using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    
    public AddCommand(String name, String date, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAddTask = new Deadline(
                new Name(name),
                new DateTime(date),
                new UniqueTagList(tagSet)
        );
    }
    
    /**
     * Convenience constructor for events using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String start, String end, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        // check if start < end
        this.toAddTask = new Event(
                new Name(name),
                new DateTime(start),
                new DateTime(end),
                new UniqueTagList(tagSet)
        );
    }
    
    /**
     * Executes the command. It will clear the future stack so that no redo can be done.
     */
    @Override
    public CommandResult execute() {
        assert model != null;{
            try {
                model.addTask(toAddTask);
                model.getFuture().clear();
                return new CommandResult(String.format(MESSAGE_SUCCESS, toAddTask));
            } catch (DuplicateFloatingTaskException e) {
                return new CommandResult(MESSAGE_DUPLICATE_TASK);
            } catch (DuplicateDeadlineException e) {
                return new CommandResult(MESSAGE_DUPLICATE_DEADLINE);
            } catch (DuplicateEventException e) {
                return new CommandResult(MESSAGE_DUPLICATE_EVENT);
            }
        }
    }
}
