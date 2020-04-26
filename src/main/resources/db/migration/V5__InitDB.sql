create table if not exists purchase_info (
    updated_at timestamp,
    purchase_number int8 not null,
    notice_id int,
    primary key (purchase_number)
);
create table if not exists purchase_xml_file (
    xml_id int,
    purchase_number int8 not null,
    xml_file xml,
    primary key (xml_id),
    foreign key (purchase_number) references purchase_info
);
