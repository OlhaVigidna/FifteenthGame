package fifteenthproject.demo.servises.solover;

import java.util.Objects;

public class Move {

    private int changedValue;
    private char valueOfMove;

    public Move() {
    }

    public Move(int changedValue, char valueOfMove) {
        this.changedValue = changedValue;
        this.valueOfMove = valueOfMove;
    }

    public int getChangedValue() {
        return changedValue;
    }

    public void setChangedValue(int changedValue) {
        this.changedValue = changedValue;
    }

    public char getValueOfMove() {
        return valueOfMove;
    }

    public void setValueOfMove(char valueOfMove) {
        this.valueOfMove = valueOfMove;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return changedValue == move.changedValue &&
                valueOfMove == move.valueOfMove;
    }

    @Override
    public int hashCode() {
        return Objects.hash(changedValue, valueOfMove);
    }

    @Override
    public String toString() {
        return changedValue +
                " " + valueOfMove;
    }
}
