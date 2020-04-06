package com.qg.www.util;

import javafx.scene.control.Alert;

/**
 * @ClassName ArgsUtil
 * @Description 参数工具类
 * @Author huange7
 * @Date 2020-04-05 10:21
 * @Version 1.0
 */
public class ArgsUtil {

    public static Integer questionNumber;

    public static Integer numberBound;

    public static String questionPath;

    public static String answerPath;

    public static void alertTip(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.titleProperty().set("提醒");
        alert.headerTextProperty().set(message);
        alert.showAndWait();
    }

    private static void init() {
        questionNumber = 0;
        numberBound = 0;
        questionPath = null;
        answerPath = null;
    }

    public static boolean isFile(String fileName) {
        if (fileName.matches(".*.txt")) {
            return true;
        }
        return false;
    }

    public static boolean verifyArgs(String args) {
        if ("".equals(args)) {
            return false;
        }
        init();
        String[] allArgs = args.split(" ");
        for (int i = 0; i < allArgs.length; i++) {
            switch (allArgs[i]) {
                case "-n":
                    try {
                        questionNumber = Integer.valueOf(allArgs[i + 1]);
                    } catch (Exception e) {
                        return false;
                    }
                    break;
                case "-r":
                    try {
                        numberBound = Integer.valueOf(allArgs[i + 1]);
                    } catch (Exception e) {
                        return false;
                    }
                    break;
                case "-e":
                    questionPath = allArgs[i + 1];
                    if (!isFile(questionPath)) {
                        return false;
                    }
                    break;
                case "-a":
                    answerPath = allArgs[i + 1];
                    if (!isFile(answerPath)) {
                        return false;
                    }
                    break;
                default:
                    break;
            }
        }
        return true;
    }
}
