/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *
 * una simple prueba del paquete RMSU, que es para extender la capacidad RMS
 * para ser usada mas simple.
 */

package hello;

import dicis.rmsu.RMSU;
import dicis.afcu.AFCU;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.midlet.*;

/**
 * @author Tejon
 */
public class RMSUtest extends MIDlet implements CommandListener{


    public void startApp() {

        Form form = new Form("test RMSU");

        RMSU.setWorkSpace(AFCU.setUpWorkSpace());
        System.out.println(RMSU.getWorkSpace());

        form.setCommandListener(this);
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
