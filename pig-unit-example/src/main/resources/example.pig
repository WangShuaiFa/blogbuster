-- Load users from hdfs
users = LOAD '/some/path/users.txt' USING PigStorage(',') AS (id:long, firstName:chararray, lastName:chararray, country:chararray, city:chararray, company:chararray);

-- Load ratings from hdfs
awesomenessRating = LOAD '/some/path/rating.txt' USING PigStorage(',') AS (userId:long, rating:long);

-- Join records by userId
joinedRecords = JOIN users BY id, awesomenessRating BY userId;

-- Filter users with awesomenessRating > 150
filteredRecords = FILTER joinedRecords BY awesomenessRating::rating > 150;

-- Generate fields that we are interested in
generatedRecords = FOREACH filteredRecords GENERATE
												users::id AS id,
												users::firstName AS firstName,
												users::country AS country,
												awesomenessRating::rating AS rating;

-- Store results
STORE generatedRecords INTO '/results/awesomeness' USING PigStorage();
