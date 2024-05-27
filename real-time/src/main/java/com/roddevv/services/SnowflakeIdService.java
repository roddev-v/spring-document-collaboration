package com.roddevv.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class SnowflakeIdService {

    private static final int WORKER_ID_BITS = 10;
    private static final int SEQUENCE_BITS = 12;

    private static final long EPOCH = 1596218400000L;

    private final long workerId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public SnowflakeIdService() {
        this.workerId = getWorkerId();
    }

    public synchronized long nextSnowflake() {
        long timestamp = Instant.now().toEpochMilli();

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & ((1 << SEQUENCE_BITS) - 1);
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        return ((timestamp - EPOCH) << (WORKER_ID_BITS + SEQUENCE_BITS))
                | (workerId << SEQUENCE_BITS)
                | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = Instant.now().toEpochMilli();
        while (timestamp <= lastTimestamp) {
            timestamp = Instant.now().toEpochMilli();
        }
        return timestamp;
    }

    private int getWorkerId() {
        final String hostname = System.getenv("HOSTNAME");
        String[] parts = hostname.split("-");
        String ordinal = parts[parts.length - 1];
        return Integer.parseInt(ordinal);
    }

    public boolean isFromCurrentWorkerNode(long snowflakeId) {
        long snowflakeWorkerId = (snowflakeId >> SEQUENCE_BITS) & ((1 << WORKER_ID_BITS) - 1);
        return snowflakeWorkerId == workerId;
    }
}

