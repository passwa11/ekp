package com.landray.kmss.sys.filestore.location.interfaces;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 流代理
 */
public interface ISysFileLocationProxyService {

	/**
	 * 写入文件流，新建文件
	 * @param inputSream 文件输入流
	 * @param fileRelativePath 包含扩展名（可以没有）的文件相对路径，用来确定文件的唯一位置，可直接用作OSS的对象名
	 * 如“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07”，“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07.png”
	 * @param header 文件http头
	 * @throws Exception
	 */
	public abstract void writeFile(InputStream inputSream, String fileRelativePath
			,Map<String,String> header) throws Exception;
	
	/**
	 * 写入文件流，新建文件
	 * @param fileData 文件字节数组
	 * @param fileRelativePath 包含扩展名（可以没有）的文件相对路径，用来确定文件的唯一位置，可直接用作OSS的对象名
	 * 如“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07”，“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07.png”
	 * @param header 文件http头
	 * @throws Exception
	 */
	public abstract void writeFile(byte[] fileData, String fileRelativePath
			,Map<String,String> header) throws Exception;
	
	/**
	 * 写入文件流，新建文件
	 * @param inputSream 文件输入流
	 * @param fileRelativePath 包含扩展名（可以没有）的文件相对路径，用来确定文件的唯一位置，可直接用作OSS的对象名
	 * 如“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07”，“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07.png”
	 * @throws Exception
	 */
	public abstract void writeFile(InputStream inputSream, String fileRelativePath
			) throws Exception;

	/**
	 * 非附件写入文件流，新建文件
	 * @param inputSream 文件输入流
	 * @param fileRelativePath 包含扩展名（可以没有）的文件相对路径，用来确定文件的唯一位置，可直接用作OSS的对象名
	 * 如“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07”，“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07.png”
	 * @param exParams 扩展参数
	 * @throws Exception
	 */
	void writeOFile(InputStream inputSream, String fileRelativePath,Map<String,String> exParams
	) throws Exception;

	/**
	 * 写入文件流，新建文件
	 * @param fileData 文件字节数组
	 * @param fileRelativePath 包含扩展名（可以没有）的文件相对路径，用来确定文件的唯一位置，可直接用作OSS的对象名
	 * 如“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07”，“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07.png”
	 * @throws Exception
	 */
	public abstract void writeFile(byte[] fileData, String fileRelativePath
			) throws Exception;
	
	/**
	 * 写文件夹
	 * @param folderPath 源文件夹，绝对路径(eg. C:\Local\Temp\177bd0e728a328484bb327440a797533)
	 * @param relativePath 目标文件夹，相对路径(eg. ui-ext/)
	 * @param zipName 生成的zip文件名，必须带后缀(eg. lux_finance_blue_2.zip)
	 * @param zipRelativePath 生成的zip在本地的相对路径，为空则默认kmss路径目录
	 * @throws Exception
	 */
	void writeFolder(String folderPath, String relativePath, String zipName,String zipRelativePath) throws Exception;

	/**
	 * 非附件写文件夹
	 * @param folderPath 源文件夹，绝对路径(eg. C:\Local\Temp\177bd0e728a328484bb327440a797533)
	 * @param relativePath 目标文件夹，相对路径(eg. ui-ext/)
	 * @param zipName 生成的zip文件名，必须带后缀(eg. lux_finance_blue_2.zip)
	 * @param zipRelativePath 生成的zip在本地的相对路径，为空则默认kmss路径目录
	 * @param exParams 扩展参数
	 * @throws Exception
	 */
	void writeOFolder(String folderPath, String relativePath, String zipName,String zipRelativePath,Map<String,String> exParams) throws Exception;

	/**
	 * 压缩文件夹
	 * @param folderPath 源文件夹，绝对路径(eg. C:\Local\Temp\177bd0e728a328484bb327440a797533)
	 * @param targetPath 目标文件夹，绝对路径(eg. C:\\landray\\kmss\\resource\\ui-ext)
	 * @param zipName 生成的zip文件名，可不带后缀(eg. lux_finance_blue_2.zip)
	 * @return zip File对象
	 * @throws Exception
	 */
	File zipFolder(String folderPath, String targetPath, String zipName) throws Exception;

