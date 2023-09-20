package com.landray.kmss.common.interfaces;

import net.sf.json.JSONObject;

/**
 * 增加方法需要在督办和值班添加实现否则会报错
 */
public interface ISuperviseProvider {

    String METHOD_ADD = "add";

    String METHOD_DELETE = "delete";

    //获取督办信息
    public JSONObject getSupervise(String fdModelId, String fdModelName)throws Exception;

    //核发督办
    public void updateConfirmSupervise(String fdModelId, String fdModelName) throws Exception;

    public void deleteSupervise(String fdModelId, String fdModelName) throws Exception;

    //回调
    public void extCallback(String fdSuperviceId, String fdModelId, String fdModelName, String method) throws Exception;
	
}
