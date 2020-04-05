package com.qg.www.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @ClassName AnswerResult
 * @Description TODO
 * @Author huange7
 * @Date 2020-04-05 10:14
 * @Version 1.0
 */
public class AnswerResult {
    // 题目
    private StringProperty question;

    public StringProperty questionProperty(){
        if (question == null){
            question = new SimpleStringProperty(this, "location");
        }
        return question;
    }

    // 题目
    private StringProperty answerByStudent;

    public StringProperty answerByStuProperty(){
        if (answerByStudent == null){
            answerByStudent = new SimpleStringProperty(this, "answerByStudent");
        }
        return answerByStudent;
    }

    // 题目
    private StringProperty answerByProject;

    public StringProperty answerByProProperty(){
        if (answerByProject == null){
            answerByProject = new SimpleStringProperty(this, "answerByProject");
        }
        return answerByProject;
    }

    public void setQuestion(String question) {
        this.questionProperty().set(question);
    }

    public void setAnswerByStudent(String answerByStudent) {
        this.answerByStuProperty().set(answerByStudent);
    }

    public void setAnswerByProject(String answerByProject) {
        this.answerByProProperty().set(answerByProject);
    }
}
