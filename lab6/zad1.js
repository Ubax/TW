function printAsync(s, cb) {
    var delay = Math.floor((Math.random()*1000)+500);
    setTimeout(function() {
        console.log(s);
        if (cb) cb();
    }, delay);
 }
 
 function task1(cb) {
     printAsync("1", function() {
         task2(cb);
     });
 }
 
 function task2(cb) {
     printAsync("2", function() {
         task3(cb);
     });
 }
 
 function task3(cb) {
     printAsync("3", cb);
 }
 
 // wywolanie sekwencji zadan
//  task1(function() {
//      console.log('done!');
//  });

var g=undefined;

 const loop = n => {
    if(n!==undefined){
        g=n;
    }
    if(g==0){
        console.log("Done");
        g=undefined;
        return;
    }
    g--;
    task1(loop);
 }
 

 loop(2);
 /* 
 ** Zadanie:
 ** Napisz funkcje loop(n), ktora powoduje wykonanie powyzszej 
 ** sekwencji zadan n razy. Czyli: 1 2 3 1 2 3 1 2 3 ... done
 ** 
 */
 
 // loop(4);