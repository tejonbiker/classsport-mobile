/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dicis.afcu;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.microedition.io.*;
import javax.microedition.io.file.*;
import java.util.*;

public class AFCU{

    static final String base = "file:///";
    static final Vector queue = new Vector();
    static final Vector fileProc = new Vector();
    static final StringBuffer WorkSpace = new StringBuffer("classsport/");

    static final String ReadmeText = "clsssprt_1.0\n"+
                                     "Archivo generado por la aplicacion Classsport.\n" +
                                     "Esta carpeta es usada por la aplicacion para almacenage\n" +
                                     "de archivos, es recomendable que no agreges archivos\n" +
                                     "a esta carpeta.\n\n" +
                                     "Federico Ramos, DICIS - UGTO.";


    /**
     * @param ext the extention of the file to search in all folders phone, the ext no have point
     * @return an array of all matches with the extention, null is no have matches
     */
    public static final Vector getFilesWith(String ext)
    {
        String              StrVol[];           //the value to return;
        FileConnection      currentFile;        //holds the info of the file to analise, file or folder
        Enumeration         currentFolder;      //holds the info of the childerns if a folder is found
        int                 j=0;                //counter of the search
        StringBuffer        bodyDir;            //holds the dir is analised
        String              reverseExt;
        StringBuffer        extention = new StringBuffer(); //hold the info to reverse the path file and found the ext

        queue.removeAllElements();          //Main Queue, hold the all info of the system folder phone
        fileProc.removeAllElements();       //Queue to process files founded


        /*Get the root volumes*/
        Enumeration volumes = FileSystemRegistry.listRoots();

        /*add to the queue to init the search*/
        while(volumes.hasMoreElements())
            queue.addElement(base + (String)volumes.nextElement());


        /*have any info to start?*/
        if(queue.size()==0)
            return null;

        /* ejemplo de como se va desarrollando la cola de busqueda

         c/
           tere.df
           luis.gto
           pque.ogt
           tejon/

         c/tejon/
                axa.exe
                tejon.apis
                blas.tere

         d/
           abra.ags
           remoto.gto
           abi.lov
           dere/

         
          queue:
         
          c,d,c/tere.df,c/luis.gto,c/pque.ogt,c/tejon/,
         */

        try{

        /*stop the search until we have analise all queue*/
        j=0;
        while(queue.size()!=j)
        {
            try{
            /*open the current folder of the queue*/
            currentFile = (FileConnection)Connector.open((String)queue.elementAt(j));

            /*is a dir?*/
            if(currentFile.isDirectory())
            {
                /*YES, let's begin with the items of the folder*/
                currentFolder = currentFile.list();
                while(currentFolder.hasMoreElements())
                    queue.addElement((String)queue.elementAt(j) + currentFolder.nextElement());
            }
            else
            {
                /*NO, ok, it's a file, let's see if the extention concurr*/
                 String NExt= getExt((String)queue.elementAt(j));

                //System.out.println(NExt);

                //¿is the ext we search?
               if(NExt!=null)
                    if(NExt.equals(ext))
                        fileProc.addElement((String)queue.elementAt(j));        //YES, add to the queue of files found
            }
            currentFile.close();
            }catch(SecurityException e){e.printStackTrace();}
            j++;
            
        }
        }catch(IOException e){e.printStackTrace();}

        return fileProc;
    }

    private static final String getExt(String path)
    {
        try{
        StringBuffer extention = new StringBuffer();
        extention.delete(0, extention.length());
        extention.append(path);

        //reverse the ext to found the point,
        extention = extention.reverse();
        String invExt = (extention.toString().substring(0, extention.toString().indexOf(".")));

        //revese the debuged ext
        extention.delete(0, extention.length());
        extention.append(invExt);
        extention.reverse();

        return extention.toString();
        }catch(StringIndexOutOfBoundsException e){return null;}
    }

