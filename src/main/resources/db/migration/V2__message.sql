CREATE TABLE public.message
(
    id bigint NOT NULL PRIMARY KEY,
    message character varying,
    created_by numeric,
    created_at date,
    updated_at date,
    updated_by numeric,
    is_deleted boolean,
    chat_id numeric,
    sequence bigint
);