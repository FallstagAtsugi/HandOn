package com.example.quizapp;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Quiz {
    /**
     * 問題文
     */
    private String question;
    /**
     * 正解
     */
    private boolean answer;


    //JUnit
    @Override
    public String toString(){
        String marubatsu = answer ? "〇" : "×";
        return  question + " " + marubatsu;
    }

    //line・・・・問題文　〇
    public static Quiz fromString(String line){
        String question = line.substring(0,line.length() -2);
        boolean answer = line.endsWith("〇");
        return new Quiz(question, answer);
    }
}
