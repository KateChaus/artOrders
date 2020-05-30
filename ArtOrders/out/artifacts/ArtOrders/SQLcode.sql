DROP SCHEMA IF EXISTS orders;
CREATE SCHEMA orders;
USE orders;

CREATE TABLE user (
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(20) UNIQUE,
    password VARCHAR(100),
	info TEXT,
	isArtist BOOLEAN DEFAULT false,
    image VARCHAR(100) DEFAULT 'images/avatars/default.png'
    );

CREATE TABLE gallery (
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    site VARCHAR(50),
    link TEXT,
    artistID INT UNSIGNED,
    FOREIGN KEY (artistID) REFERENCES user (id)
    );

CREATE TABLE orderApplication (
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    orderType ENUM ('free','description'),
    orderStyle ENUM ('digital','traditional','three_dimensional', 'animation'),
    info TEXT,
    cost INT,
    slots TINYINT UNSIGNED
    );
    
CREATE TABLE artOrder (
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    orderStatus ENUM ('applied','process','finished'),
    image VARCHAR(100) DEFAULT 'images/arts/default.jpg'
    );
    
CREATE TABLE customers(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    customerID INT UNSIGNED,
    orderID INT UNSIGNED,
    FOREIGN KEY (customerID) REFERENCES user (id),
    FOREIGN KEY (orderID) REFERENCES artOrder (id)  ON DELETE CASCADE
);

CREATE TABLE artists(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    artistID INT UNSIGNED,
    orderApplicationID INT UNSIGNED,
    FOREIGN KEY (artistID) REFERENCES user (id),
	FOREIGN KEY (orderApplicationID) REFERENCES orderApplication (id)
);

CREATE TABLE orders(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    artOrderID INT UNSIGNED,
    orderApplicationID INT UNSIGNED,
    FOREIGN KEY (artOrderID) REFERENCES artOrder (id) ON DELETE CASCADE,
    FOREIGN KEY (orderApplicationID) REFERENCES orderApplication (id)
);
    
INSERT INTO user (username, password, info, isArtist, image) VALUES 
('defaultUser','d8578edf8458ce06fbc5bb76a58c5ca4','I\'m a default user, I just watch people', false,'images/avatars/icon1.png'),
('anotherUser','e10adc3949ba59abbe56e057f20f883e','I\'m another user, but I like to talk more than the previous one, that\'s why I have so much text in my text description', false,'images/avatars/icon2.png'),
('defaultArtist','0441f9e2d94c39a70e21b83829259aa4','I\'m an artist!', true,'images/avatars/batman.png'),
('3DArtist','a9aedc6c39f654e55275ad8e65e316b3','I draw 3D art only!', true,'images/avatars/icon3.png'),
('biggestFan','d8578edf8458ce06fbc5bb76a58c5ca4','I love everyone!', false,'images/avatars/icon4.png'),
('lazyArtist','d8578edf8458ce06fbc5bb76a58c5ca4','I am an artist, but I am laz', true,'images/avatars/lazy.png'),
('NaVseRukiMaster','c3c96f4da7d08f9526400dbb9c7b54f0','I can do anything! I`m master in 3D and animation and traditional art and digital art, because I krasavchik. Everyone hates me :(', true,'images/avatars/icon5.png'),
('DramaQueen','ee157d37ea7677627dc3f7bf86af9778','I`m so sad and I want to everyone knows how much I`m sad!', true,'images/avatars/icon6.png'),
('Hater','9530fc8af42f35999b995abf4ee2d93b','I just want to say my important opinion and my opinion says me, that your art is shit', false,'images/avatars/icon7.png'),
('VanilGirl','479304afddeea7ef1f2798609f3acc1c','I`M CUTE TINY GIRL, LOVE ME <3<3<3', true,'images/avatars/icon8.png');

INSERT INTO gallery (site, link, artistID) VALUES
('VK','https://vk.com/airiskiahin','3'),
('DeviantArt','https://www.deviantart.com/jcthornton/gallery','4'),
('VK','https://vk.com/yumich_arts','6'),
('Twitter','https://twitter.com/YumiCHH','6'),
('Artstation!','https://twitter.com/YumiCHH','10'),
('VK!','https://twitter.com/YumiCHH','10'),
('DeViAnTaRt!!!','','10');

