package seedu.malitio.testutil;

import seedu.malitio.model.tag.UniqueTagList;
import seedu.malitio.model.task.*;

/**
 * A mutable floating task object. For testing only.
 */
public class TestFloatingTask implements ReadOnlyFloatingTask {

    private Name name;
    private UniqueTagList tags;

    public TestFloatingTask() {
        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    @Override
    public String tagsString() {
        return ReadOnlyFloatingTask.super.tagsString();
    }

    @Override
    public String getAsText() {
        return ReadOnlyFloatingTask.super.getAsText();
    }

}
