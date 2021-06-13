package view;

import jikanEnums.Season;

import javax.swing.*;

import java.util.ArrayList;

import static view.Page.*;

/**
 * Lets the user filter anime by season, year, and genre
 */
public class FilterBar extends ShadowedPanel {
    
    // GUI
    private final JLabel filterLabel;
    private final ArrayList<JLabel> filterLabels;
    private final ArrayList<JComponent> filterOptions;
    
    public FilterBar (boolean shouldAddGenreParameter) {
        
        super(DIALOGUE_COLOUR);
        
        // Initialize the filter label
        filterLabel = new JLabel("Filters");
        filterLabel.setBounds(PADDING*2, PADDING, PADDING*5, PADDING_Y);
        filterLabel.setFont(CATEGORY_FONT);
        filterLabel.setForeground(TEXT_COLOUR);
        getDisplayPanel().add(filterLabel);
        
        // Set up the filter selection options
        filterLabels = new ArrayList<>();
        String[] labelParameters = {
                "Season", "Year", "Genre"
        };
        
        // Initialize the filter option parameters
        filterOptions = new ArrayList<>();
        ArrayList<String>[] optionsParameters = new ArrayList[shouldAddGenreParameter? 3:2];
        for (int i = 0; i<optionsParameters.length; ++i) {
            optionsParameters[i] = new ArrayList<>();
        }
        
        // Season options
        for (Season season: Season.values()) {
            optionsParameters[0].add(season.toString());
        }
        
        // Year options
        for (int year = 2021; year>=1927; --year) {
            optionsParameters[1].add(Integer.toString(year));
        }
        
        for (int i = 0; i<optionsParameters.length; ++i) {
            
            // Create filterLabels
            filterLabels.add(new JLabel(labelParameters[i]));
            filterLabels.get(i).setBounds(
                    Page.getRightX(filterLabel)+PADDING*(2+7*i),
                    PADDING/2,
                    PADDING*6, PADDING_Y*2/3
            );
            filterLabels.get(i).setHorizontalAlignment(JLabel.CENTER);
            filterLabels.get(i).setFont(DIALOGUE_FONT);
            filterLabels.get(i).setForeground(TEXT_COLOUR);
            
            getDisplayPanel().add(filterLabels.get(i));
            
            // Create filterOptions and add options to it
            JComponent filterOption;
            
            // If the filter option is not genre then
            if (i!=2) {
                
                // Add the options into the filter option
                filterOption = new JComboBox<String>();
                JComboBox<String> comboBox = (JComboBox<String>) filterOption;
                for (String option: optionsParameters[i]) {
                    comboBox.addItem(option);
                }
                
            // Otherwise initialize the genre button
            } else {
                filterOption = new JButton("Select");
            }
    
            // Set up the filterOption GUI
            filterOption.setBounds(
                    filterLabels.get(i).getX(),
                    Page.getBottomY(filterLabels.get(i)),
                    filterLabels.get(i).getWidth(), filterLabels.get(i).getHeight()
            );
            filterOption.setFont(DIALOGUE_FONT);
            filterOption.setBackground(USER_INPUT_COLOUR);
            filterOption.setForeground(TEXT_COLOUR);
            filterOption.setBorder(null);
            
            filterOptions.add(filterOption);
            getDisplayPanel().add(filterOptions.get(i));
            
        }
        
        // Set the current season to summer
        ((JComboBox<String>) filterOptions.get(0)).setSelectedIndex(2);
        
    }
    
    // Getter
    public ArrayList<JComponent> getFilterOptions () {
        return filterOptions;
    }
    
    /**
     * Disable the user input
     * @param enabled = decides whether to enable or disable the input
     * @see Page#setEnabledUserInput(boolean)
     */
    public void setEnableUserInput (boolean enabled) {
        for (JComponent comp: filterOptions) {
            comp.setEnabled(enabled);
        }
    }
    
}
