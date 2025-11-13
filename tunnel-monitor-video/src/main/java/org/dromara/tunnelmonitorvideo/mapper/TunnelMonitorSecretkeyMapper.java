package org.dromara.tunnelmonitorvideo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.tunnelmonitorvideo.domain.PcmsTunnelMonitorSecretkey;
import org.dromara.tunnelmonitorvideo.domain.PcmsTunnelMonitorYsyCamera;
import org.dromara.tunnelmonitorvideo.domain.vo.TunnelMonitorVideoVo;

/**
 * 用户表 数据层
 *
 * @author Lion Li
 */
@Mapper
public interface TunnelMonitorSecretkeyMapper extends BaseMapper<PcmsTunnelMonitorSecretkey> {


    PcmsTunnelMonitorSecretkey getById(String fKeyId);
}
