
var exec = require('cordova/exec');

var hikvision = {
  startMonitor:function (monitorArr){
    exec(null,null,"Hikvision","startMonitor",[monitorArr]);
  },
};

module.exports = hikvision;
