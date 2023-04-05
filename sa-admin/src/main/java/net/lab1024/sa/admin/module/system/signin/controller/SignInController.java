package net.lab1024.sa.admin.module.system.signin.controller;

import io.swagger.annotations.ApiOperation;
import net.lab1024.sa.admin.module.system.employee.domain.form.EmployeeQueryForm;
import net.lab1024.sa.admin.module.system.employee.domain.vo.EmployeeVO;
import net.lab1024.sa.admin.module.system.signin.domain.form.SignInForm;
import net.lab1024.sa.admin.module.system.signin.domain.form.SignInQueryForm;
import net.lab1024.sa.admin.module.system.signin.domain.vo.SignInVo;
import net.lab1024.sa.admin.module.system.signin.service.SignInService;
import net.lab1024.sa.common.common.domain.PageResult;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @ClassName: SignInController
 * @Description:
 * @AuhtorName: Random
 * @DateTime: 2023-04-02 18:47:02
 **/
@RestController
@RequestMapping("/sign-in")
public class SignInController {

    @Resource
    private SignInService signInService;

    @PostMapping("/query")
    @ApiOperation(value = "签到信息查询 @author Random")
    public ResponseDTO<PageResult<SignInVo>> query(@Valid @RequestBody SignInQueryForm query) {
        return signInService.querySignInfo(query);
    }

    @PostMapping("/add/all")
    @ApiOperation(value = "全部签到 @author Random")
    public ResponseDTO<Integer> addALl(@Valid @RequestBody SignInQueryForm query) {
        return signInService.doSignInAll(query);
    }

    @PostMapping("/modify")
    @ApiOperation(value = "签到修改 @author Random")
    public ResponseDTO<Integer> addOne(@Valid @RequestBody List<SignInForm> signInFormList) {
        return signInService.doSignIn(signInFormList);
    }
}
