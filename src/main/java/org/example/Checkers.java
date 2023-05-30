package org.example;

import java.awt.*;
import javax.swing.*;

public class Checkers extends JPanel {
    public static JLabel text_Info, restart_info;
    public static JButton start_Game_Button, surrender_Button, restart_Button;

    public static void main(String[] args) {
        JFrame window = new JFrame("10 x 10 Checkers");
        Checkers area = new Checkers();
        window.setContentPane(area);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.pack();
        window.setIconImage(printIcon());
    }

    private static Image printIcon() {
        ImageIcon picture = new ImageIcon("src/image.png");
        return picture.getImage();
    }

    public Checkers() {
        setLayout(null);
        setPreferredSize(new Dimension(1600, 1300));
        setBackground(new Color(132, 115, 236));
        Checkers_Field board = new Checkers_Field();
        add(board);
        add(text_Info);
        add(restart_info);
        add(start_Game_Button);
        add(surrender_Button);
        add(restart_Button);
        board.setBounds(300, 150, 1000, 1000);
        text_Info.setBounds(250, 70, 1100, 60);
        restart_info.setBounds(250, 30, 1100, 50);
        start_Game_Button.setBounds(400, 1175, 300, 60);
        surrender_Button.setBounds(900, 1175, 300, 60);
        restart_Button.setBounds(1325, 65, 240, 60);
    }
}