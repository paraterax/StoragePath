package com.paratera.sgri.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;


public class DayUtils {

    private static final String param = "yyyyMMdd";

    private static final SimpleDateFormat sdf = new SimpleDateFormat(param);

    /**
     * 获得今天前一天的时间
     * 
     * @param nowDay
     * @return
     * @throws ParseException
     */
    public static String getDay() throws ParseException {
        Date onedate = new Date();
        String startTime = sdf.format(onedate);
        Calendar ca = Calendar.getInstance();
        Date date = sdf.parse(startTime);
        ca.setTime(date);
        ca.add(Calendar.DAY_OF_MONTH, -1);
        String newDay = sdf.format(ca.getTime());
        return newDay;
    }

    /**
     * 获得开始时间和结束是的天列表
     */
    public static List<String> getDayList(Date startDay, Date endDay) throws ParseException {
        List<String> dayList = new ArrayList<String>();
        Date curr = new Date(startDay.getTime());
        while (curr.getTime() <= endDay.getTime()) {
            dayList.add(DateFormatUtils.format(curr, param));
            curr = DateUtils.addDays(curr, 1);
        }
        return dayList;
    }

    /**
     * 增加或者减少的天数
     */
    public static Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }
}
