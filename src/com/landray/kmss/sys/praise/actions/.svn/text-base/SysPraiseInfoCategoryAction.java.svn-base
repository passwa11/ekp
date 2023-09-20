package com.landray.kmss.sys.praise.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import com.landray.kmss.web.action.ActionForm;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.praise.service.ISysPraiseInfoCategoryService;
import com.landray.kmss.sys.simplecategory.actions.SysSimpleCategoryAction;
import com.landray.kmss.sys.simplecategory.forms.ISysSimpleCategoryForm;
import com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ResourceUtil;

public class SysPraiseInfoCategoryAction extends SysSimpleCategoryAction {

	protected ISysPraiseInfoCategoryService sysPraiseInfoCategoryService;

	@Override
	protected ISysPraiseInfoCategoryService getServiceImp(HttpServletRequest request) {
		if (sysPraiseInfoCategoryService == null) {
			sysPraiseInfoCategoryService = (ISysPraiseInfoCategoryService) getBean("sysPraiseInfoCategoryService");
		}
		return sysPraiseInfoCategoryService;
	}

	/**
	 * 添加操作调用的函数,在有父类别参数时调用<br>
	 * 仅用于add的操作。
	 * 
	 * @param parentId
	 * @param request
	 * @param isParent
	 * @return
	 * @throws Exception
	 */
	@Override
	protected ActionForm getNewFormFromCate(String parentId, HttpServletRequest request, boolean isParent)
			throws Exception {
		ISysSimpleCategoryModel category = (ISysSimpleCategoryModel) getServiceImp(request).findByPrimaryKey(parentId);
		ISysSimpleCategoryForm categoryForm = (ISysSimpleCategoryForm) getServiceImp(request).cloneModelToForm(null,
				category, new RequestContext(request));
		if (isParent) {
			categoryForm.setFdParentId(category.getFdId().toString());
			categoryForm.setFdParentName(category.getFdName());
			categoryForm.setAuthReaderIds(null);
			categoryForm.setAuthReaderNames(null);
			categoryForm.setAuthEditorIds(null);
			categoryForm.setAuthEditorNames(null);
			categoryForm.setFdOrder(null);
			categoryForm.setFdName(null);
			categoryForm.setAuthNotReaderFlag("false");
			categoryForm.getDynamicMap().clear();
			// 设置继承的权限
			setRightInherit(categoryForm, category);
		} else {
			categoryForm.setFdOrder(null);
			categoryForm.setFdName(ResourceUtil.getString("sysSimpleCategory.copyOf", "sys-simplecategory") + " "
					+ categoryForm.getFdName());
		}

		categoryForm.setFdIsinheritMaintainer("true");
		categoryForm.setFdIsinheritUser("false");

		((ExtendForm) categoryForm).setMethod("add");
		((ExtendForm) categoryForm).setMethod_GET("add");
		if ("true".equals(categoryForm.getFdIsinheritMaintainer())) {
			request.setAttribute("parentMaintainer", getParentMaintainer(category, request, isParent));
			request.setAttribute("parentMaintainer2", getParentMaintainer2(category, request, isParent));
		}
		return (ExtendForm) categoryForm;
	}

	// 新建类别时候设置继承来的权限信息
	private void setRightInherit(ISysSimpleCategoryForm form, ISysSimpleCategoryModel model) throws Exception {
		if (model != null && model.getAuthEditors() != null) {
			List<?> list = model.getAuthEditors();
			String ids = "";
			String names = "";
			for (int i = 0; i < list.size(); i++) {
				Object o = list.get(i);
				if (o instanceof SysOrgElement) {
					SysOrgElement e = (SysOrgElement) o;
					names = names + e.getFdName() + ";";
					ids = ids + e.getFdId() + ";";
					continue;
				}
			}
			if (ids.length() > 0) {
                ids = ids.substring(0, ids.lastIndexOf(";"));
            }
			if (names.length() > 0) {
                names = names.substring(0, names.lastIndexOf(";"));
            }

			form.setAuthEditorIds(ids);
			form.setAuthEditorNames(names);
		}
		if (model != null && model.getAuthReaders() != null) {
			List<?> list = model.getAuthReaders();
			String ids = "";
			String names = "";

			for (int i = 0; i < list.size(); i++) {
				Object o = list.get(i);
				if (o instanceof SysOrgElement) {
					SysOrgElement e = (SysOrgElement) o;
					names = names + e.getFdName() + ";";
					ids = ids + e.getFdId() + ";";
					continue;
				}
			}
			if (ids.length() > 0) {
                ids = ids.substring(0, ids.lastIndexOf(";"));
            }
			if (names.length() > 0) {
                names = names.substring(0, names.lastIndexOf(";"));
            }

			form.setAuthReaderIds(ids);
			form.setAuthReaderNames(names);
			Boolean notReaderFlag = (Boolean) getProperty(model, "authNotReaderFlag");
			form.setAuthNotReaderFlag(notReaderFlag.booleanValue() ? "true" : "false");

		}

	}

	private Object getProperty(Object bean, String property)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		return PropertyUtils.getProperty(bean, property);
	}

	private String getParentMaintainer(ISysSimpleCategoryModel category, HttpServletRequest request, boolean isParent)
			throws Exception {
		List allEditors = new ArrayList();
		if (!isParent) {
			category = (ISysSimpleCategoryModel) category.getFdParent();
		}
		while (category != null && category.getFdIsinheritMaintainer() != null
				&& category.getFdIsinheritMaintainer().booleanValue()) {
			ArrayUtil.concatTwoList(category.getAuthEditors(), allEditors);
			category = (ISysSimpleCategoryModel) category.getFdParent();
		}
		return ArrayUtil.joinProperty(allEditors, "fdName", ";")[0];
	}

	// 页面上得到 可使用者， 可阅读者
	private String getParentMaintainer2(ISysSimpleCategoryModel category, HttpServletRequest request, boolean isParent)
			throws Exception {
		List<?> allReaders = new ArrayList<Object>();
		if (!isParent) {
			category = (ISysSimpleCategoryModel) category.getFdParent();

		}
		while ((category != null) && (category.getFdIsinheritMaintainer() != null)
				&& (category.getFdIsinheritMaintainer().booleanValue())) {
			ArrayUtil.concatTwoList(category.getAuthReaders(), allReaders);
			category = (ISysSimpleCategoryModel) category.getFdParent();

		}
		return ArrayUtil.joinProperty(allReaders, "fdName", ";")[0];
	}

}
