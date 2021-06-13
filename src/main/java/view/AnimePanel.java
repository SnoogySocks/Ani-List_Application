package view;

import jikanEnums.Genre;
import model.Anime;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

import static view.Page.*;

/**
 * A display for a specific anime
 */
public class AnimePanel extends JPanel {
    
    public static final Color FADED_COLOUR = new Color(0, 0, 0, 132);
    
    public static final Font MAJOR_FONT = new Font("Ubuntu", Font.BOLD, 33);
    public static final Font MEDIUM_FONT = new Font("Ubuntu", Font.BOLD, 19);
    public static final Font DIALOGUE_FONT = new Font("Ubuntu", Font.PLAIN, 15);
    
    // The panel to contain all of the information
    private final JPanel contentPanel;
    
    // Stuff to put in the title panel
    private final JTextArea titleTextArea;
    private final JPanel titlePanel;
    
    private final JLabel scoreLabel;
    private final JLabel scoreValueLabel;
    private final JLabel scoreUsersLabel;
    private final JPanel scoreIcon;
    
    // Synopsis
    private final JTextArea synopsisTextArea;
    private final JScrollPane synopsisPanel;
    
    // Miscellaneous Information about the anime
    private final JTextArea miscTextArea;
    private final ShadowedPanel miscellaneousInformationItem;
    
    // Statistics
    private final JTextArea statisticsTextArea;
    private final ShadowedPanel statisticsItem;
    
    // Button to go back
    private final JButton backButton;
    
    // Chart to display the scores
    private final DefaultCategoryDataset dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    
    // Anime
    private final ShadowedPanel displayedAnimePanel;
    private final AnimeImage displayedAnime;
    
    // DropBox
    private final JLabel dropBox;
    
