package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Checkers_Field extends JPanel implements ActionListener, MouseListener {

    Checkers_Algorithm field = new Checkers_Algorithm();
    boolean game_In_Progress = false;
    int whose_Turn;
    int selected_Line, selected_Column;
    Checkers_Moves[] possible_Moves;

    Checkers_Field() {
        addMouseListener(this);
        Checkers.text_Info = new JLabel("International Checkers", JLabel.CENTER);
        Checkers.text_Info.setFont(new Font("DialogInput", Font.BOLD, 20));
        Checkers.text_Info.setForeground(Color.black);
        Checkers.restart_info = new JLabel("", JLabel.CENTER);
        Checkers.restart_info.setFont(new Font("DialogInput", Font.BOLD, 15));
        Checkers.restart_info.setForeground(Color.black);
        Checkers.surrender_Button = new JButton("Surrender");
        Checkers.surrender_Button.addActionListener(this);
        Checkers.surrender_Button.setFont(new Font("DialogInput", Font.BOLD, 20));
        Checkers.surrender_Button.setBackground(new Color(118, 67, 178));
        Checkers.start_Game_Button = new JButton("Start GAME");
        Checkers.start_Game_Button.setFont(new Font("DialogInput", Font.BOLD, 15));
        Checkers.start_Game_Button.addActionListener(this);
        Checkers.start_Game_Button.setBackground(new Color(118, 67, 178));
        Checkers.restart_Button = new JButton("Restart");
        Checkers.restart_Button.setFont(new Font("DialogInput", Font.BOLD, 15));
        Checkers.restart_Button.setBackground(new Color(118, 67, 178));
        Checkers.restart_Button.addActionListener(this);
        Checkers.surrender_Button.setEnabled(false);
        Checkers.restart_Button.setEnabled(false);
    }

    public void actionPerformed(ActionEvent evt) {
        Object src = evt.getSource();
        if (src == Checkers.start_Game_Button)
            start_New_Game();
        if (src == Checkers.surrender_Button)
            surrender();
        if (src == Checkers.restart_Button)
            restart();
    }

    private void start_New_Game() {
        selected_Line = -1;
        selected_Column = -1;
        game_In_Progress = true;
        field.placement_Start();
        whose_Turn = Checkers_Algorithm.White;
        possible_Moves = field.possible_Moves(Checkers_Algorithm.White);
        Checkers.text_Info.setText("White starts the game");
        Checkers.start_Game_Button.setEnabled(false);
        Checkers.surrender_Button.setEnabled(true);
        Checkers.restart_Button.setEnabled(true);
        repaint();
    }

    private void surrender() {
        if (whose_Turn == Checkers_Algorithm.White)
            game_Over("White surrendered. Black win");
        else
            game_Over("Black surrendered. White win");
    }

    private void restart() {
        start_New_Game();
        Checkers.restart_info.setText("Game has been restarted");
    }

    private void game_Over(String info) {
        Checkers.restart_info.setText("");
        Checkers.text_Info.setText(info);
        Checkers.start_Game_Button.setEnabled(true);
        Checkers.surrender_Button.setEnabled(false);
        Checkers.restart_Button.setEnabled(false);
        game_In_Progress = false;
    }

    void click_Cell(int line, int column) {
        Checkers.restart_info.setText("");

        for (Checkers_Moves possible_move : possible_Moves)
            if (possible_move.from_Line == line && possible_move.from_Column == column) {
                selected_Line = line;
                selected_Column = column;
                if (whose_Turn == Checkers_Algorithm.White)
                    Checkers.text_Info.setText("White's turn");
                else
                    Checkers.text_Info.setText("Black's turn");
                repaint();
                return;
            }

        if (selected_Line < 0 || selected_Line > 10) {
            Checkers.text_Info.setText("Choose the feature you want to move");
            return;
        }

        for (Checkers_Moves possible_move : possible_Moves)
            if (possible_move.from_Line == selected_Line && possible_move.from_Column == selected_Column
                    && possible_move.to_Line == line && possible_move.to_Column == column) {
                make_Moves(possible_move);
                return;
            }
        Checkers.text_Info.setText("Click on the cell you want to move to");
    }

    void make_Moves(Checkers_Moves move) {
        field.makeMove(move);

        if (move.is_Eating()) {
            possible_Moves = field.get_Possible_Eats(whose_Turn, move.to_Line, move.to_Column);
            if (possible_Moves != null) {
                if (whose_Turn == Checkers_Algorithm.White)
                    Checkers.text_Info.setText("White's move continues, you must to eat.");
                else
                    Checkers.text_Info.setText("Black's move continues, you must to eat.");
                selected_Line = move.to_Line;
                selected_Column = move.to_Column;
                repaint();
                return;
            }
        }

        if (whose_Turn == Checkers_Algorithm.White) {
            whose_Turn = Checkers_Algorithm.Black;
            possible_Moves = field.possible_Moves(whose_Turn);
            if (possible_Moves == null)
                game_Over("Blacks can't move. White win");
            else if (possible_Moves[0].is_Eating())
                Checkers.text_Info.setText("Black's turn. You must eat");
            else
                Checkers.text_Info.setText("Black's turn");
        } else {
            whose_Turn = Checkers_Algorithm.White;
            possible_Moves = field.possible_Moves(whose_Turn);
            if (possible_Moves == null)
                game_Over("White can't move. Black win");
            else if (possible_Moves[0].is_Eating())
                Checkers.text_Info.setText("White's turn. You must eat");
            else
                Checkers.text_Info.setText("White's turn");
        }
        selected_Line = -1;
        selected_Column = -1;
        repaint();
    }

    public void paintComponent(Graphics g) {
        for (int line = 0; line < 10; line++) {
            for (int column = 0; column < 10; column++) {
                if (line % 2 != column % 2)
                    g.setColor(new Color(93, 91, 91));
                else
                    g.setColor(new Color(199, 197, 197));
                g.fillRect(column * 50, line * 50, 50, 50);
                switch (field.feature_On(line, column)) {
                    case Checkers_Algorithm.White -> {
                        g.setColor(Color.lightGray);
                        g.fillOval(3 + column * 50, 3 + line * 50, 45, 45);
                        g.setColor(Color.white);
                        g.fillOval(3 + column * 50, 3 + line * 50, 41, 41);

                    }
                    case Checkers_Algorithm.Black -> {
                        g.setColor(new Color(45, 45, 45));
                        g.fillOval(3 + column * 50, 3 + line * 50, 45, 45);
                        g.setColor(Color.black);
                        g.fillOval(7 + column * 50, 7 + line * 50, 42, 42);

                    }
                    case Checkers_Algorithm.White_King -> {
                        g.setColor(Color.lightGray);
                        g.fillOval(3 + column * 50, 3 + line * 50, 45, 45);
                        g.setColor(Color.white);
                        g.fillOval(3 + column * 50, 3 + line * 50, 41, 41);
                        g.setColor(Color.black);
                        g.fillOval(20 + column * 50, 20 + line * 50, 10, 10);
                    }
                    case Checkers_Algorithm.Black_King -> {
                        g.setColor(new Color(45, 45, 45));
                        g.fillOval(3 + column * 50, 3 + line * 50, 45, 45);
                        g.setColor(Color.black);
                        g.fillOval(7 + column * 50, 7 + line * 50, 42, 42);
                        g.setColor(Color.white);
                        g.fillOval(20 + column * 50, 20 + line * 50, 10, 10);
                    }
                }
            }
        }

        if (game_In_Progress) {
            g.setColor(new Color(134, 236, 46));
            for (Checkers_Moves possible_move : possible_Moves) {
                g.drawRect(possible_move.from_Column * 50, possible_move.from_Line * 50, 50, 50);
                g.drawRect(1 + possible_move.from_Column * 50, 1 + possible_move.from_Line * 50, 49, 49);
            }

            if (selected_Line >= 0 && selected_Line < 10) {
                g.setColor(new Color(243, 207, 16));
                g.drawRect(selected_Column * 50, selected_Line * 50, 50, 50);
                g.drawRect(1 + selected_Column * 50, 1 + selected_Line * 50, 49, 49);
                g.setColor(new Color(211, 18, 46));
                for (Checkers_Moves possible_move : possible_Moves) {
                    if (possible_move.from_Column == selected_Column && possible_move.from_Line == selected_Line) {
                        g.drawRect(possible_move.to_Column * 50, possible_move.to_Line * 50, 50, 50);
                        g.drawRect(1 + possible_move.to_Column * 50, 1 + possible_move.to_Line * 50, 49, 49);
                    }
                }
            }
        }
    }

    public void mousePressed(MouseEvent evt) {
        if (!game_In_Progress)
            Checkers.text_Info.setText("Click Start button to start a new game");
        else {
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            int line = (evt.getY()) / 50;
            int column = (evt.getX()) / 50;
            if (column >= 0 && column < 10 && line >= 0 && line < 10)
                click_Cell(line, column);
        }
    }

    public void mouseReleased(MouseEvent evt) {
    }

    public void mouseClicked(MouseEvent evt) {
    }

    public void mouseEntered(MouseEvent evt) {
    }

    public void mouseExited(MouseEvent evt) {
    }
}
