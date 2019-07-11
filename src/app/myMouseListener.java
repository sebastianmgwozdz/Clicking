package app;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

// Track mouse clicks during accuracy game
class myMouseListener implements MouseListener {
	private int clickCount;
	
	public myMouseListener() {
		this.clickCount = 0;
	}
	@Override
	public void mouseClicked(MouseEvent arg0) { 
		clickCount++;
	}
	
	public int getClickCount() {
		return clickCount;
	}
	
	public void resetClickCount() {
		clickCount = 0;
	}

	@Override
	public void mouseEntered(MouseEvent arg0) { }

	@Override
	public void mouseExited(MouseEvent arg0) { }

	@Override
	public void mousePressed(MouseEvent arg0) { }

	@Override
	public void mouseReleased(MouseEvent arg0) { }

}
