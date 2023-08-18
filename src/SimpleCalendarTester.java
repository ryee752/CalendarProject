import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Fall 2022 CS151 Assignment 5 Submission
 * @author Ryan Yee
 * @version 1.0 11/30/22
 *
 * SimpleCalendarTester loads data from events.txt to the CalendarModel if it exists.
 * This class also creates a CalendarDisplay object that represents the view in the
 * MVC pattern and attaches it to the model.
 */
public class SimpleCalendarTester {
    /**
     * Main method of SimpleCalendarTester
     * @param args the arguments
     */
    public static void main(String[] args) {
        HashMap<LocalDate, ArrayList<Event>> map = initialize();
        CalendarModel model = new CalendarModel(map);
        CalendarDisplay view = new CalendarDisplay(model);
        model.attach(view);

    }

    /**
     * Loads the data from event.txt if it exists
     * @return returns a HashMap with the data loaded from events.txt
     * or an empty HashMap if event.txt doesn't exist.
     */
    public static HashMap<LocalDate, ArrayList<Event>> initialize()
    {
        BufferedReader br;
        HashMap<LocalDate, ArrayList<Event>> map = new HashMap<LocalDate, ArrayList<Event>>();
        File f = new File("events.txt");
        //if the file exists load the data
        if (f.exists()){
            try {
                br = new BufferedReader(new FileReader(f));
                String name = br.readLine();
                while (name != null)
                {
                    String intvl = br.readLine();
                    if (intvl == null)
                    {
                        break;
                    }
                    String[] arr = intvl.split(" ");
                    DateTimeFormatter dFormat = DateTimeFormatter.ofPattern("M/d/yy");
                    DateTimeFormatter tFormat = DateTimeFormatter.ofPattern("h:mma");
                    String str = arr[0];
                    LocalDate date = LocalDate.parse(str, dFormat);
                    //					 System.out.println(d.toString());
                    String start = arr[1];
                    String end = arr[2];
                    LocalTime sTime = LocalTime.parse(start, tFormat);
                    LocalTime eTime = LocalTime.parse(end, tFormat);
                    TimeInterval tIntval = new TimeInterval(date, sTime, eTime);
                    Event evt = new Event(name, tIntval);
                    if (map.containsKey(tIntval.getDate()) == false) //if no events planned on date
                    {
                        ArrayList<Event> list = new ArrayList<Event>();
                        list.add(evt);
                        map.put(tIntval.getDate(), list);
                    }
                    else {
                        ArrayList<Event> eList = map.get(tIntval.getDate());
                        eList.add(evt);
                    }
                    name = br.readLine();
                }
            } catch (FileNotFoundException e) {
                //			 e.printStackTrace();
//                System.out.println("FileNotFoundException: File could not be found");
            } catch (IOException e) {
                //			e.printStackTrace();
//                System.out.println("IOException");
            }
        }
        return map;
    }
}