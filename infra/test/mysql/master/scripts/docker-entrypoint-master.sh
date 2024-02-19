#!/bin/bash
set -e

# wait until the DB image is up
until mysqladmin -u root -p"${MYSQL_ROOT_PASSWORD}" ping; do
  echo "# waiting for mysql - $(date)"
  sleep 3
done

# create user
mysql -u root -p"${MYSQL_ROOT_PASSWORD}" -e "CREATE USER 'replUser'@'172.28.0.%' IDENTIFIED BY '${MYSQL_USER_PASSWORD}'"
mysql -u root -p"${MYSQL_ROOT_PASSWORD}" -e "GRANT ALL PRIVILEGES ON *.* TO 'replUser'@'172.28.0.%' WITH GRANT OPTION"
mysql -u root -p"${MYSQL_ROOT_PASSWORD}" -e "GRANT REPLICATION SLAVE ON *.* TO 'replUser'@'172.28.0.%'"
mysql -u root -p"${MYSQL_ROOT_PASSWORD}" -e "FLUSH PRIVILEGES"
mysql -u root -p"${MYSQL_ROOT_PASSWORD}" -e "STOP SLAVE"