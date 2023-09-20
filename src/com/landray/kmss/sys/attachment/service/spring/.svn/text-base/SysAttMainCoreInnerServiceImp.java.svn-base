package com.landray.kmss.sys.attachment.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseCoreInnerServiceImp;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.attachment.dao.ISysAttMainCoreInnerDao;
import com.landray.kmss.sys.attachment.forms.SysAttMainForm;
import com.landray.kmss.sys.attachment.integrate.wps.util.FileDowloadUtil;
import com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCenterUtil;
import com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil;
import com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsWebOfficeUtil;
import com.landray.kmss.sys.attachment.io.DecryptionInputStream;
import com.landray.kmss.sys.attachment.io.IOUtil;
import com.landray.kmss.sys.attachment.model.SysAttBase;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.model.SysAttRtfData;
import com.landray.kmss.sys.attachment.ocx.SysAttOcxUtil;
import com.landray.kmss.sys.attachment.restservice.wps.util.WpsUtil;
import com.landray.kmss.sys.attachment.service.ISysAttDownloadLogService;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.attachment.util.*;
import com.landray.kmss.sys.cache.CacheConfig;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.cluster.interfaces.MessageCenter;
import com.landray.kmss.sys.cluster.interfaces.message.IMessage;
import com.landray.kmss.sys.cluster.interfaces.message.IMessageQueue;
import com.landray.kmss.sys.cluster.interfaces.message.IMessageReceiver;
import com.landray.kmss.sys.cluster.interfaces.message.UniqueMessageQueue;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.filestore.location.interfaces.ISysFileLocationDirectService;
import com.landray.kmss.sys.filestore.location.interfaces.ISysFileLocationProxyService;
import com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.sys.filestore.service.ISysFileConvertQueueService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerMain;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerPolicy;
import com.landray.kmss.sys.restservice.server.service.ISysRestserviceServerMainService;
import com.landray.kmss.sys.webservice2.model.SysWebserviceMain;
import com.landray.kmss.sys.webservice2.model.SysWebserviceUser;
import com.landray.kmss.sys.webservice2.service.ISysWebserviceMainService;
import com.landray.kmss.util.*;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.json.simple.JSONObject;
import org.slf4j.Logger;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 创建日期 2006-九月-04
 *
 * @author 叶中奇 附件业务接口实现
 */
