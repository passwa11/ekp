package com.landray.kmss.sys.portal.quartz;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.springframework.web.context.ServletContextAware;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.portal.model.SysPortalPage;
import com.landray.kmss.sys.portal.model.SysPortalPageDetail;
import com.landray.kmss.sys.portal.service.ISysPortalPageService;
import com.landray.kmss.util.SpringBeanUtil;

public class SysPortalJspService implements ServletContextAware {
	public static String pageFolder = "/sys/portal/page";
	private ServletContext servletContext;

	@Override
    public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public void rebuildJspFile() {
		try {
			String dir = servletContext.getRealPath(pageFolder);
			File file = new File(dir);
			if (file.exists()) {
				FileUtils.cleanDirectory(file);
			}
			ISysPortalPageService service = (ISysPortalPageService) SpringBeanUtil
					.getBean("sysPortalPageService");
			List<?> list = service.findList(new HQLInfo());
			for (int i = 0; i < list.size(); i++) {
				SysPortalPage xpage = (SysPortalPage) list.get(i);
				for (int j = 0; j < xpage.getPageDetails().size(); j++) {
					SysPortalPageDetail page = xpage.getPageDetails().get(j);
					service.createFile(page);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
