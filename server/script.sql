use eventsapp;

insert into users(email,name) values ('adesh14004@iiitd.ac.in', 'Adesh Pandey');
insert into users(email,name) values ('rishabh14086@iiitd.ac.in', 'Rishabh Gupta');
insert into users(email,name) values ('aishwarya14007@iiitd.ac.in', 'Aishwarya Jaiswal');
insert into clubs(name) values ('My Club1');
insert into clubs(name) values ('My Club2');
insert into clubs(name) values ('My Club3');

-- insert into events(name, associated_club) values ('My Event11', 1);
-- insert into events(name, associated_club) values ('My Event12', 1);
-- insert into events(name, associated_club) values ('My Event21', 2);
-- insert into events(name, associated_club) values ('My Event22', 2);


insert into events(name) values ('My Event11');
insert into events(name) values ('My Event12');
insert into events(name) values ('My Event21');
insert into events(name) values ('My Event22');

insert into clubs_events(events, clubs) values (1, 1);
insert into clubs_events(events, clubs) values (2, 1);
insert into clubs_events(events, clubs) values (3, 2);
insert into clubs_events(events, clubs) values (4, 2);


insert into clubs_users(subscribed_clubs, subscribers) values (1, 'adesh14004@iiitd.ac.in');
insert into clubs_users(subscribed_clubs, subscribers) values (2, 'adesh14004@iiitd.ac.in');
insert into clubs_users(subscribed_clubs, subscribers) values (2, 'rishabh14086@iiitd.ac.in');
insert into clubs_users(subscribed_clubs, subscribers) values (3, 'adesh14004@iiitd.ac.in');
insert into events_users(subscribed_events, subscribers) values (1, 'adesh14004@iiitd.ac.in');
insert into events_users(subscribed_events, subscribers) values (2, 'rishabh14086@iiitd.ac.in');
insert into events_users(subscribed_events, subscribers) values (3, 'aishwarya14007@iiitd.ac.in');

insert into events_admins(administered_events, administrators) values (1, 'adesh14004@iiitd.ac.in');
insert into clubs_admins(administered_clubs, administrators) values (1, 'adesh14004@iiitd.ac.in');
insert into events_admins(administered_events, administrators) values (2, 'rishabh14086@iiitd.ac.in');
insert into clubs_admins(administered_clubs, administrators) values (2, 'rishabh14086@iiitd.ac.in');
insert into events_admins(administered_events, administrators) values (3, 'adesh14004@iiitd.ac.in');
insert into clubs_admins(administered_clubs, administrators) values (3, 'adesh14004@iiitd.ac.in');
insert into events_admins(administered_events, administrators) values (4, 'adesh14004@iiitd.ac.in');
