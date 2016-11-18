insert into users(email,name) values ('adesh14004@iiitd.ac.in', 'Adesh Pandey');
insert into clubs(name) values ('My Club1');
insert into events(name, associated_club) values ('My Event1', 1);
insert into clubs_users(subscribed_clubs, subscribers) values (1, 'adesh14004@iiitd.ac.in');
insert into events_users(subscribed_events, subscribers) values (1, 'adesh14004@iiitd.ac.in');