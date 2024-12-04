package view;

import javax.swing.JComponent;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class JScrollPane extends javax.swing.JScrollPane {

    public JScrollPane(JComponent component) {
        super(component);
        setBackground(null);
        getViewport().setBackground(null);
        setBorder(null);

        JScrollBar scrollBar = new JScrollBar();
        scrollBar.setBackground(null);
        scrollBar.setBorder(null);
        scrollBar.setBorder(null);
        scrollBar.setUI(new BasicScrollBarUI() {

            //to change scroll bar color

            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = GUIConstants.textFieldHint;
            }

//          to hide the arrows of the scroll bar
            protected JButton createDecreasedButton(int orientation) {
                return createZeroButton();

            }

            protected JButton createIncreasedButton(int orientation) {
                return createZeroButton();

            }

            private JButton createZeroButton() {
                JButton btn = new JButton();
                btn.setPreferredSize(new Dimension(0,0));
                btn.setMaximumSize(new Dimension(0,0));
                btn.setMinimumSize(new Dimension(0,0));
                return btn;


            }


        });

        setVerticalScrollBar(scrollBar);

        // to hide horizontal scroll bar

        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);

        getVerticalScrollBar().setPreferredSize(new Dimension(5,0));
        getHorizontalScrollBar().setPreferredSize(new Dimension(0,5));

    }


}
