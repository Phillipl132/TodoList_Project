package ui;

import model.Task;
import model.ToDoList;
import persistence.JsonReader;
import persistence.JsonWriter;


import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;


// Represent a todolist graphical UI
public class ToDoGUI extends JPanel implements ListSelectionListener {
    private static final String JSON_STORE = "./data/todolist.json";
    private static final String s = "◻◻";
    private static final String addString = "Add Task";
    private static final String saveString = "Save Task";
    private static final String deleteString = "Delete Task";


    private final JFrame frame = new JFrame("ListOfTask");

    private JList list;
    private DefaultListModel listModel;
    private ToDoList toDoList = new ToDoList();
    private JButton deleteButton;
    private JButton imageButton;
    private JButton saveButton;
    private JTextField titleTextFiled;
    private JTextField descriptionTextFiled;
    private JTextField hoursTextFiled;
    private final JLabel image = new JLabel();

    // EFFECTS: constructs graphical UI and runs application

    public ToDoGUI() {

        //super(new BorderLayout());
        JFrame frame = createAndShowGUI();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                toDoList.printLogToConsole();
                e.getWindow().dispose();
            }
        });
        initGUI();
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void initGUI() {

        setPreferredSize(new Dimension(800, 500));
        listModel = new DefaultListModel();
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
        JButton addButton = new JButton(addString);
        AddListener addListener = new AddListener(addButton);
        addButton.setActionCommand(addString);
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);
        JButton loadButton = new JButton("Load");
        LoadListener loadListener = new LoadListener();
        loadButton.setActionCommand("Load");
        loadButton.addActionListener(loadListener);
        JButton saveButton = new JButton("Save");
        SaveListener saveListener = new SaveListener();
        saveButton.setActionCommand("Save");
        saveButton.addActionListener(saveListener);
        deleteButton = new JButton(deleteString);
        imageButton = new JButton(s);
        deleteButton.setActionCommand(deleteString);
        deleteButton.addActionListener(new DeleteListener());
        titleTextFiled = new JTextField(10);
        descriptionTextFiled = new JTextField(10);
        hoursTextFiled = new JTextField(10);
        titleTextFiled.addActionListener(addListener);
        descriptionTextFiled.addActionListener(addListener);
        hoursTextFiled.addActionListener(addListener);
        titleTextFiled.getDocument().addDocumentListener(addListener);
        descriptionTextFiled.getDocument().addDocumentListener(addListener);
        hoursTextFiled.getDocument().addDocumentListener(addListener);
        //Create a panel that uses BoxLayout.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(deleteButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(titleTextFiled);
        buttonPane.add(descriptionTextFiled);
        buttonPane.add(hoursTextFiled);
        buttonPane.add(addButton);
        buttonPane.add(imageButton);
        buttonPane.add(loadButton);
        buttonPane.add(saveButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,10,5));
        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
        image.setIcon(new ImageIcon("./data/yo.png"));
        buttonPane.add(image);
    }






    // Modifies: listOfModel, toDoList
    // Effects: delete task from listOfModel, toDoList
    class DeleteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = list.getSelectedIndex();
            listModel.remove(index);
            toDoList.removeTaskByIndex(index);
            int size = listModel.getSize();

            if (size == 0) { //Nobody's left, disable firing.
                deleteButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;

                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    // Modifies: listOfModel, toDoList
    // Effects: loads toDoList from jSON file and add to listOfModel
    class LoadListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            loadTodoList();
            listModel.clear();
            for (int i = 0; i < toDoList.numTasks(); i++) {
                listModel.add(i, toDoList.getTaskByIndex(i));
            }
        }

        private void loadTodoList() {
            try {
                JsonReader jsonReader = new JsonReader(JSON_STORE);
                toDoList = jsonReader.read();
                System.out.println("Loaded to-do list" + JSON_STORE);
                toDoList.logLoad();
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }

    // Effects: saves toDoList to jSON file
    class SaveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            saveTodoList();

        }

        private void saveTodoList() {

            try {
                JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
                jsonWriter.open();
                jsonWriter.write(toDoList);
                jsonWriter.close();

                System.out.println("Saved to-do list" + JSON_STORE);
                toDoList.logSave();
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }

        }

    }

    // Modifies: listOfModel, toDoList
    // Effects: add task to listOfModel, toDoList
    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String title = titleTextFiled.getText();
            String desc = descriptionTextFiled.getText();
            int hours = Integer.parseInt(hoursTextFiled.getText());
            Task t = new Task(title, desc, hours);

            int index = list.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }


            toDoList.addTaskByIndex(t, index);
            listModel.insertElementAt(t.toString(), index);
            //If we just wanted to add to the end, we'd do this:
            //listModel.addElement(employeeName.getText());

            //Reset the text field.
            titleTextFiled.requestFocusInWindow();
            titleTextFiled.setText("");

            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }


        // Effects: determine if the given string is arleady in listModel
        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        // Effects: determine if text field is empty or not
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable delete button.
                deleteButton.setEnabled(false);

            } else {
                //Selection, enable the delete button.
                deleteButton.setEnabled(true);
            }
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     * @return
     */
    public JFrame createAndShowGUI() {

        //Create and set up the window.
        JFrame frame = new JFrame("ListOfTask");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Create and set up the content pane.
        //JComponent newContentPane = new ToDoGUI();
        this.setOpaque(true); //content panes must be opaque
        frame.setContentPane(this);

        //Display the window.
        frame.pack();
        frame.setVisible(true);

        return frame;
    }




    private void close() {

    }

   /* public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }*/
}

