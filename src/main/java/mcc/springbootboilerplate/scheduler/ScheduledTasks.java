package mcc.springbootboilerplate.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Scheduled(cron = "*/60 * * * * *") //Every 1 seconds , refer http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/scheduling/support/CronSequenceGenerator.html
    public void testSchedule() {
        try {
            //get value from db to check if this instance is the correct one to run the schedule job
            logger.info("test testSchedule");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
