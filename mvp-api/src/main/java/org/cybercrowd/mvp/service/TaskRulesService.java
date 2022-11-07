package org.cybercrowd.mvp.service;

import org.cybercrowd.mvp.dto.BaseResponse;
import org.cybercrowd.mvp.dto.request.TaskRulesCreateReq;

public interface TaskRulesService {

    BaseResponse createTaskRules(TaskRulesCreateReq taskRulesCreateReq);
}
