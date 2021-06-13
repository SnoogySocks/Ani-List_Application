package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * The window that displays the whole application
 */
public class MainFrame extends JFrame {
    
    // GUI constants
    public static final int WIDTH = 1366;
    public static final int HEIGHT = 768;
    
    public MainFrame () {
        
        // Set up the frame
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        
    }
    
    /**
     * @param e = a moues event
     * @return the mouse's position on the frame
     */
    public Point getMouseOnFrame (MouseEvent e) {
        return new Point(e.getXOnScreen()-getX(), e.getYOnScreen()-getY());
    }
    
}
