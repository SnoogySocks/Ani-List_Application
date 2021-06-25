package view;

import model.AniList;
import model.Anime;

import javax.swing.*;
import java.awt.*;

/**
 * All the pages extend from this class. The class contains:
 * - Constant values for gui positioning, font, and colour
 * - An Ani-List that is the same for all pages to record for the same user
 * - Title panels for the name of the application
 * - An anime panel to display more specific information about an anime
 */
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
    
    private static final AniList aniList = new AniList();     // User's Ani-List
    private final JPanel loadingPanel;                        // Loading icon
    
    private Dimension actualSize;            // The actual size of the panel hidden with the scroll pane
    
    private final AnimePanel animePanel;     // To display specific information about an anime
    private final JScrollPane scrollPane;
    private final TitlePanel titlePanel;
    
    public Page () {
        
        // set up the JPanel
        setLayout(null);
        setBackground(BACKGROUND_COLOUR);
    
        // Initialize the scrolling feature
        scrollPane = new JScrollPane(this);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBounds(0, 0, WIDTH-12, HEIGHT-35);
    
        loadingPanel = new JPanel();
        loadingPanel.setLayout(null);
        loadingPanel.setBackground(ShadowedPanel.SHADOW_COLOUR);
        loadingPanel.setSize(PADDING*8, PADDING*8);
    
        // Center the loadingIcon on the loading panel
        ImageIcon gif = new ImageIcon("src/main/resources/loadingScreen.gif");
        JLabel loadingIcon = new JLabel(gif);
        loadingIcon.setBounds(
                loadingPanel.getWidth()/2-gif.getIconWidth()/2,
                loadingPanel.getHeight()/2-gif.getIconHeight()/2,
                gif.getIconWidth(), gif.getIconHeight()
        );
        loadingPanel.add(loadingIcon);
        loadingPanel.setVisible(false);
        add(loadingPanel);
    
        // Initialize the anime panel
        this.animePanel = new AnimePanel();
        add(this.animePanel);
    
        // Does not create a genre if this is a RecommendationPage
        titlePanel = new TitlePanel(!(this instanceof RecommendPage));
        add(titlePanel);
        
    }
    
    // Getters
    public JScrollPane getScrollPane () {
        return scrollPane;
    }
    
    public TitlePanel getTitlePanel () {
        return titlePanel;
    }
    
    public JPanel getLoadingPanel () {
        return loadingPanel;
    }
    
    public AnimePanel getAnimePanel () {
        return animePanel;
    }
    
    public Dimension getActualSize () {
        return actualSize;
    }
    
    public static AniList getAniList () {
        return aniList;
    }
    
    /**
     * Set the size of the panel in the scroll pane
     * @param preferredSize = the size of the panel in the scroll pane
     */
    public void setActualSize (Dimension preferredSize) {
        this.actualSize = preferredSize;
        super.setPreferredSize(preferredSize);
    }
    
    /**
     * Helper to disable all input features for displaying the anime panel
     * @param enabled = indicates whether to disable or enable all input features
     */
    public abstract void setEnabledUserInput (boolean enabled);
    
    /**
     * Removes the anime panel.
     */
    public void disableAnimePanel () {
    
        setEnabledUserInput(true);
        
        setPreferredSize(actualSize);
        titlePanel.setEnabledUserInput(true);
        animePanel.disableAnimePanel();
        
    }
    
    /**
     * Adds the anime panel when user interacts with something.
     * Cancels out all user input
     * @param anime = the anime to display with animePanel
     */
    public void enableAnimePanel (Anime anime) {

        // Disable all input features
        setEnabledUserInput(false);
        titlePanel.setEnabledUserInput(false);
        
        // Remove scrolling by making the size the same as the frame
        setPreferredSize(new Dimension(WIDTH, HEIGHT-38));
        animePanel.enableAnimePanel(anime);
        
    }
    
    /**
     * @param comp = the component to examine
     * @return gets the right x coordinate of a component
     */
    public static int getRightX (JComponent comp) {
        return comp.getX()+comp.getWidth();
    }
    
    /**
     * @param comp = the component to examine
     * @return gets the bottom y coordinate of a component
     */
    public static int getBottomY (JComponent comp) {
        return comp.getY()+comp.getHeight();
    }

}
