
var OdysseyPushNotification = function() {};
alert("coucou");

OdysseyPushNotification.prototype.setAuthInfos = function(successCallback, errorCallback, user, password) {
    if (errorCallback == null) { errorCallback = function() {}}
	alert("enter setAuthInfos in JS");
    if (typeof errorCallback != "function")  {
        console.log("OdysseyPushNotification.setAuthInfos failure: failure parameter not a function");
        return
    }

    if (typeof successCallback != "function") {
        console.log("OdysseyPushNotification.setAuthInfos failure: success callback parameter must be a function");
        return
    }

    cordova.exec(successCallback, errorCallback, "WSInvoker", "setAuthInfos", [{"user": user,"password": password,}]);
};

if(!window.plugins) {
    window.plugins = {};
}
if (!window.plugins.OdysseyPushNotification) {
    window.plugins.OdysseyPushNotification = new OdysseyPushNotification();
}

if (module.exports) {
  module.exports = OdysseyPushNotification;
}

// cordova.define("com.odysseymessaging.OdysseyPushNotification", function(require, exports, module) {
               // function OdysseyPushNotification() {}

               // OdysseyPushNotification.prototype.setAuthInfos = function (user, password, successCallback, errorCallback) {
			   // alert("Begin OdysseyPushNotification.js / function setAuthInfos");
               // cordova.exec(
                            // successCallback,
                            // errorCallback,
                            // 'WSInvoker',
                            // 'setAuthInfos',
                            // [{
								// "user": user,
								// "password": password,
                             // }]
                            // );
               // };

			   
               // OdysseyPushNotification.prototype.getTest = function (successCallback, errorCallback) {
               // cordova.exec(
                            // successCallback,
                            // errorCallback,
                            // 'WSInvoker',
                            // 'getTest',
                            // []
                            // );
               // };
              
               // module.exports = new OdysseyPushNotification();
// });
               

