package org.spc.memory.compo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.spc.base.common.constant.MemoryCT;
import org.spc.base.compo.BaseCompo;
import org.spc.base.entity.memory.MemoryBlock;
import org.spc.memory.artifact.MemoryByPrHolderArtifact;
import org.spc.memory.artifact.MemoryLoadArtifact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 内存清理组件
 */
@Slf4j
@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class MemoryClearCompo extends BaseCompo {

    //? Artifacts

    @Autowired
    MemoryByPrHolderArtifact memoryByPrHolderArtifact;

    @Autowired
    MemoryLoadArtifact memoryLoadArtifact;

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
     * 释放结束的进程对应的内存
     *
     * @param processId 传入进程ID
     */
    public void releaseMemory(int processId) {
        int[][] relateblock = memoryByPrHolderArtifact.getRelateblock();
        MemoryBlock[][] memoryBlock = memoryLoadArtifact.getMemory();

        for (int i = 0; i < MemoryCT.BLOCK_LENGTH; i++) {
            for (int j = 0; j < MemoryCT.BLOCK_LENGTH; j++) {
                if (relateblock[i][j] == processId) {
                    memoryBlock[i][j].setContent("---"); //释放内存
                    relateblock[i][j] = -1;
                }
            }
        }

    }

}
