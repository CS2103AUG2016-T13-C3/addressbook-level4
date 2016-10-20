package seedu.malitio.testutil;

import seedu.malitio.commons.exceptions.DuplicateDataException;
import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.model.Malitio;
import seedu.malitio.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestFloatingTask floatingTask1, floatingTask2, manualFloatingTask1, manualFloatingTask2;
    public static TestDeadline deadline1, deadline2, manualDeadline1, manualDeadline2;
    public static TestEvent event1, event2, event3, event4, manualEvent1, manualEvent2;
    
    public TypicalTestTasks() {
        try {
            floatingTask1 =  new FloatingTaskBuilder().withName("Adjust meter")
                    .withTags("careful").build();
            floatingTask2 = new FloatingTaskBuilder().withName("Bring along notes")
                    .withTags("pen", "cs2103").build();
            deadline1 = new DeadlineBuilder().withName("Cut hair").dueOn("11122016 2000").build();
            deadline2 = new DeadlineBuilder().withName("Do some sit-up").dueOn("21112016 2359").build();
            event1 = new EventBuilder().withName("Eat with mom").start("21102016 1800").end("21102016 1855").build();
            event2 = new EventBuilder().withName("Forgive with forget").start("22012017 1000").end("23022017 1000").build();
            event3 = new EventBuilder().withName("Go shopping").start("30032017 0900").end("30032017 2000").build();
            event4 = new EventBuilder().withName("Hopping").start("01112016 0400").end("01112016 0600").build();

            //Manually added
            manualFloatingTask1 = new FloatingTaskBuilder().withName("Spa relaxation").build();
            manualFloatingTask2 = new FloatingTaskBuilder().withName("Play cards").build();
            manualDeadline1 = new DeadlineBuilder().withName("Prepare for interview").dueOn("20102016 0000").build();
            manualDeadline2 = new DeadlineBuilder().withName("Get watch fixed").dueOn("21102016 2359").build();
            manualEvent1 = new EventBuilder().withName("Boring Lecture").start("17112016 1100").end("17112016 1200").build();
            manualEvent2 = new EventBuilder().withName("Scary Interview").start("18122016 1300").end("18122016 1400").build();
            
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadmalitioWithSampleData(Malitio ab) {
        try {
            ab.addFloatingTask(new FloatingTask(floatingTask1));
            ab.addFloatingTask(new FloatingTask(floatingTask2));
            ab.addDeadline(new Deadline(deadline1));
            ab.addDeadline(new Deadline(deadline2));
            ab.addEvent(new Event(event1));
            ab.addEvent(new Event(event2));
            ab.addEvent(new Event(event3));
        } catch (DuplicateDataException e) {
            assert false : "not possible";
        }
    }

    public TestFloatingTask[] getTypicalFloatingTasks() {
        return new TestFloatingTask[]{floatingTask1, floatingTask2};        
    }
    
    public TestDeadline[] getTypicalDeadlines() {
        return new TestDeadline[]{deadline1, deadline2};
    }
    
    public TestEvent[] getTypicalEvents() {
        return new TestEvent[]{event1, event2, event3};
    }
        

    public Malitio getTypicalMalitio() {
        Malitio ab = new Malitio();
        loadmalitioWithSampleData(ab);
        return ab;
    }
}
