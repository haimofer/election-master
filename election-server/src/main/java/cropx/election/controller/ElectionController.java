package cropx.election.controller;

import cropx.election.UserInfo;
import cropx.election.db.dao.CampaignDao;
import cropx.election.db.repository.CampaignRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/election", consumes = "application/json", produces = "application/json")
public class ElectionController {
	private static final Logger logger = LogManager.getLogger(ElectionController.class);

	@Autowired
	private CampaignRepository campaignRepository;

	@RequestMapping(value = "/campaign", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Map> getCampaigns() throws Exception {
		return CampaignDao.getTopTen();
	}

	@RequestMapping(value = "/postCurrentVote", method = RequestMethod.POST)
	@ResponseBody
	public void upsertUserVote(@RequestBody UserInfo details) throws Exception {
		logger.info("entered details: {}",details);
		/**
		 * note: at first we should do a validation to the password as follows:
		 * 1. send the pass as a hashed token using x-auth instead of in the body object
		 * 2. save in the DB every user's password as well
		 * 3. through an error if the validation is incorrect (and show an informative message in the gui)
		 *
		 * I'll skip this part, assuming the password which was entered is correct
		 */

		CampaignDao.upsert(details.getUserId(), details.getVote());
	}
}
