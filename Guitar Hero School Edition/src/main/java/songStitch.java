import java.io.*;
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
  static int startTickNumber;

    public static void loadSong(String pathname) {
        startTickNumber = Game.debugMode ? Game.startTickNumber :0;
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
                    BPMlist.add(new tickandvalue(Integer.parseInt(m.group(1))-startTickNumber, Integer.parseInt(m.group(2))));
                }//endregion

                //region getting time scale from .chart file
                p = Pattern.compile("^([0-9]+)(?U)\\s*=(?U)\\s*TS(?U)\\s*([0-9]+)(?U)\\s*");
                m = p.matcher(lineOfFile);
                // if an occurrence if a pattern was found in a given string...
                if (m.find()) {
                    TSlist.add(new tickandvalue(Integer.parseInt(m.group(1))-startTickNumber, Integer.parseInt(m.group(2))));
                }//endregion

                //region Pulling out all chart notes from .chart file
                p = Pattern.compile("^([0-9]+)(?U)\\s*=(?U)\\s*([N])(?U)\\s*([0-9]+)(?U)\\s*([0-9]+)(?U)\\s*");
                m = p.matcher(lineOfFile);
                // if an occurrence if a pattern was found in a given string...
                if (m.find()) {
                    NotesList.add(new ChartNote(Integer.parseInt(m.group(1))-startTickNumber, Integer.parseInt(m.group(3)), m.group(2), Integer.parseInt(m.group(4))));
                }//endregion

            }
            //expected: notelist, TSlist, BPMlist,resolution set
            BPM = BPMlist.get(0).value;//fixme-add extended delays & stars
            BPMlist.remove(0);
            int previousTick = 0;
            int[] previousNotes = new int[]{0, 0, 0, 0, 0};
            int previousStarLength = 0;
            int previousStarTick = -1;
            while (NotesList.size() > 0) {
                LinkedList<ChartNote> currentChartNoteSet = new LinkedList<>();
                currentChartNoteSet.add(NotesList.remove(0));
                //currentChartNoteSet has a value
                //if(NotesList.size() > 0) {
                    while (NotesList.size() > 0 && NotesList.get(0).tickNumber == currentChartNoteSet.get(0).tickNumber) {
                        currentChartNoteSet.add(NotesList.remove(0));
                    }
               // }
                //expect: currentChartNoteSet has all notes played at one time and should not be empty
                ChartNote currentNote = currentChartNoteSet.get(0);
                    //make sure bpm values are correct
                if(BPMlist.size() > 0 && currentNote.tickNumber >=BPMlist.get(0).tick){
                    BPM = BPMlist.get(0).value;
                    BPMlist.remove(0);
                }
               // if(currentNote.tickNumber>=startTickNumber) {
                    int numbTicks = currentNote.tickNumber - previousTick;//numticks set to numtics between notes
                    double ns = getNumNS(resolution, BPM, numbTicks);//convert to nanoseconds
                    ns = Game.debugMode ? ns / Game.debugRate : ns;
                    if (previousTick == 0) {//adjustment for first note
                        ns = ns - (81 * 17000000.0);
                        if (ns < 0) {
                            ns = 0;
                        }
                    }

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

                        if (currentChartNoteSet.get(i).noteNumber == 6) {
                            //open purple note stuff
                        }//endregion

                    }
                    boolean sameNotes = currentNotes[0] == previousNotes[0]
                            && currentNotes[1] == previousNotes[1]
                            && currentNotes[2] == previousNotes[2]
                            && currentNotes[3] == previousNotes[3]
                            && currentNotes[4] == previousNotes[4];
                    boolean isWhite = numbTicks <= resolution / 4 && !sameNotes;
                    boolean isStar = false;//fixme
                    boolean isExtended = currentNote.Length > 0 && currentNote.NoteType.equals("N");

                    for (int i = 0; i < currentChartNoteSet.size(); i++) {

                        if (currentChartNoteSet.get(i).noteNumber == 5) {
                            isWhite = false;
                        }
                        if (currentChartNoteSet.get(i).noteNumber == 6) {
                            //open purple note stuff
                        }//endregion

                    }
                    Line.NoteType Green = makeType(currentNotes[0], isWhite, isStar, false);
                    Line.NoteType Red = makeType(currentNotes[1], isWhite, isStar, false);
                    Line.NoteType Yellow = makeType(currentNotes[2], isWhite, isStar, false);
                    Line.NoteType Blue = makeType(currentNotes[3], isWhite, isStar, false);
                    Line.NoteType Orange = makeType(currentNotes[4], isWhite, isStar, false);

                    PlayScreen.linesList.add(new Line(ns, Green, Red, Yellow, Blue, Orange, 0, 0, 0, 0, 0, isExtended));
                    if (isExtended) {
                        currentNotes = new int[]{0, 0, 0, 0, 0};
                        double[] currentLengths = new double[]{0, 0, 0, 0, 0};
                        for (int i = 0; i < currentChartNoteSet.size(); i++) {

                            //region separates note types by number
                            if (currentChartNoteSet.get(i).noteNumber == 0) {
                                currentNotes[0] = 1;
                                currentLengths[0] = getNumNS(resolution, BPM, currentChartNoteSet.get(i).Length);
                            }
                            if (currentChartNoteSet.get(i).noteNumber == 1) {
                                currentNotes[1] = 1;
                                currentLengths[1] = getNumNS(resolution, BPM, currentChartNoteSet.get(i).Length);
                            }
                            if (currentChartNoteSet.get(i).noteNumber == 2) {
                                currentNotes[2] = 1;
                                currentLengths[2] = getNumNS(resolution, BPM, currentChartNoteSet.get(i).Length);
                            }
                            if (currentChartNoteSet.get(i).noteNumber == 3) {
                                currentNotes[3] = 1;
                                currentLengths[3] = getNumNS(resolution, BPM, currentChartNoteSet.get(i).Length);
                            }
                            if (currentChartNoteSet.get(i).noteNumber == 4) {
                                currentNotes[4] = 1;
                                currentLengths[4] = getNumNS(resolution, BPM, currentChartNoteSet.get(i).Length);
                            }
                        }
                        Green = makeType(currentNotes[0], isWhite, isStar, isExtended);
                        Red = makeType(currentNotes[1], isWhite, isStar, isExtended);
                        Yellow = makeType(currentNotes[2], isWhite, isStar, isExtended);
                        Blue = makeType(currentNotes[3], isWhite, isStar, isExtended);
                        Orange = makeType(currentNotes[4], isWhite, isStar, isExtended);
                        PlayScreen.linesList.add(new Line(1, Green, Red, Yellow, Blue, Orange, currentLengths[0], currentLengths[1], currentLengths[2], currentLengths[3], currentLengths[4], false));
                    }


             /*   long realFrames = frames;
                if(previousTick == 0){
                    realFrames = frames + 81;
                }
                double ticks = realFrames/(60.0*1000.0*1000.0)*(resolution*BPM*17.0);


                writeToFile(  ticks+ ": "+numbTicks+ ", "+frames +": "+realFrames + ", "+ Green  + ", "+ Red  + ", "+ Yellow + ", "+ Blue + ", "+ Orange + "\n");
*/
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
        PlayScreen.linesList.add(new Line(5*1000000000.0,
                makeType(0, false, false, false),
                makeType(0, false, false, false),
                makeType(0, false, false, false),
                makeType(0, false, false, false),
                makeType(0, false, false, false),0,0,0,0,0,false));

        if (PlayScreen.linesList.size() > 0) {
            PlayScreen.currentLine = PlayScreen.linesList.remove();
        }
        File myObj = new File("noteDelays.txt");
        if (myObj.delete()) {
            System.out.println("Deleted the file: " + myObj.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
        String filename = pathname+".mp3";
        MP3player mp3Player = new MP3player(filename);//Fixme
        mp3Player.play();
        Game.LastFrameTimeNS = System.nanoTime();

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
    static double remainder = 0;
    //region gets number of NS
    static double getNumNS(double resolution, double bpm, double ticks){
        double total = ((ticks*60.0*1000.0*1000.0)/(resolution*bpm))*1000000;
      /*  int frames = (int) (total + remainder);
        remainder = total +remainder - frames;
        if(remainder < 0 ){
            System.out.println("woop");
        }*/
        return total;
    }
    static void writeToFile(String line){
        File f = new File("highScore.txt");
        FileWriter fw = null;
        try {
            fw = new FileWriter(f,true);
        } catch (IOException e) {
            System.out.println("File not found  =(");
        }
        try{
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(new String(line));
            bw.flush();
            bw.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}//endregion
