package net.lab1024.sa.admin.module.system.signin.domain.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.common.common.enumeration.GenderEnum;
import net.lab1024.sa.common.common.swagger.ApiModelPropertyEnum;
import net.lab1024.sa.common.common.util.SmartVerificationUtil;
import net.lab1024.sa.common.common.validator.enumeration.CheckEnum;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Date;

/**
 * @ClassName: StudentForm
 * @Description:
 * @AuhtorName: Random
 * @DateTime: 2023-04-02 18:35:26
 **/
@Data
public class SignInForm {

    @ApiModelProperty("学号")
    @NotNull(message = "学号不能为空")
    @Length(max = 16, message = "姓名最多16字符")
    private String employeeId;

    @ApiModelProperty("姓名")
    @NotNull(message = "姓名不能为空")
    @Length(max = 30, message = "姓名最多30字符")
    private String actualName;

    @ApiModelPropertyEnum(GenderEnum.class)
    @CheckEnum(value = GenderEnum.class, message = "性别错误")
    private int gender;

    @ApiModelProperty("手机号")
    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = SmartVerificationUtil.PHONE_REGEXP, message = "手机号格式不正确")
    private String mobileNo;

    @ApiModelProperty("状态")
    @NotNull(message = "状态不能为空")
    @Length(max = 1, message = "姓名最多1字符")
    private String signState;

    @ApiModelProperty("日期")
    @NotNull(message = "日期不能为空")
    private Date signDate;

    @ApiModelProperty("备注")
    @Length(max = 150, message = "备注最多150字符")
    private String remark;

    @ApiModelProperty("部门id")
    @NotNull(message = "部门id不能为空")
    private Long departmentId;

    @ApiModelProperty("部门名称")
    @NotNull(message = "部门名称不能为空")
    private String departmentName;
}
