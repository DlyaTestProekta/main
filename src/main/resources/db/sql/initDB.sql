-- object: public.persons | type: TABLE --
-- DROP TABLE IF EXISTS public.persons CASCADE;
CREATE TABLE public.persons (
	person_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ,
	first_name text NOT NULL,
	surname text NOT NULL,
	salary_rub numeric(10,2) NOT NULL DEFAULT 0.00,
	hobby text,
	fk_organization_id bigint NOT NULL,
	fk_certificate_id bigint NOT NULL,
	CONSTRAINT persons_pk PRIMARY KEY (person_id),
	CONSTRAINT certificate_id_uq UNIQUE (fk_certificate_id)
);
-- ddl-end --
ALTER TABLE public.persons OWNER TO admindb;
-- ddl-end --

-- object: public.organizations | type: TABLE --
-- DROP TABLE IF EXISTS public.organizations CASCADE;
CREATE TABLE public.organizations (
	organization_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ,
	name text NOT NULL,
	CONSTRAINT organizations_pk PRIMARY KEY (organization_id)
);
-- ddl-end --
ALTER TABLE public.organizations OWNER TO admindb;
-- ddl-end --

-- object: public.certificates | type: TABLE --
-- DROP TABLE IF EXISTS public.certificates CASCADE;
CREATE TABLE public.certificates (
	certificate_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ,
	code text NOT NULL,
	CONSTRAINT certificates_pk PRIMARY KEY (certificate_id)
);
-- ddl-end --
ALTER TABLE public.certificates OWNER TO admindb;
-- ddl-end --

-- object: public.persons_skills | type: TABLE --
-- DROP TABLE IF EXISTS public.persons_skills CASCADE;
CREATE TABLE public.persons_skills (
	person_id bigint NOT NULL,
	skill_id bigint NOT NULL,
	CONSTRAINT persons_skills_pk PRIMARY KEY (person_id,skill_id)
);
-- ddl-end --
ALTER TABLE public.persons_skills OWNER TO admindb;
-- ddl-end --

-- object: public.skills | type: TABLE --
-- DROP TABLE IF EXISTS public.skills CASCADE;
CREATE TABLE public.skills (
	skill_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ,
	name text NOT NULL,
	CONSTRAINT skills_pk PRIMARY KEY (skill_id)
);
-- ddl-end --
ALTER TABLE public.skills OWNER TO admindb;
-- ddl-end --

-- object: public.users | type: TABLE --
-- DROP TABLE IF EXISTS public.users CASCADE;
CREATE TABLE public.users (
	user_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ,
	role_id smallint NOT NULL,
	login text NOT NULL,
	password text NOT NULL,
	CONSTRAINT login_uq UNIQUE (login),
	CONSTRAINT users_pk PRIMARY KEY (user_id)
);
-- ddl-end --
ALTER TABLE public.users OWNER TO admindb;
-- ddl-end --

-- object: public.refresh_tokens | type: TABLE --
-- DROP TABLE IF EXISTS public.refresh_tokens CASCADE;
CREATE TABLE public.refresh_tokens (
	refresh_token_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ,
	refresh_token text NOT NULL,
	fk_user_id bigint NOT NULL,
	CONSTRAINT user_id_uq UNIQUE (fk_user_id),
	CONSTRAINT refresh_tokens_pk PRIMARY KEY (refresh_token_id)
);
-- ddl-end --
ALTER TABLE public.refresh_tokens OWNER TO admindb;
-- ddl-end --

-- object: public.notifications | type: TABLE --
-- DROP TABLE IF EXISTS public.notifications CASCADE;
CREATE TABLE public.notifications (
	notification_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ,
	person_id bigint NOT NULL,
	count bigint NOT NULL,
	CONSTRAINT person_id_uq UNIQUE (person_id),
	CONSTRAINT notifications_pk PRIMARY KEY (notification_id)
);
-- ddl-end --
ALTER TABLE public.notifications OWNER TO admindb;
-- ddl-end --

-- object: organization_id_fk | type: CONSTRAINT --
-- ALTER TABLE public.persons DROP CONSTRAINT IF EXISTS organization_id_fk CASCADE;
ALTER TABLE public.persons ADD CONSTRAINT organization_id_fk FOREIGN KEY (fk_organization_id)
REFERENCES public.organizations (organization_id) MATCH SIMPLE
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: certificate_id_fk | type: CONSTRAINT --
-- ALTER TABLE public.persons DROP CONSTRAINT IF EXISTS certificate_id_fk CASCADE;
ALTER TABLE public.persons ADD CONSTRAINT certificate_id_fk FOREIGN KEY (fk_certificate_id)
REFERENCES public.certificates (certificate_id) MATCH SIMPLE
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: person_id_fk | type: CONSTRAINT --
-- ALTER TABLE public.persons_skills DROP CONSTRAINT IF EXISTS person_id_fk CASCADE;
ALTER TABLE public.persons_skills ADD CONSTRAINT person_id_fk FOREIGN KEY (person_id)
REFERENCES public.persons (person_id) MATCH SIMPLE
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: skill_id_fk | type: CONSTRAINT --
-- ALTER TABLE public.persons_skills DROP CONSTRAINT IF EXISTS skill_id_fk CASCADE;
ALTER TABLE public.persons_skills ADD CONSTRAINT skill_id_fk FOREIGN KEY (skill_id)
REFERENCES public.skills (skill_id) MATCH SIMPLE
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: user_id_fk | type: CONSTRAINT --
-- ALTER TABLE public.refresh_tokens DROP CONSTRAINT IF EXISTS user_id_fk CASCADE;
ALTER TABLE public.refresh_tokens ADD CONSTRAINT user_id_fk FOREIGN KEY (fk_user_id)
REFERENCES public.users (user_id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --