var exec = require('cordova/exec');

exports.logSentEvent = function (eventName,params, success, error) {
    exec(success, error, 'FacebookEventOS', 'logSentEvent', [eventName,params]);
};

exports.logSendPurchaseEvent = function (eventName,params, success, error) {
    exec(success, error, 'FacebookEventOS', 'logSendPurchaseEvent', [eventName,params]);
};