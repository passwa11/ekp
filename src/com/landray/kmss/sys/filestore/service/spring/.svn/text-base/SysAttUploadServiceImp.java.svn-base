package com.landray.kmss.sys.filestore.service.spring;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.query.Query;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.attachment.io.IOUtil;
import com.landray.kmss.sys.attachment.model.SysAttBase;
import com.landray.kmss.sys.filestore.constant.SysAttUploadConstant;
import com.landray.kmss.sys.filestore.dao.ISysAttCatalogDao;
import com.landray.kmss.sys.filestore.dao.ISysAttUploadDao;
import com.landray.kmss.sys.filestore.location.interfaces.ISysFileLocationProxyService;
import com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil;
import com.landray.kmss.sys.filestore.model.SysAttCatalog;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.filestore.model.SysAttFileSlice;
import com.landray.kmss.sys.filestore.model.SysAttTmp;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.sys.filestore.service.ISysFileConvertDataService;
import com.landray.kmss.sys.filestore.util.SysFileStoreUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.FileMimeTypeUtil;
import com.landray.kmss.util.FileUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.MD5Util;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;

@SuppressWarnings("rawtypes")
public class SysAttUploadServiceImp implements ISysAttUploadService, ApplicationContextAware, SysAttUploadConstant {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttUploadServiceImp.class);

	private ISysAttUploadDao sysAttUploadDao;
	
	private ISysAttCatalogDao sysAttCatalogDao;

	private String siliceFix = "_slice";

	private String fileTmp = "_tmp";

	public void setSysAttUploadDao(ISysAttUploadDao sysAttUploadDao) {
		this.sysAttUploadDao = sysAttUploadDao;
	}
	
	public void setSysAttCatalogDao(ISysAttCatalogDao sysAttCatalogDao) {
		this.sysAttCatalogDao = sysAttCatalogDao;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (SpringBeanUtil.getApplicationContext() == null) {
			SpringBeanUtil.setApplicationContext(applicationContext);
		}
	}

	@SuppressWarnings("unused")
	private ISysFileConvertDataService convertDataService = null;

	public void setConvertDataService(ISysFileConvertDataService convertDataService) {
		this.convertDataService = convertDataService;
	}

	@Override
	public Date getCurTimestamp() throws Exception {
		return sysAttUploadDao.getCurTimestamp();
	}

	@Override
	public long getUploadedCount(String fileID) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("getUploadedCount_计算文件上传总大小。。");
		}
		return sysAttUploadDao.getUploadedCount(fileID);
	}

	@Override
	public SysAttFile getFileByMd5(String fileMd5, long fileSize) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("getFileByMd5_查找相同的文件。文件md5：" + fileMd5 + ",文件大小：" + fileSize);
		}
		return sysAttUploadDao.getFileByMd5(fileMd5, fileSize);
	}

	@Override
	public SysAttFile getFileById(String fileId) throws Exception {
		return (SysAttFile) sysAttUploadDao.findByPrimaryKey(fileId, SysAttFile.class, true);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public SysAttFile getFileByPath(String filePath) throws Exception {
		HQLInfo hql = new HQLInfo();
		hql.setModelName(SysAttFile.class.getName());
		hql.setWhereBlock("fdFilePath=:fdFilePath");
		hql.setParameter("fdFilePath", filePath);
		hql.setOrderBy("docCreateTime desc");
		Object obj = sysAttCatalogDao.findFirstOne(hql);
		SysAttFile attFile = obj == null? new SysAttFile() : (SysAttFile)obj;
		if (attFile.getFdCata() != null) {//先加载fdPath，以免在LocalSchedulerImp中报错
			attFile.getFdCata().getFdPath();
		}
		return attFile;
	}

	@Override
	public SysAttFileSlice getNextFileSlice(String fileID) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("getNextFileSlice_获取该文件文件的下一个切片信息。。");
		}
		return sysAttUploadDao.getNextFileSlice(fileID);
	}

	@Override
	public void delete(String fileId) throws Exception {
		SysAttTmp attTmp = new SysAttTmp();
		attTmp.setFdId(IDGenerator.generateID());
		attTmp.setFdFileId(fileId);
		attTmp.setFdDeleteTime(new Date());
		sysAttUploadDao.add(attTmp);
	}
	
	@Override
	public void deleteRecord(String fileId) throws Exception {
		sysAttUploadDao.delete(getFileById(fileId));
	}

	@Override
	public void delete(String fileId, boolean isDelDiskFile) throws Exception {
		if (isDelDiskFile) {
			SysAttFile attfile = getFileById(fileId);
			if (attfile != null) {
				delete(attfile);
			}
		} else {
			delete(fileId);
		}
	}

	private void delete(SysAttFile file) throws Exception {
		ISysFileLocationProxyService proxy = SysFileLocationUtil.getProxyService(file.getFdAttLocation());
		if (file.getFdStatus() != SYS_ATT_FILE_STATUS_UPLOADED) {
			if (proxy.doesFileExist(file.getFdFilePath()+ siliceFix)) {
				proxy.deleteDirectory(file.getFdFilePath()+ siliceFix);
			}
			if (proxy.doesFileExist(file.getFdFilePath()+ fileTmp)){
				proxy.deleteDirectory(file.getFdFilePath()+ fileTmp);
			}
			sysAttUploadDao.clearFileSlice(file.getFdId());
		}
		if (proxy.doesFileExist(file.getFdFilePath())) {
			logger.info("[nozuo-delete]:" + (file != null ? file.toString() : "为空"));
			// realFile.delete();
		}
		sysAttUploadDao.delete(file);
	}

	@Override
	public void deleteFile(String fileId, String suffix) throws Exception {
		deleteFile(getFileById(fileId), suffix);
	}

	@Override
	public void deleteFile(SysAttFile attFile, String suffix) throws Exception {
		String suffixFile = "";
		if (StringUtil.isNotNull(suffix)) {
			suffixFile = "_" + suffix;
		}
		ISysFileLocationProxyService proxy = SysFileLocationUtil.getProxyService(attFile.getFdAttLocation());
		if (proxy.doesFileExist(attFile.getFdFilePath() + suffixFile)) {
			logger.info("[nozuo-deleteFile]:" + (attFile != null ? attFile.toString() : "为空"));
			// file.delete();
		}
	}

	@Override
	public String addFile(InputStream in,String fileName) throws Exception {
		return addFile(null, 0L, in, false,fileName);
	}

	@Override
	public InputStream getFileData(String fileId) throws Exception {
		SysAttFile attFile = getFileById(fileId);
		if(attFile == null){
			logger.warn("找不到fileId={}对应的文件",fileId);
			return null;
		}
		String fullFilePath = attFile.getFdFilePath();
		String pathPrefix = attFile.getFdCata() == null ? null : attFile.getFdCata().getFdPath();

		InputStream in = null;
		try {
			in = SysFileLocationUtil.getProxyService(attFile.getFdAttLocation()).readFile(fullFilePath, pathPrefix);
		} catch (Exception e) {
			logger.error("文件已被删除", e);
		}

		return in;
	}
	
	@Override
	public List<SysAttFile> findNotExistMainBeforeByTime(int limitNum) throws Exception {
		if (limitNum < 1) {
			return Collections.EMPTY_LIST;
		}
		return sysAttUploadDao.findNotExistMainBeforeByTime(limitNum);
	}

	@Override
	public InputStream getFileData(String fileId, String picThumbName) throws Exception {
		SysAttFile attFile = getFileById(fileId);
		String path = attFile.getFdFilePath() + "_convert/" + "image2thumbnail_";
		if ("big".equals(picThumbName)) {
			path = path + SysFileStoreUtil.getBigImageWidth();
		} else if ("small".equals(picThumbName)) {
			path = path + SysFileStoreUtil.getSmallImageWidth();
		} else {
			path = attFile.getFdFilePath();
		}
		String pathPrefix = attFile.getFdCata() == null ? null : attFile.getFdCata().getFdPath();
		return SysFileLocationUtil.getProxyService(attFile.getFdAttLocation()).readFile(path,pathPrefix);
	}

	@Override
	public String addFile(String fileMd5, long fileSize, InputStream in
			, boolean isCopy,String fileName) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("addFile_上传整个文件，不切片。。");
		}
		byte[] inputSreamByt = IOUtils.toByteArray(in);
		// in.close();
		// 在FileUtils的文件读方法的最后都会调用IOUtils.closeQuietly(in);方法来确保输入流正确关闭
		IOUtils.closeQuietly(in);
		if (StringUtil.isNull(fileMd5)) {
			fileMd5 = MD5Util.getMD5String(inputSreamByt);
		}
		if (fileSize <= 0L) {
			fileSize = inputSreamByt.length;
		}
		SysAttFile attfile = null;
		if (!isCopy) {
			attfile = sysAttUploadDao.getFileByMd5(fileMd5, fileSize);
		}
		if (attfile != null) {
			// 当直接引用服务器上，存在的附件时候，释放inputSreamByt所占用空间，减少内存消耗
			if (!exists(attfile)) {
				generateAttFile(new ByteArrayInputStream(inputSreamByt), getAbsouluteFilePath(attfile),fileName);
			}
			inputSreamByt = null;
			return attfile.getFdId();
		}
		SysAttCatalog catalog = sysAttUploadDao.getDefultCatalog();
		String id = IDGenerator.generateID();
		String absPath = generatePath(id);
		generateAttFile(new ByteArrayInputStream(inputSreamByt),absPath,fileName);
		inputSreamByt = null;
		addFile(id, fileSize, fileMd5, absPath, SYS_ATT_FILE_STATUS_UPLOADED, catalog);
		return id;
	}

	private void generateAttFile(InputStream inputSream, String filePath,String fileName) throws Exception {
		Map<String,String> header = new HashMap<String,String>();
		
		if(StringUtil.isNull(fileName)){
			header.put("Content-Type", "application/octet-stream");
		}else{
			header.put("Content-Type", FileMimeTypeUtil.getContentType(fileName));
			fileName = URLEncoder.encode(fileName,"utf-8");
			header.put("Content-Disposition", "filename=\"" + fileName + "\"");
		}
		SysFileLocationUtil.getProxyService(SysFileLocationUtil.getKey(null)).writeFile(inputSream, filePath,header);
	}

	private boolean exists(SysAttFile attfile) {
		try {
			String filePath = getAbsouluteFilePath(attfile);
			String pathPrefix = attfile.getFdCata() == null ? null : attfile.getFdCata().getFdPath();
			return SysFileLocationUtil.getProxyService(attfile.getFdAttLocation()).doesFileExist(filePath,pathPrefix);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String addStreamFile(byte[] buffer,String fileName) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("addFile_上传整个文件，不切片。。");
		}
		String fileMd5 = MD5Util.getMD5String(buffer);
		int fileSize = buffer.length;
		SysAttCatalog catalog = sysAttUploadDao.getDefultCatalog();
		String id = IDGenerator.generateID();
		String absPath = generatePath(id);
		generateAttFile(new ByteArrayInputStream(buffer), absPath,fileName);
		buffer = null;
		addFile(id, fileSize, fileMd5, absPath, SYS_ATT_FILE_STATUS_UPLOADED, catalog);
		return id;
	}

	@Override
	public String addFile(String fileMd5, long fileSize, InputStream in, boolean isCopy
			, String filePath,String fileName)
			throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("addFile_上传整个文件，不切片。。");
		}
		byte[] inputSreamByt = null;
		
		File tmpFile = null;
		if (StringUtil.isNotNull(filePath)) {
			tmpFile = new File(filePath);
		}
		if (tmpFile != null && tmpFile.exists()) {
			if (fileSize <= 0L) {
				fileSize = in.available();
			}
			if (StringUtil.isNull(fileMd5)) {
				fileMd5 = MD5Util.getMD5String(in);
			}
			IOUtils.closeQuietly(in);
		} else {
			inputSreamByt = IOUtils.toByteArray(in);
			IOUtils.closeQuietly(in);
			if (StringUtil.isNull(fileMd5)) {
				fileMd5 = MD5Util.getMD5String(inputSreamByt);
			}
			if (fileSize <= 0L) {
				fileSize = inputSreamByt.length;
			}
		}
		SysAttFile attfile = null;
		if (!isCopy) {
			attfile = sysAttUploadDao.getFileByMd5(fileMd5, fileSize);
		}
		if (attfile != null) {
			if (!exists(attfile)) {
				if (tmpFile.exists()) {
					generateAttFile(new FileInputStream(tmpFile), getAbsouluteFilePath(attfile),fileName);
				} else {
					generateAttFile(new ByteArrayInputStream(inputSreamByt), getAbsouluteFilePath(attfile),fileName);
				}
			}
			inputSreamByt = null;
			return attfile.getFdId();
		}
		SysAttCatalog catalog = sysAttUploadDao.getDefultCatalog();
		String id = IDGenerator.generateID();
		String absPath = generatePath(id);
		if (tmpFile!=null && tmpFile.exists()) {
			generateAttFile(new FileInputStream(tmpFile), absPath,fileName);
		} else {
			generateAttFile(new ByteArrayInputStream(inputSreamByt), absPath,fileName);
		}
		inputSreamByt = null;
		addFile(id, fileSize, fileMd5, absPath, SYS_ATT_FILE_STATUS_UPLOADED, catalog);
		return id;
	}

	@Override
	public String updateFile(String mainId, String fileId, InputStream in) throws Exception {
		// 将流写入临时文件
		String tmpId = IDGenerator.generateID();
		File tmpFile = new File(FileUtil.getSystemTempPath() + "/" + tmpId + ".tmp");

		while (tmpFile.exists()) {
			tmpId = IDGenerator.generateID();
			tmpFile = new File(FileUtil.getSystemTempPath() + "/" + tmpId + ".tmp");
		}
		
		File tmpPfile = tmpFile.getParentFile();
		if (!tmpPfile.exists()) {
			tmpPfile.mkdirs();
		}
//		if (tmpFile.exists()) {
//			tmpFile.delete();
//		}
		tmpFile.createNewFile();

		FileOutputStream tmpFileOutputStream = new FileOutputStream(tmpFile);
		IOUtil.write(in, tmpFileOutputStream);
		IOUtils.closeQuietly(tmpFileOutputStream);
		IOUtils.closeQuietly(in);
		// 获取临时文件MD5
		String tmpFileMd5 = MD5Util.getMD5String(tmpFile);
		SysAttCatalog catalog = sysAttUploadDao.getDefultCatalog();
		SysAttFile attFile = getFileById(fileId);
		String pathPrefix = attFile.getFdCata() == null ? null : attFile.getFdCata().getFdPath();
		ISysFileLocationProxyService sysFileLocationProxyService = SysFileLocationUtil
				.getProxyService(attFile.getFdAttLocation());
		// 只要在线编辑打开后，MD5就不一致了，无法通过MD5对比来校验，只要提交就产生新的内容
		if (StringUtil.isNotNull(fileId)) {

			// 判断是当前附件是否存在其他文档的引用，如果没有引用，则在原来的上面覆盖
			Query q = sysAttUploadDao.getHibernateSession().createNativeQuery("SELECT fd_id FROM sys_att_main WHERE fd_file_id = '" + fileId + "' and fd_id !='"
							+ mainId + "' UNION SELECT fd_id FROM sys_att_rtf_data WHERE fd_file_id = '" + fileId
							+ "' and fd_id !='" + mainId + "'");
			List attIdList = q.list();
			if (attIdList.isEmpty()) {
				String absPath = generatePath(tmpId);

				// 金格控件在线编辑保存时生成备份文件，同时删除旧的file文档记录和磁盘文件
				InputStream oldFileInputStream = sysFileLocationProxyService.readFile(getAbsouluteFilePath(fileId),pathPrefix);
				SysFileLocationUtil.getProxyService().writeFile(oldFileInputStream, absPath + "_bak");
				//保持关联不做删除处理
				//delete(fileId, true);
				FileInputStream fileInputStream = new FileInputStream(tmpFile);
				long fileSize = fileInputStream.available();
				SysFileLocationUtil.getProxyService().writeFile(fileInputStream, absPath);
				IOUtils.closeQuietly(oldFileInputStream);
				IOUtils.closeQuietly(fileInputStream);
				addFile(tmpId, fileSize, tmpFileMd5, absPath, SYS_ATT_FILE_STATUS_UPLOADED, catalog);
				// 清理临时附件
				tmpFile.delete();
				return tmpId + ";" + fileSize;
			}
		}
		// if (!fileMd5.equals(tmpFileMd5)) {
		// 将临时文件提交
		String absPath = generatePath(tmpId);
		FileInputStream fileInputStream = new FileInputStream(tmpFile);
		long fileSize = fileInputStream.available();
		SysFileLocationUtil.getProxyService().writeFile(fileInputStream, absPath);
		IOUtils.closeQuietly(fileInputStream);
		addFile(tmpId, fileSize, tmpFileMd5, absPath, SYS_ATT_FILE_STATUS_UPLOADED, catalog);
		// 清理临时附件
		tmpFile.delete();
		return tmpId + ";" + fileSize;
		// }
		// return null;
	}

	@Override
	public SysAttFileSlice addFileInfo(String fileMd5, long fileSize) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("addFileInfo_新增文件信息，并切片。。");
		}
		SysAttFileSlice attFileSlice = null;
		SysAttCatalog catalog = sysAttUploadDao.getDefultCatalog();

		String id = IDGenerator.generateID();
		SysAttFile attFile = addFile(id, fileSize, fileMd5, generatePath(id), SYS_ATT_FILE_STATUS_Init,
				catalog);

		String sliceCfgSize = ResourceUtil.getKmssConfigString("sys.att.slice.size");
		long sliceSize = SYS_ATT_CONFIG_SLICE_SIZE;
		if (StringUtil.isNotNull(sliceCfgSize)) {
			sliceSize = Long.valueOf(sliceCfgSize) * 1024L * 1024L;
		}
		if (sliceSize >= fileSize) {
			attFileSlice = addFileSlice(IDGenerator.generateID(), 0L, fileSize, 0, attFile);
		} else {
			long round = (fileSize % sliceSize == 0) ? fileSize / sliceSize : (fileSize / sliceSize + 1);
			for (long i = 0; i < round; i++) {
				long start = i * sliceSize;
				long end = (i + 1) * sliceSize;
				if ((i + 1) * sliceSize > fileSize) {
					end = fileSize;
				}
				if (i == 0) {
					attFileSlice = addFileSlice(IDGenerator.generateID(), start, end, (int) i, attFile);
				} else {
					addFileSlice(IDGenerator.generateID(), start, end, (int) i, attFile);
				}
			}
		}
		return attFileSlice;
	}

	@Override
	public SysAttFileSlice updateFileSlice(String sliceid, InputStream in) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("updateFileSlice_上传文件切片。。");
		}
		SysAttFileSlice fileSlice = (SysAttFileSlice) sysAttUploadDao.findByPrimaryKey(sliceid, SysAttFileSlice.class,
				true);
		if (fileSlice != null && fileSlice.getFdStatus() != SYS_ATT_SLICE_STATUS_UPLOADED) {
			SysAttFile attFile = fileSlice.getFdFile();
			String subFilePath = attFile.getFdFilePath() + siliceFix + "/" + attFile.getFdId() + "_" + fileSlice.getFdOrder();
			SysFileLocationUtil.getProxyService().writeFile(in, subFilePath);
			TransactionStatus updateStatus = TransactionUtils.beginNewTransaction();
			try {
				fileSlice.setFdStatus(SYS_ATT_SLICE_STATUS_UPLOADED);
				fileSlice.setFdModifyTime(new Date().getTime());
				sysAttUploadDao.update(fileSlice);
				if (attFile.getFdStatus() != SYS_ATT_FILE_STATUS_UPLOADING) {
					attFile.setFdStatus(SYS_ATT_FILE_STATUS_UPLOADING);
					sysAttUploadDao.update(attFile);
				}
				TransactionUtils.getTransactionManager().commit(updateStatus);
			} catch (Exception ex) {
				ex.printStackTrace();
				TransactionUtils.getTransactionManager().rollback(updateStatus);
			}
		}
		return fileSlice;
	}

	@Override
	public boolean combFileSlice(SysAttFile attFile) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("combFileSlice_合并切片中。。");
		}
