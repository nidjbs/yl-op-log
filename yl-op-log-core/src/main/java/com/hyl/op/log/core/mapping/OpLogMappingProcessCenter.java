package com.hyl.op.log.core.mapping;

import com.hyl.op.log.core.CustomThreadFactory;
import com.hyl.op.log.core.ISqlLogMetaDataService;
import com.hyl.op.log.core.OpLogMetaDataDO;
import com.hyl.op.log.util.SpringBeanUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author huayuanlin
 * @date 2021/06/20 23:34
 * @desc the class desc
 */
public class OpLogMappingProcessCenter implements ApplicationListener<ContextRefreshedEvent> {

    private static final IOpLogMapping OP_LOG_MAPPING;

    static {
        OP_LOG_MAPPING = StandardOpLogMapping.getInstance();
    }

    /*** the timer executor for process op log mapping,when is not process*/
    private static final ScheduledExecutorService TIMER_EXECUTOR = new ScheduledThreadPoolExecutor(1,
            new CustomThreadFactory("op_log_monitor", true));

    /*** the executor for process op log mapping*/
    private static final ThreadPoolExecutor MAPPING_PROCESS_EXECUTOR =
            new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 3,
                    50, 1000, TimeUnit.MILLISECONDS,
                    new ArrayBlockingQueue<>(3000),
                    new CustomThreadFactory("op_log_processor"),
                    new ThreadPoolExecutor.AbortPolicy());

    /**
     * submit task
     *
     * @param task task
     * @param <T>  task type
     */
    public static <T> void submitTask(AbstractOpLogMappingTask<T> task) {
        MAPPING_PROCESS_EXECUTOR.execute(() -> OP_LOG_MAPPING.process(task.convert()));
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextStartedEvent) {
        // 支持配置 todo
        TIMER_EXECUTOR.scheduleAtFixedRate(() -> {
            List<OpLogMetaDataDO> opLogMetaDataList = SpringBeanUtil
                    .getBeanByType(ISqlLogMetaDataService.class).listNotProcessor();
            List<DbLogOpLogMappingTask> dbLogOpLogMappingTasks = opLogMetaDataList
                    .stream().map(DbLogOpLogMappingTask::new).collect(Collectors.toList());
            dbLogOpLogMappingTasks.forEach(OpLogMappingProcessCenter::submitTask);
        }, 1, 5, TimeUnit.MINUTES);
    }
}
