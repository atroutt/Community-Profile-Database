
--
-- Name: contact_us_subject; Type: TABLE; Schema: public; Tablespace: 
--

CREATE TABLE contact_us_subject (
    id bigint NOT NULL,
    version integer,
    name character varying(255)
);


ALTER TABLE public.contact_us_subject OWNER TO DBUSERNAME;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; 
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO DBUSERNAME;

--
-- Name: interest; Type: TABLE; Schema: public; Tablespace: 
--

CREATE TABLE interest (
    id bigint NOT NULL,
    version integer,
    subject character varying(255)
);


ALTER TABLE public.interest OWNER TO DBUSERNAME;

--
-- Name: profile_picture; Type: TABLE; Schema: public; Tablespace: 
--

CREATE TABLE profile_picture (
    id bigint NOT NULL,
    version integer,
    bytes oid,
    height integer NOT NULL,
    mime_type character varying(255),
    width integer NOT NULL
);


ALTER TABLE public.profile_picture OWNER TO DBUSERNAME;

--
-- Name: researcher; Type: TABLE; Schema: public; Tablespace: 
--

CREATE TABLE researcher (
    id bigint NOT NULL,
    version integer,
    email character varying(255),
    job_title character varying(255),
    name character varying(255),
    organization character varying(255),
    first_name character varying(50),
    last_name character varying(50),
    bibliography text,
    deleted boolean,
    is_admin boolean,
    password character varying(255),
    website character varying(255),
    salt character varying(255),
    admin boolean,
    profile_picture bigint
);


ALTER TABLE public.researcher OWNER TO DBUSERNAME;

--
-- Name: researcher_interests; Type: TABLE; Schema: public; Tablespace: 
--

CREATE TABLE researcher_interests (
    researcher bigint NOT NULL,
    interests bigint NOT NULL
);


ALTER TABLE public.researcher_interests OWNER TO DBUSERNAME;

--
-- Name: researcher_skills; Type: TABLE; Schema: public; Tablespace: 
--

CREATE TABLE researcher_skills (
    researcher bigint NOT NULL,
    skills bigint NOT NULL
);


ALTER TABLE public.researcher_skills OWNER TO DBUSERNAME;

--
-- Name: skill; Type: TABLE; Schema: public; Tablespace: 
--

CREATE TABLE skill (
    id bigint NOT NULL,
    version integer,
    subject character varying(255)
);


ALTER TABLE public.skill OWNER TO DBUSERNAME;

--
-- Name: contact_us_subject_pkey; Type: CONSTRAINT; Schema: public; Tablespace: 
--

ALTER TABLE ONLY contact_us_subject
    ADD CONSTRAINT contact_us_subject_pkey PRIMARY KEY (id);


--
-- Name: interest_pkey; Type: CONSTRAINT; Schema: public; Tablespace: 
--

ALTER TABLE ONLY interest
    ADD CONSTRAINT interest_pkey PRIMARY KEY (id);


--
-- Name: profile_picture_pkey; Type: CONSTRAINT; Schema: public; Tablespace: 
--

ALTER TABLE ONLY profile_picture
    ADD CONSTRAINT profile_picture_pkey PRIMARY KEY (id);


--
-- Name: researcher_interests_pkey; Type: CONSTRAINT; Schema: public; Tablespace: 
--

ALTER TABLE ONLY researcher_interests
    ADD CONSTRAINT researcher_interests_pkey PRIMARY KEY (researcher, interests);


--
-- Name: researcher_pkey; Type: CONSTRAINT; Schema: public; Tablespace: 
--

ALTER TABLE ONLY researcher
    ADD CONSTRAINT researcher_pkey PRIMARY KEY (id);


--
-- Name: researcher_skills_pkey; Type: CONSTRAINT; Schema: public; Tablespace: 
--

ALTER TABLE ONLY researcher_skills
    ADD CONSTRAINT researcher_skills_pkey PRIMARY KEY (researcher, skills);


--
-- Name: skill_pkey; Type: CONSTRAINT; Schema: public; Tablespace: 
--

ALTER TABLE ONLY skill
    ADD CONSTRAINT skill_pkey PRIMARY KEY (id);


--
-- Name: fk305320d911653030; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY researcher_skills
    ADD CONSTRAINT fk305320d911653030 FOREIGN KEY (researcher) REFERENCES researcher(id);


--
-- Name: fk305320d938828d73; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY researcher_skills
    ADD CONSTRAINT fk305320d938828d73 FOREIGN KEY (skills) REFERENCES skill(id);


--
-- Name: fk305320d962ed6a26; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY researcher_skills
    ADD CONSTRAINT fk305320d962ed6a26 FOREIGN KEY (skills) REFERENCES skill(id);


--
-- Name: fk305320d974d74b5d; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY researcher_skills
    ADD CONSTRAINT fk305320d974d74b5d FOREIGN KEY (researcher) REFERENCES researcher(id);


--
-- Name: fk40f5429211653030; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY researcher_interests
    ADD CONSTRAINT fk40f5429211653030 FOREIGN KEY (researcher) REFERENCES researcher(id);


--
-- Name: fk40f542926a1236d3; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY researcher_interests
    ADD CONSTRAINT fk40f542926a1236d3 FOREIGN KEY (interests) REFERENCES interest(id);


--
-- Name: fk40f5429274d74b5d; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY researcher_interests
    ADD CONSTRAINT fk40f5429274d74b5d FOREIGN KEY (researcher) REFERENCES researcher(id);


--
-- Name: fk40f5429293bb3940; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY researcher_interests
    ADD CONSTRAINT fk40f5429293bb3940 FOREIGN KEY (interests) REFERENCES interest(id);

--
-- Name: fk7cabd388ec4bb89d; Type: FK CONSTRAINT; Schema: public; 
--

ALTER TABLE ONLY researcher
    ADD CONSTRAINT fk7cabd388ec4bb89d FOREIGN KEY (profile_picture) REFERENCES profile_picture(id);


