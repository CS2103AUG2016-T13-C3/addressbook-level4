**Loading the data into Malitio**

1. Download Malitio's latest jar release, malitio.jar and SampleData.xml file in \src\test\data\ManualTesting\

2. Create a folder named 'data' in the same folder where malitio.jar is located. Rename SampleData.xml to malitio.xml.

3. Place SampleData.xml in the newly created 'data' folder.

4. Rename SampleData(XML file) to malitio.

5. Check: You should have malitio.jar file and 'data' folder in the same location. 'data' folder should contain malitio.xml file

5. Start malitio.jar and it will be loaded with the sample data

Input :

Expected : Invalid command format!

help: Shows program usage instructions.

Example: help

UI : Command Box becomes red

Input : **saufi**

Expected : Unknown command

UI : Command Box becomes red

(Press esc key to clear command box)

Input : **redo**

Expected : no action to redo!

UI : Command Box is cleared

Input : **undo**

Expected : no action to undo!

UI : Command Box is cleared

**Add Command**

Input : **add**
Expected : Invalid command format!

add: adds a task to Malitio. Task name cannot contain &#39;/&#39;.

Parameters: NAME [by DEADLINE] [start STARTTIME end ENDTIME] [t/TAG]...

Example: add Pay John $100 by Oct 11 2359 t/oweMoney

Input : **add buy apples t/grannysmith**

Expected  Message: New task added Task: buy apples Tags: [grannysmith]

UI : New task added to-do list, index F14.

Input : **add cs2103 testing script by 10 dec 5pm t/doingnow**

Expected : New task added Deadline: cs2103 testing script Due: 10-Dec-2016, 17:00 (Sat) Tags: [doingnow]

UI : New deadline added to deadline list, index D13.

Input : **add friend 21st birthday celebration start 15 dec 4pm end 15 dec 10pm t/buypresent**

Expected : New task added Event: friend 21st birthday celebration Start: 15-Dec-2016, 16:00 (Thu) End: 15-Dec-2016, 22:00 (Thu) Tags: [buypresent]

UI : New event added to schedule list, index E9.

Input : **add eat supper with friends start todayyy end tomorrow**

Expected : Unrecognised date and time!

UI : Command Box becomes red.

Input : **add outing with secondary school friends end today**

Expected : Expecting start and end times

Example: start thursday 0800 end thursday 0900

UI : Command Box becomes red.

Input : **add lecture quiz start next year end next month**

Expected : Event must start before it ends!

UI : Command Box becomes red.

Input : **add friend 21st birthday celebration start 15 dec 4pm end 15 dec 10pm t/buypresent**

Expected : This event already exists in Malitio

UI : Not changed

Input : **add cs2103 testing script by 10 dec 5pm t/doingnow**

Expected : This deadline already exists in Malitio

UI : Not changed

Input : **add buy apples t/grannysmith**

Expected : This floating task already exists in Malitio

UI : Not changed

Input : **undo**

Expected : Undo successful. Undo add Event: friend 21st birthday celebration Start: 15-Dec-2016, 16:00 (Thu) End: 15-Dec-2016, 22:00 (Thu) Tags: [buypresent]

UI : Card E9 changed to Christmas Party

Input : **undo**

Expected : Undo successful. Undo add Deadline: cs2103 testing script Due: 10-Dec-2016, 17:00 (Sat) Tags: [doingnow]

UI : Card D13 changed to study for cs2103 finals

Input : **redo**

Expected : Redo successful. Redo add Deadline: cs2103 testing script Due: 10-Dec-2016, 17:00 (Sat) Tags: [doingnow]

UI : Card D13 revert back to cs2103 testing script

Input : **redo**

Expected : Redo successful. Redo add Event: friend 21st birthday celebration Start: 15-Dec-2016, 16:00 (Thu) End: 15-Dec-2016, 22:00 (Thu) Tags: [buypresent]

UI : Card E9 revert back to friend 21st birthday celebration

**Delete Command**

<!-- Invalid arguments -->

Input : **delete**

Expected :  Invalid command format!

