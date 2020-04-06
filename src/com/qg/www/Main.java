package com.qg.www;

import com.qg.www.calculate.Calculate;
import com.qg.www.calculate.Operations;
import com.qg.www.graphic.ShowGraphic;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @ClassName Main
 * @Description 主函数
 * @Author huange7
 * @Date 2020-04-03 10:08
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) {
        ShowGraphic.show(args);
      /*  Operations operations = new Operations();
        for (int i = 0; i <100;){
            String operation = operations.generateOperations();
            System.out.println("题目：" + operation);
            String result = Calculate.getResult(operation);
            if (result.equals("ERROR")) {
                continue;
            }
            System.out.println(operation + "=" + Calculate.getResult(operation));
            i++;
        }*/
//        String test = "1'1/2 + 1/2";
//        String tes = "(11'1/3 + 12) × 11 - 2";
//        System.out.println(Calculate.getResult(tes));
//        List<String> answerList = new ArrayList<>();
//        answerList.add("21");
//        answerList.add("2'1/2");
//        AnswerFile.WriteFile(answerList);

        //Todo huange7注意：比对答案结果直接拿下面这个即可（需要把文件路径改下）
//        File exerciseFile = new File("D://exercise.txt");
//        File answerFile = new File("D://answer.txt");
//        Map<Integer, String> result = AnswerFile.checkAnswer(exerciseFile, answerFile);
//        int right = 0, error = 0;
//        String Right = "";
//        String Error = "";
//        for (int i = 1; i <= result.size(); i++) {
//            if (result.get(i).equals("right")) {
//                right++;
//                if (Right.equals("")) {
//                    Right = Right + i;
//                }
//                else {
//                    Right = Right + ", " + i;
//                }
//            }
//            else {
//                error++;
//                if (Error.equals("")) {
//                    Error = Error + i;
//                }
//                else {
//                    Error = Error + ", " + i;
//                }
//            }
//        }
//        System.out.println("Correct: " + right + "(" + Right + ")");
//        System.out.println("Wrong: " + error + "(" + Error + ")");
    }

}
