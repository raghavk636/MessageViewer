package view;

import java.awt.*;

public class JLabel extends javax.swing.JLabel {


    public JLabel(String text) {
        super(text);
    }
    public JLabel(String text, int textSize, Color color, int style) {
        super(text);
        setFont(new Font("Segoe UI",style, textSize));
        setText(text);
        setForeground(color);

    }
}
