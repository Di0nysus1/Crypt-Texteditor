package de.dion.keybinds;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import de.dion.Main;
import de.dion.listeners.NamedAction;

public class Keybinds_SearchFrame extends KeyBindManager {

	public Keybinds_SearchFrame(JPanel jcontentPane) {
		super(jcontentPane);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("serial")
	public void addKeybinds() {
		
		addKeyBind(new NamedAction("close search frame") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Main.getGUI().closeSearchFrame();
			}
		}, KeyEvent.VK_F, KeyEvent.CTRL_MASK);
	}

}
