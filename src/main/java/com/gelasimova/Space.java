package com.gelasimova;

public class Space {
    private int x;
    private char y;
    private Piece piece;

    Space(int x, char y) {
        this.x = x;
        this.y = y;
    }

    void setPiece(Piece piece) {
        this.piece = piece;
    }

    Piece getPiece() {
        return piece;
    }

    int spaceGetX() {
        return x;
    }

    char spaceGetY() {
        return y;
    }

    String spaceCoordinate() {
        return x + "" + y;
    }

    boolean isSpaceOnTheBoard() {
        int x = spaceGetX();
        char y = spaceGetY();
        return (x >= 1 && x <= 8) && (y >= 'a' && y <= 'h');
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        Space s = (Space) o;
        return (this.spaceGetX() == s.spaceGetX() && this.spaceGetY() == s.spaceGetY());
    }
}
