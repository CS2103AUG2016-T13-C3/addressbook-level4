package seedu.malitio.commons.events.storage;

import seedu.malitio.commons.events.BaseEvent;
import seedu.malitio.model.UserPrefs;

//@@author a0126633j
/**
 * Indicates that the storage file directory for Malitio data has changed
 * 
 */
public class DataStorageFileChangedEvent extends BaseEvent {

    public String dataFilePath;
    
    public DataStorageFileChangedEvent(String dataFilePath) {
        this.dataFilePath = dataFilePath;
    }
    
    @Override
    public String toString() {
        return "Directory of storage changed to " + dataFilePath;
    }
    
}
