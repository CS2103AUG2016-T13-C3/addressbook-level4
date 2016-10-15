package seedu.malitio.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.model.tag.Tag;
import seedu.malitio.model.tag.UniqueTagList;
import seedu.malitio.model.task.DateTime;
import seedu.malitio.model.task.Deadlines;
import seedu.malitio.model.task.Events;
import seedu.malitio.model.task.Name;
import seedu.malitio.model.task.ReadOnlySchedule;
import seedu.malitio.model.task.Schedule;

public class XmlAdaptedSchedule {

    @XmlElement(required = true)
    private String name;


    @XmlElement
    private String due; 
    private String start;
    private String end;
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedSchedule() {}


    /**
     * Converts a given Schedule into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedSchedule(ReadOnlySchedule source) {
        name = source.getName().fullName;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Task
     */
    public Schedule toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final UniqueTagList tags = new UniqueTagList(taskTags);
            return new Schedule(name,tags);
    }
}
