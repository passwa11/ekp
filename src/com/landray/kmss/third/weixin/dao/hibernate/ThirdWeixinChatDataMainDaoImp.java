package com.landray.kmss.third.weixin.dao.hibernate;

import java.util.Calendar;
import java.util.Date;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.weixin.dao.IThirdWeixinChatDataMainDao;
import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;
import com.landray.kmss.third.weixin.work.model.ThirdWxWorkConfig;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import org.apache.commons.lang.StringUtils;

public class ThirdWeixinChatDataMainDaoImp extends BaseDaoImp implements IThirdWeixinChatDataMainDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdWeixinChatDataMain thirdWeixinChatDataMain = (ThirdWeixinChatDataMain) modelObj;
        if (thirdWeixinChatDataMain.getDocCreateTime() == null) {
            thirdWeixinChatDataMain.setDocCreateTime(new Date());
        }
        return super.add(thirdWeixinChatDataMain);
    }

    @Override
    public void backUp() throws Exception {
        String[] bak_field = { "fd_id", "fd_seq", "fd_msg_id", "fd_msg_type", "fd_msg_action", "fd_from", "fd_to_list", "fd_room_id", "fd_msg_time", "fd_content", "fd_encry_type", "fd_file_id", "fd_file_md5", "fd_file_size", "fd_pre_msg_id", "fd_agree_userid", "fd_agree_time", "fd_play_length", "fd_corp_name", "fd_user_id", "fd_longitude", "fd_latitude", "fd_address", "fd_title", "fd_zoom", "fd_emotion_type", "fd_width", "fd_height", "fd_to", "fd_file_ext", "fd_link_url", "fd_image_url", "fd_username", "fd_display_name", "fd_vote_type", "fd_vote_id", "fd_extend_content", "fd_meeting_id", "doc_create_time", "fd_corp_id", "fd_from_name", "fd_chat_group_id" };
        String bak_field_String = StringUtils.join(bak_field, ",");
        Calendar backupCal = Calendar.getInstance();
        // 默认设定归档时间
        int month = Integer.valueOf(WeixinWorkConfig.newInstance().getChatdataBakMonth());
        backupCal.add(Calendar.MONTH, -month);
        Date backupDate = backupCal.getTime();
        getSession().createNativeQuery(
                "insert into third_weixin_chat_data_bak ("
                        + bak_field_String + ") select " + bak_field_String
                        + " from third_weixin_chat_data_main "
                        + " where doc_create_time<:backupdate").setDate("backupdate",
                backupDate).executeUpdate();

    }
}
