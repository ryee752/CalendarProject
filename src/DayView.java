import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
/**
 * Fall 2022 CS151 Assignment 5 Submission
 * @author Ryan Yee
 * @version 1.0 11/30/22
 *
 * DayView is a JPanel that displays the day view of a calendar. It
 * creates and displays all the scheduled events on a given day.
 * DayView is a part of the CalendarDisplay and the view in the MVC pattern.
 */
public class DayView extends JPanel {
    private CalendarModel model;
    private CalendarDisplay view;
    private JPanel date;
    private JLabel day, allEvents ;
    private final int PANEL_HEIGHT = 80;
    private final int PANEL_WIDTH = 350;
    private final int LABEL_HEIGHT = 50;
    private final int LABEL_WIDTH = 450;

    /**
     * Creates a DayView with a given CalendarModel and
     * CalendarDisplay
     * @param m a CalendarModel holding the data of a calendar
     * @param d a CalendarDisplay to be added to
     */
    public DayView(CalendarModel m, CalendarDisplay d){
        model = m;
        view = d;
        setLayout(null);
    }

    private void createDatePanel(){
        //displays the current date in the calendar
        date = new JPanel(new FlowLayout());
        date.setBounds(150,0, 125, 35);
        LocalDate currDate = model.getDate();
        String s;
        int dayOfWeek = currDate.getDayOfWeek().getValue();
        if (dayOfWeek == 1){
            s = "Monday ";
        }
        else if (dayOfWeek == 2){
            s = "Tuesday ";
        }
        else if (dayOfWeek == 3){
            s = "Wednesday ";
        }
        else if (dayOfWeek == 4){
            s = "Thursday ";
        }
        else if (dayOfWeek == 5){
            s = "Friday ";
        }
        else {
            s = "Saturday ";
        }
        JLabel dateLabel = new JLabel( s + currDate.getMonthValue() + "/" + currDate.getDayOfMonth());
        date.add(dateLabel);
        add(date);
    }

    private void createSchedule(){
        if (model.getData().containsKey(model.getDate())){
            fillSchedule();
        }
        //displays 24 ScheduleLabels that each represent one hour in a day
        day = new JLabel();
        day.setBounds(0, 35, 450, 1200);
        day.setLayout(null);
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        day.setBorder(border);
        day.setBackground(new Color(255, 255, 255, 0));
        day.setOpaque(true);
        int y = 0;
        ScheduleLabel schedule = new ScheduleLabel(24);
        schedule.setBounds(0, y, 450, 50);
        day.add(schedule);
        y += LABEL_HEIGHT;
        for (int i = 1; i < 24; i++){
            schedule = new ScheduleLabel(i);
            schedule.setBounds(0, y, 450, 50);
            day.add(schedule);
            y += LABEL_HEIGHT;
        }
        add(day);
    }

    private void fillSchedule(){
        //calculates and displays all the events scheduled on this date
        ArrayList<Event> events = model.getData().get(model.getDate());
        Collections.sort(events);
        allEvents = new JLabel();
        allEvents.setBackground(new Color(255, 255, 255, 0));
        allEvents.setBounds(50, 35, 400, 1200);
        allEvents.setLayout(null);
        Composite c = new Composite(0, 0, 450, 720);
        for (Event e : events){
            TimeInterval t = e.getInterval();
            LocalTime start = t.getStart();
            LocalTime end = t.getEnd();
            double sHour = start.getHour();
            double eHour = end.getHour();
            double sMin = start.getMinute();
            double eMin = end.getMinute();
            double x = 0;
            //calculates the upper left corner of the rectangle that represents the scheduled event
            double y = (sHour + (sMin / 60)) * LABEL_HEIGHT;
//            System.out.println("y: "+y);
            double barLength = LABEL_WIDTH; //width stays the same for all rectangles
            double barHeight = ((eHour + (eMin / 60)) - (sHour + (sMin / 60))) * LABEL_HEIGHT;
//            System.out.println("height: "+barHeight);
            c.addPart(new EventBar(x, y, barLength, barHeight, e));
            //uses composite pattern to add all shapes representing event to a Composite
        }
        ShapeIcon icon = new ShapeIcon(c, 450, 720); //icon containing all scheduled events
        allEvents.setIcon(icon);
//        allEvents.setOpaque(true);
        add(allEvents);
    }

    /**
     * Paints the DayView of a calendar
     * @param g the Graphics object
     */
    public void paintComponent(Graphics g) {
        removeAll();
        createDatePanel();
        createSchedule();
        revalidate();
    }
}
