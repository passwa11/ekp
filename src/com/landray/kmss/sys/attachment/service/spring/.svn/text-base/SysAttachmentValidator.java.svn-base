package com.landray.kmss.sys.attachment.service.spring;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

import com.landray.kmss.sys.attachment.borrow.service.ISysAttBorrowBusinessModuleValidator;
import com.landray.kmss.sys.profile.util.ProfileMenuUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.landray.kmss.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.framework.util.PluginConfigLocationsUtil;
import com.landray.kmss.sys.attachment.borrow.service.spring.SysAttBorrowValidatorFactory;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.model.SysAttRtfData;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidateCore;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.right.interfaces.ExtendAuthModel;
import com.landray.kmss.sys.right.interfaces.ExtendAuthTmpModel;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import edu.emory.mathcs.backport.java.util.Arrays;

public class SysAttachmentValidator implements IAuthenticationValidator {
	private ISysAttMainCoreInnerService sysAttMainService;

	private Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttachmentValidator.class);

	@Override
	public boolean validate(ValidatorRequestContext validatorContext)
			throws Exception {
		String method = validatorContext.getParameter("method");
		if ("editDownload".equals(method) || "handleAttUpload".equals(method)
				|| "handleAttToken".equals(method)
				|| "addFile".equals(method)
				|| "getWpsCloudViewUrl".equals(method)  || "findAttMains".equals(method)
				|| "getWpsoaassistConfig".equals(method) ||
				"getAttMainViewUrl".equals(method)) {
			return true;
		}
		String fdId = validatorContext.getParameter("fdId");
		String fdType = validatorContext.getParameter("fdType");
		String filethumb = validatorContext.getParameter("filethumb");
		if ("save".equals(method)) {
			String fdKey = validatorContext.getParameter("fdKey");
			if (StringUtil.isNotNull(fdKey) && fdKey.startsWith("FlowAtt_")) {
                method = "view";
            }
		}
		if("downloadToOther".equals(method)) {
			method = "download";
		}
		String fdModelId = validatorContext.getParameter("fdModelId");
		String fdModelName = validatorContext.getParameter("fdModelName");
		String url = null;
		IBaseModel mainModel = null;
		String authType = null;
		String attType = null;
        String contentType = null;// attType不准，attType为byte的时候也可能是图片、pdf等，此时判断fdContentType
        String fileName = null;
		SysAttMain attMain = null;

		if (logger.isDebugEnabled()) {
			logger.debug("开始校验附件权限,requesturl="
					+ validatorContext.getRequestUrl());
		}
		
		//判断是否为富文本为视频
		if(fdType != null && "rtf".equals(fdType)) {
			SysAttRtfData sysAttRtfData = (SysAttRtfData) sysAttMainService.findByPrimaryKey(fdId, SysAttRtfData.class, true);
			if(sysAttRtfData != null) {
				mainModel = sysAttMainService.findByPrimaryKey(sysAttRtfData.getFdModelId(), sysAttRtfData.getFdModelName(), true);
				//请求富文本框视频封面预览 权限放过
				if("view".equals(method) && "yes".equals(filethumb)) {
					return true;
				}
			}
		}

		if (StringUtil.isNotNull(fdId)) {
			if (fdId.indexOf(";") > -1) {
                fdId = fdId.substring(0, fdId.indexOf(";"));
            }
			if (logger.isDebugEnabled()) {
				logger.debug("开始校验附件权限,fdId=" + fdId);
			}
			attMain = (SysAttMain) sysAttMainService
					.findByPrimaryKey(fdId, SysAttMain.class, true);
			if (attMain != null) {
				IExtension extension = Plugin.getExtension(
						"com.landray.kmss.sys.attachment.validator",
						attMain.getFdModelName(), "validator");
				if (extension != null) {
					IAuthenticationValidator validator = Plugin
							.getParamValue(extension, "bean");
					return validator.validate(validatorContext);
				}
				// mobile_create为移动端在线编辑特有的fdModelId，
				if (StringUtil.isNotNull(attMain.getFdModelId()) && "mobile_online_create".equals(attMain.getFdModelId())) {
					// 附件创建者为当前用户，权限放过
					if (StringUtil.isNotNull(attMain.getFdCreatorId())
							&& UserUtil.getUser().getFdId().equals(attMain.getFdCreatorId())) {
						return true;
					}
				}
				// 新建文档是，用wps在线编辑，临时生成的文件，fdTempKey不为空
				if (StringUtil.isNotNull(attMain.getFdTempKey()) && attMain.getFdTempKey().endsWith("online_create")) {
					// 附件创建者为当前用户，权限放过
					if (StringUtil.isNotNull(attMain.getFdCreatorId())
							&& UserUtil.getUser().getFdId().equals(attMain.getFdCreatorId())) {
						return true;
					}
				}

				// 新建文档是，用wps在线编辑，临时生成的文件，fdTempKey不为空
				if (StringUtil.isNotNull(attMain.getFdTempKey()) && attMain.getFdTempKey().endsWith("online_create")) {
					// 附件创建者为当前用户，权限放过
					if (StringUtil.isNotNull(attMain.getFdCreatorId())
							&& UserUtil.getUser().getFdId().equals(attMain.getFdCreatorId())) {
						return true;
					}
				}
				
				//范本起草新建文档且引用条款
				if (("com.landray.kmss.km.agreement.model.KmAgreementModel".equals(attMain.getFdModelName())
						|| "com.landray.kmss.km.sample.model.KmSampleModel".equals(attMain.getFdModelName()))
						&& "mainOnline".equals(attMain.getFdKey())
						&& ProfileMenuUtil.moduleExist("/km/clause")) {
					// 附件创建者为当前用户，权限放过
					if (StringUtil.isNotNull(attMain.getFdCreatorId())
							&& UserUtil.getUser().getFdId().equals(attMain.getFdCreatorId())) {
						return true;
					}
				}
				
				attType = attMain.getFdAttType();
				if (StringUtil.isNotNull(attMain.getFdModelId())
						&& StringUtil.isNotNull(attMain.getFdModelName())) {
					mainModel = sysAttMainService.findByPrimaryKey(attMain
							.getFdModelId(), attMain.getFdModelName(), true);
				}

                contentType = attMain.getFdContentType();
                fileName = attMain.getFdFileName();
			}
		}
		if (mainModel == null && StringUtil.isNotNull(fdModelId)
				&& StringUtil.isNotNull(fdModelName)) {
			if (logger.isDebugEnabled()) {
				logger.debug("开始校验附件权限,fdModelId=" + fdModelId
						+ ",fdModelName=" + fdModelName);
			}
			mainModel = sysAttMainService.findByPrimaryKey(fdModelId,
					fdModelName, true); // 2009-08-05 fyx 保证确实存在
		}
		if (mainModel != null) {
			url = ModelUtil.getModelUrl(mainModel);
		}
		// 上传附件校验
		if ("save".equals(method) || "update".equals(method)) {
			if (mainModel == null) {
                return true;
            }
			// 2008年10月28日修改，附件校验在View页面无法上传
			// authType = "edit";
		} else if ("edit".equals(method)) {
			if (mainModel == null) {
                return false;
            }
			authType = "edit";
		} else if ("download".equals(method) || "downloadPdf".equals(method)
            || "readDownload".equals(method)) { // 公文正文转为pdf，正文pdf下载为downloadPdf
			if (mainModel == null) {
				// 图片上传进行预览时mainModel为空
				if (StringUtil.isNotNull(attType) && "pic".equals(attType)) {
					return true;
				}

				if (attMain != null) {
					// 附件创建者为当前用户，权限放过
					if (StringUtil.isNotNull(attMain.getFdCreatorId())
							&& UserUtil.getUser().getFdId().equals(attMain.getFdCreatorId())) {
						return true;
					}
				}

				return false;
			}

			// #111498 readDownload的情况，文本、图片、pdf、ofd、html直接使用查看权限
            // 其他情况需要走下载权限校验
            if("readDownload".equals(method) && isReadDownload(attType, contentType, fileName)){
                method = "view";
                authType = "view";
            }else{
                // 更改图片的下载动作的校验方式，从原来的只限于附件可下载者扩展到包括文档可编辑者也可下载
                boolean auth = checkModelAuth(mainModel, "authAttNodownload",
                        "authAttDownloads",attType);
                if (!auth) {
                    String downLoadUrl = StringUtil.replace(url, "method=view",
                            "method=edit");
                    downLoadUrl = processModelingUrl(mainModel, downLoadUrl);
                    auth = UserUtil.checkAuthentication(downLoadUrl, "GET");
                }
                // 借阅权限
                if (!auth) {
                    auth = SysAttBorrowValidatorFactory.auth(method, fdId);
                }

                // 知识借阅权限
                if (!auth) {
                    attMain = (SysAttMain) sysAttMainService
                            .findByPrimaryKey(fdId);
					auth = checkBusinessModuleBorrowAuth(validatorContext, attMain);
//                    auth = checkKmsKnowledgeBorrowAuth(method,
//                            attMain.getFdModelId());

                }

                return auth;
            }
		} else if ("print".equals(method)) {
			if (mainModel == null) {
				return false;
			}
			boolean auth = checkModelAuth(mainModel, "authAttNoprint",
					"authAttPrints", "");
			if (!auth) {
				// 如果没有附件的相关权限，需要判断是否有文档的编辑权限
				String downLoadUrl =
						StringUtil.replace(url, "method=view", "method=edit");
				downLoadUrl = processModelingUrl(mainModel, downLoadUrl);
				auth = UserUtil.checkAuthentication(downLoadUrl, "GET");
			}

			// 借阅权限
			if (!auth) {
				auth = SysAttBorrowValidatorFactory.auth(method, fdId);
			}

			// 知识借阅权限
			if (!auth) {
				attMain = (SysAttMain) sysAttMainService
						.findByPrimaryKey(fdId);
//				auth = checkKmsKnowledgeBorrowAuth(method,
//						attMain.getFdModelId());
				auth = checkBusinessModuleBorrowAuth(validatorContext, attMain);
			}

			return auth;			
		} else if ("copy".equals(method)) {
			if (mainModel == null) {
				return false;
			}
			boolean auth = checkModelAuth(mainModel, "authAttNocopy",
					"authAttCopys", "");
			if (!auth) {
				// 如果没有附件的相关权限，需要判断是否有文档的编辑权限
				String downLoadUrl =
						StringUtil.replace(url, "method=view", "method=edit");
				downLoadUrl = processModelingUrl(mainModel, downLoadUrl);
				auth = UserUtil.checkAuthentication(downLoadUrl, "GET");
			}
			// 借阅权限
			if (!auth) {
				auth = SysAttBorrowValidatorFactory.auth(method, fdId);
			}

			// 知识借阅权限
			if (!auth) {
				attMain = (SysAttMain) sysAttMainService
						.findByPrimaryKey(fdId);
//				auth = checkKmsKnowledgeBorrowAuth(method,
//						attMain.getFdModelId());
				auth = checkBusinessModuleBorrowAuth(validatorContext, attMain);
			}

			return auth;
			
		} else if ("viewDownload".equals(method)) {
			method = "view";
			authType = "view";
		} else if ("delete".equals(method) || "deleteall".equals(method)) {
			if (mainModel == null) {
                return true;
            }
			authType = "edit";
		}
		if (mainModel == null) {
			// add 附件上传后支持预览 
			if (StringUtil.isNull(fdId)) {
				return true;
			}
			attMain = (SysAttMain) sysAttMainService.findByPrimaryKey(fdId);
			if (UserUtil.getUser().getFdId().equals(attMain.getFdCreatorId())) {
				return true;
			}
			return false;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("开始校验附件的主model的URL权限,url=" + url);
		}
		if (authType != null) {
            url = StringUtil.replace(url, "method=view", "method=" + authType);
        }
		if (logger.isDebugEnabled()) {
			logger.debug("开始校验附件的主model的URL权限,url=" + url);
		}
		if (url == null) {
			logger.warn(ModelUtil.getModelClassName(mainModel)
					+ "对应的数据字典没有配置URL，附件权限校验失败！");
			return false;
		}
		if ("edit".equals(authType)) {
			IAuthenticationValidateCore authenticationValidateCore = (IAuthenticationValidateCore) SpringBeanUtil
					.getBean("authenticationValidateCore");
			// v=true 说明附件view页面中包含主文档编辑权限（这类必须排除当前审批者，否则当前审批人审批页面也会出现可编辑附件按钮）
			// 仅有主文档编辑权限才可以编辑附件
			if ("true".equals(validatorContext.getParameter("v"))) {
				url = processModelingUrl(mainModel, url);
				return UserUtil.checkAuthentication(url, "GET");
			} else {
				// 增加当前处理人可以在线编辑附件的权利，权限区段中
				return UserUtil.checkAuthentication(url, "GET")
						|| authenticationValidateCore.excuteValidator(
								validatorContext,
								"lbpmCurHandlerValidator(method=edit)");
			}

		}
		
		// 标准权限
		Boolean auth = UserUtil.checkAuthentication(url, "GET");
		
		// 借阅权限
		if (!auth) {
			auth = SysAttBorrowValidatorFactory.auth(method, fdId);
		}

        // 知识借阅权限
        if (!auth) {
			attMain = (SysAttMain) sysAttMainService
					.findByPrimaryKey(fdId);
//			auth = checkKmsKnowledgeBorrowAuth(method,
//					attMain.getFdModelId());
			auth = checkBusinessModuleBorrowAuth(validatorContext, attMain);
        }

		return auth;
	}

    /**
     * 检测是否是阅读状态，比如图片、pdf、txt、ofd、html
     */
    private boolean isReadDownload(String attType, String contentType, String fileName) {
        boolean flag = (contentType.contains("image") || "pic".equals(attType)
                || contentType.contains("text") || "text".equals(attType)
                || contentType.contains("pdf") || "pdf".equals(attType)
                || contentType.contains("ofd") || (contentType.contains("octet") && fileName.contains("ofd")) || "ofd".equals(attType)
                || contentType.contains("html") || "html".equals(attType)
        );
        return flag;
    }

	/**
	 * 处理业务建模url
	 * 
	 * @param mainModel
	 * @param downLoadUrl
	 * @return
	 * @throws Exception
	 */
	private String processModelingUrl(IBaseModel mainModel, String downLoadUrl)
			throws Exception {
		String modelClassName = ModelUtil.getModelClassName(mainModel);
		String modelingUrl = downLoadUrl;
		if ("com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain"
				.equals(modelClassName)
				|| "com.landray.kmss.sys.modeling.main.model.ModelingAppSimpleMain"
						.equals(modelClassName)) {
			modelingUrl = StringUtil.linkString(modelingUrl, "&",
					"fdAppModelId=" + PropertyUtils
							.getProperty(mainModel, "fdModel.fdId"));
		}
		return modelingUrl;
	}

    private boolean checkKmsKnowledgeBorrowAuth(String fdMethod, String fdModelId) {
        boolean result = false;
        if (moduleExist("/kms/knowledge")) {
            try {
                String authFactoryName = "com.landray.kmss.kms.knowledge.borrow.service.spring.KmsKnowledgeBorrowValidatorFactory";
                //改为ClassUtils,有缓存，性能更优
                Class clazz = com.landray.kmss.util.ClassUtils.forName(authFactoryName,null);

				Method method = ReflectionUtils.findMethod(clazz, "auth",
						String.class, String.class);
                ReflectionUtils.makeAccessible(method);
                result = (boolean) method.invoke(null, fdMethod, fdModelId);
            } catch (Exception e) {
				logger.error("知识借阅权限出现错误", e);
            }
        }
        return result;
    }

	/**
	 * 判断业务模块借阅权限
	 * @param validatorContext
	 * @param sysAttMain
	 * @return
	 * @throws Exception
	 */
    private boolean checkBusinessModuleBorrowAuth(ValidatorRequestContext validatorContext, SysAttMain sysAttMain) throws Exception{
		IExtension extension = Plugin.getExtension(
				"com.landray.kmss.sys.attachment.borrow.business.module.validator",
				sysAttMain.getFdModelName(), "validator");
		if (extension != null) {
			Object validator = Plugin.getParamValue(extension, "bean");
			if(validator instanceof ISysAttBorrowBusinessModuleValidator){
				try {
					ISysAttBorrowBusinessModuleValidator businessModuleValidator = (ISysAttBorrowBusinessModuleValidator)validator;
					return businessModuleValidator.validate(validatorContext,sysAttMain);
				}catch (Exception e){
					logger.error("业务模块借阅权限出现错误", e);
				}
			}
		}
		return false;
	}

    /**
     * 根据模块路径判断模块是否存在
     *
     * @param path
     * @return
     */
    public static boolean moduleExist(String path) {
        Boolean exist = new File(
                PluginConfigLocationsUtil.getKmssConfigPath() + path).exists();
        return exist;
    }

	private boolean checkModelAuth(IBaseModel baseModel, String signalField,
			String listField,String attType) throws Exception {
		if (baseModel == null) {
            return false;
        }
		if("pic".equals(attType)) {
            return true;
        }
		if (logger.isDebugEnabled()) {
			logger.debug("开始校验附件的主model的开关,signalField=" + signalField);
		}
		Object value = null;
		try {
			value = PropertyUtils.getProperty(baseModel, signalField);
		} catch (NoSuchMethodException e) {
		}
		if (value != null) {
			if (((Boolean) value).booleanValue()) {
				return false;
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("开始校验附件的主model的list,signalField=" + signalField);
		}
		Object values = null;
		try {
			values = PropertyUtils.getProperty(baseModel, listField);
		} catch (NoSuchMethodException e) {
		}
		if (values != null) {
			List list = (List) values;
			if (!list.isEmpty()) {
				return UserUtil.checkUserModels(list);
			} else if(baseModel instanceof ExtendAuthModel) {
				ExtendAuthModel extendAuthModel = (ExtendAuthModel)baseModel;
				SysOrgPerson creator = extendAuthModel.getDocCreator();
				// 外部人员创建（拷贝/下载/打印）为空，则权限为所属组织 
				if (BooleanUtils.isTrue(creator.getFdIsExternal())) {
					SysOrgElement parent = creator.getFdParent();
					if (parent != null) {
						return UserUtil.checkUserModels(Arrays.asList(new SysOrgElement[] {parent}));
					}
				}
			} else if (baseModel instanceof ExtendAuthTmpModel) {
				ExtendAuthTmpModel extendAuthTmpModel = (ExtendAuthTmpModel)baseModel;
				SysOrgPerson creator = extendAuthTmpModel.getDocCreator();
				// 外部人员创建（拷贝/下载/打印）为空，则权限为所属组织 
				if (BooleanUtils.isTrue(creator.getFdIsExternal())) {
					SysOrgElement parent = creator.getFdParent();
					if (parent != null) {
						return UserUtil.checkUserModels(Arrays.asList(new SysOrgElement[] {parent}));
					}
				}
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("为限制权限,获取附件的主model的URL权限");
		}
		if (StringUtil.isNull(ModelUtil.getModelUrl(baseModel))) {
			logger.warn(ModelUtil.getModelClassName(baseModel)
					+ "对应的数据字典没有配置URL，附件权限校验失败！");
			return false;
		}

		String url = ModelUtil.getModelUrl(baseModel);
		
		if("authAttNodownload".equals(signalField)){
			if(baseModel instanceof ExtendAuthModel){
				List downs = ((ExtendAuthModel) baseModel).getAuthAttDownloads();
				if(downs.size()>0) {
                    url = StringUtil.replace(url, "method=view",
                            "method=edit");
                }
			}
		}
		
		if("authAttNocopy".equals(signalField)){
			if(baseModel instanceof ExtendAuthModel){
				List copys = ((ExtendAuthModel) baseModel).getAuthAttCopys();
				if(copys.size()>0) {
                    url = StringUtil.replace(url, "method=view",
                            "method=edit");
                }
			}
		}
		
		// 只有继承自ExtendAuthModel,ExtendAuthTmpModel 才能做内外隔离
		if (baseModel instanceof ExtendAuthModel) {
			ExtendAuthModel model = (ExtendAuthModel)baseModel;
			return model.getDocCreator().getFdIsExternal().equals(UserUtil.getUser().getFdIsExternal())
					&& UserUtil.checkAuthentication(url,
							"GET");
		} else if(baseModel instanceof ExtendAuthTmpModel) {
			ExtendAuthTmpModel model = (ExtendAuthTmpModel)baseModel;
			return model.getDocCreator().getFdIsExternal().equals(UserUtil.getUser().getFdIsExternal())
					&& UserUtil.checkAuthentication(url,
							"GET");
		} else {
			return UserUtil.checkAuthentication(url,
					"GET");
		}
	}

	public void setSysAttMainService(
			ISysAttMainCoreInnerService sysAttMainService) {
		this.sysAttMainService = sysAttMainService;
	}
}
