package de.dion.utils;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JMenu;

import de.dion.Main;
import de.dion.listeners.NamedAction;

public class ColorUtils {
	
	//Alle Farben für die GUIs
	public static int
	
	weiß = 0xFFFFFF,
	schwarz = 0x000000,
	blau = 0x0000FF,
	grau_blau = 0x002B36,
	rot = 0xFF0000,
	gruen = 0x00FF00,
	turkise = 0x00FFFF,
	orange = 0xFF6A00,
	pink = 0xFF0099,
	gelb = 0xFFD800,
	grau = 0x303030
	;
	
	public static void addColors(JMenu cmenu, ColorAreas area) {
		for(String k: values().keySet()) {
			cmenu.add(new NamedAction(k) {
				private static final long serialVersionUID = 7977721219429436325L;

				@Override
				public void actionPerformed(ActionEvent e) {
					
					setColor(values().get(k), area);
				}
			});
		}
		
	}
	
	private static void setColor(int v, ColorAreas area) {
		String save = Integer.toHexString(v);
		int need = 6 - save.length();
		for(int i = 0; i < need; i++) {
			save = "0" + save;
		}
		save = "#" + save;
		save = save.toUpperCase();
		
    	switch(area) {
    	case Font:
    		Main.getGUI().setConfigValue("font_color", save);
    		Main.getGUI().getTextArea().setForeground(new Color(v));
    		break;
    	case Back:
    		Main.getGUI().setConfigValue("background_color", save);
    		Main.getGUI().getTextArea().setBackground(new Color(v));
            break;
    	case Cursor:
    		Main.getGUI().setConfigValue("cursor_color", save);
    		Main.getGUI().getTextArea().setCaretColor(new Color(v));
            break;
		case Selection:
			Main.getGUI().setConfigValue("selection_color", save);
			Main.getGUI().getTextArea().setSelectionColor(new Color(v));
			break;
		default:
			throw new IllegalStateException("Eine Farbe wurde nicht geswitcht!");
    	}
    }
	
	public static Map<String, Integer> values()
	{
		Field[] f = ColorUtils.class.getFields();
		HashMap<String, Integer> values = new HashMap<>();
		for(int i = 0; i < f.length; i++)
		{
			try {
				values.put(f[i].getName(), (int)f[i].get(0));
			} catch (IllegalArgumentException | ClassCastException | IllegalAccessException e) {}
		}
		return values;
	}
	
}
