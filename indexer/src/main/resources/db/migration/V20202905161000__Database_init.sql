create table if not exists purchase_info (
    purchase_number int8 primary key,
    notice_id int,
    prefix_key varchar(30),
    updated_at timestamptz
);
create table if not exists purchase_xml_file (
    purchase_number int8 primary key,
    file_name varchar(150),
    xml_file text
);
create table if not exists purchase_code_mapping (
    purchase_code varchar(40) primary key,
    purchase_number int8
);
create table if not exists archives_for_region (
    archive_id serial primary key ,
    archive_name varchar(150) unique,
    region varchar(50),
    updated_at timestamptz
);