    public AnimePanel () {
    
        // Set up the panel
        setLayout(null);
        setBackground(FADED_COLOUR);
        setBounds(0, 0, Page.WIDTH, Page.HEIGHT);
    
        displayedAnime = new AnimeImage();
        add(displayedAnime);
    
        // Create the back button to be on top of everything else
        backButton = new JButton("BACK");
        backButton.setBounds(Page.WIDTH/2-PADDING*8, PADDING_Y*3, PADDING*3, PADDING_Y*2);
        backButton.setFont(DIALOGUE_FONT);
        backButton.setBackground(USER_INPUT_COLOUR);
        backButton.setForeground(TEXT_COLOUR);
        backButton.setBorder(null);
        add(backButton);
    
        // Initialize the content panel
        contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setBounds(
                Page.WIDTH/2-PADDING*6, 0,
                Page.WIDTH-(Page.WIDTH/2-PADDING*6), Page.HEIGHT
        );
        contentPanel.setBackground(BACKGROUND_COLOUR);
        add(contentPanel);
        
        // Set up the anime image
        displayedAnimePanel = new ShadowedPanel(DIALOGUE_COLOUR);
        displayedAnimePanel.setBounds(
                PADDING*3-15, PADDING_Y,
                AnimeImage.getScaledWidth(AnimeImage.ANIME_PANEL_SIZE),
                AnimeImage.getScaledHeight(AnimeImage.ANIME_PANEL_SIZE)
        );
        contentPanel.add(displayedAnimePanel);
        
        // Create the title panel
        titlePanel = new JPanel();
        titlePanel.setLayout(null);
        titlePanel.setBounds(
                0, 0,
                Page.WIDTH-(Page.WIDTH/2-PADDING*6), PADDING_Y*4
        );
        titlePanel.setBackground(TitlePanel.BACKGROUND_COLOUR);
        contentPanel.add(titlePanel);
        
        // Initialize the text for the title
        titleTextArea = new JTextArea("Placeholder Place Place holder place holder");
        titleTextArea.setLineWrap(true);
        titleTextArea.setWrapStyleWord(true);
        titleTextArea.setEditable(false);
        titleTextArea.setFont(MAJOR_FONT);
        titleTextArea.setForeground(BACKGROUND_COLOUR);
        titleTextArea.setBackground(TitlePanel.BACKGROUND_COLOUR);
        titleTextArea.setBorder(null);
        titleTextArea.setBounds(
                titlePanel.getWidth()/3+PADDING, PADDING*5/2,
                PADDING*15, PADDING_Y*2+PADDING
        );
        titlePanel.add(titleTextArea);
    
    
        // Score icon
        scoreIcon = new JPanel();
        scoreIcon.setLayout(null);
        scoreIcon.setBounds(
                titlePanel.getWidth()-PADDING*8-35, titleTextArea.getY(),
                PADDING*6, PADDING_Y*2
        );
        scoreIcon.setBackground(DIALOGUE_COLOUR);
        titlePanel.add(scoreIcon);
        
        // Initialize the score label
        scoreLabel = new JLabel("Score");
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setBounds(0, PADDING/3, scoreIcon.getWidth(), PADDING);
        scoreLabel.setFont(MEDIUM_FONT);
        scoreLabel.setForeground(TEXT_COLOUR);
        scoreIcon.add(scoreLabel);
        
        // Initialize the label that will give the score of the anime
        scoreValueLabel = new JLabel("0.0");
        scoreValueLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreValueLabel.setBounds(
                0, Page.getBottomY(scoreLabel),
                scoreIcon.getWidth(), PADDING*3/2
        );
        scoreValueLabel.setFont(MAJOR_FONT);
        scoreValueLabel.setForeground(TEXT_COLOUR);
        scoreIcon.add(scoreValueLabel);
        
        // Initialize the score users label
        scoreUsersLabel = new JLabel("Many Users");
        scoreUsersLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreUsersLabel.setBounds(
                0, Page.getBottomY(scoreValueLabel),
                scoreIcon.getWidth(), PADDING
        );
        scoreUsersLabel.setFont(new Font("Ubuntu", Font.BOLD, 12));
        scoreUsersLabel.setForeground(TEXT_COLOUR);
        scoreIcon.add(scoreUsersLabel);
        
        // Initialize the synopsis text area
        synopsisTextArea = new JTextArea(
                "Placeholderwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww"+
                "Placeholderwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww"+
                "Placeholderwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww"+
                "Placeholderwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww"+
                "Placeholderwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww"+
                "Placeholderwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww"
        );
        synopsisTextArea.setLineWrap(true);
        synopsisTextArea.setWrapStyleWord(true);
        synopsisTextArea.setEditable(false);
        synopsisTextArea.setFont(DIALOGUE_FONT);
        synopsisTextArea.setForeground(TEXT_COLOUR);
        synopsisTextArea.setBackground(BACKGROUND_COLOUR);
        synopsisTextArea.setBorder(null);
        
        // Initialize the scroll pane for the text of the synopsis
        synopsisPanel = new JScrollPane(synopsisTextArea);
        synopsisPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        synopsisPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        synopsisPanel.setBounds(
                titleTextArea.getX(), Page.getBottomY(titlePanel)+PADDING,
                Page.getRightX(scoreIcon)-titleTextArea.getX(), PADDING_Y*3
        );
        synopsisPanel.setBorder(null);
        contentPanel.add(synopsisPanel);
        
        // Initialize the miscellaneousInformationItem
        miscellaneousInformationItem = new ShadowedPanel(DIALOGUE_COLOUR);
        miscellaneousInformationItem.setLayout(null);
        miscellaneousInformationItem.setBounds(
                displayedAnimePanel.getX(),
                Page.getBottomY(displayedAnimePanel)+PADDING/2,
                displayedAnimePanel.getDisplayPanel().getWidth(),
                contentPanel.getHeight()/4
        );
        contentPanel.add(miscellaneousInformationItem);
        
        // Initialize the text area for the anime's miscellaneous information
        miscTextArea = new JTextArea(
                "Episodes\n"+
                "Aired\n"+
                "Licensors\n"+
                "Producers\n"+
                "Genres\n"+
                "bleh\n"+
                "bleh\n"+
                "bleh"
        );
        miscTextArea.setLineWrap(true);
        miscTextArea.setWrapStyleWord(true);
        miscTextArea.setEditable(false);
        miscTextArea.setFont(DIALOGUE_FONT);
        miscTextArea.setForeground(TEXT_COLOUR);
        miscTextArea.setBackground(DIALOGUE_COLOUR);
        miscTextArea.setBorder(null);
        miscTextArea.setBounds(
                PADDING/2, PADDING/2,
                miscellaneousInformationItem.getDisplayPanel().getWidth()-PADDING,
                miscellaneousInformationItem.getDisplayPanel().getHeight()-PADDING
        );
        miscellaneousInformationItem.getDisplayPanel().add(miscTextArea);
        
        // Initialize the statistics item
        statisticsItem = new ShadowedPanel(DIALOGUE_COLOUR);
        statisticsItem.setLayout(null);
        statisticsItem.setBounds(
                displayedAnimePanel.getX(),
                Page.getBottomY(miscellaneousInformationItem)+PADDING/2,
                displayedAnimePanel.getDisplayPanel().getWidth(),
                PADDING_Y*3
        );
        contentPanel.add(statisticsItem);
        
        // Initialize the statistics text area
        statisticsTextArea = new JTextArea(
                "Watching:\n"+
                "Completed:\n"+
                "On Hold:\n"+
                "Dropped:\n"+
                "Plan to Watch:"
        );
        statisticsTextArea.setLineWrap(true);
        statisticsTextArea.setWrapStyleWord(true);
        statisticsTextArea.setEditable(false);
        statisticsTextArea.setFont(DIALOGUE_FONT);
        statisticsTextArea.setForeground(TEXT_COLOUR);
        statisticsTextArea.setBackground(DIALOGUE_COLOUR);
        statisticsTextArea.setBorder(null);
        statisticsTextArea.setBounds(
                PADDING/2, PADDING/2,
                statisticsItem.getDisplayPanel().getWidth()-PADDING,
                statisticsItem.getDisplayPanel().getHeight()-PADDING
        );
        statisticsItem.getDisplayPanel().add(statisticsTextArea);
        
        // Initialize the drop box for putting anime into the user's Ani-List
        ImageIcon dropBoxImage = new ImageIcon("src/main/resources/DropBox.png");
        dropBox = new JLabel(dropBoxImage);
        dropBox.setBounds(
                PADDING*5, PADDING_Y*3,
                dropBoxImage.getIconWidth(), dropBoxImage.getIconHeight()
        );
        add(dropBox);
        
        // Initialize dataset
        dataset = new DefaultCategoryDataset();
    
        // Prevent the panel from displaying
        disableAnimePanel();
        
    }
    