delete: Deletes the task identified by the index used in the last task listing.

Parameters: INDEX

Example: delete D1

UI : Command Box becomes red

Input : **delete f0**

Expected : The task index provided is invalid

UI : Command Box becomes red

<!--- Valid arguments -->

Input : **delete f2**

Expected : Deleted Task: learn new recipes Tags: [baking][japanese]

UI : Second card in to-do list updated to F2.go skydiving

Input: **delete e2**

Expected : Deleted Event: wedding dinner Start: 05-Dec-2016, 18:00 (Mon) End: 05-Dec-2016, 21:00 (Mon) Tags:

UI : Second card in event list updated to E2. st3233 Lecture

Input: **delete d2**

Expected : Deleted Deadline: upload ST4231 tutorial Due: 03-Nov-2016, 23:59 (Thu) Tags: [needUpload]

UI : Second card in deadline list updated to D2.CS2107 project and video

Input : **undo**

Expected : Undo Successful. Undo delete Deadline: upload ST4231 tutorial Due: 03-Nov-2016, 23:59 (Thu) Tags: [needUpload]

UI : Second card in deadline list changed to D2.upload ST4231 tutorial

Input : **redo**

Expected : Redo Successful. Redo delete Deadline: upload ST4231 tutorial Due: 03-Nov-2016, 23:59 (Thu) Tags: [needUpload]

UI : Second card in deadline list revert back to D2.CS2107 project and video

**Edit Command**

Input : **edit d4 study for MA3269 quiz or not**

Expected : Successfully edited task.

Old: Deadline: study for MA3269 quiz Due: 06-Dec-2016, 22:17 (Tue) Tags:

New: Deadline: study for MA3269 quiz or not Due: 06-Dec-2016, 22:17 (Tue) Tags:

UI : Fourth card in deadline list updated. (Name changed)

Input : **edit f1 design dream house and backyard**

Expected : Successfully edited task.

Old: Task: design dream house Tags: [modern]

New: Task: design dream house and backyard Tags: [modern]

UI : First card in to-do list updated. (Name changed)

Input : **edit f2 t/extremesports t/wheeee**

Expected : Successfully edited task.

Old: Task: go skydiving Tags:

New: Task: go skydiving Tags: [extremesports][wheeee]

UI : Second card in to-do list updated. (Tags added)

Input : **edit e2 t/null**

Expected : Successfully edited task.

Old: Event: st3233 lecture Start: 07-Dec-2016, 08:00 (Wed) End: 07-Dec-2016, 09:30 (Wed) Tags: [timeseries]

New: Event: st3233 lecture Start: 07-Dec-2016, 08:00 (Wed) End: 07-Dec-2016, 09:30 (Wed) Tags:

UI : Second card in schedule list updated. (Removed tags)

Input : **edit d2 by 1 dec 4pm t/extended**

Expected : Successfully edited task.

Old: Deadline: CS2107 project and video Due: 04-Nov-2016, 23:59 (Fri) Tags:

New: Deadline: CS2107 project and video Due: 01-Dec-2016, 16:00 (Thu) Tags: [extended]

UI : Edited deadline now in D3. (Duedate changed, tag added)

Input : **edit e3 start 7 dec 8.30am**

Expected : Successfully edited task.

Old: Event: ma3269 lecture quiz Start: 07-Dec-2016, 10:00 (Wed) End: 07-Dec-2016, 11:30 (Wed) Tags:

New: Event: ma3269 lecture quiz Start: 07-Dec-2016, 08:30 (Wed) End: 07-Dec-2016, 11:30 (Wed) Tags:

UI : Third card in schedule list updated (Start time changed)

Input : **edit e3 end 7 dec 9am**

Expected : Successfully edited task.

Old: Event: ma3269 lecture quiz Start: 07-Dec-2016, 08:30 (Wed) End: 07-Dec-2016, 11:30 (Wed) Tags:

New: Event: ma3269 lecture quiz Start: 07-Dec-2016, 08:30 (Wed) End: 07-Dec-2016, 09:00 (Wed) Tags:

