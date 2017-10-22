var admin = require("firebase-admin");

var serviceAccount = require("./keys/service_account.json");
var defaultData = require("./defaultData.json");
var Pango = require('./pango.js');

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: "https://rewards-6baf9.firebaseio.com"
});

const pango = new Pango(admin);
pango.addReward(defaultData.newRecord);
