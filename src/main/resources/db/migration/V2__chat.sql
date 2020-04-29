CREATE SEQUENCE chat_id_seq
    INCREMENT 1
    START 101
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE chat
(
    id bigint NOT NULL PRIMARY KEY  DEFAULT nextval('chat_id_seq'::regclass),
    sender numeric,
    recipient_id numeric,
    is_deleted boolean,
    recipient_name character varying
);
