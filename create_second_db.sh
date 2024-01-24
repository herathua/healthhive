#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER platformuser WITH PASSWORD 'jw8s0F4';
    CREATE DATABASE platform_user_db;
    GRANT ALL PRIVILEGES ON DATABASE platform_user_db TO platformuser;
EOSQL