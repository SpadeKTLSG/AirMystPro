package org.spc.process.artifact;

import lombok.RequiredArgsConstructor;
import org.spc.base.artifact.BaseArtifact;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;


@Component
@RequiredArgsConstructor
public class QueueArtifact extends BaseArtifact {

    /**
     * 就绪队列
     */
    volatile public static ArrayBlockingQueue<ProcessA> readyQueues = new ArrayBlockingQueue<ProcessA>(10);

    /**
     * 阻塞队列
     */
    volatile public static ArrayBlockingQueue<ProcessA> blocking = new ArrayBlockingQueue<ProcessA>(10);

}
