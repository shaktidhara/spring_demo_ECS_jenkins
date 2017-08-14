uken-statsite
================================

Setup
---------------------------
Influxdb: CREATE DATABASE platform


Configuration
---------------------------
The only required enviornment variable is STREAM_CMD. This should contain the command you want statsite to run as part of it's configuration.

Simple setups using influxdb can be configured via enviornment variables
Set INFLUXDB_HOST, INFLUXDB_PORT, INFLUXDB_DB as needed. They default to localhost, 8086, and statsite respectively.  

Also included is a extra sink called dual-sink that will allow you to send metrics to influxdb and graphite at the same time. To do this set the above variables as needed, as well as GRAPHITE_HOST and GRAPHITE_PORT.  

host = {{ default .Env.INFLUXDB_HOST "127.0.0.1" }}
port = {{ default .Env.INFLUXDB_PORT "8086" }}
database = {{ default .Env.INFLUXDB_DB "platform" }}
username = {{ default .Env.INFLUXDB_USERNAME "root" }}
password = {{ default .Env.INFLUXDB_PASSWORD "root" }}
port = {{ default .Env.STATSITE_PORT "8125" }}
udp_port = {{ default .Env.STATSITE_PORT "8125" }}
log_level = {{ default .Env.STATSITE_LOG_LEVEL "INFO" }}
log_facility = {{ default .Env.STATSITE_LOG_FACILITY "local0" }}
flush_interval =  {{ default .Env.STATSITE_FLUSH_INTERVAL "10" }}
timer_eps = {{ default .Env.STATSITE_TIMER_EPS "0.01" }}
set_eps = {{ default .Env.STATSITE_SET_EPS "0.02" }}
stream_cmd = {{ default .Env.STREAM_CMD "python /bin/sinks/influxdb.py /conf/influxdb.ini INFO" }}

Usage
---------------------------
docker run --name uken_statsite -p 8125:8125 -p 8125:8125/udp -e INFLUXDB_HOST="10.0.0.51"  uken:statsite
