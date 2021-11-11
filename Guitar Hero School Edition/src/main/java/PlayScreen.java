import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by cg8200 on 10/22/2021.
 */
public class PlayScreen {
    static Line currentLine;
    int frameCount = 0;
    static LinkedList<Line> linesList = new LinkedList<>();
    static LinkedList<NoteSet> NoteSetList = new LinkedList<>();
    Image GreenFret;
    Image RedFret;
    Image YellowFret;
    Image BlueFret;
    Image OrangeFret;

    Image GreenFretPlayed;
    Image GreenFretIdle;
    Image RedFretPlayed;
    Image RedFretIdle;
    Image YellowFretPlayed;
    Image YellowFretIdle;
    Image BlueFretPlayed;
    Image BlueFretIdle;
    Image OrangeFretPlayed;
    Image OrangeFretIdle;
    Image HitBox;

    public PlayScreen() {

            GreenFretIdle = new Image("src/main/resources/GreenFret.png",250, 590, 600, 600,false);
            RedFretIdle = new Image("src/main/resources/RedFret.png",450, 590, 600, 600,false);
            YellowFretIdle = new Image("src/main/resources/YellowFret.png",650, 600, 600, 600,false );
            BlueFretIdle = new Image("src/main/resources/BlueFret.png",850, 600, 600, 600,false);
            OrangeFretIdle = new Image("src/main/resources/OrangeFret.png",1050, 600, 600, 600,false);

            GreenFretPlayed = new Image("src/main/resources/GreenFretPlayed.png",245, 585, 600, 600,true);
            GreenFret = GreenFretIdle;
            RedFretPlayed = new Image("src/main/resources/RedFretPlayed.png",430, 580, 600, 600,true);
            RedFret = RedFretIdle;
            YellowFretPlayed = new Image("src/main/resources/YellowFretPlayed.png",640, 615, 600, 600 ,true);
            YellowFret = YellowFretIdle;
            BlueFretPlayed =  new Image("src/main/resources/BlueFretPlayed.png",850, 600, 600, 600,true);
            BlueFret = BlueFretIdle;
            OrangeFretPlayed =  new Image("src/main/resources/OrangeFretPlayed.png",1050, 580, 600, 600,true);
            OrangeFret = OrangeFretIdle;
            HitBox = new Image("src/main/resources/Hitbox.png",215, 670, 1500, 400,false);

    }

