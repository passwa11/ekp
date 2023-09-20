package com.landray.kmss.sys.filestore.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.IEventMulticasterAware;
import com.landray.kmss.framework.spring.event.interfaces.IEventCallBack;
import com.landray.kmss.framework.spring.event.transaction.EventOfTransactionCommit;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.cluster.interfaces.DispatcherCenter;
import com.landray.kmss.sys.cluster.interfaces.message.SimpleMessage;
import com.landray.kmss.sys.cluster.remoting.protocol.Command;
import com.landray.kmss.sys.filestore.dao.ISysAttUploadDao;
import com.landray.kmss.sys.filestore.dao.ISysFileConvertQueueDao;
import com.landray.kmss.sys.filestore.event.ConvertFinishEvent;
import com.landray.kmss.sys.filestore.forms.SysFileConvertQueueParamForm;
import com.landray.kmss.sys.filestore.model.*;
import com.landray.kmss.sys.filestore.queue.service.ConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.third.wps.center.cache.ConvertCallbackCache;
import com.landray.kmss.sys.filestore.service.*;
import com.landray.kmss.sys.filestore.util.FileStoreConvertUtil;
import com.landray.kmss.sys.filestore.util.SysFileStoreUtil;
import com.landray.kmss.sys.filestore.util.SysFileStoreUtil.FileConverter;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.*;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEvent;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.landray.kmss.sys.filestore.constant.ConvertConstant.THIRD_CONVERTER_DIANJU;
import static com.landray.kmss.sys.filestore.constant.ConvertConstant.THIRD_CONVERTER_FOXIT;

