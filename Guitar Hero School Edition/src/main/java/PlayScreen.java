import java.awt.*;
import java.io.*;
import java.util.LinkedList;

/**
 * Created by cg8200 on 10/22/2021.
 */
public class PlayScreen {
    static Line currentLine;
    int frameCount = 0;
    static LinkedList<Line> linesList = new LinkedList<>();
    static LinkedList<NoteSet> NoteSetList = new LinkedList<>();
    static LinkedList<NoteSet> LongNoteSet = new LinkedList<>();
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
    static MP3player mp3Player;
    //Chart file stuff
    static int resolution = 192;
    double totalNSsinceNote = 0;
    static long framelengthNS = 0;



    int noteStreak;
    //region sets images
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
            HitBox = new Image("src/main/resources/Hitbox.png",650, 870, 600, 130,false);

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

    }//endregion




    //region draws images
    public void draw(Graphics g) {
        g.drawImage(HitBox.image, HitBox.x, HitBox.y, HitBox.Width, HitBox.Height, null);
        g.drawImage(GreenFret.image,GreenFret.x, GreenFret.y, GreenFret.Width, GreenFret.Height, null);
        g.drawImage(RedFret.image, RedFret.x, RedFret.y, RedFret.Width, RedFret.Height, null);
        g.drawImage(YellowFret.image, YellowFret.x, YellowFret.y, YellowFret.Width, YellowFret.Height, null);
        g.drawImage(BlueFret.image, BlueFret.x, BlueFret.y, BlueFret.Width, BlueFret.Height, null);
        g.drawImage(OrangeFret.image, OrangeFret.x, OrangeFret.y, OrangeFret.Width, OrangeFret.Height, null);
        for (int i = 0; i < LongNoteSet.size(); i++) {
            LongNoteSet.get(i).Draw(g);
        }
        for (int i = 0; i < NoteSetList.size(); i++) {
            NoteSetList.get(i).Draw(g);
        }

        GreenFire.Draw(g);
        RedFire.Draw(g);
        YellowFire.Draw(g);
        BlueFire.Draw(g);
        OrangeFire.Draw(g);


    } //endregion


    public void update() {
        long frameTimeNS = System.nanoTime();
        framelengthNS = frameTimeNS - Game.LastFrameTimeNS;
        Game.LastFrameTimeNS = frameTimeNS;
        totalNSsinceNote = totalNSsinceNote + framelengthNS;


        if (totalNSsinceNote > currentLine.delay) {
            totalNSsinceNote = totalNSsinceNote - currentLine.delay;
            // System.out.println(currentLine.Green + ", " + currentLine.Red + ", " + currentLine.Yellow + ", " + currentLine.Blue + ", " + currentLine.Orange + ", ");
            Note greenNote = null;
            Note redNote = null;
            Note yellowNote = null;
            Note blueNote = null;
            Note orangeNote = null;
            if (currentLine.Green != Line.NoteType.none) {
                greenNote = new Note(currentLine.Green, Note.NoteColor.Green,currentLine.greenLength);
            }
            if (currentLine.Red != Line.NoteType.none) {
                redNote = new Note(currentLine.Red, Note.NoteColor.Red,currentLine.redlength);
            }
            if (currentLine.Yellow != Line.NoteType.none) {
                yellowNote = new Note(currentLine.Yellow, Note.NoteColor.Yellow,currentLine.yellowLength);
            }
            if (currentLine.Blue != Line.NoteType.none) {
                blueNote = new Note(currentLine.Blue, Note.NoteColor.Blue,currentLine.blueLength);
            }
            if (currentLine.Orange != Line.NoteType.none) {
                orangeNote = new Note(currentLine.Orange, Note.NoteColor.Orange,currentLine.orangeLength);
            }
            NoteSetList.add(new NoteSet(greenNote, redNote, yellowNote, blueNote, orangeNote));

            frameCount = 0;
            if (linesList.size() > 0) {
                currentLine = linesList.remove();
                if(currentLine.greenLength != 0 || currentLine.redlength != 0 || currentLine.yellowLength != 0 || currentLine.blueLength != 0 || currentLine.orangeLength != 0){
                    totalNSsinceNote = totalNSsinceNote - currentLine.delay;
                    // System.out.println(currentLine.Green + ", " + currentLine.Red + ", " + currentLine.Yellow + ", " + currentLine.Blue + ", " + currentLine.Orange + ", ");
                     greenNote = null;
                     redNote = null;
                     yellowNote = null;
                     blueNote = null;
                     orangeNote = null;
                    if (currentLine.Green != Line.NoteType.none) {
                        greenNote = new Note(currentLine.Green, Note.NoteColor.Green,currentLine.greenLength);
                    }
                    if (currentLine.Red != Line.NoteType.none) {
                        redNote = new Note(currentLine.Red, Note.NoteColor.Red,currentLine.redlength);
                    }
                    if (currentLine.Yellow != Line.NoteType.none) {
                        yellowNote = new Note(currentLine.Yellow, Note.NoteColor.Yellow,currentLine.yellowLength);
                    }
                    if (currentLine.Blue != Line.NoteType.none) {
                        blueNote = new Note(currentLine.Blue, Note.NoteColor.Blue,currentLine.blueLength);
                    }
                    if (currentLine.Orange != Line.NoteType.none) {
                        orangeNote = new Note(currentLine.Orange, Note.NoteColor.Orange,currentLine.orangeLength);
                    }
                    LongNoteSet.add(new NoteSet(greenNote, redNote, yellowNote, blueNote, orangeNote));
                    currentLine = linesList.remove();
                }
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
            NoteSetList.get(i).Update(HitBox.y + HitBox.Height);
        }
        for (int i = 0; i < NoteSetList.size(); i++) {
            if (!NoteSetList.get(i).NoteIsActive) {
                NoteSetList.remove(i);
                i--;
            }
        }
        for (int i = 0; i < LongNoteSet.size(); i++) {
            LongNoteSet.get(i).Update(HitBox.y + HitBox.Height);
        }
        for (int i = 0; i < LongNoteSet.size(); i++) {
            if (!LongNoteSet.get(i).NoteIsActive) {
                LongNoteSet.remove(i);
                i--;
            }
        }
        //longnote ifmissed stuff
    /*    if(LongNoteSet.size()>0 && aNoteOnFret(LongNoteSet)) {
            if (LongNoteSet.getFirst().greenNote != null) {
                if ( GreenFret == GreenFretPlayed) {

                } else {
                    LongNoteSet.getFirst().greenNote.noteMissed();
                }
            }
            if (LongNoteSet.getFirst().redNote != null) {
                if ( RedFret == RedFretPlayed) {

                } else {
                    LongNoteSet.getFirst().redNote.noteMissed();
                }
            }
            if ( LongNoteSet.getFirst().yellowNote != null) {
                if (YellowFret == YellowFretPlayed) {

                } else {
                    LongNoteSet.getFirst().yellowNote.noteMissed();
                }
            }
            if (LongNoteSet.getFirst().blueNote != null) {
                if ( BlueFret == BlueFretPlayed) {

                } else {
                    LongNoteSet.getFirst().blueNote.noteMissed();
                }
            }
            if ( LongNoteSet.getFirst().orangeNote != null) {
                if (OrangeFret == OrangeFretPlayed) {

                } else {
                    LongNoteSet.getFirst().orangeNote.noteMissed();
                }
            }
        }*/


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
        //region button pressed notes on screen
        if(NoteSetList.size()>0) {
            if ((Game.ButtonCode.STRUM_UP == buttonCode || Game.ButtonCode.STRUM_DOWN == buttonCode)) {
                try {
                    if (aNoteOnFret(NoteSetList)) {
                        if (CorrectFrets(false)) {

                            System.out.println(noteStreak);
                            if ((GreenFret==GreenFretPlayed)) {
                                GreenFire.DisplayFire();
                            }
                            if (RedFret==RedFretPlayed) {
                                RedFire.DisplayFire();
                            }
                            if (YellowFret==YellowFretPlayed) {
                                YellowFire.DisplayFire();
                            }
                            if (BlueFret==BlueFretPlayed) {
                                BlueFire.DisplayFire();
                            }
                            if (OrangeFret==OrangeFretPlayed) {
                                OrangeFire.DisplayFire();
                            }
                            NotePlayed();
                        } else {
                            NoteMissed();
                        }
                    } else {
                        NoteMissed();
                    }
                } catch (IndexOutOfBoundsException ex) {
                    NoteMissed();
                }
            }
            //region tap notes
            else if (noteStreak >= 1) {
                if (aNoteOnFret(NoteSetList)) {
                    if (CorrectFrets(true)) {
                        boolean isWhite = false;
                        if ((GreenFret==GreenFretPlayed)) {
                            if (NoteSetList.get(0).greenNote != null && NoteSetList.get(0).greenNote.noteType == Line.NoteType.white) {
                                isWhite = true;
                                GreenFire.DisplayFire();
                            }
                        } else if (RedFret==RedFretPlayed) {
                            if (NoteSetList.get(0).redNote != null && NoteSetList.get(0).redNote.noteType == Line.NoteType.white) {
                                isWhite = true;
                                RedFire.DisplayFire();
                            }
                        }
                        if (YellowFret==YellowFretPlayed) {
                            if (NoteSetList.get(0).yellowNote != null && NoteSetList.get(0).yellowNote.noteType == Line.NoteType.white) {
                                isWhite = true;
                                YellowFire.DisplayFire();
                            }
                        }
                        if (BlueFret==BlueFretPlayed) {
                            if (NoteSetList.get(0).blueNote != null && NoteSetList.get(0).blueNote.noteType == Line.NoteType.white) {
                                isWhite = true;
                                BlueFire.DisplayFire();
                            }
                        }
                        if (OrangeFret==OrangeFretPlayed) {
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
            //endregion
        }
        //endregion
        //region button pressed no notes on screen
        else{

        }
        //endregion


    }
    public  void NoteMissed(){
        noteStreak = 0;
        lastNoteSetPlayed = new NoteSet(null, null,null,null,null);
    }
    public void NotePlayed(){
        lastNoteSetPlayed = NoteSetList.get(0);
        NoteSetList.get(0).NoteIsActive = false;
        noteStreak = noteStreak + 1;
    }
    //region sets correct fret information
    public boolean CorrectFrets(boolean includeLastNote){
        boolean GreenNoteExpected = NoteSetList.get(0).greenNote != null || (includeLastNote && lastNoteSetPlayed.greenNote != null);
        boolean RedNoteExpected = NoteSetList.get(0).redNote != null|| (includeLastNote && lastNoteSetPlayed.redNote != null);;
        boolean YellowNoteExpected = NoteSetList.get(0).yellowNote != null|| (includeLastNote && lastNoteSetPlayed.yellowNote != null);;
        boolean BlueNoteExpected = NoteSetList.get(0).blueNote != null|| (includeLastNote && lastNoteSetPlayed.blueNote != null);;
        boolean OrangeNoteExpected = NoteSetList.get(0).orangeNote != null|| (includeLastNote && lastNoteSetPlayed.orangeNote != null);;

        if (GreenNoteExpected == (GreenFret==GreenFretPlayed)
                && RedNoteExpected == (RedFret==RedFretPlayed)
                && YellowNoteExpected == (YellowFret==YellowFretPlayed)
                && BlueNoteExpected == (BlueFret==BlueFretPlayed)
                && OrangeNoteExpected == (OrangeFret==OrangeFretPlayed)){
            return true;
        }
        return false;
    }//endregion
    public boolean aNoteOnFret(LinkedList<NoteSet> NoteSetList){
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
    static void writeToFile(Long line){
        File f = new File("noteDelays.txt");
        FileWriter fw = null;
        try {
            fw = new FileWriter(f,true);
        } catch (IOException e) {
            System.out.println("File not found  =(");
        }
        try{
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(new String(line.toString())+"\n");
            bw.flush();
            bw.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
