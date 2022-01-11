/**
 * Created by cg8200 on 12/10/2021.
 */
public class ChartNote {
   int tickNumber;
   int noteNumber;
   String NoteType;
   int starDelay;

    public ChartNote(int tickNumber, int noteNumber, String noteType, int starDelay) {
        this.tickNumber = tickNumber;
        this.noteNumber = noteNumber;
        NoteType = noteType;
        this.starDelay = starDelay;
    }
}
