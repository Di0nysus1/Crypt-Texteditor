package de.dion.textfile;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class TextFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		if(f.getName().toLowerCase().endsWith(".txt") || f.isDirectory()) {
			return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Text File *.txt";
	}

}
