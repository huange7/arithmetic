package com.qg.www.calculate;

import com.qg.www.service.impl.ServiceImpl;
import com.qg.www.util.ArgsUtil;
import com.sun.org.apache.xpath.internal.Arg;

import java.util.*;

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
    private LinkedList<String> numberList;

    // 存储符号
    private LinkedList<Character> characters;


    public String generateOperations() {

        StringBuffer stringBuilder = new StringBuffer();

        if (randomSelective()) {
            init(true);
        }else {
            init(false);
        }

        Iterator<String> iteratorNumber = numberList.iterator();
        Iterator<Character> iteratorChar = characters.iterator();

        for (int i = 0; ; i++) {
            if (i % 2 == 0) {
                generateLeftBracket(stringBuilder);
                stringBuilder.append(iteratorNumber.next());
            } else {
                generateRightBracket(stringBuilder, false);
                stringBuilder.append(iteratorChar.next());
                count++;
            }
            if (!iteratorNumber.hasNext()) {
                break;
            }
            stringBuilder.append(" ");
        }

        generateRightBracket(stringBuilder, true);

        destroy();

        return stringBuilder.toString();
    }

    private void init(Boolean isTrueFraction) {
        // 随机生成操作符数量
        operatorsNumber = new Random().nextInt(3) + 1;

        // 设置括号的上限数量
        limit = operatorsNumber == 3 ? 2 : operatorsNumber == 1 ? 0 : 1;

        // 随机生成数字
        generateNumber(isTrueFraction);

        // 随机生成操作符
        generateChar();

        ServiceImpl.numberList.add(numberList);

        ServiceImpl.charList.add(characters);
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
    }

    // 随机生成数字（整数或真分数）
    private void generateNumber(Boolean isTrueFraction) {
        numberList = new LinkedList<>();
        for (int i = 0; i < operatorsNumber + 1; i++) {
            numberList.add(buildNumber(isTrueFraction));
        }
    }

    private String buildNumber(Boolean isTrueFraction) {
        if (isTrueFraction && randomSelective()) {
            // 保证生成大于0
            int left;
            do {
                left = new Random().nextInt(ArgsUtil.numberBound);
            }while (left <= 0);
            // 控制分母在10以内
            int mother;
            do{
                mother = new Random().nextInt(ArgsUtil.numberBound < 11 ? ArgsUtil.numberBound : 11);
            }while (mother <= 0);
            int son;
            // 保证生成最简分数
            do {
                son = new Random().nextInt(mother) + 1;
            }while (!isSimplest(son, mother));
            return left + "'" + son + "/" + mother;
        } else {
            return String.valueOf(new Random().nextInt(ArgsUtil.numberBound));
        }
    }

    // 求出最简分数
    private boolean isSimplest(int son, int mother){
        int tempMo = mother, tempSon = son;
        int r = tempMo % tempSon;
        while ( r > 0){
            tempMo = tempSon;
            tempSon = r;
            r = tempMo % tempSon;
        }
        return tempSon == 1;
    }

    // 随机生成运算符
    private void generateChar() {
        characters = new LinkedList<>();
        String chars = "+-×÷";
        for (int i = 0; i < operatorsNumber; i++) {
            characters.add(chars.charAt(new Random().nextInt(chars.length())));
        }
    }

    // 随机选择算法
    private boolean randomSelective() {
        return new Random().nextInt(2) == 1;
    }

    // 生成左括号
    private void generateLeftBracket(StringBuffer stringBuilder) {
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

    // 生成右括号
    // flag 表示是否为表达式结尾
    private void generateRightBracket(StringBuffer stringBuilder, boolean flag) {
        if (bracketsPos.isEmpty()) {
            return;
        }

        if (flag) {
            // 如果已经到达结尾位置，进行剩余括号的闭括号操作
            for (int i = 0; i < bracketsPos.size(); i++) {
                if (!bracketsPos.get(i).equals(USED)) {
                    stringBuilder.append(')');
                }
            }
            // 可能发生多加一个括号的情况，将其进行剔除
            if (bracketsPos.size() == MAX_BRACKETS && bracketsPos.get(0).equals(bracketsPos.get(1)) && bracketsPos.get(0) != -1) {
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
