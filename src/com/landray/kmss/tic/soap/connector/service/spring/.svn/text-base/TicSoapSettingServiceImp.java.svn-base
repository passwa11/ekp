package com.landray.kmss.tic.soap.connector.service.spring;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.tic.soap.connector.impl.TicSoapProjectFactory;
import com.landray.kmss.tic.soap.connector.model.TicSoapSetting;
import com.landray.kmss.tic.soap.connector.service.ITicSoapSettingService;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.support.JdbcUtils;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * WEBSERVICE服务配置业务接口实现
 * 
 * @author
 * @version 1.0 2012-08-06
 */
public class TicSoapSettingServiceImp extends BaseServiceImp implements
		ITicSoapSettingService, InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(TicSoapSettingServiceImp.class);

	private ITicSoapSettingService TicSoapSettingService;
	
	public void setTicSoapSettingService(
			ITicSoapSettingService TicSoapSettingService) {
		this.TicSoapSettingService = TicSoapSettingService;
	}

	private DataSource dataSource;

	private DataSource getDataSource() {
		if (dataSource == null) {
			dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
		}
		return dataSource;
	}

	private List<TicSoapSetting> getSoapSettingList() throws Exception {
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			List<TicSoapSetting> list = new ArrayList<>();
			conn = getDataSource().getConnection();
			String sql = "select fd_id,doc_subject,fd_wsdl_url,fd_soap_verson from tic_soap_setting";
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			while(rs.next()){
				String fdId = rs.getString(1);
				String docSubject = rs.getString(2);
				String fdWsdlUrl = rs.getString(3);
				String fdSoapVersion = rs.getString(4);
				TicSoapSetting soapSetting = new TicSoapSetting();
				soapSetting.setFdId(fdId);
				soapSetting.setDocSubject(docSubject);
				soapSetting.setFdWsdlUrl(fdWsdlUrl);
				soapSetting.setFdSoapVerson(fdSoapVersion);
				list.add(soapSetting);
			}
			return list;
		} catch (Exception ex) {
			logger.error("设置待删除标识时出错", ex);
			conn.rollback();
			throw ex;
		} finally {
			// 关闭流
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(statement);
			JdbcUtils.closeConnection(conn);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		String noLoad = ResourceUtil
				.getKmssConfigString("kmss.tic.soap.noLoad.wsdl");
		if ("true".equals(noLoad)) {
			return;
		}
		List<TicSoapSetting> list = new ArrayList<>();
		try{
			list = getSoapSettingList();
		}catch (Exception e){
			logger.error(e.getMessage(),e);
		}
		for(TicSoapSetting setting:list){
			try {
				String soapVersion = setting.getFdSoapVerson();
				if(soapVersion!=null && soapVersion.contains(";")){
					String[] versions = soapVersion.split(";");
					for(String version:versions){
						logger.warn("非错误，只是为了查看相关日志。加载WSDL："+setting.getFdId()+","+setting.getDocSubject()+","+setting.getFdWsdlUrl()+","+version);
						TicSoapProjectFactory.getWsdlInterfaceInstance(setting, version);
						logger.warn("非错误，只是为了查看相关日志。WSDL加载完成："+setting.getFdId()+","+setting.getDocSubject()+","+setting.getFdWsdlUrl()+","+version);
					}
				}else{
					logger.warn("非错误，只是为了查看相关日志。加载WSDL："+setting.getFdId()+","+setting.getDocSubject()+","+setting.getFdWsdlUrl()+","+soapVersion);
					TicSoapProjectFactory.getWsdlInterfaceInstance(setting, soapVersion);
					logger.warn("非错误，只是为了查看相关日志。WSDL加载完成："+setting.getFdId()+","+setting.getDocSubject()+","+setting.getFdWsdlUrl()+","+soapVersion);
				}
			}catch (Exception e){
				logger.error(e.getMessage(),e);
			}
		}
	}
}
