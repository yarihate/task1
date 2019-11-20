package com.gelasimova;
import java.util.*;

class Player {
    ArrayList<Piece> listOfPieces = new ArrayList<>();

    void takeBlackPieces(Board b) {
        for (int i = 0; i < b.board.size(); i++) {
            if (i <= 15) {
                //Piece also has a Player variable
                b.board.get(i).getPiece().setPlayer(this);
                listOfPieces.add(b.board.get(i).getPiece());
            }
        }
    }

    void takeWhitePieces(Board b) {
        for (int i = 0; i < b.board.size(); i++) {
            if (i > 47) {
                //Piece also has a Player variable
                b.board.get(i).getPiece().setPlayer(this);
                listOfPieces.add(b.board.get(i).getPiece());
            }
        }
    }

    private Piece choosePiece() {
        Collections.shuffle(listOfPieces);
        return listOfPieces.get(0);
    }

    boolean hasAnyAvailableMoves(Board board) {
        for (Piece piece : listOfPieces) {
            if (!piece.checkAvailableMoves(board).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    void makeMove(Board board) {
        Piece activePiece;
        ArrayList<Space> availableMoves;
        //makeMove is called after hasAnyAvailableMoves returns true in ChessMatch.playChess method
        do {
            activePiece = choosePiece();
            //checkAvailableMoves abstract method of Piece class
            availableMoves = activePiece.checkAvailableMoves(board);
        }
        while (availableMoves.isEmpty());
        System.out.println(activePiece + " on " + activePiece.getCoordinate() + " was chosen.");
        Collections.shuffle(availableMoves);
        //for(checkAvailableMoves)if (canBeatEnemyPiece) make move
        activePiece.move(availableMoves.get(0), board);
        board.showTheBoard();
    }
}