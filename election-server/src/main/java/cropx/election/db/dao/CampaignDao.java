package cropx.election.db.dao;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cropx.election.controller.ElectionController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import cropx.election.db.entity.Campaign;

@Service
public class CampaignDao {
    private static final Logger logger = LogManager.getLogger(CampaignDao.class);

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/test";

    //  Database credentials
    static final String USER = "sa";
    static final String PASS = "";

    private static final RowMapper<Campaign> CAMPAIGN_MAPPER = (rs, rowNum) -> {
        Campaign campaign = new Campaign(rs.getLong("id"));
        return campaign;
    };

    @Value("${cropx.query.select_special_campaigns}")
    private String selectSpecialCampaigns;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    public List<Campaign> getSpecialCampaigns() {
        return jdbcTemplate.query(selectSpecialCampaigns, CAMPAIGN_MAPPER);
    }

    /**
     * insert user's vote to the DB or update an old one
     *
     * @param userId
     * @param vote
     */
    public static void upsert(int userId, int vote) throws Exception {
        if (!inRange(userId, 1, 1000)) {
            throw new IllegalArgumentException("entered userId is illegal");
        }
        if (!inRange(vote, 1, 1000)) {
            throw new IllegalArgumentException("entered vote is illegal");
        }

        Connection conn = null;
        Statement stmt = null;
        try {
            conn = openConnection();
            logger.info("Connected database successfully...");

            //Execute a query
            stmt = conn.createStatement();
            String sql;
            try {
                //insert new data:
                sql = "INSERT INTO VOTES VALUES(" + userId + "," + vote + ")";
                stmt.executeUpdate(sql);
                logger.info("Inserted a new record into the table...");

            } catch (Exception e) {
                logger.error(e);
                //update an old value
                sql = "update VOTES\n" +
                        "SET vote= " + vote + "\n" +
                        "WHERE userId = " + userId;
                stmt.executeUpdate(sql);
                logger.info("Inserted an old record in the table...");
            }

            //Clean-up environment
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt);
        }
    }

    /**
     * get top 10 voted users from DB
     *
     * @return
     */
    public static Map getTopTen() throws Exception {
        logger.info("Trying to get campaign's data from DB");
        String campaignName = "Ofer's";
        LinkedHashMap topTen = new LinkedHashMap();
        Map<String, Map> res = new HashMap<>();

        //-------------------------------------------------------------------
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = openConnection();
            logger.info("Connected database successfully...");

            //Execute query
            stmt = conn.createStatement();
            String sql = "SELECT vote, COUNT(vote) AS total_followers\n" +
                    "FROM VOTES\n" +
                    "GROUP BY  vote\n" +
                    "ORDER BY total_followers DESC\n" +
                    "LIMIT 10";
            ResultSet rs = stmt.executeQuery(sql);

            extractDataFromResultSet(topTen, rs);

            //Clean-up environment
            rs.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            closeResources(conn, stmt);
        }

        res.put(campaignName, topTen);
        return res;
    }

    private static Connection openConnection() throws ClassNotFoundException, SQLException {
        //Register JDBC driver
        Class.forName(JDBC_DRIVER);

        //Open a connection
        logger.info("Connecting to a selected database...");
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    private static void extractDataFromResultSet(LinkedHashMap topTen, ResultSet rs) throws SQLException {
        while (rs.next()) {
            topTen.put("User" + rs.getInt("vote"), rs.getInt("total_followers"));
        }
    }

    private static void closeResources(Connection conn, Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException se2) {
            se2.printStackTrace();
        }

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    private static boolean inRange(int num, int low, int max) {
        return num >= low && num <= max;
    }
}
