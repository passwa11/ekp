package com.landray.kmss.sys.attachment.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseCoreInnerService;
import com.landray.kmss.sys.attachment.dao.ISysAttMainCoreInnerDao;
import com.landray.kmss.sys.attachment.forms.SysAttMainForm;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.model.SysAttRtfData;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

/**
 * 创建日期 2006-九月-04
 *
 * @author 叶中奇 附件业务对象接口
 */
public interface ISysAttMainCoreInnerService extends IBaseCoreInnerService {

	/**
	 * 批量添加附件信息
	 *
	 * @param sysAttMains
	 * @return
	 * @throws Exception
	 */
	public List add(List sysAttMains) throws Exception;

	/**
	 * 添加附件
	 * @param modelObj
	 * @param newFieldId 是否新文件
	 * @param addQueue 是否添加到转换队列
	 * @return
	 * @throws Exception
	 */
	String add(IBaseModel modelObj,Boolean newFieldId,Boolean addQueue) throws Exception;

	/**
	 * 批量修改附件信息
	 *
	 * @param sysAttMains
	 * @return
	 * @throws Exception
	 */
	public List update(List sysAttMains) throws Exception;

	/**
	 * 根据id删除附件
	 *
	 * @param fdId
	 * @throws Exception
	 */
	public void deleteAtt(String fdId) throws Exception;

	/**
	 * 删除sys_att_file记录
	 *
	 * @param fdId
	 * @return
	 * @throws Exception
	 */
	void deleteAttFile(String fdId) throws Exception;

	/**
	 * 附件信息复制
	 *
	 * @param sysAttMain
	 * @return
	 * @throws Exception
	 */
	public SysAttMain clone(SysAttMain sysAttMain) throws Exception;

	/**
	 * 附件信息复制
	 *
	 * @param sysAttMain
	 * @param newFileId
	 * @return
	 * @throws Exception
	 */
	public SysAttMain clone(SysAttMain sysAttMain,Boolean newFileId) throws Exception;

	/**
	 * 根据主文档信息查找附件列表
	 *
	 * @param modelName
	 * @param modelId
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public List findByModelKey(String modelName, String modelId, String key)
			throws Exception;

	/**
	 * 根据主文档信息查找该文档中附件key列表
	 *
	 * @param modelName
	 * @param modelId
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public List findModelKeys(String modelName, String modelId)
			throws Exception;

	/**
	 * 根据主文档信息查找该文档的附件列表
	 * @param modelName 主文档的模块名称
	 * @param modelId  主文档的id
	 */

	public List findAttListByModel(String modelName,String modelId) throws Exception;

	/**
	 * 将附件输出到输出流out中
	 *
	 * @param fdId
	 * @param out
	 * @throws Exception
	 */
	public void findData(String fdId, OutputStream out) throws Exception;

	/**
	 * 将扩展附件输出到扩展输出流out中
	 *
	 * @param fdId
	 * @param out
	 * @throws Exception
	 */
	public void findData(String fdId, String extend, OutputStream out)
			throws Exception;

	/**
	 * 将视频附件输出到扩展输出流out中
	 *
	 * @param fdId
	 * @param out
	 * @param request
	 * @throws Exception
	 */
	public void findData(String fdId, String extend, OutputStream out,
						 HttpServletRequest request) throws Exception;

	/**
	 * 增加RTF附件
	 *
	 * @param sysAttRtfData
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public String addRtfData(SysAttRtfData sysAttRtfData, InputStream in)
			throws Exception;

	/**
	 * 将RTF附件输出到输出流out中
	 *
	 * @param fdId
	 * @param out
	 * @throws Exception
	 */
	public void findRtfData(String fdId, OutputStream out,
							HttpServletRequest request, HttpServletResponse response)
			throws Exception;

	/**
	 * 根据主键查找RTF附件
	 *
	 * @param fdId
	 * @return
	 * @throws Exception
	 */
	public SysAttRtfData findRtfDataByPrimaryKey(String fdId) throws Exception;

