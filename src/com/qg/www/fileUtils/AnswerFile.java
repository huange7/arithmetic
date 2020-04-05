package com.qg.www.fileUtils;

import java.io.*;
import java.util.*;

/**
 * @description 将结果答案写入文件
 * @author guopei
 * @date 2020-04-05 10:56
 */
public class AnswerFile {

    //获取系统当前路径
//    public static final String address = System.getProperty("exe.path");

    //Todo 测试地址，后面需要采用上面的地址
    public static final String address = "D://";

    public static void WriteFile(List<String> answerList) throws IOException {

        File file = new File(address + "answer.txt");

        FileOutputStream outputStream = null;

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            int num = 1;
            for (String answer : answerList) {
                byte[] bytes = (num++ + ". " + answer + "\r\n").getBytes();
                outputStream.write(bytes);
            }
        } catch (IOException e) {
            System.out.println("文件写入失败");
        } finally {
            outputStream.close();
        }
    }

    //答案校对
    public static Map<Integer, String> checkAnswer(File exercisefile, File answerFile) {

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
            exerciseReader = new BufferedReader(new FileReader(exercisefile));
            answerReader = new BufferedReader(new FileReader(answerFile));
            //将题号和答案对应存储，以防止出现漏写某一道题的情况
            Map<Integer, String> exerciseMap = new HashMap();
            Map<Integer, String> answerMap = new HashMap();
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
                    if (answerMap.get(i).equals(exerciseMap.get(i))) {
                        //结果正确，将题号记录下来
                        result.put(i, "right");
                    }
                    else {
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
