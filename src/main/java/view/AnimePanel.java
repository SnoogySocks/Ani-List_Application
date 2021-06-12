package view;

import controller.JikanController;
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

// TODO disable all input if anime panel is put up
public class AnimePanel extends JPanel {
    
    public static final Color FADED_COLOUR = new Color(0, 0, 0, 132);
    
    public static final Font MAJOR_FONT = new Font("Ubuntu", Font.BOLD, 33);
    public static final Font MEDIUM_FONT = new Font("Ubuntu", Font.BOLD, 19);
    public static final Font DIALOGUE_FONT = new Font("Ubuntu", Font.PLAIN, 15);
    
    private final JPanel contentPanel;
    
    // Stuff to put in the title
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
    private final ItemPanel miscellaneousInformationItem;
    
    // Statistics
    private final JTextArea statisticsTextArea;
    private final ItemPanel statisticsItem;
    
    // Button to go back
    private final JButton backButton;
    
    // Chart to display the scores
    private final DefaultCategoryDataset dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    
    // Anime
    private final ItemPanel displayedAnimeItem;
    private AnimeImage displayedAnime;
    
    // DropBox
    private final JLabel dropBox;
    
    public AnimePanel () {
        
        setLayout(null);
        setBackground(FADED_COLOUR);
        setBounds(0, 0, Page.WIDTH, Page.HEIGHT);
    
        backButton = new JButton("BACK");
        backButton.setBounds(Page.WIDTH/2-PADDING*8, PADDING_Y*3, PADDING*3, PADDING_Y*2);
        backButton.setFont(DIALOGUE_FONT);
        backButton.setBackground(USER_INPUT_COLOUR);
        backButton.setForeground(TEXT_COLOUR);
        backButton.setBorder(null);
        add(backButton);
    
        contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setBounds(
                Page.WIDTH/2-PADDING*6, 0,
                Page.WIDTH-(Page.WIDTH/2-PADDING*6), Page.HEIGHT
        );
        contentPanel.setBackground(BACKGROUND_COLOUR);
        add(contentPanel);
        
        // Set up the anime image
        displayedAnimeItem = new ItemPanel(DIALOGUE_COLOUR);
        displayedAnimeItem.setBounds(
                PADDING*3-15, PADDING_Y,
                AnimeImage.getScaledWidth(AnimeImage.ANIME_PANEL_SIZE),
                AnimeImage.getScaledHeight(AnimeImage.ANIME_PANEL_SIZE)
        );
        contentPanel.add(displayedAnimeItem);
        
        // Create the title panel
        titlePanel = new JPanel();
        titlePanel.setLayout(null);
        titlePanel.setBounds(
                0, 0,
                Page.WIDTH-(Page.WIDTH/2-PADDING*6), PADDING_Y*4
        );
        titlePanel.setBackground(TitlePanel.BACKGROUND_COLOUR);
        contentPanel.add(titlePanel);
        
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
        
        scoreLabel = new JLabel("Score");
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setBounds(0, PADDING/3, scoreIcon.getWidth(), PADDING);
        scoreLabel.setFont(MEDIUM_FONT);
        scoreLabel.setForeground(TEXT_COLOUR);
        scoreIcon.add(scoreLabel);
        
        scoreValueLabel = new JLabel("0.00");
        scoreValueLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreValueLabel.setBounds(
                0, Page.getBottomY(scoreLabel),
                scoreIcon.getWidth(), PADDING*3/2
        );
        scoreValueLabel.setFont(MAJOR_FONT);
        scoreValueLabel.setForeground(TEXT_COLOUR);
        scoreIcon.add(scoreValueLabel);
        
        scoreUsersLabel = new JLabel("0 users");
        scoreUsersLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreUsersLabel.setBounds(
                0, Page.getBottomY(scoreValueLabel),
                scoreIcon.getWidth(), PADDING
        );
        scoreUsersLabel.setFont(new Font("Ubuntu", Font.BOLD, 12));
        scoreUsersLabel.setForeground(TEXT_COLOUR);
        scoreIcon.add(scoreUsersLabel);
        
        // Synopsis
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
        
        synopsisPanel = new JScrollPane(synopsisTextArea);
        synopsisPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        synopsisPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        synopsisPanel.setBounds(
                titleTextArea.getX(), Page.getBottomY(titlePanel)+PADDING,
                Page.getRightX(scoreIcon)-titleTextArea.getX(), PADDING_Y*3
        );
        synopsisPanel.setBorder(null);
        contentPanel.add(synopsisPanel);
        
        // Set up miscellaneousInformationItem
        miscellaneousInformationItem = new ItemPanel(DIALOGUE_COLOUR);
        miscellaneousInformationItem.setLayout(null);
        miscellaneousInformationItem.setBounds(
                displayedAnimeItem.getX(),
                Page.getBottomY(displayedAnimeItem)+PADDING/2,
                displayedAnimeItem.getDisplayPanel().getWidth(),
                contentPanel.getHeight()/4
        );
        contentPanel.add(miscellaneousInformationItem);
        
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
        
        statisticsItem = new ItemPanel(DIALOGUE_COLOUR);
        statisticsItem.setLayout(null);
        statisticsItem.setBounds(
                displayedAnimeItem.getX(),
                Page.getBottomY(miscellaneousInformationItem)+PADDING/2,
                displayedAnimeItem.getDisplayPanel().getWidth(),
                PADDING_Y*3
        );
        contentPanel.add(statisticsItem);
        
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
    
    public JLabel getScoreValueLabel () {
        return scoreValueLabel;
    }
    
    public JTextArea getSynopsisTextArea () {
        return synopsisTextArea;
    }
    
    public JTextArea getMiscTextArea () {
        return miscTextArea;
    }
    
    public JTextArea getStatisticsTextArea () {
        return statisticsTextArea;
    }
    
    public JButton getBackButton () {
        return backButton;
    }
    
    public DefaultCategoryDataset getDataset () {
        return dataset;
    }
    
    public JFreeChart getChart () {
        return chart;
    }
    
    public ChartPanel getChartPanel () {
        return chartPanel;
    }
    
    public ItemPanel getDisplayedAnimeItem () {
        return displayedAnimeItem;
    }
    
    public AnimeImage getDisplayedAnime () {
        return displayedAnime;
    }
    
    public JLabel getDropBox () {
        return dropBox;
    }
    
    public void disableAnimePanel () {
        setVisible(false);
    }
    
    public void enableAnimePanel (Anime anime) {
        
        // Set the image
        if (displayedAnime!=null) {
            displayedAnimeItem.getDisplayPanel().remove(displayedAnime);
        }
        displayedAnime = new AnimeImage(anime, AnimeImage.ANIME_PANEL_SIZE);
        displayedAnime.setLocation(0, 0);
        displayedAnimeItem.getDisplayPanel().add(displayedAnime);
        
        // Update the information
        titleTextArea.setText(anime.getTitle());
        if (anime.getTitle().length()>43) {
            titleTextArea.setFont(MEDIUM_FONT);
        } else {
            titleTextArea.setFont(MAJOR_FONT);
        }
        
        scoreValueLabel.setText(String.valueOf(anime.getAverageScore()));
        scoreUsersLabel.setText("Many users");
        synopsisTextArea.setText(anime.getSynopsis());
        
        // Initialize the misc text area
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("Episodes: ").append(anime.getNumEpisodes()).append('\n');
        strBuilder.append("Aired: ").append(anime.getDateAired()).append('\n');
        
        strBuilder.append("Licensors: ");
        for (String licensor : anime.getLicensors()) {
            strBuilder.append(licensor).append(", ");
        }
        if (anime.getLicensors().length==0) {
            strBuilder.append("N/A");
        }
        strBuilder.append('\n');
        
        strBuilder.append("Producers: ");
        for (String producer : anime.getProducers()) {
            strBuilder.append(producer).append(", ");
        }
        if (anime.getProducers().length==0) {
            strBuilder.append("N/A");
        }
        strBuilder.append('\n');
    
        strBuilder.append("Genres: ");
        for (Genre genre : anime.getGenres()) {
            strBuilder.append(genre).append(", ");
        }
        if (anime.getGenres().length==0) {
            strBuilder.append("N/A");
        }
        strBuilder.append('\n');
        miscTextArea.setText(strBuilder.toString());
        
        // Create the statistics text area
        statisticsTextArea.setText(
                "Watching: "+anime.getWatching()+'\n'+
                "Completed: "+anime.getCompleted()+'\n'+
                "On Hold: "+anime.getOnHold()+'\n'+
                "Dropped: "+anime.getDropped()+'\n'+
                "Plan to Watch: "+anime.getPlanToWatch()
        );
        
        setDataset(anime.getScoringVotes());
        createChart(anime);
        repaint();
        setVisible(true);
    
    }
    
    private void setDataset (int[] scoringVotes) {
    
        if (dataset.getColumnCount()!=0) {
            dataset.clear();
        }
    
        // Iterate in reverse so that score 10 is at the top
        for (int i = 9; i>=0; --i) {
            dataset.setValue(scoringVotes==null ? 0:scoringVotes[i], "Score", String.valueOf(i+1));
        }
        
    }
    
    private void createChart (Anime anime) {
        
        chart = ChartFactory.createBarChart(
                "Scoring Stats (Out of "+anime.getTotalVotes()+" Votes)",
                "# of Votes", "Score",
                dataset, PlotOrientation.HORIZONTAL,
                true, true, false
        );
        chart.setBackgroundPaint(BACKGROUND_COLOUR);
        
        if (chartPanel!=null) {
            contentPanel.remove(chartPanel);
        }
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
