package view;

import controller.JikanController;
import model.Anime;

import javax.swing.*;
import java.awt.*;

public class HomePage extends Page {
    
    private final ItemPanel[] categoriesLabels;
    private final AnimeScrollPanel[] categories;
    
    public HomePage () {
    
        setActualSize(new Dimension(WIDTH, HEIGHT*2));
    
        categoriesLabels = new ItemPanel[3];
        String[] categoryNames = {
                "Trending", "Latest Updated", "Up & Coming"
        };
        
        categoriesLabels[0] = new ItemPanel(DIALOGUE_COLOUR);
        categoriesLabels[0].setBounds(
                PADDING*8-ItemPanel.SHADOW_OFFSET, Page.getBottomY(getTitlePanel())+PADDING_Y,
                PADDING*13, PADDING*3
        );
    
        JLabel trendingCategoryLabel = new JLabel(categoryNames[0]);
        trendingCategoryLabel.setBounds(
                PADDING, PADDING/2,
                categoriesLabels[0].getDisplayPanel().getWidth()-PADDING,
                categoriesLabels[0].getDisplayPanel().getHeight()-PADDING
        );
        trendingCategoryLabel.setFont(CATEGORY_FONT);
        trendingCategoryLabel.setForeground(TEXT_COLOUR);
        
        categoriesLabels[0].getDisplayPanel().add(trendingCategoryLabel);
        add(categoriesLabels[0]);
    
        categories = new AnimeScrollPanel[categoriesLabels.length];
        categories[0] = new AnimeScrollPanel(JikanController.getTrending(), AnimeImage.LARGE_SIZE);
        categories[1] = new AnimeScrollPanel(JikanController.getLatestUpdated(), AnimeImage.MEDIUM_SIZE);
        categories[2] = new AnimeScrollPanel(JikanController.getUpAndComing(), AnimeImage.MEDIUM_SIZE);
        
        categories[0].setLocation(PADDING*5, Page.getBottomY(categoriesLabels[0])+PADDING_Y);
        add(categories[0]);
        
        for (int i = 1; i<categoriesLabels.length; ++i) {
    
            categoriesLabels[i] = new ItemPanel(DIALOGUE_COLOUR);
            categoriesLabels[i].setLocation(
                    categoriesLabels[0].getX(),
                    Page.getBottomY(categories[i-1])+PADDING_Y*2
            );
            categoriesLabels[i].setSize(categoriesLabels[0].getDisplayPanel().getSize());
        
            JLabel minorCategoryLabel = new JLabel(categoryNames[i]);
            minorCategoryLabel.setBounds(trendingCategoryLabel.getBounds());
            minorCategoryLabel.setFont(CATEGORY_FONT);
            minorCategoryLabel.setForeground(TEXT_COLOUR);
        
            categoriesLabels[i].getDisplayPanel().add(minorCategoryLabel);
            add(categoriesLabels[i]);
    
            categories[i].setLocation(
                    PADDING*5,
                    Page.getBottomY(categoriesLabels[i])+PADDING
            );
            add(categories[i]);
        
        }
        
        // TODO remove this later
//        Anime anime = categories[1].getDisplayedAnime()[0].getAnime();
//        jikanController.setAnimePanel(anime);
//        enableAnimePanel(anime);
        
    }
    
    public AnimeScrollPanel[] getCategories () {
        return categories;
    }
    
    @Override
    public String toString () {
        return "Home";
    }
    
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
