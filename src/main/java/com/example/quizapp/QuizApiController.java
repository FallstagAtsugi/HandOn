package com.example.quizapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


//API　画面がないDataのみを返している　プログラムから呼び出して利用する
@Controller
@RequestMapping("page")
public class QuizApiController {

    private List<Quiz> quizzes = new ArrayList<>();
    private QuizFileDao quizFileDao = new QuizFileDao();
    private Quiz quiz;

    /*　ランダムでクイズを一軒だけ取得する　*/
    @GetMapping("quiz")
    public String quiz(Model model) {
        int index = new Random().nextInt(quizzes.size());//引数が3の時 0~2

        model.addAttribute("quiz", quizzes.get(index));
        return "quiz";
    }

    @GetMapping("show")
    public String show(Model model) {
        model.addAttribute("quizzes",quizzes);
        return "list";
    }

    @PostMapping("create")
    public String create(@RequestParam String question,
                       @RequestParam boolean answer) {
        quiz = new Quiz(question, answer); //これあんまりフィールドに設定した意味がない…。結局newしてるから

        quizzes.add(quiz);

        return "redirect:/page/show";//これだけ
    }

    @GetMapping("check")
    public String check(Model model,
                        @RequestParam String question,
                        @RequestParam boolean answer) {

        for (Quiz check : quizzes) {
            if (check.getQuestion().equals(question)) {
                model.addAttribute("quiz",check);
                if (answer == check.isAnswer()) {
                    model.addAttribute("result", "正解！");
                } else {
                    model.addAttribute("result", "残念！不正解…");
                }
            }
        }
        return "answer";
    }

    @PostMapping("save")
    public String save(RedirectAttributes attributes) {
        try {
            quizFileDao.write(quizzes);
            attributes.addFlashAttribute("successMessage","ファイルに保存しました");
        } catch (IOException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("errorMessage","ファイルに失敗しました");
        }
        return "redirect:/page/show";
    }

    @GetMapping("load")
    public String load(RedirectAttributes attributes) {
        try {
            quizzes = quizFileDao.read();//読み込んだ結果をフィールドに代入してあげる
            attributes.addFlashAttribute("successMessage","ファイルを読み込みました");
        } catch (IOException e) { //read()メソッドで投げられた例外をこちらの処理でキャッチしている
            e.printStackTrace();
            attributes.addFlashAttribute("errorMessage","ファイルの読み込みに失敗しました");
        }
        return "redirect:/page/show";
    }
}