	/**
	 * 获取某一段时间内的附件列表
	 *
	 * @param begin
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List findAttData(Date begin, Date end) throws Exception;

	/**
	 * 根据附件id查找多个附件
	 *
	 * @param fdId
	 * @return
	 * @throws Exception
	 */
	public List findModelsByIds(String[] fdId) throws Exception;

	/**
	 * 获取附件处理DAO
	 *
	 * @return
	 */
	public ISysAttMainCoreInnerDao getSysAttMainDao();

	/**
	 * 后台添加附件的方法
	 *
	 * @param model
	 *            附件附属域模型
	 * @param fdKey
	 *            区分附件在域模型中的位置的key
	 * @param content
	 *            附件的二进制内容
	 * @param fileName
	 *            附件的名称。注意附件名称准确，如图片要使用1.jpg之类的名字，否则在线浏览在某些浏览器会有问题
	 * @param fdAttType
	 *            如果是图片，“pic”，如果是附件，"attachment"
	 * @return 返回该附件的访问url
	 * @throws Exception
	 */
	String addAttachment(IBaseModel model, String fdKey, byte[] content,
						 String fileName, String fdAttType) throws Exception;

	String addAttachment(IBaseModel model, String fdKey, byte[] content, String fileName, String fdAttType,boolean newFileId)
			throws Exception;

	String addAttachment(IBaseModel model, String fdKey, byte[] content, String fileName, String fdAttType,boolean newFileId, Integer fdOrder)
			throws Exception;

	String addAttachment(IBaseModel model, String fdKey, byte[] content, String fileName, String fdAttType, boolean newFileId, Integer fdOrder, boolean addQueue)
			throws Exception;

	/**
	 * 后台添加单一附件的方法
	 *
	 * @param model
	 *            附件附属域模型
	 * @param fdKey
	 *            区分附件在域模型中的位置的key
	 * @param content
	 *            附件的二进制内容
	 * @param fileName
	 *            附件的名称。注意附件名称准确，如图片要使用1.jpg之类的名字，否则在线浏览在某些浏览器会有问题
	 * @param fdAttType
	 *            如果是图片，“pic”，如果是附件，"attachment"
	 * @return 返回该附件的访问url
	 * @throws Exception
	 */
	public String addSingleAttachment(IBaseModel model, String fdKey,
									  byte[] content, String fileName, String fdAttType) throws Exception;

	/**
	 * 根据主文档对象，获取文档中附件列表（列表数据非对象，[id，name，contentType]）
	 *
	 * @param mainModel
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public List getCorePropsModels(IBaseModel mainModel, String key)
			throws Exception;

	/**
	 * 获取数据库当前时间
	 *
	 * @return current_timestamp
	 * @throws Exception
	 */
	public Date getCurTimestamp() throws Exception;

	/**
	 * 定时清理任务
	 *
	 * @param context
	 * @throws Exception
	 */
	public void updateClearAtt(SysQuartzJobContext context) throws Exception;

	/**
	 * 定时删除临时文件任务
	 *
	 * @param context
	 * @throws Exception
	 */
	public void delTmpFile(SysQuartzJobContext context) throws Exception;

	public Page findPage(HQLInfo hqlInfo, String modelName, String key)
			throws Exception;

	public SysAttMain addAttachment(IBaseModel model, String fdKey,
									InputStream content, String fileName, String fdAttType,
									Double fileSize, String PathName) throws Exception;

	public SysAttMain addAttachment(String fdModelId, String fdModelName,
									String fdKey, byte[] content, String fileName,
									String fdAttType) throws Exception;

	public void delete(IBaseModel model, String fdKey) throws Exception;

	/**
	 * 获取附件流
	 * @param fdId
	 * @return
	 * @throws Exception
	 */
	public InputStream getInputStream(String fdId) throws Exception;

	/**
	 * 获取附件流
	 * @param sysAttMain
	 * @return
	 * @throws Exception
	 */
	public InputStream getInputStream(SysAttMain sysAttMain) throws Exception;

