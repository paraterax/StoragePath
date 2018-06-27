package com.paratera.sgri.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.paratera.sgri.config.ConfigParams;
import com.paratera.sgri.pojo.PathPojo;


@Service
public class ParseDateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParseDateService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询数据
     * 
     * @throws IOException
     */
    public void dealData(String day) throws IOException {
        String querySql = "select project_name ,t.real_path, t.remove_path from " + ConfigParams.STORAGE_PATH
                + " t where t.end != 1";
        List<PathPojo> list = jdbcTemplate.query(querySql, new BeanPropertyRowMapper<PathPojo>(PathPojo.class));
        LOGGER.info("查询目录一共有:{}.", list.size() + "条");
        getPathSize(list, day);
    }

    /**
     * 得到存储路径的大小
     * 
     * @param realList
     * @param removeList
     * @param day
     */
    private void getPathSize(List<PathPojo> removeList, String day) {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(ConfigParams.THREAD_SIZE);
        long totalCost = 0;
        int dataSize = removeList.size();
        List<Future<Long>> resultList = new ArrayList<Future<Long>>();
        try {
            for (int j = 0; j < dataSize; j++) {
                Future<Long> future = pool.submit(new TaskWithResult(day, removeList.get(j)));
                resultList.add(future);
            }
            for (Future<Long> future : resultList) {
                totalCost += future.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
        LOGGER.info("执行完所有信息共耗时:{}s,{}m.", totalCost / 1000, totalCost / 1000 / 60);
    }

}
