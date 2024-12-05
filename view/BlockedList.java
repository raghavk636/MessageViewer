package view;


import controller.Controller;
import model.MessageViewerUser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;

public class BlockedList implements BlockedListInterface {
    Controller controller;
    MessageViewerUser currentUser;
    JFrame frame;
    JPanel panel;
    JPanel sideBar;
    JPanel header;

    public BlockedList(Controller controller, String username, JFrame frame) {
        this.controller = controller;
        currentUser = MessageViewerUser.getUser(username);
        System.out.println("In client: " + currentUser.getUsername());
        this.frame = frame;
        frame.getContentPane().removeAll();
//        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//      renderMessageLog();
        renderUI();


    }

    private void renderUI() {
        frame.getContentPane().setBackground(GUIConstants.background);
        frame.getContentPane().setLayout(new BorderLayout());
        //main panel
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(null);
        panel.add(Box.createVerticalStrut(7));
        //side bar
        renderSideBar();
        //add header
        renderHeader();

        //add panel to the frame
        frame.getContentPane().add(new JScrollPane(panel), BorderLayout.CENTER);
        frame.getContentPane().add(Box.createHorizontalStrut(182),BorderLayout.EAST);
        //add sidebar to the frame
        frame.getContentPane().add(sideBar, BorderLayout.WEST);
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
        frame.requestFocus();
    }

    private void renderHeader(){
        header = new JPanel(new BorderLayout());
        header.setBackground(GUIConstants.white);
        Dimension dimension = new Dimension(500, 50);
        header.setPreferredSize(dimension);
        header.setMaximumSize(dimension);
        header.setMinimumSize(dimension);
        header.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JPanel north = new JPanel(new BorderLayout());
        north.setBackground(null);
        north.add(new JLabel("BLOCKS",20,GUIConstants.blue,Font.BOLD),BorderLayout.WEST);

        header.add(north, BorderLayout.NORTH);

        //  JTextArea messageIn = new JTextArea("Speak your thoughts...",16,20);
        // header.add(new JScrollPane(messageIn),BorderLayout.CENTER);
        ///

        // Adding table of message threads to header panel here !!

        ArrayList<MessageViewerUser> blocked = currentUser.getBlocked();

        panel.add(header);

//        // Create the table model and table
//        DefaultTableModel model = new DefaultTableModel(new Object[]{"blocked"}, 0);
//        JTable table = new JTable(model);
//
//        // Populate the table with map data
//        for (MessageViewerUser notAFriend : blocked) {
//            model.addRow(new Object[]{notAFriend.getName()});
//        }


        //add panel to the frame
//        header.add(new JScrollPane(table), BorderLayout.EAST);

        for (MessageViewerUser block : blocked) {
            panel.add(Box.createVerticalStrut(7));
            panel.add(new BlockedUI(block,controller), BorderLayout.EAST);
        }


    }

    //METHOD TO RENDER SIDE BAR

    private void renderSideBar(){
        sideBar = new JPanel();

        sideBar.setBackground(GUIConstants.white);
        Dimension sideBarDim = new Dimension(187, 1000);
        sideBar.setPreferredSize(sideBarDim);
        sideBar.setMaximumSize(sideBarDim);
        sideBar.setMinimumSize(sideBarDim);
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));

        JPanel profile = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
        profile.setMaximumSize(new Dimension(210, 50));
        profile.setBackground(GUIConstants.white);
        profile.add(new JLabel(currentUser.getName(),20,GUIConstants.black,Font.BOLD));
        profile.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        sideBar.add(profile);
        sideBar.add(Box.createVerticalStrut(3));
        sideBar.add(new SideButton("Home", controller, frame, currentUser.getUsername()));
        sideBar.add(new SideButton("Search", controller, frame, currentUser.getUsername()));
        sideBar.add(new SideButton("Friends", controller, frame, currentUser.getUsername()));
        sideBar.add(new SideButton("Blocked", controller, frame, currentUser.getUsername()));
        sideBar.add(new SideButton("Log Out", controller, frame, currentUser.getUsername()));
        sideBar.add(Box.createVerticalStrut(3));

//add sidebar to frame
        frame.getContentPane().add(sideBar, BorderLayout.WEST);
    }



    private void logOut(){
        controller.logout();

        new WelcomeUI(controller, frame);
    }
}


