import javax.swing.*;
import java.awt.*;
import javax.swing.text.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

class PopUpWindow extends JFrame {
    protected final int MIN_WIDTH = 290;
    protected final int MAX_WIDTH = 290;
    protected ArrayList<JTextField> inputFields = new ArrayList();
    protected volatile boolean breakPoint = false;

    class SubmitButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("Submited");
            Iterator itr = inputFields.iterator();
            while(itr.hasNext()) {
                JTextField field = (JTextField)itr.next();
                System.out.println(field.getText());
            }

            PopUpWindow.this.breakPoint = true;
            PopUpWindow.this.dispose();
        }
    }

    class CloseButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("Closed");
            PopUpWindow.this.breakPoint = true;
            PopUpWindow.this.dispose();
        }
    }

    public PopUpWindow(String[] fields) {
        // super("Lab");

        JPanel panel = new JPanel(); // the panel is not visible in output
        Box vericalBox = Box.createVerticalBox();

        for (int i = 0; i < fields.length; i++) {
            Box box = Box.createHorizontalBox();
            JTextField textField = new JTextField(20);
            inputFields.add(textField);
            box.add(new JLabel(fields[i]));
            box.add(textField);
            vericalBox.add(box);
        }

        JButton submitButton = new JButton("Submit");
        JButton closeButton = new JButton("Close");
        submitButton.addActionListener(new SubmitButtonEventListener());
        closeButton.addActionListener(new CloseButtonEventListener());
        Box box = Box.createHorizontalBox();
        box.add(submitButton);
        box.add(closeButton);
        vericalBox.add(box);

        panel.add(vericalBox);

        final int HEIGHT = fields.length * 30 + 50;
        //Adding Components to the frame.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(MIN_WIDTH, HEIGHT);
        this.getContentPane().add(BorderLayout.EAST, panel);
        this.setResizable(false);
        this.setVisible(true);
    }

    public void hold() {
        try {
            while(!breakPoint) TimeUnit.SECONDS.sleep(1);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class GetCityFromPopUp extends PopUpWindow {
    class SubmitButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("kek");
            Iterator itr = inputFields.iterator();
            while(itr.hasNext()) {
                JTextField field = (JTextField)itr.next();
                System.out.println(field.getText());
            }

            breakPoint = true;
            dispose();
        }
    }

    public GetCityFromPopUp() {
        super(new String[]{"name", "size", "x", "y"});
    }
}
