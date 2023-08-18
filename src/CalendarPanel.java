import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
/**
 * Fall 2022 CS151 Assignment 5 Submission
 * @author Ryan Yee
 * @version 1.0 11/30/22
 *
 * CalendarPanel is a JPanel that displays the month view of a calendar.
 * CalendarPanel is a part of the CalendarDisplay. CalendarPanel is both
 * the view and a controller in the MVC pattern.
 */
public class CalendarPanel extends JPanel {
    private CalendarDisplay view;
    private CalendarModel model;
    private LocalDate currDate;
    private JPanel monthYear, calendar, create;
    private JButton cButton;
    private DayLabel dayLabel;
    private JLabel emptyLabel;
    private HashMap<LocalDate, DayLabel> allDayLabels;
    private final int FINAL_BOX_WIDTH = 35;

    /**
     * Creates a CalendarPanel
     * @param m the CalendarModel holding the data of a calendar
     * @param d the CalendarDisplay to be added to
     */
    public CalendarPanel(CalendarModel m, CalendarDisplay d){
        model = m;
        view = d;
        currDate = m.getDate();
        allDayLabels = new HashMap<LocalDate, DayLabel>();
        setLayout(null);
    }

    private void createButton(){
        create = new JPanel();
        create.setBounds(0,0,100,40);
        cButton = new JButton("Create");
        cButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.isSelected()){
                    createNewEventFrame();
                    view.setSelected(false);
                }
                else{
                    JOptionPane.showMessageDialog(view, "Select a date on the calendar to create an event on");
                }
            }
        });
        create.add(cButton);
        add(create);
    }

    private void createNewEventFrame(){
        //acts as a controller that mutates the data in the model by adding an event
        JFrame createFrame = new JFrame("Create an Event");
        createFrame.setLocationRelativeTo(null);
        JLabel enter = new JLabel("Enter Event Name");
        JTextField eventName = new JTextField(30);
        DateTimeFormatter dFormat = DateTimeFormatter.ofPattern("M/d/yy");
        String s = "" + dFormat.format(model.getDate());
        JLabel date = new JLabel(s);
        JLabel to = new JLabel("to");
        JLabel timeFormat = new JLabel("Enter Time in AM/PM Format (EX: 10:00AM, 3:00PM)");
        JTextField start = new JTextField(10);
        JTextField end = new JTextField(10);
        JButton save = new JButton("SAVE");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (start.getText() != null && end.getText() != null && eventName.getText() != null){
                    if (start.getText().contains("am")){
                        start.setText(start.getText().replace("am", "AM"));
                    }
                    if (start.getText().contains("pm")){
                        start.setText(start.getText().replace("pm", "PM"));
                    }
                    if (end.getText().contains("am")){
                        end.setText(end.getText().replace("am", "AM"));
                    }
                    if (end.getText().contains("pm")){
                        end.setText(end.getText().replace("pm", "PM"));
                    }
                    DateTimeFormatter tFormat = DateTimeFormatter.ofPattern("h:mma");
                    LocalTime startTime = LocalTime.parse(start.getText(), tFormat);
                    LocalTime endTime = LocalTime.parse(end.getText(), tFormat);
                    TimeInterval cIntvl = new TimeInterval(model.getDate(), startTime, endTime);
                    Event evt = new Event(eventName.getText(), cIntvl);
                    createFrame.dispose();
                    //if event conflicts with another event display a message and prompt user to create another event
                    if (!view.add(evt)){
                        JOptionPane.showMessageDialog(view, "Overlaps with other events\nEvent not added\nTry Again");
                        createNewEventFrame();
                    }
                }
            }
        });
        JPanel createPanel = new JPanel();
        createPanel.setLayout(new GridLayout(0,1));
        createPanel.add(enter);
        createPanel.add(eventName);
        createPanel.add(date);
        createPanel.add(timeFormat);
        createPanel.add(start);
        createPanel.add(to);
        createPanel.add(end);
        createPanel.add(save);
        createFrame.add(createPanel);
        createFrame.pack();
        createFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createFrame.setLocationRelativeTo(null);
        createFrame.setVisible(true);
    }

    private void createMonthYear(){
        //displays the month and year
        monthYear = new JPanel();
        monthYear.setBounds(0,50, 150, 40);
        JLabel monthYearLabel = new JLabel(String.valueOf(currDate.getMonth()) + " " + currDate.getYear());
        monthYear.add(monthYearLabel);
        add(monthYear);
    }

    private void createCalendar(){
        //creates the calendar month view
        calendar = new JPanel(null);
        calendar.setBounds(0,80, 245,270);
        int j = 0;
        for (int i = 0; i < 7; i++){
            JLabel l;
            if (i == 0){
                l = calendarBoxMaker("S");
            }
            else if (i == 1){
                l = calendarBoxMaker("M");
            }
            else if (i == 2){
                l = calendarBoxMaker("T");
            }
            else if (i == 3){
                l = calendarBoxMaker("W");
            }
            else if (i == 4){
                l = calendarBoxMaker("T");
            }
            else if (i == 5){
                l = calendarBoxMaker("F");
            }
            else{
                l = calendarBoxMaker("S");
            }
            l.setBounds(j, 0, FINAL_BOX_WIDTH, FINAL_BOX_WIDTH);
            calendar.add(l);
            j += FINAL_BOX_WIDTH;
        }
        LocalDate date = LocalDate.of(currDate.getYear(), currDate.getMonth(), 1);
        int count = 7;
        int currMonth = date.getMonthValue();
        int x = 0;
        int y = FINAL_BOX_WIDTH;
        while (currMonth == date.getMonthValue()){
            if (date.getDayOfWeek().getValue() == count) {
                dayLabel = new DayLabel(date, "" + date.getDayOfMonth(), false, model, this, view);
                if (date.equals(currDate)){
                    dayLabel.select();
                }
                dayLabel.setBounds(x, y, FINAL_BOX_WIDTH, FINAL_BOX_WIDTH);
                calendar.add(dayLabel);
                allDayLabels.put(date, dayLabel);
                if (date.getDayOfWeek().getValue() == 6) {
                    x = 0;
                    y += FINAL_BOX_WIDTH;
                }
                else{
                    x += FINAL_BOX_WIDTH;
                }
                count++;
                if (count == 8) { //day of the week reset
                    count = 1;
                }
                date = date.plusDays(1);
//                }

            }
            else {
                emptyLabel = new JLabel(" ");
                emptyLabel.setBounds(x, y, FINAL_BOX_WIDTH, FINAL_BOX_WIDTH);
                calendar.add(emptyLabel);
                count++;
                if (count == 8) //day of the week reset
                {
                    count = 1;
                }
                x += FINAL_BOX_WIDTH;
            }
        }


        add(calendar);
    }

    private JLabel calendarBoxMaker(String s){
        JLabel label = new JLabel(s);
        return label;
    }

    /**
     * Returns a HashMap containing all the DayLabels in a calendar
     * @return a HashMap containing all the DayLabels in a calendar
     */
    public HashMap<LocalDate, DayLabel> getDayLabels(){
        return allDayLabels;
    }

    /**
     * Updates the Month view with the current month
     */
    public void updateCurrentDate()
    {
        currDate = model.getDate();
    }

    /**
     * Paints the Month view of the Calendar
     * @param g the Graphics object
     */
    public void paintComponent(Graphics g) {
        removeAll();
        createButton();
        createMonthYear();
        createCalendar();
        revalidate();
    }




}
