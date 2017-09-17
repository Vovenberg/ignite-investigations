 CREATE TABLE Repository (
    id LONG PRIMARY KEY,
    name VARCHAR,
    desciption VARCHAR,
    url VARCHAR
 )    WITH "template=replicated"


CREATE TABLE Issue (
    id LONG PRIMARY KEY,
    repository_id LONG,
    state VARCHAR,
    title VARCHAR,
    createdDate DATE
 )    WITH "backups=1, affinityKey=repository_id"
