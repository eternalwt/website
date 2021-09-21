package com.greengiant.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.greengiant.website.dao.PermissionMapper;
import com.greengiant.website.pojo.model.Perm;
import com.greengiant.website.service.PermService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermServiceImpl extends ServiceImpl<PermissionMapper, Perm> implements PermService {

//    @Autowired
//    private RolePermissionService rolePermissionService;

    @Override
    public List<Perm> getPermList(Perm perm) {
        QueryWrapper<Perm> wrapper = generateQueryWrapper(perm);

        return this.list(wrapper);
    }

    private QueryWrapper<Perm> generateQueryWrapper(Perm perm) {
        QueryWrapper<Perm> wrapper = new QueryWrapper<>();

        if (perm == null) {
            return wrapper;
        }

        if (perm.getEntity() != null && !"".equals(perm.getEntity())) {
            wrapper.like("entity", perm.getEntity());
        }
        if (perm.getEntityId() != null) {
            wrapper.like("entity_id", perm.getEntityId());
        }
        if (perm.getResource() != null && !"".equals(perm.getResource())) {
            wrapper.like("resource", perm.getResource());
        }
        if (perm.getResourceCode() != null && !"".equals(perm.getResourceCode())) {
            wrapper.like("resource_code", perm.getResourceCode());
        }
        if (perm.getOperation() != null && !"".equals(perm.getOperation())) {
            wrapper.like("operation", perm.getOperation());
        }
        if (perm.getResourceInstance() != null && !"".equals(perm.getResourceInstance())) {
            wrapper.like("resource_instance", perm.getResourceInstance());
        }
        if (perm.getResourceInstanceId() != null && !"".equals(perm.getResourceInstanceId())) {
            wrapper.like("resource_instance_id", perm.getResourceInstanceId());
        }

        return wrapper;
    }

}
