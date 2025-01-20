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

    /**
     * 获取系统内存使用情况
     */
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


}
