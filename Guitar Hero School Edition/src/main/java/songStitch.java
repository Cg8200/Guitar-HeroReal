import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cg8200 on 11/30/2021.
 */
public class songStitch {

   static LinkedList <tickandvalue> BPMlist = new LinkedList<>();
   static LinkedList <tickandvalue> TSlist = new LinkedList<>();
   static int resolution = 192;
   static int BPM;
   static int TS;

    public static void loadSong(String pathname) {
        LinkedList<ChartNote> NotesList= new LinkedList<>();

        File f = new File(pathname+".txt");
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            while (br.ready()) {

                //region Getting resolution from .chart file
                String lineOfFile = br.readLine().trim();
                // if(lineOfFile.toLowerCase().startsWith("Resolution =".toLowerCase()))
                Pattern p = Pattern.compile("^Resolution =(?U)\\s*([0-9]+)(?U)\\s*");
                Matcher m = p.matcher(lineOfFile);
                // if an occurrence if a pattern was found in a given string...
                if (m.find()) {
                    resolution = Integer.parseInt(m.group(1));
                }//endregion

                //region getting BPM from .chart file
                p = Pattern.compile("^([0-9]+)(?U)\\s*=(?U)\\s*B(?U)\\s*([0-9]+)(?U)\\s*");
                m = p.matcher(lineOfFile);
                // if an occurrence if a pattern was found in a given string...
                if (m.find()) {
                    BPMlist.add(new tickandvalue(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2))));
                }//endregion

