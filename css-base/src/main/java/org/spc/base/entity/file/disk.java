package org.spc.base.entity.file;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.spc.base.entity.file.struct.block;

import java.util.List;


/**
 * 磁盘
 */
@Data
@Slf4j
public class disk {

    /**
     * 磁盘名
     */
    public String name;

    /**
     * FAT1文件分配表1
     * <p>位示图(下标位置) + 显示链接的指针(值)</p>
     * <p>下标代表当前块号, 值就是下一个块号</p>
     * <p>空值->Null_Pointer</p>
     */
    public List<Integer> FAT1;

    /**
     * FAT2文件分配表2
     * <p>位示图(下标位置) + 显示链接的指针(值)</p>
     * <p>空值->Null_Pointer</p>
     */
    public List<Integer> FAT2;

    /**
     * 一维磁盘块阵列
     * <p>->DISK_SIZE</p>
     */
    public List<block> BLOCKS;


    /**
     * 磁盘限定构造
     * <p>只允许初始化构造, 不允许自定义</p>
     */
    public disk() {
    }


    @Override
    public String toString() {
        return "disk{" +
                "name='" + name + '\'' +
                "\nFAT1=" + FAT1 +
                "\nFAT2=" + FAT2 +
                "\nBLOCKS=" + BLOCKS +
                '}';
    }

}
