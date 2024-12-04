package view;
import model.*;
import controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class WelcomeUI {
    Controller controller;
    /// //define UI components
    JFrame frame;
    JPanel panel;
    JPanel center;
    JTextField username;
    JTextField password;
    JButton loginPrompt;
    JButton createAcc;
    JButton exitApp;

    public WelcomeUI(Controller controller) {
        this.controller = controller;
        this.frame = new JFrame();
        renderUI();
    }

    public WelcomeUI(Controller controller, JFrame frame) {
        this.controller = controller;
        this.frame = frame;
        frame.getContentPane().removeAll();
        renderUI();
    }
    private void renderUI() {

        renderMainPanel();
        renderCentralPanel();
        frame.getContentPane().add(panel);
        frame.pack();
        frame.revalidate();
        frame.repaint();
        Dimension dim = new Dimension(890, 600);
        frame.setSize(dim);
        frame.setVisible(true);
        frame.requestFocus();
    }

    private void renderMainPanel(){
        panel = new JPanel(new BorderLayout());
        panel.setBackground(GUIConstants.background);
        panel.setBorder(BorderFactory.createEmptyBorder(53, 84, 76, 84));
        panel.add(new JLabel("Purdue Messenger", 40, GUIConstants.blue, Font.BOLD), BorderLayout.NORTH);
    }

    private void renderCentralPanel(){
        center = new JPanel(new GridLayout(6, 1, 10, 10));
        center.setBackground(null);
        center.setBorder(BorderFactory.createEmptyBorder(22, 231, 17, 231));
        username = new JTextField("username");
        center.add(username);
        password = new JTextField("password");
        center.add(password);
        /// ////////////action btns/////////////////////////
        loginPrompt = new JButton("Login", 45,20 );
        center.add(loginPrompt);
        ////add mouse click action implementation
        loginAction();

        /// //this should redirect the user to account creation UI
        createAcc = new JButton("Create Account", 45,20 );
        center.add(createAcc);
        createAccAction();


        exitApp = new JButton("Exit", 45,20 );
        center.add(exitApp);
        exitAction();
        panel.add(center, BorderLayout.CENTER);
    }

    private void exitAction(){
        exitApp.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
//                frame.dispose();
                controller.exitApplication();
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
    }

    //GOES TO THE CREATE ACCOUNT UI

    private void createAccAction(){
        createAcc.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
//            frame.getContentPane().removeAll();
//            frame.dispose();
            new MakeAccountUI(controller,frame);

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

    }


    /*
    Mouse click will initiate server request to login
     */
    private void loginAction(){
        loginPrompt.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (username.getText().isEmpty()){
                    JOptionPane.showMessageDialog(frame, "The name cannot be empty!", "Account Login", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (password.getText().isEmpty()){
                    JOptionPane.showMessageDialog(frame, "The password cannot be empty!", "Account Login", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String loggedUserName = controller.login(username.getText(), password.getText());
             /// direct the user to new welcome screen if successful
                /// else redirect to login screen to try again
                ///
                if(loggedUserName.equals("null")){
                    //new Alert("Try again! Error in login!",frame);
                }
                else{
                    frame.getContentPane().removeAll();
                    new HomeUI(controller, loggedUserName, frame);
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

    }


}
