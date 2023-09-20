package com.landray.kmss.km.signature.service.spring;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.km.signature.service.IKmSignatureMainService;
import com.landray.kmss.sys.attachment.jg.AbstractSysAttachmentJGFunction;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

import DBstep.iMsgServer2000;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

public class SysAttJGLoadMarkListFunction extends
		AbstractSysAttachmentJGFunction {

	// 加载文件
	@Override
    @SuppressWarnings("unchecked")
	public void execute(RequestContext request, iMsgServer2000 MsgObj)
			throws Exception {
		String jgUserId = MsgObj.GetMsgByName("jgUserId");
		MsgObj.MsgTextClear(); // 清除文本信息
		String msgError = ResourceUtil.getString(
				"signature.getsignaturelistError",
				"km-signature");
		try {
			StringBuffer wherebuffer = new StringBuffer(" ('' ");
//			//SysOrgPerson orgPerson = UserUtil.getUser(jgUserId);
			//wherebuffer.append(",'" + orgPerson.getFdId() + "'");
			/*if (orgPerson.getFdParent() != null) {
				wherebuffer.append(",'" + orgPerson.getFdParent().getFdId()
						+ "'");
			}*/
			/*List posts = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();
			for (int i = 0; i < posts.size(); i++) {
				wherebuffer.append(",'" + posts.get(i) + "'");
			}*/
			
			/* *********** */
			//拼接当前用户id
			wherebuffer.append(",'" + jgUserId + "'");
			//获取当前用户
			SysOrgPerson orgPerson = UserUtil.getUser(jgUserId);
			//获取取当前用户所有上级部门
			List<SysOrgElement> personParent = orgPerson.getAllParent(false);
			//遍历拼接上级部门id
			for (int i = 0; i < personParent.size(); i++) {
				wherebuffer.append(",'" + personParent.get(i).getFdId() + "'");
			}
			// 获取当前用户所有岗位
			List<SysOrgElement> posts = orgPerson.getFdPosts();
			// 遍历拼接岗位id
			for (int j = 0; j < posts.size(); j++) {
				wherebuffer.append(",'" + posts.get(j).getFdId() + "'");
			}
			/* *********** */
			

			wherebuffer.append(" )");
			String sql = "select s.fd_mark_name from km_signature_main s," +
					"km_signature_users su, sys_att_main st" +
					" where s.fd_id=su.fd_signature_id" +
					" and st.fd_model_id=s.fd_id and st.fd_content_type!='image/png'" +
					" and (su.fd_org_id in " + wherebuffer.toString()
					+ ") and (s.fd_is_available = :available or s.fd_is_available is null) and ( s.fd_is_free_sign = :isFree or s.fd_is_free_sign is null )"
					+ " and s.fd_doc_type = :docType ";
			String mMarkList = "";
			IKmSignatureMainService signatureService = (IKmSignatureMainService) SpringBeanUtil
					.getBean("kmSignatureMainService");
			Query query = signatureService.getBaseDao()
					.getHibernateSession().createSQLQuery(sql);
			query.setBoolean("available", Boolean.TRUE);
			query.setBoolean("isFree", Boolean.FALSE);
			query.setLong("docType", 2);
			List<String> list = query.list();
			for (int i = 0; i < list.size(); i++) {
				mMarkList += list.get(i).toString() + "\r\n";
			}

			MsgObj.SetMsgByName("MARKLIST", mMarkList); // 显示签章列表
			MsgObj.MsgError(""); // 清除错误信息
		} catch (Exception e) {
			e.printStackTrace();
			MsgObj.MsgError(msgError);
		}
	}

}
