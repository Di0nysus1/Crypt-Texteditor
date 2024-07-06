package de.dion.utils.config;

import java.util.ArrayList;
import java.util.Arrays;

import de.dion.utils.config.exceptions.EntryNotFoundException;

public class Config {
	
	private final String name;
	private final ArrayList<Entry> entrys;
	
	public Config(String name, Entry[] entrys) {
		this.name = name;
		this.entrys = new ArrayList<Entry>(Arrays.asList(entrys));
//		this.entrys = entrys;
	}
	
	public String getName() {
		return name;
	}

	public ArrayList<Entry> getEntrys() {
		return this.entrys;
	}
	
	public Entry getEntry(String name) {
		return get(name);
	}
	
	public String getValue(String name) {
		return get(name).getValue();
	}
	
	public void setValue(String name, String value) {
		get(name).setValue(value);
	}
	
	public String getDefault(String name) {
		return get(name).getDefaultValue();
	}
	
	public boolean isEntry(String name) {
		return find(name);
	}
	
	public void clearValues() {
		for(Entry ee: entrys) {
			ee.clear();
		}
	}
	
	private boolean find(String name) {
		for(Entry ee: entrys) {
			if(ee.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	
	private void addEntry(Entry e) {
		entrys.add(e);
	}
	
	private Entry get(String name) {
		for(Entry ee: entrys) {
			if(ee.getName().equalsIgnoreCase(name)) {
				return ee;
			}
		}
		Entry e = new Entry(name, "none");
		addEntry(e);
		
		return get(name);
//		throw new EntryNotFoundException("cant find: \"" + name + "\" in \"" + this.name + "\" config | size of the entrys: " + entrys.length);
	}

}
