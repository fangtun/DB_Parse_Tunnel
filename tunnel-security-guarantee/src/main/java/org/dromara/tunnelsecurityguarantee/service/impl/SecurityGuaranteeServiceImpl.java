package org.dromara.tunnelsecurityguarantee.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.utils.DateUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.tunnelintelligentconstruction.domain.vo.LargeScaleMachineryVo;
import org.dromara.tunnelintelligentconstruction.mapper.LargeScaleMachineryMapper;
import org.dromara.tunnelsecurityguarantee.domain.Carsinfo;
import org.dromara.tunnelsecurityguarantee.domain.Person;
import org.dromara.tunnelsecurityguarantee.domain.SecurityGuaranteeData;
import org.dromara.tunnelsecurityguarantee.domain.TPersonCarsInfo;
import org.dromara.tunnelsecurityguarantee.domain.vo.CarsListInTunnelVo;
import org.dromara.tunnelsecurityguarantee.domain.vo.PersonCarsListInTunnelVo;
import org.dromara.tunnelsecurityguarantee.domain.vo.PersonListInTunnelVo;
import org.dromara.tunnelsecurityguarantee.mapper.SecurityGuaranteeForCarsMapper;
import org.dromara.tunnelsecurityguarantee.mapper.SecurityGuaranteeForPersonAndCarsMapper;
import org.dromara.tunnelsecurityguarantee.mapper.SecurityGuaranteeForPersonMapper;
import org.dromara.tunnelsecurityguarantee.service.ISecurityGuaranteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 入场信息 业务层处理
 *
 * @author ZZT
 */
@Slf4j
@Service
public class SecurityGuaranteeServiceImpl implements ISecurityGuaranteeService {

    @Autowired
    private SecurityGuaranteeForPersonMapper securityGuaranteeForPersonMapper;

    @Autowired
    private SecurityGuaranteeForCarsMapper securityGuaranteeForCarsMapper;

    @Autowired
    private SecurityGuaranteeForPersonAndCarsMapper securityGuaranteeForPersonAndCarsMapper;

    @Autowired
    private LargeScaleMachineryMapper largeScaleMachineryMapper;

//    @Autowired
//    private ProjectService projectService;
//
//    @Autowired
//    private TunnelService tunnelService;

