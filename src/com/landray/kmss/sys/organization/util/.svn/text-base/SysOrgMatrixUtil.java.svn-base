package com.landray.kmss.sys.organization.util;

import java.io.File;
import java.io.FileInputStream;

import com.landray.kmss.code.upgrade.utils.StringUtil;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.lbpm.engine.support.def.XMLUtils;
import com.landray.kmss.sys.organization.model.SysOrgMatrix;

import javax.servlet.http.HttpServletRequest;

/**
 * 读取内置矩阵关系数据信息
 * 从sys/organization/sys-init-matrix.xml读取信息
 * @author hongjian
 *
 */
public class SysOrgMatrixUtil {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysOrgMatrixUtil.class);
	
	
	/**
	 * 获取当前节点子节点值
	 * @param node 当前节点
	 * @param childNodeName 当前节点的子节点名称
	 * @return
	 */
	public static Node getNodeByNodeName(Node node,String childNodeName) {
		
		NodeList nodeItem=node.getChildNodes();
		for(int i=0;i<nodeItem.getLength();i++) {
			Node nodeChildItem=nodeItem.item(i);
			if(nodeChildItem.getNodeName().equals(childNodeName)) {
				return nodeChildItem;
			}
		}
		
		return null;
	}
	
	/**
	 * 从配置文件里面获取矩阵基础信息ID
	 * @return Node  获取 sysOrgMatrix节点信息
	 * @throws Exception
	 */
	public static Node getSysOrgMatrixNode() throws Exception{
			
		 	String root = ConfigLocationsUtil.getWebContentPath();
			String nodePath = root+ "/WEB-INF/KmssConfig/sys/organization/sys-init-matrix.xml";
			
			File file = new File(nodePath);
			if(!file.exists()) {
				logger.error("init SysOrgMatrix data is not exists ");
				return null;
			}
			
			FileInputStream inputStream = new FileInputStream(file);
			Document doucment = XMLUtils.parse(inputStream);
			Node node = doucment.getFirstChild();//得到根节点
			if("root".equals(node.getNodeName())) {//读取配置的矩阵数据
				NodeList sysOrgMatrixNodeList=node.getChildNodes();//获取子节点sysOrgMatrix
				for(int z=0;z<sysOrgMatrixNodeList.getLength();z++) {
					Node sysOrgMatrixItem=sysOrgMatrixNodeList.item(z);//遍历sysOrgMatrix
					if("sysOrgMatrix".equals(sysOrgMatrixItem.getNodeName())) {
						return sysOrgMatrixItem;
					}
				}
			}
			return null;
	}
	
	public static Node getNodeSysOrgMatrixFdIdNode(Node sysOrgMatrixItem) {
		if(sysOrgMatrixItem!=null) {
			Node fdIdNode=getNodeByNodeName(sysOrgMatrixItem,"fdId");
			return fdIdNode;
		}
		return null;
	}
	
	/**
	 * 返回 relationConditionalsFdId的ID
	 * @param sysOrgMatrixItem
	 * @return
	 */
	public static Node getRelationConditionalsFdId(Node sysOrgMatrixItem) {
		if(sysOrgMatrixItem==null) {
			return null;
		}
		
		Node fdRelationConditionalsItem=getNodeByNodeName(sysOrgMatrixItem,"fdRelationConditionals");
		if(fdRelationConditionalsItem!=null) {
			NodeList sysOrgMatrixRelationItems=fdRelationConditionalsItem.getChildNodes();
			for(int i=0;i<sysOrgMatrixRelationItems.getLength();i++) {
				if("sysOrgMatrixRelation".equals(sysOrgMatrixRelationItems.item(i).getNodeName())) {
					Node relationConditionalsFdId=getNodeByNodeName(sysOrgMatrixRelationItems.item(i),"fdId");
					return relationConditionalsFdId;
				}
			}
		}
		return null;
	}
	
	/**
	 * 返回 relationConditionalsFdId的ID
	 * @param sysOrgMatrixItem
	 * @return
	 */
	public static Node getfdRelationResultsFdId(Node sysOrgMatrixItem) {
		if(sysOrgMatrixItem==null) {
			return null;
		}
		
		Node fdRelationResultsItem=getNodeByNodeName(sysOrgMatrixItem,"fdRelationResults");
		if(fdRelationResultsItem!=null) {
			NodeList sysOrgMatrixRelationItems=fdRelationResultsItem.getChildNodes();
			for(int i=0;i<sysOrgMatrixRelationItems.getLength();i++) {
				if("sysOrgMatrixRelation".equals(sysOrgMatrixRelationItems.item(i).getNodeName())) {
					Node relationConditionalsFdId=getNodeByNodeName(sysOrgMatrixRelationItems.item(i),"fdId");
					return relationConditionalsFdId;
				}
			}
		}
		return null;
	}

	/**
	 * 返回组织架构名称
	 * <p>
	 * 机构、部门、岗位、群组：名称/编号
	 * <p>
	 * 人员：名称/登录名/编号/手机号
	 *
	 * @param elem
	 * @return
	 */
	public static String getElementName(SysOrgElement elem) {
		StringBuffer names = new StringBuffer();
		names.append(elem.getFdName());
		HttpServletRequest request = Plugin.currentRequest();
		if (request != null && "findMatrixPage".equals(request.getParameter("method"))) {
			// 页面查询时，只需要名称
			return names.toString();
		}
		if (elem instanceof SysOrgPerson) {
			// 名称/登录名/编号/手机号
			SysOrgPerson person = (SysOrgPerson) elem;
			names.append("/").append(person.getFdLoginName());
			if (StringUtil.isNotNull(person.getFdNo()) || StringUtil.isNotNull(person.getFdMobileNo())) {
				names.append("/");
				if (StringUtil.isNotNull(person.getFdNo())) {
					names.append(person.getFdNo());
				}
				if (StringUtil.isNotNull(person.getFdMobileNo())) {
					names.append("/").append(person.getFdMobileNo());
				}
			}
		} else {
			if (StringUtil.isNotNull(elem.getFdNo())) {
				names.append("/").append(elem.getFdNo());
			}
		}
		return names.toString();
	}
	
}
