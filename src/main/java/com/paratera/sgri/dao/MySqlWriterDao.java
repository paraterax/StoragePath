package com.paratera.sgri.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONObject;
import com.paratera.sgri.config.ConfigParams;
import com.paratera.sgri.db.DBTools;
import com.paratera.sgri.util.FileUtils;

@Repository
public class MySqlWriterDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(MySqlWriterDao.class);

    /**
     * 创建表
     * 
     * @throws SQLException
     */
    public void createTable() throws IOException, SQLException {
        Connection connection = DBTools.getInstance().getConnection();
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS " + ConfigParams.STORAGE_SIZE + "(");
        String path = "/sql/create.sql";
        String sql = FileUtils.getConent(path);
        sb.append(sql);
        PreparedStatement stmt = connection.prepareStatement(sb.toString());
        int i = stmt.executeUpdate(sb.toString());
        connection.commit();
        connection.setAutoCommit(autoCommit);
        connection.close();
        if (i < 0) {
            LOGGER.info("数据库表创建失败");
            System.exit(0);
        } else {
            LOGGER.info("创建数据库表 ,tableName:{}", ConfigParams.STORAGE_SIZE);
        }
    }


    /**
     * 将获得数据写入Mysql中
     * 
     * @throws SQLException
     * 
     * @throws IOException
     */
    public void saveData(JSONObject json) throws SQLException {
        Connection connection = DBTools.getInstance().getConnection();
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO " + ConfigParams.STORAGE_SIZE + "(");
        sb.append("project_name, path, sgin, day, storage_size ) values (");
        sb.append("'"+json.get("project_name")+"','" + json.get("path") + "'," + json.get("sgin") + ",'" + json.get("day") + "',"
                + json.get("storage_size") + ");");
        PreparedStatement stmt = connection.prepareStatement(sb.toString());
        int i = stmt.executeUpdate(sb.toString());
        if (i <= 0) {
            LOGGER.error("插入数据失败,SQL:[{}].", sb.toString());
        }
        connection.commit();
        connection.setAutoCommit(autoCommit);
        connection.close();
        if (i <= 0) {
            LOGGER.error("插入数据失败,SQL:[{}].", sb.toString());
        }
    }

}
