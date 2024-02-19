FROM mysql:8.0.32-debian
USER root
ADD master.cnf /etc/mysql/my.cnf
RUN mkdir /var/log/mysql
RUN touch /var/log/mysql/error.log
RUN chmod -R 777 /var/log/mysql/