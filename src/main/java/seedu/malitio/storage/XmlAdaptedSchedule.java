package seedu.malitio.storage;

import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.model.tag.Tag;
import seedu.malitio.model.tag.UniqueTagList;
import seedu.malitio.model.task.*;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly version of a schedule, which consists of events and deadlines.
 */
public class XmlAdaptedSchedule {

    @XmlElement(required = true)
    private String name;
    
    @XmlElement
    private String dueDate = null;
    
    @XmlElement
    private String startDate = null;
    
    @XmlElement
    private String endDate = null;
   
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedSchedule() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedSchedule(ReadOnlyTask source) {
        name = source.getName().fullName;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        if (source.getDueDate() != null) {
        dueDate = source.getDueDate().toString();
        }
        if (source.getStartDate() != null && source.getEndDate() != null) {
            startDate = source.getStartDate().toString();
            endDate = source.getEndDate().toString();
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final UniqueTagList tags = new UniqueTagList(taskTags);

        if (dueDate != null) {
        final DateTime dueDate = new DateTime(this.dueDate);
        return new Deadlines(name, dueDate, tags);
        }
        else {
            final DateTime startDate = new DateTime(this.startDate);
            final DateTime endDate = new DateTime(this.endDate);
            return new Events(name, startDate, endDate, tags);
        }
    
    }
}
