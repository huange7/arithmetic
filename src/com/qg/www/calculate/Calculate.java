package com.qg.www.calculate;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description 对算数表达式进行结算
 * @author guopei
 * @date 2020-04-04 15:12
 */
public class Calculate {

    public static Pattern pattern = Pattern.compile("[0-9]+\\'[0-9]+\\/[0-9]+");

    public static String getResult(String expression) {

        //将所有空格去掉
        expression = expression.replaceAll(" +", "");
        //将表达式中所有的真分数转化成假分数
        Matcher m = pattern.matcher(expression);
        while(m.find())
        {
            expression = expression.replace(m.group(), Transform.TrueToFalse(m.group()));
        }
        //将中缀表达式转换成后缀表达式
        expression = Transform.change(expression);

        //对后缀表达式进行运算

        //存放操作符的栈
        Stack<String> stack = new Stack<>();

        //将后缀表达式进行切割，分成多个字符串,分割之后就是单纯的数字或者运算符
        String[] strings = expression.split("\\|+");

        for (int i = 0; i < strings.length;) {
            if (strings[i].matches("[0-9].*")) {
                stack.push(strings[i]);
                i++;
                continue;
            }
            String num2 = stack.pop();
            String num1 = stack.pop();
            String result = cout(num1, num2, strings[i]);
            if (result.equals("ERROR")) {
                return result;
            }
            stack.push(result);
            i++;
        }
        return Transform.FinalFraction(stack.pop());
    }



    //判断两个数需要进行计算的方法：num1表示数字1，num2表示数字2，temp表示运算符，
    private static String cout(String num1, String num2, String temp) {

        String result;
        //分两种方式运算，一种是整数的运算，一种是分数的运算
        if (num1.matches("\\-{0,1}[0-9]+\\/\\-{0,1}[0-9]+") || num2.matches("\\-{0,1}[0-9]+\\/\\-{0,1}[0-9]+")) {
            //说明是分数，调用分数运算方法
            result = FractionCount(num1, num2, temp);
        }
        else {
            //调用整数运算方法
            result = IntCount(num1, num2, temp);
        }
        return result;
    }

    //num1表示数字1，num2表示数字2，temp表示运算符
    private static String IntCount(String num1, String num2, String temp) {
        switch (temp) {
            case "+":
                return String.valueOf(Integer.valueOf(num1) + Integer.valueOf(num2));
            case "-":
                return String.valueOf(Integer.valueOf(num1) - Integer.valueOf(num2));
            case "×":
                return String.valueOf(Integer.valueOf(num1) * Integer.valueOf(num2));
            case "÷":
                return Simplify(Integer.valueOf(num1), Integer.valueOf(num2));
                default:
        }
        return null;
    }

    //将分数进行化简的式子(numerator为分子，denominator为分母)
    public static String Simplify(Integer numerator, Integer denominator) {

        if (numerator == 0) {
            return "0";
        }
        if (denominator == 0) {
            return "ERROR";
        }
        int p = Transform.getMax(numerator, denominator);
        numerator /= p;
        denominator /= p;
        if (denominator == 1) {
            return String.valueOf(numerator);
        }
        else {
            return (numerator) + "/" + (denominator);
        }
    }



    //分数运算：num1表示数字1，num2表示数字2，temp表示运算符
    private static String FractionCount(String num1, String num2, String temp) {
        //将所有的数字都化成最简分数来进行计算
        int[] first = Transform.changeToFraction(num1);
        int[] second = Transform.changeToFraction(num2);
        int[] result = new int[2];
        //获取两个分母的最小公倍数
        int min = first[1] * second[1] / Transform.getMax(first[1], second[1]);
        switch (temp) {
            case "+":
                //分子
                result[0] = first[0] * min / first[1] + second[0] * min / second[1];
                //分母
                result[1] = min;
                return Simplify(result[0], result[1]);
            case "-":
                //分子
                result[0] = first[0] * min / first[1] - second[0] * min / second[1];
                //分母
                result[1] = min;
                return Simplify(result[0], result[1]);
            case "×":
                //分子
                result[0] = first[0] * second[0];
                //分母
                result[1] = first[1] * second[1];
                return Simplify(result[0], result[1]);
            case "÷":
                //分子
                result[0] = first[0] * second[1];
                //分母
                result[1] = first[1] * second[0];
                return Simplify(result[0], result[1]);
        }
        return null;
    }


}
