/**
 * 创建日期 2010-03-01
 * 
 * @author 吴兵
 */

package com.landray.kmss.third.ldap.oms.in;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.third.ldap.LdapEntries;
import com.landray.kmss.third.ldap.LdapEntry;
import com.landray.kmss.third.ldap.LdapService;
import com.landray.kmss.util.SpringBeanUtil;

public class LdapSynchroToolkitImp implements ISynchroToolkit {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(LdapSynchroToolkitImp.class);

	private ISysOrgElementService sysOrgElementService;

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	public LdapSynchroToolkitImp() {
	}

	private List getAllRecords() throws Exception {
		List records = new ArrayList();
		LdapService service = new LdapService();
		try {
			service.connect();
			LdapEntries depts = service.getEntries("dept", null);
			if (depts != null) {
				for (int i = 0; i < depts.size(); i++) {
					LdapEntry entry = depts.get(i);
					records.add(new String[] { entry.getStringProperty("unid"),
							entry.getStringProperty("before") });
				}
			}
			LdapEntries persons = service.getEntries("person", null);
			if (persons != null) {
				for (int i = 0; i < persons.size(); i++) {
					LdapEntry entry = persons.get(i);
					records.add(new String[] { entry.getStringProperty("unid"),
							entry.getStringProperty("before") });
				}
			}

			LdapEntries posts = service.getEntries("post", null);
			if (posts != null) {
				for (int i = 0; i < posts.size(); i++) {
					LdapEntry entry = posts.get(i);
					records.add(new String[] { entry.getStringProperty("unid"),
							entry.getStringProperty("before") });
				}
			}
			LdapEntries groups = service.getEntries("group", null);
			if (groups != null) {
				for (int i = 0; i < groups.size(); i++) {
					LdapEntry entry = groups.get(i);
					records.add(new String[] { entry.getStringProperty("unid"),
							entry.getStringProperty("before") });
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			service.close();
		}
		return records;
	}

	@Override
    public void updateRelationLdap2IT() throws Exception {
		List all = getAllRecords();
		StringBuffer sb = new StringBuffer();
		int n = 0;
		IBaseService bs = (IBaseService) SpringBeanUtil
				.getBean("KmssBaseService");
		for (int i = 0; i < all.size(); i++) {
			String[] kv = (String[]) all.get(i);
			// String before = LdapSynchroInProviderImp.class.getName() + kv[1];
			// List result = sysOrgElementService.findList("fdImportInfo='"
			// + before + "'", null);
			// if (!result.isEmpty()) {
			// SysOrgElement element = (SysOrgElement) result.get(0);
			// String fdImportInfo = LdapSynchroInProviderImp.class.getName()
			// + kv[0];
			// if (logger.isDebugEnabled()) {
			// n++;
			// sb.append("fdName::" + element.getFdName() + ",fdId::"
			// + element.getFdId());
			// sb.append("\n");
			// sb.append("fdImportInfo::" + element.getFdImportInfo()
			// + "-->" + fdImportInfo);
			// sb.append("\n");
			// }
			// element.setFdImportInfo(fdImportInfo);
			// bs.update(element);
			// }

			sb.append("unid::" + kv[0] + ",before::" + kv[1]);
			sb.append("\n");
		}
		if (logger.isDebugEnabled()) {
			logger.debug(sb.toString());
			logger.debug("\n 一共转换" + n + "条记录");
		}
	}
}
