package net.lab1024.sa.admin.module.system.signin.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Date;

/**
 * @ClassName: StudentEntity
 * @Description: 学生表实体
 * @AuhtorName: Random
 * @DateTime: 2023-04-02 18:28:49
 **/
@Data
@TableName("t_sign_in")
public class SignInEntity {
    private Long employeeId;
    private String actualName;
    private int gender;
    private String mobileNo;
    private Date signDate;
    private String remark;
    private String signState;
    private Long departmentId;
    private String departmentName;
}
