package net.lab1024.sa.admin.cache;

import net.lab1024.sa.admin.module.system.department.dao.DepartmentDao;
import net.lab1024.sa.admin.module.system.department.domain.vo.DepartmentVO;
import net.lab1024.sa.common.common.util.SpringContextUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: DepartmentCache
 * @Description: 部门信息缓存
 * @AuhtorName: Random
 * @DateTime: 2023-04-05 17:24:55
 **/
public class DepartmentCache {
    public static Map<Long, String> DEPT_CACHE = new HashMap<>();

    public static int initialize() {
        DepartmentDao departmentDao = SpringContextUtil.getBean(DepartmentDao.class);
        List<DepartmentVO> departmentVOList = departmentDao.listAll();
        departmentVOList.forEach(departmentVO -> DEPT_CACHE.put(departmentVO.getDepartmentId(), departmentVO.getName()));
        return departmentVOList.size();
    }
}
