package de.dion.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

public class CryptedWriter extends BufferedWriter {

	public CryptedWriter(Writer out, int sz) {
		super(out, sz);
	}
	
	public CryptedWriter(Writer out) {
		super(out);
	}
	
	@Override
	public void write(char cbuf[], int off, int len) throws IOException {
		for(int i = 0; i < cbuf.length; i++) {
			System.out.print(cbuf[i]);
		}
		System.out.println(off + " " + len);
		super.write(cbuf, off, len);
	}
	

}
