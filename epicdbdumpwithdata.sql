--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: event_tags; Type: TABLE; Schema: public; Owner: mcguik2; Tablespace: 
--

CREATE TABLE event_tags (
    id integer,
    tag character varying(80)
);


ALTER TABLE public.event_tags OWNER TO mcguik2;

--
-- Name: events; Type: TABLE; Schema: public; Owner: mcguik2; Tablespace: 
--

CREATE TABLE events (
    id integer NOT NULL,
    name character varying(80),
    host character varying(80),
    source character varying(80),
    creator character varying(80),
    recurring boolean,
    starttime timestamp without time zone,
    endtime timestamp without time zone,
    location character varying(255),
    on_campus boolean
);


ALTER TABLE public.events OWNER TO mcguik2;

--
-- Name: events_id_seq; Type: SEQUENCE; Schema: public; Owner: mcguik2
--

CREATE SEQUENCE events_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.events_id_seq OWNER TO mcguik2;

--
-- Name: events_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: mcguik2
--

ALTER SEQUENCE events_id_seq OWNED BY events.id;


--
-- Name: preference_sets; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE preference_sets (
    userid character varying(80),
    monetary_limit integer,
    travel_distance integer
);


ALTER TABLE public.preference_sets OWNER TO postgres;

--
-- Name: user_tags; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE user_tags (
    userid character varying(80),
    tag character varying(80)
);


