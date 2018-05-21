import javax.swing.*;
import java.awt.*;

public class MyWindow extends JFrame {

    private JTextField textField;
    private JTextArea textArea;
    private JScrollPane scrollPane;

    public MyWindow(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat");
        setBounds(300,100,400,385);
        JButton btn1 = new JButton("Send");
        textField = new JTextField(29);
        textArea = new JTextArea(20,33);
        scrollPane = new JScrollPane(textArea);

        textArea.setLineWrap(true);
        textArea.setEditable(false);

        add(scrollPane, BorderLayout.NORTH);
//        add(textField);
//        add(btn1);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(textField, BorderLayout.WEST);
        panel.add(btn1, BorderLayout.EAST);
        add(panel, BorderLayout.SOUTH);

        btn1.addActionListener(e -> sendMsg());
        textField.addActionListener(e -> sendMsg());


        setVisible(true);
        textField.grabFocus();
    }

    public void sendMsg(){
        textArea.append(textField.getText()+"\n");
        textField.setText("");
        textField.grabFocus();
    }
}
