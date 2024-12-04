package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.JLabel;

public class JButton extends JLabel {  // Keep the name as JButton

    private Shape shape;
    private int radius;

    public JButton(String text){
        super(text);
    }
    public JButton(String text, int radius, int textSize) {
        super(text);  // Calls JLabel's constructor with the text argument
        this.radius = radius;
        setFont(new Font("Segoe UI", Font.BOLD, textSize));
        setText(text);
        setOpaque(false);

        setBackground(GUIConstants.blue);

        setForeground(GUIConstants.white);
        setHorizontalAlignment(CENTER);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    //for rounded corners

    protected void paintComponent(Graphics g) {
        g.setColor(GUIConstants.blue);
        g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1,radius,radius);
        super.paintComponent(g);
    }

    //for rounded border

    protected void paintBorder(Graphics g) {
        g.setColor(GUIConstants.white);
        g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1,radius,radius);

    }

}
