package info.vincentchan.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 发中间业务平台取实时账户信息，含余额，开户网点，账户状态，账户类型，币种
 * 类似币种要转化成国标，所以到中间业务平台获取
 * Author: Vincent Chan
 * Create At: 13-12-5 下午3:34
 */
public class Job extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(Job.class);

    private Long jobId;

    public Job(Long jobId) {
        this.jobId = jobId;
    }

    @Override
    public void run() {

    }
}