    /**
     * 新增人员入场信息
     *
     * @param person 人员入场信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertPerson(SecurityGuaranteeData person) {
        Person tperson = new Person();
        tperson.setName(person.getPersonName());
        tperson.setIdcard(person.getIdNumber());
        tperson.setSex(person.getGender());
        tperson.setDuty(person.getTeamName());
        tperson.setStation(person.getDepartment());
        tperson.setStatus(Long.valueOf(person.getAlarm()));
        tperson.setDirection(person.getRegionName().contains("左洞")?1L:2L);
        tperson.setLon(person.getLongitude());
        tperson.setLat(person.getLatitude());
        tperson.setInlx(BigDecimal.valueOf(person.getInlX()));
        tperson.setContact(person.getPhone());
        tperson.setComingtime(DateUtils.parseDate(person.getFirstTime()));
        tperson.setLeavingtime(DateUtils.parseDate(person.getLastTime()));
        tperson.setWorkingtime(Long.valueOf(person.getStayTime()));
        tperson.setProjcode("DLGS");
        tperson.setUnitcode("60-04.0003.00.00");
        tperson.setEmpcode(person.getPersonId());
//        tperson.setCreationtime(DateUtils.getNowDate());
        tperson.setCreationtime(DateUtils.parseDate(person.getLastTime()));
        tperson.setCreatoruserid(Long.valueOf("0"));
        tperson.setPileno(person.getPileNo());
        BigDecimal result = calculateProcessedPileNo(person.getPileNo());
        tperson.setProcessedpileno(result);
        tperson.setCoordinatex(String.valueOf(person.getCoordinatex()));
        tperson.setCoordinatey(String.valueOf(person.getCoordinatey()));
        tperson.setCoordinatez(String.valueOf(person.getCoordinatez()));
        // 新增用户信息
        int rows = securityGuaranteeForPersonMapper.insert(tperson);
        return rows;
    }

    public BigDecimal calculateProcessedPileNo(String pileNo) {
        // 正则表达式：匹配 K 或 ZK 开头，后接整数+小数部分
        String regex = "^(?:ZK|K)(\\d+)\\+(\\d+\\.?\\d*)$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pileNo);

        if (matcher.matches()) {
            String mainPart = matcher.group(1);
            String decimalPart = matcher.group(2);

            BigDecimal main = new BigDecimal(mainPart);
            BigDecimal decimal = new BigDecimal(decimalPart);

            return main.multiply(BigDecimal.valueOf(1000)).add(decimal);
        } else {
            return BigDecimal.ZERO;
        }
    }

    /**
     * 新增车辆入场信息
     *
     * @param cars 车辆入场信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertCars(SecurityGuaranteeData cars) {
        Carsinfo carsinfo = new Carsinfo();
//        carsinfo.setName(cars.getWorkTypeName());
        carsinfo.setName(cars.getTeamName());
        carsinfo.setCartype(cars.getVehicleType());
        carsinfo.setLicenseplatenum(cars.getPlateNumber());
        carsinfo.setContact(cars.getPhone());
        carsinfo.setStatus(Long.valueOf(cars.getAlarm()));
        carsinfo.setDirection(cars.getRegionName().contains("左洞")?1L:2L);
        carsinfo.setLon(cars.getLongitude());
        carsinfo.setLat(cars.getLatitude());
        carsinfo.setInlx(BigDecimal.valueOf(cars.getInlX()));
        carsinfo.setComingtime(DateUtils.parseDate(cars.getFirstTime()));
        carsinfo.setLeavingtime(DateUtils.parseDate(cars.getLastTime()));
        carsinfo.setWorkingtime(Long.valueOf(cars.getStayTime()));
        carsinfo.setProjcode("DLGS");
        carsinfo.setUnitcode("60-04.0003.00.00");
//        carsinfo.setCreationtime(DateUtils.getNowDate());
        carsinfo.setCreationtime(DateUtils.parseDate(cars.getLastTime()));
        BigDecimal result = calculateProcessedPileNo(cars.getPileNo());
//        carsinfo.setProcessedpileno(result);
        carsinfo.setCoordinatex(String.valueOf(cars.getCoordinatex()));
        carsinfo.setCoordinatey(String.valueOf(cars.getCoordinatey()));
        carsinfo.setCoordinatez(String.valueOf(cars.getCoordinatez()));
        carsinfo.setCarsuniqueid(cars.getPersonId());

        // 新增用户信息
        int rows = securityGuaranteeForCarsMapper.insert(carsinfo);
        securityGuaranteeForCarsMapper.updateLargeLocation(carsinfo.getDirection(),cars.getCoordinatex(),cars.getCoordinatey(),cars.getCoordinatez(),cars.getPersonId(),cars.getPileNo());
       return rows;
    }

    /**
     * 新增人员以及车辆入场信息
     *
     * @param info 人员和车辆入场信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertPersonAndCars(SecurityGuaranteeData info) {
        TPersonCarsInfo InfoData = new TPersonCarsInfo();
        if (info.getCardType().equals(10510)){
            InfoData.setName(info.getPersonName());
            InfoData.setType(0);
        } else if (info.getCardType().equals(10520)) {
            InfoData.setName(info.getPlateNumber());
            InfoData.setType(1);
        }
        InfoData.setIdcard(info.getIdNumber());
        InfoData.setSex(info.getGender());
        InfoData.setDuty(info.getWorkTypeName());
        InfoData.setStation(info.getDepartment());
        InfoData.setStatus(Long.valueOf(info.getAlarm()));
        InfoData.setDirection(info.getRegionName().contains("左洞")?1L:2L);
        InfoData.setLon(info.getLongitude());
        InfoData.setLat(info.getLatitude());
        InfoData.setContact(info.getPhone());
        InfoData.setComingtime(DateUtils.parseDate(info.getFirstTime()));
        InfoData.setLeavingtime(DateUtils.parseDate(info.getLastTime()));
        InfoData.setWorkingtime(Long.valueOf(info.getStayTime()));
//        InfoData.setProjcode(info.getProjectId());
//        InfoData.setUnitcode(info.getRegionId());
        if (info.getProjectId().equals("0905FFFF5D7F")){
            InfoData.setProjcode("DLGS");
            InfoData.setUnitcode("60-04.0003.00.00");
        }
        InfoData.setWorknumber(info.getPersonId());
        InfoData.setCreationtime(DateUtils.getNowDate());
        InfoData.setCreatoruserid(Long.valueOf("0"));
        // 新增用户信息
        int rows = securityGuaranteeForPersonAndCarsMapper.insert(InfoData);
        return rows;
    }

    /**
     * 查询人员列表
     *
     * @param projectCode 项目代码
     * @param unitCode    隧道代码
     * @return 结果
     */
    @Override
    public TableDataInfo<PersonListInTunnelVo> getPersonListInTunnel(String projectCode, String unitCode) {

        // 查询人员信息
        List<Person> personList = securityGuaranteeForPersonMapper.selectPersonList(projectCode, unitCode);


        // 转换为VO对象
        List<PersonListInTunnelVo> voList = new ArrayList<>();
        for (Person personData : personList) {
            PersonListInTunnelVo vo = new PersonListInTunnelVo();
            vo.setPersonName(personData.getName());
            vo.setComingTime(personData.getComingtime());
            vo.setDirection(personData.getDirection() != null ? (personData.getDirection() == 1 ? "左洞" : "右洞") : "");
            vo.setInlx(personData.getInlx());
            vo.setDuty(personData.getDuty());
            vo.setContact(personData.getContact());
            vo.setProName(personData.getPeojectName());
            vo.setUnitName(personData.getUnitName());
            PersonListInTunnelVo.Location location = new PersonListInTunnelVo.Location(personData.getCoordinatex(),personData.getCoordinatey(),personData.getCoordinatez());
            //PersonListInTunnelVo.Location location = new PersonListInTunnelVo.Location("-523042.079094","-413371.611130","22229.4");
            vo.setLocation( location);
            voList.add(vo);
        }

        return TableDataInfo.build(voList);
    }

