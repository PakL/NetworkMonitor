package de.pakldev.networkmonitor;

import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

/**
 *
 * @author PakL
 */
public class NetworkMonitorGUI extends JFrame {
    
    private final NetworkMonitorGUI main = this;
    private final WinNetworkInterface network;
        
    private JComboBox comboBox = new JComboBox();
    private JLabel lbl_outgoing = new JLabel( "<html><b>Upload:</b></html>" );
    private JLabel lbl_incoming = new JLabel( "<html><b>Download:</b></html>" );
    private JLabel outgoing = new JLabel( "0 KB/s" );
    private JLabel incoming = new JLabel( "0 KB/s" );
    
    private TrafficDiagramm out = new TrafficDiagramm();
    private TrafficDiagramm in = new TrafficDiagramm( );
    
    private Refresher refresher = null;
    
    public NetworkMonitorGUI( final WinNetworkInterface interfaces ) {
        this.setUndecorated( true );
        
        this.network = interfaces;
        this.setTitle( "NetworkMonitor" );
        Image icon_16 = new ImageIcon( NetworkMonitorGUI.class.getResource( "res/icon.png" ) ).getImage();
        Image icon_32 = new ImageIcon( NetworkMonitorGUI.class.getResource( "res/icon_32.png" ) ).getImage();
        Image icon_64 = new ImageIcon( NetworkMonitorGUI.class.getResource( "res/icon_64.png" ) ).getImage();
        List<Image> iconimages = new ArrayList();
        iconimages.add(icon_64);
        iconimages.add(icon_32);
        iconimages.add(icon_16);
        this.setIconImages( iconimages );
        
        Rectangle dim = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        this.setBounds( dim.width - 300, dim.height - 200, 300, 200 );
        
        this.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit( 0 );
            }
        });
        
        SpringLayout layout = new SpringLayout();
        this.setContentPane( new WindowBackground() );
        Container contentPane = this.getContentPane();
        
        
        JButton closeButton = new JButton();
        closeButton.setBorder(null);
        closeButton.setBorderPainted(false);
        closeButton.setMargin(new Insets(0,0,0,0));
        closeButton.setIcon( new ImageIcon( NetworkMonitorGUI.class.getResource( "res/close.png" ) ) );
        closeButton.setRolloverIcon( new ImageIcon( NetworkMonitorGUI.class.getResource( "res/close_over.png" ) ) );
        closeButton.setPressedIcon( new ImageIcon( NetworkMonitorGUI.class.getResource( "res/close_click.png" ) ) );
        layout.putConstraint( SpringLayout.NORTH, closeButton, 0,   SpringLayout.NORTH, contentPane );
        layout.putConstraint( SpringLayout.EAST,  closeButton, -5,  SpringLayout.EAST,  contentPane );
        layout.putConstraint( SpringLayout.WEST,  closeButton, -23, SpringLayout.EAST,  closeButton  );
        layout.putConstraint( SpringLayout.SOUTH, closeButton, 16,  SpringLayout.NORTH, closeButton );
        contentPane.add( closeButton );
        closeButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit( 0 );
            }
        });
        
        JButton minButton = new JButton();
        minButton.setBorder(null);
        minButton.setBorderPainted(false);
        minButton.setMargin(new Insets(0,0,0,0));
        minButton.setIcon( new ImageIcon( NetworkMonitorGUI.class.getResource( "res/min.png" ) ) );
        minButton.setRolloverIcon( new ImageIcon( NetworkMonitorGUI.class.getResource( "res/min_over.png" ) ) );
        minButton.setPressedIcon( new ImageIcon( NetworkMonitorGUI.class.getResource( "res/min_click.png" ) ) );
        layout.putConstraint( SpringLayout.NORTH, minButton, 0,   SpringLayout.NORTH, contentPane );
        layout.putConstraint( SpringLayout.EAST,  minButton, -5,  SpringLayout.WEST,  closeButton );
        layout.putConstraint( SpringLayout.WEST,  minButton, -23, SpringLayout.EAST,  minButton  );
        layout.putConstraint( SpringLayout.SOUTH, minButton, 16,  SpringLayout.NORTH, minButton );
        contentPane.add( minButton );
        minButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.setExtendedState( JFrame.ICONIFIED );
                main.requestFocus();
            }
        });
        
        JButton aboutButton = new JButton();
        aboutButton.setBorder(null);
        aboutButton.setBorderPainted(false);
        aboutButton.setMargin(new Insets(0,0,0,0));
        aboutButton.setIcon( new ImageIcon( NetworkMonitorGUI.class.getResource( "res/about.png" ) ) );
        aboutButton.setRolloverIcon( new ImageIcon( NetworkMonitorGUI.class.getResource( "res/about_over.png" ) ) );
        aboutButton.setPressedIcon( new ImageIcon( NetworkMonitorGUI.class.getResource( "res/about_click.png" ) ) );
        layout.putConstraint( SpringLayout.NORTH, aboutButton, 0,   SpringLayout.NORTH, contentPane );
        layout.putConstraint( SpringLayout.EAST,  aboutButton, -5,  SpringLayout.WEST,  minButton );
        layout.putConstraint( SpringLayout.WEST,  aboutButton, -23, SpringLayout.EAST,  aboutButton  );
        layout.putConstraint( SpringLayout.SOUTH, aboutButton, 16,  SpringLayout.NORTH, aboutButton );
        contentPane.add( aboutButton );
        aboutButton.addActionListener( new ActionListener() {
            private NetworkMonitorAbout ADialog = new NetworkMonitorAbout();
            @Override
            public void actionPerformed(ActionEvent e) {
                ADialog.setVisible( true );
                main.requestFocus();
            }
        });
        
        JLabel title = new JLabel( "<html><b><font color=\"#FFFFFF\">" + this.getTitle() + "</font></b></html>" );
        layout.putConstraint( SpringLayout.NORTH, title, 2,  SpringLayout.NORTH, contentPane );
        layout.putConstraint( SpringLayout.EAST,  title, -5, SpringLayout.WEST,  aboutButton );
        layout.putConstraint( SpringLayout.WEST,  title, 5,  SpringLayout.WEST,  contentPane  );
        layout.putConstraint( SpringLayout.SOUTH, title, 25, SpringLayout.NORTH, title );
        contentPane.add( title );
        
        
        layout.putConstraint( SpringLayout.SOUTH, in, -10, SpringLayout.SOUTH, contentPane );
        layout.putConstraint( SpringLayout.NORTH, in, -40, SpringLayout.SOUTH, in );
        layout.putConstraint( SpringLayout.WEST,  in, 10,  SpringLayout.WEST,  contentPane );
        layout.putConstraint( SpringLayout.EAST,  in, -10, SpringLayout.EAST,  contentPane );
        contentPane.add( in );
        
        layout.putConstraint( SpringLayout.SOUTH, lbl_incoming, -5,  SpringLayout.NORTH, in );
        layout.putConstraint( SpringLayout.NORTH, lbl_incoming, -15, SpringLayout.SOUTH, lbl_incoming );
        layout.putConstraint( SpringLayout.WEST,  lbl_incoming, 10,  SpringLayout.WEST,  contentPane );
        layout.putConstraint( SpringLayout.EAST,  lbl_incoming, 60,  SpringLayout.WEST,  lbl_incoming );
        contentPane.add( lbl_incoming );
        
        layout.putConstraint( SpringLayout.SOUTH, incoming, -5,  SpringLayout.NORTH, in );
        layout.putConstraint( SpringLayout.NORTH, incoming, -15, SpringLayout.SOUTH, incoming );
        layout.putConstraint( SpringLayout.WEST,  incoming, 10,  SpringLayout.EAST,  lbl_incoming );
        layout.putConstraint( SpringLayout.EAST,  incoming, -10, SpringLayout.EAST,  contentPane );
        contentPane.add( incoming );
        
        layout.putConstraint( SpringLayout.SOUTH, out, -10, SpringLayout.NORTH, lbl_incoming );
        layout.putConstraint( SpringLayout.NORTH, out, -40, SpringLayout.SOUTH, out );
        layout.putConstraint( SpringLayout.WEST,  out, 10,  SpringLayout.WEST,  contentPane );
        layout.putConstraint( SpringLayout.EAST,  out, -10, SpringLayout.EAST,  contentPane );
        contentPane.add( out );
        
        layout.putConstraint( SpringLayout.SOUTH, lbl_outgoing, -5,  SpringLayout.NORTH, out );
        layout.putConstraint( SpringLayout.NORTH, lbl_outgoing, -15, SpringLayout.SOUTH, lbl_outgoing );
        layout.putConstraint( SpringLayout.WEST,  lbl_outgoing, 10,  SpringLayout.WEST,  contentPane );
        layout.putConstraint( SpringLayout.EAST,  lbl_outgoing, 60,  SpringLayout.WEST,  lbl_outgoing );
        contentPane.add( lbl_outgoing );
        
        layout.putConstraint( SpringLayout.SOUTH, outgoing, -5,  SpringLayout.NORTH, out );
        layout.putConstraint( SpringLayout.NORTH, outgoing, -15, SpringLayout.SOUTH, outgoing );
        layout.putConstraint( SpringLayout.WEST,  outgoing, 10,  SpringLayout.EAST,  lbl_outgoing );
        layout.putConstraint( SpringLayout.EAST,  outgoing, -10, SpringLayout.EAST,  contentPane );
        contentPane.add( outgoing );
        
        layout.putConstraint( SpringLayout.SOUTH, comboBox, -5,  SpringLayout.NORTH, lbl_outgoing );
        layout.putConstraint( SpringLayout.NORTH, comboBox, 5,   SpringLayout.SOUTH, title );
        layout.putConstraint( SpringLayout.WEST,  comboBox, 10,  SpringLayout.WEST,  contentPane );
        layout.putConstraint( SpringLayout.EAST,  comboBox, -10, SpringLayout.EAST,  contentPane );
        comboBox.setEnabled( false );
        comboBox.addItem( "Lade Daten..." );
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if( e.getStateChange() == ItemEvent.SELECTED ) {
                    if( refresher != null ) {
                        refresher.stop();
                    }
                    
                    out.reset();
                    in.reset();
                    
                    main.requestFocus();
                    refresher = new Refresher( (String)e.getItem(), network );
                    new Thread( refresher ).start();
                }
            }
        });
        contentPane.add( comboBox );
        
       
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                X = e.getX();
                Y = e.getY();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                main.setLocation(main.getLocation().x+(e.getX()-X),main.getLocation().y+(e.getY()-Y));
            }
        });
        
        this.setLayout( layout );
        this.setVisible( true );
        this.requestFocus();
    }
    
    public static Insets getScreenInsets() {
        Insets si = Toolkit.getDefaultToolkit().getScreenInsets( new JFrame().getGraphicsConfiguration() );
        return si;
    }
    
    public void setInterfaces( String[] names ) {
        comboBox.removeAllItems();
        for( String n : names ) {
            comboBox.addItem( n );
        }
        
        comboBox.setEnabled( true );
    }
    
    private class Refresher implements Runnable {

        private boolean running = true;
        private final String interfaceName;
        private final WinNetworkInterface interfaces;
        
        public Refresher( String interfaceName, WinNetworkInterface interfaces ) {
            this.interfaceName = interfaceName;
            this.interfaces = interfaces;
        }
        
        public void stop() {
            running = false;
        }
        
        @Override
        public void run() {
            while( running ) {
                long milliStart = System.currentTimeMillis();
                
                final long o = interfaces.getLong( interfaceName, "BytesSentPerSec" );
                final long i = interfaces.getLong( interfaceName, "BytesReceivedPerSec" );
                
                if( o < 0 ) {
                    outgoing.setText( "..." );
                } else {
                    outgoing.setText( String.valueOf(Math.round((double)o/1024*(double)100)/(double)100 ) + " KB/s" );
                    out.pushData( o );
                    out.repaint();
                }
                
                if( i < 0 ) {
                    incoming.setText( "..." );
                } else {
                    incoming.setText( String.valueOf(Math.round((double)i/1024*(double)100)/(double)100) + " KB/s" );
                    in.pushData( i );
                    in.repaint();
                }
                
                long milliEnd = System.currentTimeMillis();
                long timeOut = 500 - (milliEnd-milliStart);
                
                if( timeOut > 0 ) {
                    try {
                        Thread.sleep( timeOut );
                    } catch (InterruptedException ex) {
                        running = false;
                        break;
                    }
                }
            }
        }
    }
    
    private int X = 0;
    private int Y = 0;
    
}
