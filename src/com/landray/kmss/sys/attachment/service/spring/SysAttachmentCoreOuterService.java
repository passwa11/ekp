package com.landray.kmss.sys.attachment.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseCoreOuterServiceImp;
import com.landray.kmss.common.service.ICoreOuterService;
import com.landray.kmss.sys.attachment.dao.ISysAttMainCoreInnerDao;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.attachment.model.SysAttBase;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.attachment.service.ISysAttachmentService;
import com.landray.kmss.sys.lbpmservice.support.forms.LbpmProcessMainForm;
import com.landray.kmss.sys.log.util.UserOperConvertHelper;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.IUserDetailOper;
import com.landray.kmss.sys.log.util.oper.IUserOper;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.StringUtil;

public class SysAttachmentCoreOuterService extends BaseCoreOuterServiceImp
		implements ApplicationContextAware, ISysAttachmentService,
		ICoreOuterService {
	ApplicationContext ctx = null;
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttachmentCoreOuterService.class);
	protected ISysAttMainCoreInnerService sysAttMainService;

	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		this.ctx = ctx;
	}

	public ISysAttMainCoreInnerService getSysAttMainService() {
		if (sysAttMainService == null) {
			sysAttMainService = (ISysAttMainCoreInnerService) ctx
					.getBean("sysAttMainService");
		}
		return sysAttMainService;
	}

	public void setSysAttMainService(
			ISysAttMainCoreInnerService sysAttMainService) {
		this.sysAttMainService = sysAttMainService;
	}

	class Attachment implements IAttachment {
		public AutoHashMap attachmentForms = new AutoHashMap(
				AttachmentDetailsForm.class);

		@Override
		public AutoHashMap getAttachmentForms() {
			return attachmentForms;
		}

		public void setAttachmentForms(AutoHashMap attachmentForms) {
			this.attachmentForms = attachmentForms;
		}
	}

	/**
	 * 删除主文档时，删除对应附件
	 */
	@Override
	public void delete(IBaseModel mainModel) throws Exception {
		if (mainModel instanceof IAttachment) {
			getSysAttMainService().deleteCoreModels(mainModel);
			super.delete(mainModel);
		}
	}

	@Override
	public void convertModelToForm(IExtendForm form, IBaseModel model,
			RequestContext requestContext) throws Exception {
		if (model == null || model.getFdId() == null) {
			return;
		}
		if (form instanceof IAttachmentForm && model instanceof IAttachment) {
			addAttachment((IAttachment) form, (IAttachment) model);
		}
	}

	@Override
	public void convertFormToModel(IExtendForm form, IBaseModel model,
			RequestContext requestContext) throws Exception {
		if (form instanceof IAttachmentForm && model instanceof IAttachment) {
			IAttachmentForm attachmentForm = (IAttachmentForm) form;
			IAttachment attachment = (IAttachment) model;
			AutoHashMap attachmentForms = attachmentForm.getAttachmentForms();
			attachment.getAttachmentForms().putAll(attachmentForms);
		}
	}

	@Override
	public void addAttachment(IAttachment form, IAttachment model)
			throws Exception {
		addAttachment(form, model, null, true);
	}

	@Override
	public void addAttachment(IAttachment form, IAttachment model, String fdKey)
			throws Exception {
		addAttachment(form, model, fdKey, false);
	}

	@Override
	public Map getCloneAttachmentMap(IAttachment attachment,
									 String targetModelName) throws Exception {
		return getCloneAttachmentMapForModel(attachment, targetModelName, null,
				null, true);
	}

	@Override
	public Map getCloneAttachmentMapForModel(IAttachment attachment,
											 String targetModelName, IBaseModel model) throws Exception {
		return getCloneAttachmentMapForModel(attachment, targetModelName,
				model, null, true);
	}

	@Override
	public Map getCloneAttachmentMapForModel(IAttachment attachment,
											 String targetModelName, IBaseModel model, String fdKey)
			throws Exception {
		return getCloneAttachmentMapForModel(attachment, targetModelName,
				model, fdKey, false);
	}

	@Override
	public void updateCloneAttachmentMap(IAttachment attachment)
			throws Exception {
		String modelClassName = ModelUtil.getModelClassName(attachment);
		String modelId = ((IBaseModel) attachment).getFdId();

		Map forms = attachment.getAttachmentForms();
		for (Iterator it = forms.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			if (key != null) {
				AttachmentDetailsForm attachmentForm = (AttachmentDetailsForm) forms
						.get(key);
				if (attachmentForm.getAttachments() != null) {
					List atts = attachmentForm.getAttachments();
					for (int i = 0; i < atts.size(); i++) {
						SysAttMain sysAttMain = (SysAttMain) atts.get(i);
						sysAttMain.setFdKey(key);
						sysAttMain.setFdModelId(modelId);
						sysAttMain.setFdModelName(modelClassName);
						getSysAttMainService().update(sysAttMain);
					}
				}

			}
		}

	}

	@Override
	public void cloneModelToForm(IExtendForm form, IBaseModel model,
			RequestContext requestContext) throws Exception {
		if (model == null || model.getFdId() == null) {
			return;
		}
		if (form instanceof IAttachmentForm && model instanceof IAttachment) {
			addAttachment((IAttachment) form, (IAttachment) model);
			// 克隆附件以及指定文件
			String modelClassName = ModelUtil.getModelClassName(model);
			IAttachmentForm attachmentForm = (IAttachmentForm) form;
			Map attForms = attachmentForm.getAttachmentForms();
			//161179 是否为新文件，是，则克隆时需要设置新的FileId
			String isNewFile = requestContext.getParameter("isNewFile");

			for (Iterator it = attForms.keySet().iterator(); it.hasNext();) {
				String key = (String) it.next();
				AttachmentDetailsForm attForm = (AttachmentDetailsForm) attForms
						.get(key);
				attForm.setFdModelName(modelClassName);
				attForm.setFdKey(key);
				List atts = attForm.getAttachments();
				String attids = "";
				AutoArrayList attachments = new AutoArrayList(SysAttMain.class);
				for (Iterator iter = atts.iterator(); iter.hasNext();) {
					SysAttMain sysAttMain = (SysAttMain) iter.next();
					if(SysAttBase.HISTORY_NAME.equals(sysAttMain.getFdKey())) {
                        continue;
                    }
					if(StringUtil.isNotNull(isNewFile) && "true".equalsIgnoreCase(isNewFile)) {
						sysAttMain = getSysAttMainService().clone(sysAttMain, true);
					} else {
						sysAttMain = getSysAttMainService().clone(sysAttMain);
					}

					sysAttMain.setFdVersion(1);		
					sysAttMain.setDownloadSum(0);
					attids += sysAttMain.getFdId().toString();
					if (iter.hasNext()) {
						attids += ";";
					}
					attachments.add(sysAttMain);
				}
				attForm.setAttachments(attachments);
				attForm.setAttachmentIds(attids);
			}
		}
	}

	@Override
	protected void save(IBaseModel model) throws Exception {
		if (model instanceof IAttachment) {
			IAttachment attachment = (IAttachment) model;
			String modelClassName = ModelUtil.getModelClassName(model);
			String modelId = model.getFdId();
			Map forms = attachment.getAttachmentForms();
			saveAtt(forms, modelId, modelClassName);
		}
	}

	@Override
	public void saveByLbpmForm(LbpmProcessMainForm lbpmProcessForm)
			throws Exception {
		String modelClassName = lbpmProcessForm.getSysWfBusinessForm()
				.getFdModelName();
		String modelId = lbpmProcessForm.getSysWfBusinessForm().getFdModelId();
		Map forms = lbpmProcessForm.getAttachmentForms();
		saveAtt(forms, modelId, modelClassName);
	}

    @Override
    public void saveApi(IBaseModel model) throws Exception {
        save(model);
    }

	private void saveAtt(Map forms, String modelId, String modelClassName)
			throws Exception {
		//附件机制在日志中展示的默认（虚拟）字段
        IUserDetailOper detailOper = null;
        String sysAttMainName = SysAttMain.class.getName();
        if(!UserOperHelper.isMechesUsedModelName(ModelUtil.getModelClassName(modelClassName))){
            if(logger.isDebugEnabled()){
                logger.debug("当前操作对应的主文档类型与机制对应的主文档不一致, 不记录本次机制操作日志");
            }
        }else{
            IUserOper oper4Mechs = 
                    UserOperConvertHelper.createOper4Mechs(SysAttMain.MECHANISM_NAME, modelId,null,null,sysAttMainName);
            detailOper = oper4Mechs.createOper4Detail(SysAttMain.LOG_FIELD_NAME,sysAttMainName);
        }
		for (Iterator it = forms.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			if (key != null) {
				AttachmentDetailsForm attachmentForm = (AttachmentDetailsForm) forms.get(key);
				if (StringUtil.isNotNull(attachmentForm.getDeletedAttachmentIds())) {
					String[] ids = attachmentForm.getDeletedAttachmentIds().split(";");
					if(ids.length>0){
						List<SysAttMain> sysAttMains = getSysAttMainService().findByPrimaryKeys(ids);
						for (SysAttMain attMain : sysAttMains) {//核实属于本文档的附件，才清理
							boolean deleted = false;
							if(StringUtil.isNotNull(attMain.getFdModelId()) && StringUtil.isNotNull(attMain.getFdModelName())){//修改的情况
								if(attMain.getFdModelId().equals(modelId) && attMain.getFdModelName().equals(modelClassName)){
									deleted=true;
								}
							}else{ //新建的情况
								if(StringUtil.isNull(attMain.getFdModelName())){
									if(StringUtil.isNull(attMain.getFdModelId())){
										deleted = true;
									}else{
										if(attMain.getFdModelId().equals(modelId)){
											deleted = true;
										}
									}
								}else{
									if(StringUtil.isNull(attMain.getFdModelId())){
										if(attMain.getFdModelName().equals(modelClassName)){
											deleted = true;
										}
									}
								}
							}
							if(deleted){
								getSysAttMainService().delete(attMain);
								if(detailOper!=null){
								    detailOper.putDelete(attMain.getFdId(), attMain.getFdFileName());
								}
							}
						}
					}
				}
				if (attachmentForm.getAttachmentIds() != null) {
					String[] ids = attachmentForm.getAttachmentIds().split(
							";");
					Map<String,Boolean> idHave =new HashMap<>();
					for (int i = 0; i < ids.length; i++) {
						if (ids[i] == null || ids[i].trim().length() == 0) {
                            continue;
                        }
						if(idHave.get(ids[i]) !=null){
							if(logger.isDebugEnabled()){
								logger.debug(String.format("当前操作附件重复，ID:%s",ids[i]));
							}
							continue;
						}
						idHave.put(ids[i],true);
						if(getSysAttMainService().getBaseDao().isExist(SysAttMain.class.getName(),ids[i] )) {
							SysAttMain sysAttMain = (SysAttMain) getSysAttMainService()
									.findByPrimaryKey(ids[i]);
							if (sysAttMain != null) {
								sysAttMain.setFdKey(key);
								// if (StringUtil.isNull(sysAttMain.getFdModelId())
								// ||
								// StringUtil.isNull(sysAttMain.getFdModelName())) {
								// }
								sysAttMain.setFdModelId(modelId);
								sysAttMain.setFdModelName(modelClassName);
								sysAttMain.setInputStream(null);
								sysAttMain.setFdOrder(i);
								sysAttMain.setAddQueue(attachmentForm.getAddQueue());
								if (detailOper != null) {
									detailOper.putUpdate(sysAttMain.getFdId(), sysAttMain.getFdFileName());
								}
								getSysAttMainService().update(sysAttMain);
							}
						}else{
							if(logger.isDebugEnabled()){
								logger.debug(String.format("当前操作附件为空，ID:%s",ids[i]));
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 将model的附件复制到form中
	 * 
	 * @param form
	 * @param model
	 * @param fdKey
	 *            附件所在key
	 * @param keyIsNullGetAll
	 *            当fdKey为空时，true 获取所有附件 false 不获取所有附件
	 * @throws Exception
	 */
	private void addAttachment(IAttachment form, IAttachment model,
			String fdKey, boolean keyIsNullGetAll) throws Exception {
		if (model == null) {
			return;
		}
		if (!org.hibernate.Hibernate.isInitialized(form)) {
			org.hibernate.Hibernate.initialize(form);
		}
		// 优化，减少数据库查询
		Map attForms = form.getAttachmentForms();
		String modelClassName = ModelUtil.getModelClassName(model);
		String modelId = BeanUtils.getProperty(model, "fdId");
		List atts = ((ISysAttMainCoreInnerDao) getSysAttMainService()
				.getBaseDao()).findAttListByModel(modelClassName, modelId);
		boolean isValidate = true;
		if (keyIsNullGetAll && StringUtil.isNull(fdKey)) {
			isValidate = false;
		}
		for (Iterator iter = atts.iterator(); iter.hasNext();) {
			SysAttMain sysAttMain = (SysAttMain) iter.next();
			if (isValidate && !sysAttMain.getFdKey().equals(fdKey)) {
				continue;
			}
			AttachmentDetailsForm attForm = (AttachmentDetailsForm) attForms
					.get(sysAttMain.getFdKey());
			attForm.setFdModelId(modelId);
			attForm.setFdModelName(modelClassName);
			attForm.setFdKey(sysAttMain.getFdKey());
			if (!attForm.getAttachments().contains(sysAttMain)) {
				attForm.getAttachments().add(sysAttMain);
			}
			String attids = attForm.getAttachmentIds();
			if (StringUtil.isNull(attids)) {
				attForm.setAttachmentIds(sysAttMain.getFdId());
			} else {
				attForm.setAttachmentIds(sysAttMain.getFdId() + ";" + attids);
			}
		}
	}

	/**
	 * 将model的附件或model的部分附件复制到form中
	 * 
	 * @param attachment
	 * @param targetModelName
	 * @param model
	 * @param fdKey
	 * @param keyIsNullGetAll
	 * @throws Exception
	 */
	private Map getCloneAttachmentMapForModel(IAttachment attachment,
			String targetModelName, IBaseModel model, String fdKey,
			boolean keyIsNullGetAll) throws Exception {
		IAttachment att = null;
		String modelId = null;
		Map attForms = null;
		Iterator keys = null;
		String modelClassName = targetModelName;
		if (model == null) {
			att = new Attachment();
		} else {
			att = (IAttachment) model;
			if (StringUtil.isNull(modelClassName)) {
				modelClassName = ModelUtil.getModelClassName(model);
			}
			modelId = model.getFdId();
		}
		int p = modelClassName.indexOf("$");
		if (p > -1) {
			modelClassName = modelClassName.substring(0, p);
		}

		if (StringUtil.isNull(fdKey) && keyIsNullGetAll) {
			addAttachment(att, attachment);
			attForms = att.getAttachmentForms();
			keys = attForms.keySet().iterator();
		} else {
			addAttachment(att, attachment, fdKey);
			attForms = att.getAttachmentForms();
			List tmpKeys = new ArrayList();
			tmpKeys.add(fdKey);
			keys = tmpKeys.iterator();
		}

		for (Iterator iterator = keys; iterator.hasNext();) {
			String key = (String) iterator.next();
			AttachmentDetailsForm attForm = (AttachmentDetailsForm) attForms
					.get(key);
			attForm.setFdModelName(modelClassName);
			attForm.setFdKey(key);
			List atts = attForm.getAttachments();
			String attids = "";
			AutoArrayList attachments = new AutoArrayList(SysAttMain.class);
			for (Iterator iter = atts.iterator(); iter.hasNext();) {
				SysAttMain sysAttMain = (SysAttMain) iter.next();
				if(SysAttBase.HISTORY_NAME.equals(sysAttMain.getFdKey())) {
                    continue;
                }
				sysAttMain = getSysAttMainService().clone(sysAttMain);
				sysAttMain.setFdVersion(1);
				sysAttMain.setFdModelId(modelId);
				if (model != null) {
                    sysAttMain.setFdModelName(modelClassName);
                }
				attids += sysAttMain.getFdId().toString();
				if (iter.hasNext()) {
					attids += ";";
				}
				attachments.add(sysAttMain);
			}
			attForm.setAttachments(attachments);
			attForm.setAttachmentIds(attids);
		}
		getSysAttMainService().flushHibernateSession();
		if (StringUtil.isNull(fdKey) && keyIsNullGetAll) {
			return att.getAttachmentForms();
		} else {
			Map newAttForms = new HashMap();
			newAttForms.put(fdKey, attForms.get(fdKey));
			return newAttForms;
		}
	}
}
