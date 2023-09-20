package com.landray.kmss.sys.organization.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.util.StringUtil;

/**
 * @author yezhengping
 * 用于获取禁用用户被删除前的部门信息和岗位信息
 *
 */
public class OrgPreDeptPostList implements IXMLDataBean {
	
	private ISysOrgCoreService sysOrgCoreService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}
	
	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		// TODO Auto-generated method stub
		SysOrgPerson person = (SysOrgPerson) sysOrgCoreService
				.findByPrimaryKey(requestInfo.getParameter("fdId"),
						SysOrgPerson.class);
		List preEleList = new ArrayList();
		Map node = new HashMap();
		
		if(StringUtil.isNotNull(person.getFdPreDeptId())){
			SysOrgElement preDept = (SysOrgElement) sysOrgCoreService.findByPrimaryKey(person.getFdPreDeptId(),SysOrgElement.class);
			if(preDept!=null && preDept.getFdIsAvailable()) {
                node.put("deptName", preDept.getFdName());
            } else {
                node.put("deptName", "");
            }
		}
		if(person.getFdPrePostIdsArr()!=null && person.getFdPrePostIdsArr().length>0){
			List<SysOrgElement> prePosts  = (List) sysOrgCoreService.findByPrimaryKeys(person.getFdPrePostIdsArr());
			
			List<SysOrgElement> isAvailPrePosts = new ArrayList();
			for(SysOrgElement post : prePosts){
				if(post.getFdIsAvailable()){
					isAvailPrePosts.add(post);
				}
			}
			
			if(isAvailPrePosts!=null && isAvailPrePosts.size()>0){
				String postsArr = "";
				for(int i=0;i<isAvailPrePosts.size();i++){
					if(i==0) {
                        postsArr = isAvailPrePosts.get(i).getFdName();
                    } else {
                        postsArr += ";" + isAvailPrePosts.get(i).getFdName();
                    }
				}
				node.put("postNames", postsArr);
			}else {
                node.put("postNames", "");
            }
		}
		
		preEleList.add(node);

		return preEleList;
	}

}
