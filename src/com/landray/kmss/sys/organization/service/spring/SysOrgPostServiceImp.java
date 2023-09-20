package com.landray.kmss.sys.organization.service.spring;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.forms.SysOrgElementForm;
import com.landray.kmss.sys.organization.forms.SysOrgPostForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.service.ISysOrgPostService;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.util.UserUtil;

public class SysOrgPostServiceImp extends SysOrgElementServiceImp implements
		ISysOrgPostService {

	private static final String METHOD_GET_ADD = "add";
	private static final String METHOD_SAVE = "save";
	private static final String METHOD_SAVE_ADD = "saveadd";
	
	private ISysOrgElementService sysOrgElementService;
	
	private ISysOrgPersonService sysOrgPersonService;

	public void setSysOrgElementService(ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	/**
	 * 增加岗位时，在某些情况下会报错
	 */
	@Override
    public String addNewPost(IExtendForm form, RequestContext requestContext)
			throws Exception {
        UserOperHelper.logAdd(form.getModelClass().getName());
		IBaseModel model = convertFormToModel(form, null, requestContext);
		String fdId = model.getFdId();
		if (fdId != null) {
			String reqMethodGet = requestContext.getParameter("method_GET");
			String reqMethod = requestContext.getParameter("method");
			if (METHOD_GET_ADD.equals(reqMethodGet)
					&& (METHOD_SAVE.equals(reqMethod)
							|| METHOD_SAVE_ADD.equals(reqMethod))) {
				if (getBaseDao().isExist(getModelName(), fdId)) {
					throw new UnexpectedRequestException();
				}
			}
		}

		// 在增加岗位时，如果创建者，岗位领导，人员列表，都有同一个人员，那么在保存时会报错。 ^_^
		// 如果上述条件满足，这里需要重新去数据取岗位领导的数据来设置就行了。 ^_^
		if (form instanceof SysOrgPostForm) {
			SysOrgPostForm postForm = (SysOrgPostForm) form;
			if (UserUtil.getUser().getFdId().equals(postForm.getFdThisLeaderId())) {
				SysOrgElement thisLeader = (SysOrgElement) findByPrimaryKey(
						postForm.getFdThisLeaderId(), SysOrgElement.class, true);
				if (thisLeader != null) {
					((SysOrgPost) model).setHbmThisLeader(thisLeader);
				}
			}
		}
		return add(model);
	}
	
	@Override
	public void updateDeptByPosts(String[] postIds, String deptId)
			throws Exception {
		SysOrgElement parent = (SysOrgElement) sysOrgElementService.findByPrimaryKey(deptId);
		for (String postId : postIds) {
			SysOrgPost post = (SysOrgPost) findByPrimaryKey(postId);
			// 记录日志
			if (UserOperHelper.allowLogOper("changeDept", getModelName())) {
				UserOperContentHelper.putUpdate(post).putSimple("fdParent", post.getFdParent(), parent);
			}
			if (post != null) {
				SysOrgPost oldPost =  new SysOrgPost();
				BeanUtils.copyProperties(post, oldPost);
				post.setFdParent(parent);
				addOrgModifyLog(post,oldPost,new RequestContext(),null,null);
				
			}
		}
	}

	@Override
	public void updateDeptByPosts(String[] postIds, String deptId, RequestContext requestContext) throws Exception {
		SysOrgElement parent = (SysOrgElement) sysOrgElementService.findByPrimaryKey(deptId);
		for (String postId : postIds) {
			SysOrgPost post = (SysOrgPost) findByPrimaryKey(postId);
			// 记录日志
			if (UserOperHelper.allowLogOper("changeDept", getModelName())) {
				UserOperContentHelper.putUpdate(post).putSimple("fdParent", post.getFdParent(), parent);
			}
			if (post != null) {
				SysOrgPost oldPost =  new SysOrgPost();
				BeanUtils.copyProperties(post, oldPost);
				post.setFdParent(parent);
				String newFdHierarchyId = SysOrgUtil.getTreeHierarchyId(post);
				post.setFdHierarchyId(newFdHierarchyId);
				if(null != oldPost.getFdParent()){
					if(parent.getFdId().equals(oldPost.getFdParent().getFdId())){
						continue;
					}
				}
				SysOrgElementForm form = (SysOrgElementForm) convertModelToForm(null, post, requestContext);
				SysOrgElementForm oldForm = (SysOrgElementForm) convertModelToForm(null, oldPost, requestContext);
				addOrgModifyLog(post,oldPost,requestContext,oldForm,form);
				
			}
			
		}
	}

	@Override
    public void updatePersonsByPost(String id , Date time) throws Exception {
		
		if(StringUtil.isNotNull(id)) {
			SysOrgPerson person = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(id);
			
			if(person!=null) {
				person.setFdAlterTime(time);
				sysOrgPersonService.update(person);
			}
		}
	}
}
