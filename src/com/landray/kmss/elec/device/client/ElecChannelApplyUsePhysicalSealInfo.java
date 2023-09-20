package com.landray.kmss.elec.device.client;

import java.util.Date;
import java.util.List;

/**
*@author yucf
*@date  2019年8月2日
*@Description              申请物理印章信息
*/ 

public class ElecChannelApplyUsePhysicalSealInfo  implements IElecChannelRequestMessage, IElecChannelResponseMessage {

	private static final long serialVersionUID = 1L;
	
	//申请基础信息
	private ApplyBaseInfo baseInfo;
	
	//用印信息
	private UseSealUser useSealUser;
	
	//印章信息
	private List<UseSealDetail> useSealList;
	
	//文件信息
	private List<ContentInfo> contentInfoList;	


	public ApplyBaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(ApplyBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public UseSealUser getUseSealUser() {
		return useSealUser;
	}

	public void setUseSealUser(UseSealUser useSealUser) {
		this.useSealUser = useSealUser;
	}

	public List<UseSealDetail> getUseSealList() {
		return useSealList;
	}

	public void setUseSealList(List<UseSealDetail> useSealList) {
		this.useSealList = useSealList;
	}

	public List<ContentInfo> getContentInfoList() {
		return contentInfoList;
	}

	public void setContentInfoList(List<ContentInfo> contentInfoList) {
		this.contentInfoList = contentInfoList;
	}

	@Override
	public String toString() {
		return "ElecChannelApplyPhysicalSealInfo [baseInfo=" + baseInfo
				+ ", useSealUser=" + useSealUser + ", sealList=" + useSealList
				+ "]";
	}

	/**
	 * 	申请基础信息
	 * @author michael
	 *
	 */
	public class ApplyBaseInfo {
		
		//申请ID
		private String applyId;
		
		//申请编号
		private String applyNo;
		
		//申请标题
		private String title;
		
		//用印开始使用时间
		private Date beginUseDate;
		
		//用印结束时间
		private Date endUseDate;

		//使用原因
		private String useReason;
		
		//是否外带(0:非外带  1:外带)
		private int outGoUse;
		
		public String getApplyNo() {
			return applyNo;
		}

		public void setApplyNo(String applyNo) {
			this.applyNo = applyNo;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public Date getBeginUseDate() {
			return beginUseDate;
		}

		public void setBeginUseDate(Date beginUseDate) {
			this.beginUseDate = beginUseDate;
		}

		public Date getEndUseDate() {
			return endUseDate;
		}

		public void setEndUseDate(Date endUseDate) {
			this.endUseDate = endUseDate;
		}

		public String getUseReason() {
			return useReason;
		}

		public void setUseReason(String useReason) {
			this.useReason = useReason;
		}


		public int getOutGoUse() {
			return outGoUse;
		}

		public void setOutGoUse(int outGoUse) {
			this.outGoUse = outGoUse;
		}
		
		public String getApplyId() {
			return applyId;
		}

		public void setApplyId(String applyId) {
			this.applyId = applyId;
		}

		@Override
		public String toString() {
			return "ApplyBaseInfo [applyNo=" + applyNo + ", title=" + title
					+ ", beginUseDate=" + beginUseDate + ", endUseDate="
					+ endUseDate + ", useReason=" + useReason + ", isOutGoUse="
					+ outGoUse + "]";
		}
	}
	
	
	/**
	 * 用印用户信息
	 * @author michael
	 *
	 */
	public class UseSealUser {
		
		private String userId;
		
		private String username;
		
		private String realName;
		
		//手机号码
		private String phone;
		
		//部门，公司信息后续考虑调整包装的POJO
		private String deptName;
		
		private String deptId;
		
		//单位编号
		private String companyNo;
		
		//单位名称
		private String companyName;
		
		private String companyId;

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getDeptName() {
			return deptName;
		}

		public void setDeptName(String deptName) {
			this.deptName = deptName;
		}

		public String getDeptId() {
			return deptId;
		}

		public void setDeptId(String deptId) {
			this.deptId = deptId;
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

		public String getCompanyId() {
			return companyId;
		}

		public void setCompanyId(String companyId) {
			this.companyId = companyId;
		}

		public String getRealName() {
			return realName;
		}

		public void setRealName(String realName) {
			this.realName = realName;
		}

		@Override
		public String toString() {
			return "UseSealUser [userId=" + userId + ", username=" + username
					+ ", realName=" + realName + ", phone=" + phone
					+ ", deptName=" + deptName + ", deptId=" + deptId
					+ ", companyNo=" + companyNo + ", companyName="
					+ companyName + ", companyId=" + companyId + "]";
		}
	}
	
	/**
	 * 印章信息
	 * @author michael
	 *
	 */
	public class UseSealDetail {
		
		//印章信息
		private ElecChannelPhysicalSealInfo sealInfo;

		//申请用印次数
		private int applyCounts;

		public ElecChannelPhysicalSealInfo getSealInfo() {
			return sealInfo;
		}

		public void setSealInfo(ElecChannelPhysicalSealInfo sealInfo) {
			this.sealInfo = sealInfo;
		}


		public int getApplyCounts() {
			return applyCounts;
		}

		public void setApplyCounts(int applyCounts) {
			this.applyCounts = applyCounts;
		}

		@Override
		public String toString() {
			return "UseSealDetail [sealInfo=" + sealInfo + ", applyCounts="
					+ applyCounts + "]";
		}
	}
	
	/**
	 * 文件信息
	 * @author michael
	 *
	 */
	public class ContentInfo {
		
		private String fileId;
		
		//文件地址(外网访问地址)
		private String fileUrl;
		
		//文件存储路径
		private String filePath;
		
		//文件base64串
		private String fileBase64;
		
		//文件名
		private String fileName;
		
		//是否附件
		private boolean isAttached = false;

		public String getFileId() {
			return fileId;
		}

		public void setFileId(String fileId) {
			this.fileId = fileId;
		}

		public String getFileUrl() {
			return fileUrl;
		}

		public void setFileUrl(String fileUrl) {
			this.fileUrl = fileUrl;
		}

		public String getFilePath() {
			return filePath;
		}

		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}

		public String getFileBase64() {
			return fileBase64;
		}

		public void setFileBase64(String fileBase64) {
			this.fileBase64 = fileBase64;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public boolean isAttached() {
			return isAttached;
		}

		public void setAttached(boolean isAttached) {
			this.isAttached = isAttached;
		}

		@Override
		public String toString() {
			return "ContentInfo [fileId=" + fileId + ", fileUrl=" + fileUrl
					+ ", filePath=" + filePath + ", fileBase64=" + fileBase64
					+ ", fileName=" + fileName + ", isAttached=" + isAttached
					+ "]";
		}
	}
}
