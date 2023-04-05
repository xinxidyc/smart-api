package net.lab1024.sa.admin.module.system.register.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import net.lab1024.sa.admin.module.system.department.dao.DepartmentDao;
import net.lab1024.sa.admin.module.system.department.domain.entity.DepartmentEntity;
import net.lab1024.sa.admin.module.system.department.domain.vo.DepartmentVO;
import net.lab1024.sa.admin.module.system.department.service.DepartmentService;
import net.lab1024.sa.admin.module.system.employee.dao.EmployeeDao;
import net.lab1024.sa.admin.module.system.employee.domain.entity.EmployeeEntity;
import net.lab1024.sa.admin.module.system.employee.domain.form.*;
import net.lab1024.sa.admin.module.system.employee.domain.vo.EmployeeVO;
import net.lab1024.sa.admin.module.system.employee.manager.EmployeeManager;
import net.lab1024.sa.admin.module.system.register.domain.form.PasswordUpdateForm;
import net.lab1024.sa.admin.module.system.register.domain.form.RegisterForm;
import net.lab1024.sa.admin.module.system.role.dao.RoleEmployeeDao;
import net.lab1024.sa.admin.module.system.role.domain.vo.RoleEmployeeVO;
import net.lab1024.sa.common.common.code.UserErrorCode;
import net.lab1024.sa.common.common.domain.PageResult;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.common.enumeration.UserTypeEnum;
import net.lab1024.sa.common.common.util.SmartBeanUtil;
import net.lab1024.sa.common.common.util.SmartPageUtil;
import net.lab1024.sa.common.module.support.token.TokenService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 员工 service
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2021-12-29 21:52:46
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright 1024创新实验室 （ https://1024lab.net ）
 */
@Service
public class RegisterService {

    private static final String PASSWORD_SALT_FORMAT = "smart_%s_admin_$^&*";

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private EmployeeManager employeeManager;

    /**
     * 新增员工
     *
     * @param registerForm
     * @return
     */
    public synchronized ResponseDTO<String> doRegister(RegisterForm registerForm) {
        
        // 校验名称是否重复
        EmployeeEntity employeeEntity = employeeDao.getByLoginName(registerForm.getLoginName(), false);
        if (null != employeeEntity) {
            return ResponseDTO.userErrorParam("登录名重复");
        }
        
        // 校验姓名是否重复
        employeeEntity = employeeDao.getByActualName(registerForm.getActualName(), false);
        if (null != employeeEntity) {
            return ResponseDTO.userErrorParam("姓名重复");
        }
        
        // 校验电话是否存在
        employeeEntity = employeeDao.getByPhone(registerForm.getPhone(), false);
        if (null != employeeEntity) {
            return ResponseDTO.userErrorParam("手机号已存在");
        }

        // TODO: 校验短信码

        // 部门是否存在
        Long departmentId = registerForm.getDepartmentId();
        DepartmentEntity department = departmentDao.selectById(departmentId);
        if (department == null) {
            return ResponseDTO.userErrorParam("部门不存在");
        }

        EmployeeEntity entity = SmartBeanUtil.copy(registerForm, EmployeeEntity.class);
        
        // 设置密码
        entity.setLoginPwd(getEncryptPwd(registerForm.getLoginPwd()));

        // 保存数据
        entity.setDeletedFlag(Boolean.FALSE);
        employeeManager.saveEmployee(entity, registerForm.getRoleIdList());

        return ResponseDTO.ok();
    }
    
    /**
     * 更新密码
     *
     * @param updatePasswordForm
     * @return
     */
    public ResponseDTO<String> updatePassword(PasswordUpdateForm updatePasswordForm) {
        // TODO: 校验短信码

        EmployeeEntity employeeEntity = employeeDao.getByPhone(updatePasswordForm.getMobileNo(), false);
        if (employeeEntity == null) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }

        // 更新密码
        EmployeeEntity updateEntity = new EmployeeEntity();
        updateEntity.setEmployeeId(employeeEntity.getEmployeeId());
        updateEntity.setLoginPwd(getEncryptPwd(updatePasswordForm.getNewPassword()));
        employeeDao.updateById(updateEntity);

        return ResponseDTO.ok();
    }
    
    /**
     * 重置密码
     *
     * @param employeeId
     * @return
     */
    public ResponseDTO<String> resetPassword(Integer employeeId) {
        String password = randomPassword();
        employeeDao.updatePassword(employeeId, getEncryptPwd(password));
        return ResponseDTO.ok(password);
    }

    private String randomPassword() {
        return RandomStringUtils.randomNumeric(6) + RandomStringUtils.randomAlphabetic(2).toLowerCase();
    }

    /**
     * 获取 加密后 的密码
     *
     * @param password
     * @return
     */
    public static String getEncryptPwd(String password) {
        return DigestUtils.md5Hex(String.format(PASSWORD_SALT_FORMAT, password));
    }
    
    /**
     * 根据登录名获取员工
     *
     * @param loginName
     * @return
     */
    public EmployeeEntity getByLoginName(String loginName) {
        return employeeDao.getByLoginName(loginName, null);
    }
}
