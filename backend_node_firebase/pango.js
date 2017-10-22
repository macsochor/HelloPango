class Pango {
    constructor(admin) {
        this.admin = admin;
        this.db = admin.database();
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
        records.forEach( record => this.addRecord(record, location) );
    }
    sendNotifiction(notification = {}, data = {}) {
        var topic = "offer";
        var payload = {
            notification: notification,
            data: data
        };
        this.admin.messaging().sendToTopic(topic, payload)
            .then(function (response) {
                console.log("Successfully sent message:", response);
            })
            .catch(function (error) {
                console.log("Error sending message:", error);
            });
    }
}

module.exports = Pango;