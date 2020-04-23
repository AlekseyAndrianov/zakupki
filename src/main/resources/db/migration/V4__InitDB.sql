drop table if exists purchase_info;
drop table if exists purchase_xml_file;

create table purchase_info (
    updated_at timestamp,
    purchase_number int8 not null,
    notice_id int,
    primary key (purchase_number)
);
create table purchase_xml_file (
    xml_id int,
    purchase_number int8 not null,
    xml_file xml,
    primary key (xml_id),
    foreign key (purchase_number) references purchase_info
);