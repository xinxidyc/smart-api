package net.lab1024.sa.admin.module.system.signin.domain.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.lab1024.sa.common.common.domain.PageParam;

import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * @ClassName: SignInQueryForm
 * @Description: 签到查询表单
 * @AuhtorName: Random
 * @DateTime: 2023-04-05 14:55:32
 **/
@Data
public class SignInQueryForm extends PageParam {

    @ApiModelProperty("部门id")
    @NotNull(message = "部门id不能为空")
    private Long departmentId;

    @ApiModelProperty("签到日期")
    @NotNull(message = "签到日期不能为空")
    private Date signInDate;
}
