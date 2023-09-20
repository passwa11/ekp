package com.landray.kmss.km.forum.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.landray.kmss.km.forum.model.KmForumCategory;
import com.landray.kmss.km.forum.model.KmForumTopic;
import com.landray.kmss.sys.ftsearch.config.IFullTextPropertyGetter;
import com.landray.kmss.sys.organization.model.SysOrgElement;

public class ForumFullTextPropertyGetter implements IFullTextPropertyGetter {

	@Override
	public String getPropertyToString(String proerty, Object model) {
		if ("authPermissions".equalsIgnoreCase(proerty)) {
			if (model instanceof KmForumTopic)
			{
				KmForumTopic topic = (KmForumTopic) model;
				KmForumCategory categ = topic.getKmForumCategory();
				if (categ.getAuthVisitFlag()) {
					return "all";
				}
				// 可访问者
				List readers = categ.getAuthReaders();

				// 可发帖者
				List allreaders = categ.getAuthAllReaders();

				// 作者也为可读者,防止作者权限调整为不是可发帖者也不在可访问者中
				SysOrgElement poster = topic.getFdPoster();

				// 可访问者可发帖者都为可阅读者
				List<String> docReadIds = new ArrayList<>();
				if (StringUtils.isNotBlank(poster.getFdId())) {
					docReadIds.add(poster.getFdId());
				}
				for (Object reader : readers) {
					if (reader instanceof SysOrgElement) {
						SysOrgElement orgelement = (SysOrgElement) reader;
						docReadIds.add(orgelement.getFdId());
					}
				}
				for (Object reader : allreaders) {
					if (reader instanceof SysOrgElement) {
						SysOrgElement orgelement = (SysOrgElement) reader;
						docReadIds.add(orgelement.getFdId());
					}
				}
				String value = null;
				if (!docReadIds.isEmpty()) {
					value = StringUtils.join(docReadIds, " ");
				} else {
					value = "all";
				}

				return value;
			}

		}
		return null;
	}

}
