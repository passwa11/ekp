package com.landray.kmss.sys.filestore.location.interfaces;

import java.io.InputStream;
import java.util.List;

/**
 * 文件批量入库引擎扩展接口
 * 
 * @author
 *
 */
public interface ISysFileLocationBatchTypeService {

	/**
	 * 是否有效
	 * 
	 * @return
	 */
	public Boolean enable();

	/**
	 * 读取文件
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public InputStream readFile(String filePath) throws Exception;

	/**
	 * 列表所有子文件
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public List<String> listFileNames(String filePath) throws Exception;

	/**
	 * 列出所有子目录
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public List<String> listSubDirNames(String filePath) throws Exception;

	/**
	 * 文件路径分隔符
	 * 
	 * @return
	 */
	public String separatorChar();
}
