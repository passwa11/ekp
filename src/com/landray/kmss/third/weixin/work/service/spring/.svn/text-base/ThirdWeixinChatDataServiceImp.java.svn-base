package com.landray.kmss.third.weixin.work.service.spring;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.component.locker.interfaces.ConcurrencyException;
import com.landray.kmss.component.locker.interfaces.IComponentLockService;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.weixin.chat.ThirdWeixinChatDataFile;
import com.landray.kmss.third.weixin.chat.msgtype.IChatdataMsgHandler;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.model.*;
import com.landray.kmss.third.weixin.service.*;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinChatdataSyncConfig;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinChatDataService;
import com.landray.kmss.third.weixin.work.spi.service.IWxworkOmsRelationService;
import com.landray.kmss.third.weixin.work.util.WxworkUtils;
import com.landray.kmss.util.FileMimeTypeUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.TransactionUtils;
import com.sunbor.web.tag.Page;
import com.tencent.wework.Finance;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ThirdWeixinChatDataServiceImp implements IThirdWeixinChatDataService {

    private static final Logger logger = LoggerFactory.getLogger(ThirdWeixinChatDataServiceImp.class);

    private SysQuartzJobContext jobContext = null;
    private String corpId;
    private Long syncSeq = null;
    //同步缓存表中的消息，key为msgid,value为fdId
    private Map<String,String> tempMsgMap = null;
    //已存在的消息，key为msgid,value为fdId。
    private Map<String,String> msgMap = null;
    //用户映射关系，key为微信用户id/客户id，value为ekp用户fdId。
    private Map<String,String> userMap = null;
    //key为机器人节点的ID，value机器人的名称
    //private Map<String,String> robotMap = null;
    //key为机器人节点的ID，value机器人的详情（JSON格式）
    //private Map<String,String> robotMap2add = null;
    //key为群的ID，value为群聊名称
    private Map<String,String> groupChatMap = null;
    //key为群的ID，value群聊的详情（JSON格式）
    private Map<String,String> groupChatMap2add = null;
    //key为群的ID，value为成员集合
    private Map<String,Set<String>> groupChatMap2Update = null;
    //企业微信账号映射，key为企业微信账号id，value为fdId
    private Map<String,String> accountMap = null;

    private Cipher decryptionCipher = null;
    private Cipher encrypter = null;

    public void setThirdWeixinChatDataMainService(IThirdWeixinChatDataMainService thirdWeixinChatDataMainService) {
        this.thirdWeixinChatDataMainService = thirdWeixinChatDataMainService;
    }

    private IThirdWeixinChatDataMainService thirdWeixinChatDataMainService;

    public void setWxworkOmsRelationService(IWxworkOmsRelationService wxworkOmsRelationService) {
        this.wxworkOmsRelationService = wxworkOmsRelationService;
    }

    public void setThirdWeixinContactMappService(IThirdWeixinContactMappService thirdWeixinContactMappService) {
        this.thirdWeixinContactMappService = thirdWeixinContactMappService;
    }

    private IThirdWeixinGroupChatService thirdWeixinGroupChatService;
    private IWxworkOmsRelationService wxworkOmsRelationService;
    private IThirdWeixinContactMappService thirdWeixinContactMappService;
    private IComponentLockService componentLockService = null;
    private IThirdWeixinChatDataTempService thirdWeixinChatDataTempService;

    public void setThirdWeixinChatDataTempService(IThirdWeixinChatDataTempService thirdWeixinChatDataTempService) {
        this.thirdWeixinChatDataTempService = thirdWeixinChatDataTempService;
    }

    public void setThirdWeixinChatGroupService(IThirdWeixinChatGroupService thirdWeixinChatGroupService) {
        this.thirdWeixinChatGroupService = thirdWeixinChatGroupService;
    }

    public void setThirdWeixinAccountService(IThirdWeixinAccountService thirdWeixinAccountService) {
        this.thirdWeixinAccountService = thirdWeixinAccountService;
    }

    private IThirdWeixinChatGroupService thirdWeixinChatGroupService;
    private IThirdWeixinAccountService thirdWeixinAccountService;

    public void setThirdWeixinGroupChatService(IThirdWeixinGroupChatService thirdWeixinGroupChatService) {
        this.thirdWeixinGroupChatService = thirdWeixinGroupChatService;
    }


    private IComponentLockService getComponentLockService() {
        if (componentLockService == null) {
            componentLockService = (IComponentLockService) SpringBeanUtil
                    .getBean("componentLockService");
        }
        return componentLockService;
    }

    @Override
    public void syncChatData(SysQuartzJobContext jobContext) {
        this.jobContext = jobContext;
        ThirdWeixinSynLock lock = new ThirdWeixinSynLock();
        try {
            getComponentLockService().tryLock(lock, "chatdata");
            doSynchro(jobContext);
            getComponentLockService().unLock(lock);
        } catch (ConcurrencyException e) {
            jobContext.logError(
                    "已经有同步任务正在执行，如果是由于同步过程中重启等原因导致定时任务被锁，需到“后台配置”->“应用中心”->“机制”->“锁机制”中释放锁",
                    e);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        } finally {
            getComponentLockService().unLock(lock);
        }
    }

    /**
     * 判断是否启用了会话记录同步功能
     * @param jobContext
     * @return
     */
    private boolean checkChatdataSyncEnable(SysQuartzJobContext jobContext){
        WeixinWorkConfig weixinWorkConfig = WeixinWorkConfig.newInstance();
        if(!"true".equals(weixinWorkConfig.getWxEnabled())){
            logger.info("没有启用企业微信集成");
            jobContext.logMessage("没有启用企业微信集成");
            return false;
        }
        if(!"true".equals(weixinWorkConfig.getChatdataSyncEnable())){
            logger.info("没有启用企业微信会话内容存档");
            jobContext.logMessage("没有启用企业微信会话内容存档");
            return false;
        }
        return true;
    }

    /**
     * 初始化
     * @throws Exception
     */
    private void init() throws Exception{
        WeixinWorkConfig weixinWorkConfig = WeixinWorkConfig.newInstance();
        corpId = weixinWorkConfig.getWxCorpid();
        ThirdWeixinChatdataSyncConfig syncConfig = new ThirdWeixinChatdataSyncConfig();
        syncSeq = Long.parseLong(syncConfig.getLastSyncSeq());
        //syncSeq = 0l;
        tempMsgMap = new HashMap<>();
        msgMap = new HashMap<>();
        userMap = new HashMap<>();
        groupChatMap = new ConcurrentHashMap();
        groupChatMap2add = new ConcurrentHashMap();
        accountMap = new ConcurrentHashMap();
        groupChatMap2Update = new ConcurrentHashMap();
        logger.info("配置信息初始化成功");
        jobContext.logMessage("获取配置信息成功");
    }

    /**
     * 释放资源
     * @throws Exception
     */
    private void release() throws Exception{
        syncSeq = 0L;
        tempMsgMap = new HashMap<>();
        msgMap = new HashMap<>();
        userMap = new HashMap<>();
        groupChatMap = new ConcurrentHashMap();
        groupChatMap2add = new ConcurrentHashMap();
        accountMap = new ConcurrentHashMap();
        groupChatMap2Update = new ConcurrentHashMap();
        decryptionCipher = null;
        encrypter = null;
    }

    /**
     * 初始化同步缓存表映射，主要是为了防止插入重复数据
     * @throws Exception
     */
    private void initTempMsgMap() throws Exception{
        jobContext.logMessage("开始初始化同步缓存表映射");
        List list = thirdWeixinChatDataTempService.findValue("fdMsgId,fdId",null,null);
        for(Object o:list){
            Object[] oo = (Object[]) o;
            tempMsgMap.put((String)oo[0],(String)oo[1]);
        }
        logger.info("初始化同步缓存表映射成功，总数："+tempMsgMap.size());
        jobContext.logMessage("初始化同步缓存表映射成功，总数："+tempMsgMap.size());
    }

    /**
     * 初始化会话记录映射，主要是防止插入重复记录。因为企业微信的接口只能取3天内的会议记录，所以这里只取4天内的映射数据
     * @throws Exception
     */
    private void initMsgMap() throws Exception{
        jobContext.logMessage("开始初始化聊天记录映射");
        long current = System.currentTimeMillis();
        long time = current - 345600000L;
        String whereBlock = "fdMsgTime>"+time;
        List list = thirdWeixinChatDataMainService.findValue("fdMsgId,fdId",whereBlock,null);
        for(Object o:list){
            Object[] oo = (Object[]) o;
            msgMap.put((String)oo[0],(String)oo[1]);
        }
        logger.info("初始化聊天记录映射成功，总数："+msgMap.size());
        jobContext.logMessage("初始化聊天记录映射成功，总数："+msgMap.size());
    }

    /**
     * 初始化人员映射，主要是为了获取ekp用户id
     * @throws Exception
     */
    private void initUserMap() throws Exception{
        jobContext.logMessage("开始初始化用户映射");
        List list = wxworkOmsRelationService.findValue("fdAppPkId,fdEkpId","fdAppPkId is not null and fdEkpId is not null",null);
        for(Object o:list){
            Object[] oo = (Object[]) o;
            userMap.put((String)oo[0],(String)oo[1]);
        }
        list = thirdWeixinContactMappService.findValue("fdContactUserId,fdExternalId","fdContactUserId is not null and fdExternalId is not null",null);
        for(Object o:list){
            Object[] oo = (Object[]) o;
            msgMap.put((String)oo[0],(String)oo[1]);
        }
        logger.info("初始化用户映射成功，总数："+userMap.size());
        jobContext.logMessage("初始化用户映射成功，总数："+userMap.size());
    }

    /**
     * 初始化群名称映射
     * @throws Exception
     */
    private void initGroupChatMap() throws Exception{
        jobContext.logMessage("开始初始化群映射");
        List list = thirdWeixinGroupChatService.findValue("fdRoomId,fdRoomName",null,null);
        for(Object o:list){
            Object[] oo = (Object[]) o;
            groupChatMap.put((String)oo[0],(String)oo[1]);
        }
        logger.info("初始化群映射成功，总数："+groupChatMap.size());
        jobContext.logMessage("初始化群映射成功，总数："+groupChatMap.size());
    }

    /**
     * 初始化企业微信账号信息映射
     * @throws Exception
     */
    private void initAccountMap() throws Exception{
        jobContext.logMessage("开始初始化微信账号映射");
        List list = thirdWeixinAccountService.findValue("fdAccountId,fdId",null,null);
        for(Object o:list){
            Object[] oo = (Object[]) o;
            accountMap.put((String)oo[0],(String)oo[1]);
        }
        logger.info("初始化微信账号映射成功，总数："+accountMap.size());
        jobContext.logMessage("初始化微信账号映射成功，总数："+accountMap.size());
    }

    private void doSynchro(SysQuartzJobContext jobContext) throws Exception {
        if(!checkChatdataSyncEnable(jobContext)){
            return;
        }
        init();
        jobContext.logMessage("开始同步会话记录，同步开始序号："+syncSeq);
        Finance.LoadLibrary();
        long sdk = Finance.NewSdk();
        try {
            WeixinWorkConfig weixinWorkConfig = WeixinWorkConfig.newInstance();
            int ret = Finance.Init(sdk, weixinWorkConfig.getWxCorpid(), weixinWorkConfig.getChatdataAppSecret());
            if (ret != 0) {
                throw new Exception("init sdk err. ret: " + ret);
            }
            //同步数据到缓存表
            syncChatdataIterator(sdk,syncSeq, weixinWorkConfig.getWxProxy());

            //从缓存表取出数据进行处理
            handleChatdata(sdk, weixinWorkConfig.getWxProxy());

            //历史会话记录归档
            backupChatData(jobContext);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw e;
        }finally {
            Finance.DestroySdk(sdk);
            release();
        }
    }

    /**
     * 同步会话记录到缓存表
     * @param sdk
     * @param seq 记录序号，返回该序号之后的会话记录
     * @param proxy 代理
     * @throws Exception
     */
    private void syncChatdataIterator(long sdk, Long seq, String proxy) throws Exception {
        logger.debug("开始调用接口获取会话数据存到缓存表");
        jobContext.logMessage("开始调用接口获取会话数据存到缓存表");
        initTempMsgMap();
        int count = 0;
        //循环获取会话记录，一次1000条
        do{
            Long seq_return = syncChatdata(sdk,seq,proxy);
            count++;
            //已经是最后一页了，退出同步
            if(seq_return==null || seq_return<=seq){
                break;
            }
            seq = seq_return;
        }while(count<10000);
        // 清除缓存映射
        tempMsgMap = null;
        if(syncSeq!=null) {
            //更新同步序号
            try {
                ThirdWeixinChatdataSyncConfig syncConfig = new ThirdWeixinChatdataSyncConfig();
                syncConfig.setLastSyncSeq(syncSeq + "");
                syncConfig.save();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                jobContext.logError("更新同步序号失败，syncSeq：" + syncSeq, e);
            }
        }
        logger.debug("调用接口获取会话数据存到缓存表成功");
        jobContext.logMessage("调用接口获取会话数据存到缓存表成功");
    }

    /**
     * 同步会话记录到缓存表
     * @param sdk
     * @param seq
     * @param proxy
     * @return
     * @throws Exception
     */
    private Long syncChatdata(long sdk, Long seq, String proxy) throws Exception {
        // 每次使用GetChatData拉取存档前需要调用NewSlice获取一个slice，在使用完slice中数据后，还需要调用FreeSlice释放。
        long slice = Finance.NewSlice();
        try {
            int ret = Finance.GetChatData(sdk, seq, 1000, proxy, null, 30, slice);
            if (ret != 0) {
                throw new Exception("getchatdata fail, ret: " + ret);
            }
            String content = Finance.GetContentFromSlice(slice);
            return insertChatdatas(content);
        }catch (Exception e){
            throw e;
        }finally {
            Finance.FreeSlice(slice);
        }
    }

    /**
     * 更新数据到缓存表
     * @param content
     * @return
     * @throws Exception
     */
    private Long insertChatdatas(String content) throws Exception {
        logger.debug("获取到数据："+content);
        JSONObject data = JSONObject.parseObject(content);
        int errcode = data.getIntValue("errcode");
        if(errcode!=0){
            throw new Exception("获取数据失败，响应信息："+content);
        }
        JSONArray chatdatas = data.getJSONArray("chatdata");
        return update2Temp(chatdatas);
    }


    private String getEncryKey(String encrypt_random_key) throws Exception {
        byte[] bytes = Base64.decode(encrypt_random_key);
        String encryKey = new String(decryptionCipher.doFinal(bytes));
        return encryKey;
    }

    /**
     * 新建会话分组
     * @param chatDataMain
     * @param md5
     * @return
     * @throws Exception
     */
    private ThirdWeixinChatGroup addChatGroup(ThirdWeixinChatDataMain chatDataMain, String md5) throws Exception {
        logger.debug("创建会话分组，md5:"+md5);
        ThirdWeixinChatGroup chatGroup = new ThirdWeixinChatGroup();
        String roomId = chatDataMain.getFdRoomId();
        //单聊
        if(StringUtil.isNull(roomId)){
            String fromId = chatDataMain.getFdFrom();
            String toId = chatDataMain.getFdTo();
            chatGroup.setFdUserIdFir(fromId);
            chatGroup.setFdUserIdSec(toId);
            String relateUserId = thirdWeixinChatGroupService.genRelateUserId(fromId,toId);
            chatGroup.setFdRelateUserId(relateUserId);
            String[] ids = relateUserId.split("#");
            String groupName = "";
            for(String id:ids){
                if(StringUtil.isNull(id)){
                    continue;
                }
                try {
                    String accountName = getAccount(id).getFdAccountName();
                    groupName += " | " + accountName;
                }catch (Exception e){
                    logger.error(e.getMessage(),e);
                }
            }
            if(StringUtil.isNotNull(groupName)){
                chatGroup.setFdChatGroupName(groupName.substring(3));
            }
        }
        //群聊
        else{
            String roomName = getRoomName(roomId);
            chatGroup.setFdRoomId(roomId);
            chatGroup.setFdChatGroupName(roomName);
        }
        chatGroup.setFdIsOut(false);
        chatGroup.setFdMd5(md5);
        chatGroup.setFdNewestMsgId(chatDataMain.getFdMsgId());
        chatGroup.setFdNewestMsgTime(chatDataMain.getFdMsgTime());
        thirdWeixinChatGroupService.add(chatGroup);
        return chatGroup;
    }

    /**
     * 更新会话分组的最新消息
     * @param chatGroup
     * @param chatDataMain
     * @throws Exception
     */
    private void updatChatGroupNewsMsg(ThirdWeixinChatGroup chatGroup, ThirdWeixinChatDataMain chatDataMain) throws Exception {
        logger.debug("会话分组最新消息，分组ID:"+chatGroup.getFdId()+"，最新消息ID："+chatDataMain.getFdMsgId());
        long msgTime = chatGroup.getFdNewestMsgTime();
        if(chatDataMain.getFdMsgTime()<=msgTime){
            return;
        }
        chatGroup.setFdNewestMsgId(chatDataMain.getFdMsgId());
        chatGroup.setFdNewestMsgTime(chatDataMain.getFdMsgTime());
        thirdWeixinChatGroupService.update(chatGroup);
    }

    /**
     * 处理会话记录信息
     * @param msgObj 会话内容
     * @throws Exception
     */
    private void addChatDataMain(Long seq, JSONObject msgObj, long sdk, String proxy) throws Exception {
        logger.debug("处理消息，seq:{}，内容：{}",seq,msgObj.toString());
        String msgtype = msgObj.getString("msgtype");
        if(StringUtil.isNull(msgtype)){
            logger.warn("msgtype为空，不处理。消息内容："+msgObj.toString());
            return;
        }
        IChatdataMsgHandler handler = getMsgHandler(msgtype);
        if(handler==null){
            logger.warn("找不到 "+msgtype+" 对应的消息处理器，该消息不处理。消息内容："+msgObj.toString());
            return;
        }
        ThirdWeixinChatDataMain chatDataMain = handler.buildChatDataMain(msgObj, encrypter);
        chatDataMain.setFdSeq(seq);
        String roomId = chatDataMain.getFdRoomId();
        String fromId = chatDataMain.getFdFrom();
        String toId = chatDataMain.getFdTo();
        String md5 = thirdWeixinChatGroupService.genMd5(fromId,toId,roomId);
        //查找对应的会话分组
        ThirdWeixinChatGroup chatGroup = thirdWeixinChatGroupService.findByMd5(md5);
        if(chatGroup==null){
            //新建会话分组
            chatGroup = addChatGroup(chatDataMain,md5);
        }else{
            //更新分组最新消息
            updatChatGroupNewsMsg(chatGroup,chatDataMain);
        }
        chatDataMain.setFdChatGroup(chatGroup);
        if(StringUtil.isNotNull(roomId)){
            //更新群成员列表
            setRoomMember(roomId, chatDataMain.getFdToList());
        }

        if(StringUtil.isNotNull(fromId)){
            ThirdWeixinAccount account = getAccount(fromId);
            if(account!=null){
                chatDataMain.setFdFromName(account.getFdAccountName());
            }
        }
//        String to = chatDataMain.getFdTo();
//        if(StringUtil.isNotNull(to)){
//            if(to.startsWith("wb")){
//                String robotName = getRobotName(to);
//                if(StringUtil.isNotNull(robotName)){
//                    extendData.put("toName",robotName);
//                }
//            }
//        }
//        if(StringUtil.isNotNull(roomId)){
//            String roomName = getRoomName(roomId,chatDataMain.getFdToList());
//            if(StringUtil.isNotNull(roomName)){
//                extendData.put("roomName",roomName);
//            }
//        }
        chatDataMain.setDocCreateTime(new Date());
        chatDataMain.setFdCorpId(corpId);
        if(StringUtil.isNotNull(chatDataMain.getFdUserId())){
            ThirdWeixinAccount account = getAccount(chatDataMain.getFdUserId());
            if(account!=null){
                chatDataMain.setFdUsername(account.getFdAccountName());
            }
        }
        if(StringUtil.isNotNull(chatDataMain.getFdFileId())){
            uploadFile(chatDataMain,sdk, proxy);
        }
        for(ThirdWeixinChatDataFile file:chatDataMain.getInnerFiles()){
            uploadFile(chatDataMain,file, sdk, proxy);
        }
        thirdWeixinChatDataMainService.add(chatDataMain);
    }

    /**
     * 更新会话记录到缓存表
     * @param chatdatas
     * @return
     * @throws Exception
     */
    private Long update2Temp(JSONArray chatdatas) throws Exception {
        TransactionStatus status = null;
        try {
            status = TransactionUtils.beginNewTransaction();
            Long seq_max = 0L;
            for (int i = 0; i < chatdatas.size(); i++) {
                JSONObject obj = chatdatas.getJSONObject(i);
                logger.debug("处理记录："+obj.toString());
                Long seq = obj.getLong("seq");
                String msgid = obj.getString("msgid");
                Integer publickey_ver = obj.getInteger("publickey_ver");
                String encrypt_random_key = obj.getString("encrypt_random_key");
                String encrypt_chat_msg = obj.getString("encrypt_chat_msg");
                //缓存表中以及存在该记录
                if(tempMsgMap.containsKey(msgid)){
                    if(seq>seq_max){
                        seq_max = seq;
                    }
                    continue;
                }
                ThirdWeixinChatDataTemp temp = new ThirdWeixinChatDataTemp();
                temp.setFdCorpId(corpId);
                temp.setFdEncryptRandomKey(encrypt_random_key);
                temp.setFdErrTimes(0);
                temp.setFdHandleStatus(1);
                temp.setFdMsgId(msgid);
                temp.setFdSeq(seq);
                temp.setFdPublickeyVer(publickey_ver);
                temp.setFdEncryptChatMsg(encrypt_chat_msg);
                thirdWeixinChatDataTempService.add(temp);
                if(seq>seq_max){
                    seq_max = seq;
                }
            }
            TransactionUtils.getTransactionManager().commit(status);
            if(seq_max>syncSeq){
                syncSeq = seq_max;
            }
            return seq_max;
        }catch (Exception e){
            if(status!=null){
                TransactionUtils.getTransactionManager().rollback(status);
            }
            throw e;
        }
    }

    private void initDecryptionCipher() throws Exception {
        WeixinWorkConfig config = WeixinWorkConfig.newInstance();
        String privateKey = config.getChatdataSyncPrivateKey();
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        //KeySpec keySpec = new PKCS8EncodedKeySpec(chatdataSyncSecret.getBytes(StandardCharsets.UTF_8));
        //Key priKey = keyFactory.generatePrivate(keySpec);
        KeySpec keySpec = new PKCS8EncodedKeySpec(org.bouncycastle.util.encoders.Base64.decode(privateKey));
        Key priKey = keyFactory.generatePrivate(keySpec);
        decryptionCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding",
                new org.bouncycastle.jce.provider.BouncyCastleProvider());
        decryptionCipher.init(Cipher.DECRYPT_MODE, priKey);

        if("true".equals(config.getChatdataEncryEnable())){
            encrypter = ChatdataUtil.getEncrypter();
        }
        logger.debug("构建RSA解密器成功");
    }

    /**
     * 从同步缓存表中取出数据进行处理
     * @param sdk
     * @throws Exception
     */
    private void handleChatdata(Long sdk, String proxy) throws Exception {
        initDecryptionCipher();
        initMsgMap();
        initUserMap();
        initGroupChatMap();
        initAccountMap();
        try {
            HQLInfo countHql = new HQLInfo();
            countHql.setWhereBlock(
                    "(fdHandleStatus=1 or fdHandleStatus=4) and fdErrTimes<5");
            countHql.setGettingCount(true);
            List<Object> resultList = thirdWeixinChatDataTempService.findValue(countHql);
            Object result = resultList.get(0);
            long count = Long
                    .parseLong(result != null ? result.toString() : "0");
            logger.info("同步缓存表中待处理的数量为:" + count);
            jobContext.logMessage("同步缓存表中待处理的数量为:" + count);
            if (count == 0) {
                return;
            }
            int groupCount = 100;
            int len = (int) count / groupCount;
            for (int k = 0; k <= len; k++) {
                Page page = thirdWeixinChatDataTempService.findPage(
                        "(fdHandleStatus=1 or fdHandleStatus=4) and fdErrTimes<5",
                        "fdSeq asc", 0,
                        groupCount);
                List handleList = page.getList();
                if (handleList.isEmpty()) {
                    logger.debug("【third-weixin-work】待处理队列为空");
                    return;
                }
                for (int i = 0; i < handleList.size(); i++) {
                    ThirdWeixinChatDataTemp temp = (ThirdWeixinChatDataTemp) handleList
                            .get(i);
                    int fdErrTimes = temp.getFdErrTimes();
                    if (fdErrTimes >= 5) {
                        logger.debug("失败次数>=5次，不处理。记录id："+temp.getFdId());
                        continue;
                    }
                    temp.setDocAlterTime(new Date());
                    try {
                        handleChatdata(sdk,temp.getFdId(),proxy);
                        temp.setFdHandleStatus(3);
                    } catch (Exception e) {
                        temp.setFdHandleStatus(4);
                        fdErrTimes = fdErrTimes + 1;
                        temp.setFdErrTimes(fdErrTimes);
                        logger.error(e.getMessage(), e);
                    } finally {
                        thirdWeixinChatDataTempService.update(temp);
                    }
                }
            }
            logger.debug("【third-feishu】消息发送出错重复执行成功！");
        } catch (Exception e) {
            logger.error("【third-feishu】消息发送出错重复执行失败！", e);
            throw e;
        }
        deleteChatDataTemp();
        addRooms();
        updateRoomMembers();
    }

    /**
     * 清理处理成功的记录
     * @throws Exception
     */
    private void deleteChatDataTemp() throws Exception {
        thirdWeixinChatDataTempService.clear();
    }

    /**
     * 解密会话内容
     * @param sdk
     * @param temp
     * @return
     * @throws Exception
     */
    private String decryChatdatas(long sdk, ThirdWeixinChatDataTemp temp) throws Exception {
        String encrypt_random_key = temp.getFdEncryptRandomKey();
        String encrypt_chat_msg = temp.getFdEncryptChatMsg();
        long msg = Finance.NewSlice();
        try {
            int ret = Finance.DecryptData(sdk, getEncryKey(encrypt_random_key), encrypt_chat_msg, msg);
            if (ret != 0) {
                throw new Exception("decry chatdata fail. ret: " + ret);
            }
            String decryedMsg = Finance.GetContentFromSlice(msg);
            return decryedMsg;
        }catch (Exception e){
            logger.error("解密内容失败，encrypt_random_key："+encrypt_random_key+"，encrypt_chat_msg："+encrypt_chat_msg,e);
            throw e;
        }finally {
            Finance.FreeSlice(msg);
        }
    }

    /**
     * 处理单条会话记录
     * @param sdk
     * @param tempId 缓存表fdId
     * @throws Exception
     */
    private void handleChatdata(long sdk, String tempId, String proxy) throws Exception {
        logger.debug("处理缓存表中的消息，记录fdId:"+tempId);
        TransactionStatus status = null;
        try {
            status = TransactionUtils.beginNewTransaction();
            ThirdWeixinChatDataTemp temp = (ThirdWeixinChatDataTemp)thirdWeixinChatDataTempService.findByPrimaryKey(tempId);
            String decryedMsg = decryChatdatas(sdk,temp);
            logger.debug("解密后的内容："+decryedMsg);
            JSONObject msgObj = JSONObject.parseObject(decryedMsg);
            String msgid = msgObj.getString("msgid");
            if(!msgMap.containsKey(msgid)){
                addChatDataMain(temp.getFdSeq(),msgObj,sdk,proxy);
            }
            TransactionUtils.getTransactionManager().commit(status);
        }catch (Exception e){
            if(status!=null){
                TransactionUtils.getTransactionManager().rollback(status);
            }
            throw e;
        }
    }

    private IChatdataMsgHandler getMsgHandler(String msgtype){
        msgtype = msgtype.replaceAll("_","");
        IChatdataMsgHandler handler = (IChatdataMsgHandler)SpringBeanUtil.getBean(msgtype+"Handler");
        return handler;
    }

    /**
     * 获取群名称
     * @param roomId 群ID
     * @return
     */
    private String getRoomName(String roomId) throws Exception {
        //如果已经存在群记录
        if(groupChatMap.containsKey(roomId)){
            return groupChatMap.get(roomId);
        }
        try {
            //调用企业微信接口获取群信息
            JSONObject o = WxworkUtils.getWxworkApiService().getGroupChat(roomId);
            String name = o.getString("roomname");
            groupChatMap.put(roomId,name);
            groupChatMap2add.put(roomId,o.toString());
            return name;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw e;
        }
    }

    /**
     * 记录接收人员到map中，后面需要根据该信息更新群成员列表（保存所有历史成员）
     * groupChatMap2Update的value为需要更新的成员集合
     *
     * @param roomId
     * @param toList 成员ID列表
     * @return
     * @throws Exception
     */
    private void setRoomMember(String roomId, String toList) throws Exception {
        if(StringUtil.isNotNull(toList)) {
            logger.debug("更新会话群成员列表，toList:"+toList);
            JSONArray array = JSONArray.parseArray(toList);
            if(array!=null) {
                Set<String> idSet = null;
                if (groupChatMap2Update.containsKey(roomId)) {
                    idSet = groupChatMap2Update.get(roomId);
                } else {
                    idSet = new HashSet<>();
                    groupChatMap2Update.put(roomId, idSet);
                }
                List<String> list = JSONObject.parseArray(array.toJSONString(), String.class);
                idSet.addAll(list);
            }
        }
    }

    /**
     * 获取微信账号
     * @param accountId
     * @return
     * @throws Exception
     */
    private ThirdWeixinAccount getAccount(String accountId) throws Exception {
        String fdId = accountMap.get(accountId);
        ThirdWeixinAccount account = null;
        if(fdId==null){
            account = addAccount(accountId);
        }else{
            account = (ThirdWeixinAccount)thirdWeixinAccountService.findByPrimaryKey(fdId,null,true);
        }
        return account;
    }

    /**
     * 添加群记录
     * @throws Exception
     */
    private void addRooms() throws Exception {
        if(groupChatMap2add.isEmpty()){
            return ;
        }
        logger.debug("新建群信息处理，需要新增的群信息记录数："+groupChatMap2add.size());
        for(String roomId:groupChatMap2add.keySet()){
            String detail = groupChatMap2add.get(roomId);
            JSONObject o = JSONObject.parseObject(detail);
            String roomname = o.getString("roomname");
            String creator = o.getString("creator");
            Long room_create_time = o.getLong("room_create_time");
            String notice = o.getString("notice");
            JSONArray members = o.getJSONArray("members");

            ThirdWeixinGroupChat groupChat = new ThirdWeixinGroupChat();
            groupChat.setDocAlterTime(new Date());
            groupChat.setDocCreateTime(new Date());
            groupChat.setFdRoomId(roomId);
            groupChat.setFdCorpId(corpId);
            if(members!=null){
                List<ThirdWeixinAccount> accounts = new ArrayList<>();
                for(int i=0;i<members.size();i++){
                    JSONObject member = members.getJSONObject(i);
                    String memberid = member.getString("memberid");
                    try {
                        ThirdWeixinAccount account = getAccount(memberid);
                        if (account != null) {
                            accounts.add(account);
                        }
                    }catch (Exception e){
                        logger.error(e.getMessage(),e);
                    }
                }
                groupChat.setFdMember(accounts);
            }
            if(StringUtil.isNull(groupChat.getFdRoomName())){
                String groupName = "";
                for(ThirdWeixinAccount account:groupChat.getFdMember()){
                    groupName += account.getFdAccountName()+"、";
                    if(groupName.length()>50){
                        break;
                    }
                }
                if(groupName.length()>0){
                    groupName = groupName.substring(0,groupName.length()-1);
                    groupChat.setFdRoomName(groupName);
                }
                ThirdWeixinChatGroup chatGroup = thirdWeixinChatGroupService.findByRoomId(roomId);
                if(chatGroup!=null){
                    if(StringUtil.isNull(chatGroup.getFdChatGroupName())){
                        chatGroup.setFdChatGroupName(groupName);
                        thirdWeixinChatGroupService.update(chatGroup);
                    }
                }
            }
            groupChat.setFdIsDissolve(false);
            groupChat.setFdOwnerFdid(userMap.get(creator));
            groupChat.setFdRoomCreateTime(room_create_time);
            groupChat.setFdRoomCreator(creator);
            groupChat.setFdRoomName(roomname);
            groupChat.setFdRoomNotice(notice);
            try {
                thirdWeixinGroupChatService.add(groupChat);
            }catch (Exception e){
                logger.error(e.getMessage(),e);
                jobContext.logMessage(e.getMessage());
                throw e;
            }
        }
    }

    /**
     * 创建微信账号
     * @param memberid
     * @return
     * @throws Exception
     */
    private ThirdWeixinAccount addAccount(String memberid) throws Exception {
        ThirdWeixinAccount account = new ThirdWeixinAccount();
        account.setFdAccountId(memberid);
        String name = memberid;
        try {
            logger.debug("添加账号，账号ID："+memberid);
            //如果是机器人
            if (memberid.startsWith("wb")) {
                account.setFdAccountType(3);
                JSONObject o = WxworkUtils.getWxworkApiService().getRobotInfo(memberid);
                name = o.getString("name");
            }
            //如果是外部联系人
            else if (memberid.startsWith("wo") || memberid.startsWith("wm")) {
                account.setFdAccountType(2);
                if(userMap.containsKey(memberid)){
                    account.setFdEkpId(userMap.get(memberid));
                }
                JSONObject o = WxworkUtils.getWxworkApiService().getExternalContact(memberid);
                name = o.getString("name");
            }
            // 如果是内部用户
            else {
                account.setFdAccountType(1);
                if(userMap.containsKey(memberid)){
                    account.setFdEkpId(userMap.get(memberid));
                }
                JSONObject o = WxworkUtils.getWxworkApiService().getUserInfo(memberid);
                name = o.getString("name");
            }
        }catch (Exception e){
            logger.error("获取账号信息失败",e);
            //throw e;
        }
        account.setFdAccountName(name);
        String fdId = thirdWeixinAccountService.add(account);
        accountMap.put(account.getFdAccountId(),fdId);
        return account;
    }

    private boolean contains(List<ThirdWeixinAccount> members,String memberId) {
        for(ThirdWeixinAccount member:members) {
            if(member.getFdAccountId().equals(memberId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 更新群组成员
     * @throws Exception
     */
    private void updateRoomMembers() throws Exception {
        if(groupChatMap2Update.isEmpty()){
            return ;
        }
        logger.debug("更新群成员列表，需要更新的记录数："+groupChatMap2Update.size());
        for(String roomId:groupChatMap2Update.keySet()){
            Set<String> memberIds = groupChatMap2Update.get(roomId);
            if(memberIds==null || memberIds.isEmpty()){
                continue;
            }
            ThirdWeixinGroupChat groupChat = thirdWeixinGroupChatService.findByRoomId(roomId);
            List<ThirdWeixinAccount> members = groupChat.getFdMember();
            if(members==null) {
                members = new ArrayList<>();
            }
            List<ThirdWeixinAccount> members_result = new ArrayList<>();
            for(String memberId:memberIds) {
                if(contains(members,memberId)) {
                    continue;
                }
                try {
                    members.add(getAccount(memberId));
                }catch (Exception e){
                    logger.error(e.getMessage(),e);
                }
            }
            members_result.addAll(members);
            groupChat.setFdMember(members_result);
            if(StringUtil.isNull(groupChat.getFdRoomName())){
                String groupName = "";
                for(ThirdWeixinAccount account:groupChat.getFdMember()){
                    groupName += account.getFdAccountName()+"、";
                    if(groupName.length()>50){
                        break;
                    }
                }
                if(groupName.length()>0){
                    groupName = groupName.substring(0,groupName.length()-1);
                    groupChat.setFdRoomName(groupName);
                }
            }
            try {
                thirdWeixinGroupChatService.update(groupChat);
            }catch (Exception e){
                logger.error(e.getMessage(),e);
                jobContext.logMessage(e.getMessage());
            }
        }
    }

    @Override
    public void backupChatData(SysQuartzJobContext jobContext) {
        try {
            thirdWeixinChatDataMainService.backUp();
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            jobContext.logError("会话记录归档失败",e);
        }
    }

    private ByteArrayOutputStream getMideaData(long sdk, String sdkfileid,String proxy) throws Exception {
        // 媒体文件每次拉取的最大size为512k，因此超过512k的文件需要分片拉取。若该文件未拉取完整，sdk的IsMediaDataFinish接口会返回0，同时通过GetOutIndexBuf接口返回下次拉取需要传入GetMediaData的indexbuf。
        // indexbuf一般格式如右侧所示，”Range:bytes=524288-1048575“，表示这次拉取的是从524288到1048575的分片。单个文件首次拉取填写的indexbuf为空字符串，拉取后续分片时直接填入上次返回的indexbuf即可。
        String indexbuf = "";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        long media_data = Finance.NewMediaData();
        int i=0;
        try {
            while (i<10000) {
                i++;
                // 每次使用GetMediaData拉取存档前需要调用NewMediaData获取一个media_data，在使用完media_data中数据后，还需要调用FreeMediaData释放。
                int ret = Finance.GetMediaData(sdk, indexbuf, sdkfileid, proxy, null, 30, media_data);
                if (ret != 0) {
                    throw new Exception("getmediadata error。ret:" + ret);
                }
                logger.debug("getmediadata outindex len:{}, data_len:{}, is_finis:{}", Finance.GetIndexLen(media_data), Finance.GetDataLen(media_data), Finance.IsMediaDataFinish(media_data));

                // 大于512k的文件会分片拉取，此处需要使用追加写，避免后面的分片覆盖之前的数据。
                outputStream.write(Finance.GetData(media_data));

                if (Finance.IsMediaDataFinish(media_data) == 1) {
                    break;
                } else {
                    // 获取下次拉取需要使用的indexbuf
                    indexbuf = Finance.GetOutIndexBuf(media_data);
                    Finance.FreeMediaData(media_data);
                    media_data = Finance.NewMediaData();
                }
            }
            return outputStream;
        }catch (Exception e){
            outputStream.close();
            logger.debug("通过接口获取文件数据失败，sdkfileid："+sdkfileid,e);
            throw e;
        }finally {
            Finance.FreeMediaData(media_data);
        }
    }

    private static final String[] officeExt = {"docx","docm","dotx","dotm","xlsx","xlsm","xltx","xltm","xlsb","xlam","pptx","pptm","ppsx","ppsx","potx","potm","ppam","doc","xls","ppt","mdb"};

    private static final String[] picExt = {"webp","bmp","pcx","tif","gif","jpeg","tga","exif","fpx","svg","psd","cdr","pcd","dxf","ufo","eps","png","hdri","wmf","emf"};

    private boolean isOffice(String fileExt){
        return Arrays.asList(officeExt).contains(fileExt);
    }

    private boolean isPic(String fileExt){
        return Arrays.asList(picExt).contains(fileExt);
    }

    private String getAttType(String msgType, ThirdWeixinChatDataFile file){
        String fileName = file.getFileName();
        String fileExt = file.getFileExt();
        if("image".equals(msgType) || "emotion".equals(msgType)){
            return "pic";
        }
        if(StringUtil.isNull(fileExt)){
            if(StringUtil.isNotNull(fileName) && fileName.contains(".")){
                fileExt = fileName.substring(fileName.lastIndexOf(".")+1);
            }
        }
        if(StringUtil.isNotNull(fileExt)){
            if(isOffice(fileExt)){
                return "office";
            }
            if(isPic(fileExt)){
                return "pic";
            }
        }
        return "byte";
    }

    private String getContentType(String msgType, String fileName){
        if("image".equals(msgType) || "emotion".equals(msgType)){
            return "image/png";
        }
        if("voice".equals(msgType)){
            return "audio/ogg";
        }
        return FileMimeTypeUtil.getContentType(fileName);
    }

    private void uploadFile(ThirdWeixinChatDataMain chatDataMain, long sdk, String proxy) throws Exception {
        ThirdWeixinChatDataFile file = new ThirdWeixinChatDataFile(chatDataMain.getFdFileId(),chatDataMain.getFdTitle(),chatDataMain.getFdFileSize(),chatDataMain.getFdFileMd5(),chatDataMain.getFdFileExt());
        uploadFile(chatDataMain,file,sdk,proxy);
    }

    private void uploadFile(ThirdWeixinChatDataMain chatDataMain, ThirdWeixinChatDataFile file, long sdk, String proxy) throws Exception {
        try {
            String sdkfileid = file.getFileId();
            logger.debug("同步文件，fileId:" + sdkfileid);
            ByteArrayOutputStream byteArrayOutputStream = getMideaData(sdk,sdkfileid,proxy);
            if (byteArrayOutputStream != null) {
                ISysAttUploadService sysAttUploadService = (ISysAttUploadService) SpringBeanUtil.getBean("sysAttUploadService");
                String fileName = file.getFileName();
                if(StringUtil.isNull(fileName)){
                    fileName = sdkfileid;
                }
                String fileId = sysAttUploadService.addStreamFile(byteArrayOutputStream.toByteArray(), fileName);
                logger.info("上传文件到附件机制成功，文件fileId:" + fileId);

                Date day = new Date();
                SysAttMain sysAttMain = new SysAttMain();
                sysAttMain.setFdSize(file.getFileSize()==null?null:Double.valueOf(file.getFileSize()));
                sysAttMain.setFdKey(null);
                sysAttMain.setFdFileName(fileName);
                sysAttMain.setFdAttType(getAttType(chatDataMain.getFdMsgType(),file));
                sysAttMain.setFdModelId(chatDataMain.getFdId());
                sysAttMain.setFdModelName(chatDataMain.getClass().getName());
                String fdKey = sdkfileid;
                if(fdKey.length()>450){
                    fdKey = fdKey.substring(0,450);
                }
                sysAttMain.setFdKey(fdKey);
                sysAttMain.setDocCreateTime(day);
                sysAttMain.setFdUploadTime(day);
                sysAttMain.setFdContentType(getContentType(chatDataMain.getFdMsgType(),fileName));
                sysAttMain.setFdVersion(1);
                sysAttMain.setFdFileId(fileId);

                ISysAttMainCoreInnerService sysAttMainCoreInnerService = (ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService");
                sysAttMainCoreInnerService.add(sysAttMain);
            }
        } catch (Exception e1) {
            logger.error(e1.getMessage(), e1);
            throw e1;
        }
    }
}
