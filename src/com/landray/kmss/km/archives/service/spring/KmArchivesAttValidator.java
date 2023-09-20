package com.landray.kmss.km.archives.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.km.archives.model.KmArchivesDetails;
import com.landray.kmss.km.archives.model.KmArchivesMain;
import com.landray.kmss.km.archives.service.IKmArchivesMainService;
import com.landray.kmss.km.archives.util.KmArchivesUtil;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.sso.client.util.StringUtil;


/**
 * 档案附件校验器
 * 
 * @author chao
 *
 */
public class KmArchivesAttValidator implements IAuthenticationValidator {

	//归档源来自合同
	public static final String EXTERNAL_MODEL_NAME_KM_AGREEMENT_APPLY = "com.landray.kmss.km.agreement.model.KmAgreementApply";
	
	public static final String EXTERNAL_MODEL_NAME_KM_AGREEMENT_LEDGER = "com.landray.kmss.km.agreement.model.KmAgreementLedger";
	
	//授权合同功能点
	public static final String EXTERNAL_MODEL_NAME_GRANT_KM_AGREEMENT_LEDGER = "com.landray.kmss.km.agreement.model.KmAgreementGrantQuery";
	
	public static final String PARAM_NAME_EXTERNAL_MODELID =  "exteral_model_id";
	
	public static final String PARAM_NAME_EXTERNAL_MODELNAME = "exteral_model_name";
	
	public static final String PARAM_NAME_GRANT_MODELNAME = "grant_model_name";
	
	private IKmArchivesMainService kmArchivesMainService;
	public void setKmArchivesMainService(
			IKmArchivesMainService kmArchivesMainService) {
		this.kmArchivesMainService = kmArchivesMainService;
	}

	private ISysAttMainCoreInnerService sysAttMainService;
	public void setSysAttMainService(
			ISysAttMainCoreInnerService sysAttMainService) {
		this.sysAttMainService = sysAttMainService;
	}

