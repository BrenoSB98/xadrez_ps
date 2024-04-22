package org.ps.model;

import org.ps.board.Board;
import org.ps.board.Piece;
import org.ps.board.Position;
import org.ps.util.ChessMatch;
import org.ps.util.Color;

public class King extends Piece {
    private ChessMatch chessMatch;

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    private boolean canMove(Position position) {
        Piece piece_var = getBoard().piece(position);
        return piece_var == null || piece_var.getColor() != getColor();
    }

    private boolean testRookForCastling(Position position) {
        Piece piece_var = getBoard().piece(position);
        return piece_var != null && piece_var instanceof Rook && piece_var.getColor() == getColor() && piece_var.getMoveCount() == 0;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] moves = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position pos = new Position(0, 0);

        // Above
        pos.setValues(position.getRow() - 1, position.getColumn());
        if (getBoard().isValidPosition(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }

        // Northeast
        pos.setValues(position.getRow() - 1, position.getColumn() + 1);
        if (getBoard().isValidPosition(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }

        // Right
        pos.setValues(position.getRow(), position.getColumn() + 1);
        if (getBoard().isValidPosition(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }

        // Southeast
        pos.setValues(position.getRow() + 1, position.getColumn() + 1);
        if (getBoard().isValidPosition(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }

        // Below
        pos.setValues(position.getRow() + 1, position.getColumn());
        if (getBoard().isValidPosition(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }

        // Southwest
        pos.setValues(position.getRow() + 1, position.getColumn() - 1);
        if (getBoard().isValidPosition(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }

        // Left
        pos.setValues(position.getRow(), position.getColumn() - 1);
        if (getBoard().isValidPosition(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }

        // Northwest
        pos.setValues(position.getRow() - 1, position.getColumn() - 1);
        if (getBoard().isValidPosition(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }

        // Special move: castling
        if (getMoveCount() == 0 && !chessMatch.getCheck()) {

            // Special move: castling kingside rook
            Position rookPosition1 = new Position(position.getRow(), position.getColumn() + 3);
            if (testRookForCastling(rookPosition1)) {
                Position p1 = new Position(position.getRow(), position.getColumn() + 1);
                Position p2 = new Position(position.getRow(), position.getColumn() + 2);
                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
                    moves[position.getRow()][position.getColumn() + 2] = true;
                }
            }

            // Special move: castling queenside rook
            Position rookPosition2 = new Position(position.getRow(), position.getColumn() - 4);
            if (testRookForCastling(rookPosition2)) {
                Position p1 = new Position(position.getRow(), position.getColumn() - 1);
                Position p2 = new Position(position.getRow(), position.getColumn() - 2);
                Position p3 = new Position(position.getRow(), position.getColumn() - 3);
                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
                    moves[position.getRow()][position.getColumn() - 2] = true;
                }
            }
        }
        return moves;
    }

    @Override
    public String toString() {
        return "K";
    }
}
