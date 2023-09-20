package com.landray.kmss.elec.device.client;

import java.util.List;

/**
*@author yucf
*@date  2019年7月11日
*@Description             渠道合同物理签暑   
* 注： 此类后续作废，换成 ElecChannelApplyUsePhysicalSealInfo
*/
@Deprecated
public class ElecChannelContractPhysicalSignInfo implements IElecChannelRequestMessage {

	private static final long serialVersionUID = 1L;
	
	//申请编号
	private String applyCode;
	
	//单位编号
	private String companyNo;
	
	//单位名称
	private String companyName;
	
	//部门名称
	private String deptName;
	
	//申请人姓名
	private String applyUserName;
	
	//申请人ID
	private String applyUserId;
	
	//联系电话
	private String contactPhone;
	
	//申请事由
	private String reason;
	
	//用印项目名称
	private String projectName;
	
	//用印次数
	private int useCount;
	
	//合同文件信息
	private List<Attach> attaches;
	
	//印章信息
	private List<Seal> seals;
	

	public ElecChannelContractPhysicalSignInfo() {
		super();
	}
	
	
	public String getApplyUserName() {
		return applyUserName;
	}


	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}


	public String getApplyCode() {
		return applyCode;
	}

	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getUseCount() {
		return useCount;
	}

	public void setUseCount(int useCount) {
		this.useCount = useCount;
	}
	

	public List<Attach> getAttaches() {
		return attaches;
	}


	public void setAttaches(List<Attach> attaches) {
		this.attaches = attaches;
	}


	public List<Seal> getSeals() {
		return seals;
	}

	public void setSeals(List<Seal> seals) {
		this.seals = seals;
	}


	@Override
	public String toString() {
		return "ElecChannelContractPhysicalSignInfo [applyCode=" + applyCode
				+ ", companyNo=" + companyNo + ", companyName=" + companyName
				+ ", deptName=" + deptName + ", applyUserName=" + applyUserName + ", contractPhone=" + contactPhone
				+ ", applyUserId=" + applyUserId + ", reason=" + reason
				+ ", projectName=" + projectName + ", useCount=" + useCount
				+ ", files=" + attaches + ", seals=" + seals + "]";
	}



	public class Attach {
		
		//附件名称
		private String name;
		
		//附件ID
		private String id;
	
		public Attach() {
			super();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		@Override
		public String toString() {
			return "Attach [name=" + name  + ", id=" + id
					+ "]";
		}
	}
	
	public class Seal {
		
		//印章名称
		private String name;
		
		//印章ID
		private String id;
		
		public Seal() {
			super();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		@Override
		public String toString() {
			return "Seal [name=" + name + ", id=" + id + "]";
		}
	}
	

}