	/**
	 * 根据relativePath比较本地文件和服务器文件大小
	 * @param sRelativePath 本地文件夹，相对路径(eg. ui-ext/)
	 * @param tRelativePath 目标文件夹，相对路径(eg. ui-ext/)
	 * @param fileName 文件名，必须带后缀(eg. lux_finance_blue_2.zip)
	 * @return 一致返回true，否则返回false
	 * @throws Exception
	 */
	boolean compareFile(String sRelativePath, String tRelativePath, String fileName) throws Exception;

	/**
	 * 下载服务器文件到本地kms配置路径
	 * @param relativePath 目标文件夹，相对路径(eg. ui-ext/)
	 * @param fileName 文件名，必须带后缀(eg. lux_finance_blue_2.zip)
	 * @param targetPath 下载到本地的相对路径(为空则下载到kms根路径)
	 * @return 下载到本地的File对象
	 * @throws Exception
	 */
	File downloadFile(String relativePath, String fileName, String targetPath) throws Exception;
	
	/**
	 * 读取文件流
	 * @param fileRelativePath 包含扩展名（可以没有）的文件相对路径，用来确定文件的唯一位置，可直接用作OSS的对象名
	 * 如“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07”，“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07.png”
	 * @return inputSream 文件输入流
	 * @throws Exception
	 */
	public abstract InputStream readFile(String fileRelativePath) throws Exception;

	/**
	 * 读取文件流，并写入服务器的临时文件中，对于某些特殊业务可以避免写2次以上临时文件，提高效率
	 * @param fileRelativePath 包含扩展名（可以没有）的文件相对路径，用来确定文件的唯一位置，可直接用作OSS的对象名
	 * 如“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07”，“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07.png”
	 * @return inputSream 文件输入流
	 * @throws Exception
	 */
	public abstract File readFileToTemp(String fileRelativePath, String md5) throws Exception;
	
	/**
	 * 文件或目录是否存在
	 * @param fileRelativePath 包含扩展名（可以没有）的文件相对路径，用来确定文件的唯一位置，可直接用作OSS的对象名
	 * 如“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07”，“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07.png”
	 * @return boolean 文件是否存在
	 * @throws Exception
	 */
	public abstract boolean doesFileExist(String fileRelativePath) throws Exception;
	
	/**
	 * 给出的path是不是目录
	 * @param fileRelativePath 包含扩展名（可以没有）的文件/目录相对路径
	 * @return
	 * @throws Exception
	 */
	public abstract boolean isDirectory(String fileRelativePath) throws Exception;
	
	/**
	 * 给出的path是不是目录
	 * @param fileRelativePath 包含扩展名（可以没有）的文件/目录相对路径
	 * @return
	 * @throws Exception
	 */
	public abstract boolean isDirectory(String fileRelativePath,String pathPrefix) throws Exception;
	
	/**
	 * 创建目录
	 * @param dirRelativePath 目录全路径（以/结尾），用来确定目录的唯一位置，可直接用作OSS的对象名
	 * 如“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07/”
	 * @return
	 * @throws Exception
	 */
	public abstract void createDir(String dirRelativePath) throws Exception;
	
	/**
	 * 列出目录下所有文件的名称(不包括子目录)
	 * @param dirRelativePath 目录全路径（以/结尾），用来确定目录的唯一位置，可直接用作OSS的对象名
	 * 如“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07/”
	 * @return
	 * @throws Exception
	 */
	public abstract List<String> listFileNames(String dirRelativePath) throws Exception;
	
	/**
	 * 列出目录下所有一级子目录的名称(不包括间接子目录)
	 * @param dirRelativePath 目录全路径（以/结尾），用来确定目录的唯一位置，可直接用作OSS的对象名
	 * 如“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07/”
	 * @return
	 * @throws Exception
	 */
	public abstract List<String> listSubDirNames(String dirRelativePath) throws Exception;
	
