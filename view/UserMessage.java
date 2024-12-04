package view;

import model.*;
        import controller.Controller;
import javax.swing.*;
        import java.awt.*;
        import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class UserMessage {
    Controller controller;
    MessageViewerUser currentUser;
    MessageViewerUser recipient;
    JFrame frame;
    JPanel panel;
    JPanel sideBar;
    JPanel header;
    JPanel messagesInThread;

    public UserMessage(Controller controller,JFrame frame,  String username, String receipientUsername) {
        this.controller = controller;
        currentUser = MessageViewerUser.getUser(username);
        recipient = MessageViewerUser.getUser(receipientUsername);
        this.frame = frame;
        frame.getContentPane().removeAll();
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
        Dimension dimension = new Dimension(500, 470);
        header.setPreferredSize(dimension);
        header.setMaximumSize(dimension);
        header.setMinimumSize(dimension);
        header.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JPanel north = new JPanel(new BorderLayout());
        north.setBackground(null);
        north.add(new JLabel(recipient.getUsername(),20,GUIConstants.black,Font.BOLD),BorderLayout.WEST);

        header.add(north, BorderLayout.NORTH);

        //  JTextArea messageIn = new JTextArea("Speak your thoughts...",16,20);
        // header.add(new JScrollPane(messageIn),BorderLayout.CENTER);
        ///

        ArrayList<Message> messageThread = controller.getMessagesFor(currentUser, recipient);

        // Create the table model and table
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Sender", "Receiver", "Date", "Content"}, 0);
        JTable table = new JTable(model);

        /// TO RESET MESSAGE STUFF

//        // Populate the table with map data
//        for (Message m : messageThread) {
//            model.addRow(new Object[]{m.getSender().getName(), m.getReceiver().getName(), m.getDate(), m.getContent()});
//        }

        /// THIS !!!!!!

//        messagesInThread = new JPanel(new BoxLayout(messagesInThread, BoxLayout.Y_AXIS));

//        for (Message m : messageThread) {
//            header.add(Box.createVerticalStrut(7));
//            header.add(new MessageUI3(m.getSender().getUsername(),m.getReceiver().getUsername(),m.getDate(),m.getContent()));
//        }
//
//

        //add panel to the frame
        //header.add(new JScrollPane(table), BorderLayout.EAST);




/// ///////////////////add a panel to send fresh text
        JPanel newMessage = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//        JPanel newMessage = new JPanel(new BorderLayout());
        newMessage.setBackground(GUIConstants.white);
        Dimension dimension2 = new Dimension(500,58);
        newMessage.setPreferredSize(dimension2);
        newMessage.setMaximumSize(dimension2);
        newMessage.setMinimumSize(dimension2);
        newMessage.setBorder(BorderFactory.createEmptyBorder(10,10,10,15));


        JTextArea messageIn = new JTextArea("Say something!",18,5);
        newMessage.add(new JScrollPane(messageIn),BorderLayout.CENTER);

        JButton messageBtn = new JButton("SEND", 35,16);
        messageBtn.setPreferredSize(new Dimension(81,37));
        newMessage.add(messageBtn,BorderLayout.EAST);

        messageBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {


                controller.sendMessage(recipient.getUsername(),messageIn.getText());
                new UserMessage(controller, frame, currentUser.getUsername(), recipient.getUsername());

//                new MessagingUI(thread, controller, currentUser, otherUser);
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

      panel.add(header, BorderLayout.NORTH);
      panel.add(newMessage);

//        header.add(south, BorderLayout.SOUTH);
        /// add to panel
       // panel.add(header);
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


