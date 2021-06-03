package view;

import model.Anime;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class AnimeImage extends JLabel {
    
    // Dimension constants
    public static final double TRENDING_SIZE = 1.0;
    public static final double ANIME_PANEL_SIZE = 1.2;
    public static final double ANI_LIST_SIZE = 0.2;
    public static final double MINOR_SCROLL_PANEL_SIZE = 0.5;
    
    public static final int IMAGE_WIDTH_RATIO = 225;
    public static final int IMAGE_HEIGHT_RATIO = 318;
    
    // GUI
    private Image imageOG;
    private final ImageIcon displayedImage;
    
    // The anime that will be displayed
    private Anime anime;
    
    public AnimeImage (Anime anime) {
    
        setBounds(0, 0, 0, 0);
        displayedImage = new ImageIcon();
        setIcon(displayedImage);
        setAnime(anime);
        
    }
    
    public void setAnime (Anime anime) {
        
        this.anime = anime;
        try {
            imageOG = new ImageIcon(ImageIO.read(new URL(anime.getImageURL()))).getImage();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }
        setImageSize(TRENDING_SIZE);
    
    }
    
    public void setImageSize (double size) {
        
        displayedImage.setImage(imageOG.getScaledInstance(
                (int) (size*IMAGE_WIDTH_RATIO),
                (int) (size*IMAGE_HEIGHT_RATIO),
                Image.SCALE_SMOOTH
        ));
        setSize(displayedImage.getIconWidth(), displayedImage.getIconHeight());
        
    }
    
    public Anime getAnime () {
        return anime;
    }
    
}