    // Getters
    public JButton getBackButton () {
        return backButton;
    }
    
    public AnimeImage getDisplayedAnime () {
        return displayedAnime;
    }
    
    /**
     * Put displayedAnime back to its original location
     */
    public void setDisplayedAnimeToOGLocation () {
        displayedAnime.setLocation(
                contentPanel.getX()+displayedAnimePanel.getX()+ShadowedPanel.SHADOW_OFFSET,
                displayedAnimePanel.getY()
        );
    }
    
    public JLabel getDropBox () {
        return dropBox;
    }
    
    public void disableAnimePanel () {
        setVisible(false);
    }
    
    /**
     * Disaply the anime panel on the screen
     * @param anime = anime to display with the anime panel
     */
    public void enableAnimePanel (Anime anime) {
        
        // Set the image
        displayedAnime.setAnimeImage(anime, AnimeImage.ANIME_PANEL_SIZE);
        setDisplayedAnimeToOGLocation();
        
        // Set the text area. If it is too big, make the font smaller
        titleTextArea.setText(anime.getTitle());
        if (anime.getTitle().length()>30) {
            titleTextArea.setFont(MEDIUM_FONT);
        } else {
            titleTextArea.setFont(MAJOR_FONT);
        }
        
        // set the score icon
        scoreValueLabel.setText(String.valueOf(anime.getAverageScore()));
        scoreUsersLabel.setText("Many users");
        synopsisTextArea.setText(anime.getSynopsis());
        
        // Set the misc text area
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("Episodes: ").append(anime.getNumEpisodes()).append('\n');
        strBuilder.append("Aired: ").append(anime.getDateAired()).append('\n');
        
        // Set the licensors
        strBuilder.append("Licensors: ");
        if (anime.getLicensors().length==0) {
            strBuilder.append("N/A");
        } else {
            for (String licensor : anime.getLicensors()) {
                strBuilder.append(licensor).append(", ");
            }
        }
        strBuilder.append('\n');
    
        // Set the producers
        strBuilder.append("Producers: ");
        if (anime.getProducers().length==0) {
            strBuilder.append("N/A");
        } else {
            for (String producer : anime.getProducers()) {
                strBuilder.append(producer).append(", ");
            }
        }
        strBuilder.append('\n');
    
        // Set the genres
        strBuilder.append("Genres: ");
        if (anime.getGenres().length==0) {
            strBuilder.append("N/A");
        } else {
            for (Genre genre : anime.getGenres()) {
                strBuilder.append(genre).append(", ");
            }
        }
        strBuilder.append('\n');
        miscTextArea.setText(strBuilder.toString());
        
        // Set the statistics text area
        statisticsTextArea.setText(
                "Watching: "+anime.getWatching()+'\n'+
                "Completed: "+anime.getCompleted()+'\n'+
                "On Hold: "+anime.getOnHold()+'\n'+
                "Dropped: "+anime.getDropped()+'\n'+
                "Plan to Watch: "+anime.getPlanToWatch()
        );
        
        // Set up the chart for scoring
        setDataset(anime.getScoringVotes());
        createChart(anime.getTotalVotes());
        repaint();
        setVisible(true);
    
    }
    
