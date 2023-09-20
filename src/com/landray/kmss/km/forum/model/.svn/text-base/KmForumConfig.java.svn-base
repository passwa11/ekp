package com.landray.kmss.km.forum.model;

import java.util.Arrays;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 读写论坛系统设置单例类
 * 
 * @author 吴兵
 * @version 1.0 2006-09-04
 */

public class KmForumConfig extends BaseAppConfig {

	public KmForumConfig() throws Exception {
		super();
	}

	@Override
	public String getJSPUrl() {
		return "/km/forum/km_forum_config/kmForumConfig_edit.jsp";
	}

	/**
	 * 是否允许匿名发文
	 */
	public String getAnonymous() {
		String isAnonymous = getValue("isAnonymous");
		if (StringUtil.isNull(isAnonymous)) {
			isAnonymous = "true";
		}
		return isAnonymous;
	}

	public void setAnonymous(String anonymous) {
		setValue("isAnonymous", anonymous);
	}
	
	/**
	 * 热帖开关
	 */
	public String getHotReplyCount() {
		return getValue("hotReplyCount")==null?"10":getValue("hotReplyCount");
	}

	public void setHotReplyCoun(String hotReplyCount) {
		setValue("hotReplyCount", hotReplyCount);
	}

	/**
	 * 发帖时间间隔
	 */
	public String getReplyTimeInterval() {
		return getValue("replyTimeInterval") == null ? "0"
				: getValue("replyTimeInterval");
	}

	public void setReplyTimeInterval(String replyTimeInterval) {
		setValue("replyTimeInterval", replyTimeInterval);
	}

	/**
	 * 是否允许用户修改个人信息
	 */
	public String getCanModifyRight() {
		return getValue("canModifyRight");
	}

	public void setCanModifyRight(String canModifyRight) {
		setValue("canModifyRight", canModifyRight);
	}

	/**
	 * 是否允许使用昵称
	 */
	public String getCanModifyNickname() {
		return getValue("canModifyNickname");
	}

	public void setCanModifyNickname(String canModifyNickname) {
		setValue("canModifyNickname", canModifyNickname);
	}

	/**
	 * 用户等级
	 */
	public String getLevel() {
		return getValue("level");
	}

	public void setLevel(String level) {
		setValue("level", level);
	}
	
	/**
	 * 敏感词检测开关
	 */
	public String getIsWordCheck() {
		return getValue("isWordCheck")==null?"false":getValue("isWordCheck");
	}

	public void setIsWordCheck(String isWordCheck) {
		setValue("isWordCheck", isWordCheck);
	}
	
	/**
	 * 敏感词
	 */
	public String getWords() {
		return getValue("words");
	}

	public void setWords(String words) {
		setValue("words", words);
	}
	
	/**
	 *  是否需重新获取敏感词
	 */
	public String getIsNeedAcquire() {
		return getValue("isNeedAcquire");
	}

	public void setIsNeedAcquire(String isNeedAcquire) {
		setValue("isNeedAcquire", isNeedAcquire);
	}

	

	private String levelIcon;

	public String getLevelIcon() {
		return levelIcon;
	}

	private void setLevelIcon(int pos) {
		switch (pos) {
		case 5:
			levelIcon = "4";
			break;
		case 6:
			levelIcon = "5";
			break;
		case 7:
			levelIcon = "5";
			break;
		case 8:
			levelIcon = "6";
			break;
		case 9:
			levelIcon = "6";
			break;
		case 10:
			levelIcon = "6";
			break;
		case 11:
			levelIcon = "7";
			break;
		case 12:
			levelIcon = "7";
			break;
		case 13:
			levelIcon = "8";
			break;
		case 14:
			levelIcon = "8";
			break;
		case 15:
			levelIcon = "9";
			break;
		case 16:
			levelIcon = "9";
			break;
		case 17:
			levelIcon = "10";
			break;
		default:
			levelIcon = "" + pos;
		}
	}

	public int[] getAllLevels() {
		if (getLevel() == null) {
            return null;
        }
		String[] groups = getLevel().split(";");
		String[] levels = new String[groups.length];
		int[] scores = new int[groups.length];
		int[] sortScores = new int[groups.length];
		for (int i = 0; i < groups.length; i++) {
			String[] group = groups[i].split(":");
			levels[i] = group[0];
			scores[i] = Integer.parseInt(group[1].trim());
			sortScores[i] = scores[i];
		}
		Arrays.sort(sortScores);
		return sortScores;
	}

	/**
	 * 根据积分得到相应的等级
	 */
	public String getLevelByScore(int score) {
		if (getLevel() == null) {
            return "";
        }
		String[] groups = getLevel().split(";");
		String[] levels = new String[groups.length];
		int[] scores = new int[groups.length];
		int[] sortScores = new int[groups.length];
		for (int i = 0; i < groups.length; i++) {
			String[] group = groups[i].split(":");
			levels[i] = group[0];
			scores[i] = Integer.parseInt(group[1].trim());
			sortScores[i] = scores[i];
		}
		Arrays.sort(sortScores);
		int currScore = sortScores[sortScores.length - 1];
		for (int i = 0; i < sortScores.length; i++) {
			if (score < sortScores[i]) {
				if (i == 0) {
					currScore = sortScores[i];
				} else {
					currScore = sortScores[i - 1];
				}
				break;
			}
		}
		for (int i = 0; i < scores.length; i++) {
			if (scores[i] == currScore) {
				setLevelIcon(i);
				return levels[i];
			}
		}
		return "";
	}

	private void print(String msg, String[] a) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < a.length; i++) {
			if (i != 0) {
                sb.append(",");
            }
			sb.append("" + a[i]);
		}
		System.out.println(msg + "::" + sb.toString());
	}
	
	/**
	 * webservice默认版块
	 */
	public String getWebServiceDefForumId() {
		return getValue("webServiceDefForumId");
	}

	public void setWebServiceDefForumId(String webServiceDefForumId) {
		setValue("webServiceDefForumId", webServiceDefForumId);
	}

	public String getWebServiceDefForumName() {
		return getValue("webServiceDefForumName");
	}

	public void setWebServiceDefForumName(String webServiceDefForumName) {
		setValue("webServiceDefForumName", webServiceDefForumName);
	}

	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("km-forum:menu.kmForum.config");
	}
}
