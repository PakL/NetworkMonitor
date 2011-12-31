package de.pakldev.networkmonitor;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JRootPane;

/**
 *
 * @author PakL
 */
public class WindowBackground extends JRootPane {

    public static GradientPaint gradient = new GradientPaint( 1, 1, new Color( 124, 177, 210 ), 1, 25, new Color( 107, 152, 181 ), false );
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.setPaint( gradient );
        g2d.fillRect( 0, 0, this.getWidth()-1, 25 );
        
        super.paint(g);
        
        g2d.setColor( Color.BLACK );
        g2d.drawLine( 0, 25, this.getWidth()-1, 25 );
        g2d.drawRect( 0, 0, this.getWidth()-1, this.getHeight()-1 );
    }
    
}
