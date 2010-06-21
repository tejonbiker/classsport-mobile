/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *
 * en este archivo se prueban las capacidades de FileSystem con NanoXML asi como
 * la lectura de un archivo de prueba. Basicamente lee el archivo horario.xml
 * donde FileSystem se lo permite y lo comienza a analizar.
 *
 */

package hello;

import dicis.afcu.AFCU;
import dicis.rmsu.RMSU;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

import dicis.filesystem.FileSystem;
import java.io.IOException;
import java.util.Enumeration;

import nanoxml.*;
/**
 * @author Tejon
 */
public class NanoTest extends MIDlet implements CommandListener {

    String xmltest=null;

    public void startApp() {

        Form form = new Form("FileSystem");
        form.setCommandListener(this);
        form.addCommand(new Command("Salir",Command.EXIT,0));

        FileSystem fileSystem = new FileSystem();

        try{
            //for(int i=0;i<20;i++) //prueba de enciclamiento
            xmltest = fileSystem.openFile("horario.xml");
            
            System.out.println(xmltest);
            kXMLElement horarioXML = new kXMLElement();
            kXMLElement hijo=null;
            kXMLElement elemento=null;
            Enumeration enumElemento=null;
            horarioXML.parseString(xmltest);

            Enumeration e = horarioXML.enumerateChildren();

            System.out.println("id de archivo: "+horarioXML.getProperty("fileid"));

            //el primer hijo es la version
            hijo =(kXMLElement)e.nextElement();
            System.out.println("version:" +hijo.getProperty("id"));
            System.out.println("desc: "+ hijo.getContents());

            //el siguiente elemento es el horario en si, solo contiene la propiedad de carrera
            hijo =(kXMLElement)e.nextElement();
            System.out.println("carrera: " + hijo.getProperty("tipo"));

            //se comienza a obtener los salones, solo hay un salon, el 201;
            e = hijo.enumerateChildren();
            hijo =(kXMLElement)e.nextElement();
            System.out.println(hijo.getProperty("id"));

            //se obtiene el primer elemento del salon (Federico Ramos)
            e = hijo.enumerateChildren();

            while(e.hasMoreElements())
            {
                hijo =(kXMLElement)e.nextElement();

                //se obtienen todas las propiedades del elemento del horario
                enumElemento =hijo.enumerateChildren();

                elemento = (kXMLElement)enumElemento.nextElement();

                while(enumElemento.hasMoreElements())
                {
                    System.out.println(elemento.getTagName()+": "+elemento.getContents());
                    elemento = (kXMLElement)enumElemento.nextElement();
                }
            }


        }catch(IOException e){e.printStackTrace(); System.exit(-1);}



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
