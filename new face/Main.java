import javax.swing.*;
import java.awt.*;

class Main {
    public static void main(String args[]) {
        JFrame frame = new JFrame("Chat Frame");

        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("commands");
        JMenu m2 = new JMenu("help");
        JMenu m3 = new JMenu("authorization");
        mb.add(m1);
        mb.add(m2);
        mb.add(m3);

        JMenuItem m11 = new JMenuItem("show");
        JMenuItem m12 = new JMenuItem("add");
        JMenuItem m13 = new JMenuItem("remove_first");
        JMenuItem m14 = new JMenuItem("remove");
        JMenuItem m15 = new JMenuItem("add_if_max");
        m1.add(m11);
        m1.add(m12);
        m1.add(m13);
        m1.add(m14);
        m1.add(m15);
        JMenuItem m21 = new JMenuItem("show");
        JMenuItem m22 = new JMenuItem("add");
        JMenuItem m23 = new JMenuItem("remove_first");
        JMenuItem m24 = new JMenuItem("remove");
        JMenuItem m25 = new JMenuItem("add_if_max");
        m2.add(m21);
        m2.add(m22);
        m2.add(m23);
        m2.add(m24);
        m2.add(m25);

        JMenuItem m31 = new JMenuItem("log in");
        JMenuItem m32 = new JMenuItem("sign in");
        m3.add(m31);
        m3.add(m32);

        //Creating the panel at bottom and adding components
        JPanel panel = new JPanel(); // the panel is not visible in output
        JLabel label = new JLabel("Enter Text");
        JTextField tf = new JTextField(10); // accepts upto 10 characters
        JButton send = new JButton("Send");
        JButton reset = new JButton("Reset");
        panel.add(label); // Components Added using Flow Layout
        panel.add(label); // Components Added using Flow Layout
        panel.add(tf);
        panel.add(send);
        panel.add(reset);

        // Text Area at the Center
        JTextArea ta = new JTextArea();

        //Adding Components to the frame.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, ta);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
