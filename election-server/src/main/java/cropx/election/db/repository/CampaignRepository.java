package cropx.election.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cropx.election.db.entity.Campaign;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {

}
