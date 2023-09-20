package com.landray.kmss.sys.filestore.location.service;

import com.landray.kmss.sys.filestore.location.interfaces.ISysFileLocationProxyService;
import com.landray.kmss.util.FileUtil;
import com.landray.kmss.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.zeroturnaround.zip.ZipUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractSysFileLocationProxyService implements ISysFileLocationProxyService {
	
	@Override
	public File zipFolder(String folderPath, String targetPath, String zipName) throws Exception {
		if (StringUtils.isEmpty(folderPath) || StringUtils.isEmpty(targetPath)) {
			throw new RuntimeException("folderPath、targetPath不能为空");
		}
		File folder = new File(folderPath);
		File targetFolder = new File(targetPath);
		if (!folder.isDirectory()) {
			throw new RuntimeException("folderPath不是文件夹");
		}
		if (targetFolder.exists()) {
			if (!targetFolder.isDirectory()) {
				throw new RuntimeException("targetFolder不是文件夹");
			}
		}else{
			if (targetFolder.mkdirs() && !targetFolder.isDirectory()) {
				throw new RuntimeException("targetFolder不是文件夹");
			}
		}
		if (StringUtils.isEmpty(zipName)) {
			zipName = targetFolder.getName() + ".zip";
		}
		if (!zipName.endsWith(".zip")) {
			zipName += ".zip";
		}
		File zipFile = new File(targetPath,zipName);
		zipFile.delete();
		ZipUtil.pack(folder, zipFile);
		return zipFile;
	}
	
	/**
	 * 新建目录
	 * @param dirRelativePath 目录相对路径，以/开头，以/结尾
	 * 如“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07/”
	 * @throws Exception
	 */
	@Override
	public void createDir(String dirRelativePath) throws Exception{
		writeFile(new ByteArrayInputStream(new byte[0]),dirRelativePath);
	}
	
	/**
	 * 分片上传文件，默认实现不支持，如需要支持分片上传可以覆盖此方法
	 * @param inputSream 文件输入流
	 * @param fileRelativePath 包含扩展名（可以没有）的文件相对路径，用来确定文件的唯一位置，可直接用作OSS的对象名
	 * 如“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07”，“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07.png”
	 * @throws Exception
	 */
	@Override
	public void uploadFilePart(InputStream inputSream, String fileRelativePath,
			int partNumber, int totalPart) throws Exception {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * 给出的path是不是目录
	 * @param fileRelativePath 包含扩展名（可以没有）的文件/目录相对路径
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean isDirectory(String fileRelativePath,String pathPrefix) throws Exception{
		return isDirectory(fileRelativePath);
	}

	/**
	 * 写入文件流，新建文件
	 * @param fileData 文件字节数组
	 * @param fileRelativePath 包含扩展名（可以没有）的文件相对路径，用来确定文件的唯一位置，可直接用作OSS的对象名
	 * 如“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07”，“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07.png”
	 * @throws Exception
	 */
	@Override
	public void writeFile(byte[] fileData, String fileRelativePath,Map<String,String> header) throws Exception{
		writeFile(new ByteArrayInputStream(fileData), fileRelativePath,header);
	}
	
	public void checkBeforeWriteFolder(String folderPath, String relativePath) throws Exception{
		if (StringUtils.isEmpty(folderPath) || StringUtils.isEmpty(relativePath)) {
			throw new RuntimeException("源路径和目标路径不能为空");
		}
		File folder = new File(folderPath);
		if(!folder.exists()){
			throw new RuntimeException(folderPath + " 源路径不存在");
		}
		if (!folder.isDirectory()) {
			throw new RuntimeException(folderPath + " 源路径不是文件夹");
		}
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
	public InputStream readFile(String fileRelativePath
			,String pathPrefix) throws Exception{
		return readFile(fileRelativePath);
	}

	/**
	 * 读取文件流，并写入服务器的临时文件中，对于某些特殊业务可以避免写2次以上临时文件，提高效率
	 * @param fileRelativePath 包含扩展名（可以没有）的文件相对路径，用来确定文件的唯一位置，可直接用作OSS的对象名
	 * 如“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07”，“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07.png”
	 * @return inputSream 文件输入流
	 * @throws Exception
	 */
	@Override
	public File readFileToTemp(String fileRelativePath,String md5) throws Exception{
		return null;
	}


	/**
	 * 读取文件流，并写入服务器的临时文件中，对于某些特殊业务可以避免写2次以上临时文件，提高效率
	 * @param fileRelativePath 包含扩展名（可以没有）的文件相对路径，用来确定文件的唯一位置，可直接用作OSS的对象名
	 * 如“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07”，“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07.png”
	 * @return inputSream 文件输入流
	 * @throws Exception
	 */
	@Override
	public File readFileToTemp(String fileRelativePath,String pathPrefix, String md5) throws Exception {
		return readFileToTemp(fileRelativePath, md5);
	}
	
	/**
	 * 文件是否存在
	 * @param fileRelativePath 包含扩展名（可以没有）的文件相对路径，用来确定文件的唯一位置，可直接用作OSS的对象名
	 * 如“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07”，“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07.png”
	 * @param pathPrefix 附件目录配置为路径添加的前缀
	 * @return boolean 文件是否存在
	 * @throws Exception
	 */
	@Override
	public boolean doesFileExist(String fileRelativePath
			,String pathPrefix) throws Exception{
		return doesFileExist(fileRelativePath);
	}
	
	/**
	 * 列出目录下所有文件的名称(不包括子目录)
	 * @param dirRelativePath 目录全路径（以/结尾），用来确定目录的唯一位置，可直接用作OSS的对象名
	 * 如“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07/”
	 * @param pathPrefix 附件目录配置为路径添加的前缀
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<String> listFileNames(String dirRelativePath
			,String pathPrefix) throws Exception{
		return listFileNames(dirRelativePath);
	}
	
	/**
	 * 列出目录下所有一级子目录的名称(不包括间接子目录)
	 * @param dirRelativePath 目录全路径（以/结尾），用来确定目录的唯一位置，可直接用作OSS的对象名
	 * 如“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07/”
	 * @param pathPrefix 附件目录配置为路径添加的前缀
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<String> listSubDirNames(String dirRelativePath
			,String pathPrefix) throws Exception{
		return listSubDirNames(dirRelativePath);
	}
	
	/**
	 * 测试文件存储（如OSS）配置是否正确
	 * @param map
	 * @return
	 */
	@Override
	public boolean testFileStoreConfig(Map<String, String> map) throws Exception{
		return false;
	}
	
	/**
	 * 写入文件流，新建文件
	 * @param inputSream 文件输入流
	 * @param fileRelativePath 包含扩展名（可以没有）的文件相对路径，用来确定文件的唯一位置，可直接用作OSS的对象名
	 * 如“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07”，“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07.png”
	 * @throws Exception
	 */
	@Override
	public void writeFile(InputStream inputSream, String fileRelativePath
			) throws Exception{
		writeFile(inputSream,fileRelativePath,new HashMap<String,String>());
	}
	
	/**
	 * 写入文件流，新建文件
	 * @param fileData 文件字节数组
	 * @param fileRelativePath 包含扩展名（可以没有）的文件相对路径，用来确定文件的唯一位置，可直接用作OSS的对象名
	 * 如“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07”，“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07.png”
	 * @throws Exception
	 */
	@Override
	public void writeFile(byte[] fileData, String fileRelativePath
			) throws Exception{
		writeFile(fileData,fileRelativePath,new HashMap<String,String>());
	}
	
	
	/**
	 * 获取文件路径
	 * @param pathPrefix
	 * @param fileRelativePath
	 * @return
	 */
	@Override
	public String formatReadFilePath(String pathPrefix, String fileRelativePath) {
		return fileRelativePath;
	}
	
	/**
	 * 暂时空实现，如有需要由子类覆盖
	 */
	@Override
	public void deleteDirectory(String dirRelativePath) throws Exception {
		
	}

	/**
	 * 暂时空实现，如有需要由子类覆盖
	 */
	@Override
	public void deleteFile(String relativeFilePath) throws Exception {

	}

	/**
	 * 依据相对路径生成临时文件path
	 * @param relativeFilePath
	 * @throws Exception
	 */
	@Override
	public String getTempFilePath(String relativeFilePath,String md5) throws Exception {
		String tempDir = FileUtil.getSystemTempPath();
		String fileId = relativeFilePath.substring(relativeFilePath.lastIndexOf("/"))
				+ "_" + (StringUtils.isEmpty(md5) ? MD5Util.getMD5String(relativeFilePath) : md5);
		return tempDir + fileId;
	}

}
