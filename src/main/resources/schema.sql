CREATE TABLE "plugins" (
                           "plugin_id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                           "name"	TEXT NOT NULL UNIQUE
);
CREATE TABLE "targets" (
                           "target_id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                           "target"	TEXT NOT NULL,
                           "status"	TEXT,
                           "last_updated"	DATE,
                           UNIQUE("target", "status")
);
CREATE TABLE "scans" (
                         "scan_id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                         "config_id"	INTEGER NOT NULL,
                         "plugin_id"	INTEGER NOT NULL,
                         "target_id"	INTEGER NOT NULL,
                         "version"	TEXT,
                         "os"	TEXT,
                         "string"	TEXT,
                         "account"	TEXT,
                         "model"	TEXT,
                         "firmware"	TEXT,
                         "module"	TEXT,
                         "filepath"	TEXT,
                         "certainty"	TEXT
);
CREATE TABLE "request_configs" (
                                   "config_id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                                   "value"	TEXT NOT NULL
);
CREATE TABLE "server_historiala" (
                                     "url"	TEXT NOT NULL,
                                     "last_updated"	DATETIME,
                                     PRIMARY KEY("url")
);