    /**
     * Set up the name of WorkSpace (folder name). Check if the name is valid, if no, return false.
     * Example of a workspace: classsport/tek/
     * @param name Name WorkSpace
     */
    public static final boolean setWorkSpaceName(String name)
    {   
        try{

        WorkSpace.delete(0, WorkSpace.length());
        WorkSpace.append(name);

        FileConnection fc = (FileConnection)(Connector.open(base + WorkSpace.toString()));

        if(fc.isDirectory())
        {
            fc.close();
            return true;
        }
        else
        {
            fc.close();
            return false;
        }

        }catch(IllegalArgumentException e)
        {
        }
        catch(IOException e)
        {
        }

        WorkSpace.delete(0,WorkSpace.length());
        WorkSpace.append("classsport/");
        return false;
    }

    public static final String getWorkSpaceName()
    {
        return WorkSpace.toString();
    }
   
    /**
     * This method create or search a folder (WorkSpace) in all file system phone. You can specify the name
     * of the folder with <b>AFCU.setWorkSpaceName(WS)</b> method.
     * 
     * @return The folder searched, if no exist, create. If returns null Threre's no way to setup a workspace
     * @throws SecurityException
     */
    public static final String setUpWorkSpace()
    {
        String              StrVol[];           //the value to return;
        FileConnection      currentFile;        //holds the info of the file to analise, file or folder
        FileConnection      dirCreate;
        Enumeration         currentFolder;      //holds the info of the childerns if a folder is found
        int                 j=0;                //counter of the search
        StringBuffer        bodyDir;            //holds the dir is analised
        String              reverseExt;
        StringBuffer        extention = new StringBuffer(); //hold the info to reverse the path file and found the ext

        queue.removeAllElements();          //Main Queue, hold the all info of the system folder phone
        fileProc.removeAllElements();       //Queue to process files founded


        /*Get the root volumes*/
        Enumeration volumes = FileSystemRegistry.listRoots();

        /*add to the queue to init the search*/
        while(volumes.hasMoreElements())
            queue.addElement(base + (String)volumes.nextElement());


        /*have any info to start?*/
        if(queue.size()==0)
            return null;             //dev: needs to setup a exception

        try{

        /*stop the search until we have analise all queue*/
        j=0;
        while(queue.size()!=j)
        {
            try{
            /*open the current folder of the queue*/
            currentFile = (FileConnection)Connector.open((String)queue.elementAt(j));


            /*is a dir?*/
            if(currentFile.isDirectory())
            {

                /*YES, let's begin with the write of the folder*/
                if(currentFile.canWrite())
                {
                    /*ok let's try to create the folder*/
                    dirCreate = (FileConnection)Connector.open((String)queue.elementAt(j) + WorkSpace.toString());

                    if(!dirCreate.exists()) //if the folder exist only add to the find, other, create
                    {

                        currentFile.close();                    //close the current file, don't need
                        System.err.println(dirCreate.getURL());
                        dirCreate.mkdir();                      //create the dir, (WorkSpace)
                        dirCreate.close();                      //close the dirCreate, don't need

                        //write a little readme file in the workspace
                        currentFile = (FileConnection)Connector.open(dirCreate.getURL() + "readme.txt");
                        currentFile.create();

                        DataOutputStream fileStream = currentFile.openDataOutputStream();

                        fileStream.writeChars(ReadmeText);

                        //close all and exit
                        fileStream.close();
                        currentFile.close();
                        return dirCreate.getURL();
                    }
                    else
                    {
                        System.err.println("No se pudo escribir archivo:"+ dirCreate.canWrite()+", "+dirCreate.exists()+","+dirCreate.getURL());

                        currentFile.close();
                        dirCreate.close();

                        currentFile = (FileConnection)Connector.open(dirCreate.getURL() + "readme.txt");


                        if(currentFile.exists())
                            currentFile.delete();

                        currentFile.create();
                        DataOutputStream fileStream = currentFile.openDataOutputStream();

                        fileStream.writeChars(ReadmeText);

                        currentFile.close();
                        fileStream.close();
                        return dirCreate.getURL();
                    }
                }

                /*continue the search*/
                currentFolder = currentFile.list();
                   while(currentFolder.hasMoreElements())
                        queue.addElement((String)queue.elementAt(j) + currentFolder.nextElement());
            }
            }catch(SecurityException e)
            {}
            j++;
        }
        }catch(IOException e)
        {e.printStackTrace();}

        return null;
    }

}
