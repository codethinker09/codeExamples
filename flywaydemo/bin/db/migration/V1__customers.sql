create table CUSTOMER (
  ID bigint NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  email varchar (255) NOT NULL,
  phone varchar (255) NOT NULL,
  PRIMARY KEY (ID)
);

insert into CUSTOMER (name, email, phone) values
  ('Peter ter Borg', 'terborg@bluehorizon.nl', '202-555-0143'),
  ('Richard van der Horst', 'vanderhorst@bluehorizon.nl', '202-555-0180'),
  ('M.J. van der Werf', 'vanderwerf@bluehorizon.nl', '202-555-0128'),
  ( 'Ankur Singhal', 'getankur86@gmail.com', '202-555-0174');