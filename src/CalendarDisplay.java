import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
/**
 * Fall 2022 CS151 Assignment 5 Submission
 * @author Ryan Yee
 * @version 1.0 11/30/22
 *
 * CalendarDisplay is a JFrame that contains and displays all the components of
 * the calendars view (Month and Day view). CalendarDisplay is both the view
 * and a controller in the MVC pattern.
 */
public class CalendarDisplay extends JFrame implements ChangeListener {

    private CalendarModel model;
    private JButton left, right, quit;
    private JPanel buttonsPanel, quitPanel;
    private CalendarPanel calendarPanel;
    private DayView dayPanel;
    private boolean dateSelected;
    private JScrollPane scrollPanel;

    /**
     * Creates a CalendarDisplay with ta given Model
     * @param m the CalendarModel with the data to be represented
     */
    CalendarDisplay(CalendarModel m){
        model = m;
        dateSelected = false;
        initialize();
        initializeArrowQuitButtons();
        initializeCalendar();
        initializeDayView();
        setVisible(true);
    }

    private void initialize(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(810,500);
        setLayout(null);
        setLocationRelativeTo(null);
    }

    private void initializeArrowQuitButtons(){
        left = new JButton("<");
        left.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LocalDate d = model.getDate();
                d = d.minusDays(1);
                model.setDate(d);
                calendarPanel.updateCurrentDate();
                setSelected(false);
                model.updateListeners();
            }
        });
        right = new JButton(">");
        right.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LocalDate d = model.getDate();
                d = d.plusDays(1);
                model.setDate(d);
                calendarPanel.updateCurrentDate();
                setSelected(false);
                model.updateListeners();
            }
        });
        buttonsPanel = new JPanel();
        buttonsPanel.setBounds(150, 0, 100, 50);
        buttonsPanel.add(left);
        buttonsPanel.add(right);
        add(buttonsPanel);
        quit = new JButton("Quit");
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //save to events.txt
                ArrayList<Event> allEvents = new ArrayList<Event>();
                for (ArrayList<Event> arr : model.getData().values()){
                    for (Event event : arr){
                        allEvents.add(event);
                    }
                }
                Collections.sort(allEvents);
                DateTimeFormatter dFormat = DateTimeFormatter.ofPattern("M/d/yy");
                DateTimeFormatter tFormat = DateTimeFormatter.ofPattern("h:mma");
                File f = new File("events.txt");
                try {
                    FileWriter w = new FileWriter(f);
                    for (Event event : allEvents)
                    {

                        w.write(event.getName() + "\n" + dFormat.format(event.getInterval().getDate())
                                + " " + tFormat.format(event.getInterval().getStart()) + " " +
                                tFormat.format(event.getInterval().getEnd())+ "\n");
                    }
                    w.close();

                }
                catch (IOException exception) {
//                    System.out.println("Could not write to file");
//                    exception.printStackTrace();
                }
                dispose();
            }
        });
        quitPanel = new JPanel();
        quitPanel.setBounds(530, 0,100,35);
        quitPanel.add(quit);
        add(quitPanel);
   }

   private void initializeCalendar(){
        calendarPanel = new CalendarPanel(model, this);
        calendarPanel.setBounds(0, 50, 300, 350);
        add(calendarPanel);
   }

    private void initializeDayView() {
        dayPanel = new DayView(model, this);
        dayPanel.setBounds(300, 35, 450, 1240);
//        add(dayPanel);
        this.getContentPane().setLayout(null);
        scrollPanel = new JScrollPane(dayPanel);
        scrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setBounds(300, 35, 450, 400);
        scrollPanel.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener(){
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                repaint();
            }
        });
        dayPanel.setPreferredSize(new Dimension(450, 1240));
        getContentPane().add(scrollPanel);
    }

    /**
     * Mutates the data in the model.
     * Adds an event if it doesn't conflict with other events
     * @param e the event to be added if valid
     * @return true if added, else false
     */
    public boolean add(Event e){
        TimeInterval intvl = e.getInterval();
        if (model.getData().containsKey(intvl.getDate()) == false) //if no events planned on date
        {
            model.update(e);
            return true;
        }
        ArrayList<Event> eList = model.getData().get(intvl.getDate());
        for (Event event:eList)
        {
            if (e.getInterval().overlap(event.getInterval()))
            {
//                System.out.println("Overlaps with other events\nEvent not added");
                return false;
            }
        }
        model.update(e);
        return true;
    }

    /**
     * Determines if a date in the month view is currently selected
     * @return true if a date is selected, else false
     */
    public boolean isSelected(){
        return dateSelected;
    }

    /**
     * Sets the selected variable to a boolean
     * @param b a boolean, true or false
     */
    public void setSelected(boolean b){
        dateSelected = b;
    }

    /**
     * Updates the view if a change is detected
     * @param e  a ChangeEvent object
     */
    public void stateChanged(ChangeEvent e) {
        scrollPanel.getVerticalScrollBar().setValue(0);
        repaint();
//        System.out.println(model.getDate().getDayOfMonth());
    }
}
