package com.company;

public class GameField {
    enum TypeOfCell {Empty, Minned};
    private TypeOfCell[][] field;
    private int sizeX, sizeY;
    private int numberOfMines;

    public GameField() {
        this(10, 10, 10);
    }

    public GameField(int sizeX, int sizeY, int numberOfMines) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.numberOfMines = numberOfMines;
        field = new TypeOfCell[sizeY][sizeX];
        fillMasiveWithEmptyValues();
        fillMasiveWithMines();
    }

    public boolean isMinned(int x, int y) {
        if (isOutOfBounds(x, y))
            throw new ArrayIndexOutOfBoundsException("Error in the method isMinned()");

        return field[y][x] == TypeOfCell.Minned;
    }

    private boolean isOutOfBounds(int x, int y) {
        return x >= sizeX || y >= sizeY || x < 0 || y < 0;
    }

    private void fillMasiveWithMines() {
        for (int i = 0; i < numberOfMines; i++) {
            int randomX = (int) (Math.random() * sizeX);
            int randomY = (int) (Math.random() * sizeY);

            if (field[randomY][randomX] == TypeOfCell.Empty)
                field[randomY][randomX] = TypeOfCell.Minned;
            else i--;
        }
    }

    private void fillMasiveWithEmptyValues() {
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                field[i][j] = TypeOfCell.Empty;
            }
        }
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                result += field[i][j] == TypeOfCell.Empty ? "0" : "1";
            }
            result += "\n";
        }
        return result;
    }
}
