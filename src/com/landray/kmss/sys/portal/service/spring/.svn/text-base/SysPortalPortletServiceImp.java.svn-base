package com.landray.kmss.sys.portal.service.spring;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.cxf.helpers.FileUtils;
import org.jsoup.nodes.Node;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.portal.service.ISysPortalPortletService;
import com.landray.kmss.sys.portal.util.DesignUtil;
import com.landray.kmss.util.IDGenerator;

/**
 * 系统部件业务接口实现
 * 
 * @author
 * @version 1.0 2013-09-23
 */
public class SysPortalPortletServiceImp extends BaseServiceImp implements
		ISysPortalPortletService {

	@Override
	public String genHtml(HttpServletRequest request, String config)
			throws Exception {
		JSONObject jsonConfig = JSONObject.fromObject(config);
		Node node = DesignUtil.createPortlet(jsonConfig,true);
		long ctime = System.currentTimeMillis(); 
        java.text.SimpleDateFormat f=new java.text.SimpleDateFormat("yyyyMMddHHmmss");
		String path = "/sys/portal/designer/page/"+f.format(ctime)+"_"+IDGenerator.generateID()+".jsp";
		StringBuffer content = new StringBuffer();
		content.append("<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\"%>");
		content.append("<%@ include file=\"/sys/ui/jsp/common.jsp\"%>"); 
		content.append("<%@ taglib uri=\"/WEB-INF/KmssConfig/sys/portal/portal.tld\" prefix=\"portal\"%>");
		content.append("\r\n");
		content.append(node.toString());
		String jsp = request.getSession().getServletContext().getRealPath(path);
		File file = new File(jsp);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}else{
			File[] olds = file.getParentFile().listFiles();
			for (int i = 0; i < olds.length; i++) {
				File old = olds[i];
				if(old.isFile()){
					try {
						String str = old.getName();
						str= str.substring(0,str.indexOf("_"));
						if(f.parse(str).getTime() < ctime-60000){
							FileUtils.delete(old);
						}
					} catch (Exception e) {
						FileUtils.delete(old);
					}
				}
			}
		}
		if (!file.exists()) {
			createFile(file, content.toString());
		}
		return path;
	}

	private void createFile(File file, String content) throws Exception {
		try {
			OutputStreamWriter fw = new OutputStreamWriter(
					new FileOutputStream(file), "UTF-8");
			PrintWriter out = new PrintWriter(fw);
			out.print(content);
			out.close();
			fw.close();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
