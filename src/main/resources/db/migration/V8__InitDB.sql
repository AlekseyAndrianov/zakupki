create table if not exists purchase_info (
    purchase_number int8 not null,
    notice_id int,
    updated_at timestamp,
    primary key (purchase_number)
);
create table if not exists purchase_xml_file (
    xml_id serial,
    purchase_number int8 not null,
    file_name varchar(100) not null,
    xml_file text,
    primary key (xml_id),
    foreign key (purchase_number) references purchase_info
);
create table if not exists archives_for_region (
    archive_id serial,
    archive_name varchar(150) unique ,
    region varchar(50),
    updated_at timestamp,
    primary key (archive_id)
);
insert into purchase_info (purchase_number, notice_id, updated_at)
values (0115300018518000004, 18090705, null);

insert into purchase_xml_file (purchase_number, file_name, xml_file) values (
'115300018518000004', 'notification_Magadanskaja_obl_2020040100_2020040200_101.xml', '<?xml version="1.0" encoding="UTF-8"?><ns4:fcsNotificationEP xmlns:ns4="http://zakupki.gov.ru/oos/printform/1" xmlns="http://zakupki.gov.ru/oos/types/1" xmlns:ns2="http://zakupki.gov.ru/oos/common/1" xmlns:ns3="http://zakupki.gov.ru/oos/base/1" schemeVersion="8.3">
    <id>18090705</id>
    <purchaseNumber>0115300018518000004</purchaseNumber>
    <docPublishDate>2018-11-06T12:04:44.643+03:00</docPublishDate>
    <href>http://zakupki.gov.ru/epz/order/notice/ep44/view/common-info.html?regNumber=0115300018518000004</href>
    <printForm>
        <url>http://zakupki.gov.ru/epz/order/notice/printForm/viewXml.html?noticeId=18090705</url>
    </printForm>
    <purchaseObjectInfo>Услуги связи</purchaseObjectInfo>
    <purchaseResponsible>
        <responsibleOrg>
            <regNum>01153000185</regNum>
            <consRegistryNum>97300326</consRegistryNum>
            <fullName>АДМИНИСТРАЦИЯ СОВЕТСКОГО СЕЛЬСКОГО ПОСЕЛЕНИЯ ЯДРИНСКОГО РАЙОНА ЧУВАШСКОЙ РЕСПУБЛИКИ</fullName>
            <postAddress>Российская Федерация, 429068, Чувашская Республика - Чувашия, Ядринский р-н, Советское с, УЛ СОВЕТСКАЯ, 26</postAddress>
            <factAddress>Российская Федерация, 429068, Чувашская Республика - Чувашия, Ядринский р-н, Советское с, УЛ СОВЕТСКАЯ, 26</factAddress>
            <INN>2119901278</INN>
            <KPP>211901001</KPP>
        </responsibleOrg>
        <responsibleRole>CU</responsibleRole>
        <responsibleInfo>
            <orgPostAddress>Российская Федерация, 429068, Чувашская Республика - Чувашия, Ядринский р-н, Советское с, УЛ СОВЕТСКАЯ, 26</orgPostAddress>
            <orgFactAddress>Российская Федерация, 429068, Чувашская Республика - Чувашия, Ядринский р-н, Советское с, УЛ СОВЕТСКАЯ, 26</orgFactAddress>
            <contactPerson>
                <lastName>Федоров </lastName>
                <firstName>А.</firstName>
                <middleName>К.</middleName>
            </contactPerson>
            <contactEMail>yad_sovetskoe@cap.ru</contactEMail>
            <contactPhone>7-83547-64290</contactPhone>
        </responsibleInfo>
    </purchaseResponsible>
    <placingWay>
        <code>EPP44</code>
        <name>Закупка у единственного поставщика (подрядчика, исполнителя)</name>
    </placingWay>
    <lot>
        <maxPrice>6000</maxPrice>
        <currency>
            <code>RUB</code>
            <name>Российский рубль</name>
        </currency>
        <financeSource>За счет средств Советского сельского поселения Ядринского района Чувашской Республики </financeSource>
        <interbudgetaryTransfer>false</interbudgetaryTransfer>
        <quantityUndefined>false</quantityUndefined>
        <customerRequirements>
            <customerRequirement>
                <customer>
                    <regNum>01153000185</regNum>
                    <consRegistryNum>97300326</consRegistryNum>
                    <fullName>АДМИНИСТРАЦИЯ СОВЕТСКОГО СЕЛЬСКОГО ПОСЕЛЕНИЯ ЯДРИНСКОГО РАЙОНА ЧУВАШСКОЙ РЕСПУБЛИКИ</fullName>
                </customer>
                <maxPrice>6000</maxPrice>
                <kladrPlaces>
                    <kladrPlace>
                        <kladr>
                            <kladrType>A</kladrType>
                            <kladrCode>21020000000</kladrCode>
                            <fullName>Российская Федерация, Чувашская Республика - Чувашия, Ядринский р-н</fullName>
                        </kladr>
                        <deliveryPlace>Советское с, УЛ СОВЕТСКАЯ, 26</deliveryPlace>
                    </kladrPlace>
                </kladrPlaces>
                <deliveryTerm>С 01.10.2018 по 31.12.2018</deliveryTerm>
                <purchaseCode>183211990127821190100100010080000242</purchaseCode>
                <tenderPlanInfo>
                    <plan2017Number>2018011530001850020002</plan2017Number>
                    <position2017Number>2018011530001850020000100002</position2017Number>
                </tenderPlanInfo>
            </customerRequirement>
        </customerRequirements>
        <purchaseObjects>
            <purchaseObject>
                <OKPD2>
                    <code>61.10.11.110</code>
                    <name>Услуги по предоставлению внутризоновых, междугородных и международных телефонных соединений</name>
                </OKPD2>
                <name>Услуги связи</name>
                <OKEI>
                    <code>362</code>
                    <nationalCode>МЕС</nationalCode>
                    <fullName>Месяц</fullName>
                </OKEI>
                <price>2000</price>
                <quantity>
                    <value>3</value>
                </quantity>
                <sum>6000</sum>
            </purchaseObject>
            <totalSum>6000</totalSum>
            <totalSumCurrency>0</totalSumCurrency>
        </purchaseObjects>
        <restrictInfo>не требуется</restrictInfo>
        <mustPublicDiscussion>false</mustPublicDiscussion>
    </lot>
</ns4:fcsNotificationEP>'

);