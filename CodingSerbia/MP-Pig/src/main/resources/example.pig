-- Load students from hdfs
students = LOAD '/some/path/students_S.txt' USING PigStorage(',') AS (id:long, firstName:chararray, lastName:chararray, country:chararray, city:chararray, university:chararray, course:chararray);

-- Load ratings from hdfs
rating = LOAD '/some/path/ratings_S.txt' USING PigStorage(',') AS (studentId:long, rating:float);

-- Join records by studentId
joinedRecords = JOIN students BY id, rating BY studentId;

-- Filter students with rating > 8.5
filteredRecords = FILTER joinedRecords BY rating::rating > 8.5F;

-- Generate fields that we are interested in
generatedRecords = FOREACH filteredRecords GENERATE
												students::id AS id,
												students::firstName AS firstName,
												students::lastName AS lastName,
												students::course AS course,
												rating::rating AS rating;

-- Store results
STORE generatedRecords INTO '/results/ratings_pig_S' USING PigStorage();
