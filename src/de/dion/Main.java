package de.dion;

import java.awt.AWTError;
import java.io.File;

import de.dion.gui.guis.TextGUI;
import de.dion.utils.Options;

public class Main {
	
	private static TextGUI gui;
	private static String osname;
	
	public static TextGUI getGUI() {
		return gui;
	}

	public static void main(String[] args) {
		osname = System.getProperty("os.name").toLowerCase();
		
		//Handle Parameter stuff
		if(args.length > 0) {
			String arg = "";
			for(String v: args)
			{
				arg += v + " ";
			}
			arg = arg.trim();
			
			if(arg.length() > 0) {
				Options.opendebugFile = true;
				Options.debugFile = new File(arg);
				System.out.println("öffne " + Options.debugFile.getPath());
			}
		}
		
		
		//Gui ready machen und starten.
		try {
			gui = new TextGUI();
		} catch(AWTError e) {
			e.printStackTrace();
			
			if(isLinux()) {
				/*
				 * Wenn man bei Linux die Headless version von java installiert hat Kommt dieser Fehler
				 * und der Editor schmiert ab weil der das Java Frame nicht laden kann.
				 * "Exception in thread "main" java.awt.AWTError: Assistive Technology not found: o$"
				 */
				System.err.println("Bitte installiere die nicht headless Version von Java!");
			}
		}
		gui.init();
	}
	
	public static boolean isWindows() {
		return osname.contains("windows");
	}
	
	public static boolean isLinux() {
		return osname.contains("linux");
	}

}
