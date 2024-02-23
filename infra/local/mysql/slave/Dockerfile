FROM mysql:8.0
USER root
ADD slave.cnf /etc/my.cnf
RUN mkdir /var/log/mysql
RUN touch /var/log/mysql/error.log
RUN chmod -R 777 /var/log/mysql/