    /**
     * 查询车辆列表
     *
     * @param projectCode 项目代码
     * @param unitCode    隧道代码
     * @return 结果
     */
    @Override
    public TableDataInfo<CarsListInTunnelVo> getCarsListInTunnel(String projectCode, String unitCode) {

        // 查询人员信息
        List<Carsinfo> personList = securityGuaranteeForCarsMapper.selectCarsList(projectCode, unitCode);

        //获取车辆表中被查出车辆的唯一ID，用来排除大机设备表中的用定位卡的数据
        List<String> CarsUniqueIds = personList.stream()
            .map(Carsinfo::getCarsuniqueid)
            .toList();


        // 转换为VO对象
        List<CarsListInTunnelVo> voList = new ArrayList<>();
        for (Carsinfo carsinfo : personList) {
            CarsListInTunnelVo vo = new CarsListInTunnelVo();
            vo.setCarsName(carsinfo.getName());
            vo.setComingTime(carsinfo.getComingtime());
            vo.setDirection(carsinfo.getDirection() != null ? (carsinfo.getDirection() == 1 ? "左洞" : "右洞") : "");
            vo.setInlx(carsinfo.getInlx());
            vo.setLicensePlateNum(carsinfo.getLicenseplatenum());
            vo.setContact(carsinfo.getContact());
            vo.setProName(carsinfo.getPeojectName());
            vo.setUnitName(carsinfo.getUnitName());
            CarsListInTunnelVo.Location location = new CarsListInTunnelVo.Location(carsinfo.getCoordinatex(),carsinfo.getCoordinatey(),carsinfo.getCoordinatez());
//            CarsListInTunnelVo.Location location = new CarsListInTunnelVo.Location(" -1327106.325465","-813748.995077","22900");
            vo.setLocation(location);
            voList.add(vo);
        }

        // 查询设备信息
        List<LargeScaleMachineryVo> machineryList = largeScaleMachineryMapper.selectLargeScaleMachineryList(projectCode, unitCode);
        //过滤大机数据排除CarsCardNo为null并且在CarsUniqueIds不存在的大机数据（使用基站位置定位的大机数据）
        List<LargeScaleMachineryVo> filteredMachineryList = machineryList.stream()
            .filter(machinery -> machinery.getCarsCardNo() != null && machinery.getCoordinatex() != null && machinery.getCoordinatey() != null && machinery.getCoordinatez() != null &&
                !CarsUniqueIds.contains(machinery.getCarsCardNo()))
            .toList();

        //将大机设备在首页洞内设备信息中显示
        for (LargeScaleMachineryVo largeScaleMachinery: filteredMachineryList) {
            CarsListInTunnelVo vo = new CarsListInTunnelVo();
            vo.setCarsName(largeScaleMachinery.getName());
            vo.setDirection(largeScaleMachinery.getDirection());
            vo.setProName(largeScaleMachinery.getPeojectname());
            vo.setUnitName(largeScaleMachinery.getUnitname());
            CarsListInTunnelVo.Location location = new CarsListInTunnelVo.Location(largeScaleMachinery.getCoordinatex(),largeScaleMachinery.getCoordinatey(),largeScaleMachinery.getCoordinatez());
            vo.setLocation(location);
            voList.add(vo);
        }

        return TableDataInfo.build(voList);
    }



