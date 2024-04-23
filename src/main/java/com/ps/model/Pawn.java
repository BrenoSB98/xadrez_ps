package com.ps.model;

import com.ps.board.Piece;
import com.ps.board.Board;
import com.ps.board.Position;
import com.ps.util.ChessMatch;
import com.ps.util.Color;

public class Pawn extends Piece {
    private ChessMatch chessMatch;

    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    private boolean existsEnemy(Position pos) {
        Piece piece_var = board.piece(pos);
        return piece_var != null && piece_var.getColor() != getColor();
    }

    private boolean isFree(Position pos) {
        Piece piece_var = board.piece(pos);
        return piece_var != null && piece_var.getColor() != getColor();
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] moves = new boolean[board.getRows()][board.getColumns()];

        Position pos = new Position(0, 0);

        // White pawn
        if (getColor() == Color.WHITE) {
            pos.setValues(position.getRow() - 1, position.getColumn());
            if (board.isValidPosition(pos) && isFree(pos)) {
                moves[pos.getRow()][pos.getColumn()] = true;
            }

            pos.setValues(position.getRow() - 2, position.getColumn());
            Position pos2 = new Position(position.getRow() - 1, position.getColumn());
            if (board.isValidPosition(pos2) && isFree(pos2) && board.isValidPosition(pos) && isFree(pos) && getMoveCount() == 0) {
                moves[pos.getRow()][pos.getColumn()] = true;
            }
            pos.setValues(position.getRow() - 1, position.getColumn() - 1);
            if (board.isValidPosition(pos) && existsEnemy(pos)) {
                moves[pos.getRow()][pos.getColumn()] = true;
            }
            pos.setValues(position.getRow() - 1, position.getColumn() + 1);
            if (board.isValidPosition(pos) && existsEnemy(pos)) {
                moves[pos.getRow()][pos.getColumn()] = true;
            }

            // En passant move
            if (pos.getRow() == 3) {
                Position left = new Position(position.getRow(), position.getColumn() - 1);
                if (board.isValidPosition(left) && existsEnemy(left) && board.piece(left) == chessMatch.getEnPassantVulnerable()) {
                    moves[left.getRow() - 1][left.getColumn()] = true;
                }
                Position right = new Position(position.getRow(), position.getColumn() + 1);
                if (board.isValidPosition(right) && existsEnemy(right) && board.piece(right) == chessMatch.getEnPassantVulnerable()) {
                    moves[right.getRow() - 1][right.getColumn()] = true;
                }
            }
        }
        // Black pawn
        else {
            pos.setValues(position.getRow() + 1, position.getColumn());
            if (board.isValidPosition(pos) && isFree(pos)) {
                moves[pos.getRow()][pos.getColumn()] = true;
            }
            pos.setValues(position.getRow() + 2, position.getColumn());
            Position pos2 = new Position(position.getRow() + 1, position.getColumn());
            if (board.isValidPosition(pos2) && isFree(pos2) && board.isValidPosition(pos) && isFree(pos) && getMoveCount() == 0) {
                moves[pos.getRow()][pos.getColumn()] = true;
            }
            pos.setValues(position.getRow() + 1, position.getColumn() - 1);
            if (board.isValidPosition(pos) && existsEnemy(pos)) {
                moves[pos.getRow()][pos.getColumn()] = true;
            }
            pos.setValues(position.getRow() + 1, position.getColumn() + 1);
            if (board.isValidPosition(pos) && existsEnemy(pos)) {
                moves[pos.getRow()][pos.getColumn()] = true;
            }

            // En passant move
            if (pos.getRow() == 4) {
                Position left = new Position(position.getRow(), position.getColumn() - 1);
                if (board.isValidPosition(left) && existsEnemy(left) && board.piece(left) == chessMatch.getEnPassantVulnerable()) {
                    moves[left.getRow() + 1][left.getColumn()] = true;
                }
                Position right = new Position(position.getRow(), position.getColumn() + 1);
                if (board.isValidPosition(right) && existsEnemy(right) && board.piece(right) == chessMatch.getEnPassantVulnerable()) {
                    moves[right.getRow() + 1][right.getColumn()] = true;
                }
            }
        }
        return moves;
    }

    @Override
    public String toString() {
        return "P";
    }
}
