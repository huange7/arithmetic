package com.qg.www.graphic;

import com.qg.www.model.AnswerResult;
import com.qg.www.service.Service;
import com.qg.www.service.impl.ServiceImpl;
import com.qg.www.util.ArgsUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
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

    @FXML
    private TextField right;

    @FXML
    private TextField wrong;

    /**
     * 线程池执行异步任务
     */
    public static ExecutorService executorService = Executors.newFixedThreadPool(1);

    private volatile boolean isHandlering = false;

    /**
     * 存储表格数据
     */
    public static ObservableList<AnswerResult> operationData = FXCollections.observableArrayList();

    private Service service = new ServiceImpl();

    @FXML
    private void initialize() {
        operation.setCellValueFactory(cellData -> cellData.getValue().questionProperty());
        answerByPro.setCellValueFactory(cellData -> cellData.getValue().answerByProProperty());
        answerByStu.setCellValueFactory(cellData -> cellData.getValue().answerByStuProperty());
        table.setItems(operationData);
    }

    @FXML
    void generate(ActionEvent event) {
        if (isHandlering) {
            ArgsUtil.alertTip("当前有任务正在执行...");
            return;
        }

        if (!ArgsUtil.verifyArgsX(argField.getText())) {
            ArgsUtil.alertTip("参数传输错误！");
            return;
        }
        // 异步执行任务
        isHandlering = true;
        executorService.execute(() -> {
            // 对上一次统计的遗留数据进行移除
            operationData.clear();

            // 开始执行计算
            service.generateQuestion(ArgsUtil.questionNumber);

            argField.setText("");
            isHandlering = false;
        });
    }

    @FXML
    void selectQuestions(ActionEvent event) {
        File file = chooseTxt();
        if (file == null){
            return;
        }
        ArgsUtil.questionPath = file.getAbsolutePath();
        questionsTxt.setText(file.getName());
    }

    @FXML
    void selectAnswer(ActionEvent event) {
        File file = chooseTxt();
        if (file == null){
            return;
        }
        ArgsUtil.answerPath = file.getAbsolutePath();
        answerTxt.setText(file.getName());
    }

    private File chooseTxt() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser.showOpenDialog(stage);
    }

    @FXML
    void proofread(ActionEvent event) {
        right.setText("");
        wrong.setText("");
        ArgsUtil.isX = true;
        if (isHandlering){
            ArgsUtil.alertTip("当前有任务正在执行...");
            return;
        }
        if (ArgsUtil.answerPath == null || ArgsUtil.questionPath == null){
            ArgsUtil.alertTip("暂未选择文件！");
            return;
        }
        isHandlering = true;
        executorService.execute(()->{
            int[] results = service.checkQuestion();
            if (results != null) {
                right.setText(String.valueOf(results[0]));
                wrong.setText(String.valueOf(results[1]));
            }
            isHandlering = false;
        });
    }

    @FXML
    void download(ActionEvent event) {
        if (operationData.size() <= 0){
            ArgsUtil.alertTip("请先生成题目！");
            return;
        }
        service.downloadQuestion();
    }

}
