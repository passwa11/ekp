package com.landray.kmss.tic.jdbc.util;

public class JdbcMysqlUtil{

	/**
	 * 验证是否是日期类型 
	 * @param columnType
	 * @return
	 */
    public static boolean  typeDate(String columnType){
    	return  "DATE".equals(columnType.toUpperCase())?true:false;
    }
 
    /**
     * 验证是否是日期时间类型 
     * @param columnType
     * @return
     */
    public static boolean  typeDateTime(String columnType){
    	return  "DATETIME".equals(columnType.toUpperCase())?true:false;
    }
    
    /**
     * 验证是否是timestamp类型 
     * @param columnType
     * @return
     */
    public static boolean  typeTimesTamp(String columnType){
    	return  "TIMESTAMP".equals(columnType.toUpperCase())?true:false;
    }
    
    /**
     * 验证是否是上面中的一种
     * @param columnType
     * @return
     */
    public static boolean  validateRQType4Mysql(String columnType){
    	return typeDate(columnType)||typeDateTime(columnType)||typeTimesTamp(columnType);
    }
    
    public static boolean  validataColumnType4RQ(String columnType){
    	return validateRQType4Mysql(columnType);
    }
    
    /**
	 * 根据字段类型转换成对应的日期时间字符串
	 * @param fieldInfor
	 * @return
	 */
	public String fieldTypeConvert(String fieldInfor){
		String[] arr =fieldInfor.split("|");
		String dataType=arr[1];
		String resultType="";
		String fieldColumn=arr[0];
		if("DATE".equals(dataType.toUpperCase())){
			resultType="char("+fieldColumn+",iso)||" + "'"+ " 00:00:00"+ "'";
		}else if("TIMESTAMP".equals(dataType.toUpperCase())){
			resultType="TO_CHAR(TIMESTAMP("+fieldColumn+"),'YYYY-MM-DD HH24:MI:SS')";
		}else if("DATETIME".equals(dataType.toUpperCase())){
			
		}
		return resultType;
	
	}
	
}
