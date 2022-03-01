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
                }
            }
        }
        if(redNote!=null){
            redNote.Update();
            if(redNote.noteY>yValueToRemove){
                NoteIsActive = false;
                if(redNote.noteLength==0) {
                    PlayScreen.noteStreak = 0;
                }
            }
        }
        if(yellowNote!=null){
            yellowNote.Update();
            if(yellowNote.noteY>yValueToRemove){
                NoteIsActive = false;
                if(yellowNote.noteLength==0) {
                    PlayScreen.noteStreak = 0;
                }
            }
        }
        if(blueNote!=null){
            blueNote.Update();
            if(blueNote.noteY>yValueToRemove){
                NoteIsActive = false;
                if(blueNote.noteLength==0) {
                    PlayScreen.noteStreak = 0;
                }
            }
        }
        if(orangeNote !=null){
            orangeNote.Update();
            if(orangeNote.noteY>yValueToRemove){
                NoteIsActive = false;
                if(orangeNote.noteLength==0) {
                    PlayScreen.noteStreak = 0;
                }
            }
        }
    }
}