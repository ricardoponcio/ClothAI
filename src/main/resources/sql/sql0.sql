create table company (
	id serial primary key,
	name text not null,
	created_at timestamptz(6) not null default current_timestamp,
	updated_at timestamptz(6) null,
	deleted_at timestamptz(6) null,
	active boolean not null default true
);

create table "user" (
	id serial primary key,
	name text not null,
	email text not null,
	password text not null,
	created_at timestamptz(6) not null default current_timestamp,
	updated_at timestamptz(6) null,
	deleted_at timestamptz(6) null,
	active boolean not null default true
);

create table company_user (
	id serial primary key,
	company_id bigint not null references company(id),
	user_id bigint not null references "user"(id),
	created_at timestamptz(6) not null default current_timestamp,
	updated_at timestamptz(6) null,
	deleted_at timestamptz(6) null,
	active boolean not null default true
);

create table company_token (
	id serial primary key,
	description text null,
	token text not null,
	created_at timestamptz(6) not null default current_timestamp,
	expires_at timestamptz(6) null,
	deleted_at timestamptz(6) null,
	active boolean not null default true,
	company_id bigint not null references company(id)
);

create table cloth_resource (
	id serial primary key,
	name text null,
	identification text not null,
	url text not null,
	created_at timestamptz(6) not null default current_timestamp,
	updated_at timestamptz(6) null,
	deleted_at timestamptz(6) null,
	active boolean not null default true,
	company_id bigint not null references company(id)
);

create table cloth_ai_try_on_execution (
	id serial primary key,
	created_at timestamptz(6) not null default current_timestamp,
	execution_started_at timestamptz(6) null,
	execution_finished_at timestamptz(6) null,
	status_result boolean null,
	message_result text null,
	input_url text not null,
	output_url text not null,
	execution_identification text not null,
	token_id bigint not null references company_token(id),
	company_id bigint not null references company(id)
);

create table company_usage (
	id serial primary key,
	usage_time numeric(10,2) not null,
	execution_id bigint not null references cloth_ai_try_on_execution(id)
);
