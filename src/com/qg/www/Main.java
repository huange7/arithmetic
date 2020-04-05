package com.qg.www;

import com.qg.www.calculate.Calculate;
import com.qg.www.calculate.Operations;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @ClassName Main
 * @Description 主函数
 * @Author huange7
 * @Date 2020-04-03 10:08
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) {
        Operations operations = new Operations();
        for (int i = 0; i <100; i++){
            String operation = operations.generateOperations();
            System.out.println(operation);
        }
    }

}
