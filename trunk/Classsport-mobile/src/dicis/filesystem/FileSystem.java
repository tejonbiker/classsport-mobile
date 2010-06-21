/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dicis.filesystem;


import dicis.afcu.AFCU;
import dicis.rmsu.RMSU;
import java.io.DataInputStream;

import java.util.Enumeration;
import javax.microedition.rms.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.microedition.io.*;
import javax.microedition.io.file.*;
import java.util.*;
/**
 *
 * @author Tejon
 */
public class FileSystem {

    private final int MAX_STREAM = 1024*100;

    StringBuffer dataFile;
    StringBuffer WorkSpacePath;
    String       getting=null;
    byte[]       byteStream;


    public FileSystem()
    {
        dataFile = new StringBuffer();
        WorkSpacePath = new StringBuffer();
        byteStream = new byte[MAX_STREAM];

        String ws = RMSU.getWorkSpace();

        if(ws==null)
        {
            ws = AFCU.setUpWorkSpace();

            if(ws==null)
                //una excepcion es necesaria aqui.
                System.out.println("Cannot create WorkSpace");
            else
            {
                WorkSpacePath.append(ws);
                RMSU.setWorkSpace(ws);
                getting = "WorkSpace obtenido con AFCU";
                System.out.println(getting);
            }
        }
        else
        {
            WorkSpacePath.append(ws);
            getting = "WorkSpace obtenido con RMSU";
            System.out.println(getting);
        }
    }

    public String typeOfGetting()
    {
        return getting;
    }

    public String getWorkSpacePath()
    {
        return WorkSpacePath.toString();
    }

    public void setWorkSpaceName(String NewName)
    {
        AFCU.setWorkSpaceName(NewName);
    }

    public String getWorkSpaceName()
    {
        return AFCU.getWorkSpaceName();
    }

    public String openFile(String FileName) throws IOException
    {
        DataInputStream dataStream;
        byte byteStream[];
        FileConnection file = (FileConnection)Connector.open(WorkSpacePath.toString()+FileName);

        dataStream = file.openDataInputStream();
        dataFile.delete(0, dataFile.length());



        //System.out.println(dataStream.read(byteStream));
        byteStream = new byte[dataStream.available()];

        dataStream.read(byteStream);

        /*
        while(dataStream.available()!=0)
            dataFile.append(dataStream.readChar());
         */

        file.close();
        dataStream.close();
        //return dataFile.toString();
        return new String(byteStream);
    }

    public void writeFile(String FileName,String content)throws IOException
    {
        DataOutputStream dataStream;
        FileConnection file = (FileConnection)Connector.open(WorkSpacePath.toString()+FileName);

        if(!file.exists())
            file.create();

        dataStream = file.openDataOutputStream();

        dataStream.writeChars(content);

        file.close();
        dataStream.close();
    }

}
