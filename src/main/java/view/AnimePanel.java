package view;

import model.Anime;

import javax.swing.*;
import java.awt.*;

// TODO disable all input if anime panel is put up
public class AnimePanel extends JPanel {

    public static final Color FADED_COLOUR = new Color(0, 0, 0, 132);
    
    // Stuff to put in the title
    private JLabel titleLabel;
    private JPanel titlePanel;
    
    private JLabel scoreLabel;
    private JLabel scoreValueLabel;
    private JLabel scoreUsersLabel;
    private JPanel scoreIcon;
    
    // Synopsis
    private JTextArea synopsisTextArea;
    private JScrollPane synopsisPanel;
    
    private ItemPanel miscellaneousInformationItem;
    
    // Statistics
    private ItemPanel statisticsItem;
    
    private ItemPanel displayedAnimeItem;
    private AnimeImage displayedAnime;
    
    public AnimePanel () {
        
        setBackground(FADED_COLOUR);
        setBounds(0, 0, Page.WIDTH, Page.HEIGHT);
        
        
    }
    
    public void disableAnimePanel () {
        setVisible(false);
    }
    
    public void enableAnimePanel (Anime anime) {
        
        setVisible(true);
        
        
    }
    
}
