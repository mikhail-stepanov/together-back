CREATE TABLE IF NOT EXISTS public.user
(
    id            uuid         NOT NULL,
    user_id       int          NOT NULL,
    password      varchar(64)  NOT NULL,
    name          varchar(255) NOT NULL,
    last_name     varchar(255) NOT NULL,
    patronymic    varchar(255) NOT NULL,
    email         varchar(255) NULL,
    phone         int          NULL,
    created_date  timestamp    NULL,
    deleted_date  timestamp    NULL,
    modified_date timestamp    NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.user_session
(
    id            uuid      NOT NULL,
    created_date  timestamp NULL,
    deleted_date  timestamp NULL,
    modified_date timestamp NULL,
    user_id       uuid      NOT NULL,
    CONSTRAINT user_session_pkey PRIMARY KEY (id),
    CONSTRAINT user_session_fkey FOREIGN KEY (user_id) REFERENCES public.user (id)
);

CREATE TABLE IF NOT EXISTS public.event
(
    id            uuid          NOT NULL,
    title         varchar(255)  NOT NULL,
    date          timestamp     NOT NULL,
    place         varchar(255)  NOT NULL,
    description   varchar(1024) NOT NULL,
    pic_path      varchar(255)  NOT NULL,
    is_future     boolean       NOT NULL,
    links         varchar(255)  NULL,
    created_date  timestamp     NULL,
    deleted_date  timestamp     NULL,
    modified_date timestamp     NULL,
    CONSTRAINT event_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.user_past_event
(
    id            uuid      NOT NULL,
    user_id       uuid      NOT NULL,
    event_id      uuid      NOT NULL,
    created_date  timestamp NULL,
    deleted_date  timestamp NULL,
    modified_date timestamp NULL,
    CONSTRAINT user_past_event_pkey PRIMARY KEY (id),
    CONSTRAINT past_user_fkey FOREIGN KEY (user_id) REFERENCES public.user (id),
    CONSTRAINT past_event_fkey FOREIGN KEY (event_id) REFERENCES public.event (id)
);

CREATE TABLE IF NOT EXISTS public.user_ticket
(
    id            uuid      NOT NULL,
    user_id       uuid      NOT NULL,
    event_id      uuid      NOT NULL,
    count         int       NOT NULL,
    created_date  timestamp NULL,
    deleted_date  timestamp NULL,
    modified_date timestamp NULL,
    CONSTRAINT user_ticket_pkey PRIMARY KEY (id),
    CONSTRAINT ticket_user_fkey FOREIGN KEY (user_id) REFERENCES public.user (id),
    CONSTRAINT ticket_event_fkey FOREIGN KEY (event_id) REFERENCES public.event (id)
);