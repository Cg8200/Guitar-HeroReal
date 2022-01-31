import java.awt.*;

/**
 * Created by cg8200 on 11/12/2021.
 */
public class Fire extends Image {
    private int Timer = 0;
    public Fire(String pathName,int x, int y, int width, int height,boolean isPressed) {
        super(pathName, x, y, width, height, isPressed);
    }

    public void DisplayFire(){
        Timer = 10;
        isFire = true;
    }
    public void Draw(Graphics g){
        Timer = Timer-1;
        if (Timer<0){
            Timer = 0;
            isFire = false;
        }
        if(isFire){
            g.drawImage(image, x, y, Width, Height, null);
        }
    }
}
