package view;

import javax.swing.*;
import java.awt.*;

import static view.Page.*;

/**
 * The panel that displays:
 * - The title of the application and a marketing tag
 * - The current page
 * - The filter options
 */
public class TitlePanel extends JPanel {
    
    // GUI Constants
    public static final Color BACKGROUND_COLOUR = new Color(77, 127, 161);
    
    public static final Font TITLE_FONT = new Font("Ubuntu", Font.BOLD, 120);
    public static final Font HEADER_FONT = new Font("Ubuntu", Font.BOLD, 45);
    
    // GUI
    private final JLabel titleLabel;
    private final JLabel titleCaptionLabel;
    private final JLabel pageLabel;
    
    // User input stuff
    private final JComboBox<Page> pageComboBox;
    private final FilterBar filterBar;
    
    public TitlePanel (boolean shouldAddGenreParameter) {
    
        // Set up the panel
        setLayout(null);
        setBounds(0, 0, Page.WIDTH, Page.HEIGHT/2-PADDING_Y*2);
        setBackground(BACKGROUND_COLOUR);
    
        // Initialize the titleLabel
        titleLabel = new JLabel("Ani-List");
        titleLabel.setBounds(PADDING*2, PADDING_Y+PADDING, Page.WIDTH/2-PADDING*11, PADDING_Y*2+PADDING);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(Page.BACKGROUND_COLOUR);
        add(titleLabel);
    
        // Initialize the titleCaptionLabel
        titleCaptionLabel = new JLabel("The analyst for ani-lists");
        titleCaptionLabel.setBounds(
                Page.getRightX(titleLabel)+PADDING,
                Page.getBottomY(titleLabel)-PADDING,
                PADDING*11, PADDING);
        titleCaptionLabel.setFont(DIALOGUE_FONT);
        titleCaptionLabel.setForeground(Page.BACKGROUND_COLOUR);
        add(titleCaptionLabel);
    
        // Initialize the pageComboBox
        pageComboBox = new JComboBox<>();
        pageComboBox.setBounds(
                titleLabel.getX(), Page.getBottomY(titleLabel)+PADDING,
                PADDING*16, getHeight()/4
        );
        pageComboBox.setFont(HEADER_FONT);
        pageComboBox.setBackground(USER_INPUT_COLOUR);
        pageComboBox.setForeground(TEXT_COLOUR);
        pageComboBox.setBorder(null);
        add(pageComboBox);
    
        // Initialize the pageLabel
        pageLabel = new JLabel("Page");
        pageLabel.setBounds(
                Page.getRightX(pageComboBox)+PADDING, pageComboBox.getY(),
                PADDING*8, pageComboBox.getHeight()
        );
        pageLabel.setFont(HEADER_FONT);
        pageLabel.setForeground(Page.BACKGROUND_COLOUR);
        add(pageLabel);
    
        // Initialize the filter bar
        int x = Page.WIDTH/2-PADDING*4;
        filterBar = new FilterBar(shouldAddGenreParameter);
        filterBar.setBounds(x, pageComboBox.getY(), Page.WIDTH-x-PADDING*4, pageComboBox.getHeight());
        add(filterBar);
    
    }
    
    // Getters
    public JComboBox<Page> getPageComboBox () {
        return pageComboBox;
    }
    
    public FilterBar getFilterBar () {
        return filterBar;
    }
    
    /**
     * @param enabled = Indicates whether to disable or enable user input
     * @see Page#setEnabledUserInput(boolean)
     */
    public void setEnabledUserInput (boolean enabled) {
        pageComboBox.setEnabled(enabled);
        filterBar.setEnableUserInput(enabled);
    }
    
}
