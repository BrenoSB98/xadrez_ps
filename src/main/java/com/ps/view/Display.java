package com.ps.view;

import com.ps.board.Board;
import com.ps.board.Piece;
import com.ps.util.ChessMatch;
import com.ps.util.ChessPosition;
import com.ps.util.Color;

import java.util.HashSet;

public class Display {
    public static void printMatch(ChessMatch match){
        printBoard(match.getUtilBoard());
        System.out.println();

        printCapturedPieces(match);
        System.out.println();

        System.out.println("Turno: " + match.getTurn());

        if (!match.isTerminated()){
            System.out.println("Aguardando Jogada: " + match.getCurrentPlayer());
            if (match.isCheck()){
                System.out.println("CHECK!");
            }
        }
        else{
            System.out.println("CHECK-MATE!!");
            System.out.println("Vencedor: " + match.getCurrentPlayer());
        }
    }

    public static void printBoard(Board board){
        for (int i = 0; i < board.getRows(); i++){
            for (int j = 0; j < board.getColumns(); j++){
                printPiece(board.piece(i, j));
            }
            System.out.println();
        }
        System.out.println("a b c d e f g h");
    }

    public static void printBoard(Board board, boolean[][] possibleMoves){
        for (int i = 0; i < board.getRows(); i++){
            System.out.println(8 - i + " ");
            for (int j = 0; j < board.getColumns(); j++){
                if (possibleMoves [i][j]){
                    System.out.print("\u001B[47m");
                } else {
                    System.out.print("\u001B[40m");
                }
                printPiece(board.piece(i, j));
                System.out.print("\u001B[40m");
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    public static void printCapturedPieces(ChessMatch match){
        System.out.println("Peças Capturadas: ");
        System.out.print("Brancas: ");
        printSet(match.getCapturedPieces(Color.WHITE));
        System.out.println();

        System.out.print("Pretas: ");
        printSet(match.getCapturedPieces(Color.BLACK));
        System.out.println();
    }

    public static void printSet(HashSet<Piece> set){
        System.out.print("[");
        for (Piece piece : set){
            System.out.print(piece + " ");
        }
        System.out.println("]");
    }

    public static void printPiece(Piece piece){
        if (piece == null){
            System.out.print("- ");
        }
        else {
            if (piece.getColor() == Color.WHITE){
                System.out.print(piece);
            } else {
                System.out.print("\u001B[33m" + piece + "\u001B[0m");
            }
            System.out.print(" ");
        }
    }

    public static ChessPosition readChessPosition(){
        String string = System.console().readLine();
        char column = string.charAt(0);
        int row = Integer.parseInt(string.substring(1, 2));
        return new ChessPosition(column, row);
    }
}
