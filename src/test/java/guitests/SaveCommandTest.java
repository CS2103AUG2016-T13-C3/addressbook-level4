package guitests;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import seedu.malitio.commons.exceptions.DataConversionException;
import seedu.malitio.commons.util.ConfigUtil;
import seedu.malitio.logic.commands.SaveCommand;
import seedu.malitio.model.Malitio;
import seedu.malitio.storage.StorageManager;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

//@@author a0126633j
/**
 * Assumes storage and file utils are working 
 * 
 */
public class SaveCommandTest extends MalitioGuiTest {
    private static final String DEFAULT_FILE_PATH_USING_BACKSLASH = "src\\test\\data\\tempDataForSaveCommand\\";
    private static final String DEFAULT_FILE_NAME = "malitio.xml";
    private static final String TEST_FILE_PATH = "src/test/data/tempDataForSaveCommand/test1/";
    private static final String DEFAULT_FILE_PATH = "src/test/data/tempDataForSaveCommand/";
   
    // a storage manager to read data from xml file
    private StorageManager storageManager = new StorageManager("random", "random");
    
    private Malitio original = getInitialData();
    private String originalFilePath;
    
    @Before
    public void getOriginalFilePath() throws DataConversionException {
        originalFilePath = ConfigUtil.readConfig("./config.json/").get().getMalitioFilePath();
    }
    
    @Test
    public void save() throws DataConversionException, IOException {
            
    //save default file location
    commandBox.runCommand("save " + DEFAULT_FILE_PATH);
    assertSaveSuccessful(DEFAULT_FILE_PATH);
   
    //save in new file location
    commandBox.runCommand("save " + TEST_FILE_PATH);
    assertSaveSuccessful(TEST_FILE_PATH);
    assertFileDeletionSuccessful(DEFAULT_FILE_PATH);
    
    //save default file location again but with back slash
    commandBox.runCommand("save " + DEFAULT_FILE_PATH_USING_BACKSLASH);
    assertSaveSuccessful(DEFAULT_FILE_PATH_USING_BACKSLASH);
    assertFileDeletionSuccessful(TEST_FILE_PATH);
    
    //invalid file path
    commandBox.runCommand("save abc");
    assertResultMessage(SaveCommand.MESSAGE_INVALID_DIRECTORY + SaveCommand.MESSAGE_DIRECTORY_EXAMPLE);
    
    //Preserves orginal save file location after the tests
    ConfigUtil.changeMalitioSaveDirectory(originalFilePath);
    }

    /**
     * Asserts data file is present in new file location and consistent with Malitio data
     * @throws DataConversionException 
     * @throws IOException 
     */
    public void assertSaveSuccessful(String newFileLocation) throws DataConversionException, IOException {
        File f = new File(newFileLocation + DEFAULT_FILE_NAME);
        if(f.exists()) {
            //checks consistency of data in the file
            assertEquals(original, new Malitio(storageManager.readMalitio(newFileLocation + DEFAULT_FILE_NAME).get()));
            assertResultMessage(String.format(SaveCommand.MESSAGE_SAVE_SUCCESSFUL, newFileLocation + DEFAULT_FILE_NAME));
        } else {
            assertTrue(false);
        }
    }

    /**
     * Asserts old file is successfully deleted 
     */
    public void assertFileDeletionSuccessful(String oldFileLocation) {
        File f = new File(oldFileLocation + DEFAULT_FILE_NAME);
        assertFalse(f.exists());
    }
}