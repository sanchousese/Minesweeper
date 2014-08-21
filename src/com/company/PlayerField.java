package com.company;

public class PlayerField {
    enum CellType {
        UNCHECKED(-2), FLAGGED(-1), EMPTY(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8);
        private int code;

        private CellType(int code) {
            this.code = code;
        }

        public static CellType getType(int code) {
            for (CellType c : CellType.values())
                if (c.code == code)
                    return c;
            return null;
        }

        public static int getCode(CellType cellType) {
            for (CellType c : CellType.values())
                if (c == cellType)
                    return c.code;
            return -2;
        }
    }

    private GameField gameField;
    private CellType[][] field;
    private int sizeX, sizeY;
    private int numberOfMines;
    private boolean overed = false;

    public PlayerField() {
        this(10, 10, 10);
    }

    public PlayerField(int sizeX, int sizeY, int numberOfMines) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.numberOfMines = numberOfMines;
        field = new CellType[sizeY][sizeX];
        fillFieldWithUnchecked();
        gameField = new GameField(sizeX, sizeY, numberOfMines);
        System.out.println(gameField);
    }

    private void fillFieldWithUnchecked() {
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                field[i][j] = CellType.UNCHECKED;
            }
        }
    }

    public boolean playerMove(int x, int y) {
        if (field[y][x] == CellType.FLAGGED) return true;
        if (isMinned(x, y)) return false;


        field[y][x] = CellType.getType(countMines(x, y));

        if (countMines(x, y) > 0) return true;


        for (int i = -1; i <= 1; i++)
            checkCell(x - 1, y + i);

        for (int i = -1; i <= 1; i++)
            checkCell(x + 1, y + i);

        checkCell(x, y - 1);
        checkCell(x, y + 1);

        return true;
    }

    public void setFlag(int x, int y){
        field[y][x] = field[y][x] == CellType.FLAGGED ? CellType.UNCHECKED : CellType.FLAGGED;
    }

    public boolean isOver(){
        int counter = 0;
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (field[i][j] == CellType.UNCHECKED || field[i][j] == CellType.FLAGGED)
                    counter++;
            }
        }
        return counter == numberOfMines;
    }

    private void checkCell(int x, int y) {
        if (isCheckable(x, y)) {
            int numberOfMinesNear = countMines(x, y);
            if (numberOfMinesNear == 0)
                playerMove(x, y);
            field[y][x] = CellType.getType(numberOfMinesNear);
        }
    }

    private boolean isCheckable(int x, int y) {
        return !isOutOfBounds(x, y) && field[y][x] == CellType.UNCHECKED;
    }

    private int countMines(int x, int y) {
        int counter = 0;

        for (int i = -1; i <= 1; i++)
            if (isMinned(x - 1, y + i))
                counter++;

        for (int i = -1; i <= 1; i++) {
            if (isMinned(x + 1, y + i))
                counter++;
        }

        if (isMinned(x, y - 1)) counter++;
        if (isMinned(x, y + 1)) counter++;

        return counter;
    }

    private boolean isMinned(int x, int y) {
        return !isOutOfBounds(x, y) && gameField.isMinned(x, y);
    }

    private boolean isOutOfBounds(int x, int y) {
        return x >= sizeX || y >= sizeY || x < 0 || y < 0;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (field[i][j] == CellType.UNCHECKED )
                    result += "*";
                else if (field[i][j] == CellType.FLAGGED)
                    result += "&";
                else
                    result += CellType.getCode(field[i][j]);
                result += "\t";
            }
            result += "\n";
        }
        return result;
    }
}
