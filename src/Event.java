
/**
 * Fall 2022 CS151 Assignment 5 Submission
 * @author Ryan Yee
 * @version 1.0 11/30/22
 *
 * Represents an Event consisting of a name and TimeInterval and implements Comparable
 */
public class Event implements Comparable<Event>{

    private String name;
    private TimeInterval intvl;

    /**
     * Constructs an Event, given a name, TimeInterval, and recurrence boolean
     * @param n name of Event
     * @param t TimeInterval of Event
     */
    public Event(String n, TimeInterval t)
    {
        name = n;
        intvl = t;
    }

    /**
     * Returns the name of the Event
     * @return the name of the Event
     */
    public String getName()
    {
        return name;
    }

    /**
     * Return the TimeInterval of the Event
     * @return the TimeInterval of the Event
     */
    public TimeInterval getInterval()
    {
        return intvl;
    }

    /**
     * Returns a string consisting of the Event name and TimeInterval
     * @return a string consisting of the Event name and TimeInterval
     */
    public String toString()
    {
        String s = "" + name + " : " + intvl.toString();
        return s;
    }

    /**
     * Compares Events based on time intervals
     * @return if Event time interval is before other, returns 1;
     * if after, returns 1; else 0
     */
    public int compareTo(Event o) {
        if (intvl.getDate().equals(o.getInterval().getDate()))
        {
            if (intvl.isBefore(o.intvl))
            {
                return -1;
            }
            else if (intvl.isAfter(o.intvl))
            {
                return 1;
            }
            else
            {

                return 0;
            }
        }
        else if (intvl.getDate().isBefore(o.getInterval().getDate()))
        {
            return -1;
        }
        else
        {
            return 1;
        }
    }



}
