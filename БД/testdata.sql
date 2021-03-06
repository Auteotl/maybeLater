--
-- PostgreSQL database dump
--

-- Dumped from database version 14.4
-- Dumped by pg_dump version 14.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.category (
    "idCat" bigint NOT NULL,
    "typeCat" character varying NOT NULL,
    "typeChapt" integer DEFAULT 1 NOT NULL
);


ALTER TABLE public.category OWNER TO postgres;

--
-- Name: category_idCat_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."category_idCat_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."category_idCat_seq" OWNER TO postgres;

--
-- Name: category_idCat_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."category_idCat_seq" OWNED BY public.category."idCat";


--
-- Name: chapter; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.chapter (
    "idChapt" integer NOT NULL,
    "chaptText" character varying NOT NULL
);


ALTER TABLE public.chapter OWNER TO postgres;

--
-- Name: chapter_idChapt_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.chapter ALTER COLUMN "idChapt" ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."chapter_idChapt_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: urlTab; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."urlTab" (
    "idURL" bigint NOT NULL,
    "someURL" character varying NOT NULL,
    description character varying,
    "urlCat" integer,
    date date,
    "isVisited" boolean DEFAULT false NOT NULL
);


ALTER TABLE public."urlTab" OWNER TO postgres;

--
-- Name: urlTab_idURL_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."urlTab_idURL_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."urlTab_idURL_seq" OWNER TO postgres;

--
-- Name: urlTab_idURL_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."urlTab_idURL_seq" OWNED BY public."urlTab"."idURL";


--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    password character(1) NOT NULL,
    login character(1) NOT NULL,
    "idUser" bigint NOT NULL,
    email character(1) NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: category idCat; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category ALTER COLUMN "idCat" SET DEFAULT nextval('public."category_idCat_seq"'::regclass);


--
-- Name: urlTab idURL; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."urlTab" ALTER COLUMN "idURL" SET DEFAULT nextval('public."urlTab_idURL_seq"'::regclass);


--
-- Data for Name: category; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.category ("idCat", "typeCat", "typeChapt") FROM stdin;
54	╨С╨╡╨╖ ╨║╨░╤В╨╡╨│╨╛╤А╨╕╨╕	11
\.


--
-- Data for Name: chapter; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.chapter ("idChapt", "chaptText") FROM stdin;
11	Main
\.


--
-- Data for Name: urlTab; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."urlTab" ("idURL", "someURL", description, "urlCat", date, "isVisited") FROM stdin;
1	www.bestpupa.com	╨Ь╨░╨╗╨╡╨╜╤М╨║╨╕╨╣ ╨╕ ╨▓╤А╨╡╨┤╨╜╤Л╨╣	6	2021-12-12	f
2	URL		6	2021-12-12	f
68	2454553	3	5	2022-01-16	t
5			8	2021-12-12	f
76	https://www.youtube.com/watch?v=C1odyo_ymcQ	https://www.youtube.com/watch?v=C1odyo_ymcQ	53	2022-01-17	t
75	https://www.youtube.com/watch?v=c3hfWbajkR8	╨Т╨╕╨╜╤З	53	2022-01-17	t
65	2	2	51	2022-01-16	t
80	https://www.youtube.com/watch?v=Y_8VXaJCGN4	11	53	2022-01-18	t
72	https://www.youtube.com/watch?v=_Z0hherVOfY	╨Т╨╕╤З╤В ╨╝╨╛╨╜╨║╨╕	53	2022-01-16	t
78	https://winterfell.tmweb.ru/index.php	https://winterfell.tmweb.ru/index.php	53	2022-01-17	t
66	1	2	5	2022-01-16	t
99	https://www.youtube.com/watch?v=ixoDwOWuAlg	╨о╤В╤Г╨▒╤З╨╕╨║	55	2022-02-11	t
77	https://www.youtube.com/watch?v=c3hfWbajkR8	32	53	2022-01-17	t
12	www.ya.ru	╨п╨╜╨┤╨╡╨║╤Б	11	2022-01-08	f
7	www.ya.ru	╨п╨╜╨┤╨╡╨║╤Б	11	2021-12-15	f
79	https://www.youtube.com/watch?v=xtS3OhiLR3M&t=704s	Moment	53	2022-01-18	t
67	1	2	43	2022-01-16	f
81	http://www.youtube.com/watch?v=Y_8VXaJCGN4	32	53	2022-01-18	t
102	https://youtu.be/OpCatnEea84	╨║╨╛╤В╨╕╨║╨╕	57	2022-05-20	t
70	36	56	13	2022-01-16	t
85	https://www.youtube.com/watch?v=C1odyo_ymcQ	╨Т╨╕╨╜╤З	53	2022-01-18	t
86	https://www.kryukov.biz/2017/04/javafx-rowfactory-ili-kak-ya-krasil-stroki-tablitsy/	╨Ы╨╕╨╜╨║╨╛╤А	50	2022-01-18	t
87	https://javascopes.com/java-8-comparator-comparing-c45a1eab/	╨б╤Б╤Л╨╗╨║╨░	51	2022-01-18	t
88	http://javascopes.com	╨з╤В╨╛ ╤В╨╛	5	2022-01-18	t
23	2	2	12	2022-01-11	f
10	www.google.com	╨│╤Г╨│╨╗	14	2022-01-04	t
74	https://colorscheme.ru/html-colors.html	Color	53	2022-01-17	t
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (password, login, "idUser", email) FROM stdin;
\.


--
-- Name: category_idCat_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."category_idCat_seq"', 57, true);


--
-- Name: chapter_idChapt_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."chapter_idChapt_seq"', 11, true);


--
-- Name: urlTab_idURL_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."urlTab_idURL_seq"', 103, true);


--
-- Name: category category_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pk PRIMARY KEY ("idCat");


--
-- Name: chapter chapter_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chapter
    ADD CONSTRAINT chapter_pkey PRIMARY KEY ("idChapt");


--
-- Name: urlTab urltab_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."urlTab"
    ADD CONSTRAINT urltab_pk PRIMARY KEY ("idURL");


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY ("idUser");


--
-- Name: urltab_idurl_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX urltab_idurl_uindex ON public."urlTab" USING btree ("idURL");


--
-- Name: category chapter_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT chapter_fkey FOREIGN KEY ("typeChapt") REFERENCES public.chapter("idChapt") NOT VALID;


--
-- PostgreSQL database dump complete
--

