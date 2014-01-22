package info.vincentchan.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Author: Vincent Chan
 * Create At: 13-12-5 下午4:15
 */
@Component
public class JobTask implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(JobTask.class);

    @Override
    public void run() {
        logger.info("开始扫描未完成的申请单任务！");

        logger.info("结束扫描未完成的申请单任务！");
    }
}
