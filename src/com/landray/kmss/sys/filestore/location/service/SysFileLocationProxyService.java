package com.landray.kmss.sys.filestore.location.service;

import com.landray.kmss.sys.filestore.location.interfaces.ISysFileLocationProxyService;
import com.landray.kmss.sys.filestore.location.log.model.SysFileLocationLog;
import com.landray.kmss.sys.filestore.location.log.service.SysFileLocationLogWriter;
import org.slf4j.Logger;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class SysFileLocationProxyService
		implements ISysFileLocationProxyService {

	private static Logger logger =
			org.slf4j.LoggerFactory.getLogger(SysFileLocationProxyService.class);

	/**
	 * 具体的流代理类
	 */
	private ISysFileLocationProxyService proxyService;

	public SysFileLocationProxyService(
			ISysFileLocationProxyService proxyService) {

		super();
		this.proxyService = proxyService;

	}

	@Override
	public void writeFile(InputStream inputSream, final String fileRelativePath,
			final Map<String, String> header) throws Exception {

		try {
			proxyService.writeFile(inputSream, fileRelativePath, header);
		} catch (Exception e) {
			logger.error("写文件报错：", e);
			throw e;
		} finally{
			if(inputSream != null){
				try {
					inputSream.close();
				} catch (Exception e) {
					logger.error("close inputStream error：", e);
				}
			}
		}

		new SysFileLocationLogWriter() {

			@Override
			public void bulidLog(SysFileLocationLog log) {

				log.setFdName("写文件");
				log.setFdReq("InputStream", fileRelativePath,
						header.toString());
			}
		}.write();

	}

	@Override
	public void writeFile(byte[] fileData, final String fileRelativePath,
			final Map<String, String> header) throws Exception {

		try {
			proxyService.writeFile(fileData, fileRelativePath, header);
		} catch (Exception e) {
			logger.error("写文件报错：", e);
			throw e;
		}

		new SysFileLocationLogWriter() {

			@Override
			public void bulidLog(SysFileLocationLog log) {

				log.setFdName("写文件");
				log.setFdReq("byte[]", fileRelativePath, header.toString());
			}
		}.write();

	}

	@Override
	public void writeFile(InputStream inputSream, final String fileRelativePath)
			throws Exception {
		try {
			proxyService.writeFile(inputSream, fileRelativePath);
		} catch (Exception e) {
			logger.error("写文件报错：", e);
			throw e;
		} finally{
			if(inputSream != null){
				try {
					inputSream.close();
				} catch (Exception e) {
					logger.error("close inputStream error：", e);
				}
			}
		}

		new SysFileLocationLogWriter() {

			@Override
			public void bulidLog(SysFileLocationLog log) {

				log.setFdName("写文件");
				log.setFdReq("InputStream", fileRelativePath);
			}
		}.write();

	}

	@Override
	public void writeOFile(InputStream inputSream, final String fileRelativePath, Map<String, String> exParams) throws Exception {
		try {
			proxyService.writeOFile(inputSream, fileRelativePath, exParams);
		} catch (Exception e) {
			logger.error("写文件报错：", e);
			throw e;
		} finally{
			if(inputSream != null){
				try {
					inputSream.close();
				} catch (Exception e) {
					logger.error("close inputStream error：", e);
				}
			}
		}
		new SysFileLocationLogWriter() {

			@Override
			public void bulidLog(SysFileLocationLog log) {
				log.setFdName("写文件");
				log.setFdReq("InputStream", fileRelativePath);
			}
		}.write();
	}

	@Override
	public void writeFile(byte[] fileData, final String fileRelativePath)
			throws Exception {

		try {
			proxyService.writeFile(fileData, fileRelativePath);
		} catch (Exception e) {
			logger.error("写文件报错：", e);
			throw e;
		}

		new SysFileLocationLogWriter() {

			@Override
			public void bulidLog(SysFileLocationLog log) {

				log.setFdName("写文件");
				log.setFdReq("byte[]", fileRelativePath);
			}
		}.write();

	}

	@Override
	public void writeOFolder(String folderPath, final String relativePath, final String zipName, String zipRelativePath, Map<String, String> exParams) throws Exception {
		try {
			proxyService.writeOFolder(folderPath, relativePath,zipName,zipRelativePath,exParams);
		} catch (Exception e) {
			logger.error("写文件夹报错：", e);
			throw e;
		}

		new SysFileLocationLogWriter() {

			@Override
			public void bulidLog(SysFileLocationLog log) {
				log.setFdName("写文件夹");
				log.setFdReq("String", relativePath + zipName);
			}
		}.write();
	}
	
	@Override
	public void writeFolder(String folderPath, final String relativePath, final String zipName,String zipRelativePath) throws Exception {
		try {
			proxyService.writeFolder(folderPath, relativePath,zipName,zipRelativePath);
		} catch (Exception e) {
			logger.error("写文件夹报错：", e);
			throw e;
		}

		new SysFileLocationLogWriter() {

			@Override
			public void bulidLog(SysFileLocationLog log) {
				log.setFdName("写文件夹");
				log.setFdReq("String", relativePath + zipName);
			}
		}.write();
	}

	@Override
	public File zipFolder(String folderPath, String targetPath, String zipName) throws Exception {
		return proxyService.zipFolder(folderPath,targetPath,zipName);
	}

	@Override
	public boolean compareFile(String sRelativePath, String tRelativePath, String fileName) throws Exception {
		return proxyService.compareFile(sRelativePath, tRelativePath, fileName);
	}

	@Override
	public File downloadFile(final String relativePath, final String fileName, String targetPath) throws Exception {
		File file = proxyService.downloadFile(relativePath, fileName, targetPath);
		new SysFileLocationLogWriter() {

			@Override
			public void bulidLog(SysFileLocationLog log) {
				log.setFdName("下载文件");
				log.setFdReq("String", relativePath + fileName);
			}
		}.write();
		return file;
	}

	@Override
	public InputStream readFile(final String fileRelativePath)
			throws Exception {

		InputStream inputStream;

		try {
			inputStream = proxyService.readFile(fileRelativePath);
		} catch (Exception e) {
			logger.error("读文件报错：", e);
			throw e;
		}

		new SysFileLocationLogWriter() {

			@Override
			public void bulidLog(SysFileLocationLog log) {

				log.setFdName("读文件");
				log.setFdReq(fileRelativePath);
				log.setFdResp("InputStream");
			}
		}.write();

		return inputStream;

	}

	@Override
	public File readFileToTemp(String fileRelativePath,String md5) throws Exception {
		return proxyService.readFileToTemp(fileRelativePath,md5);
	}

	@Override
	public boolean doesFileExist(final String fileRelativePath)
			throws Exception {

		final Boolean isExist;

		try {
			isExist = proxyService.doesFileExist(fileRelativePath);
		} catch (Exception e) {
			logger.error("判断文件是否存在报错：", e);
			throw e;
		}

		new SysFileLocationLogWriter() {

			@Override
			public void bulidLog(SysFileLocationLog log) {

				log.setFdName("文件是否存在");
				log.setFdReq(fileRelativePath);
				log.setFdResp(isExist ? "存在" : "不存在");
			}
		}.write();

		return isExist;
	}

	@Override
	public boolean isDirectory(final String fileRelativePath) throws Exception {

		final Boolean isDirectory;

		try {
			isDirectory = proxyService.isDirectory(fileRelativePath);
		} catch (Exception e) {
			logger.error("判断是否为目录报错：", e);
			throw e;
		}

		new SysFileLocationLogWriter() {

			@Override
			public void bulidLog(SysFileLocationLog log) {

				log.setFdName("文件是否为目录");
				log.setFdReq(fileRelativePath);
				log.setFdResp(isDirectory ? "是" : "不是");
			}
		}.write();

		return isDirectory;
	}

	@Override
	public boolean isDirectory(final String fileRelativePath,
			final String pathPrefix) throws Exception {

		final Boolean isDirectory;

		try {
			isDirectory =
					proxyService.isDirectory(fileRelativePath, pathPrefix);
		} catch (Exception e) {
			logger.error("判断是否为目录报错：", e);
			throw e;
		}

		new SysFileLocationLogWriter() {

			@Override
			public void bulidLog(SysFileLocationLog log) {

				log.setFdName("文件是否为目录");
				log.setFdReq(fileRelativePath, pathPrefix);
				log.setFdResp(isDirectory ? "是" : "不是");
			}
		}.write();

		return isDirectory;
	}

	@Override
	public void createDir(final String dirRelativePath) throws Exception {

		try {

			proxyService.createDir(dirRelativePath);

		} catch (Exception e) {
			logger.error("创建目录报错：", e);
			throw e;
		}

		new SysFileLocationLogWriter() {

			@Override
			public void bulidLog(SysFileLocationLog log) {

				log.setFdName("创建目录");
				log.setFdReq(dirRelativePath);
			}
		}.write();

	}

	@Override
	public List<String> listFileNames(final String dirRelativePath)
			throws Exception {

		final List<String> names;

		try {

			names = proxyService.listFileNames(dirRelativePath);

		} catch (Exception e) {
			logger.error("列出目录下所有文件名称报错：", e);
			throw e;
		}

		new SysFileLocationLogWriter() {

			@Override
			public void bulidLog(SysFileLocationLog log) {

				log.setFdName("列出目录下所有文件名称");
				log.setFdReq(dirRelativePath);
				log.setFdResp(names.toString());
			}
		}.write();

		return names;
	}

	@Override
	public List<String> listSubDirNames(final String dirRelativePath)
			throws Exception {

		final List<String> names;

		try {

			names = proxyService.listSubDirNames(dirRelativePath);

		} catch (Exception e) {
			logger.error("列出目录下所有子目录名称报错：", e);
			throw e;
		}

		new SysFileLocationLogWriter() {

			@Override
			public void bulidLog(SysFileLocationLog log) {

				log.setFdName("列出目录下所有子目录名称");
				log.setFdReq(dirRelativePath);
				log.setFdResp(names.toString());

			}
		}.write();

		return names;
	}

	@Override
	public void uploadFilePart(InputStream inputSream,
			final String fileRelativePath, final int partNumber,
			final int totalPart) throws Exception {

		try {

			proxyService.uploadFilePart(inputSream, fileRelativePath,
					partNumber, totalPart);

		} catch (Exception e) {
			logger.error("上传文件报错：", e);
			throw e;
		} finally{
			if(inputSream != null){
				try {
					inputSream.close();
				} catch (Exception e) {
					logger.error("upload inputStream error：", e);
				}
			}
		}

		new SysFileLocationLogWriter() {

			@Override
			public void bulidLog(SysFileLocationLog log) {

				log.setFdName("上传文件");
				log.setFdReq("InputStream", fileRelativePath,
						String.valueOf(partNumber), String.valueOf(totalPart));

			}
		}.write();

	}

	@Override
	public InputStream readFile(final String fileRelativePath,
			final String pathPrefix) throws Exception {

		InputStream inputStream;

		try {

			inputStream = proxyService.readFile(fileRelativePath, pathPrefix);

		} catch (Exception e) {
			logger.error("读文件报错：", e);
			throw e;
		}

		new SysFileLocationLogWriter() {

			@Override
			public void bulidLog(SysFileLocationLog log) {

				log.setFdName("读文件");
				log.setFdReq(fileRelativePath, pathPrefix);
				log.setFdResp("InputStream");
			}
		}.write();

		return inputStream;
	}

	@Override
	public File readFileToTemp(String fileRelativePath, String pathPrefix, String md5) throws Exception {
		return proxyService.readFileToTemp(fileRelativePath, pathPrefix, md5);
	}

	@Override
	public boolean doesFileExist(final String fileRelativePath,
			final String pathPrefix) throws Exception {

		final Boolean isExist;

		try {

			isExist = proxyService.doesFileExist(fileRelativePath, pathPrefix);

		} catch (Exception e) {
			logger.error("判断文件是否存在报错：", e);
			throw e;
		}

		new SysFileLocationLogWriter() {

			@Override
			public void bulidLog(SysFileLocationLog log) {

				log.setFdName("判断文件是否存在");
				log.setFdReq(fileRelativePath, pathPrefix);
				log.setFdResp(isExist ? "存在" : "不存在");
			}
		}.write();

		return isExist;
	}

	@Override
	public List<String> listFileNames(final String dirRelativePath,
			final String pathPrefix) throws Exception {

		final List<String> names;

		try {

			names = proxyService.listFileNames(dirRelativePath, pathPrefix);

		} catch (Exception e) {
			logger.error("列出目录下所有文件名称报错：", e);
			throw e;
		}

		new SysFileLocationLogWriter() {

			@Override
			public void bulidLog(SysFileLocationLog log) {

				log.setFdName("列出目录下所有文件名称");
				log.setFdReq(dirRelativePath, pathPrefix);
				log.setFdResp(names.toString());
			}
		}.write();

		return names;
	}

	@Override
	public List<String> listSubDirNames(final String dirRelativePath,
			final String pathPrefix) throws Exception {

		final List<String> names;

		try {

			names = proxyService.listSubDirNames(dirRelativePath, pathPrefix);

		} catch (Exception e) {
			logger.error("列出目录下所有子目录名称报错：", e);
			throw e;
		}

		new SysFileLocationLogWriter() {

			@Override
			public void bulidLog(SysFileLocationLog log) {

				log.setFdName("列出目录下所有子目录");
				log.setFdReq(dirRelativePath, pathPrefix);
				log.setFdResp(names.toString());
			}
		}.write();

		return names;
	}

	@Override
	public boolean testFileStoreConfig(Map<String, String> map)
			throws Exception {

		return proxyService.testFileStoreConfig(map);

	}

	@Override
	public String formatReadFilePath(String pathPrefix,
			String fileRelativePath) {
		return proxyService.formatReadFilePath(pathPrefix, fileRelativePath);
	}

	/**
	 * 暂时空实现，如有需要由子类覆盖
	 */
	@Override
	public void deleteDirectory(String dirRelativePath) throws Exception {
		proxyService.deleteDirectory(dirRelativePath);		
	}
	
	@Override
	public void deleteFile(final String relativeFilePath) throws Exception {
		proxyService.deleteFile(relativeFilePath);
		new SysFileLocationLogWriter() {

			@Override
			public void bulidLog(SysFileLocationLog log) {
				log.setFdName("删除文件");
				log.setFdReq("String", relativeFilePath);
			}
		}.write();
	}

	/**
	 * 依据相对路径生成临时文件path
	 * @param relativeFilePath
	 * @throws Exception
	 */
	@Override
    public String getTempFilePath(String relativeFilePath, String md5) throws Exception {
		return proxyService.getTempFilePath(relativeFilePath,md5);
	}

}
