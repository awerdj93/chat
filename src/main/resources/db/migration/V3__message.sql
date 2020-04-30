CREATE SEQUENCE message_id_seq
    INCREMENT 1
    START 101
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE message
(
    id bigint NOT NULL PRIMARY KEY DEFAULT nextval('message_id_seq'::regclass),
    message character varying,
    created_by numeric,
    created_at timestamp with time zone,
    updated_at timestamp with time zone,
    updated_by numeric,
    is_deleted boolean,
    chat_id numeric,
    sequence bigint
);