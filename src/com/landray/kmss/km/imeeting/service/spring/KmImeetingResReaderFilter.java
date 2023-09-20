package com.landray.kmss.km.imeeting.service.spring;

import com.landray.kmss.common.dao.HQLParameter;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.authentication.filter.FilterContext;
import com.landray.kmss.sys.authentication.filter.HQLFragment;
import com.landray.kmss.sys.authentication.filter.IAuthenticationFilter;

/**
 * 会议室资源权限校验器
 * 
 * <pre>
 * 	#138527 符合以下条件都允许访问：
 *    1、会议室分类管理，可维护者：可在此会议室分类下创建会议室，可使用者：可查看并预定该分类下会议室
 *    2、会议室信息，可维护者：可对此会议室进行维护（包括：编辑&删除），可使用者：可查看并预定对应的会议室资源
 * </pre>
 * 
 * @author yonghui
 * @dataTime 2021年4月25日 下午3:00:20
 */
public class KmImeetingResReaderFilter implements IAuthenticationFilter {

	@Override
	public int getAuthHQLInfo(FilterContext ctx) throws Exception {
		HQLFragment hqlFragment = new HQLFragment();
		hqlFragment.setJoinBlock(" left join " + ctx.getModelTable() + ".docCategory docCategory");
		// 如果分类不是所有人可使用，并且是该分类的可使用者，就可以使用该分类下的所有会议室
		hqlFragment.setWhereBlock("docCategory.authAllReaders.fdId not in (:everyone) and docCategory.authAllReaders.fdId in (:orgIds)");
		hqlFragment.setParameter(new HQLParameter("everyone", SysOrgConstant.ORG_PERSON_EVERYONE_ID));
		hqlFragment.setParameter(new HQLParameter("orgIds", ctx.getUser().getUserAuthInfo().getAuthOrgIds()));
		ctx.setHqlFragment(hqlFragment);
		return FilterContext.RETURN_VALUE;
	}

}
