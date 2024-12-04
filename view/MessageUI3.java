package view;

import controller.Controller;
import model.MessageThread;
import model.MessageViewerUser;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import model.Message;

public class MessageUI3 extends JPanel {

    // private LocalDateTime dateTime;


    public MessageUI3(String sender, String receiver, String date, String message) {

        // dateTime = LocalDateTime.now();


        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(GUIConstants.white);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 25));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(null);


        //need to load the username here
        JLabel author = new JLabel(sender, 20,GUIConstants.blue,Font.BOLD);
        header.add(author, BorderLayout.WEST);
        add(header);

        //content of the message

        JPanel center = new JPanel(new FlowLayout(FlowLayout.LEADING));
        center.setBackground(null);
        JTextArea content = new JTextArea(message,18,GUIConstants.black,Font.PLAIN);
        center.add(content);
        add(center);


        //local date and time here

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(null);
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm | MM.dd.yy");
//        String time = message.getDate().format(String.valueOf(formatter));

        bottom.add(new JLabel(date,15,GUIConstants.textFieldHint,Font.BOLD), BorderLayout.EAST);
        add(bottom);

        int height = (int) (90 + content.getPreferredSize().getHeight());

        Dimension dimension = new Dimension(500, height);
        setPreferredSize(dimension);
        setMaximumSize(dimension);
        setMinimumSize(dimension);




    }
}
