package com.gelasimova;

import java.util.*;

class Board {
    private ArrayList<Space> board = new ArrayList<>();

    public ArrayList<Space> getBoard() {
        return board;
    }

    public Board() {
        for (int x = 1; x <= 8; x++) {
            for (char y = 'a'; y <= 'h'; y++) {
                Space space = new Space(x, y);
                Piece piece;
                //set black pieces
                if (x == 1 && (y == 'a' || y == 'h')) {
                    piece = new Rook(Colour.BLACK, space);
                } else if (x == 1 && (y == 'b' || y == 'g')) {
                    piece = new Knight(Colour.BLACK, space);
                } else if (x == 1 && (y == 'c' || y == 'f')) {
                    piece = new Bishop(Colour.BLACK, space);
                } else if (x == 1 && y == 'd') {
                    piece = new Queen(Colour.BLACK, space);
                } else if (x == 1 && y == 'e') {
                    piece = new King(Colour.BLACK, space);
                } else if (x == 2) {
                    piece = new Pawn(Colour.BLACK, space);
                }
                //set white pieces
                else if (x == 8 && (y == 'a' || y == 'h')) {
                    piece = new Rook(Colour.WHITE, space);
                } else if (x == 8 && (y == 'b' || y == 'g')) {
                    piece = new Knight(Colour.WHITE, space);
                } else if (x == 8 && (y == 'c' || y == 'f')) {
                    piece = new Bishop(Colour.WHITE, space);
                } else if (x == 8 && y == 'd') {
                    piece = new King(Colour.WHITE, space);
                } else if (x == 8 && y == 'e') {
                    piece = new Queen(Colour.WHITE, space);
                } else if (x == 7) {
                    piece = new Pawn(Colour.WHITE, space);
                } else piece = null;
                space.setPiece(piece);
                board.add(space);
            }
        }
    }

    public void showTheBoard() {
        for (int i = 0; i < board.size(); i++) {
            if (i == 8 || i == 16 || i == 24 || i == 32 || i == 40 || i == 48 || i == 56) {
                System.out.println();
                System.out.println("----------------------------------------------------------------------------------------------------------------");
            }
            if (board.get(i).getPiece() == null) {
                System.out.print("            ");
            } else {
                System.out.printf("%12s", board.get(i).getPiece());
            }
            System.out.print(" |");
        }
        System.out.println();
        try {
            Thread.sleep(3000);
            switch (getOS()) {
                case "win":
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                    break;
                case "mac":
                case "lin":
                    new ProcessBuilder("/bin/bash", "-c", "clear").inheritIO().start().waitFor();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getOS() {
        String OS = System.getProperty("os.name").toLowerCase();
        if (OS.contains("windows")) {
            return "win";
        } else if (OS.contains("linux")) {
            return "lin";
        } else if (OS.contains("mac")) {
            return "mac";
        } else return null;
    }
}