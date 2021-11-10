/**
 * Created by cg8200 on 10/29/2021.
 */
public class Line {
    enum NoteType {
        none, regular, star, regularExtended, starExtended, white, whiteExtended, whiteStarExtended, BAD
    }
    int delay;
    NoteType Green;
    NoteType Red;
    NoteType Yellow;
    NoteType Blue;
    NoteType Orange;

    public Line(int delay, NoteType green, NoteType red, NoteType yellow, NoteType blue, NoteType orange) {
        this.delay = delay;
        Green = green;
        Red = red;
        Yellow = yellow;
        Blue = blue;
        Orange = orange;
    }
}
