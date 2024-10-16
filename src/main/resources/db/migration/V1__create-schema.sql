CREATE TABLE appuser (
                        id bigint auto_increment primary key,
                        username varchar(255) NOT NULL,
                        password varchar(255) NOT NULL
);
create table todo(
                      id bigint auto_increment primary key,
                      item varchar(255) not null,
                      username varchar(255) not null
);