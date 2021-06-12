package view;

import model.Anime;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AniListPage extends Page {
    
    // Sorting box
    private final JLabel sortByLabel;
    private final JComboBox<String> sortComboBox;
    private final ItemPanel sortPanel;
    
    // Sub categories for the anime
    private final JLabel rankLabel;
    private final JLabel titleLabel;
    private final JLabel statusLabel;
    private final JLabel scoreLabel;
    
    private final ArrayList<AniListAnimeBar> animeBars;
    private final ItemPanel aniListPanel;
    private final Dimension aniListPanelDimensions;
    
    public AniListPage () {
    
        setActualSize(new Dimension(WIDTH, HEIGHT-38));
        
        sortPanel = new ItemPanel(DIALOGUE_COLOUR);
        sortPanel.setBounds(
            PADDING*8-ItemPanel.SHADOW_OFFSET, Page.getBottomY(getTitlePanel())+PADDING_Y,
            PADDING*17, PADDING*3
        );
        add(sortPanel);
        
        sortByLabel = new JLabel("Sort by");
        sortByLabel.setBounds(
                PADDING, PADDING/2,
                PADDING*6,
                sortPanel.getDisplayPanel().getHeight()-PADDING
        );
        sortByLabel.setFont(CATEGORY_FONT);
        sortByLabel.setForeground(TEXT_COLOUR);
        sortPanel.getDisplayPanel().add(sortByLabel);
        
        sortComboBox = new JComboBox<>();
        sortComboBox.addItem("Descending");
        sortComboBox.addItem("Ascending");
        sortComboBox.setBounds(
                Page.getRightX(sortByLabel), sortByLabel.getY(),
                sortPanel.getDisplayPanel().getWidth()-Page.getRightX(sortByLabel)-PADDING/2,
                sortByLabel.getHeight()
        );
        sortComboBox.setFont(CATEGORY_FONT);
        sortComboBox.setBackground(USER_INPUT_COLOUR);
        sortComboBox.setForeground(TEXT_COLOUR);
        sortComboBox.setBorder(null);
        sortPanel.getDisplayPanel().add(sortComboBox);
    
        aniListPanelDimensions = new Dimension(Page.WIDTH-sortPanel.getX()*2, PADDING_Y*2);
        aniListPanel = new ItemPanel(DIALOGUE_COLOUR);
        aniListPanel.setLocation(sortPanel.getX(), Page.getBottomY(sortPanel)+PADDING_Y*3/2);
        aniListPanel.setSize(aniListPanelDimensions);
        add(aniListPanel);
        
        rankLabel = new JLabel("Rank");
        rankLabel.setBounds(PADDING, PADDING, PADDING*6, PADDING_Y);
        rankLabel.setHorizontalAlignment(JLabel.CENTER);
        rankLabel.setFont(CATEGORY_FONT);
        rankLabel.setForeground(TEXT_COLOUR);
        aniListPanel.getDisplayPanel().add(rankLabel);
    
        titleLabel = new JLabel("Title");
        titleLabel.setBounds(Page.getRightX(rankLabel)+PADDING, PADDING, PADDING*6, PADDING_Y);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(CATEGORY_FONT);
        titleLabel.setForeground(TEXT_COLOUR);
        aniListPanel.getDisplayPanel().add(titleLabel);
        
        scoreLabel = new JLabel("Score");
        scoreLabel.setBounds(aniListPanel.getDisplayPanel().getWidth()-PADDING*6, PADDING, PADDING*5, PADDING_Y);
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setFont(CATEGORY_FONT);
        scoreLabel.setForeground(TEXT_COLOUR);
        aniListPanel.getDisplayPanel().add(scoreLabel);
        
        statusLabel = new JLabel("Status");
        statusLabel.setBounds(scoreLabel.getX()-PADDING*9, PADDING, PADDING*6, PADDING_Y);
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        statusLabel.setFont(CATEGORY_FONT);
        statusLabel.setForeground(TEXT_COLOUR);
        aniListPanel.getDisplayPanel().add(statusLabel);
    
        animeBars = new ArrayList<>();
        
    }
    
    public JComboBox<String> getSortComboBox () {
        return sortComboBox;
    }
    
    public ItemPanel getAniListPanel () {
        return aniListPanel;
    }
    
    public Dimension getAniListPanelDimensions () {
        return aniListPanelDimensions;
    }
    
    public ArrayList<AniListAnimeBar> getAnimeBars () {
        return animeBars;
    }
    
    @Override
    public String toString () {
        return "Ani-List";
    }
    
    @Override
    public void setEnabledUserInput (boolean enabled) {
        for (AniListAnimeBar animeBar: animeBars) {
            animeBar.setEnabledUserInput(enabled);
        }
    }
    
}
