package application;

import controller.ApplicationController;

/**
 * @author              Felix Fong
 * Date:                June 13 2021
 * Course code:         ICS4U1-03, Mr Fernandes
 * Application Title:   Ani-List App
 *
 * Description:
 * It is an application that allows users to filter, browse and get
 * recommended a list of many anime using the Jikan API. While
 * browsing, the user customizes their list while the program caters
 * its recommendations to the user's preferred genre.
 *
 * Features:
 *  1. Access to the MyAnimeList dataa base with the Jikan API
 *  2. Home page anime filter by year, season, and genre
 *  3. Dragging and dropping anime images
 *  4. Adding and editing anime in the Ani-List
 *  5. Generating anime to recommend for the Recommend Page
 *  6. Interactive recommendation algorithm that moves anime based on
 *     the user's interests for the genre
 *  7. Remembers the user's genre interests until the application is
 *     exited
 *  8. Does not recommend already recommended anime
 *
 * Major Skills:
 *  1. RapidApi to set up the API
 *  2. Unirest to interact with an API
 *  3. JFreeChart to display statistics
 *  4. Project management tool Maven
 *  5. Java swing
 *  6. Object oriented programming
 *  7. Polymorphism
 *  8. File reading and writing
 *  9. Serializable to save and load ani-lists
 *
 * Areas of Concern:
 *  - Due to time constraints, some features were omitted:
 *     1. Saving and loading a user's ani-list and genre preferences
 *        after exiting the application
 *     2. A filter bar in the Ani-List page
 *     3. AnimePanel scoring statis bar highlighting and captioning
 *
 *  - Dragging an anime's image from its position on the anime panel
 *    and dropping it over the transparent background causes a weird
 *    display bug. This is a Java Swing bug; cannot do anything
 *  - Loading the AnimePanel, filtering anime, switching modes in the
 *    RecommendationPage, sorting the ani-list, and scrolling through
 *    an anime scroll panel take a long time. A loading screen was
 *    considered, but could not be implemented due to time
 *    constraints.
 *  - When there are not enough anime available to display in an anime
 *    scroll panel, the only anime available will be copy and pasted
 *    over to the next few displayed. This is not a bug it is a
 *    feature. (Maybe have a blank slate in the place of the anime
 *    image to indicate that no anime could be found.)
 *  - Some anime do not have their licensors, producers, or genres
 *    available. Recommendation algorithm would not work with these
 *    anime since it relies on an anime's genre.
 *  - When the anime panel gets displayed, the scroll pane on the page
 *    scrolls all the way back up. A fix could be to store the original
 *    scroll coordinates and set the pane back to it. Due to time
 *    constraints, this was not done.
 *
 * NOTE:
 *  - The project must have the libraries
 *    - jfreechart-1.5.3 (found in resources)
 *    - unirest-java:1.4.9
 *      (see https://rapidapi.com/blog/how-to-use-an-api-with-java/
 *      and scroll down a bit if not already added)
 *    - gson-2.8.5
 *      (see https://github.com/google/gson)
 */
public class Main {
    
    public static void main (String[] args) {
        new ApplicationController();
    }
    
}
