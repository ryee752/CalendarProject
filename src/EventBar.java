import java.awt.*;
import java.awt.geom.Rectangle2D;
/**
 * Fall 2022 CS151 Assignment 5 Submission
 * @author Ryan Yee
 * @version 1.0 11/30/22
 *
 * EventBar is a CompositeShape that represents one scheduled event on a given date.
 * This class is used as a leaf of a Composite.
 */
public class EventBar implements CompositeShape{
    double x, y, width, height;
    Event event;

    /**
     * Constructs an EventBar
     * @param x     the left of the bounding rectangle
     * @param y     the top of the bounding rectangle
     * @param width the width of the EventBar
     * @param height the height of the EventBar
     * @param e     the Event that is to be represented
     */
    public EventBar(double x, double y, double width, double height, Event e){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        event = e;
    }

    /**
     * Draws an EventBar
     * @param graphics2D the Graphics2D
     */
    public void draw(Graphics2D graphics2D) {
        //Rectangle representing an event during a time interval
        Rectangle2D.Double rectangle = new Rectangle2D.Double(x, y, width, height);
        graphics2D.draw(rectangle);
        graphics2D.setColor(new Color(255, 0, 0, 127));
        graphics2D.fill(rectangle);
        graphics2D.setColor(new Color(0, 0, 0));
        //name and time interval of the event
        graphics2D.drawString(event.getName() + " " + event.getInterval().toString(), (int) x + 5, (int) (y + height));
    }
}
