:set fileformat=unix

until "curl -XPUT localhost:9200/record_index_v1?pretty -H 'Content-Type: application/json' -d /my_index.json"; do
  echo "# waiting for elasticsearch - $(date)"
  sleep 3
done