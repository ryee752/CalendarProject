import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Fall 2022 CS151 Assignment 5 Submission
 * @author Ryan Yee
 * @version 1.0 11/30/22
 *
 * CalendarModel represents the model in the MVC pattern.
 * It holds all the data of scheduled events in a calendar.
 */
public class CalendarModel {

    private LocalDate currDate;
    private HashMap<LocalDate, ArrayList<Event>> events;
    private ArrayList<ChangeListener> listeners;

    /**
     * Constructs a CalendarModel with the given data
     * @param data a HashMap of the data of a calendar
     */
    public CalendarModel(HashMap<LocalDate, ArrayList<Event>> data){
        currDate = LocalDate.now();
        events = data;
        listeners = new ArrayList<ChangeListener>();
    }

    /**
     * Returns the data
     * @return the HashMap containing the data
     */
    public HashMap<LocalDate, ArrayList<Event>> getData(){
        return (HashMap<LocalDate, ArrayList<Event>>) events.clone();
    }

    /**
     * Sets the current date to the given date
     * @param d the new Date to be set to
     */
    public void setDate(LocalDate d){
        currDate = d;
    }

    /**
     * Updates the model with a new Event
     * @param e the new Event
     */
    public void update(Event e){
        TimeInterval intvl = e.getInterval();
        if (events.containsKey(intvl.getDate()) == false) //if no events planned on date
        {
            ArrayList<Event> list = new ArrayList<Event>();
            list.add(e);
            events.put(intvl.getDate(), list);
        }
        else {
            ArrayList<Event> eList = events.get(intvl.getDate());
            for (Event event:eList)
            {
                if (e.getInterval().overlap(event.getInterval()))
                {
//                    System.out.println("Overlaps with other events\nEvent not added");
                    return;
                }
            }
            eList.add(e);
        }
        updateListeners();
    }

    /**
     * Updates all ChangeListeners/views
     */
    public void updateListeners() {
        for (ChangeListener l : listeners){
            l.stateChanged(new ChangeEvent(this));
        }
    }

    /**
     * Attaches a view to the model
     * @param l a view
     */
    public void attach(ChangeListener l){
        listeners.add(l);
    }

    /**
     * Returns the current date
     * @return the current date
     */
    public LocalDate getDate() {
        return currDate;
    }
}
