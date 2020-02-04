CREATE TABLE IF NOT EXISTS public.user
(
    id            int          NOT NULL,
    user_id       int          NOT NULL,
    name          varchar(255) NOT NULL,
    email         varchar(255) NULL,
    phone         varchar(255) NULL,
    pic_url       varchar(255) NULL,
    facebook      varchar(255) NULL,
    instagram     varchar(255) NULL,
    is_verified   boolean      NULL,
    created_date  timestamp    NULL,
    deleted_date  timestamp    NULL,
    modified_date timestamp    NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.user_session
(
    id            int       NOT NULL,
    created_date  timestamp NULL,
    deleted_date  timestamp NULL,
    modified_date timestamp NULL,
    user_id       int       NOT NULL,
    CONSTRAINT user_session_pkey PRIMARY KEY (id),
    CONSTRAINT user_session_fkey FOREIGN KEY (user_id) REFERENCES public.user (id)
);

CREATE TABLE IF NOT EXISTS public.event
(
    id            int           NOT NULL,
    title         varchar(255)  NOT NULL,
    date          varchar(255)  NOT NULL,
    place         varchar(255)  NOT NULL,
    description   varchar(1024) NOT NULL,
    pic_big_url   varchar(255)  NOT NULL,
    pic_small_url varchar(255)  NOT NULL,
    video         varchar(255)  NOT NULL,
    ticketcloud   varchar(255)  NOT NULL,
    is_future     boolean       NOT NULL,
    youtube       varchar(1000) NULL,
    soundcloud    varchar(1000) NULL,
    cloud         varchar(1000) NULL,
    created_date  timestamp     NULL,
    deleted_date  timestamp     NULL,
    modified_date timestamp     NULL,
    CONSTRAINT event_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.user_past_event
(
    id            int       NOT NULL,
    user_id       int       NOT NULL,
    event_id      int       NOT NULL,
    created_date  timestamp NULL,
    deleted_date  timestamp NULL,
    modified_date timestamp NULL,
    CONSTRAINT user_past_event_pkey PRIMARY KEY (id),
    CONSTRAINT past_user_fkey FOREIGN KEY (user_id) REFERENCES public.user (id),
    CONSTRAINT past_event_fkey FOREIGN KEY (event_id) REFERENCES public.event (id)
);

CREATE TABLE IF NOT EXISTS public.user_ticket
(
    id            int       NOT NULL,
    user_id       int       NOT NULL,
    event_id      int       NOT NULL,
    link          varchar(255) NULL,
    created_date  timestamp NULL,
    deleted_date  timestamp NULL,
    modified_date timestamp NULL,
    CONSTRAINT user_ticket_pkey PRIMARY KEY (id),
    CONSTRAINT ticket_user_fkey FOREIGN KEY (user_id) REFERENCES public.user (id),
    CONSTRAINT ticket_event_fkey FOREIGN KEY (event_id) REFERENCES public.event (id)
);