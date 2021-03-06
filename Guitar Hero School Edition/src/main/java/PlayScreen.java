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
    boolean drawNoNoteOnFretTest = false;
    boolean drawNoNoteWithCorrectFretsTest = false;
    int playedIndex;
    int notesOnFret = 0;
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

    Image GreenNoteSpark;
    Image RedNoteSpark;
    Image YellowNoteSpark;
    Image BlueNoteSpark;
    Image OrangeNoteSpark;
    NoteSet lastNoteSetPlayed;
    boolean drawGreen = false;
    boolean drawRed= false;
    boolean drawYellow= false;
    boolean drawBlue= false;
    boolean drawOrange= false;
    static MP3player missSound;
    //Chart file stuff
    static int resolution = 192;
    double totalNSsinceNote = 0;
    static long framelengthNS = 0;
    boolean strumHappened = false;
    boolean greenJustPlayed = false;
    boolean redJustPlayed = false;
    boolean yellowJustPlayed = false;
    boolean blueJustPlayed = false;
    boolean orangeJustPlayed = false;

    boolean greenJustReleased = false;
    boolean redJustReleased = false;
    boolean yellowJustReleased = false;
    boolean blueJustReleased = false;
    boolean orangeJustReleased = false;

    Image prevGreenFret;
    Image prevRedFret;
    Image prevYellowFret;
    Image prevBlueFret;
    Image prevOrangeFret;


    static int noteStreak;
    //region sets images
    public PlayScreen() {
        String filename = "src/main/resources/missed.mp3";
        missSound = new MP3player(filename);//Fixme
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
            HitBox = new Image("src/main/resources/Hitbox.png",650, 850, 600, 200,false);

            GreenFire = new Fire("src/main/resources/1.png",710, 880, 80, 80,false);
            RedFire = new Fire("src/main/resources/1.png",810, 880, 80, 80,false);
            YellowFire = new Fire("src/main/resources/1.png",910, 880, 80, 80,false);
            BlueFire = new Fire("src/main/resources/1.png",1010, 880, 80, 80,false);
            OrangeFire = new Fire("src/main/resources/1.png",1110, 880, 80, 80,false);

        GreenNoteGlow = new Image("src/main/resources/GreenNoteGlow.png",710, 880, 80, 80,false);
        RedNoteGlow = new Image("src/main/resources/RedNoteGlow.png",810, 880, 80, 80,false);
        YellowNoteGlow = new Image("src/main/resources/YellowNoteGlow.png",910, 880, 80, 80,false);
        BlueNoteGlow = new Image("src/main/resources/BlueNoteGlow.png",1010, 880, 80, 80,false);
        OrangeNoteGlow = new Image("src/main/resources/OrangeNoteGlow.png",1110, 880, 80, 80,false);

        GreenNoteSpark = new Image("src/main/resources/sparks.png",710, 880, 80, 80,false);
        RedNoteSpark = new Image("src/main/resources/sparks.png",810, 880, 80, 80,false);
        YellowNoteSpark = new Image("src/main/resources/sparks.png",910, 880, 80, 80,false );
        BlueNoteSpark = new Image("src/main/resources/sparks.png",1010, 880, 80, 80,false);
        OrangeNoteSpark = new Image("src/main/resources/sparks.png",1110, 880, 80, 80,false);

        prevGreenFret = GreenFret;
        prevRedFret = RedFret;
        prevYellowFret = YellowFret;
        prevBlueFret = BlueFret;
        prevOrangeFret = OrangeFret;
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

        if(drawGreen){
            g.drawImage(GreenNoteSpark.image,GreenNoteSpark.x, GreenNoteSpark.y, GreenNoteSpark.Width, GreenNoteSpark.Height, null);
        }
        if(drawRed){
            g.drawImage(RedNoteSpark.image,RedNoteSpark.x, RedNoteSpark.y, RedNoteSpark.Width, RedNoteSpark.Height, null);
        }
        if(drawYellow){
            g.drawImage(YellowNoteSpark.image,YellowNoteSpark.x, YellowNoteSpark.y, YellowNoteSpark.Width, YellowNoteSpark.Height, null);
        }
        if(drawBlue){
            g.drawImage(BlueNoteSpark.image,BlueNoteSpark.x,BlueNoteSpark.y, BlueNoteSpark.Width,BlueNoteSpark.Height, null);
        }
        if(drawOrange){
            g.drawImage(OrangeNoteSpark.image,OrangeNoteSpark.x, OrangeNoteSpark.y, OrangeNoteSpark.Width, OrangeNoteSpark.Height, null);
        }


        GreenFire.Draw(g);
        RedFire.Draw(g);
        YellowFire.Draw(g);
        BlueFire.Draw(g);
        OrangeFire.Draw(g);

        g.drawString(String.valueOf(noteStreak),20,20);
        g.drawString("drawGreen: "+String.valueOf(drawGreen),20,40);
        g.drawString("drawRed: "+String.valueOf(drawGreen),20,60);
        g.drawString("drawYellow: "+String.valueOf(drawGreen),20,80);
        g.drawString("drawBlue: "+String.valueOf(drawGreen),20,100);
        g.drawString("drawOrange: "+String.valueOf(drawGreen),20,120);


       /* if(drawNoNoteWithCorrectFretsTest){
            g.drawImage(OrangeNoteSpark.image,200, 400, OrangeNoteSpark.Width, OrangeNoteSpark.Height, null);

        }
        if(drawNoNoteOnFretTest){
            g.drawImage(OrangeFire.image,300, 600, OrangeNoteSpark.Width, OrangeNoteSpark.Height, null);
        }*/
       /* Font myFont = new Font("TimesRoman", Font.BOLD, 40);
        g.setFont(myFont);
        g.setColor(Color.RED);
        g.drawString("Notes on Screen"+String.valueOf(NoteSetList.size()),20,200);
        g.setFont(myFont);
        g.setColor(Color.RED);
        g.drawString("Notes on Fret:"+String.valueOf(notesOnFret),20,250);
        g.setColor(Color.RED);
        g.drawString("Played Idx::"+String.valueOf(playedIndex),20,300);*/

    } //endregion


    public void update() {
        //region setup Justplayed and just released booleans
        greenJustPlayed = false;
        redJustPlayed = false;
        yellowJustPlayed = false;
        blueJustPlayed = false;
        orangeJustPlayed = false;

        greenJustReleased = false;
        redJustReleased = false;
        yellowJustReleased = false;
        blueJustReleased = false;
        orangeJustReleased = false;

        if(prevGreenFret == GreenFretPlayed && GreenFret == GreenFretIdle){
            greenJustReleased = true;
        }
        else if (prevGreenFret == GreenFretIdle && GreenFret == GreenFretPlayed) {
            greenJustPlayed =true;
        }
        if(prevRedFret == RedFretPlayed && RedFret == RedFretIdle){
            redJustReleased = true;
        }
        else if (prevRedFret == RedFretIdle && RedFret == RedFretPlayed) {
            redJustPlayed =true;
        }
        if(prevYellowFret == YellowFretPlayed && YellowFret == YellowFretIdle){
            yellowJustReleased = true;
        }
        else if (prevYellowFret == YellowFretIdle && YellowFret == YellowFretPlayed) {
            yellowJustPlayed =true;
        }
        if(prevBlueFret == BlueFretPlayed && BlueFret == BlueFretIdle){
            blueJustReleased = true;
        }
        else if (prevBlueFret == BlueFretIdle && BlueFret == BlueFretPlayed) {
            blueJustPlayed =true;
        }
        if(prevOrangeFret == OrangeFretPlayed && OrangeFret == OrangeFretIdle){
            orangeJustReleased = true;
        }
        else if (prevOrangeFret == OrangeFretIdle && OrangeFret == OrangeFretPlayed) {
            orangeJustPlayed =true;
        }
        //endregion

        drawGreen = false;
        drawRed = false;
        drawYellow = false;
        drawBlue = false;
        drawOrange = false;
        long frameTimeNS = System.nanoTime();
        framelengthNS = frameTimeNS - Game.LastFrameTimeNS;
        Game.LastFrameTimeNS = frameTimeNS;
        totalNSsinceNote = totalNSsinceNote + framelengthNS;

       //region add next note to screen if correct amount of time has passed
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
                    if(linesList.size()<=4){
                        System.out.println();
                    }
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
        //endregion

        //region move notes on screen
        for (int i = 0; i < NoteSetList.size(); i++) {
            NoteSetList.get(i).Update(HitBox.y + HitBox.Height);
        }
        for (int i = 0; i < LongNoteSet.size(); i++) {
            LongNoteSet.get(i).Update(HitBox.y + HitBox.Height);
        }
        //endregion


        //region test stuff
        notesOnFret = 0;
        for(int i=0; i<NoteSetList.size(); i++) {
            if (!aNoteOnFret(NoteSetList.get(i))) {
                notesOnFret = i;
                break;
            }
        }
        //endregion
        //region strum happened
        drawNoNoteOnFretTest=false;
        drawNoNoteWithCorrectFretsTest=false;
        if ((strumHappened)) {
            strumHappened =false;
            drawNoNoteWithCorrectFretsTest=true;
            try {
                playedIndex = -1;
                for(int i=0; i<NoteSetList.size(); i++){
                    if(!aNoteOnFret(NoteSetList.get(i))){
                        drawNoNoteOnFretTest =true;
                        break;
                    }
                    if(CorrectFrets(false,NoteSetList.get(i))){
                        drawNoNoteWithCorrectFretsTest =false;
                        NotePlayed(i);
                        playedIndex = i;
                        break;
                    }
                }
                //if a correct note was played on the hitbox
                if(playedIndex<NoteSetList.size() && playedIndex !=-1) {
                    if(playedIndex>0) {
                        NoteMissed();

                        //mark all of the notes to remove
                        for (int i = 0; i < playedIndex; i++) {
                            NoteSetList.get(i).NoteIsActive = false;
                        }
                    }

                    //draw fire
                    if ((GreenFret == GreenFretPlayed)) {
                        GreenFire.DisplayFire();
                    }
                    if (RedFret == RedFretPlayed) {
                        RedFire.DisplayFire();
                    }
                    if (YellowFret == YellowFretPlayed) {
                        YellowFire.DisplayFire();
                    }
                    if (BlueFret == BlueFretPlayed) {
                        BlueFire.DisplayFire();
                    }
                    if (OrangeFret == OrangeFretPlayed) {
                        OrangeFire.DisplayFire();
                    }
                }else{
                    NoteMissed();
                }

            } catch (IndexOutOfBoundsException ex) {
                NoteMissed();
            }
        }
        //endregion

        //region tap notes
        else if (noteStreak >= 1) {
            if (aNoteOnFret(NoteSetList)) {

                if(greenJustPlayed || redJustPlayed || yellowJustPlayed ||blueJustPlayed||orangeJustPlayed){
                    if(isCurrentNoteWhite()){
                        if(correctFretsJustPlayed(NoteSetList.get(0))){
                            if (NoteSetList.get(0).greenNote != null){
                                GreenFire.DisplayFire();
                            }
                            if (NoteSetList.get(0).redNote != null){
                                RedFire.DisplayFire();
                            }
                            if (NoteSetList.get(0).yellowNote != null){
                                YellowFire.DisplayFire();
                            }
                            if (NoteSetList.get(0).blueNote != null){
                                BlueFire.DisplayFire();
                            }
                            if (NoteSetList.get(0).orangeNote != null){
                                OrangeFire.DisplayFire();
                            }
                            NotePlayed(0);

                                    NoteSetList.remove(0);

                            }
                        }

                    }


                  if(greenJustReleased || redJustReleased || yellowJustReleased ||blueJustReleased||orangeJustReleased){
                    if(isCurrentNoteWhite()){
                        if(CorrectFrets(false,NoteSetList.get(0))){
                            if (NoteSetList.get(0).greenNote != null){
                                GreenFire.DisplayFire();
                            }
                            if (NoteSetList.get(0).redNote != null){
                                RedFire.DisplayFire();
                            }
                            if (NoteSetList.get(0).yellowNote != null){
                                YellowFire.DisplayFire();
                            }
                            if (NoteSetList.get(0).blueNote != null){
                                BlueFire.DisplayFire();
                            }
                            if (NoteSetList.get(0).orangeNote != null){
                                OrangeFire.DisplayFire();
                            }
                            NotePlayed(0);

                                    NoteSetList.remove(0);

                        }
                    }

                }
                /*
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
                        NotePlayed(0);
                    }
                } */
            }

        }
        //endregion



        //region longnote check if missed or played
        if(LongNoteSet.size()>0 && aNoteOnFret(LongNoteSet)) {
            if (LongNoteSet.getFirst().greenNote != null) {
                if (LongNoteSet.getFirst().greenNote.wasStartOfLNPlayed) {
                    if (LongNoteSet.getFirst().greenNote.longMissed == false) {
                        if (GreenFret == GreenFretPlayed) {
                            drawGreen = true;
                        } else {
                            LongNoteSet.getFirst().greenNote.noteMissed();

                        }
                    }
                }
            }
            if (LongNoteSet.getFirst().redNote != null) {
                if (LongNoteSet.getFirst().redNote.wasStartOfLNPlayed) {
                    if (LongNoteSet.getFirst().redNote.longMissed == false) {
                        if (RedFret == RedFretPlayed) {
                            drawRed = true;
                        } else {
                            LongNoteSet.getFirst().redNote.noteMissed();

                        }
                    }
                }
            }
            if (LongNoteSet.getFirst().yellowNote != null) {
                if (LongNoteSet.getFirst().yellowNote.wasStartOfLNPlayed) {
                    if (LongNoteSet.getFirst().yellowNote.longMissed == false) {
                        if (YellowFret == YellowFretPlayed) {
                            drawYellow = true;
                        } else {
                            LongNoteSet.getFirst().yellowNote.noteMissed();

                        }
                    }
                }
            }
            if (LongNoteSet.getFirst().blueNote != null) {
                if (LongNoteSet.getFirst().blueNote.wasStartOfLNPlayed) {
                    if (LongNoteSet.getFirst().blueNote.longMissed == false) {
                        if (BlueFret == BlueFretPlayed) {
                            drawBlue = true;
                        } else {
                            LongNoteSet.getFirst().blueNote.noteMissed();

                        }
                    }
                }
            }
            if (LongNoteSet.getFirst().orangeNote != null) {
                if (LongNoteSet.getFirst().orangeNote.wasStartOfLNPlayed) {
                    if (LongNoteSet.getFirst().orangeNote.longMissed == false) {
                        if (OrangeFret == OrangeFretPlayed) {
                            drawOrange = true;
                        } else {
                            LongNoteSet.getFirst().orangeNote.noteMissed();

                        }
                    }
                }
            }
        }
        //endregion

      //region remove inactive notes
        for (int i = 0; i < NoteSetList.size(); i++) {
            if (!NoteSetList.get(i).NoteIsActive) {
                NoteSetList.remove(i);
                i--;
            }
        }
        for (int i = 0; i < LongNoteSet.size(); i++) {
            if (!LongNoteSet.get(i).NoteIsActive) {
                LongNoteSet.remove(i);
                i--;
            }
        }
        //endregion





//fixme make it check all notes on fret to see if any are played


        prevGreenFret = GreenFret;
        prevRedFret = RedFret;
        prevYellowFret = YellowFret;
        prevBlueFret = BlueFret;
        prevOrangeFret = OrangeFret;

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
            strumHappened = true;
        }




    }
    public  void NoteMissed(){
        noteStreak = 0;
        missSound.play();
        lastNoteSetPlayed = new NoteSet(null, null,null,null,null);
    }
    public void NotePlayed(int index){
        lastNoteSetPlayed = NoteSetList.get(index);
        NoteSetList.get(index).NoteIsActive = false;
        noteStreak = noteStreak + 1;
        int i = 0;
        while(i < LongNoteSet.size()){
            if(LongNoteSet.get(i).greenNote!=null&&lastNoteSetPlayed.greenNote!=null) {
                if (lastNoteSetPlayed.greenNote.noteY -20< LongNoteSet.get(i).greenNote.getBottomY() && lastNoteSetPlayed.greenNote.getBottomY() > LongNoteSet.get(i).greenNote.getBottomY()) {
                    LongNoteSet.get(i).greenNote.wasStartOfLNPlayed = true;
                }
            }
            if(LongNoteSet.get(i).redNote!=null&&lastNoteSetPlayed.redNote!=null) {
                if (lastNoteSetPlayed.redNote.noteY -20< LongNoteSet.get(i).redNote.getBottomY() && lastNoteSetPlayed.redNote.getBottomY() > LongNoteSet.get(i).redNote.getBottomY()) {
                    LongNoteSet.get(i).redNote.wasStartOfLNPlayed = true;
                }
            }
            if(LongNoteSet.get(i).yellowNote!=null&&lastNoteSetPlayed.yellowNote!=null) {
                if (lastNoteSetPlayed.yellowNote.noteY -20< LongNoteSet.get(i).yellowNote.getBottomY() && lastNoteSetPlayed.yellowNote.getBottomY() > LongNoteSet.get(i).yellowNote.getBottomY()) {
                    LongNoteSet.get(i).yellowNote.wasStartOfLNPlayed = true;
                }
            }
            if(LongNoteSet.get(i).blueNote!=null&&lastNoteSetPlayed.blueNote!=null) {
                if (lastNoteSetPlayed.blueNote.noteY -20< LongNoteSet.get(i).blueNote.getBottomY() && lastNoteSetPlayed.blueNote.getBottomY() > LongNoteSet.get(i).blueNote.getBottomY()) {
                    LongNoteSet.get(i).blueNote.wasStartOfLNPlayed = true;
                }
            }
            if(LongNoteSet.get(i).orangeNote!=null&&lastNoteSetPlayed.orangeNote!=null) {
                if (lastNoteSetPlayed.orangeNote.noteY -20< LongNoteSet.get(i).orangeNote.getBottomY() && lastNoteSetPlayed.orangeNote.getBottomY() > LongNoteSet.get(i).orangeNote.getBottomY()) {
                    LongNoteSet.get(i).orangeNote.wasStartOfLNPlayed = true;
                }
            }
            i++;
        }
    }


    //region sets correct fret information
    public boolean CorrectFrets(boolean includeLastNote){
        boolean GreenNoteExpected = NoteSetList.get(0).greenNote != null;
        boolean RedNoteExpected = NoteSetList.get(0).redNote != null;
        boolean YellowNoteExpected = NoteSetList.get(0).yellowNote != null;
        boolean BlueNoteExpected = NoteSetList.get(0).blueNote != null;
        boolean OrangeNoteExpected = NoteSetList.get(0).orangeNote != null;

        boolean GreenNoteOptional = (includeLastNote && lastNoteSetPlayed.greenNote != null);
        boolean RedNoteOptional = (includeLastNote && lastNoteSetPlayed.redNote != null);
        boolean YellowNoteOptional = (includeLastNote && lastNoteSetPlayed.yellowNote != null);
        boolean BlueNoteOptional =(includeLastNote && lastNoteSetPlayed.blueNote != null);
        boolean OrangeNoteOptional = (includeLastNote && lastNoteSetPlayed.orangeNote != null);

        boolean expectedNotesPlayed = true;
        if(GreenNoteExpected && (GreenFret!=GreenFretPlayed)){
            expectedNotesPlayed = false;
        }
        if(RedNoteExpected && (RedFret!=RedFretPlayed)){
            expectedNotesPlayed = false;
        }
        if(YellowNoteExpected && (YellowFret!=YellowFretPlayed)){
            expectedNotesPlayed = false;
        }
        if(BlueNoteExpected && (BlueFret!=BlueFretPlayed)){
            expectedNotesPlayed = false;
        }
        if(OrangeNoteExpected && (OrangeFret!=OrangeFretPlayed)){
            expectedNotesPlayed = false;
        }

        if(expectedNotesPlayed){
            if (includeLastNote) {
                return (GreenNoteExpected || GreenNoteOptional) == (GreenFret == GreenFretPlayed)
                        && (RedNoteExpected || RedNoteOptional) == (RedFret == RedFretPlayed)
                        && (YellowNoteExpected || YellowNoteOptional) == (YellowFret == YellowFretPlayed)
                        && (BlueNoteExpected || BlueNoteOptional) == (BlueFret == BlueFretPlayed)
                        && (OrangeNoteExpected || OrangeNoteOptional) == (OrangeFret == OrangeFretPlayed);
            }
            else{
                return GreenNoteExpected == (GreenFret==GreenFretPlayed)
                        && RedNoteExpected == (RedFret==RedFretPlayed)
                        && YellowNoteExpected == (YellowFret==YellowFretPlayed)
                        && BlueNoteExpected == (BlueFret==BlueFretPlayed)
                        && OrangeNoteExpected == (OrangeFret==OrangeFretPlayed);
            }
        }

        return false;
    }

    public boolean correctFretsJustPlayed(NoteSet noteSet){
        boolean GreenNoteExpected = noteSet.greenNote != null;
        boolean RedNoteExpected = noteSet.redNote != null;
        boolean YellowNoteExpected = noteSet.yellowNote != null;
        boolean BlueNoteExpected = noteSet.blueNote != null;
        boolean OrangeNoteExpected = noteSet.orangeNote != null;

        if (GreenNoteExpected == greenJustPlayed
                && RedNoteExpected == redJustPlayed
                && YellowNoteExpected == yellowJustPlayed
                && BlueNoteExpected == blueJustPlayed
                && OrangeNoteExpected == orangeJustPlayed){
            return true;
        }
        return false;
    }
    public boolean CorrectFrets(boolean includeLastNote,NoteSet noteSet){
        boolean GreenNoteExpected = noteSet.greenNote != null || (includeLastNote && lastNoteSetPlayed.greenNote != null);
        boolean RedNoteExpected = noteSet.redNote != null|| (includeLastNote && lastNoteSetPlayed.redNote != null);;
        boolean YellowNoteExpected = noteSet.yellowNote != null|| (includeLastNote && lastNoteSetPlayed.yellowNote != null);;
        boolean BlueNoteExpected = noteSet.blueNote != null|| (includeLastNote && lastNoteSetPlayed.blueNote != null);;
        boolean OrangeNoteExpected = noteSet.orangeNote != null|| (includeLastNote && lastNoteSetPlayed.orangeNote != null);;

        if (GreenNoteExpected == (GreenFret==GreenFretPlayed)
                && RedNoteExpected == (RedFret==RedFretPlayed)
                && YellowNoteExpected == (YellowFret==YellowFretPlayed)
                && BlueNoteExpected == (BlueFret==BlueFretPlayed)
                && OrangeNoteExpected == (OrangeFret==OrangeFretPlayed)){
            return true;
        }
        return false;
    }
    //endregion
    public boolean aNoteOnFret(LinkedList<NoteSet> NoteSetList){
        if(NoteSetList.size()==0) {return false;}
       return  (NoteSetList.get(0).greenNote != null && NoteSetList.get(0).greenNote.isOnFret(HitBox.y, HitBox.y + HitBox.Height))
                || (NoteSetList.get(0).redNote != null && NoteSetList.get(0).redNote.isOnFret(HitBox.y, HitBox.y + HitBox.Height))
                || (NoteSetList.get(0).yellowNote != null && NoteSetList.get(0).yellowNote.isOnFret(HitBox.y, HitBox.y + HitBox.Height))
                || (NoteSetList.get(0).blueNote != null && NoteSetList.get(0).blueNote.isOnFret(HitBox.y, HitBox.y + HitBox.Height))
                || (NoteSetList.get(0).orangeNote != null && NoteSetList.get(0).orangeNote.isOnFret(HitBox.y, HitBox.y + HitBox.Height));
    }
    public boolean aNoteOnFret(NoteSet noteSet){
        if(noteSet == null) {return false;}
        return  (noteSet.greenNote != null && noteSet.greenNote.isOnFret(HitBox.y, HitBox.y + HitBox.Height))
                || (noteSet.redNote != null && noteSet.redNote.isOnFret(HitBox.y, HitBox.y + HitBox.Height))
                || (noteSet.yellowNote != null && noteSet.yellowNote.isOnFret(HitBox.y, HitBox.y + HitBox.Height))
                || (noteSet.blueNote != null && noteSet.blueNote.isOnFret(HitBox.y, HitBox.y + HitBox.Height))
                || (noteSet.orangeNote != null && noteSet.orangeNote.isOnFret(HitBox.y, HitBox.y + HitBox.Height));
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

    public boolean isCurrentNoteWhite(){
        if (NoteSetList.get(0).greenNote != null && NoteSetList.get(0).greenNote.noteType == Line.NoteType.white) {
           return true;
        }
        if (NoteSetList.get(0).redNote != null && NoteSetList.get(0).redNote.noteType == Line.NoteType.white) {
            return true;
        }
        if (NoteSetList.get(0).yellowNote != null && NoteSetList.get(0).yellowNote.noteType == Line.NoteType.white) {
            return true;
        }
        if (NoteSetList.get(0).blueNote != null && NoteSetList.get(0).blueNote.noteType == Line.NoteType.white) {
            return true;
        }
        if (NoteSetList.get(0).orangeNote != null && NoteSetList.get(0).orangeNote.noteType == Line.NoteType.white) {
            return true;
        }
        return false;
    }
}
