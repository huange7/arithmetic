package com.qg.www.calculate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * @description 多种转换方法
 * @author guopei
 * @date 2020-04-04 22:23
 */
public class Transform {

    //辗转相除法求最大公约数
    public static int getMax(Integer numerator, Integer denominator) {

        if(numerator < denominator){
            int tmp = numerator;
            numerator = denominator;
            denominator = tmp;
        }

        int p = numerator % denominator;
        while(p != 0){
            numerator = denominator;
            denominator = p;
            p = numerator % denominator;
        }
        return denominator;
    }

    //将分数最终结果转化成真分数（例如12/5->2'2/5）
    public static String FinalFraction(String fraction) {

        //判断一下结果是否为负
        if (fraction.matches("\\-{1}[0-9]+\\/[0-9]+") || fraction.matches("[0-9]+\\/\\-{1}[0-9]+")
            || fraction.matches("\\-[0-9]+")) {
            return "ERROR";
        }
        if (fraction.matches("[0-9]+\\/[0-9]+")) {
            Integer numerator = Integer.valueOf(fraction.split("\\/")[0]);
            Integer denominator = Integer.valueOf(fraction.split("\\/")[1]);
            if (numerator > denominator) {
                int num = (numerator - numerator % denominator) / denominator;
                return num + "'" + numerator % denominator + "/" + denominator;
            }
        }
        return fraction;
    }

    //先将前缀表达式转成后缀表达式
    public static String change(String expression) {
        //存放数字的链表
        List<String> numList = new ArrayList<>();

        //存放操作符的栈
        Stack<String> symbolStack = new Stack<>();

        //用来记录每一个数字
        StringBuffer num = new StringBuffer();

        //存放运算符优先级
        HashMap<String, Integer> hashmap = new HashMap<>();
        hashmap.put("(", 0);
        hashmap.put("+", 1);
        hashmap.put("-", 1);
        hashmap.put("×", 2);
        hashmap.put("÷", 2);

        //result为要返回的后缀表达式
        StringBuffer result = new StringBuffer();
        for(int i = 0; i < expression.length();) {
            char c = expression.charAt(i);
            if (Character.isDigit(c) || c == '/') {
                //如果是10进制数或者分子分母的分隔符，直接输出
                result.append(c);
                num.append(c);
                i++;
                continue;
            }
            //不是数字或者/则表示一个数字表答结束
            if (num.length() > 0) {
                numList.add(String.valueOf(num));
                //清空内部数据
                num.delete(0, num.length());
            }
            switch (c) {
                case '(': {
                    symbolStack.push(String.valueOf(c));
                    i++;
                    break;
                }
                case ')': {
                    //将栈顶的运算符出栈，直到左括号
                    String out = symbolStack.pop();
                    result.append('|');
                    while (!symbolStack.empty() && !out.equals("(")) {
                        result.append(out.charAt(0) + "|");
                        out = symbolStack.pop();
                    }
                    i++;
                    break;
                }
                // 最后将当前运算符入栈；
                default: {
                    result.append('|');
                    String out;
                    boolean isAdd = false;
                    while (!symbolStack.empty()) {
                        out = symbolStack.peek();
                        //优先级大于栈顶运算符, 栈顶为左括号
                        if (hashmap.get(String.valueOf(c)) > hashmap.get(out)
                                || out.equals("(")) {
                            symbolStack.push(String.valueOf(c));
                            i++;
                            isAdd = true;
                            break;
                        }
                        //栈顶的运算符出栈
                        result.append(symbolStack.pop() + "|");
                    }
                    if (!isAdd) {
                        symbolStack.push(String.valueOf(c));
                        i++;
                        break;
                    }
                }
            }
        }
        if (num.length() > 0) {
            numList.add(String.valueOf(num));
            //清空内部数据
            num.delete(0, num.length());
            result.append("|");
        }
        //最后将栈内的所有元素弹出
        while (!symbolStack.empty()) {
            result.append(symbolStack.pop() + "|");
        }
        return String.valueOf(result);
    }

    //将数字化成最简分数形式
    public static int[] changeToFraction(String num) {

        int[] number = new int[2];
        if (num.matches("\\-{0,1}[0-9]+\\/\\-{0,1}[0-9]+")) {
            //先化成最简
            String[] strings = num.split("\\/");
            num = Calculate.Simplify(Integer.valueOf(strings[0]), Integer.valueOf(strings[1]));
        }
        //提取分子分母
        if (num.matches("\\-{0,1}[0-9]+\\/\\-{0,1}[0-9]+")) {
            String[] strings = num.split("\\/");
            //分子
            number[0] = Integer.valueOf(strings[0]);
            //分母
            number[1] = Integer.valueOf(strings[1]);
        }
        else {
            number[0] = Integer.valueOf(num);
            number[1] = 1;
        }
        return number;
    }

    //将真分数转化成假分数
    public static String TrueToFalse(String fraction) {
        if (fraction.matches("[0-9]+\\'[0-9]+\\/[0-9]+")) {
            String num = fraction.split("\\/")[0];
            Integer denominator = Integer.valueOf(fraction.split("\\/")[1]);
            Integer numerator = Integer.valueOf(num.split("\\'")[0]) * denominator +
                    Integer.valueOf(num.split("\\'")[1]);
            return numerator + "/" + denominator;
        }
        return fraction;
    }
}
