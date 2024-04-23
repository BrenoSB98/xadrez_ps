package com.ps;

import com.ps.board.Position;
import com.ps.exception.BoardException;
import com.ps.util.ChessMatch;
import com.ps.view.Display;

public class Main {
    public static void main(String[] args) {
        try {
            ChessMatch match = new ChessMatch();

            while(!match.isTerminated()){
                try{
                    clearScreen();
                    Display.printMatch(match);

                    System.out.println();
                    System.out.print("Origem: ");
                    Position source = Display.readChessPosition().toPosition();
                    match.validateSourcePosition(source);

                    boolean[][] possiblePositions = match.getUtilBoard().piece(source).possibleMoves();

                    clearScreen();
                    Display.printBoard(match.getUtilBoard(), possiblePositions);

                    System.out.println();
                    System.out.print("Destino: ");
                    Position target = Display.readChessPosition().toPosition();
                    match.validateTargetPosition(source, target);

                    match.MakesMove(source, target);
                }
                catch (BoardException e){
                    System.out.println(e.getMessage());
                    System.out.println();
                }
            }
            clearScreen();
            Display.printMatch(match);
        }
        catch (BoardException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();
    }
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}