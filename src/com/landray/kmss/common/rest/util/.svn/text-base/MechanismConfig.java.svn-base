package com.landray.kmss.common.rest.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.util.ClassUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 机制接口属性描述文件加载类
 *
 * @Author 严明镜
 * @create 2021年01月14日
 */
public class MechanismConfig {

    private static MechanismConfig instance;

    private static Log logger = LogFactory.getLog(MechanismConfig.class);

    private final Map<Class<?>, OneMech> configs = new HashMap<>();

    private static final String FILE_PATH = "/sys/common/rest/mechanism.json";

    private static final String PROPERTY_NAMES_KEY = "propertyNames";

    private static final String ONE_TO_ONE_PROPERTY_KEY = "oneToOneProperty";

    private static final String IGNORE_PROPERTIES_KEY = "ignoreProperties";

    public static MechanismConfig getInstance() {
        if (instance == null) {
            instance = new MechanismConfig();
            instance.loadMechanisms();
        }
        return instance;
    }

    /**
     * 加载机制描述文件
     */
    private synchronized void loadMechanisms() {
        if (configs.isEmpty()) {
            String fullPath = ConfigLocationsUtil.getKmssConfigPath() + FILE_PATH;
            try {
                HashMap<String, Object> map = new ObjectMapper().readValue(ResourceUtils.getFile(fullPath), HashMap.class);
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    loadOneMechanismConfig(entry.getKey(), entry.getValue());
                }
            } catch (FileNotFoundException e) {
                logger.error("无法获取到机制描述文件,FilePath: " + fullPath, e);
            } catch (IOException e) {
                logger.error("无法读取机制描述文件,FilePath: " + fullPath, e);
            }
        }
    }

    /**
     * 重载机制描述文件
     */
    private void reloadMechanisms() {
        configs.clear();
        loadMechanisms();
    }

    /**
     * 单个机制的配置
     *
     * @param mechanismKey
     * @param oneInterface
     */
    private void loadOneMechanismConfig(String mechanismKey, Object oneInterface) {
        if (!(oneInterface instanceof Map)) {
            return;
        }
        for (Map.Entry<String, Map<String, Object>> entry : ((Map<String, Map<String, Object>>) oneInterface).entrySet()) {
            String entryKey = entry.getKey();
            if (PROPERTY_NAMES_KEY.equals(entryKey) || ONE_TO_ONE_PROPERTY_KEY.equals(entryKey) || IGNORE_PROPERTIES_KEY.equals(entryKey)) {
                logger.warn("暂未开启的配置项（无接口的机制）");
                return;
            }
            Class<?> interfaceClass;
            try {
                interfaceClass = ClassUtils.forName(entryKey);
            } catch (ClassNotFoundException e) {
                logger.error("未找到机制对应的接口类:" + entryKey, e);
                continue;
            }
            if (configs.containsKey(interfaceClass)) {
                logger.error("重复配置的机制接口类:" + entryKey);
                continue;
            }
            configs.put(interfaceClass, loadInterfaceConfig(entry, new OneMech(mechanismKey, interfaceClass)));
        }
    }

    /**
     * 单个机制接口下的配置
     *
     * @param entry
     * @param interfaceConfig
     * @return
     */
    private OneMech loadInterfaceConfig(Map.Entry<String, Map<String, Object>> entry, OneMech interfaceConfig) {
        Map<String, Object> entryValue = entry.getValue();
        for (Map.Entry<String, Object> interfaceEntry : entryValue.entrySet()) {
            String interfaceEntryKey = interfaceEntry.getKey();
            Object interfaceEntryValue = interfaceEntry.getValue();
            if (PROPERTY_NAMES_KEY.equals(interfaceEntryKey)) {
                if (interfaceEntryValue instanceof List) {
                    interfaceConfig.setPropertyNames((List<String>) interfaceEntryValue);
                }
            } else if (ONE_TO_ONE_PROPERTY_KEY.equals(interfaceEntryKey)) {
                interfaceConfig.setOneToOneProperty((String) interfaceEntryValue);

            } else if (IGNORE_PROPERTIES_KEY.equals(interfaceEntryKey)) {
                if (interfaceEntryValue instanceof List) {
                    interfaceConfig.setIgnores((List<String>) interfaceEntryValue);
                }
            }
        }
        return interfaceConfig;
    }

    public Map<Class<?>, OneMech> getConfigs() {
        return configs;
    }

    public class OneMech {
        private Class<?> mechInterface;
        private String mechKey;
        private String oneToOneProperty;
        private List<String> propertyNames;
        private List<String> ignores;

        public OneMech(String mechKey, Class<?> mechInterface) {
            this.mechKey = mechKey;
            this.mechInterface = mechInterface;
        }

        public Class<?> getMechInterface() {
            return mechInterface;
        }

        public void setMechInterface(Class<?> mechInterface) {
            this.mechInterface = mechInterface;
        }

        public String getMechKey() {
            return mechKey;
        }

        public void setMechKey(String mechKey) {
            this.mechKey = mechKey;
        }

        public String getOneToOneProperty() {
            return oneToOneProperty;
        }

        public void setOneToOneProperty(String oneToOneProperty) {
            this.oneToOneProperty = oneToOneProperty;
        }

        public List<String> getPropertyNames() {
            return propertyNames;
        }

        public void setPropertyNames(List<String> propertyNames) {
            this.propertyNames = propertyNames;
        }

        public List<String> getIgnores() {
            return ignores;
        }

        public void setIgnores(List<String> ignores) {
            this.ignores = ignores;
        }
    }
}
