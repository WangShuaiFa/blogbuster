products = LOAD '/example/products/*.json' USING JsonLoader('customerCategoryId:int,products:{(id:int,name:chararray,category:chararray,bought:boolean,price:double)}');

categories = LOAD '/example/dimension/customer_categories.db' AS (categoryId:int,from:int,to:int,gender:chararray);

joinedRecords = JOIN categories BY categoryId, products BY customerCategoryId;

--za svaku grupu korisnika prikazati top pet proizvoda koje grupa kupuje
flattenedProducts = FOREACH joinedRecords GENERATE
                                           categories::categoryId AS categoryId,
                                           categories::from AS ageFrom,
                                           categories::to AS ageTo,
                                           categories::gender AS gender,
                                           FLATTEN(products);

generatedProducts = FOREACH flattenedProducts GENERATE
                                           categoryId,
                                           ageFrom,
                                           ageTo,
                                           gender,
                                           products::id AS id,
                                           products::name AS name,
                                           products::category AS category,
                                           products::bought AS bought,
                                           products::price AS price;

boughtProducts = FILTER generatedProducts BY bought == true;

groupedProducts = GROUP boughtProducts BY (categoryId, ageFrom, ageTo, gender, id, name);

countedProducts = FOREACH groupedProducts GENERATE 
                                               group.categoryId AS categoryId,
                                               group.ageFrom AS ageFrom,
                                               group.ageTo AS ageTo,
                                               group.gender AS gender,
                                               group.id AS id,
                                               group.name AS name, 
                                               COUNT(boughtProducts) AS counter;

grp = GROUP countedProducts BY (categoryId, ageFrom, ageTo, gender);

result = FOREACH grp {
                      sorted = ORDER countedProducts BY counter DESC;
                      topProducts = LIMIT sorted 5;
                      GENERATE
                             FLATTEN(topProducts);
                      };
                                 
--STORE result INTO '/example/results/result3.json' USING JsonStorage();

--prosecan broj pregledanih proizvoda (kupljeni ili ne) po poseti
testAverage = FOREACH joinedRecords GENERATE
                                           categories::categoryId AS categoryId,
                                           categories::from AS ageFrom,
                                           categories::to AS ageTo,
                                           categories::gender AS gender,
                                           COUNT(products) AS counter;

grpAveragePrice = GROUP testAverage BY (categoryId, ageFrom, ageTo, gender);

averageCountedProducts = FOREACH grpAveragePrice GENERATE
                                    group.categoryId AS categoryId,
                                    group.ageFrom AS ageFrom,
				    group.ageTo AS ageTo,
                                    group.gender AS gender,
                                    AVG(testAverage.counter);

--DUMP averageCountedProducts;

--prosecan broj kupljenih proizvoda po poseti
averageBoughtProducts = FOREACH boughtProducts GENERATE
						categoryId,
						ageFrom,
						ageTo,
						gender,
						TOBAG(name, id) AS products;

generatedCountedBoughtProducts = FOREACH averageBoughtProducts GENERATE
								categoryId,
								ageFrom,
								ageTo,
								gender,
								COUNT(products) AS counter;

groupedAverageBoughtProducts = GROUP generatedCountedBoughtProducts BY (categoryId, ageFrom, ageTo, gender);

resultAverageBoughtProducts = FOREACH groupedAverageBoughtProducts GENERATE
									group.categoryId AS categoryId,
									group.ageFrom AS ageFrom,
									group.ageTo AS ageTo,
									group.gender AS gender,
									AVG(generatedCountedBoughtProducts.counter);

--DUMP resultAverageBoughtProducts;

--prosecna kolicina potrosenih para
averagePrice = FOREACH grpAveragePrice GENERATE
                                            group.categoryId,
					    group.ageFrom,
					    group.ageTo,
					    group.gender,
				 	    AVG(generatedProducts.price);

--dump averagePrice;
                               
