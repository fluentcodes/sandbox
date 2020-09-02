'use strict';
console.log('Hello World');
var theWorld : string = "World";
for(var i : number = 0; i <= theWorld.length; i++) {
    console.log("Hello "
        + theWorld.substr(i)
        + (theWorld.length - i > 0 ? '!' : '?'));
}
