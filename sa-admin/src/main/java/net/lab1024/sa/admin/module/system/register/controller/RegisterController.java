package net.lab1024.sa.admin.module.system.register.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.system.employee.domain.form.EmployeeAddForm;
import net.lab1024.sa.admin.module.system.employee.domain.form.EmployeeUpdatePasswordForm;
import net.lab1024.sa.admin.module.system.employee.service.EmployeeService;
import net.lab1024.sa.admin.module.system.register.domain.form.PasswordUpdateForm;
import net.lab1024.sa.admin.module.system.register.domain.form.RegisterForm;
import net.lab1024.sa.admin.module.system.register.service.RegisterService;
import net.lab1024.sa.common.common.annoation.NoNeedLogin;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.common.util.SmartRequestUtil;
import net.lab1024.sa.common.module.support.operatelog.annoation.OperateLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @ClassName: RegisterController
 * @Description: 注册入口
 * @AuhtorName: Random
 * @DateTime: 2023-04-01 16:21:45
 **/
@RestController
@OperateLog
@Api(tags = {AdminSwaggerTagConst.System.SYSTEM_REGISTER})
public class RegisterController {

    @Resource
    private RegisterService registerService;

    @NoNeedLogin
    @ApiOperation(value = "添加员工(返回添加员工的密码) @author 卓大")
    @PostMapping("/register")
    public ResponseDTO<String> register(@Valid @RequestBody RegisterForm registerForm) {
        return registerService.doRegister(registerForm);
    }

    @NoNeedLogin
    @ApiOperation(value = "修改密码 @author 卓大")
    @PostMapping("/register/password/reset")
    public ResponseDTO<String> updatePassword(@Valid @RequestBody PasswordUpdateForm passwordUpdateForm) {
        return registerService.updatePassword(passwordUpdateForm);
    }
}
