package view;

import controller.JikanController;
import model.Anime;

import javax.swing.*;
import java.awt.*;

public class HomePage extends Page {
    
    private final Dimension preferredSize;
    
    private final ItemPanel[] categoriesLabels;
    private final AnimeScrollPanel[] categories;
    
    public HomePage (JikanController jikanController) {
    
        preferredSize = new Dimension(WIDTH, HEIGHT*2);
        setPreferredSize(preferredSize);
    
        categoriesLabels = new ItemPanel[3];
        String[] categoryNames = {
                "Trending", "Latest Updated", "Up & Coming"
        };
        
        categoriesLabels[0] = new ItemPanel(DIALOGUE_COLOUR);
        categoriesLabels[0].setBounds(
                PADDING*8, Page.getBottomY(getTitlePanel())+PADDING_Y,
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
        categories[0] = new AnimeScrollPanel(jikanController.getTrending(), AnimeImage.LARGE_SIZE);
        categories[1] = new AnimeScrollPanel(jikanController.getLatestUpdated(), AnimeImage.MEDIUM_SIZE);
        categories[2] = new AnimeScrollPanel(jikanController.getUpAndComing(), AnimeImage.MEDIUM_SIZE);
        
        categories[0].setLocation(PADDING*5, Page.getBottomY(categoriesLabels[0])+PADDING_Y);
        add(categories[0]);
        
        for (int i = 1; i<categoriesLabels.length; ++i) {
    
            categoriesLabels[i] = new ItemPanel(DIALOGUE_COLOUR);
            categoriesLabels[i].setLocation(
                    PADDING*8,
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
//        Anime anime = categories[1].getDisplayedAnime(0).getAnime();
//        jikanController.setAnimePanel(anime);
//        enableAnimePanel(anime);
        
    }
    
    public int getCategoriesLength () {
        return categories.length;
    }
    
    public AnimeScrollPanel getCategories (int index) {
        return categories[index];
    }
    
    @Override
    public String toString () {
        return "Home";
    }
    
    public void setEnabledUserInput (boolean enabled) {
    
        // Enabled disable all the user input features
        for (AnimeScrollPanel category: categories) {
            category.setEnabledUserInput(enabled);
        }
        
        
    }
    
    @Override
    public void disableAnimePanel () {
        
        setEnabledUserInput(true);
        preferredSize.setSize(preferredSize);
        super.disableAnimePanel();
        
    }
    
    // TODO disable mouse listener thing if that ever works
    @Override
    public void enableAnimePanel (Anime anime) {
        setEnabledUserInput(false);
        super.enableAnimePanel(anime);
    }
    
}
