package de.dion.gui.guis;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import de.dion.gui.SuperGui;

public class PassWordFrame extends SuperGui {
    private static final long serialVersionUID = 8956691425692003003L;
    private JPanel contentPane;
    private JPasswordField pwField;
    private JButton btn1;
    private JCheckBox showPassword;
    private char defaultEchoChar;
    private File f;

    public PassWordFrame(int px, int py, File f) {
        this.f = f;
        setTitle("Bitte gib das Kennwort ein!");
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/icon.png")));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);

        // Fenstergröße und Position anpassen
        int width = 559;
        int height = 120;
        setSize(width, height);
        setLocation(px - width / 2, py - height / 2);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(0x515658));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        super.setLookAndFeel();

        // Passwortfeld
        pwField = new JPasswordField();
        pwField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        pwField.setColumns(20);
        pwField.setBounds(12, 12, 409, 30);
        contentPane.add(pwField);
        defaultEchoChar = pwField.getEchoChar();

        // Button zum Bestätigen
        btn1 = new JButton("Enter");
        btn1.setFont(new Font("SansSerif", Font.PLAIN, 16));
        btn1.setBorder(new LineBorder(new Color(0x0078D7), 2, false));
        btn1.setBackground(new Color(0xE1E1E1));
        btn1.setForeground(new Color(0x000000));
        btn1.setBounds(433, 12, 108, 30);
        contentPane.add(btn1);

        // Checkbox zum Anzeigen des Passworts
        showPassword = new JCheckBox("Passwort anzeigen");
        showPassword.setFont(new Font("SansSerif", Font.PLAIN, 14));
        showPassword.setBackground(new Color(0x515658));
        showPassword.setForeground(new Color(0xFFFFFF));
        showPassword.setBounds(12, 52, 200, 25);
        showPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPassword.isSelected()) {
                    // Passwort sichtbar machen
                    pwField.setEchoChar((char) 0);
                } else {
                    // Passwort maskieren
                    pwField.setEchoChar(defaultEchoChar);
                }
            }
        });
        contentPane.add(showPassword);
    }

    public void addAction(ActionListener al) {
        pwField.addActionListener(al);
        btn1.addActionListener(al);
    }

    public String getPassWord() {
        return new String(pwField.getPassword());
    }

    public boolean hasPassWord(File f) {
        return pwField.getPassword().length > 0 && this.f.getPath().equalsIgnoreCase(f.getPath());
    }

    public void clearPassWord() {
        pwField.setText("");
        showPassword.setSelected(false);
        pwField.setEchoChar(defaultEchoChar);
    }

    @Override
    public void setVisible(boolean b) {
        if (b) {
            clearPassWord();
        }
        super.setVisible(b);
        requestFocus(false);
        pwField.requestFocus(false);
        new Thread(() -> {
            try { Thread.sleep(150); } catch (Exception e) {}
            pwField.requestFocus(false);
        }).start();
    }
}
