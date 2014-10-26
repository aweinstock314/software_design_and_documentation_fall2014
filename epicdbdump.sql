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
-- Name: campus_locations; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE campus_locations (
    id integer,
    room_number integer,
    building character varying(80)
);


ALTER TABLE public.campus_locations OWNER TO postgres;

--
-- Name: event_tags; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE event_tags (
    eventid character varying(80),
    tag character varying(80)
);


ALTER TABLE public.event_tags OWNER TO postgres;

--
-- Name: events; Type: TABLE; Schema: public; Owner: mcguik2; Tablespace: 
--

CREATE TABLE events (
    id integer,
    host character varying(80),
    source character varying(80),
    creator character varying(80),
    recurring boolean,
    starttime timestamp without time zone,
    endtime timestamp without time zone,
    locationid integer,
    on_campus boolean
);


ALTER TABLE public.events OWNER TO mcguik2;

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
    email_address character varying(160)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Data for Name: campus_locations; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY campus_locations (id, room_number, building) FROM stdin;
\.


--
-- Data for Name: event_tags; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY event_tags (eventid, tag) FROM stdin;
\.


--
-- Data for Name: events; Type: TABLE DATA; Schema: public; Owner: mcguik2
--

COPY events (id, host, source, creator, recurring, starttime, endtime, locationid, on_campus) FROM stdin;
\.


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

COPY users (id, event_provider, email_address) FROM stdin;
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

