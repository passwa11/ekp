package com.landray.kmss.sys.organization.interfaces;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.landray.kmss.util.ArrayUtil;

public class PersonCommunicationInfo {
	private Map<Object, List> emails;

	private Map<Object, List> mobileNums;

	private List<String> defaultLangs;

	/**
	 * @return 邮件地址
	 */
	public List getEmails() {
		ArrayList emailsSet = new ArrayList();
		if (!emails.values().isEmpty()) {
			for (Object key : emails.keySet()) {
				for (Object email : emails.get(key)) {
					emailsSet.add(email);
				}
			}
		}
		return emailsSet;
	}

	public void setEmails(Map emails) {
		this.emails = emails;
	}

	/**
	 * @return 根据key获取邮件地址，key为默认语言
	 */
	public List getEmails(String defaultLang) {
		return (List) emails.get(defaultLang);
	}

	/**
	 * 根据key设置邮件地址，key为默认语言
	 */
	public void setEmails(String defaultLang, List emails) {
		if (this.emails.keySet().contains(defaultLang)) {
			ArrayUtil.concatTwoList(emails, this.emails.get(defaultLang));
		} else {
			this.emails.put(defaultLang, emails);
		}
		if (!this.defaultLangs.contains(defaultLang)) {
			this.defaultLangs.add(defaultLang);
		}
	}

	/**
	 * @return 手机号码
	 */
	public List getMobileNums() {
		Set mobileNumsSet = new HashSet();
		if (!mobileNums.values().isEmpty()) {
			for (Object key : mobileNums.keySet()) {
				for (Object mobileNum : mobileNums.get(key)) {
					mobileNumsSet.add(mobileNum);
				}
			}
		}
		return new ArrayList(mobileNumsSet);
	}

	public void setMobileNums(Map mobileNums) {
		this.mobileNums = mobileNums;
	}

	/**
	 * @return 根据key获取手机号码，key为默认语言
	 */
	public List getMobileNums(String defaultLang) {
		return (List) mobileNums.get(defaultLang);
	}

	/**
	 * 根据key设置手机号码，key为默认语言
	 */
	public void setMobileNums(String defaultLang, List mobileNums) {
		if (this.mobileNums.keySet().contains(defaultLang)) {
			ArrayUtil.concatTwoList(mobileNums, this.mobileNums
					.get(defaultLang));
		} else {
			this.mobileNums.put(defaultLang, mobileNums);
		}
		if (!this.defaultLangs.contains(defaultLang)) {
			this.defaultLangs.add(defaultLang);
		}
	}

	/**
	 * @return 默认语言
	 */
	public List<String> getDefaultLangs() {
		return defaultLangs;
	}

	public void setDefaultLangs(List<String> defaultLangs) {
		this.defaultLangs = defaultLangs;
	}

}