    /**
     * Create the dataset based on scoring votes
     * @param scoringVotes = The number of votes for each score
     */
    private void setDataset (int[] scoringVotes) {
    
        if (dataset.getColumnCount()!=0) {
            dataset.clear();
        }
    
        // Iterate in reverse so that score 10 is at the top
        for (int i = 9; i>=0; --i) {
            dataset.setValue(scoringVotes==null ? 0:scoringVotes[i], "Score", String.valueOf(i+1));
        }
        
    }
    
    /**
     * Create the chart panel to display the score distribution
     * @param totalVotes = the total votes from the anime
     */
    private void createChart (int totalVotes) {
        
        // Create the chart
        chart = ChartFactory.createBarChart(
                "Scoring Stats (Out of "+totalVotes+" Votes)",
                "# of Votes", "Score",
                dataset, PlotOrientation.HORIZONTAL,
                true, true, false
        );
        chart.setBackgroundPaint(BACKGROUND_COLOUR);
        
        // Remove the chart from the panel if one is already there
        if (chartPanel!=null) {
            contentPanel.remove(chartPanel);
        }
        
        // Initialize the chart panel
        chartPanel = new ChartPanel(chart);
        chartPanel.setLayout(null);
        chartPanel.setRangeZoomable(false);
        chartPanel.setDomainZoomable(false);
        chartPanel.setLocation(
                Page.getRightX(miscellaneousInformationItem)+PADDING*2,
                Page.getBottomY(synopsisPanel)+PADDING_Y
        );
        chartPanel.setSize(
                Page.getRightX(scoreIcon)-chartPanel.getX(),
                Page.getBottomY(statisticsItem)-chartPanel.getY()
        );
        contentPanel.add(chartPanel);
        
    }
    
}
