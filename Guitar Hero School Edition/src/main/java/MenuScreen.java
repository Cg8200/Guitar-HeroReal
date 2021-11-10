import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by cg8200 on 10/28/2021.
 */
public class MenuScreen {
    BufferedImage image = null;
    int activeButtonIndex;
    ArrayList<Button> buttons;
    int screenWidth;
    int screenHeight;

    public MenuScreen() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth =(int) screenSize.getWidth();
        screenHeight =(int) screenSize.getHeight();
        activeButtonIndex = 0;
        buttons = new ArrayList<>();

    }

    public void draw(Graphics g){
        // System.out.println(activeButtonIndex);
        g.drawImage(image, 0, 0, null);
        for(int i = 0; i<buttons.size();i=i+1 ){
            buttons.get(i).Draw(g);
        }
    }
    public void update(){

    }
    public void setActiveButton(){
        for(int i = 0; i<buttons.size();i=i+1 ){
            if(i == activeButtonIndex){
                buttons.get(i).isSelected=true;
            }else{
                buttons.get(i).isSelected=false;
            }
        }
    }
    public void changeSelected(boolean isUp){
        // System.out.println(activeButtonIndex);
        if (isUp){
            activeButtonIndex = activeButtonIndex - 1;
            if (activeButtonIndex<0){
                activeButtonIndex = buttons.size()-1;
            }
        }else{
            activeButtonIndex = activeButtonIndex + 1;
            if (activeButtonIndex>buttons.size()-1){
                activeButtonIndex = 0;
            }
        }
        setActiveButton();
    }

    public void Select(){
    }
}
