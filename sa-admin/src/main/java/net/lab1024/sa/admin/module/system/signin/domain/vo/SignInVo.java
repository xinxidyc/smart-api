package net.lab1024.sa.admin.module.system.signin.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.common.common.enumeration.GenderEnum;
import net.lab1024.sa.common.common.swagger.ApiModelPropertyEnum;

import javax.validation.constraints.NotNull;

/**
 * @ClassName: StudentVo
 * @Description:
 * @AuhtorName: Random
 * @DateTime: 2023-04-02 18:40:22
 **/
@Data
public class SignInVo {

    @ApiModelProperty("学号")
    private String employeeId;

    @ApiModelProperty("姓名")
    private String actualName;

    @ApiModelPropertyEnum(GenderEnum.class)
    private int gender;

    @ApiModelProperty("手机号")
    private String mobileNo;

    @ApiModelProperty("班号")
    private int classNo;

    @ApiModelProperty("年级")
    private int gradeNo;

    @ApiModelProperty("住址")
    private String address;

    @ApiModelProperty("部门id")
    private Long departmentId;

    @ApiModelProperty("部门名称")
    private String departmentName;
}
