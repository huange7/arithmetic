package com.qg.www.service;

/**
 * @InterfaceName Service
 * @Description TODO
 * @Author huange7
 * @Date 2020-04-05 10:12
 * @Version 1.0
 */
public interface Service {

    // 生成题目
    void generateQuestion(Integer number);

    // 校验题目
    int[] checkQuestion();

    // 下载题目
    void downloadQuestion();

    // 主函数
    void main(String[] args);
}
