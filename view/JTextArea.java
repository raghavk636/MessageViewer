package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;

public class JTextArea extends javax.swing.JTextArea {
    public JTextArea(String hint, int textSize, int padding) {
        super();
        setFont(new Font("Segoe UI", Font.BOLD, textSize));
        setBackground(null);
        setText(hint);
        setForeground(GUIConstants.textFieldHint);
        setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));


        addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                if(getText().equals(hint)) {
                    setText("");
                    setForeground(GUIConstants.black);
                }

            }

            @Override
            public void focusLost(FocusEvent e) {
                if(getText().equals("")) {
                    setText(hint);
                    setForeground(GUIConstants.textFieldHint);
                }

            }
        });
    }

    public JTextArea(String text, int textSize, Color color, int style) {
        super();
        setFont(new Font("Segoe UI", style, textSize));
        setText(text);
        setForeground(color);
        setEditable(false);
        setPreferredSize(new Dimension(1000,(int) getPreferredSize().getHeight()));
    }

}
