package com.landray.kmss.sys.attachment.service.spring;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.springframework.transaction.TransactionStatus;

import com.alibaba.fastjson.JSON;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.attachment.io.DecryptionInputStream;
import com.landray.kmss.sys.attachment.io.IOUtil;
import com.landray.kmss.sys.attachment.model.SysAttBase;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttToggleService;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.filestore.dao.ISysAttUploadDao;
import com.landray.kmss.sys.filestore.location.interfaces.ISysFileLocationProxyService;
import com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil;
import com.landray.kmss.sys.filestore.model.SysAttCatalog;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.quartz.interfaces.ISysQuartzCoreService;
import com.landray.kmss.sys.quartz.interfaces.ISysQuartzModel;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzModelContext;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.FileUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.sunbor.web.tag.Page;

/**
 * 附件迁移服务类
 * @author peng
 */
public class SysAttToggleServiceImp extends BaseServiceImp implements ISysAttToggleService {

	private final static Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttToggleServiceImp.class);
	
	private static KmssCache attachmentTogglingCache = new KmssCache(SysAttToggleServiceImp.class);
	
	private static final String ATT_TOGGLING_KEY = "SysAttMainCoreInnerService.attachmentTogglingCache";
	
	private static final String ATT_TOGGLING_MODEL_ID = "32f512618f7d96315b932ed6f012b18f";
	
	private ISysQuartzCoreService sysQuartzCoreService;
	
	public void setSysQuartzCoreService(ISysQuartzCoreService sysQuartzCoreService) {
		this.sysQuartzCoreService = sysQuartzCoreService;
	}
	
	private ISysAttUploadDao sysAttUploadDao;

	public ISysAttUploadDao getSysAttUploadDao() {
		if(sysAttUploadDao == null){
			sysAttUploadDao = (ISysAttUploadDao)SpringBeanUtil.getBean("sysAttUploadDao");
		}
		return sysAttUploadDao;
	}

	//迁移附件状态从缓存中取，支持集群
	private boolean isAttachmentToggling(){
		Object isAttachmentToggling = attachmentTogglingCache.get(ATT_TOGGLING_KEY);
		if(isAttachmentToggling == null || !(isAttachmentToggling instanceof Boolean)){
			return false;
		}
		return (Boolean)isAttachmentToggling;
	}
	
	//迁移附件状态存到缓存中，支持集群
	private void setAttachmentToggling(boolean isAttachmentToggling){
		attachmentTogglingCache.put(ATT_TOGGLING_KEY, isAttachmentToggling);
	}
	
	/**
	 * 迁移附件
	 * @param operate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject saveToggleAttchment(String operate,String source,String target,String startDate
			,String endDate,int toggleStartTime,int toggleEndTime) throws Exception{
		JSONObject jsonObj = new JSONObject();
		if("status".equals(operate)){//查询是否正在进行附件迁移
			jsonObj.put("toggling", isAttachmentToggling());
		}else if("start".equals(operate)){//开始迁移附件
			jsonObj.put("toggling", isAttachmentToggling());
			String time = job(source, target, startDate, endDate, toggleStartTime, toggleEndTime);
			jsonObj.put("toggleMsg", String.format(ResourceUtil.getString
					("sys-attachment:attachment.filestore.toggle.toggleMsg"),time));
		}else if("end".equals(operate)){//结束迁移附件
			setAttachmentToggling(false);
			jsonObj.put("toggling", false);
			SysAttToggle attToggle = new SysAttToggle();
			attToggle.setFdId(ATT_TOGGLING_MODEL_ID);
			sysQuartzCoreService.delete(attToggle,ATT_TOGGLING_KEY);
		}
		return jsonObj;
	}
	
	@SuppressWarnings("unchecked")
	private String job(String source,String target,String startDate
			,String endDate,int toggleStartTime,int toggleEndTime) throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("source", source);
		obj.put("target", target);
		obj.put("startDate", startDate);
		obj.put("endDate", endDate);
		obj.put("toggleStartTime", toggleStartTime);
		obj.put("toggleEndTime", toggleEndTime);
		SysAttToggle attToggle = new SysAttToggle();
		attToggle.setFdId(ATT_TOGGLING_MODEL_ID);
		SysQuartzModelContext sysQuartzModelContext=new SysQuartzModelContext();
		String cron = "0 0 " + toggleStartTime + " * * ?";
		sysQuartzModelContext.setQuartzCronExpression(cron);
		sysQuartzModelContext.setQuartzJobMethod("saveAndDoToggleAttchment");
		sysQuartzModelContext.setQuartzKey(ATT_TOGGLING_KEY);
		sysQuartzModelContext.setQuartzParameter(obj.toJSONString());
		sysQuartzModelContext.setQuartzSubject(ResourceUtil.getString("sys-attachment:attachment.filestore.toggle.task"));
		sysQuartzModelContext.setQuartzJobService("sysAttToggleService");
		sysQuartzModelContext.setQuartzLink("");
		sysQuartzCoreService.saveScheduler(sysQuartzModelContext, attToggle);
		
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		c.set(Calendar.HOUR_OF_DAY, toggleStartTime);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND,0);
		Date executeTime = null;
		if(c.getTimeInMillis()>System.currentTimeMillis()){
			executeTime = c.getTime();
		}else{
			c.add(Calendar.DAY_OF_MONTH, 1);
			executeTime = c.getTime();
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(executeTime);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void saveAndDoToggleAttchment(SysQuartzJobContext jobContext) {
		setAttachmentToggling(true);
		IBaseDao baseDao = this.getBaseDao();
		String param = jobContext.getParameter();
		com.alibaba.fastjson.JSONObject json = JSON.parseObject(param);
		String source = json.getString("source");
		String target = json.getString("target");
		String startDate = json.getString("startDate");
		String endDate = json.getString("endDate");
		String toggleStartTime = json.getString("toggleStartTime");
		String toggleEndTime = json.getString("toggleEndTime");
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		c.set(Calendar.HOUR, Integer.valueOf(toggleEndTime));
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND,0);
		Long endExecuteTime = c.getTime().getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		ISysFileLocationProxyService sourceService = SysFileLocationUtil.getProxyService(source);
		ISysFileLocationProxyService targetService = SysFileLocationUtil.getProxyService(target);
		HQLInfo hql = new HQLInfo();
		hql.setModelName(SysAttFile.class.getName());
		hql.setWhereBlock("fdAttLocation=:fdAttLocation");
		try {
			if(StringUtil.isNotNull(startDate)){
				hql.setWhereBlock(hql.getWhereBlock() + " and docCreateTime>:startDate");
				hql.setParameter("startDate",dateFormat.parse(startDate));
			}
			if(StringUtil.isNotNull(endDate)){
				hql.setWhereBlock(hql.getWhereBlock() + " and docCreateTime<:endDate");
				hql.setParameter("endDate",dateFormat.parse(endDate));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		hql.setParameter("fdAttLocation",source);
		hql.setRowSize(1000);
		hql.setPageNo(0);
		List<SysAttFile> attFileList = null;
		Iterator<SysAttFile> iterator = null;
		Page page = null;
		do{
			hql.setPageNo(hql.getPageNo()+1);
			try {
				page = findPage(hql);
				attFileList = page.getList();
				iterator = toogleFiles(baseDao, source, target,endExecuteTime, sourceService, targetService, attFileList);
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}while(page.getTotal() > hql.getPageNo()*hql.getRowSize() && !ArrayUtil.isEmpty(attFileList) 
				&& isAttachmentToggling() && System.currentTimeMillis()<endExecuteTime);
		
		setAttachmentToggling(false);
		try {
			if(iterator != null && iterator.hasNext()){//任务因结束时间到了或人为中断,添加一条普通定时任务以待第二天执行
				job(source, target, startDate, endDate, Integer.valueOf(toggleStartTime), Integer.valueOf(toggleEndTime));
			}else{
				SysQuartzModelContext sysQuartzModelContext=new SysQuartzModelContext();
				sysQuartzModelContext.setQuartzKey(ATT_TOGGLING_KEY);
				sysQuartzModelContext.setQuartzJobService("sysAttToggleService");
				SysAttToggle attToggle = new SysAttToggle();
				attToggle.setFdId(ATT_TOGGLING_MODEL_ID);
				sysQuartzModelContext.setQuartzEnabled(false);
				sysQuartzCoreService.saveScheduler(sysQuartzModelContext, attToggle);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Iterator<SysAttFile> toogleFiles(IBaseDao baseDao, String source,
			String target, Long endExecuteTime,
			ISysFileLocationProxyService sourceService,
			ISysFileLocationProxyService targetService,
			List<SysAttFile> attFileList) {
		Iterator<SysAttFile> iterator = attFileList.iterator();
		Map<String,String> header = new HashMap<String,String>();
		for (;isAttachmentToggling() && System.currentTimeMillis()<endExecuteTime && iterator.hasNext();) {
			SysAttFile sysAttFile = iterator.next();
			String pathPrefix = sysAttFile.getFdCata() == null ? null : sysAttFile.getFdCata().getFdPath();
			TransactionStatus status = null;
			InputStream in = null;
			try {
				//如果目标位置的文件存在，则跳过复制流的过程，直接修改SysAttFile表location字段
				if(!targetService.doesFileExist(sysAttFile.getFdFilePath(),pathPrefix)){
					
					String tempDir = FileUtil.getSystemTempPath();
					String tempFile = tempDir + File.separator + sysAttFile.getFdId() + 
							"-" + IDGenerator.generateID() + ".toggle.temp";
					if(SysAttBase.ATTACHMENT_LOCATION_SERVER.equals(source)){
						InputStream is = sourceService.readFile(sysAttFile.getFdFilePath(),pathPrefix);
						IOUtil.write(is, new FileOutputStream(tempFile));
						in = new DecryptionInputStream(new FileInputStream(tempFile),2);//server类型的，不管是否加过密，以兼容方式解密再读取
					}else{
						in = sourceService.readFile(sysAttFile.getFdFilePath(),pathPrefix);
					}
					if(in == null){
						logger.info("===========SysAttMainCoreInnerService.toggleAttchment faild,source not exists:"
								+ sysAttFile.getFdFilePath() + "(" + source + "->" + target + ")");
						continue;
					}
					if(!SysAttBase.ATTACHMENT_LOCATION_SERVER.equals(target)){//非server类型的，要传文件名以便存到云上，直连时不丢失文件名
						SysAttMain attMain = getAttMainByPath(sysAttFile.getFdId());
						String fileName = attMain == null ? "" : attMain.getFdFileName();
						fileName = URLEncoder.encode(fileName,"utf-8");
						header.put("Content-Disposition", "filename=" + fileName);
					}
					targetService.writeFile(in, sysAttFile.getFdFilePath(),header);
					File f = new File(tempFile);
					if(f.exists()){
						f.delete();
					}
				}
				toggleRelativeDirAndFiles(sourceService,targetService,sysAttFile.getFdFilePath(),source,pathPrefix,true);//迁移相应的convert等目录和下面的文件
				sysAttFile.setFdAttLocation(target);
				if(SysAttBase.ATTACHMENT_LOCATION_SERVER.equals(target)){//server类型的，设置catalog
					SysAttCatalog fdCata = getSysAttUploadDao().getDefultCatalog();
					sysAttFile.setFdCata(fdCata);
				}
				
				status = TransactionUtils.beginNewTransaction();
				baseDao.update(sysAttFile);
				TransactionUtils.getTransactionManager().commit(status);
				logger.info("===========SysAttMainCoreInnerService.toggleAttchment success:"
						+ sysAttFile.getFdFilePath() + "(" + source + "->" + target + ")");
			} catch (Exception e) {
				e.printStackTrace();
				if (null != status) {
					TransactionUtils.getTransactionManager().rollback(status);
				}
				logger.error("===========SysAttMainCoreInnerService.toggleAttchment error:"
						+ sysAttFile.getFdFilePath() + "(" + source + "->" + target + ")",e);
			}finally{
				if(in != null){
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return iterator;
	}

	/**
	 * 迁移相应的convert等目录和下面的文件
	 * @param fdFilePath
	 * @throws Exception 
	 */
	private void toggleRelativeDirAndFiles(ISysFileLocationProxyService sourceService,ISysFileLocationProxyService targetService
			,String filePath,String source,String pathPrefix,boolean validPrefix) throws Exception {
		String parent = filePath.substring(0,filePath.lastIndexOf("/")+1);
		String fileId = filePath.substring(filePath.lastIndexOf("/")+1);
		
		//处理以fileId开头的相关文件
		List<String> relativeFileList = sourceService.listFileNames(parent,pathPrefix);
		for (Iterator<String> iterator = relativeFileList.iterator(); iterator.hasNext();) {
			String path = iterator.next();
			String prefix = path.replace(parent, "");
			if(validPrefix && (!prefix.startsWith(fileId) || prefix.equals(fileId))){//跳过自己和不以fileId开头的文件
				continue;
			}
			InputStream in = null;
			String tempDir = FileUtil.getSystemTempPath();
			String tempFile = tempDir + File.separator + prefix + "-" + IDGenerator.generateID() + ".toggle.temp";
			try {
				if(SysAttBase.ATTACHMENT_LOCATION_SERVER.equals(source)){
					InputStream is = sourceService.readFile(path,pathPrefix);
					IOUtil.write(is, new FileOutputStream(tempFile));
					in = new DecryptionInputStream(new FileInputStream(tempFile	),2);//server类型的，不管是否加过密，以兼容方式解密再读取
				}else{
					in = sourceService.readFile(path,pathPrefix);
				}
				if(in == null){
					continue;
				}
				targetService.writeFile(in, path);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(in != null){
					try{
						in.close();
					}catch(Exception e1){
						e1.printStackTrace();
					}
				}
			}
			File f = new File(tempFile);
			if(f.exists()){
				f.delete();
			}
		}
		
		//处理以fileId开头的相关目录和目录下的文件
		List<String> relativeSubDirList = sourceService.listSubDirNames(parent,pathPrefix);
		for (Iterator<String> iterator = relativeSubDirList.iterator(); iterator.hasNext();) {
			String pathDir = iterator.next();
			String path = pathDir.replace(parent, "");
			if(validPrefix && (!path.startsWith(fileId) || path.equals(fileId))){//跳过不以fileId开头的目录
				continue;
			}
			pathDir = pathDir.endsWith("/") ? pathDir : pathDir + "/";
			toggleRelativeDirAndFiles(sourceService,targetService,pathDir,source,pathPrefix,false);//递归目录
		}
	}
	
	@SuppressWarnings("unchecked")
	public SysAttMain getAttMainByPath(String fileId) throws Exception {
		HQLInfo hql = new HQLInfo();
		hql.setModelName(SysAttMain.class.getName());
		hql.setWhereBlock("fdFileId=:fdFileId");
		hql.setParameter("fdFileId", fileId);
		hql.setOrderBy("docCreateTime asc");
		SysAttMain att = (SysAttMain)findFirstOne(hql);
		return att;
	}

	/**
	 * 实现标识接口ISysQuartzModel，供迁移定时任务用
	 * @author peng
	 *
	 */
	private class SysAttToggle extends SysAttMain implements ISysQuartzModel {
		
		private static final long serialVersionUID = -5661731258457612569L;
		
	}
}
