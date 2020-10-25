use Blog;

-- date for USER
insert into `USER`(`USERID`,`USERNAME`,`PASSWORD`,`ENABLED`)values
(1,"admin", "$2a$10$m28WLn6I/ojVXq8U/n0nwepPgBxCHNym48/LbFObl7xEuK0ITYV/q", true),
(2,"user","$2a$10$m28WLn6I/ojVXq8U/n0nwepPgBxCHNym48/LbFObl7xEuK0ITYV/q",true);

-- date for ROLE
insert into `ROLE`(`ROLEID`,`ROLE`)values
(1,"ROLE_ADMIN"),
(2,"ROLE_USER");
    
-- date for USER-ROLE
insert into `USER_ROLE`(`USERID`,`ROLEID`)values
(1,1),
(1,2),
(2,2);