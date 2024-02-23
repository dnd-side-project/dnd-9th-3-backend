FROM mysql:8.0
USER root
ADD master.cnf /usr/local/mysql/etc/my.cnf
RUN mkdir /var/log/mysql
RUN touch /var/log/mysql/error.log
RUN chmod -R 777 /var/log/mysql/