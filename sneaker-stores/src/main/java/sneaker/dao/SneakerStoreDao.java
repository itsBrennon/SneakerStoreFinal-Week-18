package sneaker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import sneaker.entity.SneakerStore;

public interface SneakerStoreDao extends JpaRepository <SneakerStore,Long>{
	
}

