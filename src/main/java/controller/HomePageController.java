package controller;

import jikanEnums.Genre;
import jikanEnums.Season;
import model.Anime;
import view.AnimeImage;
import view.AnimeScrollPanel;
import view.HomePage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Controls the HomePage
 * @see PageController
 */
public class HomePageController extends PageController {
    
    private final HomePage gui;
    private final JCheckBox[] genreFilter;  // Tracks the user's filters
    
    public HomePageController (HomePage gui) {
    
        super(gui.getAnimePanel());
        
        this.gui = gui;
        
        // Initialize genreFilter
        Genre[] filterOptions = Genre.getFilterOptions();
        genreFilter = new JCheckBox[filterOptions.length];
        for (int i = 0; i<filterOptions.length; ++i) {
            genreFilter[i] = new JCheckBox(filterOptions[i].toString());
        }
        
    }
    
    /**
     * @see PageController#setUpListeners()
     */
    @Override
    public void setUpListeners () {
    
        super.setUpListeners();
        
        // Set up action listeners for the filter bar
        ArrayList<JComponent> filterOptions = gui.getTitlePanel().getFilterBar().getFilterOptions();
        for (int i = 0; i<filterOptions.size(); ++i) {
            
             // Cast the current filter option to a combo box if it is not the genre filter option
             if (i!=2) {
                 JComboBox<String> comboBox = (JComboBox<String>) filterOptions.get(i);
                 comboBox.addActionListener(this);
                 
             // Otherwise cast it as a button
             } else {
                 JButton genreButton = (JButton) filterOptions.get(i);
                 genreButton.addActionListener(this);
             }
             
        }
        
        // Set up the action listeners for the category scroll panels
        for (AnimeScrollPanel category: gui.getCategories()) {
            
            // Add action listeners to the displayed anime
            for (JButton displayedAnimeButton: category.getDisplayedAnimeButtons()) {
                displayedAnimeButton.addActionListener(this);
            }
    
            // Add action listeners to the scroll buttons
            for (JButton scrollButton: category.getScrollButtons()) {
                scrollButton.addActionListener(this);
            }
    
        }
        
    }
    
    @Override
    public void actionPerformed (ActionEvent e) {
    
        ArrayList<JComponent> filterOptions = gui.getTitlePanel().getFilterBar().getFilterOptions();
        
        // Perform action events for the filter options
        for (int i = 0; i<filterOptions.size(); ++i) {
        
            if (e.getSource()==filterOptions.get(i)) {
                
                // Query the user for their desired genre if the event
                // comes from the genre filter option. Then update the categories
                if (i==2) {
                    decideGenreFilter();
                }
                updateCategories();
                break;
                
            }
    
        }
    
        for (AnimeScrollPanel category: gui.getCategories()) {
        
            // Perform action events for the displayed anime
            for (JButton displayedAnimeButton: category.getDisplayedAnimeButtons()) {
                
                if (e.getSource()==displayedAnimeButton) {
                    enableAnimePanel(category, displayedAnimeButton);
                    break;
                }
                
            }
        
            // Perform action events for the scroll buttons
            if (e.getSource()==category.getScrollButtons()[0]) {
                category.scrollLeft();
                break;
            } else if (e.getSource()==category.getScrollButtons()[1]) {
                category.scrollRight();
                break;
            }
        
        }
    
        // Disable the anime panel when the back button is pressed
        if (e.getSource()==gui.getAnimePanel().getBackButton()) {
            gui.disableAnimePanel();
        }
        
        gui.repaint();
        
    }
    
    /**
     * Enable the anime panel
     * @param category = the category from where the anime was selected
     * @param animeButton = the anime button that was clicked
     */
    public void enableAnimePanel (AnimeScrollPanel category, JButton animeButton) {
        
        // Find the anime that got picked
        Anime pickedAnime = new Anime();
        for (AnimeImage animeImage: category.getDisplayedAnime()) {
            if (animeImage.getIcon()==animeButton.getIcon()) {
                pickedAnime.setAnime(animeImage.getAnime());
                break;
            }
        }
    
        // Enable the anime panel
        JikanController.setAnimePanel(pickedAnime);
        gui.enableAnimePanel(pickedAnime);
        
    }
    
