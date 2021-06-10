package controller;

import view.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplicationController implements ActionListener {
    
    private final JikanController jikan;
    private final MainFrame frame;
    
    private int currentPagePtr;
    private final Page[] pages;
    private final GUIController[] guiControllers;
    
    public ApplicationController () {
    
        jikan = new JikanController();
        frame = new MainFrame();
        
        pages = new Page[3];
        pages[0] = new HomePage(jikan);
        pages[1] = new AniListPage();
        pages[2] = new RecommendPage();
    
        // Initialize each page's pageComboBox
        for (Page page1 : pages) {
            for (Page page2 : pages) {
                page1.getTitlePanel().getPageComboBox().addItem(page2);
            }
        }
        
        guiControllers = new GUIController[4];
        guiControllers[0] = new HomePageController(jikan, (HomePage) pages[0]);
        guiControllers[1] = new AniListPageController(jikan, (AniListPage) pages[1]);
        guiControllers[2] = new RecommendPageController(jikan, (RecommendPage) pages[2]);
        guiControllers[3] = new AnimePanelController(jikan, new AnimePanel());
        
        setUpListeners();
        
        // Set the current page to the home page
        switchPages();
        frame.setVisible(true);
    
    }
    
    public void setUpListeners () {
        
        for (Page page : pages) {
            page.getTitlePanel().getPageComboBox().addActionListener(this);
        }
        for (GUIController guiController: guiControllers) {
            guiController.setUpListeners();
        }
        
    }
    
    public Page getCurrentPage () {
        return pages[currentPagePtr];
    }
    
    public void switchPages () {
        
        // Get the new selected page
        currentPagePtr = getCurrentPage().getTitlePanel().getPageComboBox().getSelectedIndex();
        Page selectedPage = getCurrentPage().getTitlePanel().getPageComboBox().getItemAt(currentPagePtr);
        selectedPage.getTitlePanel().getPageComboBox().setSelectedItem(selectedPage);
        
        Container contentPane = frame.getContentPane();
        contentPane.removeAll();
        contentPane.add(selectedPage.getScrollPane());
        contentPane.revalidate();
        contentPane.repaint();
        
    }
    
    // Switch frames
    @Override
    public void actionPerformed (ActionEvent e) {
        switchPages();
    }
    
}
