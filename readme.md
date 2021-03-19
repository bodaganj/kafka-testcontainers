## This is a sandbox for kafka test containers

The following flow is implemented within the test:

1. Start kafka containers
   1. Start schema registry
   2. Start ZK
   3. Start kafka
2. Generate avro file
3. Send avro file to dockerized kafka
4. Consume avro file from dockerized kafka
5. Profit 