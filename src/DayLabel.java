import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
/**
 * Fall 2022 CS151 Assignment 5 Submission
 * @author Ryan Yee
 * @version 1.0 11/30/22
 *
 * DayLabel is a JPLabel that represents one day in a month in a calendar.
 * DayLabel is a part of the CalendarPanel. DayLabel is  both the view and the controller
 * in the MVC pattern.
 */
public class DayLabel extends JLabel {
    private CalendarDisplay display;
    private LocalDate date;
    private boolean selected;
    private CalendarModel model;
    private CalendarPanel panel;

    /**
     * Creates a DayLabel to represent a day in a month
     * @param d the LocalDate to be represented
     * @param s a string containing the day of the month (Ex:1-31)
     * @param b a boolean uses to determine whether to highlight the DayLabel
     * @param m the CalendarModel holding the data of the calendar
     * @param p the CalendarPanel it is to be added to
     * @param c the CalendarDisplay containing all the components
     */
    public DayLabel(LocalDate d, String s, boolean b, CalendarModel m, CalendarPanel p, CalendarDisplay c){
        super(s);
        date = d;
        selected = b;
        model = m;
        panel = p;
        display = c;
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        setBorder(border);
        setOpaque(true);
        addMouseListener(new MouseListeners());
    }

    /**
     * Mutator that changes the DayLabel to a selected state
     */
    public void select(){
        selected = true;
    }

    /**
     * Mutator that changes the DayLabel to an unselected state
     */
    public void unselect(){
        selected = false;
    }

    /**
     * Paints the DayLabel
     * @param g the Graphics object
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //if selected highlight the DayLabel
        if (selected){
            this.setBackground(Color.gray);
        }
        else{
            this.setBackground(Color.white);
        }
    }

    /**
     * Updates the Model and highlights this DayLabel while removing the
     * highlight from the previously selected date.
     */
    public void changeSelected(){
        panel.getDayLabels().get(model.getDate()).unselect();
        select();
        model.setDate(date);
        panel.updateCurrentDate();
        display.setSelected(true);
        model.updateListeners();
    }

    /**
     * MouseListeners is a Mouseadapter that will perform
     * actions when the mouse is clicked on the DayLabel
     */
    private class MouseListeners extends MouseAdapter {
        /**
         * Allows the DayLabel/controller to handle the data when the mouse is clicked
         * on the Daylabel
         * @param e the event to be processed
         */
        public void mouseClicked(MouseEvent e) {
            changeSelected();
        }
    }
}
