## This is a sandbox for kafka test containers

The following flow is implemented within the test:

1. Start kafka containers
   1. Start ZK
   2. Start kafka
2. Generate string message
3. Send string message to dockerized kafka
4. Consume string message from dockerized kafka
5. Profit 