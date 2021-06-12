package controller;

import view.AnimePanel;
import view.MainFrame;
import view.Page;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public abstract class PageController extends MouseInputAdapter implements ActionListener {

    private final AnimePanel animePanel;
    private boolean isOnDropBox;
    private boolean isDragging;
    
    public PageController (AnimePanel animePanel) {
        this.animePanel = animePanel;
        isOnDropBox = false;
    }
    
    public void setUpListeners () {
        
        animePanel.getBackButton().addActionListener(this);
        animePanel.getDisplayedAnime().addMouseListener(this);
        animePanel.getDisplayedAnime().addMouseMotionListener(this);
        
    }
    
    /**
     * Set the anime to invisible from its item panel if it's been pressed on
     * @param e = the event
     */
    @Override
    public void mousePressed (MouseEvent e) {
        if (e.getSource()==animePanel.getDisplayedAnime()) {
            isDragging = true;
        }
    }
    
    /**
     * Make the anime from its item panel visible again if its been released.
     * If it's released on the drop box, then add the anime to the user's list
     * @param e = the event
     */
    @Override
    public void mouseReleased (MouseEvent e) {
    
        if (isOnDropBox && isDragging) {
            Page.getAniList().add(animePanel.getDisplayedAnime().getAnime());
            System.out.println("it got the good");
        }
        isDragging = false;
        animePanel.setDisplayedAnimeToOGLocation();
        
    }
    
    /**
     * Move the anime image with the mouse if the mouse is dragging the image
     * @param e = the event
     */
    @Override
    public void mouseDragged (MouseEvent e) {
        
        // If the mouse is dragging the image is dragged with it
        final MainFrame frame = ApplicationController.getFrame();
        Point mouseLocation = new Point(frame.getXOnFrame(e.getXOnScreen()), frame.getYOnFrame(e.getYOnScreen()));
        
        if (isDragging) {
            animePanel.getDisplayedAnime().setLocation(
                    mouseLocation.x-animePanel.getDisplayedAnime().getWidth()/2,
                    mouseLocation.y-animePanel.getDisplayedAnime().getHeight()/2
            );
        }
        
        JLabel dropBox = animePanel.getDropBox();
        
        // The mouse is on the drop box if it is within its coordinates
        isOnDropBox = dropBox.getX()<=mouseLocation.x && mouseLocation.x<=Page.getRightX(dropBox)
                && dropBox.getY()<=mouseLocation.y && mouseLocation.y<=Page.getBottomY(dropBox);
    
    }
    
}

