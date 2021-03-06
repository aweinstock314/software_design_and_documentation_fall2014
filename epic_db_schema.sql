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
-- Name: event_tags; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE event_tags (
    id integer,
    tag character varying(80)
);


ALTER TABLE public.event_tags OWNER TO postgres;

--
-- Name: events; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
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


ALTER TABLE public.events OWNER TO postgres;

--
-- Name: events_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE events_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.events_id_seq OWNER TO postgres;

--
-- Name: events_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
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
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY events ALTER COLUMN id SET DEFAULT nextval('events_id_seq'::regclass);


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

