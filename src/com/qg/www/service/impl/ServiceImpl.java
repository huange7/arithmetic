package com.qg.www.service.impl;

import com.qg.www.calculate.Operations;
import com.qg.www.model.AnswerResult;
import com.qg.www.service.Service;

import static com.qg.www.graphic.Controller.operationData;

/**
 * @ClassName ServiceImpl
 * @Description TODO
 * @Author huange7
 * @Date 2020-04-05 10:13
 * @Version 1.0
 */
public class ServiceImpl implements Service {
    @Override
    public void generateQuestion(Integer number) {
        Operations operations = new Operations();
        for (int i = 0; i < number; i++) {
            AnswerResult answerResult = new AnswerResult();
            answerResult.setQuestion(operations.generateOperations());
            operationData.add(answerResult);
        }
    }
}
