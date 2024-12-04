package view;

import controller.Controller;
import model.MessageViewerUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SideButton extends JPanel {
    Controller controller;
    JFrame frame;
    String username;
    public SideButton(String text, Controller controller, JFrame frame, String username) {
        super(new FlowLayout(FlowLayout.LEFT,10,10));
        this.controller = controller;
        this.frame = frame;
        this.username = username;
        setMaximumSize(new Dimension(182,50));
        setBackground(GUIConstants.white);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        add(new JLabel(text,17,GUIConstants.textFieldHint,Font.BOLD));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(text.equals("Log Out")){
                    logOut();
                }else if(text.equals("Search")){
                    new SearchUI2(controller,frame, MessageViewerUser.getUser(username));
                } else if(text.equals("Home")){
                    new HomeUI(controller,username,frame);
                } else if(text.equals("Friends")){
                    new FriendList(controller, username,frame);
                } else if(text.equals("Blocked")){
                    new BlockedList(controller, username,frame);;
                }
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



    }

    private void logOut(){
        controller.logout();
        new WelcomeUI(controller, frame);
    }
}
