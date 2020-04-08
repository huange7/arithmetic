package com.qg.www;

import com.qg.www.calculate.Calculate;
import com.qg.www.calculate.Operations;
import com.qg.www.file.AnswerFile;
import com.qg.www.graphic.ShowGraphic;
import com.qg.www.service.Service;
import com.qg.www.service.impl.ServiceImpl;
import com.qg.www.util.ArgsUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @ClassName Main
 * @Description 主函数
 * @Author huange7
 * @Date 2020-04-03 10:08
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) {
        Service service = new ServiceImpl();
        service.main(args);
    }

}
