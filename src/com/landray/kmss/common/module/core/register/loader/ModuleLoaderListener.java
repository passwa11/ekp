package com.landray.kmss.common.module.core.register.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 启动时检查模块依赖关系，缺少强依赖模块时不启动，缺少弱依赖模块时输出提示信息
 *
 * @author 严明镜
 * @version 1.0 2021年02月23日
 */
public class ModuleLoaderListener implements ServletContextListener {

	private static final Logger log = LoggerFactory.getLogger(ModuleLoaderListener.class);

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		log.info("开始读取模块依赖关系");
		ModuleDictFactory.init();
		log.info("开始检查模块依赖关系");
		ModuleDictFactory.check();
		log.info("模块依赖关系检查完毕");
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
	}
}
