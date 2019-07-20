package fifteenthproject.demo.servises.playGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Player {

    private ArrayList<int[]> parentFields;

    private int[] startGameField;


    public Player(ArrayList<int[]> parentFields, int[] startGameField) {
        this.parentFields = parentFields;
        this.startGameField = startGameField;

    }


    public ArrayList<int[]> getParentFields() {
        return parentFields;
    }

    public void setParentFields(ArrayList<int[]> parentFields) {
        this.parentFields = parentFields;
    }

    public int[] getStartGameField() {
        return startGameField;
    }

    public void setStartGameField(int[] startGameField) {
        this.startGameField = startGameField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(parentFields, player.parentFields) &&
                Arrays.equals(startGameField, player.startGameField);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(parentFields);
        result = 31 * result + Arrays.hashCode(startGameField);
        return result;
    }

    @Override
    public String toString() {
        return "Player{" +
                "parentFields=" + parentFields +
                ", startGameField=" + Arrays.toString(startGameField) +
                '}';
    }
}
