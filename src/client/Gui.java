import javax.swing.*;
import java.awt.*;
import javax.swing.text.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

class Gui extends JFrame {
    private final int MIN_WIDTH = 400;
    private final int MIN_HEIGHT = 400;
    private final int MAX_WIDTH = 1280;
    private final int MAX_HEIGHT = 720;
    private Menu menu;
    private TextArea ta;

    public static ResourceBundle resourceBundle;

    public Gui() {
        super("Lab");
        resourceBundle=ResourceBundle.getBundle
                ("resource_ru",new Locale("ru","RU"));

        menu = new Menu();
        ta = new TextArea();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        this.setMaximumSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
        this.setSize(MIN_WIDTH, MIN_HEIGHT);
        this.getContentPane().add(BorderLayout.NORTH, menu);
        this.getContentPane().add(BorderLayout.CENTER, new JScrollPane(ta.getJTextArea()));
        this.setResizable(true);
        this.setVisible(true);
    }

    public TextArea getTextArea() {
        return ta;
    }

    public void close() {
        this.dispose();
    }

    // PopUpWindow puw = new PopUpWindow(new String[]{"City", "Size", "x", "y"});
}
