package org.spc.file.artifact;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.spc.base.artifact.BaseArtifact;
import org.spc.base.entity.file.struct.block;
import org.spc.file.app.DiskSyS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 磁盘块工件
 */
@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class BlockArtifact extends BaseArtifact {

    @Autowired
    DiskSyS diskSyS;


    @Override
    public void initial() {
        Class<?> clazz = this.getClass();
        Object instance = this;
        super.initial(clazz, instance);
    }

    @Override
    public void loadConfig() {

    }

    //! 1.磁盘块操作

    /**
     * bytes设置BLOCKS中1个block内容
     *
     * @param bytes    字节数组内容
     * @param blockNum 块号
     */
    public void setBytes21Block(byte[] bytes, int blockNum) {
        diskSyS.disk.BLOCKS.set(blockNum, new block(bytes));
    }

    /**
     * 从磁盘读取一个块的bytes内容
     *
     * @param sit 块号
     * @return 返回一个块的bytes内容
     */
    public byte[] read1Block2Bytes(int sit) {
        block tempBlock = diskSyS.disk.BLOCKS.get(sit);
        return tempBlock.bytes;
    }


}
