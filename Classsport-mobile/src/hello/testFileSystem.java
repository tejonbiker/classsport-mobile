/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *
 * prueba y ejemplo de la clase FileSystem, el cual simplifica el uso de RMSU y AFCU, basicamente
 * busca donde pueda crear una sitio donde pueda crear una carpeta y si esta carpeta
 * ha sido previamente creada simplemente accesa a ella por medio de RMS (RMSU)
 *
 * cualquier archivo que se quiera abrir o crear se hace donde la carpeta de FileSystem
 * es ubicada.
 */

package hello;

import dicis.afcu.AFCU;
import dicis.rmsu.RMSU;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

import dicis.filesystem.FileSystem;
import java.io.IOException;
/**
 * @author Tejon
 */
public class testFileSystem extends MIDlet implements CommandListener {

    public void startApp() {

        Form form = new Form("FileSystem");
        form.setCommandListener(this);
        form.addCommand(new Command("Salir",Command.EXIT,0));
        
        String content = "Esto es una prueba de FileSystem";

        FileSystem fileSystem = new FileSystem();

        try{
            fileSystem.writeFile("active.xml",content);
            form.append(fileSystem.openFile("active.xml"));
        }catch(IOException e){e.printStackTrace();}

        form.append(fileSystem.typeOfGetting());
        Display.getDisplay(this).setCurrent(form);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command cmnd, Displayable dsplbl) {
        destroyApp(false);
        notifyDestroyed();
    }
}
