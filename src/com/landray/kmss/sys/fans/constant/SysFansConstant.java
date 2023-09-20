package com.landray.kmss.sys.fans.constant;

public interface SysFansConstant {
	/**
	 * 未关注关注标识
	 */
	String UNFOLLOWED = "unfollowed";
	/**
	 * 已关注标识
	 */
	String FOLLOWED = "followed";
	
	
	/*下面这几个状态不是用于存储数据库的，只是用于前端获取关注关系，表示当前人与指定人的关注，
	    这里的互相关注的状态与数据库中存储的相互关注不一样，这里用3表示，
	    数据库中用2表示可见RELA_TYPE_FAN，RELA_TYPE_EACH_OTHER，不要混淆*/
	/**
	 * 与他人的关系：互不关注
	 */
	Integer FANS_RELA_NO = 0;
	
	/**
	 * 与他人的关系：只关注
	 */
	Integer FANS_RELA_ATT = 1;
	
	/**
	 * 与他人的关系：只被关注
	 */
	Integer FANS_RELA_FAN  = 2;
	
	/**
	 * 与他人的关系：相互关注
	 */
	Integer FANS_RELA_EACH_OTHER = 3;
	/*上面这几个状态不是用于存储是数据库的，只是用于前端获取关注关系****/
	
	
	
	/**
	 * 用户类型（人）
	 */
	public final static Integer RELATION_USER_TYPE_PERSON = 1;
	
	/**
	 * 用户类型（其他）
	 */
	public final static Integer RELATION_USER_TYPE_OTHER = 2;
	
	
	
	/**
	 * 关注 ，这个状态是存在数据库中的表示单方面关注（对应model的fdRelationType字段）
	 */
	Integer RELA_TYPE_FAN  = 1;
	
	/**
	 * 相互关注， 这个状态是存在数据库中的表示相互关注（对应model的fdRelationType字段）
	 */
	Integer RELA_TYPE_EACH_OTHER  = 2;
}
