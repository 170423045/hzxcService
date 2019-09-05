package com.hzxc.framework.domain.course.ext;

import com.hzxc.framework.domain.course.CourseBase;
import com.hzxc.framework.domain.course.CourseMarket;
import com.hzxc.framework.domain.course.CoursePic;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ProjectName: hzxcService
 * @Package: com.hzxc.framework.domain.course.ext
 * @ClassName: CourseView
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/27 15:21
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@ToString
public class CourseView implements Serializable {
    private CourseBase courseBase;
    private CoursePic coursePic;
    private CourseMarket courseMarket;
    private TeachplanNode teachplanNode;
}
