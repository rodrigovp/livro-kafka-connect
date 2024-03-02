rs.initiate({ _id: "rs0", members: [ { _id: 0, host: "localhost:27017" } ] })
db = db.getSiblingDB('livro-kafka');
db.createUser(
	{
		user: 'user', 
		pwd: 'password', 
		roles: [{role: 'readWrite', db: 'livro-kafka'}]
	}
);
db.createCollection('voos');