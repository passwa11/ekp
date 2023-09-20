package com.landray.kmss.sys.filestore.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.landray.kmss.sys.filestore.model.SysAttCatalog;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.filestore.model.SysAttFileSlice;

public interface ISysAttUploadDao {
	
	/**
	 * 获取session对象
	 * @return
	 */
	public Session getHibernateSession();
	
	/**
	 * 清理session
	 * @return
	 */
	public void clearHibernateSession();
	
	/**
	 * 清理session缓存
	 * @return
	 */
	public void flushHibernateSession() ;
	
	/**
	 * 添加对象至数据库
	 * @param modelObj
	 * @throws Exception
	 */
	public void add(Object modelObj) throws Exception;
	
	/**
	 * 删除对象
	 * @param modelObj
	 * @throws Exception
	 */
	public void delete(Object modelObj) throws Exception;
	
	/**
	 * 通过主键，加载对象
	 * @param id
	 * @param modelInfo
	 * @param noLazy
	 * @return
	 * @throws Exception
	 */
	public Object findByPrimaryKey(String id, Object modelInfo,
			boolean noLazy) throws Exception;
	
	/**
	 * 修改对象
	 * @param modelObj
	 * @throws Exception
	 */
	public void update(Object modelObj) throws Exception ;
	
	/**
	 * 获取数据库当前时间
	 * @return
	 * @throws Exception
	 */
	public Date getCurTimestamp() throws Exception;
	
	/**
	 * 根据md5信息查找文件信息
	 * @param fileMd5
	 * @param fileSize
	 * @return
	 * @throws Exception
	 */
	public SysAttFile getFileByMd5(String fileMd5, long fileSize) throws Exception;
	
	/**
	 * 根据文件id，获取已上传附件总数
	 * @param fileID
	 * @return
	 * @throws Exception
	 */
	public long getUploadedCount(String fileID) throws Exception;
	
	/**
	 * 获取默认目录
	 * @return
	 */
	public SysAttCatalog getDefultCatalog() ;
	
	/**
	 * 获取下一个切片信息
	 * @param fileID
	 * @return
	 * @throws Exception
	 */
	public SysAttFileSlice getNextFileSlice(String fileID) throws Exception;
	
	/**
	 * sys_att_file反查在sys_att_main不存在的数据
	 * @return
	 * @throws Exception
	 */
	List<SysAttFile> findNotExistMainBeforeByTime(int limitNum) throws Exception;
	
	/**
	 * 清理文件相关切片信息
	 * @param fileID
	 * @return
	 * @throws Exception
	 */
	public int clearFileSlice(String fileID) throws Exception;
}
