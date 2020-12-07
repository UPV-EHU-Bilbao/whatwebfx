CREATE TABLE plugins (plugin_id int NOT NULL AUTO_INCREMENT, name varchar(255) NOT NULL,PRIMARY KEY (plugin_id), UNIQUE (name));
CREATE TABLE targets (target_id int NOT NULL AUTO_INCREMENT, target varchar(255) NOT NULL, status varchar(10),PRIMARY KEY (target_id), UNIQUE (target, status) );
CREATE TABLE scans (scan_id int NOT NULL AUTO_INCREMENT, config_id INT NOT NULL, plugin_id INT NOT NULL, target_id INT NOT NULL, version varchar(255), os varchar(255), string varchar(1024), account varchar(1024), model varchar(1024), firmware varchar(1024), module varchar(1024), filepath varchar(1024), certainty varchar(10) ,PRIMARY KEY (scan_id));
CREATE TABLE request_configs (config_id int NOT NULL AUTO_INCREMENT, value TEXT NOT NULL, PRIMARY KEY (config_id) );
