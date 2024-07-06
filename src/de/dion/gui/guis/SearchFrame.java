package de.dion.gui.guis;

import java.awt.Color;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;

import de.dion.Main;
import de.dion.gui.SuperGui;
import de.dion.gui.components.BorderComponent;
import de.dion.keybinds.Keybinds_SearchFrame;

public class SearchFrame extends SuperGui {

	private static final long serialVersionUID = 6983322520954085730L;
	private JPanel contentPane;
	private JTextField searchField;
	private JTextField replaceField;
	private JScrollPane scrollPane_TextGUI;
	private Keybinds_SearchFrame kb;
	
	JCheckBox chbx_backward = new JCheckBox("R\u00FCckw\u00E4rts suchen");
	JCheckBox chbx_case_sensitive = new JCheckBox("Gro\u00DF / Kleinschreibung beachten");
	JCheckBox chbx_alwaysontop = new JCheckBox("Immer im Vordergrund");
	JCheckBox chbx_regex = new JCheckBox("RegEx (Regulärer Ausdruck)");
	JCheckBox chbx_startagain = new JCheckBox("Am Ende von vorne beginnen");
	public boolean idk = false;
	
	

	/**
	 * Create the frame.
	 */
	public SearchFrame(int px, int py) {
		
		setTitle("Suchen und Ersetzen");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/icon.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		int width = 750;
		int height = 424;
		setSize(width, height);
		setLocation(px - width / 2, py - height / 2);
		
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0x515658));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		contentPane.setLayout(null);
		kb = new Keybinds_SearchFrame(contentPane);
		kb.addKeybinds();
		
		super.setLookAndFeel();
		
		JLabel text1 = new JLabel("Suchen nach:");
		text1.setFont(new Font("SansSerif", Font.PLAIN, 17));
		text1.setForeground(new Color(0xE5E5E5));
		text1.setHorizontalAlignment(SwingConstants.CENTER);
		text1.setBounds(0, 37, 164, 37);
		contentPane.add(text1);
		
		JLabel text2 = new JLabel("Ersetzen durch:");
		text2.setHorizontalAlignment(SwingConstants.CENTER);
		text2.setFont(new Font("SansSerif", Font.PLAIN, 17));
		text2.setForeground(new Color(0xE5E5E5));
		text2.setBounds(0, 80, 164, 37);
		contentPane.add(text2);
		
		
		JButton btn1 = new JButton("Suchen");
		btn1.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btn1.setBorder(new LineBorder(new Color(0x0078D7), 2, false));
		btn1.setBackground(new Color(0xE1E1E1));
		btn1.setForeground(new Color(0x000000));
		btn1.setBounds(585, 40, 140, 32);
		contentPane.add(btn1);
		btn1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				search(searchField.getText());
			}
		});
		
		JButton btn2 = new JButton("Finden / Ersetzen");
		btn2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btn2.setBorder(new LineBorder(new Color(0x0078D7), 2, false));
		btn2.setBackground(new Color(0xE1E1E1));
		btn2.setForeground(new Color(0x000000));
		btn2.setBounds(585, 83, 140, 32);
		contentPane.add(btn2);
		btn2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				searchAndReplace(searchField.getText(), replaceField.getText());
			}
		});
		
		JButton btn3 = new JButton("Alle ersetzen");
		btn3.setForeground(Color.BLACK);
		btn3.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btn3.setBorder(new LineBorder(new Color(0x0078D7), 2, false));
		btn3.setBackground(new Color(225, 225, 225));
		btn3.setBounds(585, 128, 140, 32);
		contentPane.add(btn3);
		btn3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				replaceAll(searchField.getText(), replaceField.getText());
			}
		});
		
		
		searchField = new JTextField();
		searchField.setColumns(20);
		searchField.setBounds(159, 42, 409, 30);
		contentPane.add(searchField);
		searchField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				search(searchField.getText());
			}
		});
		
		replaceField = new JTextField();
		replaceField.setColumns(20);
		replaceField.setBounds(159, 85, 409, 30);
		contentPane.add(replaceField);
		replaceField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				searchAndReplace(searchField.getText(), replaceField.getText());
			}
		});
		
		BorderComponent white_line = new BorderComponent();
		white_line.setBounds(12, 173, 713, 2);
		white_line.setBorder(new LineBorder(new Color(0xbbbbbb), 1, false));
		contentPane.add(white_line);
		
		BorderComponent blue_box = new BorderComponent();
		blue_box.setLocation(15, 197);
		blue_box.setSize(710, this.getHeight() - blue_box.getY() - 50);
		blue_box.setBorder(new LineBorder(new Color(0x0078D7), 1, false));
		contentPane.add(blue_box);
		
		
		//checkboxen
		chbx_backward.setSelected(true);
		chbx_backward.setHorizontalAlignment(SwingConstants.LEFT);
		chbx_backward.setBounds(26, 210, 213, 25);
		chbx_backward.setForeground(new Color(0xDDDDDD));
		chbx_backward.setBackground(contentPane.getBackground());
		chbx_backward.setSelected(config.getBooleanValue("search_backward"));
		chbx_backward.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				config.setValue("search_backward", chbx_backward.isSelected());
				config.saveValues();
			}
		});
		contentPane.add(chbx_backward);
		
		
		chbx_case_sensitive.setHorizontalAlignment(SwingConstants.LEFT);
		chbx_case_sensitive.setForeground(new Color(0xDDDDDD));
		chbx_case_sensitive.setBackground(contentPane.getBackground());
		chbx_case_sensitive.setBounds(26, 240, 213, 25);
		chbx_case_sensitive.setSelected(config.getBooleanValue("search_case_sensitive"));
		chbx_case_sensitive.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				config.setValue("search_case_sensitive", chbx_case_sensitive.isSelected());
				config.saveValues();
			}
		});
		contentPane.add(chbx_case_sensitive);
		
		
		chbx_alwaysontop.setHorizontalAlignment(SwingConstants.LEFT);
		chbx_alwaysontop.setForeground(new Color(0xDDDDDD));
		chbx_alwaysontop.setBackground(contentPane.getBackground());
		chbx_alwaysontop.setBounds(26, 270, 213, 25);
		chbx_alwaysontop.setSelected(config.getBooleanValue("search_alwaysontop"));
		chbx_alwaysontop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				config.setValue("search_alwaysontop", chbx_alwaysontop.isSelected());
				config.saveValues();
				setAlwaysOnTop(chbx_alwaysontop.isSelected());
			}
		});
		setAlwaysOnTop(chbx_alwaysontop.isSelected());
		contentPane.add(chbx_alwaysontop);
		
		
		chbx_regex.setHorizontalAlignment(SwingConstants.LEFT);
		chbx_regex.setForeground(new Color(0xDDDDDD));
		chbx_regex.setBackground(contentPane.getBackground());
		chbx_regex.setBounds(26, 300, 213, 25);
		chbx_regex.setSelected(config.getBooleanValue("search_regex"));
		chbx_regex.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				config.setValue("search_regex", chbx_regex.isSelected());
				config.saveValues();
			}
		});
		contentPane.add(chbx_regex);
		
		
		chbx_startagain.setHorizontalAlignment(SwingConstants.LEFT);
		chbx_startagain.setForeground(new Color(0xDDDDDD));
		chbx_startagain.setBackground(contentPane.getBackground());
		chbx_startagain.setBounds(26, 330, 213, 25);
		chbx_startagain.setSelected(config.getBooleanValue("search_startagain"));
		chbx_startagain.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				config.setValue("search_startagain", chbx_startagain.isSelected());
				config.saveValues();
			}
		});
		contentPane.add(chbx_startagain);
		
		
	}
	
	
	private void search(String searchWord) {
		JTextArea area = Main.getGUI().getTextArea();
		String atext = area.getText();
		
		if(!chbx_case_sensitive.isSelected()) {
			searchWord = searchWord.toLowerCase();
			atext = atext.toLowerCase();
		}
		
		int index = -1;
		if(chbx_backward.isSelected()) {
			index = atext.substring(0, area.getSelectionStart()).lastIndexOf(searchWord);
			if(index < 0 && chbx_startagain.isSelected()) {
				index = atext.lastIndexOf(searchWord);
			}
		} else {
			index = atext.indexOf(searchWord, area.getCaretPosition());
			if(index < 0 && chbx_startagain.isSelected()) {
				index = atext.indexOf(searchWord);
			}
		}
		
		if(index >= 0) { //gefunden
			//Makiere das Gefundene Wort
			area.setCaretPosition(0);
			area.select(index, index + searchWord.length());
			
			//Scrollbar so einstellen, dass das Wort mittig von der Höhe auf dem Texteditor steht.
			try {
				JScrollBar scrollV = scrollPane_TextGUI.getVerticalScrollBar();
				double line = area.getLineOfOffset(index) + 1;
				double positionFaktor = line / area.getLineCount();
				double extend = scrollV.getModel().getExtent();
				double maxScrolled = scrollV.getModel().getMaximum();
				scrollV.setValue(
						(int)((maxScrolled * positionFaktor) - extend / 2)
				);
			} catch (BadLocationException e) {}
			
		} else {
			//nicht gefunden
			playErrorSound();
		}
	}
	
	private void searchAndReplace(String text, String rpl) {
		JTextArea area = Main.getGUI().getTextArea();
		String atext = area.getText();
		if(text.equals("")) {
			return;
		}
		
		if(!chbx_case_sensitive.isSelected()) {
			text = text.toLowerCase();
			atext = atext.toLowerCase();
		}
		
		int index = -1;
		if(chbx_backward.isSelected()) {
			index = atext.substring(0, area.getSelectionStart()).lastIndexOf(text);
			if(index < 0 && chbx_startagain.isSelected()) {
				index = atext.lastIndexOf(text);
			}
		} else {
			index = atext.indexOf(text, area.getCaretPosition());
			if(index < 0 && chbx_startagain.isSelected()) {
				index = atext.indexOf(text);
			}
		}
		
		if(index >= 0) {
			//gefunden
			area.replaceRange(rpl, index, index + text.length());
//			area.select(index, index + text.length());
		} else {
			//nicht gefunden
			playErrorSound();
		}
	}
	
	private void replaceAll(String text, String rpl) {
		JTextArea area = Main.getGUI().getTextArea();
		String atext = area.getText();
		if(text.equals("")) {
			return;
		}
		
		if(!chbx_case_sensitive.isSelected()) {
			text = text.toLowerCase();
			atext = atext.toLowerCase();
		}
		
		int index = atext.indexOf(text);
		while(index >= 0) {
			area.replaceRange(rpl, index, index + text.length());
			atext = chbx_case_sensitive.isSelected() ? area.getText() : area.getText().toLowerCase();
			index = atext.indexOf(text);
		}
	}
	
	public JTextField getSearchField() {
		return this.searchField;
	}
	
	public JTextField getReplaceField() {
		return this.replaceField;
	}
	
	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane_TextGUI = scrollPane;
	}
	
}
