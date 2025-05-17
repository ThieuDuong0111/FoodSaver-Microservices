USE foodsaver_product_service;

INSERT INTO Product(Creator_Id, Category_Id, Name, Description, Price, Discount_Price, Quantity,sold_count) VALUES
(2, 1, 'Apple', 'The apple is one of the pome (fleshy) fruits. Apples at harvest vary widely in size, shape, colour, and acidity, but most are fairly round and some shade of red or yellow. The thousands of varieties fall into three broad classes: cider, cooking, and dessert varieties.', 
100000, 50000, 100, 5),
(2, 1, 'Grape', 'A grape is a fruit, botanically a berry, of the deciduous woody vines of the flowering plant genus Vitis. Grapes are a non-climacteric type of fruit, generally occurring in clusters. The cultivation of grapes began perhaps 8,000 years ago, and the fruit has been used as human food over history.', 
50000, 25000, 150, 5),
(2, 2, 'Potato', 'The potato is a starchy root vegetable native to the Americas that is consumed as a staple food in many parts of the world. Potatoes are tubers of the plant ...', 
10000, 5000, 300, 5),
(2, 2, 'Tomato', 'The tomato is the edible berry of the plant Solanum lycopersicum, commonly known as the tomato plant. The species originated in western South America, Mexico, and Central America. The Nahuatl word tomatl gave rise to the Spanish word tomate, from which the English word tomato derives.', 
75000, 55000, 200, 5);
