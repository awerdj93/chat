CREATE TABLE public.chat
(
    id bigint NOT NULL PRIMARY KEY,
    sender numeric,
    recipient_id numeric,
    is_deleted boolean,
    recipient_name character varying
);