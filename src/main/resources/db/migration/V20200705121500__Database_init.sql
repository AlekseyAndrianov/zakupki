create table if not exists purchase_info (
    purchase_number int8 primary key,
    notice_id int,
    updated_at timestamptz
);
create table if not exists purchase_xml_file (
    xml_id serial primary key ,
    purchase_number int8,
    file_name varchar(150),
    xml_file text
);
create table if not exists archives_for_region (
    archive_id serial primary key ,
    archive_name varchar(150) unique,
    region varchar(50),
    updated_at timestamptz
);