package com.landray.kmss.sys.oms.in.interfaces;

public enum ValueMapTo {
	LDAPDN {
		@Override
        public String getColumnName() {
			return "fdLdapDN";

		}
	},
	NUMBER {
		@Override
        public String getColumnName() {
			return "fdNo";

		}
	},
	KEYWORD {
		@Override
        public String getColumnName() {
			return "fdKeyword";

		}
	},
	IMPORTINFO {
		@Override
        public String getColumnName() {
			return "fdImportInfo";

		}
	},
	ID {
		@Override
        public String getColumnName() {
			return "fdId";

		}
	};
	public abstract String getColumnName();
}
