package controller;

import view.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplicationController implements ActionListener {
    
    private JikanController jikanController;
    private MainFrame frame;
    
    public ApplicationController () {
    
        jikanController = new JikanController();
        frame = new MainFrame(jikanController);
        frame.setVisible(true);
        setUpListeners();
        
    }
    
    public void setUpListeners () {
        for (int i = 0; i<frame.getPageLength(); ++i) {
            frame.getPage(i).getTitlePanel().getPageComboBox().addActionListener(this);
        }
    }
    
    // Switch frames
    @Override
    public void actionPerformed (ActionEvent e) {
        frame.switchPages();
    }
    
}
