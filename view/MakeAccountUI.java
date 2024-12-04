package view;

import controller.Controller;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MakeAccountUI {
    Controller controller;
    JFrame frame;


    public MakeAccountUI(Controller controller,JFrame frame) {
        this.controller = controller;

        this.frame = frame;
        frame.getContentPane().removeAll();
        frame.setSize(850,700);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(GUIConstants.background);
        panel.setBorder(BorderFactory.createEmptyBorder(115, 0, 182, 0));

        JLabel title = new JLabel("Create Account", 40, GUIConstants.blue, Font.BOLD);
        title.setHorizontalAlignment(JLabel.CENTER);
        panel.add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(4, 1,10,15));
        center.setBackground(null);
        center.setBorder(BorderFactory.createEmptyBorder(22, 231, 10, 231));

        JTextField name = new JTextField(("Name"));
        center.add(name);
        JTextField username = new JTextField(("Username"));
        center.add(username);
        JTextField password = new JTextField(("Password"));
        center.add(password);

        JButton createAccount = new JButton("Create",45,20);
        center.add(createAccount);

        panel.add(center, BorderLayout.CENTER);

        JLabel goBackToLogin = new JLabel("Already have an account? Login.", 20, GUIConstants.black, Font.BOLD);
        goBackToLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        goBackToLogin.setHorizontalAlignment(JLabel.CENTER);
        panel.add(goBackToLogin, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
        frame.requestFocus();

        goBackToLogin.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                    new WelcomeUI(controller);
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

        createAccount.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (name.getText().isEmpty()){
                    JOptionPane.showMessageDialog(frame, "The name cannot be empty!", "Account Creation", JOptionPane.ERROR_MESSAGE);

                    return;
                } else if (username.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "The username cannot be empty!", "Account Creation", JOptionPane.ERROR_MESSAGE);

                    return;
                } else if (password.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "The password cannot be empty!", "Account Creation", JOptionPane.ERROR_MESSAGE);

                    return;
                }


                String response = controller.createNewAccount(name.getText(), username.getText(), password.getText());

                if (response.equals("true")){
                    JOptionPane.showMessageDialog(frame, "Account created successfully!", "Account Creation", JOptionPane.INFORMATION_MESSAGE);
                    new WelcomeUI(controller, frame);
                }else
                    JOptionPane.showMessageDialog(frame, response, "Account Creation", JOptionPane.INFORMATION_MESSAGE);
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
