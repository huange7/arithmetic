package com.qg.www.calculate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @ClassName Operations
 * @Description 表达式生成类
 * @Author huange7
 * @Date 2020-04-04 23:40
 * @Version 1.0
 */
public class Operations {
    // 记录运算符数量
    private Integer operatorsNumber;

    // 记录括号的位置
    private List<Integer> bracketsPos = new ArrayList<>();

    // 括号最大值
    private static final Integer MAX_BRACKETS = 2;

    // 括号已结束
    private static final Integer USED = -1;

    // 记录已经使用的运算符数量
    private int count = 0;

    // 括号的限制数量
    private int limit = 0;

    // 存储数字
    private LinkedList<String> numberList = new LinkedList<>();

    // 存储符号
    private LinkedList<Character> characters = new LinkedList<>();


    public String generateOperations() {

        StringBuilder stringBuilder = new StringBuilder();

        if (randomSelective()) {
            init(true);
        }else {
            init(false);
        }

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
            stringBuilder.append(" ");
        }

        generateRightKuohao(stringBuilder, true);

        destroy();

        return stringBuilder.toString();
    }

    private void init(Boolean isTrueFraction) {
        // 随机生成操作符数量
        operatorsNumber = new Random().nextInt(3) + 1;

        // 设置括号的上限数量
        limit = operatorsNumber == 3 ? 2 : operatorsNumber == 1 ? 0 : 1;

        // 随机生成数字
        generateNumber(numberList, isTrueFraction);

        // 随机生成操作符
        generateChar(characters);
    }

    private void destroy() {
        // 对数字进行归零
        operatorsNumber = 0;

        // 对括号位置进行置零
        bracketsPos.clear();

        // 对操作符数量进行置零
        count = 0;

        // 对括号上限进行置零
        limit = 0;

        // 对随机数进行置零
        numberList.clear();

        // 对随机操作符进行置零
        characters.clear();
    }

    private void generateNumber(LinkedList<String> numberList, Boolean isTrueFraction) {
        for (int i = 0; i < operatorsNumber + 1; i++) {
            numberList.add(buildNumber(isTrueFraction));
        }
    }

    private String buildNumber(Boolean isTrueFraction) {
        if (isTrueFraction && randomSelective()) {
            // 保证生成大于0
            int left = new Random().nextInt(10) + 1;
            int mother = new Random().nextInt(101) + 1;
            int son = new Random().nextInt(mother);
            return left + "'" + son + "/" + mother;
        } else {
            return String.valueOf(new Random().nextInt(101));
        }
    }

    private void generateChar(LinkedList<Character> characters) {
        String chars = "+-*÷";
        for (int i = 0; i < operatorsNumber; i++) {
            characters.add(chars.charAt(new Random().nextInt(chars.length())));
        }
    }

    private boolean randomSelective() {
        return new Random().nextInt(2) == 1;
    }

    private void generateLeftKuohao(StringBuilder stringBuilder) {
        for (int i = 0; i < limit; i++) {
            if (bracketsPos.size() >= limit) {
                break;
            }
            if (count >= operatorsNumber) {
                break;
            }
            if (count == 0 || (stringBuilder.charAt(stringBuilder.length() - 1) < '0' ||
                    stringBuilder.charAt(stringBuilder.length() - 1) > '9')) {
                if (!bracketsPos.isEmpty() && count - bracketsPos.get(0) > 1) {
                    break;
                }
                if (!bracketsPos.isEmpty() && count == bracketsPos.get(0) && bracketsPos.get(0) == operatorsNumber - 1) {
                    break;
                }
                // 随机算法-true则为加上括号
                if (randomSelective()) {
                    stringBuilder.append('(');
                    bracketsPos.add(count);
                }
            }
        }
    }

    private void generateRightKuohao(StringBuilder stringBuilder, boolean flag) {
        if (bracketsPos.isEmpty()) {
            return;
        }

        if (flag) {
            for (int i = 0; i < bracketsPos.size(); i++) {
                if (!bracketsPos.get(i).equals(USED)) {
                    stringBuilder.append(')');
                }
            }
            if (bracketsPos.size() == MAX_BRACKETS && bracketsPos.get(0).equals(bracketsPos.get(1))) {
                stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
            }
            return;
        }

        if (bracketsPos.size() == MAX_BRACKETS) {
            // 说明此时有两个括号
            if (bracketsPos.get(0).equals(bracketsPos.get(1))) {
                // 说明此时起始位置一样
                if (count - bracketsPos.get(1) == 1) {
                    // 说明此时内括号应该结束
                    stringBuilder.append(')');
                } else if (count - bracketsPos.get(0) == operatorsNumber - 1) {
                    // 说明此时内括号应该结束
                    stringBuilder.append(')');
                    bracketsPos.replaceAll(pos -> USED);
                }
            } else {
                // 说明起始位置不同
                if (count - bracketsPos.get(1) == 1) {
                    stringBuilder.append("))");
                    bracketsPos.replaceAll(pos -> USED);
                }
            }
        } else {
            if (count - bracketsPos.get(0) == operatorsNumber - 1) {
                stringBuilder.append(')');
                bracketsPos.set(0, -1);
                return;
            }
            if (count - bracketsPos.get(0) == 1) {
                if (randomSelective()) {
                    stringBuilder.append(')');
                    bracketsPos.set(0, -1);
                }
            }

        }
    }
}
