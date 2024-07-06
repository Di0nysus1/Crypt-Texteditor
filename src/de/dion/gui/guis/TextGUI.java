package de.dion.gui.guis;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.undo.UndoManager;

import de.dion.WrongPassWordException;
import de.dion.gui.SuperGui;
import de.dion.gui.components.OMenu;
import de.dion.keybinds.Keybinds_TextGUI;
import de.dion.listeners.ActionAndWindowListener;
import de.dion.listeners.NamedAction;
import de.dion.listeners.SaveDocListener;
import de.dion.textfile.FileChooser;
import de.dion.utils.ColorAreas;
import de.dion.utils.ColorUtils;
import de.dion.utils.Crypter;
import de.dion.utils.Options;

public class TextGUI extends SuperGui {
	
	private static final long serialVersionUID = 5979268271321195295L;
	
	private JTextArea textArea = new JTextArea("");
    private JScrollPane scroll = new JScrollPane(textArea);
    private JMenuBar menuBar = new JMenuBar();
    private FileChooser chooser;
    private UndoManager undoManager;
    private String oldText = "";
    private SearchFrame search;
    private Keybinds_TextGUI kb;
    private PassWordFrame pwFrame;
    private NamedAction alwaysontop;
    public File openedFile;
    
    public void init() {
        //init variables
    	openedFile = null;
    	undoManager = new UndoManager();
    	kb = new Keybinds_TextGUI((JPanel)getContentPane());
    	kb.addKeybinds();
    	chooser = new FileChooser(new File(config.getValue("start_position")));
    	undoManager.setLimit(config.getIntValue("undohistory_size"));
		
    	
		//Set JFrame basic stuff
    	System.out.println(Options.credits);
    	System.out.println("Version: " + Options.version);
    	setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/icon.png")));
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(true);
        setAlwaysOnTop(config.getBooleanValue("window_alwaysontop"));
        updateTitle();
        
        
        //Textarea & colors
        textArea.setForeground(new Color(Integer.decode("" + config.getValue("font_color"))));
        textArea.setBackground(new Color(Integer.decode("" + config.getValue("background_color"))));
        textArea.setCaretColor(new Color(Integer.decode("" + config.getValue("cursor_color"))));
        textArea.setSelectionColor(new Color(Integer.decode("" + config.getValue("selection_color"))));
        textArea.setFont(new Font(config.getValue("font_name"), config.getIntValue("font_style"), config.getIntValue("font_size")));
        textArea.getDocument().addUndoableEditListener(undoManager);
        textArea.getDocument().addDocumentListener(new SaveDocListener());
        
        
        //Fenstergröße ermitteln und setzen
        int width = Options.default_window_sizeX;
        int height = Options.default_window_sizeY;
        if(config.getBooleanValue("load_window_size")) {
        	width = config.getIntValue("window_size_x");
        	height = config.getIntValue("window_size_y");
        }
        setSize(width, height);
        
        
        //Fensterposition
        if(config.getBooleanValue("load_window_pos")) {
        	//lade position aus config
        	try {
        		setLocation(config.getIntValue("window_pos_x"), config.getIntValue("window_pos_y"));
        	} catch(NumberFormatException e) {
        		//wenn noch keine Position angegeben setze mittig
        		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            	int x = d.width / 2;
            	int y = d.height / 2;
            	setLocation(x - width / 2, y - height / 2);
            	//save new position values to config
            	config.setValue("window_pos_x", getX());
            	config.setValue("window_pos_y", getY());
            	config.saveValues();
        	}
        } else {
        	//platziere fenster mittig
        	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        	int x = d.width / 2;
        	int y = d.height / 2;
        	setLocation(x - width / 2, y - height / 2);
        }
        search = new SearchFrame(getX() + getWidth() / 2, getY() + getHeight() / 2);
        pwFrame = new PassWordFrame(getX() + getWidth() / 2, getY() + getHeight() / 2, new File(""));
        
        //Fullscreen
        if(config.getBooleanValue("fullscreen_enabled")) {
        	setExtendedState(MAXIMIZED_BOTH);
        }
        update();
        
        
        //Menubar
        Container container = getContentPane();
        scroll.setBounds(4, 5, 770, 540);
        container.setLayout(null);
        container.add(scroll);
        addScrollingEvent();
        search.setScrollPane(scroll); 
        setJMenuBar(menuBar);
        
        
        //Menubar elemente
        OMenu menu = new OMenu("Datei", Options.menu_font);
        OMenu edit = new OMenu("Bearbeiten", Options.menu_font);
        OMenu color = new OMenu("Farben", Options.menu_font);
        OMenu font = new OMenu("Schrift", Options.menu_font);
        OMenu back = new OMenu("Hintergrund", Options.menu_font);
        OMenu cursor = new OMenu("Cursor", Options.menu_font);
        OMenu selection = new OMenu("Markierung", Options.menu_font);
        OMenu hilfe = new OMenu("Hilfe", Options.menu_font);
        OMenu trash = new OMenu("Such", Options.menu_font);
        OMenu sett = new OMenu("Einstellungen", Options.menu_font);
        OMenu dd = new OMenu("Dir", Options.menu_font);
        
        color.addSeparator();
        color.add(font);
        color.add(back);
        color.add(cursor);
        color.add(selection);
        trash.addSeparator();
        trash.add(dd);
        hilfe.addSeparator();
        hilfe.add(trash);
        menuBar.add(menu);
        menuBar.add(edit);
        menuBar.add(sett);
        menuBar.add(color);
        menuBar.add(hilfe);
        ColorUtils.addColors(font, ColorAreas.Font);
        ColorUtils.addColors(back, ColorAreas.Back);
        ColorUtils.addColors(cursor, ColorAreas.Cursor);
        ColorUtils.addColors(selection, ColorAreas.Selection);
        
        
        alwaysontop = new NamedAction("Always on Top [" + (config.getBooleanValue("window_alwaysontop") ? "aktiviert" : "deaktiviert") + "]") {
			
			private static final long serialVersionUID = -1789219653943324012L;

			@Override
			public void actionPerformed(ActionEvent e) {
				config.setValue("window_alwaysontop", !config.getBooleanValue("window_alwaysontop"));
				alwaysontop.setName("Always on Top [" + (config.getBooleanValue("window_alwaysontop") ? "aktiviert" : "deaktiviert") + "]");
				setAlwaysOnTop(config.getBooleanValue("window_alwaysontop"));
				config.saveValues();
			}
		};
        Action open_config = new AbstractAction("Config bearbeiten") {

			private static final long serialVersionUID = 7942406002328587030L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if(config.getBooleanValue("asktosave") && mustSave()) {
		    		SaveQFrame sqf = new SaveQFrame(getX() + getWidth() / 2, getY() + getHeight() / 2, true);
		    		sqf.setNeListener(new ActionAndWindowListener() {
		    			 
		    			@Override
		    			public void actionPerformed(ActionEvent e) {
		    				new Thread(new Runnable() {
		    					
		    					@Override
		    					public void run() {
		    						
		    						openFile(config.getFile(), null);
		    					}
		    				}).start();
		    			}
		    		});
		    		sqf.setYaListener(new ActionAndWindowListener() {
		    			
		    			@Override
		    			public void actionPerformed(ActionEvent e) {
		    				save_file();
		    				new Thread(new Runnable() {
		    					
		    					@Override
		    					public void run() {
		    						save_file();
		    						
		    						openFile(config.getFile(), null);
		    					}
		    				}).start();
		    			}
		    		});
		    		sqf.setVisible(true);
		    	} else {
		    		openFile(config.getFile(), null);
		    	}
			}
		};
		Action reload_config = new AbstractAction("Config reloaden (F5)") {

			private static final long serialVersionUID = -3133819031307398086L;

			@Override
			public void actionPerformed(ActionEvent e) {
				reloadConfig();
			}
		};
        Action shit = new AbstractAction("selber welche"){

			private static final long serialVersionUID = -4315492145856228915L;

			@Override
            public void actionPerformed(ActionEvent e){
            	doshit();
            }
        };
        Action clone_line = new AbstractAction("Zeile Klonen (STRG + SHIFT + D)"){

			private static final long serialVersionUID = -3879217620911685098L;

			@Override
            public void actionPerformed(ActionEvent e){
            	cloneLine();
            }
        };
        Action delete_line = new AbstractAction("Zeile löschen (STRG + D)"){

			private static final long serialVersionUID = -7119738769385887137L;

			@Override
            public void actionPerformed(ActionEvent e){
            	deleteLine();
            }
        };
        Action undo_b = new AbstractAction("Undo"){

			private static final long serialVersionUID = -2972914331084301053L;

			@Override
            public void actionPerformed(ActionEvent e){
            	undo();
            }
        };
        Action redo_b = new AbstractAction("Redo"){

			private static final long serialVersionUID = -6753139820391185590L;

			@Override
            public void actionPerformed(ActionEvent e){
            	redo();
            }
        };
        Action neu = new AbstractAction("Neu"){

			private static final long serialVersionUID = -5563441597802485104L;

			@Override
            public void actionPerformed(ActionEvent e){
                new_file();
            }
        };
        Action oeffnen = new AbstractAction("Öffnen..."){
        	
			private static final long serialVersionUID = -7421955706728070520L;

			@Override
            public void actionPerformed(ActionEvent e){
            	open_file();
            }
        };
        Action speichern = new AbstractAction("Speichern"){


			private static final long serialVersionUID = 924068140098254504L;

			@Override
            public void actionPerformed(ActionEvent e) {
            	save_file();
            }
        };
        Action speichernunter = new AbstractAction("Speichern unter..."){

			private static final long serialVersionUID = -9089147012381790099L;

			@Override
            public void actionPerformed(ActionEvent e) {
            	
            	chooser.setSelectedFile(new File(""));
            	chooser.save();
            	saveFile(chooser.getSelectedFile());
            }
        };
        Action beenden = new AbstractAction("Beenden"){

			private static final long serialVersionUID = 139564302993347321L;

			@Override
            public void actionPerformed(ActionEvent e) {
            	close();
            }
        };
        Action search = new NamedAction("Suchen und Ersetzen") {
			
			private static final long serialVersionUID = -9102328050851300393L;

			@Override
			public void actionPerformed(ActionEvent e) {
				openSearchFrame();
			}
		};
        sett.add(open_config);
        sett.add(reload_config);
        sett.add(alwaysontop);
        dd.add(shit);
        edit.add(search);
        edit.add(clone_line);
        edit.add(delete_line);
        edit.add(undo_b);
        edit.add(redo_b);
        menu.add(neu);
        menu.add(oeffnen);
        menu.add(speichern);
        menu.add(speichernunter);
        menu.add(beenden);
        
        
        addWindowEvents();
        
