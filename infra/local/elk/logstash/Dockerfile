ARG ELK_VERSION

# https://www.docker.elastic.co/
FROM docker.elastic.co/logstash/logstash:7.17.8
COPY /mysql-connector-java-8.0.21.jar /usr/share/logstash/logstash-core/lib/jars

# Add your logstash plugins setup here
# Example: RUN logstash-plugin install logstash-filter-json