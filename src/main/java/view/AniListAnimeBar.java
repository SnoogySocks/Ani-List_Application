package view;

import jikanEnums.Status;
import model.Anime;

import javax.swing.*;

import static view.Page.*;

public class AniListAnimeBar extends JPanel {

    public static final int WIDTH = Page.WIDTH-(PADDING*8-ItemPanel.SHADOW_OFFSET)*2;
    public static final int HEIGHT = PADDING_Y*3;
    
    private Anime anime;
    
    private JLabel rankLabel;
    private AnimeImage animeImage;
    private JLabel titleLabel;
    private JButton editButton;
    private JLabel statusLabel;
    private JLabel scoreLabel;
    
    public AniListAnimeBar (Anime anime) {
    
        setLayout(null);
        setSize(WIDTH, HEIGHT);
        setBackground(DIALOGUE_COLOUR);
        
        this.anime = anime;
    
        rankLabel = new JLabel("N/A");
        rankLabel.setBounds(PADDING, PADDING_Y, PADDING*6, PADDING_Y);
        rankLabel.setHorizontalAlignment(JLabel.CENTER);
        rankLabel.setFont(DIALOGUE_FONT);
        rankLabel.setForeground(TEXT_COLOUR);
        add(rankLabel);
        
        animeImage = new AnimeImage(anime, AnimeImage.SMALL_SIZE);
        animeImage.setLocation(Page.getRightX(rankLabel)+PADDING*2, PADDING/2);
        add(animeImage);
    
        titleLabel = new JLabel(anime.getTitle());
        titleLabel.setBounds(Page.getRightX(animeImage)+PADDING, PADDING_Y/2, PADDING*12, PADDING_Y*2);
        titleLabel.setFont(DIALOGUE_FONT);
        titleLabel.setForeground(TEXT_COLOUR);
        add(titleLabel);
        
        scoreLabel = new JLabel("N/A");
        scoreLabel.setBounds(WIDTH-PADDING*6, PADDING_Y, PADDING*5, PADDING_Y);
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setFont(DIALOGUE_FONT);
        scoreLabel.setForeground(TEXT_COLOUR);
        add(scoreLabel);
        
        statusLabel = new JLabel(Status.NA.toString());
        statusLabel.setBounds(scoreLabel.getX()-PADDING*9, PADDING_Y, PADDING*6, PADDING_Y);
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        statusLabel.setFont(DIALOGUE_FONT);
        statusLabel.setForeground(TEXT_COLOUR);
        add(statusLabel);
        
        editButton = new JButton("Edit");
        editButton.setBounds(statusLabel.getX()-PADDING*9, PADDING_Y, PADDING*6, PADDING_Y);
        editButton.setFont(DIALOGUE_FONT);
        editButton.setBackground(USER_INPUT_COLOUR);
        editButton.setForeground(TEXT_COLOUR);
        editButton.setBorder(null);
        add(editButton);
    
    }
    
    public void setEnabledUserInput (boolean enabled) {
        editButton.setEnabled(enabled);
    }

}