package net.lab1024.sa.admin.module.system.signin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.cache.DepartmentCache;
import net.lab1024.sa.admin.module.system.department.service.DepartmentService;
import net.lab1024.sa.admin.module.system.employee.dao.EmployeeDao;
import net.lab1024.sa.admin.module.system.employee.domain.entity.EmployeeEntity;
import net.lab1024.sa.admin.module.system.employee.domain.vo.EmployeeVO;
import net.lab1024.sa.admin.module.system.signin.dao.SignInDao;
import net.lab1024.sa.admin.module.system.signin.domain.entity.SignInEntity;
import net.lab1024.sa.admin.module.system.signin.domain.form.SignInForm;
import net.lab1024.sa.admin.module.system.signin.domain.form.SignInQueryForm;
import net.lab1024.sa.admin.module.system.signin.domain.vo.SignInVo;
import net.lab1024.sa.common.common.domain.PageResult;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.common.util.SmartBeanUtil;
import net.lab1024.sa.common.common.util.SmartPageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: SignInService
 * @Description: 签到服务
 * @AuhtorName: Random
 * @DateTime: 2023-04-02 18:48:30
 **/
@Service
public class SignInService {

    @Resource
    private SignInDao signInDao;

    @Resource
    private EmployeeDao employeeDao;

    @Resource
    private DepartmentService departmentService;

    /**
     *
     * 指定日期及部门全部签到
     *
     * @param query
     * @return
     */
    public ResponseDTO<Integer> doSignInAll(SignInQueryForm query) {
        Long deptId = query.getDepartmentId();
        Date signInDate = query.getSignInDate();

        List<Long> departmentIdList = new ArrayList<>();
        departmentIdList.addAll(departmentService.selfAndChildrenIdList(deptId));

        // 查询员工: 角色全部是学生的数据; 部门是传入的部门
        List<EmployeeEntity> employeeEntityList = employeeDao.selectByDepartmentIdAndRoleId(departmentIdList, 1L);

        // 登记签到
        List<SignInEntity> signInEntityList = employeeEntityList.stream().map(employeeEntity -> {
            SignInEntity signInEntity = new SignInEntity();
            signInEntity.setEmployeeId(employeeEntity.getEmployeeId());
            signInEntity.setActualName(employeeEntity.getActualName());
            signInEntity.setDepartmentId(employeeEntity.getDepartmentId());
            signInEntity.setGender(employeeEntity.getGender());
            signInEntity.setMobileNo(employeeEntity.getPhone());
            signInEntity.setSignState("1");
            signInEntity.setSignDate(new Date(System.currentTimeMillis()));
            signInEntity.setRemark("");
            signInEntity.setSignDate(signInDate);
            signInEntity.setDepartmentName(DepartmentCache.DEPT_CACHE.get(employeeEntity.getDepartmentId()));
            return signInEntity;
        }).collect(Collectors.toList());
        int count = signInDao.insetBatch(signInEntityList);

        return ResponseDTO.ok(count);
    }

    /**
     *
     * 单个签到
     *
     * @param signInFormList
     * @return
     */
    public ResponseDTO<Integer> doSignIn(List<SignInForm> signInFormList) {
        int updateCount = signInDao.updateBatch(signInFormList);
        return ResponseDTO.ok(updateCount);
    }

    /**
     * 查询指定条件签到数据
     * deptId 老师的部门
     * signInDate 签到日期
     * @param query
     * @return
     */
    public ResponseDTO<PageResult<SignInVo>> querySignInfo(SignInQueryForm query) {
        Page pageParam = SmartPageUtil.convert2PageQuery(query);

        List<Long> departmentIdList = new ArrayList<>();
        departmentIdList.addAll(departmentService.selfAndChildrenIdList(query.getDepartmentId()));

        List<SignInEntity> signInEntityList = signInDao.selectByDepartmentId(pageParam, departmentIdList, query.getSignInDate());
        if (CollectionUtils.isEmpty(signInEntityList)) {
            PageResult<SignInVo> PageResult = SmartPageUtil.convert2PageResult(pageParam, new ArrayList<>());
            return ResponseDTO.ok(PageResult);
        }

        List<SignInVo> signInVoList = signInEntityList.stream()
                .map(e -> SmartBeanUtil.copy(e, SignInVo.class))
                .collect(Collectors.toList());

        PageResult<SignInVo> PageResult = SmartPageUtil.convert2PageResult(pageParam, signInVoList);
        return ResponseDTO.ok(PageResult);
    }
}