ALTER TABLE public.user_tags OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE users (
    id character varying(80),
    event_provider boolean,
    email_address character varying(160),
    username character varying(80),
    password character varying(80)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: id; Type: DEFAULT; Schema: public; Owner: mcguik2
--

ALTER TABLE ONLY events ALTER COLUMN id SET DEFAULT nextval('events_id_seq'::regclass);


--
-- Data for Name: event_tags; Type: TABLE DATA; Schema: public; Owner: mcguik2
--

COPY event_tags (id, tag) FROM stdin;
\.


--
-- Data for Name: events; Type: TABLE DATA; Schema: public; Owner: mcguik2
--

COPY events (id, name, host, source, creator, recurring, starttime, endtime, location, on_campus) FROM stdin;
1	Shelnutt Gallery Installation: Dia De Los Muertos	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-09-22 23:00:00	2014-12-19 23:00:00	Rensselaer Union Room 3606 (Shelnutt Gallery)	t
2	UPAC Cinema: The Incredibles	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-10-25 19:00:00	1969-12-31 19:00:00	DCC 308	t
3	Register for Class of 2015 Senior Portraits	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-10-13 09:05:00	2014-11-08 09:05:00	Rensselaer Union Games Room	t
4	RPI Bookstore Family Weekend Sale	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-10-26 10:00:00	2014-10-26 14:00:00	Rensselaer Union Bookstore	t
5	Shelnutt Gallery Installation: Dia De Los Muertos	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-09-22 23:00:00	2014-12-19 23:00:00	Rensselaer Union Room 3606 (Shelnutt Gallery)	t
6	Guinness &amp; Wing Night at the Clubhouse Pub	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-10-27 16:00:00	2014-10-27 23:59:00	Rensselaer Pub	t
7	Register for Class of 2015 Senior Portraits	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-10-13 09:05:00	2014-11-08 09:05:00	Rensselaer Union Games Room	t
8	Financial Workshop for Rensselaer Union Funded Clubs	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-10-27 16:00:00	2014-10-27 17:00:00	Rensselaer Union Mothers Wine Emporium	t
9	Shelnutt Gallery Installation: Dia De Los Muertos	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-09-22 23:00:00	2014-12-19 23:00:00	Rensselaer Union Room 3606 (Shelnutt Gallery)	t
10	Financial Workshop for Rensselaer Union Funded Clubs	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-10-28 16:00:00	2014-10-28 17:00:00	Rensselaer Union Mothers Wine Emporium	t
11	Take a Deep Breath	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-10-28 18:30:00	2014-10-28 19:15:00	Mueller Center	t
12	Register for Class of 2015 Senior Portraits	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-10-13 09:05:00	2014-11-08 09:05:00	Rensselaer Union Games Room	t
13	Drop-In Meditation Classes: Beginners Welcome.	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-10-28 18:30:00	2014-10-28 19:15:00	Mueller Center	t
14	Tuesday Trivia at the Clubhouse Pub	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-10-28 19:30:00	2014-10-28 21:30:00	Rensselaer Pub	t
15	Shelnutt Gallery Installation: Dia De Los Muertos	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-09-22 23:00:00	2014-12-19 23:00:00	Rensselaer Union Room 3606 (Shelnutt Gallery)	t
16	Mug Night at the Clubhouse Pub	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-10-29 16:00:00	2014-10-29 23:59:00	Rensselaer Pub	t
17	Register for Class of 2015 Senior Portraits	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-10-13 09:05:00	2014-11-08 09:05:00	Rensselaer Union Games Room	t
18	Wilderness Medicine Lecture Dr. Tom Welch	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-10-29 16:00:00	2014-10-29 18:00:00	Low 4050	t
19	Shelnutt Gallery Installation: Dia De Los Muertos	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-09-22 23:00:00	2014-12-19 23:00:00	Rensselaer Union Room 3606 (Shelnutt Gallery)	t
20	Grad Student Social &amp; Wine Night at the Clubhouse Pub	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-10-30 16:00:00	2014-10-30 23:59:00	Rensselaer Pub	t
21	Register for Class of 2015 Senior Portraits	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-10-13 09:05:00	2014-11-08 09:05:00	Rensselaer Union Games Room	t
22	Shelnutt Gallery Installation: Dia De Los Muertos	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-09-22 23:00:00	2014-12-19 23:00:00	Rensselaer Union Room 3606 (Shelnutt Gallery)	t
23	Tidbits by TALKS- Inaugural faculty/staff/student casual lecture series	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-10-31 09:00:00	2014-10-31 10:00:00	Rensselaer Union, Mothers	t
24	Happy Hour at the Clubhouse Pub	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-10-31 16:00:00	2014-10-31 18:00:00	Rensselaer Pub	t
25	Register for Class of 2015 Senior Portraits	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-10-13 09:05:00	2014-11-08 09:05:00	Rensselaer Union Games Room	t
26	Outing Club hosts LL BEAN Boot Mobile Outdoor Activities	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-10-31 10:00:00	2014-10-31 14:00:00	Rensselaer Union	t
27	54th Annual Sigma Chi Haunted House Co-Hosted with Alpha Phi	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-10-31 18:00:00	2014-10-31 22:00:00	Troy, NY	t
28	Shelnutt Gallery Installation: Dia De Los Muertos	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-09-22 23:00:00	2014-12-19 23:00:00	Rensselaer Union Room 3606 (Shelnutt Gallery)	t
29	Troy Compost seeks Volunteers	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-11-01 08:00:00	2014-11-01 14:00:00	Campus-wide	t
30	Public Observing at the Hirsch Observatory	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-11-01 20:00:00	2014-11-01 22:00:00	Hirsch Observatory, Jonsson-Rowland Science Center	t
31	Register for Class of 2015 Senior Portraits	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-10-13 09:05:00	2014-11-08 09:05:00	Rensselaer Union Games Room	t
32	Saturday Hours &amp; Brainstormer Quiz at the Clubhouse Pub	Union	http://events.rpi.edu/union/main/showMainEnd.rdo	UnionScraper	f	2014-11-01 16:00:00	2014-11-01 23:59:00	Rensselaer Pub	t
\.


--
-- Name: events_id_seq; Type: SEQUENCE SET; Schema: public; Owner: mcguik2
--

SELECT pg_catalog.setval('events_id_seq', 32, true);


--
-- Data for Name: preference_sets; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY preference_sets (userid, monetary_limit, travel_distance) FROM stdin;
\.


--
-- Data for Name: user_tags; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY user_tags (userid, tag) FROM stdin;
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY users (id, event_provider, email_address, username, password) FROM stdin;
\.


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

