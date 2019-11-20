package main.java.com.gelasimova;
import java.util.*;

class Player {
    private ArrayList<Piece> listOfPieces = new ArrayList<>();

    public ArrayList<Piece> getListOfPieces(){
        return listOfPieces;
    }

    public void takeBlackPieces(Board b) {
        for (int i = 0; i < b.getBoard().size(); i++) {
            if (i <= 15) {
                //Piece also has a Player variable
                b.getBoard().get(i).getPiece().setPlayer(this);
                listOfPieces.add(b.getBoard().get(i).getPiece());
            }
        }
    }

    public void takeWhitePieces(Board b) {
        for (int i = 0; i < b.getBoard().size(); i++) {
            if (i > 47) {
                //Piece also has a Player variable
                b.getBoard().get(i).getPiece().setPlayer(this);
                listOfPieces.add(b.getBoard().get(i).getPiece());
            }
        }
    }

    private Piece choosePiece() {
        Collections.shuffle(listOfPieces);
        return listOfPieces.get(0);
    }

    public boolean hasAnyAvailableMoves(Board board) {
        for (Piece piece : listOfPieces) {
            if (!piece.getAvailableMoves(board).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public void makeMove(Board board) {
        Piece activePiece;
        ArrayList<Space> availableMoves;
        //makeMove is called after hasAnyAvailableMoves returns true in ChessMatch.playChess method
        do {
            activePiece = choosePiece();
            //checkAvailableMoves abstract method of Piece class
            availableMoves = activePiece.getAvailableMoves(board);
        }
        while (availableMoves.isEmpty());
        System.out.println(activePiece + " on " + activePiece.getCoordinates() + " was chosen.");
        Collections.shuffle(availableMoves);
        //can add - for(checkAvailableMoves)if (canBeatEnemyPiece) make move
        activePiece.move(availableMoves.get(0), board);
        board.showTheBoard();
    }
}
