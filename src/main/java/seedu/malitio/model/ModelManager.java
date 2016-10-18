package seedu.malitio.model;

import javafx.collections.transformation.FilteredList;
import seedu.malitio.commons.core.ComponentManager;
import seedu.malitio.commons.core.LogsCenter;
import seedu.malitio.commons.core.UnmodifiableObservableList;
import seedu.malitio.commons.events.model.MalitioChangedEvent;
import seedu.malitio.commons.util.StringUtil;
import seedu.malitio.model.task.Deadline;
import seedu.malitio.model.task.Event;
import seedu.malitio.model.task.FloatingTask;
import seedu.malitio.model.task.ReadOnlyDeadline;
import seedu.malitio.model.task.ReadOnlyEvent;
import seedu.malitio.model.task.ReadOnlyFloatingTask;
import seedu.malitio.model.task.UniqueDeadlineList.DuplicateDeadlineException;
import seedu.malitio.model.task.UniqueEventList.DuplicateEventException;
import seedu.malitio.model.task.UniqueFloatingTaskList.DuplicateFloatingTaskException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskNotFoundException;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the malitio data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Malitio malitio;
    private final FilteredList<FloatingTask> filteredFloatingTasks;
    private final FilteredList<Deadline> filteredDeadlines;
    private final FilteredList<Event> filteredEvents;

    /**
     * Initializes a ModelManager with the given Malitio
     * Malitio and its variables should not be null
     */
    public ModelManager(Malitio src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with malitio: " + src + " and user prefs " + userPrefs);

        malitio = new Malitio(src);
        filteredFloatingTasks = new FilteredList<>(malitio.getFloatingTasks());
        filteredDeadlines = new FilteredList<>(malitio.getDeadlines());
        filteredEvents = new FilteredList<>(malitio.getEvents());
    }

    public ModelManager() {
        this(new Malitio(), new UserPrefs());
    }

    public ModelManager(ReadOnlyMalitio initialData, UserPrefs userPrefs) {
        malitio = new Malitio(initialData);
        filteredFloatingTasks = new FilteredList<>(malitio.getFloatingTasks());
        filteredDeadlines = new FilteredList<>(malitio.getDeadlines());
        filteredEvents = new FilteredList<>(malitio.getEvents());
    }

    @Override
    public void resetData(ReadOnlyMalitio newData) {
        malitio.resetData(newData);
        indicatemalitioChanged();
    }

    @Override
    public ReadOnlyMalitio getMalitio() {
        return malitio;
    }

    /** Raises an event to indicate the model has changed */
    private void indicatemalitioChanged() {
        raise(new MalitioChangedEvent(malitio));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyFloatingTask target) throws FloatingTaskNotFoundException {
        malitio.removeTask(target);
        indicatemalitioChanged();
    }

    @Override
    public void addFloatingTask(FloatingTask task) throws DuplicateFloatingTaskException {
        malitio.addFloatingTask(task);
        updateFilteredTaskListToShowAll();
        indicatemalitioChanged();
    }

    @Override
    public void addDeadline(Deadline deadline) throws DuplicateDeadlineException {
        malitio.addDeadline(deadline);
        updateFilteredDeadlineListToShowAll();
        indicatemalitioChanged();
        
    }
    
    @Override
    public void addEvent(Event event) throws DuplicateEventException {
        malitio.addEvent(event);
        updateFilteredDeadlineListToShowAll();
        indicatemalitioChanged();
        
    }

    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyFloatingTask> getFilteredFloatingTaskList() {
        return new UnmodifiableObservableList<>(filteredFloatingTasks);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyDeadline> getFilteredDeadlineList() {
        return new UnmodifiableObservableList<>(filteredDeadlines);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyEvent> getFilteredEventList() {
        return new UnmodifiableObservableList<>(filteredEvents);
    }
    
    @Override
    public void updateFilteredTaskListToShowAll() {
        filteredFloatingTasks.setPredicate(null);
    }
    
    @Override
    public void updateFilteredDeadlineListToShowAll() {
        filteredDeadlines.setPredicate(null);
    }
    
    @Override
    public void updateFilteredEventListToShowAll() {
        filteredEvents.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredFloatingTasks.setPredicate(expression::satisfies);
    }
    
    @Override
    public void updateFilteredDeadlineList(Set<String> keywords){
    	updateFilteredDeadlines(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredDeadlines(Expression expression) {
        filteredDeadlines.setPredicate(expression::satisfies);
    }
    
    @Override
    public void updateFilteredEventList(Set<String> keywords){
        updateFilteredEvents(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredEvents(Expression expression) {
        filteredEvents.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyFloatingTask task);
        boolean satisfies(ReadOnlyDeadline deadline);
        boolean satisfies(ReadOnlyEvent event);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyFloatingTask task) {
            return qualifier.run(task);
        }
        
        @Override
        public boolean satisfies(ReadOnlyDeadline deadline) {
            return qualifier.run(deadline);
        }
        
        @Override
        public boolean satisfies(ReadOnlyEvent event) {
            return qualifier.run(event);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyFloatingTask task);
        boolean run(ReadOnlyDeadline schedule);
        boolean run(ReadOnlyEvent event);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyFloatingTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }
        
        @Override
        public boolean run(ReadOnlyDeadline deadline) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(deadline.getName().fullName
                            + " " + deadline.getDue().toString(), 
                            keyword))
                    .findAny() 
                    .isPresent();
        }
        
        @Override
        public boolean run(ReadOnlyEvent event) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(event.getName().fullName
                            + " " + event.getStart().toString()
                            + " " + event.getEnd().toString(), 
                            keyword))
                    .findAny() 
                    .isPresent();
        }


        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
