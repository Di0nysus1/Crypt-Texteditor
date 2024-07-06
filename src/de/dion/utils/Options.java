package de.dion.utils;

import java.awt.Font;
import java.io.File;

import de.dion.utils.config.Config;
import de.dion.utils.config.Entry;

public class Options {
	
	public static final String title = " - 1 Krasser Text Editor";
	public static final String credits = "by NightDev401 & Dionysus";
	public static final String version = "2";
	
	//zu debug zwecken
	public static boolean opendebugFile = false;
	public static File debugFile = new File("Y:\\admin1\\Desktop\\w.txt");
	
	//an dieser stelle startet der filechooser
	private static File startPosition = new File(System.getProperty("user.home") + File.separator + "Desktop");
	
	public static String cryptedFileEnding = ".crypt";
	public static Font menu_font = new Font("SansSerif", Font.PLAIN, 15);
	
	public static int default_window_sizeX = 1300;
	public static int default_window_sizeY = 900;
	public static int default_undohistory_size = 250;
	
	public static String default_text_font_name = "SansSerif";
	public static int default_text_font_style = Font.BOLD;
	public static int default_text_font_size = 20;
	
	
	//Config Datei
	public static final Config conf = new Config("Texteditor Einstellungen", new Entry[]{
			new Entry("font_color", "#FF6A00", false, "Schrift Farbe"),
			new Entry("background_color", "#002B36", false, "Hintergrund Farbe"),
			new Entry("cursor_color", "#00FF00", false, "Farbe vom Cursor -> |"),
			new Entry("selection_color", "#DDDDDD", false, "Farbe des markierten Text"),
			new Entry("fullscreen_enabled", "false", false),
			new Entry("window_size_x", default_window_sizeX, false),
			new Entry("window_size_y", default_window_sizeY, false),
			new Entry("load_window_size", "true", false),
			new Entry("load_window_pos", "true", false, 
					"Öffnet den Editor beim nächsten mal an der Stelle\nauf dem Bildschirm wieder, "
					+ "wo er geschlossen wurde.\nAnsonsten wird er mittig auf dem Bildschirm geöffnet."),
			new Entry("window_pos_x", "unknown", false),
			new Entry("window_pos_y", "unknown", false),
			new Entry("window_alwaysontop", "false", false),
			new Entry("font_style", default_text_font_style, false, "schrift style\n0 = normal, 1 = FETT, 2 = kursiv"),
			new Entry("font_name", default_text_font_name, true),
			new Entry("font_size", default_text_font_size, false),
			new Entry("undohistory_size", default_undohistory_size, false, "Legt fest, wie viele Schritte in der Undohistory maximal gespeichert werden"),
			new Entry("asktosave", "true", false, "Möchtest du vorm schließen einer Datei gefragt werden, ob diese gespeichert werden soll?"),
			new Entry("start_position", startPosition, true, "An welcher Position soll der File-Chooser starten?"),
			new Entry("smart_autocompletion", false, false, "comming soon @fadeofficial"),
			new Entry("search_regex", false, false, "comming soon @fadeofficial"),
			new Entry("search_backward", false, false),
			new Entry("search_case_sensitive", false, false),
			new Entry("search_alwaysontop", false, false),
			new Entry("search_startagain", true, false),
			});
	
	
	
}
