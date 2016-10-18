/**
 * 
 */
package seedu.malitio.model.task;

import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.model.tag.UniqueTagList;

public class Events extends Task {
	
	private DateTime start = null;
    private DateTime end = null;
    
    private final String MESSAGE_INVALID_EVENT = "Event must start before it ends!";

	public Events(Name name, DateTime start, DateTime end, UniqueTagList tags) 
	        throws IllegalValueException {
	   super(name, tags);
	    
	   if(!isValidEvent(start, end)) {       
	       throw new IllegalValueException(MESSAGE_INVALID_EVENT);
	   }
	   this.start = start;
       this.end = end;

	}
	
	/**
     * Copy constructor.
     */
    public Events(ReadOnlyTask source) {
        super(source);
    }
    
    private static boolean isValidEvent(DateTime start, DateTime end) {
        return end.isAfter(start);
    }

    @Override
    public DateTime getDueDate() {
        return null;
    }

    @Override
    public DateTime getStartDate() {
        return start;
    }

    @Override
    public DateTime getEndDate() {
        return end;
    }

}
