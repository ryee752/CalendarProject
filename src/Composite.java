import java.awt.*;
import java.util.ArrayList;

/**
 * Fall 2022 CS151 Assignment 5 Submission
 * @author Ryan Yee
 * @version 1.0 11/30/22
 *
 * Composite is a CompositeShape that represents a composite.
 * Used to hold all the composite shapes represting a scheduled event
 * on a given day.
 */
public class Composite implements CompositeShape{
    double x, y, width, height;
    private ArrayList<CompositeShape> parts;

    /**
     * Constructs a Composite.
     * @param x     the left of the bounding rectangle
     * @param y     the top of the bounding rectangle
     * @param width the width of the bounding rectangle
     * @param height the height of the bounding rectangle
     */
    public Composite(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        parts = new ArrayList<CompositeShape>();

    }

    /**
     * Adds a leaf to the Composite
     * @param compositeShape the leaf to be added
     */
    public void addPart(CompositeShape compositeShape) {
        parts.add(compositeShape);
    }

    /**
     * Returns the array of parts (leafs)
     * @return parts
     */
    public ArrayList<CompositeShape> getParts() {
        return parts;
    }
    /**
     * Draws the CompositeShapes
     * @param graphics2D the Graphics2D
     */
    public void draw(Graphics2D graphics2D) {
        for (CompositeShape part : parts) {
            part.draw(graphics2D);
        }
    }
}
