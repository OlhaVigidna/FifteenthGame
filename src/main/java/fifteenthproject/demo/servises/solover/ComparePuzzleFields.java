package fifteenthproject.demo.servises.solover;

import java.util.Comparator;
import java.util.function.ToIntFunction;

public class ComparePuzzleFields implements Comparator<PuzzleFieldState> {


    @Override
    public int compare(PuzzleFieldState o1, PuzzleFieldState o2) {
        if (o1.getTotalScore() == o2.getTotalScore()){
            return o1.getgScore()-o2.getgScore();
        }
        return o1.getTotalScore() - o2.getTotalScore();
    }

}
