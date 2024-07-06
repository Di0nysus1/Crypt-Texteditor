package de.dion.listeners;

import javax.swing.AbstractAction;
import javax.swing.Action;

public abstract class NamedAction extends AbstractAction {

	private static final long serialVersionUID = -2086681120606900867L;
	private String name;
	
	public NamedAction(String name) {
		super(name);
		this.name = name;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		putValue(Action.NAME, name);
	}

}
