package org.ps.board;

import org.ps.util.Color;

public abstract class Piece {
    protected Position position;
    private Color color;
    protected int moveCount;
    protected Board board;

    public Piece(Board board, Color color){
        position = null;
        this.board = board;
        this.color = color;
        moveCount = 0;
    }

    public Position getPosition() {
        return position;
    }

    public Color getColor() {
        return color;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public Board getBoard() {
        return board;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void incrementMoveCount() {
        moveCount++;
    }

    public void decrementMoveCount() {
        moveCount--;
    }

    public boolean existsPossibleMoves(){
        boolean[][] mat = possibleMoves();

        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                if (mat[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean possibleMove() {
        return possibleMoves()[position.getRow()][position.getColumn()];
    }

    public abstract boolean[][] possibleMoves();
}
