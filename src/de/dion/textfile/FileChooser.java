package de.dion.textfile;

import java.awt.Color;
import java.io.File;

import javax.swing.JFileChooser;

import de.dion.Main;
import de.dion.gui.SuperGui;
import de.dion.utils.Options;

public class FileChooser extends JFileChooser {

	private static final long serialVersionUID = -4680960313584007486L;
	private File startFile;
	
	public FileChooser(File f) {
		super();
		this.startFile = f;
		setCurrentDirectory(startFile);
		this.setFileFilter(new CryptedFileFilter());
		this.addChoosableFileFilter(new TextFileFilter());
	}
	
	
	public File getStartFile() {
		return startFile;
	}

	public void setStartFile(File startFile) {
		setCurrentDirectory(startFile);
		this.startFile = startFile;
	}

	public FileChooser() {
		this(new File(""));
	}
	
	public void open() {
		Main.getGUI().setAlwaysOnTop(false);
		showOpenDialog(null);
		Main.getGUI().setAlwaysOnTop(SuperGui.getConfig().getBooleanValue("window_alwaysontop"));
	}
	
	public void save() {
		Main.getGUI().setAlwaysOnTop(false);
		showSaveDialog(null);
		Main.getGUI().setAlwaysOnTop(SuperGui.getConfig().getBooleanValue("window_alwaysontop"));
	}
	
	
	@Override
	public File getSelectedFile() {
		if(getDialogType() == SAVE_DIALOG && getFileFilter() != null && super.getSelectedFile() != null && super.getSelectedFile().getPath().length() > 0) {
			if(getFileFilter() instanceof TextFileFilter) {
				return (super.getSelectedFile().getName().toLowerCase().endsWith(".txt") ?
						super.getSelectedFile() : new File(super.getSelectedFile().getPath() + ".txt"));
			} else if(getFileFilter() instanceof CryptedFileFilter) {
				return (super.getSelectedFile().getName().toLowerCase().endsWith(Options.cryptedFileEnding) ?
						super.getSelectedFile() : new File(super.getSelectedFile().getPath() + Options.cryptedFileEnding));
			}
		}
        return super.getSelectedFile();
    }
	
}
