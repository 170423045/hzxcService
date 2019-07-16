package com.hzxc.test.freemarker.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @ProjectName: bwqcService01
 * @Package: com.bwqc.test.freemarker.model
 * @ClassName: Student
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/9 21:28
 * @Version: 1.0
 */
@Data
@ToString
public class Student {
    private String name;//姓名
    private int age;//年龄
    private Date birthday;//生日
    private Float mondy;//钱包
    private List<Student> friends;//朋友列表
    private Student bestFriend;//最好的朋友
}
