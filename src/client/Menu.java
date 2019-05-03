import javax.swing.*;
import java.awt.*;
import javax.swing.text.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

class Menu extends JMenuBar {
    public Menu() {
        JMenu m1 = new JMenu("commands");
        JMenu m2 = new JMenu("help");
        JMenu m3 = new JMenu("authorization");
        add(m1);
        add(m2);
        add(m3);

        JMenuItem m11 = new JMenuItem("show");
        JMenuItem m12 = new JMenuItem("add");
        JMenuItem m13 = new JMenuItem("remove_first");
        JMenuItem m14 = new JMenuItem("remove");
        JMenuItem m15 = new JMenuItem("add_if_max");
        m11.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("show");
                }
            }
        );
        m12.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("add");
                    GetCityFromPopUp gcfpu = new GetCityFromPopUp();
                    gcfpu.hold();
                }
            }
        );
        m13.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("remove_first");
                }
            }
        );
        m14.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("remove");
                }
            }
        );
        m15.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("add_if_max");
                }
            }
        );
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
        m21.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("man show");
                }
            }
        );
        m22.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("man add");
                }
            }
        );
        m23.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("man remove_first");
                }
            }
        );
        m24.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("man remove");
                }
            }
        );
        m25.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("man add_if_max");
                }
            }
        );
        m2.add(m21);
        m2.add(m22);
        m2.add(m23);
        m2.add(m24);
        m2.add(m25);

        JMenuItem m31 = new JMenuItem("log in");
        JMenuItem m32 = new JMenuItem("sign in");
        m31.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("log in");
                }
            }
        );
        m32.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("sign in");
                }
            }
        );
        m3.add(m31);
        m3.add(m32);
    }
}
