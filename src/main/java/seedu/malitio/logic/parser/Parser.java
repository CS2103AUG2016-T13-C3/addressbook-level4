package seedu.malitio.logic.parser;

import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.commons.util.StringUtil;
import seedu.malitio.logic.commands.*;

import static seedu.malitio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.malitio.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>)[e|d|f|E|D|F]\\d+");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    private static final Pattern TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags

    private static final Pattern EDIT_DATA_ARGS_FORMAT =

            Pattern.compile("(?<targetIndex>[e|d|f|E|D|F]\\d+)"
                    + "(?<name>(?:\\s[^/]+)?)"
                    + "(?<tagArguments>(?: t/[^/]+)*)");

    private static final Pattern COMPLETE_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>[d|f|D|F]\\d+)");

    private static final Set<String> TYPES_OF_TASKS = new HashSet<String>(Arrays.asList("f", "d", "e" ));

    public static final String MESSAGE_MISSING_START_END = "Expecting start and end times\nExample: start thursday 800 end thursday 900";
    
    public Parser() {}

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return prepareAdd(arguments);

        case EditCommand.COMMAND_WORD:
            return prepareEdit(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case CompleteCommand.COMMAND_WORD:
            return prepareComplete(arguments);
            
        case UncompleteCommand.COMMAND_WORD:
            return prepareUncomplete(arguments);

        case MarkCommand.COMMAND_WORD:
            return prepareMark(arguments);

        case UnmarkCommand.COMMAND_WORD:
            return prepareUnmark(arguments);
            
        case ClearCommand.COMMAND_WORD:
            return prepareClear(arguments);
            
        case ListAllCommand.COMMAND_WORD:
            return new ListAllCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ListCommand.COMMAND_WORD:
            return prepareList(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();
            
        case SaveCommand.COMMAND_WORD:
            return prepareSave(arguments);

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    //@@author a0126633j
    /**
     * Parses arguments in the context of the clear command. Also checks validity of arguments
     */
    private Command prepareClear(String arguments) {
        if (!Arrays.asList(ClearCommand.VALID_ARGUMENTS).contains(arguments.trim().toLowerCase())) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
        }
        return new ClearCommand(arguments.trim().toLowerCase());
    }
    
    /**
     * Parses arguments in the context of the save command. Also ensure the argument is not empty
     */
    private Command prepareSave(String arguments) {
        if (arguments.trim().isEmpty()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
        }
        return new SaveCommand(arguments.trim());
    }
    //@@author
    
    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     * @@author A0153006W
     */
    private Command prepareAdd(String args){
        final Matcher matcher = TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        boolean hasStart = false;
        boolean hasEnd = false;
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        try {
            String name = matcher.group("name");
            String deadline = getDeadlineFromArgs(StringUtil.removeTagsFromString(name));
                   
            String start = getStartFromArgs(StringUtil.removeTagsFromString(name));
            if (!start.isEmpty()) {
                name = name.substring(0, name.lastIndexOf("start")).trim();
                hasStart = true;
            }
            
            String end = getEndFromArgs(StringUtil.removeTagsFromString(args));
            if (!end.isEmpty()) {
                hasEnd = true;
            }            
            if (!deadline.isEmpty()) {
                name = name.substring(0, name.lastIndexOf("by")).trim();
            }
            if (!deadline.isEmpty() && !hasStart && !hasEnd) {
                return new AddCommand(
                        name,
                        deadline,
                        getTagsFromArgs(matcher.group("tagArguments"))
                        );
            } else if (hasStart && hasEnd) {
                return new AddCommand(
                        name,
                        start,
                        end,
                        getTagsFromArgs(matcher.group("tagArguments"))
                        );
            } else if (hasStart ^ hasEnd) {
                return new IncorrectCommand(MESSAGE_MISSING_START_END);
            }
            return new AddCommand(
                    name,
                    getTagsFromArgs(matcher.group("tagArguments"))
                    );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    //@@author A0129595N
    /**
     * Parses arguments in the context of the edit task command.
     * 
     * @param arguments
     * @return the prepared command
     */
    private Command prepareEdit(String args) {
        final Matcher matcher = EDIT_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        try {
            String index = parseIndex(matcher.group("targetIndex"));
            if (index.isEmpty()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            }
            char taskType = index.charAt(0);
            int taskNum = Integer.parseInt(index.substring(1));
            String name = matcher.group("name");
            System.out.println(name);
            if (name.equals("") && getTagsFromArgs(matcher.group("tagArguments")).isEmpty()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            }
            String deadline = getDeadlineFromArgs(name);
            if (!deadline.isEmpty()) {
                name = name.replaceAll(" by " + deadline, "");
            }

            String start = getStartFromArgs(name);
            if (!start.isEmpty()) {
                name = name.replaceAll(" start " + start, "");
            }

            String end = getEndFromArgs(name);
            if (!end.isEmpty()) {
                name = name.replaceAll(" end " + end, "");
            }
            name = name.trim();
                return new EditCommand(
                        taskType,
                        taskNum,
                        name,
                        deadline,
                        start,
                        end,
                        getTagsFromArgs(matcher.group("tagArguments"))
                        );

        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    //@@author A0122460W
    /**
     * Parses arguments in the context of the complete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareComplete(String args) {
        final Matcher matcher = COMPLETE_INDEX_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
        }
        try {
            String index = parseIndex(matcher.group("targetIndex"));
            if (index.isEmpty()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
            }
            char taskType = index.charAt(0);
            int taskNum = Integer.parseInt(index.substring(1));

            return new CompleteCommand(taskType,taskNum);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    /**
     * Parses arguments in the context of the uncomplete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareUncomplete(String args) {
        final Matcher matcher = COMPLETE_INDEX_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UncompleteCommand.MESSAGE_USAGE));
        }
        try {
            String index = parseIndex(matcher.group("targetIndex"));
            if (index.isEmpty()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UncompleteCommand.MESSAGE_USAGE));
            }
            char taskType = index.charAt(0);
            int taskNum = Integer.parseInt(index.substring(1));

            return new UncompleteCommand(taskType,taskNum);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    //@@author
    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {
        String index = parseIndex(args);
          if(index.isEmpty()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
          }
          char taskType = index.charAt(0);
          int taskNum = Integer.parseInt(index.substring(1));
      
        return new DeleteCommand(Character.toString(taskType), taskNum);
    }

    /**
     * Parses arguments in the context of the mark task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareMark(String args) {
        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        }
        String index = parseIndex(args);
        if (index.isEmpty()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        }
        char taskType = index.charAt(0);
        int taskNum = Integer.parseInt(index.substring(1));
        return new MarkCommand(taskType, taskNum);
    }

    /**
     * Parses arguments in the context of the unmark task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareUnmark(String args) {
        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
        }
        String index = parseIndex(args);
        if (index.isEmpty()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
        }
        char taskType = index.charAt(0);
        int taskNum = Integer.parseInt(index.substring(1));
        return new UnmarkCommand(taskType, taskNum);
    }
    
    /**
     * Parses arguments in the context of the find task command.
     *
     * @param args full command args string
     * @return the prepared command
     * @@author
     */
    private Command prepareFind(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        String[] keywords = matcher.group("keywords").split("\\s+");
        String typeOfTask = "";

        if(TYPES_OF_TASKS.contains(keywords[0])) {
            typeOfTask = keywords[0];
            keywords = removeFirstFromArray(keywords);
        }
        if (keywords.length < 1) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }
        
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(typeOfTask, keywordSet);
    }

    //@@author a0126633j
    private String[] removeFirstFromArray(String[] arg) {
        String[] result = new String[arg.length - 1];
        for(int i = 1; i < arg.length; i++) {
            result[i - 1] = arg[i];
        }
        return result;
    }

    /**
     * Parses arguments in the context of the list task command.
     *
     * @param args full command args string
     * @return the prepared command
     * @@author A0153006W
     */
    private Command prepareList(String args) {
        if (args.isEmpty()) {
            return new ListCommand();
        }
        try {
            args = args.trim().toLowerCase();
            return new ListCommand(args);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns the specified index as a String in the {@code command}
     */
    private String parseIndex(String command) {
        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return "";
        }
        String index = command.trim().toLowerCase();
        return index;
    }

    /**
     * Extracts the task's deadline from the command's arguments string.
     */
    private static String getDeadlineFromArgs(String args) throws IllegalValueException {
        int byIndex = args.lastIndexOf(" by ");
        String deadline = "";
        if(byIndex >= 0 && byIndex < args.length() - 4) {
                deadline = args.substring(byIndex + 4);
        }
        return deadline;
    }

    /**
     * Extracts the task's event start from the command's arguments string.
     */
    private static String getStartFromArgs(String args) throws IllegalValueException {
        int startIndex = args.lastIndexOf(" start ");
        int endIndex = args.lastIndexOf(" end");
        if (startIndex >= 0 && endIndex > 0) {
            return args.substring(startIndex + 7, endIndex);
        } else if (startIndex >= 0 && endIndex < 0) {
            return args.substring(startIndex + 7);
        } else {
            return "";
        }
    }

    /**
     * Extracts the task's event end from the command's arguments string.
     */
    private static String getEndFromArgs(String args) throws IllegalValueException {
        int endIndex = args.lastIndexOf(" end ");
        if (endIndex >= 0) {
         return args.substring(endIndex + 5);
        } else {
            return ""; 
        }
    }

    /**
     * Extracts the new task's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     * @@author
     */
    private static Set<String> getTagsFromArgs(String tagArguments) throws IllegalValueException {
        // no tags
        if (tagArguments.isEmpty()) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" t/", "").split(" t/"));
        return new HashSet<>(tagStrings);
    }
}