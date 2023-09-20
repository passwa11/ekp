package com.landray.kmss.km.archives.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.archives.model.KmArchivesCategory;
import com.landray.kmss.km.archives.model.KmArchivesMain;
import com.landray.kmss.km.archives.service.IKmArchivesCategoryService;
import com.landray.kmss.km.archives.util.KmArchivesUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel;
import com.landray.kmss.sys.simplecategory.service.ISysSimpleCategoryService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class KmArchivesCategoryServiceImp extends ExtendDataServiceImp implements IKmArchivesCategoryService, ISysSimpleCategoryService {

	private static final String PATH = "/km/archives/xform/";

	@Override
	public List findList(HQLInfo hqlInfo) throws Exception {
		if (!UserUtil.getKMSSUser().isAdmin()) {
			String whereBlock = hqlInfo.getWhereBlock();
			List authOrgIds = UserUtil.getKMSSUser().getUserAuthInfo()
					.getAuthOrgIds();
			String modelTable = "kmArchivesCategory";
			if(StringUtil.isNotNull(hqlInfo.getModelTable())) {
				modelTable = hqlInfo.getModelTable();
			}
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"(" + modelTable + ".authReaderFlag = 1 or "
							+ HQLUtil.buildLogicIN(modelTable + ".authAllReaders.fdId", authOrgIds) + ")");
			hqlInfo.setWhereBlock(whereBlock);
		}
		return super.findList(hqlInfo);
	}

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof KmArchivesCategory) {
            KmArchivesCategory kmArchivesCategory = (KmArchivesCategory) model;
        }
        return model;
    }

	@Override
    public void update(IExtendForm form, RequestContext requestContext) throws Exception {
		UserOperHelper.logUpdate(getModelName());
		IBaseModel model = convertFormToModel(form, null, requestContext);
		// 修改子分类及文档
		setAppToChildren((KmArchivesCategory) model, requestContext);
		super.update(model);
	}

	private void setAppToChildren(KmArchivesCategory model, RequestContext requestContext) throws Exception {
		String appToMyDoc = requestContext.getParameter("appToMyDoc");
		String appToChildren = requestContext.getParameter("appToChildren");

		if (StringUtil.isNotNull(appToMyDoc)) {
			changeDoc(model, model.getFdId());
		}
		if (StringUtil.isNotNull(appToChildren)) { // 子类信息
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("kmArchivesCategory.fdHierarchyId like :fdHierarchyId "
					+ "and kmArchivesCategory.fdId!=:templateId");
			hqlInfo.setParameter("fdHierarchyId", model.getFdHierarchyId() + "%");
			hqlInfo.setParameter("templateId", model.getFdId());
			List<?> list = this.findList(hqlInfo);
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					KmArchivesCategory childModel = (KmArchivesCategory) list.get(i);
					if (StringUtil.isNotNull(appToChildren)) {
						changeChildTemplate(model, childModel);
					}
				}
			}
		}
	}

	// 修改子类
	private void changeChildTemplate(KmArchivesCategory model, KmArchivesCategory childModel) throws Exception {
		// if (model.getSysPropertyTemplate() != null) {
		// childModel.setSysPropertyTemplate(model.getSysPropertyTemplate());
		// } else {
		// childModel.setSysPropertyTemplate(null);
		// }
		// 应用父类的标签
		// List childTags = new ArrayList();
		// List<?> tagList = model.getSysTagTemplates();
		// for (int i = 0; i < tagList.size(); i++) {
		// SysTagTemplate tagTemplate = (SysTagTemplate) tagList.get(i);
		// if (tagTemplate != null) {
		// SysTagTemplate childTag = new SysTagTemplate();
		// childTag.setFdKey(tagTemplate.getFdKey());
		// childTag.setFdModelId(childModel.getFdId());
		// childTag.setFdModelName(tagTemplate.getFdModelName());
		// childTag.setFdTagIds(tagTemplate.getFdTagIds());
		// childTag.setFdTagNames(tagTemplate.getFdTagNames());
		// childTags.add(childTag);
		// }
		// }
		// childModel.setSysTagTemplates(childTags);

		// 可维护者
		List copyAuthEditors = new ArrayList();
		copyAuthEditors.addAll(model.getAuthEditors());
		List oldAuthEditors = childModel.getAuthEditors();
		ArrayUtil.concatTwoList(copyAuthEditors, oldAuthEditors);
		childModel.setAuthEditors(oldAuthEditors);

		// 可使用者
		List copyAuthReaders = new ArrayList();
		copyAuthReaders.addAll(model.getAuthReaders());
		List oldAuthReaders = childModel.getAuthReaders();
		ArrayUtil.concatTwoList(copyAuthReaders, oldAuthReaders);
		childModel.setAuthReaders(oldAuthReaders);

		// 起草人不可修改可阅读者
		childModel.setAuthChangeReaderFlag(model.getAuthChangeReaderFlag());
		
		// 默认阅读者
		List copyAuthTmpReaders = new ArrayList();
		copyAuthTmpReaders.addAll(model.getAuthTmpReaders());
		List oldAuthTmpReaders = childModel.getAuthTmpReaders();
		ArrayUtil.concatTwoList(copyAuthTmpReaders, oldAuthTmpReaders);
		childModel.setAuthTmpReaders(oldAuthTmpReaders);

		this.update(childModel);
	}

	// 修改类别下的文档
	private void changeDoc(KmArchivesCategory model, String fdId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("kmArchivesMain.docTemplate.fdId=:categoryId");
		hqlInfo.setParameter("categoryId", fdId);
		hqlInfo.setModelName("com.landray.kmss.km.archives.model.KmArchivesMain");
		List<?> list = this.findList(hqlInfo);
		IBaseService docService = (IBaseService) SpringBeanUtil.getBean("kmArchivesMainService");
		List authTmpReaders = model.getAuthTmpReaders();
		Boolean authChangeReaderFlag = model.getAuthChangeReaderFlag();
		List copyTmpReaders = new ArrayList();
		copyTmpReaders.addAll(authTmpReaders);
		for (int i = 0; i < list.size(); i++) {
			KmArchivesMain doc = (KmArchivesMain) list.get(i);
			// if (model.getSysPropertyTemplate() != null) {
			// String e = PATH + model.getSysPropertyTemplate().getFdId() + "/"
			// + model.getSysPropertyTemplate().getFdId();
			// doc.setExtendFilePath(e);
			// } else
			// doc.setExtendFilePath(null);
			List oldReaders = doc.getAuthReaders();
			ArrayUtil.concatTwoList(copyTmpReaders, oldReaders);
			doc.setAuthReaders(oldReaders);
			doc.setAuthRBPFlag(model.getAuthRBPFlag());
			doc.setAuthChangeReaderFlag(authChangeReaderFlag);
			docService.update(doc);
		}
	}

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        KmArchivesCategory kmArchivesCategory = new KmArchivesCategory();
        kmArchivesCategory.setDocCreateTime(new Date());
        kmArchivesCategory.setDocCreator(UserUtil.getUser());
        KmArchivesUtil.initModelFromRequest(kmArchivesCategory, requestContext);
        return kmArchivesCategory;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        KmArchivesCategory kmArchivesCategory = (KmArchivesCategory) model;
    }

    @Override
    public List<KmArchivesCategory> findByFdParent(KmArchivesCategory fdParent) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("kmArchivesCategory.fdParent.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdParent.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List getAllChildCategory(ISysSimpleCategoryModel category) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fdHierarchyId like :fdHierarchyId and fdId!=:fdId");
        hqlInfo.setParameter("fdHierarchyId", category.getFdHierarchyId() + "%");
        hqlInfo.setParameter("fdId", category.getFdId());
        return this.findList(hqlInfo);
    }
}
