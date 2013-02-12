var CustomerOrderRepository = require('../../repo/CustomerOrderRepository').CustomerOrderRepository,
    repo = new CustomerOrderRepository();

/*
 * GET users listing.
 */
exports.list = function(req, res){

    var doList = function() {
        repo.findAll().then(function(docs){
            console.debug(JSON.stringify(docs));
            res.send(docs);
        });
    };

    repo.count().then(function(count){
        if (count === 0) {
            console.log('There are no orders - inserting one');
            repo.insert({
                orderNumber: "O000001",
                customerReference: "CR00001"
            }).then(doList);
        }
        else {
            doList();
        }
    });


};