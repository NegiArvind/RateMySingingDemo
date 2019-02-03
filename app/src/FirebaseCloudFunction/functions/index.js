const functions = require('firebase-functions');

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });

const admin=require('firebase-admin');
admin.initializeApp();

// exports.count=functions.database.ref('/Users/{userId}').onWrite((change,context)=>{
	
// 	return change.after.ref.parent.once("value").then((snapshot)=>{
// 		const count=snapshot.numChildren();
// 		console.log(count)
// 		if(count>10){
// 			console("inside count if",count);
// 			return functions.database.ref('/Users').limitToFirst(1).once('value').then(function(slotValues) {
// 			  if (slotValues.exists()) {
// 				var location;
// 				slotValues.forEach(function(slotValue) {
// 				  const key = slotValue.key
// 				  const wonIndex = slotValue.val()
// 				  console.log(key)
// 				  console.log(wonIndex)
// 				  location = '/Users/${key}';
// 				});
// 				return functions.database.ref(location).remove().then(snap => {
// 				  return change.after.adminRef.remove()
// 				});
// 			  }
// 			  else{
// 				  throw new Error("Not exist");
// 			  }
// 			})
// 			//.catch(function(error){
// 			//	console.log("error sending message",error);
// 		//	});
// 		}else{
// 			throw new Error("Not Exist");
// 		}
// 	})
// 	.catch(function(error){
// 		return console.log("error : ",error);
// 	});
// });

exports.count = functions.database.ref('/User/{userId}').onWrite((change,context) => {
  return change.after.ref.parent.once('value').then((snapshot) => {
    if (snapshot.numChildren() > 10) {
      let childCount = 0;
      const deletedNode = {};
      snapshot.forEach((child) => {
        if (++childCount <= snapshot.numChildren() - 10) {
          deletedNode[child.key] = null;
        }
      });
      return change.after.ref.parent.update(deletedNode);
    }
    return null;
  });
});