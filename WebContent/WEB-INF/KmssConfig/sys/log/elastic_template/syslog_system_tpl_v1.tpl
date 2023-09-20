{
  "order" : 100,
  "index_patterns": ["syslog_system_*"],
  "settings": {
    "number_of_shards": 5
  },
 "aliases" : {
  },
  "mappings": {
    "com.landray.kmss.sys.log.model.SysLogSystem": {             
      "properties": {
            "fdId":{
              "type": "keyword"
            },
            "fdSubject": {
                "type": "text"
            }, 
            "fdStartTime": {
                "type":   "date",
                "format": "yyyy-MM-dd HH:mm:ss"
            }, 
            "fdEndTime": {
                "type":   "date",
                "format": "yyyy-MM-dd HH:mm:ss"
            }, 
            "fdServiceBean": {
                "type": "keyword"
            }, 
             "fdServiceMethod": {
                "type": "keyword"
            }, 
             "fdRequestMsg": {
                "type": "text",
                "index":false
            }, 
             "fdResponseMsg": {
                "type": "text",
                "index":false
            }, 
            "fdSuccess": {
                "type": "integer"
            }, 
            "fdClientIp": {
                "type": "keyword"
            },
            "fdServiceIp": {
                "type": "keyword"
            },
            "fdTaskDuration": {
                "type": "long"
            },
            "fdType": {
                "type": "integer"
            },
             "fdDesc": {
                "type": "text",
                "index":false
            },
             "fdRequestHeader": {
                "type": "text",
                "index":false
            },
             "fdResponseHeader": {
                "type": "text",
                "index":false
            },
             "fdOriginUri": {
                "type": "keyword"
            }
        }
      }
    }
}