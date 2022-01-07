# Ani-List_Felix_Fong
<h2>Description</h2>
It is an application that allows users to filter, browse and get recommended a list of many anime using the Jikan API. While browsing, the user customizes their list while the program caters its recommendations to the user's preferred genre.

<h2>Features</h2>
<ol>
  <li>Access to the <a href="https://myanimelist.net/">MyAnimeList</a> data base with the <a href="https://jikan.moe/">Jikan API</a></li>
  <li>Home page anime filter by year, season, and genre</li>
  <li>Dragging and dropping anime images</li>
  <li>Adding and editing anime in the Ani-List</li>
  <li>Generating anime to recommend for the Recommend Page</li>
  <li>Interactive recommendation algorithm that moves anime based on the user's interests for the genre</li>
  <li>Remembers the user's genre interests until the application is exited</li>
  <li>Does not recommend already recommended anime</li>
  <li>Saving and loading ani-list</li>
  <li>A loading screen</li>
</ol>

<h2>Major Skills</h2>
<ol>
  <li><a href="https://rapidapi.com/">RapidApi</a> to set up the API</li>
  <li><a href="http://kong.github.io/unirest-java/">Unirest</a> to interact with the API</li>
  <li>JFreeChart to display statistics</li>
  <li>Project management tool Maven</li>
  <li>Java swing</li>
  <li>Object oriented programming</li>
  <li>Polymorphism</li>
  <li>File reading and writing</li>
  <li>Serialization to save and load ani-list</li>
  <li>Swing-worker for multi-threading loading</li>
</ol>

<h2>Areas of Concern</h2>
<ul>
  <li>Due to time constraints, some features were omitted:</li>
<ol>
  <li>Saving and loading a user's ani-list and genre preferences after exiting the application</li>
  <li>A filter bar in the Ani-List page</li>
  <li>AnimePanel scoring statis bar highlighting and captioning</li>
</ol>
</ul>
  
<ul>
  <li>Dragging an anime's image from its position on the anime panel and dropping it over the transparent background causes a weird display bug. This is a Java Swing bug; cannot do anything</li>
  <li>Loading the AnimePanel, filtering anime, switching modes in the RecommendationPage, sorting the ani-list, and scrolling through an anime scroll panel take a long time. A loading screen was considered, but could not be implemented due to time constraints.</li>
  <li>When there are not enough anime available to display in an anime scroll panel, the only anime available will be copy and pasted over to the next few displayed. This is not a bug it is a feature. (Maybe have a blank slate in the place of the anime image to indicate that no anime could be found.)</li>
  <li>Some anime do not have their licensors, producers, or genres available. Recommendation algorithm would not work with these anime since it relies on an anime's genre.</li>
  <li>When the anime panel gets displayed, the scroll pane on the page scrolls all the way back up. A fix could be to store the original scroll coordinates and set the pane back to it. Due to time constraints, this was not done.</li>
</ul>

<h2>NOTE</h2>
<ul>
  <li>The project must have the following libraries:</li>
  <ul>
    <li>jfreechart-1.5.1</li>
    <li>unirest-java:1.4.9 (see <a href="https://rapidapi.com/blog/how-to-use-an-api-with-java/">here</a> and scroll down a bit if not already added.)</li>
    <li>gson-2.8.5 (see <a href="https://github.com/google/gson">here</a>)</li>
  </ul>
</ul>