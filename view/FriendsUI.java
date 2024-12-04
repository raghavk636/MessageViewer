package view;

import controller.Controller;
import model.MessageViewerUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FriendsUI extends JPanel {
    public FriendsUI(MessageViewerUser friend, Controller controller) {
        setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        setBackground(GUIConstants.white);
        setBorder(BorderFactory.createEmptyBorder(15,15,15,25));

        JLabel author = new JLabel(friend.getUsername(),20,GUIConstants.black, Font.BOLD);
        add(author,BorderLayout.CENTER);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        right.setBackground(null);

        JButton block = new JButton("Add",35,16);
        block.setPreferredSize(new Dimension(81,37));
        block.setCursor(new Cursor(Cursor.HAND_CURSOR));

        //check this
        block.setVisible(false);
        right.add(block);

        JLabel remove = new JLabel("Remove",16,GUIConstants.blue, Font.BOLD);
        remove.setCursor(new Cursor(Cursor.HAND_CURSOR));

        //check this
        remove.setVisible(true);
        right.add(remove);
        remove.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.removeFriend(author.getText());
                new Alert(author.getText() + " removed as friend");



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