public class SysAttMainCoreInnerServiceImp extends BaseCoreInnerServiceImp
		implements ISysAttMainCoreInnerService, IMessageReceiver {

	public final static String IMG_COMPASS_SIGN_M = "m";

	public final static String IMG_COMPASS_SIGN_S = "s";

	private final static Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttMainCoreInnerServiceImp.class);

	protected ISysAttUploadService sysAttUploadService;

	protected ISysFileConvertQueueService convertQueueService;

	protected ISysAttDownloadLogService sysAttDownloadLogService;

	private CacheConfig config = null;
	private KmssCache cache = null;

	public SysAttMainCoreInnerServiceImp() {
		config = CacheConfig.get(SysAttMainCoreInnerServiceImp.class);
		config.cacheType = CacheConfig.TYPE_REDIS;
		cache = new KmssCache(SysAttMainCoreInnerServiceImp.class);
	}

	public ISysFileConvertQueueService getConvertQueueService() {
		if (convertQueueService == null) {
			convertQueueService = (ISysFileConvertQueueService) SpringBeanUtil.getBean("sysFileConvertQueueService");
		}
		return convertQueueService;
	}

	public void setSysAttUploadService(ISysAttUploadService sysAttUploadService) {
		this.sysAttUploadService = sysAttUploadService;
	}

	@Override
	public ISysAttMainCoreInnerDao getSysAttMainDao() {
		return (ISysAttMainCoreInnerDao) getBaseDao();
	}

	public void setSysAttDownloadLogService(
			ISysAttDownloadLogService sysAttDownloadLogService) {
		this.sysAttDownloadLogService = sysAttDownloadLogService;
	}

	@Override
	public Date getCurTimestamp() throws Exception {
		return sysAttUploadService.getCurTimestamp();
	}

	@Override
	public List<String> getAttIds(List<String> fdList, String queryStr,
								  String modelName) throws Exception {
		Session session = getBaseDao().getHibernateSession();
		List<String> fdIds = null;
		if (StringUtil.isNull(modelName)) {
			fdIds = session.createNativeQuery(queryStr)
					.setParameterList("list", fdList).list();

		} else {
			fdIds = session.createNativeQuery(queryStr)
					.setParameterList("fileIds", fdList)
					.setParameter("modelName", modelName)
					.list();
		}
		return fdIds;
	}

	@Override
	public List<SysAttFile> findNotExistMainBeforeByTime(int limitNum) throws Exception {
		return sysAttUploadService.findNotExistMainBeforeByTime(limitNum);
	}

	@Override
	public List add(List sysAttMains) throws Exception {
		List<IBaseModel> result = new ArrayList<IBaseModel>();
		for (Iterator it = sysAttMains.iterator(); it.hasNext();) {
			SysAttMain sysAttMain = (SysAttMain) it.next();
			// 判断在线编辑中的图片附件是否已经存在
			if ("fdAttachmentPic".equals(sysAttMain.getFdKey())) {
				byte[] inputSreamByt = IOUtils.toByteArray(sysAttMain.getInputStream());
				String fileMd5 = MD5Util.getMD5String(inputSreamByt);
				SysAttFile attfile = sysAttUploadService.getFileByMd5(fileMd5, sysAttMain.getFdSize().longValue());
				// 重新设置输入流
				sysAttMain.setInputStream(new ByteArrayInputStream(inputSreamByt));
				if (attfile != null) {
					HQLInfo hql = new HQLInfo();
					hql.setWhereBlock("sysAttMain.fdFileId=:fdFileId");
					hql.setParameter("fdFileId", attfile.getFdId());
					Object obj = getSysAttMainDao().findFirstOne(hql);
					if (obj!=null) {
						result.add((IBaseModel)obj);
					} else {
						this.add(sysAttMain);
						result.add(sysAttMain);
					}
				} else {
					this.add(sysAttMain);
					result.add(sysAttMain);
				}
			} else {
				this.add(sysAttMain);
				result.add(sysAttMain);
			}
		}
		return result;
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		return add(modelObj, false);
	}

	public String add(IBaseModel modelObj,Boolean newFieldId) throws Exception {
		return add(modelObj, newFieldId, true);
	}

	@Override
	public String add(IBaseModel modelObj,Boolean newFieldId,Boolean addQueue) throws Exception {
		SysAttMain sysAttMain = (SysAttMain) modelObj;
		String fileId = sysAttMain.getFdFileId();
		if(sysAttMain.getFdVersion() == null || 0 == sysAttMain.getFdVersion()) {
            sysAttMain.setFdVersion(1);
        }
		if(sysAttMain.getFdUploadTime()==null) {
            sysAttMain.setFdUploadTime(sysAttMain.getDocCreateTime());
        }
		if(newFieldId) {
			String md5 = MD5Util.getMD5String(IDGenerator.generateID());
			fileId = sysAttUploadService.addFile(md5, 0L, sysAttMain.getInputStream(), true,sysAttMain.getFdFileName());
			sysAttMain.setFdFileId(fileId);
			sysAttMain.setInputStream(null);
			if (logger.isDebugEnabled()) {
                logger.debug("增加附件信息：文件流随附件信息一起传递情况。");
            }
		}else {
			if (StringUtil.isNotNull(fileId)) {
				// 已上传附件至附件服务器的情况
				if (sysAttMain.getFdSize() == null || sysAttMain.getFdSize() == 0) {
					SysAttFile attFile = sysAttUploadService.getFileById(fileId);
					sysAttMain.setFdSize(Double.valueOf(attFile.getFdFileSize()));
				}
				if (logger.isDebugEnabled()) {
                    logger.debug("增加附件信息：上传文件后，再增加附件信息情况。");
                }
			} else {
				// 附件流随sysAttMain对象一起传递到后台的情况
				fileId = sysAttUploadService.addFile(sysAttMain.getInputStream(),sysAttMain.getFdFileName());
				sysAttMain.setFdFileId(fileId);
				sysAttMain.setInputStream(null);
				if (logger.isDebugEnabled()) {
                    logger.debug("增加附件信息：文件流随附件信息一起传递情况。");
                }
			}
		}
		// proccessPic(sysAttMain);
		if (StringUtil.isNull(sysAttMain.getFdCreatorId())) {
			sysAttMain.setFdCreatorId(UserUtil.getUser().getFdId());
		}
		if(StringUtil.isNull(sysAttMain.getFdUploaderId())) {
            sysAttMain.setFdUploaderId(sysAttMain.getFdCreatorId());
        }
//		sysAttMain.setFdAttLocation(SysFileLocationUtil.getLocation().getKey());
		// sysAttSwfService.addByAtt(sysAttMain);
		// sysAttVideoService.addByAtt(sysAttMain);
		//添加时两个sysAttMain是一样的，不需要oldAttMain
		if (sysAttMain.getAddQueue() && addQueue) {
			getConvertQueueService().addQueueAndPdfUpdate(sysAttMain, sysAttMain, sysAttMain.getFdFileId(), sysAttMain.getFdFileName(),
					sysAttMain.getFdModelName(), sysAttMain.getFdModelId(), "", sysAttMain.getFdId());
			getConvertQueueService().addFileToQueue(sysAttMain, sysAttMain);
		}
		return getSysAttMainDao().add(sysAttMain);
	}

	private void addComprassFile(SysAttRtfData sysAttRtfData, SysAttFile attFile, String suffix) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("addFile_上传整个文件，不切片。。");
		}
		String path = sysAttUploadService.getAbsouluteFilePath(attFile) + "_" + suffix;
		ByteArrayOutputStream output = null;
		try {
			output = new ByteArrayOutputStream();
			ImageCompressUtils.getInstance().compressImage(sysAttRtfData.getInputStream(), output,
					Integer.valueOf(sysAttRtfData.getWidth()).intValue(),
					Integer.valueOf(sysAttRtfData.getHeight()).intValue(), true);
			SysFileLocationUtil.getProxyService(attFile.getFdAttLocation())
					.writeFile(output.toByteArray(), path);
		} catch (Throwable e) {
		} finally {
			try {
				output.close();
			} catch (Exception e2) {
			}
		}
	}

	/**
	 * 当附件为图片时,保存原始图片，压缩图片
	 *
	 * @param sysAttMain
	 * @throws Exception
	 */
	private void proccessPic(SysAttMain sysAttMain) throws Exception {
		if ("pic".equals(sysAttMain.getFdAttType())) {
			if (sysAttMain.getWidth() > 0 && sysAttMain.getHeight() > 0) {
				// 存储未压缩图片
				if (logger.isDebugEnabled()) {
                    logger.debug("保存一份未压缩的原始图片附件。");
                }
				byte[] imagesByte = null;
				if (sysAttMain.getInputStream() != null) {
					imagesByte = IOUtils.toByteArray(sysAttMain.getInputStream());
				} else {
					imagesByte = IOUtils.toByteArray(getIntputStream(sysAttMain, false));
				}
				ByteArrayInputStream byteIn = new ByteArrayInputStream(imagesByte);
				SysAttMain original = (SysAttMain) sysAttMain.clone();
				original.setFdKey("original");
				original.setFdModelName(SysAttMain.class.getName());
				original.setFdModelId(sysAttMain.getFdId());
				original.setFdFileId(sysAttMain.getFdFileId());
//				original.setFdAttLocation(SysFileLocationUtil.getLocation().getKey());
				getSysAttMainDao().add(original);

				sysAttMain.setInputStream(byteIn);
				// 压缩图片,更新压缩附件信息的文件id
				if (sysAttMain.getInputStream() != null) {
					if (sysAttMain.getInputStream().markSupported()) {
                        sysAttMain.getInputStream().reset();
                    }
					AttImageUtils.resetInputStream(sysAttMain);
					sysAttMain.setFdFileId(sysAttUploadService.addFile(sysAttMain.getInputStream()
							,sysAttMain.getFdFileName()));
				}
				// 未提交前modelid为空，避免编辑界面添加附件后不提交主文档，导致主文档有该临时附件
				sysAttMain.setFdModelId(null);
			}
		}
	}

	@Override
	public void updateByUser(IBaseModel modelObj,String userId) throws Exception {
		SysAttMain sysAttMain = (SysAttMain) modelObj;
		SysAttMain oldSysAttMain = (SysAttMain)sysAttMain.clone();
		if (sysAttMain.getInputStream() != null) {
			// 图片压缩
			if (logger.isDebugEnabled()) {
                logger.debug("编辑附件处理：存在附件流的情况。");
            }
			AttImageUtils.resetInputStream((SysAttMain) modelObj);
			String returnString = sysAttUploadService.updateFile(sysAttMain.getFdId(), sysAttMain.getFdFileId(),
					sysAttMain.getInputStream());
			if (StringUtil.isNotNull(returnString)) {
				String fileId = returnString.split(";")[0];
				String fileSize = returnString.split(";")[1];
				sysAttMain.setFdFileId(fileId);
				sysAttMain.setFdSize(Double.valueOf(fileSize));
			}
			if (StringUtil.isNull(sysAttMain.getFdFileId())) {
				// 旧数据存在DB的情况
				sysAttMain.setFdData(null);
				// 旧数据存在file的情况
				if (StringUtil.isNotNull(sysAttMain.getFdFilePath())) {
					File oldFile = new File(sysAttMain.getFdFilePath());
					if (oldFile.exists()) {
						oldFile.delete();
					}
					sysAttMain.setFdFilePath(null);
				}
			}
			// sysAttSwfService.updateByAtt(modelObj);
			sysAttMain.setInputStream(null);
//			sysAttMain.setFdAttLocation(SysFileLocationUtil.getLocation().getKey());


			if(StringUtil.isNull(oldSysAttMain.getFdUploaderId())) {
                oldSysAttMain.setFdUploaderId(oldSysAttMain.getFdCreatorId());
            }

			if(StringUtil.isNotNull(userId)){
				sysAttMain.setFdUploaderId(userId);
			}else{
				sysAttMain.setFdUploaderId(UserUtil.getKMSSUser().getPerson().getFdId());
			}

			Boolean isNewVersion=false;
			if (!sysAttMain.getFdUploaderId()
					.equals(oldSysAttMain.getFdUploaderId())
					|| UserUtil.getKMSSUser().isAnonymous()) {// 上下为同一人不更新版本
				isNewVersion=true;
			}

			if (SysAttWpsCloudUtil.isEnable() && sysAttMain.getFdUploaderId()
					.equals(oldSysAttMain.getFdUploaderId())
					&& UserUtil.getKMSSUser().isAnonymous()) {
				isNewVersion = false;
			}
			//wps中台
			if (SysAttWpsCenterUtil.isEnable() && UserUtil.getKMSSUser().isAnonymous()) {
				//同一个人不更新版本
				if (sysAttMain.getFdUploaderId().equals(oldSysAttMain.getFdUploaderId())) {
					isNewVersion = false;
				}else {
					markWpsSaveBack(sysAttMain);
				}
			}
			sysAttMain.setInputStream(null);
			if(((SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_JG.equals(SysAttConfigUtil.getOnlineToolType())
					&& "2".equals(SysAttConfigUtil.isReadJGForMobile()))
					|| ( SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSWPSOAASSIST.equals(SysAttConfigUtil.getOnlineToolType())
					&& "2".equals(SysAttConfigUtil.isReadWPSForMobile())))&&UserUtil.getKMSSUser().isAnonymous()) {
				isNewVersion = false;
			}

			//根据业务模块配置设置isNewVersion
			String ocxName = SysAttOcxUtil.getDocOcxName(sysAttMain.getFdKey(),sysAttMain.getFdModelName());
			if (StringUtil.isNotNull(ocxName)) {
				//拷贝上述内容
				if (SysAttOcxUtil.OCX_WPS_CLOUD.equals(ocxName)
						&& sysAttMain.getFdUploaderId().equals(oldSysAttMain.getFdUploaderId())
						&& UserUtil.getKMSSUser().isAnonymous()) {
					isNewVersion = false;
				} else if (SysAttOcxUtil.OCX_WPS_CENTER.equals(ocxName)
						&& sysAttMain.getFdUploaderId().equals(oldSysAttMain.getFdUploaderId())
						&& UserUtil.getKMSSUser().isAnonymous()) {
					isNewVersion = false;
				} else if ((SysAttOcxUtil.OCX_JG.equals(ocxName) || SysAttOcxUtil.OCX_WPS_OA_ASSIST_VALUE.equals(ocxName))
					&& (UserUtil.getKMSSUser().isAnonymous())) {
					isNewVersion = false;
				}
			}

			if(sysAttMain.getFdVersion()==null || 0 == sysAttMain.getFdVersion()) {
				oldSysAttMain.setFdVersion(1);
				sysAttMain.setFdVersion(2);
			} else {
				if(isNewVersion) {//上下为同一人不更新版本
					oldSysAttMain.setFdVersion(sysAttMain.getFdVersion());
					sysAttMain.setFdVersion(sysAttMain.getFdVersion()+1);
				}
			}

			sysAttMain.setFdUploadTime(new Date());

			if(oldSysAttMain.getFdUploadTime()==null) {
                oldSysAttMain.setFdUploadTime(oldSysAttMain.getDocCreateTime());
            }

			//更新为最新版本附件
			getSysAttMainDao().update(modelObj);

			oldSysAttMain.setFdKey(SysAttBase.HISTORY_NAME);

			//关联数据用于清除
			if (SysAttWpsCenterUtil.isEnable() && isNewVersion == false) {
				isNewVersion = true;
				oldSysAttMain.setFdKey(SysAttBase.WPS_CENTER_TEMP_NAME);
				oldSysAttMain.setFdVersion(0);
			}

			oldSysAttMain.setFdOriginId(sysAttMain.getFdId());

			//新增一份版本附件，上下为同一人不更新版本
			if(isNewVersion) {
                getSysAttMainDao().add(oldSysAttMain);
            }

			if (StringUtil.isNotNull(oldSysAttMain.getFdFileId())) {
				SysAttFile attFile = sysAttUploadService.getFileById(oldSysAttMain.getFdFileId());
				ISysFileLocationProxyService sysFileLocationService = SysFileLocationUtil.getProxyService(attFile.getFdAttLocation());
				sysFileLocationService.deleteFile(attFile.getFdFilePath() + "_bak");

				if (logger.isDebugEnabled()) {
                    logger.debug("删除上一份bak文件。");
                }
			}
		} else {
			if (logger.isDebugEnabled()) {
                logger.debug("编辑附件处理：不存在附件流的情况。");
            }
			if ("pic".equals(sysAttMain.getFdAttType())) {
				// 处理上传的原图片
				List<?> originals = findByModelKey(SysAttMain.class.getName(), sysAttMain.getFdId(), "original");
				// 如无原图片则认为更新，不删除已存在的原图片
				if (originals != null && originals.size() != 0) {
					// 删除更新操作后的无用的原始图片
					if (logger.isDebugEnabled()) {
                        logger.debug("处理附件类型为图片（pic）的附件。");
                    }
					if (sysAttMain.getFdKey() != null && sysAttMain.getFdModelName() != null
							&& sysAttMain.getFdModelId() != null) {
						String deleteHql = "delete from " + SysAttMain.class.getName()
								+ " sysAttMain where sysAttMain.fdKey=:fdKey"
								+ " and sysAttMain.fdModelName=:fdModelName" + " and sysAttMain.fdModelId=:fdModelId";
						Query query = getSysAttMainDao().getHibernateSession().createQuery(deleteHql);
						query.setParameter("fdKey", sysAttMain.getFdKey() + "_original");
						query.setParameter("fdModelName", sysAttMain.getFdModelName());
						query.setParameter("fdModelId", sysAttMain.getFdModelId());
						query.executeUpdate();
					}
				}
				for (int originalIndex = 0; originalIndex < originals.size(); originalIndex++) {
					SysAttMain original = (SysAttMain) originals.get(originalIndex);
					original.setFdKey(sysAttMain.getFdKey() + "_original");
					original.setFdModelId(sysAttMain.getFdModelId());
					original.setFdModelName(sysAttMain.getFdModelName());
					original.setInputStream(null);
					getSysAttMainDao().update(original);
				}
			}
			// sysAttSwfService.updateAll(modelObj);
			getSysAttMainDao().update(modelObj);
		}
		if (sysAttMain.getAddQueue()) {
			getConvertQueueService().addQueueAndPdfUpdate(sysAttMain,oldSysAttMain,sysAttMain.getFdFileId(), sysAttMain.getFdFileName(),
					sysAttMain.getFdModelName(), sysAttMain.getFdModelId(), "", sysAttMain.getFdId());

			getConvertQueueService().addFileToQueue(sysAttMain, oldSysAttMain);
		}

	}

	/**
	 * 标记wps回调save时文档更新版本的model id，model name
	 * @param sysAttMain
	 */
	private void markWpsSaveBack(SysAttMain sysAttMain) {
		//重试获取全局锁的次数
		int count = 3;
		boolean stop = false;
		while(!stop) {
			if(count--<0) {
				logger.warn("发现线程在此尝试获取wpsCenterLockKey超过重试次数"+count+"，开始删除key");
				//当发生无法获取全局锁的情况，尝试删除全局锁，以防止（其他）线程永久等待（饥饿）
				int removeTryCount = 10;
				while(cache.get(SysAttBase.wpsCenterLockKey)!=null) {
					if(removeTryCount--<0) {
						logger.warn("强制删除wpsCenterLockKey失败，可能存在其他线程set该值，当前线程退出");
						break;
					}
					cache.remove(SysAttBase.wpsCenterLockKey);
				}
				break;
			}

			Object o = cache.get(SysAttBase.wpsCenterLockKey);
			logger.warn("get SysAttBase.wpsCenterLockKey: "+o);
			if(o == null) {
				if(logger.isDebugEnabled()) {
					logger.debug("设置wpsCenterLockKey");
				}
				cache.put(SysAttBase.wpsCenterLockKey, SysAttBase.wpsCenterLockKey);
				try {
					Object m = cache.get(SysAttBase.wpsCenterCacheKey);
					if(m == null) {
						cache.put(SysAttBase.wpsCenterCacheKey, new ConcurrentHashMap());
						m = cache.get(SysAttBase.wpsCenterCacheKey);
					}
					ConcurrentHashMap map = (ConcurrentHashMap)m;
					map.put(sysAttMain.getFdModelId(), sysAttMain.getFdModelName());
					cache.put(SysAttBase.wpsCenterCacheKey, map);
				}finally {
					try {
						if(logger.isDebugEnabled()) {
							logger.debug("释放wpsCenterLockKey");
						}
						cache.remove(SysAttBase.wpsCenterLockKey);
					}finally {
						stop = true;
					}
				}
			}else {
				if(logger.isInfoEnabled()) {
					logger.info("found sAttBase.wpsCenterLockKey, sleep.");
				}
			}
			try {
				if (!stop) {
					Thread.sleep(50);
				}
			} catch (InterruptedException e) {

			}
		}
	}

	@Override
	public void deleteAttFile(String fdId) throws Exception {
		sysAttUploadService.deleteRecord(fdId);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		updateByUser(modelObj, "");
		SysAttMain sysAttMain = (SysAttMain) modelObj;

		/*
		 * 移动端是WPS云文档,PC端为WPS加载项,则将附件推送到云端
		 * PC：金格，移动：WPS云文档 或PC：WPS加载项   移动：WPS云文档
		 * 或PC：：WPS云文档   移动：WPS云文档
		 */
		if((SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_JG.equals(SysAttConfigUtil.getOnlineToolType())
				&& "2".equals(SysAttConfigUtil.isReadJGForMobile()))
				|| (SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSWPSOAASSIST.equals(SysAttConfigUtil.getOnlineToolType())
				&& "2".equals(SysAttConfigUtil.isReadWPSForMobile()))
				|| (SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSCLOUD.equals(SysAttConfigUtil.getOnlineToolType())
				&& "2".equals(SysAttConfigUtil.isReadWPSCloudForMobile()))
				&& StringUtil.isNotNull(sysAttMain.getFdKey()) && !"originalAtt".equals(sysAttMain.getFdKey()))
		{
			SysAttWpsCloudUtil.syncAttToAddByMainId(sysAttMain.getFdId());
		}
	}

	@Override
	public void deleteAtt(String fdId) throws Exception {
		SysAttMain sysAttMain = (SysAttMain) this.findByPrimaryKey(fdId,null,true);
		if (sysAttMain != null) {
			delete(sysAttMain);
		}
	}

	@Override
	public void deleteCoreModels(IBaseModel mainModel) throws Exception {
		List coreModels = getCoreModels(mainModel, null);
		for (int i = coreModels.size() - 1; i >= 0; i--) {
            delete((IBaseModel) coreModels.get(i));
        }
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		SysAttMain sysAttMain = (SysAttMain) modelObj;
		UserOperHelper.logDelete(sysAttMain,sysAttMain.getFdFileName());
		if (StringUtil.isNotNull(sysAttMain.getFdFileId())) {
			sysAttUploadService.delete(sysAttMain.getFdFileId());
		} else {
			String tempFilePath = sysAttMain.getFdFilePath();
			if (StringUtil.isNotNull(tempFilePath)) {
				File tempFile = new File(tempFilePath);
				if (tempFile.exists()) {
					tempFile.delete();
				}
			}
		}
		// 删除打印日志内容
		sysAttDownloadLogService.deleteByAttId(sysAttMain.getFdId());
		// sysAttSwfService.deleteByAttId(sysAttMain.getFdId());
		getSysAttMainDao().delete(modelObj);
	}

	@Override
	public List findByModelKey(String modelName, String modelId, String key) throws Exception {
		return getSysAttMainDao().findByModelKey(modelName, modelId, key);
	}

	@Override
	public List findModelKeys(String modelName, String modelId) throws Exception {
		return getSysAttMainDao().findModelKeys(modelName, modelId);
	}

	@Override
	public List findAttListByModel(String modelName, String modelId) throws Exception {
		List attr = getSysAttMainDao().findAttListByModel(modelName, modelId);
		setListData(attr);
		return attr;
	}

	@Override
	public Page findPage(HQLInfo hqlInfo, String modelName, String key) throws Exception {
		hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
				"sysAttMain.fdModelName =:fdModelName and sysAttMain.fdKey =:fdKey"));
		hqlInfo.setParameter("fdKey", key);
		hqlInfo.setParameter("fdModelName", modelName);
		return this.findPage(hqlInfo);
	}

	@Override
	public void findData(String fdId, OutputStream out) throws Exception {
		SysAttMain sysAttMain = (SysAttMain) findByPrimaryKey(fdId);
		outPutAttData(sysAttMain, out);
	}

	@Override
	public void findData(String fdId, String extend, OutputStream out) throws Exception {
		SysAttMain sysAttMain = (SysAttMain) findByPrimaryKey(fdId);
		if (StringUtil.isNotNull(sysAttMain.getFdFileId())) {
			sysAttMain.getFdData();
			InputStream in = sysAttUploadService.getFileData(sysAttMain.getFdFileId(), extend);
			IOUtil.write(in, out);
		}
	}

	@Override
	public void findData(String fdId, String extend, OutputStream out, HttpServletRequest request) throws Exception {
		SysAttMain sysAttMain = (SysAttMain) findByPrimaryKey(fdId);
		if (StringUtil.isNotNull(sysAttMain.getFdFileId())) {
			sysAttMain.getFdData();
			InputStream in = sysAttUploadService.getFileData(sysAttMain.getFdFileId(), extend);
			IOUtil.write(in, out);
		}
	}

	@Override
	public String addRtfData(SysAttRtfData sysAttRtfData, InputStream in) throws Exception {
		String fileId = sysAttUploadService.addFile(in,sysAttRtfData.getFdFileName());
		sysAttRtfData.setFdFileId(fileId);
//		sysAttRtfData.setFdAttLocation(SysFileLocationUtil.getLocation(sysAttRtfData.getFdAttLocation()).getKey());
		String sysAttRtfDataId = getSysAttMainDao().add(sysAttRtfData);
		getConvertQueueService().addQueueWithAttMainModelName(fileId, sysAttRtfData.getFdFileName(), sysAttRtfData.getFdModelName(),
				sysAttRtfData.getFdModelId(), "", sysAttRtfData.getFdId(), SysAttRtfData.class.getName());
		IOUtils.closeQuietly(in);
		return sysAttRtfDataId;
	}

	/**
	 * RTF图片压缩
	 *
	 * @param fileId
	 *            原图fileId
	 * @param sing
	 *            压缩文件后缀名标记
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @throws Exception
	 */
	private void proccessRtfPic(String fileId, InputStream in, String sing, int width) throws Exception {
		byte[] imagesByte = null;
		in = sysAttUploadService.getFileData(fileId);
		imagesByte = IOUtils.toByteArray(in);
		ByteArrayInputStream byteIn = new ByteArrayInputStream(imagesByte);
		BufferedImage img = null;
		try {
			img = ImageIO.read(byteIn);
		} catch (IOException e) {
			logger.warn("该文件不是标准图片类型，不做压缩");
		}
		// 非图不压缩
		if (img == null) {
			return;
		}
		// 原图宽度
		int imgW = img.getWidth();

		SysAttRtfData sysAttRtfData = new SysAttRtfData();
		sysAttRtfData.setWidth(width);
		sysAttRtfData.setHeight(img.getHeight());
		sysAttRtfData.setInputStream(byteIn);
		// 压缩图片,更新压缩附件信息的文件id
		if (sysAttRtfData.getInputStream() != null) {
			if (sysAttRtfData.getInputStream().markSupported()) {
                sysAttRtfData.getInputStream().reset();
            }
			sysAttRtfData.setFdAttType("pic");
			SysAttFile sysAttFile = sysAttUploadService.getFileById(fileId);
			// 不压缩，或者压缩宽度>原图宽度
			if (width == 0 || width >= imgW) {
				// 删除原压缩文件
				sysAttUploadService.deleteFile(sysAttFile, sing);
			} else {
				this.addComprassFile(sysAttRtfData, sysAttFile, sing);
			}
		}
		IOUtils.closeQuietly(in);
	}

	@Override
	public void findRtfData(String fdId, OutputStream out, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysAttRtfData sysAttRtfData = findRtfDataByPrimaryKey(fdId);
		outPutRtfData(sysAttRtfData, out, request, response);
	}

	@Override
	public SysAttMain clone(SysAttMain sysAttMain) throws Exception {
		if (logger.isDebugEnabled()) {
            logger.debug("复制附件处理：不存在附件流的情况。");
        }
		SysAttMain copyAtt = (SysAttMain) sysAttMain.clone();
		copyAtt.setFdId(IDGenerator.generateID());
		copyAtt.setFdCreatorId(UserUtil.getUser().getFdId());
		copyAtt.setDocCreateTime(new Date());
		OutputStream fos = null;
		File tempFile = null;
		try {
			tempFile = File.createTempFile("sys_attachment", "tmp");
			fos = new FileOutputStream(tempFile);
			outPutAttData(sysAttMain, fos);
			copyAtt.setInputStream(new FileInputStream(tempFile));
			copyAtt.setFdModelId(null);
			copyAtt.setFdFileId(null);
			copyAtt.setFdFilePath(null);
		} finally {
			IOUtils.closeQuietly(fos);
		}
		add(copyAtt);
		if (tempFile != null && tempFile.exists() && !tempFile.delete()) {
			logger.error("复制附件处理:删除临时文件错误！");
		}
		return copyAtt;
	}

	@Override
	public SysAttMain clone(SysAttMain sysAttMain,Boolean newFileId) throws Exception {
		if (logger.isDebugEnabled()) {
            logger.debug("复制附件处理：不存在附件流的情况。");
        }
		SysAttMain copyAtt = (SysAttMain) sysAttMain.clone();
		copyAtt.setFdId(IDGenerator.generateID());
		copyAtt.setFdCreatorId(UserUtil.getUser().getFdId());
		copyAtt.setDocCreateTime(new Date());
		OutputStream fos = null;
		File tempFile = null;
		try {
			tempFile = File.createTempFile("sys_attachment", "tmp");
			fos = new FileOutputStream(tempFile);
			outPutAttData(sysAttMain, fos);
			copyAtt.setInputStream(new FileInputStream(tempFile));
			copyAtt.setFdModelId(null);
			copyAtt.setFdFileId(null);
			copyAtt.setFdFilePath(null);
		} finally {
			IOUtils.closeQuietly(fos);
		}
		add(copyAtt,newFileId);
		if (tempFile != null && tempFile.exists() && !tempFile.delete()) {
			logger.error("复制附件处理:删除临时文件错误！");
		}
		return copyAtt;
	}

	@Override
	public SysAttRtfData findRtfDataByPrimaryKey(String fdId) throws Exception {
		return getSysAttMainDao().findRtfDataByPrimaryKey(fdId);
	}

	@Override
	public List findAttData(Date begin, Date end) throws Exception {
		List attList = getSysAttMainDao().findAttData(begin, end);
		setListData(attList);
		return attList;
	}

	@Override
	public List findModelsByIds(String[] fdId) throws Exception {
		List attList = getSysAttMainDao().findModelsByIds(fdId);
		setListData(attList);
		return attList;
	}

	@Override
	public List update(List sysAttMains) throws Exception {
		for (Iterator it = sysAttMains.iterator(); it.hasNext();) {
			SysAttMain sysAttMain = (SysAttMain) it.next();
			this.update(sysAttMain);
		}
		return sysAttMains;
	}


	@Override
	public String addAttachment(IBaseModel model, String fdKey, byte[] content, String fileName, String fdAttType, boolean newFileId) throws Exception {
		return addAttachment(model, fdKey, content, fileName, fdAttType, newFileId, null);
	}

	@Override
	public String addAttachment(IBaseModel model, String fdKey, byte[] content, String fileName, String fdAttType, boolean newFileId, Integer fdOrder) throws Exception {
		return addAttachment(model, fdKey, content, fileName, fdAttType, newFileId, fdOrder, true);
	}

	@Override
	public String addAttachment(IBaseModel model, String fdKey, byte[] content, String fileName, String fdAttType, boolean newFileId, Integer fdOrder, boolean addQueue) throws Exception {
		SysAttMain sysAttMain = new SysAttMain();
		sysAttMain.setDocCreateTime(new Date());
		sysAttMain.setFdFileName(fileName);
		sysAttMain.setFdSize((double) content.length);
		sysAttMain.setFdFileName(fileName);
		sysAttMain.setDocCreateTime(new Date());
		String contentType = FileMimeTypeUtil.getContentType(fileName);
		sysAttMain.setFdContentType(contentType);
		sysAttMain.setInputStream(new ByteArrayInputStream(content));
		sysAttMain.setFdKey(fdKey);
		sysAttMain.setFdModelId(model.getFdId());
		String modelName = ModelUtil.getModelClassName(model);
		sysAttMain.setFdModelName(modelName);
		sysAttMain.setFdAttType(fdAttType);
		sysAttMain.setFdOrder(fdOrder);
		sysAttMain.setAddQueue(addQueue);
		add(sysAttMain,newFileId,addQueue);
		return "/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=" + sysAttMain.getFdId();
	}

	@Override
	public String addAttachment(IBaseModel model, String fdKey, byte[] content, String fileName, String fdAttType)
			throws Exception {
		return addAttachment(model, fdKey, content, fileName, fdAttType, false);
	}

	@Override
	public SysAttMain addAttachment(IBaseModel model, String fdKey, InputStream content, String fileName,
									String fdAttType, Double fileSize, String pathName) throws Exception {
		SysAttMain sysAttMain = new SysAttMain();
		sysAttMain.setDocCreateTime(new Date());
		sysAttMain.setFdFileName(fileName);
		sysAttMain.setFdSize(fileSize);
		sysAttMain.setFdFileName(fileName);
		sysAttMain.setDocCreateTime(new Date());
		String contentType = FileMimeTypeUtil.getContentType(fileName);
		sysAttMain.setFdContentType(contentType);
		sysAttMain.setInputStream(content);
		sysAttMain.setFdKey(fdKey);
		sysAttMain.setFdModelId(model.getFdId());
		String modelName = ModelUtil.getModelClassName(model);
		sysAttMain.setFdModelName(modelName);
		sysAttMain.setFdAttType(fdAttType);
		add(sysAttMain, pathName);
		return sysAttMain;
	}

	@Override
	public SysAttMain addAttachment(String fdModelId, String fdModelName, String fdKey, byte[] content, String fileName, String fdAttType)
			throws Exception {
		SysAttMain sysAttMain = new SysAttMain();
		sysAttMain.setDocCreateTime(new Date());
		sysAttMain.setFdFileName(fileName);
		sysAttMain.setFdSize(Double.valueOf(content.length));
		sysAttMain.setFdFileName(fileName);
		sysAttMain.setDocCreateTime(new Date());
		String contentType = FileMimeTypeUtil.getContentType(fileName);
		sysAttMain.setFdContentType(contentType);
		sysAttMain.setInputStream(new ByteArrayInputStream(content));
		sysAttMain.setFdKey(fdKey);
		sysAttMain.setFdModelId(fdModelId);
		sysAttMain.setFdModelName(fdModelName);
		sysAttMain.setFdAttType(fdAttType);
		add(sysAttMain);
		return sysAttMain;
	}

	private String add(IBaseModel modelObj, String pathName) throws Exception {
		SysAttMain sysAttMain = (SysAttMain) modelObj;
		String fileId = sysAttMain.getFdFileId();
		if (StringUtil.isNotNull(fileId)) {
			// 已上传附件至附件服务器的情况
			if (sysAttMain.getFdSize() == null || sysAttMain.getFdSize() == 0) {
				SysAttFile attFile = sysAttUploadService.getFileById(fileId);
				sysAttMain.setFdSize(Double.valueOf(attFile.getFdFileSize()));
			}
			if (logger.isDebugEnabled()) {
                logger.debug("增加附件信息：上传文件后，再增加附件信息情况。");
            }
		} else {
			// 附件流随sysAttMain对象一起传递到后台的情况
			fileId = sysAttUploadService.addFile(null, sysAttMain.getInputStream().available(),
					sysAttMain.getInputStream(), false, pathName,sysAttMain.getFdFileName());
			sysAttMain.setFdFileId(fileId);
			sysAttMain.setInputStream(null);
			if (logger.isDebugEnabled()) {
                logger.debug("增加附件信息：文件流随附件信息一起传递情况。");
            }
		}
		// proccessPic(sysAttMain);
		if (StringUtil.isNull(sysAttMain.getFdCreatorId())) {
            sysAttMain.setFdCreatorId(UserUtil.getUser().getFdId());
        }
//		sysAttMain.setFdAttLocation(SysFileLocationUtil.getLocation().getKey());
		getConvertQueueService().addQueue(sysAttMain.getFdFileId(), sysAttMain.getFdFileName(),
				sysAttMain.getFdModelName(), sysAttMain.getFdModelId(), "", sysAttMain.getFdId());
		return getSysAttMainDao().add(sysAttMain);
	}

	@Override
	public String addSingleAttachment(IBaseModel model, String fdKey, byte[] content, String fileName, String fdAttType)
			throws Exception {
		getSysAttMainDao().clearAttachment(model, fdKey);
		return addAttachment(model, fdKey, content, fileName, fdAttType);
	}

	@Override
	public void delete(IBaseModel model, String fdKey) throws Exception {
		// 删除打印日志内容
		List list = getCorePropsModels(model, fdKey);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				// fdId,fdFileName,fdContentType
				Object[] row = (Object[]) list.get(i);
				sysAttDownloadLogService
						.deleteByAttId(row[0].toString());
			}
		}
		getSysAttMainDao().clearAttachment(model, fdKey);
	}

	@Override
	public List getCorePropsModels(IBaseModel mainModel, String key) throws Exception {
		return getSysAttMainDao().getCorePropsModels(mainModel, key);
	}

	private void outPutAttData(SysAttBase sysAttMain, OutputStream out) throws Exception {
		InputStream in = null;
		in = getIntputStream(sysAttMain, false);
		IOUtil.write(in, out);
	}

	/**
	 * 获取rtf图片流
	 *
	 * @param sysAttMain
	 * @param out
	 * @throws Exception
	 */
	private void outPutRtfData(SysAttBase sysAttMain, OutputStream out, HttpServletRequest request,
							   HttpServletResponse response) throws Exception {
		InputStream in = null;
		if (StringUtil.isNotNull(sysAttMain.getFdFileId())) {
			String picthumb = request.getParameter("picthumb");
			if ("original".equals(picthumb)) {
				in = sysAttUploadService.getFileData(sysAttMain.getFdFileId());
			} else {
				try {
					in = sysAttUploadService.getFileData(sysAttMain.getFdFileId(), picthumb);
				} catch (Exception e) {
					logger.debug("获取rtf压缩图片异常");
				}
				// 原图不需设置ContentLength，否则部分环境下会导致图片下载缓慢
				if (StringUtil.isNull(picthumb) || in == null || in.available() == 0) {
					in = sysAttUploadService.getFileData(sysAttMain.getFdFileId());
				} else {
					byte[] inputSreamByt = IOUtils.toByteArray(in);
					if (!ServerUtil.isWebLogic()) {
						response.setContentLength(inputSreamByt.length);
					}
					in = new ByteArrayInputStream(inputSreamByt);
				}
			}
		} else {
			if (SysAttMain.ATTACHMENT_LOCATION_DB.equalsIgnoreCase(sysAttMain.getFdAttLocation())) {// db 类型的附件
				if (sysAttMain.getFdData() != null && sysAttMain.getFdData().getBinaryStream() != null) {
					in = sysAttMain.getFdData().getBinaryStream();
				}
			} else if (SysAttMain.ATTACHMENT_LOCATION_FILE.equalsIgnoreCase(sysAttMain.getFdAttLocation())){// file 类型的附件 ,FilePath为绝对路径
				in = new FileInputStream(sysAttMain.getFdFilePath());
			}
		}
		IOUtil.write(in, out);
		if (ServerUtil.isWebLogic()) {
			response.flushBuffer();
		}
	}

	private void setListData(List attData) throws Exception {
		for (int i = 0; i < attData.size(); i++) {
			SysAttMain attMain = (SysAttMain) attData.get(i);
			attMain.setInputStream(getIntputStream(attMain, true));
		}
	}

	@Override
	public InputStream getInputStream(String id) throws Exception {
		return getInputStream((SysAttMain) getSysAttMainDao().findByPrimaryKey(id));
	}

	@Override
	public InputStream getInputStreamByFile(String fileId) throws Exception {
		if (StringUtil.isNotNull(fileId)) {
            return sysAttUploadService.getFileData(fileId);
        }
		return null;
	}

	@Override
	public InputStream getInputStream(SysAttMain sysAttMain) throws Exception {
		return getIntputStream(sysAttMain, true);
	}

	private InputStream getIntputStream(SysAttBase sysAttMain, boolean changeToFile) throws Exception {
		InputStream in = null;
		try {
			if (StringUtil.isNotNull(sysAttMain.getFdFileId())) {
				in = sysAttUploadService.getFileData(sysAttMain.getFdFileId());
			} else {
				if (SysAttMain.ATTACHMENT_LOCATION_DB.equalsIgnoreCase(sysAttMain.getFdAttLocation())) {// db 类型的附件
					if (sysAttMain.getFdData() != null && sysAttMain.getFdData().getBinaryStream() != null) {
						if (changeToFile) {
							File tempFile = File.createTempFile("sys_attachment", "tmp");
							FileOutputStream fos = null;
							try {
								fos = new FileOutputStream(tempFile);
								if (logger.isDebugEnabled()) {
									logger.debug("Read to copy sysAttMain[" + sysAttMain.getFdId() + "] to file: " + tempFile.getAbsolutePath());
								}
								IOUtil.write(new DecryptionInputStream(sysAttMain.getFdData().getBinaryStream()), fos);
								in = new FileInputStream(tempFile);
							} finally {
								if (fos != null) {
									try {
										fos.close();
									} catch (IOException ioe) {
										//ignore
									}
								}
							}
						} else {
                            in = new DecryptionInputStream(sysAttMain.getFdData().getBinaryStream());
                        }
					}
				} else if (SysAttMain.ATTACHMENT_LOCATION_FILE.equalsIgnoreCase(sysAttMain.getFdAttLocation())) {// file 类型的附件 ,FilePath为绝对路径
					in = new DecryptionInputStream(new FileInputStream(sysAttMain.getFdFilePath()));
				}
			}
		}catch (Exception e){
			IOUtils.closeQuietly(in);
		}
		return in;
	}

	@Override
	public void updateClearAtt(SysQuartzJobContext context) throws Exception {
		Session session = getBaseDao().getHibernateSession();
		Calendar attExpireCal = Calendar.getInstance();
		attExpireCal.set(Calendar.DAY_OF_MONTH, attExpireCal.get(Calendar.DAY_OF_MONTH) - 1);

		Query q = session.createNativeQuery("select fd_id from sys_att_main "
				+ "where (fd_model_id is null or fd_model_id = '') " + "and doc_create_time <= :attExpireCal ")
				.setParameter("attExpireCal", attExpireCal.getTime());
		List attIdList = q.list();
		if (!attIdList.isEmpty()) {
			delete((String[]) attIdList.toArray(new String[attIdList.size()]));
			context.logMessage("清理无用的附件信息(SysAttMain),共计" + attIdList.size() + "条。");
		}

		attIdList = null;
		String expireCfg = ResourceUtil.getKmssConfigString("sys.att.slice.expire");
		if (StringUtil.isNull(expireCfg)) {
			expireCfg = "1";
		}
		int monthExpire = Integer.valueOf(expireCfg);
		attExpireCal = Calendar.getInstance();
		attExpireCal.set(Calendar.MONTH, attExpireCal.get(Calendar.MONTH) - monthExpire);
		String where = " WHERE fd_file_id not in ("
				+ "SELECT fd_file_id FROM sys_att_main WHERE fd_file_id IS NOT NULL "
				+ "UNION SELECT fd_file_id FROM sys_att_rtf_data WHERE fd_file_id IS NOT NULL) "
				+ "and fd_delete_time <:timpStampVal";
		q = session.createNativeQuery("SELECT fd_file_id FROM sys_att_tmp" + where).setParameter("timpStampVal",
				attExpireCal.getTime());
		attIdList = q.list();
		if (!attIdList.isEmpty()) {
			for (int i = 0; i < attIdList.size(); i++) {
				sysAttUploadService.delete((String) attIdList.get(i), true);
			}
			NativeQuery nativeQuery = session.createNativeQuery("delete from sys_att_tmp" + where);
			int tmpCount = nativeQuery.addSynchronizedQuerySpace("sys_att_tmp")
					.setParameter("timpStampVal", attExpireCal.getTime()).executeUpdate();
			context.logMessage("清理无用 的附件关联信息(SysAttTmp),共计" + tmpCount + "条,删除相关联的文件信息" + attIdList.size() + "条。");
		}

		attIdList = null;
		where = " WHERE fd_id not in (" + "SELECT fd_file_id FROM sys_att_main WHERE fd_file_id IS NOT NULL "
				+ "UNION SELECT fd_file_id FROM sys_att_rtf_data WHERE fd_file_id IS NOT NULL) "
				+ "and doc_create_time <:timpStampVal";
		NativeQuery nativeQuery = session.createNativeQuery("SELECT fd_id FROM sys_att_file" + where);
		q = nativeQuery.addSynchronizedQuerySpace("sys_att_file").setParameter("timpStampVal",
				attExpireCal.getTime());
		attIdList = q.list();
		if (!attIdList.isEmpty()) {
			for (int i = 0; i < attIdList.size(); i++) {
				sysAttUploadService.delete((String) attIdList.get(i), true);
			}
			context.logMessage(
					"清理没有使用的附件信息(SysAttFile),共计" + attIdList.size() + "条,删除相关联的文件信息" + attIdList.size() + "条。");
		}

		attIdList = null;
		q = session.createNativeQuery("select fd_file_id from sys_att_file_slice "
				+ "where fd_modify_time <:timpStampVal group by fd_file_id")
				.setParameter("timpStampVal", attExpireCal.getTime().getTime());
		attIdList = q.list();
		if (!attIdList.isEmpty()) {
			for (int i = 0; i < attIdList.size(); i++) {
				sysAttUploadService.delete((String) attIdList.get(i), true);
			}
			context.logMessage("清理未上传完的附件信息(SysAttFile),共计" + attIdList.size() + "条。");
		}
		attIdList = null;
		String videoEnabled = ResourceUtil.getKmssConfigString("kmss.att.video.enabled");
		if (videoEnabled != null && "true".equals(videoEnabled.trim())) {
			q = session.createNativeQuery(
					"select sys_att_video.fd_id,sys_att_video.fd_file_path from Sys_att_video sys_att_video where sys_att_video.fd_att_id not in(select sys_att_main.fd_id from Sys_att_main sys_att_main) and sys_att_video.fd_create_time <:timpStampVal")
					.setParameter("timpStampVal", attExpireCal.getTime());
			attIdList = q.list();
			if (!attIdList.isEmpty()) {
				for (int i = 0; i < attIdList.size(); i++) {
					Object[] obj = (Object[]) attIdList.get(i);
				}
				context.logMessage("清理视频附件信息(SysAttVideo),共计" + attIdList.size() + "条。");
			}
		}
	}

	@Override
	public void delTmpFile(SysQuartzJobContext context) throws Exception {
		MessageCenter.getInstance().sendToOther(new TmpCleanMessage(this.runnabled));
		long dateNum = System.currentTimeMillis() - DateUtil.HOUR * 2;
		String dirPath = FileUtil.getSystemTempPath();
		if (StringUtil.isNull(dirPath)) {
			return;
		}
		File directory = new File(dirPath);
		File[] files = directory.listFiles();
		if (files == null) {
			return;
		}
		logger.info("开始清理临时文件夹：" + directory.getAbsolutePath());
		for (File file : files) {
			if (file.isFile() && dateNum > file.lastModified()) {
				try {
					if (file.delete()) {
						logger.info("删除文件：" + file.getName() + " 成功！");
						if (context != null) {
							context.logMessage("删除文件：" + file.getName() + " 成功！");
						}
					}
				} catch (Exception e) {
					logger.error("无法删除文件：" + file.getPath(), e);
				}
			}
		}
	}

	protected boolean runnabled = true;

	protected IMessageQueue messageQueue = new UniqueMessageQueue();

	@Override
	public IMessageQueue getMessageQueue() {
		return messageQueue;
	}

	@Override
	public void receiveMessage(IMessage message) throws Exception {
		if (((TmpCleanMessage) message).isImmediateRun()) {
			this.runnabled = false;
			this.delTmpFile(null);
		}
	}

	private String formatSize(String filesize) throws Exception {
		String result = "";
		if (filesize != null) {
			BigDecimal Num = new BigDecimal(filesize);
			double size = Num.doubleValue();
			if (size < 1024) {
                result = size + "B";
            } else {
				size = Math.round(size * 100 / 1024) / 100;
				if (size < 1024) {
                    result = size + "KB";
                } else {
					size = Math.round(size * 100 / 1024) / 100;
					if (size < 1024) {
                        result = size + "M";
                    } else {
						size = Math.round(size * 100 / 1024) / 100;
						result = size + "G";
					}
				}
			}
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public JSONArray findFilePath(String fdDocIds) throws Exception {
		JSONArray jsonArr = new JSONArray();
		Session session = getBaseDao().getHibernateSession();
		String sql = "select a.fd_file_name,a.fd_key,a.fd_size,f.fd_id,a.fd_model_id,a.fd_model_name from sys_att_file f left join sys_att_main a on f.fd_id = a.fd_file_id where a.fd_model_id in ('"
				+ fdDocIds.replace(";", "','") + "')";
		Query q = session.createNativeQuery(sql);
		List attList = q.list();
		for (int i = 0; i < attList.size(); i++) {
			Object[] obj = (Object[]) attList.get(i);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("fdFileName", obj[0]);
			jsonObj.put("fdKey", obj[1]);
			String fdSize = obj[2].toString();
			jsonObj.put("fdSize", formatSize(fdSize));
			String FilePath = sysAttUploadService.getAbsouluteFilePath((String) obj[3]);
			String bakf = FilePath + "_bak";
			SysAttFile file = sysAttUploadService.getFileById((String) obj[3]);
			String pathPrefix = file.getFdCata() == null ? null : file.getFdCata().getFdPath();
			if (SysFileLocationUtil.getProxyService(file.getFdAttLocation()).doesFileExist(bakf,pathPrefix)) {
				jsonObj.put("fdExist", "true");
			} else {
				jsonObj.put("fdExist", "false");
			}
			jsonObj.put("fdFilePath", FilePath);

			SysDictModel model = SysDataDict.getInstance().getModel((String) obj[5]);
			if (model != null && !StringUtil.isNull(model.getUrl())) {
				jsonObj.put("fdLink", formatModelUrl((String) obj[4], model.getUrl()));
			}
			jsonArr.add(jsonObj);
		}
		return jsonArr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject restorefile(String[] filePath) throws Exception {
		JSONObject jsonObj = new JSONObject();
		List<String> successList = new ArrayList<String>();
		List<String> failList = new ArrayList<String>();
		ISysFileLocationProxyService sysFileLocationService = SysFileLocationUtil.getProxyService();
		for (int i = 0; i < filePath.length; i++) {
			String bakFile = filePath[i] + "_bak";
			SysAttFile attFile = sysAttUploadService.getFileByPath(bakFile);
			String pathPrefix = attFile.getFdCata() == null ? null : attFile.getFdCata().getFdPath();
			if (SysFileLocationUtil.getProxyService(attFile.getFdAttLocation()).doesFileExist(bakFile,pathPrefix)) {
				InputStream bakFileInputStream = SysFileLocationUtil
						.getProxyService(attFile.getFdAttLocation()).readFile(bakFile,pathPrefix);
				// 根据备份文件生成附件文件
				sysFileLocationService.writeFile(bakFileInputStream, filePath[i]);
				IOUtils.closeQuietly(bakFileInputStream);
				successList.add(filePath[i]);
			} else {
				failList.add(filePath[i]);
			}
		}
		jsonObj.put("success", successList);
		jsonObj.put("fail", failList);
		return jsonObj;
	}

	private static String formatModelUrl(String value, String url) {
		if (StringUtil.isNull(url)) {
            return null;
        }
		Pattern p = Pattern.compile("\\$\\{([^}]+)\\}");
		Matcher m = p.matcher(url);
		while (m.find()) {
			String property = m.group(1);
			try {
				url = StringUtil.replace(url, "${" + property + "}", value);
			} catch (Exception e) {
			}
		}
		return url;
	}

	@Override
	public void addDownloadCount(String attId) throws Exception {
		String hql = "update SysAttMain set downloadSum = downloadSum + 1 where fdId = :fdId";
		getBaseDao().getHibernateSession().createQuery(hql)
				.setParameter("fdId", attId).executeUpdate();
	}

	@Override
	public JSONObject findMainDocInfo(String fdId) throws Exception {

		JSONObject info = new JSONObject();
		SysAttMain main = (SysAttMain) this.findByPrimaryKey(fdId,null,true);
		if(main !=null){
			String fdModelId = main.getFdModelId();
			String fdModelName = main.getFdModelName();
			if(StringUtil.isNotNull(fdModelId)&&StringUtil.isNotNull(fdModelName)){
				SysDictModel docModel = SysDataDict.getInstance().getModel(fdModelName);
				if(docModel!=null){

					IBaseService docService = (IBaseService) SpringBeanUtil.getBean(docModel.getServiceBean());
					IBaseModel model = docService.findByPrimaryKey(fdModelId, null, true);
					String url = ModelUtil.getModelUrl(model);
					info.put("url", url);
				}

			}
		}
		return info;
	}

	@Override
	public void addFile(String fdMd5, String fdFileSize, String fdId,
						String fdPath) throws Exception {
		sysAttUploadService.addFile(fdId, Long.valueOf(fdFileSize), fdMd5,
				fdPath);
	}

	@Override
	public String getFilePath(String fdId) throws Exception {
		SysAttMain sysAttMain = (SysAttMain) this.findByPrimaryKey(fdId);
		return sysAttUploadService.getFileById(sysAttMain.getFdFileId())
				.getFdFilePath();
	}

	@Override
	public SysAttFile getFile(String fdId) throws Exception {
		SysAttMain sysAttMain = (SysAttMain) this.findByPrimaryKey(fdId);
		if(sysAttMain == null) {
			return null;
		}
		return sysAttUploadService.getFileById(sysAttMain.getFdFileId());
	}

	@Override
	public SysAttFile getFileByPath(String filePath) throws Exception {
		return sysAttUploadService.getFileByPath(filePath);
	}

	@SuppressWarnings("unchecked")
	@Override
	public SysAttMain getAttMainByPath(String filePath) throws Exception {
		SysAttFile attFile = sysAttUploadService.getFileByPath(filePath);
		if(StringUtil.isNull(attFile.getFdFilePath())){
			return null;
		}
		HQLInfo hql = new HQLInfo();
		hql.setModelName(SysAttMain.class.getName());
		hql.setWhereBlock("fdFileId=:fdFileId");
		hql.setParameter("fdFileId", attFile.getFdId());
		hql.setOrderBy("docCreateTime asc");
		SysAttMain obj = (SysAttMain)findFirstOne(hql);
		return obj;
	}

	@Override
	public SysAttFile getFileByMd5(String fileMd5, long fileSize) throws Exception {
		return sysAttUploadService.getFileByMd5(fileMd5, fileSize);
	}

	@Override
	public boolean validateDownloadSignature(String expires, String fdId
			, String sign) throws Exception {
		// 外部过来的下载请求，如果signature验证通过，允许免登录下载
		boolean auth = checkDownloadSignature(sign,expires,fdId);
		if (auth){
			logger.info("SysAttachmentSignValidator.validate success,no need to login. download attMainId:"
					+ fdId + "\nexpires:" + expires + ",sign:" + sign);
			return true;
		}
		return false;
	}

	@Override
	public boolean validateDownloadSignatureRest(String expires, String fdId, String sign) throws Exception {
		// 外部过来的下载请求，如果signature验证通过，允许免登录下载
		boolean auth = checkDownloadSignatureRest(sign, expires, fdId);
		if (auth) {
			logger.info("SysAttachmentSignValidator.validate success,no need to login. download attMainId:" + fdId
					+ "\nexpires:" + expires + ",sign:" + sign);
			return true;
		}
		return false;
	}

	/**
	 * 外部过来的下载请求，验证signature和expires，验证通过后允许下载附件
	 * @param sign
	 * @param expires
	 * @return
	 */
	private boolean checkDownloadSignature(String sign, String expires, String fdId) throws Exception {
		boolean auth = false;
		if(StringUtil.isNotNull(sign) && StringUtil.isNotNull(expires)){
			sign = sign.replaceAll(" ", "+");
			long expiresTime = Long.valueOf(expires);
			if(expiresTime < System.currentTimeMillis()){
				return false;
			}
			String signStr = expires + ":" + fdId;
			ISysWebserviceMainService sysWsMainService = (ISysWebserviceMainService)
					SpringBeanUtil.getBean("sysWebserviceMainService");
			SysWebserviceMain sysWsMain = sysWsMainService.findByServiceBean("coWritingWSService");
			List<SysWebserviceUser> webUsers = sysWsMain.getFdUser();
			if(ArrayUtil.isEmpty(webUsers)){
				return false;
			}
			for (Iterator<SysWebserviceUser> iterator = webUsers.iterator(); iterator.hasNext();) {
				SysWebserviceUser webUser = iterator.next();
				String calcSign = SignUtil.getHMAC(signStr + ":" + webUser.getFdUserName()
						, StringUtil.isNotNull(webUser.getFdPassword()) ? webUser.getFdPassword() : webUser.getFdId());

				if(sign.equals(calcSign)){
					auth = true;
					break;
				}
			}
		}
		return auth;
	}

	private boolean checkDownloadSignatureRest(String sign, String expires, String fdId) throws Exception {
		boolean auth = false;
		String redisSign = (String)SysAttUtil.tokenCache.get(sign);

		if (StringUtil.isNotNull(sign) && StringUtil.isNotNull(expires)) {
			sign = sign.replaceAll(" ", "+");
			long expiresTime = Long.valueOf(expires);
			if (expiresTime < System.currentTimeMillis()) {
				return false;
			}

			if(StringUtil.isNotNull(redisSign) && !"false".equals(redisSign)) { //加入一次有效的逻辑，当redis有值并且不为false的时候做校验，即已经被使用过一次。（兼容以前没有一次有效的功能逻辑）。
				return false;
			}

			String signStr = expires + ":" + fdId;
			ISysRestserviceServerMainService sysRestMainService = (ISysRestserviceServerMainService) SpringBeanUtil
					.getBean("sysRestserviceServerMainService");
			SysRestserviceServerMain sysRestserviceServerMain = sysRestMainService
					.findByServiceBean("sysAttachmentRestService");
			List<SysRestserviceServerPolicy> webPolicys = sysRestserviceServerMain.getFdPolicy();
			if (ArrayUtil.isEmpty(webPolicys)) {
				return false;
			}
			for (Iterator<SysRestserviceServerPolicy> iterator = webPolicys.iterator(); iterator.hasNext();) {
				SysRestserviceServerPolicy webPolicy = iterator.next();
				String calcSign = SignUtil.getHMAC(signStr + ":" + webPolicy.getFdLoginId(),
						StringUtil.isNotNull(webPolicy.getFdPassword()) ? webPolicy.getFdPassword()
								: webPolicy.getFdId());
				if (sign.equals(calcSign)) {
					auth = true;
					break;
				}
			}
		}

		if(auth==true && "false".equals(redisSign)) {//当校验通过并且redis值为未使用的时候，重新设置为true
			Long time = Long.valueOf(expires) - System.currentTimeMillis();
			if(time>1000) { //大于1秒才重新设置redis
				SysAttUtil.tokenCache.put(sign, "true", time.intValue());
			}
		}

		return auth;
	}

	@Override
	public void statistics(HttpServletRequest request, SysAttMainForm form)
			throws Exception {

		// 文件大小
		request.setAttribute("fdSize", SysAttViewerUtil
				.convertFileSize(Double.valueOf(form.getFdSize())));

		String fdModelName = form.getFdModelName();
		String fdModelId = form.getFdModelId();

		if (StringUtil.isNull(fdModelName)) {
			return;
		}

		// 模块名称
		request.setAttribute("fdModuleName", getMainModuleName(fdModelName));
		String[] urlAndName = getMainUrlAndName(fdModelId, fdModelName);

		// 主文档阅读路径
		request.setAttribute("fdMainUrl", urlAndName[0]);

		// 主文档名称
		if (StringUtil.isNull(urlAndName[1])) {
			return;
		}
		request.setAttribute("fdMainName", urlAndName[1]);

	}

	/**
	 * 获取主文档模块名
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getMainModuleName(String modelName) throws Exception {

		SysDictModel dict = SysDataDict.getInstance().getModel(modelName);

		if (dict == null) {
			return null;
		}

		return ResourceUtil.getString(dict.getMessageKey());
	}

	/**
	 * 获取主文档链接和标题
	 * @return
	 * @throws Exception
	 */
	@Override
	public String[] getMainUrlAndName(String modelId, String modelName)
			throws Exception {

		SysDictModel dict = SysDataDict.getInstance().getModel(modelName);

		if (dict == null) {
			return null;
		}

		String fdMainName = null;
		String fdUrl = null;

		try {
			IBaseService service = (IBaseService) SpringBeanUtil
					.getBean(dict.getServiceBean());
			IBaseModel baseModel =
					(IBaseModel) service.findByPrimaryKey(modelId);

			// 主文档名称

			if (PropertyUtils.isReadable(baseModel, "docSubject")) {
				fdMainName = PropertyUtils.getProperty(baseModel, "docSubject")
						.toString();
			}

			if (StringUtil.isNull(fdMainName)) {
				if (PropertyUtils.isReadable(baseModel, "fdName")) {
					fdMainName = PropertyUtils.getProperty(baseModel, "fdName")
							.toString();
				}
			}

			fdUrl = ModelUtil.getModelUrl(baseModel);
		} catch (Exception e) {
			logger.error("获取主文档标题和链接报错：" + e);
		}

		return new String[] { fdUrl, fdMainName };
	}

	@Override
	public String[] checkShare(HttpServletRequest request) throws Exception {

		// 需要转化的附件
		String[] fdAttIds = request.getParameterValues("fdAttIds");

		if (ArrayUtils.isEmpty(fdAttIds)) {
			return null;
		}

		List<String> ids = new ArrayList<>();
		// 有下载权限的附件才能转换
		for (String fdAttId : fdAttIds) {
			Boolean auth = UserUtil.checkAuthentication(
					"/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId="
							+ fdAttId,
					"get");

			// 只要有一个没权都算是越权
			if (auth) {
				ids.add(fdAttId);
			}
		}

		return ids.toArray(new String[0]);

	}

	@Override
	public boolean updateRelation(HttpServletRequest request) throws Exception {
		boolean flag = true;
		try {
			String fdAttMainId = request.getParameter("fdAttMainId");
			String fdModelId = request.getParameter("fdModelId");
			String fdModelName = request.getParameter("fdModelName");
			String fdKey = request.getParameter("fdKey");
			SysAttMain sysAttMain = (SysAttMain) this.findByPrimaryKey(fdAttMainId);
			if (StringUtil.isNotNull(sysAttMain.getFdTempKey())) {
				sysAttMain.setFdTempKey("");
			}
			sysAttMain.setFdKey(fdKey);
			sysAttMain.setFdModelId(fdModelId);
			sysAttMain.setFdModelName(fdModelName);
			this.update(sysAttMain);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean updateCloudRelation(HttpServletRequest request)
			throws Exception {
		boolean flag = true;
		try {
			String fdAttMainId = request.getParameter("fdAttMainId");
			SysAttWpsCloudUtil.updateAttByMainId(fdAttMainId);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public SysAttMain addOnlineFile(HttpServletRequest request) throws Exception {

		SysAttMain copyAtt = new SysAttMain();
		String fdTemplateModelId = request.getParameter("fdTemplateModelId");
		String fdTemplateModelName = request.getParameter("fdTemplateModelName");
		String fdTemplateModelKey = request.getParameter("fdTemplateKey");
		String fdTempKey = request.getParameter("fdTempKey");
		String fdKey = request.getParameter("fdKey");
		String fdModelId = request.getParameter("fdModelId");
		String fdModelName = request.getParameter("fdModelName");
		Object attObj = this.findFirstOne("sysAttMain.fdKey = '" + fdKey + "' and sysAttMain.fdModelId = '"
				+ fdModelId + "' and sysAttMain.fdModelName='" + fdModelName + "'", "");
		ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
				.getBean("sysAttMainService");
		if (attObj == null) {
			InputStream in = null;
			try {
				if (StringUtil.isNotNull(fdTemplateModelId) && StringUtil.isNotNull(fdTemplateModelName)
						&& StringUtil.isNotNull(fdTemplateModelKey)) {
					Object obj = this.findFirstOne(
							"sysAttMain.fdKey = '" + fdTemplateModelKey + "' and sysAttMain.fdModelId = '"
									+ fdTemplateModelId + "' and sysAttMain.fdModelName='" + fdTemplateModelName + "'",
							"");
					if (obj!=null) {
						SysAttMain tempAtt = (SysAttMain)obj;
						in = this.getInputStream(tempAtt);
					}
				}
				if (in == null) {
					File file = new File(ConfigLocationsUtil.getWebContentPath()
							+ "/sys/attachment/templateFile/editOnlineTemplate.doc");
					if (file.exists()) {
						in = new FileInputStream(file);
					}
				}
				copyAtt.setInputStream(in);
				copyAtt.setFdModelId(fdModelId);
				copyAtt.setFdModelName(fdModelName);
				copyAtt.setFdKey(fdKey);
				copyAtt.setFdTempKey(fdTempKey + "_online_create");
				double fileSize = in.available();
				copyAtt.setFdSize(fileSize);
				copyAtt.setFdContentType("application/msword");
				copyAtt.setFdAttType("office");
				copyAtt.setFdFileName("editonline.doc");
				sysAttMainService.add(copyAtt);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (in != null) {
					in.close();
				}
			}
		} else {
			copyAtt = (SysAttMain) attObj;
		}

		return copyAtt;
	}

	@Override
	public SysAttMain addCloudOnlineFile(HttpServletRequest request)
			throws Exception {
		if (logger.isDebugEnabled()) {
            logger.debug("复制附件处理：不存在附件流的情况。");
        }
		SysAttMain copyAtt = new SysAttMain();
		String fdTemplateModelId = request.getParameter("fdTemplateModelId");
		String fdTemplateModelName = request
				.getParameter("fdTemplateModelName");
		String fdTemplateModelKey = request.getParameter("fdTemplateKey");
		String fdKey = request.getParameter("fdKey");
		String fdModelName = request.getParameter("fdModelName");
		String fdModelId = request.getParameter("fdModelId");
		String fdFileExt = request.getParameter("fdFileExt");
		String isPrint = request.getParameter("isPrint");

		Object attObj = this.findFirstOne(
				"sysAttMain.fdKey = '" + fdKey
						+ "' and sysAttMain.fdModelId = '"
						+ fdModelId
						+ "' and sysAttMain.fdModelName='"
						+ fdModelName + "'",
				"");
		if (attObj == null) {
			InputStream in = null;
			if (StringUtil.isNotNull(fdTemplateModelId)
					&& StringUtil.isNotNull(fdTemplateModelName)
					&& StringUtil.isNotNull(fdTemplateModelKey)) {
				Object obj = this.findFirstOne(
						"sysAttMain.fdKey = '" + fdTemplateModelKey
								+ "' and sysAttMain.fdModelId = '"
								+ fdTemplateModelId
								+ "' and sysAttMain.fdModelName='"
								+ fdTemplateModelName + "'",
						"");
				if (obj != null) {
					SysAttMain tempAtt = (SysAttMain)obj;
					String fdDownLoadUrl = SysAttWpsCloudUtil.getWpsDownloadUrl(tempAtt.getFdId());
					if (StringUtil.isNotNull(fdDownLoadUrl)) {
						in = FileDowloadUtil.getFileInputStream(fdDownLoadUrl);
					} else {
						in = this.getInputStream(tempAtt);
					}
				}
			}
			if (in == null) {
				String templateFilePath = "/sys/attachment/templateFile/editOnlineTemplate.doc";
				if ("docx".equals(fdFileExt)) {
					templateFilePath = "/sys/attachment/templateFile/editOnlineTemplate.docx";
				}
				if ("true".equals(isPrint)) {
					templateFilePath = "/sys/attachment/templateFile/editOnlinePrint.docx";
				}
				File file = new File(ConfigLocationsUtil.getWebContentPath()
						+ templateFilePath);
				if (file.exists()) {
					in = new FileInputStream(file);
				}
			}

			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			byte[] buff = new byte[100];
			int rc = 0;
			while ((rc = in.read(buff, 0, 100)) > 0) {
				bao.write(buff, 0, rc);
			}

			String appendStr = copyAtt.getFdId();
			byte[] appendByte = appendStr.getBytes();
			bao.write(appendByte);
			byte[] byt = bao.toByteArray();
			InputStream newin = new ByteArrayInputStream(byt);
			copyAtt.setInputStream(newin);
			copyAtt.setFdModelId(fdModelId);
			copyAtt.setFdModelName(fdModelName);
			copyAtt.setFdKey(fdKey);
			double fileSize = newin.available();
			copyAtt.setFdSize(fileSize);
			copyAtt.setFdAttType("office");
			if ("docx".equals(fdFileExt)) {
				copyAtt.setFdFileName("editonline.docx");
				copyAtt.setFdContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
			} else {
				copyAtt.setFdFileName("editonline.doc");
				copyAtt.setFdContentType("application/msword");
			}
			if ("true".equals(isPrint)) {
				copyAtt.setFdFileName("editOnlinePrint.docx");
			}

			ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
					.getBean("sysAttMainService");
			try {
				sysAttMainService.add(copyAtt);
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				// TODO: handle finally clause
				if (in != null) {
					in.close();
				}
				if (newin != null) {
					newin.close();
				}
			}
			// 上传到wps
			SysAttWpsCloudUtil.syncAttToAddByMainId(copyAtt.getFdId());

		} else {
			copyAtt = (SysAttMain) attObj;
		}
		return copyAtt;
	}


	@Override
	public JSONObject getWpsUrlAndToken(HttpServletRequest request) throws Exception {
		JSONObject json = new JSONObject();
		String fdAttMainId = request.getParameter("fdAttMainId");
		if (fdAttMainId == null || fdAttMainId.isEmpty()) {
			return null;
		}
		SysAttMain sysAttMain = (SysAttMain) this.findByPrimaryKey(fdAttMainId);
		if (sysAttMain == null) {
			return null;
		}
		String fdMode = request.getParameter("fdMode");
		String fdKey = sysAttMain.getFdKey();
		String fdModelId = sysAttMain.getFdModelId();
		String fdModelName = sysAttMain.getFdModelName();
		String filename = sysAttMain.getFdFileName();
		String fileExt = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
		long expires = System.currentTimeMillis() + (4 * 60 * 60 * 1000);// 请求链接4个小时有效
		String wpsUrl = SysAttWpsWebOfficeUtil.getUrl() + "/office";
		if (WpsUtil.isExcel(fileExt)) {
			wpsUrl += "/s/";
		} else if (WpsUtil.isPpt(fileExt)) {
			wpsUrl += "/p/";
		} else if (WpsUtil.isPdf(fileExt)) {
			wpsUrl += "/f/";
		} else {
			wpsUrl += "/w/";
		}
		wpsUrl += fdAttMainId + "?";
		String params = "_w_appid=" + SysAttWpsWebOfficeUtil.getAppid() + "&_w_tokentype=1&_w_fdMode=" + fdMode
				+ "&_w_fileId=" + fdAttMainId + "&_w_fdKey=" + fdKey;
		if(StringUtil.isNotNull(fdModelId)){
			params += "&_w_fdModelId=" + fdModelId ;
		}
		if(StringUtil.isNotNull(fdModelName)){
			params += "&_w_fdModelName=" + fdModelName ;
		}
		net.sf.json.JSONObject watermarkCfg = SysAttViewerUtil.getWaterMarkConfigInDB(true);
		if (watermarkCfg.get("markWordVar") != null) {
			String markWord = SysAttViewerUtil.getVarMarkWord(watermarkCfg.getString("markWordVar"), request);
			boolean isAvailable = UserUtil.getKMSSUser().getUserAuthInfo()
					.getAuthRoleAliases().contains("ROLE_SYSATTACHMENT_REMOVE_WATERMARK");
			if(UserUtil.getKMSSUser().isAdmin()) {
				isAvailable = false;
			}
			if(!isAvailable) {
				if (StringUtil.isNotNull(markWord)) {
					params += "&_w_markWord=" + markWord;
				}
			}
		}
		params += "&_w_userid=" + UserUtil.getUser().getFdId() + "&_w_username=" + UserUtil.getUser().getFdName()
				+ "&_w_Expires=" + expires;

		String signature = WpsUtil.getSignature(WpsUtil.paramToMap(params), SysAttWpsWebOfficeUtil.getAppkey());

		wpsUrl += params + "&_w_signature=" + signature;
		json.put("wpsUrl", wpsUrl);
		String token = getRestSign(fdAttMainId,expires);
		json.put("token", token);
		return json;
	}

	/**
	 * 为下载链接签名
	 *
	 * @param method
	 * @param expires
	 * @param attMainId
	 * @return
	 * @throws Exception
	 */
	private String getRestSign(String attMainId, long expires) throws Exception {
		String signStr = expires + ":" + attMainId;
		ISysRestserviceServerMainService sysRestMainService = (ISysRestserviceServerMainService) SpringBeanUtil
				.getBean("sysRestserviceServerMainService");
		SysRestserviceServerMain sysRestserviceServerMain = sysRestMainService
				.findByServiceBean("sysAttachmentRestService");
		if(sysRestserviceServerMain == null){
			return "";
		}
		List<SysRestserviceServerPolicy> webPolicys = sysRestserviceServerMain.getFdPolicy();
		if (ArrayUtil.isEmpty(webPolicys)) {
			return "";
		}
		SysRestserviceServerPolicy webPolicy = webPolicys.get(0);
		String sign = SignUtil.getHMAC(signStr + ":" + webPolicy.getFdLoginId(),
				StringUtil.isNotNull(webPolicy.getFdPassword()) ? webPolicy.getFdPassword() : webPolicy.getFdId());
		return sign;
	}

	@Override
	public String getRestDownloadUrl(String fdId) throws Exception {
		return getRestDownload(fdId,null);
	}

	@Override
	public String getRestDownloadUrl(String fdId, String modelName) throws Exception {
		return getRestDownload(fdId, modelName);
	}

	private String getRestDownload(String fdId,String modelName) {
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("fdId=:fdId");
			hqlInfo.setParameter("fdId", fdId);
			Object obj = this.findFirstOne(hqlInfo);

			SysAttMain attMain = (SysAttMain)obj;
			SysAttFile file = sysAttUploadService.getFileById(attMain.getFdFileId());
			ISysFileLocationDirectService directService = SysFileLocationUtil.getDirectService(file.getFdAttLocation());
			boolean isSupportDirect = directService.isSupportDirect("");
			if (isSupportDirect) {// 支持直连时直接返回
				return directService.getDownloadUrl(file.getFdFilePath(), attMain.getFdFileName());
			}
			long expires = System.currentTimeMillis() + (3 * 60 * 1000);// 下载链接3分钟有效
			// 不支持直连时（代理）走统一的系统附件下载url，因为对外时可能未登录，要添加签名验证
			String url = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
			String sign = getRestSign(fdId, expires);
			if(StringUtil.isNotNull(modelName)){
				url = url.toLowerCase() + "/sys/attachment/sys_att_main/downloadFile.jsp?fdId=" + fdId + "&Expires="
						+ expires + "&Signature=" + sign + "&reqType=rest&wpsExtAppModel="+modelName + "&filename=" + URLEncoder.encode(attMain.getFdFileName(), "utf-8");
			}else{
				url = url.toLowerCase() + "/sys/attachment/sys_att_main/downloadFile.jsp?fdId=" + fdId + "&Expires="
						+ expires + "&Signature=" + sign + "&reqType=rest&filename=" + URLEncoder.encode(attMain.getFdFileName(), "utf-8");
			}
			return url;
		} catch (Exception e) {
			logger.error("",e.getCause());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<SysAttMain> cloneDocAtts(JSONArray attsInfo) throws Exception {
		// TODO Auto-generated method stub
		List<SysAttMain> atts = new ArrayList();

		for(int i=0; i<attsInfo.size(); i++){
			net.sf.json.JSONObject obj = attsInfo.getJSONObject(i);
			String exsitAttIds = obj.getString("exsitAttIds"); //已经创建过的临时附件id
			String newKey = obj.getString("newKey"); //上传文档附件列表key
			String fdModelId = obj.getString("fdModelId"); //选择文档的id
			String fdModelName = obj.getString("fdModelName"); //选择文档的classname
			String oldKey = obj.getString("oldKey"); //选择文档的附件列表key

			List<SysAttMain> exsitList = new ArrayList(); //已经存在的list
			Map<String,SysAttMain> exsitAtt = new HashMap();

			if(StringUtil.isNotNull(exsitAttIds)) {
				String[] ids = exsitAttIds.split("[;；]");
				exsitList = getSysAttMainDao().findByPrimaryKeys(ids);
				for(SysAttMain att :exsitList) {
					exsitAtt.put(att.getFdFileId(), att);
				}
			}

			List<SysAttMain> tempList = getSysAttMainDao().findByModelKey(fdModelName, fdModelId, oldKey);
			for(SysAttMain att : tempList) {
				if(exsitList.size()>0 && exsitAtt.containsKey(att.getFdFileId())) {
					atts.add(exsitAtt.get(att.getFdFileId()));
				}else {
					SysAttMain copyAtt = (SysAttMain) att.clone();
					copyAtt.setFdId(IDGenerator.generateID());
					copyAtt.setFdCreatorId(UserUtil.getUser().getFdId());
					copyAtt.setDocCreateTime(new Date());
					copyAtt.setFdModelId("");
					copyAtt.setFdModelName("");
					copyAtt.setDownloadSum(0);
					copyAtt.setFdBorrowCount(new Long(0));
					copyAtt.setFdOrder(null);
					copyAtt.setFdLastOpenTime(null);
					copyAtt.setFdKey(newKey);

					getSysAttMainDao().add(copyAtt);
					atts.add(copyAtt);
				}
			}
		}

		return atts;
	}

	@Override
	public String downloadPluginXml(String realPath) throws Exception {
		// TODO Auto-generated method stub
		String xmlFile = realPath
				+ "/sys/attachment/sys_att_main/wps/oaassist/xml/jsplugins.xml";
		String xml = FileUtil.getFileString(xmlFile, "UTF-8");
		xml = xml.replaceAll(
				"\\$\\{sys.attmain.wps.oaassit.hostPortAndAppName\\}",
				ResourceUtil.getKmssConfigString("kmss.urlPrefix"));
		return xml;
	}

	@Override
	public SysAttMain addWpsOaassistOnlineFile(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		SysAttMain copyAtt = new SysAttMain();
		String fdTemplateModelId = request.getParameter("fdTemplateModelId");
		String fdTemplateModelName = request
				.getParameter("fdTemplateModelName");
		String fdTemplateModelKey = request.getParameter("fdTemplateKey");
		String fdKey = request.getParameter("fdKey");
		String fdModelName = request.getParameter("fdModelName");
		String fdModelId = request.getParameter("fdModelId");

		return addWpsOaassistOnlineFile(fdTemplateModelId, fdTemplateModelName, fdTemplateModelKey, fdKey, fdModelName, fdModelId);
	}

	@Override
	public SysAttMain addWpsOaassistOnlineFile(String fdTemplateModelId,String fdTemplateModelName,String fdTemplateModelKey,String fdKey,String fdModelName,String fdModelId)
			throws Exception {
		// TODO Auto-generated method stub
		SysAttMain copyAtt = new SysAttMain();
		Object attObj = this.findFirstOne(
				"sysAttMain.fdKey = '" + fdKey
						+ "' and sysAttMain.fdModelId = '"
						+ fdModelId
						+ "' and sysAttMain.fdModelName='"
						+ fdModelName + "'",
				"");
		if (attObj == null) {
			InputStream in = null;
			if (StringUtil.isNotNull(fdTemplateModelId)
					&& StringUtil.isNotNull(fdTemplateModelName)
					&& StringUtil.isNotNull(fdTemplateModelKey)) {
				Object obj = this.findFirstOne(
						"sysAttMain.fdKey = '" + fdTemplateModelKey
								+ "' and sysAttMain.fdModelId = '"
								+ fdTemplateModelId
								+ "' and sysAttMain.fdModelName='"
								+ fdTemplateModelName + "'",
						"");
				if (obj!=null) {
					SysAttMain tempAtt = (SysAttMain)obj;
					in = this.getInputStream(tempAtt);
				}
			}
			if (in == null) {
				String templateFilePath = "/sys/attachment/templateFile/editOnlineTemplate.docx";
				File file = new File(ConfigLocationsUtil.getWebContentPath()
						+ templateFilePath);
				if (file.exists()) {
					in = new FileInputStream(file);
				}
			}

			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			byte[] buff = new byte[100];
			int rc = 0;
			while ((rc = in.read(buff, 0, 100)) > 0) {
				bao.write(buff, 0, rc);
			}

			String appendStr = copyAtt.getFdId();
			byte[] appendByte = appendStr.getBytes();
			bao.write(appendByte);
			byte[] byt = bao.toByteArray();
			InputStream newin = new ByteArrayInputStream(byt);
			copyAtt.setInputStream(newin);
			copyAtt.setFdModelId(fdModelId);
			copyAtt.setFdModelName(fdModelName);
			copyAtt.setFdKey(fdKey);
			double fileSize = newin.available();
			copyAtt.setFdSize(fileSize);
			copyAtt.setFdAttType("office");
			copyAtt.setFdFileName("editonline.docx");
			copyAtt.setFdContentType(
					"application/vnd.openxmlformats-officedocument.wordprocessingml.document");

			ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
					.getBean("sysAttMainService");
			try {
				sysAttMainService.add(copyAtt);
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				// TODO: handle finally clause
				if (in != null) {
					in.close();
				}
				if (newin != null) {
					newin.close();
				}
			}

		} else {
			copyAtt = (SysAttMain) attObj;
		}
		return copyAtt;
	}


	@Override
	public SysAttMain addWpsOaassistOnlineFile(SysAttMain sam)throws Exception {

		String fdTemplateModelId = sam.getFdModelId();
		String fdTemplateModelName = sam.getFdModelName();
		String fdTemplateModelKey = sam.getFdKey();
		String fdKey = sam.getFdKey();
		String fdModelName = sam.getFdModelName();
		String fdModelId = sam.getFdModelId();
		String fdFileName = sam.getFdFileName();

		SysAttMain copyAtt = new SysAttMain();
		Object attObj = this.findFirstOne(
				"sysAttMain.fdKey = '" + fdKey
						+ "' and sysAttMain.fdModelId = '"
						+ fdModelId
						+ "' and sysAttMain.fdModelName='"
						+ fdModelName + "'",
				"");
		if (attObj == null) {
			InputStream in = null;
			if (StringUtil.isNotNull(fdTemplateModelId)
					&& StringUtil.isNotNull(fdTemplateModelName)
					&& StringUtil.isNotNull(fdTemplateModelKey)) {
				Object obj = this.findFirstOne(
						"sysAttMain.fdKey = '" + fdTemplateModelKey
								+ "' and sysAttMain.fdModelId = '"
								+ fdTemplateModelId
								+ "' and sysAttMain.fdModelName='"
								+ fdTemplateModelName + "'",
						"");
				if (obj!=null) {
					SysAttMain tempAtt = (SysAttMain) obj;
					in = this.getInputStream(tempAtt);
				}
			}
			if (in == null) {
				String templateFilePath = "/sys/attachment/templateFile/editOnlineTemplate.docx";
				File file = new File(ConfigLocationsUtil.getWebContentPath()
						+ templateFilePath);
				if (file.exists()) {
					in = new FileInputStream(file);
				}
			}

			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			byte[] buff = new byte[100];
			int rc = 0;
			while ((rc = in.read(buff, 0, 100)) > 0) {
				bao.write(buff, 0, rc);
			}

			String appendStr = copyAtt.getFdId();
			byte[] appendByte = appendStr.getBytes();
			bao.write(appendByte);
			byte[] byt = bao.toByteArray();
			InputStream newin = new ByteArrayInputStream(byt);
			copyAtt.setInputStream(newin);
			copyAtt.setFdModelId(fdModelId);
			copyAtt.setFdModelName(fdModelName);
			copyAtt.setFdKey(fdKey);
			double fileSize = newin.available();
			copyAtt.setFdSize(fileSize);
			copyAtt.setFdAttType("office");
			copyAtt.setFdFileName(fdFileName + ".docx");
			copyAtt.setFdContentType(
					"application/vnd.openxmlformats-officedocument.wordprocessingml.document");

			ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
					.getBean("sysAttMainService");
			try {
				sysAttMainService.add(copyAtt);
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				// TODO: handle finally clause
				if (in != null) {
					in.close();
				}
				if (newin != null) {
					newin.close();
				}
			}

		} else {
			copyAtt = (SysAttMain) attObj;
		}
		return copyAtt;
	}

	/**
	 * 创建附件：如果附件已经存在(可能是模板)则直接复制附件；否则创建新的附件
	 *
	 * @param sam SysAttMain 原对象，用于查询对象是否在数据库中
	 * @param modelId 需要新对象的属性
	 * @param modelName  需要新对象的属性
	 */
	@Override
	public SysAttMain setWpsOnlineFile(SysAttMain sam, String modelId,String modelName)throws Exception {

		String fdKey = sam.getFdKey();
		String fdModelName = sam.getFdModelName();
		String fdModelId = sam.getFdModelId();
		String fdFileName = sam.getFdFileName();
		InputStream in = null;
		InputStream newin =  null;

		SysAttMain copyAtt = new SysAttMain();
		SysAttMain sysAttMain = null;
		Object obj = this.findFirstOne(
				"sysAttMain.fdKey = '" + fdKey
						+ "' and sysAttMain.fdModelId = '"
						+ fdModelId
						+ "' and sysAttMain.fdModelName='"
						+ fdModelName + "'",
				"");
		try {

			if (obj!=null) {
				sysAttMain = (SysAttMain) obj;
				in = this.getInputStream(sysAttMain);
			}
			else
			{
				String templateFilePath = "/sys/attachment/templateFile/editOnlineTemplate.docx";
				File file = new File(ConfigLocationsUtil.getWebContentPath()
						+ templateFilePath);
				if (file.exists()) {
					in = new FileInputStream(file);
				}
			}

			if (in == null) {
				String templateFilePath = "/sys/attachment/templateFile/editOnlineTemplate.docx";
				File file = new File(ConfigLocationsUtil.getWebContentPath()
						+ templateFilePath);

				if (file.exists()) {
					in = new FileInputStream(file);
				}
			}
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			byte[] buff = new byte[100];
			int rc = 0;
			while ((rc = in.read(buff, 0, 100)) > 0) {
				bao.write(buff, 0, rc);
			}

			String appendStr = copyAtt.getFdId();
			byte[] appendByte = appendStr.getBytes();
			bao.write(appendByte);
			byte[] byt = bao.toByteArray();
			newin = new ByteArrayInputStream(byt);
			copyAtt.setInputStream(newin);
			copyAtt.setFdModelId(modelId);
			copyAtt.setFdModelName(modelName);
			copyAtt.setFdCreatorId(UserUtil.getUser().getFdId());
			copyAtt.setDocCreateTime(new Date());
			copyAtt.setFdKey(fdKey);
			double fileSize = newin.available();
			copyAtt.setFdSize(fileSize);
			copyAtt.setFdAttType("office");
			if (sysAttMain != null) {
				copyAtt.setFdFileName(sysAttMain.getFdFileName());
			} else {
				copyAtt.setFdFileName(fdFileName + ".docx");
			}

			copyAtt.setFdContentType(
					"application/vnd.openxmlformats-officedocument.wordprocessingml.document");

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (in != null) {
				in.close();
			}
			if (newin != null) {
				newin.close();
			}
		}

		return copyAtt;
	}

	@Override
	public boolean updateDelRelation(String fdAttMainId) throws Exception {
		boolean flag = true;
		try {
			SysAttMain sysAttMain = (SysAttMain) this.findByPrimaryKey(fdAttMainId);
			if(!sysAttMain.getFdCreatorId().equals(UserUtil.getKMSSUser().getUserId())) {
                return false;
            }
			sysAttMain.setFdModelId("");
			sysAttMain.setFdModelName("");
			getSysAttMainDao().update(sysAttMain);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public SysAttMain addWpsCenterOnlineFile(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
            logger.debug("复制附件处理：不存在附件流的情况。");
        }
		SysAttMain copyAtt = new SysAttMain();
		String fdTemplateModelId = request.getParameter("fdTemplateModelId");
		String fdTemplateModelName = request
				.getParameter("fdTemplateModelName");
		String fdTemplateModelKey = request.getParameter("fdTemplateKey");
		String fdKey = request.getParameter("fdKey");
		String fdModelName = request.getParameter("fdModelName");
		String fdModelId = request.getParameter("fdModelId");
		String fdFileExt = request.getParameter("fdFileExt");
		String isPrint = request.getParameter("isPrint");

		Object attObj = this.findFirstOne(
				"sysAttMain.fdKey = '" + fdKey
						+ "' and sysAttMain.fdModelId = '"
						+ fdModelId
						+ "' and sysAttMain.fdModelName='"
						+ fdModelName + "'",
				"");
		if (attObj == null) {
			InputStream in = null;
			if (StringUtil.isNotNull(fdTemplateModelId)
					&& StringUtil.isNotNull(fdTemplateModelName)
					&& StringUtil.isNotNull(fdTemplateModelKey)) {
				Object obj = this.findFirstOne(
						"sysAttMain.fdKey = '" + fdTemplateModelKey
								+ "' and sysAttMain.fdModelId = '"
								+ fdTemplateModelId
								+ "' and sysAttMain.fdModelName='"
								+ fdTemplateModelName + "'",
						"");
				if (obj!=null) {
					SysAttMain tempAtt = (SysAttMain) obj;
					in = this.getInputStream(tempAtt);
				}
			}
			if (in == null) {
				String templateFilePath = "/sys/attachment/templateFile/";
				switch (fdFileExt){
					case "docx":
						templateFilePath+="editOnlineTemplate.docx";
						break;
					case "xls":
						templateFilePath+="editOnlineTemplate.xls";
						break;
					case "xlsx":
						templateFilePath+="editOnlineTemplate.xlsx";
						break;
					case "ppt":
						templateFilePath+="editOnlineTemplate.ppt";
						break;
					case "pptx":
						templateFilePath+="editOnlineTemplate.pptx";;
						break;
					default:
						templateFilePath+="editOnlineTemplate.doc";
						break;
				}
				if ("true".equals(isPrint)) {
					templateFilePath = "/sys/attachment/templateFile/editOnlinePrint.docx";
				}
				File file = new File(ConfigLocationsUtil.getWebContentPath()
						+ templateFilePath);
				if (file.exists()) {
					in = new FileInputStream(file);
				}
			}

			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			byte[] buff = new byte[100];
			int rc = 0;
			while ((rc = in.read(buff, 0, 100)) > 0) {
				bao.write(buff, 0, rc);
			}

			String appendStr = copyAtt.getFdId();
			byte[] appendByte = appendStr.getBytes();
			bao.write(appendByte);
			byte[] byt = bao.toByteArray();
			InputStream newin = new ByteArrayInputStream(byt);
			copyAtt.setInputStream(newin);
			copyAtt.setFdModelId(fdModelId);
			copyAtt.setFdModelName(fdModelName);
			copyAtt.setFdKey(fdKey);
			double fileSize = newin.available();
			copyAtt.setFdSize(fileSize);
			copyAtt.setFdAttType("office");
			if(StringUtil.isNull(fdFileExt)){
				fdFileExt="";
			}
			switch (fdFileExt){
				case "docx":
					copyAtt.setFdFileName("editonline.docx");
					copyAtt.setFdContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
					break;
				case "xls":
					copyAtt.setFdFileName("editonline.xls");
					copyAtt.setFdContentType("application/vnd.ms-excel");
					break;
				case "xlsx":
					copyAtt.setFdFileName("editonline.xlsx");
					copyAtt.setFdContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
					break;
				case "ppt":
					copyAtt.setFdFileName("editonline.ppt");
					copyAtt.setFdContentType("application/vnd.ms-powerpoint");
					break;
				case "pptx":
					copyAtt.setFdFileName("editonline.pptx");
					copyAtt.setFdContentType("application/vnd.openxmlformats-officedocument.presentationml.presentation");
					break;
				default:
					copyAtt.setFdFileName("editonline.doc");
					copyAtt.setFdContentType("application/msword");
					break;
			}
			if ("true".equals(isPrint)) {
				copyAtt.setFdFileName("editOnlinePrint.docx");
			}

			ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
					.getBean("sysAttMainService");
			try {
				sysAttMainService.add(copyAtt);
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				// TODO: handle finally clause
				if (in != null) {
					in.close();
				}
				if (newin != null) {
					newin.close();
				}
			}

		} else {
			copyAtt = (SysAttMain) attObj;
		}
		return copyAtt;
	}

	/**
	 * 读取附件流
	 * 所有业务代码应调用此方法读取附件，而不应使用java.io.File来读取文件流
	 *
	 * @param sysAttFileId
	 * @return 文件流
	 * @throws Exception
	 */
	@Override
	public InputStream readAttachment(String sysAttFileId) throws Exception {
		SysAttFile sysAttFile = sysAttUploadService.getFileById(sysAttFileId);
		return readAttachment(sysAttFile);
	}

	/**
	 * 读取附件流
	 * 所有业务代码应调用此方法读取附件，而不应使用java.io.File来读取文件流
	 *
	 * @param sysAttFile
	 * @return 文件流
	 * @throws Exception
	 */
	@Override
	public InputStream readAttachment(SysAttFile sysAttFile) throws Exception {
		String pathPrefix = sysAttFile.getFdCata() == null ? null : sysAttFile.getFdCata().getFdPath();

		//读文件获取proxy时要传AttLocation
		InputStream inputStream = SysFileLocationUtil.getProxyService(sysAttFile.getFdAttLocation())
				.readFile(sysAttFile.getFdFilePath(),pathPrefix);
		return inputStream;
	}

	/**
	 * 附件流
	 * 所有业务代码应调用此方法读取附件，而不应使用java.io.File来读取文件流
	 *
	 * @param inputStream         文件流
	 * @param sysAttFile
	 * @throws Exception
	 */
	@Override
	public void writeAttachment(InputStream inputStream, SysAttFile sysAttFile) throws Exception {
		writeAttachment(inputStream,sysAttFile.getFdFilePath());
	}

	/**
	 * @param inputStream       文件流
	 * @param filePath
	 * @throws Exception
	 */
	@Override
	public void writeAttachment(InputStream inputStream, String filePath) throws Exception {
		SysFileLocationUtil.getProxyService().writeFile(inputStream,filePath);
	}

	@Override
	public void updateByTmpAttmainId(String editonlineFdId, String editonlineTmpFdId) throws Exception {
		if(StringUtil.isNotNull(editonlineFdId)&&StringUtil.isNotNull(editonlineTmpFdId)){
			SysAttMain sysAttMain= (SysAttMain) findByPrimaryKey(editonlineFdId);
			sysAttMain.setInputStream(getInputStream(editonlineTmpFdId));
			updateByUser(sysAttMain,UserUtil.getUser().getFdId());
		}
	}
}
