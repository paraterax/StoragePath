package com.paratera.sgri.config;


import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConfigParams {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigParams.class);

    /**
     * 默认构造函数.
     */
    private ConfigParams() {}

    private static Configuration conf;

    static {
        String fileName = "application.properties";
        try {
            conf = new PropertiesConfiguration(fileName);
        } catch (ConfigurationException e) {
            LOGGER.warn("can't load config file, {}", fileName);
            conf = new PropertiesConfiguration();
        }
    }

    public static final String STORAGE_PATH = conf.getString("t_storage_path_tab");

    public static final String STORAGE_SIZE = conf.getString("t_storage_size_tab");

    public static final Long SPACETIME = conf.getLong("spaceTime");

    public static final int TASKTIME = conf.getInt("taskTime");

    public static final String ORDER = conf.getString("order");

    public static final int THREAD_SIZE = conf.getInt("thread.size");

}