    /**
     * Update the categories if a genre has been changed
     */
    public void updateCategories () {
        
        ArrayList<JComponent> filterOptions = gui.getTitlePanel().getFilterBar().getFilterOptions();
        String season = ((String) ((JComboBox<String>) filterOptions.get(0)).getSelectedItem()).toLowerCase();
        String year = ((String) ((JComboBox<String>) filterOptions.get(1)).getSelectedItem()).toLowerCase();
        
        ArrayList<Genre> validGenres = new ArrayList<>();
        for (JCheckBox genre : genreFilter) {
            if (genre.isSelected()) {
                validGenres.add(Genre.parseGenre(genre.getText()));
            }
        }
    
        // Re enable the latest category and set everything back to the home screen
        // if the parameters are the same
        if (season.equals("summer") && year.equals("2021") && validGenres.size()==0) {
            
            gui.getCategories()[1].setEnabledUserInput(true);
            gui.getCategories()[0].setAnimeCategory(JikanController.getTrending());
            gui.getCategories()[1].setAnimeCategory(JikanController.getLatestUpdated());
            gui.getCategories()[2].setAnimeCategory(JikanController.getUpAndComing());
            
        } else {
            
            // Disable the latest anime category
            gui.getCategories()[1].setEnabledUserInput(false);
            
            // Get the trending anime
            ArrayList<Anime> categoryTemp;
            categoryTemp = JikanController.getSeason(year, season);
            categoryTemp.sort(Collections.reverseOrder());
            
            // Filter categoryTemp if the user chose a filter
            if (validGenres.size()!=0) {
                
                categoryTemp = filterGenre(categoryTemp, validGenres);
                
                if (categoryTemp.size()==0) {
                    displayedEmptyErrorMessage();
                    return;
                }
                
            }
    
            // Get the up and coming anime
            Season season2 = Season.parseSeason(season);
            categoryTemp = JikanController.getSeason(
                    season2!=Season.FALL ? year:Integer.toString(Integer.parseInt(year)+1),
                    season2.getNextSeason().toString().toLowerCase()
            );
            categoryTemp.sort(Collections.reverseOrder());
    
            // Filter categoryTemp if the user chose a filter
            if (validGenres.size()!=0) {
                
                categoryTemp = filterGenre(categoryTemp, validGenres);
    
                if (categoryTemp.size()==0) {
                    displayedEmptyErrorMessage();
                    return;
                }
                
            }
            
            gui.getCategories()[0].setAnimeCategory(categoryTemp);
            gui.getCategories()[2].setAnimeCategory(categoryTemp);
    
        }
        
    }
    
    /**
     * Display an error message when no anime could be found with the filters
     */
    public void displayedEmptyErrorMessage () {
        
        JOptionPane.showMessageDialog(
                ApplicationController.getFrame(),
                "No anime match the filters! Try again.",
                "Alter",
                JOptionPane.ERROR_MESSAGE
        );
        
    }
    
    /**
     * @param ogList = the original list of anime
     * @param validGenres = the valid genre
     * @return a filtered array containing only anime with the specified genre
     */
    public ArrayList<Anime> filterGenre (ArrayList<Anime> ogList, ArrayList<Genre> validGenres) {
    
        ArrayList<Anime> filteredList = new ArrayList<>();
        
        // Go over all the anime in the list
        for (int i = 0;
             filteredList.size()<gui.getCategories()[0].getAnimeCategory().size()
                     && i<ogList.size();
             ++i) {
        
            // Go over every genre
            boolean hasGenre = false;
            for (Genre validGenre: validGenres) {
                
                hasGenre = false;
                
                // Iterate through the anime's genre until the required genre is found
                for (Genre animeGenre: ogList.get(i).getGenres()) {
                    if (validGenre==animeGenre) {
                        hasGenre = true;
                        break;
                    }
                }
            
                // Break if the anime does not have the genre
                if (!hasGenre) {
                    break;
                }
                
            }
            
            // If the anime has all the genres then add it to the list
            if (hasGenre) {
                filteredList.add(ogList.get(i));
            }
        
        }
        
        return filteredList;
        
    }
    
    /**
     * Inquire the user for their genre filter
     */
    public void decideGenreFilter () {
    
        Object[] message = {
                "Pick genres to filter",
                genreFilter
        };
        
        JOptionPane.showMessageDialog(ApplicationController.getFrame(), message, "Genre Filter", JOptionPane.QUESTION_MESSAGE);
    
    }
    
    /**
     * @see PageController#mouseDragged(MouseEvent)
     */
    @Override
    public void mouseDragged (MouseEvent e) {
        super.mouseDragged(e);
        gui.repaint();
    }
    
}
