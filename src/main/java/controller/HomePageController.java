package controller;

import jikanEnums.Genre;
import jikanEnums.Season;
import model.Anime;
import view.AnimeImage;
import view.AnimeScrollPanel;
import view.HomePage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;

public class HomePageController extends PageController {
    
    private final HomePage gui;
    private final JCheckBox[] genreFilter;      // TODO clear filter if user switches pages
    
    public HomePageController (HomePage gui) {
        
        this.gui = gui;
        
        Genre[] filterOptions = Genre.getFilterOptions();
        genreFilter = new JCheckBox[filterOptions.length];
        for (int i = 0; i<filterOptions.length; ++i) {
            genreFilter[i] = new JCheckBox(filterOptions[i].toString());
        }
        
    }
    
    @Override
    public void setUpListeners () {
    
        // Set up action listeners for the filter bar
        ArrayList<JComponent> filterOptions = gui.getTitlePanel().getFilterBar().getFilterOptions();
        for (int i = 0; i<filterOptions.size(); ++i) {
            
             if (i!=2) {
                 JComboBox<String> comboBox = (JComboBox<String>) filterOptions.get(i);
                 comboBox.addActionListener(this);
             } else {
                 JButton genreButton = (JButton) filterOptions.get(i);
                 genreButton.addActionListener(this);
             }
             
        }
        
        for (AnimeScrollPanel category: gui.getCategories()) {
            
            // Add action listeners to the anime options
            for (JButton displayedAnimeButton: category.getDisplayedAnimeButtons()) {
                displayedAnimeButton.addActionListener(this);
            }
    
            // Add action listeners to the scroll buttons
            for (JButton scrollButton: category.getScrollButtons()) {
                scrollButton.addActionListener(this);
            }
    
        }
        
        gui.getAnimePanel().getBackButton().addActionListener(this);
        
    }
    
    @Override
    public void actionPerformed (ActionEvent e) {
    
        ArrayList<JComponent> filterOptions = gui.getTitlePanel().getFilterBar().getFilterOptions();
        for (int i = 0; i<filterOptions.size(); ++i) {
        
            if (e.getSource()!=filterOptions.get(i)) continue;
    
            if (i==2) {
                decideGenreFilter();
            }
            updateCategories();
            break;
    
        }
    
        for (AnimeScrollPanel category: gui.getCategories()) {
        
            // Add action listeners to the anime options
            for (JButton displayedAnimeButton: category.getDisplayedAnimeButtons()) {
                
                if (e.getSource()==displayedAnimeButton) {
                    enableAnimePanel(category, displayedAnimeButton);
                    break;
                }
                
            }
        
            // Add action listeners to the scroll buttons
            if (e.getSource()==category.getScrollButtons()[0]) {
                category.scrollLeft();
                break;
            } else if (e.getSource()==category.getScrollButtons()[1]) {
                category.scrollRight();
                break;
            }
        
        }
        
        if (e.getSource()==gui.getAnimePanel().getBackButton()) {
            gui.disableAnimePanel();
        }
        
        gui.repaint();
        
    }
    
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
            ArrayList<Anime> categoryTemp1;
            categoryTemp1 = JikanController.getSeason(year, season);
            categoryTemp1.sort(Collections.reverseOrder());
            
            // Filter categoryTemp1 if the user chose a filter
            if (validGenres.size()!=0) {
                categoryTemp1 = filterGenre(categoryTemp1, validGenres);
            }
            
            gui.getCategories()[0].setAnimeCategory(categoryTemp1);
            
            // Get the up and coming anime
            Season season2 = Season.parseSeason(season);
            categoryTemp1 = JikanController.getSeason(
                    season2!=Season.FALL ? year:Integer.toString(Integer.parseInt(year)+1),
                    season2.getNextSeason().toString().toLowerCase()
            );
            categoryTemp1.sort(Collections.reverseOrder());
    
            // Filter categoryTemp1 if the user chose a filter
            if (validGenres.size()!=0) {
                categoryTemp1 = filterGenre(categoryTemp1, validGenres);
            }
            gui.getCategories()[2].setAnimeCategory(categoryTemp1);
    
        }
        
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
    
    public void decideGenreFilter () {
    
        Object[] message = {
                "Pick genres to filter",
                genreFilter
        };
        
        JOptionPane.showMessageDialog(gui, message, "Genre Filter", JOptionPane.QUESTION_MESSAGE);
    
    }
    
}
