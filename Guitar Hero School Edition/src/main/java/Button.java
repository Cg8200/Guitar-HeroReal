import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by cg8200 on 10/22/2021.
 */
public class Button {

    public String btntxt;
    int btnX;
    int btnY;
    int btnHeight;
    int btnWidth;
    boolean isSelected;
    BufferedImage image = null;

    public Button(String btntxt, int btnX, int btnY, int btnHeight) {
        this.btntxt = btntxt;
        this.btnX = btnX;
        this.btnY = btnY-btnHeight;
        this.btnHeight = btnHeight;
        this.btnWidth = btntxt.length() * btnHeight * 67 / 100;
        this.isSelected = isSelected;
        try {
            image = ImageIO.read(new File("src/main/resources/SelectButton.png"));
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void Draw(Graphics g) {
        g.setColor(Color.green);
        if (isSelected) {
            g.drawImage(image,btnX, btnY, btnWidth, btnHeight, null);
        }

        Font myFont = new Font("TimesRoman", Font.PLAIN, btnHeight - 4);
        g.setFont(myFont);
        g.drawString(btntxt, btnX + btnHeight / 2, btnY - btnHeight / 5);

    }


}


