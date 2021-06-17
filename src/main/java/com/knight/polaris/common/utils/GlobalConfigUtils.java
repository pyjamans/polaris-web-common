package com.knight.polaris.common.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class GlobalConfigUtils implements EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(GlobalConfigUtils.class);

    private static Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        env = environment;
    }

    public static List<String> getPropertyToList(String key, String separator) {
        String propertyValue = env.getProperty(key);
        if(StringUtils.isBlank(propertyValue)) {
            return Collections.emptyList();
        }
        String[] valueArr = propertyValue.split(separator);
        List<String> valueList = new ArrayList<>();
        for (String value : valueArr) {
            valueList.add(value);
        }
        return valueList;
    }

    public static String getProperty(String key) {
        return env.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return env.getProperty(key, defaultValue);
    }

    public static Integer getIntProperty(String key) {
        String property = env.getProperty(key);
        if(StringUtils.isBlank(property)) {
            return null;
        }
        Integer result = null;
        try {
            result = Integer.parseInt(property);
        }catch (Exception e) {
            logger.error("Property [{}] is not instanceof integer![属性[{}]不是Integer类型!]", key, key);
        }
        return result;
    }

    public static Integer getIntProperty(String key, Integer defaultValue) {
        String property = env.getProperty(key);
        if(StringUtils.isBlank(property)) {
            return defaultValue;
        }
        Integer result;
        try {
            result = Integer.parseInt(property);
        }catch (Exception e) {
            logger.error("Property [{}] is not instanceof integer![属性[{}]不是Integer类型!]", key, key);
            result = defaultValue;
        }
        return result;
    }

    public static boolean getBoolProperty(String key) {
        return Boolean.parseBoolean(env.getProperty(key));
    }
}
