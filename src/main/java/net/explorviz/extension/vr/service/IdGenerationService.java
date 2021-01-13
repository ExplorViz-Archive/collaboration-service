package net.explorviz.extension.vr.service;

import java.util.concurrent.atomic.AtomicLong;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class IdGenerationService {
    private final AtomicLong ID_GENERATOR = new AtomicLong();
    
    public String nextId() {
        return String.valueOf(ID_GENERATOR.incrementAndGet());
    }
    
}
