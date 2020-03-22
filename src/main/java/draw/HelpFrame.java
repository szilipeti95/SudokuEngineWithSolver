package draw;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class HelpFrame extends JDialog implements KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HelpFrame(Frame frame){
		super(frame, "About", true);
        Point loc = frame.getLocation();
        setLocation(loc.x+80,loc.y+80);
		setPreferredSize(new Dimension(318,540));
		addKeyListener(this);
		setVisible(true);
	}
	public void paint(Graphics g){
		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream("files/HelpFrame.png");
			BufferedImage img = ImageIO.read(in);
			g.drawImage(img, 9, 32, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.pack();
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_ESCAPE)
			this.dispose();
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
