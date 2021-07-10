package com.greengiant.infrastructure.utils.office;

public class PoiExcelUtil {

    /***
     * 【现在我关心的问题是如何设计出正交的API】
     * 1.创建一个新文件，含有n个sheet：每个sheet一个二维数组的标题和内容
     * 2.直接创建一个有1个sheet的文件，参数：二维数组的标题和数据
     * 3.给现有的文件添加一个sheet，包括sheet名和数据
     * 【2和3是啥关系？】上述的每一项功能要能设置标题、数据的样式
     *
     * 4.删除多个sheet
     * 5.特例：删除一个sheet
     * 6.读取所有sheet的数据
     * 7.读取其中某个index的sheet数据
     *
     */

    /***
     * 建立本util的指导思想：
     * 可以边用边建立，不用一次性弄完；但建的过程中要依据上面的思路来创建，最终得到一个正交的API集合，如果发现问题（不合适的地方），则可以修改和优化设计
     * 部分参考资料：
     * https://mkyong.com/java/apache-poi-reading-and-writing-excel-file-in-java/
     * https://www.cnblogs.com/dzpykj/p/8417738.html
     *
     * 零散问题分析：
     * 1.对于生成excel来说，是不是完全没有没有必要准备模板
     * 2.性能经过测试才更加可靠
     *
     *
     */

}
