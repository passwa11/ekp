{
  "order" : 100,
  "index_patterns": ["syslog_notify_*"],
  "settings": {
    "number_of_shards": 5,
    "refresh_interval":"60s"
  },
 "aliases" : {
  },
  "mappings": {
    "com.landray.kmss.sys.log.model.SysLogNotify": {             
      "properties": {
            "fdId":{
              "type": "keyword"
            },
            "fdSubject": {
                "type": "text",
                "index":false
            }, 
            "fdCreateTime": {
                "type":   "date",
                "format": "yyyy-MM-dd HH:mm:ss"
            }, 
            "fdNotifyType": {
                "type": "keyword"
            },
             "fdDesc": {
                "type": "text",
                "index":false
            },
             "fdNotifyTargets": {
                "type": "text",
                "index":false
            }
        }
      }
    }
}