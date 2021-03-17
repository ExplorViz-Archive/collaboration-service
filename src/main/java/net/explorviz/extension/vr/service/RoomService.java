package net.explorviz.extension.vr.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import net.explorviz.extension.vr.service.factory.RoomFactory;

@ApplicationScoped
public class RoomService {

    @Inject
    RoomFactory roomFactory;
}
