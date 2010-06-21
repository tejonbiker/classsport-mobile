/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hello;

import dicis.afcu.AFCU;
import dicis.rmsu.RMSU;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

/**
 * @author Tejon
 */
public class classs_rms_afcu extends MIDlet implements CommandListener{

    Form form = new Form("tejon");
    Display display;


    public void startApp() {

        display = Display.getDisplay(this);
        form.addCommand(new Command("salir",1,Command.EXIT));
        form.setCommandListener(this);
        Vector files=null;

        //for(int i=0;i<200;i++){files = AFCU.getFilesWith("blend");}


        /*
        try{

        form.append(AFCU.setUpWorkSpace());

        }catch(SecurityException e)
        {form.append("cannot setup the workspace in automatic mode, ");}
         */

        String ws = RMSU.getWorkSpace();

        if(ws==null)
        {
            ws = AFCU.setUpWorkSpace();
            RMSU.getWorkSpace();

            if(ws==null)
                form.append("No se pudo crear el WorkSpace");
            else
            {
                form.append("El WorkSpace por busqueda es:\n "+ws);
                RMSU.setWorkSpace(ws);
            }
        }
        else
        {
            form.append("El WorkSpace por RMS es: "+ws);
        }

        try{

        FileConnection fc = (FileConnection)Connector.open(ws + "readme.txt");
        //DataInputStream fileStream = fc.openDataInputStream();
        DataInputStream fileStream = fc.openDataInputStream();
        byte data[]=new byte[(int)fc.fileSize()];
        int readData;

        form.append(fc.getURL()+", r:"+fc.canRead()+", s: "+fc.fileSize()+",w:"+fc.canWrite());

        StringBuffer stringData = new StringBuffer();

        System.out.println(fileStream.available());

        /*
        readData = fileStream.read(data);


        for(int i=0;i<readData;i++)
        {
            stringData.append((char)data[i]);
        }*/

        while(fileStream.available()!=0)
            stringData.append(fileStream.readChar());

        form.append(stringData.toString()+"\ntamaño del archivo (bytes): "+fc.fileSize());

         fileStream.close();
        fc.close();


        }catch(IOException e)
        {
            e.printStackTrace();
            form.append("no se pudo abrir el archivo readme");
        }

        /*
        files = AFCU.getFilesWith("mp3");


        if(files!=null)
        {
            for(int i=0;i<files.size();i++)
            {
                form.append((String)files.elementAt(i)+"\n");
            }
        }
        else
        {
            form.append("No se detectaron volumenes");
        }*/

        display.setCurrent(form);
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
