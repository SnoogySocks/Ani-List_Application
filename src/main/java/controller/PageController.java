package controller;

import model.AniList;
import view.AnimePanel;

import javax.swing.event.MouseInputAdapter;
import java.awt.event.ActionListener;

public abstract class PageController extends MouseInputAdapter implements ActionListener {

    private AnimePanel animePanel;
    
    public PageController () {
    }
    
    public abstract void setUpListeners ();
    
}

