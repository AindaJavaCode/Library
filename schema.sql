--
-- PostgreSQL database dump
--

\restrict iSO0p0XWKH3g2pTSktIII42oPkcpQfEiT7MUNUi7IlleSMndH7p7pF1aCeECigm

-- Dumped from database version 18.6
-- Dumped by pg_dump version 18.6

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: pgcrypto; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;


--
-- Name: EXTENSION pgcrypto; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION pgcrypto IS 'cryptographic functions';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: books; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.books (
    book_id integer CONSTRAINT "Books_BookId_not_null" NOT NULL,
    book_name text CONSTRAINT "Books_BookName_not_null" NOT NULL,
    num_pages integer CONSTRAINT "Books_NumOfPages_not_null" NOT NULL,
    book_author text CONSTRAINT "Books_Author_not_null" NOT NULL,
    is_rare boolean CONSTRAINT "Books_IsRare_not_null" NOT NULL,
    is_borrowed boolean CONSTRAINT "Books_isBorrowed_not_null" NOT NULL,
    isborrowed_by_customer_id integer
);


ALTER TABLE public.books OWNER TO postgres;

--
-- Name: Books_BookId_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.books ALTER COLUMN book_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Books_BookId_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 1000
    CACHE 1
);


--
-- Name: admin_credentials; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.admin_credentials (
    admin_id integer NOT NULL,
    admin_username text NOT NULL,
    admin_password text NOT NULL
);


ALTER TABLE public.admin_credentials OWNER TO postgres;

--
-- Name: admin_credentials_admin_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.admin_credentials ALTER COLUMN admin_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.admin_credentials_admin_id_seq
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    MAXVALUE 1
    CACHE 1
);


--
-- Name: customer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customer (
    customer_id integer NOT NULL,
    customer_username text,
    customer_password text,
    customer_email text
);


ALTER TABLE public.customer OWNER TO postgres;

--
-- Name: customer_customer_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.customer ALTER COLUMN customer_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.customer_customer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 1000
    CACHE 1
);


--
-- Name: books Books_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.books
    ADD CONSTRAINT "Books_pkey" PRIMARY KEY (book_id);


--
-- Name: admin_credentials admin_credentials_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.admin_credentials
    ADD CONSTRAINT admin_credentials_pkey PRIMARY KEY (admin_id);


--
-- Name: customer customer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (customer_id);


--
-- PostgreSQL database dump complete
--

\unrestrict iSO0p0XWKH3g2pTSktIII42oPkcpQfEiT7MUNUi7IlleSMndH7p7pF1aCeECigm

