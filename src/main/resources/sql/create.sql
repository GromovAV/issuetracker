drop table user if exists;
create table user (
  id integer PRIMARY KEY AUTO_INCREMENT,
  login TEXT NOT NULL,
  password TEXT NOT NULL
);

drop table issue if exists;
create table issue (
  id integer PRIMARY KEY AUTO_INCREMENT,
  userId integer not null,
  name TEXT NOT NULL,
  author TEXT ,
  description TEXT,
  publicationDate timestamp,
  status integer not null
);

drop table comment if exists;
create table comment (
  id integer PRIMARY KEY AUTO_INCREMENT,
  issueId integer not null,
  author TEXT,
  text TEXT ,
  commentDate timestamp,
  changeStatus  boolean default false
);
