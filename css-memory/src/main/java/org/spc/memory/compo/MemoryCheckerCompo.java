package org.spc.memory.compo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.spc.base.entity.memory.MemoryBlock;
import org.spc.base.sys.compo.BaseCompo;
import org.spc.memory.artifact.MemoryLoadArtifact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 内存检查组件
 */
@Slf4j
@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class MemoryCheckerCompo extends BaseCompo {

    //? Artifacts

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
     * 查找连续块
     */
    public int[] findConsecutiveBlocks(int consecutiveBlocks) {

        //查找并返回连续分配的起始块索引
        for (int i = 0; i < 8; i++)
            for (int j = 0; j <= 8 - consecutiveBlocks; j++)
                if (isConsecutiveBlocksAvailable(i, j, consecutiveBlocks)) {
                    return new int[]{i, j};
                }

        log.warn("内存不足，无法分配连续块");
        return null;
    }


    /**
     * 检查连续块是否可用
     */
    private boolean isConsecutiveBlocksAvailable(int row, int col, int consecutiveBlocks) {
        //从给定索引开始检查
        MemoryBlock[][] memoryBlock = memoryLoadArtifact.getMemory();

        for (int j = col; j < col + consecutiveBlocks; j++)
            if (!memoryBlock[row][j].getContent().equals("---")) {
                return false;
            }

        return true;
    }
}
