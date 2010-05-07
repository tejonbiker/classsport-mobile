/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dicis.rmsu;

import javax.microedition.rms.*;

/**
 *
 * @author Tejon
 */
public class RMSU {

    static RecordStore WorkSpace;

    public static final String getWorkSpace()
    {
        try {
            WorkSpace = RecordStore.openRecordStore("workspace", false);
            return new String(WorkSpace.getRecord(0));
        } catch (RecordStoreException ex) {
            return null;
        }
    }
}
