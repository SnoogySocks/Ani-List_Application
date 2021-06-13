package view;

import model.Anime;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Class to visualize an anime with its picture as a JLabel
 */
public class AnimeImage extends JLabel {
    
    // Dimension constants
    public static final double ANIME_PANEL_SIZE = 0.90;
    public static final double LARGE_SIZE = 0.85;
    public static final double SMALL_SIZE = 0.35;
    public static final double MEDIUM_SIZE = 0.6;
    
    public static final int IMAGE_WIDTH_RATIO = 225;
    public static final int IMAGE_HEIGHT_RATIO = 318;
    
    // GUI
    private double size;
    private Image originalImage;
    private ImageIcon displayedImage;
    
    // The anime that will be displayed
    private Anime anime;
    
    // Default constructor
    public AnimeImage () {
    }
    
    public AnimeImage (Anime anime, double size) {
        setAnimeImage(anime, size);
    }
    
    /**
     * Constructor as a method
     * @param anime = the anime
     * @param size = the size of the image
     */
    public void setAnimeImage (Anime anime, double size) {
        
        // Initialize and set the displayed image as the JLabel's icon
        displayedImage = new ImageIcon();
        setIcon(displayedImage);
        
        this.size = size;
        setAnime(anime);
        
    }
    
    /**
     * Set the image according to the anime
     * @param anime = the anime
     */
    public void setAnime (Anime anime) {
        
        this.anime = anime;
        
        // Extract from online the image with the anime's image url
        try {
            originalImage = new ImageIcon(ImageIO.read(new URL(anime.getImageURL()))).getImage();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }
        setImageSize();
    
    }
    
    /**
     * Adjust the size of the JLabel
     */
    private void setImageSize () {
        
        // Adjust the size of the displayedImage
        displayedImage.setImage(originalImage.getScaledInstance(
                getScaledWidth(size),
                getScaledHeight(size),
                Image.SCALE_SMOOTH
        ));
        
        // Adjust the size ofh te label and remove the ugly border
        setSize(displayedImage.getIconWidth(), displayedImage.getIconHeight());
        setBorder(null);
        
    }
    
    public Anime getAnime () {
        return anime;
    }
    
    public ImageIcon getDisplayedImage () {
        return displayedImage;
    }
    
    /**
     * @param size = the size of the label
     * @return the calculated width of the label given the size
     */
    public static int getScaledWidth (double size) {
        return (int) (size*IMAGE_WIDTH_RATIO);
    }
    
    /**
     * @param size = the size of the label
     * @return the calculated height of the label given the size
     */
    public static int getScaledHeight (double size) {
        return (int) (size*IMAGE_HEIGHT_RATIO);
    }
    
}