	/**
	 * 分片写入文件流，适用于文件较大需要分片的场景
	 * @param inputSream 分片文件流
	 * @param fileRelativePath 包含扩展名（可以没有）的文件相对路径，用来确定文件的唯一位置，可直接用作OSS的对象名
	 * 如“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07”，“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07.png”
	 * @param partNumber 分片号
	 * @param totalPart 总片数
	 * @throws Exception
	 */
	public abstract void uploadFilePart(InputStream inputSream, String fileRelativePath
			,int partNumber, int totalPart) throws Exception;
	
	/**
	 * 读取文件流
	 * @param fileRelativePath 包含扩展名（可以没有）的文件相对路径，用来确定文件的唯一位置，可直接用作OSS的对象名
	 * 如“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07”，“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07.png”
	 * @param pathPrefix 附件目录配置为路径添加的前缀
	 * @return inputSream 文件输入流
	 * @throws Exception
	 */
	public abstract InputStream readFile(String fileRelativePath
			,String pathPrefix) throws Exception;

	/**
	 * 读取文件流，并写入服务器的临时文件中，对于某些特殊业务可以避免写2次以上临时文件，提高效率
	 * @param fileRelativePath 包含扩展名（可以没有）的文件相对路径，用来确定文件的唯一位置，可直接用作OSS的对象名
	 * 如“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07”，“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07.png”
	 * @return inputSream 文件输入流
	 * @throws Exception
	 */
	public abstract File readFileToTemp(String fileRelativePath,String pathPrefix,String md5) throws Exception;

	/**
	 * 文件是否存在
	 * @param fileRelativePath 包含扩展名（可以没有）的文件相对路径，用来确定文件的唯一位置，可直接用作OSS的对象名
	 * 如“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07”，“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07.png”
	 * @param pathPrefix 附件目录配置为路径添加的前缀
	 * @return boolean 文件是否存在
	 * @throws Exception
	 */
	public abstract boolean doesFileExist(String fileRelativePath
			,String pathPrefix) throws Exception;
	
	/**
	 * 列出目录下所有文件的名称(不包括子目录)
	 * @param dirRelativePath 目录全路径（以/结尾），用来确定目录的唯一位置，可直接用作OSS的对象名
	 * 如“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07/”
	 * @param pathPrefix 附件目录配置为路径添加的前缀
	 * @return
	 * @throws Exception
	 */
	public abstract List<String> listFileNames(String dirRelativePath
			,String pathPrefix) throws Exception;
	
	/**
	 * 列出目录下所有一级子目录的名称(不包括间接子目录)
	 * @param dirRelativePath 目录全路径（以/结尾），用来确定目录的唯一位置，可直接用作OSS的对象名
	 * 如“/2018/10/3/1667bb23178829b3f71f3cc465fa1d07/”
	 * @param pathPrefix 附件目录配置为路径添加的前缀
	 * @return
	 * @throws Exception
	 */
	public abstract List<String> listSubDirNames(String dirRelativePath
			,String pathPrefix) throws Exception;

	/**
	 * 测试文件存储（如OSS）配置是否正确
	 * @param map
	 * @return
	 */
	public abstract boolean testFileStoreConfig(Map<String, String> map) throws Exception;
	
	/**
	 * 获取文件路径
	 * @param pathPrefix
	 * @param fileRelativePath
	 * @return
	 */
	public String formatReadFilePath(String pathPrefix,
			String fileRelativePath);

	/**
	 * 删除目录
	 * @param dirRelativePath
	 */
	public abstract void deleteDirectory(String dirRelativePath) throws Exception;
	
	/**
	 * 删除文件，需带文件后缀
	 * @param relativeFilePath
	 * @throws Exception
	 */
	void deleteFile(String relativeFilePath) throws Exception;

	/**
	 * 依据相对路径生成临时文件path
	 * @param relativeFilePath
	 * @throws Exception
	 */
	String getTempFilePath(String relativeFilePath,String md5) throws Exception;

}
