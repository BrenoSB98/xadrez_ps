package org.ps.model;

import org.ps.board.Board;
import org.ps.board.Piece;
import org.ps.board.Position;
import org.ps.util.Color;

public class Knight extends Piece {
    public Knight(Board board, Color color) {
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

        pos.setValues(position.getRow() - 1, position.getColumn() - 2);
        if (board.isValidPosition(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }
        pos.setValues(position.getRow() - 2, position.getColumn() - 1);
        if (board.isValidPosition(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }
        pos.setValues(position.getRow() - 2, position.getColumn() + 1);
        if (board.isValidPosition(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }
        pos.setValues(position.getRow() - 1, position.getColumn() + 2);
        if (board.isValidPosition(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }
        pos.setValues(position.getRow() + 1, position.getColumn() + 2);
        if (board.isValidPosition(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }
        pos.setValues(position.getRow() + 2, position.getColumn() + 1);
        if (board.isValidPosition(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }
        pos.setValues(position.getRow() + 2, position.getColumn() - 1);
        if (board.isValidPosition(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }
        pos.setValues(position.getRow() + 1, position.getColumn() - 2);
        if (board.isValidPosition(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }
        return moves;
    }

    @Override
    public String toString() {
        return "N";
    }
}
