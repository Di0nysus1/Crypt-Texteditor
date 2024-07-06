package de.dion.gui;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import de.dion.Main;
import de.dion.listeners.ActionAndWindowListener;
import de.dion.utils.Options;
import de.dion.utils.config.ConfigHelper;
import de.dion.utils.config.exceptions.WrongFileTypeException;
import de.dion.utils.config.settings.ConfigSettings;

public abstract class SuperGui extends JFrame {

	private static final long serialVersionUID = -3544661617094797887L;
	protected static ConfigHelper config;

	public static ConfigHelper getConfig() {
		return config;
	}
	
	/**
	 *  Wenn ein button noch keine funktion hat kann ihm mit
	 *  dieser methode die <B>DISPOSE Funktion</B> gegeben werden. <br />
	 *  Diese macht das Frame in dem er sich befindet kein und in den hintergund
	 *  <B>setVisiable(false)</B>
	 */
	protected void addDisposeFunction(JButton btn) {
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	
	
	/**
	 * Fügt eine Funktion zum Button hinzu und hided nach dem Ausführen dieser
	 * das Frame, in dem er sich befindet.
	 */
	protected void addActionAndDispose(JButton btn, ActionAndWindowListener al) {
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				al.actionPerformed(e);
				dispose();
			}
		});
	}
	
	
	protected void playErrorSound() {
		if (Main.isWindows()) {
			final Runnable runnable = (Runnable) Toolkit.getDefaultToolkit()
					.getDesktopProperty("win.sound.exclamation");
			if (runnable != null)
				runnable.run();
		}
	}
	
	
	/**
	 * Verändert minimal das Theme. Funktioniert<p> nur auf Windows.
	 * */
	protected void setLookAndFeel() {
		if(Main.isWindows()) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
	            ex.printStackTrace();
	        }
		}
	}
	
	
	/**
	 * Config Datei EIN MAL laden.
	 * */
	static {
		try {
			File configFile;
			String absolutePath = SuperGui.class.getProtectionDomain().getCodeSource().getLocation().toExternalForm();
			absolutePath = absolutePath.substring(0, absolutePath.length() - 1);
			absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/") + 1).substring(5);
			
			if(Main.isWindows()) {
				absolutePath = absolutePath.replaceAll("%20", " ");
			}
				
			configFile = new File(absolutePath + File.separator + "Crypt_Texteditor" + ConfigSettings.fileending);

			config = new ConfigHelper(Options.conf, configFile);
			config.read();
		} catch (WrongFileTypeException e1) {
			e1.printStackTrace();
		}
	}

}
