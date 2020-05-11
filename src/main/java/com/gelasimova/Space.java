package com.gelasimova;

public class Space {
    private int x;
    private char y;
    private Piece piece;

    public Space(int x, char y) {
        this.x = x;
        this.y = y;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    public int getX() {
        return x;
    }

    public char getY() {
        return y;
    }

    public String getSpaceCoordinates() {
        return x + "" + y;
    }

    public boolean isSpaceOnTheBoard() {
        int x = getX();
        char y = getY();
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
        return (this.getX() == s.getX() && this.getY() == s.getY());
    }
}
