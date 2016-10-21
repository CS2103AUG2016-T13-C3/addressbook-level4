package seedu.malitio.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.malitio.commons.exceptions.DuplicateDataException;
import seedu.malitio.commons.util.CollectionUtil;
import seedu.malitio.model.task.UniqueEventList.DuplicateEventException;
import seedu.malitio.model.task.UniqueEventList.EventNotFoundException;
import seedu.malitio.model.task.UniqueFloatingTaskList.DuplicateFloatingTaskException;
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
            super("Operation would result in duplicate deadlines");
        }
    }

    /**
     * Signals that an operation targeting a specified task in the list would fail because
     * there is no such matching task in the list.
     */
    public static class EventNotFoundException extends Exception {}

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
        if (contains(edited)) {
            throw new DuplicateEventException();
        }
        
        if (!contains(beforeEdit)) {
            throw new EventNotFoundException();
        }
        
        int indexToReplace = internalList.indexOf(beforeEdit);
        internalList.add(indexToReplace, edited);
        internalList.remove(beforeEdit);
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

