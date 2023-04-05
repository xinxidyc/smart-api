package net.lab1024.sa.admin.module.system.signin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.system.signin.domain.entity.SignInEntity;
import net.lab1024.sa.admin.module.system.signin.domain.form.SignInForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

/**
 * @InterfaceName: SignInDao
 * @Description:
 * @AuhtorName: Random
 * @DateTime: 2023-04-02 19:43:40
 **/
@Mapper
@Component
public interface SignInDao extends BaseMapper<SignInEntity> {
    List<SignInEntity> selectByDepartmentId(Page pageParam, @Param("deptId") List<Long> deptId, @Param("signInDate") Date signInDate);
    int insetBatch(@Param("signInList") List<SignInEntity> signInList);
    int updateBatch(@Param("signInFormList") List<SignInForm> signInFormList);
}
