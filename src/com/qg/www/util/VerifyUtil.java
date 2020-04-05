package com.qg.www.util;

import javafx.scene.control.Alert;

/**
 * @ClassName VerifyUtil
 * @Description TODO
 * @Author huange7
 * @Date 2020-04-05 10:21
 * @Version 1.0
 */
public class VerifyUtil {
    public static void alertTip(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.titleProperty().set("提醒");
        alert.headerTextProperty().set(message);
        alert.showAndWait();
    }

    public static boolean verifyArgs(String args){
        if ("".equals(args)){
            return false;
        }
        try {
            int number = Integer.valueOf(args);
        }catch (NumberFormatException e){
            System.out.println("参数传输错误！");
            return false;
        }
        return true;
    }
}
