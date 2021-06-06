package application;

import controller.JikanController;
import model.Anime;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {
    
    public static void main (String[] args) {
    
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1366, 768);
        frame.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        panel.setLayout(null);
        frame.add(panel);
    
        panel.add(new TitlePanel(true));
        
        frame.setVisible(true);
//        JikanController jikanController = new JikanController();
//        ArrayList<Anime> anime = jikanController.getUpAndComing();
//        jikanController.setAnimePanel(anime.get(0));
//        System.out.println();
        
    }
    
}
