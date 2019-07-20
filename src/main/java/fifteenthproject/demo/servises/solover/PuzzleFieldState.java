package fifteenthproject.demo.servises.solover;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class PuzzleFieldState {

    private int gScore;

    private int heuristicCost;

    private int totalScore;

    private int positionOfZero;

    private ArrayList<Move> listOfMoves = new ArrayList<>();

    private int[] gameField;

    private ArrayList<int[]> parentFields = new ArrayList<>();


    public PuzzleFieldState(int gScore, int[] gameField) {
        this.gScore = gScore;
        this.gameField = gameField;
        this.heuristicCost = findHeuristicCost(this.gameField);
        this.positionOfZero = findPositionOfZero(this.gameField);
        this.totalScore = findTotalScore();
    }


    private int findPositionOfZero(int[] gameField) {
        int indexOfZero = 0;

        for (int i = 0; i < gameField.length; i++) {
            if (gameField[i] == 0) {
                indexOfZero = i;
            }
        }
        return indexOfZero;
    }
//Визначаємо еврестичне значення, тобто мфнімально необхідну кількість ходів
    private int findHeuristicCost(int[] ourGameField) {

        int heuristicSum = 0;

        for (int i = 0; i < ourGameField.length; i++) {

            if (ourGameField[i] == 0) {
                continue;

            } else{
                int forRowGameTile = ourGameField[i];
            int forColumnGameTile = ourGameField[i];


            int currentColumn = (i%4)+1;
            int columnMoveTo = forColumnGameTile%4;

            if (forColumnGameTile%4==0){
                columnMoveTo = 4;
                forRowGameTile = forRowGameTile-1;
            }

            int currentRow = i/4;
            int rowMoveTo = (forRowGameTile-1)/4;

            int moveInColumns = Math.abs(columnMoveTo-currentColumn);
            int moveInRows = Math.abs(rowMoveTo-currentRow);

            heuristicSum+=(moveInColumns+moveInRows);
        }

        }
        return heuristicSum;
    }

    private int findTotalScore() {
        return this.gScore + this.heuristicCost;
    }

    public int getgScore() {
        return gScore;
    }

    public void setgScore(int gScore) {
        this.gScore = gScore;
    }

    public int getHeuristicCost() {
        return heuristicCost;
    }

    public void setHeuristicCost(int heuristicCost) {
        this.heuristicCost = heuristicCost;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getPositionOfZero() {
        return positionOfZero;
    }

    public void setPositionOfZero(int positionOfZero) {
        this.positionOfZero = positionOfZero;
    }

    public ArrayList<Move> getListOfMoves() {
        return listOfMoves;
    }

    public void setListOfMoves(ArrayList<Move> listOfMoves) {
        this.listOfMoves = listOfMoves;
    }

    public int[] getGameField() {
        return gameField;
    }

    public void setGameField(int[] gameField) {
        this.gameField = gameField;
    }

    public ArrayList<int[]> getParentFields() {
        return parentFields;
    }

    public void setParentFields(ArrayList<int[]> parentFields) {
        this.parentFields = parentFields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PuzzleFieldState that = (PuzzleFieldState) o;
        return gScore == that.gScore &&
                heuristicCost == that.heuristicCost &&
                totalScore == that.totalScore &&
                positionOfZero == that.positionOfZero &&
                Objects.equals(listOfMoves, that.listOfMoves) &&
                Arrays.equals(gameField, that.gameField);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(gScore, heuristicCost, totalScore, positionOfZero, listOfMoves);
        result = 31 * result + Arrays.hashCode(gameField);
        return result;
    }

    @Override
    public String toString() {
        return "PuzzleFieldState{" +
                "gScore=" + gScore +
                ", heuristicCost=" + heuristicCost +
                ", totalScore=" + totalScore +
                ", positionOfZero=" + positionOfZero +
                ", listOfMoves=" + listOfMoves +
                ", gameField=" + Arrays.toString(gameField) +
                '}';
    }
}
