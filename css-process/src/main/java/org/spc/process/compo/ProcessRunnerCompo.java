package org.spc.process.compo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.spc.base.compo.BaseCompo;
import org.spc.process.entity.Process;
import org.springframework.stereotype.Service;

/**
 * 进程运行组件
 */
@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class ProcessRunnerCompo extends BaseCompo {

    //? Artifacts

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
    public void runProcess(Process process) {
        if (pcb.state == 1) { //运行态

            String s = bufferedReader.readLine();
            log.debug("进程{}运行{}", pcb.pcbId, s);

            //分配内存
            MemoryManager.allocateMemory(pcb.pcbId, s);
            MemoryManager.displayMemory();
            pcb.lines = s;

            //进行指令处理
            if (s == null) { //Supress warning
                pcb.state = 3; //终止态
                ProcessScheduling.runing = null;
                processScheduling.getReadyToRun();
                this.stop = true;

            } else if (s.contains("=")) { //赋值语句
                String[] split = s.split("=");
                pcb.register.put(split[0], Integer.valueOf(split[1]));

            } else if (s.contains("++")) { //自增语句
                String[] split = s.split("\\+\\+");
                Integer integer = pcb.register.get(split[0]); //取出寄存器的值
                pcb.register.put(split[0], integer + 1);

            } else if (s.contains("--")) { //自减语句
                String[] split = s.split("--");
                Integer integer = pcb.register.get(split[0]);
                pcb.register.put(split[0], integer - 1);

            } else if (s.startsWith("!")) { //设备请求语句
                String c = String.valueOf(s.charAt(1));
//                    System.out.println(deviceManagement.devices.size());
                log.debug("设备{}请求", c);
                //放入设备的等待队列中
                deviceManagement.devices.get(c).arrayBlockingQueue.put(new ProcessDeviceUse(this, s.charAt(2) - '0'));

                //将其设为阻塞状态
                pcb.state = 2;
                //从就绪队列中选一个进程运行
                ProcessScheduling.blocking.add(this);
                ProcessScheduling.runing = null;

                this.wait();

            } else if (s.equals("end")) { //终止语句
                bufferedReader.close();
                file.close();
                wirth();
                stop = true;
            }

            Thread.sleep(2000); //模拟处理过程

            pcb.state = 0; //就绪态
            ProcessScheduling.readyQueues.add(ProcessScheduling.runing);
            ProcessScheduling.runing = null;
        }


    }
}
