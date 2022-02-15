package com.ryuzu.server.service.impl;

import com.ryuzu.server.domain.MailLog;
import com.ryuzu.server.mapper.MailLogMapper;
import com.ryuzu.server.service.IMailLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ryuzu
 * @since 2022-02-14
 */
@Service
public class MailLogServiceImpl extends ServiceImpl<MailLogMapper, MailLog> implements IMailLogService {

}
