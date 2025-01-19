package org.spc.memory.compo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.spc.base.compo.BaseCompo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 内存与进程交互组件
 */
@Slf4j
@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class ProcessInteractCompo extends BaseCompo {


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

        //todo 获取linkedList


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

        //进程链表遍历
        linkedList.forEach((k, v) -> {
            if (v.pcb.state == 2) { //2代表阻塞
                blockList.add(String.valueOf(v.pcb.pcbId));
            } else if (v.pcb.state == 0) { //0代表就绪
                readyList.add(String.valueOf(v.pcb.pcbId));
            }
        });

        boolean running_flag = ProcessScheduling.runing != null; //运行判断

        //正在运行 = 当前指令 = 如果有就+1

        //二区段
        for (int i = 0; i < 64; i++) {

            if (i < getSystemMemoryUsage() * 2) { // 系统模块占用内存 *2
                greatmemory.add(3);
                continue;
            }

            if (i < getSystemMemoryUsage() * 2 + blockList.size() + readyList.size()) { //普通占用内存 占用 = 就绪 + 阻塞
                greatmemory.add(1);
                continue;
            }

            if (running_flag && i == getSystemMemoryUsage() * 2 + blockList.size() + readyList.size()) { //正在运行
                greatmemory.add(2);
                continue;
            }

            greatmemory.add(0);

        }

        return greatmemory;

    }
}
