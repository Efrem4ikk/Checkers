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
        setPreferredSize(new Dimension(800, 650));
        setBackground(new Color(52, 134, 134));
        Checkers_Field board = new Checkers_Field();
        add(board);
        add(text_Info);
        add(restart_info);
        add(start_Game_Button);
        add(surrender_Button);
        add(restart_Button);
        board.setBounds(150, 75, 500, 500);
        text_Info.setBounds(125, 35, 550, 30);
        restart_info.setBounds(125, 15, 550, 25);
        start_Game_Button.setBounds(200, 587, 150, 30);
        surrender_Button.setBounds(450, 587, 150, 30);
        restart_Button.setBounds(662, 62, 120, 30);
    }
}