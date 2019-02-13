package cropx.election;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cropx.election.db.dao.CampaignDao;
import cropx.election.db.entity.Campaign;
import cropx.election.db.repository.CampaignRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElectionApplicationTests {

	private static final Logger log = LogManager.getLogger();

	@Autowired
	private CampaignRepository campaignRepository;

	@Autowired
	private CampaignDao campaignDao;

	@Test
	public void testDb() {
		List<Campaign> campaigns = campaignRepository.findAll();
		log.debug("Found {} campaigns", campaigns.size());

		campaigns = campaignDao.getSpecialCampaigns();
		log.debug("Found {} special campaigns", campaigns.size());
	}
}