                //region getting time scale from .chart file
                p = Pattern.compile("^([0-9]+)(?U)\\s*=(?U)\\s*TS(?U)\\s*([0-9]+)(?U)\\s*");
                m = p.matcher(lineOfFile);
                // if an occurrence if a pattern was found in a given string...
                if (m.find()) {
                    TSlist.add(new tickandvalue(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2))));
                }//endregion

                //region Pulling out all chart notes from .chart file
                p = Pattern.compile("^([0-9]+)(?U)\\s*=(?U)\\s*([NS])(?U)\\s*([0-9]+)(?U)\\s*([0-9]+)(?U)\\s*");
                m = p.matcher(lineOfFile);
                // if an occurrence if a pattern was found in a given string...
                if (m.find()) {
                    NotesList.add(new ChartNote(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(3)), m.group(2), Integer.parseInt(m.group(4))));
                }//endregion

            }
            BPM = BPMlist.get(0).value;//fixme-add extended delays & stars
            BPMlist.remove(0);
            int previousTick = 0;
            while (NotesList.size() > 0) {

                int[] previousNotes = new int[]{0, 0, 0, 0, 0};
                int previousStarLength = 0;
                int previousStarTick = -1;
                LinkedList<ChartNote> currentChartNoteSet = new LinkedList<>();
                currentChartNoteSet.add(NotesList.remove(0));
                //if(NotesList.size() > 0) {
                    while (NotesList.size() > 0 && NotesList.get(0).tickNumber == currentChartNoteSet.get(0).tickNumber) {
                        currentChartNoteSet.add(NotesList.remove(0));
                    }
               // }
                ChartNote currentNote = currentChartNoteSet.get(0);
                if(currentNote.tickNumber >=BPMlist.get(0).tick){
                    BPM = BPMlist.get(0).value;
                    BPMlist.remove(0);
                }
                int numbTicks = currentNote.tickNumber - previousTick;
                //frames = (#ticks * 60 * 17) / (resolution * BPM)
                int frames = getNumFrames(resolution, BPM, numbTicks);
                if(previousTick == 0){
                    frames = frames - 81;
                    if(frames < 0){
                        frames = 0;
                    }
                }
                boolean isWhite = numbTicks <= resolution / 4;
                boolean isStar = false;//fixme
                boolean isExtended = false;//fixme
                int[] currentNotes = new int[]{0, 0, 0, 0, 0};
                for (int i = 0; i < currentChartNoteSet.size(); i++) {

                    //region separates note types by number
                    if (currentChartNoteSet.get(i).noteNumber == 0) {
                        currentNotes[0] = 1;
                    }
                    if (currentChartNoteSet.get(i).noteNumber == 1) {
                        currentNotes[1] = 1;
                    }
                    if (currentChartNoteSet.get(i).noteNumber == 2) {
                        currentNotes[2] = 1;
                    }
                    if (currentChartNoteSet.get(i).noteNumber == 3) {
                        currentNotes[3] = 1;
                    }
                    if (currentChartNoteSet.get(i).noteNumber == 4) {
                        currentNotes[4] = 1;
                    }
                    if (currentChartNoteSet.get(i).noteNumber == 5) {
                        isWhite = false;
                    }
                    if (currentChartNoteSet.get(i).noteNumber == 6) {
                        //open purple note stuff
                    }//endregion

                }
                if (previousNotes[0] == currentNotes[0]
                        && previousNotes[1] == currentNotes[1]
                        && previousNotes[2] == currentNotes[2]
                        && previousNotes[3] == currentNotes[3]
                        && previousNotes[4] == currentNotes[4]) {

                }
                Line.NoteType Green = makeType(currentNotes[0], isWhite, isStar, isExtended);
                Line.NoteType Red = makeType(currentNotes[1], isWhite, isStar, isExtended);
                Line.NoteType Yellow = makeType(currentNotes[2], isWhite, isStar, isExtended);
                Line.NoteType Blue = makeType(currentNotes[3], isWhite, isStar, isExtended);
                Line.NoteType Orange = makeType(currentNotes[4], isWhite, isStar, isExtended);
                PlayScreen.linesList.add(new Line(frames, Green, Red, Yellow, Blue, Orange));

                //5 = forced note for that tick
                previousNotes[0] = currentNotes[0];
                previousNotes[1] = currentNotes[1];
                previousNotes[2] = currentNotes[2];
                previousNotes[3] = currentNotes[3];
                previousNotes[4] = currentNotes[4];
                previousTick = currentNote.tickNumber;
                currentChartNoteSet.clear();
            }


            //  }


                /*
                String[] parsedline = lineOfFile.split(",");
                if (parsedline.length != 6) {
                    System.out.println("line does not have delay and 5 notes" + lineOfFile);
                } else {

                }
                */

        } catch (FileNotFoundException e) {
            System.out.println(e);
        }catch (IOException e) {
            System.out.println(e);
        }

        if (PlayScreen.linesList.size() > 0) {
            PlayScreen.currentLine = PlayScreen.linesList.remove();
        }

        String filename = "example.mp3";
        MP3Player mp3Player = new MP3Player(filename);
        mp3Player.play();

    }
    //region defines white star and extended
    public static Line.NoteType makeType(int notePresent,boolean isWhite,boolean isStar,boolean isExternded) {
        if (notePresent == 0) {
            return Line.NoteType.none;
        } else if (notePresent == 1 && !isWhite && !isStar && !isExternded) {
            return Line.NoteType.regular;
        } else if (notePresent == 1 && !isWhite && isStar && !isExternded) {
            return Line.NoteType.star;
        } else if (notePresent == 1 && !isWhite && !isStar && isExternded) {
            return Line.NoteType.regularExtended;
        } else if (notePresent == 1 && !isWhite && isStar && isExternded) {
            return Line.NoteType.starExtended;
        } else if (notePresent == 1 && isWhite && !isStar && !isExternded) {
            return Line.NoteType.white;
        } else if (notePresent == 1 && isWhite && !isStar && isExternded) {
            return Line.NoteType.whiteExtended;
        } else if (notePresent == 1 && isWhite && isStar && isExternded) {
            return Line.NoteType.whiteStarExtended;
        }  else if (notePresent == 1 && isWhite && isStar && !isExternded) {
            return Line.NoteType.whiteStar;
        } else {
            return Line.NoteType.BAD;
        }
    }//endregion

    //region gets number of frames
    static int getNumFrames(double resolution, double bpm, double ticks){
        return (int) ((ticks*60.0*1000.0*1000.0)/(resolution*bpm*17.0));
    }
}//endregion
