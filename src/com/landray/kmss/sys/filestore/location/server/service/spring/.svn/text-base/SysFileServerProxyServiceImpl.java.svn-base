package com.landray.kmss.sys.filestore.location.server.service.spring;

import com.landray.kmss.sys.attachment.io.DecryptionInputStream;
import com.landray.kmss.sys.attachment.io.EncryptionInputStream;
import com.landray.kmss.sys.attachment.io.IOUtil;
import com.landray.kmss.sys.filestore.dao.ISysAttCatalogDao;
import com.landray.kmss.sys.filestore.dao.ISysAttUploadDao;
import com.landray.kmss.sys.filestore.location.service.AbstractSysFileLocationProxyService;
import com.landray.kmss.sys.filestore.model.SysAttCatalog;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 系统默认的附件存储server类型存储服务的后端实现
 * @author peng
 */
public class SysFileServerProxyServiceImpl extends AbstractSysFileLocationProxyService {
	protected static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysFileServerProxyServiceImpl.class);
	
	private ISysAttUploadDao sysAttUploadDao;

	public void setSysAttUploadDao(ISysAttUploadDao sysAttUploadDao) {
		this.sysAttUploadDao = sysAttUploadDao;
	}
	
	@SuppressWarnings("unused")
	private ISysAttCatalogDao sysAttCatalogDao;

	public void setSysAttCatalogDao(ISysAttCatalogDao sysAttCatalogDao) {
		this.sysAttCatalogDao = sysAttCatalogDao;
	}
	
	@Override
	public void writeFile(InputStream inputSream, String fileRelativePath
			,Map<String,String> header) throws Exception{
		
		String fileFullPath = formatWriteFilePath(fileRelativePath);
		
		// 当服务器上面不存在附件文件时候，分别构建输入，输出流
		EncryptionInputStream encryptionInputStream = new EncryptionInputStream(inputSream);
		File file = new File(fileFullPath);
		File pfile = file.getParentFile();
		if (!pfile.exists()) {
			try {
				pfile.mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		try {
			// 进行附件文件写操作
			IOUtil.write(encryptionInputStream, fileOutputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 附件写操作完成后，依次关闭输出流和输入流，释放输出，输入流所在内存空间
			IOUtils.closeQuietly(inputSream);
			IOUtils.closeQuietly(fileOutputStream);
			IOUtils.closeQuietly(encryptionInputStream);
		}
	}

	@Override
	public void writeOFile(InputStream inputSream, String fileRelativePath, Map<String, String> exParams) throws Exception {
		String fileFullPath = getTargetPath(fileRelativePath);

		// 当服务器上面不存在附件文件时候，分别构建输入，输出流
		EncryptionInputStream encryptionInputStream = new EncryptionInputStream(inputSream);
		File file = new File(fileFullPath);
		File pfile = file.getParentFile();
		if (!pfile.exists()) {
			try {
				pfile.mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		try {
			// 进行附件文件写操作
			IOUtil.write(encryptionInputStream, fileOutputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 附件写操作完成后，依次关闭输出流和输入流，释放输出，输入流所在内存空间
			IOUtils.closeQuietly(inputSream);
			IOUtils.closeQuietly(fileOutputStream);
			IOUtils.closeQuietly(encryptionInputStream);
		}
	}

	@Override
	public void writeOFolder(String folderPath, String relativePath, String zipName, String zipRelativePath, Map<String, String> exParams) throws Exception {
		checkBeforeWriteFolder(folderPath,relativePath);
		if (!relativePath.endsWith("/")) {
			relativePath += "/";
		}
		if (zipName.lastIndexOf(".") != -1) {
			zipName = zipName.substring(0, zipName.lastIndexOf("."));
		}
		File folder = new File(folderPath);
		String targetFullPath = getTargetPath(relativePath)+zipName;
		File targetFolder = new File(targetFullPath);
		if (StringUtils.equals(folder.getCanonicalPath(), targetFolder.getCanonicalPath())) {
			//源路径和目标路径一致的情况下直接返回
			return;
		}
		if(targetFolder.exists()){
			if (!targetFolder.isDirectory()) {
				throw new RuntimeException(targetFullPath + " 目标路径不是文件夹");
			}
		}else{
			if (!targetFolder.mkdirs() && !targetFolder.isDirectory()) {
				throw new RuntimeException(targetFullPath + " 目标路径创建失败");
			}
		}
		if (!targetFolder.canWrite()) {
			throw new IOException(targetFullPath + " 目标路径没有写入权限");
		}
		FileUtils.copyDirectory(folder, targetFolder);
	}

	private String getTargetPath(String fileRelativePath) {
		String cfgPath = ResourceUtil.getKmssConfigString("kmss.resource.path").replaceAll("\\\\", "/");
		if (!cfgPath.endsWith("/")) {
			cfgPath += "/";
		}
		fileRelativePath = fileRelativePath.replaceAll("\\\\", "/");
		if (fileRelativePath.startsWith("/")) {
			fileRelativePath = fileRelativePath.substring(1);
		}
		if (fileRelativePath.contains(cfgPath)) {
			return fileRelativePath;
		} else {
			return cfgPath + fileRelativePath;
		}
	}
	
	@Override
	public void writeFolder(String folderPath, String relativePath, String zipName,String zipRelativePath) throws Exception {
		checkBeforeWriteFolder(folderPath,relativePath);
		if (!relativePath.endsWith("/")) {
			relativePath += "/";
		}
		if (zipName.lastIndexOf(".") != -1) {
			zipName = zipName.substring(0, zipName.lastIndexOf("."));
		}
		File folder = new File(folderPath);
		String targetFullPath = formatWriteFilePath(relativePath)+zipName;
		File targetFolder = new File(targetFullPath);
		if (StringUtils.equals(folder.getCanonicalPath(), targetFolder.getCanonicalPath())) {
			throw new RuntimeException("源路径和目标路径不能相同");
		}
		if(targetFolder.exists()){
			if (!targetFolder.isDirectory()) {
				throw new RuntimeException(targetFullPath + " 目标路径不是文件夹");
			}
		}else{
			if (!targetFolder.mkdirs() && !targetFolder.isDirectory()) {
				throw new RuntimeException(targetFullPath + " 目标路径创建失败");
			}
		}
		if (!targetFolder.canWrite()) {
			throw new IOException(targetFullPath + " 目标路径没有写入权限");
		}
		FileUtils.copyDirectory(folder, targetFolder);
	}

	@Override
	public boolean compareFile(String sRelativePath, String tRelativePath, String fileName) throws Exception {
		return false;
	}

	@Override
	public File downloadFile(String relativePath, String fileName, String targetPath) throws Exception {
		return null;
	}
	
	@Override
	public void createDir(String dirRelativePath)
			throws Exception {
		String dirFullPath = formatWriteFilePath(dirRelativePath);
		File dir = new File(dirFullPath);
		if(!dir.exists()){
			dir.mkdirs();
		}
	}

	@Override
	public InputStream readFile(String fileRelativePath) throws Exception {
		return readFile(fileRelativePath,null);
	}

	/**
	 * 读取文件流
	 * @param fileRelativePath 包含扩展名（可以没有）的文件相对路径，用来确定文件的唯一位置，可直接用作OSS的对象名
	 * 如“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07”，“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07.png”
	 * @param pathPrefix 附件目录配置为路径添加的前缀
	 * @return inputSream 文件输入流
	 * @throws Exception
	 */
	@Override
	public InputStream readFile(String fileRelativePath, String pathPrefix)
			throws Exception {
		String fileFullPath = formatReadFilePath(pathPrefix, fileRelativePath);
		File file = new File(fileFullPath);
		if (file.exists()) {
			return new DecryptionInputStream(new FileInputStream(file));
		}
		if (!StringUtil.isNull(pathPrefix)) {// 如果attFile的cata有值且在cata的根目录没有找到，到admin.do配置的附件根目录下再找一次
			fileFullPath = formatReadFilePath(null, fileRelativePath);
			file = new File(fileFullPath);
			if (file.exists()) {
				return new DecryptionInputStream(new FileInputStream(file));
			}
		}
		return null;
	}


	@Override
	public File readFileToTemp(String fileRelativePath, String md5) throws Exception {
		return readFileToTemp(fileRelativePath,null,md5);
	}

	/**
	 * 读文件数据
	 * @param fileRelativePath 包含扩展名（可以没有）的文件相对路径，用来确定文件的唯一位置，可直接用作OSS的对象名
	 * 如“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07”，“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07.png”
	 * @return 临时文件
	 * @throws Exception
	 */
	@Override
	public File readFileToTemp(String fileRelativePath, String pathPrefix, String md5) throws Exception {
		String fileFullPath = formatReadFilePath(pathPrefix, fileRelativePath);
		File file = new File(fileFullPath);
		File temp = new File(getTempFilePath(fileFullPath,md5));
		if (file.exists()) {
			DecryptionInputStream in = new DecryptionInputStream(new FileInputStream(file));
			IOUtil.write(in,new FileOutputStream(temp));
			return temp;
		}
		if (!StringUtil.isNull(pathPrefix)) {// 如果attFile的cata有值且在cata的根目录没有找到，到admin.do配置的附件根目录下再找一次
			fileFullPath = formatReadFilePath(null, fileRelativePath);
			file = new File(fileFullPath);
			if (file.exists()) {
				InputStream in = new DecryptionInputStream(new FileInputStream(file));
				IOUtil.write(in,new FileOutputStream(temp));
				return temp;
			}
		}

		return null;
	}
	
	@Override
	public List<String> listFileNames(String dirRelativePath) throws Exception {
		return listFileNames(dirRelativePath,null);
	}
	
	@Override
	public List<String> listFileNames(String dirRelativePath, String pathPrefix)
			throws Exception {
		List<String> fileNames = new ArrayList<String>();
		String dirFullPath = formatReadFilePath(pathPrefix, dirRelativePath);
		File dir = new File(dirFullPath);
		if (!dir.exists()) {
			return fileNames;
		}
		File[] dirNames = dir.listFiles();
		for (int i = 0; i < dirNames.length; i++) {
			File file = dirNames[i];
			if(file.isFile()){
				fileNames.add(dirRelativePath + (dirRelativePath.endsWith("/") ? "" : "/") + file.getName());
			}
		}
		return fileNames;
	}
	
	@Override
	public List<String> listSubDirNames(String dirRelativePath) throws Exception {
		return listSubDirNames(dirRelativePath,null);
	}
	
	@Override
	public List<String> listSubDirNames(String dirRelativePath,
			String pathPrefix) throws Exception {
		List<String> fileNames = new ArrayList<String>();
		String dirFullPath = formatReadFilePath(pathPrefix, dirRelativePath);
		File dir = new File(dirFullPath);
		if (!dir.exists()) {
			return fileNames;
		}
		File[] dirNames = dir.listFiles();
		for (int i = 0; i < dirNames.length; i++) {
			File file = dirNames[i];
			if(file.isDirectory()	){
				fileNames.add(dirRelativePath + (dirRelativePath.endsWith("/") ? "" : "/") + file.getName());
			}
		}
		return fileNames;
	}
	
	@Override
	public boolean doesFileExist(String fileRelativePath) throws Exception {
		return doesFileExist(fileRelativePath,null);
	}
	
	@Override
	public boolean doesFileExist(String fileRelativePath, String pathPrefix)
			throws Exception {
		boolean result = false;
		String filePath = formatReadFilePath(pathPrefix, fileRelativePath);
		if (new File(filePath).exists()) {
			result = true;
		}
		return result;
	}
	
	/**
	 * 给出的path是不是目录
	 * @param fileRelativePath 包含扩展名（可以没有）的文件/目录相对路径
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean isDirectory(String fileRelativePath) throws Exception{
		return isDirectory(fileRelativePath,null);
	}
	
	/**
	 * 给出的path是不是目录
	 * @param fileRelativePath 包含扩展名（可以没有）的文件/目录相对路径
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean isDirectory(String fileRelativePath,String pathPrefix) throws Exception{
		boolean exists = doesFileExist(fileRelativePath, pathPrefix);
		if(exists){
			return false;
		}
		String fileFullPath = formatReadFilePath(pathPrefix, fileRelativePath);
		File file = new File(fileFullPath);
		return file.isDirectory();
	}
	
	@Override
	public String formatReadFilePath(String pathPrefix, String fileRelativePath) {
		if(StringUtil.isNull(pathPrefix)){
			return ResourceUtil.getKmssConfigString("kmss.resource.path") + fileRelativePath;
		}
		return pathPrefix + fileRelativePath;
	}
	
	private String formatWriteFilePath(String fileRelativePath) {
		String cfgPath = null;
		SysAttCatalog catalog = this.sysAttUploadDao.getDefultCatalog();
		if (catalog == null) {
			cfgPath = ResourceUtil.getKmssConfigString("kmss.resource.path");
		} else {
			cfgPath = catalog.getFdPath();
		}
		cfgPath = cfgPath.replaceAll("\\\\", "/");
		if (!cfgPath.endsWith("/")) {
			cfgPath += "/";
		}
		fileRelativePath = fileRelativePath.replaceAll("\\\\", "/");
		if (fileRelativePath.startsWith("/")) {
			fileRelativePath = fileRelativePath.substring(1);
		}
		if (fileRelativePath.contains(cfgPath)) {
			return fileRelativePath;
		} else {
			return cfgPath + fileRelativePath;
		}
	}

	@Override
	public void deleteDirectory(String dirRelativePath) throws Exception {
		String fileFullPath = formatWriteFilePath(dirRelativePath);
		
		File file = new File(fileFullPath);
		if (file.exists()) {
			FileUtils.deleteDirectory(file);
		}
	}
	
	@Override
	public void deleteFile(String relativeFilePath) throws Exception {
		if (StringUtils.isEmpty(relativeFilePath)) {
			throw new RuntimeException("relativeFilePath不能为空");
		}
		String path = formatWriteFilePath(relativeFilePath);
		File zip = new File(path);
		if (zip.exists()) {
			zip.delete();
		}
		if (path.lastIndexOf(".") != -1) {
			String localFolderPath = path.substring(0, path.lastIndexOf("."));
			File extendFolder = new File(localFolderPath);
			FileUtils.deleteDirectory(extendFolder);
		}
	}

}
