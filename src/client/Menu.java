import javax.swing.*;
import java.awt.*;
import javax.swing.text.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

class Menu extends JMenuBar {
    private static final long serialVersionUID = 1L;

    public Menu() {
        JMenu m1 = new JMenu("commands");
        JMenu m2 = new JMenu("help");
        JMenu m3 = new JMenu("authorization");
        JMenu m4 = new JMenu("laguages");
        add(m1);
        add(m2);
        add(m3);
        add(m4);

        JMenuItem m11 = new JMenuItem("show");
        JMenuItem m12 = new JMenuItem("add");
        JMenuItem m13 = new JMenuItem("remove_first");
        JMenuItem m14 = new JMenuItem("remove");
        JMenuItem m15 = new JMenuItem("add_if_max");
        m11.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // System.out.println("show");
                    ClientInteraction.send("show");
                }
            }
        );
        m12.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new PopUpWindow(new String[] { "name", "size", "x", "y" }) {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public void handle() {
                            City city = getCity();
                            // System.out.println(city.toString());
                            ClientInteraction.send("add " + city.toJson());
                        }
                    };
                }
            }
        );
        m13.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // System.out.println("remove_first");
                    ClientInteraction.send("remove_first");
                }
            }
        );
        m14.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("remove");
                    
                    new PopUpWindow(new String[] { "name", "size", "x", "y" }) {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public void handle() {
                            City city = getCity();
                            // System.out.println(city.toString());
                            ClientInteraction.send("remove " + city.toJson());
                        }
                    };
                }
            }
        );
        m15.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("add_if_max");

                    new PopUpWindow(new String[] { "name", "size", "x", "y" }) {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public void handle() {
                            City city = getCity();
                            // System.out.println(city.toString());
                            ClientInteraction.send("add_if_max " + city.toJson());
                        }
                    };
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
                    // System.out.println("man show");
                    ClientInteraction.send("man show");
                }
            }
        );
        m22.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // System.out.println("man add");
                    ClientInteraction.send("man add");
                }
            }
        );
        m23.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // System.out.println("man remove_first");
                    ClientInteraction.send("man remove_first");
                }
            }
        );
        m24.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // System.out.println("man remove");
                    ClientInteraction.send("man remove");
                }
            }
        );
        m25.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // System.out.println("man add_if_max");
                    ClientInteraction.send("man add_if_max");
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
                    // System.out.println("log in");

                    new PopUpWindow(new String[] { "login", "password" }) {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public void handle() {
                            String[] answers = (String[]) getAnswersArr();
                            
                            // System.out.println("login: " + answers[0]);
                            // System.out.println("password: " + answers[1]);
                            ClientInteraction.setLoginAndPassword(answers[0], answers[1]);
                        }
                    };
                }
            }
        );
        m32.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // System.out.println("sign in");

                    new PopUpWindow(new String[] { "login" }) {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public void handle() {
                            String[] answers = (String[]) getAnswersArr();

                            // System.out.println("login: " + answers[0]);
                            ClientInteraction.send("reg " + answers[0]);
                        }
                    };
                }
            }
        );
        m3.add(m31);
        m3.add(m32);

        JMenuItem m41 = new JMenuItem("laguage 1");
        JMenuItem m42 = new JMenuItem("laguage 2");
        m41.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("l1");
                }
            }
        );
        m42.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("l2");
                }
            }
        );
        m4.add(m41);
        m4.add(m42);
    }
}
