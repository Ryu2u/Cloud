package com.ryuzu.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ryuzu.server.config.RabbitConfig;
import com.ryuzu.server.domain.Employee;
import com.ryuzu.server.domain.PageRespBean;
import com.ryuzu.server.domain.RespBean;
import com.ryuzu.server.mapper.EmployeeMapper;
import com.ryuzu.server.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ryuzu
 * @since 2022-02-14
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public PageRespBean getAllEmployee(Integer pageNo, Integer pageSize, Employee employee, LocalDate[] beginDateScope) {
        //开启分页
        Page<Employee> page = new Page<>(pageNo,pageSize);
        IPage<Employee> iPage =  employeeMapper.getALlEmployee(page,employee,beginDateScope);

        return new PageRespBean(iPage.getTotal(),iPage.getRecords());
    }

    /**
     * 获取最大工号
     * @return RespBean
     */
    @Override
    public RespBean getMaxWordID() {
        //获取最大工号
        List<Map<String, Object>> maps = employeeMapper.selectMaps(new QueryWrapper<Employee>().select("max(workID)"));
        return RespBean.success(null,String.format("%08d",Integer.parseInt(maps.get(0).get("max(workID)").toString())+1));

    }

    @Override
    public RespBean addEmployee(Employee employee) {
        // 处理合同日期 保留两位小数
        LocalDate beginContract = employee.getBeginContract();
        LocalDate endContract = employee.getEndContract();
        long days = beginContract.until(endContract, ChronoUnit.DAYS);
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        double contractTerm = Double.parseDouble(decimalFormat.format(days / 365.00));
        employee.setContractTerm(contractTerm);
        if (employeeMapper.insert(employee) == 1) {
            Employee emp = employeeMapper.exportEmployee(employee.getId()).get(0);
            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY, emp);
            return RespBean.success("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @Override
    public List<Employee> exportEmployee(Integer id) {
       return employeeMapper.exportEmployee(id);

    }
}

