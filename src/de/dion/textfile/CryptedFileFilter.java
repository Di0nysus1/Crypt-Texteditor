package de.dion.textfile;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import de.dion.utils.Options;


public class CryptedFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		if(f.getName().toLowerCase().endsWith(Options.cryptedFileEnding) || f.isDirectory()) {
			return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Encrypted File *" + Options.cryptedFileEnding;
	}

}
