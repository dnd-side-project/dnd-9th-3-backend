SET FOREIGN_KEY_CHECKS = 0;
truncate table member;
truncate table image;
truncate table record;
SET FOREIGN_KEY_CHECKS = 1;

insert into member (member_id, emails, interests, name, oauth_id, password, user_role)
    values ('yonog7317', 'yong80211@gmail.com,youg1322@naver.com','1,2,3','hahn', '', '', 'ROLE_USER');
insert into member (member_id, emails, interests, name, oauth_id, password, user_role)
    values ('kim1322', 'yong80211@gmail.com','4,5,6','kim', '', '', 'ROLE_USER');
insert into member (member_id, emails, interests, name, oauth_id, password, user_role)
    values ('son7', 'yong80211@gmail.com','4,5,6','son', '', '', 'ROLE_USER');

 insert into record (record_number, place_latitude, place_longitude, place_title, description, record_date, record_score, recorder_id, recorder_name, state, title)
    values ('record1', '123123.56', '664747.56', 'kangwon', 'record description', now(), 20, 'yong7317', 'haeyong', 'PRIVATE', 'record title');
 insert into record (record_number, place_latitude, place_longitude, place_title, description, record_date, record_score, recorder_id, recorder_name, state, title)
     values ('record2', '123123.56', '664747.56', 'kangwon2', 'record description2', now(), 20, 'yong7317', 'haeyong', 'PRIVATE', 'record title2');