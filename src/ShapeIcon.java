import javax.swing.*;
import java.awt.*;

/**
 * Fall 2022 CS151 Assignment 5 Submission
 * @author Ryan Yee
 * @version 1.0 11/30/22
 *
 * An icon that contains a Composite shape.
 */
public class ShapeIcon implements Icon {

    private int width;
    private int height;
    private CompositeShape shape;

    /**
     * Creates a Shape icon with the given shape, width, and height
     * @param shape the composite shape to be drawn in the icon
     * @param width the width of the icon
     * @param height the height of the icon
     */
    public ShapeIcon(CompositeShape shape, int width, int height) {
        this.shape = shape;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the icon width
     * @return the icon width
     */
    public int getIconWidth() {
        return width;
    }

    /**
     * Returns the icon height
     * @return the icon height
     */
    public int getIconHeight() {
        return height;
    }

    /**
     * Paints the icon with the given shape
     * @param c the component
     * @param g the graphics
     * @param x the x coordinate of the corner
     * @param y the y coordinate of the corner
     */
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g;
        shape.draw(g2);
    }
}
