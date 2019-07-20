package fifteenthproject.demo.servises.solover;

import fifteenthproject.demo.servises.playGame.Player;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;


@Service
public class FifteenthSolverImpl {

    private Player player;
    private PuzzleFieldState solvedState;



    public void startSolving(MultipartFile file) {

        int[] gameField = parseMultipartFile(file);
        ArrayList<Move> listOfMoves = new ArrayList<>();


//        if (isSolvable(gameField)) {

        PuzzleFieldState currentState = new PuzzleFieldState(0, gameField);
        ArrayList<int[]> currentStateParentFields = currentState.getParentFields();
        currentStateParentFields.add(currentState.getGameField());
        currentState.setParentFields(currentStateParentFields);

        PuzzleFieldState solvedState = solveGame(currentState);

        this.solvedState = solvedState;


        try {
            writeInFile(listOfMoves);
        } catch (IOException e) {
            e.printStackTrace();
        }


//        }else {
//            System.out.println("unsolveble");
//        }

       this.player = new Player(solvedState.getParentFields(), gameField);

        ArrayList<int[]> parentFields = solvedState.getParentFields();
    }

    private int[] parseMultipartFile(MultipartFile file) {

        byte[] bytes = null;
        int[] gameField = new int[16];
        int gameFieldLength = 0;

        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String fileToString = new String(bytes);
        String[] rows = fileToString.split("\n");

        for (int i = 0; i < rows.length; i++) {

            String rowToString = new String(rows[i]);
            String[] row = rowToString.split(" ");

            for (int j = 0; j < row.length; j++) {

                String s = row[j];

                try {
                    int i1 = Integer.parseInt(s);
                    gameField[gameFieldLength] = i1;
                    gameFieldLength++;
                } catch (NumberFormatException n) {
                }
            }
        }

        return gameField;
    }

    private boolean isSolvable(int[] gameField) {
        int inversions = 0;
        int zeroPosition = 1;

        for (int i = 0; i < gameField.length; i++) {
            if (gameField[i] == 0) {
                zeroPosition += i / 4;
            } else {
                for (int j = i + 1; j < gameField.length; j++) {
                    if (gameField[i] > gameField[j] && gameField[j] != 0) {
                        inversions++;
                    }
                }
            }
        }

        System.out.println("inversion " + inversions);
        System.out.println("position of zero " + zeroPosition);

        if ((inversions + zeroPosition) % 2 == 0) {
            return true;
        }
        return false;
    }

    private PuzzleFieldState solveGame(PuzzleFieldState gameField) {

        ArrayList<PuzzleFieldState> open = new ArrayList<>();
        ArrayList<PuzzleFieldState> closed = new ArrayList<>();

        open.add(gameField);

        while (open.size() > 0) {

            System.out.println("work");

            open = (ArrayList<PuzzleFieldState>) open.stream().sorted(new ComparePuzzleFields()).collect(Collectors.toList());

            PuzzleFieldState currentState = open.get(0);

            if (currentState.getHeuristicCost() == 0) {
                return currentState;
            }

            open.remove(currentState);

            ArrayList<PuzzleFieldState> openNeighborFields = openNeighborFields(currentState);

            for (PuzzleFieldState openNeighborField : openNeighborFields) {
                if (openNeighborField != null) {

                    if ((!open.contains(openNeighborField)) && (!closed.contains(openNeighborField))) {
                        open.add(openNeighborField);

                    } else if (open.contains(openNeighborField)) {

                        replaceField(open, openNeighborField);

                    } else if (closed.contains(openNeighborField)) {

                        int indexInClosed = closed.indexOf(openNeighborField);
                        PuzzleFieldState gameFieldFromClosed = closed.get(indexInClosed);

                        if (gameFieldFromClosed.getTotalScore() > openNeighborField.getTotalScore()) {

                            if (!open.contains(openNeighborField)) {

                                open.add(openNeighborField);

                            } else {

                                replaceField(open, openNeighborField);
                            }
                        }
                    }
                }
            }

            closed.add(currentState);
        }

        return null;
    }

    private ArrayList<PuzzleFieldState> openNeighborFields(PuzzleFieldState currentState) {
        ArrayList<PuzzleFieldState> neighborFields = new ArrayList<>();

        //left
        neighborFields.add(createNewPuzzleState(currentState, -1, '\u2190'));

        //right
        neighborFields.add(createNewPuzzleState(currentState, 1, '\u2192'));

        //up
        neighborFields.add(createNewPuzzleState(currentState, -4, '\u2191'));

        //down
        neighborFields.add(createNewPuzzleState(currentState, 4, '\u2193'));

        return neighborFields;
    }

    private PuzzleFieldState createNewPuzzleState(PuzzleFieldState currentState, int move, char arrow) {

        PuzzleFieldState newPuzzleFieldState = null;

        int startPositionOfZero = currentState.getPositionOfZero();
        int newPositionOfZero = startPositionOfZero + move;


        if (newPositionOfZero >= 0 && newPositionOfZero < currentState.getGameField().length) {

            int[] gameField = currentState.getGameField();
            int[] newGameFieldState = cloneArray(gameField); // clone
            for (int i = 0; i < gameField.length; i++) {
                newGameFieldState[i] = gameField[i];

            }
            ArrayList<Move> listOfMoves = new ArrayList<>(currentState.getListOfMoves());

            int valueToSwich = newGameFieldState[newPositionOfZero];
            int scoreOfMoves = currentState.getgScore() + 1;


            newGameFieldState[newPositionOfZero] = 0;
            newGameFieldState[startPositionOfZero] = valueToSwich;

            newPuzzleFieldState = new PuzzleFieldState(scoreOfMoves, newGameFieldState);

            listOfMoves.add(new Move(valueToSwich, arrow));
            ArrayList<int[]> parentFields = new ArrayList<>(currentState.getParentFields());
            parentFields.add(newGameFieldState);

            newPuzzleFieldState.setListOfMoves(listOfMoves);

            newPuzzleFieldState.setParentFields(parentFields);

        }

        return newPuzzleFieldState;
    }

    private int[] cloneArray(int[] array) {
        int[] newArray = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }

        return newArray;
    }

    private void replaceField(ArrayList<PuzzleFieldState> list, PuzzleFieldState fieldState) {

        if (list.contains(fieldState)) {

            int indexOfFieldStsate = list.indexOf(fieldState);
            PuzzleFieldState fieldFromList = list.get(indexOfFieldStsate);

            if (fieldFromList.getTotalScore() > fieldState.getTotalScore()) {

                list.remove(indexOfFieldStsate);
                list.add(fieldState);
            }
        }

    }

    private void writeInFile(ArrayList<Move> moves) throws IOException {

        File file = new File("listOfMoves.txt"); ///
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(file);

        for (Move move : moves) {

            fileWriter.write(move.toString());
            fileWriter.write("\n");
        }

        fileWriter.flush();
        fileWriter.close();

    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public PuzzleFieldState getSolvedState() {
        return solvedState;
    }

    public void setSolvedState(PuzzleFieldState solvedState) {
        this.solvedState = solvedState;
    }
}
