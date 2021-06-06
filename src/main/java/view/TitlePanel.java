package view;

import javax.swing.*;
import java.awt.*;

import static view.Page.*;

public class TitlePanel extends JPanel {
    
    public static final int WIDTH = Page.WIDTH;
    public static final int HEIGHT = Page.HEIGHT/2-PADDING_Y*2;
    
    // GUI Constants
    public static final Color BACKGROUND_COLOUR = new Color(77, 127, 161);
    
    public static final Font TITLE_FONT = new Font("Ubuntu", Font.BOLD, 120);
    public static final Font HEADER_FONT = new Font("Ubuntu", Font.BOLD, 45);
    
    private final JLabel titleLabel;
    private final JLabel titleCaptionLabel;
    private final JLabel pageLabel;
    
    private final JComboBox<Page> pageComboBox;
    private final FilterBar filterBar;
    
    public TitlePanel (boolean shouldAddGenreParameter) {
    
        setLayout(null);
        setBounds(0, 0, WIDTH, HEIGHT);
        setBackground(BACKGROUND_COLOUR);
        
        titleLabel = new JLabel("Ani-List");
        titleLabel.setBounds(PADDING*3, PADDING_Y+PADDING, Page.WIDTH/2-PADDING*11, PADDING_Y*2+PADDING);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(Page.BACKGROUND_COLOUR);
        add(titleLabel);
        
        titleCaptionLabel = new JLabel("The analyst for ani-lists");
        titleCaptionLabel.setBounds(
                Page.getRightX(titleLabel)+PADDING,
                Page.getBottomY(titleLabel)-PADDING,
                PADDING*11, PADDING);
        titleCaptionLabel.setFont(DIALOGUE_FONT);
        titleCaptionLabel.setForeground(Page.BACKGROUND_COLOUR);
        add(titleCaptionLabel);
        
        pageComboBox = new JComboBox<>();
        pageComboBox.setBounds(
                PADDING*3, Page.getBottomY(titleLabel)+PADDING,
                PADDING*14, TitlePanel.HEIGHT/4
        );
        pageComboBox.setFont(HEADER_FONT);
        pageComboBox.setBackground(USER_INPUT_COLOUR);
        pageComboBox.setForeground(TEXT_COLOUR);
        pageComboBox.setBorder(null);
        add(pageComboBox);
        
        pageLabel = new JLabel("Page");
        pageLabel.setBounds(
                Page.getRightX(pageComboBox)+PADDING, pageComboBox.getY(),
                PADDING*8, pageComboBox.getHeight()
        );
        pageLabel.setFont(HEADER_FONT);
        pageLabel.setForeground(Page.BACKGROUND_COLOUR);
        add(pageLabel);
        
        int x = Page.WIDTH/2-PADDING*6;
        filterBar = new FilterBar(shouldAddGenreParameter);
        filterBar.setBounds(x, pageComboBox.getY(), Page.WIDTH-x-PADDING*6, pageComboBox.getHeight());
        add(filterBar);
    
    }
    
    public JComboBox<Page> getPageComboBox () {
        return pageComboBox;
    }
    
    public FilterBar getFilterBar () {
        return filterBar;
    }
    
}
