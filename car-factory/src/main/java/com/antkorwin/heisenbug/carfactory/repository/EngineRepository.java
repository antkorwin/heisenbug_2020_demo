package com.antkorwin.heisenbug.carfactory.repository;

import java.util.UUID;

import com.antkorwin.heisenbug.carfactory.model.Engine;

import org.springframework.data.jpa.repository.JpaRepository;


public interface EngineRepository extends JpaRepository<Engine, UUID> {

	Engine findByName(String name);
}
