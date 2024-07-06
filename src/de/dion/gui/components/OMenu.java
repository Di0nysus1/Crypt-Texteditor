package de.dion.gui.components;

import java.awt.Font;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import de.dion.utils.Options;

public class OMenu extends JMenu {

	private static final long serialVersionUID = 266717911363170764L;
	
	//Klasse ist nur zum überladen vom konstruktor da
	
	public OMenu(String name, Font f) {
        super(name);
        setFont(f);
    }
	
	public OMenu(String name) {
		super(name);
	}
	
	public JMenuItem add(Action a) {
		JMenuItem jmi = super.add(a);
		jmi.setFont(Options.menu_font);
        return jmi;
    }
	
}
