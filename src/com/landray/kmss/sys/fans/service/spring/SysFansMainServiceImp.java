package com.landray.kmss.sys.fans.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.query.Query;
import org.hibernate.query.NativeQuery;
import org.hibernate.Session;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.fans.constant.SysFansConstant;
import com.landray.kmss.sys.fans.model.SysFansMain;
import com.landray.kmss.sys.fans.service.ISysFansMainService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 关注机制业务接口实现
 * 
 * @author
 * @version 1.0 2015-02-13
 */
public class SysFansMainServiceImp extends BaseServiceImp
		implements ISysFansMainService {
	private ISysOrgPersonService sysOrgPersonService;

	public void
			setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	/**
	 * 关注操作
	 */
	@Override
	public String add(IExtendForm form, RequestContext requestContext)
			throws Exception {
		String fdModelName = requestContext.getParameter("fdModelName");
		String userId = requestContext.getParameter("userId");
		// SysOrgPerson fdUser = null;
		// if(StringUtil.isNotNull(userId)){
		// fdUser = (SysOrgPerson)sysOrgPersonService.findByPrimaryKey(userId);
		// }
		String curUserId = UserUtil.getUser().getFdId();

		SysFansMain model = new SysFansMain();
		model.setFdFollowTime(new Date());
		model.setFdFansId(curUserId);
		model.setFdUserId(userId);
		model.setFdUserType(1);
		model.setFdCanUnfollow(true);
		model.setFdModelName(fdModelName);
		return add(model);
	}

	/**
	 * 获得关注数/粉丝数
	 * 
	 * @param userId
	 * @param type
	 *            :0代表关注数；1代表粉丝数
	 * @return
	 * @throws Exception
	 */
	@Override
	public Integer getFollowCount(String userId, Integer type,
								  String fdModelName) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = " 1=1 ";
		if (StringUtil.isNull(fdModelName)) {
			return 0;
		}
		if (StringUtil.isNull(userId)) {
			userId = UserUtil.getUser().getFdId();
		}
		if (0 == type) {
			whereBlock +=
					" and sysFansMain.fdModelName =:fdModelName and sysFansMain.fdFansId =:userId ";
		} else {
			whereBlock +=
					" and sysFansMain.fdModelName =:fdModelName and sysFansMain.fdUserId =:userId ";
		}

		hqlInfo.setParameter("fdModelName", fdModelName);
		hqlInfo.setParameter("userId", userId);
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setSelectBlock(" count(*) ");

		Object count = findValue(hqlInfo).get(0);
		return new Integer(count.toString());
	}

	/**
	 * 校验是否已关注
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public Boolean checkFollow(String userId, String fdModelName)
			throws Exception {
		String curUserId = UserUtil.getUser().getFdId();
		Boolean isFollow = isFollowPerson(curUserId, userId, fdModelName);
		return isFollow;
	}

	@Override
	public Boolean isFollowPerson(String originId, String targetId,
								  String fdModelName) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = " 1=1 ";
		if (StringUtil.isNull(targetId) || StringUtil.isNull(fdModelName)
				|| StringUtil.isNull(originId)) {
			return false;
		}
		whereBlock += " and sysFansMain.fdModelName =:fdModelName "
				+ "and sysFansMain.fdUserId =:userId "
				+ "and sysFansMain.fdFansId =:fdFansId ";
		hqlInfo.setParameter("fdModelName", fdModelName);
		hqlInfo.setParameter("userId", targetId);
		hqlInfo.setParameter("fdFansId", originId);
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setSelectBlock(" count(*) ");

		Object count = findValue(hqlInfo).get(0);
		Integer followCount = new Integer(count.toString());
		if (followCount >= 1) {
			return true;
		}
		return false;
	}

	@Override
	public String addFansByIds(String originId, String targetId,
							   String fdModelName, Integer fdUserType) throws Exception {
		SysFansMain model = new SysFansMain();
		model.setFdFollowTime(new Date());
		model.setFdFansId(originId);
		model.setFdUserId(targetId);
		model.setFdUserType(fdUserType);
		model.setFdCanUnfollow(true);
		model.setFdModelName(fdModelName);
		Integer fdRelationType = SysFansConstant.RELA_TYPE_FAN;
		if (fdUserType.equals(SysFansConstant.RELATION_USER_TYPE_PERSON)) {
			String fansRecordId = getPersonFollow(targetId);
			if (StringUtil.isNotNull(fansRecordId)) {
				fdRelationType = SysFansConstant.RELA_TYPE_EACH_OTHER;

				String hql =
						"update SysFansMain set fdRelationType =:type where fdId =:fdId";
				Query query =
						getBaseDao().getHibernateSession().createQuery(hql);
				query.setParameter("type",
						SysFansConstant.RELA_TYPE_EACH_OTHER);
				query.setParameter("fdId", fansRecordId);
				query.executeUpdate();

				// 记录更新双向关注日志
				if (UserOperHelper.allowLogOper("addFollow", getModelName())) {
					UserOperContentHelper.putUpdate(fansRecordId).putSimple(
							"fdRelationType", "",
							SysFansConstant.RELA_TYPE_EACH_OTHER);
				}
			}
		}
		model.setFdRelationType(fdRelationType);

		// 记录日志
		if (UserOperHelper.allowLogOper("addFollow", getModelName())) {
			UserOperContentHelper.putAdd(model, "fdFansId", "fdUserId",
					"fdUserType", "fdCanUnfollow", "fdModelName",
					"fdRelationType");
		}

		return add(model);
	}

	/**
	 * 关注操作
	 */
	@Override
	public String addFans(String userId, String fdModelName, Integer fdUserType)
			throws Exception {
		String curUserId = UserUtil.getUser().getFdId();

		String rtnVal =
				addFansByIds(curUserId, userId, fdModelName, fdUserType);
		return rtnVal;
	}

	/**
	 * 获取关注当前人员的关注记录
	 * 
	 * @param personId
	 * @return
	 * @throws Exception
	 */
	public String getPersonFollow(String personId) throws Exception {
		String curUserId = UserUtil.getUser().getFdId();
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = " 1=1 ";
		if (StringUtil.isNull(personId) || personId.equals(curUserId)) {
			return null;
		}
		whereBlock += "and sysFansMain.fdUserId =:userId "
				+ "and sysFansMain.fdFansId =:fdFansId "
				+ "and sysFansMain.fdUserType =:fdUserType";
		hqlInfo.setParameter("userId", curUserId);
		hqlInfo.setParameter("fdFansId", personId);
		hqlInfo.setParameter("fdUserType",
				SysFansConstant.RELATION_USER_TYPE_PERSON);
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setSelectBlock(" sysFansMain.fdId ");

		List<String> result = findValue(hqlInfo);

		if (result.size() >= 1) {
			return result.get(0);
		}

		return null;
	}

	/**
	 * 关注/粉丝数,增加或者减少margin(1/-1)
	 * 
	 * @param requestInfo
	 * @param margin
	 * @throws Exception
	 */
	private void updateAttenFanNum(HttpServletRequest requestInfo, int margin)
			throws Exception {
		String fdPersonId = requestInfo.getParameter("fdPersonId");// 被关注者
		String fdModelName = requestInfo.getParameter("attentModelName");// 被关注者模型
		String fansModelName = requestInfo.getParameter("fansModelName");// 关注者模型
		String isFollowPerson = requestInfo.getParameter("isFollowPerson");// 关注的对象是否是人

		SysDictModel atDictModel =
				SysDataDict.getInstance().getModel(fdModelName);
		SysDictModel fnDictModel =
				SysDataDict.getInstance().getModel(fansModelName);
		if (fnDictModel == null || atDictModel == null) {
			return;
		}
		if (StringUtil.isNotNull(isFollowPerson)
				&& !"false".equals(isFollowPerson)) {
			fdModelName = fansModelName;// 被关注对象为人时，关注对象和被关注对象的模型相同

			updatePersonModel(fdPersonId, fdModelName);
			updatePersonModel(UserUtil.getUser().getFdId(), fdModelName);
		}
		// 关注、粉丝数必须要有初始值，这里没有使用isnull的hql函数
		if (StringUtil.isNotNull(fdModelName)
				&& StringUtil.isNotNull(fansModelName)) {
			// 被关注者——>粉丝数增减
			String simpleName = ModelUtil.getModelTableName(fdModelName);
			StringBuffer hql = new StringBuffer();
			hql.append("update ").append(fdModelName).append(" " + simpleName)
					.append(" set " + simpleName + ".fdFansNum = " + simpleName
							+ ".fdFansNum + ")
					.append(margin)
					.append(" where " + simpleName + ".fdId=:fdId");
			Session session = getBaseDao().getHibernateSession();
			session.createQuery(hql.toString()).setParameter("fdId", fdPersonId)
					.executeUpdate();

			// 关注者——>关注数增减
			String fansSimpleName = ModelUtil.getModelTableName(fansModelName);
			StringBuffer hql2 = new StringBuffer();
			hql2.append("update ").append(fansModelName)
					.append(" " + fansSimpleName)
					.append(" set " + fansSimpleName + ".fdAttentionNum = "
							+ fansSimpleName + ".fdAttentionNum + ")
					.append(margin)
					.append(" where " + fansSimpleName + ".fdId=:fdId");
			Session session2 = getBaseDao().getHibernateSession();
			session2.createQuery(hql2.toString())
					.setParameter("fdId", UserUtil.getUser().getFdId())
					.executeUpdate();
		}
	}

	/**
	 * 如果是人员表，不存在记录，则插入一条新纪录
	 * 
	 * @param fdId
	 * @param fdModelName
	 * @throws Exception
	 */
	public void updatePersonModel(String fdId, String fdModelName)
			throws Exception {
		String modelTableName = ModelUtil.getModelTableName(fdModelName);
		if (StringUtil.isNotNull(fdId)) {
			SysOrgPerson person = (SysOrgPerson) this.findByPrimaryKey(fdId,
					SysOrgPerson.class, true);
			if (person == null) {
				return;
			}
		}
		String personSql = " from " + fdModelName + " " + modelTableName
				+ " where " + modelTableName + ".fdId =:fdId";
		IBaseDao baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
		List<IBaseModel> modelList = baseDao.getHibernateSession()
				.createQuery(personSql).setParameter("fdId", fdId).list();
		if (modelList.size() <= 0) {
			String sql = "";
			Boolean contain = false;
			SysDictModel dictModel =
					SysDataDict.getInstance().getModel(fdModelName);
			List<?> propertyList = dictModel.getPropertyList();
			String tableName = dictModel.getTable();
			for (int n = 0; n < propertyList.size(); n++) {
				SysDictCommonProperty property =
						(SysDictCommonProperty) propertyList.get(n);
				if (StringUtil.isNotNull(property.getName())
						&& "fdLastModifiedTime".equals(property.getName())) {
					contain = true;
					break;
				}
			}
			NativeQuery nativeQuery;
			if (contain) {
				sql = "insert into " + tableName
						+ " (fd_id, fd_attention_nums, fd_fans_nums,fd_last_modified_time) values(?, ?, ?, ?)";
				nativeQuery = getBaseDao().getHibernateSession().createNativeQuery(sql);
				nativeQuery.setParameter(0, fdId);
				nativeQuery.setParameter(1, 0);
				nativeQuery.setParameter(2, 0);
				nativeQuery.setParameter(3, new Date());
				nativeQuery.addSynchronizedQuerySpace(tableName);
				nativeQuery.executeUpdate();
			} else {
				sql = "insert into " + tableName
						+ " (fd_id, fd_attention_nums, fd_fans_nums) values(?, ?, ?)";
				nativeQuery = getBaseDao().getHibernateSession().createNativeQuery(sql);
				nativeQuery.setParameter(0, fdId);
				nativeQuery.setParameter(1, 0);
				nativeQuery.setParameter(2, 0);
				nativeQuery.addSynchronizedQuerySpace(tableName);
				nativeQuery.executeUpdate();
			}
		}
	}

	/**
	 * 关注或取消关注
	 * 
	 * @param requestInfo
	 * @return
	 * @throws Exception
	 */
	@Override
	public String updateFollow(HttpServletRequest requestInfo)
			throws Exception {
		String fdPersonId = requestInfo.getParameter("fdPersonId");// 被关注者
		String attentModelName = requestInfo.getParameter("attentModelName");// 被关注者模型
		String isFollowed = requestInfo.getParameter("isFollowed");
		String isFollowPerson = requestInfo.getParameter("isFollowPerson");// 关注对象是否是人
		Integer fdUserType = SysFansConstant.RELATION_USER_TYPE_PERSON;// 默认关注对象为人
		String currentId = UserUtil.getUser().getFdId();

		if (StringUtil.isNotNull(isFollowPerson)
				&& "false".equals(isFollowPerson)) {
			fdUserType = SysFansConstant.RELATION_USER_TYPE_OTHER;
		}

		if (StringUtil.isNotNull(fdPersonId)
				&& StringUtil.isNotNull(attentModelName)
				&& StringUtil.isNotNull(isFollowed)) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setParameter("fdId",fdPersonId);
			hqlInfo.setWhereBlock(" fdId =:fdId");
			hqlInfo.setModelName(SysOrgPerson.class.getName());
			List result =sysOrgPersonService.findList(hqlInfo);
			if(CollectionUtils.isEmpty(result)){
				return "fail";
			}

			Integer relationType = this.getRelation(currentId, fdPersonId);
			if (SysFansConstant.UNFOLLOWED.equals(isFollowed)) {// 关注
				if (SysFansConstant.FANS_RELA_ATT.equals(relationType)
						|| SysFansConstant.FANS_RELA_EACH_OTHER
								.equals(relationType)) {
					// 已经关注过，不能重复关注
					throw new KmssException(new KmssMessage(
							"sys-fans:sysFansMain.error.repeat"));
				}

				addFans(fdPersonId, attentModelName, fdUserType);
				updateAttenFanNum(requestInfo, 1);
			} else {// 取消关注
				if (!SysFansConstant.FANS_RELA_ATT.equals(relationType)
						&& !SysFansConstant.FANS_RELA_EACH_OTHER
								.equals(relationType)) {
					// 没有关注过，不能取消关注
					throw new KmssException(new KmssMessage(
							"sys-fans:sysFansMain.error.unfollow"));
				}

				// 记录日志
				if (UserOperHelper.allowLogOper("addFollow", getModelName())) {
					UserOperHelper.setEventType(ResourceUtil
							.getString("sys-fans:sysFansMain.delFollow"));
				}

				if (fdUserType.equals(SysFansConstant.RELATION_USER_TYPE_OTHER)) {
					Boolean fdCanUnFollow = canUnFollow(fdPersonId);
					if (!fdCanUnFollow) {
						return "canNotCancel";
					}
				}

				deleteAtten(fdPersonId);

				// 更新关注类型
				if (fdUserType.equals(SysFansConstant.RELATION_USER_TYPE_PERSON)) {
					String fansRecordId = getPersonFollow(fdPersonId);
					if (StringUtil.isNotNull(fansRecordId)) {
						String hql =
								"update SysFansMain set fdRelationType =:type where fdId =:fdId";
						Query query = getBaseDao().getHibernateSession()
								.createQuery(hql);
						query.setParameter("type",
								SysFansConstant.RELA_TYPE_FAN);
						query.setParameter("fdId", fansRecordId);
						query.executeUpdate();

						// 记录更新双向关注日志
						if (UserOperHelper.allowLogOper("addFollow",
								getModelName())) {
							UserOperContentHelper.putUpdate(fansRecordId)
									.putSimple("fdRelationType", "",
											SysFansConstant.RELA_TYPE_FAN);
						}
					}
				}
				updateAttenFanNum(requestInfo, -1);
			}
			return "success";
		}
		return "fail";
	}

	/**
	 * 能否取消关注该帐号
	 * 
	 * @param fdPersonId
	 * @return
	 * @throws Exception
	 */
	public Boolean canUnFollow(String fdPersonId) throws Exception {
		HQLInfo hql = new HQLInfo();
		hql.setSelectBlock("fdCanUnfollow");
		hql.setWhereBlock("fdUserId=:fdUserId and fdFansId=:fdFansId");
		hql.setParameter("fdUserId", fdPersonId);
		hql.setParameter("fdFansId", UserUtil.getUser().getFdId());
		Object obj = findFirstOne(hql);
		Boolean fdCanUnFollow = true;
		if (obj != null) {
			fdCanUnFollow = (Boolean)obj;
		}

		return fdCanUnFollow;
	}

	/**
	 * 
	 * @param fdUserId
	 * @throws Exception
	 */
	public void deleteAtten(String fdUserId) throws Exception {
		HQLInfo hql = new HQLInfo();
		hql.setSelectBlock("fdId");
		hql.setWhereBlock("fdUserId=:fdUserId and fdFansId=:fdFansId");
		hql.setParameter("fdUserId", fdUserId);
		hql.setParameter("fdFansId", UserUtil.getUser().getFdId());
		Object obj = findFirstOne(hql);
		if (obj != null) {
			delete(obj.toString());
		}
	}

	/**
	 * 用户关系
	 * 
	 * @param orgId1
	 * @param orgId2
	 * @param fdModelName
	 * @return 返回0表示二者没关系，返回1表示orgId1单方面关注orgId2，
	 *         返回2表示表示orgId2单方面关注orgId1，返回3表示orgId1和orgId2互相关注
	 * @throws Exception
	 */
	@Override
	public Integer getRelation(String orgId1, String orgId2) throws Exception {
		if (StringUtil.isNull(orgId1) || StringUtil.isNull(orgId2)
				|| orgId1.equals(orgId2)) {
			throw new IllegalArgumentException();
		}
		Integer rtnFlg = SysFansConstant.FANS_RELA_NO;

		Session session = this.getBaseDao().getHibernateSession();
		String sql = " select main1.fd_user_id from sys_fans_main  main1"
				+ " where main1.fd_user_id=:fdUserId1 and main1.fd_fans_id=:fdFansId1"
				+ " union all "
				+ " select main2.fd_user_id from sys_fans_main  main2"
				+ " where main2.fd_user_id=:fdUserId2 and main2.fd_fans_id=:fdFansId2";
		@SuppressWarnings("unchecked")
		List<String> result =
				session.createNativeQuery(sql).setParameter("fdUserId1", orgId1)
						.setParameter("fdFansId1", orgId2)
						.setParameter("fdUserId2", orgId2)
						.setParameter("fdFansId2", orgId1).list();
		if (ArrayUtil.isEmpty(result)) {
			return rtnFlg;
		} else {
			if (result.size() >= 2) {
                rtnFlg = SysFansConstant.FANS_RELA_EACH_OTHER;
            } else if (result.size() == 1) {
				String rela = result.get(0);
				if (orgId2.equals(rela)) {
                    rtnFlg = SysFansConstant.FANS_RELA_ATT;
                } else {
                    rtnFlg = SysFansConstant.FANS_RELA_FAN;
                }
			}
		}
		return rtnFlg;
	}

	@Override
	public JSONArray getRelationByIds(String personIdsStr) throws Exception {
		String[] personIds = personIdsStr.split(",");
		StringBuffer sql = new StringBuffer();
		sql = sql
				.append("select fans_main.fd_user_id,fans_main.fd_relation_type from sys_fans_main ")
				.append(" fans_main where fans_main.fd_fans_id=:fdId and fans_main.fd_user_id in(:fdUserIds)");
		List list = this.getBaseDao().getHibernateSession().createNativeQuery(sql.toString()).setParameter("fdId", UserUtil.getUser().getFdId()).setParameterList("fdUserIds",
						ArrayUtil.convertArrayToList(personIds))
				.list();
		
		JSONArray relaType = JSONArray.fromObject(list);
		return relaType;
	}

	@Override
	public Page findFollowPage(int pageno, int rowsize, String orderby,
							   String id, String type, String fansModelName) throws Exception {
		Page page = new Page();
		page.setRowsize(rowsize);
		page.setPageno(pageno);
		page.setOrderby(orderby);
		page.setTotalrows(getFollowTotal(id, type));
		page.excecute();
		setFollowList(id, type, page, fansModelName);
		return page;
	}

	// 获取关注、粉丝总数
	@Override
	public int getFollowTotal(String id, String type) throws Exception {
		StringBuffer sql = new StringBuffer("select ");
		if ("fans".equals(type)) {
			// 粉丝
			sql = sql.append("count(DISTINCT fans_main.fd_fans_id) from ")
					.append("sys_fans_main fans_main ")
					.append("where fans_main.fd_user_id=:fdId");
		} else if ("mobileAttention".equals(type)) {
			sql = sql.append("count(DISTINCT fans_main.fd_user_id) from ")
					.append("sys_fans_main fans_main ")
					.append("where fans_main.fd_fans_id=:fdId and fans_main.fd_user_type=1");
		} else {
			sql = sql.append("count(DISTINCT fans_main.fd_user_id) from ")
					.append("sys_fans_main fans_main ")
					.append("where fans_main.fd_fans_id=:fdId");
		}
		Integer size = Integer.valueOf(this.getBaseDao().getHibernateSession().createNativeQuery(sql.toString()).setParameter("fdId", id).uniqueResult().toString());
		return size.intValue();
	}

	// 获取关注、粉丝的当前页集合
	private void setFollowList(String id, String type, Page page,
			String fansModelName) throws Exception {
		StringBuffer sql = new StringBuffer();
		if ("fans".equals(type)) {
			// 粉丝
			sql = sql.append("select * from sys_fans_main ")
					.append(" fans_main where fans_main.fd_user_id=:fdId");
		} else if ("mobileAttention".equals(type)) {
			sql = sql.append("select * from sys_fans_main ").append(
					" fans_main where fans_main.fd_fans_id=:fdId and fans_main.fd_user_type=1");
		} else {
			sql = sql.append("select * from sys_fans_main ")
					.append(" fans_main where fans_main.fd_fans_id=:fdId");
		}
		NativeQuery query = this.getBaseDao().getHibernateSession()
				.createNativeQuery(sql.toString());
		query.setFirstResult(page.getStart());
		query.setMaxResults(page.getRowsize());
		@SuppressWarnings("unchecked")
		List<SysFansMain> list = query.addEntity("fans_main", SysFansMain.class)
				.setParameter("fdId", id).list();

		UserOperHelper.logFindAll(list, getModelName());

		page.setList(getFollowList(list, type, fansModelName));
	}

	private List<JSONObject> getFollowList(List<SysFansMain> list, String type,
			String fansModelName) throws Exception {
		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		for (SysFansMain fansMain : list) {
			// SysOrgPerson person = personInfo.getPerson();
			JSONObject json = new JSONObject();
			json.accumulate("fdId", fansMain.getFdId());
			String fdModelId = "";
			// 当是粉丝列表时，粉丝类型是人；当是关注列表时，关注类型是人和公共帐号
			if ("fans".equals(type)) {
				fdModelId = fansMain.getFdFansId();
				json.accumulate("fdUserType",
						SysFansConstant.RELATION_USER_TYPE_PERSON);
				json.accumulate("fdModelName", fansModelName);
				json.accumulate("fansModelName", fansModelName);
			} else {
				fdModelId = fansMain.getFdUserId();
				json.accumulate("fdUserType", fansMain.getFdUserType());
				json.accumulate("fdModelName", fansMain.getFdModelName());
				json.accumulate("fansModelName", fansModelName);
			}
			json.accumulate("fdModelId", fdModelId);

			jsonList.add(json);
		}
		return jsonList;
	}
}
