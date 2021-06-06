package view;

import javax.swing.*;
import java.awt.*;

public abstract class Page extends JPanel {
    
    // Size constants
    public static final int WIDTH = MainFrame.WIDTH;
    public static final int HEIGHT = MainFrame.HEIGHT;
    
    public static int PADDING = 20;
    public static int PADDING_Y = 40;
    
    // Font sizes
    public static final Font CATEGORY_FONT = new Font("Ubuntu", Font.BOLD, 29);
    public static final Font DIALOGUE_FONT = new Font("Ubuntu", Font.PLAIN, 19);
    
    // Colour
    public static final Color BACKGROUND_COLOUR = new Color(237, 244, 244);
    public static final Color USER_INPUT_COLOUR = new Color(213, 235, 255);
    public static final Color TEXT_COLOUR = new Color(67, 67, 67);
    public static final Color DIALOGUE_COLOUR = new Color(255, 255, 255);
    
    private TitlePanel titlePanel;
    
    public Page () {
        
        setBackground(BACKGROUND_COLOUR);
        setBounds(0, 0, WIDTH, HEIGHT);
        
        // Does not create a genre if this is a RecommendationPage
        titlePanel = new TitlePanel(!(this instanceof RecommendationPage));
        add(titlePanel);
        
    }
    
    public static int getRightX (JComponent comp) {
        return comp.getX()+comp.getWidth();
    }
    
    public static int getBottomY (JComponent comp) {
        return comp.getY()+comp.getHeight();
    }

}
