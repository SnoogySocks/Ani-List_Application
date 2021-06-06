package view;

import controller.JikanController;

import javax.swing.*;

public class MainFrame extends JFrame {
    
    public static final int WIDTH = 1366;
    public static final int HEIGHT = 768;
    
    private final Page[] pages;
    
    public MainFrame (JikanController jikanController) {
        
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        
        pages = new Page[3];
        pages[0] = new HomePage(jikanController);
        pages[1] = new AniListPage();
        pages[2] = new RecommendationPage();
        
        // Initialize each page's pageComboBox
        for (Page page1 : pages) {
            for (Page page2 : pages) {
                page1.getTitlePanel().getPageComboBox().addItem(page2);
            }
        }
        
        // Set the current page to the home page
        switchPages(pages[0]);
        
    }
    
    public Page getPage (int index) {
        return pages[index];
    }
    
    public void switchPages (Page page) {
        
        getContentPane().removeAll();
        getContentPane().repaint();
        page.getTitlePanel().getPageComboBox().setSelectedItem(page);
        add(page.getScrollPane());
    }
    
}
