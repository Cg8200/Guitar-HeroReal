import java.awt.*;

/**
 * Created by cg8200 on 11/11/2021.
 */
public class NoteSet {
    Note greenNote;
    Note redNote;
    Note yellowNote;
    Note blueNote;
    Note orangeNote;
    boolean NoteIsActive;
    public NoteSet(Note greenNote, Note redNote, Note yellowNote, Note blueNote, Note orangeNote) {
        this.greenNote = greenNote;
        this.redNote = redNote;
        this.yellowNote = yellowNote;
        this.blueNote = blueNote;
        this.orangeNote = orangeNote;
        NoteIsActive = true;
    }
    public void Draw(Graphics g) {
       if(greenNote!=null){
           greenNote.Draw(g);
       }
        if(redNote!=null){
            redNote.Draw(g);
        }
        if(yellowNote!=null){
            yellowNote.Draw(g);
        }
        if(blueNote!=null){
            blueNote.Draw(g);
        }
        if(orangeNote !=null){
            orangeNote.Draw(g);
        }
    }
    public void Update(int yValueToRemove){
        if(greenNote!=null){
            greenNote.Update();
            if(greenNote.noteY>yValueToRemove){
                NoteIsActive = false;
                if(greenNote.noteLength==0) {
                    PlayScreen.noteStreak = 0;
                    for (int i = 0; i < PlayScreen.LongNoteSet.size(); i++) {
                        if(PlayScreen.LongNoteSet.get(i).greenNote!=null) {
                            if (greenNote.noteY - 20 < PlayScreen.LongNoteSet.get(i).greenNote.getBottomY() && greenNote.getBottomY() > PlayScreen.LongNoteSet.get(i).greenNote.getBottomY()) {
                                PlayScreen.LongNoteSet.get(i).greenNote.noteMissed();
                            }
                        }
                    }
                }
            }
        }
        if(redNote!=null){
            redNote.Update();
            if(redNote.noteY>yValueToRemove){
                NoteIsActive = false;
                if(redNote.noteLength==0) {
                    PlayScreen.noteStreak = 0;
                    for (int i = 0; i < PlayScreen.LongNoteSet.size(); i++) {
                        if(PlayScreen.LongNoteSet.get(i).redNote!=null) {
                            if (redNote.noteY - 20 < PlayScreen.LongNoteSet.get(i).redNote.getBottomY() && redNote.getBottomY() > PlayScreen.LongNoteSet.get(i).redNote.getBottomY()) {
                                PlayScreen.LongNoteSet.get(i).redNote.noteMissed();
                            }
                        }
                    }
                }
            }
        }
        if(yellowNote!=null){
            yellowNote.Update();
            if(yellowNote.noteY>yValueToRemove){
                NoteIsActive = false;
                if(yellowNote.noteLength==0) {
                    PlayScreen.noteStreak = 0;
                    for (int i = 0; i < PlayScreen.LongNoteSet.size(); i++) {
                        if(PlayScreen.LongNoteSet.get(i).yellowNote!=null) {
                            if (yellowNote.noteY - 20 < PlayScreen.LongNoteSet.get(i).yellowNote.getBottomY() && yellowNote.getBottomY() > PlayScreen.LongNoteSet.get(i).yellowNote.getBottomY()) {
                                PlayScreen.LongNoteSet.get(i).yellowNote.noteMissed();
                            }
                        }
                    }
                }
            }
        }
        if(blueNote!=null){
            blueNote.Update();
            if(blueNote.noteY>yValueToRemove){
                NoteIsActive = false;
                if(blueNote.noteLength==0) {
                    PlayScreen.noteStreak = 0;
                    for (int i = 0; i < PlayScreen.LongNoteSet.size(); i++) {
                        if(PlayScreen.LongNoteSet.get(i).blueNote!=null) {
                            if (blueNote.noteY - 20 < PlayScreen.LongNoteSet.get(i).blueNote.getBottomY() && blueNote.getBottomY() > PlayScreen.LongNoteSet.get(i).blueNote.getBottomY()) {
                                PlayScreen.LongNoteSet.get(i).blueNote.noteMissed();
                            }
                        }
                    }
                }
            }
        }
        if(orangeNote !=null){
            orangeNote.Update();
            if(orangeNote.noteY>yValueToRemove){
                NoteIsActive = false;
                if(orangeNote.noteLength==0) {
                    PlayScreen.noteStreak = 0;
                    for (int i =0; i<PlayScreen.LongNoteSet.size(); i++) {
                        if(PlayScreen.LongNoteSet.get(i).orangeNote!=null) {
                            if (orangeNote.noteY - 20 < PlayScreen.LongNoteSet.get(i).orangeNote.getBottomY() && orangeNote.getBottomY() > PlayScreen.LongNoteSet.get(i).orangeNote.getBottomY()) {
                                PlayScreen.LongNoteSet.get(i).orangeNote.noteMissed();
                            }
                        }
                    }
                }
            }
        }

    }
}