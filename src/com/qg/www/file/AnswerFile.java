package com.qg.www.file;

import com.qg.www.graphic.Controller;
import com.qg.www.model.AnswerResult;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author guopei
 * @description 将结果答案写入文件
 * @date 2020-04-05 10:56
 */
public class AnswerFile {

    //获取系统当前路径
    public static final String address = System.getProperty("user.dir");

    //将答案写入文件
    public static void writeFile(List answerList, boolean isAnswer) throws IOException {
        File answerfile;
        File exercisefile;
        FileOutputStream outputStreamAnswer = null;
        FileOutputStream outputStreamExercise = null;
        if (isAnswer) {
            answerfile = new File(address + "\\answer.txt");
            outputStreamAnswer = new FileOutputStream(answerfile);
            if (!answerfile.exists()) {
                answerfile.createNewFile();
            }
        } else {
            exercisefile = new File(address + "\\exercise.txt");
            outputStreamExercise = new FileOutputStream(exercisefile);
            if (!exercisefile.exists()) {
                exercisefile.createNewFile();
            }
        }
        int num = 1;
        for (Object o : answerList) {
            String answer;
            if (o instanceof String) {
                answer = (String) o;
            } else {
                answer = ((AnswerResult) o).questionProperty().getValue();
            }
            byte[] bytes = (num++ + ". " + answer + "\r\n").getBytes();
            if (isAnswer){
                outputStreamAnswer.write(bytes);
            }else {
                outputStreamExercise.write(bytes);
            }
        }
        if (isAnswer){
            outputStreamAnswer.close();
        }else {
            outputStreamExercise.close();
        }
    }

    //答案校对，返回的是比对结果，key是题号，value是right或error
    public static Map<Integer, String> checkAnswer(File exercisefile, File answerFile) {

        Controller.operationData.clear();

        BufferedReader exerciseReader = null;

        BufferedReader answerReader = null;

        Map<Integer, String> result = new HashMap<>();

        try {
            if (!exercisefile.exists()) {
                System.out.println("练习答案文件不存在");
                return null;
            }
            if (!answerFile.exists()) {
                System.out.println("答案文件不存在");
                return null;
            }
            if (!exercisefile.getName().matches(".+(.txt)$") || !answerFile.getName().matches(".+(.txt)$")) {
                System.out.println("文件格式不支持");
                return null;
            }

            InputStreamReader exerciseIn = new InputStreamReader(new FileInputStream(exercisefile.getAbsolutePath()), StandardCharsets.UTF_8);
            InputStreamReader answerIn = new InputStreamReader(new FileInputStream(answerFile.getAbsolutePath()), StandardCharsets.UTF_8);
            exerciseReader = new BufferedReader(exerciseIn);
            answerReader = new BufferedReader(answerIn);
            //将题号和答案对应存储，以防止出现漏写某一道题的情况
            Map<Integer, String> exerciseMap = new HashMap<>();
            Map<Integer, String> answerMap = new HashMap<>();
            String content = null;
            while ((content = exerciseReader.readLine()) != null) {
                //去除字符串的所有空格
                content = content.replaceAll(" +", "");
                exerciseMap.put(Integer.valueOf(content.split("\\.")[0]), content.split("\\.")[1]);
            }
            while ((content = answerReader.readLine()) != null) {
                //去除字符串的所有空格
                content = content.replaceAll(" +", "");
                answerMap.put(Integer.valueOf(content.split("\\.")[0]), content.split("\\.")[1]);
            }
            exerciseReader.close();
            answerReader.close();
            //比对结果
            for (int i = 1; i <= answerMap.size(); i++) {
                if (exerciseMap.containsKey(i)) {
                    //将答案切割出来（格式：3+2=5）
                    String exercise = exerciseMap.get(i).split("=")[1];

                    // 将答案写入图形化界面的表格
                    AnswerResult answerResult = new AnswerResult();
                    answerResult.setQuestion(exerciseMap.get(i).split("\\=")[0]);
                    answerResult.setAnswerByStudent(exercise);
                    answerResult.setAnswerByProject(answerMap.get(i));
                    Controller.operationData.add(answerResult);

                    if (answerMap.get(i).equals(exercise)) {
                        //结果正确，将题号记录下来
                        result.put(i, "right");
                    } else {
                        result.put(i, "error");
                    }
                }
                //说明该题漏写或者错误
                else {
                    result.put(i, "error");
                }
            }

        } catch (IOException e) {
            System.out.println("读取文件错误");
        }
        return result;
    }

}
