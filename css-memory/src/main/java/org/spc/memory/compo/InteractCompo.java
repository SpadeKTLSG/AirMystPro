package org.spc.memory.compo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.spc.base.client.ProcessClient;
import org.spc.base.entity.process.Process;
import org.spc.base.sys.compo.BaseCompo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 交互组件
 */
@Slf4j
@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class InteractCompo extends BaseCompo {

    @Autowired
    ProcessClient processClient;

    @Autowired
    MemoryDisplayCompo memoryDisplayCompo;

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

    //! Flow

    /**
     * 传递进程内存状态
     */
    public List<Integer> displayStatus() {
        List<Integer> greatmemory = new ArrayList<>();
        // 0: 空闲 1:占用 2:正在使用  3: 系统-> DTO FAT


        //2. 2个区段
        //2.1 一区段, 获取数据, 确定对应状态的块数是多少
        //      对应方法: 获取系统占用盘块数( int ) / 获取 .... / .....
        // 一区段负责向二区段提供对应状态占用的盘块数

        //2.2 二区段, 根据已有的对应盘块数, 通过一个for循, 逐个添加对应状态的盘块数
        //      for(i 0 -> 64){
        //          for(System){i -> V; continue}
        //          for(Ready){i -> V; continue}
        //          for(Block){i -> V; continue}
        //          溢出? -> break;报警}
        //      }

        //3. 返回值, 二区段的List<Integer> : K is Item, V is Status {0,1,2}


        //一区段
        //创建展示进程需要的两个状态队列
        List<String> blockList = new ArrayList<>(10); // 阻塞队列
        List<String> readyList = new ArrayList<>(10); // 就绪队列

        ConcurrentHashMap<Integer, Process> processList = processClient.getProcessList();

        //进程链表遍历
        processList.forEach((k, v) -> {
            int state = v.getPcb().getState();
            String idName = String.valueOf(v.getPcb().getPcbId());

            switch (state) {
                case 2 -> blockList.add(idName); // 2代表阻塞
                case 0 -> readyList.add(idName); // 0代表就绪
            }
        });

        Process process = processClient.getRunningProcess();
        boolean running_flag = process != null; //运行判断

        //正在运行 = 当前指令 = 如果有就+1

        //二区段
        int sysMemoryUsage = memoryDisplayCompo.getSysMemoryUsage() * 2;
        int totalOccupiedMemory = sysMemoryUsage + blockList.size() + readyList.size();

        for (int i = 0; i < 64; i++) {
            if (i < sysMemoryUsage) { // 系统模块占用内存 *2
                greatmemory.add(3);
            } else if (i < totalOccupiedMemory) { // 普通占用内存 占用 = 就绪 + 阻塞
                greatmemory.add(1);
            } else if (running_flag && i == totalOccupiedMemory) { // 正在运行
                greatmemory.add(2);
            } else {
                greatmemory.add(0);
            }
        }

        return greatmemory;

    }
}
