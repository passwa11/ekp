package com.landray.kmss.sys.zone.service.spring;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.landray.kmss.common.service.IKmssSystemInitBean;
import com.landray.kmss.sys.cluster.interfaces.ClusterDiscover;
import com.landray.kmss.sys.cluster.interfaces.Event_ClusterReady;
import com.landray.kmss.sys.cluster.interfaces.group.GroupConfigChangeMessage;
import com.landray.kmss.sys.cluster.interfaces.group.IGroupConfigStorer;
import com.landray.kmss.sys.cluster.interfaces.message.IMessage;
import com.landray.kmss.sys.cluster.interfaces.message.IMessageQueue;
import com.landray.kmss.sys.cluster.interfaces.message.IMessageReceiver;
import com.landray.kmss.sys.cluster.interfaces.message.UniqueMessageQueue;
import com.landray.kmss.sys.cluster.model.SysClusterGroup;
import com.landray.kmss.sys.zone.util.SysZoneConfigUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class SysZonePluginService implements ApplicationListener, 
					IKmssSystemInitBean, IMessageReceiver {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysZonePluginService.class);
	
	
	private IGroupConfigStorer groupConfigStorer;

	public IGroupConfigStorer getGroupConfigStorer() {
		return groupConfigStorer;
	}

	public void setGroupConfigStorer(IGroupConfigStorer groupConfigStorer) {
		this.groupConfigStorer = groupConfigStorer;
	}
	
	protected IMessageQueue messageQueue = new UniqueMessageQueue();
	
	@Override
	public IMessageQueue getMessageQueue() {
		return messageQueue;
	}
	
	// 群集准备好后，加载其他群集的扩展点
	@Override
    public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof Event_ClusterReady) {
			loadOherGroupPlugin();
		}
	}

	private void loadOherGroupPlugin() {
		List<SysClusterGroup> groups = ClusterDiscover.getInstance()
				.getAllGroup();
		for (int i = 0; i < groups.size(); i++) {
			SysClusterGroup group = groups.get(i);
			if (!group.getFdLocal()) {
				String code = group.getFdKey();
				try {
					if (StringUtil.isNotNull(code)) {
						loadExtendServerConfig(code);
					}
				}catch (Exception e) {
					e.printStackTrace();
					logger.error("获取" + code + "群组的员工黄页扩展信息错误",e);
				}
			}
		}
	}

	@Override
	public String initName() {
		return ResourceUtil
				.getString("sysZonePersonInfo.init.name", "sys-zone");
	}
	
	
	/**
	 * 系统初始化时，存储自己的信息
	 */
	@Override
	public KmssMessages initializeData() {
		try {
			JSONObject rtn =  new JSONObject();
			String currentKey = SysZoneConfigUtil.getCurrentServerGroupKey();
			//存储即时通讯
	 		JSONArray carray = new JSONArray();
	 		carray = SysZoneConfigUtil.getCommnicateList();
		 	if(!carray.isEmpty()) {
		 		for(Object tObj : carray) {
		 			Object tmp = ((JSONObject)tObj).get("server");
		 			if(tmp != null && !tmp.toString().equals(currentKey)) {
		 				//只存储自己的信息
		 				carray.remove(tObj);
		 			}
		 		}
		 	}
		 	rtn.put(SysZoneConfigUtil.COMMUNICATE_MAP_EXTENSION_POINT_ID, carray);
		 	
		 	
		 	//存储专家、勋章等展现信息
		 	JSONArray infos = new JSONArray();
		 	Map rMap = null;
	 		rMap = SysZoneConfigUtil.getOtherInfosMap();
	 		if(rMap != null) {
		 		Iterator iterator = rMap.keySet().iterator();
				while(iterator.hasNext()){
					Map tMap = (Map)rMap.get(iterator.next().toString());
					Object tmp = tMap.get("server");
					if(tmp == null || tmp.toString().equals(currentKey)) {
						infos.add(JSONObject.fromObject(tMap));
					} 
				}
		 	}
	 		rtn.put(SysZoneConfigUtil.OTHER_INFO_MAP_EXTENSION_POINT_ID, infos);
			//存储自己的扩展信息
			groupConfigStorer.publishGroupConfig(this.getClass().getName(),
					rtn);
		} catch (Exception e) {
			return new KmssMessages().addError(new KmssMessage(
					"sys-zone:zone.config.publish.failure"), e);
		}
		return new KmssMessages().addMsg(new KmssMessage(
				"sys-zone:zone.config.publish.succeed"));
	}
	
	

	
	
	
	
	/**
	 * 其它系统启动时，读取它的配置信息
	 */
	@Override
	public void receiveMessage(IMessage message) throws Exception {
		if (message instanceof GroupConfigChangeMessage) {
			GroupConfigChangeMessage info = (GroupConfigChangeMessage) message;
			String key = info.getGroupKey();
			loadExtendServerConfig(key);
		}
	}
	
	
	/**
	 * 获取其他服务的扩展
	 * @param key 对应服务器的key
	 * @param extendId 要加载的扩展点Id
	 */
	public  void loadExtendServerConfig(String serverKey)
				throws Exception{
		JSONObject json = getGroupConfigStorer().loadGroupConfig(this.getClass().getName(),
				serverKey);
		if(json != null && !json.isEmpty()) {
			//领域勋章等信息
			JSONArray array = json.getJSONArray(SysZoneConfigUtil.OTHER_INFO_MAP_EXTENSION_POINT_ID);
			for(Object obj : array) {
				Map<String, String> tmap = (Map<String, String>)obj;
				String  infoId = tmap.get("infoId");
				//去掉重复的
				if(SysZoneConfigUtil.getOtherInfosMap().get(infoId) == null) {
					tmap.put("infoId",serverKey + SysZoneConfigUtil.SEPARATOR + infoId);
					SysZoneConfigUtil.getOtherInfosMap().put(infoId, tmap);
				}
			}
			
			array = json.getJSONArray(SysZoneConfigUtil.COMMUNICATE_MAP_EXTENSION_POINT_ID);
			HashMap<String, Boolean> tmpMap = new HashMap<String, Boolean>();
			for(Object tmpObj :  SysZoneConfigUtil.getCommnicateList()) {
				tmpMap.put((String)((JSONObject)tmpObj).get("unid"), Boolean.TRUE);
			}
			for(Object obj : array) { 
				JSONObject tobj = (JSONObject)obj;
				if(tmpMap.get((String)tobj.get("unid")) == null ) {
					SysZoneConfigUtil.getCommnicateList().add(obj);
				}
			}
			SysZoneConfigUtil.sortCommunicate(SysZoneConfigUtil.getCommnicateList());
		}
	}

}
