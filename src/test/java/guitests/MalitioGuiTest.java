package guitests;

import guitests.guihandles.*;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.testfx.api.FxToolkit;
import seedu.malitio.TestApp;
import seedu.malitio.testutil.TestUtil;
import seedu.malitio.testutil.TypicalTestTasks;
import seedu.malitio.commons.core.EventsCenter;
import seedu.malitio.model.Malitio;
import seedu.malitio.model.task.ReadOnlyDeadline;
import seedu.malitio.model.task.ReadOnlyEvent;
import seedu.malitio.model.task.ReadOnlyFloatingTask;

import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A GUI Test class for malitio.
 */
public abstract class MalitioGuiTest {

    /* The TestName Rule makes the current test name available inside test methods */
    @Rule
    public TestName name = new TestName();

    TestApp testApp;

    protected TypicalTestTasks td = new TypicalTestTasks();

    /*
     *   Handles to GUI elements present at the start up are created in advance
     *   for easy access from child classes.
     */
    protected MainGuiHandle mainGui;
    protected MainMenuHandle mainMenu;
    protected FloatingTaskListPanelHandle floatingTaskListPanel;
    protected DeadlineListPanelHandle deadlineListPanel;
    protected EventListPanelHandle eventListPanel;
    protected ResultDisplayHandle resultDisplay;
    protected CommandBoxHandle commandBox;
    private Stage stage;

    @BeforeClass
    public static void setupSpec() {
        try {
            FxToolkit.registerPrimaryStage();
            FxToolkit.hideStage();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setup() throws Exception {
        FxToolkit.setupStage((stage) -> {
            mainGui = new MainGuiHandle(new GuiRobot(), stage);
            mainMenu = mainGui.getMainMenu();
            floatingTaskListPanel = mainGui.getFloatingTaskListPanel();
            deadlineListPanel = mainGui.getDeadlineListPanel();
            eventListPanel = mainGui.getEventListPanel();
            resultDisplay = mainGui.getResultDisplay();
            commandBox = mainGui.getCommandBox();
            this.stage = stage;
        });
        EventsCenter.clearSubscribers();
        testApp = (TestApp) FxToolkit.setupApplication(() -> new TestApp(this::getInitialData, getDataFileLocation()));
        FxToolkit.showStage();
        while (!stage.isShowing());
        mainGui.focusOnMainApp();
    }

    /**
     * Override this in child classes to set the initial local data.
     * Return null to use the data in the file specified in {@link #getDataFileLocation()}
     */
    protected Malitio getInitialData() {
        Malitio ab = TestUtil.generateEmptymalitio();
        TypicalTestTasks.loadMalitioWithSampleData(ab);
        return ab;
    }

    /**
     * Override this in child classes to set the data file location.
     */
    protected String getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    @After
    public void cleanup() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    /**
     * Asserts the task shown in the card is same as the given task
     */
    public void assertMatching(ReadOnlyFloatingTask task, FloatingTaskCardHandle card) {
        assertTrue(TestUtil.compareCardAndTask(card, task));
    }
    public void assertMatching(ReadOnlyDeadline task, DeadlineCardHandle card) {
        assertTrue(TestUtil.compareCardAndTask(card, task));
    }
    public void assertMatching(ReadOnlyEvent task, EventCardHandle card) {
        assertTrue(TestUtil.compareCardAndTask(card, task));
    }
    
    /**
     * Asserts the size of the total list is equal to the given number.
     */
    protected void assertTotalListSize(int size) {
        int numberOfTasks = 
                floatingTaskListPanel.getNumberOfTasks() 
                + deadlineListPanel.getNumberOfTasks()
                + eventListPanel.getNumberOfTasks();
        assertEquals(size, numberOfTasks);
    }

    protected void assertFloatingTaskListSize(int size) {
        int numberOfTasks = floatingTaskListPanel.getNumberOfTasks();
        assertEquals(size, numberOfTasks);
    }
    protected void assertDeadlineListSize(int size) {
        int numberOfTasks = deadlineListPanel.getNumberOfTasks();
        assertEquals(size, numberOfTasks);
    }
    protected void assertEventListSize(int size) {
        int numberOfTasks = eventListPanel.getNumberOfTasks();
        assertEquals(size, numberOfTasks);
    }
    /**
     * Asserts the message shown in the Result Display area is same as the given string.
     */
    protected void assertResultMessage(String expected) {
        assertEquals(expected, resultDisplay.getText());
    }
}
