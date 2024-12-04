package view;

import model.*;
import controller.Controller;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

public class HomeUI {
    Controller controller;
    MessageViewerUser currentUser;
    JFrame frame;
    JPanel panel;
    JPanel sideBar;
    JPanel header;
    JPanel notifs;

    public HomeUI(Controller controller, String username, JFrame frame) {
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
        Dimension dimension = new Dimension(500, 356);
        header.setPreferredSize(dimension);
        header.setMaximumSize(dimension);
        header.setMinimumSize(dimension);
        header.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JPanel north = new JPanel(new BorderLayout());
        JPanel center = new JPanel(new BorderLayout());


        center.setBackground(null);
        north.setBackground(null);
        north.add(new JLabel("Home",20,GUIConstants.black,Font.BOLD),BorderLayout.WEST);
        north.add(new JLabel("Whats New!",30,GUIConstants.blue,Font.BOLD),BorderLayout.SOUTH);


        center.add(new JLabel("- added GUI",18,GUIConstants.textFieldHint,Font.BOLD),BorderLayout.NORTH);
        center.add(new JLabel("- added messaging",18,GUIConstants.textFieldHint,Font.BOLD),BorderLayout.CENTER);
        center.add(new JLabel("- added friends and blocked",18,GUIConstants.textFieldHint,Font.BOLD),BorderLayout.SOUTH);



        header.add(north, BorderLayout.NORTH);
        header.add(center, BorderLayout.CENTER);


      //  JTextArea messageIn = new JTextArea("Speak your thoughts...",16,20);
        // header.add(new JScrollPane(messageIn),BorderLayout.CENTER);
        ///

        // Adding table of message threads to header panel here !!
//
//        Map<String, String> messages = controller.getMessages(currentUser);
//
//        // Create the table model and table
//        DefaultTableModel model = new DefaultTableModel(new Object[]{"User", "Message"}, 0);
//        JTable table = new JTable(model);
//
//        // Populate the table with map data
//        for (Map.Entry<String, String> entry : messages.entrySet()) {
//            model.addRow(new Object[]{entry.getKey(), entry.getValue()});
//        }
//
//
//
//
//
//        //add panel to the frame
//        header.add(new JScrollPane(table), BorderLayout.EAST);
//
//        notifs = new JPanel(new BorderLayout());
//        notifs.setBackground(GUIConstants.white);
//        Dimension screen = new Dimension(500, 159);
//        notifs.setPreferredSize(screen);
//        notifs.setMaximumSize(screen);
//        notifs.setMinimumSize(screen);
//        notifs.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        //TRY TO USE DATA FROM THE JTABLE TO DO THE MESSAGES HERE

//        for (Map.Entry<String, String> entry : messages.entrySet()) {
//            model.addRow(new Object[]{entry.getKey(), entry.getValue()});
//            for (int row = 0; row < model.getRowCount(); row++) {
//                // Get the values in the first column (Key) and second column (Value)
//                String key = (String) model.getValueAt(row, 0);  // First column (Key)
//                String value = (String) model.getValueAt(row, 1);  // Second column (Value)
//                notifs.add(new MessageUI2(key, value), BorderLayout.CENTER);
//            }
//        }
//
//
//
//


//        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//        south.setBackground(null);
//
//        JButton sendBtn = new JButton("SEND",35,16);
//        sendBtn.setPreferredSize(new Dimension(81,37));
//        south.add(sendBtn);

        //SEND BUTTON COMMENTED OUT

//        header.add(south, BorderLayout.SOUTH);

        /// add to panel
        panel.add(header);
        //added notifs
     //   panel.add(notifs, BorderLayout.SOUTH);
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
