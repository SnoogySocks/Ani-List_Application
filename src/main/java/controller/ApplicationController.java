package controller;

import view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static controller.SerializationController.ANI_LIST_FILE;

/**
 * Initializes the pages and page controllers.
 * Also switches the screen
 */
public class ApplicationController extends WindowAdapter implements ActionListener {
    
    private static final MainFrame frame = new MainFrame();
    
    private int currentPagePtr;     // Point to the current page
    private final Page[] pages;
    private final PageController[] pageControllers;
    
    public ApplicationController () {
    
        // Initialize the jikan controller
        JikanController.init();
        
        // Initialize the pages
        pages = new Page[3];
        pages[0] = new HomePage();
        pages[1] = new AniListPage();
        pages[2] = new RecommendPage();
    
        // Initialize each page's pageComboBox
        for (Page page1 : pages) {
            for (Page page2 : pages) {
                page1.getTitlePanel().getPageComboBox().addItem(page2);
            }
        }
    
        // Initialize the page controllers
        pageControllers = new PageController[3];
        pageControllers[0] = new HomePageController((HomePage) pages[0]);
        pageControllers[1] = new AniListPageController((AniListPage) pages[1]);
        pageControllers[2] = new RecommendPageController((RecommendPage) pages[2]);
        
        setUpListeners();
        
        // Set the current page to the home page
        switchPages();
        frame.setVisible(true);
    
    }
    
    public static MainFrame getFrame () {
        return frame;
    }
    
    public void setUpListeners () {
    
        frame.addWindowListener(this);
    
        for (Page page : pages) {
            page.getTitlePanel().getPageComboBox().addActionListener(this);
        }
        for (PageController guiController: pageControllers) {
            guiController.setUpListeners();
        }
        
    }
    
    public Page getCurrentPage () {
        return pages[currentPagePtr];
    }
    
    /**
     * Switch the page according to the page combo box
     */
    public void switchPages () {
        
        // Get the new selected page
        currentPagePtr = getCurrentPage().getTitlePanel().getPageComboBox().getSelectedIndex();
        Page selectedPage = getCurrentPage().getTitlePanel().getPageComboBox().getItemAt(currentPagePtr);
        selectedPage.getTitlePanel().getPageComboBox().setSelectedItem(selectedPage);
        
        // Generate the Ani-List if the page is the AniListPage
        if (selectedPage==pages[1]) {
            AniListPageController aniListPageController = (AniListPageController) pageControllers[1];
            aniListPageController.generateAniList();
        }
        
        // Add the new page to the frame
        Container contentPane = frame.getContentPane();
        contentPane.removeAll();
        contentPane.add(selectedPage.getScrollPane());
        contentPane.revalidate();
        contentPane.repaint();
        
    }
    
    /**
     * Swtich the frames
     */
    @Override
    public void actionPerformed (ActionEvent e) {
        switchPages();
    }
    
    /**
     * Save the user's ani-list when the window closes
     */
    @Override
    public void windowClosing (WindowEvent e) {
    
        if (JOptionPane.showConfirmDialog(
                frame,
                "Are you sure you want to exit the program?", "Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
    
            JOptionPane.showMessageDialog(
                    frame,
                    "Saving Ani-List...", "Info",
                    JOptionPane.INFORMATION_MESSAGE
            );
            
            SerializationController.serialize(ANI_LIST_FILE, Page.getAniList());
            System.exit(0);
            
        }
    
    }
    
    /**
     * Load the user's ani-list when the window opens
     */
    @Override
    public void windowOpened (WindowEvent e) {
        Page.getAniList().setAniList(SerializationController.deserialize(ANI_LIST_FILE));
    }
    
}
