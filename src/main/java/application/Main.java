package application;

import controller.JikanController;
import model.Anime;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {
    
    public static void main (String[] args) {
        
        JikanController jikanController = new JikanController();
        MainFrame frame = new MainFrame(jikanController);
        frame.setVisible(true);
        
    }
    
}
