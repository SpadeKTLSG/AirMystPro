package org.spc.process.compo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.spc.base.compo.BaseCompo;
import org.spc.process.artifact.RunningProcessArtifact;
import org.spc.process.entity.Process;
import org.spc.process.entity.struct.Pcb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 进程运行组件
 */
@Slf4j
@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class ProcessRunnerCompo extends BaseCompo {

    //? Artifacts
    @Autowired
    RunningProcessArtifact runningProcessArtifact;

    @Autowired
    ProcessSchedulerCompo processSchedulingCompo;

    @Autowired
    IOHandlerCompo ioHandlerCompo;

    //? Default Methods
    @Override
    public void initial() {
        Class<?> clazz = this.getClass();
        Object instance = this;
        super.initial(clazz, instance);
    }

    @Override
    public void loadArtifact(Class<?> clazz, Object instance) {

    }

    @Override
    public void loadConfig() {

    }


    //! Func

    /**
     * (CPU)运行进程
     */
    public void runProcess(Process process) throws IOException, InterruptedException {
        Pcb pcb = process.getPcb();

        if (pcb.getState() != 1) {
            return;
        }
        //运行态

        String s = process.getBufferedReader().readLine();
        log.debug("进程{}运行{}", pcb.getPcbId(), s);

        //分配内存 todo
        //MemoryManager.allocateMemory(pcb.getPcbId(), s);

        pcb.setTargetLine(s);

        //进行指令处理
        if (s == null) { //Supress warning
            pcb.setState(3); //终止态
            runningProcessArtifact.setRunningProcess(null);
            processSchedulingCompo.getReadyToRun();
            process.setStop(true);

        } else if (s.contains("=")) { //赋值语句
            String[] split = s.split("=");
            pcb.getRegister().put(split[0], Integer.valueOf(split[1]));

        } else if (s.contains("++")) { //自增语句
            String[] split = s.split("\\+\\+");
            Integer integer = pcb.getRegister().get(split[0]); //取出寄存器的值
            pcb.getRegister().put(split[0], integer + 1);

        } else if (s.contains("--")) { //自减语句
            String[] split = s.split("--");
            Integer integer = pcb.getRegister().get(split[0]);
            pcb.getRegister().put(split[0], integer - 1);

        } else if (s.startsWith("!")) { //设备请求语句
            String c = String.valueOf(s.charAt(1));
//
            //放入设备的等待队列中 todo
//            deviceManagement.devices.get(c).arrayBlockingQueue.put(new ProcessDeviceUse(this, s.charAt(2) - '0'));

            //将其设为阻塞状态
            pcb.setState(2);
            //从就绪队列中选一个进程运行
            processSchedulingCompo.getBlockQueueArtifact().getArrayBlockingQueue().add(process);
            processSchedulingCompo.getRunningProcessArtifact().setRunningProcess(null);

            this.wait();

        } else if (s.equals("end")) { //终止语句
            process.getBufferedReader().close();
            process.getFile().close();
            ioHandlerCompo.write2File(process);
            process.setStop(true);
        }

        Thread.sleep(2000); //模拟处理过程

        pcb.setState(0); //就绪态
        processSchedulingCompo.getReadyQueueArtifact().getArrayBlockingQueue().add(runningProcessArtifact.getRunningProcess());
        processSchedulingCompo.getRunningProcessArtifact().setRunningProcess(null);
    }


}
