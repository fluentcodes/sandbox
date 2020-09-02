var assert = require('assert');
var x = "y";
console.log("Start with x=" + x);
assert.equal("y", x);
assert.equal("z", x);
console.log("Finished");