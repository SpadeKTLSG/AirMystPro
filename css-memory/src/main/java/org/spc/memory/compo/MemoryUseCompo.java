package org.spc.memory.compo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.spc.base.client.FileClient;
import org.spc.base.common.constant.MemoryCT;
import org.spc.base.entity.memory.MemoryBlock;
import org.spc.base.sys.compo.BaseCompo;
import org.spc.memory.artifact.MemoryByPrHolderArtifact;
import org.spc.memory.artifact.MemoryLoadArtifact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 内存使用组件
 */
@Slf4j
@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class MemoryUseCompo extends BaseCompo {

    @Autowired
    FileClient fileClient;

    @Autowired
    MemoryCheckerCompo memoryCheckerCompo;

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
     * 分配内存
     *
     * @param processId 进程ID
     * @param data      数据
     */
    public void allocateMemory(int processId, String data) {

        int[][] relateblock = memoryByPrHolderArtifact.getRelateblock();
        MemoryBlock[][] memoryBlock = memoryLoadArtifact.getMemory();

        // 查找要分配的连续块
        int consecutiveBlocks = data.length() / 3 + (data.length() % 3 == 0 ? 0 : 1);
        int[] startingBlock = memoryCheckerCompo.findConsecutiveBlocks(consecutiveBlocks);

        // 如果找到连续块，则分配内存
        if (startingBlock != null) {
            MemoryBlock[] allocatedBlocks = new MemoryBlock[consecutiveBlocks];
            int blockIndex = 0;

            for (int i = startingBlock[0]; i < startingBlock[0] + consecutiveBlocks; i++) {
                for (int j = startingBlock[1]; j < MemoryCT.BLOCK_LENGTH; j++) {

                    if (blockIndex < data.length()) {
                        int blockSize = Math.min(3, data.length() - blockIndex);
                        memoryBlock[i][j].setContent(data.substring(blockIndex, blockIndex + blockSize));
                        allocatedBlocks[blockIndex] = memoryBlock[i][j];
                        blockIndex += blockSize;
                    }
                    //跟踪内存被哪些进程所占用
                    relateblock[i][j] = processId;
                }
            }

            log.debug("为进程{}分配{}内存 ", processId, allocatedBlocks[0].getContent());

        } else {
            log.error("进程{}的内存分配失败 ", processId);
        }
    }


}
