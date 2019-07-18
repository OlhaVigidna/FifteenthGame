package fifteenthproject.demo.controllers;

import fifteenthproject.demo.servises.shuffler.GameShuffler;
import fifteenthproject.demo.servises.solover.FifteenthSolverImpl;
import fifteenthproject.demo.servises.solover.Move;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class MainController {

    private GameShuffler gameShuffler;
    private FifteenthSolverImpl fifteenthSolver;

    public MainController(GameShuffler gameShuffler, FifteenthSolverImpl fifteenthSolver) {
        this.gameShuffler = gameShuffler;
        this.fifteenthSolver = fifteenthSolver;
    }

    @GetMapping("/")
    public String home() {
        System.out.println("work");
        return "homePage";
    }

    @GetMapping("/shuffler")
    public String shuffler(Model model) {
        int[] array = gameShuffler.getGameField();
        model.addAttribute("array", array);
        model.addAttribute("shuffled", array);
        System.out.println("button work");
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
        System.out.println("button work");
        return "solverPage";
    }

    @PostMapping("/solve")
    public String solveGame(@RequestPart(name = "file", required = false) MultipartFile file, Model model) {
        System.out.println("file size " + file.getSize());
        System.out.println("Start solving");
        ArrayList<Move> listOfMoves = fifteenthSolver.startSolving(file);
        model.addAttribute("moves", listOfMoves);
        return "solverPage";
    }


    @GetMapping("/play")
    public String play() {
        System.out.println("button work");
        return "index";
    }

}
