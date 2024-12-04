package view;

import controller.Controller;
import model.MessageViewerUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BlockedUI extends JPanel {

    public BlockedUI(MessageViewerUser friend, Controller controller) {
        setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        setBackground(GUIConstants.white);
        setBorder(BorderFactory.createEmptyBorder(15,15,15,25));

        JLabel author = new JLabel(friend.getUsername(),20,GUIConstants.black, Font.BOLD);
        add(author,BorderLayout.WEST);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        right.setBackground(null);

        JButton block = new JButton("Block",35,16);
        block.setPreferredSize(new Dimension(81,37));
        block.setCursor(new Cursor(Cursor.HAND_CURSOR));

        //check this
        block.setVisible(false);
        right.add(block);

        JLabel unblock = new JLabel("Unblock",16,GUIConstants.blue, Font.BOLD);
        unblock.setCursor(new Cursor(Cursor.HAND_CURSOR));

        //check this
        unblock.setVisible(true);
        right.add(unblock);

        unblock.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.unblockUser(author.getText());
                new Alert(author.getText() + " has been unblocked");



            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(GUIConstants.background);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(GUIConstants.white);


            }
        });


        add(right);



        Dimension dimension = new Dimension(500,72);
        setPreferredSize(dimension);
        setMaximumSize(dimension);
        setMinimumSize(dimension);

    }
}
