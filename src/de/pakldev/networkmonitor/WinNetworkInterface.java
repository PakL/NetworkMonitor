package de.pakldev.networkmonitor;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComFailException;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.EnumVariant;
import com.jacob.com.Variant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author PakL
 */
public class WinNetworkInterface {
    
    public static String table = "Win32_PerfFormattedData_Tcpip_NetworkInterface";
    public static String connectStr = "winmgmts:{impersonationLevel=impersonate}!\\\\localhost\\root\\CIMV2";
    
    private List<String> interfaceNames = new ArrayList();
    private ActiveXComponent axWMI;
    
    public void init() {
        ComThread.InitSTA();
        axWMI = new ActiveXComponent( connectStr );
        
        String query = "SELECT Name FROM " + table;
        Variant vCollection = axWMI.invoke( "ExecQuery", new Variant( query ) );
        try {
            EnumVariant enumVariant = new EnumVariant( vCollection.toDispatch() );
            Dispatch item = null;
            while( enumVariant.hasMoreElements() ) {
                item = enumVariant.nextElement().toDispatch();

                String Name = Dispatch.call( item, "Name" ).toString();
                interfaceNames.add( Name );
            }
        } catch( ComFailException ex ) {
            System.err.println( ex );
            JOptionPane.showMessageDialog( (JFrame)null, "There was an error getting some ActiveX-Informations.\nThat happens. Just retry starting!", "ActiveX-Error", JOptionPane.ERROR_MESSAGE );
            System.exit( -1 );
        } finally {
            ComThread.Release();
        }
    }
    
    public String[] getInterfaces() {
        String[] names = new String[interfaceNames.size()];
        for( int i = 0; i < interfaceNames.size(); i++ ) {
            names[i] = interfaceNames.get( i );
        }
        
        return names;
    }
    
    
    
    private HashMap<String, HashMap<String, String>> map = new HashMap();
    private HashMap<String, Long> lastupdate = new HashMap();
    
    private void update( String interfaceName ) {
        ComThread.InitSTA();
        axWMI = new ActiveXComponent( connectStr );
        
        String query = "SELECT * FROM " + table + " WHERE Name = '" + interfaceName + "'";
        Variant vCollection = axWMI.invoke( "ExecQuery", new Variant( query ) );
        
        long result = 0;
        try {
            EnumVariant enumVariant = new EnumVariant( vCollection.toDispatch() );
            Dispatch item = null;
            HashMap<String, String> m = new HashMap();
            while( enumVariant.hasMoreElements() ) {
                item = enumVariant.nextElement().toDispatch();

                m.put( "BytesReceivedPerSec", Dispatch.call( item, "BytesReceivedPerSec" ).toString() );
                m.put( "BytesSentPerSec",     Dispatch.call( item, "BytesSentPerSec" ).toString() );
            }
            map.put( interfaceName, m );
            lastupdate.put( interfaceName, System.currentTimeMillis() );
        } catch( ComFailException ex ) {
        } finally {
            ComThread.Release();
        }
        
    }
    
    
    public long getLong( String interfaceName, String attr ) {
        long result = -1;
        
        if( lastupdate.containsKey( interfaceName ) ) {
            long last = lastupdate.get( interfaceName );
            if( (last+100) < System.currentTimeMillis() ) {
                update( interfaceName );
            }
        } else {
            update( interfaceName );
        }
        
        if( map.containsKey( interfaceName ) ) {
            HashMap<String, String> m = map.get( interfaceName );
            if( m.containsKey( attr ) ) {
                result = Long.parseLong( m.get( attr ) );
            }
        }
        
        return result;
    }
    
    public String getString( String interfaceName, String attr ) {
        String result = "";
        
        if( lastupdate.containsKey( interfaceName ) ) {
            long last = lastupdate.get( interfaceName );
            if( (last+100) < System.currentTimeMillis() ) {
                update( interfaceName );
            }
        } else {
            update( interfaceName );
        }
        
        if( map.containsKey( interfaceName ) ) {
            HashMap<String, String> m = map.get( interfaceName );
            if( m.containsKey( attr ) ) {
                result = m.get( attr );
            }
        }
        
        return result;
    }
    
}
