drop database if exists Blog;
create database Blog;
use Blog;

-- create USER table
create table `USER`(
`USERID` int primary key auto_increment,
`USERNAME` varchar(30) not null unique,
`PASSWORD` varchar(100) not null,
`ENABLED` boolean not null);

-- create ROLE table
create table `ROLE`(
`ROLEID` int primary key auto_increment,
`ROLE` varchar(30) not null);

-- create USER_ROLE table 
create table `USER_ROLE`(
`USERID` int not null,
`ROLEID` int not null,
primary key(`USERID`,`ROLEID`),
foreign key (`USERID`) references `USER`(`USERID`),
foreign key (`ROLEID`) references `ROLE`(`ROLEID`));


-- create TAGS table
create table `TAGS`(
`HASHTAGID` int primary key auto_increment,
`HASHTAG` varchar(30) not null);

-- create BLOG table
create table `BLOG`(
`BLOGID` int primary key auto_increment,
`TITLE` varchar(100) not null, 
`BLOGTEXT` LONGTEXT not null, 
`DATEOFSHOW` DATE,
`EXPIRATION` DATE,
`IMAGE` varchar(100),
`BLOGVARIFIED` boolean not null,
`STATIC` boolean not null,
`USERID` int not null,
foreign key (`USERID`) references `USER`(`USERID`));

-- create TAGS_BLOG table 
create table `TAGS_BLOG`(
`HASHTAGID` int not null,
`BLOGID` int not null,
primary key(`HASHTAGID`,`BLOGID`),
foreign key (`HASHTAGID`) references `TAGS`(`HASHTAGID`),
foreign key (`BLOGID`) references `BLOG`(`BLOGID`));
