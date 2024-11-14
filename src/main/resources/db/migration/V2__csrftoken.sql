CREATE TABLE csrftoken (
                        id bigint auto_increment primary key,
                        token varchar(255) NOT NULL,
                        date_created datetime NOT NULL
);