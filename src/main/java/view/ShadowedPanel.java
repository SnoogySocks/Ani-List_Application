package view;

import javax.swing.*;
import java.awt.*;

/**
 * A JPanel that has a shadow under it
 */
public class ShadowedPanel extends JPanel {
    
    // GUI constants
    public static final Color SHADOW_COLOUR = new Color(67, 67, 67);
    public static final int SHADOW_OFFSET = 15;
    
    // GUI
    private final JPanel displayPanel;
    private final JPanel shadow;
    
    public ShadowedPanel (Color displayPanelColour) {
        
        // Set up the panel
        setLayout(null);
        setOpaque(false);
    
        // Initialize displayPanel
        displayPanel = new JPanel();
        displayPanel.setLayout(null);
        displayPanel.setBackground(displayPanelColour);
        add(displayPanel);
        
        // Initialize the shadow
        shadow = new JPanel();
        shadow.setBackground(SHADOW_COLOUR);
        add(shadow);
        
    }
    
    // Getter
    public JPanel getDisplayPanel () {
        return displayPanel;
    }
    
    /**
     * Set the size of the panel relative to the displayPanel
     */
    @Override
    public void setSize (int width, int height) {
        
        super.setSize(width, height);
        displayPanel.setSize(width, height);
        shadow.setSize(width, height);
        
    }
    
    @Override
    public void setLocation (int x, int y) {
        setBounds(x, y, displayPanel.getWidth(), displayPanel.getHeight());
    }
    
    /**
     * Set the bounds of the panel relative to the displayPanel
     */
    @Override
    public void setBounds (int x, int y, int width, int height) {
        
        super.setBounds(x, y, width+SHADOW_OFFSET, height+SHADOW_OFFSET);
        displayPanel.setBounds(SHADOW_OFFSET, 0, width, height);
        shadow.setBounds(0, SHADOW_OFFSET, width, height);
        
    }

}
