package com.landray.kmss.sys.ui.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;

import com.landray.kmss.sys.portal.util.PortalUtil;
import com.landray.kmss.sys.portal.xml.model.SysPortalFooter;
import com.landray.kmss.sys.portal.xml.model.SysPortalHeader;
import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.sys.ui.xml.model.SysUiLayout;
import com.landray.kmss.sys.ui.xml.model.SysUiRender;
import com.landray.kmss.sys.ui.xml.model.SysUiTemplate;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.StringUtil;

import javax.servlet.http.HttpServletRequest;

public class ComponentUtil {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ComponentUtil.class);

	/**
	 * 获取所有页面模板
	 */
	public static List<SysUiTemplate> getAllTemplate(HttpServletRequest request) {
		String keyword = request.getParameter("q.keyword");//关键字
		String fdSource = request.getParameter("q.fdSource"); //来源
		String fdAnonymous = request.getParameter("q.fdAnonymous"); //匿名
		List<SysUiTemplate> templates = new ArrayList<SysUiTemplate>();
		List<SysUiTemplate> list = new ArrayList<SysUiTemplate>(
				SysUiPluginUtil.getTemplates().values());
		for (int i = 0; i < list.size(); i++) {
			SysUiTemplate template = list.get(i);
			template.setUiType("template");
			// 系统
			if (("portal".equals(template.getFdFor()) ||"anonymous".equals(template.getFdFor()))
					&& StringUtil.isNull(template.getPath())) {
				templates.add(template);
			} // 扩展
			if (("portal".equals(template.getFdFor()) ||"anonymous".equals(template.getFdFor()))
					&& StringUtil.isNotNull(template.getPath())) {
				templates.add(template);
			}
		}
		//关键字筛选
		if(StringUtil.isNotNull(keyword)){
			List<SysUiTemplate> keyList = new ArrayList<>();
			for (int i = 0; i < templates.size(); i++) {
				String fdName = templates.get(i).getFdName();
				String fdId = templates.get(i).getFdId();
				fdName = fdName.toLowerCase();
				fdId = fdId.toLowerCase();
				if (fdName.indexOf(keyword.toLowerCase()) != -1 || fdId.indexOf(keyword.toLowerCase()) !=-1) {
					keyList.add(templates.get(i));
				}
			}
			templates = keyList;
		}
		//来源筛选
		if(StringUtil.isNotNull(fdSource)){
			List<SysUiTemplate> keyList = new ArrayList<>();
			for (int i = 0; i < templates.size(); i++) {
				String path = templates.get(i).getPath();
				if("true".equals(fdSource) && StringUtil.isNull(path)){ //内置
					keyList.add(templates.get(i));
				}else if("false".equals(fdSource) && StringUtil.isNotNull(path)){ //自定义
					keyList.add(templates.get(i));
				}
			}
			templates = keyList;
		}
		//是否匿名
		if(StringUtil.isNotNull(fdAnonymous)){
			List<SysUiTemplate> keyList = new ArrayList<>();
			for (int i = 0; i < templates.size(); i++) {
				String fdFor = templates.get(i).getFdFor();
				if("true".equals(fdAnonymous) && "anonymous".equals(fdFor)){ //匿名
					keyList.add(templates.get(i));
				}else if("false".equals(fdAnonymous) && !"anonymous".equals(fdFor)){ //普通
					keyList.add(templates.get(i));
				}
			}
			templates = keyList;
		}
		return templates;
	}

	/**
	 * 获取所有呈现
	 * @param request
	 */
	public static List<SysUiRender> getAllRender(HttpServletRequest request) {
		String keyword = request.getParameter("q.keyword");//关键字
		String fdSource = request.getParameter("q.fdSource"); //来源
		String[] formates = request.getParameterValues("q.formate"); //数据来源

		List<SysUiRender> renders = new ArrayList<SysUiRender>();
		List<SysUiRender> list = new ArrayList<SysUiRender>(SysUiPluginUtil.getRenders().values());
		for (int i = 0; i < list.size(); i++) {
			SysUiRender render = list.get(i);
			if((StringUtil.isNull(render.getFdFor()) || "portal".equals(render.getFdFor())) && StringUtil.isNotNull(render.getFdName())){
				render.setUiType("render");
				renders.add(render);
			}
		}
		//关键字筛选
		if(StringUtil.isNotNull(keyword)){
			List<SysUiRender> keyList = new ArrayList<>();
			for (int i = 0; i < renders.size(); i++) {
				String fdName = renders.get(i).getFdName();
				String fdId = renders.get(i).getFdId();
				fdName = fdName.toLowerCase();
				fdId = fdId.toLowerCase();
				if (fdName.indexOf(keyword.toLowerCase()) != -1 || fdId.indexOf(keyword.toLowerCase()) !=-1) {
					keyList.add(renders.get(i));
				}
			}
			renders = keyList;
		}
		//来源筛选
		if(StringUtil.isNotNull(fdSource)){
			List<SysUiRender> keyList = new ArrayList<>();
			for (int i = 0; i < renders.size(); i++) {
				String path = renders.get(i).getPath();
				if("true".equals(fdSource) && StringUtil.isNull(path)){ //内置
					keyList.add(renders.get(i));
				}else if("false".equals(fdSource) && StringUtil.isNotNull(path)){ //自定义
					keyList.add(renders.get(i));
				}
			}
			renders = keyList;
		}
		//数据格式
		if (formates != null) {
			List<SysUiRender> keyList = new ArrayList<>();
			for (int i = 0; i < renders.size(); i++) {
				String fdFormat = renders.get(i).getFdFormat();
				if(Arrays.asList(formates).contains(fdFormat)){ //如果选择的数据格式和呈现的数据格式匹配
					keyList.add(renders.get(i));
				}
			}
			renders = keyList;
		}
		return renders;
	}



	/**
	 * 获取所有外观
	 *
	 */
	public static List<SysUiLayout> getAllLayout(HttpServletRequest request) {
		String keyword = request.getParameter("q.keyword");//关键字
		String fdSource = request.getParameter("q.fdSource"); //来源
		List<SysUiLayout> layouts = new ArrayList<SysUiLayout>();
		List<SysUiLayout> list = new ArrayList<SysUiLayout>(
				SysUiPluginUtil.getLayouts().values());
		for (int i = 0; i < list.size(); i++) {
			SysUiLayout layout = list.get(i);
			String[] temps = layout.getFdFor().split(";");
			List<String> tempList = ArrayUtil.asList(temps);
			if (tempList.contains("portal")) {
					layout.setUiType("layout");
					layouts.add(layout);
			}
		}
		//关键字筛选
		if(StringUtil.isNotNull(keyword)){
			List<SysUiLayout> keyList = new ArrayList<>();
			for (int i = 0; i < layouts.size(); i++) {
				String fdName = layouts.get(i).getFdName();
				String fdId = layouts.get(i).getFdId();
				fdName = fdName.toLowerCase();
				fdId = fdId.toLowerCase();
				if (fdName.indexOf(keyword.toLowerCase()) != -1 || fdId.indexOf(keyword.toLowerCase()) !=-1) {
					keyList.add(layouts.get(i));
				}
			}
			layouts = keyList;
		}
		//来源筛选
		if(StringUtil.isNotNull(fdSource)){
			List<SysUiLayout> keyList = new ArrayList<>();
			for (int i = 0; i < layouts.size(); i++) {
				String path = layouts.get(i).getPath();
				if("true".equals(fdSource) && StringUtil.isNull(path)){ //内置
					keyList.add(layouts.get(i));
				}else if("false".equals(fdSource) && StringUtil.isNotNull(path)){ //自定义
					keyList.add(layouts.get(i));
				}
			}
			layouts = keyList;
		}
		return layouts;
	}

	/**
	 * 获取所有页眉
	 */
	public static List<SysPortalHeader> getAllHeader(HttpServletRequest request) {
		String keyword = request.getParameter("q.keyword");//关键字
		String fdSource = request.getParameter("q.fdSource"); //来源
		String fdAnonymous = request.getParameter("q.fdAnonymous"); //匿名
		List<SysPortalHeader> headers = new ArrayList<SysPortalHeader>();
		List<SysPortalHeader> list = new ArrayList<SysPortalHeader>(PortalUtil
				.getPortalHeaders().values());
		for (int i = 0; i < list.size(); i++) {
			SysPortalHeader header = list.get(i);
			if (!"abandon".equals(header.getFdFor())) {
					header.setUiType("header");
					headers.add(header);
			}
		}
		//关键字筛选
		if(StringUtil.isNotNull(keyword)){
			List<SysPortalHeader> keyList = new ArrayList<>();
			for (int i = 0; i < headers.size(); i++) {
				String fdName = headers.get(i).getFdName();
				String fdId = headers.get(i).getFdId();
				fdName = fdName.toLowerCase();
				fdId = fdId.toLowerCase();
				if (fdName.indexOf(keyword.toLowerCase()) != -1 || fdId.indexOf(keyword.toLowerCase()) !=-1) {
					keyList.add(headers.get(i));
				}
			}
			headers = keyList;
		}
		//来源筛选
		if(StringUtil.isNotNull(fdSource)){
			List<SysPortalHeader> keyList = new ArrayList<>();
			for (int i = 0; i < headers.size(); i++) {
				String path = headers.get(i).getPath();
				if("true".equals(fdSource) && StringUtil.isNull(path)){ //内置
					keyList.add(headers.get(i));
				}else if("false".equals(fdSource) && StringUtil.isNotNull(path)){ //自定义
					keyList.add(headers.get(i));
				}
			}
			headers = keyList;
		}
		//是否匿名
		if(StringUtil.isNotNull(fdAnonymous)){
			List<SysPortalHeader> keyList = new ArrayList<>();
			for (int i = 0; i < headers.size(); i++) {
				String fdFor = headers.get(i).getFdFor();
				if("true".equals(fdAnonymous) && "anonymous".equals(fdFor)){ //匿名
					keyList.add(headers.get(i));
				}else if("false".equals(fdAnonymous) && !"anonymous".equals(fdFor)){ //普通
					keyList.add(headers.get(i));
				}
			}
			headers = keyList;
		}
		return headers;
	}

	/**
	 * 获取所有页脚
	 *
	 */
	public static List<SysPortalFooter> getAllFooter(HttpServletRequest request) {
		String keyword = request.getParameter("q.keyword");//关键字
		String fdSource = request.getParameter("q.fdSource"); //来源
		String fdAnonymous = request.getParameter("q.fdAnonymous"); //匿名
		List<SysPortalFooter> footers = new ArrayList<SysPortalFooter>();
		List<SysPortalFooter> list = new ArrayList<SysPortalFooter>(PortalUtil
				.getPortalFooters().values());
		for (int i = 0; i < list.size(); i++) {
			SysPortalFooter footer = list.get(i);
			if (!"abandon".equals(footer.getFdFor())) {
					footer.setUiType("footer");
					footers.add(footer);
			}
		}
		//关键字筛选
		if(StringUtil.isNotNull(keyword)){
			List<SysPortalFooter> keyList = new ArrayList<>();
			for (int i = 0; i < footers.size(); i++) {
				String fdName = footers.get(i).getFdName();
				String fdId = footers.get(i).getFdId();
				fdName = fdName.toLowerCase();
				fdId = fdId.toLowerCase();
				if (fdName.indexOf(keyword.toLowerCase()) != -1 || fdId.indexOf(keyword.toLowerCase()) !=-1) {
					keyList.add(footers.get(i));
				}
			}
			footers = keyList;
		}
		//来源筛选
		if(StringUtil.isNotNull(fdSource)){
			List<SysPortalFooter> keyList = new ArrayList<>();
			for (int i = 0; i < footers.size(); i++) {
				String path = footers.get(i).getPath();
				if("true".equals(fdSource) && StringUtil.isNull(path)){ //内置
					keyList.add(footers.get(i));
				}else if("false".equals(fdSource) && StringUtil.isNotNull(path)){ //自定义
					keyList.add(footers.get(i));
				}
			}
			footers = keyList;
		}
		//是否匿名
		if(StringUtil.isNotNull(fdAnonymous)){
			List<SysPortalFooter> keyList = new ArrayList<>();
			for (int i = 0; i < footers.size(); i++) {
				String fdFor = footers.get(i).getFdFor();
				if("true".equals(fdAnonymous) && "anonymous".equals(fdFor)){ //匿名
					keyList.add(footers.get(i));
				}else if("false".equals(fdAnonymous) && !"anonymous".equals(fdFor)){ //普通
					keyList.add(footers.get(i));
				}
			}
			footers = keyList;
		}
		return footers;
	}

}