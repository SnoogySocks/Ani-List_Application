package view;

import javax.swing.*;
import java.awt.*;

public class ItemPanel extends JPanel {
    
    public static final Color SHADOW_COLOUR = new Color(67, 67, 67);
    public static final int SHADOW_OFFSET = 15;
    
    private final JPanel displayPanel;
    private final JPanel shadow;
    
    public ItemPanel (Color displayPanelColour) {
        
        setLayout(null);
        setOpaque(false);
    
        displayPanel = new JPanel();
        displayPanel.setLayout(null);
        displayPanel.setBackground(displayPanelColour);
        add(displayPanel);
        
        shadow = new JPanel();
        shadow.setBackground(SHADOW_COLOUR);
        add(shadow);
        
    }
    
    public JPanel getDisplayPanel () {
        return displayPanel;
    }
    
    @Override
    public void setBounds (int x, int y, int width, int height) {
        
        super.setBounds(x, y, width+SHADOW_OFFSET, height+SHADOW_OFFSET);
        displayPanel.setBounds(SHADOW_OFFSET, 0, width, height);
        shadow.setBounds(0, SHADOW_OFFSET, width, height);
        
    }

}
