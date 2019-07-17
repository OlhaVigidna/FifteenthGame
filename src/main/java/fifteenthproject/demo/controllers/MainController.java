package fifteenthproject.demo.controllers;

import fifteenthproject.demo.servises.shuffler.GameShuffler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    private GameShuffler gameShuffler;

    public MainController(GameShuffler gameShuffler) {
        this.gameShuffler = gameShuffler;
    }

    @GetMapping("/")
    public String home(){
        System.out.println("work");
        return "homePage";
    }

    @GetMapping("/shuffler")
    public String shuffler(Model model){
        int[] array = gameShuffler.getGameField();
        model.addAttribute("array", array);
        model.addAttribute("shuffled", array);
        System.out.println("button work");
        return "shufflerStartPage";
    }

    @PostMapping("/shuffling")
    public String shuffling(Integer numberOfMoves, Model model){
        int[] ints = gameShuffler.startShuffler(numberOfMoves);
        int[] gameField = gameShuffler.getGameField();
        model.addAttribute("shuffled", ints);
        model.addAttribute("array", gameField);
        System.out.println(numberOfMoves);
        return "shufflerStartPage";
    }

     @GetMapping("/solve")
    public String solve(){
        System.out.println("button work");
        return "index";
    }
     @GetMapping("/play")
    public String play(){
        System.out.println("button work");
        return "index";
    }

}
