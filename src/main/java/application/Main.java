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
    
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1366, 768);
        frame.setLayout(null);
    
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        panel.setLayout(null);
        frame.add(panel);
    
        panel.add(new AnimeScrollPanel(jikanController.getTrending(), AnimeImage.MEDIUM_SIZE));
    
        frame.setVisible(true);
//        ArrayList<Anime> anime = jikanController.getUpAndComing();
//        jikanController.setAnimePanel(anime.get(0));
//        System.out.println();
        
    }
    
}
