package keelung;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SightRepository extends MongoRepository<Sight, String> {
    
    /**
     * 根據區域名稱查詢所有景點
     */
    List<Sight> findByZone(String zone);
}