        if(Options.opendebugFile) {
        	if(Options.debugFile.exists() && Options.debugFile.isFile()) {
        		open2(Options.debugFile);
        	}
        }
        
        setVisible(true);
    }
    
    /**
     * Funktion zum speichern der geöffneten Datei
     * Wenn die Datei auf .crypt endet wird ggf ein Feld angezeigt um ein Passwort anzugeben
     * */
    private void saveFile(File f) {
    	if(f != null && f.getPath().length() > 0) {
    		if(f.getPath().endsWith(Options.cryptedFileEnding)) {
    			if(pwFrame.hasPassWord(f)) {
        			writeFile(f, pwFrame.getPassWord());
    			} else {
    				pwFrame = new PassWordFrame(getX() + getWidth() / 2, getY() + getHeight() / 2, f);
    				pwFrame.addAction(new ActionListener() {
    					
    					@Override
    					public void actionPerformed(ActionEvent e) {
    						if(pwFrame.hasPassWord(f)) {
    							writeFile(f, pwFrame.getPassWord());
    						}
    						pwFrame.dispose();
    					}
    				});
    				pwFrame.setVisible(true);
    			}
			} else {
				writeFile(f, null);
			}
        }
    }
    
    /**
     * Schreibt die Daten verschlüsselt oder unverschlüsselt in das angegebene File
     * */
    private void writeFile(File f, String pw) {
    	try{
    		if(pw == null) {
    			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), Charset.forName("utf-8")));
    			
    			openedFile = f;
    			textArea.write(writer);
