package view;

import model.*;
import controller.Controller;
import view.*;
import view.JLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

public class UserProfileUI extends JFrame {
    Controller controller;
    MessageViewerUser currentUser;
    MessageViewerUser searchedUser;
    JFrame frame;
    JPanel panel;
    JPanel sideBar;
    JPanel profile;
    JPanel userInfo;
    JLabel isAFriend = new JLabel("Friend");

    public UserProfileUI(Controller controller, String username, JFrame frame,String searchedUsername) {
        this.controller = controller;
        currentUser = MessageViewerUser.getUser(username);
        searchedUser =MessageViewerUser.getUser(searchedUsername);

        frame.getContentPane().removeAll();
        this.frame = frame;


//        frame = new JFrame();
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
        panel.add(Box.createVerticalStrut(7));

        //sidebar
        renderSideBar();
        //add header
        renderProfile();

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

    private void renderProfile(){

        //goes at the very top
        JPanel title = new JPanel(new BorderLayout());
        title.setBackground(GUIConstants.white);
        title.add(new JLabel("PROFILE",20,GUIConstants.blue,Font.BOLD),BorderLayout.NORTH);


        userInfo = new JPanel(new BorderLayout());
        userInfo.setBackground(GUIConstants.white);

        try {
            userInfo.add(new JLabel("Name:" + searchedUser.getName(),20,GUIConstants.black,Font.BOLD),BorderLayout.NORTH);
        } catch (Exception e) {
        }
        userInfo.add(new JLabel("Username:" + searchedUser.getUsername(),20,GUIConstants.black,Font.BOLD),BorderLayout.CENTER);
        if(controller.isMyFriend(currentUser, searchedUser))
            userInfo.add(isAFriend,BorderLayout.SOUTH);

        Dimension dimensionInfo = new Dimension(400, 200);
        userInfo.setPreferredSize(dimensionInfo);
        userInfo.setMaximumSize(dimensionInfo);
        userInfo.setMinimumSize(dimensionInfo);

        JPanel buttons = new JPanel(new BorderLayout());
        buttons.setBackground(GUIConstants.white);

        // THE BUTTONS

        JButton addFriendBtn = new JButton("add friend",45,18);
        buttons.add(addFriendBtn,BorderLayout.NORTH);

        JButton blockBtn = new JButton("block",45,18);
        buttons.add(blockBtn,BorderLayout.CENTER);

        JButton messageBtn = new JButton("message",45,18);
        buttons.add(messageBtn,BorderLayout.SOUTH);


        /// MOUSE LISTENER

        //FOR ADD FRIEND

        addFriendBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(currentUser.getBlocked().contains(searchedUser)) {
                    new Alert("user blocked",frame);
                } else if (searchedUser.getBlocked().contains(currentUser)) {
                    new Alert("this user has blocked you",frame);
                } else if (searchedUser.equals(currentUser)) {
                    new Alert("that's you!", frame);
                } else {
                    controller.addFriend(searchedUser.getUsername());
                    if (controller.addFriend(searchedUser.getUsername()))
                        JOptionPane.showMessageDialog(frame, searchedUser.getUsername() + " added as a friend", "User Info", JOptionPane.INFORMATION_MESSAGE);
                    else {
                        JOptionPane.showMessageDialog(frame, searchedUser.getUsername() + " couldn't be added as a friend", "User Info", JOptionPane.INFORMATION_MESSAGE);
                        userInfo.revalidate();
                        frame.revalidate();
                        frame.repaint();
                    }
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

        /// FOR BLOCKING

        blockBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (searchedUser.equals(currentUser)) {
                    new Alert("can't block yourself!!",frame);
                } else {
                    controller.blockUser(searchedUser.getUsername());
                    new Alert(searchedUser.getUsername() + " blocked", frame);
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

        /// FOR SENDING MESSAGES
        messageBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(currentUser.getBlocked().contains(searchedUser)) {
                    new Alert("user blocked",frame);
                } else if (searchedUser.getBlocked().contains(currentUser)) {
                    new Alert("this user has blocked you",frame);
                } else if (searchedUser.equals(currentUser)) {
                    new Alert("that's you!",frame);
                } else {
                    new UserMessage(controller, frame, currentUser.getUsername(), searchedUser.getUsername());
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
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

        });

        profile = new JPanel(new BorderLayout());
        profile.setBackground(GUIConstants.background);
        Dimension dimension = new Dimension(500, 230);
        profile.setPreferredSize(dimension);
        profile.setMaximumSize(dimension);
        profile.setMinimumSize(dimension);
        profile.setBorder(BorderFactory.createEmptyBorder(10, 15, 20, 15));

       profile.add(title, BorderLayout.NORTH);
       profile.add(userInfo, BorderLayout.CENTER);
       profile.add(buttons, BorderLayout.SOUTH);

        panel.add(profile);
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
        frame.dispose();
        new WelcomeUI(controller);
    }
}

