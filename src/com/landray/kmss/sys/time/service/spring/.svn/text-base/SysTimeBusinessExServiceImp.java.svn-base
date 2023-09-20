package com.landray.kmss.sys.time.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLWrapper;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.organization.dao.ISysOrgElementDao;
import com.landray.kmss.sys.organization.dao.ISysOrgGroupDao;
import com.landray.kmss.sys.organization.dao.ISysOrgPersonDao;
import com.landray.kmss.sys.organization.interfaces.SysOrgHQLUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.model.SysTimeBusinessEx;
import com.landray.kmss.sys.time.service.ISysTimeBusinessExService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class SysTimeBusinessExServiceImp extends ExtendDataServiceImp implements ISysTimeBusinessExService , SysOrgConstant {
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(SysTimeBusinessExServiceImp.class);

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof SysTimeBusinessEx) {
            SysTimeBusinessEx sysTimeBusinessEx = (SysTimeBusinessEx) model;
        }
        return model;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        SysTimeBusinessEx sysTimeBusinessEx = (SysTimeBusinessEx) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
    
    @Override
	public List<SysTimeBusinessEx> findByFlowId(String processId)
			throws Exception {
		if(StringUtil.isNull(processId)) {
			return null;
		}
		HQLInfo hqlInfo = new HQLInfo();
		StringBuffer whereBlock = new StringBuffer(" 1=1 ");
		whereBlock.append(
				" and sysTimeBusinessEx.fdFlowId = :fdFlowId and (sysTimeBusinessEx.fdStatus=0 or sysTimeBusinessEx.fdStatus is null)");
		hqlInfo.setWhereBlock(whereBlock.toString());
		hqlInfo.setParameter("fdFlowId", processId);
		List<SysTimeBusinessEx> list = findList(hqlInfo);
		return list;
	}


    private ISysOrgElementDao sysOrgElementDao;
    private ISysOrgPersonDao personDao;
    private ISysOrgGroupDao groupDao;
    public  ISysOrgElementDao getSysOrgElementDao() {
        if (sysOrgElementDao == null) {
            sysOrgElementDao = (ISysOrgElementDao) SpringBeanUtil.getBean("sysOrgElementDao");
        }
        return sysOrgElementDao;
    }
    public ISysOrgPersonDao getPersonDao() {
        if (personDao == null) {
            personDao = (ISysOrgPersonDao) SpringBeanUtil.getBean("sysOrgPersonDao");
        }
        return personDao;
    }
    public ISysOrgGroupDao getSysOrgGroupDao() {
        if (groupDao == null) {
            groupDao = (ISysOrgGroupDao) SpringBeanUtil.getBean("sysOrgGroupDao");
        }
        return groupDao;
    }



    /**
     * 组织ID，转换成人员ID
     * 不取部门下岗位
     * @param orgList
     * @return
     * @throws Exception
     */
    @Override
    public List<String> expandToPersonIds(List orgList) throws Exception {
        if (orgList == null || orgList.isEmpty()) {
            return new ArrayList();
        }
        Session session = getSysOrgElementDao().getHibernateSession();
        List hierarchyIds = new ArrayList();
        List groupIds = new ArrayList();
        List postIds = new ArrayList();
        List personIds = new ArrayList();
        List results;
        String sql, whereBlock;

        for (int i = 0; i < orgList.size(); i++) {
            Object tmpOrg = orgList.get(i);
            SysOrgElement element = null;
            if (tmpOrg instanceof String) {
                //查询具体列，其他不需要的对象属性。不查询
                sql = "select fd_id,fd_org_type,fd_hierarchy_id from sys_org_element where fd_id =:fd_id";
                List<Object[]> resultsTemp = session.createNativeQuery(sql).setParameter("fd_id",tmpOrg.toString()).list();
                if(CollectionUtils.isNotEmpty(resultsTemp)){
                    element =new SysOrgElement();
                    element.setFdId(tmpOrg.toString());
                    element.setFdHierarchyId(String.valueOf(resultsTemp.get(0)[2]));
                    element.setFdOrgType(Integer.valueOf(String.valueOf(resultsTemp.get(0)[1])));
                }
            } else {
                element = (SysOrgElement) orgList.get(i);
            }
            if (element != null) {
                switch (element.getFdOrgType().intValue()) {
                    case ORG_TYPE_ORG:
                    case ORG_TYPE_DEPT:
                        hierarchyIds.add(element.getFdHierarchyId());
                        break;
                    case ORG_TYPE_POST:
                        if (!postIds.contains(element.getFdId())) {
                            postIds.add(element.getFdId());
                        }
                        break;
                    case ORG_TYPE_PERSON:
                        if (!personIds.contains(element.getFdId())) {
                            personIds.add(element.getFdId());
                        }
                        break;
                    case ORG_TYPE_GROUP:
                        if (!groupIds.contains(element.getFdId())) {
                            groupIds.add(element.getFdId());
                        }
                        break;
                }
            }
        }
        // 解释群组
        if (!groupIds.isEmpty()) {
            groupIds = getSysOrgGroupDao().fetchChildGroupIds(groupIds);
            whereBlock = HQLUtil.buildLogicIN("fd_groupid", groupIds);
            sql = "select fd_elementid,fd_org_type,fd_hierarchy_id from sys_org_group_element "
                    + "left join sys_org_element on fd_elementid=fd_id where fd_org_type<"
                    + ORG_TYPE_GROUP + " and " + whereBlock;
            if (logger.isDebugEnabled()) {
                logger.debug("群组解释：" + sql);
            }
            results = session.createNativeQuery(sql).list();
            for (int i = 0; i < results.size(); i++) {
                Object[] objArr = (Object[]) results.get(i);
                int intValue;
                // oracle11G会将objArr[1]改为java.math.BigDecimal
                if (objArr[1] instanceof java.math.BigDecimal) {
                    intValue = ((java.math.BigDecimal) objArr[1]).intValue();
                } else {
                    intValue = ((Number) objArr[1]).intValue();
                }
                switch (intValue) {
                    case ORG_TYPE_ORG:
                    case ORG_TYPE_DEPT:
                        hierarchyIds.add(objArr[2]);
                        break;
                    case ORG_TYPE_POST:
                        if (!postIds.contains(objArr[0])) {
                            postIds.add(objArr[0]);
                        }
                        break;
                    case ORG_TYPE_PERSON:
                        if (!personIds.contains(objArr[0])) {
                            personIds.add(objArr[0]);
                        }
                }
            }
        }
        // 解释部门
        if (!hierarchyIds.isEmpty()) {
            hierarchyIds = SysOrgHQLUtil.formatHierarchyIdList(hierarchyIds);
            StringBuffer whereBf = new StringBuffer();
            for (int i = 0; i < hierarchyIds.size(); i++) {
                whereBf.append(" or fd_hierarchy_id like '").append(
                        hierarchyIds.get(i)).append("%' and fd_is_available = " + SysOrgHQLUtil.toBooleanValueString(true) + " ");
            }
            // 加上fd_is_available=1条件，以兼容有些数据迁移同步过程中层级id没置空的情况
            whereBlock = "(" + whereBf.substring(4) + ")";
            sql = "select fd_id from sys_org_element where fd_org_type="
                    + ORG_TYPE_PERSON + " and " + whereBlock;
            if (logger.isDebugEnabled()) {
                logger.debug("部门解释个人：" + sql);
            }
            addQueryResultToList(session, sql, personIds);
        }
        // 解释岗位
        if (!postIds.isEmpty()) {
            whereBlock = HQLUtil.buildLogicIN("fd_postid", postIds);
            sql = "select fd_personid from sys_org_post_person where "
                    + whereBlock;
            if (logger.isDebugEnabled()) {
                logger.debug("岗位解释：" + sql);
            }
            addQueryResultToList(session, sql, personIds);
        }
        return personIds;
    }

    /**
     * 获取人员对象
     * @param orgList
     * @return
     * @throws Exception
     */
    @Override
    public List<SysOrgPerson> expandToPerson(List orgList) throws Exception {
        List rtnList = new ArrayList();
        List<String> personIds = expandToPersonIds(orgList);
        if (!personIds.isEmpty()) {
            int beginIndex = 0, endIndex = 0;
            while (endIndex < personIds.size()) {
                endIndex = beginIndex + 2000;
                if (endIndex > personIds.size()) {
                    endIndex = personIds.size();
                }
                List subList = personIds.subList(beginIndex, endIndex);
                HQLWrapper hqlWrapper = HQLUtil.buildPreparedLogicIN("fdId",
                        "sysOrgPerson" + "0_", subList);
                HQLInfo hqlInfo = new HQLInfo();
                hqlInfo.setWhereBlock(hqlWrapper.getHql());
                hqlInfo.setParameter(hqlWrapper.getParameterList());
                List results = getPersonDao().findList(hqlInfo);
                if (!ArrayUtil.isEmpty(results)) {
                    rtnList.addAll(results);
                }
                beginIndex += 2000;
            }
        }

        return rtnList;
    }

    private void addQueryResultToList(Session session, String sql, List list) {
        ArrayUtil.concatTwoList(session.createNativeQuery(sql).list(), list);
    }
}
