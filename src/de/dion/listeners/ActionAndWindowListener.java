package de.dion.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class ActionAndWindowListener extends WindowAdapter implements ActionListener {
	
	public void windowClosing(WindowEvent windowEvent) {
		actionPerformed(new ActionEvent(new Object(), 0x1337, "penis"));
	}
	
}
