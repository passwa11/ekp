package com.landray.kmss.sys.organization.service.spring;

import java.util.Iterator;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgElementXmlOutService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.util.StringUtil;

public class SysOrgPersonToRTXService implements SysOrgConstant, ISysOrgElementXmlOutService {
	
	private ISysOrgElementService sysOrgElementService;

	public void setSysOrgElementService(ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}
	
	private ISysOrgPersonService sysOrgPersonService;

	public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}
	
	private static int maxRtxNo = 1000;
	
	// 只让一个线程访问，保证生成的RTX号是正确的
	@Override
	public synchronized String getXML() throws Exception {
		try {
			setMaxNo();
			StringBuilder xml = new StringBuilder();
			xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n")
				.append("<enterprise>\n")
				.append("<departments>");
			addAllDepts(xml);
			xml.append("\n</departments>")
				.append("\n</enterprise>");
			return xml.toString();
		} finally {
			resetMaxNo();
		}
	}
	
	private void addAllDepts(StringBuilder xml) throws Exception {
		List<?> orgs = getAllRootOrg();
		for (Iterator<?> it = orgs.iterator(); it.hasNext(); ) {
			SysOrgElement org = (SysOrgElement) it.next();
			addDepartment(xml, org);
		}
	}
	
	private void addDepartment(StringBuilder xml, SysOrgElement element) throws Exception {
		xml.append("\n<department");
		addAttribute(xml, "name", element.getFdName());
		addAttribute(xml, "describe", element.getFdMemo());
		xml.append(">");
		List<?> children = element.getFdChildren();
		for (Iterator<?> it = children.iterator(); it.hasNext(); ) {
			SysOrgElement elem = (SysOrgElement) it.next();
			if (elem.getFdIsAvailable() && 
					(elem.getFdIsAbandon() == null || !elem.getFdIsAbandon())) {
				if (elem.getFdOrgType() == 1 || elem.getFdOrgType() == 2) {
					addDepartment(xml, elem);
				}
				else if (elem.getFdOrgType() == 8) {
					addUser(xml, (SysOrgPerson) elem);
				}
			}
		}
		xml.append("\n</department>");
	}
	
	private void addUser(StringBuilder xml, SysOrgPerson person) throws Exception {
		boolean needUpdate = false;
		xml.append("\n<user");
		addAttribute(xml, "uid", person.getFdLoginName());
		addAttribute(xml, "name", person.getFdName());
		addAttribute(xml, "email", person.getFdEmail());
		addAttribute(xml, "mobile", person.getFdMobileNo());
		if (StringUtil.isNull(person.getFdRtxNo())) { // ---------- 为空需要新生成
			person.setFdRtxNo(newRtxNo());
			needUpdate = true;
		}
		addAttribute(xml, "rtxno", person.getFdRtxNo());
		addAttribute(xml, "phone", person.getFdWorkPhone());
		addAttribute(xml, "position", null); // 职务
		addAttribute(xml, "fax", null); //传真
		addAttribute(xml, "address", null); //地址
		addAttribute(xml, "postcode", null); //邮编
		addAttribute(xml, "country", null); //国家
		addAttribute(xml, "province", null); //省份
		addAttribute(xml, "city", null); //城市
		addAttribute(xml, "college", null); //?
		addAttribute(xml, "age", null); //年龄
		addAttribute(xml, "gender", null); //性别
		addAttribute(xml, "birthday", null); //生日
		addAttribute(xml, "bloodtype", null); //血型
		addAttribute(xml, "homepage", null); //个人主页
		addAttribute(xml, "memo", person.getFdMemo()); //血型
		addAttribute(xml, "type", null); //?
		
		xml.append("/>");
		
		if (needUpdate) {
			savePerson(person);
		}
	}
	
	private void savePerson(SysOrgElement person) throws Exception {
		sysOrgElementService.update(person);
	}
	
	private List<?> getAllRootOrg() throws Exception {
		List<?> orgs = sysOrgElementService.findList("sysOrgElement.fdOrgType = 1"
				+ " and sysOrgElement.hbmParent is null"
				+ " and sysOrgElement.fdIsAvailable = true"
				+ " and (sysOrgElement.fdIsAbandon = false or sysOrgElement.fdIsAbandon is null)", null);
		return orgs;
	}
	
	private void addAttribute(StringBuilder xml, String name, String value) {
		if (value == null) {
            value = "";
        }
		xml.append(" ").append(name).append("=\"").append(StringUtil.XMLEscape(value)).append("\"");
	}
	
	private void resetMaxNo() {
		maxRtxNo = 1000;
	}
	
	private String newRtxNo() throws Exception {
		maxRtxNo ++;
		String rts = String.valueOf(maxRtxNo);
		return rts;
	}
	
	private void setMaxNo() throws Exception {
		HQLInfo info = new HQLInfo();
		info.setSelectBlock("max(sysOrgPerson.fdRtxNo)");
		List<?> value = sysOrgPersonService.findValue(info);
		if (value != null && !value.isEmpty()) {
			String nb = (String) value.get(0);
			if (nb != null) {
				maxRtxNo = Integer.parseInt(nb);
			}
		}
	}

}
