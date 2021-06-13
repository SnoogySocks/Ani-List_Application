package view;

import javax.swing.*;
import java.awt.*;

public class RecommendPage extends Page {
    
    // Starting stuff
    private final JLabel numAnimeToGenerateLabel;
    private final JComboBox<Integer> numAnimeToGenerateComboBox;
    
    private final JButton switchModesButton;
    
    // Recommending mode
    private final JLabel interestedLabel;
    private final JPanel interestedPanel;
    
    private final JLabel uninterestedLabel;
    private final JPanel uninterestedPanel;
    
    public RecommendPage () {
    
        setActualSize(getSize());
        
        // Add the start button
        switchModesButton = new JButton("<html>Press to Start<br>Recommending</html>");
        switchModesButton.setBounds(
                getTitlePanel().getWidth()/2-PADDING*4, getTitlePanel().getHeight(),
                PADDING*8, PADDING_Y*2
        );
        switchModesButton.setFont(DIALOGUE_FONT);
        switchModesButton.setBackground(USER_INPUT_COLOUR);
        switchModesButton.setForeground(TEXT_COLOUR);
        switchModesButton.setBorder(null);
        add(switchModesButton);
    
        // Adjust the filter bar size
        FilterBar filterBar = getTitlePanel().getFilterBar();
        filterBar.setBounds(
                getTitlePanel().getWidth()-filterBar.getWidth()+PADDING*5,
                Page.getBottomY(filterBar.getDisplayPanel())+filterBar.getY()-filterBar.getHeight()*2+PADDING_Y,
                filterBar.getWidth()-PADDING*9, filterBar.getHeight()*2-PADDING_Y
        );
        
        numAnimeToGenerateLabel = new JLabel("# of Anime to Generate");
        numAnimeToGenerateLabel.setBounds(PADDING*2, filterBar.getHeight()/2, PADDING*16, PADDING_Y);
        numAnimeToGenerateLabel.setFont(CATEGORY_FONT);
        numAnimeToGenerateLabel.setForeground(TEXT_COLOUR);
        filterBar.getDisplayPanel().add(numAnimeToGenerateLabel);
        
        numAnimeToGenerateComboBox = new JComboBox<>();
        
        // Put items into the combo box
        for (int numAnime = 10; numAnime<=50; numAnime += 10) {
            numAnimeToGenerateComboBox.addItem(numAnime);
        }
        
        numAnimeToGenerateComboBox.setBounds(
                Page.getRightX(numAnimeToGenerateLabel)+PADDING,
                numAnimeToGenerateLabel.getY()+numAnimeToGenerateLabel.getHeight()/2-PADDING,
                filterBar.getDisplayPanel().getWidth()-Page.getRightX(numAnimeToGenerateLabel)-PADDING*3,
                PADDING*2
        );
        numAnimeToGenerateComboBox.setFont(DIALOGUE_FONT);
        numAnimeToGenerateComboBox.setBackground(USER_INPUT_COLOUR);
        numAnimeToGenerateComboBox.setForeground(TEXT_COLOUR);
        numAnimeToGenerateComboBox.setBorder(null);
        filterBar.getDisplayPanel().add(numAnimeToGenerateComboBox);
        
        uninterestedPanel = new JPanel();
        uninterestedPanel.setBounds(0, 0, Page.WIDTH/6, Page.HEIGHT);
        uninterestedPanel.setBackground(new Color(234, 153, 153));
        add(uninterestedPanel);
        
        uninterestedLabel = new JLabel("Uninterested");
        uninterestedLabel.setBounds(0, PADDING_Y, uninterestedPanel.getWidth(), PADDING_Y);
        uninterestedLabel.setHorizontalAlignment(JLabel.CENTER);
        uninterestedLabel.setFont(Page.CATEGORY_FONT);
        uninterestedLabel.setForeground(TEXT_COLOUR);
        uninterestedPanel.add(uninterestedLabel);
        
        interestedPanel = new JPanel();
        interestedPanel.setLocation(Page.WIDTH-uninterestedPanel.getWidth()-16, 0);
        interestedPanel.setSize(uninterestedPanel.getSize());
        interestedPanel.setBackground(new Color(182, 215, 168));
        add(interestedPanel);
    
        interestedLabel = new JLabel("Interested");
        interestedLabel.setBounds(0, PADDING_Y, interestedPanel.getWidth(), PADDING_Y);
        interestedLabel.setHorizontalAlignment(JLabel.CENTER);
        interestedLabel.setFont(Page.CATEGORY_FONT);
        interestedLabel.setForeground(TEXT_COLOUR);
        interestedPanel.add(interestedLabel);
        
        setToRecommending(false);
        
    }
    
    public JComboBox<Integer> getNumAnimeToGenerateComboBox () {
        return numAnimeToGenerateComboBox;
    }
    
    public JButton getSwitchModesButton () {
        return switchModesButton;
    }
    
    public JPanel getInterestedPanel () {
        return interestedPanel;
    }
    
    public JPanel getUninterestedPanel () {
        return uninterestedPanel;
    }
    
    public void setToRecommending (boolean isRecommending) {
        
        getTitlePanel().setVisible(!isRecommending);
        interestedPanel.setVisible(isRecommending);
        uninterestedPanel.setVisible(isRecommending);
    
        if (!isRecommending) {
            switchModesButton.setText("<html>Press to Start<br>Recommending</html>");
            switchModesButton.setLocation(switchModesButton.getX(), getTitlePanel().getHeight());
        } else {
            switchModesButton.setText("<html>Press to Finish<br>Recommending</html>");
            switchModesButton.setLocation(switchModesButton.getX(), 0);
        }
        
    }
    
    @Override
    public String toString () {
        return "Recommend";
    }
    
    public void setEnabledUserInput (boolean enabled) {
        switchModesButton.setEnabled(enabled);
    }
    
}
