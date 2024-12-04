package view;

@SuppressWarnings("serial")
public class JFrame extends javax.swing.JFrame {

    public JFrame() {
        super("Messaging App");
        getContentPane().setBackground(GUIConstants.white);
        setSize(900, 625);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
