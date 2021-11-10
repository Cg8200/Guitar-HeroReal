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
    static LinkedList<Note> NoteList = new LinkedList<>();
    BufferedImage GreenFret;
    BufferedImage RedFret;
    BufferedImage YellowFret;
    BufferedImage BlueFret;
    BufferedImage OrangeFret;

    BufferedImage GreenFretPlayed;
    BufferedImage GreenFretIdle;
    BufferedImage RedFretPlayed;
    BufferedImage RedFretIdle;
    BufferedImage YellowFretPlayed;
    BufferedImage YellowFretIdle;
    BufferedImage BlueFretPlayed;
    BufferedImage BlueFretIdle;
    BufferedImage OrangeFretPlayed;
    BufferedImage OrangeFretIdle;
    BufferedImage HitBox;

    public PlayScreen() {
        try {
            GreenFretIdle = ImageIO.read(new File("src/main/resources/GreenFret.png"));
            RedFretIdle = ImageIO.read(new File("src/main/resources/RedFret.png"));
            YellowFretIdle = ImageIO.read(new File("src/main/resources/YellowFret.png"));
            BlueFretIdle = ImageIO.read(new File("src/main/resources/BlueFret.png"));
            OrangeFretIdle = ImageIO.read(new File("src/main/resources/OrangeFret.png"));

            GreenFretPlayed = ImageIO.read(new File("src/main/resources/GreenFretPlayed.png"));
            GreenFret = GreenFretIdle;
            RedFretPlayed = ImageIO.read(new File("src/main/resources/RedFretPlayed.png"));
            RedFret = RedFretIdle;
            YellowFretPlayed = ImageIO.read(new File("src/main/resources/YellowFretPlayed.png"));
            YellowFret = YellowFretIdle;
            BlueFretPlayed = ImageIO.read(new File("src/main/resources/BlueFretPlayed.png"));
            BlueFret = BlueFretIdle;
            OrangeFretPlayed = ImageIO.read(new File("src/main/resources/OrangeFretPlayed.png"));
            OrangeFret = OrangeFretIdle;
            HitBox = ImageIO.read(new File("src/main/resources/Hitbox.png"));


        } catch (IOException e) {
            e.printStackTrace();
        }
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
        g.drawImage(HitBox, 0, 600, 2000, 550, null);
        g.drawImage(GreenFret, 250, 600, 600, 600, null);
        g.drawImage(RedFret, 450, 600, 600, 600, null);
        g.drawImage(YellowFret, 650, 600, 600, 600, null);
        g.drawImage(BlueFret, 850, 600, 600, 600, null);
        g.drawImage(OrangeFret, 1050, 600, 600, 600, null);
        for (int i = 0; i < NoteList.size(); i++) {
            NoteList.get(i).Draw(g);
        }

    }


    public void update() {
        if (frameCount < currentLine.delay) {
            frameCount++;
        } else {
            System.out.println(currentLine.Green + ", " + currentLine.Red + ", " + currentLine.Yellow + ", " + currentLine.Blue + ", " + currentLine.Orange + ", ");
            if (currentLine.Green != Line.NoteType.none) {
                NoteList.add(new Note(currentLine.Green, Note.NoteColor.Green));
            }
            if (currentLine.Red != Line.NoteType.none) {
                NoteList.add(new Note(currentLine.Red, Note.NoteColor.Red));
            }
            if (currentLine.Yellow != Line.NoteType.none) {
                NoteList.add(new Note(currentLine.Yellow, Note.NoteColor.Yellow));
            }
            if (currentLine.Blue != Line.NoteType.none) {
                NoteList.add(new Note(currentLine.Blue, Note.NoteColor.Blue));
            }
            if (currentLine.Orange != Line.NoteType.none) {
                NoteList.add(new Note(currentLine.Orange, Note.NoteColor.Orange));
            }

            frameCount = 0;
            if (linesList.size() > 0) {
                currentLine = linesList.remove();
            } else {
                Game.playIsActive = false;
                Game.songSelectIsActive = true;
            }

        }
        for (int i = 0; i < NoteList.size(); i++) {
            NoteList.get(i).Update();
        }
        for (int i = 0; i < NoteList.size(); i++) {
            if (!NoteList.get(i).NoteIsActive) {
                NoteList.remove(i);
                i--;
            }
        }
    }

    public void FretPressed(Game.ButtonCode buttonCode) {
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
