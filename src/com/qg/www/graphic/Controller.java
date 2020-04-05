package com.qg.www.graphic;

import com.qg.www.calculate.Operations;
import com.qg.www.model.AnswerResult;
import com.qg.www.service.Service;
import com.qg.www.service.impl.ServiceImpl;
import com.qg.www.util.VerifyUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller {

    @FXML
    private TableColumn<AnswerResult, String> answerByPro;

    @FXML
    private Button download;

    @FXML
    private TextField argField;

    @FXML
    private Button selectAnswer;

    @FXML
    private TextField answerTxt;

    @FXML
    private TableColumn<AnswerResult, String> answerByStu;

    @FXML
    private TextField questionsTxt;

    @FXML
    private Button proofread;

    @FXML
    private TableColumn<AnswerResult, String> operation;

    @FXML
    private Button generate;

    @FXML
    private TableView<AnswerResult> table;

    @FXML
    private Button selectQuestions;

    /**
     * 线程池执行异步任务
     */
    public static ExecutorService executorService = Executors.newFixedThreadPool(1);

    private boolean isHandlering = false;

    /**
     * 存储表格数据
     */
    public static ObservableList<AnswerResult> operationData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        operation.setCellValueFactory(cellData -> cellData.getValue().questionProperty());
        answerByPro.setCellValueFactory(cellData -> cellData.getValue().answerByProProperty());
        answerByStu.setCellValueFactory(cellData -> cellData.getValue().answerByStuProperty());
        table.setItems(operationData);
    }

    @FXML
    void generate(ActionEvent event) {
        if (isHandlering){
            VerifyUtil.alertTip("当前有任务正在执行...");
            return;
        }
        if (!VerifyUtil.verifyArgs(argField.getText())){
            VerifyUtil.alertTip("参数传输错误！");
            return;
        }
        // 异步执行任务
        executorService.execute(() -> {
            isHandlering = true;

            Service service = new ServiceImpl();
            // 对上一次统计的遗留数据进行移除
            operationData.clear();

            // 开始执行计算
            service.generateQuestion(Integer.valueOf(argField.getText()));

            argField.setText("");
            isHandlering = false;
        });
    }

    @FXML
    void selectQuestions(ActionEvent event) {

    }

    @FXML
    void selectAnswer(ActionEvent event) {

    }

    @FXML
    void proofread(ActionEvent event) {

    }

    @FXML
    void download(ActionEvent event) {

    }

}
