import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import org.libsdl.SDL;
import org.libsdl.SDL_Error;
import uk.co.electronstudio.sdl2gdx.SDL2ControllerManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * Created by cg8200 on 10/21/221.
 */
public class Game  implements ActionListener,KeyListener,MouseListener {
    Renderer renderer;
    static Game game;
    static boolean mainMenuIsActive;
    static boolean songSelectIsActive;
    static boolean playIsActive;
    static boolean creditsIsActive;
    MainMenu mainMenu;
    SongSelect songSelect;
    PlayScreen playScreen;
    Credits credits;
    static long LastFrameTimeNS = 0;

    private  Controller recent;
     SDL2ControllerManager manager;
    enum ButtonCode {
        GREEN, RED, YELLOW, BLUE, ORANGE, STRUM_UP, STRUM_DOWN,  D_LEFT, D_RIGHT, BACK, START, BAD
    }

    public Game(){
        // region screen creation
        mainMenu = new MainMenu();
        songSelect = new SongSelect();
        playScreen = new PlayScreen();
        credits = new Credits();
        //endregion
        Timer timer = new Timer(17, this);//setup framerate
       //region window setup
        JFrame jframe = new JFrame();
        renderer = new Renderer();
        renderer.setBackground(Color.BLACK);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width =(int) screenSize.getWidth();
        int height =(int) screenSize.getHeight();
        jframe.add(renderer);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(width, height);
        jframe.setResizable(false);
        //region mouse and key listener
        jframe.addKeyListener(this);
        jframe.addMouseListener(this);
        //endregion
        jframe.setVisible(true);
        //endregion
        manager = new SDL2ControllerManager();
       //region controller setup
        manager.addListenerAndRunForConnectedControllers(new ControllerListener() {
            @Override
            public void connected(Controller controller) {
                //System.out.println("*** CONNECTED "+controller);
                recent=controller;
            }

            @Override
            public void disconnected(Controller controller) {
               // System.out.println("*** DISCONNECTED "+controller);
            }

            @Override
            public boolean buttonDown(Controller controller, int buttonCode) {
                handleButton(getButtonCode(buttonCode));
                return false;
            }

            @Override
            public boolean buttonUp(Controller controller, int buttonCode) {
                handleButtonRelease(getButtonCode(buttonCode));
                return false;
            }

            @Override
            public boolean axisMoved(Controller controller, int axisCode, float value) {
                //System.out.println("AXIS MOVED "+controller+" "+axisCode+" "+value);
                return false;
            }

            @Override
            public boolean povMoved(Controller controller, int povCode, PovDirection value) {
              //  System.out.println("POV MOVED +"+controller+" "+povCode+" "+value.toString());
                return false;
            }

            @Override
            public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
              //  System.out.println("XSLIDER MOVED +"+controller+" "+sliderCode+" "+value);
                return false;
            }

            @Override
            public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
              //  System.out.println("YSLIDER MOVED +"+controller+" "+sliderCode+" "+value);
                return false;
            }

            @Override
            public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
               // System.out.println("ACCELEROMETER MOVED +"+controller+" "+accelerometerCode+" "+value);
                return false;
            }
        });
        //endregion
        timer.start();
    }
    private void writeToFile(String fileName, String text){
        File f = new File(fileName);
        FileWriter fw = null;
        try {
            fw = new FileWriter(f);
        } catch (IOException e) {
            System.out.println("File not found  =(");
        }
        try{
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(String.valueOf(text));
            bw.flush();
            bw.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    // action performed = called once per frame
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            manager.pollState();

        } catch (SDL_Error sdl_error) {
            sdl_error.printStackTrace();
        }

       //region updating current screen
        if (mainMenuIsActive){
            mainMenu.update();
        }else if(songSelectIsActive){
            songSelect.update();
        }else if(playIsActive){
            playScreen.update();
        }else if(creditsIsActive){
            credits.update();
        }
        //endregion
        renderer.repaint();// calls repaint

    }

    public void repaint(Graphics g) {
        //region draw current screen
        if (mainMenuIsActive) {
            mainMenu.draw(g);
        } else if (songSelectIsActive) {
            songSelect.draw(g);
        } else if (playIsActive) {
            playScreen.draw(g);
        } else if (creditsIsActive) {
            credits.draw(g);
        }
        //endregion
    }
     //region used events
    @Override
    public void keyPressed(KeyEvent e) {

        handleButton(getButtonCode(e));
    }
    @Override
    public void keyReleased(KeyEvent e) {
        handleButtonRelease(getButtonCode(e));
    }
    //endregion
    // region unused events
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    //endregion

    //region mapping of controller buttons
 public ButtonCode getButtonCode(int buttonCode){
        if(buttonCode == 0){
            return ButtonCode.GREEN;
        }else if(buttonCode == 1){
            return ButtonCode.RED;
        }else if(buttonCode == 3){
            return ButtonCode.YELLOW;
        }else if(buttonCode == 2){
            return ButtonCode.BLUE;
        }else if(buttonCode == 9){
            return ButtonCode.ORANGE;
        }else if(buttonCode == 12){
            return ButtonCode.STRUM_DOWN;
        }else if(buttonCode == 11){
            return ButtonCode.STRUM_UP;
        }else if(buttonCode == 13){
            return ButtonCode.D_LEFT;
        }else if(buttonCode == 14){
            return ButtonCode.D_RIGHT;
        }else if(buttonCode == 4){
            return ButtonCode.BACK;
        }else if(buttonCode == 6){
            return ButtonCode.START;
        }
    return ButtonCode.BAD;
}
//endregion
    //region mapping of keyboard keys
     public ButtonCode getButtonCode(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_1){
            return ButtonCode.GREEN;
        }else if(e.getKeyCode() == KeyEvent.VK_2){
            return ButtonCode.RED;
        }else if(e.getKeyCode() == KeyEvent.VK_3){
            return ButtonCode.YELLOW;
        }else if(e.getKeyCode() == KeyEvent.VK_4){
            return ButtonCode.BLUE;
        }else if(e.getKeyCode() == KeyEvent.VK_5){
            return ButtonCode.ORANGE;
        }else if(e.getKeyCode() == KeyEvent.VK_UP){
            return ButtonCode.STRUM_UP;
        }else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            return ButtonCode.STRUM_DOWN;
        }else if(e.getKeyCode() == KeyEvent.VK_SPACE){
            return ButtonCode.STRUM_DOWN;
        }else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
            return ButtonCode.BACK;
        }else if(e.getKeyCode() == KeyEvent.VK_ENTER){
            return ButtonCode.START;
        }
        return ButtonCode.BAD;
    }
    //endregion

    //region do things on button press
     public void handleButton(ButtonCode buttonCode) {
         //region main menu
         if (mainMenuIsActive) {

             if (buttonCode == ButtonCode.STRUM_UP) {
                 mainMenu.changeSelected(true);
             } else if (buttonCode == ButtonCode.STRUM_DOWN) {
                 mainMenu.changeSelected(false);
             } else if (buttonCode == ButtonCode.GREEN) {
                 mainMenu.Select();
             }
         }
         //endregion
         else if (songSelectIsActive) {
             if (buttonCode == ButtonCode.STRUM_UP) {
                 songSelect.changeSelected(true);
             } else if (buttonCode == ButtonCode.STRUM_DOWN) {
                 songSelect.changeSelected(false);
             } else if (buttonCode == ButtonCode.GREEN) {
                 songSelect.Select();
             }
         } else if (playIsActive) {
             if (buttonCode == ButtonCode.GREEN || buttonCode == ButtonCode.RED || buttonCode == ButtonCode.YELLOW || buttonCode == ButtonCode.BLUE || buttonCode == ButtonCode.ORANGE || buttonCode == ButtonCode.STRUM_UP || buttonCode == ButtonCode.STRUM_DOWN) {
                 playScreen.ButtonPressed(buttonCode);
             }

         } else if (creditsIsActive) {
             if (buttonCode == ButtonCode.GREEN) {
                 credits.Select();
             }
         }

     }
     //endregion
    public void handleButtonRelease(ButtonCode buttonCode){
        if(playIsActive){
            if (buttonCode == ButtonCode.GREEN||buttonCode == ButtonCode.RED||buttonCode == ButtonCode.YELLOW||buttonCode == ButtonCode.BLUE||buttonCode == ButtonCode.ORANGE){
                playScreen.FretReleased(buttonCode);
            }

        }
    }
    public static void main(String[] args) {
        mainMenuIsActive = true;
        playIsActive = false;
        songSelectIsActive = false;


        SDL.SDL_SetHint("SDL_XINPUT_ENABLED", "0");

        game = new Game();
    }
}