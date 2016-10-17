package seedu.malitio.model.task;

import seedu.malitio.model.tag.UniqueTagList;

public class FloatingTask extends Task implements ReadOnlyTask {

	public FloatingTask(Name name, UniqueTagList tags) {
		super(name, tags);
		// TODO Auto-generated constructor stub
	}
	
	public FloatingTask(ReadOnlyTask source) {
        this(source.getName(), source.getTags());
    }

    @Override
    public DateTime getDueDate() {
        return null;
    }

    @Override
    public DateTime getStartDate() {
        return null;
    }

    @Override
    public DateTime getEndDate() {
        return null;
    }

}
