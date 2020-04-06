package com.qg.www.service.impl;

import com.qg.www.calculate.Calculate;
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
        while (number > 0) {
            AnswerResult answerResult = new AnswerResult();
            String operation = operations.generateOperations();
            String resultString = Calculate.getResult(operation).split("'")[0];
            if ("ERROR".equals(resultString)){
                continue;
            }
            Double result = Double.valueOf(resultString);
            if (result > 100 || result < 0){
                continue;
            }
            answerResult.setQuestion(operation);
            answerResult.setAnswerByProject(result.toString());
            number--;
        }
    }

    @Override
    public void checkQuestion() {

    }

    @Override
    public void downloadQuestion() {

    }
}
