/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dicis.rmsu;

import java.util.Enumeration;
import javax.microedition.rms.*;

/**
 *
 * @author Tejon
 */
public class RMSU {

    static RecordStore WorkSpace=null;

    public static final String getWorkSpace()
    {
        try {
            byte[] StringBytes;
            WorkSpace = RecordStore.openRecordStore("workspace", false);
            StringBytes = WorkSpace.getRecord(1);
            WorkSpace.closeRecordStore();
            return new String(StringBytes);
        } catch (RecordStoreException ex) {
            return null;
        }
    }

    public static final void setWorkSpace(String pathWorkSpace)
    {
        try{
            byte[] path;
            path = pathWorkSpace.getBytes();
            int idRecord;

            WorkSpace = RecordStore.openRecordStore("workspace", true);

            RecordEnumeration record = WorkSpace.enumerateRecords(null, null, false);

            while(record.hasNextElement())
                WorkSpace.deleteRecord(record.nextRecordId());

            System.err.println("id ws: "+WorkSpace.addRecord(path,0,path.length));

            WorkSpace.closeRecordStore();
            
        }catch(RecordStoreException e){
            e.printStackTrace();
        };

    }
}
