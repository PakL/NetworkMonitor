package de.pakldev.networkmonitor;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author PakL
 */
public class NetworkMonitor {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
        } catch( Exception ex ) {}
        
        if( System.getProperty( "os.name" ).toLowerCase().contains( "windows" ) ) {
            new NetworkMonitor();
        } else {
            JOptionPane.showMessageDialog( (JFrame)null, "Only Windows is supported. I'm sorry!", "Incompatible Operation System", JOptionPane.ERROR_MESSAGE );
        }
    }
    
    
    private NetworkMonitorGUI gui;
    private WinNetworkInterface network;
    
    public NetworkMonitor() {
        network = new WinNetworkInterface();
        gui = new NetworkMonitorGUI( network );
        network.init();
        gui.setInterfaces( network.getInterfaces() );
    }
}
