package view;

import controller.JikanController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {
    
    public static final int WIDTH = 1366;
    public static final int HEIGHT = 768;
    
    public MainFrame () {
        
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        
    }
    
    public Point getMouseOnFrame (MouseEvent e) {
        return new Point(e.getXOnScreen()-getX(), e.getYOnScreen()-getY());
    }
    
}
