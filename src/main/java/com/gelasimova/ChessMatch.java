package main.java.com.gelasimova;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChessMatch {
    public static void main(String[] args)  {
        Player player1 = new Player();
        Player player2 = new Player();
        Board board = new Board();

        player1.takeWhitePieces(board);
        player2.takeBlackPieces(board);
        playChess(player1, player2, board);

    }

    private static void playChess(Player player1, Player player2, Board board) {
        while (true) {
            if (player1.getListOfPieces().isEmpty()) {
                System.out.println("Black wins. Game over");
                break;
            } else if (player1.hasAnyAvailableMoves(board)) {
                player1.makeMove(board);
            } else {
                System.out.println("White has no available moves. Black wins. Game over");
                break;
            }
            if (player2.getListOfPieces().isEmpty()) {
                System.out.println("White wins. Game over");
                break;
            } else if (player2.hasAnyAvailableMoves(board)) {
                player2.makeMove(board);
            } else {
                System.out.println("Black has no available moves. White wins. Game over");
                break;
            }
        }
        System.out.println(player1.getListOfPieces());
        System.out.println(player2.getListOfPieces());
    }
}
