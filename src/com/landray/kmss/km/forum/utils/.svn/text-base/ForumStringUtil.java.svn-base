package com.landray.kmss.km.forum.utils;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.util.ResourceUtil;

public class ForumStringUtil {
	public static final String KM_FORUM_POST_NOTIFIER = "km_forum_post_notifier"; // 待办key
    public static String getNewQuote(String oldQuote){
    	String key = ResourceUtil.getString("kmForumPost.floor", "km-forum");
    	if(oldQuote.indexOf(key)<0){
    		return oldQuote;
    	}
    	String lastString = oldQuote.substring(oldQuote.lastIndexOf(key));
    	String preString = "";
    	String middleString = "";
    	List digistList = new ArrayList<Integer>();
    	oldQuote = oldQuote.substring(0,oldQuote.lastIndexOf(key));
    	for(int i=oldQuote.length();i>0;i--){
    		char floor = oldQuote.charAt(i-1);
    		if(floor<48 || floor>57){
    			preString = oldQuote.substring(0,i);
    		    break;
    		}else{
    			digistList.add(Integer.parseInt(String.valueOf(floor)));
    		}
    	}
    	for(int j=digistList.size()-1;j>=0;j--){
    		middleString +=digistList.get(j).toString();
    	}
    	int newFloor = Integer.parseInt(middleString)-1;
    	return preString + String.valueOf(newFloor) + lastString;
    	
    }
    
    public static void main(String[] args) {
		String old = "引用 管理员2楼 在2014-10-22 14:44的发表:";
		String newString = getNewQuote(old);
		String old1 = "引用 管理员20楼 在2014-10-22 14:44的发表:";
		String newString1 = getNewQuote(old1);
		System.out.println(newString);
		System.out.println(newString1);
	}
}
