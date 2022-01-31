/**
 * Created by cg8200 on 10/29/2021.
 */
public class Line {
    enum NoteType {
        none, regular, star, regularExtended, starExtended, white, whiteExtended, whiteStarExtended,whiteStar, BAD
    }
    double delay;
    NoteType Green;
    NoteType Red;
    NoteType Yellow;
    NoteType Blue;
    NoteType Orange;
    double greenLength;
    double redlength;
    double yellowLength;
    double blueLength;
    double orangeLength;

    public Line(double delay, NoteType green, NoteType red, NoteType yellow, NoteType blue, NoteType orange, double greenLength, double redLength, double yellowLength, double blueLength,double orangeLength) {
        this.delay = delay;
        Green = green;
        Red = red;
        Yellow = yellow;
        Blue = blue;
        Orange = orange;
        this.greenLength = greenLength;
        this.redlength = redLength;
        this.yellowLength = yellowLength;
        this.blueLength = blueLength;
        this.orangeLength = orangeLength;
    }
}
