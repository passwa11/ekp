package com.landray.kmss.third.ding.listener.interactiveCard;

import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.third.ding.util.DingInteractivecardUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.context.ApplicationListener;

import java.util.List;
import java.util.Map;

public class CardEventListener implements ApplicationListener<Event_Common> {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(CardEventListener.class);

    /**
     *  互动卡片参数说明:
     *  method(String) 操作卡片方式:推送send,更新update
     *  mainModel(BaseModel) 主文档model
     *
     *  title(Strig) 文档标题
     *  from(Strig) 业务来源
     *  publicData(String) 公有数据
     *  {
     * 	"公有key": "公有value"
     * }
     *  privateData(String) 私有数据
     *  {
     * 	  "handler": {
     * 		"处理人01-fdId": {
     * 			"canOperate": "Y"
     *                },
     * 		"处理人02-fdId": {
     * 			"showStatus": "Y"
     *        }
     *    },
     * 	 "other": {
     * 		"showStatus": "Y"
     * 	  }
     * }
     *  users(List<String>) 发送人
     */
    @Override
    public void onApplicationEvent(Event_Common event) {
        if ("dingInteractiveCard".equals(event.getSource().toString())) {//监听业务对卡片的调用
            Map params = event.getParams();
            logger.debug("业务对卡片的调用:"+params);
            if(params==null||params.size()==0) {
                return;
            }
            //操作：send、update
            if(!params.containsKey("method")){
                logger.warn("缺少参数：method(send/update)");
                return;
            }
            if(!params.containsKey("mainModel")){
                logger.warn("缺少主文档参数：mainModel(BaseModel.class)");
                return;
            }
            String method = (String) params.get("method");
            if(!("send".equals(method)||"update".equals(method))){
                logger.warn("参数method异常：method必须是send或update，当前请求入参为：{}",method);
                return;
            }
            BaseModel baseModel = (BaseModel) params.get("mainModel");
            if (baseModel==null){
                logger.warn("主文档mainModel为空");
                return;
            }
            String title = null;
            if(params.containsKey("title")){
                title = (String) params.get("title");
            }
            String from = null;
            if(params.containsKey("from")){
                from = (String) params.get("from");
            }

            JSONObject publicData = null;
            if(params.containsKey("publicData")){
                publicData = JSONObject.fromObject(params.get("publicData"));
            }
            JSONObject privateData = null;
            if(params.containsKey("privateData")){
                privateData = JSONObject.fromObject(params.get("privateData"));
            }
            List<String> users = null;
            if(params.containsKey("users")){
                users = (List) params.get("users");
            }
            switch (method){
                case "send":
                    DingInteractivecardUtil.sendCardByModel(baseModel,title,privateData,publicData,users,from);
                    break;
                case "update":
                    DingInteractivecardUtil.updateCard(baseModel,title,privateData,publicData,users);
                    break;
            }
        }
    }

}
