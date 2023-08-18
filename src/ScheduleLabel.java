import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
/**
 * Fall 2022 CS151 Assignment 5 Submission
 * @author Ryan Yee
 * @version 1.0 11/30/22
 *
 * ScheduleLabel is a JLabel that displays a visual representation
 * of an hour in a day in 12-hour AM/PM format. ScheduleLabel is a part of the DayView
 * and the view in the MVC pattern.
 */
public class ScheduleLabel extends JLabel {

    private final int PANEL_HEIGHT = 50;
    private final int PANEL_WIDTH = 350;
    private final int LABEL_HEIGHT = 25;
    private final int LABEL_WIDTH = 300;
    private final Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
    private JLabel timeLabel, firstHalf, secondHalf;
    private int hour;

    /**
     * Creates a ScheduleLabel for the given Hour
     * @param h an integer representing one hour of the day (1-24)
     */
    public ScheduleLabel(int h){
        hour = h;
        setLayout(null);
    }

    private void createHour(){
        //creates a JLabel to display the hour
        String time;
        if (hour == 12){
            time = "" + hour + "PM";
        }
        else if (hour == 24){
            time = "" + 12 + "AM";
        }
        else if (hour > 12){
            time = "" + (hour - 12) + "PM";
        }
        else{
            time = "" + hour + "AM";
        }
        timeLabel = new JLabel(time);
        timeLabel.setLayout(null);
        timeLabel.setBounds(0, 0, 50, PANEL_HEIGHT);
        timeLabel.setBorder(border);
        timeLabel.setOpaque(true);
        add(timeLabel);
    }

    private void createFirstHalf(){
        //creates a JLabel to display the first 30 minutes of an hour
        firstHalf = new JLabel();
        firstHalf.setLayout(null);
        firstHalf.setBounds(50, 0, 400, LABEL_HEIGHT);
        firstHalf.setBorder(border);
        firstHalf.setBackground(new Color(255, 255, 255, 0));
        firstHalf.setOpaque(true);
        add(firstHalf);
    }

    private void createSecondHalf(){
        //creates a JLabel to display the last 30 minutes within an hour
        secondHalf = new JLabel();
        secondHalf.setLayout(null);
        secondHalf.setBounds(50, LABEL_HEIGHT, 400, LABEL_HEIGHT);
        secondHalf.setBorder(border);
        secondHalf.setBackground(new Color(255, 255, 255, 0));
        secondHalf.setOpaque(true);
        add(secondHalf);
    }

    /**
     * Paints the ScheduleLabel
     * @param g the Graphics object
     */
    public void paintComponent(Graphics g) {
        createHour();
        createFirstHalf();
        createSecondHalf();
    }


}
