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

    public static boolean isX;

    public static Boolean isGenerate;

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
        isX = false;
        isGenerate = false;
    }

    public static boolean isFile(String fileName) {
        if (fileName.matches(".*\\.txt")) {
            return true;
        }
        return false;
    }

    public static boolean verifyArgsX(String args){
        if ("".equals(args)) {
            return false;
        }
        return verifyArgs(args.split(" "), true);
    }

    public static boolean verifyArgs(String[] args, boolean isXCall) {

        boolean isR = false, isN = false, isE = false, isA = false;
        init();
        if (isXCall){
            isX = true;
        }
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-n":
                    try {
                        isN = true;
                        questionNumber = Integer.valueOf(args[i + 1]);
                    } catch (Exception e) {
                        return false;
                    }
                    if (questionNumber <= 0){
                        return false;
                    }
                    break;
                case "-r":
                    try {
                        isR = true;
                        numberBound = Integer.valueOf(args[i + 1]) + 1;
                    } catch (Exception e) {
                        return false;
                    }
                    if (numberBound <= 0){
                        return false;
                    }
                    break;
                case "-e":
                    if (isXCall){
                        return false;
                    }
                    isE = true;
                    try {
                        questionPath = args[i + 1];
                    } catch (Exception e) {
                        return false;
                    }
                    if (!isFile(questionPath)) {
                        return false;
                    }
                    break;
                case "-a":
                    if (isXCall){
                        return false;
                    }
                    isA = true;
                    try {
                        answerPath = args[i + 1];
                    } catch (Exception e) {
                        return false;
                    }
                    if (!isFile(answerPath)) {
                        return false;
                    }
                    break;
                case "-x":
                    if (isXCall){
                        return false;
                    }
                    isX = true;
                    break;
                default:
                    break;
            }
        }
        isGenerate = isN;
        return isN && isR || isA && isE || isX;
    }
}