UI : Third card in schedule list updated (End time changed)

Input : **undo**

Expected : Undo successful. Undo edit from

Event: ma3269 lecture quiz Start: 07-Dec-2016, 08:30 (Wed) End: 07-Dec-2016, 09:00 (Wed) Tags:  to

Event: ma3269 lecture quiz Start: 07-Dec-2016, 08:30 (Wed) End: 07-Dec-2016, 11:30 (Wed) Tags:

UI : Third card in schedule list revert back (End time changed)

Input : **redo**

Expected : Redo successful. Redo edit from

Event: ma3269 lecture quiz Start: 07-Dec-2016, 08:30 (Wed) End: 07-Dec-2016, 11:30 (Wed) Tags:  to

Event: ma3269 lecture quiz Start: 07-Dec-2016, 08:30 (Wed) End: 07-Dec-2016, 09:00 (Wed) Tags:

UI : Third card in schedule list updated (End time changed)

Input :   **edit e3 end 5 dec 9am**

Expected : Event must start before it ends!

UI : Command Box becomes red

Input : **edit e3 start 10 dec 8pm**

Expected : Event must start before it ends!

UI : Command Box becomes red

Input : **edit e3 by today**

Expected : Changing of task type not supported. Please do not use key words (by, start, end) in names

UI : Command Box becomes red

Input : **edit d3 end tomorrow start today**

Expected : Changing of task type not supported. Please do not use key words (by, start, end) in names

UI : Command Box becomes red

Input : **edit f1 by today**

Expected : Changing of task type not supported. Please do not use key words (by, start, end) in names

UI : Command Box becomes red

Input : **edit e0 a**

Expected : The task index provided is invalid

UI : Command Box becomes red

**Complete Command**

Input : **complete f1**

Expected : Successfully completed floating task.

UI : Everything in the box is grayed out and the name is striked out at index F1, command box cleared

Input: **undo**

Expected : Undo complete successful

UI : Everything in the box is un-grayed out and the name is not striked out at index F1, command box cleared

Input: **redo**

Expected : Redo complete successful

UI : Everything in the box is grayed out and the name is striked out at index F1, command box cleared

Input : **complete f1**

Expected : The floating task is already completed in Malitio

UI : Command box cleared

Input : **complete asdf**

Expected : Invalid command format!

complete: complete the task or deadline identified by the index number used in the last task listing.

