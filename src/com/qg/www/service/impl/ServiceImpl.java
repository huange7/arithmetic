package com.qg.www.service.impl;

import com.qg.www.calculate.Calculate;
import com.qg.www.calculate.Operations;
import com.qg.www.file.AnswerFile;
import com.qg.www.graphic.Controller;
import com.qg.www.graphic.ShowGraphic;
import com.qg.www.model.AnswerResult;
import com.qg.www.service.Service;
import com.qg.www.util.ArgsUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @ClassName ServiceImpl
 * @Description TODO
 * @Author huange7
 * @Date 2020-04-05 10:13
 * @Version 1.0
 */
public class ServiceImpl implements Service {

    private List<String> answerList = new ArrayList<>();

    List<LinkedList<String>> numberList = new ArrayList<>();

    List<LinkedList<Character>> charList = new ArrayList<>();

    @Override
    public void generateQuestion(Integer number) {
        // 清空答案列表
        answerList.clear();
        Operations operations = new Operations();
        while (number > 0) {
            AnswerResult answerResult = new AnswerResult();
            String operation = operations.generateOperations();
            String resultString = Calculate.getResult(operation, ArgsUtil.numberBound);
            if ("ERROR".equals(resultString)) {
                continue;
            }
            answerResult.setQuestion(operation + " =");
            answerList.add(resultString);
            Controller.operationData.add(answerResult);
            number--;
        }

        if (!ArgsUtil.isX) {
            // 将题目展示在控制台
            System.out.println("-----------------------------------------");
            printQuestion();
        }

        // 将答案写到文件
        try {
            AnswerFile.writeFile(answerList, true);
        } catch (IOException e) {
            System.out.println("生成答案文件异常");
        }

        System.out.println("答案文件已经存放在：" + AnswerFile.address);

        if (!ArgsUtil.isX) {
            downloadQuestion();
        }
    }


    private void printQuestion() {
        Controller.operationData.forEach(answerResult -> {
            System.out.println(answerResult.questionProperty().getValue());
            System.out.println("-----------------------------------------");
        });
    }

    @Override
    public int[] checkQuestion() {
        File exerciseFile = new File(ArgsUtil.questionPath);
        File answerFile = new File(ArgsUtil.answerPath);
        Map<Integer, String> result = AnswerFile.checkAnswer(exerciseFile, answerFile);
        if (result == null){
            System.out.println("文件不存在！");
            return null;
        }
        int right = 0, error = 0;
        String Right = "";
        String Error = "";
        for (int i = 1; i <= result.size(); i++) {
            if (result.get(i).equals("right")) {
                right++;
                if (Right.equals("")) {
                    Right = Right + i;
                }
                else {
                    Right = Right + ", " + i;
                }
            }
            else {
                error++;
                if (Error.equals("")) {
                    Error = Error + i;
                }
                else {
                    Error = Error + ", " + i;
                }
            }
        }
        System.out.println("Correct: " + right + "(" + Right + ")" +"\r\n" +  "Wrong: " + error + "(" + Error + ")");
        return new int[]{right, error};
    }

    @Override
    public void downloadQuestion() {
        // 生成题目文件
        try {
            AnswerFile.writeFile(Controller.operationData, false);
        } catch (IOException e) {
            System.out.println("生成题目文件时失败");
        }
        System.out.println("题目文件已经存放在：" + AnswerFile.address);
    }

    @Override
    public void main(String[] args) {
        // 进行参数的校验
        if (!ArgsUtil.verifyArgs(args, false)) {
            System.out.println("参数输入错误！");
            return;
        }

        // 展开图形界面
        if (ArgsUtil.isX) {
            ShowGraphic.show(args);
            return;
        }

        // 进行处理
        if (ArgsUtil.isGenerate) {
            // 生成题目
            generateQuestion(ArgsUtil.questionNumber);
        }else {
            // 进行答案的校对
            checkQuestion();
        }
    }
}
