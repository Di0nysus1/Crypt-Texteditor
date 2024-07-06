package de.dion.gui.components;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.border.Border;

public class BorderComponent extends JComponent {

	private static final long serialVersionUID = 2357897715586341123L;
	
	public BorderComponent(Border border) {
		this();
		this.setBorder(border);
	}
	
	public BorderComponent() {
		super();
	}
	
	public void setBorder(Border border) {
		super.setBorder(border);
	}

}
