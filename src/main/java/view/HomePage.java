package view;

import controller.JikanController;

import javax.swing.*;
import java.awt.*;

/**
 * Home page to browse anime from
 * - Trending
 * - Latest updated
 * - Up & Coming
 * @see Page
 */
public class HomePage extends Page {
    
    private final ShadowedPanel[] categoriesLabels;
    private final AnimeScrollPanel[] categories;    // The scroll panels for browsing categories
    
    public HomePage () {
    
        // Set up the page
        setActualSize(new Dimension(WIDTH, HEIGHT*2));
        
        // Initialize categoriesLabels
        categoriesLabels = new ShadowedPanel[3];
        String[] categoryNames = {
                "Trending", "Latest Updated", "Up & Coming"
        };
        
        // Initialize categoriesLabels trending category
        categoriesLabels[0] = new ShadowedPanel(DIALOGUE_COLOUR);
        categoriesLabels[0].setBounds(
                PADDING*8-ShadowedPanel.SHADOW_OFFSET, Page.getBottomY(getTitlePanel())+PADDING_Y,
                PADDING*13, PADDING*3
        );
    
        // Initialize trendingCategoryText
        JLabel trendingCategoryText = new JLabel(categoryNames[0]);
        trendingCategoryText.setBounds(
                PADDING, PADDING/2,
                categoriesLabels[0].getDisplayPanel().getWidth()-PADDING,
                categoriesLabels[0].getDisplayPanel().getHeight()-PADDING
        );
        trendingCategoryText.setFont(CATEGORY_FONT);
        trendingCategoryText.setForeground(TEXT_COLOUR);
        categoriesLabels[0].getDisplayPanel().add(trendingCategoryText);
        add(categoriesLabels[0]);
    
        // Initialize categories
        categories = new AnimeScrollPanel[categoriesLabels.length];
        categories[0] = new AnimeScrollPanel(JikanController.getTrending(), AnimeImage.LARGE_SIZE);
        categories[1] = new AnimeScrollPanel(JikanController.getLatestUpdated(), AnimeImage.MEDIUM_SIZE);
        categories[2] = new AnimeScrollPanel(JikanController.getUpAndComing(), AnimeImage.MEDIUM_SIZE);
        
        // Position the trending category
        categories[0].setLocation(PADDING*5, Page.getBottomY(categoriesLabels[0])+PADDING_Y);
        add(categories[0]);
        
        // Iterate from the latest updated category to the up & coming category
        for (int i = 1; i<categoriesLabels.length; ++i) {
    
            // Initialize the category label
            categoriesLabels[i] = new ShadowedPanel(DIALOGUE_COLOUR);
            categoriesLabels[i].setLocation(
                    categoriesLabels[0].getX(),
                    Page.getBottomY(categories[i-1])+PADDING_Y*2
            );
            categoriesLabels[i].setSize(categoriesLabels[0].getDisplayPanel().getSize());
        
            // Initialize the category text for the label
            JLabel categoryText = new JLabel(categoryNames[i]);
            categoryText.setBounds(trendingCategoryText.getBounds());
            categoryText.setFont(CATEGORY_FONT);
            categoryText.setForeground(TEXT_COLOUR);
            categoriesLabels[i].getDisplayPanel().add(categoryText);
            add(categoriesLabels[i]);
    
            // Position the category
            categories[i].setLocation(
                    PADDING*5,
                    Page.getBottomY(categoriesLabels[i])+PADDING
            );
            add(categories[i]);
        
        }
        
    }
    
    // Getter
    public AnimeScrollPanel[] getCategories () {
        return categories;
    }
    
    @Override
    public String toString () {
        return "Home";
    }
    
    /**
     * @param enabled = indicates whether to disable or enable all input features
     * @see Page#setEnabledUserInput(boolean)
     */
    @Override
    public void setEnabledUserInput (boolean enabled) {
        
        // Enabled/disable trending input features
        categories[0].setEnabledUserInput(enabled);
        
        // Set the last three displayedAnime panels in trending
        // to be invisible so they do not pop up
        JButton[] trendingButtons = categories[0].getDisplayedAnimeButtons();
        for (int i = trendingButtons.length*2/5; i<trendingButtons.length; ++i) {
            trendingButtons[i].setVisible(enabled);
        }
        
    }
    
}
