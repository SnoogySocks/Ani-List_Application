package view;

import javax.swing.*;
import java.awt.*;

/**
 * The page that recommends anime to the player
 * @see Page
 */
public class RecommendPage extends Page {
    
    // Starting screen stuff
    private final JLabel numAnimeToGenerateLabel;
    private final JComboBox<Integer> numAnimeToGenerateComboBox;
    
    // Recommending mode stuff
    private final JLabel interestedLabel;
    private final JPanel interestedPanel;
    
    private final JLabel uninterestedLabel;
    private final JPanel uninterestedPanel;
    
    // Switches the mode of the page
    private final JButton switchModesButton;
    
    public RecommendPage () {
    
        // Set the size of the page
        setActualSize(getSize());
        
        // Initialize the switchModesButton
        switchModesButton = new JButton();
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
        
        // Initialize the numAnimeToGenerateLabel
        numAnimeToGenerateLabel = new JLabel("# of Anime to Generate");
        numAnimeToGenerateLabel.setBounds(PADDING*2, filterBar.getHeight()/2, PADDING*16, PADDING_Y);
        numAnimeToGenerateLabel.setFont(CATEGORY_FONT);
        numAnimeToGenerateLabel.setForeground(TEXT_COLOUR);
        filterBar.getDisplayPanel().add(numAnimeToGenerateLabel);
    
        // Initialize the numAnimeToGenerateComboBox
        numAnimeToGenerateComboBox = new JComboBox<>();
        
        // Put options for the number of anime in the combo box
        for (int numAnime = 10; numAnime<=50; numAnime += 10) {
            numAnimeToGenerateComboBox.addItem(numAnime);
        }
        
        // Set up the combo box's GUI
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
    
        // Initialize the uninterestedPanel
        uninterestedPanel = new JPanel();
        uninterestedPanel.setBounds(0, 0, Page.WIDTH/6, Page.HEIGHT);
        uninterestedPanel.setBackground(new Color(234, 153, 153));
        add(uninterestedPanel);
    
        // Initialize the uninterestedLabel
        uninterestedLabel = new JLabel("Uninterested");
        uninterestedLabel.setBounds(0, PADDING_Y, uninterestedPanel.getWidth(), PADDING_Y);
        uninterestedLabel.setHorizontalAlignment(JLabel.CENTER);
        uninterestedLabel.setFont(Page.CATEGORY_FONT);
        uninterestedLabel.setForeground(TEXT_COLOUR);
        uninterestedPanel.add(uninterestedLabel);
    
        // Initialize the interestedPanel
        interestedPanel = new JPanel();
        interestedPanel.setLocation(Page.WIDTH-uninterestedPanel.getWidth()-16, 0);
        interestedPanel.setSize(uninterestedPanel.getSize());
        interestedPanel.setBackground(new Color(182, 215, 168));
        add(interestedPanel);
        
        // Initialize the interestedLabel
        interestedLabel = new JLabel("Interested");
        interestedLabel.setBounds(0, PADDING_Y, interestedPanel.getWidth(), PADDING_Y);
        interestedLabel.setHorizontalAlignment(JLabel.CENTER);
        interestedLabel.setFont(Page.CATEGORY_FONT);
        interestedLabel.setForeground(TEXT_COLOUR);
        interestedPanel.add(interestedLabel);
        
        // Switch to the starting screen
        setToRecommending(false);
        
    }
    
    // Getters
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
    
    /**
     * Switch the modes to recommending mode
     * @param isRecommending = which mode to switch to
     */
    public void setToRecommending (boolean isRecommending) {
        
        getTitlePanel().setVisible(!isRecommending);
        interestedPanel.setVisible(isRecommending);
        uninterestedPanel.setVisible(isRecommending);
    
        // Switch the position and text of the switchModesButton depending on the mode
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
    
    /**
     * @param enabled = indicates whether to disable or enable all input features
     * @see Page#setEnabledUserInput(boolean)
     */
    @Override
    public void setEnabledUserInput (boolean enabled) {
        switchModesButton.setEnabled(enabled);
    }
    
}
