package com.landray.kmss.third.pda.loader;

import java.io.Serializable;

import org.apache.commons.betwixt.io.BeanReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;

public class PdaSysConfigs implements Serializable {
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(PdaSysConfigs.class);

	private static boolean isInit = false;

	private static KmssCache moduleCache = new KmssCache(PdaSysConfig.class);

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static PdaSysConfigs getInstance() {
		if (!isInit) {
			init();
		}
		return new PdaSysConfigs();
	}

	public static void reload() {
		isInit = false;
		init();
	}

	/**
	 * 初始化
	 * 
	 * @throws Exception
	 */
	private static synchronized void init() {
		if (isInit) {
            return;
        }
		try {
			moduleCache.clear(true);
			String basePath = ConfigLocationsUtil.getWebContentPath();
			String[] files = ConfigLocationsUtil.getConfigLocationArray(
					basePath, "pda.xml", basePath);
			BeanReader xmlReader = new BeanReader();
			xmlReader.registerBeanClass(PdaSysConfigs.class);
			for (int i = 0; i < files.length; i++) {
				try {
					xmlReader.parse(files[i]);
				} catch (Exception e1) {
					logger.error("解析PDA文件:" + files[i] + "时出错,错误信息:" + e1);
				}
			}
		} catch (Exception e2) {
			logger.error("解析PDA配置初始化过程出错,错误信息:" + e2);
		}
		isInit = true;
	}

	public void addModules(PdaSysConfig pdaCfg) throws Exception {
		PdaSysConfig module = (PdaSysConfig) moduleCache.get(pdaCfg
				.getUrlPrefix());
		if (module == null) {
			if (logger.isDebugEnabled()) {
                logger.debug("加载设计资源信息：" + pdaCfg.getUrlPrefix());
            }
			moduleCache.put(pdaCfg.getUrlPrefix(), pdaCfg);
		} else {
			if (logger.isDebugEnabled()) {
                logger.debug("合并设计资源信息：" + pdaCfg.getUrlPrefix());
            }
			module.combine(pdaCfg);
		}
	}

	public PdaSysConfig getModuleCfg(String urlPrefix) {
		return (PdaSysConfig) moduleCache.get(urlPrefix);
	}
}
