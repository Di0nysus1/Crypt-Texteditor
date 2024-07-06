package de.dion.gui.guis;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import de.dion.gui.SuperGui;
import de.dion.listeners.ActionAndWindowListener;

public class SaveQFrame extends SuperGui {

	private static final long serialVersionUID = -935271196667756073L;
	private boolean isYaActionSet = false;
	private boolean isNeActionSet = false;
	private final JButton buttonYes = new JButton("Ya");
	private final JButton buttonNo = new JButton("Nö");
	private final boolean doNothingOnClose;
	private JPanel contentPane;
	
	
	public SaveQFrame(int px, int py, boolean doNothingOnClose) {
		
		this.doNothingOnClose = doNothingOnClose;
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/icon.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		
		//place in the middle of the editor 
		int width = 399;
		int height = 210;
		setSize(width, height);
		setLocation(px - width / 2, py - height / 2);
		
//		setBounds(500, 500, 399, 210);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0x515658));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel lblMchtestDuDie = new JLabel("Möchtest du die Datei speichern?");
		lblMchtestDuDie.setForeground(new Color(0xDDDDDD));
		lblMchtestDuDie.setFont(new Font("SansSerif", Font.PLAIN, 18));
		lblMchtestDuDie.setHorizontalAlignment(SwingConstants.CENTER);
		lblMchtestDuDie.setBounds(12, 39, 357, 37);
		contentPane.add(lblMchtestDuDie);
		
		super.setLookAndFeel();
		
		buttonYes.setFont(new Font("SansSerif", Font.PLAIN, 16));
		buttonYes.setBorder(new LineBorder(new Color(0x0078D7), 2, false));
		buttonYes.setBackground(new Color(0xE1E1E1));
		buttonYes.setForeground(new Color(0x000000));
		buttonYes.setBounds(12 + 35, 125, 135, 37);
//		btnYa.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				
//				dispose();
//			}
//		});
		contentPane.add(buttonYes);
		
		
		buttonNo.setFont(new Font("SansSerif", Font.PLAIN, 16));
		buttonNo.setBorder(new LineBorder(new Color(0x0078D7), 2, false));
		buttonNo.setBackground(new Color(0xE1E1E1));
		buttonNo.setForeground(new Color(0x000000));
		buttonNo.setBounds(254 - 35, 125, 135, 37);
//		btnNe.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				dispose();
//			}
//		});
		contentPane.add(buttonNo);
		
		Canvas canvas = new Canvas();
		canvas.setBackground(new Color(0x616668));
		canvas.setBounds(0, 111, 393, 63);
		contentPane.add(canvas);
	}
	
	public boolean isDoNothingOnClose() {
		return doNothingOnClose;
	}

	public void setYaListener(ActionAndWindowListener al) {
		addActionAndDispose(buttonYes, al);
		isYaActionSet = true;
	}
	
	public void setNeListener(ActionAndWindowListener al) {
		if(!doNothingOnClose) {
			addWindowListener(al);
		}
		addActionAndDispose(buttonNo, al);
		isNeActionSet = true;
	}
	
	
	@Override
	public void setVisible(boolean b) {
		if(!isNeActionSet) {
			addDisposeFunction(buttonNo);
			isNeActionSet = true;
		}
		if(!isYaActionSet) {
			addDisposeFunction(buttonYes);
			isYaActionSet = true;
		}
		super.setVisible(b);
	}

}
