package com.landray.kmss.sys.oms.in;

public class OmsInRelationField {

	private String fdKeyword;

	private String fdNo;

	private String fdLdapDN;

	public OmsInRelationField(String fdKeyword, String fdNo, String fdLdapDN) {
		this.fdKeyword = fdKeyword;
		this.fdNo = fdNo;
		this.fdLdapDN = fdLdapDN;
	}

	private boolean isEqualKeyword(String fdKeyword) {
		boolean fdKeyword_equal = false;
		if (this.fdKeyword == null) {
			if (fdKeyword == null) {
				fdKeyword_equal = true;
			}
		} else {
			fdKeyword_equal = this.fdKeyword.equals(fdKeyword);
		}
		return fdKeyword_equal;
	}

	private boolean isEqualNo(String fdNo) {
		boolean fdNo_equal = false;
		if (this.fdNo == null) {
			if (fdNo == null) {
				fdNo_equal = true;
			}
		} else {
			fdNo_equal = this.fdNo.equals(fdNo);
		}
		return fdNo_equal;
	}

	public boolean isEqualLdapDN(String fdLdapDN) {
		boolean fdLdapDN_equal = false;
		if (this.fdLdapDN == null) {
			if (fdLdapDN == null) {
				fdLdapDN_equal = true;
			}
		} else {
			fdLdapDN_equal = this.fdLdapDN.equals(fdLdapDN);
		}
		return fdLdapDN_equal;
	}

	public boolean isEqualKeywordAndDN(String fdKeyword, String fdLdapDN) {
		return isEqualKeyword(fdKeyword) && isEqualLdapDN(fdLdapDN);
	}

	public boolean isEqualNoAndDN(String fdNo, String fdLdapDN) {
		return isEqualNo(fdNo) && isEqualLdapDN(fdLdapDN);
	}

	public boolean isEqualAll(String fdKeyword, String fdNo, String fdLdapDN) {
		return isEqualKeyword(fdKeyword) && isEqualNo(fdNo)
				&& isEqualLdapDN(fdLdapDN);
	}

}
