var exec = require('cordova/exec');

exports.logSentFriendRequestEvent = function (eventName,params, success, error) {
    exec(success, error, 'FacebookEventOS', 'logSentFriendRequestEvent', [eventName,params]);
};
