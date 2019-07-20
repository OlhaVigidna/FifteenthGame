package fifteenthproject.demo.controllers;

import fifteenthproject.demo.servises.playGame.Player;
import fifteenthproject.demo.servises.shuffler.GameShuffler;
import fifteenthproject.demo.servises.solover.FifteenthSolver;
import fifteenthproject.demo.servises.solover.Move;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Controller
public class MainController {

    private GameShuffler gameShuffler;
    private FifteenthSolver fifteenthSolver;

    public MainController(GameShuffler gameShuffler, FifteenthSolver fifteenthSolver) {
        this.gameShuffler = gameShuffler;
        this.fifteenthSolver = fifteenthSolver;
    }

    @GetMapping("/")
    public String home() {
        return "homePage";
    }

    @GetMapping("/shuffler")
    public String shuffler(Model model) {
        int[] array = gameShuffler.getGameField();
        model.addAttribute("array", array);
        model.addAttribute("shuffled", array);
        return "shufflerStartPage";
    }

    @PostMapping("/shuffling")
    public String shuffling(Integer numberOfMoves, Model model) {
        int[] ints = gameShuffler.startShuffler(numberOfMoves);
        int[] gameField = gameShuffler.getGameField();
        model.addAttribute("shuffled", ints);
        model.addAttribute("array", gameField);
        System.out.println(numberOfMoves);
        return "shufflerStartPage";
    }

    @GetMapping("/solve")
    public String solve() {
        return "solverPage";
    }

    @PostMapping("/solve")
    public String solveGame(@RequestPart(name = "file", required = false) MultipartFile file, Model model) {
        this.fifteenthSolver.startSolving(file);
        ArrayList<Move> listOfMoves = this.fifteenthSolver.getSolvedState().getListOfMoves();
        model.addAttribute("moves", listOfMoves);
        return "solverPage";
    }


    @PostMapping("/play")
    public String play(Integer time, Model model) {
        Player player = this.fifteenthSolver.getPlayer();

        model.addAttribute("timeInterval", time);
        model.addAttribute("gameField", player.getStartGameField());
        model.addAttribute("parentFields", player.getParentFields());


        return "playGameSolving";
    }

}
