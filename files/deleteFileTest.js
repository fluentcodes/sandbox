const fs = require('fs'); 
const assert = require('assert'); 
const df = require('./deleteFile'); 
console.log("Start assert");
df.deleteFile("no-such-file", (err) => { 
    assert.throws( 
        function() { 
            if (err) {
                console.log(err);
                throw err;
            }; 
        }, 
        function(error) { 
            if ((error instanceof Error) 
             && /does not exist/.test(error)) { 
                console.log("Not error: " + error);
               return true; 
            } 
            else {
                console.log("Error function " + error);
                return false; 
            }
        }, 
        "unexpected error" 
    ); 
}); 
console.log("Finished");