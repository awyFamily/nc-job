package com.yhw.nc.job.admin.core.thread;


import com.yhw.nc.job.admin.core.trigger.TriggerTypeEnum;
import com.yhw.nc.job.admin.core.trigger.NcJobTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


public class JobTriggerPoolHelper {
    private static Logger logger = LoggerFactory.getLogger(JobTriggerPoolHelper.class);


    // ---------------------- trigger pool ----------------------

    // fast/slow thread pool
    private ThreadPoolExecutor fastTriggerPool = new ThreadPoolExecutor(
            50,
            200,
            60L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(1000),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "nc-job, admin JobTriggerPoolHelper-fastTriggerPool-" + r.hashCode());
                }
            });

    private ThreadPoolExecutor slowTriggerPool = new ThreadPoolExecutor(
            10,
            100,
            60L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(2000),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "nc-job, admin JobTriggerPoolHelper-slowTriggerPool-" + r.hashCode());
                }
            });


    // job timeout count
    private volatile long minTim = System.currentTimeMillis()/60000;     // ms > min
    private volatile ConcurrentMap<Integer, AtomicInteger> jobTimeoutCountMap = new ConcurrentHashMap<>();


    /**
     * add trigger
     */
    public void addTrigger(final int jobId, final TriggerTypeEnum triggerType) {

        // choose thread pool
        ThreadPoolExecutor triggerPool_ = fastTriggerPool;
        AtomicInteger jobTimeoutCount = jobTimeoutCountMap.get(jobId);
        // job-timeout 10 times in 1 min
        if (jobTimeoutCount!=null && jobTimeoutCount.get() > 10) {
            triggerPool_ = slowTriggerPool;
        }

        // trigger
        triggerPool_.execute(new Runnable() {
            @Override
            public void run() {

                long start = System.currentTimeMillis();

                try {
                    // do trigger
                    NcJobTrigger.trigger(jobId, triggerType);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                } finally {

                    // check timeout-count-map
                    long minTim_now = System.currentTimeMillis()/60000;
                    if (minTim != minTim_now) {
                        minTim = minTim_now;
                        jobTimeoutCountMap.clear();
                    }

                    // incr timeout-count-map
                    long cost = System.currentTimeMillis()-start;
                    // ob-timeout threshold 500ms
                    if (cost > 500) {
                        AtomicInteger timeoutCount = jobTimeoutCountMap.putIfAbsent(jobId, new AtomicInteger(1));
                        if (timeoutCount != null) {
                            timeoutCount.incrementAndGet();
                        }
                    }

                }

            }
        });
    }

    public void stop() {
        //triggerPool.shutdown();
        fastTriggerPool.shutdownNow();
        slowTriggerPool.shutdownNow();
        logger.info(">>>>>>>>> nc-job trigger thread pool shutdown success.");
    }

    // ---------------------- helper ----------------------

    private static JobTriggerPoolHelper helper = new JobTriggerPoolHelper();

    /**
     * @param jobId
     * @param triggerType
     */
    public static void trigger(int jobId, TriggerTypeEnum triggerType) {
        helper.addTrigger(jobId, triggerType);
    }


    public static void toStop() {
        helper.stop();
    }

}

