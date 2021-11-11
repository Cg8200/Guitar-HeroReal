import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Note {
    int noteX;
    int noteY;
    BufferedImage image;
    int Width;
    int Height;
    int Speed;

    NoteColor noteColor;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
  int ScreenHeight = (int) screenSize.getHeight();
    enum NoteColor {
        Green, Red, Yellow, Blue, Orange
    }
    public Note(Line.NoteType noteType,NoteColor noteColor){
        this.noteColor=noteColor;

        if(noteColor == NoteColor.Green){
            noteX = 295;
        }else if(noteColor == NoteColor.Red){
            noteX = 495;
        }else if(noteColor == NoteColor.Yellow){
            noteX = 695;
        }else if(noteColor == NoteColor.Blue){
            noteX = 895;
        }else if(noteColor == NoteColor.Orange){
            noteX = 1095;
        }
        noteY = -20;
        Width = 500;
        Height = 300;
        Speed = 20;
        try {
            if(noteColor == NoteColor.Green){
                if(noteType == Line.NoteType.regular) {
                    image = ImageIO.read(new File("src/main/resources/GreenNote.png"));
                }
            }else if(noteColor == NoteColor.Red){
                if(noteType == Line.NoteType.regular){
                image = ImageIO.read(new File("src/main/resources/RedNote.png"));}
            }else if(noteColor == NoteColor.Yellow){
                if(noteType == Line.NoteType.regular);{
                    image = ImageIO.read(new File("src/main/resources/YellowNote.png"));}
            }else if(noteColor == NoteColor.Blue){
                if(noteType == Line.NoteType.regular){
                image = ImageIO.read(new File("src/main/resources/BlueNote.png"));}
            }else if(noteColor == NoteColor.Orange){
                if(noteType == Line.NoteType.regular){
                image = ImageIO.read(new File("src/main/resources/OrangeNote.png"));}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void Update(){
        noteY=noteY+Speed;
    }
    public void Draw(Graphics g){
        g.drawImage(image, noteX, noteY,Width,Height, null);
    }

    public boolean isOnFret(int Y1,int Y2){
      if(noteY>=Y1&&noteY<=Y2){
          return true;
      }else if(noteY+Height>=Y1&&noteY+Height<=Y2){
        return true;
      }else{
          return false;
      }

    }
}
