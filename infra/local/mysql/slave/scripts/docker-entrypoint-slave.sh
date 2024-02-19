#!/bin/bash
set -e

# wait until master is completed
until mysqladmin -u root -p"${MYSQL_ROOT_PASSWORD}" -h 172.28.0.2 ping; do
  echo "# waiting for master - $(date)"
  sleep 3
done

# create user
mysql -u root -p"${MYSQL_ROOT_PASSWORD}" -e "CREATE USER 'replUser'@'172.28.0.%' IDENTIFIED BY '${MYSQL_USER_PASSWORD}'"
mysql -u root -p"${MYSQL_ROOT_PASSWORD}" -e "GRANT ALL PRIVILEGES ON *.* TO 'replUser'@'172.28.0.%' WITH GRANT OPTION"
mysql -u root -p"${MYSQL_ROOT_PASSWORD}" -e "FLUSH PRIVILEGES"

# get master log file
master_log_file=$(mysql -u root -p"${MYSQL_ROOT_PASSWORD}" -h 172.28.0.2 -e "SHOW MASTER STATUS\G" | grep mysql-bin)
re="[a-z]*-bin.[0-9]*"

if [[ $master_log_file =~ $re ]];then
  master_log_file=${BASH_REMATCH[0]}
fi

# get master log position
master_log_pos=$(mysql -u root -p"${MYSQL_ROOT_PASSWORD}" -h 172.28.0.2 -e "SHOW MASTER STATUS\G" | grep Position)
re="[0-9]+"

if [[ $master_log_pos =~ $re ]];then
  master_log_pos=${BASH_REMATCH[0]}
fi

# connect master
sql="CHANGE MASTER TO MASTER_HOST='172.28.0.2', MASTER_USER='replUser', MASTER_PASSWORD='${MYSQL_USER_PASSWORD}', MASTER_LOG_FILE='${master_log_file}', MASTER_LOG_POS=${master_log_pos}, GET_MASTER_PUBLIC_KEY=1"
mysql -u root -p"${MYSQL_ROOT_PASSWORD}" -e "${sql}"
mysql -u root -p"${MYSQL_ROOT_PASSWORD}" -e "START SLAVE"

# create data base in master DB
mysql -u root -p"${MYSQL_ROOT_PASSWORD}" -h 172.28.0.2 -e "CREATE DATABASE ${MYSQL_DB}"