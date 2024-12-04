package view;

import model.*;
import controller.Controller;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

public class SearchUI2 {
    Controller controller;
    MessageViewerUser currentUser;
    JFrame frame;
    JPanel panel;
    JPanel sideBar;



    public SearchUI2(Controller controller, JFrame frame, MessageViewerUser currentUser) {
        this.controller = controller;
//        this.frame = frame;
        this.currentUser = currentUser;
        frame.getContentPane().removeAll();
        this.frame = frame;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        renderUI();


    }

    private void renderUI() {
        frame.getContentPane().setBackground(GUIConstants.background);
        frame.getContentPane().setLayout(new BorderLayout());
        //main panel
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(null);

        //ADD THE SEARCHBAR HERE IN PANEL

        JPanel center = new JPanel(new GridLayout(8, 1, 10, 10));
        center.setBackground(GUIConstants.background);
        center.setBorder(BorderFactory.createEmptyBorder(22, 231, 17, 231));
        JTextField search = new JTextField("Search for a username!",17);

        //center is the panel this thing is in
        center.add(search);


        JButton searchBtn = new JButton("SEARCH", 35,16);
        searchBtn.setPreferredSize(new Dimension(81,37));
        center.add(searchBtn,BorderLayout.EAST);
        panel.add(center,BorderLayout.CENTER);


        //mouse listener
        searchBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boolean userFound = false;

                if (search.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "The username cannot be empty!", "User Search", JOptionPane.ERROR_MESSAGE);
                }

                try {
                    currentUser.searchUser(search.getText());
                    JOptionPane.showMessageDialog(frame, "User  found!!", "User Info", JOptionPane.INFORMATION_MESSAGE);

                    //                    frame.dispose();
                    new UserProfileUI(controller,currentUser.getUsername(),frame,search.getText());

                } catch (InvalidUsernameException ex) {
//                    new Alert("User not found!", frame);
                    JOptionPane.showMessageDialog(frame, "User not found!!", "User Info", JOptionPane.INFORMATION_MESSAGE);
                }
//                if (userFound) {
//
//                    // frame.dispose();
//
//                    //renderUserProfileInSearch adds the userProfile thing to the frame
//                    renderUserProfileInSearch(search);
//
//                    //we render an updated UI with the userProfile panel
//                   // renderUI();
//
//                }

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


        //sidebar


        renderSideBar();


        //add panel to the frame
        frame.add(panel, BorderLayout.CENTER);
        //add sidebar to the frame
//        frame.getContentPane().add(sideBar, BorderLayout.WEST);
        frame.pack();
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
        frame.requestFocus();
    }



//    //METHOD TO RENDER THE USER PROFILE IN THE SEARCH MENU AFTER SEARCHING FOR IT
//
//    private void renderUserProfileInSearch(JTextField search) {
//
//        JButton friendBtn = new JButton("Add Friend", 45, 15);
//        friendBtn.setVisible(true);
//
//        JButton blockBtn = new JButton("Block", 45, 15);
//        blockBtn.setVisible(true);
//
//        JButton messageBtn = new JButton("Message", 45, 15);
//        messageBtn.setVisible(true);
//
//
//        JPanel userProfile = new JPanel(new GridLayout(4, 1, 10, 10));
//        userProfile.setBackground(GUIConstants.white);
//        userProfile.setBorder(BorderFactory.createEmptyBorder(22, 231, 17, 231));
//
//        //HERE !!!!!!
//
//        try {
//            MessageViewerUser searchedUser = currentUser.searchUser(search.getText());
//            JLabel profileUsername = new JLabel(searchedUser.getUsername(), 20, GUIConstants.textFieldHint, Font.BOLD);
//            JLabel profileName = new JLabel(searchedUser.getName(), 20, GUIConstants.textFieldHint, Font.BOLD);
//
//            //ADDING TO USER PROFILE
//
//            userProfile.add(profileUsername);
//            userProfile.add(profileName);
//            userProfile.add(friendBtn, BorderLayout.SOUTH);
//            userProfile.add(blockBtn, BorderLayout.SOUTH);
//            userProfile.add(messageBtn, BorderLayout.SOUTH);
//
//        } catch (InvalidUsernameException e) {
//            throw new RuntimeException(e);
//        }
//
//        frame.getContentPane().add(userProfile, BorderLayout.SOUTH);
//    }

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

        //
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
        frame.dispose();
        new WelcomeUI(controller);
    }
}
