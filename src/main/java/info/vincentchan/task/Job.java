package info.vincentchan.task;

import com.cmbchina.xm.connection.GatewayLongConnector;
import com.cmbchina.xm.dao.ApplyDao;
import com.cmbchina.xm.datagram.DatagramUtil;
import com.cmbchina.xm.datagram.gateway.MapResponse;
import com.cmbchina.xm.domain.AcctInfo;
import com.cmbchina.xm.domain.ApplyInfo;
import com.cmbchina.xm.domain.Server;
import com.cmbchina.xm.domain.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
