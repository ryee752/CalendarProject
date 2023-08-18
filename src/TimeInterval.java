
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Fall 2022 CS151 Assignment 5 Submission
 * @author Ryan Yee
 * @version 1.0 11/30/22
 *
 * TimeInterval represents an interval of time for events in standard 12-hour
 * AM/PM format
 */
public class TimeInterval {

    LocalTime start, end;
    LocalDate date;

    /**
     * Constructs a TimeInterval with a given date, start and end time
     * used for one time events
     * @param dt date of the TimeInterval
     * @param st starting time of the TimeInterval
     * @param et ending time of the TimeInterval
     */
    public TimeInterval(LocalDate dt, LocalTime st, LocalTime et)
    {
        date = dt;
        start = st;
        end = et;
    }

    /**
     * Returns the date of the TimeInterval
     * @return the date of the TimeInterval
     */
    public LocalDate getDate()
    {
        return date;
    }

    /**
     * Returns the start time of the TimeInterval
     * @return the start time of the TimeInterval
     */
    public LocalTime getStart()
    {
        return start;
    }

    /**
     * Returns the end time of the TimeInterval
     * @return the end time of the TimeInterval
     */
    public LocalTime getEnd()
    {
        return end;
    }

    /**
     * Determines if the time interval overlaps with another interval
     * @param o the TimeInterval to be compared to
     * @return false if it occurs before or after the other TimeInterval, else true
     */
    public boolean overlap(TimeInterval o)
    {
        if (end.isBefore(o.start))
        {
            return false;
        }
        else if (start.isAfter(o.end))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Determines if the time interval occurs before the other interval
     * @param o  the TimeInterval to be compared to
     * @return false if the time interval occurs after, else true
     */
    public boolean isBefore(TimeInterval o)
    {
        return end.isBefore(o.start);
    }

    /**
     * Determines if the time interval occurs after the other interval
     * @param o  the TimeInterval to be compared to
     * @return false if the time interval occurs before, else true
     */
    public boolean isAfter(TimeInterval o)
    {
        return start.isAfter(o.end);
    }

    /**
     * Returns a string consisting of the starting and ending time
     * @return a string consisting of the starting and ending time
     */
    public String toString()
    {
        DateTimeFormatter tFormat = DateTimeFormatter.ofPattern("h:mma");
        String s = "" + tFormat.format(start) + " - " + tFormat.format(end);
        return s;
    }

}
