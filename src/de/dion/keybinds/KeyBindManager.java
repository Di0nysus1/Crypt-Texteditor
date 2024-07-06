package de.dion.keybinds;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import de.dion.Main;
import de.dion.listeners.NamedAction;

public abstract class KeyBindManager {
	
	JPanel jcontentPane;

	public KeyBindManager(JPanel jcontentPane) {
		super();
		this.jcontentPane = jcontentPane;
	}
	
	public JPanel getJcontentPane() {
		return jcontentPane;
	}
	
	
	protected abstract void addKeybinds();

	
	protected void addKeyBind(NamedAction a, int key1) {
		addKeyBind(a, key1, 0);
	}
	
	protected void addKeyBind(NamedAction a, int key1, int key2) {
        InputMap iMap = jcontentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap aMap = jcontentPane.getActionMap();
        iMap.put(KeyStroke.getKeyStroke(key1, key2), a.getName());
        aMap.put(a.getName(), a);
	}
	
}
