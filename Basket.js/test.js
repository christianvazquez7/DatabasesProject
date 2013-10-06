var gcm = require('node-gcm');



// or with object values
var message = new gcm.Message({
    collapseKey: 'demo',
    delayWhileIdle: true,
    timeToLive: 3,
    data: {
        key1: 'message1',
        key2: 'message2'
    }
});

var sender = new gcm.Sender('AIzaSyCTFn1fBSl-7jcUgWIDb6SE17qiaoFpr6o');
var registrationIds = [];





// At least one required
registrationIds.push('APA91bEs805ooHsghZIjy8a9QwjgvsNIBkoXQNMh3IuCEBJAJ6GgparizHQlOO2RUWfPkg7tBkmTkYg2KeuRSJHCmPKAxGm08VeFhCa9gscOgbVmC8NQR47p09yge5Ie-nnqR_sxyxR2Xke9aSQai7PAC15sUod77Q');
/**
 * Parameters: message-literal, registrationIds-array, No. of retries, callback-function
 */
sender.send(message, registrationIds, 4, function (err, result) {
    console.log(result);
});