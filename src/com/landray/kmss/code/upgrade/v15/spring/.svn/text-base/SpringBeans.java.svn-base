package com.landray.kmss.code.upgrade.v15.spring;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.landray.kmss.code.upgrade.utils.XMLReaderUtil;
import com.landray.kmss.code.upgrade.v15.struts.StrutsConfig;

public class SpringBeans {
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(SpringBeans.class);
	
	public static SpringBeans getInstance(File file) throws Exception {
		return (SpringBeans) XMLReaderUtil.getInstance(file, SpringBeans.class);
	}

	private List<SpringBean> beans = new ArrayList<SpringBean>();

	public List<SpringBean> getBeans() {
		return beans;
	}

	public void addBean(SpringBean bean) {
		beans.add(bean);
	}

	public void setBeans(List<SpringBean> beans) {
		this.beans = beans;
	}

	public static void main(String[] args) throws Exception {
		String path = "WebContent/WEB-INF/KmssConfig/dbcenter/flowstat/struts.xml";
		StrutsConfig beans = StrutsConfig.getInstance(new File(path));
		logger.info(beans.toString());
	}
}
