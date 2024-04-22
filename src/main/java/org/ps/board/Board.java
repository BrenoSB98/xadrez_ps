package org.ps.board;

import org.ps.exception.BoardException;

public class Board {
    private int rows;
    private int columns;
    private Piece[][] pieces;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.pieces = new Piece[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public Piece piece (int row, int column) {
        return pieces[row][column];
    }

    public Piece piece (Position position) {
        return pieces[position.getRow()][position.getColumn()];
    }

    public boolean isValidPosition (Position position) {
        return position.getRow() >= 0 && position.getRow() < rows && position.getColumn() >= 0 && position.getColumn() < columns;
    }

    private void validatePosition (Position position) throws BoardException {
        if (!isValidPosition(position)) {
            throw new BoardException("Invalid position");
        }
    }

    public boolean isThereAPiece(Position position) throws BoardException {
        validatePosition(position);
        return piece(position) != null;
    }

    public void placePiece(Position position, Piece piece) throws BoardException {
        if (isThereAPiece(position)) {
            throw new BoardException("There is already a piece on this position");
        }
        else {
            pieces[position.getRow()][position.getColumn()] = piece;
            piece.setPosition(position);
        }
    }

    public Piece removePiece(Position position) throws BoardException {
        if (isThereAPiece(position)) {
            Piece aux = piece(position);
            aux.setPosition(null);
            pieces[position.getRow()][position.getColumn()] = null;
            return aux;
        }
        else{
            return null;
        }
    }
}
