package seedu.malitio.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.malitio.commons.exceptions.DuplicateDataException;
import seedu.malitio.commons.util.CollectionUtil;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineMarkedException;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineNotFoundException;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineUnmarkedException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskNotFoundException;

import java.util.*;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */

public class UniqueEventList implements Iterable<Event> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateEventException extends DuplicateDataException {
        protected DuplicateEventException() {
            super("Operation would result in duplicate events");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would fail because
     * there is no such matching task in the list.
     */
    public static class EventNotFoundException extends Exception {}
    
    public static class EventMarkedException extends Exception {}
    
    public static class EventUnmarkedException extends Exception {}

    private final ObservableList<Event> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty EventList.
     */
    public UniqueEventList() {}

    /**
     * Returns true if the list contains an equivalent event as the given argument.
     */
    public boolean contains(ReadOnlyEvent toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }
    //@@author A0129595N
    /**
     * Returns true if the list contains an equivalent event as the given argument as well as identical tag(s).
     */
    public boolean containsWithTags(ReadOnlyEvent toCheck) {
        assert toCheck!=null;
        if (!internalList.contains(toCheck)) {
            return false;
        } else {
            int index = internalList.indexOf(toCheck);
            if (toCheck.getTags().getInternalList().isEmpty()) {
                return internalList.get(index).getTags().getInternalList().isEmpty();
            } else {
                return internalList.get(index).getTags().getInternalList()
                        .containsAll(toCheck.getTags().getInternalList());
            }
        }
    }
    
    /**
     * Adds a task to the list.
     *
     * @throws DuplicateEventException if the event to add is a duplicate of an existing event in the list.
     */
    public void add(Event toAdd) throws DuplicateEventException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateEventException();
        }
        internalList.add(toAdd);
    }
    
    public void edit(Event edited, ReadOnlyEvent beforeEdit) throws DuplicateEventException, EventNotFoundException {
        assert edited!=null;
        assert beforeEdit!=null;
        if (containsWithTags(edited)) {
            throw new DuplicateEventException();
        }
        
        if (!contains(beforeEdit)) {
            throw new EventNotFoundException();
        }
        
        internalList.remove(beforeEdit);
        internalList.add(edited);
    }
    //@@author
    
  //@@author A0153006W
    /**
     * Marks the event in the list.
     *
     * @throws EventNotFoundException if the event doesn't exist.
     * @throws EventMarkedException if the event is already marked.
     */
    public void mark(ReadOnlyEvent taskToMark)
            throws EventNotFoundException, EventMarkedException {
        if (taskToMark.isMarked()) {
            throw new EventMarkedException();
        }
        
        if (!contains(taskToMark)) {
            throw new EventNotFoundException();
        }
        taskToMark.setMarked(true);
        updateEventList(taskToMark);
    }

    /**
     * Unmarks the task in the list.
     *
     * @throws EventNotFoundException if the event doesn't exist.
     * @throws EventUnmarkedException if the event is already unmarked.
     */
    public void unmark(ReadOnlyEvent taskToUnmark)
            throws EventNotFoundException, EventUnmarkedException {
        if (!taskToUnmark.isMarked()) {
            throw new EventUnmarkedException();
        }
        
        if (!contains(taskToUnmark)) {
            throw new EventNotFoundException();
        }
        taskToUnmark.setMarked(false);
        updateEventList(taskToUnmark);
    }

    //@@author
    /*
     * Updates Malitio
     */
    private void updateEventList(ReadOnlyEvent eventToComplete) {
        int indexToReplace = internalList.indexOf(eventToComplete);
        internalList.remove(eventToComplete);
        internalList.add(indexToReplace, (Event) eventToComplete);
    }
    
    /**
     * Removes the equivalent schedule from the list.
     *
     * @throws FloatingTaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyEvent toRemove) throws EventNotFoundException {
        assert toRemove != null;
        final boolean eventFoundAndDeleted = internalList.remove(toRemove);
        if (!eventFoundAndDeleted) {
            throw new EventNotFoundException();
        }
        return eventFoundAndDeleted;
    }

    public ObservableList<Event> getInternalList() {
        return internalList;
    }
    
    //@@author
    public void sort() {
    	Collections.sort(internalList, new Comparator<Event>() {
      	  public int compare(Event e1, Event e2) {
      	      if (e1.getStart() == null || e2.getStart() == null)
      	        return 0;
      	      return e1.getStart().compareTo(e2.getStart());
      	  }
      	});
    }

    @Override
    public Iterator<Event> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueEventList // instanceof handles nulls
                && this.internalList.equals(
                ((UniqueEventList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}

