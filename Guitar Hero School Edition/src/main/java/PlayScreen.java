import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
    Fire GreenFire;
    Fire RedFire;
    Fire YellowFire;
    Fire BlueFire;
    Fire OrangeFire;

    Image GreenNoteGlow;
    Image RedNoteGlow;
    Image YellowNoteGlow;
    Image BlueNoteGlow;
    Image OrangeNoteGlow;
    NoteSet lastNoteSetPlayed;

    int noteStreak;
    public PlayScreen() {
        lastNoteSetPlayed = new NoteSet(null, null,null,null,null);
            GreenFretIdle = new Image("src/main/resources/GreenFret.png",710, 880, 80, 80,false);
            RedFretIdle = new Image("src/main/resources/RedFret.png",810, 880, 80, 80,false);
            YellowFretIdle = new Image("src/main/resources/YellowFret.png",910, 880, 80, 80,false );
            BlueFretIdle = new Image("src/main/resources/BlueFret.png",1010, 880, 80, 80,false);
            OrangeFretIdle = new Image("src/main/resources/OrangeFret.png",1110, 880, 80, 80,false);

            GreenFretPlayed = new Image("src/main/resources/GreenFretPlayed.png",710, 880, 80, 80,true);
            GreenFret = GreenFretIdle;
            RedFretPlayed = new Image("src/main/resources/RedFretPlayed.png",810, 880, 80, 80,true);
            RedFret = RedFretIdle;
            YellowFretPlayed = new Image("src/main/resources/YellowFretPlayed.png",910, 880, 80, 80 ,true);
            YellowFret = YellowFretIdle;
            BlueFretPlayed =  new Image("src/main/resources/BlueFretPlayed.png",1010, 880, 80, 80,true);
            BlueFret = BlueFretIdle;
            OrangeFretPlayed =  new Image("src/main/resources/OrangeFretPlayed.png",1110, 880, 80, 80,true);
            OrangeFret = OrangeFretIdle;
            HitBox = new Image("src/main/resources/Hitbox.png",650, 850, 600, 150,false);

            GreenFire = new Fire("src/main/resources/fire.png",710, 880, 80, 80,false);
            RedFire = new Fire("src/main/resources/fire.png",810, 880, 80, 80,false);
            YellowFire = new Fire("src/main/resources/fire.png",910, 880, 80, 80,false);
            BlueFire = new Fire("src/main/resources/fire.png",1010, 880, 80, 80,false);
            OrangeFire = new Fire("src/main/resources/fire.png",1110, 880, 80, 80,false);

        GreenNoteGlow = new Image("src/main/resources/GreenNoteGlow.png",710, 880, 80, 80,false);
        RedNoteGlow = new Image("src/main/resources/RedNoteGlow.png",810, 880, 80, 80,false);
        YellowNoteGlow = new Image("src/main/resources/YellowNoteGlow.png",910, 880, 80, 80,false);
        BlueNoteGlow = new Image("src/main/resources/BlueNoteGlow.png",1010, 880, 80, 80,false);
        OrangeNoteGlow = new Image("src/main/resources/OrangeNoteGlow.png",1110, 880, 80, 80,false);


    }

    public static void loadSong(String pathname) {
        File f = new File(pathname);
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            while (br.ready()) {
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
        GreenFire.Draw(g);
        RedFire.Draw(g);
        YellowFire.Draw(g);
        BlueFire.Draw(g);
        OrangeFire.Draw(g);
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
            Note orangeNote=null;
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
                orangeNote=new Note(currentLine.Orange, Note.NoteColor.Orange);
            }
            NoteSetList.add(new NoteSet(greenNote,redNote,yellowNote,blueNote,orangeNote));

            frameCount = 0;
            if (linesList.size() > 0) {
                currentLine = linesList.remove();
            } else {
                NoteSetList.remove(0);
                GreenFret = GreenFretIdle;
                RedFret = RedFretIdle;
                YellowFret = YellowFretIdle;
                BlueFret = BlueFretIdle;
                OrangeFret = OrangeFretIdle;
                Game.playIsActive = false;
                Game.songSelectIsActive = true;
                noteStreak = 0;
            }

        }
        for (int i = 0; i < NoteSetList.size(); i++) {
            NoteSetList.get(i).Update(HitBox.y+HitBox.Height);
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
          try {
              if (aNoteOnFret()) {
                  if (CorrectFrets(false)) {

                      System.out.println(noteStreak);
                      if (GreenFret.isPressed) {
                          GreenFire.DisplayFire();
                      }
                      if (RedFret.isPressed) {
                          RedFire.DisplayFire();
                      }
                      if (YellowFret.isPressed) {
                          YellowFire.DisplayFire();
                      }
                      if (BlueFret.isPressed) {
                          BlueFire.DisplayFire();
                      }
                      if (OrangeFret.isPressed) {
                          OrangeFire.DisplayFire();
                      }
                      NotePlayed();
                  } else {
                      NoteMissed();
                  }
              } else {
                  NoteMissed();
              }
          } catch (IndexOutOfBoundsException ex){
              NoteMissed();
          }
        }
        else if (noteStreak >= 1) {
            if (aNoteOnFret()) {
                if (CorrectFrets(true)) {
                    boolean isWhite = false;
                    if (GreenFret.isPressed) {
                        if (NoteSetList.get(0).greenNote != null && NoteSetList.get(0).greenNote.noteType == Line.NoteType.white) {
                            isWhite = true;
                            GreenFire.DisplayFire();
                        }
                    } else if (RedFret.isPressed) {
                        if (NoteSetList.get(0).redNote != null && NoteSetList.get(0).redNote.noteType == Line.NoteType.white) {
                            isWhite = true;
                            RedFire.DisplayFire();
                        }
                    }
                    if (YellowFret.isPressed) {
                        if (NoteSetList.get(0).yellowNote != null && NoteSetList.get(0).yellowNote.noteType == Line.NoteType.white) {
                            isWhite = true;
                            YellowFire.DisplayFire();
                        }
                    }
                    if (BlueFret.isPressed) {
                        if (NoteSetList.get(0).blueNote != null && NoteSetList.get(0).blueNote.noteType == Line.NoteType.white) {
                            isWhite = true;
                            BlueFire.DisplayFire();
                        }
                    }
                    if (OrangeFret.isPressed) {
                        if (NoteSetList.get(0).orangeNote != null && NoteSetList.get(0).orangeNote.noteType == Line.NoteType.white) {
                            isWhite = true;
                            OrangeFire.DisplayFire();
                        }
                    }
                    if (isWhite) {
                        NotePlayed();
                    }
                }
            }
        }


    }
    public  void NoteMissed(){
        System.out.println("AWWW MAAAAN");
        noteStreak = 0;
        lastNoteSetPlayed = new NoteSet(null, null,null,null,null);
    }
    public void NotePlayed(){
        lastNoteSetPlayed = NoteSetList.get(0);
        NoteSetList.get(0).NoteIsActive = false;
        noteStreak = noteStreak + 1;
    }
    public boolean CorrectFrets(boolean includeLastNote){
        boolean GreenNoteExpected = NoteSetList.get(0).greenNote != null || (includeLastNote && lastNoteSetPlayed.greenNote != null);
        boolean RedNoteExpected = NoteSetList.get(0).redNote != null|| (includeLastNote && lastNoteSetPlayed.redNote != null);;
        boolean YellowNoteExpected = NoteSetList.get(0).yellowNote != null|| (includeLastNote && lastNoteSetPlayed.yellowNote != null);;
        boolean BlueNoteExpected = NoteSetList.get(0).blueNote != null|| (includeLastNote && lastNoteSetPlayed.blueNote != null);;
        boolean OrangeNoteExpected = NoteSetList.get(0).orangeNote != null|| (includeLastNote && lastNoteSetPlayed.orangeNote != null);;

        if (GreenNoteExpected == GreenFret.isPressed
                && RedNoteExpected == RedFret.isPressed
                && YellowNoteExpected == YellowFret.isPressed
                && BlueNoteExpected == BlueFret.isPressed
                && OrangeNoteExpected == OrangeFret.isPressed){
            return true;
        }
        return false;
    }
    public boolean aNoteOnFret(){
       return  (NoteSetList.get(0).greenNote != null && NoteSetList.get(0).greenNote.isOnFret(HitBox.y, HitBox.y + HitBox.Height))
                || (NoteSetList.get(0).redNote != null && NoteSetList.get(0).redNote.isOnFret(HitBox.y, HitBox.y + HitBox.Height))
                || (NoteSetList.get(0).yellowNote != null && NoteSetList.get(0).yellowNote.isOnFret(HitBox.y, HitBox.y + HitBox.Height))
                || (NoteSetList.get(0).blueNote != null && NoteSetList.get(0).blueNote.isOnFret(HitBox.y, HitBox.y + HitBox.Height))
                || (NoteSetList.get(0).orangeNote != null && NoteSetList.get(0).orangeNote.isOnFret(HitBox.y, HitBox.y + HitBox.Height));
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