	/**
	 * 根据文件获取附件流
	 * @param fileId
	 * @return
	 * @throws Exception
	 */
	public InputStream getInputStreamByFile(String fileId) throws Exception;


	/**
	 * 根据文档id和附件key查找附件路劲
	 *
	 * @param fdClaimantId
	 * @return
	 * @throws Exception
	 */
	public JSONArray findFilePath(String fdDocIds) throws Exception;

	/**
	 * 根据附件路劲恢复附件
	 *
	 * @param fdClaimantId
	 * @return
	 * @throws Exception
	 */
	public JSONObject restorefile(String[] filePath) throws Exception;

	/**
	 * 查询转换附件的fileId和附件id
	 *
	 * @param queryStr
	 * @param fdFileIds
	 * @param modelName
	 * @return
	 * @throws Exception
	 */
	public List<String> getAttIds(List<String> fdFileIds, String queryStr,
								  String modelName) throws Exception;

	/**
	 * 根据sys_att_file查询不在sys_att_main的记录
	 *
	 * @throws Exception
	 */
	List<SysAttFile> findNotExistMainBeforeByTime(int limitNum) throws Exception;

	/**
	 * 附件的下载次数+1
	 *
	 * @param attId
	 * @throws Exception
	 */
	public void addDownloadCount(String attId) throws Exception;

	/**
	 * 根据附件返回主文档信息
	 *
	 * @param fdId
	 * @return
	 * @throws Exception
	 */
	public JSONObject findMainDocInfo(String fdId) throws Exception;

	/**
	 * 新增sysAttFile对象
	 *
	 * @param fdMd5
	 * @param fdFileSize
	 * @param fdId
	 * @param fdPath
	 * @return
	 * @throws Exception
	 */
	public void addFile(String fdMd5, String fdFileSize, String fdId,
						String fdPath) throws Exception;

	/**
	 * 根据SysAttMain主键获取文件存储路径
	 *
	 * @param fdId
	 * @return
	 * @throws Exception
	 */
	public String getFilePath(String fdId) throws Exception;

	/**
	 * 根据SysAttMain主键获取SysAttFile对象
	 * @param fdId
	 * @return
	 * @throws Exception
	 */
	public SysAttFile getFile(String fdId) throws Exception;

	/**
	 * 根据filePath获取SysAttFile对象，此方法仅限原始文件path调用，类似缩略图等转换后的文件path，不能调用此方法
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public SysAttFile getFileByPath(String filePath) throws Exception;

	/**
	 * 根据filePath获取SysAttMain对象
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public SysAttMain getAttMainByPath(String filePath) throws Exception;

	/**
	 * 根据md5查找文件
	 * @param fileMd5
	 * @param fileSize
	 * @return
	 * @throws Exception
	 */
	public SysAttFile getFileByMd5(String fileMd5, long fileSize) throws Exception;

	/**
	 * 校验下载附件签名,签名验证通过可以免登录下载
	 * @param expires
	 * @param fdId
	 * @param sign
	 * @return
	 */
	public boolean validateDownloadSignature(String expires, String fdId, String sign) throws Exception;

	public boolean validateDownloadSignatureRest(String expires, String fdId, String sign) throws Exception;

	/**
	 * 统计相关信息封装
	 * @param request
	 * @param form
	 * @throws Exception
	 */
	public void statistics(HttpServletRequest request, SysAttMainForm form)
			throws Exception;

	/**
	 * 获取主文档模块名
	 *
	 * @param modelName
	 * @return
	 * @throws Exception
	 */
	public String getMainModuleName(String modelName) throws Exception;

	/**
	 * 获取主文档链接和标题
	 *
	 * @param modelId
	 * @param modelName
	 * @return
	 * @throws Exception
	 */
	public String[] getMainUrlAndName(String modelId, String modelName) throws Exception;


