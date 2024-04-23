package com.ps.util;

import com.ps.board.Piece;
import com.ps.board.Board;
import com.ps.board.Position;
import com.ps.exception.BoardException;
import com.ps.model.Rook;
import com.ps.model.King;
import com.ps.model.Knight;
import com.ps.model.Queen;
import com.ps.model.Bishop;
import com.ps.model.Pawn;

import java.util.HashSet;
import java.util.Set;

public class ChessMatch {
    private final Board utilBoard;
    private int turn;
    private Color currentPlayer;
    private boolean terminated;
    private final Set<Piece> pieces;
    private final Set<Piece> capturedPieces;
    private Piece enPassantVulnerable;
    private boolean check;

    public ChessMatch() throws BoardException {
        utilBoard = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.BLACK;
        terminated = false;
        pieces = new HashSet<>();
        capturedPieces = new HashSet<>();
        enPassantVulnerable = null;
        initialSetup();
    }

    public Board getUtilBoard() {
        return utilBoard;
    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isCheck() {
        return check;
    }

    public boolean isTerminated() {
        return terminated;
    }

    public Piece getEnPassantVulnerable() {
        return enPassantVulnerable;
    }

    public Piece executeMovement(Position source, Position target) throws BoardException {
        Piece pieceVar = utilBoard.removePiece(source);
        pieceVar.incrementMoveCount();
        Piece capturedPiece = utilBoard.removePiece(target);
        utilBoard.insertPiece(target, pieceVar);

        if(capturedPiece != null){
            capturedPieces.add(capturedPiece);
        }

        if(pieceVar instanceof King && target.getColumn() == source.getColumn() + 2){
            Position sourcePosition = new Position(source.getRow(), source.getColumn() + 3);
            Position targetPosition = new Position(source.getRow(), source.getColumn() + 1);
            Piece T = utilBoard.removePiece(sourcePosition);
            T.incrementMoveCount();
            utilBoard.insertPiece(targetPosition, T);
        }

        if(pieceVar instanceof King && target.getColumn() == source.getColumn() - 2){
            Position sourcePosition = new Position(source.getRow(), source.getColumn() - 4);
            Position targetPosition = new Position(source.getRow(), source.getColumn() - 1);
            Piece T = utilBoard.removePiece(sourcePosition);
            T.incrementMoveCount();
            utilBoard.insertPiece(targetPosition, T);
        }

        if(pieceVar instanceof Pawn){
            if(source.getColumn() == target.getColumn() && capturedPieces == null){
                Position pawnPosition;
                if(pieceVar.getColor() == Color.WHITE){
                    pawnPosition = new Position(target.getRow() + 1, target.getColumn());
                }
                else {
                    pawnPosition = new Position(target.getRow() - 1, target.getColumn());
                }
                capturedPiece = utilBoard.removePiece(pawnPosition);
                capturedPieces.add(capturedPiece);
            }
        }
        return capturedPiece;
    }

    public void MakesMove(Position source, Position target) throws BoardException {
        Piece capturedPiece = executeMovement(source, target);
        if(testCheck(currentPlayer)){
            undoMovement(source, target, capturedPiece);
            throw new BoardException("Chess Match Error");
        }
        Piece pierceVar = utilBoard.piece(target);

        if(pierceVar instanceof Pawn){
            if((pierceVar.getColor() == Color.WHITE && target.getRow() == 0) || (pierceVar.getColor() == Color.BLACK && target.getRow() == 7)){
                pierceVar = utilBoard.removePiece(target);
                pieces.remove(pierceVar);
                Piece queen = new Queen(utilBoard, pierceVar.getColor());
                utilBoard.insertPiece(target, queen);
                pieces.add(queen);
            }
        }

        if(testCheck(opponent(currentPlayer))){
            check = true;
        }
        else{
            check = false;
        }

        if(testCheckMate(opponent(currentPlayer))){
            terminated = true;
        }
        else{
            turn++;
        }

        if(pierceVar instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)){
            enPassantVulnerable = pierceVar;
        }
        else{
            enPassantVulnerable = null;
        }
    }

    public void undoMovement(Position source, Position target, Piece capturedPiece) throws BoardException {
        Piece pieceVar = utilBoard.removePiece(target);
        pieceVar.decrementMoveCount();
        if(capturedPiece != null){
            utilBoard.insertPiece(target, capturedPiece);
            capturedPieces.remove(capturedPiece);
        }
        utilBoard.insertPiece(source, pieceVar);

        if(pieceVar instanceof King && target.getColumn() == source.getColumn() + 2){
            Position sourcePosition = new Position(source.getRow(), source.getColumn() + 3);
            Position targetPosition = new Position(source.getRow(), source.getColumn() + 1);
            Piece T = utilBoard.removePiece(targetPosition);
            T.decrementMoveCount();
            utilBoard.insertPiece(sourcePosition, T);
        }

        if(pieceVar instanceof King && target.getColumn() == source.getColumn() - 2){
            Position sourcePosition = new Position(source.getRow(), source.getColumn() + 4);
            Position targetPosition = new Position(target.getRow(), target.getColumn() + 1);
            Piece T = utilBoard.removePiece(targetPosition);
            T.decrementMoveCount();
            utilBoard.insertPiece(sourcePosition, T);
        }

        if(pieceVar instanceof Pawn){
            if(target.getColumn() == source.getColumn() && capturedPieces == enPassantVulnerable){
                Piece pawn = utilBoard.removePiece(source);
                Position pawnPosition;
                if(pieceVar.getColor() == Color.WHITE){
                    pawnPosition = new Position(3, target.getColumn());
                }
                else {
                    pawnPosition = new Position(3, target.getColumn());
                }
                utilBoard.insertPiece(pawnPosition, pawn);
            }
        }
    }

