package de.pakldev.networkmonitor;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author PakL
 */
public class TrafficDiagramm extends JPanel {
    public static GradientPaint gradient = new GradientPaint( 1, 1, new Color( 124, 177, 210 ), 1, 40, new Color( 107, 152, 181 ), false );
    public static int steps = 100;
    
    private Polygon p1 = new Polygon();
    private Polygon p2 = new Polygon();
    
    private List<Long> data = new ArrayList();
    
    private Polygon currPolygon = new Polygon();
    private long currMax = 0;
    private long showMax = 2;
    
    public TrafficDiagramm() {
        this.setBackground( Color.WHITE );
        this.setDoubleBuffered( true );
        
        for( int i = 0; i < steps; i++ ) {
            data.add( 0L );
        }
    }
    
    public void reset() {
        data.clear();
        for( int i = 0; i < TrafficDiagramm.steps; i++ ) {
            data.add( 0L );
        }
    }
    
    @Override
    public void paint( Graphics g ) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        
        g2d.setColor( this.getBackground() );
        g2d.fillRect( 0, 0, this.getWidth(), this.getHeight() );
        
        g2d.setPaint( TrafficDiagramm.gradient );
        g2d.fill( currPolygon );

        g2d.setColor( Color.BLACK );
        g2d.draw( currPolygon );
        
        try {
            Font f = Font.createFont( Font.TRUETYPE_FONT, TrafficDiagramm.class.getResourceAsStream( "res/raffix_simple_upcase.ttf" ) );
            f = f.deriveFont( Font.PLAIN, 8 );
            g2d.setFont( f );
            String max = String.valueOf( showMax/1024 );
            
            Rectangle fontOutline = f.getStringBounds(max, g2d.getFontRenderContext() ).getBounds();
            fontOutline.setLocation( 2, 2 );
            fontOutline.height = 7;
            fontOutline.width += 1;
            
            g2d.setColor( Color.WHITE );
            g2d.fillRect( fontOutline.x, fontOutline.y, fontOutline.width, fontOutline.height );
            
            g2d.setColor( Color.BLACK );
            g2d.drawString( max, 3, 8 );
            
        } catch (Exception ex) {
            System.err.println( ex );
        }
        
        g2d.drawRect( 0, 0, this.getWidth()-1, this.getHeight()-1 );
    }
    
    public void pushData( long traffic ) {
        List<Long> newData = new ArrayList();
        currMax = traffic;
        if( data.size() >= TrafficDiagramm.steps ) {
            for( int i = 0; i < data.size(); i++ ) {
                if( i < (TrafficDiagramm.steps-1) ) {
                    if( data.size() > (i+1) ) {
                        if( currMax < data.get( (i+1) ) )
                            currMax = data.get( (i+1) );
                        
                        newData.add( data.get( (i+1) ) );
                    }
                }
            }
            newData.add( traffic );
        
            data = newData;
        } else {
            data.add( traffic );
        }
        
        showMax = 2;
        while( currMax > showMax ) {
            showMax *= 2;
        }
        
        int h = this.getHeight() -1;
        
        float m = (float)(this.getWidth() -1)/(float)(steps-1);
        
        currPolygon.reset();
        
        if( h > 0 && showMax > 0 ) {
            Point last = null;
            
            int i = 0;
                
            currPolygon.addPoint( 0, h );
        
            for( long d : data ) {
                Point newPoint = new Point();
                newPoint.x = (int)(m * i);
                newPoint.y = (int)Math.round((((double)h / (double)showMax) * (double)d));
                newPoint.y = h - newPoint.y;
                
                
                currPolygon.addPoint( newPoint.x, newPoint.y );
                last = newPoint;
                i++;
            }
            
            currPolygon.addPoint( last.x, h );
            currPolygon.addPoint( 0, h );
        }
    }
    
    
    
}
