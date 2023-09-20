{
	"order": 101,
	"index_patterns": [
		"syslog_useroper_*"
	],
	"settings": {
		"number_of_shards": 5,
		"refresh_interval": "60s"
	},
	"aliases": {},
	"mappings": {
		"com.landray.kmss.sys.log.model.SysLogUserOper": {
			"properties": {
				"fdId": {
					"type": "keyword"
				},
				"fdEquipment": {
					"type": "keyword"
				},
				"fdIp": {
					"type": "keyword"
				},
				"fdCreateTime": {
					"type": "date",
					"format": "yyyy-MM-dd HH:mm:ss"
				},
				"fdReportTime": {
					"type": "date",
					"format": "yyyy-MM-dd HH:mm:ss"
				},
				"fdEventType": {
					"type": "text"
				},
				"fdSuccess": {
					"type": "integer"
				},
				"fdSecret": {
					"type": "keyword"
				},
				"fdSign": {
					"type": "keyword"
				},
				"fdStatus": {
					"type": "integer"
				},
				"fdRequest": {
					"type": "object",
					"dynamic": false,
					"properties": {
						"fdUrl": {
							"type": "keyword"
						},
						"fdMethod": {
							"type": "keyword",
							"ignore_above": 256
						},
						"fdUserAgent": {
							"type": "text",
							"index": false
						},
						"fdReferer": {
							"type": "text",
							"index": false
						},
						"fdFullUrl": {
							"type": "text",
							"index": false
						}
					}
				},
				"fdVersion": {
					"type": "keyword"
				},
				"fdOperLoginName": {
					"type": "keyword"
				},
				"fdOperator": {
					"type": "keyword"
				},
				"fdOperatorId": {
					"type": "keyword"
				},
				"fdUserGrade": {
					"type": "integer"
				},
				"fdModelDesc": {
					"type": "text"
				},
				"fdModelName": {
					"type": "keyword"
				},
				"fdParaMethod": {
					"type": "keyword"
				},
				"fdModelId": {
					"type": "keyword"
				},
				"fdAuditId": {
					"type": "keyword"
				},
				"fdSource": {
					"type": "keyword"
				},
				"fdTenantId": {
					"type": "integer"
				},
				"fdGroup": {
					"type": "keyword"
				},
				"fdServiceIp": {
					"type": "keyword"
				},
				"fdModule": {
					"type": "keyword"
				},
				"fdModuleDesc": {
					"type": "keyword"
				},
				"fdTraceId": {
					"type": "keyword"
				},
				"fdCreateDate": {
					"type": "long"
				},
				"fdEndDate": {
					"type": "long"
				},
				"fdOperation": {
					"type": "keyword"
				},
				"fdAuditDesc": {
					"type": "text",
					"index": false
				},
				"fdContent": {
					"type": "object",
					"dynamic": false,
					"properties": {
						"param": {
							"type": "object",
							"dynamic": false,
							"properties": {
								"_mainModel_fdId": {
									"type": "keyword"
								},
								"_mainModel_fdModelName": {
									"type": "keyword"
								}
							}
						},
						"add": {
							"type": "object",
							"dynamic": false,
							"properties": {
								"fdId": {
									"type": "keyword"
								},
								"displayName": {
									"type": "keyword"
								}
							}
						},
						"update": {
							"type": "object",
							"dynamic": false,
							"properties": {
								"fdId": {
									"type": "keyword"
								},
								"displayName": {
									"type": "keyword"
								}
							}
						},
						"delete": {
							"type": "object",
							"dynamic": false,
							"properties": {
								"fdId": {
									"type": "keyword"
								},
								"displayName": {
									"type": "keyword"
								}
							}
						},
						"find": {
							"type": "object",
							"dynamic": false,
							"properties": {
								"fdId": {
									"type": "keyword"
								},
								"displayName": {
									"type": "keyword"
								}
							}
						},
						"message": {
							"type": "object",
							"dynamic": false,
							"properties": {
								"info": {
									"type": "text",
									"index": false
								},
								"error": {
									"type": "text",
									"index": false
								}
							}
						},
						"audit": {
							"type": "object",
							"dynamic": false,
							"properties": {
								"fdId": {
									"type": "keyword"
								},
								"fields": {
									"type": "object",
									"dynamic": false,
									"properties": {
										"auditRecords": {
											"type": "object",
											"dynamic": false,
											"properties": {
												"update": {
													"type": "object",
													"dynamic": false,
													"properties": {
														"fdId": {
															"type": "keyword"
														},
														"displayName": {
															"type": "keyword"
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}