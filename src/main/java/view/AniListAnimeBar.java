package view;

import model.Anime;

import javax.swing.*;

import static view.Page.*;

/**
 * The bar that displays information on an anime in the AniListPage:
 * - Rank in the Ani-List
 * - Image
 * - Title
 * - User's status on the anime
 * @see jikanEnums.Status
 * - User's score for the anime
 * - An edit button to change the anime's status and score
 */
public class AniListAnimeBar extends JPanel {
    
    public static final int WIDTH = Page.WIDTH-(PADDING*8-ShadowedPanel.SHADOW_OFFSET)*2;
    public static final int HEIGHT = PADDING_Y*3;
    
    // The anime on the anime bar
    private final AnimeImage animeImage;
    
    // GUI to display the anime's information
    private final JLabel rankLabel;
    private final JLabel titleLabel;
    private final JLabel statusLabel;
    private final JLabel scoreLabel;
    
    // Button to edit the user's preferences
    private final JButton editButton;
    
    public AniListAnimeBar (Anime anime, int rank) {
    
        // Set up the JPanel
        setLayout(null);
        setSize(WIDTH, HEIGHT);
        setBackground(DIALOGUE_COLOUR);
        
        // Initialize the rank label
        rankLabel = new JLabel(Integer.toString(rank));
        rankLabel.setBounds(PADDING, PADDING_Y, PADDING*6, PADDING_Y);
        rankLabel.setHorizontalAlignment(JLabel.CENTER);
        rankLabel.setFont(DIALOGUE_FONT);
        rankLabel.setForeground(TEXT_COLOUR);
        add(rankLabel);
    
        // Initialize the anime image
        animeImage = new AnimeImage(anime, AnimeImage.SMALL_SIZE);
        animeImage.setLocation(Page.getRightX(rankLabel)+PADDING*2, PADDING/2);
        add(animeImage);
    
        // Initialize the title label
        titleLabel = new JLabel(anime.getTitle());
        titleLabel.setBounds(Page.getRightX(animeImage)+PADDING, PADDING_Y/2, PADDING*12, PADDING_Y*2);
        titleLabel.setFont(DIALOGUE_FONT);
        titleLabel.setForeground(TEXT_COLOUR);
        add(titleLabel);
        
        // Initialize the score label
        scoreLabel = new JLabel(Integer.toString(anime.getUserScore()));
        scoreLabel.setBounds(WIDTH-PADDING*6, PADDING_Y, PADDING*5, PADDING_Y);
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setFont(DIALOGUE_FONT);
        scoreLabel.setForeground(TEXT_COLOUR);
        add(scoreLabel);
        
        // Initialize the status label
        statusLabel = new JLabel(anime.getUserStatus().toString());
        statusLabel.setBounds(scoreLabel.getX()-PADDING*9, PADDING_Y, PADDING*6, PADDING_Y);
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        statusLabel.setFont(DIALOGUE_FONT);
        statusLabel.setForeground(TEXT_COLOUR);
        add(statusLabel);
        
        // Initialize the edit button
        editButton = new JButton("Edit");
        editButton.setBounds(statusLabel.getX()-PADDING*9, PADDING_Y, PADDING*6, PADDING_Y);
        editButton.setFont(DIALOGUE_FONT);
        editButton.setBackground(USER_INPUT_COLOUR);
        editButton.setForeground(TEXT_COLOUR);
        editButton.setBorder(null);
        add(editButton);
    
    }
    
    // Getters
    public void setEnabledUserInput (boolean enabled) {
        editButton.setEnabled(enabled);
    }
    
    public JButton getEditButton () {
        return editButton;
    }
    
    public AnimeImage getAnimeImage () {
        return animeImage;
    }
    
    public Anime getAnime () {
        return animeImage.getAnime();
    }
    
}
