import javax.swing.*;
import java.awt.*;
import javax.swing.text.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

class PopUpWindow extends JFrame {
    protected final int WIDTH = 340;
    protected ArrayList<JTextField> inputFields = new ArrayList<>();
    protected ArrayList<String> answers = new ArrayList<>();
    protected volatile boolean breakPoint = false;

    public City getCity() {
        String[] answers = (String[]) getAnswersArr();

        Integer size = 0;
        try {
            size = Integer.parseInt(answers[1]);
        } catch (java.lang.NumberFormatException ignored) {
        }

        Integer x = 0;
        try {
            x = Integer.parseInt(answers[2]);
        } catch (java.lang.NumberFormatException ignored) {
        }

        Integer y = 0;
        try {
            y = Integer.parseInt(answers[2]);
        } catch (java.lang.NumberFormatException ignored) {
        }

        City city = new City(answers[0], size, x, y);
        return city;
    }

    public void handle() {

    }

    class SubmitButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Iterator itr = inputFields.iterator();
            while(itr.hasNext()) {
                JTextField field = (JTextField)itr.next();
                
                // System.out.println(field.getText());
                answers.add(field.getText());
            }

            PopUpWindow.this.breakPoint = true;
            PopUpWindow.this.dispose();
            handle();
        }
    }

    class CloseButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
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
        this.setSize(WIDTH, HEIGHT);
        this.getContentPane().add(BorderLayout.EAST, panel);
        this.setResizable(false);
        this.setVisible(true);
    }

    public String[] getAnswersArr() {
        return (String[])answers.toArray(new String[answers.size()]);
    }
}