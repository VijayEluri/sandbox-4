var
    mongodb = require('mongodb'),
    server = new mongodb.Server('localhost', mongodb.Connection.DEFAULT_PORT),
    DB_NAME = 'orderManagement-dev',
    conn = new mongodb.Db(DB_NAME, server, {safe: true}),
    Q = require('q'),
    BaseRepository;

/**
 * A base repository providing common repository methods.
 * @constructor
 */
BaseRepository = function() {
};

/**
 * Returns a deferred, executes the passed callback within an open connection, and resolves the returned deferred
 * with the results of the callback after having closed the connection.
 * @param callback the callback to execute with the open connection.
 * @return {Object} the deferred which will be resolved with the results of the callback.
 */
BaseRepository.prototype.execDb = function (callback) {
    var ret = Q.defer();

    conn.open(function (err, db) {
        if (err) {
            console.error(err);
            console.error("Did you forget to start the server?")
        }
        var d = Q.defer();
        callback(db, d);
        d.promise.then(function (results) {
            conn.close();
            ret.resolve(results);
        });
    });

    return ret.promise;
};

BaseRepository.prototype.entityClass = null;

BaseRepository.prototype.count = function () {
    var self = this;
    return this.execDb(function (db, d) {
        db.collection(self.COLL_NAME, function (err, coll) {
            coll.count(function (err, count) {
                if (err) {
                    console.log('An error occurred on count');
                    console.log(err);
                    throw new Error(JSON.stringify(err));
                }
                d.resolve(count);
            });
        });
    });
};

BaseRepository.prototype.findAll = function() {
    var self = this;
    return this.execDb(function (db, d) {
        var entities = [], entity;
        db.collection(self.COLL_NAME, function (err, coll) {
            coll.find().each(function (err, item) {
                if (item) {
                    entity = new self.entityClass();
                    entity.hydrate(item);
                    entities.push(entity);
                }
                else {
                    // we get a null item when there are no more
                    d.resolve(entities);
                }
            });
        });
    });
};

BaseRepository.prototype.insert = function (order) {
    var self = this;
    return this.execDb(function (db, d) {
        db.collection(self.COLL_NAME, function (err, coll) {
            coll.insert(order, {safe: true}, function (err, doc) {
                if (err) {
                    throw new Error(err);
                }
                else {
                    d.resolve(doc);
                }
            });
        });
    });
};

exports.BaseRepository = BaseRepository;