//    			writer.write(textArea.getText());
    			writer.close();
    			oldText = textArea.getText();
    			updateTitle();
    		} else {
    			FileOutputStream fos = new FileOutputStream(f);
    			Crypter.genKey(pw);
    			fos.write(Crypter.encrypt(textArea.getText().getBytes()));
    			
    			oldText = textArea.getText();
    			openedFile = f;
    			updateTitle();
    			fos.close();
    			
    		}
    	} catch(Exception notignore) {
    		notignore.printStackTrace();
    		JOptionPane.showMessageDialog(this, "Error: " + notignore.getMessage());
    	}
    }
    
    
    public void update() {
    	if(getContentPane().getComponentCount() > 0) {
    		Component c = getContentPane().getComponent(0);
    		c.setSize(getSize().width - 26, getSize().height - 82);
    	}
    }
    
    /**
     * Öffne die ausgewählte Datei.
     * Wenn ein Passwort angegeben ist entschlüssle die Datei und öffne sie dann.
     * */
    public void openFile(File f, String pw) {
    	if(f == null || !f.exists() || !f.isFile()) {
    		return;
    	}
    	if(pw == null) {
    		try {
        		openedFile = f;
        		textArea.setText("");
        		oldText = textArea.getText();
        		
        		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
        		char[] buff = new char[1024 * 10];
        		int len = 0;
                
                while((len = br.read(buff)) > -1) {
                	char ss = 13;
                	StringBuilder sb = new StringBuilder(new String(buff, 0, len));
                	while(sb.toString().contains(ss + "")) {
                		sb.deleteCharAt(sb.indexOf(ss + ""));
                	}
                	textArea.append(sb.toString());
                }
                
                oldText = textArea.getText();
                textArea.setCaretPosition(0);
                undoManager.discardAllEdits();
                updateTitle();
                br.close();

            } catch(Exception notignore){
            	notignore.printStackTrace();
            	JOptionPane.showMessageDialog(this, "Error: " + notignore.getMessage());
            }
    	} else {
    		try {
    			openedFile = f;
        		textArea.setText("");
        		oldText = textArea.getText();
        		
				FileInputStream fis = new FileInputStream(f);
				Crypter.genKey(pw);
				byte[] buf = new byte[(int)f.length()];
				int len = fis.read(buf);
				
				buf = Arrays.copyOf(buf, len);
				textArea.setText(new String(Crypter.decrypt(buf)));
				
				oldText = textArea.getText();
				undoManager.discardAllEdits();
                textArea.setCaretPosition(0);
                updateTitle();
				
				fis.close();
			} catch (Exception e) {
				//Try Decryption with old Key generated
				openFileWithOldEncryption(f, pw);
			}
    	}
    }
    
    /**
     * Versuche die Datei mit der alten verschlüsselung zu öffnen.
     * */
    private void openFileWithOldEncryption(File f, String pw) {
    	try {
			openedFile = f;
    		textArea.setText("");
    		oldText = textArea.getText();
    		undoManager.discardAllEdits();
    		
			FileInputStream fis = new FileInputStream(f);
			Crypter.OldCrypter.genKey(pw);
			byte[] buf = new byte[(int)f.length()];
			int len = fis.read(buf);
			
			buf = Arrays.copyOf(buf, len);
			textArea.setText(new String(Crypter.OldCrypter.decrypt(buf)));
			
			oldText = textArea.getText();
            textArea.setCaretPosition(0);
            updateTitle();
			
			fis.close();
		} catch (Exception e) {
			openedFile = null;
        	updateTitle();
			this.setAlwaysOnTop(false);
			this.pwFrame.setAlwaysOnTop(false);
			JOptionPane.showMessageDialog(this, "Falsches Passwort!");
			this.pwFrame.setAlwaysOnTop(true);
			this.setAlwaysOnTop(SuperGui.getConfig().getBooleanValue("window_alwaysontop"));
			throw new WrongPassWordException();
		}
    }
    
    private void addWindowEvents() {
    	
    	//screensize change event
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
            	update();
            }
          });
        
        //fullscreen mode change event
        addWindowStateListener(new WindowStateListener() {
            public void windowStateChanged(WindowEvent event) {
               update();
               config.setValue("fullscreen_enabled", isFullScreen());
               config.saveValues();
            }
        });
        
        //window close event
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
            	close();
            }
        });
    }
    
    public void updateTitle() {
    	setTitle((mustSave() ? "*" : "") + ((openedFile != null && openedFile.isFile()) ? openedFile.getName() + Options.title : "unknown" + Options.title));
    }
    
    private void doshit() {
    	JOptionPane.showMessageDialog(this, "Nö");
    }
    
    public void undo() {
    	if(undoManager.canUndo()) {
    		undoManager.undo();
    	}
    }
    
    public void redo() {
    	if(undoManager.canRedo()) {
    		undoManager.redo();
    	}
    }
    
    public void reloadConfig() {
    	if(openedFile.getPath().equalsIgnoreCase(config.getFile().getPath())) {
    		save_file();
    	}
    	
    	
    	config.refresh();
    	
    	//color stuff
    	textArea.setForeground(new Color(Integer.decode("" + config.getValue("font_color"))));
        textArea.setBackground(new Color(Integer.decode("" + config.getValue("background_color"))));
        textArea.setCaretColor(new Color(Integer.decode("" + config.getValue("cursor_color"))));
        textArea.setSelectionColor(new Color(Integer.decode("" + config.getValue("selection_color"))));
        boolean fulls = config.getBooleanValue("fullscreen_enabled");
        if(fulls && !isFullScreen()) {
        	setExtendedState(MAXIMIZED_BOTH);
        } else if(!fulls && isFullScreen()) {
        	setExtendedState(NORMAL);
        }
        setAlwaysOnTop(config.getBooleanValue("window_alwaysontop"));
        textArea.setFont(new Font(config.getValue("font_name"), config.getIntValue("font_style"), config.getIntValue("font_size")));
        chooser.setStartFile(new File(config.getValue("start_position")));
        alwaysontop.setName("Always on Top [" + (config.getBooleanValue("window_alwaysontop") ? "aktiviert" : "deaktiviert") + "]");
    }
    
    public void save_file() {
    	if(openedFile != null && openedFile.exists() && openedFile.isFile() && openedFile.getPath().length() > 0) {
    		saveFile(openedFile);
    	} else {
    		chooser.setSelectedFile(new File(""));
    		chooser.save();
    		saveFile(chooser.getSelectedFile());
    	}
    }
    
    public void open_file() {
    	if(config.getBooleanValue("asktosave") && mustSave()) {
    		SaveQFrame sqf = new SaveQFrame(getX() + getWidth() / 2, getY() + getHeight() / 2, true);
    		sqf.setNeListener(new ActionAndWindowListener() {
    			 
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				new Thread(new Runnable() {
    					
    					@Override
    					public void run() {
    						
    						chooser.open();
    			    		
    			    		if(chooser.getSelectedFile() != null && chooser.getSelectedFile().isFile()) {
    			    			open2(chooser.getSelectedFile());
    			    		}
    					}
    				}).start();
    			}
    		});
    		sqf.setYaListener(new ActionAndWindowListener() {
    			
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				new Thread(new Runnable() {
    					
    					@Override
    					public void run() {
    						save_file();
    						chooser.open();
    			    		
    			    		if(chooser.getSelectedFile() != null && chooser.getSelectedFile().isFile()){
    			    			open2(chooser.getSelectedFile());
    			    		}
    					}
    				}).start();
    			}
    		});
    		sqf.setVisible(true);
    	} else {
    		chooser.open();
    		
    		if(chooser.getSelectedFile() != null && chooser.getSelectedFile().isFile()){
    			open2(chooser.getSelectedFile());
    		}
    	}
    }
    
    private void open2(File f) {
    	if(f.getName().endsWith(Options.cryptedFileEnding)) {
    		pwFrame = new PassWordFrame(getX() + getWidth() / 2, getY() + getHeight() / 2, f);
    		pwFrame.addAction(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(pwFrame.hasPassWord(f)) {
						try {
							openFile(f, pwFrame.getPassWord());
							pwFrame.dispose();
						} catch(WrongPassWordException e1) {}
					}
				}
			});
    		pwFrame.setVisible(true);
    	} else {
    		openFile(f, null);
    	}
    }
    
    public void new_file() {
    	if(config.getBooleanValue("asktosave") && mustSave()) {
    		//Öffne den "willste speichern" Zettel
    		SaveQFrame sqf = new SaveQFrame(getX() + getWidth() / 2, getY() + getHeight() / 2, true);
    		sqf.setNeListener(new ActionAndWindowListener() {
    			
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				openedFile = null;
    	        	try {
    	    			textArea.getDocument().remove(0, textArea.getDocument().getLength());
    	    			oldText = textArea.getText();
    	    			undoManager.discardAllEdits();
    	    		} catch (BadLocationException e1) {
    	    			e1.printStackTrace();
    	    		}
    	        	updateTitle();
    			}
    		});
    		sqf.setYaListener(new ActionAndWindowListener() {
    			
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				save_file();
    				openedFile = null;
    	        	try {
    	    			textArea.getDocument().remove(0, textArea.getDocument().getLength());
    	    			oldText = textArea.getText();
    	    			undoManager.discardAllEdits();
    	    		} catch (BadLocationException e1) {
    	    			e1.printStackTrace();
    	    		}
    	        	updateTitle();
    			}
    		});
    		sqf.setVisible(true);
    	} else {
    		openedFile = null;
        	try {
    			textArea.getDocument().remove(0, textArea.getDocument().getLength());
    			oldText = textArea.getText();
    			undoManager.discardAllEdits();
    		} catch (BadLocationException e1) {
    			e1.printStackTrace();
    		}
        	updateTitle();
    	}
    }
    
    public boolean isFullScreen() {
    	return getExtendedState() == MAXIMIZED_BOTH;
    }
    
    public boolean mustSave() {
    	return !textArea.getText().equals(oldText);
    }
    
    public void setConfigValue(String name, String val) {
    	config.setValue(name, val);
    	config.saveValues();
    }
    
    public JTextArea getTextArea() {
    	return this.textArea;
    }
    
    public void deleteLine() {
    	try {
    		int cs = textArea.getLineOfOffset(textArea.getSelectionStart());
    		int ce = textArea.getLineOfOffset(textArea.getSelectionEnd());
			textArea.replaceRange("", 
					textArea.getLineStartOffset(cs), 
					textArea.getLineEndOffset(ce));
		} catch (BadLocationException e) {}
    }
    
    public void cloneLine() {
    	try {
			int line = textArea.getLineOfOffset(textArea.getCaretPosition());
			int l_start = textArea.getLineStartOffset(line);
			int l_end = textArea.getLineEndOffset(line);
			
			String ll = textArea.getText(l_start, l_end - l_start);
			if(ll.length() > 0 && ll.charAt(ll.length() - 1) == 10) {
				ll = ll.substring(0, ll.length() - 1);
			}
			if(line == textArea.getLineCount() - 1) {
				textArea.insert("\n" + ll, l_end);
			} else {
				textArea.insert("\n" + ll, l_end - 1);
			}
		} catch (BadLocationException e) {}
    }
    
    private void close() {
    	if(config.getBooleanValue("asktosave") && mustSave()) {
    		//Öffne den "willste speichern" Zettel
    		SaveQFrame sqf = new SaveQFrame(getX() + getWidth() / 2, getY() + getHeight() / 2, true);
    		sqf.setNeListener(new ActionAndWindowListener() {
    			
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				saveValuesToConfig();
    				System.exit(0);
    			}
    		});
    		sqf.setYaListener(new ActionAndWindowListener() {
    			
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				save_file();
    				saveValuesToConfig();
    				System.exit(0);
    			}
    		});
    		sqf.setVisible(true);
    	} else {
    		saveValuesToConfig();
    		System.exit(0);
    	}
    }
    
    public void openSearchFrame() {
    	if(!isSearchOpened()) {
    		search.setVisible(true);
    	} else {
    		search.requestFocus();
    		search.getSearchField().requestFocus(true);
    		search.getSearchField().selectAll();
    	}
    }
    
    public void closeSearchFrame() {
    	search.getSearchField().setText("");
    	search.getReplaceField().setText("");
    	search.setVisible(false);
    }
    
    public boolean isSearchOpened() {
    	return search.isVisible();
    }
    
    private void saveValuesToConfig() {
    	config.setValue("fullscreen_enabled", isFullScreen());
    	if(isFullScreen()) {
    		setExtendedState(NORMAL);
    	}
    	config.setValue("window_size_x", getWidth());
    	config.setValue("window_size_y", getHeight());
    	config.setValue("window_pos_x", getX());
    	config.setValue("window_pos_y", getY());
    	config.saveValues();
    }
    
    public void openPWFrame() {
    	pwFrame.setVisible(true);
    }
    
    private void addScrollingEvent() {
        scroll.setWheelScrollingEnabled(false);
        scroll.addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				//Wenn SHIFT taste gedrückt ist scolle seitwärts
				JScrollBar sb = e.isShiftDown() ? scroll.getHorizontalScrollBar() : scroll.getVerticalScrollBar();
				
				//Wenn STRG taste gedrückt ist: scrolle schneller
				int scrollSpeed = e.isControlDown() ? 58 * 12 : 58;
				
				if(e.getWheelRotation() > 0) {
					sb.setValue((sb.getValue() + scrollSpeed <= sb.getMaximum()) ? sb.getValue() + scrollSpeed : sb.getMaximum());
				} else {
					sb.setValue((sb.getValue() - scrollSpeed >= sb.getMinimum()) ? sb.getValue() - scrollSpeed : sb.getMinimum());
				}
			}
		});
    }
    
}
