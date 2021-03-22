## This is a sandbox for kafka test containers
This is a playground to run some test which sends message to dockerized kafka and smth like that.

**Note:** docker should be up and running localy.

The following flow is implemented within the test:

1. Start kafka containers
   1. Start ZK
   2. Start kafka
2. Generate string message
3. Send string message to dockerized kafka
4. Consume string message from dockerized kafka
5. Profit 