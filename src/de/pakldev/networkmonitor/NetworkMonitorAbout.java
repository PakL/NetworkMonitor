package de.pakldev.networkmonitor;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 *
 * @author PakL
 */
public class NetworkMonitorAbout extends JDialog implements MouseListener, MouseMotionListener {
    
    public NetworkMonitorAbout(  ) {
        this.setTitle( "About: NetworkMonitor" );
        this.setSize( 340, 120 );
        this.setLocationRelativeTo( null );
        this.setResizable( false );
        
        Container contentPane = this.getContentPane();
        
        JLabel dialog = new JLabel( "<html><table>"
                + "<tr><td><b>Author:</b></td><td>Pascal Pohl</td></tr>"
                + "<tr><td><b>Website:</b></td><td><a href=\"\">pakldev.de</a></td></tr>"
                + "<tr><td valign=\"top\"><b>More Credits:</b></td><td>Small font by eiszfuchs (<a href=\"\">eisfuchslabor.de</a>)<br>"
                + "NetworkMonitor uses the JACOB library<br>(<a href=\"\">http://sourceforge.net/projects/jacob-project/</a>)</td></tr>"
                + "</table></html>" );
        
        contentPane.add( dialog );
        
        this.addMouseListener( this );
        this.addMouseMotionListener( this );
        this.setDefaultCloseOperation( JDialog.HIDE_ON_CLOSE );
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        String url = "";
        if( pakldev.contains(x, y) ) {
            url = "http://pakldev.de";
        } else if( eisfuchslabor.contains(x, y) ) {
            url = "http://eisfuchslabor.de";
        } else if( jacob.contains(x, y) ) {
            url = "http://sourceforge.net/projects/jacob-project/";
        }
        
        if( !url.isEmpty() ) {
            if( e.getClickCount() == 1 ) {
                try {
                    // Quick and dirty... we are on Windows, anyway...
                    Runtime.getRuntime().exec( "cmd.exe /c start " + url );
                } catch (IOException ex) {}
            }
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseDragged(MouseEvent e) {}

    private Rectangle pakldev = new Rectangle( 86, 51, 54, 13 );
    private Rectangle eisfuchslabor = new Rectangle( 202, 69, 82, 13 );
    private Rectangle jacob = new Rectangle( 89, 99, 228, 13 );
    
    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        
        if( pakldev.contains(x, y) ) {
            // pakldev.de
            this.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
        } else if( eisfuchslabor.contains(x, y) ) {
            // eisfuchlabor.de
            this.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
        } else if( jacob.contains(x, y) ) {
            // sourceforge.net/projects/jacob-project/
            this.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
        } else {
            this.setCursor( Cursor.getDefaultCursor() );
        }
        
    }
}
