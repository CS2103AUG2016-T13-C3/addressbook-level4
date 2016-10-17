package seedu.malitio.model;

import javafx.collections.ObservableList;
import seedu.malitio.model.tag.Tag;
import seedu.malitio.model.tag.UniqueTagList;
import seedu.malitio.model.task.Deadlines;
import seedu.malitio.model.task.FloatingTask;
import seedu.malitio.model.task.ReadOnlyTask;
import seedu.malitio.model.task.Task;
import seedu.malitio.model.task.UniqueTaskList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the application level
 * Duplicates are not allowed (by .equals comparison)
 */
public class Malitio implements ReadOnlyMalitio {

    private final UniqueTaskList tasks;
    private final UniqueTaskList tasks2;
    private final UniqueTagList tags;

    {
        tasks = new UniqueTaskList();
        tasks2 = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public Malitio() {}

    /**
     * Tasks and Tags are copied into this Malitio
     */
    public Malitio(ReadOnlyMalitio toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTaskList2(), toBeCopied.getUniqueTagList());
    }

    /**
     * Tasks and Tags are copied into this Malitio
     */
    public Malitio(UniqueTaskList tasks, UniqueTaskList tasks2, UniqueTagList tags) {
        resetData(tasks.getInternalList(), tasks2.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyMalitio getEmptymalitio() {
        return new Malitio();
    }

//// list overwrite operations

    public ObservableList<Task> getFloatingTasks() {
        return tasks.getInternalList();
    }
    
    public ObservableList<Task> getEventAndDeadlines() {
        return tasks2.getInternalList();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }
    
    public void setTasks2(List<Task> tasks) {
        this.tasks2.getInternalList().setAll(tasks);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newTasks, Collection<? extends ReadOnlyTask> newTasks2, Collection<Tag> newTags) {
        setTasks(newTasks.stream().map(FloatingTask::new).collect(Collectors.toList()));
        setTasks2(newTasks2.stream().map(Deadlines::new).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyMalitio newData) {
        resetData(newData.getTaskList(), newData.getTaskList2(), newData.getTagList());
    }

//// task-level operations

    /**
     * Adds a task to Malitio.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException {
        syncTagsWithMasterList(p);
        
        if(p instanceof FloatingTask) {
            tasks.add(p);
        } else {
            tasks2.add(p);
        }
        }
    

    /**
     * Ensures that every tag in this task:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(Task task) {
        final UniqueTagList taskTags = task.getTags();
        tags.mergeFrom(taskTags);

        // Create map with values = tag object references in the master list
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        for (Tag tag : tags) {
            masterTagObjects.put(tag, tag);
        }

        // Rebuild the list of task tags using references from the master list
        final Set<Tag> commonTagReferences = new HashSet<>();
        for (Tag tag : taskTags) {
            commonTagReferences.add(masterTagObjects.get(tag));
        }
        task.setTags(new UniqueTagList(commonTagReferences));
    }

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    
    public boolean removeTask2(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks2.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }

//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks, " + tags.getInternalList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }
    
    @Override
    public List<ReadOnlyTask> getTaskList2() {
        return Collections.unmodifiableList(tasks2.getInternalList());
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }
    
    @Override
    public UniqueTaskList getUniqueTaskList2() {
        return this.tasks;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Malitio // instanceof handles nulls
                && this.tasks.equals(((Malitio) other).tasks)
                && this.tags.equals(((Malitio) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }
}