@SuppressWarnings("unchecked")
public class SysFileConvertQueueServiceImp extends BaseServiceImp
		implements ISysFileConvertQueueService, IEventMulticasterAware {
	protected static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysFileConvertQueueServiceImp.class);

	private ISysFileConvertLogService logService = null;
	private ISysFileConvertClientService clientService = null;
	private ISysFileViewerParamService paramService = null;
	protected ISysAttMainCoreInnerService sysAttMainService = null;
	protected ISysAttUploadService sysAttUploadService = null;
	private ISysFileConvertConfigService sysFileConvertConfigService = null;
	/** 事件分发服务 **/
	private IEventMulticaster multicaster;

	@Override
	public void setEventMulticaster(IEventMulticaster multicaster) {
		this.multicaster = multicaster;
	}

	@Override
	public ISysAttMainCoreInnerService getAttService() {
		if (sysAttMainService == null) {
			sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService");
		}
		return sysAttMainService;
	}

	@Override
	public ISysAttUploadService getAttUploadService() {
		if (sysAttUploadService == null) {
			sysAttUploadService = (ISysAttUploadService) SpringBeanUtil.getBean("sysAttUploadService");
		}
		return sysAttUploadService;
	}

	private ISysFileConvertClientService getClientService() {
		if (clientService == null) {
			clientService = (ISysFileConvertClientService) SpringBeanUtil.getBean("sysFileConvertClientService");
		}
		return clientService;
	}

	private ISysFileConvertConfigService getSysFileConvertConfigService() {
		if (sysFileConvertConfigService == null) {
			sysFileConvertConfigService = (ISysFileConvertConfigService) SpringBeanUtil.getBean("sysFileConvertConfigTarget");
		}
		return sysFileConvertConfigService;
	}

	/**
	 * 转换队列信息
	 */
	private static ConvertQueue convertQueue = null;
	private static ConvertQueue getConvertQueue() {
		if(convertQueue == null) {
			convertQueue = (ConvertQueue) SpringBeanUtil.getBean("convertQueueImpl");
		}

		return convertQueue;
	}
	public void setParamService(ISysFileViewerParamService paramService) {
		this.paramService = paramService;
	}

	public void setLogService(ISysFileConvertLogService logService) {
		this.logService = logService;
	}

	private List<SysFileConvertQueue> exsitsQueue(String fileId, String attMainId, String converterKey,
			String converterParam, String convertType) throws Exception {
		List<SysFileConvertQueue> result = null;
		String whereBlock = " fdConverterKey=:fdConverterKey ";
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setGetCount(false);
		
		if(StringUtil.isNotNull(convertType))
		{
			whereBlock += " and fdConverterType=:fdConverterType";
			hqlInfo.setParameter("fdConverterType", convertType);
		}
		
		if (StringUtil.isNotNull(fileId)) {
			whereBlock += " and fdFileId=:fdFileId";
			hqlInfo.setParameter("fdFileId", fileId);
		} else {
			whereBlock += " and fdAttMainId=:fdAttMainId";
			hqlInfo.setParameter("fdAttMainId", attMainId);
		}
		
		hqlInfo.setParameter("fdConverterKey", converterKey);
		
		hqlInfo.setWhereBlock(whereBlock);
		result = findList(hqlInfo);
		return result;
	}

	/**
	 * 查询队列
	 * @param fileId
	 * @param attMainId
	 * @param converterKeys
	 * @param convertType
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<SysFileConvertQueue> getQueues(String fileId, String attMainId,
												 String[] converterKeys,String convertType) throws Exception {
		List<SysFileConvertQueue> result = null;
		/**
		 * 转换类型
		 */
		String converterKey = "";
		for(String key : converterKeys) {
			converterKey += "\'" + key + "\',";
		}
		converterKey = converterKey.substring(0, converterKey.lastIndexOf(","));
		String whereBlock = " fdConverterKey in ("+ converterKey +") ";
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setGetCount(false);

		if(StringUtil.isNotNull(convertType)) {
			whereBlock += " and fdConverterType=:fdConverterType";
			hqlInfo.setParameter("fdConverterType", convertType);
		}

		if (StringUtil.isNotNull(fileId)) {
			whereBlock += " and fdFileId=:fdFileId";
			hqlInfo.setParameter("fdFileId", fileId);
		} else {
			whereBlock += " and fdAttMainId=:fdAttMainId";
			hqlInfo.setParameter("fdAttMainId", attMainId);
		}

		hqlInfo.setWhereBlock(whereBlock);
		result = findList(hqlInfo);
		return result;
	}

	@Override
	public void addQueue(String fileId, String fileName, String modelName, String modelId, String param,
			String attMainId) throws Exception {
		if ((StringUtil.isNull(fileId) && StringUtil.isNull(attMainId)) || StringUtil.isNull(modelName)) {
			return;
		}

		Boolean isConverterAspose = FileStoreConvertUtil.isOpenAsposeConverter();
		Boolean isConverterYozo = FileStoreConvertUtil.isOpenYozoConverter();
		SysFileStoreUtil.enableDefaultConfig();

		if (StringUtil.isNull(fileName)) {
			logger.error("附件名称为空，不执行附件转换操作!");
			return;
		}

		String extName = fileName.substring(fileName.lastIndexOf(".") + 1);
		List<FileConverter> converters = SysFileStoreUtil.getFileConverters(extName, modelName);
		if (converters.size() > 0) {
			for (FileConverter converter : converters) {
				String convertType = converter.getConverterType();

				// 第三方的转换不在此处添加
				if(FileStoreConvertUtil.containsThirdConverter(convertType)) {
					continue;
				}

				// 是aspose，但是aspose没有开，则不添加
				if(!isConverterAspose && (StringUtil.isNull(convertType)
						|| "aspose".equals(convertType))) {
					continue;
				}

				// 是yozo,但是yozo没有开，则不添加
				if(!isConverterYozo && "yozo".equals(convertType)) {
					continue;
				}

				if ("image2thumbnail".equals(converter.getConverterKey())) {
					String bigImageWidth = SysFileStoreUtil.getBigImageWidth();
					String smallImageWidth = SysFileStoreUtil.getSmallImageWidth();
					if ("0".equals(bigImageWidth) && "0".equals(smallImageWidth)) {
						continue;
					}
					StringBuffer paramBuffer = new StringBuffer();
					String tempStrB = "{'thumb':[";
					String tempStrE = "]}";
					String tempStrM = "";
					if (!("0".equals(bigImageWidth))) {
						tempStrM += "{'name':'" + bigImageWidth + "','w':'" + bigImageWidth + "'},";
					}
					if (!("0".equals(smallImageWidth))) {
						tempStrM += "{'name':'" + smallImageWidth + "','w':'" + smallImageWidth + "'}";
					}
					if (StringUtil.isNull(param)) {
						paramBuffer.append(tempStrB + tempStrM + tempStrE);
					} else {
						paramBuffer.append(param);
						paramBuffer.deleteCharAt(paramBuffer.length() - 1);
						paramBuffer.deleteCharAt(paramBuffer.length() - 1);
						paramBuffer.append(",");
						paramBuffer.append(tempStrM + tempStrE);
					}
					param = paramBuffer.toString();
				} else {
					JSONObject paramJson = new JSONObject();
					String picResolution = converter.getPicResolution();
					String picRectangle = converter.getPicRectangle();
					String highFidelity = converter.getFdHighFidelity();
					paramJson.put("picResolution", StringUtil.isNotNull(picResolution) ? picResolution : "96");
					paramJson.put("picRectangle", StringUtil.isNotNull(picRectangle) ? picRectangle : "A3");
					paramJson.put("highFidelity", StringUtil.isNotNull(highFidelity) ? highFidelity : "0");
					param = paramJson.toString();
				}
				List<SysFileConvertQueue> exsitsQueue = exsitsQueue(fileId, attMainId, converter.getConverterKey(),
						param, converter.getConverterType());
				if (exsitsQueue == null || exsitsQueue.size() == 0) {
					final SysFileConvertQueue queue = new SysFileConvertQueue();
					Date inQueueTime = new Date();
					queue.setFdFileId(fileId);
					queue.setFdConvertStatus(0);
					queue.setFdConvertNumber(0);
					queue.setFdIsFinish(false);
					queue.setFdStatusTime(inQueueTime);
					queue.setFdCreateTime(inQueueTime);
					queue.setFdConverterKey(converter.getConverterKey());
					queue.setFdFileName(fileName);
					queue.setFdFileExtName(extName);
					queue.setFdConverterParam(param);
					queue.setFdDispenser(converter.getDispenser());
					queue.setFdAttMainId(attMainId);
					queue.setFdModelUrl(SysFileStoreUtil.getQueueHelpFul(modelId, modelName, fileName)[1]);
					queue.setFdAttModelId(modelId);
					queue.setFdConverterType(converter.getConverterType());
					queue.setFdIsLongQueue(false);
					try {
						add(queue);
						multicaster.attatchEvent(new EventOfTransactionCommit(StringUtils.EMPTY), new IEventCallBack() {
							@Override
							public void execute(ApplicationEvent event) throws Throwable {
								if (!"image2thumbnail".equals(queue.getFdConverterKey())) {
									// 激活队列任务
									DispatcherCenter dc = DispatcherCenter.getInstance();
									try {
										dc.sendToDispatcher(new SimpleMessage(), "sysFileConvertDispatcher");
									} catch (Exception e) {
										logger.error("激活转换任务失败", e);
									}
								}
							}
						});
						logger.debug("附件ID:" + attMainId + "文件ID:" + fileId + "准备入转换队列");
						logger.debug("生成转换队列：" + queue);
						logger.debug("队列对应文件路径：" + getQueueFilePath(fileId, attMainId));
					} catch (Exception e) {
						logger.info("入队出错", e);
					}
				}
			}
		}
	}
	
	@Override
	public void addQueueWithAttMainModelName(String fileId, String fileName, String modelName, String modelId, String param,
			String attMainId, String attMainModelName) throws Exception {
		if ((StringUtil.isNull(fileId) && StringUtil.isNull(attMainId)) || StringUtil.isNull(modelName)) {
			return;
		}
		SysFileStoreUtil.enableDefaultConfig();

		if (StringUtil.isNull(fileName)) {
			logger.error("附件名称为空，不执行附件转换操作!");
			return;
		}

		String extName = fileName.substring(fileName.lastIndexOf(".") + 1);
		List<FileConverter> converters = SysFileStoreUtil.getFileConverters(extName, modelName);
		if (converters.size() > 0) {
			for (FileConverter converter : converters) {
				if ("image2thumbnail".equals(converter.getConverterKey())) {
					String bigImageWidth = SysFileStoreUtil.getBigImageWidth();
					String smallImageWidth = SysFileStoreUtil.getSmallImageWidth();
					if ("0".equals(bigImageWidth) && "0".equals(smallImageWidth)) {
						continue;
					}
					StringBuffer paramBuffer = new StringBuffer();
					String tempStrB = "{'thumb':[";
					String tempStrE = "]}";
					String tempStrM = "";
					if (!("0".equals(bigImageWidth))) {
						tempStrM += "{'name':'" + bigImageWidth + "','w':'" + bigImageWidth + "'},";
					}
					if (!("0".equals(smallImageWidth))) {
						tempStrM += "{'name':'" + smallImageWidth + "','w':'" + smallImageWidth + "'}";
					}
					if (StringUtil.isNull(param)) {
						paramBuffer.append(tempStrB + tempStrM + tempStrE);
					} else {
						paramBuffer.append(param);
						paramBuffer.deleteCharAt(paramBuffer.length() - 1);
						paramBuffer.deleteCharAt(paramBuffer.length() - 1);
						paramBuffer.append(",");
						paramBuffer.append(tempStrM + tempStrE);
					}
					param = paramBuffer.toString();
				} else {
					JSONObject paramJson = new JSONObject();
					String picResolution = converter.getPicResolution();
					String picRectangle = converter.getPicRectangle();
					String highFidelity = converter.getFdHighFidelity();
					paramJson.put("picResolution", StringUtil.isNotNull(picResolution) ? picResolution : "96");
					paramJson.put("picRectangle", StringUtil.isNotNull(picRectangle) ? picRectangle : "A3");
					paramJson.put("highFidelity", StringUtil.isNotNull(highFidelity) ? highFidelity : "0");
					param = paramJson.toString();
				}
				List<SysFileConvertQueue> exsitsQueue = exsitsQueue(fileId, attMainId, converter.getConverterKey(),
						param, converter.getConverterType());
				if (exsitsQueue == null || exsitsQueue.size() == 0) {
					final SysFileConvertQueue queue = new SysFileConvertQueue();
					Date inQueueTime = new Date();
					queue.setFdFileId(fileId);
					queue.setFdConvertStatus(0);
					queue.setFdConvertNumber(0);
					queue.setFdIsFinish(false);
					queue.setFdStatusTime(inQueueTime);
					queue.setFdCreateTime(inQueueTime);
					queue.setFdConverterKey(converter.getConverterKey());
					queue.setFdFileName(fileName);
					queue.setFdFileExtName(extName);
					queue.setFdConverterParam(param);
					queue.setFdDispenser(converter.getDispenser());
					queue.setFdAttMainId(attMainId);
					queue.setFdAttMainModelName(attMainModelName);				
					queue.setFdModelUrl(SysFileStoreUtil.getQueueHelpFul(modelId, modelName, fileName)[1]);
					queue.setFdAttModelId(modelId);
					queue.setFdConverterType(converter.getConverterType());
					queue.setFdIsLongQueue(false);
					try {
						add(queue);
						multicaster.attatchEvent(new EventOfTransactionCommit(StringUtils.EMPTY), new IEventCallBack() {
							@Override
							public void execute(ApplicationEvent event) throws Throwable {
								if (!"image2thumbnail".equals(queue.getFdConverterKey())) {
									// 激活队列任务
									DispatcherCenter dc = DispatcherCenter.getInstance();
									try {
										dc.sendToDispatcher(new SimpleMessage(), "sysFileConvertDispatcher");
									} catch (Exception e) {
										logger.error("激活转换任务失败", e);
									}
								}
							}
						});
						logger.debug("附件ID:" + attMainId + "文件ID:" + fileId + "准备入转换队列");
						logger.debug("生成转换队列：" + queue);
						logger.debug("队列对应文件路径：" + getQueueFilePath(fileId, attMainId));
					} catch (Exception e) {
						logger.info("入队出错", e);
					}
				}
			}
		}
	}

	@Override
	public void addQueueAndPdfUpdate(SysAttMain attMain, SysAttMain oldAttMain,String fileId, String fileName, String modelName, String modelId, String param,
			String attMainId) throws Exception {
		if ((StringUtil.isNull(fileId) && StringUtil.isNull(attMainId))
				|| StringUtil.isNull(modelName) || StringUtil.isNull(modelId)) {
			return;
		}

		Boolean isConverterAspose = FileStoreConvertUtil.isOpenAsposeConverter();
		Boolean isConverterYozo = FileStoreConvertUtil.isOpenYozoConverter();
		SysFileStoreUtil.enableDefaultConfig();

		if (StringUtil.isNull(fileName)) {
			logger.error("附件名称为空，不执行附件转换操作!");
			return;
		}

		String extName = fileName.substring(fileName.lastIndexOf(".") + 1);
		List<FileConverter> converters = SysFileStoreUtil.getFileConverters(extName, modelName);
		if (converters.size() > 0) {
			for (FileConverter converter : converters) {
				String convertType = converter.getConverterType();
				// 第三方的转换不在此处添加
				if(FileStoreConvertUtil.containsThirdConverter(convertType)) {
					continue;
				}

				// 是aspose，但是aspose没有开，则不添加
				if(!isConverterAspose && (StringUtil.isNull(convertType)
						|| "aspose".equals(convertType))) {
					continue;
				}

				// 是yozo,但是yozo没有开，则不添加
				if(!isConverterYozo && "yozo".equals(convertType)) {
					continue;
				}

				if ("image2thumbnail".equals(converter.getConverterKey())) {
					String bigImageWidth = SysFileStoreUtil.getBigImageWidth();
					String smallImageWidth = SysFileStoreUtil.getSmallImageWidth();
					if ("0".equals(bigImageWidth) && "0".equals(smallImageWidth)) {
						continue;
					}
					StringBuffer paramBuffer = new StringBuffer();
					String tempStrB = "{'thumb':[";
					String tempStrE = "]}";
					String tempStrM = "";
					if (!("0".equals(bigImageWidth))) {
						tempStrM += "{'name':'" + bigImageWidth + "','w':'" + bigImageWidth + "'},";
					}
					if (!("0".equals(smallImageWidth))) {
						tempStrM += "{'name':'" + smallImageWidth + "','w':'" + smallImageWidth + "'}";
					}
					if (StringUtil.isNull(param)) {
						paramBuffer.append(tempStrB + tempStrM + tempStrE);
					} else {
						paramBuffer.append(param);
						paramBuffer.deleteCharAt(paramBuffer.length() - 1);
						paramBuffer.deleteCharAt(paramBuffer.length() - 1);
						paramBuffer.append(",");
						paramBuffer.append(tempStrM + tempStrE);
					}
					param = paramBuffer.toString();
				} else {
					JSONObject paramJson = new JSONObject();
					String picResolution = converter.getPicResolution();
					String picRectangle = converter.getPicRectangle();
					String highFidelity = converter.getFdHighFidelity();
					paramJson.put("picResolution", StringUtil.isNotNull(picResolution) ? picResolution : "96");
					paramJson.put("picRectangle", StringUtil.isNotNull(picRectangle) ? picRectangle : "A3");
					paramJson.put("highFidelity", StringUtil.isNotNull(highFidelity) ? highFidelity : "0");
					param = paramJson.toString();
				}
				
				if("dwg".equals(extName)) //dwg文件的converterKey需要为cadToImg
				{
					converter.setConverterKey("cadToImg");
				}
				List<SysFileConvertQueue> exsitsQueue = exsitsQueue(fileId, attMainId, converter.getConverterKey(),
						param, converter.getConverterType());


				String fdModelUrl = SysFileStoreUtil.getQueueHelpFul(modelId, modelName, fileName)[1];

				if (exsitsQueue == null || exsitsQueue.size() == 0 || isFileChange(attMain, oldAttMain)) {
					final SysFileConvertQueue queue = new SysFileConvertQueue();
					Date inQueueTime = new Date();
					queue.setFdFileId(fileId);
					queue.setFdConvertStatus(0);
					queue.setFdConvertNumber(0);
					queue.setFdIsFinish(false);
					queue.setFdStatusTime(inQueueTime);
					queue.setFdCreateTime(inQueueTime);
					queue.setFdConverterKey(converter.getConverterKey());
					queue.setFdFileName(fileName);
					queue.setFdFileExtName(extName);
					queue.setFdConverterParam(param);
					queue.setFdDispenser(converter.getDispenser());
					queue.setFdAttMainId(attMainId);
					queue.setFdModelUrl(fdModelUrl);
					queue.setFdAttModelId(modelId);
					queue.setFdConverterType(converter.getConverterType());
					queue.setFdIsLongQueue(false);
					try {
						add(queue);
//						multicaster.attatchEvent(new EventOfTransactionCommit(StringUtils.EMPTY), new IEventCallBack() {
//							public void execute(ApplicationEvent event) throws Throwable {
//								if (!queue.getFdConverterKey().equals("image2thumbnail")) {
//									// 激活队列任务
//									DispatcherCenter dc = DispatcherCenter.getInstance();
//									try {
//										dc.sendToDispatcher(new SimpleMessage(), "sysFileConvertDispatcher");
//									} catch (Exception e) {
//										logger.error("激活转换任务失败", e);
//									}
//								}
//							}
//						});

						logger.debug("附件ID:" + attMainId + "文件ID:" + fileId + "准备入转换队列");
						logger.debug("生成转换队列：" + queue);
						logger.debug("队列对应文件路径：" + getQueueFilePath(fileId, attMainId));
					} catch (Exception e) {
						logger.info("入队出错", e);
					}
				} else if (exsitsQueue.size() > 0 && StringUtil.isNotNull(fdModelUrl)
						&& (modelName.contains("km.imissive") || modelName.contains("km.agreement")) && !"pdf".equals(extName)) { //如果本身是pdf，不需要再转
					SysFileConvertQueue queue = exsitsQueue.get(0);
					if(queue.getFdIsPdfGenerated()==null || (queue.getFdIsPdfGenerated()!=null && !queue.getFdIsPdfGenerated())) { //如果已经转了pdf也不需要再转
						queue.setFdConvertStatus(0);
						queue.setFdIsFinish(false);
						queue.setFdStatusTime(new Date());
						queue.setFdModelUrl(fdModelUrl);
						if(queue.getFdIsLongQueue()==null) {
							queue.setFdIsLongQueue(false);
						}
						try {
							update(queue);
						} catch (Exception e) {
							logger.info("更新队列出错", e);
						}
					}
				}
			}
		}
	}

	@Override
	public void updateQueue(String fileId, String fileName, String modelName, String modelId, String param,
			String attMainId) throws Exception {
		if ((StringUtil.isNull(fileId) && StringUtil.isNull(attMainId)) || StringUtil.isNull(modelName)) {
			return;
		}
		SysFileStoreUtil.enableDefaultConfig();

		if (StringUtil.isNull(fileName)) {
			logger.error("附件名称为空，不执行附件转换操作!");
			return;
		}

		String extName = fileName.substring(fileName.lastIndexOf(".") + 1);
		List<FileConverter> converters = SysFileStoreUtil.getFileConverters(extName, modelName);
		if (converters.size() > 0) {
			for (FileConverter converter : converters) {
				List<SysFileConvertQueue> exsitsQueue = exsitsQueue(fileId, attMainId, converter.getConverterKey(),
						param, converter.getConverterType());
				String fdModelUrl = SysFileStoreUtil.getQueueHelpFul(modelId, modelName, fileName)[1];
				SysFileConvertQueue updateQueue = null;
				if (exsitsQueue == null || exsitsQueue.size() == 0) {
					if ("image2thumbnail".equals(converter.getConverterKey())) {
						String bigImageWidth = SysFileStoreUtil.getBigImageWidth();
						String smallImageWidth = SysFileStoreUtil.getSmallImageWidth();
						if ("0".equals(bigImageWidth) && "0".equals(smallImageWidth)) {
							continue;
						}
						StringBuffer paramBuffer = new StringBuffer();
						String tempStrB = "{'thumb':[";
						String tempStrE = "]}";
						String tempStrM = "";
						if (!("0".equals(bigImageWidth))) {
							tempStrM += "{'name':'" + bigImageWidth + "','w':'" + bigImageWidth + "'},";
						}
						if (!("0".equals(smallImageWidth))) {
							tempStrM += "{'name':'" + smallImageWidth + "','w':'" + smallImageWidth + "'}";
						}
						if (tempStrM.endsWith(",")) {
							tempStrM = tempStrM.substring(0, tempStrM.length() - 1);
						}
						if (StringUtil.isNull(param)) {
							paramBuffer.append(tempStrB + tempStrM + tempStrE);
						} else {
							paramBuffer.append(param);
							paramBuffer.deleteCharAt(paramBuffer.length() - 1);
							paramBuffer.deleteCharAt(paramBuffer.length() - 1);
							paramBuffer.append(",");
							paramBuffer.append(tempStrM + tempStrE);
						}
						param = paramBuffer.toString();
					} else {
						param = "";
					}
					updateQueue = new SysFileConvertQueue();
					Date inQueueTime = new Date();
					updateQueue.setFdFileId(fileId);
					updateQueue.setFdConvertStatus(0);
					updateQueue.setFdConvertNumber(0);
					updateQueue.setFdIsFinish(false);
					updateQueue.setFdStatusTime(inQueueTime);
					updateQueue.setFdCreateTime(inQueueTime);
					updateQueue.setFdConverterKey(converter.getConverterKey());
					updateQueue.setFdFileName(fileName);
					updateQueue.setFdFileExtName(extName);
					updateQueue.setFdConverterParam(param);
					updateQueue.setFdDispenser(converter.getDispenser());
					updateQueue.setFdAttMainId(attMainId);
					updateQueue.setFdModelUrl(SysFileStoreUtil.getQueueHelpFul(modelId, modelName, fileName)[1]);
					updateQueue.setFdAttModelId(modelId);
					updateQueue.setFdConverterType(converter.getConverterType());
					updateQueue.setFdIsLongQueue(false);
					try {
						add(updateQueue);
					} catch (Exception e) {
						logger.info("入队出错", e);
					}
				} else if (exsitsQueue.size() > 0 && StringUtil.isNotNull(fdModelUrl)) {
					SysFileConvertQueue queue = exsitsQueue.get(0);
					queue.setFdConvertStatus(0);
					queue.setFdIsFinish(false);
					queue.setFdStatusTime(new Date());
					queue.setFdModelUrl(fdModelUrl);
					if(queue.getFdIsLongQueue()==null) {
                        queue.setFdIsLongQueue(false);
                    }
					try {
						update(queue);
					} catch (Exception e) {
						logger.info("更新队列出错", e);
					}
				} else if (exsitsQueue.size() == 1) {
					updateQueue = exsitsQueue.get(0);
					updateQueue.setFdConvertStatus(0);
					updateQueue.setFdIsFinish(false);
					updateQueue.setFdStatusTime(new Date());
					if(updateQueue.getFdIsLongQueue()==null) {
                        updateQueue.setFdIsLongQueue(false);
                    }
					try {
						update(updateQueue);
					} catch (Exception e) {
						logger.info("更新队列出错", e);
					}
				}
				final SysFileConvertQueue queue = updateQueue;
				multicaster.attatchEvent(new EventOfTransactionCommit(StringUtils.EMPTY), new IEventCallBack() {
					@Override
					public void execute(ApplicationEvent event) throws Throwable {
						if (!"image2thumbnail".equals(queue.getFdConverterKey())) {
							// 激活队列任务
							DispatcherCenter dc = DispatcherCenter.getInstance();
							try {
								dc.sendToDispatcher(new SimpleMessage(), "sysFileConvertDispatcher");
							} catch (Exception e) {
								logger.error("激活转换任务失败", e);
							}
						}
					}
				});
			}
		}
	}

	@Override
	public void saveReDistribute(String redistributeAll, String[] ids, String[] failureType) throws Exception {
		HQLInfo findQueue = new HQLInfo();
		if ("true".equals(redistributeAll)) {
			if (failureType == null || failureType.length == 0) {
				findQueue.setWhereBlock("fdConvertStatus=" + SysFileConvertConstant.FAILURE);
			} else {
				String statusWhere = "fdConvertStatus=";
				for (int i = 0; i < failureType.length; i++) {
					if (i == failureType.length - 1) {
						statusWhere += failureType[i];
					} else {
						statusWhere += failureType[i] + " or fdConvertStatus=";
					}
				}
				findQueue.setWhereBlock(statusWhere);
			}
			List<SysFileConvertQueue> updateFailures = findList(findQueue);
			if (updateFailures != null && updateFailures.size() > 0) {
				for (SysFileConvertQueue item : updateFailures) {
					item.setFdConvertStatus(SysFileConvertConstant.UNASSIGNED);
					item.setFdStatusTime(new Date());
				}
			}
		} else {
			SysFileConvertQueue queue = null;
			for (String id : ids) {
				queue = (SysFileConvertQueue) findByPrimaryKey(id);
				queue.setFdConvertStatus(SysFileConvertConstant.UNASSIGNED);
				queue.setFdStatusTime(new Date());
				if(queue.getFdIsLongQueue()==null) {
                    queue.setFdIsLongQueue(false);
                }
				update(queue);
				String convertType = queue.getFdConverterType();
				if(StringUtil.isNotNull(convertType) && ("dianju".equals(convertType) || "foxit".equals(convertType))) {
					getConvertQueue().put(queue,queue.getFdConverterType());
				}

				// #166698 如果转换成功等待回调时，重新分配了，则需要删除回调中的缓存
				if(StringUtil.isNotNull(convertType) && ("wpsCenter".equals(convertType))) {
					String taskId = ConvertCallbackCache.getInstance().get(id);
					if (StringUtil.isNotNull(taskId)) {
						ConvertCallbackCache.getInstance().remove(taskId);
					}

					ConvertCallbackCache.getInstance().remove(id);
				}

			}
		}
	}

	@Override
	public void deleteQueues(String[] ids) throws Exception {
		if (ids != null && ids.length > 0) {
			for (String id : ids) {
				delete(findByPrimaryKey(id));
			}
		}
	}

	@Override
	public Page findOKPage(HQLInfo hqlInfo) throws Exception {
		return findPage(hqlInfo);
	}

	private SysFileConvertClient getQueueClient(SysFileConvertQueue convertingQueue, String clientId) throws Exception {
		SysFileConvertClient resultClient = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdId = :clientId");
		hqlInfo.setParameter("clientId", StringUtil.isNotNull(clientId) ? clientId : convertingQueue.getFdClientId());
		resultClient = (SysFileConvertClient)getClientService().findFirstOne(hqlInfo);
		return resultClient;
	}
	
	private SysFileConvertClient getQueueLongClient() throws Exception {
		SysFileConvertClient resultClient = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("avail = :avail and isLongTask = :isLongTask");
		hqlInfo.setParameter("avail", true);
		hqlInfo.setParameter("isLongTask", true);
		resultClient = (SysFileConvertClient)getClientService().findFirstOne(hqlInfo);
		return resultClient;
	}
	
	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		final SysFileConvertQueue queue = (SysFileConvertQueue) modelObj;
		paramService.deleteQueueParams(queue, getQueueClient(queue, null));
		logService.deleteLogs("all", queue.getFdId(), null);
		super.delete(modelObj);
		if (queue.getFdConvertStatus() != SysFileConvertConstant.ASSIGNED) {
			deleteQueueConvertFiles(queue);
		}
	}

	private void deleteQueueConvertFiles(SysFileConvertQueue queue) {
		String queueFilePath = getQueueFilePath(queue.getFdFileId(), queue.getFdAttMainId());
		try {
			FileUtils.deleteDirectory(new File(queueFilePath + "_convert"));
		} catch (IOException e) {
			//
		}
	}

	@Override
	public void setRemoteConvertQueue(Command receiveMessage, String statusType, String queueId, String clientId,
			String statusDesc, ISysFileConvertClientService clientService, ISysFileConvertLogService logService)
			throws Exception {
		String queueIdInner = "";
		String statusTypeInner = "";
		Map<String, String> receiveInfos = null;
		if (receiveMessage == null) {
			queueIdInner = queueId;
			statusTypeInner = statusType;
		} else {
			receiveInfos = receiveMessage.getExtFields();
			statusTypeInner = receiveInfos.get("msgType");
			queueIdInner = receiveInfos.get("queueId");
		}
		SysFileConvertQueue convertQueue = null;
		SysFileConvertLog convertLog = null;
		SysFileConvertClient queueClient = null;
		String errorHintSufix = "";
		try {
			convertQueue = (SysFileConvertQueue)findFirstOne("fdId='" + queueIdInner + "'", "");
			if (convertQueue!=null) {
				Date statusTime = new Date();
				if ("taskInvalid".equals(statusTypeInner)) {
					errorHintSufix = "设置转换队列为无效出错";
					convertQueue.setFdConvertStatus(SysFileConvertConstant.INVALID);
					convertLog = new SysFileConvertLog();
					convertLog.setFdQueueId(queueIdInner);
					convertLog.setFdConvertStatus(SysFileConvertConstant.INVALID);
					convertLog.setFdStatusTime(statusTime);
					convertLog.setFdConvertKey("invalid");
					convertLog.setFdStatusInfo(statusDesc);
				} else {
					queueClient = getQueueClient(convertQueue, clientId);
					convertQueue.setFdStatusTime(statusTime);
					if ("taskUnAssigned".equals(statusTypeInner)) {
						errorHintSufix = "设置转换队列为未分配出错";
						convertQueue.setFdClientId("");
						convertQueue.setFdConvertStatus(SysFileConvertConstant.UNASSIGNED);
						convertLog = new SysFileConvertLog();
						convertLog.setFdQueueId(queueIdInner);
						convertLog.setFdConvertKey(queueClient != null ? queueClient.getConverterFullKey() : "*");
						convertLog.setFdConvertStatus(SysFileConvertConstant.UNASSIGNED);
						convertLog.setFdStatusTime(statusTime);
						convertLog.setFdStatusInfo(statusDesc);
						if (queueClient != null) {
							queueClient.subTaskConvertingNum();
						}
					} else if ("taskAssigned".equals(statusTypeInner)) {
						errorHintSufix = "设置转换队列为已分配出错";
						convertQueue.setFdClientId(queueClient != null ? queueClient.getFdId() : "*");
						convertQueue.setFdConvertStatus(SysFileConvertConstant.ASSIGNED);
						convertLog = new SysFileConvertLog();
						convertLog.setFdQueueId(queueIdInner);
						convertLog.setFdConvertKey(queueClient != null ? queueClient.getConverterFullKey() : "*");
						convertLog.setFdConvertStatus(SysFileConvertConstant.ASSIGNED);
						convertLog.setFdStatusTime(statusTime);
						convertLog.setFdStatusInfo(statusDesc);
						if (queueClient != null) {
							queueClient.addTaskConvertingNum();
						}
					} else if("otherConvertFinish".equals(statusTypeInner)) //wps等其它的转换成功后直接设置状态为SUCCESS
					{
						errorHintSufix = "设置转换队列为转换成功出错(otherConvertFinish)";
						convertQueue.setFdClientId(queueClient != null ? queueClient.getFdId() : "*");
						convertQueue.setFdConvertStatus(SysFileConvertConstant.SUCCESS);
						convertQueue.setFdConvertNumber(convertQueue.getFdConvertNumber() + 1);
						convertLog = new SysFileConvertLog();
						convertLog.setFdQueueId(queueIdInner);
						convertLog.setFdConvertKey(queueClient != null ? queueClient.getConverterFullKey() : "*");
						convertLog.setFdConvertStatus(SysFileConvertConstant.SUCCESS);
						convertLog.setFdStatusTime(statusTime);
						if("true".equals(receiveInfos.get("isPdfGenerated"))) {
							convertQueue.setFdIsPdfGenerated(true);
						}else {
							convertQueue.setFdIsPdfGenerated(false);
						}
						convertLog.setFdStatusInfo(statusDesc);
						if (queueClient != null) {
							queueClient.addTaskConvertingNum();
						}
					}
					else if ("convertFinish".equals(statusTypeInner)) {
						ConvertFinishEvent finishEvent = new ConvertFinishEvent(convertQueue);
						String convertFinishResult = receiveInfos.get("convertFinishResult");
						if ("true".equals(convertFinishResult)) {
							finishEvent.setSuccess(true);
							errorHintSufix = "设置转换队列为转换成功出错";
							if (convertQueue.getFdConvertStatus() != SysFileConvertConstant.SUCCESS) {
								convertQueue.setFdConvertStatus(SysFileConvertConstant.SUCCESS);
								convertQueue.setFdIsFinish(Boolean.TRUE);
								convertQueue.setFdConvertNumber(convertQueue.getFdConvertNumber() + 1);
								convertLog = new SysFileConvertLog();
								convertLog.setFdQueueId(convertQueue.getFdId());
								convertLog
										.setFdConvertKey(queueClient != null ? queueClient.getConverterFullKey() : "*");
								convertLog.setFdConvertStatus(SysFileConvertConstant.SUCCESS);
								convertLog.setFdStatusTime(statusTime);
								convertLog.setFdStatusInfo(receiveInfos.get("convertElapsedTime"));
								if (queueClient != null) {
									queueClient.subTaskConvertingNum();
								}
							}
						} else {
							finishEvent.setSuccess(false);
							errorHintSufix = "设置转换队列为转换失败出错";
							String failureInfo = receiveInfos.get("failureInfo");
							String failureType = receiveInfos.get("failureType");
							if (convertQueue.getFdConvertStatus() != SysFileConvertConstant.CONVERTERTOOLFAIL
									|| convertQueue.getFdConvertStatus() != SysFileConvertConstant.TIMEOUTFAILURE
									|| convertQueue.getFdConvertStatus() != SysFileConvertConstant.OOMFAILURE
									|| convertQueue.getFdConvertStatus() != SysFileConvertConstant.FAILURE) {
								convertLog = new SysFileConvertLog();
								convertLog.setFdStatusInfo(failureInfo);
								if ("tool".equals(failureType)) {
									convertQueue.setFdConvertStatus(SysFileConvertConstant.CONVERTERTOOLFAIL);
									convertLog.setFdConvertStatus(SysFileConvertConstant.CONVERTERTOOLFAIL);
								} else if ("timeout".equals(failureType)) {
									SysFileConvertClient client = getQueueLongClient();
									logger.debug("client:"+client);
									logger.debug("queueClient.getIsLongTask():"+queueClient.getIsLongTask());
									logger.debug("convertQueue.getFdIsLongQueue():"+convertQueue.getFdIsLongQueue());
									if(client!=null && !queueClient.getIsLongTask() && convertQueue.getFdIsLongQueue() != true){//存在长转换配置，且不是在长转换服务转换的时候，增加长转换标识，再重新分发一次
										logger.debug("run long again");
										convertQueue.setFdConvertStatus(SysFileConvertConstant.UNASSIGNED);
										convertQueue.setFdIsLongQueue(true);
									}else{
										logger.debug("not run long");
										convertQueue.setFdConvertStatus(SysFileConvertConstant.TIMEOUTFAILURE);
									}
									convertLog.setFdConvertStatus(SysFileConvertConstant.TIMEOUTFAILURE);
								} else if ("oom".equals(failureType)) {
									convertQueue.setFdConvertStatus(SysFileConvertConstant.OOMFAILURE);
									convertLog.setFdConvertStatus(SysFileConvertConstant.OOMFAILURE);
								} else {
									convertQueue.setFdConvertStatus(SysFileConvertConstant.FAILURE);
									convertLog.setFdConvertStatus(SysFileConvertConstant.FAILURE);
								}
								convertQueue.setFdIsFinish(true);
								convertQueue.setFdConvertNumber(convertQueue.getFdConvertNumber() + 1);
								convertLog.setFdQueueId(convertQueue.getFdId());
								convertLog
										.setFdConvertKey(queueClient != null ? queueClient.getConverterFullKey() : "*");
								convertLog.setFdStatusTime(statusTime);
								if (queueClient != null) {
									queueClient.subTaskConvertingNum();
								}
							}
						}
						SpringBeanUtil.getApplicationContext().publishEvent(finishEvent);
					}
				}
				update(convertQueue);
				if (queueClient != null) {
					clientService.update(queueClient);
				}
				if (convertLog != null) {
					try {
						logService.add(convertLog);
					} catch (Exception e) {
						logger.info("添加日志信息出错", e);
					}
				}
			}
		} catch (Exception e) {
			logger.info(errorHintSufix, e);
			throw new Exception("setConvertQueue-" + statusTypeInner + "-" + errorHintSufix);
		}

	}

	@Override
	public void setLocalConvertQueue(String statusType, String queueId, String failureInfo,
			ISysFileConvertLogService logService) throws Exception {
		SysFileConvertQueue convertQueue = null;
		SysFileConvertLog convertLog = null;
		String errorHintSufix = "";
		try {
			convertQueue = (SysFileConvertQueue) findByPrimaryKey(queueId);
			Date statusTime = new Date();
			convertQueue.setFdStatusTime(statusTime);
			if ("taskInvalid".equals(statusType)) {
				errorHintSufix = "设置图片队列为无效队列出错";
				convertQueue.setFdClientId("");
				convertQueue.setFdConvertStatus(SysFileConvertConstant.INVALID);
				convertLog = new SysFileConvertLog();
				convertLog.setFdQueueId(queueId);
				convertLog.setFdConvertKey(convertQueue.getFdConverterKey());
				convertLog.setFdConvertStatus(SysFileConvertConstant.INVALID);
				convertLog.setFdStatusTime(statusTime);
			} else if ("taskUnAssigned".equals(statusType)) {
				errorHintSufix = "设置图片队列为未分配出错";
				convertQueue.setFdClientId("");
				convertQueue.setFdConvertStatus(SysFileConvertConstant.UNASSIGNED);
				convertLog = new SysFileConvertLog();
				convertLog.setFdQueueId(queueId);
				convertLog.setFdConvertKey(convertQueue.getFdConverterKey());
				convertLog.setFdConvertStatus(SysFileConvertConstant.UNASSIGNED);
				convertLog.setFdStatusTime(statusTime);
			} else if ("taskAssigned".equals(statusType)) {
				errorHintSufix = "设置图片队列为已分配出错";
				convertQueue.setFdClientId("");
				convertQueue.setFdConvertStatus(SysFileConvertConstant.ASSIGNED);
				convertLog = new SysFileConvertLog();
				convertLog.setFdQueueId(queueId);
				convertLog.setFdConvertKey(convertQueue.getFdConverterKey());
				convertLog.setFdConvertStatus(SysFileConvertConstant.ASSIGNED);
				convertLog.setFdStatusTime(statusTime);
			} else if ("taskBegin".equals(statusType)) {
				errorHintSufix = "设置图片队列为正在压缩出错";
				convertQueue.setFdConvertStatus(SysFileConvertConstant.HAVEBEGUN);
				convertLog = new SysFileConvertLog();
				convertLog.setFdQueueId(convertQueue.getFdId());
				convertLog.setFdConvertKey(convertQueue.getFdConverterKey());
				convertLog.setFdConvertStatus(SysFileConvertConstant.HAVEBEGUN);
				convertLog.setFdStatusTime(statusTime);
			} else if ("taskSuccess".equals(statusType)) {
				errorHintSufix = "设置图片队列为压缩成功出错";
				convertQueue.setFdConvertStatus(SysFileConvertConstant.SUCCESS);
				convertQueue.setFdIsFinish(Boolean.TRUE);
				convertQueue.setFdConvertNumber(convertQueue.getFdConvertNumber() + 1);
				convertLog = new SysFileConvertLog();
				convertLog.setFdQueueId(convertQueue.getFdId());
				convertLog.setFdConvertKey(convertQueue.getFdConverterKey());
				convertLog.setFdConvertStatus(SysFileConvertConstant.SUCCESS);
				convertLog.setFdStatusTime(statusTime);
			} else if ("taskFailure".equals(statusType)) {
				errorHintSufix = "设置图片队列为压缩失败出错";
				convertLog = new SysFileConvertLog();
				convertLog.setFdStatusInfo(failureInfo);
				convertQueue.setFdConvertStatus(SysFileConvertConstant.FAILURE);
				convertLog.setFdConvertStatus(SysFileConvertConstant.FAILURE);
				convertQueue.setFdIsFinish(true);
				convertQueue.setFdConvertNumber(convertQueue.getFdConvertNumber() + 1);
				convertLog.setFdQueueId(convertQueue.getFdId());
				convertLog.setFdConvertKey(convertQueue.getFdConverterKey());
				convertLog.setFdStatusTime(statusTime);
			}
			update(convertQueue);
		} catch (Exception e) {
			logger.info(errorHintSufix, e);
			throw new Exception("setConvertQueue-" + statusType + "-" + errorHintSufix);
		}
		try {
			if (convertLog != null) {
				logService.add(convertLog);
			}
		} catch (Exception e) {
			logger.info("添加日志信息出错", e);
			throw new Exception("setConvertQueue-" + statusType + "-" + "addConvertLog添加日志信息出错");
		}
	}

	@Override
	public void setLocalWpsConvertQueue(String statusType, String queueId,
			String failureInfo,
			ISysFileConvertLogService logService) throws Exception {
		SysFileConvertQueue convertQueue = null;
		SysFileConvertLog convertLog = null;
		String errorHintSufix = "";
		try {
			convertQueue = (SysFileConvertQueue) findByPrimaryKey(queueId);
			Date statusTime = new Date();
			convertQueue.setFdStatusTime(statusTime);
			if ("taskInvalid".equals(statusType)) {
				errorHintSufix = "设置 附件上传WPS队列为无效队列出错";
				convertQueue.setFdClientId("");
				convertQueue.setFdConvertStatus(SysFileConvertConstant.INVALID);
				convertLog = new SysFileConvertLog();
				convertLog.setFdQueueId(queueId);
				convertLog.setFdConvertKey(convertQueue.getFdConverterKey());
				convertLog.setFdConvertStatus(SysFileConvertConstant.INVALID);
				convertLog.setFdStatusTime(statusTime);
			} else if ("taskUnAssigned".equals(statusType)) {
				errorHintSufix = "设置附件上传WPS队列为未分配出错";
				convertQueue.setFdClientId("");
				convertQueue
						.setFdConvertStatus(SysFileConvertConstant.UNASSIGNED);
				convertLog = new SysFileConvertLog();
				convertLog.setFdQueueId(queueId);
				convertLog.setFdConvertKey(convertQueue.getFdConverterKey());
				convertLog
						.setFdConvertStatus(SysFileConvertConstant.UNASSIGNED);
				convertLog.setFdStatusTime(statusTime);
			} else if ("taskAssigned".equals(statusType)) {
				errorHintSufix = "设置附件上传WPS队列为已分配出错";
				convertQueue.setFdClientId("");
				convertQueue
						.setFdConvertStatus(SysFileConvertConstant.ASSIGNED);
				convertLog = new SysFileConvertLog();
				convertLog.setFdQueueId(queueId);
				convertLog.setFdConvertKey(convertQueue.getFdConverterKey());
				convertLog.setFdConvertStatus(SysFileConvertConstant.ASSIGNED);
				convertLog.setFdStatusTime(statusTime);
			} else if ("taskBegin".equals(statusType)) {
				errorHintSufix = "设置开始附件上传WPS队列出错";
				convertQueue
						.setFdConvertStatus(SysFileConvertConstant.HAVEBEGUN);
				convertLog = new SysFileConvertLog();
				convertLog.setFdQueueId(convertQueue.getFdId());
				convertLog.setFdConvertKey(convertQueue.getFdConverterKey());
				convertLog.setFdConvertStatus(SysFileConvertConstant.HAVEBEGUN);
				convertLog.setFdStatusTime(statusTime);
			} else if ("taskSuccess".equals(statusType)) {
				errorHintSufix = "设置附件上传WPS队列成功出错";
				convertQueue.setFdConvertStatus(SysFileConvertConstant.SUCCESS);
				convertQueue.setFdIsFinish(Boolean.TRUE);
				convertQueue.setFdConvertNumber(
						convertQueue.getFdConvertNumber() + 1);
				convertLog = new SysFileConvertLog();
				convertLog.setFdQueueId(convertQueue.getFdId());
				convertLog.setFdConvertKey(convertQueue.getFdConverterKey());
				convertLog.setFdConvertStatus(SysFileConvertConstant.SUCCESS);
				convertLog.setFdStatusTime(statusTime);
			} else if ("taskFailure".equals(statusType)) {
				errorHintSufix = "设置附件上传WPS队列失败出错";
				convertLog = new SysFileConvertLog();
				convertLog.setFdStatusInfo(failureInfo);
				convertQueue.setFdConvertStatus(SysFileConvertConstant.FAILURE);
				convertLog.setFdConvertStatus(SysFileConvertConstant.FAILURE);
				convertQueue.setFdIsFinish(true);
				convertQueue.setFdConvertNumber(
						convertQueue.getFdConvertNumber() + 1);
				convertLog.setFdQueueId(convertQueue.getFdId());
				convertLog.setFdConvertKey(convertQueue.getFdConverterKey());
				convertLog.setFdStatusTime(statusTime);
			}
			update(convertQueue);
		} catch (Exception e) {
			logger.info(errorHintSufix, e);
			throw new Exception(
					"setConvertQueue-" + statusType + "-" + errorHintSufix);
		}
		try {
			if (convertLog != null) {
				logService.add(convertLog);
			}
		} catch (Exception e) {
			logger.info("添加日志信息出错", e);
			throw new Exception("setConvertQueue-" + statusType + "-"
					+ "addConvertLog添加日志信息出错");
		}
	}

	@Override
	public List<SysFileConvertQueue> getUnassignedTasks(String dispenser, String converterKey, Integer maxTaskNum) {
		logger.debug("获取未分配的");
		Page page = null;
		try {
			HQLInfo hqlInfo = new HQLInfo();
			String whereBlock = " fdConvertStatus=:fdConvertStatus";
			if("videoToFlv".equals(converterKey)) {
				whereBlock += " and fdDispenser=:fdDispenser and (fdConverterKey = :convererKey or fdConverterKey = :videoToMp4)";
				hqlInfo.setParameter("videoToMp4", "videoToMp4");
			}else {
				whereBlock += " and fdDispenser=:fdDispenser and fdConverterKey = :convererKey";
			}
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setParameter("fdConvertStatus", SysFileConvertConstant.UNASSIGNED);
			hqlInfo.setParameter("fdDispenser", dispenser);
			hqlInfo.setParameter("convererKey", converterKey);
			hqlInfo.setOrderBy(" fdStatusTime asc");
			hqlInfo.setPageNo(1);
			hqlInfo.setRowSize(maxTaskNum);
			hqlInfo.setGetCount(false);
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, SysAuthConstant.AuthCheck.SYS_NONE);
			page = findPage(hqlInfo);
		} catch (Exception e) {
			logger.debug("Get Unsigned Task Error!", e);
		}
		logger.debug("获取未分配的结束");
		return page == null ? new ArrayList<SysFileConvertQueue>() : page.getList();
	}

	@Override
	public List<SysFileConvertQueue> getUnassignedTasksByconverterKeys(String dispenser, String[] converterKeys, Integer maxTaskNum) {
		logger.debug("获取未分配的");
		Page page = null;
		String converterKey = "";
		for(String key : converterKeys) {
			converterKey += "\'" + key + "\',";
		}
		converterKey = converterKey.substring(0, converterKey.lastIndexOf(","));
		try {
			HQLInfo hqlInfo = new HQLInfo();
			String whereBlock = " fdConvertStatus=:fdConvertStatus" +
					" and fdDispenser=:fdDispenser and fdConverterKey in (" +converterKey +" ) ";

			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setParameter("fdConvertStatus", SysFileConvertConstant.UNASSIGNED);
			hqlInfo.setParameter("fdDispenser", dispenser);
			//hqlInfo.setParameter("converterKey", converterKey);
			hqlInfo.setOrderBy(" fdStatusTime asc");
			hqlInfo.setPageNo(1);
			hqlInfo.setRowSize(maxTaskNum);
			hqlInfo.setGetCount(false);
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, SysAuthConstant.AuthCheck.SYS_NONE);
			page = findPage(hqlInfo);
		} catch (Exception e) {
			logger.debug("Get Unsigned Task Error!", e);
		}
		logger.debug("获取未分配的结束");
		return page == null ? new ArrayList<SysFileConvertQueue>() : page.getList();
	}
	/**
	 * 根据多个key查询队列的指定的转换器
	 *
	 * @param converterType  转换器
	 * @param converterKeys
	 * @return
	 */
	@Override
	public List<SysFileConvertQueue> getUnassignedTasksByKeysAndType(String converterType, String[] converterKeys,
																	 Integer maxTaskNum,  Object o) {
		logger.debug("getUnassignedTasksByKeysAndType::获取未分配的");
		Page page = null;
		String converterKey = "";
		for(String key : converterKeys) {
			converterKey += "\'" + key + "\',";
		}
		converterKey = converterKey.substring(0, converterKey.lastIndexOf(","));
		try {
			HQLInfo hqlInfo = new HQLInfo();
			String whereBlock = " fdConvertStatus=:fdConvertStatus" +
					" and fdDispenser=:fdDispenser and fdConverterType=:fdConverterType and fdConverterKey in (" +converterKey +" ) ";

			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setParameter("fdConvertStatus", SysFileConvertConstant.UNASSIGNED);
			hqlInfo.setParameter("fdDispenser", "remote");
			hqlInfo.setParameter("fdConverterType", converterType);
			hqlInfo.setOrderBy(" fdStatusTime asc");
			hqlInfo.setPageNo(1);
			hqlInfo.setRowSize(maxTaskNum);
			hqlInfo.setGetCount(false);
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, SysAuthConstant.AuthCheck.SYS_NONE);
			page = findPage(hqlInfo);
		} catch (Exception e) {
			logger.debug("Get Unsigned Task Error!", e);
		}
		logger.debug("获取未分配的结束");
		return page == null ? new ArrayList<SysFileConvertQueue>() : page.getList();
	}

	@Override
	public void clearInvalid() {
		((ISysFileConvertQueueDao) getBaseDao()).clearInvalid();
	}

	@Override
	public String getQueueFileLocation(String attFileId) {

		String location = null;

		try {
			SysAttFile attFile = getAttUploadService().getFileById(attFileId);
			if (attFile != null) {
				location = attFile.getFdAttLocation();
			}
		} catch (Exception e) {
			logger.error("获取文件存储类型出错", e);
		}

		return location;
	}

	@Override
	public String getQueueFilePath(String attFileId, String attMainId, Boolean isFullPath) {

		String filePath = "";
		if (StringUtil.isNotNull(attFileId)) {
			try {
				SysAttFile attFile = getAttUploadService().getFileById(attFileId);
				if (attFile == null) {
					logger.warn("附件对应的文件记录没有找到");
				} else {
					filePath = getAttUploadService().getAbsouluteFilePath(attFile, isFullPath);
				}
			} catch (Exception e) {
				logger.warn("获取文件绝对路径出错", e);
			}
		} else {
			try {
				SysAttMain attMain = (SysAttMain) getAttService().findByPrimaryKey(attMainId, SysAttMain.class, true);
				if (attMain != null) {
					filePath = attMain.getFdFilePath();
				} else {
					logger.warn("附件不存在");
				}
			} catch (Exception e) {
				logger.warn("获取文件绝对路径出错", e);
			}
		}
		logger.debug("附件ID:" + attMainId);
		logger.debug("文件ID:" + attFileId);
		logger.debug("文件路径:" + filePath);
		return filePath;

	}

	@Override
	public String getQueueFilePath(String attFileId, String attMainId) {
		return getQueueFilePath(attFileId, attMainId, false);
	}

	@Override
	public boolean isExist(String queueId) {
		boolean result = false;
		try {
			IBaseModel model = findByPrimaryKey(queueId, getModelName(), true);
			if (model != null) {
				result = true;
			}
		} catch (Exception e) {
			//
		}
		return result;
	}

	@Override
	public boolean supportHighFidelity(SysFileConvertQueue queue) {
		String extName = queue.getFdFileExtName().toLowerCase();
		return "doc".equals(extName) || "docx".equals(extName) || "ppt".equals(extName) || "pptx".equals(extName)
				|| "pdf".equals(extName) || "wps".equals(extName) || "rtf".equals(extName);
	}

	@Override
	public SysFileConvertQueueParamForm getParamForm(String queueIds) throws Exception {
		SysFileConvertQueueParamForm result = new SysFileConvertQueueParamForm();
		String newQueueIds = "";
		String[] queueIdsArray = queueIds.split(";");
		if (queueIdsArray.length == 1) {
			JSONObject queueParam = null;
			SysFileConvertQueue queue = (SysFileConvertQueue) findByPrimaryKey(queueIdsArray[0]);
			if ("remote".equals(queue.getFdDispenser()) && queue.getFdConvertStatus() != SysFileConvertConstant.ASSIGNED
					&& queue.getFdConvertStatus() != SysFileConvertConstant.HAVEBEGUN) {
				newQueueIds += ";" + queue.getFdId();
				String convertParam = queue.getFdConverterParam();
				String highFidelity = "0";
				String picResolution = "96";
				String picRectangle = "A3";
				if (StringUtil.isNotNull(convertParam) && convertParam.startsWith("{")) {
					queueParam = JSONObject.fromObject(queue.getFdConverterParam());
					if (queueParam.containsKey("picRectangle")) {
						picRectangle = queueParam.getString("picRectangle");
					}
					if (queueParam.containsKey("picResolution")) {
						picResolution = queueParam.getString("picResolution");
					}
					if (queueParam.containsKey("highFidelity")) {
						highFidelity = queueParam.getString("highFidelity");
					}
				}
				result.setConverterType(queue.getFdConverterType());
				if ((!"true".equals(result.getContainsHighFidelity())) && supportHighFidelity(queue)) {
					result.setContainsHighFidelity("true");
				}
				result.setHighFidelity("1".equals(highFidelity) ? "true" : "false");
				result.setPicResolution(picResolution);
				result.setPicRectangle(picRectangle);
			}
		} else {
			for (String queueId : queueIdsArray) {
				SysFileConvertQueue queue = (SysFileConvertQueue) findByPrimaryKey(queueId);
				if ("remote".equals(queue.getFdDispenser())
						&& queue.getFdConvertStatus() != SysFileConvertConstant.ASSIGNED
						&& queue.getFdConvertStatus() != SysFileConvertConstant.HAVEBEGUN) {
					newQueueIds += ";" + queueId;
				}
				if ((!"true".equals(result.getContainsHighFidelity())) && supportHighFidelity(queue)) {
					result.setContainsHighFidelity("true");
				}
			}
			result.setHighFidelity("false");
		}
		if (StringUtil.isNotNull(newQueueIds)) {
			newQueueIds = newQueueIds.substring(1);
		}
		result.setQueueIds(newQueueIds);
		return result;
	}

	@Override
	public void saveQueueParam(SysFileConvertQueueParamForm paramForm) throws Exception {
		String queueIds = paramForm.getQueueIds();
		if (StringUtil.isNotNull(queueIds)) {
			String[] queueIdsArray = queueIds.split(";");
			for (String queueId : queueIdsArray) {
				SysFileConvertQueue queue = (SysFileConvertQueue) findByPrimaryKey(queueId);
				if ("remote".equals(queue.getFdDispenser())
						&& queue.getFdConvertStatus() != SysFileConvertConstant.ASSIGNED
						&& queue.getFdConvertStatus() != SysFileConvertConstant.HAVEBEGUN) {
					String queueParam = queue.getFdConverterParam();
					JSONObject param = new JSONObject();
					if (StringUtil.isNotNull(queueParam)) {
						param = JSONObject.fromObject(queueParam);
					}
					param.put("picResolution", paramForm.getPicResolution());
					param.put("picRectangle", paramForm.getPicRectangle());
					param.put("highFidelity", "true".equals(paramForm.getHighFidelity()) ? "1" : '0');
					queue.setFdConverterParam(param.toString());
					queue.setFdConvertStatus(SysFileConvertConstant.UNASSIGNED);// 修改参数之后重新转换
					queue.setFdConverterType(paramForm.getConverterType());
					if(queue.getFdIsLongQueue()==null) {
                        queue.setFdIsLongQueue(false);
                    }
					update(queue);
				}
			}
		}
	}

	@Override
	public String getPicResolution(SysFileConvertQueue convertQueue) {
		String picResolution = "96";
		String convertParam = convertQueue.getFdConverterParam();
		if (StringUtil.isNotNull(convertParam) && convertParam.startsWith("{")) {
			JSONObject jsonObject = JSONObject.fromObject(convertParam);
			if (jsonObject.containsKey("picResolution")) {
				picResolution = jsonObject.getString("picResolution");
			}
		}
		return picResolution;
	}

	@Override
	public String getPicRectangle(SysFileConvertQueue convertQueue) {
		String picRectangle = "A3";
		String convertParam = convertQueue.getFdConverterParam();
		if (StringUtil.isNotNull(convertParam) && convertParam.startsWith("{")) {
			JSONObject jsonObject = JSONObject.fromObject(convertParam);
			if (jsonObject.containsKey("picRectangle")) {
				picRectangle = jsonObject.getString("picRectangle");
			}
		}
		return picRectangle;
	}

	@Override
	public String getHighFidelity(SysFileConvertQueue convertQueue) {
		String highFidelity = "0";
		String convertParam = convertQueue.getFdConverterParam();
		if (StringUtil.isNotNull(convertParam) && convertParam.startsWith("{")) {
			JSONObject jsonObject = JSONObject.fromObject(convertParam);
			if (jsonObject.containsKey("highFidelity")) {
				highFidelity = jsonObject.getString("highFidelity");
			}
		}
		return highFidelity;
	}

	private ISysAttUploadDao sysAttUploadDao;

	public ISysAttUploadDao getSysAttUploadDao() {
		if (sysAttUploadDao == null) {
			sysAttUploadDao = (ISysAttUploadDao) SpringBeanUtil.getBean("sysAttUploadDao");
		}
		return sysAttUploadDao;
	}

	private static Lock lock = new ReentrantLock();

	@Override
	public void updateAtt(SysFileConvertQueue convertQueue) throws Exception {
		if (convertQueue != null && convertQueue.getFdConvertStatus() == SysFileConvertConstant.SUCCESS) {
			String attId = convertQueue.getFdAttMainId();
			SysAttMain att = (SysAttMain) getAttService().findByPrimaryKey(attId, SysAttMain.class, true);
			String fileId = convertQueue.getFdFileId();
			SysAttFile attFile = getAttUploadService().getFileById(fileId);

			String filePath = ResourceUtil.getKmssConfigString("kmss.resource.path") + attFile.getFdFilePath();
			if (filePath.contains("\\")) {
				filePath = filePath.replaceAll("\\\\", "/");
			}
			File file = new File(filePath);
			File zipfile = new File(filePath + "_uncompress");
			Map<String, String> map = new HashMap<String, String>();
			if (file.exists() && zipfile.exists()) {
				if (zipfile.isDirectory()) {
					getFile(zipfile, map);
				}
			}
			if (!map.isEmpty()) {
				List<String> keys = new ArrayList<String>(map.keySet());
				SysAttFile afile = null;
				File tmpFile = null;
				Date today = new Date();
				for (String key : keys) {
					lock.lock();
					try {
						afile = getAttUploadService().getFileById(key);
						if (afile == null) {
							tmpFile = new File(map.get(key));
							afile = new SysAttFile();
							// 保存文件
							saveFile(afile, tmpFile, today, key, map);
							// 保存附件
							addAttachment(today, att.getFdModelId(), att.getFdModelName(),
									att.getFdKey() + "_asposezip", tmpFile.length(), key, "byte", afile.getFdId());
						}
					} catch (Exception e) {
						throw new KmssRuntimeException(e);
					} finally {
						lock.unlock();
					}
				}
			}
		}
	}

	public void addAttachment(Date date, String modelId, String modelName, String fdKey, long size, String fileName,
			String fdAttType, String fid) throws Exception {
		SysAttMain sysAttMain = new SysAttMain();
		sysAttMain.setDocCreateTime(date);
		sysAttMain.setFdCreatorId(UserUtil.getUser().getFdId());
		sysAttMain.setFdAttLocation(SysAttMain.ATTACHMENT_LOCATION_SERVER);
		sysAttMain.setFdFileName(fileName);
		sysAttMain.setFdSize((double) size);
		String contentType = FileMimeTypeUtil.getContentType(fileName);
		sysAttMain.setFdContentType(contentType);
		sysAttMain.setFdKey(fdKey);
		sysAttMain.setFdModelId(modelId);
		sysAttMain.setFdModelName(modelName);
		sysAttMain.setFdAttType(fdAttType);
		sysAttMain.setFdFileId(fid);
		getAttService().add(sysAttMain);
	}

	private void saveFile(SysAttFile afile, File file, Date today, String key, Map<String, String> map)
			throws Exception {
		afile.setFdFileSize(file.length());
		afile.setDocCreateTime(today);
		String tmpFileMd5 = MD5Util.getMD5String(file);
		afile.setFdMd5(tmpFileMd5);
		afile.setFdFilePath(renameFile(key, map, afile.getFdId()));
		afile.setFdStatus(2);
		getSysAttUploadDao().add(afile);
	}

	private String renameFile(String key, Map<String, String> map, String id) throws IOException {
		String path = null;
		File oFile = new File(map.get(key));
		File nFile = new File(oFile.getParent() + File.separator + id);
		boolean flag = oFile.renameTo(nFile);
		path = nFile.getCanonicalPath().toLowerCase();
		if (flag) {
			oFile = new File(map.get(key) + "_convert");
			nFile = new File(oFile.getParent() + File.separator + id + "_convert");
			oFile.renameTo(nFile);
		}
		String filePath = ResourceUtil.getKmssConfigString("kmss.resource.path");
		path = path.substring(filePath.length());
		if (path.contains("\\")) {
			path = path.replaceAll("\\\\", "/");
		}
		if (!path.startsWith("/")) {
            path += "/";
        }
		if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
		return path;
	}

	@Override
	public void addQueueToOFD(String fileId, String fileName, String modelName, String modelId, String param,
			String attMainId) throws Exception {
		if ((StringUtil.isNull(fileId) && StringUtil.isNull(attMainId)) || StringUtil.isNull(modelName)) {
			return;
		}
		
		//判断转换后缀和所属模块
		String eName = fileName.substring(fileName.lastIndexOf(".") + 1);
		List<FileConverter> converters = SysFileStoreUtil.getFileConverters(eName, modelName);
		if (converters.size() > 0) {
		//	final String converterKey = "toOFD";
			final String dispenser = "remote";

			for(FileConverter converter : converters) {
				JSONObject paramJson = new JSONObject();
				param = paramJson.toString();

				List<SysFileConvertQueue> exsitsQueue = exsitsQueue(fileId, attMainId, converter.getConverterKey(), param, converter.getConverterType());
				if (exsitsQueue == null || exsitsQueue.size() == 0) {
					String extName = fileName.substring(fileName.lastIndexOf(".") + 1);
					final SysFileConvertQueue queue = new SysFileConvertQueue();
					Date inQueueTime = new Date();
					queue.setFdFileId(fileId);
					queue.setFdConvertStatus(0);
					queue.setFdConvertNumber(0);
					queue.setFdIsFinish(false);
					queue.setFdStatusTime(inQueueTime);
					queue.setFdCreateTime(inQueueTime);
					queue.setFdConverterKey(converter.getConverterKey());
					queue.setFdConverterType(converter.getConverterType());
					queue.setFdFileName(fileName);
					queue.setFdFileExtName(extName);
					queue.setFdConverterParam(param);
					queue.setFdDispenser(dispenser);
					queue.setFdAttMainId(attMainId);
					queue.setFdModelUrl(SysFileStoreUtil.getQueueHelpFul(modelId, modelName, fileName)[1]);
					queue.setFdAttModelId(modelId);
					queue.setFdIsLongQueue(false);
					try {
						add(queue);
//						multicaster.attatchEvent(new EventOfTransactionCommit(StringUtils.EMPTY), new IEventCallBack() {
//							public void execute(ApplicationEvent event) throws Throwable {
//								if (queue.getFdConverterKey().equals(converterKey)) {
//									// 激活队列任务
//									DispatcherCenter dc = DispatcherCenter.getInstance();
//									try {
//										dc.sendToDispatcher(new SimpleMessage(), "sysFileConvertDispatcher");
//									} catch (Exception e) {
//										logger.error("激活转换任务失败", e);
//									}
//								}
//							}
//						});
						logger.debug("附件ID:" + attMainId + "文件ID:" + fileId + "准备入转换队列");
						logger.debug("生成转换队列：" + queue);
						logger.debug("队列对应文件路径：" + getQueueFilePath(fileId, attMainId));
					} catch (Exception e) {
						logger.error("入队出错", e);
						throw e;
					}
				}
			}
		}
	
		
		
	}
	
	/**
	 * 第三方厂商转换 历史代码外，其它就不要用了
	 */
	@Override
	public void addFileQueueToOFD(SysAttMain sysAttMain, SysAttMain oldAttMain) throws Exception {
		addFileToQueue(sysAttMain, oldAttMain);

	}

	/**
	 * 第三方厂商转换
	 */
	@Override
	public void addFileToQueue(SysAttMain sysAttMain, SysAttMain oldAttMain) throws Exception {

		String fileId = sysAttMain.getFdFileId();
		String fileName = sysAttMain.getFdFileName();
		String modelName = sysAttMain.getFdModelName();
		String attMainId = sysAttMain.getFdId();
		String modelId = sysAttMain.getFdModelId();
		
		if ((StringUtil.isNull(fileId) && StringUtil.isNull(attMainId)) 
				|| StringUtil.isNull(modelName) || StringUtil.isNull(modelId)) {
			return;
		}

		//判断转换后缀和所属模块
		String eName = fileName.substring(fileName.lastIndexOf(".") + 1);
		List<FileConverter> converters = SysFileStoreUtil.getFileConverters(eName, modelName);
		if(converters == null) {
			return;
		}
		for(FileConverter converter : converters) {
			String convertKey = converter.getConverterKey();
			if(!("toOFD".equals(convertKey) || "toPDF".equals(convertKey))) {
				continue;
			}

			if(FileStoreConvertUtil.whetherExecute(converter.getConverterType(), false)) {
				putConvertQueue(sysAttMain, oldAttMain, converter);
			}
		}

	}

	public void putSKConvert(SysAttMain sysAttMain,SysAttMain oldAttMain,FileConverter converter) throws Exception {
		JSONObject paramJson = new JSONObject();
		String param = paramJson.toString();
		String fileId = sysAttMain.getFdFileId();
		String fileName = sysAttMain.getFdFileName();
		String modelName = sysAttMain.getFdModelName();
		String modelId = sysAttMain.getFdModelId();
		String attMainId = sysAttMain.getFdId();
		
		List<SysFileConvertQueue> exsitsQueue = exsitsQueue(fileId, attMainId, converter.getConverterKey(), param, converter.getConverterType());


		if (exsitsQueue == null || exsitsQueue.size() == 0 || isFileChange(sysAttMain, oldAttMain)) {
			String extName = fileName.substring(fileName.lastIndexOf(".") + 1);
			final SysFileConvertQueue queue = new SysFileConvertQueue();
			Date inQueueTime = new Date();
			queue.setFdFileId(fileId);
			queue.setFdConvertStatus(0);
			queue.setFdConvertNumber(0);
			queue.setFdIsFinish(false);
			queue.setFdStatusTime(inQueueTime);
			queue.setFdCreateTime(inQueueTime);
			queue.setFdConverterKey(converter.getConverterKey());
			queue.setFdConverterType(converter.getConverterType());
			queue.setFdFileName(fileName);
			queue.setFdFileExtName(extName);
			queue.setFdConverterParam(param);
			queue.setFdDispenser("remote");
			queue.setFdAttMainId(attMainId);
			queue.setFdModelUrl(SysFileStoreUtil.getQueueHelpFul(modelId, modelName, fileName)[1]);
			queue.setFdAttModelId(modelId);
			queue.setFdIsLongQueue(false);
			try {
				add(queue);
//				multicaster.attatchEvent(new EventOfTransactionCommit(StringUtils.EMPTY), new IEventCallBack() {
//					public void execute(ApplicationEvent event) throws Throwable {
//						if (queue.getFdConverterKey().equals(converterKey)) {
//							// 激活队列任务
//							DispatcherCenter dc = DispatcherCenter.getInstance();
//							try {
//								dc.sendToDispatcher(new SimpleMessage(), "sysFileConvertDispatcher");
//							} catch (Exception e) {
//								logger.error("激活转换任务失败", e);
//							}
//						}
//					}
//				});
				logger.debug("附件ID:" + attMainId + "文件ID:" + fileId + "准备入转换队列");
				logger.debug("生成转换队列：" + queue);
				logger.debug("队列对应文件路径：" + getQueueFilePath(fileId, attMainId));
			} catch (Exception e) {
				logger.error("入队出错", e);
				throw e;
			}
		}
	}
	
	public void putConvertQueue(SysAttMain sysAttMain, SysAttMain oldAttMain, FileConverter converter) throws Exception {
		String fileId = sysAttMain.getFdFileId();
		String fileName = sysAttMain.getFdFileName();
		String modelName = sysAttMain.getFdModelName();
		String modelId = sysAttMain.getFdModelId();
		String attMainId = sysAttMain.getFdId();
		if ((StringUtil.isNull(fileId) && StringUtil.isNull(attMainId))
				|| StringUtil.isNull(modelName) || StringUtil.isNull(modelId)) {
			return;
		}

		JSONObject paramJson = new JSONObject();
		String param = paramJson.toString();
		List<SysFileConvertQueue> exsitsQueue = exsitsQueue("", attMainId, converter.getConverterKey(), param, converter.getConverterType());

		if (exsitsQueue == null || exsitsQueue.size() == 0 || isFileChange(sysAttMain, oldAttMain)) {
			String extName = fileName.substring(fileName.lastIndexOf(".") + 1);
			final SysFileConvertQueue queue = new SysFileConvertQueue();
			String url = "/sys/attachment/sys_att_main/downloadFile.jsp?fdId=" + attMainId  + "&reqType=rest&filename=" + URLEncoder.encode(fileName, "utf-8");
			Date inQueueTime = new Date();
			queue.setFdFileId(fileId);
			queue.setFdConvertStatus(0);
			queue.setFdConvertNumber(0);
			queue.setFdIsFinish(false);
			queue.setFdStatusTime(inQueueTime);
			queue.setFdCreateTime(inQueueTime);
			queue.setFdConverterKey(converter.getConverterKey());
			queue.setFdConverterType(converter.getConverterType());
			queue.setFdFileName(fileName);
			queue.setFdFileExtName(extName);
			queue.setFdConverterParam(param);
			queue.setFdDispenser("remote");
			queue.setFdAttMainId(attMainId);
			queue.setFdModelUrl(SysFileStoreUtil.getQueueHelpFul(modelId, modelName, fileName)[1]);
			queue.setFdFileDownUrl(url);
			queue.setFdAttModelId(modelId);
			queue.setFdIsLongQueue(false);
			try {
				add(queue);
				if(THIRD_CONVERTER_DIANJU.equals(converter.getConverterType())
						|| THIRD_CONVERTER_FOXIT.equals(converter.getConverterType())) {
					getConvertQueue().put(queue,converter.getConverterType());
				}

				logger.debug("附件ID:" + attMainId + "文件ID:" + fileId + "准备入转换队列");
				logger.debug("生成转换队列：" + queue);
				logger.debug("队列对应文件路径：" + getQueueFilePath(fileId, attMainId));
			} catch (Exception e) {
				logger.error("入队出错", e);
				throw e;
			}
		}
	}

	@Override
	public void addOrUpdateToPDFQueue(String fileId, String fileName, String modelName, String modelId,
									  String param, String attMainId, String serverUrl) throws Exception {
		if ((StringUtil.isNull(fileId) && StringUtil.isNull(attMainId)) || StringUtil.isNull(modelName)) {
			throw new IllegalArgumentException();
		}
		final String converterType = "aspose";
		final String converterKey = "toPDF";
		final String dispenser = "remote";

		JSONObject paramJson = new JSONObject();
		// XXX 预留添加水印功能
		// paramJson.put("waterMarker", "");
		param = paramJson.toString();

		List<SysFileConvertQueue> exsitsQueue = exsitsQueue(fileId, attMainId, converterKey, param,converterType);
		if (exsitsQueue == null || exsitsQueue.size() == 0) {
			String extName = fileName.substring(fileName.lastIndexOf(".") + 1);
			final SysFileConvertQueue queue = new SysFileConvertQueue();
			Date inQueueTime = new Date();
			queue.setFdFileId(fileId);
			queue.setFdConvertStatus(0);
			queue.setFdConvertNumber(0);
			queue.setFdIsFinish(false);
			queue.setFdStatusTime(inQueueTime);
			queue.setFdCreateTime(inQueueTime);
			queue.setFdConverterKey(converterKey);
			queue.setFdFileName(fileName);
			queue.setFdFileExtName(extName);
			queue.setFdConverterParam(param);
			queue.setFdDispenser(dispenser);
			queue.setFdAttMainId(attMainId);
			queue.setFdModelUrl(SysFileStoreUtil.getQueueHelpFul(modelId, modelName, fileName)[1]);
			queue.setFdAttModelId(modelId);
			queue.setFdConverterType(converterType);
			queue.setFdIsLongQueue(false);
			try {
				add(queue);
			} catch (Exception e) {
				logger.error("入队出错", e);
				throw e;
			}
		}else{
			SysFileConvertQueue queue = exsitsQueue.get(0);
			queue.setFdConvertStatus(0);
			queue.setFdIsFinish(false);
			queue.setFdStatusTime(new Date());
			queue.setFdModelUrl(SysFileStoreUtil.getQueueHelpFul(modelId, modelName, fileName)[1]);
			if(queue.getFdIsLongQueue()==null) {
                queue.setFdIsLongQueue(false);
            }
			try {
				update(queue);
			} catch (Exception e) {
				logger.info("更新队列出错", e);
			}

		}

	}

	@Override
	public SysFileConvertQueue addQueueToPDF(String fileId, String fileName, String modelName, String modelId,
			String param, String attMainId, String serverUrl) throws Exception {
		if ((StringUtil.isNull(fileId) && StringUtil.isNull(attMainId)) || StringUtil.isNull(modelName)) {
			throw new IllegalArgumentException();
		}
		final String converterType = "aspose";
		final String converterKey = "toPDF";
		final String dispenser = "remote";

		JSONObject paramJson = new JSONObject();
		// XXX 预留添加水印功能
		// paramJson.put("waterMarker", "");
		param = paramJson.toString();

		List<SysFileConvertQueue> exsitsQueue = exsitsQueue(fileId, attMainId, converterKey, param, converterType);
		if (exsitsQueue != null && exsitsQueue.size() > 0) {
			for (SysFileConvertQueue q : exsitsQueue) {
				if (q.getFdConvertStatus() == 4) {
					return q;
				}
			}
			return exsitsQueue.get(0);
		}
		String extName = fileName.substring(fileName.lastIndexOf(".") + 1);
		final SysFileConvertQueue queue = new SysFileConvertQueue();
		Date inQueueTime = new Date();
		queue.setFdFileId(fileId);
		queue.setFdConvertStatus(0);
		queue.setFdConvertNumber(0);
		queue.setFdIsFinish(false);
		queue.setFdStatusTime(inQueueTime);
		queue.setFdCreateTime(inQueueTime);
		queue.setFdConverterKey(converterKey);
		queue.setFdFileName(fileName);
		queue.setFdFileExtName(extName);
		queue.setFdConverterParam(param);
		queue.setFdDispenser(dispenser);
		queue.setFdAttMainId(attMainId);
		queue.setFdModelUrl(SysFileStoreUtil.getQueueHelpFul(modelId, modelName, fileName)[1]);
		queue.setFdAttModelId(modelId);
		queue.setFdConverterType(converterType);
		queue.setFdIsLongQueue(false);
		try {
			add(queue);
			multicaster.attatchEvent(new EventOfTransactionCommit(StringUtils.EMPTY), new IEventCallBack() {
				@Override
				public void execute(ApplicationEvent event) throws Throwable {
					if (queue.getFdConverterKey().equals(converterKey)) {
						// 激活队列任务
						DispatcherCenter dc = DispatcherCenter.getInstance();
						try {
							dc.sendToDispatcher(new SimpleMessage(), "sysFileConvertDispatcher");
						} catch (Exception e) {
							logger.error("激活转换任务失败", e);
						}
					}
				}
			});
			logger.debug("附件ID:" + attMainId + "文件ID:" + fileId + "准备入转换队列");
			logger.debug("生成转换队列：" + queue);
			logger.debug("队列对应文件路径：" + getQueueFilePath(fileId, attMainId));
			return queue;
		} catch (Exception e) {
			logger.error("入队出错", e);
			throw e;
		}
	}

	@Override
	public List<String> isAllSuccess(String modelId, String converterKey, String converterType) throws Exception {
		if (StringUtil.isNull(modelId)) {
			return new ArrayList<>();
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock(" fdFileId ");
		String whereBlock = null;
		whereBlock = StringUtil.linkString(whereBlock, " and ", "fdAttModelId = :modelId");
		hqlInfo.setParameter("modelId", modelId);
		whereBlock = StringUtil.linkString(whereBlock, " and ", "fdConvertStatus != :status");
		hqlInfo.setParameter("status", 4);

		if (converterKey != null) {
			whereBlock = StringUtil.linkString(whereBlock, " and ", "fdConverterKey = :converterKey");
			hqlInfo.setParameter("converterKey", converterKey);
		}
		if (converterType != null) {
			whereBlock = StringUtil.linkString(whereBlock, " and ", "fdConverterType = :converterType");
			hqlInfo.setParameter("converterType", converterType);
		}
		hqlInfo.setWhereBlock(whereBlock);
		List<String> result = findValue(hqlInfo);
		return result;
	}

	private void getFile(File file, Map<String, String> map) throws Exception {
		File[] files = file.listFiles();
		if (files != null && files.length > 0) {
			for (File fl : files) {
				if (fl.isFile()) {
					map.put(fl.getName(), fl.getCanonicalPath());
				} else if (fl.isDirectory() && !fl.getName().endsWith("_convert")) {
					getFile(fl, map);
				}
			}
		}
	}

	@Override
	public void updateAtt(Map<String, String> map, SysAttMain att) throws Exception {
		if (!map.isEmpty()) {
			List<String> keys = new ArrayList<String>(map.keySet());
			SysAttFile afile = null;
			File tmpFile = null;
			Date today = new Date();
			for (String key : keys) {
				lock.lock();
				try {
					afile = getAttUploadService().getFileById(key);
					if (afile == null) {
						tmpFile = new File(map.get(key));
						afile = new SysAttFile();
						// 保存文件
						saveFile(afile, tmpFile, today, key, map);
						// 保存附件
						addAttachment(today, att.getFdModelId(), att.getFdModelName(), att.getFdKey() + "_asposezip",
								tmpFile.length(), key, "byte", afile.getFdId());
					}
				} catch (Exception e) {
					throw new KmssRuntimeException(e);
				} finally {
					lock.unlock();
				}
			}
		}

	}
	
	@Override
	public void clearOldFiles(SysQuartzJobContext context) {
		context.logMessage("清除旧转换附件数据开始");
		Date start = new Date();
		int fileCount = 0;
		try {
			SysFileConvertClearConfig config = new SysFileConvertClearConfig();
			
			if("true".equals(config.getKmssConvertFileClearEnabled())){
				List<SysFileConvertQueue> result = null;
				String whereBlock = " fdConvertStatus = '4' and fdStatusTime is not null and fdStatusTime <:fdStatusTime ";
				HQLInfo hqlInfo = new HQLInfo();
				//默认处理500条数据
				hqlInfo.setRowSize(Integer.valueOf(config.getKmssConvertFileClearBatchNum()));
				hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_NONE);
	
		        Calendar c = Calendar.getInstance();
		        int month = Integer.valueOf(config.getKmssConvertFileClearMonth());
		        c.setTime(new Date());
		        c.add(Calendar.MONTH, -month);
		        Date m = c.getTime();
	
				hqlInfo.setParameter("fdStatusTime",m);
				hqlInfo.setWhereBlock(whereBlock);
				
				Page page = findPage(hqlInfo);
				
				result = page.getList();
	
				for (SysFileConvertQueue queue : result) {
					String filePath = getQueueFilePath(queue.getFdFileId(), queue.getFdAttMainId(), true);
					File f = new File(filePath+"_convert");
					if(f.exists()){
						this.delete(queue);
						FileUtil.deleteDir(f);
						fileCount++;
						logger.debug("路径下文件已删除：" + filePath + "_convert");
					}else{
						this.delete(queue);//公文有特殊情况，会产生同个fileId的队列，所以要清除
						logger.debug("路径不存在：" + filePath + "_convert");
					}
				}
			}
		} catch (Exception e) {
			context.logError("清理数据失败", e);
		}
		Date end = new Date();
		context.logMessage("清除旧转换附件数据结束，共对" + fileCount
				+ "个附件数据执行了清理操作，共耗时" + (end.getTime() - start.getTime())
				+ "毫秒");
	}
	
	public boolean fileContentChange(String fileId, Long fileSize) throws Exception {
		SysAttFile attFile = getAttUploadService().getFileById(fileId);
		if(attFile != null) {
			String md5 = attFile.getFdMd5();
			//Long fileSize = attFile.getFdFileSize();
			SysAttFile sysAttFile = getAttService().getFileByMd5(md5, fileSize);
			if(sysAttFile == null) {
				return true;
			}
		}

		return false;
	}

	/**
	 *
	 * 判断是不是同份文件
	 * @param attMain
	 * @param oldAttMain
	 * @return
	 * @throws Exception
	 */
	public Boolean isFileChange(SysAttMain attMain, SysAttMain oldAttMain) throws Exception{
		Boolean fileChange = false;

		if(attMain != null && oldAttMain != null) {
			SysAttFile nSysAttFile = getAttUploadService().getFileById(attMain.getFdFileId());
			SysAttFile oSysAttFile = getAttUploadService().getFileById(oldAttMain.getFdFileId());
			String nMd5 = "";
			String oMd5 = "";
			if(nSysAttFile != null) {
				nMd5 = nSysAttFile.getFdMd5();
			}

			if(oSysAttFile!= null) {
				oMd5 = oSysAttFile.getFdMd5();
			}

			if(StringUtil.isNotNull(nMd5) && StringUtil.isNotNull(oMd5) && !nMd5.equals(oMd5)) {
				fileChange = true;
			}

		}

		return fileChange;
	}
}
