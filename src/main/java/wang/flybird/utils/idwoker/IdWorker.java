package wang.flybird.utils.idwoker;

import java.io.IOException;
import java.util.Random;

import org.springframework.stereotype.Service;

import wang.flybird.utils.date.DateUtils;

/**
 * tweeter的snowflake 移植到Java:
 *   (a) id构成: 42位的时间前缀 +5位的datacenter id + 5位的机器id + 12位的sequence避免并发的数字(12位不够用时强制得到新的时间前缀)
 */
@Service
public class IdWorker {

	private final long workerId;//服务id
    private final long datacenterId;//数据中心id
    private final long idepoch;  // 时间起始标记点，作为基准，一般取系统的最近时间2012年

    private static final long workerIdBits = 5L; // 机器标识位数
    private static final long datacenterIdBits = 5L; //数据中心标识位数
    private static final long maxWorkerId = -1L ^ (-1L << workerIdBits);// 服务id最大值: 32
    private static final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);//数据中心id最大值32

    private static final long sequenceBits = 12L; //毫秒内自增位序列  每毫秒最大分配4096个
    private static final long workerIdShift = sequenceBits;
    private static final long datacenterIdShift = sequenceBits + workerIdBits;
    private static final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private static final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long lastTimestamp = -1L;
    private long sequence;
    private static final Random r = new Random();

    public IdWorker() {
        this(1344322705519L);
    }

    public IdWorker(long idepoch) {
        this(r.nextInt((int) maxWorkerId), r.nextInt((int) maxDatacenterId), 0, idepoch);
    }

    public IdWorker(long workerId, long datacenterId, long sequence) {
        this(workerId, datacenterId, sequence, 1344322705519L);
    }

    //
    public IdWorker(long workerId, long datacenterId, long sequence, long idepoch) {
        this.workerId = workerId;
        this.datacenterId = datacenterId;
        this.sequence = sequence;
        this.idepoch = idepoch;
        if (workerId < 0 || workerId > maxWorkerId) {
            throw new IllegalArgumentException("workerId is illegal: " + workerId);
        }
        if (datacenterId < 0 || datacenterId > maxDatacenterId) {
            throw new IllegalArgumentException("datacenterId is illegal: " + workerId);
        }
        if (idepoch >= System.currentTimeMillis()) {
            throw new IllegalArgumentException("idepoch is illegal: " + idepoch);
        }
    }

    public long getDatacenterId() {
        return datacenterId;
    }

    public long getWorkerId() {
        return workerId;
    }

    public long getTime() {
        return System.currentTimeMillis();
    }

    public long getId() {
        long id = nextId();
        return id;
    }
    
    public String getStrId() {
        long id = nextId();
        String currentTime = DateUtils.getCurrentTime("yyyyMMddHHmm");
        return currentTime+String.valueOf(id);
    }
    
    

    private synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new IllegalStateException("Clock moved backwards.");
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        
        long id = ((timestamp - idepoch) << timestampLeftShift)//
                | (datacenterId << datacenterIdShift)//
                | (workerId << workerIdShift)//
                | sequence;
        return id;
    }

    /**
     * get the timestamp (millis second) of id
     * @param id the nextId
     * @return the timestamp of id
     */
    public long getIdTimestamp(long id){
        return idepoch + (id >> timestampLeftShift);
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IdWorker{");
        sb.append("workerId=").append(workerId);
        sb.append(", datacenterId=").append(datacenterId);
        sb.append(", idepoch=").append(idepoch);
        sb.append(", lastTimestamp=").append(lastTimestamp);
        sb.append(", sequence=").append(sequence);
        sb.append('}');
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
    	//IdWorker uid = new IdWorker();
//        int n = 100;
//        System.out.println(uid.toString());
        //System.out.println(uid.nextId());
//        for(int i=0; i<1000; i++) {
//            System.out.write(uid.getId());
//            System.out.println(uid.nextId());
//        }
    	long l=5L;
    	System.out.println(-1^(-1<<l));
    }
}
