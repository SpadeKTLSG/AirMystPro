package org.spc.memory.artifact;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.spc.base.common.constant.MemoryCT;
import org.spc.base.sys.artifact.BaseArtifact;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * 内存-进程持有工件
 */
@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class MemoryByPrHolderArtifact extends BaseArtifact {


    /**
     * 内存-进程持有状态
     */
    int[][] relateblock;

    @Override
    public void initial() {
        Class<?> clazz = this.getClass();
        Object instance = this;
        super.initial(clazz, instance);

        relateblock = new int[MemoryCT.BLOCK_LENGTH][MemoryCT.BLOCK_LENGTH];
        Arrays.stream(relateblock).forEach(
                row -> Arrays.fill(row, -1)
        );
    }

    @Override
    public void loadConfig() {

    }
}
