package org.example;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class Checkers_Algorithm {
    int[][] board;
    static final int
            Empty = 0,
            White = 1,
            Black = 2,
            White_King = 3,
            Black_King = 4;

    Checkers_Algorithm() {
        board = new int[10][10];
        placement_Start();
    }

    void placement_Start() {
        for (int line = 0; line < 10; line++) {
            for (int column = 0; column < 10; column++) {
                if (line % 2 != column % 2) {
                    if (line <= 3)
                        board[line][column] = 2;
                    else if (line >= 6)
                        board[line][column] = 1;
                    else
                        board[line][column] = 0;
                } else {
                    board[line][column] = 0;
                }
            }
        }
    }

    int feature_On(int line, int column) {
        return board[line][column];
    }

    void makeMove(Checkers_Moves move) {
        makeMove(move.from_Line, move.from_Column, move.to_Line, move.to_Column);
    }

    void makeMove(int from_Line, int from_Column, int to_Line, int to_Column) {
        board[to_Line][to_Column] = board[from_Line][from_Column];
        board[from_Line][from_Column] = 0;
        if (abs(from_Line - to_Line) == 2 && abs(from_Column - to_Column) == 2) {
            int ate_Line = (from_Line + to_Line) / 2;
            int ate_Column = (from_Column + to_Column) / 2;
            board[ate_Line][ate_Column] = 0;
        }
        if (to_Line == 0 && board[to_Line][to_Column] == 1)
            board[to_Line][to_Column] = 3;
        if (to_Line == 9 && board[to_Line][to_Column] == 2)
            board[to_Line][to_Column] = 4;
    }

    private boolean can_Move(int user, int from_line, int from_column, int to_Line, int to_Column) {
        if (to_Line < 0 || to_Line >= 10 || to_Column < 0 || to_Column >= 10)
            return false;
        if (feature_On(to_Line, to_Column) != 0)
            return false;

        if (user == 1) {
            if (feature_On(from_line, from_column) == 1 && to_Line > from_line)
                return false;
        } else {
            if (feature_On(from_line, from_column) == 2 && to_Line < from_line)
                return false;
        }
        return true;
    }

    private boolean can_Eat(int user, int from_Line, int from_column, int enemy_Line, int enemy_Column, int to_Line, int to_Column) {
        if (to_Line < 0 || to_Line >= 10 || to_Column < 0 || to_Column >= 10)
            return false;
        if (feature_On(to_Line, to_Column) != 0)
            return false;

        if (user == 1) {
            if (feature_On(enemy_Line, enemy_Column) != 2 && feature_On(enemy_Line, enemy_Column) != 4)
                return false;
            if (feature_On(from_Line, from_column) == 1 && to_Line > from_Line)
                return false;
        } else {
            if (feature_On(enemy_Line, enemy_Column) != 1 && feature_On(enemy_Line, enemy_Column) != 3)
                return false;
            if (feature_On(from_Line, from_column) == 2 && to_Line < from_Line)
                return false;
        }
        return true;
    }

    Checkers_Moves[] possible_Moves(int user) {
        int user_King;
        ArrayList<Checkers_Moves> moves_List = new ArrayList<>();

        if (user == 1)
            user_King = 3;
        else
            user_King = 4;

        for (int line = 0; line < 10; line++) {
            for (int column = 0; column < 10; column++) {
                if (feature_On(line, column) == user || feature_On(line, column) == user_King) {
                    if (can_Eat(user, line, column, line + 1, column + 1, line + 2, column + 2))
                        moves_List.add(new Checkers_Moves(line, column, line + 2, column + 2));
                    if (can_Eat(user, line, column, line - 1, column + 1, line - 2, column + 2))
                        moves_List.add(new Checkers_Moves(line, column, line - 2, column + 2));
                    if (can_Eat(user, line, column, line + 1, column - 1, line + 2, column - 2))
                        moves_List.add(new Checkers_Moves(line, column, line + 2, column - 2));
                    if (can_Eat(user, line, column, line - 1, column - 1, line - 2, column - 2))
                        moves_List.add(new Checkers_Moves(line, column, line - 2, column - 2));
                }
            }
        }

        if (moves_List.isEmpty()) {
            for (int line = 0; line < 10; line++) {
                for (int column = 0; column < 10; column++) {
                    if (feature_On(line, column) == user || feature_On(line, column) == user_King) {
                        if (can_Move(user, line, column, line + 1, column + 1))
                            moves_List.add(new Checkers_Moves(line, column, line + 1, column + 1));
                        if (can_Move(user, line, column, line - 1, column + 1))
                            moves_List.add(new Checkers_Moves(line, column, line - 1, column + 1));
                        if (can_Move(user, line, column, line + 1, column - 1))
                            moves_List.add(new Checkers_Moves(line, column, line + 1, column - 1));
                        if (can_Move(user, line, column, line - 1, column - 1))
                            moves_List.add(new Checkers_Moves(line, column, line - 1, column - 1));
                    }
                }
            }
        }

        if (moves_List.isEmpty())
            return null;
        else {
            Checkers_Moves[] moves_Array = new Checkers_Moves[moves_List.size()];
            for (int i = 0; i < moves_List.size(); i++)
                moves_Array[i] = moves_List.get(i);
            return moves_Array;
        }
    }

    Checkers_Moves[] get_Possible_Eats(int user, int line, int column) {
        int user_King;
        ArrayList<Checkers_Moves> eating_List = new ArrayList<>();

        if (user == 1)
            user_King = 3;
        else
            user_King = 4;

        if (feature_On(line,column) == user || feature_On(line,column) == user_King) {
            if (can_Eat(user, line, column, line + 1, column + 1, line + 2, column + 2))
                eating_List.add(new Checkers_Moves(line, column, line + 2, column + 2));
            if (can_Eat(user, line, column, line - 1, column + 1, line - 2, column + 2))
                eating_List.add(new Checkers_Moves(line, column, line - 2, column + 2));
            if (can_Eat(user, line, column, line + 1, column - 1, line + 2, column - 2))
                eating_List.add(new Checkers_Moves(line, column, line + 2, column - 2));
            if (can_Eat(user, line, column, line - 1, column - 1, line - 2, column - 2))
                eating_List.add(new Checkers_Moves(line, column, line - 2, column - 2));
        }
        if (eating_List.isEmpty())
            return null;
        else {
            Checkers_Moves[] eating_Array = new Checkers_Moves[eating_List.size()];
            for (int i = 0; i < eating_List.size(); i++)
                eating_Array[i] = eating_List.get(i);
            return eating_Array;
        }
    }
}