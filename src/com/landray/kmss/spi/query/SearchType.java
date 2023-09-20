package com.landray.kmss.spi.query;

import com.landray.kmss.util.StringUtil;

public enum SearchType {
	NE {
		@Override
        public String getText() {
			return "!=";
		}
	},
	GT {
		@Override
        public String getText() {
			return ">";
		}
	},
	LT {
		@Override
        public String getText() {
			return "<";
		}
	},
	EQ {
		@Override
        public String getText() {
			return "=";
		}
	},
	GE {
		@Override
        public String getText() {
			return ">=";
		}
	},
	LE {
		@Override
        public String getText() {
			return "<=";
		}
	},
	BT {
		@Override
        public String getText() {
			return "between";
		}
	},
	IN {
		@Override
        public String getText() {
			return "in";
		}
	},
	LIKE {
		@Override
        public String getText() {
			return "like";
		}
	},
	PREFIX {
		@Override
        public String getText() {
			return "prefix";
		}
	},
	SUFFIX {
		@Override
        public String getText() {
			return "suffix";
		}
	},
	LIST {
		@Override
        public String getText() {
			return "$collection$";
		}
	};
	public static SearchType getTypeByString(String str) throws Exception {
		if (StringUtil.isNull(str)) {
			throw new Exception("字符串错误");
		}
		if ("eq".equals(str.toLowerCase())) {
			return SearchType.EQ;
		}
		if ("ne".equals(str.toLowerCase())) {
			return SearchType.NE;
		}
		if ("gt".equals(str.toLowerCase())) {
			return SearchType.GT;
		}
		if ("lt".equals(str.toLowerCase())) {
			return SearchType.LT;
		}
		if ("ge".equals(str.toLowerCase())) {
			return SearchType.GE;
		}
		if ("le".equals(str.toLowerCase())) {
			return SearchType.LE;
		}
		if ("bt".equals(str.toLowerCase())) {
			return SearchType.BT;
		}
		if ("in".equals(str.toLowerCase())) {
			return SearchType.IN;
		}
		if ("like".equals(str.toLowerCase())) {
			return SearchType.LIKE;
		}
		if ("prefix".equals(str.toLowerCase())) {
			return SearchType.PREFIX;
		}
		if ("suffix".equals(str.toLowerCase())) {
			return SearchType.SUFFIX;
		}
		if ("list".equals(str.toLowerCase())) {
			return SearchType.LIST;
		}
		throw new Exception("字符串错误");
	}

	public abstract String getText();
}
