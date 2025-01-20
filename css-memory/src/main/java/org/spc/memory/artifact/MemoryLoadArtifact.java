package org.spc.memory.artifact;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.spc.base.artifact.BaseArtifact;
import org.spc.base.common.constant.MemoryCT;
import org.spc.memory.entity.MemoryBlock;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * 内存负载工件
 */
@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class MemoryLoadArtifact extends BaseArtifact {

    /**
     * 内存块组成的内存负载
     */
    MemoryBlock[][] memory;

    @Override
    public void initial() {
        Class<?> clazz = this.getClass();
        Object instance = this;
        super.initial(clazz, instance);

        memory = new MemoryBlock[MemoryCT.BLOCK_LENGTH][MemoryCT.BLOCK_LENGTH];
        Arrays.stream(memory).forEach(
                row -> Arrays.fill(row, new MemoryBlock())
        );
    }

    @Override
    public void loadConfig() {

    }

}
