package org.spc.file.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.spc.base.common.exception.FailedException;
import org.spc.base.entity.file.dir;
import org.spc.base.entity.file.disk;
import org.spc.base.entity.file.file;
import org.spc.base.entity.file.struct.block;
import org.spc.base.sys.app.BaseApp;
import org.spc.file.compo.HandleDiskCompo;
import org.spc.file.compo.HandleFileCompo;
import org.spc.file.special.TXTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.spc.base.common.constant.FileCT.*;
import static org.spc.base.common.util.ByteUtil.str2Byte;

/**
 * 磁盘应用
 */
@Slf4j
@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class DiskSyS extends BaseApp {


    /**
     * 磁盘
     */
    public disk disk;

    @Autowired
    TXTUtil txtUtil;

    @Autowired
    DiskSyS diskSyS;

    @Autowired
    FileSyS fileSyS;


    //All Compos

    @Autowired
    HandleDiskCompo handleDiskCompo;

    @Autowired
    HandleFileCompo handleFileCompo;


    //? Default Methods

    /**
     * ?覆盖模式
     * <p>直接用当前JAVA对象覆盖磁盘映射文件TXT</p>
     */
    public void coverRebootDisk() {
        //手动把当前的FAT覆盖磁盘
        handleDiskCompo.mountFAT2BLOCKS(diskSyS.disk.BLOCKS, handleDiskCompo.FAT2Bytes(diskSyS.disk.FAT1), 1); //挂载FAT1字节对象
        handleDiskCompo.mountFAT2BLOCKS(diskSyS.disk.BLOCKS, handleDiskCompo.FAT2Bytes(diskSyS.disk.FAT2), 2); //挂载FAT2字节对象
        //写入磁盘
        txtUtil.writeAllDISK2TXT(diskSyS.disk.BLOCKS, WORKSHOP_PATH + DISK_FILE);
        putStr2Disk(txtUtil.readAllTXT2Str(WORKSHOP_PATH + DISK_FILE));
        log.debug("磁盘模块覆盖完成");
    }

    /**
     * ?格式化模式
     * <p>重新格式化磁盘, 会清空磁盘中的所有数据</p>
     */
    public void cleanRebootDisk() {
        //获取新磁盘对象
        diskSyS.disk = handleDiskCompo.initialDisk();
        coverRebootDisk();
        log.debug("{}格式化完成!", diskSyS.disk.name);
    }

    /**
     * 获取默认格式化的空BLOCKS
     *
     * @return 默认BLOCKS
     */
    public List<block> initialBLOCKS() {

        List<block> BLOCKS = new ArrayList<>(DISK_SIZE);
        for (int i = 0; i < DISK_SIZE; i++)
            BLOCKS.add(new block());

        return BLOCKS;
    }


    //! Flow

    /**
     * StrTXT内容赋值BLOCKJava对象
     *
     * @param great_str 磁盘映射文件长字符串
     */
    public void putStr2Disk(String great_str) {

        String[] str = great_str.split("\n");

        for (int i = 0; i < DISK_SIZE; i++) {

            if (i == FAT1_DIR) {
                byte[] bytes_temp = str2Byte(str[i]);
                diskSyS.disk.FAT1 = handleDiskCompo.Bytes2FAT(bytes_temp);
                handleDiskCompo.mountFAT2BLOCKS(diskSyS.disk.BLOCKS, bytes_temp, 1);
                continue;
            }

            if (i == FAT2_DIR) {
                byte[] bytes_temp = str2Byte(str[i]);
                diskSyS.disk.FAT2 = handleDiskCompo.Bytes2FAT(bytes_temp);
                handleDiskCompo.mountFAT2BLOCKS(diskSyS.disk.BLOCKS, bytes_temp, 2);
                continue;
            }

            handleDiskCompo.setStr21Block_TXT(str[i], i); //或者是setBytes21Block
        }

        //writeAllDISK2TXT(diskSyS.disk.BLOCKS, WORKSHOP_PATH + DISK_FILE); //二次写入磁盘保证一致性
        log.debug("{}初始化完成!", diskSyS.disk.name);
    }


    //! 1. 磁盘系统/

    /**
     * 将对象加入磁盘模块
     *
     * @param A 文件/文件夹对象
     */
    public void addContentDS(Object A) {


        //0. 合法性校验
        //0.1 长度校验
        if (A instanceof file file_temp) {
            if (handleFileCompo.getFileArtifact().getFileLength(file_temp) > BLOCK_SIZE) {
                log.warn("文件{}过大, 无法保存", file_temp.fcb.getPathName());
                throw new FailedException("文件过大, 无法保存");
            }
        }


        //1. 占用盘块资源 in FAT
        Integer inputPos = handleDiskCompo.set1FATUse();
        log.debug("占用了盘块: {}", inputPos);


        if (inputPos == -1) {
            log.warn("系统磁盘爆炸咯!");
            throw new FailedException("系统磁盘被撑爆了, Behave yourself!");
        }


        //2. 文件属性处理 + 2.5 写入BLOCKS

        if (A instanceof file file_temp) {
            log.debug("正在往磁盘保存文件{}", file_temp.fcb.getPathName());
            //?属性拷贝: 对于DTO文件XXX.txt, 创建path和文件名(扩展名)和文件类型都已经赋值好. (仿照Linux交互)
            file_temp.fcb.setStartBlock(inputPos); //设置文件起始块
            file_temp.fcb.setFileLength(handleFileCompo.getFileArtifact().getFileLength(file_temp)); //设置文件长度
            handleDiskCompo.getBlockArtifact().setBytes21Block(handleFileCompo.getFileArtifact().file2Bytes(file_temp), inputPos); //写入磁盘

        } else if (A instanceof dir dir_temp) {
            log.debug("正在往磁盘保存文件夹{}", dir_temp.fcb.getPathName());
            dir_temp.fcb.setStartBlock(inputPos); //设置文件起始块
            handleDiskCompo.getBlockArtifact().setBytes21Block(handleFileCompo.getFileArtifact().dir2Bytes(dir_temp), inputPos); //写入磁盘

        } else {
            log.warn("不是文件也不是文件夹, 你是什么东西?{}", A);
            //从磁盘刷新系统, 还原被占用的FAT空间
            normalRebootDisk();
            throw new RuntimeException("被投喂了奇怪的东西, 我当场趋势: " + A);
        }

        //3. FAT写入BLOCKS
        handleDiskCompo.mountFAT2BLOCKS(diskSyS.disk.BLOCKS, handleDiskCompo.FAT2Bytes(diskSyS.disk.FAT1), 1); //挂载FAT1字节对象
        handleDiskCompo.mountFAT2BLOCKS(diskSyS.disk.BLOCKS, handleDiskCompo.FAT2Bytes(diskSyS.disk.FAT2), 2); //挂载FAT2字节对象

        //4. BLOCKS全量覆写TXT
        txtUtil.writeAllDISK2TXT(diskSyS.disk.BLOCKS, WORKSHOP_PATH + DISK_FILE);


    }

    /**
     * 将对象赶出磁盘模块
     *
     * @param A 文件/文件夹对象
     */
    public void deleteContentDS(Object A) {


        if (A instanceof file file_temp) {
            log.debug("正在从磁盘删除文件{}", file_temp.fcb.getPathName());

            //1. 定位盘块, 回收对应FAT
            Integer state = handleDiskCompo.set1FATFree(file_temp.fcb.getStartBlock());
            if (state == -1) {
                log.warn("文件{}不存在, 无法删除", file_temp.fcb.getPathName());
                throw new FailedException("你请求的文件不存在, 无法删除");
            }

            //2. FAT写入BLOCKS
            handleDiskCompo.mountFAT2BLOCKS(diskSyS.disk.BLOCKS, handleDiskCompo.FAT2Bytes(diskSyS.disk.FAT1), 1);
            handleDiskCompo.mountFAT2BLOCKS(diskSyS.disk.BLOCKS, handleDiskCompo.FAT2Bytes(diskSyS.disk.FAT2), 2);

            //3. BLOCKS全量覆写TXT
            txtUtil.writeAllDISK2TXT(diskSyS.disk.BLOCKS, WORKSHOP_PATH + DISK_FILE);

        } else if (A instanceof dir dir_temp) {
            log.debug("正在从磁盘删除文件夹{}", dir_temp.fcb.getPathName());

            //1. 定位盘块, 回收对应FAT
            Integer state = handleDiskCompo.set1FATFree(dir_temp.fcb.getStartBlock());
            if (state == -1) {
                log.warn("文件夹{}不存在, 无法删除", dir_temp.fcb.getPathName());
                throw new FailedException("你请求的文件不存在, 无法删除");
            }

            //2. FAT写入BLOCKS
            handleDiskCompo.mountFAT2BLOCKS(diskSyS.disk.BLOCKS, handleDiskCompo.FAT2Bytes(diskSyS.disk.FAT1), 1);
            handleDiskCompo.mountFAT2BLOCKS(diskSyS.disk.BLOCKS, handleDiskCompo.FAT2Bytes(diskSyS.disk.FAT2), 2);

            //3. BLOCKS全量覆写TXT
            txtUtil.writeAllDISK2TXT(diskSyS.disk.BLOCKS, WORKSHOP_PATH + DISK_FILE);

        } else {//无需清除BLock和TXT对应位置内容, 下次写入和更新时直接自动覆盖对应行
            log.warn("不是文件也不是文件夹, 你是什么东西?{}", A);
            //从磁盘刷新系统, 还原被错误操作的FAT空间
            normalRebootDisk();
            throw new RuntimeException("被投喂了奇怪的东西, 我当场趋势: " + A);

        }

    }

    /**
     * 修改磁盘模块的一个对象
     * <p>属性拷贝: 对于DTO文件XXX.txt, 创建path和文件名(扩展名)和文件类型都已经赋值好. (仿照Linux交互)</p>
     *
     * @param A 被修改的对象
     * @param B 修改后的对象(完整)
     */
    public void alterContentDS(Object A, Object B) {
        //由于存在两种甚至多重类型, 因此不能简单采用直接替换的方法, 而是用删除后新增(直接覆盖)的方式
        //改文件为新文件(文件夹): 直接删除后新增到相同盘块位置; FAT保持不变
        //情境分析: 文件/文件夹内部修改, 要么改名要么改类型, 要么改内容, 或者同时; 其他的都不变

        //0. 合法性校验

        if (!(B instanceof file) & !(B instanceof dir)) {
            log.warn("不是文件也不是文件夹, 你是什么东西?{}", A);
            throw new RuntimeException("被投喂了奇怪的东西, 我当场趋势: " + A);
        }
        if (B instanceof file file_temp) {
            if (handleFileCompo.getFileArtifact().getFileLength(file_temp) > BLOCK_SIZE) {
                log.warn("文件{}过大, 无法保存", file_temp.fcb.getPathName());
                throw new FailedException("文件{}过大, 无法保存");
            }
        }

        //1. 定位文件A, 获得盘块位置
        Integer pos = A instanceof file ? ((file) A).fcb.getStartBlock() : ((dir) A).fcb.getStartBlock();

        //2. 调用addContext核心(B)精确到位置

        if (B instanceof file file_temp) {
            log.debug("正在往磁盘保存文件{}", file_temp.fcb.getPathName());
            file_temp.fcb.setStartBlock(pos); //设置文件起始块
            handleDiskCompo.getBlockArtifact().setBytes21Block(handleFileCompo.getFileArtifact().file2Bytes(file_temp), pos); //写入磁盘

        } else {
            dir dir_temp = (dir) B;
            log.debug("正在往磁盘保存文件夹{}", dir_temp.fcb.getPathName());
            dir_temp.fcb.setStartBlock(pos); //设置文件夹起始块
            handleDiskCompo.getBlockArtifact().setBytes21Block(handleFileCompo.getFileArtifact().dir2Bytes(dir_temp), pos); //写入磁盘

        }

        //4. BLOCKS全量覆写TXT
        txtUtil.writeAllDISK2TXT(diskSyS.disk.BLOCKS, WORKSHOP_PATH + DISK_FILE);
    }


    //! 2. 磁盘/

    /**
     * 查找这个文件的磁盘内的相关信息
     *
     * @param A 文件/文件夹对象
     */
    public void selectContentDS(Object A) {
        //1. 定位文件A, 获得盘块位置
        Integer pos = A instanceof file ? ((file) A).fcb.getStartBlock() : ((dir) A).fcb.getStartBlock();
        //2. 读取磁盘对应位置内容
        String str = txtUtil.read1BlockiTXT(pos);
        //3. 转换成对象
        if (A instanceof file file_temp) {
            file_temp = handleFileCompo.getFileArtifact().bytes2File(str2Byte(str));

            //4. 打印信息
            System.out.println(file_temp);
            log.info("找到了文件对象实体: {}", file_temp.fcb.getPathName());
        } else {
            dir dir_temp = handleFileCompo.getFileArtifact().bytes2Dir(str2Byte(str));

            //4. 打印信息
            System.out.println(dir_temp);
            log.info("找到了文件夹对象实体: {}", dir_temp.fcb.getPathName());

        }
    }

    /**
     * ?正常模式
     * <p>从磁盘映射文件完全加载磁盘系统</p>
     */
    public void normalRebootDisk() {
        putStr2Disk(txtUtil.readAllTXT2Str(WORKSHOP_PATH + DISK_FILE));
        log.debug("磁盘模块重读完成");
    }

    @Override
    public void initial() {
        Class<?> clazz = this.getClass();
        Object instance = this;
        super.initial(clazz, instance);

        //通电
        try {
            this.power();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void loadCompo(Class<?> clazz, Object instance) {

    }

    @Override
    public void loadConfig() {

    }

    /**
     * DiskSyS通电
     */
    public void power() throws IOException, InterruptedException {
        //磁盘不需要主动保持运行, 核心在CPU那里
    }

}