//		String filePath = formatPath(attFile.getFdCata(), attFile.getFdFilePath());
		String filePath = attFile.getFdFilePath();
		String fileDir = attFile.getFdFilePath() + siliceFix;
		File tmpFile = File.createTempFile(fileTmp,"tmp");
		if (tmpFile.exists() && tmpFile.isFile()) {
			tmpFile.delete();
		}
		tmpFile.createNewFile();
		if (logger.isDebugEnabled()) {
			logger.debug("combFileSlice_创建临时文件:" + tmpFile.getPath());
		}
		OutputStream fileOutput = null;
		ISysFileLocationProxyService sysFileLocationService = SysFileLocationUtil.getProxyService(attFile.getFdAttLocation());
		String pathPrefix = attFile.getFdCata() == null ? null : attFile.getFdCata().getFdPath();
		try {
			fileOutput = new FileOutputStream(tmpFile);
			if (sysFileLocationService.doesFileExist(fileDir,pathPrefix)) {
				if (logger.isDebugEnabled()) {
					logger.debug("combFileSlice_读取切片文件夹：" + fileDir);
				}
				for (int i = 0; true; i++) {
					String sliceFile = fileDir +"/" + attFile.getFdId() + "_" + i;
					if (sysFileLocationService.doesFileExist(sliceFile,pathPrefix)) {
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						IOUtil.write(sysFileLocationService.readFile(sliceFile,pathPrefix),baos);
						byte[] bytes = baos.toByteArray();
						fileOutput.write(bytes);
						fileOutput.flush();
						bytes = null;
					} else {
						break;
					}
				}
				fileOutput.flush();
				SysFileLocationUtil.getProxyService().writeFile(FileUtils.openInputStream(tmpFile)
						,filePath);
				fileOutput.close();
				fileOutput = null;
				tmpFile.delete();
				tmpFile = null;

				TransactionStatus updateStatus = TransactionUtils.beginNewTransaction();
				try {
					attFile.setFdStatus(SYS_ATT_FILE_STATUS_UPLOADED);
					sysAttUploadDao.update(attFile);
					sysAttUploadDao.clearFileSlice(attFile.getFdId());
					TransactionUtils.getTransactionManager().commit(updateStatus);
				} catch (Exception ex) {
					TransactionUtils.getTransactionManager().rollback(updateStatus);
					ex.printStackTrace();
					return false;
				}
			} else {
				throw new FileNotFoundException("切片目录不存在！");
			}
			if (logger.isDebugEnabled()) {
				logger.debug("combFileSlice_合并完成。。");
			}
		} catch (Exception e) {
			logger.error("合并文件combFileSlice_发生异常：" + e);
			throw new Exception(e);
		} finally {
			IOUtils.closeQuietly(fileOutput);
		}
		return true;
	}
	
	@Override
	public SysAttFile addFile(String fdId, long fdFileSize, String fdFileMd5,
			String path) throws Exception {
		Date today = new Date();
		SysAttFile attFile = new SysAttFile();
		attFile.setFdId(fdId);
		attFile.setFdFileSize(fdFileSize);
		attFile.setDocCreateTime(today);
		attFile.setFdMd5(fdFileMd5);
		attFile.setFdFilePath(path);
		attFile.setFdStatus(SYS_ATT_FILE_STATUS_UPLOADED);
		String currLocation = SysFileLocationUtil.getKey(null);
		if(SysAttBase.ATTACHMENT_LOCATION_SERVER.equals(currLocation)){//server类型的，设置catalog
			attFile.setFdCata(sysAttUploadDao.getDefultCatalog());
		}
		attFile.setFdAttLocation(SysFileLocationUtil.getKey(null));
		sysAttUploadDao.add(attFile);
		return attFile;
	}

	private SysAttFile addFile(String id, long fileSize, String fileMd5, String path, int status,
			SysAttCatalog catalog) throws Exception {
		Date today = new Date();
		SysAttFile attFile = new SysAttFile();
		attFile.setFdId(id);
		attFile.setFdFileSize(fileSize);
		attFile.setDocCreateTime(today);
		attFile.setFdMd5(fileMd5);
		attFile.setFdFilePath(path);
		attFile.setFdStatus(status);
		String currLocation = SysFileLocationUtil.getKey(null);
		if(SysAttBase.ATTACHMENT_LOCATION_SERVER.equals(currLocation)){//server类型的，设置catalog
			attFile.setFdCata(catalog);
		}
		attFile.setFdAttLocation(SysFileLocationUtil.getKey(null));
		sysAttUploadDao.add(attFile);
		return attFile;
	}

	private SysAttFileSlice addFileSlice(String id, long start, long end, int order, SysAttFile attFile)
			throws Exception {
		SysAttFileSlice fileSlice = new SysAttFileSlice();
		fileSlice.setFdId(IDGenerator.generateID());
		fileSlice.setFdStartPoint(start);
		fileSlice.setFdEndPoint(end);
		fileSlice.setFdOrder(order);
		fileSlice.setFdStatus(SYS_ATT_SLICE_STATUS_INIT);
		fileSlice.setFdFile(attFile);
		fileSlice.setFdModifyTime(new Date().getTime());
		sysAttUploadDao.add(fileSlice);
		return fileSlice;
	}

	@Override
	public String generatePath(String id) throws Exception {
		
		Date createTime = sysAttUploadDao.getCurTimestamp();
		Calendar cal = Calendar.getInstance();
		cal.setTime(createTime);
		return "/" + cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1) + "/"
				+ cal.get(Calendar.WEEK_OF_MONTH) + "/" + id;
	}
	
	private String formatPath(SysAttCatalog catalog, String relativePath) {
		return formatCataPath(catalog) + relativePath;
	}
	
	private String formatCataPath(SysAttCatalog catalog) {
		String cfgPath = null;
		if (catalog == null) {
			cfgPath = "";
		} else {
			cfgPath = catalog.getFdPath();
		}
		if (StringUtil.isNotNull(cfgPath)) {
			cfgPath = cfgPath.replace("\\", "/");
		}
		if (cfgPath.endsWith("/")) {
			cfgPath = cfgPath.substring(0, cfgPath.length() - 1);
		}
		return cfgPath;
	}

	@Override
	public String getDefaultCatalog() {
		String cfgPath = null;
		SysAttCatalog catalog = this.sysAttUploadDao.getDefultCatalog();
		if (catalog == null) {
			cfgPath = "";//ResourceUtil.getKmssConfigString("kmss.resource.path");
		} else {
			cfgPath = "";//catalog.getFdPath();
		}
		return cfgPath;
	}

	@Override
	public String getAbsouluteFilePath(SysAttFile attFile) throws Exception {
		//return formatPath(attFile.getFdCata(), attFile.getFdFilePath());
		return attFile.getFdFilePath();
	}
	
	
	@Override
	public String getAbsouluteFilePath(SysAttFile attFile, Boolean isFullPath)
			throws Exception {

		String fileRelativePath = this.getAbsouluteFilePath(attFile);

		if (isFullPath != null && isFullPath) {
			fileRelativePath = SysFileLocationUtil
					.getProxyService(attFile.getFdAttLocation())
					.formatReadFilePath(formatCataPath(attFile.getFdCata()),
							fileRelativePath);
		}

		return fileRelativePath;
	}
	
	
	@Override
	public String getAbsouluteFilePath(String fileId) throws Exception {
		return getAbsouluteFilePath(getFileById(fileId));
	}
}
