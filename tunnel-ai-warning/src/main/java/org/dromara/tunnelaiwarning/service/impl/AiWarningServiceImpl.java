package org.dromara.tunnelaiwarning.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.utils.DateUtils;
import org.dromara.tunnelaiwarning.service.IAiWarningService;
import org.dromara.tunnelenvironmentmonitor.domain.TUnitWarning;
import org.dromara.tunnelenvironmentmonitor.mapper.EnvAlarmMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * 入场信息 业务层处理
 *
 * @author ZZT
 */
@Slf4j
@Service
public class AiWarningServiceImpl implements IAiWarningService {

    @Autowired
    private EnvAlarmMapper envAlarmMapper;

    /**
     * 新增AI报警信息
     *
     * @param requestData
     * @return
     */
    @Override
    public int insertAiWarnning(Map<String, Object> requestData) {

        TUnitWarning InfoData = new TUnitWarning();
        int rows = 0;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        InfoData.setProjcode("DLGS");
        InfoData.setUnitcode("60-04.0003.00.00");
        InfoData.setStatus(0L);
        Map<String, Object> result = (Map<String, Object>) requestData.get("Result");
        Map<String, Object> media = (Map<String, Object>) requestData.get("Media");
        InfoData.setWarningcontent((String) media.get("MediaDesc")+ "报警：" + (String) result.get("Description"));
        InfoData.setAlarmid(requestData.get("AlarmId").toString());
        try {
            InfoData.setStarttime(dateFormat.parse(requestData.get("Time").toString()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        InfoData.setDevicecode(requestData.get("BoardId").toString());
        InfoData.setCreationtime(DateUtils.getNowDate());
        InfoData.setCreatoruserid(Long.valueOf("0"));
        // 新增环保监测信息
        rows = envAlarmMapper.insert(InfoData);
        return rows;
    }
}
