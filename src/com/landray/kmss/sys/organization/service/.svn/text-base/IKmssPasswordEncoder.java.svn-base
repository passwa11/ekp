package com.landray.kmss.sys.organization.service;

public interface IKmssPasswordEncoder {
	/**
	 *
	 * 对密码进行加密<br/>
	 * 注意：<br/>
	 * 1.若有直接使用本方法进行如：<br/>
	 *  if(passwordEncoder.encodePassword(oldPassword).equals(person.getFdPassword())){...}<br/>
	 *	进行比较的逻辑代码，会存在person.getFdPassword()为md5加密字符串(旧数据)<br/>
	 *	而passwordEncoder.encodePassword(oldPassword)为sm3等其他方式加密字符串的情况<br/>
	 *	请使用:
	 * @see com.landray.kmss.sys.organization.util.PasswordEncoderUtils#checkPassWordEquals
	 * @param password
	 * @return
	 */
	public abstract String encodePassword(String password);
}
