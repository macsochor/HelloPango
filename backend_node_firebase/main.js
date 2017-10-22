var admin = require("firebase-admin");

var serviceAccount = require("./service_account.json");
var defaultData = require("./defaultData.json");

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: "https://rewards-6baf9.firebaseio.com"
});

// The topic name can be optionally prefixed with "/topics/".

// See the "Defining the message payload" section below for details
// on how to define a message payload.

class Pango {
    constructor(db) {
        this.db = db;
        this.ref = this.db.ref("backend");
    }
    addRecord(record, location = "offers") {
        const locationRef = this.ref.child(location);
        return new Promise((resolve, reject) => {
            const newRecord = locationRef.push();
            newRecord.set(record).then(function (response) {
                console.log("Record was added");
                resolve(newRecord.key);
            })
            .catch(function (error) {
                console.log("Error adding record:", error);
                reject(error);
            });
          });
        return newRecord;
    }
    getRecords() {
        this.ref.once("value", function(data) {
            console.log("YOOOO!!!", data.val());
          });
    }
    deleteRecords() {
        this.ref.remove().then(function (response) {
            console.log("All records were deleted", response);
        })
        .catch(function (error) {
            console.log("Error deleting records", error);
        });
    }
    addReward(reward) {
        const that = this
        this.addRecord(reward).then(function (key) {
            console.log("YOOO", key);
            that.sendNotifiction({
                title: `you just earned $${reward.amount} reward from ${reward.merchantName}`,
                body: "Thanks!"
            }, Object.assign({objectId: key}, reward));
    
        });

    }
    indexRewards(rewards) {
        const allRewards = rewards.filter(reward => reward.type === "reward")
        const offers = rewards.filter(reward => reward.type === "offer")
        const progress = rewards.filter(reward => reward.type === "progress")
        const feed = [];
        let i = 0;
        let j = 0;
        while (i < allRewards.length) {
            feed.push(allRewards[i]);
            if (i % 3 == 0) {
                const nextOffer = offers[j];
                if (nextOffer) {
                    feed.push(nextOffer);
                    j++;    
                }
            }
            i++;
        }
        const realFeed = [...feed, ...progress];        
        realFeed.forEach( (f,i) => f.index = `${i + 1}`);
        console.log(realFeed);
        return realFeed;
    }
    addRecords(records, location = "offers") {
        records.forEach( record => pango.addRecord(record, location) );
    }
    sendNotifiction(notification = {}, data = {}) {
        var topic = "offer";
        var payload = {
            notification: notification,
            data: data
        };
        admin.messaging().sendToTopic(topic, payload)
            .then(function (response) {
                console.log("Successfully sent message:", response);
            })
            .catch(function (error) {
                console.log("Error sending message:", error);
            });
    }

}

var record = {
    merchantName: "Macy's",
    amount: "5.00",
    merchantLogoUrl: "https://upload.wikimedia.org/wikipedia/commons/thumb/0/09/Macys.svg/2000px-Macys.svg.png",
    title: "Some title, but does it matter?"
}

const pango = new Pango(admin.database());
pango.deleteRecords();
//pango.addRecords(defaultData.records);
pango.addRecords(defaultData.transactions, "transactions");
pango.addRecord(defaultData.profile, "profile");
pango.addReward(defaultData.newRecord);
console.log(pango.indexRewards(defaultData.records).map(a => a.type));
pango.addRecords(pango.indexRewards(defaultData.records));
//pango.sendNotifiction({
//    title: "You earned $5 rewards for Starbucks.",
//    body: "Reedem with next purchase."
//});
//var newPostRef = postsRef.push();
//newPostRef.set({
//});

// we can also chain the two calls together

//{Secret: "EJgCvD2B1Y1rJn8LATosZ66WiiOt4o4OUlKkAvpM"}