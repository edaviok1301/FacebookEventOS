var exec = require('cordova/exec');

exports.logSentFriendRequestEvent = function (arg0, success, error) {
    exec(success, error, 'FacebookEventOS', 'logSentFriendRequestEvent', [arg0]);
};
