-- Initial SQL to check if flyway is working correctly;

CREATE SEQUENCE public.hibernate_sequence
INCREMENT BY 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1;

CREATE TABLE public.users (
	id int8 NOT NULL,
	"name" varchar(255) NOT NULL,
	CONSTRAINT users_pkey PRIMARY KEY (id)
) ;

INSERT INTO public.users
(id, name)
VALUES(nextval('hibernate_sequence'), 'Jožko Mrkvička');

SELECT setval('hibernate_sequence', (SELECT MAX(id) FROM public.users));