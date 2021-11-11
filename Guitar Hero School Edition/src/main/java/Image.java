import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by cg8200 on 11/11/2021.
 */
public class Image {
    BufferedImage image;
    int x;
    int y;
    int Height;
    int Width;
    boolean isPressed;

    public Image(String pathName,int x, int y, int width, int height,boolean isPressed) {
        try {
            image = ImageIO.read(new File(pathName));

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.x = x;
        this.y = y;
        Height = height;
        Width = width;
        this.isPressed = isPressed;
    }
}