    /**
     * 获取人员车辆列表
     *
     * @param projectCode 项目代码
     * @param unitCode    隧道代码
     * @return 人员车辆列表
     */
    @Override
    public TableDataInfo<PersonCarsListInTunnelVo> getPersonCarsListInTunnel(String projectCode, String unitCode) {

//        QueryWrapper<TPersonCarsInfo> wrapper = new QueryWrapper<>();
//
//        wrapper.select("t_person_cars_info.*", "p.pro_abbreviation", "u.name")
//            .apply("LEFT JOIN t_project p ON t_person_cars_info.ProjCode = p.ProjCode")
//            .apply("LEFT JOIN t_unit u ON t_person_cars_info.UnitCode = u.UnitCode");
//
//        // 条件判断
//        if (StringUtils.isNotBlank(projectCode)) { //项目代码
//            wrapper.eq("projcode", projectCode);
//
//            if (StringUtils.isNotBlank(unitCode)) { //隧道代码
//                wrapper.eq("unitcode", unitCode);
//            }
//        }
//
//        wrapper.apply("DATE(CreationTime) = CURRENT_DATE()")  // 使用数据库当前日期函数
//            .inSql("id", "SELECT MAX(id) FROM t_person_cars_info " +
//                "WHERE DATE(CreationTime) = CURRENT_DATE() " +
//                "GROUP BY WorkNumber");

        // 查询人员车辆信息
//        List<TPersonCarsInfo> personCarsList = securityGuaranteeForPersonAndCarsMapper.selectList(wrapper);


        List<TPersonCarsInfo> personCarsList = securityGuaranteeForPersonAndCarsMapper.personAndCarsList(projectCode, unitCode);

        // 转换为VO对象
        List<PersonCarsListInTunnelVo> voList = new ArrayList<>();
        for (TPersonCarsInfo personCarsData : personCarsList) {
            PersonCarsListInTunnelVo vo = new PersonCarsListInTunnelVo();
//            vo.setPersonName(person.getName());
            vo.setName(personCarsData.getName());
            vo.setComingTime(personCarsData.getComingtime());
            vo.setDirection(personCarsData.getDirection() != null ? (personCarsData.getDirection() == 1 ? "左洞" : "右洞") : "");
            vo.setDuty(personCarsData.getDuty());
            vo.setContact(personCarsData.getContact());
            vo.setProName(personCarsData.getPeojectName());
            vo.setUnitName(personCarsData.getUnitName());
            voList.add(vo);
        }

        return TableDataInfo.build(voList);
    }
}
