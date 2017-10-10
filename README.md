# Test ES Cluster

This module allows to execute quick tests against Elastic Search connections created with Database Connector.

## How To Use

There are two api entries

* {host}/modules/testesc/test
* {host}/modules/testesc/test-connection/{connectionName}/{indexName}/{searchForType}

The first entry is there to simply check if the module is up and running.
The second will test a connection by connecting to given index and doing a matchAll query against given type. It returns
number of hits.