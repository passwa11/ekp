package com.landray.kmss.sys.filestore.service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.filestore.model.SysAttFileSlice;

public interface ISysAttUploadService {

	/**
	 * 获取数据库当前时间
	 * 
	 * @return current_timestamp
	 * @throws Exception
	 */
	public Date getCurTimestamp() throws Exception;

	/**
	 * 根据文件信息查找对应文件
	 * 
	 * @param fileMd5
	 * @return
	 * @throws Exception
	 */
	public SysAttFile getFileByMd5(String fileMd5, long fileSize)
			throws Exception;

	/**
	 * 根据文件ID查找文件信息
	 * 
	 * @param fileId
	 * @return
	 * @throws Exception
	 */
	public SysAttFile getFileById(String fileId) throws Exception;
	
	/**
	 * 根据文件path查找文件信息
	 * 
	 * @param fileId
	 * @return
	 * @throws Exception
	 */
	public SysAttFile getFileByPath(String filePath) throws Exception;

	/**
	 * 获取对应文件二进制流
	 * 
	 * @param fileId
	 * @return
	 * @throws Exception
	 */
	public InputStream getFileData(String fileId) throws Exception;

	/**
	 * 获取对应扩展文件二进制流
	 * 
	 * @param fileId
	 * @param extend
	 * @return
	 * @throws Exception
	 */
	public InputStream getFileData(String fileId, String extend)
			throws Exception;

	/**
	 * sys_att_file反查在sys_att_main不存在的数据
	 * @return
	 * @throws Exception
	 */
	List<SysAttFile> findNotExistMainBeforeByTime(int limitNum) throws Exception;
	
	/**
	 * 添加文件信息
	 * 
	 * @param fileMd5
	 * @param fileSize
	 * @return
	 * @throws Exception
	 */
	public SysAttFileSlice addFileInfo(String fileMd5, long fileSize)
			throws Exception;

	/**
	 * 单个文件直接上传
	 * 
	 * @param fileMd5
	 * @param fileSize
	 * @param in
	 * @param isCopy
	 *            复制附件还是引用同一份
	 * @return
	 * @throws Exception
	 */
	public String addFile(String fileMd5, long fileSize, InputStream in,
			boolean isCopy,String fileName) throws Exception;

	public String addFile(String fileMd5, long fileSize, InputStream in,
			boolean isCopy, String filePath,String fileName) throws Exception;

	public String addFile(InputStream in,String fileName) throws Exception;

	public String addStreamFile(byte[] buffer,String fileName) throws Exception;

	void deleteRecord(String fileId) throws Exception;
	
	/**
	 * 触发删除文件信息，记录删除临时文档
	 * 
	 * @param fileId
	 * @throws Exception
	 */
	public void delete(String fileId) throws Exception;

	/**
	 * 删除文件信息，并删除真实存储文件
	 * 
	 * @param fileId
	 * @param isDelDiskFile
	 * @throws Exception
	 */
	public void delete(String fileId, boolean isDelDiskFile) throws Exception;

	/**
	 * 根据文件信息，删除对应后缀物理文件
	 * 
	 * @param path
	 * @throws Exception
	 */
	public void deleteFile(SysAttFile file, String suffix) throws Exception;

	/**
	 * 根据文件id，删除对应后缀物理文件
	 * 
	 * @param path
	 * @throws Exception
	 */
	public void deleteFile(String fileId, String suffix) throws Exception;

	/**
	 * 更新文件
	 * 
	 * @param fileId
	 * @param in
	 * @throws Exception
	 */
	public String updateFile(String mainId, String fileId, InputStream in)
			throws Exception;

	/**
	 * 获取文件的切片信息
	 * 
	 * @param fileID
	 * @return
	 * @throws Exception
	 */
	public SysAttFileSlice getNextFileSlice(String fileID) throws Exception;

	/**
	 * 获取已上传的切片总大小
	 * 
	 * @param fileID
	 * @return
	 * @throws Exception
	 */
	public long getUploadedCount(String fileID) throws Exception;

	/**
	 * 上传当前切片文件，并返回当前切片对象
	 * 
	 * @param sliceid
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public SysAttFileSlice updateFileSlice(String sliceid, InputStream in)
			throws Exception;

	/**
	 * 合并上传的切片到文件系统
	 * 
	 * @param attFile
	 * @return
	 * @throws Exception
	 */
	public boolean combFileSlice(SysAttFile attFile) throws Exception;

	/**
	 * 获取文件在服务器中的相对地址
	 * 
	 * @param attFile
	 * @return
	 */
	public String getAbsouluteFilePath(SysAttFile attFile) throws Exception;

	/**
	 * 获取文件在服务器中的相对地址
	 * 
	 * @param fileId
	 * @return
	 */
	public String getAbsouluteFilePath(String fileId) throws Exception;
	
	/**
	 * 获取文件在服务器中的绝对地址
	 * @param attFile
	 * @param isFullPath
	 * @return
	 * @throws Exception
	 */
	public String getAbsouluteFilePath(SysAttFile attFile, Boolean isFullPath)
			throws Exception;
	
	/**
	 * 获取默认上传根路径
	 * 
	 * @return
	 */
	public String getDefaultCatalog();
	
	/**
	 * 根据id获取存储路径
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String generatePath(String id) throws Exception;
	
	/**
	 * 保存附件文件对象
	 * 
	 * @param id
	 * @param fileSize
	 * @param fileMd5
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public SysAttFile addFile(String id, long fileSize, String fileMd5,
			String path) throws Exception;
}
