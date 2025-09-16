package keelung.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import keelung.model.Sight;

@Repository
public interface SightRepository extends MongoRepository<Sight, String> {
    
    /**
     * 根據區域名稱查詢所有景點
     */
    List<Sight> findByZone(String zone);
}