Parameters: INDEX (must be either &#39;f&#39;/&#39;d&#39; and a positive integer) Example: complete f1

UI : Command box becomes red

Input : **complete f0**

Expected : The task index provided is invalid

UI : Command box becomes red

Input : **complete f200**

Expected : The task index provided is invalid

UI : Command box becomes red

Input : **complete d1**

Expected : Successfully completed deadline.

UI : the deadline that is in red disappears from view, command box cleared

Input: **undo**

Expected : Undo complete successful

UI : The task reappear at index D1 and colored red, command box cleared

Input: **redo**

Expected : Redo complete successful

UI : the deadline that is in red disappears from view, command box cleared

Input : **complete d5**

Expected : Successfully completed deadline.

UI : Everything in the box is grayed out and the name is striked out at index D5, command box cleared.

Input : **complete d5**

Expected : The deadline is already completed in Malitio

UI : Command box cleared

Input : **complete d0**

Expected :The task index provided is invalid

UI : Command box becomes red

Input : **complete d200**

Expected :The task index provided is invalid

UI : Command box becomes red

**Uncomplete Command**

Input : **uncomplete f1**

Expected : Successfully uncomplete floating task.

UI : Everything in the box is un-grayed out and the name is not striked out at index F1, command box cleared

Input: **undo**

Expected : Undo uncomplete successful

UI : Everything in the box is grayed out and the name is striked out at index F1, command box cleared

Input: **redo**

Expected : Redo uncomplete successful

UI : Everything in the box is un-grayed out and the name is striked out at index F1, command box cleared

Input : **uncomplete f1**

Expected : The floating task is already uncompleted in Malitio

UI : Command box cleared

Input : **uncomplete f0**

Expected : The deadline index provided is invalid

UI : Command box becomes red

Input : **uncomplete f200**

Expected : The deadline index provided is invalid

UI : Command box becomes red

Input : **uncomplete d5**

Expected : Successfully uncomplete deadline.

UI : Everything in the box is un-grayed out and the name is back to normal at index F5, command box cleared

Input : **uncomplete d5**

Expected : The deadline is already uncompleted in Malitio

UI : Command box cleared

Input :  **listall**

**uncomplete d1**

Expected : Listed all tasks from beginning of time

  Successfully uncomplete deadline.

UI:  Showing everything from beginning of time, command box cleared

The deadline is back to the list and it is colored red, command box cleared

Input: **undo**

Expected : Undo uncomplete successful

UI : The deadline at index D1 disappears, command box cleared

Input: **redo**

Expected : Redo uncomplete successful

UI : the deadline appears at D1 and colored red, command box cleared


Input : **uncomplete d0**

Expected : The deadline index provided is invalid

UI : Command box becomes red

Input : **uncomplete d200**

Expected : The deadline index provided is invalid

UI : Command box becomes red

Input: **undo**

Expected : Undo uncomplete successful

UI : D1. Collect Passport disappears from deadline list

Input: **undo**

Expected : Undo uncomplete successful

UI : D5. add logo for Malitio gets gray-ed out and striked-out


**Clear Command**


Input : **clear absd**

Expected : Invalid command format! 

clear: Deletes multiple tasks at once.

Parameters: none OR  expired

Example: clear, clear expired

UI: Command Box becomes red

Input: **listall**

Expected: Listed all tasks from beginning of time

UI: Displays all tasks

Input : **clear expired**

Expected : Malitio has been cleared!

UI: 13 tasks in to-do list, 15 tasks in deadlines list, and 11 tasks in schedule

Input: **listall**

Expected: Listed all tasks from beginning of time

UI: Does not change

Input: **undo**

Expected : Undo clear successful

UI: Does not change

Input : **clear**

Expected : Malitio has been cleared!

UI: Empty lists

Input: **undo**

Expected : Undo clear successful

UI: Lists get populated

Input: **redo**

Expected : Redo clear successful

UI: Empty lists

Input: **undo**

Expected : Undo clear successful

UI: Lists get populated

**Mark Command**

Input: **mark f1**

Expected: Task has been marked as priority

UI: f1 changes to yellow

Input: **mark f5**

Expected: Task has been marked as priority

UI: f5 changes to yellow

Input: **undo**

Expected: Undo mark successful

UI: f5 changes back to white

Input: **redo**

Expected: Redo mark successful

UI: f5 changes back to yellow

Input: **mark f1**

Expected: Task has already been marked as priority

UI: none

Input: **mark d5**

Expected: Task has been marked as priority

UI: d5 changes to yellow

Input: **mark e3**

Expected: Task has been marked as priority

UI: e3 changes to yellow

Input: **mark d0**

Expected: The task index provided is invalid

UI: Command Box becomes red

Input: **mark f300**

Expected: The task index provided is invalid

UI: Command Box becomes red

Input: **mark do**

Expected: Invalid command format!

mark: Marks specified task or deadline as priority in Malitio

Parameters: INDEX

Example: mark f1

UI: Command Box becomes red

**Unmark Command**

Input: **unmark f1**

Expected: Task has been unmarked as priority

UI: f1 changes to white

Input: **unmark d5**

Expected: Task has been unmarked as priority

UI: d5 changes to white

Input: **unmark d5**

Expected: Task has already been unmarked as priority

UI: none

Input: **unmark e3**

Expected: Task has been unmarked as priority

UI: e3 changes to white

Input: **undo**

Expected: Undo unmark successful

UI: e3 changes back to yellow

Input: **redo**

Expected: Redo unmark successful

UI: e3 changes back to white

Input: **unmark e0**

Expected: The task index provided is invalid

UI: Command Box becomes red

Input: **unmark e300**

Expected: The task index provided is invalid

UI: Command Box becomes red

Input: **unmark**

Expected: Invalid command format!

unmark: Unmarks specified task or deadline as priority in Malitio

Parameters: INDEX

Example: unmark f1

UI: Command Box becomes red

**List Command**

Input: **list 05 nov**

Expected: Listed all tasks

UI: lists tasks on and after 5th nov

Input: **list tasks**

Expected: Listed floating tasks

UI: lists all tasks

Input: **list deadlines**

Expected: Listed deadlines

UI: lists overdue and non-completed deadlines

Input: **list deadlines invalid**

Expected: Invalid command format!

list: Lists specified type of task to Malitio

Parameters: [events|deadlines|tasks] [DATETIME]

Example: list deadlines sunday midnight

UI: Command Box becomes red

Input: **list events dec 5 8am**

Expected: Listed events

UI: lists events on and after dec 5th, 8am

Input: **list**

Expected: Listed all tasks

UI: lists all current/relevant tasks

**Find Command**

<!-- Invalid input -->

Input: **find**

Expected: Invalid command format!

find: Finds [specified] tasks whose names contain any of the specified keywords and displays them as a list with index numbers.

Parameters: KEYWORD [MORE\_KEYWORDS]...

Example: find [f/d/e] adjust bring chill

UI: Command Box becomes red

Input: **find e**

Expected: Invalid command format!

find: Finds [specified] tasks whose names contain any of the specified keywords and displays them as a list with index numbers.

Parameters: KEYWORD [MORE\_KEYWORDS]...

Example: find [f/d/e] adjust bring chill

UI: Command Box becomes red

Input: **find d**

Expected: Invalid command format!

find: Finds [specified] tasks whose names contain any of the specified keywords and displays them as a list with index numbers.

Parameters: KEYWORD [MORE\_KEYWORDS]...

Example: find [f/d/e] adjust bring chill

UI: Command Box becomes red

Input: **find f**

Expected: Invalid command format!

find: Finds [specified] tasks whose names contain any of the specified keywords and displays them as a list with index numbers.

Parameters: KEYWORD [MORE\_KEYWORDS]...

Example: find [f/d/e] adjust bring chill

UI: Command Box becomes red

<!-- valid input -->

<!-- finding in each panel-->

Input: **find f learn**

Expected: 1 tasks found!

UI: To-Do List now shows 1 tasks

Input: **find d 10-dec**

Expected: 4 tasks found!

UI: Deadline List now shows 4 tasks, all due on 10-Dec

Input: **find e presentation**

Expected: 1 tasks found!

UI: Schedule List now shows 1 task

<!-- finding in all panels -->

Input: **listall** followed by **find cs**

Expected: 8 tasks found!

UI: Deadline List shows 4 tasks, and Schedule List now shows 4 tasks

<!-- multiple keywords and multiple finds -->

Input: **list** followed by **find japan dinner project**

Expected: 6 tasks found!

UI: To-Do List shows 1 task, Deadline List shows 4 tasks, and Schedule List now shows 1 task

Input: **find cs**

Expected: 2 tasks found!

UI: Deadline List shows 2 tasks

**Save Command**

<!-- invalid file path -->

Input : **save**

Expected : Invalid command format! 

save: Changes data file location of Malitio.

Parameters: File Directory

Example: save C://Users/User PC/Downloads/

UI: Command Box becomes red
 
Input : **save data**

Expected : The directory is invalid! Valid file paths must end with '/' or '\'

Example: C://Users/User PC/Downloads/

Input : **save newFolderForSave/**

Expected : Malitio data will be saved in newFolderForSave/malitio.xml from now onwards.

UI : Data file is saved at newFolderForSave/malitio.xml
<!-- saving in the same folder -->

Input : **save newFolderForSave\**

Expected : Malitio data will be saved in newFolderForSave/malitio.xml from now onwards.

UI : Data file is saved at newFolderForSave\malitio.xml

<!-- saving in another folder -->

Input : **save data/**

Expected : Malitio data will be saved in data/malitio.xml from now onwards.

UI: Data file is saved at data/malitio.xml
