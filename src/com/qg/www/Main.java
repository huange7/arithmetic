package com.qg.www;

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
    // 记录运算符数量
    static int operatorsNunber;

    // 记录括号的位置
    static List<Integer> bracketsPos = new ArrayList<>();

    // 记录已经使用的运算符数量
    static int count = 0;

    // 括号的限制数量
    static int limit = 0;

    public static void main(String[] args) {

        for (int i = 0; i <100; i++){
            generateOperations();
            destroy();
        }

    }

    public static void generateOperations(){
        operatorsNunber = new Random().nextInt(3) + 1;
        limit = operatorsNunber == 3 ? 2 : operatorsNunber == 1 ? 0 : 1;
        LinkedList<Integer> numberList = new LinkedList<>();
        LinkedList<Character> characters = new LinkedList<>();
        generateNumber(numberList);
        generateChar(characters);

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; ; i++) {
            if (i % 2 == 0) {
                generateLeftKuohao(stringBuilder);
                stringBuilder.append(numberList.poll());

            } else {
                generateRightKuohao(stringBuilder, false);
                stringBuilder.append(characters.poll());
                count++;
            }
            if (numberList.isEmpty()) {
                break;
            }
        }

        generateRightKuohao(stringBuilder, true);

        System.out.println(stringBuilder.toString());
    }

    public static void destroy(){
        operatorsNunber = 0;
        bracketsPos.clear();
        count = 0;
        limit = 0;
    }

    public static void generateNumber(LinkedList<Integer> numberList) {
        for (int i = 0; i < operatorsNunber + 1; i++) {
            numberList.add(new Random().nextInt(101));
        }
    }

    public static void generateChar(LinkedList<Character> characters) {
        String chars = "+-*÷";
        for (int i = 0; i < operatorsNunber; i++) {
            characters.add(chars.charAt(new Random().nextInt(chars.length())));
        }
    }

    public static void generateLeftKuohao(StringBuilder stringBuilder) {
        for (int i = 0; i < limit; i++) {
            if (bracketsPos.size() >= 2) {
                break;
            }
            if (count >= operatorsNunber){
                break;
            }
            if (count == 0 || (stringBuilder.charAt(stringBuilder.length() - 1) < '0' ||
                    stringBuilder.charAt(stringBuilder.length() - 1) > '9')) {
                if (!bracketsPos.isEmpty() && count - bracketsPos.get(0) > 1) {
                    break;
                }
                if (new Random().nextInt(2) == 1) {
                    stringBuilder.append('(');
                    bracketsPos.add(count);
                }
            }
        }
    }

    public static void generateRightKuohao(StringBuilder stringBuilder, boolean flag) {
        if (bracketsPos.isEmpty()) {
            return;
        }
        for (int i = 0; i < bracketsPos.size(); i++) {

            int posNumber = bracketsPos.get(i);

            if (posNumber == -1) {
                continue;
            }

            if (flag) {
                stringBuilder.append(')');
                bracketsPos.set(i, -1);
                continue;
            }

            if (bracketsPos.size() == 2) {
                if (count - bracketsPos.get(1) == 1) {
                    stringBuilder.append(')');
                    bracketsPos.set(1, -1);
                }
            }

            if (count - posNumber == 1) {
                if (new Random().nextInt(2) == 1) {
                    stringBuilder.append(')');
                    bracketsPos.set(i, -1);
                }
            } else if (count - posNumber == operatorsNunber - 1) {
                stringBuilder.append(')');
                bracketsPos.set(i, -1);
            }
        }
    }

}