	/**
	 * 检查附件是否有权限进行转换
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String[] checkShare(HttpServletRequest request) throws Exception;

	/**
	 * 新增一个附件供新建在线编辑
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public SysAttMain addOnlineFile(HttpServletRequest request) throws Exception;

	/**
	 * 新增一个附件供新建在线编辑
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public SysAttMain addCloudOnlineFile(HttpServletRequest request)
			throws Exception;

	/**
	 * wps在线编辑时更新附件的关联关系
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean updateRelation(HttpServletRequest request) throws Exception;

	/**
	 * wps在线编辑时更新附件的关联关系
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean updateCloudRelation(HttpServletRequest request)
			throws Exception;

	/**
	 * 获取生成Wps在线查看的链接地址
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public JSONObject getWpsUrlAndToken(HttpServletRequest request) throws Exception;

	/**
	 * 通过系统配置的附件rest服务账号密码生成一个url
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String getRestDownloadUrl(String fdId) throws Exception;

	/**
	 *
	 * 通过系统配置的附件rest服务账号密码生成一个url(追加modelName)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String getRestDownloadUrl(String fdId,String modelName) throws Exception;

	/**
	 *
	 * 拷贝文档指定key的附件列表
	 * @param attsInfo [{"exsitAttIds":"xxx1;xxx1;xxx1","newKey":"xxx1","fdModelId":"xxx1","fdModelName":"xxx1","modelKey":"xxx1"},{"exsitAttIds":"xxx2;xxx2;xxx2","newKey":"xxx1","fdModelId":"xxx2","fdModelName":"xxx2","modelKey":"xxx2"}]
	 *
	 * @return
	 * @throws Exception
	 */
	public List<SysAttMain> cloneDocAtts(JSONArray attsInfo) throws Exception;

	public void updateByUser(IBaseModel modelObj,String userId) throws Exception;

	public String downloadPluginXml(String realPath) throws Exception;

	public SysAttMain addWpsOaassistOnlineFile(HttpServletRequest request)
			throws Exception;

	public SysAttMain addWpsOaassistOnlineFile(String fdTemplateModelId,String fdTemplateModelName,String fdTemplateModelKey,String fdKey,String fdModelName,String fdModelId)
			throws Exception;
	public SysAttMain addWpsOaassistOnlineFile(SysAttMain sam)throws Exception;

	public SysAttMain setWpsOnlineFile(SysAttMain sam, String modelId,String modelName)throws Exception;

	public SysAttMain addWpsCenterOnlineFile(HttpServletRequest request)throws Exception;

	/**
	 * 读取附件流
	 * 所有业务代码应调用此方法读取附件，而不应使用java.io.File来读取文件流
	 * @param sysAttFileId
	 * @return 文件流
	 * @throws Exception
	 */
	public InputStream readAttachment(String sysAttFileId) throws Exception;

	/**
	 * 读取附件流
	 * 所有业务代码应调用此方法读取附件，而不应使用java.io.File来读取文件流
	 * @param sysAttFile
	 * @return 文件流
	 * @throws Exception
	 */
	public InputStream readAttachment(SysAttFile sysAttFile) throws Exception;

	/**
	 * 附件流
	 * 所有业务代码应调用此方法读取附件，而不应使用java.io.File来读取文件流
	 * @param inputStream 文件流
	 * @param sysAttFile
	 * @throws Exception
	 */
	public void writeAttachment(InputStream inputStream,SysAttFile sysAttFile) throws Exception;

	/**
	 *
	 * @param is 文件流
	 * @param filePath
	 * @throws Exception
	 */
	public void writeAttachment(InputStream inputStream,String filePath) throws Exception;


	/**
	 * 删除附件关联（钉盘上传，只能处理自己创建的附件）
	 *
	 * @param fdAttMainId 附件fdId
	 * @return
	 * @throws Exception
	 */
	public boolean updateDelRelation(String fdAttMainId) throws Exception;


	public void updateByTmpAttmainId(String editonlineFdId, String editonlineTmpFdId) throws Exception;
}
