package mcc.springbootboilerplate.repository;

import mcc.springbootboilerplate.utils.LcaseAliasTpEntityMapResultTransformer;
import mcc.springbootboilerplate.utils.ResourceReader;
import org.apache.commons.compress.utils.Lists;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Repository
public class SampleSqlRepository {

    Logger logger = LoggerFactory.getLogger(SampleSqlRepository.class);


    @Autowired
    private SessionFactory sessionFactory;



    @Value("classpath:sql/selectAllDummy.sql")
    private Resource selectAllDummy;
    private String selectAllDummySql;

    @PostConstruct
    public void init(){
        selectAllDummySql = ResourceReader.asString(selectAllDummy);
    }

    public List<Map<String, Object>> selectAllDummy() {
        String sqlQuery = this.selectAllDummySql;
        try (Session session =  sessionFactory.openSession()){
            List resultList = session.createSQLQuery(sqlQuery).setResultTransformer(LcaseAliasTpEntityMapResultTransformer.INSTANCE).getResultList();
            for (Object o: resultList) {
                logger.info(o.toString());
            }
            return resultList;
        } catch (Exception ex){
            logger.error("error", ex);
            return Lists.newArrayList();
        } finally {
        }
    }
}