	@Override
	/**
	 * 校验
	 */
	public boolean validate(ValidatorRequestContext validatorContext)
			throws Exception {
		String method = validatorContext.getParameter("method");
		String fdId = validatorContext.getParameter("fdId");
		if (StringUtil.isNotNull(fdId)) {
			if (fdId.indexOf(";") > -1) {
                fdId = fdId.substring(0, fdId.indexOf(";"));
            }
			SysAttMain attMain = (SysAttMain) sysAttMainService
					.findByPrimaryKey(fdId, SysAttMain.class, true);
			if (attMain != null) {
				String modelId = attMain.getFdModelId();
				if (StringUtil.isNotNull(modelId)) {
					KmArchivesMain mainModel = (KmArchivesMain) kmArchivesMainService
							.findByPrimaryKey(modelId);
					// 在附件View页面查看主文档 #63426
					String url = ModelUtil.getModelUrl(mainModel);
					if ("findMainDocInfo".equals(method)) {
						return UserUtil.checkAuthentication(url, "GET");
					}

					// 是否有查看所有文件级权限
					boolean viewAllFILE = UserUtil.checkRole("ROLE_KMARCHIVES_VIEW_ALLFILE");
					if (viewAllFILE) {
						if ("edit".equals(method)) {
							return false;
						} else {
							return true;
						}
					} else {
						// 是否有查看所有文档权限
						boolean viewAll = UserUtil.checkRole("ROLE_KMARCHIVES_VIEW_ALL");

						// 是否是文件级可阅读者
						boolean isFileReader = !ArrayUtil.isEmpty(mainModel.getAuthFileReaders())
								&& UserUtil.checkUserModels(mainModel.getAuthFileReaders());
						// 是否是档案流程的审批人和历史审批人
						boolean isWork = KmArchivesUtil.isWorkUser(modelId);
						// 是否是档案的创建者
						boolean isCreator = mainModel.getDocCreator() != null
								&& mainModel.getDocCreator().getFdId().equals(UserUtil.getUser().getFdId());
						// 是否是档案的保管员
						ISysOrgCoreService sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
								.getBean("sysOrgCoreService");
						boolean isKeeper = false;
						SysOrgElement fdStorekeeper = mainModel.getFdStorekeeper();
						if (fdStorekeeper != null) {
							List orgList = new ArrayList();
							orgList.add(fdStorekeeper);
							List idList = sysOrgCoreService.expandToPersonIds(orgList);
							isKeeper = idList.contains(UserUtil.getUser().getFdId()) ? true : false;
						}
						// 是否是借阅过的档案且为过期
						Date nowDate = new Date();
						KmArchivesDetails details = KmArchivesUtil.getBorrowDetail(modelId);
						boolean isBorrowed = false;
						if (details != null) {
							Date fdRenewReturnDate = details.getFdRenewReturnDate();
							Date fdReturnDate = details.getFdReturnDate();
							if (fdRenewReturnDate != null) {
								if (fdRenewReturnDate.getTime() > nowDate.getTime()) {
									isBorrowed = true;
								}
							} else if (fdReturnDate != null) {
								if (fdReturnDate.getTime() > nowDate.getTime()) {
									isBorrowed = true;
								}
							}
						}
						// 是否能下载
						boolean canDownload = isBorrowed && (StringUtil.isNotNull(details.getFdAuthorityRange())
								? details.getFdAuthorityRange().contains("download") : false);
						// 是否能拷贝
						boolean canCopy = isBorrowed && (StringUtil.isNotNull(details.getFdAuthorityRange())
								? details.getFdAuthorityRange().contains("copy") : false);
						// 是否能打印
						boolean canPrint = isBorrowed && (StringUtil.isNotNull(details.getFdAuthorityRange())
								? details.getFdAuthorityRange().contains("print") : false);
						
						//是否被授权的
						boolean granted = this.isHadGranted(mainModel);
						
						// 这些条件成立才能看见文件级标签
						if (viewAll || isFileReader || isWork || isCreator || isKeeper || isBorrowed || granted) {
							
							//检查当前请求动作是否被授权
							boolean grantStatus = this.checkGrant(granted, mainModel, validatorContext);
							
							if ("view".equals(method) || "read".equals(method) || "viewDownload".equals(method)
									|| "readDownload".equals(method)) { // 能看到文件级就能查看附件
								// 档案创建者，保管者，借阅者和文件级阅读者可以查看档案附件
								if (isCreator || isKeeper || isBorrowed || isFileReader || isWork || grantStatus) {
									return true;
								} else {
									return false;
								}
							} else if ("download".equals(method)) { // 下载
								String open = validatorContext.getParameter("open");
								String attType = attMain.getFdAttType();
								String fdContentType = attMain.getFdContentType();
								if (StringUtil.isNotNull(attType) && "pic".equals(attType)) {
									return true;
								}
								
								if (canDownload || grantStatus) {
									return true;
								}
								if ("1".equals(open) && StringUtil.isNotNull(fdContentType)
										&& fdContentType.indexOf("image") > -1) {
									return true;
								}
							} else if ("print".equals(method)) { // 打印
								if (canPrint || grantStatus) {
									return true;
								}
							} else if ("copy".equals(method)) { // 拷贝
								if (canCopy) {
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 *   是否被授权的
	 * @param mainModel
	 * @return
	 */
	private boolean isHadGranted(KmArchivesMain mainModel) {
		return EXTERNAL_MODEL_NAME_KM_AGREEMENT_APPLY.equals(mainModel.getFdModelName())
				|| EXTERNAL_MODEL_NAME_KM_AGREEMENT_LEDGER.equals(mainModel.getFdModelName());
	}

	
	private boolean checkGrant(boolean hadGranted, KmArchivesMain mainModel, ValidatorRequestContext validatorContext) throws Exception {
		
		if(!hadGranted) {
			return false;
		}
		
		//合同台帐授权
		if(EXTERNAL_MODEL_NAME_KM_AGREEMENT_APPLY.equals(mainModel.getFdModelName())
				|| EXTERNAL_MODEL_NAME_KM_AGREEMENT_LEDGER.equals(mainModel.getFdModelName())) {
			IExtension extension = Plugin.getExtension(
					"com.landray.kmss.sys.attachment.validator",
					EXTERNAL_MODEL_NAME_GRANT_KM_AGREEMENT_LEDGER, "validator");
			if (extension != null) {
				IAuthenticationValidator validator = Plugin.getParamValue(extension, "bean");
				validatorContext.setParameter(PARAM_NAME_EXTERNAL_MODELID, mainModel.getFdModelId());
				validatorContext.setParameter(PARAM_NAME_EXTERNAL_MODELNAME, mainModel.getFdModelName());
				validatorContext.setParameter(PARAM_NAME_GRANT_MODELNAME, EXTERNAL_MODEL_NAME_GRANT_KM_AGREEMENT_LEDGER);
				return validator.validate(validatorContext);
			}
		}
		
		return false;
	}
}
