package de.dion.keybinds;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import de.dion.Main;
import de.dion.listeners.NamedAction;

public class Keybinds_TextGUI extends KeyBindManager {
	
	public Keybinds_TextGUI(JPanel jcontentPane) {
		super(jcontentPane);
	}
	
	@SuppressWarnings("serial")
	public void addKeybinds() {
		
		addKeyBind(new NamedAction("file_save") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Main.getGUI().save_file();
			}
		}, KeyEvent.VK_S, KeyEvent.CTRL_MASK);
		
		addKeyBind(new NamedAction("file_new") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Main.getGUI().new_file();
			}
		}, KeyEvent.VK_N, KeyEvent.CTRL_MASK);
		
		addKeyBind(new NamedAction("file_open") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Main.getGUI().open_file();
			}
		}, KeyEvent.VK_O, KeyEvent.CTRL_MASK);
		
		addKeyBind(new NamedAction("edit_undo") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Main.getGUI().undo();
			}
		}, KeyEvent.VK_Z, KeyEvent.CTRL_MASK);
		
		addKeyBind(new NamedAction("edit_redo") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Main.getGUI().redo();
			}
		}, KeyEvent.VK_Y, KeyEvent.CTRL_MASK);
		
		addKeyBind(new NamedAction("config_reload") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Main.getGUI().reloadConfig();
			}
		}, KeyEvent.VK_F5);
		
		addKeyBind(new NamedAction("line_delete") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Main.getGUI().deleteLine();
			}
		}, KeyEvent.VK_D, KeyEvent.CTRL_MASK);
		
		addKeyBind(new NamedAction("line_clone") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Main.getGUI().cloneLine();
			}
		}, KeyEvent.VK_D, KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK);
		
		addKeyBind(new NamedAction("edit_search") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Main.getGUI().openSearchFrame();
			}
		}, KeyEvent.VK_F, KeyEvent.CTRL_MASK);
		
		addKeyBind(new NamedAction("testidk") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Main.getGUI().openPWFrame();
			}
		}, KeyEvent.VK_G, KeyEvent.CTRL_MASK);
	}
	
}
