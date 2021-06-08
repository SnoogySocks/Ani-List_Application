package view;

import model.Anime;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class AnimeImage extends JButton {
    
    // Dimension constants
    public static final double ANIME_PANEL_SIZE = 0.90;
    public static final double LARGE_SIZE = 0.85;
    public static final double SMALL_SIZE = 0.2;
    public static final double MEDIUM_SIZE = 0.6;
    
    public static final int IMAGE_WIDTH_RATIO = 225;
    public static final int IMAGE_HEIGHT_RATIO = 318;
    
    // GUI
    private final double size;
    private Image imageOG;
    private final ImageIcon displayedImage;
    
    // The anime that will be displayed
    private Anime anime;
    
    public AnimeImage (Anime anime, double size) {
        
        displayedImage = new ImageIcon();
        setIcon(displayedImage);
    
        this.size = size;
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
        setImageSize();
    
    }
    
    private void setImageSize () {
        
        displayedImage.setImage(imageOG.getScaledInstance(
                getScaledWidth(size),
                getScaledHeight(size),
                Image.SCALE_SMOOTH
        ));
        setSize(displayedImage.getIconWidth(), displayedImage.getIconHeight());
        setBorder(null);
        
    }
    
    public Anime getAnime () {
        return anime;
    }
    
    public ImageIcon getDisplayedImage () {
        return displayedImage;
    }
    
    public static int getScaledWidth (double size) {
        return (int) (size*IMAGE_WIDTH_RATIO);
    }
    
    public static int getScaledHeight (double size) {
        return (int) (size*IMAGE_HEIGHT_RATIO);
    }
    
}
