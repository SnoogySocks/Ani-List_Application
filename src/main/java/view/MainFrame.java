package view;

import controller.JikanController;

import javax.swing.*;

public class MainFrame extends JFrame {
    
    public static final int WIDTH = 1366;
    public static final int HEIGHT = 768;
    
    public MainFrame () {
        
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        
    }
    
    public int getXOnFrame (int xOnScreen) {
        return xOnScreen-getX();
    }
    
    public int getYOnFrame (int yOnScreen) {
        return yOnScreen-getY();
    }
    
}