    public void validateSourcePosition(Position position) throws BoardException {
        if (utilBoard.piece(position) == null) {
            throw new BoardException("There is no piece in the chosen position!");
        }
        if (currentPlayer != utilBoard.piece(position).getColor()) {
            throw new BoardException("The chosen piece is not yours");
        }
        if (!utilBoard.piece(position).existsPossibleMoves()) {
            throw new BoardException("There is no possible moves for the chosen piece");
        }
    }

    public void validateTargetPosition(Position source, Position target) throws BoardException {
        if (!utilBoard.piece(source).possibleMove(target)) {
            throw new BoardException("The chosen piece can't move to target position");
        }
    }

    private void alterPlayer() {
        if (currentPlayer == Color.BLACK) {
            currentPlayer = Color.WHITE;
        }
        else{
            currentPlayer = Color.BLACK;
        }
    }

    private Color opponent(Color color) {
        if (color == Color.WHITE) {
            return Color.BLACK;
        }
        return Color.WHITE;
    }

    public HashSet<Piece> getCapturedPieces(Color color) {
        HashSet<Piece> aux = new HashSet<Piece>();
        for(Piece x : capturedPieces){
            if(x.getColor() == color){
                aux.add(x);
            }
        }
        return aux;
    }

    public HashSet<Piece> piecesInGame(Color color) {
        HashSet<Piece> aux = new HashSet<Piece>();
        for(Piece x : capturedPieces){
            if(x.getColor() == color){
                aux.add(x);
            }
        }
        aux.removeAll(getCapturedPieces(color));
        return aux;
    }

    private Piece king(Color color) {
        for (Piece piece : piecesInGame(color)) {
            if (piece instanceof King) {
                return piece;
            }
        }
        return null;
    }

    public boolean testCheck(Color color) throws BoardException {
        Piece king = king(color);
        if(king == null){
            throw new BoardException("The King" + color + "aren't in the board");
        }
        for (Piece piece : piecesInGame(opponent(color))) {
            boolean[][] moves = piece.possibleMoves();
            if (moves[king.getPosition().getRow()][king.getPosition().getColumn()]) {
                return true;
            }
        }
        return false;
    }

    public boolean testCheckMate(Color color) throws BoardException {
        if (!testCheck(color)) {
            return false;
        }
        for (Piece piece : piecesInGame(color)) {
            boolean[][] moves = piece.possibleMoves();
            for (int i = 0; i < utilBoard.getRows(); i++) {
                for (int j = 0; j < utilBoard.getColumns(); j++) {
                    if (moves[i][j]) {
                        Position source = piece.getPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = executeMovement(source, target);
                        boolean check = testCheck(color);
                        undoMovement(source, target, capturedPiece);
                        if (!check) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void placeNewPiece(char column, int row, Piece piece) throws BoardException {
        utilBoard.insertPiece(new ChessPosition(column, row).toPosition(), piece);
        pieces.add(piece);
    }

    private void initialSetup() throws BoardException {
        placeNewPiece('a', 1, new Rook(utilBoard, Color.WHITE));
        placeNewPiece('b', 1, new Knight(utilBoard, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(utilBoard, Color.WHITE));
        placeNewPiece('d', 1, new Queen(utilBoard, Color.WHITE));
        placeNewPiece('e', 1, new King(utilBoard, Color.WHITE, this));
        placeNewPiece('f', 1, new Bishop(utilBoard, Color.WHITE));
        placeNewPiece('g', 1, new Knight(utilBoard, Color.WHITE));
        placeNewPiece('h', 1, new Rook(utilBoard, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(utilBoard, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(utilBoard, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(utilBoard, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(utilBoard, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(utilBoard, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(utilBoard, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(utilBoard, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(utilBoard, Color.WHITE, this));

        placeNewPiece('a', 8, new Rook(utilBoard, Color.BLACK));
        placeNewPiece('b', 8, new Knight(utilBoard, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(utilBoard, Color.BLACK));
        placeNewPiece('d', 8, new Queen(utilBoard, Color.BLACK));
        placeNewPiece('e', 8, new King(utilBoard, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(utilBoard, Color.BLACK));
        placeNewPiece('g', 8, new Knight(utilBoard, Color.BLACK));
        placeNewPiece('h', 8, new Rook(utilBoard, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(utilBoard, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(utilBoard, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(utilBoard, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(utilBoard, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(utilBoard, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(utilBoard, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(utilBoard, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(utilBoard, Color.BLACK, this));
    }
}
