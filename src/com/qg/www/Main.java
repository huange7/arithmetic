package com.qg.www;

import com.qg.www.calculate.Calculate;

/**
 * @ClassName Main
 * @Description 主函数
 * @Author huange7
 * @Date 2020-04-03 10:08
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) {
        String expression = "1/2+(4-2)×3+9÷3";
        String expression1 = "2/1×5÷(2+10)";
        String expression2 = "(1+2)+(1×3)×24+22";
        System.out.println(Calculate.getResult(expression));
        System.out.println(Calculate.getResult(expression1));
        System.out.println(Calculate.getResult(expression2));
    }
}