INSERT INTO orderApplication (orderType, orderStyle, info, cost, slots) VALUES
('free','digital','Free character to choose','25','5'),
('description','three_dimensional','I draw only humans','50','7'),
('description','traditional','I draw only girls and nature','45','3'),
('free','animation','Only chibi','30','10'),
('free','traditional','Portraits only','60','12'),
('free','three_dimensional','Bg for your games','500','4'),
('description','three_dimensional','Animation model','100','2'),
('description','animation','Simple blinking','40','20'),
('free','animation','Full animation for two characters','450','1'),
('description','digital','Fullbody of your character','70','4'),
('description','traditional','Icons with bg','50','7'),
('free','three_dimensional','Shaking butty!','50','10'),
('free','traditional','Halfbody','60','12'),
('description','digital','References','105','3'),
('free','digital','Paws!','25','5');

INSERT into artists (artistID, orderApplicationID) VALUES
(3,1),
(3,3),
(3,5),
(6,4),
(6,5),
(6,14),
(6,15),
(4,2),
(4,6),
(7,7),
(7,8),
(7,9),
(7,10),
(8,11),
(9,12),
(9,13);

INSERT INTO artOrder (orderStatus, image) VALUES
('process','images/arts/default.jpg'),
('applied','images/arts/default.jpg'),
('applied','images/arts/default.jpg'),
('finished','images/arts/4.gif'),
('process','images/arts/5.gif'),
('process','images/arts/1.gif'),
('applied','images/arts/default.jpg'),
('applied','images/arts/default.jpg'),
('finished','images/arts/4.gif'),
('process','images/arts/default.jpg'),
('applied','images/arts/default.jpg'),
('process','images/arts/default.jpg'),
('applied','images/arts/default.jpg'),
('applied','images/arts/default.jpg'),
('finished','images/arts/3.gif'),
('process','images/arts/2.gif'),
('process','images/arts/default.jpg'),
('applied','images/arts/default.jpg'),
('process','images/arts/default.jpg'),
('applied','images/arts/default.jpg'),
('applied','images/arts/default.jpg');

INSERT INTO orders (artOrderID, orderApplicationID) VALUES
(1,1),
(2,2),
(3,2),
(4,1),
(5,1),
(6,2),
(7,2),
(8,3),
(9,14),
(10,10),
(11,13),
(12,7),
(13,4),
(14,3),
(15,8),
(16,7),
(17,9),
(18,11),
(19,12),
(20,1),
(21,2);

INSERT INTO customers (orderID, customerID) VALUES
(1,1),
(2,3),
(3,6),
(4,1),
(5,1),
(6,2),
(7,2),
(8,3),
(9,4),
(10,5),
(11,5),
(12,1),
(13,4),
(14,5),
(15,8),
(16,7),
(17,9),
(18,5),
(19,5),
(20,1),
(21,2);

SELECT * FROM user;
SELECT * FROM gallery;
SELECT * FROM orderApplication;
SELECT * FROM artOrder;
SELECT * FROM artists;

SELECT * FROM orderApplication JOIN artists on orderApplication.id = artists.orderApllicationID WHERE cost <=2147483647;


SELECT * FROM artOrder JOIN customers ON customers.orderID = artOrder.id;

SELECT artOrder.id, user.username FROM artOrder
JOIN orders ON artOrder.id = orders.artOrderID
JOIN orderApplication ON orders.orderApplicationID = orderApplication.id
JOIN artists ON orderApplication.id = artists.orderApplicationID
JOIN user ON artists.artistID=user.id;

SELECT * FROM artOrder JOIN customers ON customers.orderID = artOrder.id JOIN orders ON orders.artOrderID = artOrder.id WHERE customers.customerID='6';

SELECT * FROM artOrder
JOIN orders ON artOrder.id = orders.artOrderID
JOIN orderApplication ON orders.orderApplicationID = orderApplication.id
JOIN artists ON orderApplication.id = artists.orderApplicationID
JOIN customers ON artOrder.id = customers.orderID
WHERE orderStatus = 'applied' AND artistID = '6';

SELECT * FROM artOrder 
JOIN customers ON artOrder.id = customers.orderID
WHERE customers.customerID = 5;

/*SELECT artOrder.id, artOrder.orderStatus, user.username FROM artOrder JOIN customers ON artOrder.id = customers.orderID JOIN user ON customers.customerID = user.id;
