import java.awt.*;

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

    public Button(String btntxt, int btnX, int btnY, int btnHeight) {
        this.btntxt = btntxt;
        this.btnX = btnX;
        this.btnY = btnY;
        this.btnHeight = btnHeight;
        this.btnWidth = btntxt.length() * btnHeight * 67 / 100;
        this.isSelected = isSelected;
    }

    public void Draw(Graphics g) {
        g.setColor(Color.green);
        if (isSelected) {

            g.drawRect(btnX, btnY - btnHeight, btnWidth, btnHeight);
        }

        Font myFont = new Font("TimesRoman", Font.PLAIN, btnHeight - 4);
        g.setFont(myFont);
        g.drawString(btntxt, btnX + btnHeight / 2, btnY - btnHeight / 5);

    }


}


