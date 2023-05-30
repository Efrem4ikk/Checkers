package org.example;

import static java.lang.Math.abs;

public class Checkers_Moves {
    int from_Line, from_Column, to_Line, to_Column;

    Checkers_Moves(int from_Line, int from_Column, int to_Line, int to_Column) {
        this.from_Line = from_Line;
        this.from_Column = from_Column;
        this.to_Line = to_Line;
        this.to_Column = to_Column;
    }

    boolean is_Eating() {
        return (abs(from_Line - to_Line) == 2 && abs(from_Column - to_Column) == 2);
    }
}