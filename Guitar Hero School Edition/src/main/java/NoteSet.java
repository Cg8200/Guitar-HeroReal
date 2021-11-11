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
    public void Update(){

        if(greenNote!=null){
            greenNote.Update();
            if(greenNote.noteY>greenNote.ScreenHeight){
                NoteIsActive = false;
            }
        }
        else if(redNote!=null){
            redNote.Update();
            if(redNote.noteY>redNote.ScreenHeight){
                NoteIsActive = false;
            }
        }
        else if(yellowNote!=null){
            yellowNote.Update();
            if(yellowNote.noteY>yellowNote.ScreenHeight){
                NoteIsActive = false;
            }
        }
        else if(blueNote!=null){
            blueNote.Update();
            if(blueNote.noteY>blueNote.ScreenHeight){
                NoteIsActive = false;
            }
        }
        else if(orangeNote !=null){
            orangeNote.Update();
            if(orangeNote.noteY>orangeNote.ScreenHeight){
                NoteIsActive = false;
            }
        }
    }
}
