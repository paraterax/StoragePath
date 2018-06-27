package com.paratera.sgri.service;

import java.io.File;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.paratera.sgri.config.ConfigParams;
import com.paratera.sgri.dao.MySqlWriterDao;
import com.paratera.sgri.pojo.PathPojo;
import com.paratera.sgri.util.CLIUtils;


public class TaskWithResult implements Callable<Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskWithResult.class);

    private String day;
    private PathPojo pojo;
    private MySqlWriterDao mySqlWriterDao;

    public TaskWithResult(String day, PathPojo pojo) {
        this.day = day;
        this.pojo = pojo;
        this.mySqlWriterDao = new MySqlWriterDao();
    }


    @Override
    public Long call() throws Exception {
        long startTime = System.currentTimeMillis();
        String realPath = pojo.getReal_path();
        if (StringUtils.isNotBlank(realPath)) {
            String[] allPath = realPath.split(",");
            for (String pa : allPath) {
                File file1 = new File(pa);
                if (file1.exists()) {
                    JSONObject json = new JSONObject();
                    String cmdLine = ConfigParams.ORDER + " " + pa;
                    String data = CLIUtils.exec(cmdLine);
                    LOGGER.info("[{}],执行的命令为:[{}].", day, cmdLine);
                    if (StringUtils.isNotBlank(data) && StringUtils.isNotEmpty(data)) {
                        String[] segs = data.split("\\s+");
                        Long ll = Long.parseLong(segs[0]);
                        json.put("storage_size", ll);
                    } else {
                        json.put("storage_size", 0L);
                    }
                    json.put("project_name", pojo.getProject_name());
                    json.put("path", pa);
                    json.put("sgin", 1);
                    json.put("day", day);
                    mySqlWriterDao.saveData(json);
                } else {
                    LOGGER.warn("文件目录不存在:{}", pa);
                }
            }
        }
        String removePath = pojo.getRemove_path();
        if (StringUtils.isNotBlank(removePath)) {
            String[] allPath = removePath.split(",");
            for (String pa : allPath) {
                File file1 = new File(pa);
                if (file1.exists()) {
                    JSONObject json = new JSONObject();
                    String cmdLine = ConfigParams.ORDER + " " + pa;
                    String data = CLIUtils.exec(cmdLine);
                    LOGGER.info("[{}],执行的命令为:[{}].", day, cmdLine);
                    if (StringUtils.isNotBlank(data) && StringUtils.isNotEmpty(data)) {
                        String[] segs = data.split("\\s+");
                        Long ll = Long.parseLong(segs[0]);
                        json.put("storage_size", ll);
                    } else {
                        json.put("storage_size", 0L);
                    }
                    json.put("project_name", pojo.getProject_name());
                    json.put("path", pa);
                    json.put("sgin", 0);
                    json.put("day", day);
                    mySqlWriterDao.saveData(json);
                } else {
                    LOGGER.warn("文件目录不存在:{}", pa);
                }
            }
        }
        long totalCost = System.currentTimeMillis() - startTime;
        return totalCost;
    }

}
