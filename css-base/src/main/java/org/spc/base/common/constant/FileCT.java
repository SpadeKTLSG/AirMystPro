package org.spc.base.common.constant;

import java.util.List;
import java.util.Map;

public interface FileCT {

    //? GlobalField全局变量&信息定义

    //!磁盘DISK

    /**
     * 默认磁盘名 = 西数XXX
     */
    String DISK_NAME = "西数8KB岩浆暖气片";

    /**
     * 模拟磁盘文件名 = disk.txt
     */
    String DISK_FILE = "\\disk.txt";

    /**
     * 与外界交互文件路径固定
     */
    String WORKSHOP_PATH = "src\\main\\resources\\common\\file";

    /**
     * 磁盘大小 = 128
     * <p>单磁盘, 块数128个盘块</p>
     */
    Integer DISK_SIZE = 128;

    /**
     * 磁盘块大小 = 64
     * <p>单磁盘, 每个盘块64字节</p>
     */
    Integer BLOCK_SIZE = 64;

    /**
     * FAT1 位置 = 0号盘块
     */
    Integer FAT1_DIR = 0;

    /**
     * FAT2 位置 = 1号盘块
     */
    Integer FAT2_DIR = FAT1_DIR + 1;
    /**
     * 根目录下所有目录挂载位置 = 2号盘块
     */
    Integer ROOT_DIR_BLOCK = FAT2_DIR + 1;
    /**
     * FAT 大小  = BLOCK_SIZE
     */
    Integer FAT_SIZE = BLOCK_SIZE;
    /**
     * FAT分配表中的Null指针 = 514
     */
    Integer Null_Pointer = 514;
    /**
     * 根目录本体挂载位置 = 不存在
     */
    Integer ROOT_DIR_BLOCK_WHERE = Null_Pointer;


    //!目录DIR

    String TRASH_DIR_PATHNAME = "/boot:trash";

    /**
     * FCB占用的字节数 = 8B
     */
    Integer FCB_BYTE_LENGTH = 8;

    /**
     * 文件目录树结构的名称
     */
    String FILE_TREE_NAME = "尤克特拉希尔";
    /**
     * 根目录鉴权标识 = root
     */
    String ROOT_AUTH = "root";
    /**
     * 默认的文件夹扩展名不存在
     * <p>但是为了能够识别, 用空格代替</p>
     */
    List<String> DIR_EXTEND = List.of(" ", "  ");


    //?文件夹没有类型
    /**
     * 默认的文件夹名 = 新文件夹
     */
    String DIR_NAME_DEFAULT = "新文件夹";
    /**
     * 默认/初始化的文件夹长度 = 0
     */
    Integer DIR_LENGTH_DEFAULT = 0;
    /**
     * 一次输入的最大字符数 = 255/一个字节容量 -> 一个块的大小-FCB大小 = 56个ASCII字符
     * <p>输入长度超过时自动分配空间</p>
     */
    Integer MAX_1INPUT = BLOCK_SIZE - FCB_BYTE_LENGTH;


    //!文件FILE
    /**
     * 文件扩展名S List = .txt,...
     */
    List<String> FILE_EXTEND = List.of(".", ".txt", ".docx", ".md", ".tmp", ".dll", ".java");
    /**
     * 默认的文件名 = 新文件夹
     */
    String FILE_NAME_DEFAULT = "新文件";
    /**
     * 默认/初始化的文件长度 = 0
     */
    Integer FILE_LENGTH_DEFAULT = 0;
    /**
     * FCB中每个字段的字节数
     * <p>目录路径 3</p>
     * <p>磁盘路径 1</p>
     * <p>扩展名 2</p>
     * <p>类型标识 1</p>
     * <p>长度 1</p>
     */
    Map<String, Integer> FCB_LENGTH = Map.of(
            "pathName", 3,
            "startBlock", 1,
            "extendName", 2,
            "typeFlag", 1,
            "fileLength", 1
    );

    //? InteractField 交互变量&信息定义
    /**
     * 用户新增扩展名序列
     * <p>预定义了两个ppt项, 后续增添时需要判断重复</p>
     */
    List<String> AddedEXTEND = List.of(".ppt", "pptx");


}
