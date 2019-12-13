/*
DROP TABLE IF EXISTS mysql.comments, mysql.images, mysql.places,  mysql.users;
CREATE TABLE IF NOT EXISTS mysql.users(
    user_id INT(11) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(30) NOT NULL UNIQUE,
    user_password VARCHAR(30) NOT NULL
);
CREATE TABLE IF NOT EXISTS mysql.places(
    place_id INT(11) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    place_name VARCHAR(31) NOT NULL,
    description VARCHAR(3000) NOT NULL,
    latitude FLOAT(10, 6) NOT NULL,
    longitude FLOAT(10, 6) NOT NULL,
    address VARCHAR(255) NOT NULL,
    place_created_at DATETIME NOT NULL,
    place_updated_at DATETIME,
    contains_permanent BOOLEAN NOT NULL,
    user_id INT(11) UNSIGNED NOT NULL,
    FOREIGN KEY fk_places_users_user_id(user_id) REFERENCES mysql.users(user_id)
);
CREATE TABLE IF NOT EXISTS mysql.images(
    image_id INT(11) UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
    place_id INT(11) UNSIGNED NOT NULL,
    public_id VARCHAR(30) NOT NULL,
    format VARCHAR(6) NOT NULL,
    user_id INT(11) UNSIGNED NOT NULL,
    image_created_at DATETIME NOT NULL,
    FOREIGN KEY fk_images_users_user_id(user_id) REFERENCES mysql.users(user_id),
    FOREIGN KEY fk_images_places_place_id(place_id) REFERENCES mysql.places(place_id)
);
CREATE TABLE IF NOT EXISTS mysql.comments(
    comment_id INT(11) UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
    place_id INT(11) UNSIGNED NOT NULL,
    content VARCHAR(3000) NOT NULL,
    user_id INT(11) UNSIGNED NOT NULL,
    user_name VARCHAR(30) NOT NULL,
    comment_created_at DATETIME NOT NULL,
    comment_updated_at DATETIME,
    FOREIGN KEY fk_comments_places_place_id(place_id) REFERENCES mysql.places(place_id),
    FOREIGN KEY fk_comments_users_user_id(user_id) REFERENCES mysql.users(user_id));


INSERT INTO mysql.users VALUES(null,  'user1', 'password1');
*/

INSERT INTO mysql.users VALUES(null, 'user2', 'password2');

/*
INSERT INTO mysql.places VALUES(null, '多摩動物公園', 'オラウータンがいる。日本で初めてバッタを養殖した。',
35.6494125, 139.3998765, '東京都日野市程久保', '2019-11-19 13:27:00', null, true, 1);
INSERT INTO mysql.places VALUES(null, '日野宿本陣', '新選組の何か。',
35.6781071, 139.3974444, '東京都日野市日野本町', '2019-11-19 13:29:00', null, true, 1);
*/