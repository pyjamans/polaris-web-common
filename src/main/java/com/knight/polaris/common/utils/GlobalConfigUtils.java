package com.knight.polaris.common.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GlobalConfigUtils implements EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(GlobalConfigUtils.class);

    private static final String DEFAULT_KEY_VALUE_SEPARATOR = ":";

    private static final String DEFAULT_LIST_SEPARATOR = ";";

    private static Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        env = environment;
    }

    public static List<String> getPropertiesToList(String key) {
        return getPropertiesToList(key, DEFAULT_LIST_SEPARATOR);
    }

    public static List<String> getPropertiesToList(String key, String separator) {
        String propertyValue = env.getProperty(key);
        if(StringUtils.isNotBlank(propertyValue)) {
           try {
               String[] valueArr = propertyValue.split(separator);
               List<String> valueList = new ArrayList<>();
               for (String value : valueArr) {
                   valueList.add(value);
               }
               return valueList;
           }catch (Exception e) {
               logger.error("Property value print =>[{}]", propertyValue);
               logger.error("Parse properties to list fail![属性格式化为List失败!]", e);
           }
        }
        return Collections.emptyList();
    }

    public static String getProperty(String key) {
        return env.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return env.getProperty(key, defaultValue);
    }

    public static Integer getIntProperty(String key) {
        String property = env.getProperty(key);
        if(StringUtils.isNotBlank(property)) {
            try {
                return Integer.parseInt(property);
            }catch (Exception e) {
                logger.error("Property [{}] is not instanceof integer![属性[{}]不是Integer类型!]", key, key);
            }
        }
        return null;
    }

    public static Integer getIntProperty(String key, Integer defaultValue) {
        String property = env.getProperty(key);
        if(StringUtils.isNotBlank(property)) {
            try {
                return Integer.parseInt(property);
            }catch (Exception e) {
                logger.error("Property [{}] is not instanceof integer![属性[{}]不是Integer类型!]", key, key);
            }
        }
        return defaultValue;
    }

    public static boolean getBoolProperty(String key) {
        return Boolean.parseBoolean(env.getProperty(key));
    }

    public static Map<String, String> getPropertiesToMap(String key) {
        return getPropertiesToMap(key, DEFAULT_LIST_SEPARATOR, DEFAULT_KEY_VALUE_SEPARATOR);
    }

    public static Map<String, String> getPropertiesToMap(String key, String eleSep) {
        return getPropertiesToMap(key, eleSep, DEFAULT_KEY_VALUE_SEPARATOR);
    }

    public static Map<String, String> getPropertiesToMap(String key, String eleSep, String keyValSep) {
        String propertyValue = env.getProperty(key);
        if(StringUtils.isNotBlank(propertyValue)) {
            try {
                String[] keyValues = propertyValue.split(eleSep);
                Map<String, String> propertiesMap = new HashMap<>();
                for(String keyVal : keyValues) {
                    int sepIdx = keyVal.indexOf(keyValSep);
                    if(sepIdx > 0) {
                        propertiesMap.put(keyVal.substring(0, sepIdx), keyVal.substring(sepIdx + 1));
                    }else {
                        logger.warn("Error property string print =>[{}]", keyVal);
                        logger.warn("Current property can not find keyValue separator! Continue![当前属性没有找到keyValue分隔符，已跳过!]");
                    }
                }
                return propertiesMap;
            }catch (Exception e) {
                logger.error("Property value print =>[{}]", propertyValue);
                logger.error("Parse properties to map fail![属性格式化为Map失败!]", e);
            }
        }
        return Collections.emptyMap();

    }
}
