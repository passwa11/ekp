package com.landray.kmss.sys.portal.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.portal.forms.SysPortalPageDetailForm;
import com.landray.kmss.sys.portal.forms.SysPortalPageForm;
import com.landray.kmss.sys.portal.model.SysPortalGuide;
import com.landray.kmss.sys.portal.model.SysPortalPage;
import com.landray.kmss.sys.portal.model.SysPortalPageDetail;
import com.landray.kmss.sys.portal.service.ISysPortalGuideService;
import com.landray.kmss.sys.portal.service.ISysPortalPageService;
import com.landray.kmss.sys.portal.util.DesignUtil;
import com.landray.kmss.sys.portal.util.DesignUtil.DesignInfo;
import com.landray.kmss.sys.portal.util.PortalUtil;
import com.landray.kmss.util.*;
import com.landray.kmss.web.filter.security.ConverterContext;
import com.landray.kmss.web.filter.security.ConvertorBase64x;
import com.landray.kmss.web.filter.security.IConvertor;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

/**
 * 主文档业务接口实现
 * 
 * @author
 * @version 1.0 2013-07-18
 */
public class SysPortalPageServiceImp extends BaseServiceImp implements
		ISysPortalPageService, ServletContextAware {

	public static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysPortalPageServiceImp.class);

	private ServletContext servletContext;

	@Override
    public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Override
    public void update(IBaseModel modelObj) throws Exception {
		SysPortalPage xpage = (SysPortalPage) modelObj;
		modelModify(xpage);
		super.update(xpage);
		PortalUtil.clearPageCache();
	}

	@Override
    public String add(IBaseModel modelObj) throws Exception {
		SysPortalPage xpage = (SysPortalPage) modelObj;
		xpage.setDocCreateTime(new Date(System.currentTimeMillis()));
		xpage.setDocCreator(UserUtil.getUser());
		modelModify(xpage);
		return super.add(xpage);
	}

	@Override
	public String saveAdd(IBaseModel modelObj) throws Exception {
		SysPortalPage xpage = (SysPortalPage) modelObj;
		xpage.setDocCreateTime(new Date(System.currentTimeMillis()));
		xpage.setDocCreator(UserUtil.getUser());
		return super.add(xpage);
	}

	private void modelModify(SysPortalPage xpage) throws Exception {
		for (int i = 0; i < xpage.getPageDetails().size(); i++) {
			SysPortalPageDetail page = xpage.getPageDetails().get(i);
			try {
				DesignInfo info = DesignUtil.compile(page.getDocContent());
				page.setFdJsp(info.getBody());
				page.setFdMd5(MD5Util.getMD5String(page.getFdJsp()));
				if (info.getHeader() != null) {
					page.setFdHeader(info.getHeader().getHeaderId());
					page.setFdHeaderVars(info.getHeader().getHeaderVars());
					page.setFdLogo(info.getHeader().getLogo());
				}
				if (info.getFooter() != null) {
					page.setFdFooter(info.getFooter().getFooterId());
					page.setFdFooterVars(info.getFooter().getFooterVars());
				}
				if (info.getGuide() != null && StringUtil.isNotNull(info.getGuide().getGuideId())) {
					ISysPortalGuideService sysPortalGuideService = (ISysPortalGuideService) SpringBeanUtil
							.getBean("sysPortalGuideService");
					SysPortalGuide sysPortalGuide = (SysPortalGuide) sysPortalGuideService
							.findByPrimaryKey(info.getGuide().getGuideId());
					page.setFdGuide(sysPortalGuide.getFdId());
					JSONObject cfg = JSONObject.fromObject(sysPortalGuide
							.getCfgJson());
					if (StringUtil.isNotNull(info.getGuide().getGuideCfg())) {
						JSONObject _cfg = JSONObject.fromObject(info.getGuide()
								.getGuideCfg());
						cfg.putAll(_cfg);
					}
					cfg.put("updateTime", new Date().getTime());
					page.setFdGuideCfg(cfg.toString());
				}
				
				/*
				 * 1、匿名门户产生的页面另存到/resource/portal路径中
				 * 2、原因是原路径/sys/portal下的有权限控制
				 * @author 吴进 by 20191202
				 */
				Boolean anonymous = xpage.getFdAnonymous();
				if (anonymous) {
					createFileAnonym(page);
				} else {
					createFile(page);
				}
			} catch (Exception e) {
				logger.error("更新页面设计信息出错", e);
			}
		}
	}

	@Override
    public void delete(IBaseModel modelObj) throws Exception {
		SysPortalPage page = (SysPortalPage) modelObj;
		// 文件路劲安全校验
		if(!checkFiId(page.getFdId())){
			throw new RuntimeException(ResourceUtil.getString(
			          "sysPortalPage.desgin.err.fdId", "kmss-portal"));
		}
		super.delete(page);
		
		/*********** 删除JSP文件，区别普通或匿名 @author 吴进 by 20191202 **************/
		String dir = servletContext.getRealPath("/sys/portal/page/" + page.getFdId());
		try {
			if (FileUtils.getFile(dir).exists()) {
				FileUtils.deleteDirectory(new File(dir));
			} else {
				dir = servletContext.getRealPath("/resource/portal/" + page.getFdId());
				if (FileUtils.getFile(dir).exists()) {
					FileUtils.deleteDirectory(new File(dir));
				}
			}
		} catch (Exception e) {
			logger.error("删除页面jsp报错", e);
		}
		PortalUtil.clearPageCache();
	}

	private void createFile(File file, String content) throws Exception {
		try {
			OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			PrintWriter out = new PrintWriter(fw);
			out.print(content);
			out.close();
			fw.close();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	/**
	 * 
	 * @param path
	 * @return
	 */
	private  Boolean checkFiId(String  fdId){ 
		
		if (fdId.length() != 32  ){
			return false;
		}
		if (fdId.contains(".")){
			return false;
		}
		if( fdId.contains("/")){
			return false;
		}
		return true;
	}
	@Override
	public String createFile(SysPortalPageDetail page) throws Exception {
		// 文件路劲安全校验
		if(!checkFiId(page.getFdId())){
			throw new RuntimeException(ResourceUtil.getString(
			          "sysPortalPage.desgin.err.fdId", "kmss-portal"));
		}
		String path = pageJspPath(page.getSysPortalPage().getFdId(), page.getFdMd5());
		String jsp = servletContext.getRealPath(path);
		File file = new File(jsp);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists()) {
			createFile(file, page.getFdJsp());
		}
		return path;
	}

	@Override
	public boolean existPageFile(String pageId, String md5) throws Exception {
		String path = pageJspPath(pageId, md5);
		String jsp = servletContext.getRealPath(path);
		File file = new File(jsp);
		return file.exists();
	}

	@Override
	public String pageJspPath(String pageId, String md5) throws Exception {
		return "/sys/portal/page/" + pageId + "/" + md5 + ".jsp";
	}
	
	/********* 匿名门户的文件处理 Start @author 吴进 by 20191202 ********************************/
	@Override
	public String createFileAnonym(SysPortalPageDetail page) throws Exception {
		String path = pageJspPathAnonym(page.getSysPortalPage().getFdId(), page.getFdMd5());
		String jsp = servletContext.getRealPath(path);
		File file = new File(jsp);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists()) {
			createFile(file, page.getFdJsp());
		}
		return path;
	}
	
	@Override
	public boolean existPageFileAnonym(String pageId, String md5) throws Exception {
		String path = pageJspPathAnonym(pageId, md5);
		String jsp = servletContext.getRealPath(path);
		File file = new File(jsp);
		return file.exists();
	}
	
	@Override
	public String pageJspPathAnonym(String pageId, String md5) throws Exception {
		return "/resource/portal/" + pageId + "/" + md5 + ".jsp";
	}
	/********* 匿名门户的文件处理 End @author 吴进 by 20191202 ********************************/

	
	private final IConvertor convert = new ConvertorBase64x();
	
	@Override
	public IBaseModel convertFormToModel(IExtendForm form, IBaseModel model,
			RequestContext requestContext) throws Exception {
		SysPortalPageForm pageForm = (SysPortalPageForm)form;
		List list = pageForm.getPageDetails();
		for(int i = 0 ;i < list.size();i++){
			SysPortalPageDetailForm detailForm = (SysPortalPageDetailForm)list.get(i);
			String docContent = detailForm.getDocContent();
			if(StringUtil.isNotNull(docContent) && docContent.startsWith(convert.getPrefix())){
				detailForm.setDocContent(convert.convert(docContent,
						new ConverterContext("docContent", requestContext.getRequest())));
			}
		}
		return super.convertFormToModel(form, model, requestContext);
	}
}
