package view;

import controller.JikanController;
import model.AniList;
import model.Anime;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AniListPage extends Page {
    
    private JLabel sortByLabel;
    private JComboBox<String> sortComboBox;
    private ItemPanel sortPanel;
    
    // Bar for displaying anime
    private JLabel rankLabel;
    private JLabel titleLabel;
    private JLabel statusLabel;
    private JLabel scoreLabel;
    
    private AniList aniList;
    private ArrayList<AniListAnimeBar> animeBars;
    private ItemPanel aniListPanel;
    
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
        sortComboBox.addItem("Ascending");
        sortComboBox.addItem("Descending");
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
    
        aniListPanel = new ItemPanel(DIALOGUE_COLOUR);
        aniListPanel.setBounds(
                sortPanel.getX(), Page.getBottomY(sortPanel)+PADDING_Y*3/2,
                Page.WIDTH-sortPanel.getX()*2, PADDING_Y*2
        );
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
    
        aniList = new AniList();
        animeBars = new ArrayList<>();
    
        // TODO testing
//        JikanController jikan = new JikanController();
//        ArrayList<Anime> season = jikan.getSeason("2018", "winter");
//        addAnime(season.get(0));
//        addAnime(season.get(1));
        
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
    
    public void addAnime (Anime anime) {
        
        aniList.getMyAnimeList().add(anime);
        
        animeBars.add(new AniListAnimeBar(anime));
        int back = animeBars.size()-1;
        JPanel displayedPanel = aniListPanel.getDisplayPanel();
        animeBars.get(back).setLocation(0, Page.getBottomY(rankLabel)+AniListAnimeBar.HEIGHT*back);
        displayedPanel.add(animeBars.get(back));
        
        aniListPanel.setSize(
                displayedPanel.getWidth(),
                displayedPanel.getHeight()+AniListAnimeBar.HEIGHT
        );
        
        getActualSize().setSize(new Dimension(WIDTH, HEIGHT+AniListAnimeBar.HEIGHT));
        revalidate();
        
    }
    
}
