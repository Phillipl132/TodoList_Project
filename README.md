This is my personal todo list project. This application is designed for anyone who needs help to keep track of plans, deadlines, and activties, as well to keep track of random things to do. This application is able to ask the user to add or delete tasks, and generate a to-do list. Some additional features of this application could include a view of all tasks, tasks to-be-completed, completed tasks, overdue tasks, and a timer/alarm. This application is interesting to me because it is an extremely useful and versatile one. Creating this application will also satisfy my personal needs of keeping track of all my deadlines.

User Stories:
- As a user, I want to be able to add a task to my to-do list
- As a user, I want to be able to view all my tasks on my to-do list
- As a user, I want to be able to delete a task from my to-do-list
- As a user, I want to be able to quit my application - As a user, I want to be able to save my to-do list to file - As a user, I want to be able to be able to load my to-do list from file

Phase 4: Task 2 - Event Log Example:
Fri Nov 26 00:37:12 PST 2021
Task from todo list loaded from Json File
Fri Nov 26 00:37:15 PST 2021
Task deleted from todo list
Fri Nov 26 00:37:15 PST 2021
Task deleted from todo list
Fri Nov 26 00:37:16 PST 2021
Task deleted from todo list
Fri Nov 26 00:37:19 PST 2021
Task from todo list saved to Json File
Fri Nov 26 00:37:23 PST 2021
Task deleted from todo list
Fri Nov 26 00:37:24 PST 2021
Task from todo list loaded from Json File

Phase 4: Task 3 - Potential Further Improvements:
Given more time to work on the project, I would refactor the ToDoGUI Class. I would first write a few helper functions for the constructor. My current constructor is very long and messy, and by creating helpers that each take care of a task(eg. create the buttons on panel) the code would be much more easier to debug and read. Also, instead of making an individual class for each listener action in the ToDoGUI class, I can make one class for all listener actions, and create methods in the task for the specific listener actions(eg. add, delete).