    public static void loadSong(String pathname) {
        File f = new File(pathname);
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            while (br.ready()) {
                //fixme  - logic to read from file
                String lineOfFile = br.readLine();
                String[] parsedline = lineOfFile.split(",");
                if (parsedline.length != 6) {
                    System.out.println("line does not have delay and 5 notes" + lineOfFile);
                } else {
                    try {
                        int delay = Integer.parseInt(parsedline[0]);
                        Line.NoteType Green = makeType(Integer.parseInt(parsedline[1]));
                        Line.NoteType Red = makeType(Integer.parseInt(parsedline[2]));
                        Line.NoteType Yellow = makeType(Integer.parseInt(parsedline[3]));
                        Line.NoteType Blue = makeType(Integer.parseInt(parsedline[4]));
                        Line.NoteType Orange = makeType(Integer.parseInt(parsedline[5]));
                        linesList.add(new Line(delay, Green, Red, Yellow, Blue, Orange));
                    } catch (NumberFormatException e) {
                        System.out.println("line has non number ya goof" + lineOfFile);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        if (linesList.size() > 0) {
            currentLine = linesList.remove();
        }
    }

    public static Line.NoteType makeType(int noteType) {
        if (noteType == 0) {
            return Line.NoteType.none;
        } else if (noteType == 1) {
            return Line.NoteType.regular;
        } else if (noteType == 2) {
            return Line.NoteType.star;
        } else if (noteType == 3) {
            return Line.NoteType.regularExtended;
        } else if (noteType == 4) {
            return Line.NoteType.starExtended;
        } else if (noteType == 5) {
            return Line.NoteType.white;
        } else if (noteType == 6) {
            return Line.NoteType.whiteExtended;
        } else if (noteType == 7) {
            return Line.NoteType.whiteStarExtended;
        } else {
            return Line.NoteType.BAD;
        }
    }

    public void draw(Graphics g) {
        g.drawImage(HitBox.image, HitBox.x, HitBox.y, HitBox.Width, HitBox.Height, null);
        g.drawImage(GreenFret.image,GreenFret.x, GreenFret.y, GreenFret.Width, GreenFret.Height, null);
        g.drawImage(RedFret.image, RedFret.x, RedFret.y, RedFret.Width, RedFret.Height, null);
        g.drawImage(YellowFret.image, YellowFret.x, YellowFret.y, YellowFret.Width, YellowFret.Height, null);
        g.drawImage(BlueFret.image, BlueFret.x, BlueFret.y, BlueFret.Width, BlueFret.Height, null);
        g.drawImage(OrangeFret.image, OrangeFret.x, OrangeFret.y, OrangeFret.Width, OrangeFret.Height, null);
        for (int i = 0; i < NoteSetList.size(); i++) {
            NoteSetList.get(i).Draw(g);
        }

    }


    public void update() {
        if (frameCount < currentLine.delay) {
            frameCount++;
        } else {
           // System.out.println(currentLine.Green + ", " + currentLine.Red + ", " + currentLine.Yellow + ", " + currentLine.Blue + ", " + currentLine.Orange + ", ");
            Note greenNote=null;
            Note redNote=null;
            Note yellowNote=null;
            Note blueNote=null;
            Note orenageNote=null;
            if (currentLine.Green != Line.NoteType.none) {
                greenNote=new Note(currentLine.Green, Note.NoteColor.Green);
            }
            if (currentLine.Red != Line.NoteType.none) {
                redNote=new Note(currentLine.Red, Note.NoteColor.Red);
            }
            if (currentLine.Yellow != Line.NoteType.none) {
                yellowNote=new Note(currentLine.Yellow, Note.NoteColor.Yellow);
            }
            if (currentLine.Blue != Line.NoteType.none) {
                blueNote=new Note(currentLine.Blue, Note.NoteColor.Blue);
            }
            if (currentLine.Orange != Line.NoteType.none) {
                orenageNote=new Note(currentLine.Orange, Note.NoteColor.Orange);
            }


            frameCount = 0;
            if (linesList.size() > 0) {
                currentLine = linesList.remove();
            } else {
                Game.playIsActive = false;
                Game.songSelectIsActive = true;
            }

        }
        for (int i = 0; i < NoteSetList.size(); i++) {
            NoteSetList.get(i).Update();
        }
        for (int i = 0; i < NoteSetList.size(); i++) {
            if (!NoteSetList.get(i).NoteIsActive) {
                NoteSetList.remove(i);
                i--;
            }
        }
    }

    public void ButtonPressed(Game.ButtonCode buttonCode) {
        if (buttonCode == Game.ButtonCode.GREEN) {
            GreenFret = GreenFretPlayed;
        }
        if (buttonCode == Game.ButtonCode.RED) {
            RedFret = RedFretPlayed;
        }
        if (buttonCode == Game.ButtonCode.YELLOW) {
            YellowFret = YellowFretPlayed;
        }
        if (buttonCode == Game.ButtonCode.BLUE) {
            BlueFret = BlueFretPlayed;
        }
        if (buttonCode == Game.ButtonCode.ORANGE) {
            OrangeFret = OrangeFretPlayed;
        }
        if ((Game.ButtonCode.STRUM_UP == buttonCode || Game.ButtonCode.STRUM_DOWN == buttonCode)) {
            boolean GreenNoteExpected = false;
            boolean RedNoteExpected = false;
            boolean YellowNoteExpected = false;
            boolean BlueNoteExpected = false;
            boolean OrangeNoteExpected = false;
            for (int i = 0; i < NoteList.size(); i++) {
                if (NoteList.get(i).isOnFret(HitBox.y, HitBox.y + HitBox.Height)) {
                    if (NoteList.get(i).noteColor == Note.NoteColor.Green) {
                        GreenNoteExpected = true;
                    } else if (NoteList.get(i).noteColor == Note.NoteColor.Red) {
                        RedNoteExpected = true;
                    } else if (NoteList.get(i).noteColor == Note.NoteColor.Yellow) {
                        YellowNoteExpected = true;
                    } else if (NoteList.get(i).noteColor == Note.NoteColor.Blue) {
                        BlueNoteExpected = true;
                    } else if (NoteList.get(i).noteColor == Note.NoteColor.Orange) {
                        OrangeNoteExpected = true;
                    }
                }
            }
            if (GreenNoteExpected == GreenFret.isPressed
                    && RedNoteExpected == RedFret.isPressed
                    && YellowNoteExpected == YellowFret.isPressed
                    && BlueNoteExpected == BlueFret.isPressed
                    && OrangeNoteExpected == OrangeFret.isPressed) {

                System.out.println("YAY, WOOHOO!!!");
                for (int i = 0; i < NoteList.size(); i++) {
                    if (NoteList.get(i).isOnFret(HitBox.y, HitBox.y + HitBox.Height)) {
                        NoteList.get(i).NoteIsActive=false;
                    }
                }
            } else {
                System.out.println("AWWW MAAAAN");
            }
        }
    }

    public void FretReleased(Game.ButtonCode buttonCode) {

        if (buttonCode == Game.ButtonCode.GREEN) {
            GreenFret = GreenFretIdle;
        }
        if (buttonCode == Game.ButtonCode.RED) {
            RedFret = RedFretIdle;
        }
        if (buttonCode == Game.ButtonCode.YELLOW) {
            YellowFret = YellowFretIdle;
        }
        if (buttonCode == Game.ButtonCode.BLUE) {
            BlueFret = BlueFretIdle;
        }
        if (buttonCode == Game.ButtonCode.ORANGE) {
            OrangeFret = OrangeFretIdle;
        }

    }



}
