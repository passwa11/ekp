package com.landray.kmss.km.signature.service.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.hibernate.query.Query;
import org.springframework.jdbc.support.JdbcUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLCombiner;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLWrapper;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.km.signature.model.KmSignatureMain;
import com.landray.kmss.km.signature.service.IKmSignatureMainService;
import com.landray.kmss.km.signature.service.IKmSignaturePasswordEncoder;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.lbpmservice.support.model.LbpmAuditNote;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 印章库业务接口实现
 * 
 * @author
 * @version 1.0 2013-09-23
 */
public class KmSignatureMainServiceImp extends BaseServiceImp implements
		IKmSignatureMainService {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(KmSignatureMainServiceImp.class);

	protected IKmSignaturePasswordEncoder kmSignaturePasswordEncoder;

	public void setKmSignaturePasswordEncoder(
			IKmSignaturePasswordEncoder kmSignaturePasswordEncoder) {
		this.kmSignaturePasswordEncoder = kmSignaturePasswordEncoder;
	}

	// IKmSignaturePasswordEncoder kmSignaturePasswordEncoder =
	// (IKmSignaturePasswordEncoder) SpringBeanUtil
	// .getBean("kmSignaturePasswordEncoder");

	protected ISysAttMainCoreInnerService sysAttMainCoreInnerService = null;

	public void setSysAttMainCoreInnerService(
			ISysAttMainCoreInnerService sysAttMainCoreInnerService) {
		this.sysAttMainCoreInnerService = sysAttMainCoreInnerService;
	}

	@Override
	public String add(IExtendForm form, RequestContext requestContext)
			throws Exception {
		UserOperHelper.logAdd(getModelName());
		IBaseModel model = convertFormToModel(form, null, requestContext);
		if (model == null) {
            throw new NoRecordException();
        }
//		SysDocBaseInfo sysDocBaseInfo = (SysDocBaseInfo) model;
		KmSignatureMain kmSignatureMain = (KmSignatureMain) model;
		// 设置创建者
		kmSignatureMain.setDocCreator(UserUtil.getUser());
		kmSignatureMain.setDocCreateTime(new Date());
		kmSignatureMain.setDocCreatorClientIp(requestContext.getRemoteAddr());
		// 设置修改人
		// sysDocBaseInfo.setDocAlteror(UserUtil.getUser());
		// sysDocBaseInfo.setDocAlterTime(new Date());
		// sysDocBaseInfo.setDocAlterClientIp(requestContext.getRemoteAddr());
		//当前人签章类型为个人签章，并且为默认个人签章设置时则：设置 则当前人 其他的个人签名自动置为为否，每个人仅一个默认个人签名
        if ((kmSignatureMain.getFdDocType()==1) && 
    		    kmSignatureMain.getFdIsDefault()){ 
        	String userName = kmSignatureMain.getFdUserName();
        	if(StringUtil.isNull(userName)){
        		userName = UserUtil.getKMSSUser().getUserName();
        	}
        	updateDefault(kmSignatureMain,false);
       }
	   return add(model);
	}

	@Override
	public void update(IExtendForm form, RequestContext requestContext)
			throws Exception {
		UserOperHelper.logUpdate(getModelName());
		IBaseModel model = convertFormToModel(form, null, requestContext);
		if (model == null) {
            throw new NoRecordException();
        }
//		SysDocBaseInfo sysDocBaseInfo = (SysDocBaseInfo) model;
		KmSignatureMain kmSignatureMain = (KmSignatureMain) model;
		kmSignatureMain.setDocAlteror(UserUtil.getUser());
		kmSignatureMain.setDocAlterTime(new Date());
		kmSignatureMain.setDocAlterClientIp(requestContext.getRemoteAddr());
		if ((kmSignatureMain.getFdDocType()==1) && 
    		    kmSignatureMain.getFdIsDefault()){ 
			String userName = kmSignatureMain.getFdUserName();
			updateDefault(kmSignatureMain,false);
       }
		super.update(kmSignatureMain);
	}

	/**
	 * 根据用户ID获取用户的签名图片地址
	 * 
	 * @param personId
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getPicPath(LbpmAuditNote lbpmAuditNote) throws Exception {
		String picPathsQZ = "";
		// 获取流程节点fdId
		String fdNodeId = lbpmAuditNote.getFdId();
		if (StringUtil.isNotNull(fdNodeId)) {
			// 签章fdkey
			String fdNodeId_qz = fdNodeId + "_qz";
			// 复制流程的时候，会把附件信息都复制一份，但是fdModelId不会复制，故这里用fdModelId过滤那些复制的附件 #47125
			String hqlQZ = "from com.landray.kmss.sys.attachment.model.SysAttMain sysAttMain where sysAttMain.fdKey =:fdNodeId_qz and sysAttMain.fdModelId != null";
			Query queryQZ = this.getBaseDao().getHibernateSession()
					.createQuery(hqlQZ);
			queryQZ.setParameter("fdNodeId_qz", fdNodeId_qz);
			List<SysAttMain> listQZ = queryQZ.list();
			if (!listQZ.isEmpty()) {
				for (int i = 0; i < listQZ.size(); i++) {
					SysAttMain sysAttMain = (SysAttMain) listQZ.get(i);
					if (sysAttMain != null) {
						if ("".equals(picPathsQZ)) {
							picPathsQZ = "/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId="
									+ sysAttMain.getFdId();
						} else {
							picPathsQZ += ";"
									+ "/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId="
									+ sysAttMain.getFdId();
						}
					}
				}
			}
		}
		return picPathsQZ;
	}

	@SuppressWarnings("unused")
	private String getAttachmentLink(String mainId) throws Exception {
		return "/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId="
				+ mainId;
	}

	@Override
	public List showSig(String userId) throws Exception {
		List rtnList = new ArrayList();
		String hql = "select kmSignatureMain.fdId,kmSignatureMain.fdMarkName "
				+ "from com.landray.kmss.km.signature.model.KmSignatureMain kmSignatureMain "
				+ "left join kmSignatureMain.fdUsers fdUsers "
				+ " where (kmSignatureMain.fdIsAvailable = :fdIsAvailable or kmSignatureMain.fdIsAvailable is null) and (fdUsers.fdId in :orgIds or kmSignatureMain.docCreator.fdId =:creatorId )";
		Query query = this.getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameter("fdIsAvailable",Boolean.TRUE);
		query.setParameterList("orgIds", UserUtil.getKMSSUser()
				.getUserAuthInfo().getAuthOrgIds());
		query.setParameter("creatorId", UserUtil.getKMSSUser().getUserId());				
		
		List<KmSignatureMain> list = query.list();
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				KmSignatureMain kmSignatureMain = (KmSignatureMain) list.get(i);
				Object[] object = new Object[2];
				object[0] = kmSignatureMain.getFdId();
				object[1] = kmSignatureMain.getFdMarkName();
				rtnList.add(object);
			}
		}
		return rtnList;
	}

	@SuppressWarnings("unchecked")
	public List getDataList(RequestContext xmlContext) throws Exception {
		List rtnList = new ArrayList();
		String hql = "select kmSignatureMain.fdId,kmSignatureMain.fdMarkName "
				+ "from com.landray.kmss.km.signature.model.KmSignatureMain kmSignatureMain "
				+ "left join kmSignatureMain.fdUsers fdUsers "
				+ " where (kmSignatureMain.fdIsAvailable = :fdIsAvailable or kmSignatureMain.fdIsAvailable is null) and (fdUsers.fdId in :orgIds or kmSignatureMain.docCreator.fdId =:creatorId )";
		Query query = this.getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameter("fdIsAvailable",Boolean.TRUE);
		query.setParameterList("orgIds", UserUtil.getKMSSUser()
				.getUserAuthInfo().getAuthOrgIds());
		query.setParameter("creatorId", UserUtil.getKMSSUser().getUserId());
				
		List<KmSignatureMain> list = query.list();
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				KmSignatureMain kmSignatureMain = (KmSignatureMain) list.get(i);
				Object[] object = new Object[2];
				object[0] = kmSignatureMain.getFdId();
				object[1] = kmSignatureMain.getFdMarkName();
				List<SysAttMain> listAtt = ((ISysAttMainCoreInnerService) SpringBeanUtil
						.getBean("sysAttMainService")).findByModelKey(
						"com.landray.kmss.km.signature.model.KmSignatureMain",
						kmSignatureMain.getFdId(), "sigPic");
				object[2] = listAtt.get(0).getFdId();
				rtnList.add(object);
			}
		}
		return rtnList;
	}

	/**
	 * 重置密码时的保存密码操作，调用md5算法加密
	 */
	@Override
	public void savePassword(String id, String newPassword,
							 RequestContext requestContext) throws Exception {
		KmSignatureMain kmSignatureMain = (KmSignatureMain) findByPrimaryKey(id);
		if (StringUtil.isNotNull(newPassword)) {
			newPassword = kmSignaturePasswordEncoder.encodePassword(newPassword);// md5加密算法
		}
		if (UserOperHelper.allowLogOper("savePassword", getModelName())) {
			UserOperHelper.setEventType("重置密码");
			UserOperContentHelper.putUpdate(kmSignatureMain).putSimple(
					"fdPassword", kmSignatureMain.getFdPassword(), newPassword);
		}
		kmSignatureMain.setFdPassword(newPassword);
		update(kmSignatureMain);
	}

	// 经过筛选器筛选后的文档hql（已权限处理）
	@Override
	public HQLWrapper getDocHql(String whereBlock, String __joinBlock,
								HttpServletRequest request) throws Exception {

		HQLInfo hql = new HQLInfo();
		if (hql.getCheckParam(SysAuthConstant.CheckType.AllCheck) == null) {
			hql.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.DEFAULT);
		}
		hql.setSelectBlock("kmSignatureMain.fdId");
		hql.setFromBlock(__joinBlock);
		hql.setModelName("com.landray.kmss.km.signature.model.kmSignatureMain");
		hql.setWhereBlock(whereBlock);
		HQLCombiner hqlCombiner = (HQLCombiner) SpringBeanUtil
				.getBean("kmssAuthHQLBuilder");
		HQLWrapper hqlWrapper = hqlCombiner.buildQueryHQL(hql);
		return hqlWrapper;
	}

	@Override
	public String getCardPicIdsBySignatureId(String fdId) throws Exception {
		try {
			List<SysAttMain> attMainList = new ArrayList<SysAttMain>();
			attMainList = this.sysAttMainCoreInnerService.findByModelKey(
					"com.landray.kmss.km.signature.model.KmSignatureMain",
					fdId, "sigPic");

			if (!ArrayUtil.isEmpty(attMainList) && attMainList.size() >= 1) {
				return attMainList.get(0).getFdId();
			}
			return null;
		} catch (Exception e) {
			logger.error("获取资产卡片图片失败", e);
			throw e;
		}
	}

	@Override
	public void updateInvalidated(String id, RequestContext requestContext) {
		try {
			KmSignatureMain kmSignatureMain = (KmSignatureMain) this
					.findByPrimaryKey(id);
			if (kmSignatureMain != null) {
				if (kmSignatureMain.getFdIsAvailable() == null
						|| kmSignatureMain.getFdIsAvailable() == true) {
					if (UserOperHelper.allowLogOper("updateInvalidated",
							getModelName())) {
						UserOperHelper.setEventType("置为无效");
						UserOperContentHelper.putUpdate(kmSignatureMain)
								.putSimple("fdIsAvailable",
										kmSignatureMain.getFdIsAvailable(),
										new Boolean(false));
					}
					kmSignatureMain.setFdIsAvailable(new Boolean(false));
					update(kmSignatureMain);
					flushHibernateSession();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateInvalidated(String[] ids, RequestContext requestContext)
			throws Exception {
		for (int i = 0; i < ids.length; i++) {
			this.updateInvalidated(ids[i], requestContext);
		}
	}
	
	public void updateValidated(String id, RequestContext requestContext) {
		try {
			KmSignatureMain kmSignatureMain = (KmSignatureMain) this
					.findByPrimaryKey(id);
			if (kmSignatureMain != null) {
				if (kmSignatureMain.getFdIsAvailable() == null
						|| kmSignatureMain.getFdIsAvailable() == false) {
					if (UserOperHelper.allowLogOper("updateValidated",
							getModelName())) {
						UserOperHelper.setEventType("置为有效");
						UserOperContentHelper.putUpdate(kmSignatureMain)
								.putSimple("fdIsAvailable",
										kmSignatureMain.getFdIsAvailable(),
										new Boolean(true));
					}
					kmSignatureMain.setFdIsAvailable(new Boolean(true));
					update(kmSignatureMain);
					flushHibernateSession();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateValidated(String[] ids, RequestContext requestContext)
			throws Exception {
		for (int i = 0; i < ids.length; i++) {
			this.updateValidated(ids[i], requestContext);
		}
	}
	
	/**
	 * 设置默认签章：对当前人 其他的个人签名自动置为为否，每个人仅一个默认个人签名
	 * @param currentSignatureId 当前签章对象
	 * @param userName   当前使用者
	 * @param needUpdateSignature 是否需要更新当前签章
	 * @throws Exception
	 */
	@Override
	public void setDefaultSignature(String currentSignatureId, String userName, Boolean needUpdateSignature) throws Exception {
		if (StringUtil.isNull(currentSignatureId)){
			logger.error("设置默认签章发生报错:签章记录不存在！");
			throw new Exception("设置默认签章发生报错:签章记录不存在！");
		}
		//将当前人的其他个人签章设置为非默认
		
		DataSource dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
		Connection conn = null;
		PreparedStatement psDefaultNo = null;
		PreparedStatement psDefaultYes = null;
		try {
			String sql = " update km_signature_main set fd_is_default=? where fd_user_name =? and fd_doc_type='1' and fd_id !=? ";
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			psDefaultNo = conn.prepareStatement(sql);
			psDefaultNo.setBoolean(1,false);
			psDefaultNo.setString(2,userName );
			psDefaultNo.setString(3,currentSignatureId );
			psDefaultNo.executeUpdate();
			conn.commit();
			
			//是否需要设置当前签章对象为非默认
			if (needUpdateSignature){
				sql = " update km_signature_main set fd_is_default=? where fd_id =? ";
				psDefaultYes = conn.prepareStatement(sql);
				psDefaultYes.setBoolean(1,true);
				psDefaultYes.setString(2,currentSignatureId );
				psDefaultYes.executeUpdate();
				conn.commit();
			}		
		} catch (Exception ex) {
			conn.rollback();
			logger.error("设置默认签章发生报错:"+ex);
			throw ex;
		} finally {
			JdbcUtils.closeStatement(psDefaultNo);
			JdbcUtils.closeStatement(psDefaultYes);
			JdbcUtils.closeConnection(conn);
		}
	}
	
	@Override
	public void updateDefault(KmSignatureMain kmSignatureMain, Boolean needUpdateSignature) throws Exception {
		//将当前人的其他个人签章设置为非默认
		try {
			List fdUsers = kmSignatureMain.getFdUsers();
			if(fdUsers.size()>0){
				SysOrgElement org = (SysOrgElement)fdUsers.get(0);
				List authOrgIds = ((ISysOrgCoreService) SpringBeanUtil
						.getBean("sysOrgCoreService")).getOrgsUserAuthInfo(org).getAuthOrgIds();
				String hql = "select kmSignatureMain2.fdId from com.landray.kmss.km.signature.model.KmSignatureMain kmSignatureMain2 left join kmSignatureMain2.fdUsers fdUsers where "
						+ HQLUtil.buildLogicIN("fdUsers.fdId", authOrgIds);
				Query query = this.getBaseDao().getHibernateSession().createQuery(hql);
				List l = query.list();
				if (l.size() > 0) {
					String hql2 = "update KmSignatureMain kmSignatureMain1 set kmSignatureMain1.fdIsDefault =:fdIsDefault where "
							+ HQLUtil.buildLogicIN("kmSignatureMain1.fdId", l)
							+ " and kmSignatureMain1.fdDocType='1' and kmSignatureMain1.fdId !=:fdCurId";
					Query query2 = this.getBaseDao().getHibernateSession().createQuery(hql2);
					query2.setParameter("fdIsDefault", Boolean.FALSE);
					query2.setParameter("fdCurId", kmSignatureMain.getFdId());
					query2.executeUpdate();
				}
			
				//是否需要设置当前签章对象为非默认
				if (needUpdateSignature){
					String sql = " update KmSignatureMain kmSignatureMain set kmSignatureMain.fdIsDefault =:fdIsDefaultx where kmSignatureMain.fdId =:fdCurIdx";
					Query queryx = this.getBaseDao().getHibernateSession().createQuery(sql);
					queryx.setParameter("fdIsDefaultx", Boolean.TRUE);
					queryx.setParameter("fdCurIdx", kmSignatureMain.getFdId());
					queryx.executeUpdate();
					if (UserOperHelper.allowLogOper("updateDefault",
							getModelName())) {
						UserOperHelper.setEventType("置为默认");
						UserOperContentHelper.putUpdate(kmSignatureMain)
								.putSimple("fdIsDefault",
										kmSignatureMain.getFdIsDefault(),
										Boolean.TRUE);
					}
				}
			}
		} catch (Exception ex) {
			logger.error("设置默认签章发生报错:"+ex);
			throw ex;
		}
	}
	

	/**
	 * 根据用户获得免密签名对象
	 * @param count
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getAutoSignature(int count) throws Exception{
		List rtnList = new ArrayList();
		String hql = "select kmSignatureMain "
				+ "from com.landray.kmss.km.signature.model.KmSignatureMain kmSignatureMain "
				+ "left join kmSignatureMain.fdUsers fdUsers "
				+ " where kmSignatureMain.fdIsDefault =:fdIsDefault and kmSignatureMain.fdIsFreeSign =:fdIsFreeSign and (kmSignatureMain.fdIsAvailable = :fdIsAvailable or kmSignatureMain.fdIsAvailable is null) and (fdUsers.fdId in (:orgIds))";//  or kmSignatureMain.docCreator.fdId =:creatorId
		Query query = this.getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameter("fdIsAvailable",Boolean.TRUE);
		query.setParameter("fdIsDefault",true);
		query.setParameter("fdIsFreeSign",true);
		query.setParameterList("orgIds", UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds());
		//query.setParameter("creatorId",UserUtil.getKMSSUser().getUserId());
		query.setMaxResults(count);
		List<KmSignatureMain> list = query.list();
		if (!list.isEmpty()) {
			for (KmSignatureMain kmSignatureMain: list) {
				Object[] object = new Object[3];
				object[0] = kmSignatureMain.getFdId();
				object[1] = kmSignatureMain.getFdMarkName();
				List<SysAttMain> listAtt = ((ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService")).findByModelKey(
						"com.landray.kmss.km.signature.model.KmSignatureMain",kmSignatureMain.getFdId(), "sigPic");
				if (listAtt!=null && listAtt.size()>0){
					object[2] = listAtt.get(0).getFdId();
				}else{
					object[2] = "";
				}
				rtnList.add(object);
			}
		}
		return rtnList;
	}
}
