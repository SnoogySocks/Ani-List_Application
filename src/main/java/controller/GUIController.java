package controller;

import javax.swing.event.MouseInputAdapter;

public abstract class GUIController extends MouseInputAdapter {

    private JikanController jikan;
    
    public GUIController (JikanController jikan) {
        this.jikan = jikan;
    }
    
    public JikanController getJikan () {
        return jikan;
    }
    
    public abstract void setUpListeners ();
    
}

