package com.bank.beans;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Component;

/**
 *
 * @author SARAT
 */
@Component
public class Sequencer implements Serializable {

    private final AtomicInteger sequenceNumber
            = new AtomicInteger(6);

    public int next() {
        return sequenceNumber.getAndIncrement();
    }

}
