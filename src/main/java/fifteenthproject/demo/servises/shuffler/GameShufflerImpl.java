package fifteenthproject.demo.servises.shuffler;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class GameShufflerImpl implements GameShuffler {

    private int[] gameField;


    @PostConstruct
    private void initGameField() {
        int[] gameField = new int[16];
        int length = gameField.length - 1;

        for (int i = 0; i < length; i++) {
            gameField[i] = i + 1;
        }

        gameField[length] = 0;

        this.gameField = gameField;

    }

    @Override
    public int[] getGameField() {
        return gameField;
    }

    public void setGameField(int[] gameField) {
        this.gameField = gameField;
    }

    @Override
    public int[] startShuffler(int numberOfMoves) {

        int[] shuffledGameField = shufflerGameField(numberOfMoves);

        try {
            writeInFile(shuffledGameField);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return shuffledGameField;
    }

    private int getPositionOfZero(int[] gameField) {
        int positionOfZero = 0;

        for (int i = 0; i < gameField.length; i++) {
            if (gameField[i] == 0) {
                positionOfZero = i;
            }

        }
        return positionOfZero;
    }


    private int[] shufflerGameField(int numberOfMoves) { //!!!!

        int indexMoveTo = 0;
        int move = 0;

        int[] currentGameField = this.gameField;
        int[] suffleredGameField = new int[16];

        for (int i = 0; i < currentGameField.length; i++) {
            suffleredGameField[i] = currentGameField[i];
        }

        while (numberOfMoves > 0) {
            move = (int) (Math.random() * 4) + 1;

            indexMoveTo = indexMoveTo(suffleredGameField, move);

            if (indexMoveTo != -1) {
                shuffling(suffleredGameField, indexMoveTo);

                numberOfMoves--;
            } 
        }


        return suffleredGameField;
    }

    private int indexMoveTo(int[] currentGameField, int move) {
        int indexMoveTo = 0;

        switch (move) {
            case 1: {
                indexMoveTo = checkIfMoveIsValide(currentGameField, -4); //move up
                break;
            }
            case 2: {
                indexMoveTo = checkIfMoveIsValide(currentGameField, 4); //move down
                break;
            }
            case 3: {
                indexMoveTo = checkIfMoveIsValide(currentGameField, -1); //move to left
                break;
            }
            case 4: {
                indexMoveTo = checkIfMoveIsValide(currentGameField, 1); //move to right
                break;
            }
        }

        return indexMoveTo;
    }

    private int checkIfMoveIsValide(int[] currentArray, int indexMoveTo) {
        int positionOfZero = getPositionOfZero(currentArray);
        int move = positionOfZero + indexMoveTo;

        if (move >= 0 && move < 16) {
            return move;
        }

        return -1;
    }


    private int[] shuffling(int[] currentGameField, int indexMoveTo) {
        int indexOfZero = getPositionOfZero(currentGameField);
        int valueToReplace = currentGameField[indexMoveTo];

        currentGameField[indexOfZero] = valueToReplace;
        currentGameField[indexMoveTo] = 0;

        return currentGameField;
    }

    private void writeInFile(int[] shuffledGameField) throws IOException {
        File file = new File("gameField.txt"); ///
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(file);

        for (int i = 0; i < shuffledGameField.length; i++) {

            if (i % 4 == 0 && i != 0) {
                fileWriter.append('\n');
            }

            fileWriter.write(" " + shuffledGameField[i]);

        }

        fileWriter.flush();
        fileWriter.close();
    }
}
