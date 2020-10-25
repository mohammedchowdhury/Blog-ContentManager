use Blog;

-- data for USER
insert into `USER`(`USERID`,`USERNAME`,`PASSWORD`,`ENABLED`)values
(1,"admin", "$2a$10$TwKsXLMet1deRfiAqTidjeCmsWCZB5/trgzsGm1daUq6Bea85dSQi", true),
(2,"user","$2a$10$TwKsXLMet1deRfiAqTidjeCmsWCZB5/trgzsGm1daUq6Bea85dSQi",true);

-- data for ROLE
insert into `ROLE`(`ROLEID`,`ROLE`)values
(1,"ROLE_ADMIN"),
(2,"ROLE_USER");
    
-- data for USER-ROLE
insert into `USER_ROLE`(`USERID`,`ROLEID`)values
(1,1),
(1,2),
(2,2);

-- data for TAGS
-- insert into `TAGS`(`HASHTAGID`,`HASHTAG`)values
-- (1,"FOOD"),
-- (2,"HEALTH"),
-- (3,"HOME"); 

-- data for TAGS
-- insert into `BLOG`(`BLOGID`,`TITLE`,`BLOGTEXT`,`DATEOFSHOW`,`EXPIRATION`,`IMAGE`,`BLOGVARIFIED`,`STATIC`,`USERID`)values
-- (1,"Title","TestText",'2020-10-20','2020-10-25',"https://tinyurl.com/q2o9mux", false, false, 1),
-- (2,"Title2","TestText2",'2020-10-20','2020-10-25',"https://tinyurl.com/q2o9mux", false, true, 1),
-- (3,"Title3",'"<p>hello how <strong>are</strong> yu<span style="font-family: arial, helvetica, sans-serif;"> aasasd this&nbsp;<span style="font-family: courier new'',' 'courier, monospace;">asadas<code></code></span></span></p>"',
-- '2020-10-20','2020-10-25',"", false, false, 1),
-- (4,"Title4","TestText4",'2020-10-20','2020-10-25',"https://tinyurl.com/q2o9mux", false, true, 1); 


-- data for TAGS_BLOG
-- insert into `TAGS_BLOG`(`HASHTAGID`,`BLOGID`)values
-- (1,1),
-- (1,2),
-- (1,3), 
-- (2,3); 


-- create table `BLOG`(
-- `BLOGID` int primary key auto_increment,
-- `TITLE` varchar(100) not null, 
-- `BLOGTEXT` TEXT not null, 
-- `DATEOFSHOW` DATE,
-- `EXPIRATION` DATE,
-- `IMAGE` varchar(100),
-- `BLOGVARIFIED` boolean not null,
-- `STATIC` boolean not null,
-- `USERID` int not null,