/**
 * Created by cg8200 on 12/10/2021.
 */
public class ChartNote {
   int tickNumber;
   int noteNumber;
   String NoteType;
   int Length;

    public ChartNote(int tickNumber, int noteNumber, String noteType, int Length) {
        this.tickNumber = tickNumber;
        this.noteNumber = noteNumber;
        NoteType = noteType;
        this.Length = Length;
    }
}
