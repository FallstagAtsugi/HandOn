package com.example.quizapp;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

//Dao・・・Data　access　Objectの略
public class QuizFileDao {
    private static final String FILE_PATH = "quizzes.txt";

    public void write(List<Quiz> quizzes) throws IOException {
        List<String> lines = new ArrayList<>();
        for(Quiz quiz: quizzes){
            lines.add(quiz.toString());
        }

        Path path = Paths.get(FILE_PATH);
        //第一引数：Path(場所)　　
        //第二引数：書き込むデータList<String>
        Files.write(path, lines);
    }
    public List<Quiz> read() throws IOException {
        Path path = Paths.get(FILE_PATH);
        List<String> lines = Files.readAllLines(path);

        //ListをQuiz型にしてあげる必要がある。からのリストを用意する
        List<Quiz> quizzes = new ArrayList<>();
        for (String line : lines){
            quizzes.add(Quiz.fromString(line));
        }
        return quizzes;
    }
}
