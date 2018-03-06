#!/bin/sh
# wait-for-mysql.sh

set -e
cmd="$@"

until  nc -z "$MYSQL_ADDR" "$MYSQL_PORT" </dev/null;
do
  >&2 echo "Mysql is unavailable - sleeping"
  sleep 5
done

>&2 echo "Mysql is up - executing command"
exec $@