package net.lab1024.sa.admin.module.system.register.domain.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.common.common.util.SmartVerificationUtil;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @ClassName: PasswordUpdateForm
 * @Description: 修改密码表单
 * @AuhtorName: Random
 * @DateTime: 2023-04-01 16:50:33
 **/
@Data
public class PasswordUpdateForm {

    @ApiModelProperty("手机号")
    @NotNull(message = "手机号不能为空")
    @Length(max = 11, message = "手机号最多11字符")
    private String mobileNo;

    @ApiModelProperty("短信码")
    @NotNull(message = "短信码不能为空")
    @Length(max = 30, message = "短信码最多6字符")
    private String Sms;

    @ApiModelProperty("新密码")
    @NotBlank(message = "新密码不能为空哦")
    @Pattern(regexp = SmartVerificationUtil.PWD_REGEXP, message = "新密码请输入6-15位(数字|大小写字母|小数点)")
    private String newPassword;
}
