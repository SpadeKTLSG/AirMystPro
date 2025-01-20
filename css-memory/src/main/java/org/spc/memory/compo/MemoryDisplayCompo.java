package org.spc.memory.compo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.spc.base.client.FileClient;
import org.spc.base.compo.BaseCompo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 内存显示组件
 */
@Slf4j
@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class MemoryDisplayCompo extends BaseCompo {

    @Autowired
    FileClient fileClient;

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

    public int getSysMemoryUsage() {
        //系统内存 = 磁盘大小 + 系统分配
        List<Integer> usage = fileClient.queryBlockStatus(); //从文件模块获取占用表

        List<Integer> temp = new ArrayList<>(); //查表, 找到系统占用大小

        usage.forEach(e -> {
            if (e == 3)
                temp.add(e);
        });

        return temp.size();
    }

    /**
     * 显示内存状态
     */
    public void displayMemory() {
        int status = 0;

        //显示内存的当前状态
//        System.out.println("Memory Status:");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
//                System.out.print(memory[i][j].getContent() + " ");
                if (memory[i][j].getContent().equals("---")) {
                    status++;
                }
            }
//            System.out.println();

        }
    }


}
