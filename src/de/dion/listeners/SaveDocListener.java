package de.dion.listeners;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import de.dion.Main;

public class SaveDocListener implements DocumentListener {

	@Override
	public void insertUpdate(DocumentEvent e) {
		Main.getGUI().updateTitle();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		Main.getGUI().updateTitle();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		Main.getGUI().updateTitle();
	}

}
