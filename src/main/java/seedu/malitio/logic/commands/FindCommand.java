package seedu.malitio.logic.commands;

import java.util.Set;

/**
 * Finds and lists all tasks in Malitio whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final Set<String> keywords;
    private final String type;

    public FindCommand(String type, Set<String> keywords) {
        this.keywords = keywords;
        this.type = type;
    }

    @Override
    public CommandResult execute() {

        switch (type) {
        case "f": 
            model.updateFilteredTaskList(keywords);
            break;
        case "d":
            model.updateFilteredDeadlineList(keywords);
            break;
        case "e":
            model.updateFilteredEventList(keywords);
            break;
        default:
            model.updateFilteredTaskList(keywords);
            model.updateFilteredDeadlineList(keywords);
            model.updateFilteredEventList(keywords);    
        }
 
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredFloatingTaskList().size()));
    }

}
