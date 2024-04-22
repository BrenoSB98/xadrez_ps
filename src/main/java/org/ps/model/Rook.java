package org.ps.model;

import org.ps.board.Board;
import org.ps.board.Piece;
import org.ps.board.Position;
import org.ps.util.Color;

public class Rook extends Piece {
    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] moves = new boolean[board.getRows()][board.getColumns()];
        Position pos = new Position(0, 0);

        //Move Up
        pos.setValues(position.getRow() - 1, position.getColumn());
        while(board.isValidPosition(pos) && canMove(pos)){
            moves[pos.getRow()][pos.getColumn()] = true;
            if (board.piece(pos) != null && board.piece(pos).getColor() != getColor()) {
                break;
            }
            else{
                pos.setRow(pos.getRow() - 1);
            }
        }

        // Right
        pos.setValues(position.getRow(), position.getColumn() + 1);
        while (board.isValidPosition(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
            if (board.piece(pos) != null && board.piece(pos).getColor() != getColor()) {
                break;
            }
            pos.setColumn(pos.getColumn() + 1);
        }

        // Down
        pos.setValues(position.getRow() + 1, position.getColumn());
        while (board.isValidPosition(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
            if (board.piece(pos) != null && board.piece(pos).getColor() != getColor()) {
                break;
            }
            pos.setRow(pos.getRow() + 1);
        }

        // Left
        pos.setValues(position.getRow(), position.getColumn() - 1);
        while (board.isValidPosition(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
            if (board.piece(pos) != null && board.piece(pos).getColor() != getColor()) {
                break;
            }
            pos.setColumn(pos.getColumn() - 1);
        }
        return moves;
    }

    private boolean canMove(Position position) {
        Piece piece_var = board.piece(position);
        return piece_var == null || piece_var.getColor() != getColor();
    }

    @Override
    public String toString(){
        return "T";
    }
}
