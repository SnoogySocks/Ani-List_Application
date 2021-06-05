package application;

import controller.JikanController;
import model.Anime;

import java.util.ArrayList;

public class Main {
    
    public static void main (String[] args) {
    
//        JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(1366, 768);
//        frame.setLayout(null);
//
//        Anime anime = new Anime("48583",
//                "Shingeki no Kyojin: The Final Season Part 2",
//                "Second part of Shingeki no Kyojin: The Final Season.",
//                "8.1",
//                "yes",
//                "12",
//                new String[] { "Action" },
//                "https://cdn.myanimelist.net/images/anime/1079/109928.jpg"
//        );
//        AnimeImage image = new AnimeImage(anime);
//
//        JPanel panel = new JPanel();
//        panel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
//        panel.setLayout(null);
//        panel.add(image);
//        frame.add(panel);
//
//        frame.setVisible(true);
        JikanController jikanController = new JikanController();
        ArrayList<Anime> anime = jikanController.getUpAndComing();
        jikanController.setAnimePanel(anime.get(0));
        System.out.println();
        
    }
    
}
