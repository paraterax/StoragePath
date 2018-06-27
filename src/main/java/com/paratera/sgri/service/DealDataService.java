package com.paratera.sgri.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paratera.sgri.config.ConfigParams;
import com.paratera.sgri.dao.MySqlWriterDao;
import com.paratera.sgri.util.DayUtils;

@Service
public class DealDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DealDataService.class);

    @Autowired
    private MySqlWriterDao dao;

    @Autowired
    private ParseDateService parseDateService;

    /**
     * 定时器任务
     * 
     * @throws ParseException
     * @throws SQLException
     */
    public void timerTask() throws IOException, ParseException, SQLException {
        dao.createTable();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, ConfigParams.TASKTIME);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 5);
        Date time = calendar.getTime();
        if (time.before(new Date())) {
            time = DayUtils.addDay(time, 1);
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    String day = DayUtils.getDay();
                    LOGGER.info("查询日期为:[{}].", day);
                    parseDateService.dealData(day);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, time, ConfigParams.SPACETIME);
    }
}
