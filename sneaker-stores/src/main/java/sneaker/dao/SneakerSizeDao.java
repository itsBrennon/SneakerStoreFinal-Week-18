package sneaker.dao;

import org.springframework.data.jpa.repository.JpaRepository;


import sneaker.entity.SneakerSize;


public interface SneakerSizeDao extends JpaRepository<SneakerSize,Long> {
	
}