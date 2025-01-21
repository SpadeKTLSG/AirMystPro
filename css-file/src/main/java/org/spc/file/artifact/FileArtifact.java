package org.spc.file.artifact;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.spc.base.common.enumeration.FCB_FIELD;
import org.spc.base.common.enumeration.FileDirTYPE;
import org.spc.base.entity.file.dir;
import org.spc.base.entity.file.file;
import org.spc.base.entity.file.struct.FCB;
import org.spc.base.sys.artifact.BaseArtifact;
import org.spc.file.app.FileSyS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static java.util.Arrays.stream;
import static org.spc.base.common.constant.FileCT.*;
import static org.spc.base.common.enumeration.FileDirTYPE.DIR;
import static org.spc.base.common.enumeration.FileDirTYPE.FILE;
import static org.spc.base.common.util.ByteUtil.*;

/**
 * 文件工件
 */
@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class FileArtifact extends BaseArtifact {

    @Autowired
    FileSyS fileSyS;


    @Override
    public void initial() {
        Class<?> clazz = this.getClass();
        Object instance = this;
        super.initial(clazz, instance);
    }

    @Override
    public void loadConfig() {

    }


    //! 0. 文件/文件夹


    /**
     * file转换为Bytes, 直接FCB转Bytes + 内容转Bytes
     *
     * @param file file
     * @return Bytes
     */
    public byte[] file2Bytes(file file) {
        return byteMerger(fcb2Bytes(file.fcb), file.content.getBytes());
    }


    /**
     * Bytes转换为file
     *
     * @param bytes Bytes
     * @return file
     */
    public file bytes2File(byte[] bytes) {
        file file = new file();

        byte[] byte_content = new byte[bytes.length - FCB_BYTE_LENGTH];        //将bytes划分为FCB和内容
        System.arraycopy(bytes, FCB_BYTE_LENGTH, byte_content, 0, bytes.length - FCB_BYTE_LENGTH);

        file.fcb = bytes2Fcb(bytes);
        file.content = new String(byte_content);
        return file;
    }


    /**
     * dir转换为Bytes
     *
     * @param dir dir
     * @return Bytes
     */
    public byte[] dir2Bytes(dir dir) {
        return byteMerger(fcb2Bytes(dir.fcb), dir.content.getBytes());
    }


    /**
     * Bytes转换为dir
     *
     * @param bytes Bytes
     * @return dir
     */
    public dir bytes2Dir(byte[] bytes) {
        dir dir = new dir();
        dir.fcb = bytes2Fcb(bytes);
        return dir;
    }


    //! 1. 文件类型操作


    /**
     * TypeFlag标识转Int
     *
     * @param fcb FCB
     * @return 0: 文件, 1: 文件夹
     */
    public Integer FileorDir2Int(FCB fcb) {
        return fcb.getTypeFlag().equals(FILE) ? 0 : 1;
    }


    /**
     * Int转FCB的TypeFlag标识
     *
     * @param num 0: 文件, 1: 文件夹
     * @return FCB的TypeFlag标识
     */
    public FileDirTYPE Int2FileorDir(Integer num) {
        return num.equals(0) ? FILE : DIR;
    }


    //! 2. 文件/文件夹名称操作


    /**
     * 获取文件/文件夹对象单独真名
     *
     * @param fcb 文件/文件夹对象的fcb
     * @return 文件/文件夹对象单独真名
     */
    public String getName(FCB fcb) {
        if (!Objects.equals(fcb.getPathName(), "/" + ":")) {
            return fcb.getPathName().split(":")[1];
        } else { //根目录
            return "-> /";
        }
    }


    /**
     * 获取文件/文件夹对象单独真名
     *
     * @param fcb 文件/文件夹对象的fcb
     * @return 文件/文件夹对象单独真名
     */
    public String[] getPathArray(FCB fcb) {
        //切分目录部分, 将其按照"/"切分为数组, 每一项都是对应的目录结构(利用删除时候的鉴权保证一定存在)
        String[] dir = fcb.pathName.split(":")[0].split("/");  //1.1切分目录部分, 将其按照"/"切分为数组, 每一项都是对应的目录结构(利用删除时候的鉴权保证一定存在)

        dir = stream(dir) //重构: 流法删除""项
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);

        return dir;
    }


    //! 3. 文件路径操作


    /**
     * Str转Path
     *
     * @param path 路径
     * @return 路径
     */
    public String str2Path(String path) {
        return '/' + path;
    }


    //! 4. 文件长度操作


    /**
     * 获得文件内容String类型的长度, 单位:字节
     *
     * @param file 文件
     * @return 文件内容String类型的长度
     */
    public Integer getFileLength(file file) {
        return FCB_BYTE_LENGTH + file.getContent().length();
    }


    /**
     * 根据内容设置文件内容Str长度, 单位:字节
     *
     * @param content 文件内容
     * @return 文件内容String类型的长度
     */
    public Integer setFileContextLength(String content) {
        return content.length();
    }


    //! 5. FCB操作

    /**
     * FCB转换为Bytes
     *
     * @return Bytes
     */
    public byte[] fcb2Bytes(FCB fcb) {

        int index = 0;

        byte[] bytes = new byte[FCB_BYTE_LENGTH];

        for (FCB_FIELD field : FCB_FIELD.values()) {

            int length = FCB_LENGTH.get(field.getName());

            Integer temp = switch (field) { //不能使用任何简单的toString, 需要自己转换为对应映射表
                case PATH_NAME -> fileSyS.bindPM(fcb.pathName);
                case START_BLOCK -> fcb.startBlock;
                case EXTEND_NAME -> fileSyS.getHandleFileCompo().findKeyiEM(fcb.extendName);
                case TYPE_FLAG -> FileorDir2Int(fcb);
                case FILE_LENGTH -> fcb.fileLength;
            };

            byte[] valueBytes = toFixLenBytes(Int2Byte(temp), length);
            System.arraycopy(valueBytes, 0, bytes, index, length);

            index += length;
        }

        return bytes;
    }


    /**
     * Bytes转换为FCB
     *
     * @param bytes Bytes
     * @return FCB
     */
    public FCB bytes2Fcb(byte[] bytes) {

        int index = 0;

        FCB fcb = new FCB();

        for (FCB_FIELD field : FCB_FIELD.values()) {

            int length = FCB_LENGTH.get(field.getName());
            byte[] valueBytes = new byte[length];

            System.arraycopy(bytes, index, valueBytes, 0, length);

            Integer temp = byte2Int(valueBytes);

            switch (field) {
                case PATH_NAME -> fcb.pathName = fileSyS.selectPM(temp);
                case START_BLOCK -> fcb.startBlock = temp;
                case EXTEND_NAME -> fcb.extendName = fileSyS.getHandleFileCompo().selectEM(temp);
                case TYPE_FLAG -> fcb.typeFlag = Int2FileorDir(temp);
                case FILE_LENGTH -> fcb.fileLength = temp;
            }

            index += length;
        }

        return fcb;
    }


    /**
     * Bytes转换为FCB, 增添PM绑定模式
     *
     * @param bytes Bytes
     * @return FCB
     */
    public FCB bytes2Fcb_AppendPM(byte[] bytes) {

        int index = 0;

        FCB fcb = new FCB();

        for (FCB_FIELD field : FCB_FIELD.values()) {

            int length = FCB_LENGTH.get(field.getName());
            byte[] valueBytes = new byte[length];

            System.arraycopy(bytes, index, valueBytes, 0, length);

            Integer temp = byte2Int(valueBytes);

            switch (field) {
//                case PATH_NAME -> fcb.pathName = selectPM(temp);
                case PATH_NAME -> fcb.pathName = TRASH_DIR_PATHNAME;// 重大bug: 无法修复, 如果没有额外的存储名字的磁盘的话, 这将导致重启后文件名丢失; 因此只能调到boot目录下了
                case START_BLOCK -> fcb.startBlock = temp;
                case EXTEND_NAME -> fcb.extendName = fileSyS.getHandleFileCompo().selectEM(temp);
                case TYPE_FLAG -> fcb.typeFlag = Int2FileorDir(temp);
                case FILE_LENGTH -> fcb.fileLength = temp;
            }

            index += length;
        }

        return fcb;
    }

}
