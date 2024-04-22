package org.ps.model;

import org.ps.board.Board;
import org.ps.board.Piece;
import org.ps.board.Position;
import org.ps.util.Color;

public class Bishop extends Piece {
    public Bishop(Board board, Color color) {
        super(board, color);
    }

    private boolean canMove(Position position) {
        Piece piece_var = board.piece(position);
        return piece_var == null || piece_var.getColor() != getColor();
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] moves = new boolean[board.getRows()][board.getColumns()];

        Position pos = new Position(0, 0);

        // NO
        pos.setValues(position.getRow() - 1, position.getColumn() - 1);
        while (board.isValidPosition(pos) && canMove(pos)) {

            moves[pos.getRow()][pos.getColumn()] = true;
            if (board.piece(pos) != null && board.piece(pos).getColor() != getColor()) {
                break;
            }
            pos.setValues(pos.getRow() - 1, pos.getColumn() - 1);

        }

        // NE
        pos.setValues(position.getRow() - 1, position.getColumn() + 1);
        while (board.isValidPosition(pos) && canMove(pos)) {

            moves[pos.getRow()][pos.getColumn()] = true;
            if (board.piece(pos) != null && board.piece(pos).getColor() != getColor()) {
                break;
            }
            pos.setValues(pos.getRow() - 1, pos.getColumn() + 1);

        }
        // SE
        pos.setValues(position.getRow() + 1, position.getColumn() + 1);
        while (board.isValidPosition(pos) && canMove(pos)) {

            moves[pos.getRow()][pos.getColumn()] = true;
            if (board.piece(pos) != null && board.piece(pos).getColor() != getColor()) {
                break;
            }
            pos.setValues(pos.getRow() + 1, pos.getColumn() + 1);

        }
        // SO
        pos.setValues(position.getRow() + 1, position.getColumn() - 1);
        while (board.isValidPosition(pos) && canMove(pos)) {

            moves[pos.getRow()][pos.getColumn()] = true;
            if (board.piece(pos) != null && board.piece(pos).getColor() != getColor()) {
                break;
            }
            pos.setValues(pos.getRow() + 1, pos.getColumn() - 1);

        }
        return moves;
    }

    @Override
    public String toString() {
        return "B";
    }
}
